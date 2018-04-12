<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="en">
  <%@include file="pageHeader.jsp"%>
  <body>
	<%@include file="header.jsp"%>
	
	<main id="content" class="mainContent sutd-template" role="main">
	<div class="container">
		<%@include file="errorMessage.jsp"%>
		<div id="accountBalance">
			<h3>Account Balance:  </h3>
			<div>${clientInfo.account.amount}</div>
		</div>
		<div id="transHistory">
			<h3>Transaction History:  </h3>
			<table border="1" cellpadding="5" class="commonTable">
				<tr>
					<th style="width: 150px">Transaction code</th>
					<th style="width: 150px">To (account number)</th>
					<th style="width: 150px">Datatime</th>
					<th style="width: 150px">Amount</th>
					<th style="width: 150px">Status</th>
				</tr>
				<c:forEach var="trans" items="${clientInfo.transactions}">
					<tr>
						<td>${trans.transCode}</td>
						<td>${trans.toAccountNum}</td>
						<td>${trans.dateTime}</td>
						<td>${trans.amount}</td>
						<c:if test="${empty trans.status}">
							<td>Waiting</td>
						</c:if>
						<c:if test="${not empty trans.status}">
							<td>${trans.status}</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div id="createTransaction" style="padding-top: 50px">
			<form id="registrationForm" action="newTransaction" method="get">
				<button id="createTransBtn" type="submit" class="btn btn-default">New Transaction</button>
			</form>
		</div>
	</div>
	</main>
  </body>
</html>
