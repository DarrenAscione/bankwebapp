<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="en">
  <%@include file="pageHeader.jsp" %>

  <body>
	<%@include file="header.jsp" %>

	<div class="welcome">
		<c:if test="${not empty req_msg}">
			<h1 style="text-align: center;padding-top: 150px;">${req_msg}</h1>
		</c:if>
		<c:if test="${empty req_msg}">
			<h1 style="text-align: center;padding-top: 150px;">Welcome to the bank!</h1>
		</c:if>
		<c:remove var="req_msg" scope="session" /> 
	</div>
  </body>
</html>
