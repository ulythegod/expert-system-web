function soil_checked(){
    /**
     * В настройках слоев выбран для типа почв:
     * 1 - цвет слоя 1 и/или цвет слоя 2
     * 4 - пусто
     */
    if($("#radio_soil_1_color").prop("checked") || $("#radio_soil_2_color").prop("checked")){
        return 1;
    }else{
        return 4;
    }
}

function culture_checked(){
    /**
     * В настройках слоев выбран для культуры:
     * 1 - цвет слоя 1 и/или цвет слоя 2
     * 2 - штриховка слоя 1 и/или штриховка слоя 2
     * 3 - (цвет слоя 1 и штриховка слоя 2) или (штриховка слоя 1 и цвет слоя 2)
     * 4 - пусто
     */
    if(($("#radio_cult_1_color").prop("checked") && $("#radio_cult_2_pattern").prop("checked")) ||
        ($("#radio_cult_2_color").prop("checked") && $("#radio_cult_1_pattern").prop("checked")) ||
        ($("#radio_cult_1_color").prop("checked") && $("#radio_cult_1_pattern").prop("checked")) ||
        ($("#radio_cult_2_color").prop("checked") && $("#radio_cult_2_pattern").prop("checked"))){
        return 3;
    }else if($("#radio_cult_1_pattern").prop("checked") || $("#radio_cult_2_pattern").prop("checked")){
        return 2;
    }else if($("#radio_cult_1_color").prop("checked") || $("#radio_cult_2_color").prop("checked")){
        return 1;
    }else{
        return 4;
    }
}

function prev_checked(){
    /**
     * В настройках слоев выбран для предшественника:
     * 1 - цвет слоя 1 и/или цвет слоя 2
     * 2 - штриховка слоя 1 и/или штриховка слоя 2
     * 3 - (цвет слоя 1 и штриховка слоя 2) или (штриховка слоя 1 и цвет слоя 2)
     * 4 - пусто
     */
    if(($("#radio_pred_1_color").prop("checked") && $("#radio_pred_2_pattern").prop("checked")) ||
        ($("#radio_pred_2_color").prop("checked") && $("#radio_pred_1_pattern").prop("checked")) ||
        ($("#radio_pred_1_color").prop("checked") && $("#radio_pred_1_pattern").prop("checked")) ||
        ($("#radio_pred_2_color").prop("checked") && $("#radio_pred_2_pattern").prop("checked"))){
        return 3;
    }else if($("#radio_pred_1_pattern").prop("checked") || $("#radio_pred_2_pattern").prop("checked")){
        return 2;
    }else if($("#radio_pred_1_color").prop("checked") || $("#radio_pred_2_color").prop("checked")){
        return 1;
    }else{
        return 4;
    }
};