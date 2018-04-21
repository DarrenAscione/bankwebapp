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
import java.util.ArrayList;
import java.util.List;

import sg.edu.sutd.bank.webapp.commons.ServiceException;
import sg.edu.sutd.bank.webapp.model.ClientAccount;
import sg.edu.sutd.bank.webapp.model.ClientInfo;
import sg.edu.sutd.bank.webapp.model.ClientTransaction;
import sg.edu.sutd.bank.webapp.model.User;


public class ClientInfoDAOImpl extends AbstractDAOImpl implements ClientInfoDAO {
	private ClientTransactionDAO transactionDAO = new ClientTransactionDAOImpl();

	@Override
	public void create(ClientInfo account) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		try {
			ps = prepareStmt(conn, "INSERT INTO CLIENT_INFO(full_name, fin, date_of_birth, occupation, mobile_number, address, email, user_id)"
					+ " VALUES(?,?,?,?,?,?,?,?)");
			int idx = 1;
			ps.setString(idx++, account.getFullName());
			ps.setString(idx++, account.getFin());
			ps.setDate(idx++, account.getDateOfBirth());
			ps.setString(idx++, account.getOccupation());
			ps.setString(idx++, account.getMobileNumber());
			ps.setString(idx++, account.getAddress());
			ps.setString(idx++, account.getEmail());
			ps.setInt(idx++, account.getUser().getId());
			executeInsert(account, ps);
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, null);
		}
	}

	@Override
	public List<ClientInfo> loadWaitingList() throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT acc.*, u.* FROM client_info acc, user u WHERE acc.user_id = u.id and u.status is null");
			rs = ps.executeQuery();
			List<ClientInfo> result = new ArrayList<ClientInfo>();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("u.id"));
				user.setUserName(rs.getString("u.user_name"));
				user.setStatus(rs.getString("u.status"));
				ClientInfo clientAccount = new ClientInfo();
				clientAccount.setId(rs.getInt("acc.id"));
				clientAccount.setFullName(rs.getString("acc.full_name"));
				clientAccount.setFin(rs.getString("acc.fin"));
				clientAccount.setDateOfBirth(rs.getDate("acc.date_of_birth"));
				clientAccount.setOccupation(rs.getString("acc.occupation"));
				clientAccount.setMobileNumber(rs.getString("acc.mobile_number"));
				clientAccount.setAddress(rs.getString("acc.address"));
				clientAccount.setEmail(rs.getString("acc.email"));
				clientAccount.setUser(user);
				result.add(clientAccount);
			}
			return result;
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}

	@Override
	public ClientInfo loadAccountInfo(String userName) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ClientInfo clientInfo = null;
		try {
			ps = conn.prepareStatement(
					"SELECT info.*, acc.*, u.* FROM client_info info, user u, client_account acc WHERE acc.user_id = u.id and info.user_id = u.id and u.user_name=?");
			int idx = 1;
			ps.setString(idx ++, userName);
			rs = ps.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("u.id"));
				user.setUserName(rs.getString("u.user_name"));
				ClientAccount account = new ClientAccount();
				account.setUser(user);
				account.setId(rs.getInt("acc.id"));
				account.setAmount(rs.getBigDecimal("acc.amount"));
				clientInfo = new ClientInfo();
				clientInfo.setId(rs.getInt("info.id"));
				clientInfo.setUser(user);
				clientInfo.setAccount(account);
			} else {
				throw new SQLException("No data found for user " + userName);
			}
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
		if (clientInfo != null) {
			List<ClientTransaction> transactions = transactionDAO.load(clientInfo.getUser());
			clientInfo.setTransactions(transactions);
		}
		return clientInfo;
	}
	
	
}
