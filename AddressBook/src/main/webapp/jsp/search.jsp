<%-- 
    Document   : home
    Created on : Mar 28, 2016, 1:16:09 PM
    Author     : apprentice
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Address Book</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/addressbook.png">
    </head>
    <body>
        <div class="container-fluid">
            <div class="content">
                <img src="${pageContext.request.contextPath}/img/addressbook.png" 
                     alt="addressbook_logo" 
                     style="padding-right: 5px" 
                     height="30" 
                     width="35" 
                     align="left">
                <h2>Address Book</h2>
            </div>
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <div class="navbar-brand" 
                             style="pointer-events: none; ">Address Book</div>
                    </div>

                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav"> 
                            <li role="presentation">
                                <a href="${pageContext.request.contextPath}/home">Home</a>
                            </li>
                            <li role="presentation">
                                <a href="${pageContext.request.contextPath}/rest">REST</a>
                            </li>
                            <li role="presentation">
                                <a href="${pageContext.request.contextPath}/restmodal">REST (Modal)</a>
                            </li>
                            <li role="presentation">
                                <a href="${pageContext.request.contextPath}/displayAddressesNoAjax">No Ajax</a>
                            </li>
                            <li role="presentation" class="active">
                                <a href="${pageContext.request.contextPath}/search">Search</a>
                            </li>
                            <li role="presentation">
                                <a href="${pageContext.request.contextPath}/stats">Stats</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        <div class="container-fluid" style="margin: 0px 10px;">

            <div class="row">
                <div class="col-md-6">
                    <h3>Search Results</h3>
                    <%@include file="contactSummaryTableFragment.jsp"%>
                </div>

                <div class="col-md-6">
                    <h3>Search Addresses</h3>
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="search-first-name" class="col-md-4 control-label">First Name:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="search-first-name" placeholder="First Name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="search-last-name" class="col-md-4 control-label">Last Name:</label>
                            <div class="col-md-8" >
                                <input type="text" class="form-control" id="search-last-name" placeholder="Last Name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="search-street" class="col-md-4 control-label">Street:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="search-street" placeholder="Street">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="search-city" class="col-md-4 control-label">City:</label>
                            <div class="col-md-8">
                                <input type="city" class="form-control" id="search-city" placeholder="City">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="search-state" class="col-md-4 control-label">State:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="search-state" placeholder="State">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="search-zip" class="col-md-4 control-label">Zip:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="search-zip" placeholder="Zip">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class ="col-md-offset-4 col-md-8">
                                <button type="submit" id="search-button" class="btn btn-primary">Search Addresses</button>
                            </div>
                        </div>
                    </form>
                    <div id="validationErrors" style="color: red" />
                </div>
            </div>
        </div>
        <jsp:include page="footer.jsp"/>
    </div>

    <%@include file="detailsEditModalFragment.jsp"%>            

    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/mockData.js"></script>
    <script src="${pageContext.request.contextPath}/js/restmodal.js"></script>
</body>
</html>

