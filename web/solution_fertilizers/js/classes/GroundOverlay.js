(function() {

  /**
   * @typedef {Object} app.GroundOverlay~GroundOverlayOptions
   * @property {external:google.maps.LatLngBounds} bounds - Границы рамки изображения.
   * Если они не заданы, то рассчитываются автоматически.
   * @property {external:google.maps.Map} map - Карта, на которой показывается изображение.
   * @property {number} opacity=0.5 - Непрозрачность изображения от 0.0 до 1.0.
   * @property {string} url - URL изображения.
   */

  /**
   * Загружает и накладывает на карту изображение, например, оцифрованной бумажной карты.
   * Загруженное изображение можно перетаскивать и менять его размеры. Чтобы рисовать поверх
   * такого изображения, его предварительно нужно закрепить с помощью соответствующей кнопки.
   *
   * @constructor
   * @alias app.GroundOverlay
   * @augments external:google.maps.OverlayView
   * @param {app.GroundOverlay~GroundOverlayOptions} options
   */
  function GroundOverlay(options) {
    options = options || {};

    this.url_ = options.url;
    this.loaded_ = false;
    this.fixed_ = options.bounds ? true : false;

    this.imageDiv_ = null;
    this.img_ = null;

    this.controlDiv_ = null;
    this.removeBtn_ = null;
    this.pinBtn_ = null;

    this.frame_ = null;

    this.set('bounds', options.bounds);
    this.set('opacity', options.opacity == undefined ? 0.5 : options.opacity);
    this.set('map', options.map);
  }

  GroundOverlay.prototype = new google.maps.OverlayView();

  /**
   * Этот метод вызывается один раз после вызова <code>setMap()</code> с правильной картой.
   * К этому моменту панели и проекция карты уже инициализированы. Создает необходимые
   * DOM-элементы и добавляет их к нужной панели карты.
   *
   * @protected
   */
  GroundOverlay.prototype.onAdd = function() {
    var self = this;
    var map = this.getMap();

    // Создаем контейнер для изображения
    var imageDiv = this.imageDiv_ = document.createElement('div');
    imageDiv.style.position = 'absolute';
    imageDiv.style.overflow = 'hidden';
    imageDiv.className = 'ground-overlay-image loading';

    // Создаем скрытое изображение и добавляем его в контейнер.
    // Изображение покажется только после его полной загрузки.
    var img = this.img_ = document.createElement('img');
    img.onload = function() {
      // Вызов setTimeout гарантирует, что onLoadImage_ будет вызван не раньше, чем onAdd
      // закончит работу
      setTimeout(function() {
        self.onLoadImage_();
      }, 0);
    };
    img.src = this.url_;
    img.style.position = 'absolute';
    img.style.visibility = 'hidden';
    img.style.opacity = this.get('opacity');
    imageDiv.appendChild(img);

    // Создаем скрытый контейнер для элементов управления.
    // Элементы управления также покажутся только после полной загрузки изображения.
    var controlDiv = this.controlDiv_ = document.createElement('div');
    controlDiv.style.position = 'absolute';
    controlDiv.style.overflow = 'hidden';
    controlDiv.style.left = '0px';
    controlDiv.style.top = '0px';
    controlDiv.style.whiteSpace = 'nowrap';
    controlDiv.style.display = 'none';
    controlDiv.className = 'ground-overlay-control-panel';

    // Создаем кнопку 'удалить'
    var removeBtn = this.removeBtn_ = document.createElement('button');
    removeBtn.className = 'ground-overlay-remove-button';
    removeBtn.innerHTML = removeBtn.title = 'удалить';
    removeBtn.onclick = function() {
      self.setMap(null);
    };
    controlDiv.appendChild(removeBtn);

    // Создаем кнопку 'прикрепить/открепить'
    var pinBtn = this.pinBtn_ = document.createElement('button');
    pinBtn.className = 'ground-overlay-pin-button';
    pinBtn.innerHTML = pinBtn.title = (this.fixed_) ? 'открепить' : 'прикрепить';
    if (this.fixed_) {
      pinBtn.classList.add('pressed');
    }
    pinBtn.onclick = function() {
      if (self.fixed_) {
        self.unpin();
      } else {
        self.pin();
      }
    };
    controlDiv.appendChild(pinBtn);

    // Если границы рамки не заданы явно, то рассчитываем их
    if (!this.get('bounds')) {
      this.set('bounds', this.initialFrameBounds_());
    }

    // Создаем рамку для управления положением и размером изображения
    var frame = this.frame_ = new google.maps.Rectangle({
      editable: false,
      draggable: !this.fixed_,
      fillOpacity: 0,
      strokeColor: 'black',
      strokeOpacity: 1,
      strokeWeight: 1,
      strokePosition: google.maps.StrokePosition.OUTSIDE,
      visible: !this.fixed_
    });

    frame.bindTo('bounds', this);
    frame.bindTo('map', this);

    // Прячем рамку при перетаскивании
    frame.addListener('dragstart', function() {
      if (self.loaded_) {
        self.frame_.setVisible(false);
      }
    });
    frame.addListener('dragend', function() {
      self.frame_.setVisible(true);
    });

    // Добавляем созданные элементы на панели карты
    var panes = this.getPanes();
    panes.overlayLayer.appendChild(imageDiv);
    panes.overlayMouseTarget.appendChild(controlDiv);
  };

  /**
   * Вызывается после полной загрузки изображения. Убирает текст 'Загрузка...' и показывает
   * загруженное изображение, а также элементы управления им.
   *
   * @private
   */
  GroundOverlay.prototype.onLoadImage_ = function() {
    var img = this.img_;
    var imgSize = new google.maps.Size(img.width, img.height);

    img.style.width = '100%';
    img.style.height = '100%';
    img.style.visibility = 'visible';

    this.imageDiv_.classList.remove('loading');
    this.controlDiv_.style.display = 'inline-block';
    if (!this.fixed_) {
      var newBounds = this.calculateFrameBounds_(this.get('bounds').getCenter(), imgSize);
      this.set('bounds', newBounds);
      this.frame_.setEditable(true);
    }
    this.loaded_ = true;
  };

  /**
   * Рассчитывает границы рамки с учетом размеров загруженного изображения. Если размеры
   * изображения превышают размеры видимой области карты, то изображение автоматически
   * уменьшается с сохранением пропорций.
   *
   * @private
   * @param {external:google.maps.LatLng} center - Центр рамки.
   * @param {external:google.maps.Size} size - Реальные размеры изображения.
   * @returns {external:google.maps.LatLngBounds}
   */
  GroundOverlay.prototype.calculateFrameBounds_ = function(center, size) {
    var map = this.getMap();
    var overlayProjection = this.getProjection();
    var mapBounds = map.getBounds();
    var mapSWPoint = overlayProjection.fromLatLngToDivPixel(mapBounds.getSouthWest());
    var mapNEPoint = overlayProjection.fromLatLngToDivPixel(mapBounds.getNorthEast());
    var maxWidth = mapNEPoint.x - mapSWPoint.x - 20;
    var maxHeight = mapSWPoint.y - mapNEPoint.y - 20;
    var width = size.width;
    var height = size.height;
    var ratio = width / height;
    if (width > maxWidth) {
      width = maxWidth;
      height = width / ratio;
    }
    if (height > maxHeight) {
      height = maxHeight;
      width = height * ratio;
    }
    var centerPoint = overlayProjection.fromLatLngToDivPixel(center);
    var frameSWPoint = new google.maps.Point(centerPoint.x - width / 2, centerPoint.y + height / 2);
    var frameNEPoint = new google.maps.Point(frameSWPoint.x + width, frameSWPoint.y - height);
    return new google.maps.LatLngBounds(
      overlayProjection.fromDivPixelToLatLng(frameSWPoint),
      overlayProjection.fromDivPixelToLatLng(frameNEPoint));
  };

  /**
   * Рассчитывает начальные границы рамки, когда они не заданы явно при инициализации,
   * а размеры изображения еще не известны.
   *
   * @private
   * @returns {external:google.maps.LatLngBounds}
   */
  GroundOverlay.prototype.initialFrameBounds_ = function() {
    var map = this.getMap();
    var overlayProjection = this.getProjection();
    var bounds = map.getBounds();
    var swPoint = overlayProjection.fromLatLngToDivPixel(bounds.getSouthWest());
    var nePoint = overlayProjection.fromLatLngToDivPixel(bounds.getNorthEast());
    var centerPoint = overlayProjection.fromLatLngToDivPixel(map.getCenter());
    var halfSize = (swPoint.y - nePoint.y) / 6;
    swPoint.x = centerPoint.x - halfSize;
    swPoint.y = centerPoint.y + halfSize;
    nePoint.x = centerPoint.x + halfSize;
    nePoint.y = centerPoint.y - halfSize;
    return new google.maps.LatLngBounds(
      overlayProjection.fromDivPixelToLatLng(swPoint),
      overlayProjection.fromDivPixelToLatLng(nePoint));
  };

  /**
   * Вызывается автоматически после <code>onAdd()</code>, смены масштаба, центра или типа карты.
   * При необходимости можно вызвать явно. Изменяет положение и размер DOM-элементов, которые
   * были добавлены в <code>onAdd()</code>.
   *
   * @protected
   */
  GroundOverlay.prototype.draw = function() {
    var bounds = this.get('bounds');
    var overlayProjection = this.getProjection();

    if (!bounds || !overlayProjection) {
      return;
    }

    var sw = overlayProjection.fromLatLngToDivPixel(bounds.getSouthWest());
    var ne = overlayProjection.fromLatLngToDivPixel(bounds.getNorthEast());

    var imageDiv = this.imageDiv_;
    imageDiv.style.left = sw.x + 'px';
    imageDiv.style.top = ne.y + 'px';
    imageDiv.style.width = (ne.x - sw.x) + 'px';
    imageDiv.style.height = (sw.y - ne.y) + 'px';

    var controlDiv = this.controlDiv_;
    controlDiv.style.left = sw.x + 'px';
    controlDiv.style.top = ne.y + 'px';
    controlDiv.style.maxWidth = imageDiv.style.width;
    controlDiv.style.maxHeight = imageDiv.style.height;
  };

  /**
   * Вызывается автоматически при установке свойства map в значение null.
   * Удаляет загруженное изображение и элементы управления им с карты.
   *
   * @protected
   */
  GroundOverlay.prototype.onRemove = function() {
    this.imageDiv_.parentNode.removeChild(this.imageDiv_);
    this.imageDiv_ = null;
    this.img_ = null;

    this.controlDiv_.parentNode.removeChild(this.controlDiv_);
    this.controlDiv_ = null;
    this.removeBtn_ = null;
    this.pinBtn_ = null;

    this.frame_ = null;
  };

  /**
   * Возвращает, прикреплено ли изображение к карте.
   * @returns {boolean}
   */
  GroundOverlay.prototype.isFixed = function() {
    return this.fixed_;
  }

  /**
   * Прикрепляет загруженное изображение к карте.
   */
  GroundOverlay.prototype.pin = function() {
    if (!this.fixed_) {
      this.fixed_ = true;
      this.frame_.setOptions({
        editable: false,
        draggable: false,
        visible: false
      });
      this.pinBtn_.innerHTML = this.pinBtn_.title = 'открепить';
      this.pinBtn_.classList.add('pressed');
    }
  };

  /**
   * Открепляет загруженное изображение от карты.
   */
  GroundOverlay.prototype.unpin = function() {
    if (this.fixed_) {
      this.fixed_ = false;
      this.frame_.setOptions({
        editable: true,
        draggable: true,
        visible: true
      });
      this.pinBtn_.innerHTML = this.pinBtn_.title = 'прикрепить';
      this.pinBtn_.classList.remove('pressed');
    }
  };

  /**
   * Возвращает уровень непрозрачности загруженного изображения.
   * @returns {number}
   */
  GroundOverlay.prototype.getOpacity = function() {
    return this.get('opacity');
  };

  /**
   * Устанавливает уровень непрозрачности загруженного изображения.
   * @param {number} opacity
   */
  GroundOverlay.prototype.setOpacity = function(opacity) {
    this.set('opacity', opacity);
  };

  /**
   * Вызывается автоматически при установке свойства opacity объекта. Изменяет CSS-свойство
   * opacity у загруженного изображения.
   *
   * @protected
   */
  GroundOverlay.prototype.opacity_changed = function() {
    if (this.img_) {
      this.img_.style.opacity = this.get('opacity');
    }
  };

  /**
   * Возвращает текущие границы рамки.
   * @returns {external:google.maps.LatLngBounds}
   */
  GroundOverlay.prototype.getBounds = function() {
    return this.get('bounds');
  };

  /**
   * Устанавливает текущие границы рамки.
   * @param {external:google.maps.LatLngBounds} bounds
   */
  GroundOverlay.prototype.setBounds = function(bounds) {
    this.set('bounds', bounds);
  };

  /**
   * Вызывается автоматически при установке свойства bounds объекта. В свою очередь вызывает
   * метод <code>draw()</code>.
   *
   * @protected
   */
  GroundOverlay.prototype.bounds_changed = function() {
    this.draw();
  };

  window.app.GroundOverlay = GroundOverlay;

})();
