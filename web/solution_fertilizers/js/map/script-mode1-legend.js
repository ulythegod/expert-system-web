var prev_color;
var prev_pattern;
var pattern_crops = null;
var color_crops;
var tabbb_flag = false;
var obj_test;
var old = "h3_2";
var flag = true;
var ravno = "";
var id = "";
var legend = false;

soils = {
    arr_name: [],
    arr_color: []
};

predecessors = {
    arr_name: [],
    arr_color: []
};

crops = {
    arr_name: [],
    arr_pattern: []
};

var arr_content  = [];
var arr_header = ["Культура", "Тип почвы", "Предшественник"];
$('document').ready(function(){
    for(var i = 0; i < 3; i++){
        $("#l"+i).text(arr_header[i]);
    }
    for(var i = 0; i < 3; i++){
        $("#p"+i).text(arr_content[i]);
    }

    $('#collapseLayers').on('hidden.bs.collapse', function () {
        $('#div_setting_layer').removeClass("panel-radius-open").addClass("panel-radius-close");
    });

    $('#collapseLayers').on('show.bs.collapse', function () {
        $('#div_setting_layer').removeClass("panel-radius-close").addClass("panel-radius-open");
    });

    $('#collapse_leg').on('hidden.bs.collapse', function () {
        if(!flag){
            $('#collapse_leg').collapse('show');
        }else{
            $(obj_test).removeClass("active");
        }
    });
    $('#collapse_leg').on('show.bs.collapse', function () {
        flag = false;
        switch(id){
            case 'a0':
                addRow_crops();
                break;
            case 'a1':
                addRow_soils();
                break;
            case 'a2':
                addRow_predecessor();
                break;
        }
        $("#ap_table").css({'opacity': '1' });
    });

    $("#tabbb").on("click", function () {
        if(tabbb_flag){
            tabbb_flag = false;
        }else{
            tabbb_flag = true;
        }
    });

});

function acc(obj){
    obj_test = obj;
    $("#a0").removeClass("active");
    $("#a1").removeClass("active");
    $("#a2").removeClass("active");
    $(obj).addClass("active");
    id = $(obj).attr("id");
    $("#ap_table").css({'opacity': '0' });
    if(ravno == id) {
        flag = true;
    }else{
        flag = false;
    }
    ravno = id;
    $('#collapse_leg').collapse('hide');
    $('#collapse_leg').collapse('show');
}

function addRow_soils(){
    $("#ap_table").empty();
    for(var i = 0; i < soils.arr_name.length; i++){
        $("#ap_table").append('<tr><td><div class="rectangle" style="background-color:'+soils.arr_color[i]+'"></div></td><td class="table-text">'+soils.arr_name[i]+'</td></tr>');
    }
}

function addRow_predecessor(){
    $("#ap_table").empty();
    for(var i = 0; i < predecessors.arr_name.length; i++){
        $("#ap_table").append('<tr><td><div class="rectangle" style="background-color:'+predecessors.arr_color[i]+'"></div></td><td class="table-text">'+predecessors.arr_name[i]+'</td></tr>');
    }
}

function addRow_crops(){
    $("#ap_table").empty();
    var id_svg = "cross_";
    for(var i = 0; i < crops.arr_name.length; i++){
        id_svg+=i;
        $("#ap_table").append('<tr><td><div class="rectangle">'+
            patternCropsDiv(id_svg, i)
            +'</div></td><td class="table-text">'+crops.arr_name[i]+'</td></tr>');
        id_svg = "cross_";
    }
}

$("form#parameters1 input[name=yield1]").on("click", function () {
    $.get("/set1", {id_crops : 1}, function(responseJson) {
        prev_color = responseJson["prev_color"];
        prev_pattern = responseJson["prev_pattern"];
    });
});

function getLegendData() {
    legend = true;
    $.get("/getLegend", {id_legend : 1}, function(responseJson) {
        var ordered = {};
        Object.keys(responseJson["allColorFields"]).sort().forEach(function (key) {
            ordered[key] = responseJson["allColorFields"][key];
        });
        $.each(ordered, function(key, value) {
            soils.arr_name.push(key);
            soils.arr_color.push(value);
        });
        ordered = {};
        Object.keys(responseJson["allColorCrops"]).sort().forEach(function (key) {
            ordered[key] = responseJson["allColorCrops"][key];
        });
        $.each(ordered, function(key, value) {
            predecessors.arr_name.push(key);
            predecessors.arr_color.push(value);
        });
        ordered = {};
        Object.keys(responseJson["allPatternCrops"]).sort().forEach(function (key) {
            ordered[key] = responseJson["allPatternCrops"][key];
        });
        $.each(ordered, function(key, value) {
            crops.arr_name.push(key);
            crops.arr_pattern.push(value);
        });
    });
}

