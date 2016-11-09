<%-- 
    Document   : factorizor
    Created on : Sep 21, 2016, 8:36:01 PM
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
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <div class="panel-group">
                        <br>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h5>Factorizer</h5>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <form class="form-horizontal" action="factorizer" method="POST">
                                    <div class="form-group">
                                        <!--                                            <label for="num" class="col-xs-1 control-label">Number</label>-->
                                        <div class="col-xs-12">
                                            <input type="text" class="form-control" id="number" name="number" placeholder="Enter a Number" />
                                        </div>
                                    </div>
                                    <!--                                        <label for="button" class="col-xs-1"></label>-->
                                    <div class="col-xs-11">
                                        <button type="submit" class="btn btn-warning">Factorize</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <h4 style="color:red">${error}</h4>
                </div>
                <div class="col-md-6">
                    <div class="panel-group">
                        <br>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h5>Results</h5>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="panel-info text-center">
                                    <div class="well well-sm"> 
                                        Factors of <b>${number}</b>:
                                    </div>
                                    <div>
                                        <c:forEach var="factor" items="${factors}">
                                            <h4><c:out value="${factor}"/></h4>                                              
                                        </c:forEach>
                                        <h4>${number}</h4>
                                    </div>
                                    <br>
                                    <c:if test="${perfect}">
                                        <div class="well well-sm"> 
                                            <b>${number}</b> is a perfect number
                                        </div>
                                    </c:if>
                                    <c:if test="${prime}">
                                        <div class="well well-sm">  
                                            <b>${number}</b> is a prime number
                                        </div>
                                    </c:if>
                                </div>                      
                                <div class="col-md-12 text-center">
                                    <a href="factorizer">
                                        <button type="submit" class="btn btn-danger">Reset</button>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@ include file="footer.jsp"%>                        
        </div>
        <script src="js/jquery-1.11.3.min.js"></script>
        <script src="js/bootstrap.min.js"></script>

    </body>


</html>
