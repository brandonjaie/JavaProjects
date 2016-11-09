<%-- 
    Document   : tipCalc
    Created on : Sep 21, 2016, 1:46:39 PM
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
                                <h5>Tip Calculator</h5>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <form action="tip-calculator" class="form-horizontal" method="POST">
                                    <div class="form-group">
                                        <label for="bill" class="col-xs-2 control-label">Bill</label>
                                        <div class="col-xs-10">
                                            <input type="text" class="form-control" id="bill" name="bill" placeholder="$" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="tipPct" class="col-xs-2 control-label">Tip</label>
                                        <div class="col-xs-10">
                                            <input type="text" class="form-control" id="tipPct" name="tipPct" placeholder="%" />
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
                        <br/>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h5>Results</h5>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <table class="table table-responsive table-sm table-striped table-bordered " style="border: 1px solid lightgray">
                                    <tbody>
                                        <tr>
                                            <th>Bill</th>
                                            <td>${bill}</td>
                                        </tr>
                                        <tr> 
                                            <th>%</th>
                                            <td>${tipPct}</td>
                                        </tr>
                                        <tr> 
                                            <th style="width: 15%">Tip</th>
                                            <td>${tip}</td>
                                        </tr>
                                        <tr class="success"> 
                                            <th>Total</th>
                                            <td>${total}</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="col-md-12 text-center">
                                    <a href="tip-calculator">
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
