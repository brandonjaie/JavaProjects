<%-- 
    Document   : admin
    Created on : May 10, 2016, 3:46:27 PM
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
                                <li><a href="${pageContext.request.contextPath}/assets">Assets</a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_EMPLOYEE')">
                                <li><a href="${pageContext.request.contextPath}/members">Members</a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <li class="active"><a href="${pageContext.request.contextPath}/admin">Admin</a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_USER')">
                                <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
                                </sec:authorize>
                        </ul>
                        <sec:authorize access="isAuthenticated()">
                            <div class="signout navbar-form navbar-right">
                                <button class="btn principal">
                                    <sec:authentication property="principal.username"/>
                                </button>
                                <a href="${pageContext.request.contextPath}/j_spring_security_logout"> 
                                    <button type="submit" id="logOut" class="btn btn-danger gradient">Log Out</button>
                                </a>
                            </div>
                        </sec:authorize>
                    </div><!-- /.navbar-collapse -->
                </div><!-- /.container-fluid -->
            </nav>
        </div>
        <div class="container-fluid body">
            <h4>Kobold Camp Admin</h4>
            <div class="row">
                <div class="col-md-6">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <div class="col-sm-4">
                                <label></label>
                                <input type="text" id="search-admin-member-id" class="form-control" placeholder="Member Id">
                            </div>
                            <div class="col-sm-4">
                                <label></label>
                                <input type="text" id="search-admin-member-lastname" class="form-control" placeholder="Last Name">
                            </div>
                            <div class="col-sm-4"> 
                                <br>
                                <button type="submit" id="search-admin-button" class="btn btn-primary">Search</button>
                            </div>
                        </div>
                    </form>
                    <table class="table table-responsive table-condensed table-striped">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Type</th>
                            <th class="tableHeading">Remove</th>
                            <th class="tableHeading">Password</th>
                        </tr>
                        <tbody id="adminRows">
                            <c:forEach var="user" items="${users}">
                                <tr> 
                                    <td>${user.userId}</td>
                                    <td>${user.firstName} ${user.lastName}</td>
                                    <td>${user.userProfile.memberId}</td>
                                    <s:url value="deleteUserNoAjax" var="delete_url">
                                        <s:param name="userId" value="${user.userId}"/>
                                    </s:url>

                                    <s:url value="resetPasswordNoAjax" var="resetPassword_url">
                                        <s:param name="userId" value="${user.userId}"/>
                                    </s:url>
                                    <td><a href="${delete_url}">Delete</a></td>
                                    <td><a href="${resetPassword_url}">Reset Password</a></td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                </div>

                <div class="col-md-6">
                    <form class="form-horizontal">
                        <br>
                        <h4>Add Equipment</h4>
                        <div class="form-group">
                            <label for="add-category" class="col-md-3 control-label">Category:</label>
                            <div class="col-md-5">
                                <select class="form-control" id="add-category">
                                    <option value="" selected>Category</option>
                                    <option value="1">Backpacks</option>
                                    <option value="2">Sleeping Bags</option>
                                    <option value="3">Camping Stoves</option>
                                    <option value="4">Paddling Gear</option>
                                    <option value="5">Tents</option>
                                </select> 
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-brand" class="col-md-3 control-label">Brand:</label>
                            <div class="col-md-7">
                                <input type="text" class="form-control" id="add-brand" placeholder="Brand">
                            </div>

                        </div>
                        <div class="form-group">
                            <label for="add-description" class="col-md-3 control-label">Description:</label>
                            <div class="col-md-7">
                                <textarea type="text" class="form-control" id="add-description" placeholder="Description"></textarea>
                            </div>
                        </div>

                        <div class="col-md-offset-2 col-md-10">
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-3 col-md-8">
                                <button type="submit" class="btn btn-primary" id="add-equipment-button">Add Equipment</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-3 col-md-8 error" id="validationErrors">
                            </div>
                        </div>
                    </form>

                </div>

            </div>
        </div>
        <hr>
        <div class="container-fluid body" id="assetRecordsDiv">
            <h4>Kobold Camp Records</h4> 
            <div class="row">
                <div class="col-md-12">

                    <form class="form-horizontal">
                        <div class="form-group">
                            <div class="col-md-2">
                                <label></label>
                                <input id="search-date" type="text" class="form-control" placeholder="Date YYYY-MM-DD">
                            </div>
                            <div class="col-md-2">
                                <label></label>
                                <select id="search-employee-id" class="form-control">
                                    <option value="" selected="selected">Employee</option> 
                                    <c:forEach items="${employees}" var="employee">          
                                        <option value="${employee.userId}">
                                            ${employee.userId} - ${employee.firstName} ${employee.lastName}</option>
                                        </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <label></label>
                                <select id="search-members-id" class="form-control">
                                    <option value="" selected="selected">Member</option> 
                                    <c:forEach items="${members}" var="member">          
                                        <option value="${member.userId}">
                                            ${member.userId} - ${member.firstName} ${member.lastName}</option>
                                        </c:forEach>
                                </select>
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
                                <br>
                                <button type="submit" id="search-associates-button" class="btn btn-primary">Search</button>
                            </div>
                            <div class="col-md-2">
                                <br>
                                <button type="submit" id="search-todays-records" class="btn btn-info">Today's Records</button>
                            </div>
                            <!--                            <div class="col-md-2">
                                                        </div>-->
                        </div>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-responsive table-condensed table-striped">
                        <tr>
                            <th>Date</th>
                            <th class="tableHeading">Associate</th>
                            <th class="tableHeading">Status</th>
                            <th class="tableHeading">Asset Description</th>
                        </tr>
                        <tbody id="todaysRecords">

                        </tbody>
                        <tbody id="records">

                        </tbody>
                    </table>
                </div>
            </div>
            <jsp:include page="footer.jsp"/>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/asset.js"></script>
        <script src="${pageContext.request.contextPath}/js/admin.js"></script>
    </body>
</html>
