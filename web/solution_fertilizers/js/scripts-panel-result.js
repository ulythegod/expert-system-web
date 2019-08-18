
/* Сворачивание/разворачивание панели результатов*/
$(document).ready(function () {
    $(function () {
        $('#tabbb').click(function () {
            var panel = $('#panel-res');
            if (panel.hasClass('open')) {
                $(this).html('<i class="fa fa-caret-square-o-right" style="margin: 0px 0px 0px 18px; " title="Посмотреть результаты"></i>').removeClass('show');
                $('#panel-res').animate({ width: '0' }, 500, function () { });
                panel.removeClass('open');

            } else {
                $(this).html('<i class="fa fa-caret-square-o-left" style="margin: 0px 0px 0px -2px; " title="Свернуть панель"></i>').removeClass('show');
                panel.addClass('open');
                $('#panel-res').animate({ width: '700' }, 500, function () { });

            }
        });
    });
});

$(document).ready(function () {
    initListeners();
});




function addSelection(element) {
    element.closest("tr").addClass("selected");
}

function removeSelection(element) {
    element.closest("tr").removeAttr("class");
}

function initListeners() {
    $("#delete").on("click", function(){
        return confirm("Вы уверены, что хотите удалить выбранные записи?");
    });

    $("#checkUncheckAll").on("change", function () {
        check($(this));
    });

    $("#results-tests1 input[type=checkbox]").on("change", function () {
        if ($(this).is(":checked")) {
            addSelection($(this));
        }
        else {
            removeSelection($(this));
        }
        var stop = false;
        $("#results-tests1 input[type=checkbox]").each(function (i) {
            if (this.checked) {
                $(".control input[type=checkbox]").hide();
                $(".control img#checkbox-minus").show();
                stop = true;
            }
            else {
                $(".control input[type=checkbox]").show();
                $(".control img#checkbox-minus").hide();
            }
            if (stop) return false;
        });
    });

    $(".control img#checkbox-minus").on("click", function () {
        $("#results-tests1 input[type=checkbox]").each(function (i) {
            if (this.checked) {
                this.checked = "";
                removeSelection($(this));
            }
        });
        $(this).hide();
        $(".control input[type=checkbox]").removeAttr("checked");
        $(".control input[type=checkbox]").show();
    });
}

function check(self) {
    var checkboxes = self.closest('form').find(':checkbox');
    if (self.is(':checked')) {
        checkboxes.attr('checked', 'checked');
        checkboxes.each(function () {
            addSelection($(this));
        });
    }
    else {
        checkboxes.removeAttr('checked');
        checkboxes.each(function () {
            removeSelection($(this));
        });
    }
}
function setParam(name, value){
    var res = '';
    var d = location.href.split("?");
    var base = d[0];
    var query = d[1];
    if(query) {
        var params = query.split("&");
        for(var i = 0; i < params.length; i++) {
            var keyval = params[i].split("=");
            if(keyval[0] == name){
                break;
            }
            res += params[i] + '&';
        }
    }
    console.log("name=" + name + " value = " + value);
    res += name + '=' + value;
    document.location.href = base + '?' + res;
}