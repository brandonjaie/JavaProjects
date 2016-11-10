<%-- 
    Document   : index
    Created on : Sep 21, 2016, 2:02:37 PM
    Author     : Brandon
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <title>Site Lab</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/bootstrap-theme.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/calculator.png">
    </head>

    <body>
        <jsp:include page="navbar.jsp"/>
        <div class="container-fluid" style="margin: 0px 10px"> 
            <div class="row">
                <div class="col-md-6" style="padding: 20px">
                    <h4>Mission Statement</h4>
                    <p>Pickled synth single-origin coffee cray brunch messenger bag VHS tacos. Pabst skateboard hella semiotics next level migas cold-pressed echo park, man braid waistcoat yuccie hoodie tofu thundercats. Cliche beard lomo, migas tilde etsy farm-to-table. </p>
                    <div class="row">
                        <div class="col-md-12">
                            <h4>Site Info</h4>
                            <p>Four dollar toast mlkshk gentrify 90's echo park, DIY messenger bag scenester offal williamsburg. Single-origin coffee hammock polaroid lumbersexual gochujang distillery messenger bag hashtag gentrify food truck gluten-free pop-up, butcher affogato. Hoodie hashtag venmo, put a bird on it narwhal mumblecore flexitarian. Pitchfork bitters church-key keytar, cornhole tousled biodiesel 3 wolf moon tumblr marfa selfies jean shorts. Irony meh try-hard, YOLO cardigan plaid gastropub sustainable jean shorts intelligentsia deep v cold-pressed chicharrones affogato.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <img class="img img-responsive center-block" src="${pageContext.request.contextPath}/img/calculator.png"
                         height="400" width="400">
                </div>
            </div>
            <jsp:include page="footer.jsp"/>
        </div> 
        <script src="js/jquery-1.11.3.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
