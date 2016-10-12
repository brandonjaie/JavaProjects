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
        
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/trees.png">
    </head>
    <body>
        <div class="container-fluid">
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
                            <button class="btn principal">
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

        <div class="container-fluid body" id="assetRecordsDiv">
            <h4>Kobold Camp Assets</h4> 
            <div class="row">
                <div class="col-md-12">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <div class="col-md-2">
                                <label></label>
                                <input id="search-asset-id" type="text" class="form-control" placeholder="Asset ID">
                            </div>
                            <div class="col-md-2">
                                <label></label>
                                <select id="search-category" class="form-control">
                                    <option value="" selected>Category</option>
                                    <option value="1">Backpacks</option>
                                    <option value="2">Sleeping Bags</option>
                                    <option value="3">Camping Stoves</option>
                                    <option value="4">Paddling Gear</option>
                                    <option value="5">Tents</option>
                                </select>
                            </div>
                            <div class="hidden-xs col-md-2">
                                <label></label>
                                <input id="search-description" type="text" class="form-control" placeholder="Description">
                            </div>
                            <div class="col-md-2">
                                <label></label>
                                <select id="search-status" class="form-control">
                                    <option value="" selected>Status</option>
                                    <option value="1">AVAILABLE</option>
                                    <option value="2">CHECKED OUT</option>
                                    <option value="3">BROKEN</option>
                                    <option value="4">LOST</option>
                                    <option value="5">UNDER REPAIRS</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <label></label>
                                <input id="search-member-id" type="text" class="form-control" placeholder="Last Name">
                            </div>
                            <div class="col-md-2">
                                <br>
                                <button type="submit" id="search-button" class="btn btn-primary">Search</button>
                            </div>   
                        </div>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-responsive table-condensed table-striped">
                        <tr>
                            <th>ID</th>
                            <th>Category</th>
                            <th class="hidden-xs">Brand</th>
                            <th class="hidden-xs">Description</th>
                            <th>Status</th>
                            <th>Member</th>
                            <th class="hidden-xs tableHeading">History</th>
                            <th class="tableHeading">Check In/Out</th>
                        </tr>
                        <tbody id="assetRows">

                        </tbody>
                    </table>
                </div>
            </div>
            <jsp:include page="footer.jsp"/>
        </div>
        <%@include file="editStatusModal.jsp"%>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/assetrecord.js"></script>
    </body>
</html>
