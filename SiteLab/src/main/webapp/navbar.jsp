<%-- 
    Document   : navbar
    Created on : Sep 21, 2016, 2:01:07 PM
    Author     : Brandon
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
@media (max-width: 1000px) {
  .navbar-header {
      float: none;
  }
  .navbar-left,.navbar-right {
      float: none !important;
  }
  .navbar-toggle {
      display: block;
  }
  .navbar-collapse {
      border-top: 1px solid transparent;
      box-shadow: inset 0 1px 0 rgba(255,255,255,0.1);
  }
  .navbar-fixed-top {
      top: 0;
      border-width: 0 0 1px;
  }
  .navbar-collapse.collapse {
      display: none!important;
  }
  .navbar-nav {
      float: none!important;
      margin-top: 7.5px;
  }
  .navbar-nav>li {
      float: none;
  }
  .navbar-nav>li>a {
      padding-top: 10px;
      padding-bottom: 10px;
  }
  .collapse.in{
      display:block !important;
  }
}

</style>
<div class="container">
    <div class="content">
        <img src="${pageContext.request.contextPath}/img/calculator.png" 
             alt="calculator_logo" 
             style="padding-right: 5px" 
             height="30" 
             width="30" 
             align="left">
        <h2>Site Lab</h2>
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
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">                   
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('/') ? 'active' : 'none'}"><a href="/SiteLab">Home</a></li>
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('/interestcalculator.jsp') ? 'active' : 'none'}"><a href="interest-calculator">Interest Calculator</a></li>
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('/flooringcalculator.jsp') ? 'active' : 'none'}"><a href="flooring-calculator">Flooring Calculator</a></li>
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('/tipcalculator.jsp') ? 'active' : 'none'}"><a href="tip-calculator">Tip Calculator</a></li>
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('/unitconverter.jsp') ? 'active' : 'none'}"><a href="unit-converter">Unit Converter</a></li>
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('/luckysevens.jsp') ? 'active' : 'none'}"><a href="lucky-sevens">Lucky Sevens</a></li>
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('/factorizer.jsp') ? 'active' : 'none'}"><a href="factorizer">Factorizer</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