function patternCropsDiv(id_svg, id) {
    var result;
    var s = crops.arr_pattern[id].match(/[a-zA-Z]+/gim);
    switch(s[0]){
        case 'Line' :
            result = linePattern(id_svg, crops.arr_pattern[id].match(/\d+/gim));
            break;
        case 'Plus' :
            result = plusPattern(id_svg, crops.arr_pattern[id].match(/\d+/gim));
            break;
        case 'Triangle' :
            result = trianglePattern(id_svg, crops.arr_pattern[id].match(/\d+/gim));
            break;
        case 'Cross' :
            result = crossPattern(id_svg, crops.arr_pattern[id].match(/\d+/gim));
            break;
    }
    return result;
}

var id_pattern = 0;
function patternCropsDiv2(pattern) {
    var result;
    id_pattern++;
    switch(pattern.match(/[a-zA-Z]+/gim)[0]){
        case 'Line' :
            result = linePattern("pat"+id_pattern, pattern.match(/\d+/gim));
            break;
        case 'Plus' :
            result = plusPattern("pat"+id_pattern, pattern.match(/\d+/gim));
            break;
        case 'Triangle' :
            result = trianglePattern("pat"+id_pattern, pattern.match(/\d+/gim));
            break;
        case 'Cross' :
            result = crossPattern("pat"+id_pattern, pattern.match(/\d+/gim));
            break;
    }
    return result;
}

function linePattern(id, rotate) {
    if(rotate == null) rotate = 0;
    return '<svg width="100%" height="100%" xmlns="http://www.w3.org/2000/svg" version="1.1">' +
        '<defs><pattern id="'+id+'" patternUnits="userSpaceOnUse" height="5" width="5" patternTransform="rotate('+rotate+')">' +
        '<line x1="0" y1="0.5" x2="7" y2="0.5" stroke="black" stroke-width="1"/></pattern></defs>' +
        '<rect x="0" y="0" width="30" height="15" fill="url(#'+id+')" stroke="black"/>' +
        '</svg>'
}

function plusPattern(id, rotate) {
    if(rotate == null) rotate = 0;
    return '<svg width="100%" height="100%" xmlns="http://www.w3.org/2000/svg" version="1.1">' +
        '<defs><pattern id="'+id+'" patternUnits="userSpaceOnUse" height="10" width="10" patternTransform="rotate('+rotate+')">' +
        '<path d="M 4,0 L 4,8 M 0,4 L 8,4 M 13,9 L 13,17 M 9,13 L 17,13" stroke="black" stroke-width="1" fill="none"/></pattern></defs>' +
        '<rect x="0" y="0" width="30" height="15" fill="url(#'+id+')" stroke="black"/>' +
        '</svg>'
}

function trianglePattern(id, rotate) {
    if(rotate == null) rotate = 0;
    return '<svg width="100%" height="100%" xmlns="http://www.w3.org/2000/svg" version="1.1">' +
        '<defs><pattern id="'+id+'" patternUnits="userSpaceOnUse" height="7" width="7" patternTransform="rotate('+rotate+')">' +
        '<g><polygon points="4,0 8,8 0,8"/>' +
        '<polygon points="14,10 18,18 10,18"/></g></pattern></defs>' +
        '<rect x="0" y="0" width="30" height="15" fill="url(#'+id+')" stroke="black"/>' +
        '</svg>'
}

function crossPattern(id, rotate) {
    if(rotate == null) rotate = 0;
    return '<svg width="100%" height="100%" xmlns="http://www.w3.org/2000/svg" version="1.1">' +
        '<defs><pattern id="'+id+'" patternUnits="userSpaceOnUse" height="7" width="7" patternTransform="rotate('+rotate+')">' +
        '<polyline points="0,0.5 6.5,0.5 6.5,7" fill="none" stroke="black" stroke-width="1"/></pattern></defs>' +
        '<rect x="0" y="0" width="30" height="15" fill="url(#'+id+')" stroke="black"/>' +
        '</svg>'
}