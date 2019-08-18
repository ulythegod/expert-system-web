// Отключение кеширования для всех Ajax-запросов
$.ajaxSetup({cache: false});

// Глобальные обработчики Ajax-событий
$(document).ajaxSuccess(function (event, jqxhr, settings, data) {
    if (data.notAuth) {
        location = '/login.do';
    }
});

$(document).ajaxError(function (event, jqxhr, settings, thrownError) {
    if (jqxhr.status == 401) {        // Неавторизован
        location = '/login.do';
    }
});

$(document).ajaxSend(function (event, jqxhr, settings) {
    if (settings.statusBarText) {
        var mapStatusBar = $('#mapStatusBar');
        settings.statusBarChild = $('<span/>').text(settings.statusBarText);
        mapStatusBar.children().hide();
        mapStatusBar.finish().prepend(settings.statusBarChild).show();
    }
});

$(document).ajaxComplete(function (event, jqxhr, settings) {
    if (settings.statusBarChild) {
        var mapStatusBar = $('#mapStatusBar');
        if (mapStatusBar.children().length > 1) {
            settings.statusBarChild.remove();
            mapStatusBar.children().first().show();
        } else {
            mapStatusBar.fadeOut(200, function () {
                $(this).empty();
            });
        }
    }
});

$(document).ready(function () {
    // Контейнер для карты
    var map = $('#map');
    if (map.length) {
        // Автоматическая адаптация размеров карты
        var mapCanvas = $('<div/>', {id: 'mapCanvas'}).insertBefore(map);
        setInterval(function () {
                google.maps.event.trigger(map.get(0), 'resize');
        }, 100);
        // Строка состояния в углу карты
        $('<div/>', {id: 'mapStatusBar'}).appendTo(mapCanvas);
    }
});
