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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/")
public class DefaultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (ServletPaths.LOGOUT.equals(req.getServletPath())) {
			logout(req);
			resp.sendRedirect(getRedirectPath(ServletPaths.LOGIN));
		} else if (req.getServletPath().startsWith("/resources/")) {
			forward(req, resp);
		} else {
			forward(req, resp);
		}
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req.getServletPath(), req, resp);
	}
	
	protected void sendError(HttpServletRequest req, String msg) {
		req.getSession().setAttribute("req_error", msg);
	}
	
	protected int getUserId(HttpServletRequest req) {
		return (int) req.getSession().getAttribute("user_id");
	}
	
	protected void setUserId(HttpServletRequest req, Integer userId) {
		req.getSession(false).setAttribute("user_id", userId);
	}
	
	protected void sendMsg(HttpServletRequest req, String msg) {
		req.getSession().setAttribute("req_msg", msg);
	}
	
	private void logout(HttpServletRequest req) throws ServletException {
		req.logout();
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.setAttribute("authenticatedUser", null);
		}
	}
	
	protected void forward(String path, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, NullPointerException {
	    final RequestDispatcher view;
		try {
		    view = req.getRequestDispatcher(getPath(path));
        } catch (NullPointerException e) {
            throw new ServletException("error");
        }
        view.forward(req, resp);

    }
	
	protected void redirect(HttpServletResponse resp, String templage) throws IOException {
		resp.sendRedirect(getRedirectPath(templage));
	}

	protected String getPath(String template) {
		return "WEB-INF/jsp" + template + ".jsp";
	}
	
	protected String getRedirectPath(String template) {
		return "/sutdbank" + template;
	}
}
