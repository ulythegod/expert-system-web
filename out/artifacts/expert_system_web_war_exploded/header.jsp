<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header col-lg-12">
            <div class="col-md-10" style="padding-left: 0px; padding-right: 0px;"><a class="navbar-brand" href="/es_main.jsp" title="<fmt:message key="HEADER_REF"/>" ><fmt:message key="HEADER_TITLE"/></a></div>
            <c:if test="${authUser != null}">
                <div class="col-md-2" style="padding-left: 0px; padding-right: 0px;"><span class="navbar-user">${authUser.name}</span></div>
                <div class="col-md-2" style="padding-left: 0px; padding-right: 0px;"><a class="navbar-exit" href="/login.do?logout=true"><fmt:message key="HEADER_EXIT"/></a>
                </div>
            </c:if>

        </div>
    </div>
</nav>