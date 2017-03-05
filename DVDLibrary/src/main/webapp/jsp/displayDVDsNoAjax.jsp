<%-- 
    Document   : home
    Created on : Mar 28, 2016, 1:16:09 PM
    Author     : apprentice
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <h3>My DVDs
                <a href="displayNewDVDFormNoAjax">
                    <button class="btn btn-primary btn-sm">Add DVD</button>
                </a>
            </h3>
            <hr/>
            <c:forEach var="dvd" items="${dvds}">

                <s:url value="deleteDVDNoAjax" var="deleteDVD_url">
                    <s:param name="dvdId" value="${dvd.dvdId}"/>
                </s:url>
                <s:url value="displayEditDVDFormNoAjax" var="editDVD_url">
                    <s:param name="dvdId" value="${dvd.dvdId}"/>
                </s:url>
                Title: ${dvd.title} |
                <a href="${deleteDVD_url}">Delete</a> |
                <a href="${editDVD_url}">Edit</a><br>

                Release Date: ${dvd.releaseDate}<br/>
                MPAA Rating: ${dvd.mpaaRating}<br/>
                Director: ${dvd.director}<br/>
                Studio: ${dvd.studio}<br/>
                Genre: ${dvd.genre.genreName}<br/>
                <hr/>

            </c:forEach>
        </div>
        <jsp:include page="footer.jsp"/>
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
