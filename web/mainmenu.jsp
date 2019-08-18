<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<div id="main-menu">
    <nav class="navbar navbar-default ">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>

            <div class="collapse navbar-collapse" id="bs-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="#" style="padding-left: 8px;"><fmt:message key="MAIN_MENU_LAND_FARMING"/> <span class="sr-only">(current)</span></a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message key="MAIN_MENU_PLANNING"/> <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#"><fmt:message key="MAIN_MENU_FORMATION_PACKEGES"/></a></li>
                            <li><a href="#"><fmt:message key="MAIN_MENU_PLANNING_FARM_SYST"/></a></li>
                            <li><a href="/solution_fertilizers/solution_fertilizers_main.jsp"><fmt:message key="MAIN_MENU_SOLUTION_FERTILIZERS"/></a></li>
                            <li><a href="/solution_fertilizers/solution_fertilizers_main_blocked.jsp"><fmt:message key="MAIN_MENU_SOLUTION_FERTILIZERS_BLOCKED"/></a></li>
                        </ul>
                    </li>
                    <li><a href="#" style="padding-left: 8px;"><fmt:message key="MAIN_MENU_INTEGRATION1C"/><span class="sr-only">(current)</span></a></li>
                    <li><a href="#" style="padding-left: 8px;"><fmt:message key="MAIN_MENU_REFERENCES"/><span class="sr-only">(current)</span></a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message key="MAIN_MENU_SETTINGS"/> <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#"><fmt:message key="MAIN_MENU_ELECTR_LAND_BASE"/></a></li>
                            <li><a href="#"><fmt:message key="MAIN_MENU_SYST_INTERFACE"/></a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#"><fmt:message key="MAIN_MENU_PERSONAL_ACCOUNT"/></a></li>
                        </ul>
                    </li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true" style="padding-left:30px;">
                            <i class="fa fa-language"></i>
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/es_main.jsp?locale=ru">Русский</a></li>
                            <li><a href="/es_main.jsp?locale=en">English</a></li>
                            <li><a href="#">Deutsch</a></li>
                            <li><a href="#">Français</a></li>
                            <li><a href="#">Italiano</a></li>
                            <li><a href="#">Español</a></li>
                            <li><a href="#">Português</a></li>
                            <li><a href="#">Türkçe</a></li>
                            <li><a href="#">China</a></li>
                            <li><a href="#">Japan</a></li>
                        </ul>
                    </li>
                </ul>

                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="<fmt:message key="MAIN_MENU_SEARCH"/>">
                    </div>
                    <button type="submit" class="btn btn-default"><fmt:message key="MAIN_MENU_OK"/></button>
                </form>
            </div>
        </div>
    </nav>
</div>