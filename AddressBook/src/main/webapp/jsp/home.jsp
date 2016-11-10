<%-- 
    Document   : home
    Created on : Nov 5, 2016, 7:22:51 PM
    Author     : Brandon
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
                            <li role="presentation" class="active">
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
            <h3>Address Book</h3> 
            <div class="row">
                <div class="col-md-6">

                    <h5 class="hidden-xs">Mission Statement</h5>
                    <p class="hidden-xs">Pickled synth single-origin coffee cray brunch messenger bag VHS tacos. Pabst skateboard hella semiotics next level migas cold-pressed echo park, man braid waistcoat yuccie hoodie tofu thundercats. Cliche beard lomo, migas tilde etsy farm-to-table. </p>
                    <div class="row">
                        <div class="col-sm-12">
                            <h5>AddressBook Info</h5>
                            <p>Four dollar toast mlkshk gentrify 90's echo park, DIY messenger bag scenester offal williamsburg. Single-origin coffee hammock polaroid lumbersexual gochujang distillery messenger bag hashtag gentrify food truck gluten-free pop-up, butcher affogato. Hoodie hashtag venmo, put a bird on it narwhal mumblecore flexitarian. Pitchfork bitters church-key keytar, cornhole tousled biodiesel 3 wolf moon tumblr marfa selfies jean shorts. Irony meh try-hard, YOLO cardigan plaid gastropub sustainable jean shorts intelligentsia deep v cold-pressed chicharrones affogato.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <img class="img img-responsive center-block" src="${pageContext.request.contextPath}/img/addressbook.png">
                </div>
            </div>
            <jsp:include page="footer.jsp"/>
        </div> 
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>


