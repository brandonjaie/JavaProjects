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
        <title>DVD Library</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/dvdlogo1.png">
    </head>
    <body>
        <div class="container-fluid">
            <div class="content">
                <img src="${pageContext.request.contextPath}/img/dvdlogo1.png" 
                     alt="dvd_logo" 
                     style="padding-right: 5px" 
                     height="30" 
                     width="35" 
                     align="left">
                <h2>DVD Library</h2>
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
                             style="pointer-events: none; ">DVD Library</div>
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
                                <a href="${pageContext.request.contextPath}/displayDVDsNoAjax">No Ajax</a>
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
                    <table id="searchTable" class="table table-hover">
                        <tr>
                            <th width="15%">Title</th>
                            <th width="10%">Release</th>
                            <th width="10%">Rating</th>
                            <th width="15%">Director</th>
                            <th width="15%">Studio</th>
                            <th width="15%">Genre</th>
                        </tr>
                        <tbody id="searchRows"></tbody>
                    </table>
                </div>
                <div class="col-md-6">
                    <h3>Search DVDs</h3>
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="search-title" class="col-md-4 control-label">Title:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="search-title" placeholder="Title">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="search-release-date" class="col-md-4 control-label">Release Date:</label>
                            <div class="col-md-8" >
                                <input type="text" class="form-control" id="search-release-date" placeholder="Release Date">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="search-mpaa-rating" class="col-md-4 control-label">MPAA Rating:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="search-mpaa-rating" placeholder="MPAA Rating">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="search-director" class="col-md-4 control-label">Director:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="search-director" placeholder="Director">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="search-studio" class="col-md-4 control-label">Studio:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" id="search-studio" placeholder="Studio">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="search-genre" class="col-md-4 control-label">Genre:</label>
                            <div class="col-md-8">
                                <select id="search-genre" class="form-control">
                                    <option value="" selected>Genre</option>
                                    <option value="1">ACTION</option>
                                    <option value="2">CLASSIC</option>
                                    <option value="3">COMEDY</option>
                                    <option value="4">DOCUMENTARY</option>
                                    <option value="5">DRAMA</option>
                                    <option value="6">ROMANCE</option>
                                    <option value="7">THRILLER</option>
                                    <option value="8">SCI-FI</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class ="col-md-offset-4 col-md-8">
                                <button type="submit" id="search-button" class="btn btn-primary">Search DVDs</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="footer.jsp"/>
        <%@include file="detailsEditModalFragment.jsp"%>
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/search.js"></script>
    </body>
</html>
