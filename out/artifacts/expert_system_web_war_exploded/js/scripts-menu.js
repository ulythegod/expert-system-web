/*Расчет ширины верхнего меню при загрузке документа*/
$(document).ready(function() {
    $("#collapseForm").addClass("collapse");

    $('#main-menu').css({
        width: $('body').width() - $('#sidebar-wrapper').width() + 'px'
    });
});

/*Кнопка управления боковой панелью*/
$("#menu-toggle").click(function(e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
    changeArrow();

    $('#main-menu').toggleClass("main-menu-toggle");
    setMainMenuWidth();
});
$(".toggle-element").click(function(e) {
    $("#wrapper").removeClass("toggled");
    changeArrow();
    if ($(this).parent().find(".collapse:not('.in')").length) {
        $(this).addClass("sidebar-active");
    } else {
        $(this).removeClass("sidebar-active");
    }
});
/*Изменить направление стрелки на кнопке*/
function changeArrow(){
    if ($("#wrapper.toggled").length){
        $("#menu-toggle").html('<i class="menu-icon-arrow fa fa-angle-double-right"></i>');
        $(".toggle-element").removeClass("sidebar-active");
        $(".toggle-element-2").removeClass("sidebar-active");
        $(".sidebar-nav .collapse").collapse('hide');
    } else {
        $("#menu-toggle").html('<i class="menu-icon-arrow fa fa-angle-double-left"></i>');
    }
}
/*Изменить ширину верхнего меню*/
function setMainMenuWidth() {
    if ($('#main-menu').hasClass("main-menu-toggle"))
        $('#main-menu').width($('body').width() - 50);
    else
        $('#main-menu').width($('body').width() - 250);
}

/*Управление раскрывающимися списками в левом меню*/
glyph_opts = { //внешний вид
    map: {
        doc: "fa fa-file-o",
        docOpen: "fa fa-file-o",
        checkbox: "fa fa-square-o",
        checkboxSelected: "fa fa-check-square-o",
        checkboxUnknown: "fa fa-share-square-o",
        dragHelper: "fa fa-play",
        dropMarker: "fa fa-arrow-right",
        error: "fa fa-warning-sign",
        expanderClosed: "fa fa-caret-down", //"fa fa-plus",
        expanderLazy: "fa fa-caret-down",//"fa fa-plus",
        expanderOpen: "fa fa-caret-up", // "fa fa-minus",
        folder: "fa fa-folder",
        folderOpen: "fa fa-folder-open",
        loading: "fa fa-refresh fa-spinner"
    }
};

$(".tree").each(function() { //действия
    $(this).fancytree({
        extensions: ["glyph"],
        glyph: glyph_opts,
        icons: false,

        debugLevel: 0,
        activate: function(event, data){
            var node = data.node,
                orgEvent = data.originalEvent || {};
            if(node.data.href){
                window.location=node.data.href;
            }
            if( window.parent &&  parent.history && parent.history.pushState ) {
                parent.history.pushState({title: node.title}, "", "#" + (node.data.href || ""));
            }
        },
        click: function(event, data){
            var node = data.node,
                orgEvent = data.originalEvent;
            if(node.isActive() && node.data.href){
                window.location=node.data.href;
            }
        }
    });
});




