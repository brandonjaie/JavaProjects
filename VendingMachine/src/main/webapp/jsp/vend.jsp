<%-- 
    Document   : vendingmachine
    Created on : Sep 10, 2016, 9:00:18 PM
    Author     : Brandon
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Vending Machine</title>

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/vending_logo.png">
    </head>
    <body>
        <div class="container-fluid">
            <div class="content">
                <img src="${pageContext.request.contextPath}/img/vending_logo.png" 
                     alt="vending_logo" 
                     style="padding-right: 5px" 
                     height="30" 
                     width="30" 
                     align="left">
                <h2>Refreshment Solutions</h2>
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
                        <div class="navbar-brand" 
                             style="pointer-events: none; ">Vending Machine</div>
                    </div>
                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <!--NAV LINKS-->
                        <ul class="nav navbar-nav">                   
                            <!--render navbar links based on roles-->
                            <li class="active"><a href="${pageContext.request.contextPath}/vend">Home</a></li>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <li><a href="${pageContext.request.contextPath}/admin">Admin</a></li>
                                </sec:authorize>
                        </ul>
                        <!--NAV LINKS-->
                        <!--LOG IN FORM-->        
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
                                <button  id="logIn" name="commit" type="submit" class="btn btn-success">
                                    <img src="${pageContext.request.contextPath}/img/key2.png"
                                         alt="key_logo" 
                                         height="14" 
                                         width="33">
                                </button>
                            </form>
                        </sec:authorize>
                        <!--END LOG IN FORM-->
                        <!--LOG OUT BUTTON-->
                        <sec:authorize access="isAuthenticated()">
                            <div class="signout navbar-form navbar-right">
                                <button class="btn" 
                                        style="background: lightgray; pointer-events: none;">
                                    <sec:authentication property="principal.username"/>
                                </button>
                                <a href="${pageContext.request.contextPath}/j_spring_security_logout"> 
                                    <button type="submit" id="logOut" class="btn btn-danger gradient">
                                        <img src="${pageContext.request.contextPath}/img/key2.png"
                                             alt="key_logo" 
                                             height="14" 
                                             width="33">
                                    </button>
                                </a>
                            </div>
                        </sec:authorize>
                        <!--END LOG OUT BUTTON-->
                    </div><!-- /.navbar-collapse -->
                </div><!-- /.container-fluid -->
            </nav>
        </div>

        <div class="container-fluid">
            <div class="row text-center">
                <!--VENDING MACHINE DIV-->
                <div class="col-md-6" style="padding: 20px">
                    <form class="form-horizontal" 
                          modelAttribute="items"
                          role="form" 
                          action="vendItem" 
                          method="POST"
                          autocomplete="off">
                        <h4>Vending Machine</h4>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <span class="btn btn-success insertFunds" data-money-value="1.0">$1.00</span>
                                <span class="btn btn-success insertFunds" data-money-value=".25">$0.25</span>
                                <span class="btn btn-success insertFunds" data-money-value=".1">$0.10</span>
                                <span class="btn btn-success insertFunds" data-money-value=".05">$0.05</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-2"></div>
                            <div class="col-xs-5">
                                <input type="text" 
                                       class="form-control text-center" 
                                       id="totalDeposit" 
                                       name="totalDeposit"
                                       onSubmit="saveDeposit()"
                                       placeholder="Insert Funds" 
                                       readonly/> 
                            </div>
                            <div class="col-xs-3">
                                <button type="submit" 
                                        class="btn btn-danger">Coin Return</button>
                            </div>
                            <div class="col-xs-2"></div>
                        </div>
                        <div class="form-group">
                            <!--ITEM PANELS-->
                            <c:forEach var="item" items="${items}">
                                <div class="col-sm-4 text-center">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <c:if test="${item.inventory > 0}">
                                                <b>${item.itemName}</b>
                                            </c:if>
                                            <c:if test="${item.inventory == 0}">
                                                <b style="color: red">SOLD OUT</b>
                                            </c:if>
                                        </div>
                                        <div class="panel-body">
                                            <div class="row">    
                                                <div class="col-xs-6">
                                                    <button type="submit" class="btn btn-secondary btn-lg"
                                                            style="background: #265a88; color: white;"
                                                            id="itemId" name="itemId" value="${item.itemId}">
                                                        <b>${item.itemPosition}</b>
                                                    </button>
                                                </div>
                                                <div class="col-xs-6">       
                                                    <img class="img img-responsive" 
                                                         src="${pageContext.request.contextPath}/${item.image}"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-footer">
                                            <div class="row" style="padding-top: 15px">
                                                <div class="col-sm-6">
                                                    <div class="well well-sm text-center"
                                                         id="item-price"
                                                         style="border-color: lightgray"><b>${item.itemPrice}</b></div>
                                                </div>
                                                <div class="col-sm-6">
                                                    <div class="well well-sm" 
                                                         style="border-color: lightgray"><b>${item.inventory}</b></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <!--END ITEM PANELS-->
                        </div>
                    </form>
                </div>
                <!--END VENDING MACHINE DIV-->
                <!--RECEIPT, COIN RETURN, ERRORS DIV-->
                <div class="col-md-6">
                    <br>
                    <h4>Refreshment Solutions Receipt</h4>
                    <div class="row">
                        <div class="col-sm-12">
                            <br>
                            <!--RECEIPT-->
                            <c:if test="${!empty successMsg}">
                                <img class="img img-responsive center-block" alt="product" 
                                     src="${pageContext.request.contextPath}/${image}" width="100" height="100"/>
                                <br>
                            </div>
                            <div class="col-sm-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">                            
                                            <c:if test="${!empty successMsg}">
                                                <strong>
                                                    ${successMsg}
                                                </strong>
                                            </c:if></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="table-responsive">
                                            <table class="table table-condensed">
                                                <thead>
                                                    <tr>
                                                        <td><strong>Item</strong></td>
                                                        <td class="text-center"><strong>Price</strong></td>
                                                        <td class="text-center"><strong>Quantity</strong></td>
                                                        <td class="text-right"><strong>Totals</strong></td>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>${item.itemName}</td>
                                                        <td class="text-center">${itemPrice}</td>
                                                        <td class="text-center">1</td>
                                                        <td class="text-right">${itemPrice}</td>

                                                    </tr>
                                                    <tr>
                                                        <td class="thick-line"></td>
                                                        <td class="thick-line"></td>
                                                        <td class="thick-line text-center"><strong>Deposit</strong></td>
                                                        <td class="thick-line text-right">${deposit}</td>
                                                    </tr>
                                                    <tr>
                                                        <td class="no-line"></td>
                                                        <td class="no-line"></td>
                                                        <td class="no-line text-center"><strong>Return</strong></td>
                                                        <td class="no-line text-right">
                                                            <c:if test="${quarters > 0}">
                                                                <b>${quarters}</b>
                                                                <img src="${pageContext.request.contextPath}/img/quarter.png" 
                                                                     alt="quarter"
                                                                     height="25" 
                                                                     width="25">
                                                            </c:if>
                                                            <c:if test="${dimes > 0}">
                                                                <b>${dimes}</b>
                                                                <img src="${pageContext.request.contextPath}/img/dime.png" 
                                                                     alt="dime"
                                                                     height="18" 
                                                                     width="18">
                                                            </c:if>
                                                            <c:if test="${nickels > 0}">
                                                                <b>${nickels}</b>
                                                                <img src="${pageContext.request.contextPath}/img/nickel.jpg" 
                                                                     alt="nickel"
                                                                     height="20" 
                                                                     width="20">
                                                            </c:if>
                                                            <c:if test="${quarters == 0 && dimes == 0 && nickels == 0}">
                                                                None
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="no-line"></td>
                                                        <td class="no-line"></td>
                                                        <td class="no-line text-center"><strong>Change</strong></td>
                                                        <td class="no-line text-right">${coins}</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="row">     
                                            <div class="col-md-12 text-center">
                                                <a href="vend">
                                                    <button type="submit" class="btn btn-danger">Make Another Selection</button>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <!--END RECEIPT-->
                            <!--INSUFFICIENT FUNDS-->
                            <c:if test="${!empty insertFundsMsg}">
                                <img class="img img-responsive center-block" alt="product_image" 
                                     src="${pageContext.request.contextPath}/${image}" width="100" height="100"/>
                                <h3>
                                    <span class="label label-danger">
                                        ${insertFundsMsg}
                                    </span>
                                </h3>
                            </c:if>
                            <!--END INSUFFICIENT FUNDS-->
                            <!--COIN RETURN-->
                            <c:if test="${!empty coinReturnMsg}">
                                <br>
                                <div class="col-sm-2"></div>
                                <div class="col-sm-8">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h3 class="panel-title">                            
                                                <c:if test="${!empty coinReturnMsg}">
                                                    <strong>
                                                        ${coinReturnMsg}
                                                    </strong>
                                                </c:if></h3>
                                        </div>
                                        <div class="panel-body">
                                            <div class="row">
                                                <div class="col-sm-12">
                                                    <c:if test="${quarters > 0}">
                                                        <b>${quarters}</b>
                                                        <img src="${pageContext.request.contextPath}/img/quarter.png" 
                                                             alt="quarter"
                                                             height="45" 
                                                             width="45">
                                                    </c:if>
                                                    <c:if test="${dimes > 0}">
                                                        <b>${dimes}</b>
                                                        <img src="${pageContext.request.contextPath}/img/dime.png" 
                                                             alt="dime"
                                                             height="38" 
                                                             width="38">
                                                    </c:if>
                                                    <c:if test="${nickels > 0}">
                                                        <b>${nickels}</b>
                                                        <img src="${pageContext.request.contextPath}/img/nickel.jpg" 
                                                             alt="nickel"
                                                             height="40" 
                                                             width="40">
                                                    </c:if>
                                                    <c:if test="${quarters == 0 && dimes == 0 && nickels == 0}">
                                                        <h4>None</h4>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2"></div>
                                <br>
                            </c:if>
                            <!--END COIN RETURN-->
                            <!--SOLD OUT-->
                            <c:if test="${!empty soldOutMsg}">
                                <img class="img img-responsive center-block" alt="product_image" 
                                     src="${pageContext.request.contextPath}/${image}" width="100" height="100"/>
                                <h3>
                                    <span class="label label-danger">
                                        ${soldOutMsg}
                                    </span>
                                </h3>
                            </c:if>
                            <!--END SOLD OUT-->
                        </div>
                    </div>
                </div>
                <!--END RECEIPT, COIN RETURN, ERRORS DIV-->
            </div>
            <jsp:include page="footer.jsp"/>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/vendingmachine.js"></script>
    </body>
</html>




