$(document).ready(function () {
    $("div .tab-content").hide();
    initListeners();
});

function initListeners(){
    $("#newNameSolution").on("input", function () {
        $("#parameters1 input[name=createNewNameSolutionButton]").removeAttr("disabled");
        newNameSolution = this.value;
    });

    $("#parameters1 input[name=createNewNameSolutionButton]").on("click", function () {
        createNewNameSolutionButton = this.value;
        var params = {
            newNameSolution: newNameSolution,
            createNewNameSolutionButton: createNewNameSolutionButton
        };
        $("#parameters1 input[name=clearAll1]").removeAttr("disabled");
        $.get("/solution_fertilizers_setting_mode_without_agriculture_system", $.param(params), function(responseJson) {
            var $select = $("select[name=field1]");
            fixedStr = '<option name="field1" value="-1" disabled="disabled"  selected>' + $select.find("option[value='-1']").text() + '</option>';
            $select.find("option").remove();
            $.each(responseJson, function(index, value) {
                /*!НЕ МЕНЯТЬ, НОМЕР ПОЛЯ НУЖЕН ДЛЯ ПАРСИНГА CSV ФАЙЛОВ*/
                //$("<option>").val(key).text(value).appendTo($select);
                $select.append('<option name="'+value["field-number"]+'" value="'+value["field-id"]+'">' + value["field-name"] + '</option>');
            });
            $select.append(fixedStr);
        });
        $("#parameters1 #fieldCharacteristics").removeAttr("disabled");
        $("#parameters1 select[name=field1]").removeAttr("disabled");
    });

    $("#parameters1 select[name=field1]").on("change", function () {
            field1 = this.value;
            var params = {
                newNameSolution: newNameSolution,
                createNewNameSolutionButton: createNewNameSolutionButton,
                field1: field1
            };
            $("#parameters1 select[name=field1] option[value='-1']").removeAttr("selected");
            $.get("/solution_fertilizers_setting_mode_without_agriculture_system", $.param(params), function (responseJson) {
                area1 = responseJson["area1"];
                typeSoil1 = responseJson["typeSoil1"];
                depth = responseJson["depth"];
                bulk = responseJson["bulk"];

                $("#parameters1 input[name=area1]").val(area1);
                $("#parameters1 input[name=typeSoil1]").val(typeSoil1);
                $("#parameters1 input[name=depth]").val(depth);
                $("#parameters1 input[name=bulk]").val(bulk);
                $("input[name=depth]").trigger("input");
                $("input[name=bulk]").trigger("input");

                var $select = $("select[name=corpSelect]");
                $select.find("option").remove();
                $.each(responseJson["corpSelect"], function (key, value) {
                    $("<option>").val(key).text(value).appendTo($select);
                });
                $select.append(fixedStr);

                $("#parameters1 select[name=corpSelect]").removeAttr("disabled");
                $("#parameters1 select[name=corpSelect]").prev("p").attr("class", "enabled");
            });
            agrSystIsntNeeded();
    });

    //СТАРТ РАССЧЕТОВ
    $("#parameters1 input[name=calc1]").on("click", function () {
        var params = {
            newNameSolution: newNameSolution,
            createNewNameSolutionButton: createNewNameSolutionButton,
            field1 : $("#parameters1 select[name=field1] :selected").attr("value"),
            area1: area1,
            typeSoil1: typeSoil1,
            depth: depth,
            bulk: bulk,
            planningYield: planningYield,
            predDoze:$("#parameters1 input[name=predDoze]").val(),
            predYield:$("#parameters1 input[name=predYield]").val(),
            humus:$("#parameters1 input[name=humus]").val(),
            potassium:$("#parameters1 input[name=potassium]").val(),
            phosphorus:$("#parameters1 input[name=phosphorus]").val(),
            nitrogen1:$("#parameters1 input[name=nitrogen1]").val(),
            phosphorus1:$("#parameters1 input[name=phosphorus1]").val(),
            potassium1:$("#parameters1 input[name=potassium1]").val(),
            predYear:$('#parameters1 select[name=yearOfUsing] :selected').val(),
            nitrogen2:$("#parameters1 input[name=nitrogen2]").val(),
            phosphorus2:$("#parameters1 input[name=phosphorus2]").val(),
            potassium2:$("#parameters1 input[name=potassium2]").val(),
            nitrogen3:$("#parameters1 input[name=nitrogen3]").val(),
            phosphorus3:$("#parameters1 input[name=phosphorus3]").val(),
            potassium3:$("#parameters1 input[name=potassium3]").val(),
            calc:"true"
        };
        if($('input[name=agricultureSystRB]:checked').val() === "false"){
            params["corpSelect"]=corpSelect;
            params["systFert"]=systFert;
            params["predSelect"]=predSelect;
        }
            $.get("/solution_fertilizers_setting_mode_without_agriculture_system", $.param(params), function(responseJson) {
                if (responseJson["error-null"]!=null){
                    alert("Ошибка. Введены не все данные для расчетов");
                    return;
                }
                if (responseJson["error-number"]!=null){
                    alert("Ошибка. Данные введен некорректно");
                    return;
                }

                console.log("calculationsSuccess");
                $('.data-result #table-result tr:nth-child(1) th').text("Результаты расчета доз удобрений "+$("#parameters1 input[name=newNameSolution]").val());
                $('.data-result #table-result tr:nth-child(2) td').text($("#parameters1 select[name=agricultureSystSelect] :selected").text());
                $('.data-result #table-result tr:nth-child(3) td').text($("#parameters1 select[name=field1] :selected").text());
                $('.data-result #table-result tr:nth-child(4) td').text($("#parameters1 input[name=area1]").val());
                $('.data-result #table-result tr:nth-child(5) td').text($("#parameters1 input[name=typeSoil1]").val());
                $('.data-result #table-result tr:nth-child(6) td').text($("#parameters1 input[name=depth]").val());
                $('.data-result #table-result tr:nth-child(7) td').text($("#parameters1 input[name=bulk]").val());
                $('.data-result #table-result tr:nth-child(8) td').text($("#parameters1 select[name=corpSelect] :selected").text());
                $('.data-result #table-result tr:nth-child(9) td').text($("#parameters1 input[name=planningYield]").val());
                $('.data-result #table-result tr:nth-child(10) td').text($("#parameters1 select[name=predSelect] :selected").text());
                $('.data-result #table-result tr:nth-child(11) td').text($("#parameters1 input[name=predDoze]").val());
                $('.data-result #table-result tr:nth-child(12) td').text($("#parameters1 input[name=predYield]").val());
                $('.data-result #table-result tr:nth-child(13) td').text($("#parameters1 select[name=systFert] :selected").text());

                $('.data-result #table-result tr:nth-child(15) td:nth-child(1)').text($("#parameters1 input[name=humus]").val());
                $('.data-result #table-result tr:nth-child(15) td:nth-child(2)').text($("#parameters1 input[name=phosphorus]").val());
                $('.data-result #table-result tr:nth-child(15) td:nth-child(3)').text($("#parameters1 input[name=potassium]").val());
                $('.data-result #table-result tr:nth-child(17) td:nth-child(2)').text($("#parameters1 input[name=nitrogen1]").val());
                $('.data-result #table-result tr:nth-child(17) td:nth-child(3)').text($("#parameters1 input[name=phosphorus1]").val());
                $('.data-result #table-result tr:nth-child(17) td:nth-child(4)').text($("#parameters1 input[name=potassium1]").val());
                if ($('input[name=coeff1]:checked').val() === "true")
                    $('.data-result #table-result tr:nth-child(17) td:nth-child(5)').text("Коэффициенты хозяйства");
                else $('.data-result #table-result tr:nth-child(17) td:nth-child(5)').text("Рекомендуемые коэффициенты (из справочников)");

                $('.data-result #table-result tr:nth-child(18) td:nth-child(2)').text($("#parameters1 input[name=nitrogen2]").val());
                $('.data-result #table-result tr:nth-child(18) td:nth-child(3)').text($("#parameters1 input[name=phosphorus2]").val());
                $('.data-result #table-result tr:nth-child(18) td:nth-child(4)').text($("#parameters1 input[name=potassium2]").val());
                $('.data-result #table-result tr:nth-child(19) td:nth-child(1)').text($("#parameters1 #labelYear").text()+" "+$('#parameters1 select[name=yearOfUsing] :selected').val());
                $('.data-result #table-result tr:nth-child(19) td:nth-child(2)').text($("#parameters1 #labelYear").text()+" "+$('#parameters1 select[name=yearOfUsing] :selected').val());
                $('.data-result #table-result tr:nth-child(19) td:nth-child(3)').text($("#parameters1 #labelYear").text()+" "+$('#parameters1 select[name=yearOfUsing] :selected').val());
                if ($('input[name=coeff2]:checked').val() === "true")
                    $('.data-result #table-result tr:nth-child(18) td:nth-child(5)').text("Коэффициенты хозяйства");
                else $('.data-result #table-result tr:nth-child(18) td:nth-child(5)').text("Рекомендуемые коэффициенты (из справочников)");

                $('.data-result #table-result tr:nth-child(20) td:nth-child(1)').text($("#parameters1 input[name=nitrogen3]").val());
                $('.data-result #table-result tr:nth-child(20) td:nth-child(2)').text($("#parameters1 input[name=phosphorus3]").val());
                $('.data-result #table-result tr:nth-child(20) td:nth-child(3)').text($("#parameters1 input[name=potassium3]").val());
                if ($('input[name=coeff3]:checked').val() === "true")
                    $('.data-result #table-result tr:nth-child(20) td:nth-child(4)').text("Коэффициенты хозяйства");
                else $('.data-result #table-result tr:nth-child(20) td:nth-child(4)').text("Рекомендуемые коэффициенты (из справочников)");

                /*СЮДА РАСЧЕТНЫЕ ДАННЫЕ*/
                console.log(responseJson);
                $('#common-res-table tr:nth-child(1) td:nth-child(2)').text(responseJson["real_possible_harvest_N"]);
                $('#common-res-table tr:nth-child(1) td:nth-child(3)').text(responseJson["real_possible_harvest_Ph"]);
                $('#common-res-table tr:nth-child(1) td:nth-child(4)').text(responseJson["real_possible_harvest_P"]);
                $('#common-res-table tr:nth-child(2) td:nth-child(2)').text(responseJson["additional_N"]);
                $('#common-res-table tr:nth-child(2) td:nth-child(3)').text(responseJson["additional_Ph"]);
                $('#common-res-table tr:nth-child(2) td:nth-child(4)').text(responseJson["additional_P"]);
                $('#common-res-table tr:nth-child(3) th:nth-child(2)').text(responseJson["need_in_N"]);
                $('#common-res-table tr:nth-child(3) th:nth-child(3)').text(responseJson["need_in_Ph"]);
                $('#common-res-table tr:nth-child(3) th:nth-child(4)').text(responseJson["need_in_P"]);

                /*Расчетные данные - подробные результаты*/
                $('#res-description-table tr:nth-child(1) td:nth-child(2)').text(responseJson["takeawayN"]);
                $('#res-description-table tr:nth-child(1) td:nth-child(3)').text(responseJson["takeawayPh"]);
                $('#res-description-table tr:nth-child(1) td:nth-child(4)').text(responseJson["takeawayP"]);
                $('#res-description-table tr:nth-child(2) td:nth-child(2)').text(responseJson["m"]);
                $('#res-description-table tr:nth-child(2) td:nth-child(3)').text(responseJson["m"]);
                $('#res-description-table tr:nth-child(2) td:nth-child(4)').text(responseJson["m"]);
                $('#res-description-table tr:nth-child(3) td:nth-child(4)').text(responseJson["phContent"]);
                $('#res-description-table tr:nth-child(3) td:nth-child(5)').text(responseJson["pContent"]);
                $('#res-description-table tr:nth-child(4) td:nth-child(1)').text(responseJson["value_humus"]);
                $('#res-description-table tr:nth-child(4) td:nth-child(2)').text(responseJson["value_of_nitrogen_in_humus"]);
                $('#res-description-table tr:nth-child(5) td:nth-child(2)').text(responseJson["value_forms_of_nitrogen"]);
                $('#res-description-table tr:nth-child(6) td:nth-child(2)').text(responseJson["number_digestible_N"]);
                $('#res-description-table tr:nth-child(6) td:nth-child(3)').text(responseJson["number_digestible_Ph"]);
                $('#res-description-table tr:nth-child(6) td:nth-child(4)').text(responseJson["number_digestible_P"]);
                $('#res-description-table tr:nth-child(7) td:nth-child(2)').text(responseJson["real_possible_harvest_N"]);
                $('#res-description-table tr:nth-child(7) td:nth-child(3)').text(responseJson["real_possible_harvest_Ph"]);
                $('#res-description-table tr:nth-child(7) td:nth-child(4)').text(responseJson["real_possible_harvest_P"]);
                $('#res-description-table tr:nth-child(8) td:nth-child(2)').text(responseJson["absorption"]);
                $('#res-description-table tr:nth-child(9) td:nth-child(2)').text(responseJson["accumulated_balance"]);
                $('#res-description-table tr:nth-child(10) td:nth-child(2)').text(responseJson["accumulated_balance_in_soil"]);
                $('#res-description-table tr:nth-child(11) td:nth-child(2)').text(responseJson["accumulated_balance_in_leavings"]);
                $('#res-description-table tr:nth-child(12) td:nth-child(2)').text(responseJson["common_amount_of_digestible_N"]);
                $('#res-description-table tr:nth-child(12) td:nth-child(3)').text(responseJson["common_amount_of_digestible_Ph"]);
                $('#res-description-table tr:nth-child(12) td:nth-child(4)').text(responseJson["common_amount_of_digestible_P"]);
                $('#res-description-table tr:nth-child(13) td:nth-child(2)').text(responseJson["additional_N"]);
                $('#res-description-table tr:nth-child(13) td:nth-child(3)').text(responseJson["additional_Ph"]);
                $('#res-description-table tr:nth-child(13) td:nth-child(4)').text(responseJson["additional_P"]);
                $('#res-description-table tr:nth-child(14) td:nth-child(2)').text(responseJson["need_in_N"]);
                $('#res-description-table tr:nth-child(14) td:nth-child(3)').text(responseJson["need_in_Ph"]);
                $('#res-description-table tr:nth-child(14) td:nth-child(4)').text(responseJson["need_in_P"]);

                $("div .tab-content").show();
                $(".data-hardcode").hide();
                $(".data-result").show();
            });
    });

    //
    $("#parameters1 input[name=clearAll1]").on("click", function () {
        $("input[name=newNameSolution]").val("");
        disabledAndClearAll();
    });

    //ХАРДКОД, КНОПКА ТЕСТ
    $("#parameters1 input[name=test_button]").on("click", function () {
        $("#parameters1 input[name=newNameSolution]").val("result_1");
        $("#parameters1 select[name=field1] :selected").text("Поле №1");
        $("#parameters1 select[name=corpSelect] :selected").text("Картофель");
        $("#parameters1 select[name=predSelect] :selected").text("Кукуруза");
        $("#parameters1 input[name=typeSoil1]").val("1.2 Серые  лесные почвы водоразделов с уклоном до 2 градусов");
        $("#parameters1 select[name=systFert] :selected").val("1.2 Погрузка, транспортировка, внесение фосфорно-калийных удобрений под зяблевую вспашку. Погрузка, транспортировка, внесение азотных удобрений под предпосевную культивацию");
        $("#parameters1 input[name=area1]").val("68.02");
        $("#parameters1 input[name=planningYield]").val("21");
        $("#parameters1 input[name=depth]").val("22");
        $("#parameters1 input[name=bulk]").val("2.5");
        $("#parameters1 input[name=predDoze]").val("30");
        $("#parameters1 input[name=predYield]").val("27.5");
        $("#parameters1 input[name=humus]").val("5.2");
        $("#parameters1 input[name=phosphorus]").val("110");
        $("#parameters1 input[name=potassium]").val("331");
        $("#parameters1 input[name=phosphorus]").removeAttr("disabled");
        $("#parameters1 input[name=potassium]").removeAttr("disabled");
        $("#parameters1 fieldset[id=coef1]").removeAttr("disabled");
        $("#parameters1 fieldset[id=coef2]").removeAttr("disabled");
        $('input:radio[name=coeff1][value=true]').removeAttr("disabled");
        $('input:radio[name=coeff1][value=false]').removeAttr("disabled");
        $("#parameters1 input[name=nitrogen1]").val("0.4");
        $("#parameters1 input[name=phosphorus1]").val("0.2");
        $("#parameters1 input[name=potassium1]").val("0.6");
        $('input:radio[name=coeff2][value=true]').removeAttr("disabled");
        $('input:radio[name=coeff2][value=false]').removeAttr("disabled");
        $("#parameters1 input[name=nitrogen2]").val("65");
        $("#parameters1 input[name=phosphorus2]").val("17");
        $("#parameters1 input[name=potassium2]").val("55");
        $('input:radio[name=coeff3][value=true]').removeAttr("disabled");
        $('input:radio[name=coeff3][value=false]').removeAttr("disabled");
        $("#parameters1 input[name=nitrogen3]").val("50");
        $("#parameters1 input[name=phosphorus3]").val("10");
        $("#parameters1 input[name=potassium3]").val("5");
        $('.data-hardcode #table-top tr:nth-child(1) th').text("Результаты расчета доз удобрений "+$("#parameters1 input[name=newNameSolution]").val());
        $('.data-hardcode #table-top tr:nth-child(2) td').text("Не выбрано");
        $('.data-hardcode #table-top tr:nth-child(3) td').text("Поле №1");
        $('.data-hardcode #table-top tr:nth-child(4) td').text(68.02);
        $('.data-hardcode #table-top tr:nth-child(5) td').text("1.2 Серые  лесные почвы водоразделов с уклоном до 2 градусов");
        $('.data-hardcode #table-top tr:nth-child(6) td').text(22);
        $('.data-hardcode #table-top tr:nth-child(7) td').text(2.5);
        $('.data-hardcode #table-top tr:nth-child(8) td').text("Картофель");
        $('.data-hardcode #table-top tr:nth-child(9) td').text(180.33);
        $('.data-hardcode #table-top tr:nth-child(10) td').text("Кукуруза");
        $('.data-hardcode #table-top tr:nth-child(11) td').text(30);
        $('.data-hardcode #table-top tr:nth-child(12) td').text(27.5);
        $('.data-hardcode #table-top tr:nth-child(13) td').text("1.2 Погрузка, транспортировка, внесение фосфорно-калийных удобрений под зяблевую вспашку. Погрузка, транспортировка, внесение азотных удобрений под предпосевную культивацию");

        $('.data-hardcode #table-top tr:nth-child(15) td:nth-child(1)').text(5.2);
        $('.data-hardcode #table-top tr:nth-child(15) td:nth-child(2)').text(110);
        $('.data-hardcode #table-top tr:nth-child(15) td:nth-child(3)').text(331);
        $('.data-hardcode #table-top tr:nth-child(17) td:nth-child(2)').text(0.4);
        $('.data-hardcode #table-top tr:nth-child(17) td:nth-child(3)').text(0.2);
        $('.data-hardcode #table-top tr:nth-child(17) td:nth-child(4)').text(0.6);
        $('.data-hardcode #table-top tr:nth-child(17) td:nth-child(5)').text("Коэффициенты хозяйства");

        $('.data-hardcode #table-top tr:nth-child(18) td:nth-child(2)').text(65);
        $('.data-hardcode #table-top tr:nth-child(18) td:nth-child(3)').text(17);
        $('.data-hardcode #table-top tr:nth-child(18) td:nth-child(4)').text(55);
        $('.data-hardcode #table-top tr:nth-child(19) td:nth-child(1)').text($("#parameters1 #labelYear").text()+" "+1);
        $('.data-hardcode #table-top tr:nth-child(19) td:nth-child(2)').text($("#parameters1 #labelYear").text()+" "+1);
        $('.data-hardcode #table-top tr:nth-child(19) td:nth-child(3)').text($("#parameters1 #labelYear").text()+" "+1);
        $('.data-hardcode #table-top tr:nth-child(18) td:nth-child(5)').text("Коэффициенты хозяйства");

        $('.data-hardcode #table-top tr:nth-child(20) td:nth-child(2)').text(50);
        $('.data-hardcode #table-top tr:nth-child(20) td:nth-child(3)').text(10);
        $('.data-hardcode #table-top tr:nth-child(20) td:nth-child(4)').text(5);
        $('.data-hardcode #table-top tr:nth-child(20) td:nth-child(5)').text("Коэффициенты хозяйства");

        /*СЮДА РАСЧЕТНЫЕ ДАННЫЕ*/
        $('#CommonResHard-table tr:nth-child(1) td:nth-child(2)').text(3890743.5);
        $('#CommonResHard-table tr:nth-child(1) td:nth-child(3)').text(2057604.63);
        $('#CommonResHard-table tr:nth-child(1) td:nth-child(4)').text(1031919.94);
        $('#CommonResHard-table tr:nth-child(2) td:nth-child(2)').text(14885.92);
        $('#CommonResHard-table tr:nth-child(2) td:nth-child(3)').text(3754.56);
        $('#CommonResHard-table tr:nth-child(2) td:nth-child(4)').text(5109.56);
        $('#CommonResHard-table tr:nth-child(3) th:nth-child(2)').text("Не требуется дополнительного внесения микроэлемента");
        $('#CommonResHard-table tr:nth-child(3) th:nth-child(3)').text("Не требуется дополнительного внесения микроэлемента");
        $('#CommonResHard-table tr:nth-child(3) th:nth-child(4)').text("Не требуется дополнительного внесения микроэлемента");

        /*Расчетные данные - подробные результаты*/
        $('#ResDescriptionHard-table tr:nth-child(1) td:nth-child(2)').text(721.3);
        $('#ResDescriptionHard-table tr:nth-child(1) td:nth-child(3)').text(360.65);
        $('#ResDescriptionHard-table tr:nth-child(1) td:nth-child(4)').text(1081.96);
        $('#ResDescriptionHard-table tr:nth-child(2) td:nth-child(2)').text(374109.94);
        $('#ResDescriptionHard-table tr:nth-child(2) td:nth-child(3)').text(374109.94);
        $('#ResDescriptionHard-table tr:nth-child(2) td:nth-child(4)').text(374109.94);
        $('#ResDescriptionHard-table tr:nth-child(3) td:nth-child(4)').text(41152.09);
        $('#ResDescriptionHard-table tr:nth-child(3) td:nth-child(5)').text(123830.4);
        $('#ResDescriptionHard-table tr:nth-child(4) td:nth-child(1)').text(19453.72);
        $('#ResDescriptionHard-table tr:nth-child(4) td:nth-child(2)').text(972685.81);
        $('#ResDescriptionHard-table tr:nth-child(5) td:nth-child(2)').text(31125.95);
        $('#ResDescriptionHard-table tr:nth-child(6) td:nth-child(2)').text(15562.97);
        $('#ResDescriptionHard-table tr:nth-child(6) td:nth-child(3)').text(4115.21);
        $('#ResDescriptionHard-table tr:nth-child(6) td:nth-child(4)').text(6191.52);
        $('#ResDescriptionHard-table tr:nth-child(7) td:nth-child(2)').text(3890743.5);
        $('#ResDescriptionHard-table tr:nth-child(7) td:nth-child(3)').text(2057604.63);
        $('#ResDescriptionHard-table tr:nth-child(7) td:nth-child(4)').text(1031919.94);
        $('#ResDescriptionHard-table tr:nth-child(8) td:nth-child(2)').text(19.5);
        $('#ResDescriptionHard-table tr:nth-child(9) td:nth-child(2)').text(4125);
        $('#ResDescriptionHard-table tr:nth-child(10) td:nth-child(2)').text(82.5);
        $('#ResDescriptionHard-table tr:nth-child(11) td:nth-child(2)').text(24.75);
        $('#ResDescriptionHard-table tr:nth-child(12) td:nth-child(2)').text(15607.22);
        $('#ResDescriptionHard-table tr:nth-child(12) td:nth-child(3)').text(4115.21);
        $('#ResDescriptionHard-table tr:nth-child(12) td:nth-child(4)').text(6191.52);
        $('#ResDescriptionHard-table tr:nth-child(13) td:nth-child(2)').text(14885.92);
        $('#ResDescriptionHard-table tr:nth-child(13) td:nth-child(3)').text(3754.56);
        $('#ResDescriptionHard-table tr:nth-child(13) td:nth-child(4)').text(5109.56);
        $('#ResDescriptionHard-table tr:nth-child(14) td:nth-child(2)').text("Не требуется дополнительного внесения микроэлемента");
        $('#ResDescriptionHard-table tr:nth-child(14) td:nth-child(3)').text("Не требуется дополнительного внесения микроэлемента");
        $('#ResDescriptionHard-table tr:nth-child(14) td:nth-child(4)').text("Не требуется дополнительного внесения микроэлемента");

        $("div .tab-content").show();
        $(".data-hardcode").show();
        $(".data-result").hide();
    });
}

