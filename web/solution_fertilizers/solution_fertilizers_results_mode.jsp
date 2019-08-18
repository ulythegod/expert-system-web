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
    <title>Расчет норм удобрения для поля - Просмотр результатов</title>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="../css/simple-sidebar.css" rel="stylesheet">
    <link href="../css/default_v4.css" rel="stylesheet">
    <link href="../css/general-styles.css" rel="stylesheet">
    <link href="/solution_fertilizers/css/proect-page-styles.css" rel="stylesheet">
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
        <c:if test="${typeResult == 1}">
            <%@include file="/solution_fertilizers/solution_fertilizers_left_menu.jsp"%>
        </c:if>
        <c:if test="${typeResult == 2}">
            <%@include file="/solution_fertilizers/solution_fertilizers_left_menu_blocked.jsp"%>
        </c:if>

        <!-- Page Content -->
        <div id="page-content-wrapper">
            <!-- Верхнее меню -->
            <%@include file="../mainmenu.jsp"%>

            <!-- ПАНЕЛИ -->
            <div class="row" style="margin: 0; ">
                <!-- ПАНЕЛЬ ПРОСМОТРА РЕЗУЛЬТАТОВ -->
                <div class="col-md-12" id="sidebar-panel-results" >
                    <div id="panel-res"  class="panel-results open">
                        <div class="content" >
                            <div class="panel-results-title"><h3><fmt:message key="FERT_FORM_PANEL_RESULTS_TITLE" /> </h3> </div>
                            <div class="panel-results-with-scroll">
                                <form id="results1" action="" >
                                    <table class="table table-bordered" id="results-tests1">
                                        <thead>
                                        <tr>
                                            <th rowspan="2"></th>
                                            <th rowspan="2"><fmt:message key="FERT_FORM_PANEL_RESULTS_NUMBER" /></th>
                                            <th rowspan="2"><fmt:message key="FERT_FORM_PANEL_RESULTS_NAME" /></th>
                                            <th rowspan="2"><fmt:message key="FERT_FORM_PANEL_RESULTS_DATE" /></th>
                                            <th rowspan="2"><fmt:message key="FERT_FORM_PANEL_RESULTS_FIELD" /></th>
                                            <th colspan="3"><fmt:message key="FERT_FORM_PANEL_RESULTS_DOZE" /></th>
                                        </tr>
                                        <tr>
                                            <th><fmt:message key="FERT_FORM_PANEL_RESULTS_N" /></th>
                                            <th><fmt:message key="FERT_FORM_PANEL_RESULTS_P" /></th>
                                            <th><fmt:message key="FERT_FORM_PANEL_RESULTS_K" /></th>
                                        </tr>
                                        </thead>
                                        <c:if test="${results != null}">
                                        <tbody>
                                        <c:forEach items="${results}" var="res" varStatus="n">
                                            <tr>
                                                <th><input type="checkbox" name="checkResult" id="${res.resultsFertilizersId}" value="${res.resultsFertilizersId}" /></th>
                                                <th>${n.count}</th>
                                                <th><a href="#" onclick="clickNameResult(${res.resultsFertilizersId})" >${res.resultsFertilizersName}</a></th>
                                                <th>
                                                    ${res.date}
                                                </th>
                                                <th><fmt:message key="FERT_FORM_SETT_FIELD_NUMBER" /> ${res.agricultureField.fieldNumber}</th>
                                                <th>
                                                    <c:if test="${res.additional_N > 0}">
                                                        <fmt:message key="FERT_RES_SUMMARY" />
                                                    </c:if>
                                                    <c:if test="${res.additional_N < 0}">
                                                        ${res.need_in_N}
                                                    </c:if>
                                                </th>
                                                <th>
                                                    <c:if test="${res.additional_Ph > 0}">
                                                        <fmt:message key="FERT_RES_SUMMARY" />
                                                    </c:if>
                                                    <c:if test="${res.additional_Ph < 0}">
                                                        ${res.need_in_Ph}
                                                    </c:if>
                                                </th>
                                                <th>
                                                    <c:if test="${res.additional_P > 0}">
                                                        <fmt:message key="FERT_RES_SUMMARY" />
                                                    </c:if>
                                                    <c:if test="${res.additional_P < 0}">
                                                        ${res.need_in_P}
                                                    </c:if>
                                                </th>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        </c:if>
                                    </table>
                                    <div class="control">
                                        <input type="checkbox" name="checkUncheckAll" id="checkUncheckAll" />
                                        <img src="../images/minus1.png" id="checkbox-minus" style="display: none;" alt="-"/>
                                        <span><fmt:message key="FERT_FORM_PANEL_RESULTS_SELECT" /></span>
                                        <button id="delete" type="submit"><img src="../images/delete.png" alt="Х"/> <fmt:message key="FERT_FORM_PANEL_RESULTS_DELETE" /></button>
                                        <br />
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- КНОПКА УПРАВЛЕНИЯ ПАНЕЛЬЮ-->
                    <div id="tabbb">
                        <i class="fa fa-caret-square-o-left" style="margin: 0px 0px 0px -2px; " title="<fmt:message key="PL_FORM_PANEL_RESULTS_BUTTON" />"></i>
                    </div>

                    <!-- КАРТА-->
                    <div id="map"> </div>
                </div><!-- end ПАНЕЛЬ ПРОСМОТРА РЕЗУЛЬТАТОВ  -->
            </div><!-- end ПАНЕЛИ-->

            <br>
            <!-- ТАБЛИЦА РЕЗУЛЬТАТОВ -->
            <div class="tab-content">
                <div id="tablesResult" class="tab-pane fade in active">
                    <div class="container-fluid container-fluid-2">
                        <div id="tools-1">
                            <div class="col-md-12" style="padding:0px;" id="table-result-1">
                                <%@include file="solution_fertilizers_table_all_results.jsp"%>
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="../js/fancytree/jquery-ui.custom.js" type="text/javascript"></script>
    <script src="../js/fancytree/jquery.fancytree.js" type="text/javascript"></script>
    <script src="../js/fancytree/jquery.fancytree.glyph.js" type="text/javascript"></script>
    <script type="text/javascript" src="/solution_fertilizers/js/jquery.dataTables.min.js"></script>

    <!-- Карта -->
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyDu0iNuEqzwpO9Tg6Fd9hpoQUXkNpR0msw&libraries=geometry" ></script>
    <script src="/getListTypeSoilJs.do"></script>
    <script src="/solution_fertilizers/js/map/load.js"></script>
    <script src="/solution_fertilizers/js/map/drawFields.js"></script>
    <script src="/solution_fertilizers/js/map/draw-PFSM1.js"></script>
    <script src="/solution_fertilizers/js/map/draw-PFSM1-create.js"></script>
    <script src="/solution_fertilizers/js/map/scripts-ajax-map.js"></script>

    <!--Управление содержимым левого меню -->
    <script type="text/javascript" src="../js/scripts-menu.js"> </script>

    <script type="text/javascript" src="/solution_fertilizers/js/script-solution-fertilizers-all-results.js"></script>
    <!--Управление содержимым панели результатов-->
    <script type="text/javascript" src="/solution_fertilizers/js/scripts-panel-result.js"></script>
</body>
</html>