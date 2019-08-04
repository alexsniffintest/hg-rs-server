package server.util;

import server.model.players.Client;

import java.sql.*;

public class MadTurnipConnection extends Thread {

	public static Connection con = null;
	public static Statement stm;

	public static void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://hg-rs.com/hgrscom_donate", "hgrscom_donate", "@.%-(=VG.BMi");
			stm = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			con = null;
			stm = null;
		}
	}
	
	public MadTurnipConnection(){
		
	}
	
	public void run() {
		while(true) {		
			try {
				if(con == null)
					createConnection(); 
				else
					ping();
				Thread.sleep(10000);//10 seconds
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void ping(){
		try {
			String query = "SELECT * FROM donation WHERE username = 'null'";
			query(query);
		} catch (Exception e) {
			e.printStackTrace();
			con = null;
			stm = null;
		}
	}
	
	public static void addDonateItems(final Client c,final String name){
		if(con == null){
			if(stm != null){
				try {
					stm = con.createStatement();
				} catch(Exception e){
					con = null;
					stm = null;
					//put a sendmessage here telling them to relog in 30 seconds
					return;
				}
			} else {
				//put a sendmessage here telling them to relog in 30 seconds
				return;
			}
		}
		new Thread(){
			@Override
			public void run()
			{
				try {
					String name2 = name.replaceAll(" ","_");
					String query = "SELECT * FROM donation WHERE username = '"+name2+"'";
					ResultSet rs = query(query);
					boolean b = false;
					while(rs.next()){
						int prod = Integer.parseInt(rs.getString("productid"));
						int price = Integer.parseInt(rs.getString("price"));
						c.dAmount += price;
						if(prod == 1 && price == 5) {//Perished Kit
							if(c.kits[3] == null) {
								c.sendMessage("@red@Perished@bla@ has been added to your kit list, thank you for donating!");
								c.kits[3] = "Perished";
								c.getPA().sendFrame126("@gre@" + "Perished", 16210);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Perished@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$3 worth of donator points@bla@ instead.");
								c.donatorPoints += 300;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						} else if(prod == 2 && price == 6) {//Venom 4
							if(c.kits[4] == null) {
								c.sendMessage("@red@Venom@bla@ has been added to your kit list, thank you for donating!");
								c.kits[4] = "Venom";
								c.getPA().sendFrame126("@gre@" + "Venom", 16211);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Venom@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$4 worth of donator points@bla@ instead.");
								c.donatorPoints += 400;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						} else if(prod == 3 && price == 6) {//Blizzard 4
							if(c.kits[5] == null) {
								c.sendMessage("@red@Blizzard@bla@ has been added to your kit list, thank you for donating!");
								c.kits[5] = "Blizzard";
								c.getPA().sendFrame126("@gre@" + "Blizzard", 16212);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Blizzard@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$4 worth of donator points@bla@ instead.");
								c.donatorPoints += 400;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						} else if(prod == 4 && price == 6) {//Flash 4
							if(c.kits[6] == null) {
								c.sendMessage("@red@Flash@bla@ has been added to your kit list, thank you for donating!");
								c.kits[6] = "Flash";
								c.getPA().sendFrame126("@gre@" + "Flash", 16213);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Flash@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$4 worth of donator points@bla@ instead.");
								c.donatorPoints += 400;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						} else if(prod == 5 && price == 8) {//Looter 6
							if(c.kits[7] == null) {
								c.sendMessage("@red@Looter@bla@ has been added to your kit list, thank you for donating!");
								c.kits[7] = "Looter";
								c.getPA().sendFrame126("@gre@" + "Looter", 16214);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Looter@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$6 worth of donator points@bla@ instead.");
								c.donatorPoints += 600;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						} else if(prod == 6 && price == 5) {//Jumper
							if(c.kits[10] == null) {
								c.sendMessage("@red@Jumper@bla@ has been added to your kit list, thank you for donating!");
								c.kits[10] = "Jumper";
								c.getPA().sendFrame126("@gre@" + "Jumper", 16215);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Jumper@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$3 worth of donator points@bla@ instead.");
								c.donatorPoints += 300;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						} else if(prod == 7 && price == 7) {//Skiller
							if(c.kits[11] == null) {
								c.sendMessage("@red@Skiller@bla@ has been added to your kit list, thank you for donating!");
								c.kits[11] = "Skiller";
								c.getPA().sendFrame126("@gre@" + "Skiller", 16216);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Skiller@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$5 worth of donator points@bla@ instead.");
								c.donatorPoints += 500;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						} else if(prod == 8 && price == 9) {//chaos 7
							if(c.kits[13] == null) {
								c.sendMessage("@red@Chaos@bla@ has been added to your kit list, thank you for donating!");
								c.kits[13] = "Chaos";
								c.getPA().sendFrame126("@gre@" + "Chaos", 16216);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Chaos@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$7 worth of donator points@bla@ instead.");
								c.donatorPoints += 700;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						} else if(prod == 9 && price == 9) {//Tainted 7
							if(c.kits[14] == null) {
								c.sendMessage("@red@Tainted@bla@ has been added to your kit list, thank you for donating!");
								c.kits[14] = "Tainted";
								c.getPA().sendFrame126("@gre@" + "Tainted", 16216);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Tainted@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$7 worth of donator points@bla@ instead.");
								c.donatorPoints += 700;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						} else if(prod == 10 && price == 1) {//$1 of donator points
							c.sendMessage("Thank you for donating.");
							c.sendMessage("You've been given @red@$1@bla@ worth of donator points");
							c.donatorPoints += 100;
							c.sendMessage("You now have @blu@" + c.donatorPoints + "@bla@ donator points!");
							b = true;
						} else if(prod == 11 && price == 5) {//$5 donator points
							c.sendMessage("Thank you for donating.");
							c.sendMessage("You've been given @red@$5@bla@ worth of donator points");
							c.donatorPoints += 550;
							c.sendMessage("You now have @blu@" + c.donatorPoints + "@bla@ donator points!");
							b = true;
						} else if(prod == 12 && price == 10) {//$10 donator points 10
							c.sendMessage("Thank you for donating.");
							c.sendMessage("You've been given @red@$10@bla@ worth of donator points");
							c.donatorPoints += 1100;
							c.sendMessage("You now have @blu@" + c.donatorPoints + "@bla@ donator points!");
							b = true;
						} else if(prod == 13 && price == 7) {//Necromorpher
							if(c.kits[16] == null) {
								c.sendMessage("@red@Necromorpher@bla@ has been added to your kit list, thank you for donating!");
								c.kits[16] = "Necromorpher";
								c.getPA().sendFrame126("@gre@" + "Necromorpher", 16216);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Necromorpher@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$5 worth of donator points@bla@ instead.");
								c.donatorPoints += 500;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						} else if(prod == 14 && price == 8) {//Knight 6
							if(c.kits[17] == null) {
								c.sendMessage("@red@Knight@bla@ has been added to your kit list, thank you for donating!");
								c.kits[17] = "Knight";
								c.getPA().sendFrame126("@gre@" + "Knight", 16216);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Knight@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$6 worth of donator points@bla@ instead.");
								c.donatorPoints += 600;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						} else if(prod == 15 && price == 7) {//wretched
							if(c.kits[19] == null) {
								c.sendMessage("@red@Wretched@bla@ has been added to your kit list, thank you for donating!");
								c.kits[19] = "Wretched";
								c.getPA().sendFrame126("@gre@" + "Wretched", 16216);
								b = true;
							} else {
								c.sendMessage("You donated for @red@Wretched@bla@ but it appears you already have it.");
								c.sendMessage("You've been given @red@$5 worth of donator points@bla@ instead.");
								c.donatorPoints += 500;
								c.sendMessage("Thank you for donating.");
								b = true;
							}
						}
						c.getPA().sendFrame126("Donator Points: @gre@" +c.donatorPoints, 16242);
					}
					if(b){
						query("DELETE FROM `donation` WHERE `username` = '"+name2+"';");
						//query("DELETE FROM `Stats` WHERE `User` = '"+name2+"';");
						//query("INSERT INTO Stats (User, Amount) VALUES ('"+name2+"', '"+c.dAmount+"')");
					}
				} catch (Exception e) {
					e.printStackTrace();
					con = null;
					stm = null;
				}
			}
		}.start();
	}
	
	public static ResultSet query(String s) throws SQLException {
		try {
			if (s.toLowerCase().startsWith("select")) {
				ResultSet rs = stm.executeQuery(s);
				return rs;
			} else {
				stm.executeUpdate(s);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			con = null;
			stm = null;
		}
		return null;
	}
}