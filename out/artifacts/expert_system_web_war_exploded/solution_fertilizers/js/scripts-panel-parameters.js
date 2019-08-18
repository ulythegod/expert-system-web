/*Сворачивание/разворачивание панели*/
$(document).ready(function () {
    $(function () {
        $('#tabbb').click(function () {
            var panel = $('#panel');
            var map = $('#map');

            if (panel.hasClass('open')) {
                $(this).html('<i class="fa fa-caret-square-o-right" style="margin: 0px 0px 0px 18px; " title="Настроить параметры"></i>').removeClass('show');
                $('#panel').animate({ width: '0' }, 500, function () { });
                panel.removeClass('open');

            } else {
                $(this).html('<i class="fa fa-caret-square-o-left" style="margin: 0px 0px 0px -2px; " title="Свернуть панель"></i>').removeClass('show');
                panel.addClass('open');
                $('#panel').animate({ width: '500' }, 500, function () { });
            }
        });
    });
});