(function() {
    function disabledAllNext(element) {
        element.nextAll("select:enabled").prev("p").attr("class", "disabled");
        element.nextAll("select:enabled").val("-1");
        element.nextAll("select:enabled").attr("disabled", "disabled");
        element.nextAll("input:enabled").prev("p").attr("class", "disabled");
        element.nextAll("input:checked").prop("checked", false);
        element.nextAll("input:enabled").attr("disabled", "disabled");
        element.closest('fieldset').nextAll('fieldset').attr("disabled", "disabled");
        element.closest('fieldset').nextAll('fieldset').find("select").prev("p").attr("class", "disabled");
        element.closest('fieldset').nextAll('fieldset').find("select").val('-1');
        element.closest('fieldset').nextAll('fieldset').find("select").attr("disabled", "disabled");
        $("#parameters1 input[name=calc1]").attr("disabled", "disabled");
    }

    function disabledAll(element) {
        $("fieldset").find('select').prev("p").attr("class", "disabled");
        $("fieldset").find('select').attr("disabled", "disabled");
        $("fieldset").attr("disabled", "disabled");
        $("input[name=calc1]").attr("disabled", "disabled");
        $("#system1").attr("disabled", "disabled");
        $("input[name=createNewNameSolutionButton]").attr("disabled", "disabled");
    }

    function disabledAndClearAll(){
        $("input[name=createNewNameSolutionButton]").attr("disabled", "disabled");
        $("fieldset:not(.first)").find('input').attr("disabled", "disabled");
        $("fieldset:not(.first)").find('input[type="text"]').val("");
        $("fieldset:not(.first)").find('input[type="radio"]').attr('checked', false);
        $("select:not(.first)[name=field1]").find("option").remove();
        $("select[name=field1]").append('<option name="field1" value="-1" disabled="disabled"  selected>Не выбрано</option>');
        $("select[name=corpSelect]").append('<option name="corpSelect" value="-1" disabled="disabled"  selected>Не выбрано</option>');
        $("select[name=predSelect]").append('<option name="predSelect" value="-1" disabled="disabled"  selected>Не выбрано</option>');
        $("select[name=systFert]").append('<option name="systFert" value="-1" disabled="disabled"  selected>Не выбрано</option>');
        $("select[name=yearOfUsing]").append('<option name="yearOfUsing" value="-1" disabled="disabled"  selected>Не выбрано</option>');
        $("fieldset:not(.first)").find('select').attr("disabled", "disabled");
        $("fieldset:not(.first)").attr("disabled", "disabled");
    }

    function disabledOnRadioButtonClick(element){
        element.nextAll("input:enabled").prev("p").attr("class", "disabled");
        element.nextAll("input:checked").prop("checked", false);
        element.nextAll("input:enabled").attr("disabled", "disabled");
    }

    function setParametersForChemicalSurvey(response){
        if (response["humus"]!=null&&response["phosphorus"]&&response["potassium"]){
            $("#parameters1 input[name=humus]").val(response["humus"]);
            $("#parameters1 input[name=phosphorus]").val(response["phosphorus"]);
            $("#parameters1 input[name=potassium]").val(response["potassium"]);
            /*Разблокировка панели Коэффициенты 1*/
            $("#parameters1 fieldset[id=coef1]").removeAttr("disabled");
            $('input:radio[name=coeff1][value=true]').removeAttr("disabled");
            $('input:radio[name=coeff1][value=false]').removeAttr("disabled");
        }
    }

    function agrSystIsntNeeded(){
        $("#parameters1 select[name=corpSelect]").on("change", function () {
            corpSelect = this.value;
            $("#parameters1 select[name=corpSelect] option[value='-1']").removeAttr("selected");
            $("#parameters1 input[name=planningYield]").val("21");
            $("#parameters1 input[name=planningYield]").removeAttr("disabled");
            $("#parameters1 input[name=planningYield]").prev("p").attr("class", "enabled");
        });

        $("#planningYield").on("input", function () {
            if($("select[name=predSelect]").is(':disabled')) {
                planningYield = this.value;
                var params = {
                    newNameSolution: newNameSolution,
                    field1: field1,
                    corpSelect: corpSelect,
                    planningYield: planningYield
                };
                $.get("/solution_fertilizers_setting_mode_without_agriculture_system", $.param(params), function (responseJson) {
                    var $select = $("select[name=predSelect]");
                    $select.find("option").remove();
                    $.each(responseJson["predSelect"], function (key, value) {
                        $("<option>").val(key).text(value).appendTo($select);
                    });
                    $select.append(fixedStr);
                });
                $("#parameters1 select[name=predSelect]").removeAttr("disabled");
                $("#parameters1 select[name=predSelect]").prev("p").attr("class", "enabled");
            }
        });

        $("#parameters1 select[name=predSelect]").on("change", function () {
            predSelect = this.value;
            $("#parameters1 select[name=predSelect] option[value='-1']").removeAttr("selected");
            $("#parameters1 input[name=predDoze]").val("30");
            $("#parameters1 input[name=predDoze]").removeAttr("disabled");
            $("#parameters1 input[name=predDoze]").prev("p").attr("class", "enabled");
        });

        $("#predDoze").on("input", function () {
            predDoze = this.value;
            $("#parameters1 input[name=predYield]").val("27.5");
            $("#parameters1 input[name=predYield]").removeAttr("disabled");
            $("#parameters1 input[name=predYield]").prev("p").attr("class", "enabled");
        });

        $("#predYield").on("input", function () {
            predYield = this.value;
            var params = {
                newNameSolution: newNameSolution,
                field1: field1,
                corpSelect:  corpSelect,
                planningYield: planningYield,
                predSelect: predSelect,
                predDoze: predDoze,
                predYield: predYield
            };
            if($("select[name=systFert]").is(':disabled')) {
                $.get("/solution_fertilizers_setting_mode_without_agriculture_system", $.param(params), function (responseJson) {
                    var $select = $("select[name=systFert]");
                    $select.find("option").remove();
                    $.each(responseJson["systFert"], function (key, value) {
                        $("<option>").val(key).text(value).appendTo($select);
                    });
                    $select.append(fixedStr);
                });
                $("#parameters1 select[name=systFert]").removeAttr("disabled");
                $("#parameters1 select[name=systFert]").prev("p").attr("class", "enabled");
            }
        });

        $("#parameters1 select[name=systFert]").on("change", function () {
            systFert = this.value;
            $("#parameters1 select[name=systFert] option[value='-1']").removeAttr("selected");
            $("#parameters1 fieldset[id=chem_composition_data]").removeAttr("disabled");
            $("#parameters1 input[name=humus]").val("5.2");
            $("#parameters1 input[name=humus]").removeAttr("disabled");
            $("#parameters1 input[name=humus]").prev("p").attr("class", "enabled");

        });

        $("#humus").on("input", function () {
            humus = this.value;
            $("#parameters1 input[name=phosphorus]").val("110");
            $("#parameters1 input[name=phosphorus]").removeAttr("disabled");
            $("#parameters1 input[name=phosphorus]").prev("p").attr("class", "enabled");
        });

        $("#phosphorus").on("input", function () {
            phosphorus = this.value;
            $("#parameters1 input[name=potassium]").val("331");
            $("#parameters1 input[name=potassium]").removeAttr("disabled");
            $("#parameters1 input[name=potassium]").prev("p").attr("class", "enabled");
        });

        $("#potassium").on("input", function () {
            if($("#parameters1 fieldset[id=coef1]").is(':disabled')) {
                potassium = this.value;
                var params = getParamsForPanelCoeff();
                params['coeff1'] = $('input[name=coeff1]:checked').val();
                $.get("/solution_fertilizers_setting_mode_without_agriculture_system", $.param(params), function (responseJson) {
                    $("#parameters1 input[name=nitrogen1]").attr("disabled", "disabled");
                    $("#parameters1 input[name=phosphorus1]").attr("disabled", "disabled");
                    $("#parameters1 input[name=potassium1]").attr("disabled", "disabled");
                    if (responseJson["nitrogen1"] != null)
                        $("#parameters1 input[name=nitrogen1]").val(responseJson["nitrogen1"]);
                    else {
                        $("#parameters1 input[name=nitrogen1]").removeAttr("disabled");
                        $("#parameters1 input[name=nitrogen1]").val("0.4");
                    }
                    if (responseJson["potassium1"] != null)
                        $("#parameters1 input[name=potassium1]").val(responseJson["potassium1"]);
                    else {
                        $("#parameters1 input[name=potassium1]").removeAttr("disabled");
                      $("#parameters1 input[name=potassium1]").val("0.6");
                    }
                    if (responseJson["phosphorus1"] != null)
                        $("#parameters1 input[name=phosphorus1]").val(responseJson["phosphorus1"]);
                    else {
                        $("#parameters1 input[name=phosphorus1]").removeAttr("disabled");
                        $("#parameters1 input[name=phosphorus1]").val("0.2");
                    }
                    $("input[name=nitrogen1]").trigger("input");
                    $("input[name=potassium1]").trigger("input");
                    $("input[name=phosphorus1]").trigger("input");
                });
                $("#parameters1 fieldset[id=coef1]").removeAttr("disabled");
            }
        });

        /*РАЗБЛОКИРОВКА ПАНЕЛИ КОЭФФИЦИЕНТОВ 2 ТОЛЬКО ПОСЛЕ ТОГО, КАК БЫЛИ ВВЕДЕНЫ ВСЕ КОЭФФИЦИЕНТЫ НА ПАНЕЛИ 1*/
        $("#nitrogen1").on("input", function () {
            checkInputCoeff1();
        });

        $("#potassium1").on("input", function () {
            checkInputCoeff1();
        });

        $("#phosphorus1").on("input", function () {
            checkInputCoeff1();
        });

        function checkInputCoeff1(){
            if ($("#parameters1 input[name=nitrogen1]").val().length>0 && $("#parameters1 input[name=potassium1]").val().length>0 && $("#parameters1 input[name=phosphorus1]").val().length>0){
                if($("#parameters1 fieldset[id=coef2]").is(':disabled')) {
                    $("#parameters1 #coef2").removeAttr("disabled");

                    $("#parameters1 input[name=nitrogen2]").attr("disabled", "disabled");
                    $("#parameters1 input[name=phosphorus2]").attr("disabled", "disabled");
                    $("#parameters1 input[name=potassium2]").attr("disabled", "disabled");
                    $('#parameters1 select[name=yearOfUsing]').removeAttr("disabled");
                    var params = getParamsForPanelCoeff();
                    params['coeff2'] = $('input[name=coeff2]:checked').val();
                    $.get("/solution_fertilizers_setting_mode_without_agriculture_system", $.param(params), function (responseJson) {
                        if (responseJson["nitrogen2"] != null)
                            $("#parameters1 input[name=nitrogen2]").val(responseJson["nitrogen2"]);
                        else {
                            $("#parameters1 input[name=nitrogen2]").removeAttr("disabled");
                            $("#parameters1 input[name=nitrogen2]").val("3");
                        }
                        if (responseJson["potassium2"] != null)
                            $("#parameters1 input[name=potassium2]").val(responseJson["potassium2"]);
                        else {
                            $("#parameters1 input[name=potassium2]").removeAttr("disabled");
                           $("#parameters1 input[name=potassium2]").val("0.8");
                        }
                        if (responseJson["phosphorus2"] != null)
                            $("#parameters1 input[name=phosphorus2]").val(responseJson["phosphorus2"]);
                        else {
                            $("#parameters1 input[name=phosphorus2]").removeAttr("disabled");
                            $("#parameters1 input[name=phosphorus2]").val("1.5");
                        }
                    });
                }
                }else{
                $("#parameters1 fieldset[id=coef2]").attr("disabled","disabled");
            }
        }

        $("#parameters1 select[name=yearOfUsing]").on("change", function () {
            checkInputCoeff2();
        });

        /*РАЗБЛОКИРОВКА ПАНЕЛИ КОЭФФИЦИЕНТОВ 3 ТОЛЬКО ПОСЛЕ ТОГО, КАК БЫЛИ ВВЕДЕНЫ ВСЕ КОЭФФИЦИЕНТЫ НА ПАНЕЛИ 2*/
        $("#nitrogen2").on("input", function () {
            checkInputCoeff2();
        });

        $("#potassium2").on("input", function () {
            checkInputCoeff2();
        });

        $("#phosphorus2").on("input", function () {
            checkInputCoeff2();
        });

        function checkInputCoeff2(){
            if ($("#parameters1 input[name=nitrogen2]").val().length>0 && $("#parameters1 input[name=potassium2]").val().length>0 && $("#parameters1 input[name=phosphorus2]").val().length>0 && $('#parameters1 select[name=yearOfUsing] :selected').val()!="-1"){
                if($("#parameters1 fieldset[id=coef3]").is(':disabled')) {
                    $("#parameters1 #coef3").removeAttr("disabled");
                    var params = getParamsForPanelCoeff();
                    params['coeff3'] = $('input[name=coeff3]:checked').val();
                    $.get("/solution_fertilizers_setting_mode_without_agriculture_system", $.param(params), function (responseJson) {
                        if (responseJson["nitrogen3"] != null)
                            $("#parameters1 input[name=nitrogen3]").val(responseJson["nitrogen3"]);
                        else {
                            $("#parameters1 input[name=nitrogen3]").removeAttr("disabled");
                            $("#parameters1 input[name=nitrogen3]").val("1");
                        }
                        if (responseJson["potassium3"] != null)
                            $("#parameters1 input[name=potassium3]").val(responseJson["potassium3"]);
                        else {
                            $("#parameters1 input[name=potassium3]").removeAttr("disabled");
                            $("#parameters1 input[name=potassium3]").val("1");
                        }
                        if (responseJson["phosphorus3"] != null)
                            $("#parameters1 input[name=phosphorus3]").val(responseJson["phosphorus3"]);
                        else {
                            $("#parameters1 input[name=phosphorus3]").removeAttr("disabled");
                           $("#parameters1 input[name=phosphorus3]").val("1");
                        }
                    });
                }
            }else{
                $("#parameters1 fieldset[id=coef3]").attr("disabled","disabled");
            }
        }

        $("#nitrogen3").on("input", function () {
            checkInputCoeff3();
        });

        $("#potassium3").on("input", function () {
            checkInputCoeff3();
        });

        $("#phosphorus3").on("input", function () {
            checkInputCoeff3();
        });

        function  checkInputCoeff3() {
            if ($("#parameters1 input[name=nitrogen3]").val().length>0 && $("#parameters1 input[name=potassium3]").val().length>0 && $("#parameters1 input[name=phosphorus3]").val().length>0) {
                    $("#parameters1 input[name=calc1]").removeAttr("disabled");
                }
            }


        function getParamsForPanelCoeff(){
            var params = {
                newNameSolution: newNameSolution,
                createNewNameSolutionButton: createNewNameSolutionButton,
                field1: field1,
                area1: area1,
                typeSoil1: typeSoil1,
                depth: depth,
                bulk: bulk,
                planningYield: planningYield,
                corpSelect:corpSelect,
                systFert:systFert,
                predSelect:$("#parameters1 select[name=predSelect] :selected").text()
            };
            return params;
        }
    }

    window.disabledAllNext = disabledAllNext;
    window.disabledAll = disabledAll;
    window.disabledAndClearAll = disabledAndClearAll;
    window.disabledOnRadioButtonClick = disabledOnRadioButtonClick;
    window.setParametersForChemicalSurvey = setParametersForChemicalSurvey;
    window.agrSystIsntNeeded = agrSystIsntNeeded;
})();