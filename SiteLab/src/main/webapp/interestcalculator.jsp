<%-- 
    Document   : interestCalc
    Created on : Sep 21, 2016, 5:22:18 PM
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
                                <h5>Interest Calculator</h5>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <form action="interest-calculator" class="form-horizontal" method="POST">
                                    <div class="form-group">
                                        <label for="principal" class="col-sm-2 control-label text-left">Principal</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="principal" name="principal" placeholder="$" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="interest" class="col-sm-2 control-label">Interest</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="interest" name="interest" placeholder="%" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="years" class="col-sm-2 control-label">Years</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="years" name="years" placeholder="" />
                                        </div>
                                    </div>
                                    <div class="col-xs-1 col-sm-offset-2">
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
                                <table class="table table-responsive table-striped " style="border: 1px solid lightgray">
                                    <tr style="background-color: lightgray; color: black;">
                                        <th style="text-align: center">Year</th>
                                        <th style="text-align: center">Beginning Principal</th>
                                        <th style="text-align: center">Ending Principal</th>
                                        <th style="text-align: center">Interest Earned</th>
                                    </tr>
                                    <tbody style="text-align: center">
                                        <c:forEach var="interest" items="${interests}">
                                            <tr>
                                                <td><c:out value="${interest.year}"/></td>
                                                <td><c:out value="${interest.beginningPrincipal}"/></td>
                                                <td><c:out value="${interest.endingPrincipal}"/></td>
                                                <td><c:out value="${interest.interestEarned}"/></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <div class="col-md-12 text-center">
                                    <a href="interest-calculator">
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

