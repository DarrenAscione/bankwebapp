/*
 * SUTD (Singapore)
 * 
 */

package sutdbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sg.edu.sutd.bank.webapp.service.AbstractDAOImpl;

public class DbCreator extends AbstractDAOImpl {
	protected static String dbScript = "create.sql";
	
	public static void main(String[] args) {
		try {
			verifyDatasource();
			System.out.println("Done!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * return whether datasource is verified or not;
	 * */
	private static boolean verifyDatasource() throws SQLException {
		// verify database
		String dbName = schema;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(dbUrl, username, password);
			conn.setAutoCommit(true);
			rs = conn.getMetaData().getCatalogs();
			boolean exist = false;
			while (rs.next()) {
				String database = rs.getString(1);
				if (database.equals(dbName)) {
					exist = true;
					break;
				}
			}
			rs.close();
			if (exist) {
				String sql = "DROP DATABASE " + dbName;
				System.out.println(sql);
				stmt = conn.createStatement();
			    stmt.executeUpdate(sql);
			    stmt.close();
			}
			stmt = conn.createStatement();
			String sql = "CREATE DATABASE " + dbName;
			System.out.println(sql);
			int row = stmt.executeUpdate(sql);
			if (row <= 0) {
				throw new SQLException("Cannot create database " + dbName);
			}
			executeCreateScript();
			return false;
		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			closeDb(conn, stmt, rs);
		}
	}

	private static void executeCreateScript() throws Exception {
		/* execute db script */
		String s;
		StringBuffer sb = new StringBuffer();
		ClassLoader classLoader = AbstractDAOImpl.class.getClassLoader();
		File file = new File(classLoader.getResource(dbScript).getFile());
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		while ((s = br.readLine()) != null) {
			if (!s.startsWith("--"))
			sb.append(s);
		}
		br.close();
		String[] inst = sb.toString().split(";");
		Connection conn = null;
		Statement st = null;
		try {
			conn = connectDB();
			st = conn.createStatement();
			for (int i = 0; i < inst.length; i++) {
				if (!inst[i].trim().equals("")) {
					st.executeUpdate(inst[i]);
					System.out.println(">>" + inst[i]);
				}
			}
		} finally {
			closeDb(conn, st, null);
		}
	}
	
}
