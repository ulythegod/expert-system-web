<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="Resource" />
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Расчет норм удобрения для поля - Построение нового решения</title>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="../css/simple-sidebar.css" rel="stylesheet">
    <link href="../css/default_v4.css" rel="stylesheet">
    <link href="../css/general-styles.css" rel="stylesheet">
    <link href="/solution_fertilizers/css/proect-page-styles.css" rel="stylesheet">
    <link href="/solution_fertilizers/css/solution-fertilizers-special.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/font-awesome.min.css">
    <link href="../css/fancytree/ui.fancytree.css" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
</head>
<body>

<!-- Header-->
<%@include file="../header.jsp"%>

<!-- содержание-->
<div id="wrapper" style="padding-top: 50px;">

    <!-- Левое меню -->
    <%@include file="/solution_fertilizers/solution_fertilizers_left_menu.jsp"%>

    <!-- Page Content -->
    <div id="page-content-wrapper">
        <!-- Верхнее меню -->
        <%@include file="../mainmenu.jsp"%>

        <br>
        <!-- ПАНЕЛИ -->
        <div class="row" style="margin: 0; ">
            <!-- ПАНЕЛЬ НАСТРОЙКИ ПАРАМЕТРОВ -->
            <div class="col-md-12" id="sidebar-panel-setting" >
                <div id="panel"  class="panel-setting-parameters open">
                    <div class="content" >
                        <div class="panel-setting-parameters-title"><h3><fmt:message key="FERT_FORM_SETT_TITLE"/> </h3> </div>
                        <div class="panel-setting-parameters-with-scroll">

                            <div id="parameters1">

                                <fieldset id="newSol" class="first">
                                    <legend><fmt:message key="FERT_FORM_SETT_NEW"/></legend>
                                    <p><fmt:message key="FERT_FORM_SETT_NEW_NAME"/> </p><input type="text" name="newNameSolution" id="newNameSolution" style="width:80%;"/>
                                    &nbsp;<input type="button" name="createNewNameSolutionButton" value="<fmt:message key="FERT_FORM_SETT_BUT_CREATE"/>" style="float:right; width:17%; height:25px;" disabled="disabled"/>

                                    <p class="disabled"><fmt:message key="FERT_FORM_SETT_AGR_SYST"/> </p>
                                    <input type="radio" class="first" name="agricultureSystRB" value="true" disabled="disabled"/> <fmt:message key="FERT_FORM_SETT_MODE1_SOL"/><br />
                                    <input type="radio" class="first" name="agricultureSystRB" value="false" disabled="disabled"/> <fmt:message key="FERT_FORM_SETT_NEW_SOL"/>
                                    <select name="agricultureSystSelect" class="first" disabled="disabled" style="margin-top: 10px; margin-bottom: 5px;">
                                        <option name="agricultureSystSelect" value='-1' disabled="disabled" selected><fmt:message key="FERT_FORM_SETT_NOT_SEL"/></option>
                                    </select>
                                </fieldset>

                                <fieldset id="fieldCharacteristics" disabled="disabled">
                                    <legend><fmt:message key="FERT_FORM_SETT_FIELD_PARAM"/></legend>
                                    <p><fmt:message key="FERT_FORM_SETT_FIELD"/></p>
                                    <select name="field1" disabled="disabled">
                                        <option name="field1" value='-1' disabled="disabled"  selected><fmt:message key="FERT_FORM_SETT_NOT_SEL"/></option>
                                    </select>

                                    <p class="disabled"><fmt:message key="FERT_FORM_SETT_AREA"/></p>
                                    <input type="text" name="area1" disabled="disabled" />

                                    <p class="disabled"><fmt:message key="FERT_FORM_SOIL_TYPE"/></p>
                                    <input type="text" name="typeSoil1" disabled="disabled"/>

                                    <p class="disabled"><fmt:message key="FERT_FORM_SOIL_DEPTH"/></p>
                                    <input type="text" name="depth" disabled="disabled"/>

                                    <p class="disabled"><fmt:message key="FERT_FORM_SOIL_MASS"/></p>
                                    <input type="text" name="bulk" disabled="disabled"/>

                                    <p class="disabled"><fmt:message key="FERT_FORM_CORP"/></p>
                                    <select name="corpSelect" disabled="disabled">
                                        <option name="corpSelect" value='-1' disabled="disabled"  selected><fmt:message key="FERT_FORM_SETT_NOT_SEL"/></option>
                                    </select>

                                    <p class="disabled"><fmt:message key="FERT_FORM_PLANNING_YELD"/></p>
                                    <input type="text" name="planningYield" id="planningYield" disabled="disabled"/>

                                    <p class="disabled"><fmt:message key="FERT_FORM_SOIL_PRED"/></p>
                                    <select name="predSelect" disabled="disabled">
                                        <option name="predSelect" value='-1' disabled="disabled"  selected><fmt:message key="FERT_FORM_SETT_NOT_SEL"/></option>
                                    </select>

                                    <p class="disabled"><fmt:message key="FERT_FORM_SOIL_PRED_DOZE"/></p>
                                    <input type="text" name="predDoze" id="predDoze" disabled="disabled"/>

                                    <p class="disabled"><fmt:message key="FERT_FORM_SOIL_PRED_YIELD"/></p>
                                    <input type="text" name="predYield" id="predYield" disabled="disabled"/>

                                    <p class="disabled"><fmt:message key="FERT_FORM_SYS_FERT"/></p>
                                    <select name="systFert" disabled="disabled">
                                        <option name="systFert" value='-1' disabled="disabled"  selected><fmt:message key="FERT_FORM_SETT_NOT_SEL"/></option>
                                    </select>
                                </fieldset>

                                <fieldset id="chem_composition_data" disabled="disabled">
                                    <legend><fmt:message key="FERT_FORM_SETT_CHEM_COMP_DATA"/></legend>
                                    <input type="radio" name="chemCompDataRB" value="true" disabled="disabled"/> <fmt:message key="FERT_FORM_CHOOSE_CHEM_COMP"/><br />

                                    <div class="comp-data-select" disabled="disabled">
                                        <p style="margin-top: 4px;"><fmt:message key="FERT_FORM_CHOOSE_DATA"/></p>
                                        <select name="chemCompDataSelect" disabled="disabled" style="font-size: 10px !important;">
                                            <option name="chemCompDataSelect" value='-1' disabled="disabled"  selected><fmt:message key="FERT_FORM_SETT_NOT_SEL"/></option>
                                        </select>
                                        <button name="uploadChemicalDataButton" disabled="disabled" class="btn btn-default btn-panel" style="margin-top: 6px;margin-bottom: 6px;">Загрузить данные</button>
                                    </div>

                                    <input type="radio" name="chemCompDataRB" value="false" disabled="disabled"/> <fmt:message key="FERT_FORM_LOAD_CHEM_COMP"/><br />

                                    <div class="comp-data-load" disabled="disabled">
                                        <ul class="pagination" style="width: 100%;">
                                            <li>
                                                <span class="panel-input-span" style="padding:4px !important;width: 100%;">
                                                    <form id="uploadChemicalDataForm" action="/chemical.do">
                                                       <input disabled="disabled" type="file" name="methods" accept=".csv"
                                                              title="Выберите файл с методами обследования" style="display:none;">
                                                       <input disabled="disabled" type="file" name="data" accept=".csv" class="btn btn-default btn-panel input-file"
                                                              style="display:none;"/>
                                                        <button disabled="disabled" style="width: 33%;" name="uploadChemicalDataFilesButton" class="btn btn-default btn-panel"><fmt:message key="FERT_FORM_SETT_BUT_LOAD_DATA"/></button>
                                                    </form>
                                                </span>
                                            </li>
                                        </ul>
                                    </div>

                                    <p class="disabled"><fmt:message key="FERT_FORM_SOIL_HUMUS"/></p>
                                    <input type="text" name="humus" disabled="disabled"/>
                                    <p><fmt:message key="FERT_FORM_AMOUNT_PO"/></p>
                                    <input type="text" name="phosphorus" id="phosphorus" disabled="disabled" />
                                    <p><fmt:message key="FERT_FORM_AMOUNT_KO"/></p>
                                    <input type="text" name="potassium" id="potassium" disabled="disabled" />
                                </fieldset>

                                <fieldset id="coef1" disabled="disabled">
                                    <legend><fmt:message key="FERT_FORM_SETT_COEF"/></legend>
                                    <input type="radio" name="coeff1" value="true" id="coeff1" disabled="disabled"/> <fmt:message key="FERT_FORM_COEF_FIRM"/><br />
                                    <input type="radio" name="coeff1" value="false" id="coeff1" disabled="disabled"/> <fmt:message key="FERT_FORM_COEF_DEFAULT"/>
                                    <p><fmt:message key="FERT_FORM_N"/></p>
                                    <input type="text" name="nitrogen1" id="nitrogen1" disabled="disabled" />
                                    <p><fmt:message key="FERT_FORM_KO"/></p>
                                    <input type="text" name="potassium1" id="potassium1" disabled="disabled" />
                                    <p><fmt:message key="FERT_FORM_PO"/></p>
                                    <input type="text" name="phosphorus1" id="phosphorus1" disabled="disabled" />
                                </fieldset>

                                <fieldset id="coef2" disabled="disabled">
                                    <legend><fmt:message key="FERT_FORM_SETT_COEF_USING_FERT"/></legend>
                                    <input type="radio" name="coeff2" value="true" disabled="disabled"/> <fmt:message key="FERT_FORM_COEF_FIRM"/><br />
                                    <input type="radio" name="coeff2" value="false" disabled="disabled"/> <fmt:message key="FERT_FORM_COEF_DEFAULT"/>
                                    <p id="labelYear" class="disabled"><fmt:message key="FERT_FORM_SYS_FERT_YEAR"/></p>
                                    <select name="yearOfUsing" disabled="disabled">
                                        <option name="yearOfUsing" value='-1' disabled="disabled"  selected><fmt:message key="FERT_FORM_SETT_NOT_SEL"/></option>
                                        <option name="yearOfUsing" value='1'>1 год</option>
                                        <option name="yearOfUsing" value='2'>2 год</option>
                                        <option name="yearOfUsing" value='3'>3 год</option>
                                    </select>
                                    <p><fmt:message key="FERT_FORM_N"/></p>
                                    <input type="text" name="nitrogen2" id="nitrogen2" disabled="disabled" />
                                    <p><fmt:message key="FERT_FORM_KO"/></p>
                                    <input type="text" name="potassium2" id="potassium2" disabled="disabled" />
                                    <p><fmt:message key="FERT_FORM_PO"/></p>
                                    <input type="text" name="phosphorus2" id="phosphorus2" disabled="disabled" />
                                </fieldset>
                                <fieldset id="coef3" disabled="disabled" class="first">
                                    <legend><fmt:message key="FERT_FORM_SETT_COEF_USING"/></legend>
                                    <input type="radio" name="coeff3" value="true" disabled="disabled" checked/> <fmt:message key="FERT_FORM_COEF_FIRM"/><br />
                                    <input type="radio" name="coeff3" value="false" disabled="disabled"/> <fmt:message key="FERT_FORM_COEF_DEFAULT"/>
                                    <p><fmt:message key="FERT_FORM_N"/></p>
                                    <input type="text" name="nitrogen3" id="nitrogen3" disabled="disabled" />
                                    <p><fmt:message key="FERT_FORM_KO"/></p>
                                    <input type="text" name="potassium3" id="potassium3" disabled="disabled" />
                                    <p><fmt:message key="FERT_FORM_PO"/></p>
                                    <input type="text" name="phosphorus3" id="phosphorus3" disabled="disabled" />
                                </fieldset>
                                <div class="form-buttons">
                                    <input type="button" value="Тест" name="test_button"/>
                                    <input type="button" value="<fmt:message key="PL_FORM_SETT_BUTT_CALC"/>" name="calc1" />
                                    <input type="reset" value="<fmt:message key="PL_FORM_SETT_BUTT_CLEAR"/>" name="clearAll1" disabled="disabled" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- КНОПКА УПРАВЛЕНИЯ ПАНЕЛЬЮ НАСТРОЕК-->
                <div id="tabbb">
                    <i class="fa fa-caret-square-o-left" style="margin: 0px 0px 0px -2px; " title="<fmt:message key="FERT_FORM_SETT_BUTT_COLL"/>"></i>
                </div>

                <!-- КАРТА-->
                <div id="map"></div>

            </div><!-- end ПАНЕЛЬ НАСТРОЙКИ ПАРАМЕТРОВ -->
        </div><!-- end ПАНЕЛИ-->

        <br>
        <!-- ТАБЛИЦА РЕЗУЛЬТАТОВ -->
        <div class="tab-content">
            <div id="tablesResult" class="tab-pane fade in active">
                <div class="container-fluid container-fluid-2">
                    <div id="tools-1">
                        <div class="col-md-12" style="padding:0px;" id="table-result-1">
                            <%@include file="solution_fertilizers_table_results.jsp"%>
                            <%@include file="solution_fertilizers_table_results_hardcode.jsp"%>
                        </div>
                    </div>
                </div>
            </div>
        </div> <!-- end ТАБЛИЦА РЕЗУЛЬТАТОВ -->
        <!-- end ТАБЛИЦА РЕЗУЛЬТАТОВ -->

    </div> <!-- end #page-content-wrapper -->
