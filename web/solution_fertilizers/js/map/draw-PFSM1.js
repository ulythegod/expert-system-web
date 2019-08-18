var text_prev;
var text_cult;
var text_fert;
var text_depth;
var text_bulk;
var text_planningYield;
var text_predDoze;
var text_predYield;
var text_humus;
var text_phosphorus;
var text_potassium;
var text_nitrogen1;
var text_phosphorus1;
var text_potassium1;
var text_yearOfUsing;
var text_nitrogen2;
var text_phosphorus2;
var text_potassium2;
var text_nitrogen3;
var text_phosphorus3;
var text_potassium3;
var color_field = null;
var field_id = {
    id: undefined
};
var color_previous;

function getDrawMap() {
    return new app.MapMode1( $('#map').get(0), {
        selectableElements: [app.FieldPolygon]
    });
}

function addListeners() {
    //Слушатель на изменение select'a выбора поля
    $("#parameters1 select[name=field1]").on("change", function () {
        default_layer_settings();
        var number = this.value;

        text_fert = null;
        text_depth = null;
        text_bulk = null;
        text_planningYield = null;
        text_predDoze = null;
        text_predYield = null;
        text_humus=null;
        text_phosphorus=null;
        text_potassium=null;
        text_nitrogen1=null;
        text_phosphorus1=null;
        text_potassium1=null;
        text_yearOfUsing=null;
        text_nitrogen2=null;
        text_phosphorus2=null;
        text_potassium2=null;
        text_nitrogen3=null;
        text_phosphorus3=null;
        text_potassium3=null;

        cont.clearTriangleCulture();
        fields.forEach(function(field) {
            cont.taskColor(field);

            /*насколько корректно это здесь? очистка культуры и предшественника для всех полей*/
            field.setPrevCulture(undefined);
            field.setCulture(undefined);

            if(field.getActive()){
                field.setColor(color_previous);
                field.setActive(false);
                field.set('pattern', null);
            }
            if (field.id == number) {
                field_id = field;
                color_previous = field.getColor();
                cont.inputTriangle(field);
                /*if($("#radio_empty_1_color").prop("checked")){
                    field_id.setColor("#999999");
                }else if($("#radio_soil_1_color").prop("checked")) {
                    cont.taskColor(field);
                    color_field = field.getColor();
                }
                if($("#radio_soil_2_color").prop("checked")) {
                    cont.setColorTriangle(cont.getFielldColor(field_id));
                    color_field = field.getColor();
                }*/
                field.setActive(true);
                field.set('pattern', null);
            }
        });
    });

    $("select[name=predSelect]").on("change", function () {
        text_prev = $("option:selected", this).text();
        field_id.setPrevCulture(text_prev);
        /*
        var color_culture = prev_color[text_prev.match(/\d+(\.\d+)/gim)];
        var pattern_culture = prev_pattern[text_prev.match(/\d+(\.\d+)/gim)];
        field_id.setPrevCulture(text_prev.match(/\d+(\.\d+)/gim));
        if(color_culture !== undefined && pattern_culture !== undefined){
            if($("#radio_pred_1_color").prop("checked")) {
                field_id.setColor(color_culture);
            }else if($("#radio_pred_1_pattern").prop("checked")){
                field_id.set('pattern', pattern_culture);
            }
            if($("#radio_pred_2_color").prop("checked")){
                cont.setColorTriangle(color_culture);
            }else if($("#radio_pred_2_pattern").prop("checked")){
                cont.setPatternTriangle(pattern_culture);
            }
            if($("#radio_empty_2_color").prop("checked")){
                cont.setColorTriangle("transparent");
            }
        }*/
    });

    $("select[name=corpSelect]").on("change", function () {
        text_cult = $("option:selected", this).text();
        field_id.setCulture(text_cult);
        /*var color_culture = color_crops[text_cult.match(/\d+(\.\d+)/gim)];
        var pattern_culture = pattern_crops[text_cult.match(/\d+(\.\d+)/gim)];
        field_id.setCulture(text_cult.match(/\d+(\.\d+)/gim));

        if(color_culture !== undefined && pattern_culture !== undefined){
            if($("#radio_cult_1_color").prop("checked")) {
                field_id.setColor(color_culture);
            }else if($("#radio_cult_1_pattern").prop("checked")){
                field_id.set('pattern', pattern_culture);
            }
            if($("#radio_cult_2_color").prop("checked")){
                cont.setColorTriangle(color_culture);
            }else if($("#radio_cult_2_pattern").prop("checked")){
                cont.setPatternTriangle(pattern_culture);
            }
        }*/
    });


    $("select[name=systFert]").on("change", function () {
        text_fert = $("option:selected", this).text();
    });

    $("input[name=depth]").on("input", function () {
        text_depth = this.value;
    });

    $("input[name=bulk]").on("input", function () {
        text_bulk = this.value;
    });

    $("input[name=planningYield]").on("input", function () {
        text_planningYield = this.value;
    });

    $("input[name=predDoze]").on("input", function () {
        text_predDoze = this.value;
    });

    $("input[name=predYield]").on("input", function () {
        text_predYield = this.value;
    });

    $("input[name=humus]").on("input", function () {
        text_humus = this.value;
    });
    $("input[name=phosphorus]").on("input", function () {
        text_phosphorus = this.value;
    });
    $("input[name=potassium]").on("input", function () {
        text_potassium = this.value;
    });
    $("input[name=nitrogen1]").on("input", function () {
        text_nitrogen1 = this.value;
    });
    $("input[name=phosphorus1]").on("input", function () {
        text_phosphorus1 = this.value;
    });
    $("input[name=potassium1]").on("input", function () {
        text_potassium1 = this.value;
    });
    $("select[name=yearOfUsing]").on("change", function () {
        text_yearOfUsing = $("option:selected", this).text();
    });
    $("input[name=nitrogen2]").on("input", function () {
        text_nitrogen2 = this.value;
    });
    $("input[name=phosphorus2]").on("input", function () {
        text_phosphorus2 = this.value;
    });
    $("input[name=potassium2]").on("input", function () {
        text_potassium2 = this.value;
    });
    $("input[name=nitrogen3]").on("input", function () {
        text_nitrogen3 = this.value;
    });
    $("input[name=phosphorus3]").on("input", function () {
        text_phosphorus3 = this.value;
    });
    $("input[name=potassium3]").on("input", function () {
        text_potassium3 = this.value;
    });


    //Слушатель на нажатие кнопки Создать
    $("#parameters1 input[name=createNewNameSolutionButton]").on("click", function () {
        cont.setClickFieldFlag(true);
        default_layer_settings();
        if(cont.notActive){
            cont.delClickNotActive();
            cont.addListRightClick();
            cont.setNotActive(false);
        }
        cont.clearSelectionAndColor();
        cont.setSelectionFieldFlag(true);
        cont.closeInfoWindow();
        /*$("#ul_legend").removeClass("div-disabled");
        $("#a0").removeClass("div-disabled-color");
        $("#a1").removeClass("div-disabled-color");
        $("#a2").removeClass("div-disabled-color");
        $("#radio_soil_1_color").prop('disabled', false);
        $("#radio_soil_2_color").prop('disabled', false);
        $("#radio_empty_1_color").prop('disabled', false);
        $("#radio_empty_2_color").prop('disabled', false);*/
    });

    $("#parameters1 input[name=agricultureSystRB]").on("click", function () {
        cont.clearSelectionAndColor();
        cont.closeInfoWindow();
        if($('input[name=agricultureSystRB]:checked').val() === "false"){
            cont.addListClick();
        }else cont.delListClick();
    });

    $("form#parameters1 input[name=yield1]").on("click", function () {
        $("#radio_pred_1_color").prop('disabled', false);
        $("#radio_pred_1_pattern").prop('disabled', false);
        $("#radio_pred_2_color").prop('disabled', false);
        $("#radio_pred_2_pattern").prop('disabled', false);
        $("#radio_empty_1_pattern").prop('disabled', false);
        $("#radio_empty_2_pattern").prop('disabled', false);
        cont.setClickRadio(true);
        $("#ch3").removeAttr("disabled");
    });

    $("form#parameters1 select[name=rotationShema1]").on("change", function () {
        $("#radio_cult_1_color").prop('disabled', false);
        $("#radio_cult_1_pattern").prop('disabled', false);
        $("#radio_cult_2_color").prop('disabled', false);
        $("#radio_cult_2_pattern").prop('disabled', false);
    });

    $("form#parameters1 input[name=calc1]").on("click", function () {
        $("#span_print").removeClass("div-disabled");
        $("#print").removeClass("div-disabled-color");
        $("#print_ui").removeClass("div-disabled");
    });

    $("#parameters1 input[name=clearAll1]").on("click", function () {
        cont.clearTriangleCulture();
        fields.forEach(function (field) {
            cont.taskColor(field)
        });
        cont.clearSelectionAndColor();
        cont.setSelectionFieldFlag(false);
        cont.setClickFieldFlag(false);
        cont.setClickInfoWindow(false);
        cont.setOpenInfoWindow(false);
        cont.setClickRadio(false);
        cont.closeInfoWindow();
        cont.delClickNotActive();
        cont.addClickNotActive();
        cont.delListRightClick();
        cont.setNotActive(true);
    });

    addListenersRadio();
}
