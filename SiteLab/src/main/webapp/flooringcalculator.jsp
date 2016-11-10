<%-- 
    Document   : flooringCalc
    Created on : Sep 21, 2016, 11:38:39 PM
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
                <div class="col-md-6">
                    <div class="panel-group">
                        <br>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h5>Flooring Calculator</h5>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <form action="flooring-calculator" class="form-horizontal" method="POST">
                                    <div class="form-group">
                                        <label for="width" class="col-xs-2 control-label">Width</label>
                                        <div class="col-xs-10">
                                            <input type="text" class="form-control" id="width" name="width" placeholder="FT" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="length" class="col-xs-2 control-label">Length</label>
                                        <div class="col-xs-10">
                                            <input type="text" class="form-control" id="length" name="length" placeholder="FT" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="cost" class="col-xs-2 control-label">Cost</label>
                                        <div class="col-xs-10">
                                            <input type="text" class="form-control" id="cost" name="cost" placeholder="$ Per Square Foot" />
                                        </div>
                                    </div>
                                    <label for="button" class="col-xs-2"></label>
                                    <div class="col-xs-8">
                                        <button type="submit" class="btn btn-warning">Calculate</button>
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
                                <table class="table table-responsive table-condensed table-bordered" style="border: 1px solid lightgray">
                                    <tbody>
                                        <tr class="info">
                                            <th>Width</th>
                                            <td>${width}</td>
                                        </tr>
                                        <tr class="info"> 
                                            <th>Length</th>
                                            <td>${length}</td>
                                        </tr>
                                        <tr class="info"> 
                                            <th>Cost PSF</th>
                                            <td>${cost}</td>
                                        </tr>
                                        <tr class="warning"> 
                                            <th style="width: 40%">Material Cost</th>
                                            <td>${materialCost}</td>
                                        </tr>
                                        <tr class="warning"> 
                                            <th>Labor Cost</th>
                                            <td>${laborCost}</td>
                                        </tr>
                                        <tr class="success"> 
                                            <th>Total Cost</th>
                                            <td>${totalCost}</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="col-md-12 text-center">
                                    <a href="flooring-calculator">
                                        <button type="submit" class="btn btn-danger">Reset</button>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%@ include file="footer.jsp"%>
            </div>
        </div>
        <script src="js/jquery-1.11.3.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>