</div> <!-- end содержание-->

<!-- Footer -->
<%@include file="../footer.jsp"%>

<!-- jQuery
<script src="js/jquery.js"></script>-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script>
<!--<script src="../js/fancytree/jquery-ui.custom.js" type="text/javascript"></script>-->
<script src="../js/fancytree/jquery.fancytree.js" type="text/javascript"></script>
<script src="../js/fancytree/jquery.fancytree.glyph.js" type="text/javascript"></script>

<script src="/maps/js/jquery.ajax-file-uploader.js"></script>
<script src="/solution_fertilizers/js/scriptsAjaxChemicalSurvey.js"></script>

<!-- Карта -->
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyDu0iNuEqzwpO9Tg6Fd9hpoQUXkNpR0msw&libraries=geometry" ></script>
<script src="/getListTypeSoilJs.do"></script>
<script src="/solution_fertilizers/js/map/load.js"></script>
<script src="/solution_fertilizers/js/map/drawFields.js"></script>
<script src="/solution_fertilizers/js/map/draw-PFSM1.js"></script>
<script src="/solution_fertilizers/js/map/draw-PFSM1-create.js"></script>
<script src="/solution_fertilizers/js/map/script-mode1-legend.js"></script>
<script src="/solution_fertilizers/js/map/script-mode1-checked.js"></script>
<script src="/solution_fertilizers/js/map/scripts-ajax-map.js"></script>

<!--Управление содержимым левого меню -->
<script type="text/javascript" src="../js/scripts-menu.js"> </script>
<!-- Управление содержимым панели настройки параметров-->
<script type="text/javascript" src="/solution_fertilizers/js/script-solution-fertilizers-mode.js"> </script>
<!-- Сворачивание/разворачивание панели настройки параметров-->
<script type="text/javascript" src="/solution_fertilizers/js/scripts-panel-parameters.js"></script>
</body>
</html>
