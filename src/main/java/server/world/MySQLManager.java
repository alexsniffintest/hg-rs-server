package server.world;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Represents the MySQL manager.
 * @author Derive
 *
 */
public class MySQLManager {

	/**
	 * The logger.
	 */
	private Logger logger = Logger.getLogger(MySQLManager.class.getName());
	
	/**
	 * The singleton instance.
	 */
	private static MySQLManager singleton = new MySQLManager(
			MySQLConstants.MYSQL_HOST, MySQLConstants.MYSQL_DATABASE,
			MySQLConstants.MYSQL_USERNAME, MySQLConstants.MYSQL_PASSWORD);
	
	/**
	 * The database connection.
	 */
	private Connection connection;
	
	/**
	 * The statement.
	 */
	private Statement statement;
	
	/**
	 * Gets the singleton instance.
	 * @return
	 * 		The singleton instance.
	 */
	public static MySQLManager getSingleton() {
		return singleton;
	}
	
	/**
	 * Creates an instance of MySQLManager.
	 * @param host
	 * 		The host.
	 * @param database
	 * 		The database.
	 * @param username
	 * 		The username.
	 * @param password
	 * 		The password.
	 */
	public MySQLManager(String host, String database, String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, username, password);
			statement = connection.createStatement();
			statement.setEscapeProcessing(true);
		} catch (Exception e) {
			logger.info("MySQL manager failed to start!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Executes a get query.
	 * @param query
	 * 		The query.
	 * @return
	 * 		The results.
	 */
	public ResultSet getQuery(String query) {
		try {
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Prepares a statement.
	 * @param statement
	 * 		The string that will be returned as a statement.
	 * @return
	 * 		A statement.
	 */
	public PreparedStatement prepareStatement(String statement) {
		try {
			return connection.prepareStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Executes an update query.
	 * @param query
	 * 		The query.
	 * @return
	 * 		The results.
	 */
	public int updateQuery(String query) {
		try {
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
