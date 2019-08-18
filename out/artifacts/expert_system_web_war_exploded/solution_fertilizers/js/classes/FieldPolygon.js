(function() {

  /**
   * Создает поле, которое может содержать участки. Управление участками реализовано
   * через специальную коллекцию, ссылку на которую можно получить с помощью метода
   * <code>getParcels()</code>.
   *
   * @constructor
   * @alias app.FieldPolygon
   * @augments app.BasePolygon
   * @param {app.BasePolygon~BasePolygonOptions} options
   *
   * @example <caption>Пример создания нового поля</caption>
   * var field = new app.FieldPolygon({
   *   map: map,
   *   path: [
   *     {lat:56.399773265979505, lng:40.43771266937256},
   *     {lat:56.401233809472814, lng:40.440781116485596},
   *     {lat:56.39871641501166, lng:40.443077087402344}
   *   ],
   *   color: 'green'
   * });
   *
   * @example <caption>Пример добавления участка на поле</caption>
   * // Получение ссылки на коллекцию участков
   * var fieldParcels = field.getParcels();
   * // Создание нового участка
   * var parcel = new app.ParcelPolygon({...});
   * // Добавление участка в коллекцию
   * fieldParcels.push(parcel);
   *
   * @example <caption>Пример обработчика события изменения свойства ground</caption>
   * // Так как класс app.FieldPolygon наследует google.maps.MVCObject, то
   * // при изменении свойств объекта генерируются специальные события, на
   * // которые можно добавлять свои обработчики
   * field.addListener('ground_changed', function() {
   *   // refs.GROUND_TYPES - специальный справочник типов почв, в котором
   *   // мы ищем объект с описанием нужного типа почвы по его идентификатору
   *   var ground = refs.GROUND_TYPES.lookupId(this.getGround());
   *   // Устанавливаем цвет поля в зависимости от типа почвы
   *   this.setColor(ground ? ground.color : '#cccccc');
   * });
   * //...
   * field.setGround(2);
   *
   */
  function FieldPolygon(options) {
    var field = this;

    // Вызываем конструктор базового класса
    app.BasePolygon.call(this, options);

    // Создаем пустую коллекцию участков
    var parcels = this.parcels_ = new google.maps.MVCArray();

    // Добавляем обработчики событий добавления, удаления и изменения
    // элементов коллекции участков

    parcels.addListener('insert_at', function(index) {
      field.onAddParcel_(this.getAt(index));
      field.redrawFillOverlay_(true);
    });

    parcels.addListener('remove_at', function(index, parcel) {
      field.onRemoveParcel_(parcel);
      field.redrawFillOverlay_(true);
    });

    parcels.addListener('set_at', function(index, prevParcel) {
      field.onRemoveParcel_(prevParcel);
      field.onAddParcel_(this.getAt(index));
      field.redrawFillOverlay_(true);
    });
  }

  FieldPolygon.prototype = Object.create(app.BasePolygon.prototype);

  FieldPolygon.prototype.constructor = FieldPolygon;

  /**
   * Вызывается при добавлении участка в коллекцию.
   *
   * @private
   * @param {app.ParcelPolygon} parcel
   */
  FieldPolygon.prototype.onAddParcel_ = function(parcel) {
    var field = this;

    parcel.fieldListeners_ = [];

    parcel.fieldListeners_.push(
      parcel.addListener('path_changed', function() {
        field.redrawFillOverlay_();
      })
    );

    parcel.bindTo('map', field);
    parcel.bindTo('visible', field);
    parcel.set('field', field);
  };

  /**
   * Вызывается при удалении участка из коллекции.
   *
   * @private
   * @param {app.ParcelPolygon} parcel
   */
  FieldPolygon.prototype.onRemoveParcel_ = function(parcel) {
    var field = this;

    for (var i = 0; i < parcel.fieldListeners_.length; i++) {
      parcel.fieldListeners_[i].remove();
    }
    parcel.fieldListeners_ = null;

    parcel.unbind('map');
    parcel.unbind('visible');
    parcel.set('field');
    parcel.setMap(null);
  };

  /**
   * Возвращает коллекцию участков.
   *
   * @returns {external:google.maps.MVCArray<app.ParcelPolygon>}
   */
  FieldPolygon.prototype.getParcels = function() { return this.parcels_; };

  /**
   * Перерисовывает фон поля с учетом участков. Если были внесены какие-то изменения
   * в коллекцию участков, то resetPaths необходимо установить в true.
   *
   * @private
   * @param {boolean=} resetPaths
   */
  FieldPolygon.prototype.redrawFillOverlay_ = function(resetPaths) {
    if (!resetPaths) {
      this.fillOverlay_.notify('paths');
      return;
    }

    var paths = new google.maps.MVCArray();

    paths.push(this.polygon_.getPath());

    this.parcels_.forEach(function(parcel) {
      paths.push(parcel.getPolygon().getPath());
    });

    this.fillOverlay_.setPaths(paths);
  };

  window.app.FieldPolygon = FieldPolygon;

})();
