package server.model.players;
import java.sql.*;
public class Online {
	public static Connection con;
	public static Statement stm;
	public static boolean connected;
	public static String Host = "roatpkz.com";
	public static String User = "gretar";
	public static String Pass = "password123";

	public static void process() {
		try {
			Class.forName(Driver).newInstance();
			Connection con = DriverManager.getConnection(Host, User, Pass);
			stm = con.createStatement();
			connected = true;
		} catch(Exception e) {
			connected = false;
			e.printStackTrace();
		}
	}

public static ResultSet query(String s)
        throws SQLException
    {
        if(s.toLowerCase().startsWith("select"))
        {
            ResultSet resultset = stm.executeQuery(s);
            return resultset;
        }
        try
        {
            stm.executeUpdate(s);
            return null;
        }
        catch(Exception e)
        {
            destroy();
        }
        process();
        return null;
    }
	public static void destroy() {
		try {
			stm.close();
			con.close();
			connected = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean insert(Client c) {
		try {
			query("INSERT INTO `serveronline` VALUES ('" + c.playerName + "')");
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean delete(Client c) {
		try {
			query("DELETE FROM `serveronline` WHERE playerName = '"+c.playerName+"')");
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean empty() {
		try {
			query("TRUNCATE TABLE `serveronline`");
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static String Driver = "com.mysql.jdbc.Driver";
}