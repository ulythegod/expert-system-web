$(document).ready(function () {
    /*Инициализация таблицы*/
    table = $('#results-tests1').dataTable({
        /*Названия в элементах навигации*/
        "oLanguage": {
            "sZeroRecords": "К сожалению, ничего не найдено",
            "sSearch": "Поиск в таблице:",
        },
        /*Отключение сортировки в 1ом столбце*/
        "aoColumnDefs" : [
            {
                "aTargets" : [0],
                "bSortable" : false
            }],
        "order": [[ 1, "asc" ]],
        "paging" : false,
        "autoWidth": true,
        "info": false
    });
});
