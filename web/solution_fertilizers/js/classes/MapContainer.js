(function() {

  /**
   * @typedef {Object} app.MapContainer~MapContainerOptions
   * @property {Array<Class>} selectableElements - Массив классов, экземпляры
   * которых можно выделять. По умолчанию можно выделять участки и поля.
   * @property {Array<Class>} editableElements - Массив классов, экземпляры
   * которых можно редактировать.
   */

  /**
   * Создает контейнер для карты и полей/участков. Карта создается автоматически внутри указанного
   * HTML-элемента. Для доступа к карте нужно использовать метод <code>getMap()</code>, а для
   * доступа к полям - <code>getFields()</code>. Также контейнер включает в себя функциональность
   * для рисования полей и участков.
   *
   * @constructor
   * @alias app.MapContainer
   * @augments external:google.maps.MVCObject
   * @param {HTMLElement} div
   * @param {app.MapContainer~MapContainerOptions} options
   *
   * @example
   * // Инициализация контейнера карты
   * var cont = new app.MapContainer(document.getElementById('map'), {
   *   selectableElements: [app.FieldPolygon]  // можно выделять только поля
   * });
   * // Создание нового поля
   * var field = new app.FieldPolygon({...});
   * // Добавление поля в контейнер
   * cont.getFields().push(field);
   *
   */
  function MapContainer(div, options) {
    var cont = this;

    this.drawingFieldFlag_ = false;
    this.drawingParcelFlag_ = false;
    this.drawingParcelField_ = null;
    this.firstStepMarker_ = new google.maps.Marker();
    this.fieldNextId_ = -1;
    this.parcelNextId_ = -1;
    this.selectionFieldFlag_ = false;
    this.clickFieldFlag_ = false;

    options = options || {};
    this.set('selectableElements', options.selectableElements ||
      [app.FieldPolygon, app.ParcelPolygon]);
    this.set('editableElements', options.editableElements || []);

    // Создаем карту
    var map = this.map_ = new google.maps.Map(div, {
      zoom: 16,
      center: {lat: 56.399285, lng: 40.447454},
      scaleControl: true,
      mapTypeId: google.maps.MapTypeId.ROADMAP,
      draggableCursor: 'pointer',
      disableDoubleClickZoom: true
    });

    // Создаем полилинию, которая будет использоваться для рисования полей и участков
    var polyline = this.polyline_ = new google.maps.Polyline({
      map: map,
      strokeColor: '#000000',
      strokeOpacity: 1.0,
      strokeWeight: 2,
      clickable: false,
      zIndex: 2
    });

    // Создаем информационное окно и прикрепляем его к карте
    var infoWindow = this.infoWindow_ = new google.maps.InfoWindow();
    map.set('infoWindow', infoWindow);

    // Создаем контекстное меню и прикрепляем его к карте
    var contextMenu = this.contextMenu_ = new app.ContextMenu([
      {id: 'mapInfo', label: 'Информация', context: 'map'},
      {id: 'fieldInfo', label: 'Информация', context: 'field'},
      {id: 'parcelInfo', label: 'Информация', context: 'parcel'},
      {id: 'removeVertex', label: 'Удалить вершину', context: 'pathVertex'},
      {separator: true, context: ''},
      {id: 'zoomIn', label: 'Увеличить', context: ''},
      {id: 'zoomOut', label: 'Уменьшить', context: ''},
      {separator: true, context: ''},
      {id: 'panTo', label: 'Центрировать сюда', context: ''}
    ]);
    map.set('contextMenu', contextMenu);

    // Добавляем обработчик нажатия на пункт меню 'Информация' в контексте 'map'
    contextMenu.addListener('mapinfoclick', function(context, params) {
      var position = this.getPosition();
      var contentString = '<b>Координаты щелчка</b>' +
        '<br>Широта:' + position.lat().toFixed(6) +
        '<br>Долгота:' + position.lng().toFixed(6);
      infoWindow.setPosition(position);
      infoWindow.setContent(contentString);
      infoWindow.open(this.getMap());
    });

    // Добавляем обработчик нажатия на пункт меню 'Информация' в контексте 'field'
    contextMenu.addListener('fieldinfoclick', function(context, params) {
      var field = params.field;
      var area = field.getArea();
      var aArea = field.getAArea();
      var sArea = field.getSArea();
      var ground = refs.GROUND_TYPES.lookupId(field.getGround());
       /*var culture = refs.CULTURE_TYPES.lookupId(field.getCulture());
      var prevCulture = refs.CULTURE_TYPES.lookupId(field.getPrevCulture());*/
      /*var contentString = 'Номер поля: ' + (field.getNumber() || 'не задан') + '<br>' +
        'Тип почвы: ' + (ground ? ground.code + ' ' + ground.name : 'не задан') + '<br>' +
        'Тип культуры: ' + (culture ? culture.name : 'не задан') + '<br>' +
        'Предшественник: ' + (prevCulture ? prevCulture.name : 'не задан') + '<br>' +
        'Площадь: ' + area.toFixed(2) + ' кв.м. или ' + (area / 10000).toFixed(2) + ' Га <br>';
      if (aArea) {
        contentString += 'Площадь с высотами: ' + aArea.toFixed(2) + ' кв.м. или ' +
          (aArea / 10000).toFixed(2) + ' Га <br>';
      }
      if (sArea) {
        contentString += 'Заданная площадь: ' + sArea + ' Га <br>';
      }*/
      var contentString = 'Номер поля: ' + (field.getNumber() || 'не задан') + '<br>' +
          'Тип почвы: ' + (ground ? ground.code + ' ' + ground.name : 'не задан') + '<br>' +
            'Площадь: ' + (area / 10000).toFixed(2) + ' Га <br>';
      infoWindow.setPosition(this.getPosition());
      infoWindow.setContent(contentString);
      infoWindow.open(this.getMap());
    });

    // Добавляем обработчик нажатия на пункт меню 'Информация' в контексте 'parcel'
    contextMenu.addListener('parcelinfoclick', function(context, params) {
      var parcel = params.parcel;
      var field = parcel.getField();
      var area = parcel.getArea();
      var aArea = parcel.getAArea();
      var sArea = parcel.getSArea();
      var ground = refs.GROUND_TYPES.lookupId(parcel.getGround());
      var culture = refs.CULTURE_TYPES.lookupId(parcel.getCulture());
      var prevCulture = refs.CULTURE_TYPES.lookupId(parcel.getPrevCulture());
      var contentString = 'Номер поля: ' + (field.getNumber() || 'не задан') + '<br>' +
        'Номер участка: ' + (parcel.getNumber() || 'не задан') + '<br>' +
        'Тип почвы: ' + (ground ? ground.code + ' ' + ground.name : 'не задан') + '<br>' +
        'Тип культуры: ' + (culture ? culture.name : 'не задан') + '<br>' +
        'Предшественник: ' + (prevCulture ? prevCulture.name : 'не задан') + '<br>' +
        'Площадь: ' + area.toFixed(2) + ' кв.м. или ' +
        (area / 10000).toFixed(2) + ' Га <br>';
      if (aArea) {
        contentString += 'Площадь с высотами: ' + aArea.toFixed(2) + ' кв.м. или ' +
          (aArea / 10000).toFixed(2) + ' Га <br>';
      }
      if (sArea) {
        contentString += 'Заданная площадь: ' + sArea + ' Га <br>';
      }
      infoWindow.setPosition(this.getPosition());
      infoWindow.setContent(contentString);
      infoWindow.open(this.getMap());
    });

    // Добавляем обработчик нажатия на пункт меню 'Удалить вершину'
    contextMenu.addListener('removevertexclick', function(context, params) {
      if (params.path.getLength() > 3) {
        params.path.removeAt(params.vertex);
      } else {
        alert('Количество вершин меньше четырех!');
      }
    });

    // Добавляем обработчик нажатия на пункт меню 'Увеличить'
    contextMenu.addListener('zoominclick', function() {
      var map = this.getMap();
      map.setZoom(map.getZoom() + 1);
    });

    // Добавляем обработчик нажатия на пункт меню 'Уменьшить'
    contextMenu.addListener('zoomoutclick', function() {
      var map = this.getMap();
      map.setZoom(map.getZoom() - 1);
    });

    // Добавляем обработчик нажатия на пункт меню 'Центрировать сюда'
    contextMenu.addListener('pantoclick', function() {
      this.getMap().panTo(this.getPosition());
    });

    // При клике правой кнопкой по карте открываем меню в контексте 'map'
    map.addListener('rightclick', function(e) {
      contextMenu.open(map, 'map', {latLng: e.latLng});
    });

    // При клике левой кнопкой по карте рисуем новый сегмент полилинии, если активен
    // соответствующий режим
    map.addListener('click', function(e) {
      if (cont.drawingFieldFlag_) {
        cont.drawPolyline_(e.latLng, cont.createFieldFromPolyline_);
      }
    });

    // Создаем коллекцию полей
    var fields = this.fields_ = new google.maps.MVCArray();

    // Добавляем обработчики на добавление, удаление и изменение элементов коллекции полей
    fields.addListener('insert_at', function(index) {
      cont.onAddField_(this.getAt(index));
    });

    fields.addListener('remove_at', function(index, field) {
      cont.onRemoveField_(field);
    });

    fields.addListener('set_at', function(index, prevField) {
      cont.onRemoveField_(prevField);
      cont.onAddField_(this.getAt(index));
    });
  }

  MapContainer.prototype = new google.maps.MVCObject();

  /**
   * Вызывается при клике левой кнопкой по полю. Переключает выделение на поле, если оно
   * может быть выделено. В режиме рисования участка отвечает за то, чтобы участок рисовался
   * только в пределах одного поля.
   *
   * @private
   * @param {external:google.maps.PolyMouseEvent} event
   * @param {app.FieldPolygon} field
   */
  MapContainer.prototype.onClickField_ = function(event, field) {
    this.infoWindow_.close();
    if (this.drawingParcelFlag_) {
      if (!this.drawingParcelField_) {
        this.drawingParcelField_ = field;
      }
      if (this.drawingParcelField_ == field) {
        this.drawPolyline_(event.latLng, this.createParcelFromPolyline_);
      }
      return;
    }

    //___________________________________
    if(this.clickFieldFlag_){
      this.getFields().forEach(function(field_func) {
        if(field_func.getActive() && field_func!==field){
          field_func.setActive(false);
            field_func.setColor('#999999');
          }
      });
      this.taskColor(field);
      if(this.selectionFieldFlag_){
        this.setFieldIdForSelect(field.id);
        return;
      }
      if (this.canSelect_(field)) {
        if(field.getActive()){
            field.setColor('#999999');
        }
        field.setActive(!field.getActive());
      }
      return;
    }
    //___________________________________

    if (this.canSelect_(field)) {
      field.setActive(!field.getActive());
    }
  };

  /**
   * Вызывается при клике правой кнопкой по полю. Открывает контекстное меню.
   *
   * @private
   * @param {external:google.maps.PolyMouseEvent} event
   * @param {app.FieldPolygon} field
   */
  MapContainer.prototype.onRightClickField_ = function(event, field) {
    if (event.vertex != undefined) {
      this.contextMenu_.open(this.map_, 'pathVertex', {
        path: field.getPolygon().getPaths().getAt(event.path),
        vertex: event.vertex,
        latLng: event.latLng
      });
    } else {
      this.contextMenu_.open(this.map_, 'field', {field: field, latLng: event.latLng});
    }
  };

  /**
   * Вызывается при клике левой кнопкой по участку. Переключает выделение на участке, если он
   * может быть выделен.
   *
   * @private
   * @param {external:google.maps.PolyMouseEvent} event
   * @param {app.ParcelPolygon} parcel
   */
  MapContainer.prototype.onClickParcel_ = function(event, parcel) {
    this.infoWindow_.close();
    if (this.canSelect_(parcel)) {
      parcel.setActive(!parcel.getActive());
    }
  };

  /**
   * Вызывается при клике правой кнопкой по участку. Открывает контекстное меню.
   *
   * @private
   * @param {external:google.maps.PolyMouseEvent} event
   * @param {app.ParcelPolygon} parcel
   */
  MapContainer.prototype.onRightClickParcel_ = function(event, parcel) {
    if (event.vertex != undefined) {
      this.contextMenu_.open(this.map_, 'pathVertex', {
        path: parcel.getPolygon().getPaths().getAt(event.path),
        vertex: event.vertex,
        latLng: event.latLng
      });
    } else {
      this.contextMenu_.open(this.map_, 'parcel', {parcel: parcel, latLng: event.latLng});
    }
  };

  /**
   * Вызывается при добавлении поля к контейнеру.
   * @private
   * @param {app.FieldPolygon} field
   */
  MapContainer.prototype.onAddField_ = function(field) {
    var cont = this;

    field.contListeners_ = [];

    field.contListeners_.push(
      field.addListener('click', function(e) {
        cont.onClickField_(e, this);
      })
    );

    field.contListeners_.push(
      field.addListener('rightclick', function(e) {
        cont.onRightClickField_(e, this);
      })
    );

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
  MapContainer.prototype.onRemoveField_ = function(field) {
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
  MapContainer.prototype.onAddParcel_ = function(parcel) {
    var cont = this;

    parcel.contListeners_ = [];

    parcel.contListeners_.push(
      parcel.addListener('click', function(e) {
        cont.onClickParcel_(e, this);
      })
    );

    parcel.contListeners_.push(
      parcel.addListener('rightclick', function(e) {
        cont.onRightClickParcel_(e, this);
      })
    );

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
  MapContainer.prototype.onRemoveParcel_ = function(parcel) {
    for (var i = 0; i < parcel.contListeners_.length; i++) {
      parcel.contListeners_[i].remove();
    }
    parcel.contListeners_ = null;
  };

  /**
   * Возвращает карту.
   * @returns {external:google.maps.Map}
   */
  MapContainer.prototype.getMap = function() {
    return this.map_;
  };

  /**
   * Возвращает true, если в данный момент активен режим рисования поля.
   * @returns {boolean}
   */
  MapContainer.prototype.drawingFieldIsActive = function() {
    return this.drawingFieldFlag_;
  };

  /**
   * Начинает процесс рисования поля.
   */
  MapContainer.prototype.startDrawingField = function() {
    if (!this.drawingFieldFlag_) {
      if (this.drawingParcelFlag_) {
        this.stopDrawingParcel();
      }
      this.drawingFieldFlag_ = true;
      /**
       * Это событие срабатывает при начале процесса рисования поля.
       *
       * @event app.MapContainer#startdrawingfield
       */
      google.maps.event.trigger(this, 'startdrawingfield');
    }
  };

  /**
   * Отменяет процесс рисования поля, при этом все объекты, использованные при
   * рисовании, убираются с карты.
   */
  MapContainer.prototype.stopDrawingField = function() {
    if (this.drawingFieldFlag_) {
      this.clearPolyline_();
      this.drawingFieldFlag_ = false;
      /**
       * Это событие срабатывает при отмене процесса рисования поля.
       *
       * @event app.MapContainer#stopdrawingfield
       */
      google.maps.event.trigger(this, 'stopdrawingfield');
    }
  };

  /**
   * Возвращает true, если в данный момент активен режим рисования участка.
   * @returns {boolean}
   */
  MapContainer.prototype.drawingParcelIsActive = function() {
    return this.drawingParcelFlag_;
  };

  /**
   * Начинает процесс рисования участка.
   */
  MapContainer.prototype.startDrawingParcel = function() {
    if (!this.drawingParcelFlag_) {
      if (this.drawingFieldFlag_) {
        this.stopDrawingField();
      }
      this.drawingParcelFlag_ = true;
      /**
       * Это событие срабатывает при начале процесса рисования участка.
       *
       * @event app.MapContainer#startdrawingparcel
       */
      google.maps.event.trigger(this, 'startdrawingparcel');
    }
  };

  /**
   * Отменяет процесс рисования участка, при этом все объекты, использованные при
   * рисовании, убираются с карты.
   */
  MapContainer.prototype.stopDrawingParcel = function() {
    if (this.drawingParcelFlag_) {
      this.clearPolyline_();
      this.drawingParcelFlag_ = false;
      this.drawingParcelField_ = null;
      /**
       * Это событие срабатывает при отмене процесса рисования участка.
       *
       * @event app.MapContainer#stopdrawingparcel
       */
      google.maps.event.trigger(this, 'stopdrawingparcel');
    }
  };

  /**
   * Возвращает коллекцию полей.
   * @returns {external:google.maps.MVCArray<app.FieldPolygon>}
   */
  MapContainer.prototype.getFields = function() {
    return this.fields_;
  };

  /**
   * Создает поле из нарисованной полилинии.
   *
   * @private
   */
  MapContainer.prototype.createFieldFromPolyline_ = function() {
    var field = new app.FieldPolygon({
      id: this.fieldNextId_--,
      path: this.polyline_.getPath()
    });
    this.fields_.push(field);
    this.clearPolyline_();
    this.drawingFieldFlag_ = false;
    /**
     * Это событие срабатывает при создании поля в результате рисования.
     *
     * @event app.MapContainer#createfield
     * @param {app.FieldPolygon} field
     */
    google.maps.event.trigger(this, 'createfield', field);
  };

  /**
   * Создает участок из нарисованной полилинии.
   *
   * @private
   */
  MapContainer.prototype.createParcelFromPolyline_ = function() {
    var parcel = new app.ParcelPolygon({
      id: this.parcelNextId_--,
      path: this.polyline_.getPath()
    });
    this.drawingParcelField_.getParcels().push(parcel);
    this.clearPolyline_();
    this.drawingParcelFlag_ = false;
    this.drawingParcelField_ = null;
    /**
     * Это событие срабатывает при создании участка в результате рисования.
     *
     * @event app.MapContainer#createparcel
     * @param {app.ParcelPolygon} parcel
     */
    google.maps.event.trigger(this, 'createparcel', parcel);
  };

  /**
   * Рисует новый сегмент полилинии. Если полилиния замкнулась, то вызывается функция,
   * переданная в параметре onClosePath.
   *
   * @private
   * @param {external:google.maps.LatLng} latLng
   * @param {function} onClosePath
   */
  MapContainer.prototype.drawPolyline_ = function(latLng, onClosePath) {
    var path = this.polyline_.getPath();
    var pathLen = path.getLength();
    if (pathLen > 2 && this.pointClick_(path.getAt(0), latLng)) {
      onClosePath.call(this);
    } else {
      if (pathLen == 0) {
        this.firstStepMarker_.setOptions({
          position: latLng,
          map: this.map_
        });
      } else if (pathLen == 1) {
        this.firstStepMarker_.setMap(null);
      }
      path.push(latLng);
    }
  };

  /**
   * Убирает с карты объекты, использованные при рисовании поля/участка:
   * маркер, обозначающий первую точку, и полилинию.
   *
   * @private
   */
  MapContainer.prototype.clearPolyline_ = function() {
    this.firstStepMarker_.setMap(null);
    this.polyline_.setPath([]);
  };

  /**
   * Возвращает true, если координаты двух точек находятся достаточно близко друг к другу.
   * @private
   * @param {external:google.maps.LatLng} firstPoint
   * @param {external:google.maps.LatLng} secondPoint
   * @returns {boolean}
   */
  MapContainer.prototype.pointClick_ = function(firstPoint, secondPoint) {
    var zoom = this.map_.getZoom();
    var distance = google.maps.geometry.spherical.computeLength([firstPoint, secondPoint]);
    return distance <= Math.pow(2, 19 - zoom) * 3.75;
  };

  /**
   * Возвращает, можно ли выделить данный элемент.
   * @private
   * @param {*} element
   * @returns {boolean}
   */
  MapContainer.prototype.canSelect_ = function(element) {
    return this.get('selectableElements').indexOf(element.constructor) != -1;
  };

  /**
   * Возвращает, можно ли редактировать данный элемент.
   * @private
   * @param {*} element
   * @returns {boolean}
   */
  MapContainer.prototype.canEdit_ = function(element) {
    return this.get('editableElements').indexOf(element.constructor) != -1;
  };

  /**
   * Снимает выделение со всех объектов.
   */
  MapContainer.prototype.clearSelection = function() {
    this.fields_.forEach(function(field) {
      field.setActive(false);
      field.getParcels().forEach(function(parcel) {
        parcel.setActive(false);
      });
    });
  };

    /**
     * Снимает выделение со всех объектов и убирает цвет.
     */
    MapContainer.prototype.clearSelectionAndColor = function() {
        this.fields_.forEach(function(field) {
            field.setActive(false);
            field.setColor('#999999');
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
    MapContainer.prototype.setSelectionFieldFlag = function(a) {
          this.selectionFieldFlag_ = a;
    };

    /**
     * Задает взаимодействие пользователя с полями и участками на карте
     * Если true - взаимодействие разрешено
     */
    MapContainer.prototype.setClickFieldFlag = function(a) {
        this.clickFieldFlag_ = a;
    };

    /**
     *Задание fieldId
     */
    MapContainer.prototype.setFieldIdForSelect = function(id) {
        $("form#parameters1 select[name=field1]").val(id);
        $("form#parameters1 select[name=field1]").trigger("change");
    };

    MapContainer.prototype.taskColor = function(field) {
        var ground = refs.GROUND_TYPES.lookupId(field.getGround());
        field.setColor(ground ? ground.color : field.getColor());
    };

  window.app.MapContainer = MapContainer;

})();
