(function() {

  /**
   * @typedef {Object} app.BasePolygon~BasePolygonOptions
   * @property {boolean} active - Если установлено в true, обводка полигона становится более
   * толстой и непрозрачность заливки увеличивается.
   * @property {string} color=#FF0000 - Цвет заливки и обводки. Все цвета CSS3 поддерживаются, за
   * исключением расширенных именованных цветов.
   * @property {number} culture - Идентификатор типа культуры.
   * @property {boolean} editable=false - Если установлено в true, пользователь может изменять
   * форму полигона перетаскиванием контрольных точек, которые показываются на вершинах и на каждом
   * сегменте.
   * @property {number} ground - Идентификатор типа почвы.
   * @property {number} id - Идентификатор поля/участка.
   * @property {external:google.maps.Map} map - Карта, на которой отображается полигон.
   * @property {number} number - Номер поля/участка.
   * @property {external:google.maps.MVCArray<external:google.maps.LatLng>|
   * Array<external:google.maps.LatLng|external:google.maps.LatLngLiteral>} path - Упорядоченная
   * последовательность координат полигона.
   * @property {string} pattern - Тип штриховки полигона. Возможные значения этой опции описаны
   * в документации класса {@link app.FillOverlay}.
   * @property {string} patternColor=#000000 - Цвет штриховки полигона. Все цвета CSS3
   * поддерживаются, за исключением расширенных именованных цветов.
   * @property {number} patternOpacity=0.7 - Непрозрачность штриховки от 0.0 до 1.0.
   * @property {number} prevCulture - Идентификатор типа культуры-предшественника.
   * @property {number} sArea - Заданная площадь полигона.
   * @property {boolean} visible=true - Является ли полигон видимым на карте.
   */

  /**
   * Данный класс является абстрактным и базовым для классов {@link app.FieldPolygon} (поле) и
   * {@link app.ParcelPolygon} (участок).
   *
   * @constructor
   * @abstract
   * @alias app.BasePolygon
   * @augments external:google.maps.MVCObject
   * @param {app.BasePolygon~BasePolygonOptions} options
   */
  function BasePolygon(options) {
    var base = this;
    options = options || {};

    // Создаем лежащий в основе объект класса google.maps.Polygon с прозрачным фоном.
    // За фон будет отвечать другой объект.
    var poly = this.polygon_ = new google.maps.Polygon({
      paths: options.path || [],
      fillColor: 'transparent',
      strokeOpacity: 0.8,
      strokeWeight: 2,
      zIndex: 0
    });

    // Добавляем к объекту класса google.maps.Polygon обработчики событий click и rightclick,
    // которые просто генерируют эти же самые события на текущем объекте, что позволяет в
    // дальнейшем добавлять обработчики событий click и rightclick к экземплярам
    // классов-наследников BasePolygon

    /**
     * Это событие вызывается при клике левой кнопкой мыши на полигоне.
     *
     * @event app.BasePolygon#click
     * @param {external:google.maps.PolyMouseEvent} event
     */
    poly.addListener('click', function(e) {
      google.maps.event.trigger(base, 'click', e);
    });

    /**
     * Это событие вызывается при клике правой кнопкой мыши на полигоне.
     *
     * @event app.BasePolygon#rightclick
     * @param {external:google.maps.PolyMouseEvent} event
     */
    poly.addListener('rightclick', function(e) {
      google.maps.event.trigger(base, 'rightclick', e);
    });

    // Создаем объект, отвечающий за отображение фона и штриховки полигона
    var fillOverlay = this.fillOverlay_ = new app.FillOverlay({
      paths: poly.getPaths()
    });

    // Добавляем обработчик события изменения свойства map текущего объекта, который
    // автоматически меняет это же свойство у объекта, отвечающего за фон.
    //
    // Мы не можем просто сделать fillOverlay.bindTo('map', base), т.к. при удалении
    // с карты объекта класса google.maps.OverlayView, к которому принадлежит fillOverlay,
    // происходит неявный вызов unbindAll().
    this.addListener('map_changed', function() {
      fillOverlay.setMap(this.getMap());
    });

    // При фактическом добавлении объекта-фона на карту мы связываем некоторые его свойства
    // со свойствами текущего объекта
    fillOverlay.addListener('onAdd', function() {
      this.bindTo('visible', base);
      this.bindTo('pattern', base);
      this.bindTo('patternColor', base);
      this.bindTo('patternOpacity', base);
      this.bindTo('fillColor', base, 'color');
    });

    // Добавляем один обработчик для всех возможных событий изменения последовательности
    // координат полигона
    var polyPath = poly.getPath();
    polyPath.addListener('insert_at', polyPathChanged);
    polyPath.addListener('remove_at', polyPathChanged);
    polyPath.addListener('set_at', polyPathChanged);

    /**
     * Это событие вызывается при любом изменении формы полигона.
     *
     * @event app.BasePolygon#path_changed
     */
    function polyPathChanged() {
      // Оповещаем объект, отвечающий за фон, о том, что координаты изменились и
      // нужно все перерисовать
      fillOverlay.notify('paths');
      // Пересчитываем площадь поля
      base.area_ = google.maps.geometry.spherical.computeArea(polyPath);
      // Генерируем единое событие изменения координат полигона на текущем объекте
      google.maps.event.trigger(base, 'path_changed');
    }

    // Рассчитываем площадь полигона
    this.area_ = google.maps.geometry.spherical.computeArea(polyPath);
    this.aArea_ = null;

    // Устанавливаем публичные свойства текущего объекта
    this.set('id', options.id);
    this.set('map', options.map);
    this.set('color', options.color || '#FF0000');
    this.set('visible', options.visible !== false);
    this.set('editable', options.editable);
    this.set('pattern', options.pattern);
    this.set('patternColor', options.patternColor || '#000000');
    this.set('patternOpacity', options.patternOpacity == undefined ? 0.7 : options.patternOpacity);
    this.set('number', options.number);
    this.set('ground', options.ground);
    this.set('culture', options.culture);
    this.set('prevCulture', options.prevCulture);
    this.set('sArea', options.sArea);
    this.set('active', options.active);

    // Связываем некоторые свойства объекта класса google.maps.Polygon и текущего объекта
    poly.bindTo('map', this);
    poly.bindTo('visible', this);
    poly.bindTo('editable', this);
    poly.bindTo('strokeColor', this, 'color');
  }

  BasePolygon.prototype = new google.maps.MVCObject();

  /**
   * Возвращает ссылку на лежащий в основе объект класса
   * [google.maps.Polygon]{@link https://developers.google.com/maps/documentation/javascript/3.exp/reference#Polygon}.
   * @returns {external:google.maps.Polygon}
   */
  BasePolygon.prototype.getPolygon = function() { return this.polygon_; };

  /**
   * Возвращает числовой идентификатор поля/участка.
   * @returns {number}
   */
  BasePolygon.prototype.getId = function() { return this.get('id'); };

  /**
   * Устанавливает числовой идентификатор поля/участка.
   * @param {number} id
   */
  BasePolygon.prototype.setId = function(id) { this.set('id', id); };

  /**
   * Возвращает номер поля/участка.
   * @returns {number}
   */
  BasePolygon.prototype.getNumber = function() { return this.get('number'); };

  /**
   * Устанавливает номер поля/участка.
   * @param {number} number
   */
  BasePolygon.prototype.setNumber = function(number) { this.set('number', number); };

  /**
   * Возвращает рассчитанную площадь поля.
   * @returns {number}
   */
  BasePolygon.prototype.getArea = function() { return this.area_; };

  /**
   * Возвращает рассчитанную площадь поля с высотами.
   * @todo Пока не реализовано.
   * @returns {number}
   */
  BasePolygon.prototype.getAArea = function() { return this.aArea_; };

  /**
   * Возвращает заданную площадь поля.
   * @returns {number}
   */
  BasePolygon.prototype.getSArea = function() { return this.get('sArea'); };

  /**
   * Устанавливает заданную площадь поля.
   * @param {number} sArea
   */
  BasePolygon.prototype.setSArea = function(sArea) { this.set('sArea', sArea); };

  /**
   * Возвращает карту, к которой прикреплен полигон.
   * @returns {external:google.maps.Map}
   */
  BasePolygon.prototype.getMap = function() { return this.get('map'); };

  /**
   * Выводит полигон на указанной карте. Если map установлен в null, то полигон будет удален.
   * @param {external:google.maps.Map} map
   */
  BasePolygon.prototype.setMap = function(map) { this.set('map', map); };

  /**
   * Возвращает, является ли полигон активным (выделенным).
   * @returns {boolean}
   */
  BasePolygon.prototype.getActive = function() { return this.get('active'); };

  /**
   * Если установлено в true, обводка полигона становится более толстой и непрозрачность
   * заливки увеличивается. Таким образом обозначается выделение полигона.
   * @param {boolean} active
   */
  BasePolygon.prototype.setActive = function(active) { this.set('active', active); }

  /**
   * Вызывается автоматически при установке свойства active объекта. Выполняет всю работу
   * по визуализации выделения.
   * @protected
   */
  BasePolygon.prototype.active_changed = function() {
    var active = this.get('active');
    this.polygon_.set('strokeWeight', active ? 4 : 2);
    this.fillOverlay_.set('fillOpacity', active ? 0.55 : 0.35);
  };

  /**
   * Возвращает текущий цвет заливки и обводки полигона.
   * @returns {string}
   */
  BasePolygon.prototype.getColor = function() { return this.get('color'); };

  /**
   * Устанавливает текущий цвет заливки и обводки полигона. Все цвета CSS3 поддерживаются, за
   * исключением расширенных именованных цветов.
   * @param {string} color
   */
  BasePolygon.prototype.setColor = function(color) { this.set('color', color); }

  /**
   * Возвращает, может ли пользователь редактировать форму полигона.
   * @returns {boolean}
   */
  BasePolygon.prototype.getEditable = function() { return this.get('editable'); };

  /**
   * Если установлено в true, пользователь может редактировать форму полигона, перетаскивая
   * контрольные точки на вершинах и на каждом сегменте.
   * @param {boolean} editable
   */
  BasePolygon.prototype.setEditable = function(editable) { this.set('editable', editable); };

  /**
   * Возвращает, виден ли полигон на карте.
   * @returns {boolean}
   */
  BasePolygon.prototype.getVisible = function() { return this.get('visible'); };

  /**
   * Скрывает полигон, если установлено в false.
   * @param {boolean} visible
   */
  BasePolygon.prototype.setVisible = function(visible) { this.set('visible', visible); };

  /**
   * Возвращает идентификатор типа почвы поля/участка.
   * @returns {number}
   */
  BasePolygon.prototype.getGround = function() { return this.get('ground'); };

  /**
   * Устанавливает идентификатор типа почвы поля/участка.
   * @param {number} ground
   */
  BasePolygon.prototype.setGround = function(ground) { this.set('ground', ground); };

  /**
   * Возвращает идентификатор типа культуры поля/участка.
   * @returns {number}
   */
  BasePolygon.prototype.getCulture = function() { return this.get('culture'); };

  /**
   * Устанавливает идентификатор типа культуры поля/участка.
   * @param {number} culture
   */
  BasePolygon.prototype.setCulture = function(culture) { this.set('culture', culture); };

  /**
   * Возвращает идентификатор типа культуры-предшественника поля/участка.
   * @returns {number}
   */
  BasePolygon.prototype.getPrevCulture = function() { return this.get('prevCulture'); };

  /**
   * Устанавливает идентификатор типа культуры-предшественника поля/участка.
   * @param {number} culture
   */
  BasePolygon.prototype.setPrevCulture = function(culture) { this.set('prevCulture', culture); };

  /**
   * Возвращает координаты центра масс полигона.
   * @see {@link https://en.wikipedia.org/wiki/Centroid#Centroid_of_polygon}
   * @returns {external:google.maps.LatLng}
   */
  BasePolygon.prototype.getCentroid = function() {
    var path = this.polygon_.getPath();
    var pathLen = path.getLength();
    var x = 0;
    var y = 0;
    var A = 0;
    var f;
    var j = pathLen - 1;

    for (var i = 0; i < pathLen; j = i++) {
      var pt1 = path.getAt(i);
      var pt2 = path.getAt(j);
      f = pt1.lat() * pt2.lng() - pt2.lat() * pt1.lng();
      x += (pt1.lat() + pt2.lat()) * f;
      y += (pt1.lng() + pt2.lng()) * f;
      A += f;
    }
    A /= 2;
    f = A * 6;
    return new google.maps.LatLng(x / f, y / f);
  };

  window.app.BasePolygon = BasePolygon;

})();
