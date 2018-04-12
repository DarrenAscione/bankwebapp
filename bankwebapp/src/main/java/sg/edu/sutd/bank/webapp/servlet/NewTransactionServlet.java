/*
 * Copyright 2017 SUTD Licensed under the
	Educational Community License, Version 2.0 (the "License"); you may
	not use this file except in compliance with the License. You may
	obtain a copy of the License at

https://opensource.org/licenses/ECL-2.0

	Unless required by applicable law or agreed to in writing,
	software distributed under the License is distributed on an "AS IS"
	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
	or implied. See the License for the specific language governing
	permissions and limitations under the License.
 */

package sg.edu.sutd.bank.webapp.servlet;

import static sg.edu.sutd.bank.webapp.servlet.ServletPaths.NEW_TRANSACTION;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sg.edu.sutd.bank.webapp.commons.ServiceException;
import sg.edu.sutd.bank.webapp.model.ClientTransaction;
import sg.edu.sutd.bank.webapp.model.User;
import sg.edu.sutd.bank.webapp.service.ClientTransactionDAO;
import sg.edu.sutd.bank.webapp.service.ClientTransactionDAOImpl;
import sg.edu.sutd.bank.webapp.model.ClientInfo;
import sg.edu.sutd.bank.webapp.service.ClientInfoDAO;
import sg.edu.sutd.bank.webapp.service.ClientInfoDAOImpl;

@WebServlet(NEW_TRANSACTION)
public class NewTransactionServlet extends DefaultServlet {
	private static final long serialVersionUID = 1L;
	private ClientTransactionDAO clientTransactionDAO = new ClientTransactionDAOImpl();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			ClientTransaction clientTransaction = new ClientTransaction();
			ClientInfo clientInfo = new ClientInfo();
			User user = new User(getUserId(req));
			clientTransaction.setUser(user);
			clientInfo.setUser(user);
			BigDecimal amount = new BigDecimal(req.getParameter("amount"));
			clientTransaction.setAmount(amount);
			clientTransaction.setTransCode(req.getParameter("transcode"));
			clientTransaction.setToAccountNum(req.getParameter("toAccountNum"));
			clientTransactionDAO.create(clientTransaction);
			redirect(resp, ServletPaths.CLIENT_DASHBOARD_PAGE);
		} catch (ServiceException e) {
			sendError(req, e.getMessage());
			forward(req, resp);
		}
	}
}
