$(document).ready(function () {
    $("div .tab-content").hide();
});

function clickNameResult(idResult) {
    console.log("clickNameSolution: " + idResult);

    var params = {
        idResult: idResult
    };
    $.get("/solution_fertilizers_results_mode", $.param(params),function(responseJson) {
        console.log("clickOnNameSuccess");
        console.log(responseJson);
        $('.data-result #table-result tr:nth-child(1) th').text(responseJson["newNameSolution"]);
        if ($("#parameters1 select[name=agricultureSystSelect] :selected").val()!=-1&&$("#parameters1 select[name=agricultureSystSelect]").is(':enabled')){
            $('<a>',{
                text:responseJson["agrSyst"],
                href:'#'
            }).appendTo('.data-result #table-result tr:nth-child(2) td');
            //$('.data-result #table-result tr:nth-child(2) td').text('<a href="#">'+$("#parameters1 select[name=agricultureSystSelect] :selected").text()+'</a>');
        }else $('.data-result #table-result tr:nth-child(2) td').text(responseJson["agrSyst"]);
        $('.data-result #table-result tr:nth-child(3) td').text(responseJson["field"]);
        $('.data-result #table-result tr:nth-child(4) td').text(responseJson["area"]);
        $('.data-result #table-result tr:nth-child(5) td').text(responseJson["typeOfSoil"]);
        $('.data-result #table-result tr:nth-child(6) td').text(responseJson["depth"]);
        $('.data-result #table-result tr:nth-child(7) td').text(responseJson["bulk"]);
        $('.data-result #table-result tr:nth-child(8) td').text(responseJson["crop"]);
        $('.data-result #table-result tr:nth-child(9) td').text(responseJson["planningYield"]);
        $('.data-result #table-result tr:nth-child(10) td').text(responseJson["pred"]);
        $('.data-result #table-result tr:nth-child(11) td').text(responseJson["predDoze"]);
        $('.data-result #table-result tr:nth-child(12) td').text(responseJson["predYield"]);
        $('.data-result #table-result tr:nth-child(13) td').text(responseJson["systFert"]);

        $('.data-result #table-result tr:nth-child(15) td:nth-child(1)').text(responseJson["humus"]);
        $('.data-result #table-result tr:nth-child(15) td:nth-child(2)').text(responseJson["phosphorus"]);
        $('.data-result #table-result tr:nth-child(15) td:nth-child(3)').text(responseJson["potassium"]);
        $('.data-result #table-result tr:nth-child(17) td:nth-child(2)').text(responseJson["nitrogen1"]);
        $('.data-result #table-result tr:nth-child(17) td:nth-child(3)').text(responseJson["phosphorus1"]);
        $('.data-result #table-result tr:nth-child(17) td:nth-child(4)').text(responseJson["potassium1"]);
        $('.data-result #table-result tr:nth-child(17) td:nth-child(5)').text(responseJson["coeff1Info"]);

        $('.data-result #table-result tr:nth-child(18) td:nth-child(2)').text(responseJson["nitrogen2"]);
        $('.data-result #table-result tr:nth-child(18) td:nth-child(3)').text(responseJson["phosphorus2"]);
        $('.data-result #table-result tr:nth-child(18) td:nth-child(4)').text(responseJson["potassium2"]);
        $('.data-result #table-result tr:nth-child(19) td:nth-child(1)').text(responseJson["yearOfUsing"]);
        $('.data-result #table-result tr:nth-child(19) td:nth-child(2)').text(responseJson["yearOfUsing"]);
        $('.data-result #table-result tr:nth-child(19) td:nth-child(3)').text(responseJson["yearOfUsing"]);
        $('.data-result #table-result tr:nth-child(18) td:nth-child(5)').text(responseJson["coeff2Info"]);

        $('.data-result #table-result tr:nth-child(20) td:nth-child(1)').text(responseJson["nitrogen3"]);
        $('.data-result #table-result tr:nth-child(20) td:nth-child(2)').text(responseJson["phosphorus3"]);
        $('.data-result #table-result tr:nth-child(20) td:nth-child(3)').text(responseJson["potassium3"]);
        $('.data-result #table-result tr:nth-child(20) td:nth-child(4)').text(responseJson["coeff3Info"]);

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
        $(".data-result").show();
        document.location.href += "tab-results";
    });
}