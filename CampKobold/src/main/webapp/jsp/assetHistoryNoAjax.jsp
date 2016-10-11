<%-- 
    Document   : assetHistoryNoAjax
    Created on : May 25, 2016, 11:27:20 AM
    Author     : Brandon
--%>
<%-- 
    Document   : Assets
    Created on : May 10, 2016, 4:37:36 PM
    Author     : Brandon
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Kobold Camp Asset Management</title>

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/trees.png">

    </head>
    <body>
        <div class="container">
            <div class="content">
                <img src="${pageContext.request.contextPath}/img/trees.png" 
                     alt="tree_logo" 
                     class="trees"
                     align="left">
                <h2>Kobold Camp</h2>
            </div>
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <div class="navbar-brand">Equipment Rental</div>
                    </div>

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">                   
                            <!--render navbar links based on roles-->
                            <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                            <li><a href="${pageContext.request.contextPath}/rentals">Rentals</a></li>
                                <sec:authorize access="hasRole('ROLE_EMPLOYEE')">
                                <li class="active"><a href="${pageContext.request.contextPath}/assets">Assets</a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_EMPLOYEE')">
                                <li><a href="${pageContext.request.contextPath}/members">Members</a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <li><a href="${pageContext.request.contextPath}/admin">Admin</a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_USER')">
                                <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
                                </sec:authorize>
                        </ul>
                        <div class="signout navbar-form navbar-right">
                            <button class="btn principal" style="pointer-events: none; background-color: lightslategray; color: white">
                                <sec:authentication property="principal.username"/>
                            </button>
                            <a href="${pageContext.request.contextPath}/j_spring_security_logout"> 
                                <button type="submit" id="logOut" class="btn btn-danger gradient">Log Out</button>
                            </a>
                        </div>
                    </div><!-- /.navbar-collapse -->
                </div><!-- /.container-fluid -->
            </nav>
        </div>

        <div class="container">
            <h4>Kobold Camp Assets</h4> 
            <div class="row">
                <div class="col-sm-1"></div>
                <div class="col-sm-10 pad">
                    <table class="table table-responsive table-condensed">
                        <tr>
                            <th width="5%">ID</th>
                            <th width="5%">Category</th>
                            <th width="5%">Description</th>
                            <th width="5%">Current Status</th>
                        </tr>
                        <tbody id="historyRows1">
                            <c:if test="${not empty(asset)}">
                                <tr> 
                                    <td>${asset.asset.assetId}</td>
                                    <td>${asset.asset.category.name}</td>
                                    <td>${asset.asset.description}</td>
                                    <td>${asset.status.status}</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
                <div class="col-sm-1"></div>
            </div>
            <h4>Rental History</h4> 
            <div class="row">

                <div class="col-sm-12 pad">
                    <table class="table table-responsive table-condensed table-striped">
                        <tr>
                            <th width="10%">Date</th>
                            <th width="10%">Employee</th>
                            <th width="10%">Status</th>
                            <th width="10%">Member</th>
                            <th width="10%">Notes</th>
                        </tr>
                        <tbody id="historyRows2">
                            <c:forEach var="rec" items="${records}">
                                <tr> 
                                    <td>${rec.recordDate}</td>
                                    <td>${rec.employee.firstName} ${rec.employee.lastName}</td>
                                    <td>${rec.status.status}</td>
                                    <td>${rec.member.firstName} ${rec.member.lastName}</td>
                                    <td>${rec.assetNote}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <jsp:include page="footer.jsp"/>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/assetRecord.js"></script>
    </body>
</html>
