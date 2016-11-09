<%-- 
    Document   : uniConv
    Created on : Sep 22, 2016, 8:20:33 AM
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
                                <h5>Unit Converter</h5>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-body">                                
                                <form action="unit-converter" method="POST">
                                    <div class="row">

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <input type="text" class="form-control" name="value" placeholder="Length"/>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <select class="form-control" name="userInput1">
                                                    <option value="" selected disabled>From</option>
                                                    <option value="Millimeters">Millimeters</option>
                                                    <option value="Centimeters">Centimeters</option>
                                                    <option value="Inches">Inches</option>
                                                    <option value="Feet">Feet</option>
                                                    <option value="Yards">Yards</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <select class="form-control" name="userInput2">
                                                    <option value="" selected disabled>To</option>
                                                    <option value="Millimeters">Millimeters</option>
                                                    <option value="Centimeters">Centimeters</option>
                                                    <option value="Inches">Inches</option>
                                                    <option value="Feet">Feet</option>
                                                    <option value="Yards">Yards</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <button type="submit" class="btn btn-warning">Convert</button>
                                            </div>
                                        </div>

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
                            <div class="panel-body text-center">
                                <div class="row">

                                    <div class="col-md-5">
                                        <div class="panel panel-default">
                                            <div class="panel-body">${value}</div>
                                            <div class="panel-heading">${userInput1}</div>
                                        </div>
                                    </div>

                                    <div class="col-md-2">
                                        <h1>=</h1>
                                    </div>

                                    <div class="col-md-5">
                                        <div class="panel panel-default">
                                            <div class="panel-body">${result} </div>
                                            <div class="panel-heading">${userInput2}</div>
                                        </div>
                                    </div>

                                </div>
                                <br> 
                                <div class="row">
                                    <div class="col-md-12 text-center">
                                        <a href="unit-converter">
                                            <button type="submit" class="btn btn-danger">Reset</button>
                                        </a>
                                    </div>

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