(function() {

  /**
   * @typedef {Object} app.FillOverlay~FillOverlayOptions
   * @property {string} fillColor=none - Цвет заливки. Все цвета CSS3 поддерживаются, за
   * исключением расширенных именованных цветов.
   * @property {number} fillOpacity=0.35 - Непрозрачность заливки от 0.0 до 1.0.
   * @property {external:google.maps.Map} map - Карта, на которой отображается фон.
   * @property {external:google.maps.MVCArray<external:google.maps.MVCArray<external:google.maps.LatLng>>}
   * paths - Контуры фона. Первый элемент массива описывает внешний контур, а остальные -
   * внутренние.
   * @property {string} pattern - Тип штриховки.
   * @property {string} patternColor=#000000 - Цвет штриховки. Все цвета CSS3 поддерживаются, за
   * исключением расширенных именованных цветов.
   * @property {number} patternOpacity=0.7 - Непрозрачность штриховки от 0.0 до 1.0.
   * @property {boolean} visible=true - Является ли фон видимым на карте.
   */

  /**
   * Создает фон для полигона с возможностью задавать внутренние контуры, в которых фон
   * выводиться не будет. Кроме цвета и непрозрачности заливки для фона можно также указать
   * тип, цвет и непрозрачность штриховки. Тип штриховки задается строкой следующего вида:
   * <code>&lt;Имя_узора&gt;[_&lt;Угол_поворота&gt;]</code>. <code>&lt;Имя_узора&gt;</code>
   * должно быть именем одного из классов в пространстве имен <code>app.pattern</code>,
   * за исключением <code>Base</code>.
   *
   * @constructor
   * @alias app.FillOverlay
   * @augments external:google.maps.OverlayView
   * @param {app.FillOverlay~FillOverlayOptions} options
   *
   * @example
   * // Штриховка поля горизонтальными линиями
   * field.set('pattern', 'Line');
   * // Штриховка поля горизонтальными линиями под углом 45 градусов
   * field.set('pattern', 'Line_45');
   *
   */
  function FillOverlay(options) {
    this.bounds_ = this.calculateBounds_(options.paths);
    this.div_ = null;
    this.svg_ = null;
    this.defs_ = null;
    this.fillColorPath_ = null;
    this.patternPath_ = null;
    this.patternObject_ = null;

    this.set('paths', options.paths);
    this.set('fillColor', options.fillColor || 'none');
    this.set('fillOpacity', options.fillOpacity == undefined ? 0.35 : options.fillOpacity);
    this.set('pattern', options.pattern);
    this.set('patternColor', options.patternColor || '#000000');
    this.set('patternOpacity', options.patternOpacity == undefined ? 0.7 : options.patternOpacity);
    this.set('visible', options.visible !== false);
    this.set('map', options.map);
  }

  FillOverlay.prototype = new google.maps.OverlayView();

  /**
   * Этот метод вызывается один раз после вызова <code>setMap()</code> с правильной картой.
   * К этому моменту панели и проекция карты уже инициализированы. Создает необходимые
   * DOM-элементы и добавляет их к нужной панели карты.
   *
   * @protected
   */
  FillOverlay.prototype.onAdd = function() {
    /**
     * Это событие генерируется при вызове метода <code>onAdd()</code>.
     *
     * @event app.FillOverlay#onAdd
     */
    google.maps.event.trigger(this, 'onAdd');

    // Создаем DIV-контейнер
    var div = this.div_ = document.createElement('div');
    div.style.position = 'absolute';

    // Создаем корневой SVG-элемент и добавляем его в DIV-контейнер
    var svgns = 'http://www.w3.org/2000/svg';
    var svg = this.svg_ = document.createElementNS(svgns, 'svg');
    svg.setAttribute('preserveAspectRatio', 'xMidYMid meet');
    svg.style.width = '100%';
    svg.style.height = '100%';
    svg.style.position = 'absolute';
    div.appendChild(svg);

    // Создаем контейнер для SVG-определения штриховки
    var defs = this.defs_ = document.createElementNS(svgns, 'defs');
    svg.appendChild(defs);

    // Создаем SVG-контур, отвечающий за заливку цветом
    var fillColorPath = this.fillColorPath_ = document.createElementNS(svgns, 'path');
    fillColorPath.setAttribute('stroke', 'none');
    fillColorPath.setAttribute('fill-rule', 'evenodd');
    svg.appendChild(fillColorPath);

    // Создаем SVG-контур, отвечающий за штриховку
    var patternPath = this.patternPath_ = document.createElementNS(svgns, 'path');
    patternPath.setAttribute('stroke', 'none');
    patternPath.setAttribute('fill', 'none');
    patternPath.setAttribute('fill-rule', 'evenodd');
    svg.appendChild(patternPath);

    // Оповещаем объект об изменении свойств
    this.notify('fillColor');
    this.notify('fillOpacity');
    this.notify('pattern');
    this.notify('visible');

    // Добавляем DIV-контейнер на панель overlayLayer карты
    var panes = this.getPanes();
    panes.overlayLayer.appendChild(div);
  };

  /**
   * Вызывается автоматически после <code>onAdd()</code>, смены масштаба, центра или типа карты.
   * При необходимости можно вызвать явно. Изменяет положение и размер DOM-элементов, которые
   * были добавлены в <code>onAdd()</code>.
   *
   * @protected
   */
  FillOverlay.prototype.draw = function() {
    var overlayProjection = this.getProjection();

    if (!overlayProjection) {
      return;
    }

    // Переводим географические координаты углов рамки фона в координаты на экране
    var sw = overlayProjection.fromLatLngToDivPixel(this.bounds_.getSouthWest());
    var ne = overlayProjection.fromLatLngToDivPixel(this.bounds_.getNorthEast());

    // Изменяем положение и размеры DIV-контейнера
    var div = this.div_;
    div.style.left = sw.x + 'px';
    div.style.top = ne.y + 'px';
    div.style.width = (ne.x - sw.x) + 'px';
    div.style.height = (sw.y - ne.y) + 'px';

    // Изменяем координаты SVG-контуров, отвечающих за заливку цветом и штриховку
    var points = '';
    this.getPaths().forEach(function(path) {
      points += ' M';
      path.forEach(function(vertex) {
        var point = overlayProjection.fromLatLngToDivPixel(vertex);
        points += ' ' + (point.x - sw.x) + ',' + (point.y - ne.y);
      });
      points += 'z';
    });
    this.fillColorPath_.setAttribute('d', points);
    this.patternPath_.setAttribute('d', points);
  };

  /**
   * Вызывается автоматически при установке свойства map в значение null.
   * Полностью удаляет фон с карты.
   *
   * @protected
   */
  FillOverlay.prototype.onRemove = function() {
    this.div_.parentNode.removeChild(this.div_);
    this.div_ = null;

    if (this.patternObject_) {
      this.patternObject_.unbindAll();
      this.patternObject_ = null;
    }

    this.svg_ = null;
    this.defs_ = null;
    this.fillColorPath_ = null;
    this.patternPath_ = null;
  };

  /**
   * Возвращает контуры фона.
   *
   * @returns {external:google.maps.MVCArray<external:google.maps.MVCArray<external:google.maps.LatLng>>}
   */
  FillOverlay.prototype.getPaths = function() { return this.get('paths'); };

  /**
   * Устанавливает контуры фона. Первый элемент массива описывает внешний контур, а остальные -
   * внутренние.
   *
   * @param {external:google.maps.MVCArray<external:google.maps.MVCArray<external:google.maps.LatLng>>}
   * paths
   */
  FillOverlay.prototype.setPaths = function(paths) { this.set('paths', paths); };

  /**
   * Вызывается автоматически при установке свойства paths объекта. Перерисовывает фон.
   *
   * @protected
   */
  FillOverlay.prototype.paths_changed = function() {
    this.bounds_ = this.calculateBounds_(this.get('paths'));
    this.draw();
  }

  /**
   * Возвращает, виден ли фон на карте.
   *
   * @returns {boolean}
   */
  FillOverlay.prototype.getVisible = function() { return this.get('visible'); };

  /**
   * Скрывает фон, если установлено в false.
   *
   * @param {boolean}
   */
  FillOverlay.prototype.setVisible = function(visible) { this.set('visible', visible); };

  /**
   * Вызывается автоматически при установке свойства visible объекта. Устанавливает
   * CSS-свойство visibility у DIV-контейнера в соответствии со значением свойства visible.
   *
   * @protected
   */
  FillOverlay.prototype.visible_changed = function() {
    if (this.div_) {
      this.div_.style.visibility = this.get('visible') ? 'visible' : 'hidden';
    }
  }

  /**
   * Возвращает тип штриховки.
   *
   * @returns {string}
   */
  FillOverlay.prototype.getPattern = function() { return this.get('pattern'); };

  /**
   * Устанавливает тип штриховки.
   *
   * @param {string}
   */
  FillOverlay.prototype.setPattern = function(pattern) { this.set('pattern', pattern); };

  /**
   * Вызывается автоматически при установке свойства pattern объекта. Сначала удаляется старая
   * штриховка, а затем создается новая, если свойство pattern содержит не пустое и не ошибочное
   * значение.
   *
   * @protected
   */
  FillOverlay.prototype.pattern_changed = function() {
    if (!this.div_) {
      return;
    }

    var pattern = this.get('pattern');
    var defs = this.defs_;
    var path = this.patternPath_;

    // Удаляем старую штриховку
    if (this.patternObject_) {
      this.patternObject_.unbindAll();
      this.patternObject_ = null;
      path.setAttribute('fill', 'none');
      defs.removeChild(defs.firstChild);
    }

    // Не продолжаем, если свойство pattern пустое
    if (!pattern) {
      return;
    }

    // Разбиваем значение свойства pattern на две части: имя класса-узора и угол поворота,
    // затем проверяем полученные значения на ошибки
    var patternParts = pattern.split('_', 2);

    var patternName = patternParts[0];
    if (!app.pattern[patternName]) {
      throw 'Тип штриховки "' + patternName + '" не поддерживается!';
    }
    if (patternName == 'Base') {
      throw 'Класс app.pattern.Base является абстрактным!';
    }

    if (patternParts[1] != undefined) {
      var patternAngle = patternParts[1];
      if (!/^\d+$/.test(patternAngle)) {
        throw "Угол поворота штриховки должен быть положительным целым числом!";
      }
    } else {
      var patternAngle = 0;
    }

    // Создаем новую штриховку
    var patternId = 'pattern' + this.uniqueId_();
    path.setAttribute('fill', 'url(#' + patternId + ')');

    var patternObject = this.patternObject_ = new app.pattern[patternName]({
      id: patternId,
      container: defs,
      angle: patternAngle
    });
    patternObject.bindTo('color', this, 'patternColor');
    patternObject.bindTo('opacity', this, 'patternOpacity');
  };

  /**
   * Возвращает уникальный в пределах всех экземпляров этого класса числовой идентификатор.
   *
   * @private
   * @function
   * @returns {number}
   */
  FillOverlay.prototype.uniqueId_ = (function() {
    var id = 1;
    return function() {
      return id++;
    }
  })();

  /**
   * Возвращает координаты левого нижнего и правого верхнего углов рамки, в которую целиком
   * помещаются контуры фона.
   *
   * @private
   * @param {external:google.maps.MVCArray<external:google.maps.MVCArray<external:google.maps.LatLng>>}
   * paths
   * @returns {external:google.maps.LatLngBounds}
   */
  FillOverlay.prototype.calculateBounds_ = function(paths) {
    var bounds = new google.maps.LatLngBounds();
    paths.forEach(function(path) {
      path.forEach(function(vertex) {
        bounds.extend(vertex);
      });
    });
    return bounds;
  };

  /**
   * Вызывается автоматически при установке свойства fillColor объекта. Устанавливает
   * атрибут fill у SVG-контура, отвечающего за заливку цветом.
   *
   * @protected
   */
  FillOverlay.prototype.fillColor_changed = function() {
    if (this.fillColorPath_) {
      this.fillColorPath_.setAttribute('fill', this.get('fillColor'));
    }
  };

  /**
   * Вызывается автоматически при установке свойства fillOpacity объекта. Устанавливает
   * атрибут fill-opacity у SVG-контура, отвечающего за заливку цветом.
   *
   * @protected
   */
  FillOverlay.prototype.fillOpacity_changed = function() {
    if (this.fillColorPath_) {
      this.fillColorPath_.setAttribute('fill-opacity', this.get('fillOpacity'));
    }
  };

  window.app.FillOverlay = FillOverlay;

})();
