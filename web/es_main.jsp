<%--
  User: Skosyreva Maria
  Date: 07.11.2017
  Time: 16:46
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale}" scope="session"/>
<fmt:setBundle basename="Resource" />
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ЭСОЗ - Главная страница</title>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="/css/simple-sidebar.css" rel="stylesheet">
    <link href="/css/default_v4.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/font-awesome.min.css">
    <link href="/css/fancytree/ui.fancytree.css" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
</head>
<body>
<!-- Header-->
<%@include file="header.jsp"%>

<!-- содержание-->
<div id="wrapper" style="padding-top: 50px;">

    <!-- Левое меню -->
    <%@include file="es_leftmenu.jsp"%>

    <!-- Page Content -->
    <div id="page-content-wrapper">
        <!-- Верхнее меню -->
        <%@include file="mainmenu.jsp"%>

        <br><fmt:message key="ES_MAIN_TEMPORARY_INSCRIPTION"/>

    </div> <!-- end #page-content-wrapper -->

</div> <!-- end содержание-->

<!-- Footer -->
<%@include file="footer.jsp"%>



<!-- jQuery -->
<script src="../js/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/fancytree/jquery-ui.custom.js" type="text/javascript"></script>
<script src="../js/fancytree/jquery.fancytree.js" type="text/javascript"></script>
<script src="../js/fancytree/jquery.fancytree.glyph.js" type="text/javascript"></script>


<!--Управление содержимым левого меню -->
<script type="text/javascript" src="../js/scripts-menu.js"> </script>

</body>
</html>
