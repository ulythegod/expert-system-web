(function() {
    function MapContainerPFSM(div, options) {
        //Вызываем конструктор базового класса
        app.BaseMapContainer.call(this, div, options);
        var con = this;

        this.selectionFieldFlag_ = false;
        this.clickFieldFlag_ = false;
        this.clickInfoWindow_ = false;
        this.openInfoWindow_ = false;
        this.clickRadio_ = false;
        this.notActive = true;

        var triangle_culture = this.triangle_culture_ = new google.maps.Polygon();

        // Добавляем обработчики на добавление, удаление и изменение элементов коллекции полей
        this.fields_.addListener('insert_at', function(index) {
            con.onAddField_(this.getAt(index));
        });

        this.fields_.addListener('remove_at', function(index, field) {
            con.onRemoveField_(field);
        });

        this.fields_.addListener('set_at', function(index, prevField) {
            con.onRemoveField_(prevField);
            con.onAddField_(this.getAt(index));
        });
    }

    MapContainerPFSM.prototype = Object.create(app.BaseMapContainer.prototype);

    MapContainerPFSM.prototype.constructor = MapContainerPFSM;

    /**
     * Снимает выделение со всех объектов и убирает цвет.
     */
    MapContainerPFSM.prototype.clearSelectionAndColor = function() {
        this.fields_.forEach(function(field) {
            field.setActive(false);
            field.set('pattern', null);
            field.getParcels().forEach(function(parcel) {
                parcel.setActive(false);
            });
        });
    };

    /**
     * Задает взаимодействие карты режима
     * Обработки поля с выпадающем списком Посевного участка (поля)
     * Если true - взаимодействие разрешено
     */
    MapContainerPFSM.prototype.setSelectionFieldFlag = function(a) {
        this.selectionFieldFlag_ = a;
    };

    /**
     * Задает взаимодействие пользователя с полями и участками на карте
     * Если true - взаимодействие разрешено
     */
    MapContainerPFSM.prototype.setClickFieldFlag = function(a) {
        this.clickFieldFlag_ = a;
    };

    /**
     * Задает взаимодействие карты режима
     * Обработки поля с выпадающем списком Посевного участка (поля)
     * Если true - взаимодействие разрешено
     */
    MapContainerPFSM.prototype.setClickInfoWindow = function(a) {
        this.clickInfoWindow_ = a;
    };

    /**
     * Задает взаимодействие пользователя с полями и участками на карте
     * Если true - взаимодействие разрешено
     */
    MapContainerPFSM.prototype.setOpenInfoWindow = function(a) {
        this.openInfoWindow_ = a;
    };

    MapContainerPFSM.prototype.setNotActive = function(a) {
        this.notActive = a;
    };

    /**
     * Задает взаимодействие пользователя с полями и участками на карте
     * Если true - взаимодействие разрешено
     */
    MapContainerPFSM.prototype.setClickInfoWindow = function(a) {
        var cont = this;
        this.clickInfoWindow_ = a;
        if(a){
            this.fields_.forEach(function(field){
                field.addListener('mousemove', function() {
                    var field_inside = this;
                    if(cont.openInfoWindow_){
                        cont.closeInfoWindow();
                        cont.openInfoWindow_ = false;
                    }else{
                        clearTimeout(this.timerId);
                        field_inside.timerId = setTimeout(function(){cont.onMouseoverField_(field_inside);}, 500);
                    }
                });
            });
        }else{
            this.fields_.forEach(function(field){
                //google.maps.event.clearListeners(field, 'mouseout');
                google.maps.event.clearListeners(field, 'mousemove');
            });
            google.maps.event.clearListeners(this.triangle_culture_, 'mouseout');
            google.maps.event.clearListeners(this.triangle_culture_, 'mousemove');
        }
    };

    MapContainerPFSM.prototype.closeInfoWindow = function() {
        this.infoWindow_.close();
    };

    MapContainerPFSM.prototype.inputTriangle = function(field) {
        var sqrtOf3 = Math.sqrt(3);
        var triangleArea = field.getArea() * .03;
        var triangleSide = 2 * Math.sqrt(1 / sqrtOf3 * triangleArea);
        var triangleOuterRadius = sqrtOf3 / 3 * triangleSide;
        var trianglePath = [];
        trianglePath.push(google.maps.geometry.spherical.computeOffset(
            field.getCentroid(), triangleOuterRadius, 0));
        trianglePath.push(google.maps.geometry.spherical.computeOffset(
            trianglePath[0], triangleSide, 150));
        trianglePath.push(google.maps.geometry.spherical.computeOffset(
            trianglePath[1], triangleSide, -90));
        this.triangle_culture_ = new app.FieldPolygon({
            path: trianglePath,
            zIndex: 1
        });
        this.triangle_culture_.setActive(true);
        this.triangle_culture_.setColor("transparent");
        this.triangle_culture_.setMap(this.map_);
    };

    MapContainerPFSM.prototype.clearTriangleCulture = function(){
        this.triangle_culture_.setMap(null);
    };

    MapContainerPFSM.prototype.setColorTriangle = function(a){
        this.triangle_culture_.setColor(a);
    };

    MapContainerPFSM.prototype.setPatternTriangle = function(a){
        this.triangle_culture_.set('pattern', a);
    };

    MapContainerPFSM.prototype.taskColor = function(field) {
        var ground;
        ground = refs.GROUND_TYPES.lookupId(field.getGround());
        field.setColor(ground ? ground.color : field.getColor());
    };

    MapContainerPFSM.prototype.getFielldColor = function(field) {
        var ground;
        ground = refs.GROUND_TYPES.lookupId(field.getGround());
        return ground ? ground.color : field.getColor();
    };


    MapContainerPFSM.prototype.ifPrevCulrure = function(field) {
        if(field.getPrevCulture() !== undefined && !$("select[name=predecessor1]").prop('disabled')){
            switch(prev_checked()){
                case 1:
                    return '<tr><td><div class="rectangle" style="background-color:'+
                        prev_color[field.getPrevCulture()]+'"></div></td><td>'+'Предшественник: ' + text_prev +'</td></tr>';
                case 2:
                    return '<tr><td><div class="rectangle">'+
                        patternCropsDiv2(prev_pattern[field.getPrevCulture()])+'</div></td><td>'+'Предшественник: ' + text_prev +'</td></tr>';
                case 3:
                    return '<tr><td rowspan="1"><div class="rectangle" style="background-color:'+
                        prev_color[field.getPrevCulture()]+'">' +
                        '</div></td><td rowspan="2" valign="middle">'+'Предшественник: ' + text_prev +'</td></tr>' +
                        '<tr><td rowspan="1"><div class="rectangle">' + patternCropsDiv2(prev_pattern[field.getPrevCulture()])+'</div></td></tr>';
                case 4:
                    return '<tr><td></td><td>'+'Предшественник: ' + text_prev +'</td></tr>';
            }
        }else{
            return '';
        }
    };


    MapContainerPFSM.prototype.ifCulrure = function(field) {
        if(field.getCulture() !== undefined && !$("select[name=gradeCrop1]").prop('disabled')){
            switch(culture_checked()){
                case 1:
                    return '<tr><td><div class="rectangle" style="background-color:'+
                        color_crops[field.getCulture()]+'"></div></td><td>'+'Культура: ' + text_cult +'</td></tr>';
                case 2:
                    return '<tr><td><div class="rectangle">'+
                        patternCropsDiv2(pattern_crops[field.getCulture()])+'</div></td><td>'+'Культура: ' + text_cult +'</td></tr>';
                case 3:
                    return '<tr><td rowspan="1"><div class="rectangle" style="background-color:'+
                        color_crops[field.getCulture()]+'">' +
                        '</div></td><td rowspan="2" valign="middle">'+'Культура: ' + text_cult +'</td></tr>' +
                        '<tr><td rowspan="1"><div class="rectangle">' + patternCropsDiv2(pattern_crops[field.getCulture()])+'</div></td></tr>';
                case 4:
                    return '<tr><td></td><td>'+'Культура: ' + text_cult +'</td></tr>';
            }
        }else{
            return '';
        }
    };

    MapContainerPFSM.prototype.ifAllModeFert = function(field) {
        var add = "";
        if (field.id == field_id.id) {
            if (text_depth !== undefined && text_depth != null && text_depth != "")
                add += '<tr><td></td><td>' + 'Глубина пахотного слоя, см: ' + text_depth + '</td></tr>';
            if (text_bulk !== undefined && text_bulk != null && text_bulk != "")
                add += '<tr><td></td><td>' + 'Объемная масса почвы: ' + text_bulk + '</td></tr>';
            if (text_planningYield !== undefined && text_planningYield != null && text_planningYield != "")
                add += '<tr><td></td><td>' + 'Планируемая урожайность, ц: ' + text_planningYield + '</td></tr>';
            if (text_predDoze !== undefined && text_predDoze != null && text_predDoze != "")
                add += '<tr><td></td><td>' + 'Доза подкормки предшественника, кг/га: ' + text_predDoze + '</td></tr>';
            if (text_predYield !== undefined && text_predYield != null && text_predYield != "")
                add += '<tr><td></td><td>' + 'Урожай предшественника, ц: ' + text_predYield + '</td></tr>';
            if (text_fert !== undefined && text_fert != null && text_fert != "")
                add += '<tr><td></td><td>' + 'Система применения удобрений: ' + text_fert + '</td></tr>';
            if (text_nitrogen1 !== undefined && text_nitrogen1 != null && text_nitrogen1 != "")
                add += '<tr><td></td><td>' + 'Азот, коэффициент 1: ' + text_nitrogen1 + '</td></tr>';
            if (text_phosphorus1 !== undefined && text_phosphorus1 != null && text_phosphorus1 != "")
                add += '<tr><td></td><td>' + 'Фосфор, коэффициент 1: ' + text_phosphorus1 + '</td></tr>';
            if (text_potassium1 !== undefined && text_potassium1 != null && text_potassium1 != "")
                add += '<tr><td></td><td>' + 'Калий, коэффициент 1: ' + text_potassium1 + '</td></tr>';
            if (text_yearOfUsing !== undefined && text_yearOfUsing != null && text_yearOfUsing != "")
                add += '<tr><td></td><td>' + 'Год использования: ' + text_yearOfUsing + '</td></tr>';
            if (text_nitrogen2 !== undefined && text_nitrogen2 != null && text_nitrogen2 != "")
                add += '<tr><td></td><td>' + 'Азот, коэффициент 2: ' + text_nitrogen2 + '</td></tr>';
            if (text_phosphorus2 !== undefined && text_phosphorus2 != null && text_phosphorus2 != "")
                add += '<tr><td></td><td>' + 'Фосфор, коэффициент 2: ' + text_phosphorus2 + '</td></tr>';
            if (text_potassium2 !== undefined && text_potassium2 != null && text_potassium2 != "")
                add += '<tr><td></td><td>' + 'Калий, коэффициент 2: ' + text_potassium2 + '</td></tr>';
            if (text_nitrogen3 !== undefined && text_nitrogen3 != null && text_nitrogen3 != "")
                add += '<tr><td></td><td>' + 'Азот, коэффициент 3: ' + text_nitrogen3 + '</td></tr>';
            if (text_phosphorus3 !== undefined && text_phosphorus3 != null && text_phosphorus3 != "")
                add += '<tr><td></td><td>' + 'Фосфор, коэффициент 3: ' + text_phosphorus3 + '</td></tr>';
            if (text_potassium3 !== undefined && text_potassium3 != null && text_potassium3 != "")
                add += '<tr><td></td><td>' + 'Калий, коэффициент 3: ' + text_potassium3 + '</td></tr>';
        }
        return add;
    };


    MapContainerPFSM.prototype.ifField = function(field) {
        var ground = refs.GROUND_TYPES.lookupId(field.getGround());
        if(field.id == field_id.id){
            switch(soil_checked()){
                case 1:
                    return '<tr><td><div class="rectangle" style="background-color:'+color_field+'"></div></td><td>'+'Тип почвы: '
                        + (ground ? ground.code + ' ' + ground.name : 'не задан') +'</td></tr>';
                case 4:
                    return '<tr><td></td><td>'+'Тип почвы: '
                        + (ground ? ground.code + ' ' + ground.name : 'не задан') +'</td></tr>';
            }
        }
        return '<tr><td><div class="rectangle" style="background-color:'+field.getColor()+'"></div></td><td>'+'Тип почвы: '
            + (ground ? ground.code + ' ' + ground.name : 'не задан') +'</td></tr>';
    };

    MapContainerPFSM.prototype.onMouseoverField_ = function(field) {
        this.openInfoWindow_ = true;
        var area = field.getArea();
        var contentString =  '<table class="table-map-content"><tr><td></td><td><b>'+'Номер поля: ' + (field.getNumber() || 'не задан') + '</b></td></tr>' +
            this.ifField(field) +
            this.ifPrevCulrure(field) +
            this.ifCulrure(field)+
            '<tr><td></div></td><td>'+'Площадь: ' + area.toFixed(2) + ' кв.м. или ' + (area / 10000).toFixed(2) + ' Га' +'</td></tr>'+
            this.ifAllModeFert(field)+'</table>';

        this.infoWindow_.setPosition(field.getCentroid());
        this.infoWindow_.setContent(contentString);
        this.infoWindow_.open(this.getMap());
    };

    MapContainerPFSM.prototype.delListClick = function() {
        this.fields_.forEach(function(field){
            google.maps.event.clearListeners(field, 'click');
            google.maps.event.clearListeners(field, 'mouseover');
            google.maps.event.clearListeners(field, 'mouseout');
        });
    };

    MapContainerPFSM.prototype.addClickNotActive = function() {
        this.fields_.forEach(function(field){
            field.addListener('click', function(e) {
                cont.onClickFieldNotActive(e, this);
            });
        });
    };

    MapContainerPFSM.prototype.delClickNotActive = function() {
        this.fields_.forEach(function(field){
            google.maps.event.clearListeners(field, 'click');
        })
    };

    MapContainerPFSM.prototype.addListClick = function() {
        this.fields_.forEach(function(field){
            field.addListener('click', function(e) {
                cont.onClickField2_(e, this);
            });
            field.contListeners_.push(
                field.addListener('mouseover', function(e) {
                    this.setStrokeWeight(4);
                })
            );
            field.contListeners_.push(
                field.addListener('mouseout', function() {
                    this.setStrokeWeight(2);
                    if(cont.clickInfoWindow_){
                        clearTimeout(this.timerId);
                        if(cont.openInfoWindow_){
                            cont.closeInfoWindow();
                            cont.openInfoWindow_ = false;
                        }
                    }
                })
            );
        });
    };

    MapContainerPFSM.prototype.delListRightClick = function() {
        this.fields_.forEach(function(field){
            google.maps.event.clearListeners(field, 'rightclick');
        });
    };

    MapContainerPFSM.prototype.addListRightClick = function() {
        this.fields_.forEach(function(field){
            field.addListener('rightclick', function() {
                cont.onMouseoverField_(this);
            })
        });
    };

    MapContainerPFSM.prototype.setClickRadio = function(a){
        this.clickRadio_ = a;
    };

    MapContainerPFSM.prototype.alertField = function(field){
        $('#myModal').modal('show');
        addFieldTest(field);
    };

    MapContainerPFSM.prototype.alertFieldNotActive = function(){
        $('#modal_not_active').modal('show');
    };

    /**
     * Вызывается при клике левой кнопкой по полю. Переключает выделение на поле, если оно
     * может быть выделено. В режиме рисования участка отвечает за то, чтобы участок рисовался
     * только в пределах одного поля.
     *
     * @private
     * @param {external:google.maps.PolyMouseEvent} event
     * @param {app.FieldPolygon} field
     */
    MapContainerPFSM.prototype.onClickField2_ = function(event, field) {
        console.log(field);
        this.infoWindow_.close();
        console.log(this.drawingParcelFlag_);
        if (this.drawingParcelFlag_) {
            if (!this.drawingParcelField_) {
                this.drawingParcelField_ = field;
            }
            if (this.drawingParcelField_ == field) {
                this.drawPolyline_(event.latLng, this.createParcelFromPolyline_);
            }
            return;
        }
        console.log(this.clickFieldFlag_);
        if(this.clickFieldFlag_){
            if(this.clickRadio_){
                this.alertField(field);
                return;
            }
            this.getFields().forEach(function(field_func) {
                if(field_func.getActive() && field_func!==field){
                    field_func.setColor(color_previous);
                    field_func.set('pattern', null);
                    field_func.setActive(false);
                }
            });
            console.log(this.selectionFieldFlag_);
            if(this.selectionFieldFlag_){
                this.setFieldIdForSelect(field.id);
                return;
            }
            console.log(this.canSelect_(field));
            if (this.canSelect_(field)) {
                field.setActive(!field.getActive());
                field.setStrokeWeight(6);
            }
            return;
        }

        if (this.canSelect_(field)) {
            field.setActive(!field.getActive());
        }
    };

    MapContainerPFSM.prototype.onClickFieldNotActive = function(event, field) {
        this.alertFieldNotActive();
    };



    /**
     * Вызывается при добавлении поля к контейнеру.
     * @private
     * @param {app.FieldPolygon} field
     */
    MapContainerPFSM.prototype.onAddField_ = function(field) {
        var cont = this;

        field.contListeners_ = [];

        field.contListeners_.push(
            field.addListener('path_changed', function() {
                cont.infoWindow_.close();
            })
        );

        field.contListeners_.push(
            field.addListener('active_changed', function() {
                var active = this.getActive();
                /**
                 * Это событие срабатывает при выделении поля.
                 *
                 * @event app.MapContainer#selectfield
                 * @param {app.FieldPolygon} field
                 */
                /**
                 * Это событие срабатывает при снятии выделения с поля.
                 *
                 * @event app.MapContainer#deselectfield
                 * @param {app.FieldPolygon} field
                 */
                google.maps.event.trigger(cont, active ? 'selectfield' : 'deselectfield', this);
                if (cont.canEdit_(this)) {
                    this.setEditable(active);
                }
            })
        );

        var parcels = field.getParcels();

        parcels.forEach(function(parcel) {
            cont.onAddParcel_(parcel);
        });

        parcels.contListeners_ = [];

        parcels.contListeners_.push(
            parcels.addListener('insert_at', function(index) {
                cont.onAddParcel_(this.getAt(index));
            })
        );

        parcels.contListeners_.push(
            parcels.addListener('remove_at', function(index, parcel) {
                cont.onRemoveParcel_(parcel);
            })
        );

        parcels.contListeners_.push(
            parcels.addListener('set_at', function(index, prevParcel) {
                cont.onRemoveParcel_(prevParcel);
                cont.onAddParcel_(this.getAt(index));
            })
        );

        field.setMap(cont.map_);
    };

    /**
     * Вызывается при удалении поля из контейнера.
     * @private
     * @param {app.FieldPolygon} field
     */
    MapContainerPFSM.prototype.onRemoveField_ = function(field) {
        var cont = this;
        var parcels = field.getParcels();

        for (var i = 0; i < field.contListeners_.length; i++) {
            field.contListeners_[i].remove();
        }
        field.contListeners_ = null;

        for (var i = 0; i < parcels.contListeners_.length; i++) {
            parcels.contListeners_[i].remove();
        }
        parcels.contListeners_ = null;

        parcels.forEach(function(parcel) {
            cont.onRemoveParcel_(parcel);
        });

        field.setMap(null);
    };


    /**
     * Вызывается при добавлении участка к полю, которое прикреплено к контейнеру.
     * @private
     * @param {app.ParcelPolygon} parcel
     */
    MapContainerPFSM.prototype.onAddParcel_ = function(parcel) {
        var cont = this;

        parcel.contListeners_ = [];

        parcel.contListeners_.push(
            parcel.addListener('path_changed', function() {
                cont.infoWindow_.close();
            })
        );

        parcel.contListeners_.push(
            parcel.addListener('active_changed', function() {
                var active = this.getActive();
                /**
                 * Это событие срабатывает при выделении участка.
                 *
                 * @event app.MapContainer#selectparcel
                 * @param {app.ParcelPolygon} parcel
                 */
                /**
                 * Это событие срабатывает при снятии выделения с участка.
                 *
                 * @event app.MapContainer#deselectparcel
                 * @param {app.ParcelPolygon} parcel
                 */
                google.maps.event.trigger(cont, active ? 'selectparcel' : 'deselectparcel', this);
                if (cont.canEdit_(this)) {
                    this.setEditable(active);
                }
            })
        );
    };

    /**
     * Вызывается при удалении участка с поля, которое прикреплено к контейнеру.
     * @private
     * @param {app.ParcelPolygon} parcel
     */
    MapContainerPFSM.prototype.onRemoveParcel_ = function(parcel) {
        for (var i = 0; i < parcel.contListeners_.length; i++) {
            parcel.contListeners_[i].remove();
        }
        parcel.contListeners_ = null;
    };

    window.app.MapContainerPFSM = MapContainerPFSM;

})();
