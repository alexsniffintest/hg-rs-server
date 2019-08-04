package server.model.players;

import java.io.*;
 
public class serverStatistics {
	
	private static Client c;
	public static String fileName = "./Data/ServerStatistics.txt";
	
	public static void serverStats()
	{
		PrintWriter statistics = null;
		try
		{       
			statistics = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		}
		catch (IOException e)
		{
			System.out.println("Problem writing " + fileName);
		}

		statistics.println("[ Hunger Games Server Statisics ]"); 

		statistics.println("GamesPlayed = " + c.totalGamesPlayed);
		statistics.println("PlayersKilled = " + c.totalPlayersKilled);
		statistics.println("PlayersDied = " + c.totalPlayersDied);
		statistics.println("ChestsLooted = " + c.totalChestsLooted);
		statistics.println("TotalTraps = " + c.totalTraps);
		statistics.println("TotalGrandChests = " + c.totalGrandChests);
		statistics.println("TotalExp = " + c.totalExp);
		statistics.println("Lottery = " + c.lottery);

		statistics.close();
	}
	
	public static void readServerStats()
	{
		try
		{	
			String str1 = "", str2 = "", str3 = "";
			String[] arrayOfString = new String[3];
			int i = 0, j = 1;
			
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			while ((str1 = in.readLine()) != null)
			{
				str1 = str1.trim();
				int k = str1.indexOf(" = ");
				
				if (k > -1) {
					str2 = str1.substring(0, k);
					str2 = str2.trim();
					str3 = str1.substring(k + 3);
					str3 = str3.trim();
					arrayOfString = str3.split("\t");
					switch (j)
					{
						case 1:
						if (str2.equals("GamesPlayed"))
						{
							c.totalGamesPlayed = Integer.parseInt(str3);
						}
						
						if (str2.equals("PlayersKilled"))
						{
							c.totalPlayersKilled = Integer.parseInt(str3);
						}
						
						if (str2.equals("PlayersDied"))
						{
							c.totalPlayersDied = Integer.parseInt(str3);
						}
						
						if (str2.equals("ChestsLooted"))
						{
							c.totalChestsLooted = Integer.parseInt(str3);
						}
						
						if (str2.equals("TotalTraps"))
						{
							c.totalTraps = Integer.parseInt(str3);
						}
						
						if (str2.equals("TotalGrandChests"))
						{
							c.totalGrandChests = Integer.parseInt(str3);
						}
						
						if (str2.equals("TotalExp"))
						{
							c.totalExp = Long.parseLong(str3);
						}
						
						if (str2.equals("Lottery"))
						{
							c.lottery = Integer.parseInt(str3);
						}
					}
				}
			}
			in.close();
		}
		catch (FileNotFoundException e){
			c.totalGamesPlayed = 0;
			c.totalPlayersKilled = 0;
			c.totalPlayersDied = 0;
			c.totalChestsLooted = 0;
			c.totalTraps = 0;
			c.totalGrandChests = 0;
			c.totalExp = 0;
			c.lottery = 0;
			serverStats();
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}
}