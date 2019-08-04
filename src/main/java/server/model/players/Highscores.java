package server.model.players;

import java.sql.*;

public class Highscores {
	public static Connection con;
	public static Statement stm;
    public static boolean connected;
	
	public static String Host = "";
	public static String User = "";
	public static String Pass = "";
	
    public static void process() {
        try
        {
            Class.forName(Driver).newInstance();
			Connection con = DriverManager.getConnection(Host, User, Pass);
			stm = con.createStatement();
            connected = true;
        }
        catch(Exception e)
        {
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
        try
        {
           // stm.close();
           // con.close();
           //connected = false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

	public static boolean highSave(Client c) {
        try
        {
			//query("DELETE FROM `HighScore` WHERE playerName = '"+c.playerName+"';");
			//query("INSERT INTO `HighScore` (`playerName`,`games_played`,`wins`,`insane_wins`,`extreme_wins`,`tough_wins`,`kills`,`deaths`, `kits`, `perks`, `chests`, `votes`, `rights`) VALUES ('"+c.playerName+"',"+c.totalGames+","+c.totalGameWins+",'"+c.insaneWins+"','"+c.extremeWins+"','"+c.toughWins+"','"+c.KC+"','"+c.DC+"','"+c.kitAmount()+"','"+c.perkAmount()+"','"+c.chestsOpened+"','"+c.voteTimes+"','"+c.playerRights+"');");
		}
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

	public static String Driver = "com.mysql.jdbc.Driver";
}