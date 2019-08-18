// Отключение кеширования для всех Ajax-запросов
$.ajaxSetup({cache: false});

// Настройка плагина AjaxFileUploader
$.fn.ajaxFileUploader.defaults.browseButtonAttrs['class'] = 'btn btn-default btn-panel highlight';

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

$(window).load(function() {

    // Инициализация формы для загрузки на сервер данных химического обследования
    $('#uploadChemicalDataForm').ajaxFileUploader({
        onUpload: function(data) {
            if (data["error"]!=null){
                alert('Ошибка: ' + data["error"]);
                return;
            }
            if (data["error-save"]!=null)
                console.log(data["error-save"]);
            setParametersForChemicalSurvey(data);
            /*else {
                var params = {
                    nameFileData: data["data"],
                    nameFileMethods: data["methods"],
                    numberField: $("#parameters1 select[name=field1] :selected").attr("value")
                };
                $.get("/chemical.do?action=getAverageDataFromFiles", $.param(params), function(responseJson) {
                    if (responseJson["error"]!=null)
                        alert('Ошибка: ' + responseJson["error"]);
                    else setParametersForChemicalSurvey(responseJson);
                });
            }*/
        }
    });
});

