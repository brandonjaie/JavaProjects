<%-- 
    Document   : newContactFormNoAjax
    Created on : Mar 29, 2016, 10:24:09 AM
    Author     : apprentice
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
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
                            <li role="presentation" class="active">
                                <a href="${pageContext.request.contextPath}/displayDVDsNoAjax">No Ajax</a>
                            </li>
                            <li role="presentation">
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
            <h3>Add DVD
                <a href="displayDVDsNoAjax">
                    <button class="btn btn-primary btn-sm">Display DVDs</button>
                </a>
            </h3>
            <hr/>
            <sf:form class="form-horizontal" modelAttribute="dvd" role="form" action="addNewDVDNoAjax" method="POST">
                <div class="form-group">
                    <label for="add-title" class="col-md-4 control-label">Title:</label>
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-title" path="title" placeholder="Title"/>
                        <sf:errors path="title" cssClass="text-danger"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-release-date" class="col-md-4 control-label">Release Date:</label>
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-release-date" path="releaseDate" placeholder="Release Date"/>
                        <sf:errors path="releaseDate" cssClass="text-danger"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-mpaa-rating" class="col-md-4 control-label">MPAA Rating:</label>
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-mpaa-rating" path="mpaaRating" placeholder="MPAA Rating"/>
                        <sf:errors path="mpaaRating" cssClass="text-danger"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-director" class="col-md-4 control-label">Director:</label>
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-director" path="director" placeholder="Director"/>
                        <sf:errors path="director" cssClass="text-danger"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-studio" class="col-md-4 control-label">Studio:</label>
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-studio" path="studio" placeholder="Studio"/>
                        <sf:errors path="studio" cssClass="text-danger"></sf:errors>
                        </div>
                    </div>                      
                    <div class="form-group">
                        <label for="add-genre" class="col-md-4 control-label">Genre:</label>
                        <div class="col-md-8">
                        <sf:select path="genre.genreId" id="edit-genre" class="form-control">
                                <sf:option value="0">Genre</sf:option>
                                <c:forEach var="genre" items="${genres}">
                                    <sf:option value="${genre.genreId}">${genre.genreName}</sf:option>
                                </c:forEach>
                        </sf:select>
                        <sf:errors path="genre.genreId" cssClass="text-danger"></sf:errors>
                        <sf:errors path="genre" cssClass="target-danger"></sf:errors>
                        <sf:hidden path="genre.genreId"/>
                        <sf:hidden path="genre.genreName"/>
                        <sf:hidden path="dvdId"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <button type="submit" id="add-button" class="btn btn-primary">Add New DVD</button>
                    </div>
                </div>
            </div>
        </sf:form>
        <jsp:include page="footer.jsp"/>
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
