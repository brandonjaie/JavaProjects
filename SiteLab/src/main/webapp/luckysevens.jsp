<%-- 
    Document   : luckySevens
    Created on : Sep 21, 2016, 10:32:05 PM
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
                                <h5>Lucky Sevens</h5>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <form class="form-horizontal" action="lucky-sevens" method="POST">
                                    <div class="form-group">
                                        <!--                                            <label for="dollars" class="col-xs-1 control-label">Bet</label>-->
                                        <div class="col-xs-12">
                                            <input type="text" class="form-control" id="dollars" name="dollars" placeholder="$ Enter Bet $" />
                                        </div>
                                    </div>
                                    <!--                                        <label for="button" class="col-xs-1"></label>-->
                                    <div class="col-xs-11">
                                        <button type="submit" class="btn btn-success">Roll Dice</button>
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
                                <table class="table table-responsive table-condensed" style="border: 1px solid lightgray">
                                    <tbody>
                                        <tr class="success">
                                            <th>Starting Bet</th>
                                            <td>${startingBet}</td>
                                        </tr>
                                        <tr> 
                                            <th>Total Rolls</th>
                                            <td>${rolls}</td>
                                        </tr>
                                        <tr> 
                                            <th style="width: 50%">No. of Rolls with Most $</th>
                                            <td>${rollsAtMax}</td>
                                        </tr>
                                        <tr class="success"> 
                                            <th>Max Won</th>
                                            <td>${maxMoney}</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="panel-info text-center">
                                    <a href="lucky-sevens">
                                        <button type="submit" class="btn btn-danger">Play Again</button>
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
