var cont;
var fields;
$(window).load(function() {
    // Инициализация контейнера карты
    cont = getDrawMap();
    fields = cont.getFields();

    // Загрузка данных земельного хозяйства
    $.ajax({
        url: '/drawFields.do',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            // Устанавливаем центр и масштаб карты для просмотра тестового хозяйства
            var map = cont.getMap();
            var lat = data.agriculture_latitude, long = data.agriculture_longitude; //координаты хозяйства
            console.log("lat = " + lat, " long = " + long);
            map.setCenter({lat: lat, lng: long});
            map.setZoom(12);

            // Добавляем поля на карту
            var dataFields = data.fields;
            console.log("fields.size = " + dataFields.length);
            dataFields.sort(function(a, b) {
                if(a.fieldNumber > b.fieldNumber)
                    return 1;
                return -1;
            });
            for (var i = 0; i < dataFields.length; i++) {
                var fieldData = dataFields[i], fieldPath = [];
                if (fieldData.poligon.length == 0) {
                    continue;
                }
                var polygon = fieldData.poligon;
                for (var j = 0; j < polygon.length; j++) {
                    polygon.sort(function(a, b) {
                        if(a.number > b.number)
                            return 1;
                        return -1;
                    });
                    fieldPath.push({
                        lat: polygon[j].latitude,
                        lng: polygon[j].longitude
                    });
                }
                var field = new app.FieldPolygon({
                    id: fieldData.agricultureFieldId,
                    number: fieldData.fieldNumber,
                    path: fieldPath,
                    color: fieldData.color,
                    sArea: fieldData.area
                });
                if (fieldData.typeOfSoil) {
                    field.setGround(fieldData.typeOfSoil.typeOfSoilId);
                }
                var ground = refs.GROUND_TYPES.lookupId(field.getGround());
                field.setColor(ground ? ground.color : field.getColor());
                fields.push(field);
            }
            //cont.delListClick();
            //cont.delListRightClick();
            //cont.addListRightClick();
            cont.addClickNotActive();
        },
        error: function() {
            alert('При загрузке данных произошла ошибка');
        }
    });
    addListeners();
});

function default_layer_settings() {
    $("#radio_empty_1_color").prop('disabled', false);
    $("#radio_soil_1_color").prop('disabled', false);
    $("#radio_pred_1_color").prop('disabled', true);
    $("#radio_cult_1_color").prop('disabled', true);
    $("#radio_empty_1_color").prop('checked', false);
    $("#radio_soil_1_color").prop('checked', true);
    $("#radio_pred_1_color").prop('checked', false);
    $("#radio_cult_1_color").prop('checked', false);

    $("#radio_empty_1_pattern").prop('disabled', false);
    $("#radio_pred_1_pattern").prop('disabled', true);
    $("#radio_cult_1_pattern").prop('disabled', true);
    $("#radio_empty_1_pattern").prop('checked', false);
    $("#radio_pred_1_pattern").prop('checked', false);
    $("#radio_cult_1_pattern").prop('checked', true);

    $("#radio_empty_2_color").prop('disabled', false);
    $("#radio_soil_2_color").prop('disabled', false);
    $("#radio_pred_2_color").prop('disabled', true);
    $("#radio_cult_2_color").prop('disabled', true);
    $("#radio_empty_2_color").prop('checked', false);
    $("#radio_soil_2_color").prop('checked', false);
    $("#radio_pred_2_color").prop('checked', true);
    $("#radio_cult_2_color").prop('checked', false);

    $("#radio_empty_2_pattern").prop('disabled', false);
    $("#radio_pred_2_pattern").prop('disabled', true);
    $("#radio_cult_2_pattern").prop('disabled', true);
    $("#radio_empty_2_pattern").prop('checked', true);
    $("#radio_pred_2_pattern").prop('checked', false);
    $("#radio_cult_2_pattern").prop('checked', false);
}