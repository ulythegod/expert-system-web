function addListenersRadio(){

    $("input[name=radio_1_color]").on("change", function () {
        if ($("#radio_empty_1_color").prop("checked")) {
            field_id.setColor("#999999");
        }else if($("#radio_soil_1_color").prop("checked")) {
            cont.taskColor(field_id);
        }else if($("#radio_pred_1_color").prop("checked")) {
            console.log(123);
            var color_pred = prev_color[text_prev.match(/\d+(\.\d+)/gim)];
            field_id.setColor(color_pred);
        }else if($("#radio_cult_1_color").prop("checked")) {
            var color_cult = color_crops[text_cult.match(/\d+(\.\d+)/gim)];
            field_id.setColor(color_cult);
        }
    });

    $("input[name=radio_1_pattern]").on("change", function () {
        if ($("#radio_empty_1_pattern").prop("checked")) {
            field_id.set('pattern', null);
        }else if($("#radio_pred_1_pattern").prop("checked")) {
            var pattern_pred = prev_pattern[text_prev.match(/\d+(\.\d+)/gim)];
            field_id.set('pattern', pattern_pred);
        }else if($("#radio_cult_1_pattern").prop("checked")) {
            var pattern_cult = pattern_crops[text_cult.match(/\d+(\.\d+)/gim)];
            field_id.set('pattern', pattern_cult);
        }
    });

    $("input[name=radio_2_color]").on("change", function () {
        if ($("#radio_empty_2_color").prop("checked")) {
            cont.setColorTriangle("transparent");
        }else if($("#radio_soil_2_color").prop("checked")) {
            cont.setColorTriangle(cont.getFielldColor(field_id));
        }else if($("#radio_pred_2_color").prop("checked")) {
            var color_pred = prev_color[text_prev.match(/\d+(\.\d+)/gim)];
            cont.setColorTriangle(color_pred);
        }else if($("#radio_cult_2_color").prop("checked")) {
            var color_cult = color_crops[text_cult.match(/\d+(\.\d+)/gim)];
            cont.setColorTriangle(color_cult);
        }
    });

    $("input[name=radio_2_pattern]").on("change", function () {
        if ($("#radio_empty_2_pattern").prop("checked")) {
            cont.setPatternTriangle(null);
        }else if($("#radio_pred_2_pattern").prop("checked")) {
            var pattern_pred = prev_pattern[text_prev.match(/\d+(\.\d+)/gim)];
            cont.setPatternTriangle(pattern_pred);
        }else if($("#radio_cult_2_pattern").prop("checked")) {
            var pattern_cult = pattern_crops[text_cult.match(/\d+(\.\d+)/gim)];
            cont.setPatternTriangle(pattern_cult);
        }
    });
}