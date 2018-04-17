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

package sg.edu.sutd.bank.webapp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mockito.internal.matchers.Null;
import sg.edu.sutd.bank.webapp.commons.ServiceException;
import sg.edu.sutd.bank.webapp.model.User;

public class UserDAOImpl extends AbstractDAOImpl implements UserDAO {

	@Override
	public User loadUser(String userName) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT id, user_name, status FROM user WHERE user_name=?");
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("user_name"));
				user.setStatus(rs.getString("status"));
				return user;
			}
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
		return null;
	}

	@Override
	public void create(User user) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = prepareStmt(conn, "INSERT INTO user(user_name, password) VALUES(?,?)");
			int idx = 1;
			ps.setString(idx++, user.getUserName());
			ps.setString(idx++, user.getPassword());
			executeInsert(user, ps);
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}

	@Override
    public void blockUser(User user) throws ServiceException {
        Connection conn = connectDB();
        PreparedStatement ps = null;
        try {
            ps = prepareStmt(conn, "UPDATE status = BLOCKED WHERE user_name = ?");
            int idx = 1;
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ps.setString(idx++, user.getUserName());
            executeUpdate(ps);
        } catch (SQLException e) {
            throw ServiceException.wrap(e);
        } finally {
            closeDb(conn, ps, null);
        }
    }


    /**
	 * UPDATE config
		   SET config_value = CASE config_name 
		                      WHEN 'name1' THEN 'value' 
		                      WHEN 'name2' THEN 'value2' 
		                      ELSE config_value
		                      END
		 WHERE config_name IN('name1', 'name2');
	 * @throws ServiceException 
	 */
	@Override
	public void updateDecision(List<User> users) throws ServiceException {
		StringBuilder query = new StringBuilder("UPDATE user SET status = Case id ");
		for (User user : users) {
			query.append(String.format("WHEN %d THEN '%s' ", user.getId(), user.getStatus().name()));
		}
		query.append("ELSE status ")
			.append("END ")
			.append("WHERE id IN(");
		for (int i = 0; i < users.size(); i++) {
			query.append(users.get(i).getId());
			if (i < users.size() - 1) {
				query.append(", ");
			}
		}
		query.append(");");
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = prepareStmt(conn, query.toString());
			int rowNum = ps.executeUpdate();
			if (rowNum == 0) {
				throw new SQLException("Update failed, no rows affected!");
			}
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}

}
