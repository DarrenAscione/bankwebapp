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

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sg.edu.sutd.bank.webapp.commons.ServiceException;
import sg.edu.sutd.bank.webapp.model.ClientInfo;
import sg.edu.sutd.bank.webapp.model.Role;
import sg.edu.sutd.bank.webapp.model.User;
import sg.edu.sutd.bank.webapp.model.UserRole;
import sg.edu.sutd.bank.webapp.service.ClientInfoDAO;
import sg.edu.sutd.bank.webapp.service.ClientInfoDAOImpl;
import sg.edu.sutd.bank.webapp.service.EmailService;
import sg.edu.sutd.bank.webapp.service.EmailServiceImp;
import sg.edu.sutd.bank.webapp.service.UserDAO;
import sg.edu.sutd.bank.webapp.service.UserDAOImpl;
import sg.edu.sutd.bank.webapp.service.UserRoleDAO;
import sg.edu.sutd.bank.webapp.service.UserRoleDAOImpl;

/**
 * @author SUTD
 */
@WebServlet("/register")
public class RegisterServlet extends DefaultServlet {
	private static final long serialVersionUID = 1L;
	private ClientInfoDAO clientAccountDAO = new ClientInfoDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
	private EmailService emailService = new EmailServiceImp();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		User user = new User();
		user.setUserName(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));

		ClientInfo clientAccount = new ClientInfo();
		clientAccount.setFullName(request.getParameter("fullName"));
		clientAccount.setFin(request.getParameter("fin"));
		clientAccount.setDateOfBirth(Date.valueOf(request.getParameter("dateOfBirth")));
		clientAccount.setOccupation(request.getParameter("occupation"));
		clientAccount.setMobileNumber(request.getParameter("mobileNumber"));
		clientAccount.setAddress(request.getParameter("address"));
		clientAccount.setEmail(request.getParameter("email"));
		clientAccount.setUser(user);
		
		try {
			userDAO.create(user);
			clientAccountDAO.create(clientAccount);
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(Role.client);
			userRoleDAO.create(userRole);
			emailService.sendMail(clientAccount.getEmail(), "SutdBank registration", "Thank you for the registration!");
			sendMsg(request, "You are successfully registered...");
			redirect(response, ServletPaths.WELCOME);
		} catch (ServiceException e) {
			sendError(request, e.getMessage());
			forward(request, response);
		}
	}
}
