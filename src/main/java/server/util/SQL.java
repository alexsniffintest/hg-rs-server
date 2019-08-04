package server.util;

import server.model.players.Client;

import java.sql.*;

public class SQL {
	public static Connection con = null;
	public static Statement stmt;
	private static long lastUsed = System.currentTimeMillis();

	public static void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(
					"", "", "");
			stmt = con.createStatement();
            		Misc.println("Connected");
		} catch (Exception e) {
			Misc.println("Connection Problem");
		}
	}

	public static ResultSet query(String s) throws SQLException {
		try {
			if (s.toLowerCase().startsWith("select")) {
				ResultSet rs = stmt.executeQuery(s);
				return rs;
			} else {
				stmt.executeUpdate(s);
			}
			return null;
		} catch (Exception e) {
			destroyConnection();
			createConnection();
		}
		return null;
	}

	public static void destroyConnection() {
		try {
			stmt.close();
			con.close();
		} catch (Exception e) {
		}
	}
	
	public static Connection getConnection() throws Exception {
		if (con == null) {
			throw new Exception("connection is null");
		}
		if (System.currentTimeMillis() - lastUsed > 300000) {
			try {
				lastUsed = System.currentTimeMillis();
				con.close();
				createConnection();
			} catch (Exception e) {
				throw new Exception("error refreshing database connection");
			}
		}
		return con;
	}

	public static boolean checkStatus(Client c) {
		try {
		Statement s = getConnection().createStatement();
		ResultSet results = s.executeQuery("SELECT * FROM `status` WHERE `username` = '" + c.playerName + "' AND `given` = '0' LIMIT 10;");
		while(results.next()) {
			c.getItems().addItem(results.getInt("item"), results.getInt("quantity"));
			Statement st = getConnection().createStatement();
			st.executeUpdate("UPDATE `status` SET `given` = '10' WHERE `id`='" + results.getInt("id") + "';");
			st.close();
		}
		s.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
}