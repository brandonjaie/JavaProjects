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
        <img src="${pageContext.request.contextPath}/img/addressbook.png" 
             alt="addressbook_logo" 
             style="padding-right: 5px" 
             height="30" 
             width="35" 
             align="left">
        <h2>AddressBook</h2>
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
<!--                                        <li role="presentation" class="active">
                        <a href="${pageContext.request.contextPath}/home">Home</a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/search">Search</a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/stats">Stats</a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/rest">REST</a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/displayAddressesNoAjax">Address Book (No Ajax)</a>
                    </li>-->
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('') ? 'active' : 'none'}"><a href="/AddressBook">Home</a></li>
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('/search.jsp') ? 'active' : 'none'}"><a href="search">Search</a></li>
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('/stats.jsp') ? 'active' : 'none'}"><a href="stats">Stats</a></li>
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('/rest.jsp') ? 'active' : 'none'}"><a href="rest">Rest</a></li>
                    <li class="${pageContext.request.requestURI eq pageContext.request.contextPath.concat('/displayAddressesNoAjax.jsp') ? 'active' : 'none'}"><a href="displayAddressesNoAjax">No Ajax</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
