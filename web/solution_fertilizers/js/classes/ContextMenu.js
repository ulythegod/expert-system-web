(function() {

  /**
   * @typedef {Object} app.ContextMenu~ContextMenuItem
   * @property {string} id - Идентификатор пункта меню.
   * @property {string} label - Текст пункта меню.
   * @property {string|Array<string>} context - Один или несколько контекстов, в которых
   * показывается данный пункт меню. Если передано пустое значение, то пункт меню будет
   * показываться всегда.
   * @property {boolean} separator - Если установлено в true, то пункт меню является разделителем.
   */

  /**
   * Создает контекстное меню из заданного набора пунктов. Для каждого пункта указывается один
   * или несколько контекстов, в которых он показывается. Контекст задается при открытии меню.
   * При клике по пункту меню генерируется событие, имя которого составляется из идентификатора
   * этого пункта в нижнем регистре и слова "click".
   *
   * @constructor
   * @alias app.ContextMenu
   * @augments external:google.maps.MVCObject
   * @param {Array<app.ContextMenu~ContextMenuItem>} items
   *
   * @example
   * // Создаем контекстное меню из 2-х пунктов
   * var contextMenu = new app.ContextMenu([
   *   {id: 'fieldInfo', label: 'Номер поля', context: 'field'},
   *   {id: 'parcelInfo', label: 'Номер участка', context: 'parcel'}
   * ]);
   * // Добавляем обработчик клика по пункту меню 'Номер поля'
   * contextMenu.addListener('fieldinfoclick', function(context, params) {
   *   alert('Поле №' + params.field.getNumber());
   * });
   * // Добавляем обработчик клика по пункту меню 'Номер участка'
   * contextMenu.addListener('parcelinfoclick', function(context, params) {
   *   alert('Участок №' + params.parcel.getNumber());
   * });
   * // Создаем поле и добавляем обработчик правого клика по нему, который
   * // открывает меню в контексте 'field'
   * var field = new app.FieldPolygon({...});
   * field.addListener('rightclick', function(e) {
   *   contextMenu.open(map, 'field', {field: this, latLng: e.latLng});
   * });
   * // Создаем участок и добавляем обработчик правого клика по нему, который
   * // открывает меню в контексте 'parcel'
   * var parcel = new app.ParcelPolygon({...});
   * parcel.addListener('rightclick', function(e) {
   *   contextMenu.open(map, 'parcel', {parcel: this, latLng: e.latLng});
   * });
   * // Добавляем участок на поле
   * field.getParcels().push(parcel);
   *
   */
  function ContextMenu(items) {
    // Создаем DIV-контейнер
    var div = this.div_ = document.createElement('div');
    div.className = 'context-menu';
    div.style.position = 'absolute';
    div.style.background = '#fff';
    div.style.color = '#666';
    div.style.border = '1px solid #999';
    div.style.fontSize = '12px';

    this.items_ = items;
    this.context_ = null;
    this.contextParams_ = null;

    var menu = this;
    // Добавляем обработчик клика по DIV-контейнеру, который определяет пункт меню,
    // для которого нужно сгенерировать click-событие и генерирует его.
    google.maps.event.addDomListener(div, 'click', function(e) {
      var itemDiv = e.target;
      while (!itemDiv.hasAttribute('data-item-id') && itemDiv != div) {
        itemDiv = itemDiv.parentNode;
      }

      if (itemDiv != div) {
        var itemId = itemDiv.getAttribute('data-item-id');
        var eventName = itemId.toLowerCase() + 'click';
        google.maps.event.trigger(menu, eventName, menu.context_, menu.contextParams_);
        menu.close();
      }

      e.cancelBubble = true;
      if (e.stopPropagation) {
        e.stopPropagation();
      }
    });
  }

  ContextMenu.prototype = new google.maps.OverlayView();

  /**
   * Этот метод вызывается один раз после вызова <code>setMap()</code> с правильной картой.
   * К этому моменту панели и проекция карты уже инициализированы. Создает необходимые
   * DOM-элементы и добавляет их к нужной панели карты.
   *
   * @protected
   */
  ContextMenu.prototype.onAdd = function() {
    var menu = this;
    var map = this.getMap();
    var div = this.div_;
    var items = this.items_;
    var context = this.context_;

    // Создаем те пункты меню, которые подходят по контексту и добавляем их в DIV-контейнер
    for (var i = 0; i < items.length; i++) {
      var item = items[i];

      if (item.context &&
          (item.context.constructor == Array && item.context.indexOf(context) == -1 ||
            item.context.constructor == String && item.context != context)) {
        continue;
      }

      var itemDiv = document.createElement('div');
      if (item.separator) {
        itemDiv.className = 'context-menu-separator';
        itemDiv.style.borderTop = '1px solid #999';
      } else {
        itemDiv.className = 'context-menu-item';
        itemDiv.setAttribute('data-item-id', item.id);
        itemDiv.appendChild(document.createTextNode(item.label));
      }
      div.appendChild(itemDiv);
    }

    // Добавляем DIV-контейнер меню на панель floatPane карты
    this.getPanes().floatPane.appendChild(div);

    // Добавляем обработчик события mousedown на DIV-контейнере карты. Если это событие
    // возникнет где угодно, кроме DIV-контейнера меню, то меню автоматически закроется.
    this.divListener_ = google.maps.event.addDomListener(map.getDiv(), 'mousedown', function(e) {
      var target = e.target;
      while (target != menu.div_ && target != this) {
        target = target.parentNode;
      }
      if (target == this) {
        menu.close();
      }
    }, true);
  };

  /**
   * Вызывается автоматически при установке свойства map в значение null.
   * Удаляет меню с карты.
   *
   * @protected
   */
  ContextMenu.prototype.onRemove = function() {
    this.divListener_.remove();

    while (this.div_.firstChild) {
      this.div_.removeChild(this.div_.firstChild);
    }
    this.div_.parentNode.removeChild(this.div_);

    this.set('position');
    this.context_ = null;
    this.contextParams_ = null;
  };

  /**
   * Закрывает меню.
   */
  ContextMenu.prototype.close = function() {
    this.setMap(null);
  };

  /**
   * Вызывается автоматически после <code>onAdd()</code>, смены масштаба, центра или типа карты.
   * При необходимости можно вызвать явно. Изменяет положение и размер DOM-элементов, которые
   * были добавлены в <code>onAdd()</code>.
   *
   * @protected
   */
  ContextMenu.prototype.draw = function() {
    var position = this.get('position');
    var projection = this.getProjection();

    if (!position || !projection) {
      return;
    }

    var point = projection.fromLatLngToDivPixel(position);
    this.div_.style.top = point.y + 'px';
    this.div_.style.left = point.x + 'px';
  };

  /**
   * Возвращает позицию меню.
   *
   * @returns {external:google.maps.LatLng}
   */
  ContextMenu.prototype.getPosition = function() {
    return this.get('position');
  };

  /**
   * Устанавливает позицию меню.
   *
   * @param {external:google.maps.LatLng} position
   */
  ContextMenu.prototype.setPosition = function(position) {
    this.set('position', position);
  };

  /**
   * Открывает меню  в определенном контексте на указанной карте. Всю дополнительную информацию,
   * которая может потребоваться в обработчиках событий нужно передавать в объекте contextParams.
   * Меню открывается в позиции, заданной методом <code>setPosition()</code>. Если позиция меню
   * не задана, но в объекте contextParams установлено свойство latLng, то будет использовано оно.
   *
   * @param {external:google.maps.Map} map
   * @param {string} context
   * @param {Object} contextParams
   */
  ContextMenu.prototype.open = function(map, context, contextParams) {
    if (this.getMap()) {
      return;
    }

    if (!this.get('position') && contextParams && contextParams.latLng) {
      this.set('position', contextParams.latLng);
    }

    this.context_ = context;
    this.contextParams_ = contextParams;

    this.setMap(map);
  };

  window.app.ContextMenu = ContextMenu;

})();
