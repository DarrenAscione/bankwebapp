<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>

	<header class="sutd-template">
	    <nav class="navbar navbar-inverse navbar-fixed-top">
	      <div class="container">
	        <div class="navbar-header">
	          <button id="navbar-button" type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="navbar-brand"><img alt="SUTD Logo" src="<c:url value="/resources/img/sutd-logo.png" />"></a>
	        </div>
	        <div id="navbar" class="collapse navbar-collapse">
	          <ul class="nav navbar-nav">
	            <c:if test="${empty sessionScope.authenticatedUser}">
	            	<li><a href="login">Login</a></li>
	            	<li class="header-text">or</li>
            	 	<li><a href="register">Register</a></li>
	            </c:if>
	            <c:if test="${not empty sessionScope.authenticatedUser}">
					<li><a href="clientDashboard">Home</a></li>
					<li><a href="logout">Logout</a></li>
	            </c:if>
	          </ul>
	        </div><!--/.nav-collapse -->
	      </div>
	    </nav>
	</header>

	<c:if test="${not empty sessionScope.authenticatedUser}">
		<!-- Logout form -->
		<form id="logoutForm" action="logout" method="post">
		</form>
	</c:if>
