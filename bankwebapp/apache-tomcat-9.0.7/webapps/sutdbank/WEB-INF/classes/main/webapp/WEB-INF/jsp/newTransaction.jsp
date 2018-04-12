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
		<div id="createTransaction">
			<form id="newTransactionForm" action="newTransaction" method="post">
				<div id="input-group-transcode" class="form-group">
					<label for="transcode" class="control-label">Transaction code</label>
					<input type="text" class="form-control" id="transcode" name="transcode" placeholder="Transaction Code">
				</div>
				<div id="input-group-toAccount" class="form-group">
					<label for="toAccountNum" class="control-label">To (account number)</label>
					<input type="text" class="form-control" id="toAccountNum" name="toAccountNum" placeholder="To Account Number">
				</div>
				<div id="input-group-amount" class="form-group">
					<label for="amount" class="control-label">Amount</label>
					<input type="text" class="form-control" id="amount" name="amount" placeholder="amount">
				</div>
				<button id="createTransBtn" type="submit" class="btn btn-default">Submit</button>
			</form>
		</div>
	</div>
	</main>
  </body>
</html>
