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
    <title>Расчет норм удобрения для поля - Корректировка результатов</title>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="../css/simple-sidebar.css" rel="stylesheet">
    <link href="../css/default_v4.css" rel="stylesheet">
    <link href="../css/general-styles.css" rel="stylesheet">
    <link href="/planning_formation/css/proect-page-styles.css" rel="stylesheet">
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
                            <div class="panel-setting-parameters-title"><h3><fmt:message key="FERT_FORM_CORR_TITLE"/> </h3> </div>
                            <div class="panel-setting-parameters-with-scroll">
                                <form id="corrections" action="" method="post">

                                    <fieldset id="resultSelect">
                                        <legend><fmt:message key="FERT_FORM_CORR_FERT_SEL"/></legend>
                                        <p><fmt:message key="FERT_FORM_CORR_FERT"/> </p>
                                        <select name="fertResultSelect">
                                            <option name="fertResultSelect" value='-1' selected><fmt:message key="FERT_FORM_CORR_NOT_SEL"/></option>
                                        </select>
                                        <input type="button" name="selectFertResult" value="<fmt:message key="FERT_FORM_SETT_BUT_SEL"/>" style="float:right; width:17%; height:25px;" disabled="disabled"/>
                                    </fieldset>

                                    <fieldset id="fieldCharacteristics" disabled="disabled">
                                        <legend><fmt:message key="FERT_FORM_SETT_FIELD_PARAM"/></legend>
                                        <p class="disabled"><fmt:message key="FERT_FORM_SETT_FIELD"/></p>
                                        <select name="field1" disabled="disabled">
                                            <option name="field1" value='-1' disabled="disabled"  selected><fmt:message key="FERT_FORM_SETT_NOT_SEL"/></option>
                                        </select>

                                        <p class="disabled"><fmt:message key="FERT_FORM_SETT_AREA"/></p>
                                        <input type="text" name="area1" disabled="disabled" />

                                        <p class="disabled"><fmt:message key="FERT_FORM_SOIL_TYPE"/></p>
                                        <input type="text" name="typeSoil1" disabled="disabled"/>

                                        <p class="disabled"><fmt:message key="FERT_FORM_CORP"/></p>
                                        <select name="corpSelect" disabled="disabled">
                                        </select>

                                        <p class="disabled"><fmt:message key="FERT_FORM_PLANNING_YELD"/></p>
                                        <input type="text" name="planningYield" id="planningYield1" disabled="disabled"/>

                                        <p class="disabled"><fmt:message key="FERT_FORM_SYS_FERT"/></p>
                                        <select name="systFert" disabled="disabled">
                                        </select>
                                    </fieldset>

                                    <fieldset id="newCorretion" disabled="disabled">
                                        <legend><fmt:message key="FERT_FORM_CORR_NEW"/></legend>
                                        <p class="disabled"><fmt:message key="FERT_FORM_CORR_MONTH"/> </p>
                                        <table>
                                            <tr>
                                                <td><input type="checkbox" name="month1" value="1" disabled="disabled"><fmt:message key="FERT_FORM_CORR_JANUARY"/></td>
                                                <td><input type="checkbox" name="month7" value="7" disabled="disabled"><fmt:message key="FERT_FORM_CORR_JULY"/></td>
                                            </tr>
                                            <tr>
                                                <td><input type="checkbox" name="month2" value="2" disabled="disabled"><fmt:message key="FERT_FORM_CORR_FEB"/></td>
                                                <td><input type="checkbox" name="month8" value="8" disabled="disabled"><fmt:message key="FERT_FORM_CORR_AUGUST"/></td>
                                            </tr>
                                            <tr>
                                                <td><input type="checkbox" name="month3" value="3" disabled="disabled"><fmt:message key="FERT_FORM_CORR_MARCH"/></td>
                                                <td><input type="checkbox" name="month9" value="9" disabled="disabled"><fmt:message key="FERT_FORM_CORR_SEPTEMBER"/></td>
                                            </tr>
                                            <tr>
                                                <td><input type="checkbox" name="month4" value="4" disabled="disabled"><fmt:message key="FERT_FORM_CORR_APRIL"/></td>
                                                <td><input type="checkbox" name="month10" value="10" disabled="disabled"><fmt:message key="FERT_FORM_CORR_OCTOBER"/></td>
                                            </tr>
                                            <tr>
                                                <td><input type="checkbox" name="month5" value="5" disabled="disabled"><fmt:message key="FERT_FORM_CORR_MAY"/></td>
                                                <td><input type="checkbox" name="month11" value="11" disabled="disabled"><fmt:message key="FERT_FORM_CORR_NOVEMBER"/></td>
                                            </tr>
                                            <tr>
                                                <td><input type="checkbox" name="month6" value="6" disabled="disabled"><fmt:message key="FERT_FORM_CORR_JUNE"/></td>
                                                <td><input type="checkbox" name="month12" value="12" disabled="disabled"><fmt:message key="FERT_FORM_CORR_DECEMBER"/></td>
                                            </tr>
                                        </table>
                                        <p><input type="button" name="chooseMonthButton" value="<fmt:message key="FERT_FORM_CORR_BUT_CHOOSE"/>" style="float:right; width:17%; height:25px;" disabled="disabled"></p>
                                    </fieldset>

                                    <fieldset id="characteristicsForCorrections" disabled="disabled">
                                        <legend><fmt:message key="FERT_FORM_CORR_CHAR"/></legend>
                                        <table>
                                            <tr>
                                                <td><input type="checkbox" name="wet" value="wet" disabled="disabled"><fmt:message key="FERT_FORM_CORR_WET"/></td>
                                                <td><input type="checkbox" name="temperature" value="temperature" disabled="disabled"><fmt:message key="FERT_FORM_CORR_TEMP"/></td>
                                            </tr>
                                        </table>
                                    </fieldset>

                                    <div class="form-buttons">
                                        <input type="button" value="<fmt:message key="PL_FORM_SETT_BUTT_CALC"/>" name="calc1"/>
                                        <input type="reset" value="<fmt:message key="PL_FORM_SETT_BUTT_CLEAR"/>" name="clearAll1" disabled="disabled" />
                                    </div>
                                </form>
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
    <script src="../js/fancytree/jquery-ui.custom.js" type="text/javascript"></script>
    <script src="../js/fancytree/jquery.fancytree.js" type="text/javascript"></script>
    <script src="../js/fancytree/jquery.fancytree.glyph.js" type="text/javascript"></script>

    <!-- Карта -->
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyDu0iNuEqzwpO9Tg6Fd9hpoQUXkNpR0msw&libraries=geometry" ></script>
    <script src="/getListTypeSoilJs.do"></script>
    <script src="/planning_formation/js/load.js"></script>
    <script src="/planning_formation/js/drawFieldsPFSM1.js"></script>
    <script src="/planning_formation/js/scripts-ajax-map.js"></script>

    <!--Управление содержимым левого меню -->
    <script type="text/javascript" src="../js/scripts-menu.js"> </script>
    <!-- Управление содержимым панели настройки параметров-->
    <script type="text/javascript" src="solution_fertilizers/js/script-solution-corrections.js"> </script>

    <!-- Сворачивание/разворачивание панели настройки параметров-->
    <script type="text/javascript" src="/planning_formation/js/scripts-panel-parameters.js"></script>
</body>
</html>
