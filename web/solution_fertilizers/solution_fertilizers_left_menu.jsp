<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!-- Sidebar -->
<div id="sidebar-wrapper" >
    <ul class="sidebar-nav">
        <li>
            <a href="#menu-toggle" id="menu-toggle">
                <i class="menu-icon-arrow fa fa-angle-double-left"></i>
            </a>
        </li>
        <li>
            <a class="toggle-element" data-toggle="collapse" href="#collapseProcessField" aria-expanded="false" aria-controls="collapseProcessField">
                <i class="menu-icon fa fa-sliders" title="<fmt:message key="LEFT_MENU_COUNT_FERT_FIELD"/>"></i>  <!-- fa-map-signs-->
                <span class="full-width">&nbsp;<fmt:message key="LEFT_MENU_COUNT_FERT_FIELD"/></span></a>
            <div class="collapse" id="collapseProcessField">
                <div class = "tree">
                    <ul id="treeData" style="display: none;">
                        <li data-expanded="false"><a href="/solution_fertilizers_setting_mode"><fmt:message key="LEFT_MENU_CREATE_SOLUTION_FERT"/></a></li>
                        <%--<li data-expanded="false"><a href="/solution_fertilizers_corrections_mode"><fmt:message key="LEFT_MENU_RESULTS_CORRECTION"/></a></li>--%>
                        <li data-expanded="false"><a href="/solution_fertilizers_results_mode?typeResult=1"><fmt:message key="LEFT_MENU_SHOW_RESULTS_FERT"/></a></li>
                    </ul>
                </div>
            </div>
        </li>
        <%--<li>--%>
            <%--<a class="toggle-element" data-toggle="collapse" href="#collapseProcessRotation" aria-expanded="false" aria-controls="collapseProcessRotation">--%>
                <%--<i class="menu-icon fa fa-cogs" title="<fmt:message key="LEFT_MENU_COUNT_FERT_ROTATION"/>"></i>--%>
                <%--<span class="full-width" href="#">&nbsp;<fmt:message key="LEFT_MENU_COUNT_FERT_ROTATION"/></span></a>--%>
            <%--<div class="collapse" id="collapseProcessRotation">--%>
                <%--<div class = "tree">--%>
                    <%--<ul id="treeData" style="display: none;">--%>
                        <%--<li data-expanded="false"><a href=""><fmt:message key="LEFT_MENU_PROCESS_ROTATION_ONE_FIELD"/></a>--%>
                            <%--<ul>--%>
                                <%--<li data-level="1"><a href="#"><fmt:message key="LEFT_MENU_CREATE_SOLUTION"/></a></li>--%>
                                <%--<li data-level="1"><a href="#"><fmt:message key="LEFT_MENU_EDIT_SOLUTION"/></a></li>--%>
                            <%--</ul>--%>
                        <%--<li data-expanded="false"><a href=""><fmt:message key="LEFT_MENU_PROCESS_ROTATION_ALL_FIELDS"/></a>--%>
                            <%--<ul>--%>
                                <%--<li data-level="1"><a href="#"><fmt:message key="LEFT_MENU_CREATE_SOLUTION"/></a></li>--%>
                                <%--<li data-level="1"><a href="#"><fmt:message key="LEFT_MENU_EDIT_SOLUTION"/></a></li>--%>
                            <%--</ul>--%>
                        <%--<li data-expanded="false"><a href=#"><fmt:message key="LEFT_MENU_SHOW_RESULTS"/></a></li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <li>
            <a class="toggle-element" data-toggle="collapse" href="#collapseHelp" aria-expanded="false" aria-controls="collapseHelp">
                <i class="menu-icon fa fa-book" title=<fmt:message key="LEFT_MENU_HELP"/>></i>
                <span class="full-width">&nbsp;<fmt:message key="LEFT_MENU_HELP"/></span></a>
            <div class="collapse" id="collapseHelp">
                <div class = "tree">
                    <ul id="treeData" style="display: none;">
                        <li data-expanded="false"><a href=#"><fmt:message key="LEFT_MENU_HELP_MAIN"/></a> </li>
                        <li data-expanded="false"> <a href="#"><fmt:message key="LEFT_MENU_PROCESS_FIELD"/></a>
                            <ul>
                                <li data-expanded="false"><a href="#"><fmt:message key="LEFT_MENU_EXAMPLE1"/></a></li>
                            </ul>
                        </li>
                        <%--<li data-expanded="false"> <a href="#"><fmt:message key="LEFT_MENU_PROCESS_ROTATION"/></a>--%>
                            <%--<ul>--%>
                                <%--<li data-expanded="false"><a href="#"><fmt:message key="LEFT_MENU_EXAMPLE1"/></a></li>--%>
                                <%--<li data-expanded="false"><a href="#"><fmt:message key="LEFT_MENU_EXAMPLE2"/></a></li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>

                    </ul>
                </div>

            </div>
        </li>
    </ul>
</div>
<!-- /#sidebar-wrapper -->
