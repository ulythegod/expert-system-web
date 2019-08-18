<%--
  Created by IntelliJ IDEA.
  User: julia
  Date: 22.05.2019
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty param.locale}">
    <fmt:setLocale value="${param.locale}" scope="session"/>
</c:if>
<fmt:setBundle basename="Resource" />
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Расчет доз удобрения для сельскохозяйственных культур - Главная</title>
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
    <%@include file="/solution_fertilizers/solution_fertilizers_left_menu.jsp"%>

    <!-- Page Content -->
    <div id="page-content-wrapper">
        <!-- Верхнее меню -->
        <%@include file="../mainmenu.jsp"%>

        <br><fmt:message key="FERT_TEMPORARY_DINSCRIPTION"/>

    </div> <!-- end #page-content-wrapper -->

</div> <!-- end содержание-->

<!-- Footer -->
<%@include file="../footer.jsp"%>

<!-- jQuery -->
<script src="../js/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/fancytree/jquery-ui.custom.js" type="text/javascript"></script>
<script src="../js/fancytree/jquery.fancytree.js" type="text/javascript"></script>
<script src="../js/fancytree/jquery.fancytree.glyph.js" type="text/javascript"></script>


<!--Управление содержимым левого меню -->
<script type="text/javascript" src="../js/scripts-menu.js"> </script>

<script type="text/javascript">
    /* $("#checkUser").on("click", function(){
       var login = $("#panel-auth input[name=login]").attr("value");
       var pass = $("#panel-auth input[name=password]").attr("value");
       document.location.href = "/auth";
       console.log(login + " " + pass);
       /*document.location.href = "/auth?login=" + login + "&password=" + pass;*/

</script>
</body>
</html>
