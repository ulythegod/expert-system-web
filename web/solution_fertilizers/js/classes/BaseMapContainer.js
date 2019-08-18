(function() {
    function BaseMapContainer(div, options) {

        this.drawingFieldFlag_ = false;

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

        // Создаем информационное окно и прикрепляем его к карте
        var infoWindow = this.infoWindow_ = new google.maps.InfoWindow();
        map.set('infoWindow', infoWindow);

        // Создаем коллекцию полей
        var fields = this.fields_ = new google.maps.MVCArray();
    }

    BaseMapContainer.prototype = new google.maps.MVCObject();



    /**
     * Возвращает, можно ли выделить данный элемент.
     * @private
     * @param {*} element
     * @returns {boolean}
     */
    BaseMapContainer.prototype.canSelect_ = function(element) {
        return this.get('selectableElements').indexOf(element.constructor) != -1;
    };

    /**
     * Возвращает, можно ли редактировать данный элемент.
     * @private
     * @param {*} element
     * @returns {boolean}
     */
    BaseMapContainer.prototype.canEdit_ = function(element) {
        return this.get('editableElements').indexOf(element.constructor) != -1;
    };

    /**
     * Возвращает карту.
     * @returns {external:google.maps.Map}
     */
    BaseMapContainer.prototype.getMap = function() {
        return this.map_;
    };

    /**
     * Возвращает коллекцию полей.
     * @returns {external:google.maps.MVCArray<app.FieldPolygon>}
     */
    BaseMapContainer.prototype.getFields = function() {
        return this.fields_;
    };

    window.app.BaseMapContainer = BaseMapContainer;

})();
