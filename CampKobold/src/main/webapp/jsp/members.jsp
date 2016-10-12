<%-- 
    Document   : members
    Created on : May 10, 2016, 3:45:29 PM
    Author     : Brandon
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
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
                                <li class="active"><a href="${pageContext.request.contextPath}/members">Members</a></li>
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
        <div class="container-fluid body">
            <h4>Kobold Camp Members</h4>
            <div class="row">
                <div class="col-md-6">

                    <form class="form-horizontal">
                        <div class="form-group">
                            <div class="col-sm-4">
                                <label></label>
                                <input type="text" class="form-control" id="search-userProfile-id" placeholder="Member Id">
                            </div>
                            <div class="col-sm-4">
                                <label></label>
                                <input type="text" class="form-control" id="search-userProfile-name" placeholder="Last Name">
                            </div>
                            <div class="col-sm-4">
                                <br>
                                <button type="submit" id="search-button" class="btn btn-primary">Search</button>
                            </div>
                        </div>
                    </form>
                    <table class="table table-responsive table-condensed table-striped">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                        </tr>
                        <tbody id="userProfileRows">
                            <c:forEach var="user" items="${UserUserProfiles}">
                                <tr> 
                                    <td>${user.userId}</td>
                                    <td>${user.firstName} ${user.lastName}</td>
                                    <td>${user.email}</td>
                                    <td>${user.phone}</td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>

                </div>

                <div class="col-md-6">
                    <sf:form class="form-horizontal" 
                             modelAttribute="UserUserProfile"
                             role="form" 
                             action="addUserUserProfileNoAjax" 
                             method="POST"
                             autocomplete="off">
                        <br>
                        <h4>Add Member</h4>
                        <div class="form-group">
                            <label id="username" class="col-md-3 control-label">Username:</label>
                            <div class="col-md-9">
                                <sf:input type="text" 
                                          class="form-control" 
                                          id="add-username"
                                          path="userName"
                                          name="userName"
                                          placeholder="Check Username Availability"/>
                                <sf:errors path="userName" cssClass="text-danger"></sf:errors>
                                </div>

                            </div>
                            <div class="form-group">
                                <label for="add-first-name" class="col-md-3 control-label">First Name:</label>
                                <div class="col-md-9">
                                <sf:input type="text" 
                                          class="form-control" 
                                          id="add-first-name" 
                                          path="firstName"
                                          name="firstName"
                                          placeholder="First Name"/>
                                <sf:errors path="firstName" cssClass="text-danger"></sf:errors>
                                </div>

                            </div>
                            <div class="form-group">
                                <label for="add-last-name" class="col-md-3 control-label">Last Name:</label>
                                <div class="col-md-9">
                                <sf:input type="text" 
                                          class="form-control" 
                                          id="add-last-name" 
                                          path="lastName"
                                          name="lastName"
                                          placeholder="Last Name"/>
                                <sf:errors path="lastName" cssClass="text-danger"></sf:errors>
                                </div>

                            </div>
                            <div class="form-group">
                                <label for="email" class="col-md-3 control-label">Email:</label>
                                <div class="col-md-9">
                                <sf:input type="text" 
                                          class="form-control" 
                                          id="add-email" 
                                          path="email"
                                          name="email"
                                          placeholder="Email"/>
                                <sf:errors path="email" cssClass="text-danger"></sf:errors>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="phone" class="col-md-3 control-label">Phone:</label>
                                <div class="col-md-9">
                                <sf:input type="text" 
                                          class="form-control" 
                                          id="add-phone" 
                                          path="phone"
                                          name="phone"
                                          placeholder="Phone"/>
                                <sf:errors path="phone" cssClass="text-danger"></sf:errors>
                                <sf:hidden path="userId"/>
                                <sf:hidden path="password" value="koboldcamp"/>
                                <sf:hidden path="confirmPassword"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-4"></div>
                            <div class="col-md-8">
                                <label class="checkbox-inline">
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <input type="checkbox" 
                                               id="check1" 
                                               name="isAdmin" 
                                               path="isAdmin" 
                                               value="yes"/> Admin
                                    </sec:authorize>
                                </label>
                                <label class="checkbox-inline">
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <input type="checkbox" 
                                               id="check2" 
                                               name="isEmployee" 
                                               path="isEmployee" 
                                               value="yes"/> Employee
                                    </sec:authorize>
                                </label>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-offset-3 col-md-8">
                                <button type="submit" id="add-member-button" class="btn btn-primary">Add Member</button>
                            </div>
                        </div>
                    </sf:form>
                </div>
            </div>
            <jsp:include page="footer.jsp"/>
        </div><!-- /container -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/member.js"></script>

    </body>
</html>
