<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="<c:url value="/resources/img/sutd-logo.ico" />">

    <title>SUTD BANK</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.3.js" />"></script>

    <!-- Custom styles for this template -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-theme.min.css" />">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bankwebapp.css" />">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
  </head>

  <body>
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
	          <a class="navbar-brand" href="welcome"><img alt="SUTD Logo" src="<c:url value="/resources/img/sutd-logo.png" />"></a>
	        </div>
	      </div>
	    </nav>
	</header>

	<!-- Registration form -->
	<main class="mainContent sutd-template" role="main">
		<div>
			<h2 style="text-align: center">Registration Form</h2>
		<div class="containter registrationForm">
			<form id="registrationForm" action="register" method="post">
				<div id="messageBox" class="hidden"></div>
				
				<c:if test="${not empty req_error}">
						<div id="errorMsg">
							<p class="text-danger">${req_error}</p>
						</div>
				</c:if>
				<c:remove var="req_error" scope="session" /> 
				
				<div id="input-group-fullName" class="form-group">
					<label for="fullName" class="control-label">Full Name</label>
					<input type="text" class="form-control" id="fullName" name="fullName" placeholder="Full Name">
				</div>
				<div id="input-group-fin" class="form-group">
					<label for="fin" class="control-label">FIN</label>
					<input type="text" class="form-control" id="fin" name="fin" placeholder="FIN number">
				</div>
				<div id="input-group-dateOfBirth" class="form-group">
					<label for="dateOfBirth" class="control-label">Date of Birth</label>
					<input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" placeholder="Date of Birth">
				</div>
				<div id="input-group-occupation" class="form-group">
					<label for="occupation" class="control-label">Occupation</label>
					<input type="text" class="form-control" id="occupation" name="occupation" placeholder="Occupation">
				</div>
				<div id="input-group-mobileNumber" class="form-group">
					<label for="mobileNumber" class="control-label">Mobile Number</label>
					<input type="number" class="form-control" id="mobileNumber" name="mobileNumber" placeholder="Mobile Number">
				</div>
				<div id="input-group-address" class="form-group">
					<label for="address" class="control-label">Address</label>
					<input type="text" class="form-control" id="address" name="address" placeholder="Address">
				</div>
				<div id="input-group-email" class="form-group">
					<label for="email" class="control-label">Email</label>
					<input type="text" class="form-control" id="email" name="email" placeholder="Email">
				</div>
				<div id="input-group-username" class="form-group">
					<label for="username" class="control-label">User name</label>
					<input type="text" class="form-control" id="username" name="username" placeholder="User name">
				</div>
				<div id="input-group-password" class="form-group">
					<label for="password" class="control-label">Password</label>
					<input type="password" class="form-control" id="password" name="password" placeholder="Password">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</div>
				<button id="registerBtn" type="submit" value="register" class="btn btn-default">Register</button>
			</form>
		</div>
		</div>
	</main>

    <!-- jQuery and line numbering JavaScript -->
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.3.js" />"></script>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/register.js" />"></script>
  </body>
</html>
