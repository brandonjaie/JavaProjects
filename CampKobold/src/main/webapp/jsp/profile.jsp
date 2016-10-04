<%-- 
    Document   : profile
    Created on : May 10, 2016, 3:45:52 PM
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
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">

        <!-- SWC Icon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/trees.png">

    </head>
    <body>
        <div class="container">
            <div class="content">
                <img src="${pageContext.request.contextPath}/img/trees.png" 
                     alt="tree_logo" 
                     style="padding-right: 5px" 
                     height="30" 
                     width="30" 
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
                        <div class="navbar-brand" style="pointer-events: none; background-color: lightslategray; color: white">Equipment Rental</div>
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
                                <li><a href="${pageContext.request.contextPath}/admin">Admin</a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_USER')">
                                <li class="active"><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
                                </sec:authorize>
                        </ul>
                        <div class="signout navbar-form navbar-right">
                            <button class="btn" style="pointer-events: none; background-color: lightslategray; color: white">
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
            <h4>Kobold Camp Member Profile</h4>
            <div class="row">
                <div class="col-md-6" style="padding: 20px">
                    <!-- EDIT USER PROFILE NO AJAX -->
                    <sf:form class="form-horizontal" 
                             modelAttribute="UserUserProfile" 
                             role="form" 
                             action="editUserUserProfileNoAjax" 
                             method="POST">
                        <h4>Edit Information</h4>
                        <div class="form-group">
                            <label for="edit-first-name" class="col-sm-3 control-label">First Name:</label>
                            <div class="col-sm-7">
                                <sf:input type="text" 
                                          class="form-control" 
                                          id="edit-first-name" 
                                          path="firstName" 
                                          placeholder="First Name"/>
                                <sf:errors path="firstName" cssClass="text-danger"></sf:errors>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-last-name" class="col-sm-3 control-label">Last Name:</label>
                                <div class="col-sm-7">
                                <sf:input type="text" 
                                          class="form-control" 
                                          id="edit-last-name" 
                                          path="lastName" 
                                          placeholder="Last Name"/>
                                <sf:errors path="lastName" cssClass="text-danger"></sf:errors>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-sm-3 control-label">Email:</label>
                                <div class="col-sm-7">
                                <sf:input type="text" 
                                          class="form-control" 
                                          id="edit-email" 
                                          path="email" 
                                          placeholder="Email"/>
                                <sf:errors path="email" cssClass="text-danger"></sf:errors>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="phone" class="col-sm-3 control-label">Phone:</label>
                                <div class="col-sm-7">
                                <sf:input type="text" 
                                          class="form-control" 
                                          id="edit-phone" 
                                          path="phone" 
                                          placeholder="Phone"/>
                                <sf:errors path="phone" cssClass="text-danger"></sf:errors>
                                <%--<sf:hidden id="edit-user-id" path="userId"/>--%>
                            </div>
                        </div>
                        <div style="padding: 10px">
                            <h4>Update Password</h4>
                            <div class="form-group">
                                <label for="new-password" class="col-sm-4 control-label">New Password:</label>
                                <div class="col-sm-6">
                                    <sf:input type="password" 
                                              class="form-control passClass" 
                                              id="edit-new-password" 
                                              path="password" 
                                              placeholder="Password"/>
                                    <sf:errors path="password" cssClass="text-danger"></sf:errors>
                                    </div>
<!--                                    <div id="passCorrect"><img src="${pageContext.request.contextPath}/img/yes.png" 
                                                           height="15" width="15"></div>
                                <div id="passIncorrect"><img src="${pageContext.request.contextPath}/img/no3.png" 
                                                             height="15" width="15"></div>-->
                            </div>

                            <div class="form-group">
                                <label for="repeat-password" class="col-sm-4 control-label">Repeat Password:</label>
                                <div class="col-sm-6">
                                    <sf:input type="password" 
                                              class="form-control passClass" 
                                              id="edit-repeat-password" 
                                              path="confirmPassword" 
                                              placeholder="Password"/>
                                    <sf:errors path="confirmPassword" cssClass="text-danger"></sf:errors>
                                    <sf:hidden id="edit-user-id" path="userId"/>
                                    <sf:hidden path="userName"/>
                                    <sf:hidden path="enabled"/>
                                </div>
                                <!--                                <div id="cPassCorrect">
                                                                    <img src="${pageContext.request.contextPath}/img/yes.png" 
                                                                         height="15" width="15"></div>
                                                                <div id="incorrect">
                                                                    <img src="${pageContext.request.contextPath}/img/no3.png" 
                                                                         height="15" width="15">
                                                                </div>-->
                            </div>
                            <div class="col-sm-offset-4" id="incorrectMessage" style="font-family: Arial; color: crimson">
                                <b>Passwords must match.</b>
                            </div>
                            <div class="col-sm-offset-4" id="correctMessage" style="font-family: Arial; color: forestgreen">
                                <b>Passwords match.</b>
                            </div>
                        </div>
                        <div class="col-sm-offset-3 col-sm-8">
                            <button type="submit" id="edit-user-profile-button" class="btn btn-primary">Update Information</button>
                        </div>
                    </sf:form>
                </div>
                <div class="col-md-6">
                    <br>
                    <h4>Rental History</h4>
                    <table class="table table-responsive table-condensed table-striped " style="border: 1px solid lightgray">
                        <tr style="background-color: lightslategray; color: white">
                            <th>Date</th>
                            <th>Employee</th>
                            <th>Status</th>
                            <th style="text-align: center">Asset</th>
                        </tr>
                        <tbody id="profileRows">
                            <c:forEach var="rec" items="${records}">
                                <tr> 
                                    <td>${rec.recordDate}</td>
                                    <td>${rec.employee.firstName} ${rec.employee.lastName}</td>
                                    <td>${rec.status.status}</td>
                                    <td style="text-align: center">${rec.asset.assetId}</td>
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
        <script src="${pageContext.request.contextPath}/js/profile.js"></script>
    </body>
</html>
