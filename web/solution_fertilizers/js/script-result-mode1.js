$(document).ready(function () {
    $("div .tab-content").hide();
});

function clickNameResult(idResult, number) {
    if(!legend){
        getLegendData();
    }
    console.log("clickNameSolution: " + idResult);
    console.log("clickNameField: " + number);
    $("#ul_legend").removeClass("div-disabled");
    $("#print_ui").removeClass("div-disabled");
    $("#print").removeClass("div-disabled-color");
    $("#a0").removeClass("div-disabled-color");
    $("#a1").removeClass("div-disabled-color");
    $("#a2").removeClass("div-disabled-color");
    $("#radio_empty_1_color").prop('disabled', false);
    $("#radio_soil_1_color").prop('disabled', false);
    $("#radio_pred_1_color").prop('disabled', false);
    $("#radio_cult_1_color").prop('disabled', false);
    $("#radio_empty_1_pattern").prop('disabled', false);
    $("#radio_pred_1_pattern").prop('disabled', false);
    $("#radio_cult_1_pattern").prop('disabled', false);
    $("#radio_empty_2_color").prop('disabled', false);
    $("#radio_soil_2_color").prop('disabled', false);
    $("#radio_pred_2_color").prop('disabled', false);
    $("#radio_cult_2_color").prop('disabled', false);
    $("#radio_empty_2_pattern").prop('disabled', false);
    $("#radio_pred_2_pattern").prop('disabled', false);
    $("#radio_cult_2_pattern").prop('disabled', false);

    $("#radio_empty_1_color").prop('checked', false);
    $("#radio_soil_1_color").prop('checked', true);
    $("#radio_pred_1_color").prop('checked', false);
    $("#radio_cult_1_color").prop('checked', false);

    $("#radio_empty_1_pattern").prop('checked', false);
    $("#radio_pred_1_pattern").prop('checked', false);
    $("#radio_cult_1_pattern").prop('checked', true);

    $("#radio_empty_2_color").prop('checked', false);
    $("#radio_soil_2_color").prop('checked', false);
    $("#radio_pred_2_color").prop('checked', true);
    $("#radio_cult_2_color").prop('checked', false);

    $("#radio_empty_2_pattern").prop('checked', true);
    $("#radio_pred_2_pattern").prop('checked', false);
    $("#radio_cult_2_pattern").prop('checked', false);

    var flag = true;
    fields.forEach(function(field) {
        cont.taskColor(field);
        field.set('pattern', null);
        if(field.id == number){
            flag = false;
        }
    });
    if(flag) {
        cont.clearTriangleCulture();
        fields.forEach(function(field) {
            if (field.getActive()) {
                field.set('pattern', null);
                field.setActive(false);
            }
        });
        console.log("Поля нет на карте");
        return;
    }else{
        console.log("Поле есть на карте");
    }

    var params = {
        idResult: idResult
    };
    $.get("/res", $.param(params),function(response) {
        $("div .tab-content").show();
        $("#table-result-1").html($(response).find("data").html());
        document.location.href += "tab-results";

        clear_fields(number);
        $.get("/res", {result_info : idResult},function(response) {
            console.log(response);
            prev_color = response[0];
            prev_pattern = response[1];
            color_crops = response[2];
            pattern_crops = response[3];
            if($("#radio_pred_1_color").prop("checked")) {
                field_id.setColor(prev_color);
            }else if($("#radio_pred_1_pattern").prop("checked")){
                field_id.set('pattern', prev_pattern);
            }
            if($("#radio_pred_2_color").prop("checked")){
                cont.setColorTriangle(prev_color);
            }else if($("#radio_pred_2_pattern").prop("checked")){
                cont.setPatternTriangle(prev_pattern);
            }
            if($("#radio_empty_2_color").prop("checked")){
                cont.setColorTriangle("transparent");
            }

            if($("#radio_cult_1_color").prop("checked")) {
                field_id.setColor(color_crops);
            }else if($("#radio_cult_1_pattern").prop("checked")){
                field_id.set('pattern', pattern_crops);
            }
            if($("#radio_cult_2_color").prop("checked")){
                cont.setColorTriangle(color_crops);
            }else if($("#radio_cult_2_pattern").prop("checked")){
                cont.setPatternTriangle(pattern_crops);
            }
        });
    });
    //res2?solutionId=${s.solutionMode2Id}#tab-results
}

function clear_fields(number) {
    cont.setSelectionFieldFlag(true);
    cont.clearTriangleCulture();
    fields.forEach(function(field) {
        if(field.getActive()){
            field.set('pattern', null);
            field.setActive(false);
        }
        if (field.id == number) {
            field_id = field;
            cont.inputTriangle(field);
            if($("#radio_empty_1_color").prop("checked")){
                field_id.setColor("#999999");
            }else if($("#radio_soil_1_color").prop("checked")) {
                cont.taskColor(field);
                //color_field = field.getColor();
            }
            if($("#radio_soil_2_color").prop("checked")) {
                cont.setColorTriangle(cont.getFielldColor(field_id));
                //color_field = field.getColor();
            }
            field.setActive(true);
            field.set('pattern', null);
        }
    });
}





