<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.locale}" scope="session"/>
<fmt:setBundle basename="Resource" />
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html lang="ru">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>Авторизация</title>

  <link href="css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom CSS -->
  <link href="css/simple-sidebar.css" rel="stylesheet">
  <link href="css/default_v4.css" rel="stylesheet">
  <link href="css/general-styles.css" rel="stylesheet">
  <link href="/auth/authorisation-style.css" rel="stylesheet">

  <link rel="stylesheet" href="css/font-awesome.min.css">
  <link href="css/fancytree/ui.fancytree.css" rel="stylesheet" type="text/css">
  <link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>

  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>

<body>
  <%@include file="../header.jsp" %>
  <br>

  <div class="content">

    <h3 class="page-header">
      <fmt:message key="AUTH_MAIN"/>
    </h3>

    <c:if test="${not empty loginErrors}">
      <div class="alert alert-danger alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <c:forEach var="loginError" items="${loginErrors}">
          <p>${loginError}</p>
        </c:forEach>
      </div>
    </c:if>

    <form action="" method="post" id="loginForm" class="form-horizontal">
      <div class="form-group">
        <label for="loginField" class="col-sm-3 control-label"><fmt:message key="AUTH_LOGIN"/></label>
        <div class="col-sm-9">
          <input type="text" name="login" id="loginField" class="form-control">
        </div>
      </div>
      <div class="form-group">
        <label for="passwordField" class="col-sm-3 control-label"><fmt:message key="AUTH_PASSWORD"/></label>
        <div class="col-sm-9">
          <input type="password" name="password" id="passwordField" class="form-control">
        </div>
      </div>
      <div class="form-group">
        <div class="col-sm-offset-3 col-sm-9">
          <button class="btn btn-default"><fmt:message key="AUTH_ENTER"/></button>
        </div>
      </div>
    </form>

  </div>

  <%@ include file="../footer.jsp" %>

  <!-- jQuery -->
  <script src="/js/jquery.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="/js/bootstrap.min.js"></script>
  <script src="/js/fancytree/jquery-ui.custom.js" type="text/javascript"></script>
  <script src="/js/fancytree/jquery.fancytree.js" type="text/javascript"></script>
  <script src="/js/fancytree/jquery.fancytree.glyph.js" type="text/javascript"></script>
</body>

</html>



