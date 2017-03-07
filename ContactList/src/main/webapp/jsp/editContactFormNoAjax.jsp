<%-- 
    Document   : editContactFormNoAjax
    Created on : Feb 26, 2017, 2:26:57 PM
    Author     : Brandon
--%>

<%--This page will be similar to the JSP that
displays the New Contact form except that the Edit Contact form will be pre-populated with the data of the
Contact that is to be edited. Create a JSP called editContactForNoAjax.jsp and modify it so it has the following
content. Pay special attention to the new taglib directive at the top (this allows us to use the Spring Forms
tags) and the modelAttribute and path attributes on the form and form inputs. These attributes tell Spring
how to grab and display the Contact information that we placed on the Model in the
displayEditContactFormNoAjax controller method. --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- #1 - Directive for Spring Form tag libraries -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Company Contacts</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <!-- SWC Icon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icon.png">
    </head>
    <body>
        <div class="container">
            <h1>Company Contacts</h1>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/home">Home</a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/search">Search</a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/stats">Stats</a>
                    </li>
                    <li role="presentation" class="active">
                        <a href="${pageContext.request.contextPath}/displayContactListNoAjax">
                            Contact List (No Ajax)
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="container">
            <h1>New Contact Form</h1>
            <a href="displayContactListNoAjax">Contact List (No Ajax)</a><br/>
            <hr/>
            <sf:form class="form-horizontal" role="form" modelAttribute="contact"
                     action="editContactNoAjax" method="POST">
                <div class="form-group">
                    <label for="add-first-name" class="col-md-4 control-label">First Name:</label>
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-first-name"
                                  path="firstName" placeholder="First Name"/>
                        <!-- #1 - error tag -->
                        <sf:errors path="firstName" cssClass="text-danger"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-last-name" class="col-md-4 control-label">Last Name:</label>
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-last-name"
                                  path="lastName" placeholder="Last Name"/>
                        <sf:errors path="lastName" cssClass="text-danger"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-company" class="col-md-4 control-label">Company:</label>
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-company"
                                  path="company" placeholder="Company"/>
                        <sf:errors path="company" cssClass="text-danger"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-email" class="col-md-4 control-label">Email:</label>
                        <div class="col-md-8">
                        <sf:input type="email" class="form-control" id="add-email"
                                  path="email" placeholder="Email"/>
                        <sf:errors path="email" cssClass="text-danger"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-phone" class="col-md-4 control-label">Phone:</label>
                        <div class="col-md-8">
                        <sf:input type="tel" class="form-control" id="add-phone"
                                  path="phone" placeholder="Phone"/>
                        <sf:errors path="phone" cssClass="text-danger"></sf:errors>
                        <sf:hidden path="contactId"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <button type="submit" id="add-button" class="btn btn-default">Add New Contact</button>
                    </div>
                </div>
            </sf:form>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
