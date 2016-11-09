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


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Address Book</title>
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/addressbook.png">

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
                        <li role="presentation" class="active">
                            <a href="${pageContext.request.contextPath}/displayAddressesNoAjax">No Ajax</a>
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

    <div class="container">

        <h2>My Address Book
            <a href="displayNewAddressFormNoAjax">
                <button class="btn btn-primary btn-sm">New Address Form</button>
            </a>
        </h2>
        <hr/>
        <c:forEach var="address" items="${addresses}">

            <s:url value="deleteAddressNoAjax" var="deleteAddress_url">
                <s:param name="addressId" value="${address.addressId}"/>
            </s:url>
            <s:url value="displayEditAddressFormNoAjax" var="editAddress_url">
                <s:param name="addressId" value="${address.addressId}"/>
            </s:url>
            Name: ${address.firstName} ${address.lastName} |
            <a href="${deleteAddress_url}">Delete</a> |
            <a href="${editAddress_url}">Edit</a><br>

            Street: ${address.street}<br/>
            City: ${address.city}<br/>
            State: ${address.state}<br/>
            Zip: ${address.zip}<br/>
            <hr/>

        </c:forEach>

        <jsp:include page="footer.jsp"/>
    </div>

    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>

