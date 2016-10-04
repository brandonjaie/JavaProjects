<%-- 
    Document   : home
    Created on : May 10, 2016, 1:37:25 PM
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
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        
        <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">

        <!-- SWC Icon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/trees.png">
        <!--        <style>
                    .footloose {
                        position: absolute;
                        bottom: 0;
                        width: 100%;
                        /* Set the fixed height of the footer here */
                        /*                        height: 60px;*/
                        background-color: #f5f5f5;
                    }
                </style>-->
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
                            <li class="active"><a href="${pageContext.request.contextPath}/home">Home</a></li>
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
                                <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
                                </sec:authorize>
                        </ul>
                        <sec:authorize access="isAnonymous()">
                            <form id="logInForm" class="signin navbar-form navbar-right" 
                                  method="post" 
                                  action="j_spring_security_check">
                                <div class="form-group">
                                    <input type="text" 
                                           id="username_or_email" 
                                           name="j_username" 
                                           class="form-control" 
                                           placeholder="username">
                                </div>
                                <div class="form-group">
                                    <input type="password" 
                                           id="password" 
                                           name="j_password" 
                                           class="form-control" 
                                           placeholder="password">
                                </div>
                                <button  id="logIn" name="commit" type="submit" class="btn btn-success">Login</button>
                            </form>
                        </sec:authorize>
                        <sec:authorize access="isAuthenticated()">
                            <div class="signout navbar-form navbar-right">
                                <button class="btn" style="pointer-events: none; background-color: lightslategray; color: white">
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
        <div class="container">
            <h4 class="hidden-xs">Welcome to Kobold Camp Equipment Rental Management System</h4> 
            <h4 class="visible-xs">Kobold Camp Equipment Rental</h4>
            <div class="row">
                <div class="col-md-6" style="padding: 20px">
                     
                    <h5 class="hidden-xs">Mission Statement</h5>
                    <p class="hidden-xs">Pickled synth single-origin coffee cray brunch messenger bag VHS tacos. Pabst skateboard hella semiotics next level migas cold-pressed echo park, man braid waistcoat yuccie hoodie tofu thundercats. Cliche beard lomo, migas tilde etsy farm-to-table. </p>
                    <div class="row">
                        <div class="col-sm-12">
                            <h5>Camp Info</h5>
                            <p>Four dollar toast mlkshk gentrify 90's echo park, DIY messenger bag scenester offal williamsburg. Single-origin coffee hammock polaroid lumbersexual gochujang distillery messenger bag hashtag gentrify food truck gluten-free pop-up, butcher affogato. Hoodie hashtag venmo, put a bird on it narwhal mumblecore flexitarian. Pitchfork bitters church-key keytar, cornhole tousled biodiesel 3 wolf moon tumblr marfa selfies jean shorts. Irony meh try-hard, YOLO cardigan plaid gastropub sustainable jean shorts intelligentsia deep v cold-pressed chicharrones affogato.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <img class="img img-responsive center-block" src="${pageContext.request.contextPath}/img/koboldcamplogo1.png"
                         height="400" width="400">
                </div>
            </div>
            <jsp:include page="footer.jsp"/>
        </div> 
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
