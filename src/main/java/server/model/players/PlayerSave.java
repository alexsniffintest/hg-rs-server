package server.model.players;

import server.Server;
import server.util.Misc;

import java.io.*;

public class PlayerSave
{

	/**
	*Loading
	**/
	public static int loadGame(Client p, String playerName, String playerPass) {
		String line = "";
		String token = "";
		String token2 = "";
		String[] token3 = new String[3];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		boolean File1 = false;
		
		try {
			characterfile = new BufferedReader(new FileReader("./Data/characters/"+playerName+".txt"));
			File1 = true;
		} catch(FileNotFoundException fileex1) {
		}
		
		if (File1) {
			//new File ("./characters/"+playerName+".txt");
		} else {
			Misc.println(playerName+": character file not found.");
			p.newPlayer = false;
			return 0;
		}
		try {
			line = characterfile.readLine();
		} catch(IOException ioexception) {
			Misc.println(playerName+": error loading file.");
			return 3;
		}
		while(EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token3 = token2.split("\t");
				switch (ReadMode) {
				case 1:
					 if (token.equals("character-password")) {
						if (playerPass.equalsIgnoreCase(token2) || Misc.basicEncrypt(playerPass).equals(token2)) {
							playerPass = Misc.basicEncrypt(playerPass);
						} else {
							return 3;
						}
					}
					break;
				case 2:
					if (token.equals("character-height")) {
						p.heightLevel = Integer.parseInt(token2);
					} else if (token.equals("character-posx")) {
						p.teleportToX = (Integer.parseInt(token2) <= 0 ? 3210 : Integer.parseInt(token2));
					} else if (token.equals("character-posy")) {
						p.teleportToY = (Integer.parseInt(token2) <= 0 ? 3424 : Integer.parseInt(token2));
					} else if (token.equals("character-rights")) {
						p.playerRights = Integer.parseInt(token2);
					} else if (token.equals("character-title")) {
						p.playerTitle = Integer.parseInt(token2);
					} else if (token.equals("games")) {
						p.totalGames = Integer.parseInt(token2);
					} else if (token.equals("wins")) {
						p.totalGameWins = Integer.parseInt(token2);
					} else if (token.equals("insaneWins")) {
						p.insaneWins = Integer.parseInt(token2);
					} else if (token.equals("extremeWins")) {
						p.extremeWins = Integer.parseInt(token2);
					} else if (token.equals("toughWins")) {
						p.toughWins = Integer.parseInt(token2);
					} else if (token.equals("chests")) {
						p.chestsOpened = Integer.parseInt(token2);
					} else if (token.equals("hg-exp")) {
						p.totalHungerGameExp = Integer.parseInt(token2);
					} else if (token.equals("hg-kit")) {
					//p.kits[Integer.parseInt(token3[0])] = token3[1];
						p.rememberedKit = token2;
					} else if(token.equals("spectateEx")) {
						p.spectateEx = Integer.parseInt(token2);
					} else if(token.equals("setcc")) {
						p.myCC = token2;
					} else if (token.equals("freePartyHat")) {
						p.freePartyHat = Integer.parseInt(token2);
					} else if(token.equals("votes")) {
						p.voteTimes = Integer.parseInt(token2);
					} else if(token.equals("lastGame")) {
						p.lastGame = Integer.parseInt(token2);
					} else if(token.equals("startChristmas")) {
						p.startChristmas = Integer.parseInt(token2);
					} else if(token.equals("gifts")) {
						p.gifts = Integer.parseInt(token2);
					} else if(token.equals("vote-Chest")) {
						p.voteChest = Integer.parseInt(token2);
					} else if(token.equals("connected-from")) {
						p.lastConnectedFrom.add(token2);
					} else if (token.equals("skull-timer")) {
						p.skullTimer = Integer.parseInt(token2);
					} else if (token.equals("magic-book")) {
						p.playerMagicBook = Integer.parseInt(token2);
					 } else if (token.equals("special-amount")) {
						p.specAmount = Double.parseDouble(token2);					
					} else if (token.equals("teleblock-length")) {
						p.teleBlockDelay = System.currentTimeMillis();
						p.teleBlockLength = Integer.parseInt(token2);							
					} else if (token.equals("Donate-Amount")) {
						p.donatorPoints = Integer.parseInt(token2);							
					} else if (token.equals("Vote-Level")) {
						p.voteLevels = Integer.parseInt(token2);
					} else if (token.equals("Vote-Points")) {
						p.votePoints = Integer.parseInt(token2);
					} else if (token.equals("autoRet")) {
						p.autoRet = Integer.parseInt(token2);					
					} else if (token.equals("flagged")) {
						p.accountFlagged = Boolean.parseBoolean(token2);
					} else if (token.equals("fightMode")) {
						p.fightMode = Integer.parseInt(token2);
					} else if (token.equals("tradeTimer")) {
						p.tradeTimer = Integer.parseInt(token2);
					} else if (token.equals("memberStatus")) {
						p.memberStatus = Integer.parseInt(token2);
					} else if (token.equals("donatorChest")) {
						p.donatorChest = Integer.parseInt(token2);
					} else if (token.equals("betaPlayer")) {
						p.betaPlayer = Integer.parseInt(token2);
					} else if (token.equals("KC")) {
						p.KC = Integer.parseInt(token2);
					} else if (token.equals("DC")) {
						p.DC = Integer.parseInt(token2);
					} else if (token.equals("isJailed")) {
						p.isJailed = Boolean.parseBoolean(token2);
					} else if (token.equals("ban-marks")) {
						p.BlackMarks = Integer.parseInt(token2);
					} else if (token.equals("vote")) {
						p.vote = Integer.parseInt(token2);
					} else if (token.equals("abyssKc")) {
						p.abyssKc = Integer.parseInt(token2);
					} else if (token.equals("dragonKc")) {
						p.dragonKc = Integer.parseInt(token2);
					} else if (token.equals("votingPoints")) {
						p.votingPoints = Integer.parseInt(token2);
					} else if (token.equals("perkOne")) {
						p.perkOne = Integer.parseInt(token2);
					} else if (token.equals("perkTwo")) {
						p.perkTwo = Integer.parseInt(token2);
					} else if (token.equals("perkThreeMelee")) {
						p.perkThreeMelee = Integer.parseInt(token2);
					} else if (token.equals("perkFourRange")) {
						p.perkFourRange = Integer.parseInt(token2);
					} else if (token.equals("perkFiveMagic")) {
						p.perkFiveMagic = Integer.parseInt(token2);
					} else if (token.equals("perkSixPray")) {
						p.perkSixPray = Integer.parseInt(token2);
					} else if (token.equals("perkSevenIce")) {
						p.perkSevenIce = Integer.parseInt(token2);
					} else if (token.equals("perkEightStat")) {
						p.perkEightStat = Integer.parseInt(token2);
					} else if (token.equals("perkKaboom")) {
						p.perkKaboom = Integer.parseInt(token2);
					} else if (token.equals("perkGifted")) {
						p.perkGifted = Integer.parseInt(token2);
					} else if (token.equals("perkSwitch")) {
						p.perkSwitch = Integer.parseInt(token2);
					} else if (token.equals("perkVeng")) {
						p.perkVeng = Integer.parseInt(token2);
					} else if (token.equals("perkConnect")) {
						p.perkConnect = Integer.parseInt(token2);
					} else if (token.equals("perkConquer")) {
						p.perkConquer = Integer.parseInt(token2);
					} else if (token.equals("perkYell")) {
						p.perkYell = Integer.parseInt(token2);
					} else if (token.equals("perkVengTwo")) {
						p.perkVengTwo = Integer.parseInt(token2);
					} else if (token.equals("perkLazy")) {
						p.perkLazy = Integer.parseInt(token2);
					} else if (token.equals("pumpkinPerk")) {
						p.pumpkinPerk = Integer.parseInt(token2);
					} else if (token.equals("beerPerk")) {
						p.beerPerk = Integer.parseInt(token2);
					} else if (token.equals("noSmuggle")) {
						p.noSmuggle = Integer.parseInt(token2);
					} else if (token.equals("firstLogin")) {
						p.firstLogin = Integer.parseInt(token2);
					} else if (token.equals("resetPass")) {
						p.resetPass = Integer.parseInt(token2);
					} else if (token.equals("summonId")) {
						p.summonId = Integer.parseInt(token2);
					} else if (token.equals("has-npc")) {
						p.hasNpc = Boolean.parseBoolean(token2);
					}
					break;
				case 3:
					if (token.equals("character-equip")) {
						p.playerEquipment[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerEquipmentN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 4:
					if (token.equals("character-look")) {
						p.playerAppearance[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
					} 
					break;
				case 5:
					if (token.equals("character-skill")) {
						p.playerLevel[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerXP[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 6:
					if (token.equals("character-item")) {
						p.playerItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 7:
					if (token.equals("character-bank")) {
						p.bankItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.bankItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 8:
					 if (token.equals("character-friend")) {
						p.friends[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
					} 
					break;
				case 9:
					if (token.equals("character-kit")) {
						p.kits[Integer.parseInt(token3[0])] = token3[1];
					} 
					break;
				case 10:
					if (token.equals("character-ach")) {
						p.achievements[Integer.parseInt(token3[0])][0] = Integer.parseInt(token3[1]);
					} 
					break;
				}
			} else {
				if (line.equals("[ACCOUNT]")) {		ReadMode = 1;
				} else if (line.equals("[CHARACTER]")) {	ReadMode = 2;
				} else if (line.equals("[EQUIPMENT]")) {	ReadMode = 3;
				} else if (line.equals("[LOOK]")) {		ReadMode = 4;
				} else if (line.equals("[SKILLS]")) {		ReadMode = 5;
				} else if (line.equals("[ITEMS]")) {		ReadMode = 6;
				} else if (line.equals("[BANK]")) {		ReadMode = 7;
				} else if (line.equals("[FRIENDS]")) {		ReadMode = 8;
				} else if (line.equals("[KITS]")) {		ReadMode = 9;
				} else if (line.equals("[ACHEIVEMENTS]")) {		ReadMode = 10;
				} else if (line.equals("[EOF]")) {		try { characterfile.close(); } catch(IOException ioexception) { } return 1;
				}
			}
			try {
				line = characterfile.readLine();
			} catch(IOException ioexception1) { EndOfFile = true; }
		}
		try { characterfile.close(); } catch(IOException ioexception) { }
		return 13;
	}
	
	
	
	
	/**
	*Saving
	**/
	public static boolean sa1veGame(Client p) {
		if(!p.saveFile || p.newPlayer || !p.saveCharacter) {
			//System.out.println("first");
			return false;
		}
		if(p.playerName == null || Server.playerHandler.players[p.playerId] == null) {
			//System.out.println("second");
			return false;
		}
		p.playerName = p.playerName2;
		int tbTime = (int)(p.teleBlockDelay - System.currentTimeMillis() + p.teleBlockLength);
		if(tbTime > 300000 || tbTime < 0){
			tbTime = 0;
		}
		
		BufferedWriter characterfile = null;
		try {
			characterfile = new BufferedWriter(new FileWriter("./Data/characters/"+p.playerName+".txt"));
			
			/*ACCOUNT*/
			characterfile.write("HG ACCOUNT FILE", 0, 15);
			characterfile.newLine();
			characterfile.write("[ACCOUNT]", 0, 9);
			characterfile.newLine();
			characterfile.write("character-username = ", 0, 21);
			characterfile.write(p.playerName, 0, p.playerName.length());
			characterfile.newLine();
			characterfile.write("character-password = ", 0, 21);
			characterfile.write(Misc.basicEncrypt(p.playerPass), 0, Misc.basicEncrypt(p.playerPass).length());
			characterfile.newLine();
			characterfile.newLine();
			
			/*CHARACTER*/
			characterfile.write("[CHARACTER]", 0, 11);
			characterfile.newLine();
			characterfile.write("character-height = ", 0, 19);
			characterfile.write(Integer.toString(p.heightLevel), 0, Integer.toString(p.heightLevel).length());
			characterfile.newLine();
			characterfile.write("character-posx = ", 0, 17);
			characterfile.write(Integer.toString(p.absX), 0, Integer.toString(p.absX).length());
			characterfile.newLine();
			characterfile.write("character-posy = ", 0, 17);
			characterfile.write(Integer.toString(p.absY), 0, Integer.toString(p.absY).length());
			characterfile.newLine();
			characterfile.write("character-rights = ", 0, 19);
			characterfile.write(Integer.toString(p.playerRights), 0, Integer.toString(p.playerRights).length());
			characterfile.newLine();
			characterfile.write("UUID = ", 0, 7);
			characterfile.write(p.UUID, 0, p.UUID.length());
			characterfile.newLine();
			characterfile.write("character-title = ", 0, 18);
			characterfile.write(Integer.toString(p.playerTitle), 0, Integer.toString(p.playerTitle).length());
			characterfile.newLine();
			for (int i = 0; i < p.lastConnectedFrom.size(); i++) {
				characterfile.write("connected-from = ", 0, 17);
				characterfile.write(p.lastConnectedFrom.get(i), 0, p.lastConnectedFrom.get(i).length());
				characterfile.newLine();
			}
			characterfile.write("games = ", 0, 8);
			characterfile.write(Integer.toString(p.totalGames), 0, Integer.toString(p.totalGames).length());
			characterfile.newLine();
			characterfile.write("wins = ", 0, 7);
			characterfile.write(Integer.toString(p.totalGameWins), 0, Integer.toString(p.totalGameWins).length());
			characterfile.newLine();
			characterfile.write("insaneWins = ", 0, 13);
			characterfile.write(Integer.toString(p.insaneWins), 0, Integer.toString(p.insaneWins).length());
			characterfile.newLine();
			characterfile.write("extremeWins = ", 0, 14);
			characterfile.write(Integer.toString(p.extremeWins), 0, Integer.toString(p.extremeWins).length());
			characterfile.newLine();
			characterfile.write("toughWins = ", 0, 12);
			characterfile.write(Integer.toString(p.toughWins), 0, Integer.toString(p.toughWins).length());
			characterfile.newLine();
			characterfile.write("chests = ", 0, 9);
			characterfile.write(Integer.toString(p.chestsOpened), 0, Integer.toString(p.chestsOpened).length());
			characterfile.newLine();
			characterfile.write("hg-exp = ", 0, 9);
			characterfile.write(Integer.toString(p.totalHungerGameExp), 0, Integer.toString(p.totalHungerGameExp).length());
			characterfile.newLine();
			characterfile.write("hg-kit = " + p.rememberedKit, 0, 9 + p.rememberedKit.length());
			characterfile.newLine();
			characterfile.write("spectateEx = ", 0, 13);
			characterfile.write(Integer.toString(p.spectateEx), 0, Integer.toString(p.spectateEx).length());
			characterfile.newLine();
			characterfile.write("setcc = ", 0, 8);
			characterfile.write(p.myCC, 0, p.myCC.length());
			characterfile.newLine();
			characterfile.write("freePartyHat = ", 0, 15);
			characterfile.write(Integer.toString(p.freePartyHat), 0, Integer.toString(p.freePartyHat).length());
			characterfile.newLine();
			characterfile.write("votes = ", 0, 8);
			characterfile.write(Integer.toString(p.voteTimes), 0, Integer.toString(p.voteTimes).length());
			characterfile.newLine();
			characterfile.write("abyssKc = ", 0, 10);
			characterfile.write(Integer.toString(p.abyssKc), 0, Integer.toString(p.abyssKc).length());
			characterfile.newLine();
			characterfile.write("dragonKc = ", 0, 11);
			characterfile.write(Integer.toString(p.dragonKc), 0, Integer.toString(p.dragonKc).length());
			characterfile.newLine();
			characterfile.write("votingPoints = ", 0, 15);
			characterfile.write(Integer.toString(p.votingPoints), 0, Integer.toString(p.votingPoints).length());
			characterfile.newLine();
			characterfile.write("skull-timer = ", 0, 14);
			characterfile.write(Integer.toString(p.skullTimer), 0, Integer.toString(p.skullTimer).length());
			characterfile.newLine();
			characterfile.write("lastGame = ", 0, 11);
			characterfile.write(Integer.toString(p.lastGame), 0, Integer.toString(p.lastGame).length());
			characterfile.newLine();
			characterfile.write("startChristmas = ", 0, 17);
			characterfile.write(Integer.toString(p.startChristmas), 0, Integer.toString(p.startChristmas).length());
			characterfile.newLine();
			characterfile.write("gifts = ", 0, 8);
			characterfile.write(Integer.toString(p.gifts), 0, Integer.toString(p.gifts).length());
			characterfile.newLine();
			characterfile.write("vote-Chest = ", 0, 13);
			characterfile.write(Integer.toString(p.voteChest), 0, Integer.toString(p.voteChest).length());
			characterfile.newLine();
			characterfile.write("magic-book = ", 0, 13);
			characterfile.write(Integer.toString(p.playerMagicBook), 0, Integer.toString(p.playerMagicBook).length());
			characterfile.newLine();
			characterfile.write("special-amount = ", 0, 17);
			characterfile.write(Double.toString(p.specAmount), 0, Double.toString(p.specAmount).length());
			characterfile.newLine();
			characterfile.write("teleblock-length = ", 0, 19);
			characterfile.write(Integer.toString(tbTime), 0, Integer.toString(tbTime).length());
			characterfile.newLine();
			characterfile.write("Donate-Amount = ", 0, 16);
			characterfile.write(Integer.toString(p.donatorPoints), 0, Integer.toString(p.donatorPoints).length());
			characterfile.newLine();
			characterfile.write("autoRet = ", 0, 10);
			characterfile.write(Integer.toString(p.autoRet), 0, Integer.toString(p.autoRet).length());
			characterfile.newLine();
			characterfile.write("flagged = ", 0, 10);
			characterfile.write(Boolean.toString(p.accountFlagged), 0, Boolean.toString(p.accountFlagged).length());
			characterfile.newLine();
			characterfile.write("fightMode = ", 0, 12);
			characterfile.write(Integer.toString(p.fightMode), 0, Integer.toString(p.fightMode).length());
			characterfile.newLine();
			characterfile.write("tradeTimer = ", 0, 13);
			characterfile.write(Integer.toString(p.tradeTimer), 0, Integer.toString(p.tradeTimer).length());
			characterfile.newLine();
			characterfile.write("memberStatus = ", 0, 15);
			characterfile.write(Integer.toString(p.memberStatus), 0, Integer.toString(p.memberStatus).length());
			characterfile.newLine();
			characterfile.write("donatorChest = ", 0, 15);
			characterfile.write(Integer.toString(p.donatorChest), 0, Integer.toString(p.donatorChest).length());
			characterfile.newLine();
			characterfile.write("betaPlayer = ", 0, 13);
			characterfile.write(Integer.toString(p.betaPlayer), 0, Integer.toString(p.betaPlayer).length());
			characterfile.newLine();
			characterfile.write("KC = ", 0, 4);
			characterfile.write(Integer.toString(p.KC), 0, Integer.toString(p.KC).length());
			characterfile.newLine();
			characterfile.write("DC = ", 0, 4);
			characterfile.write(Integer.toString(p.DC), 0, Integer.toString(p.DC).length());
			characterfile.newLine();
			characterfile.write("isJailed = ", 0, 11);
			characterfile.write(Boolean.toString(p.isJailed), 0, Boolean.toString(p.isJailed).length());
			characterfile.newLine();
			characterfile.write("ban-marks = ", 0, 12);
			characterfile.write(Integer.toString(p.BlackMarks), 0, Integer.toString(p.BlackMarks).length());
			characterfile.newLine();
			characterfile.write("vote = ", 0, 7);
			characterfile.write(Integer.toString(p.vote), 0, Integer.toString(p.vote).length());
			characterfile.newLine();
			characterfile.write("perkOne = ", 0, 10);
			characterfile.write(Integer.toString(p.perkOne), 0, Integer.toString(p.perkOne).length());
			characterfile.newLine();
			characterfile.write("perkTwo = ", 0, 10);
			characterfile.write(Integer.toString(p.perkTwo), 0, Integer.toString(p.perkTwo).length());
			characterfile.newLine();
			characterfile.write("perkThreeMelee = ", 0, 17);
			characterfile.write(Integer.toString(p.perkThreeMelee), 0, Integer.toString(p.perkThreeMelee).length());
			characterfile.newLine();
			characterfile.write("perkFourRange = ", 0, 16);
			characterfile.write(Integer.toString(p.perkFourRange), 0, Integer.toString(p.perkFourRange).length());
			characterfile.newLine();
			characterfile.write("perkFiveMagic = ", 0, 16);
			characterfile.write(Integer.toString(p.perkFiveMagic), 0, Integer.toString(p.perkFiveMagic).length());
			characterfile.newLine();
			characterfile.write("perkSixPray = ", 0, 14);
			characterfile.write(Integer.toString(p.perkSixPray), 0, Integer.toString(p.perkSixPray).length());
			characterfile.newLine();
			characterfile.write("perkSevenIce = ", 0, 15);
			characterfile.write(Integer.toString(p.perkSevenIce), 0, Integer.toString(p.perkSevenIce).length());
			characterfile.newLine();
			characterfile.write("perkEightStat = ", 0, 16);
			characterfile.write(Integer.toString(p.perkEightStat), 0, Integer.toString(p.perkEightStat).length());
			characterfile.newLine();
			characterfile.write("perkKaboom = ", 0, 13);
			characterfile.write(Integer.toString(p.perkKaboom), 0, Integer.toString(p.perkKaboom).length());
			characterfile.newLine();
			characterfile.write("perkGifted = ", 0, 13);
			characterfile.write(Integer.toString(p.perkGifted), 0, Integer.toString(p.perkGifted).length());
			characterfile.newLine();
			characterfile.write("perkSwitch = ", 0, 13);
			characterfile.write(Integer.toString(p.perkSwitch), 0, Integer.toString(p.perkSwitch).length());
			characterfile.newLine();
			characterfile.write("perkVeng = ", 0, 11);
			characterfile.write(Integer.toString(p.perkVeng), 0, Integer.toString(p.perkVeng).length());
			characterfile.newLine();
			characterfile.write("perkConnect = ", 0, 14);
			characterfile.write(Integer.toString(p.perkConnect), 0, Integer.toString(p.perkConnect).length());
			characterfile.newLine();
			characterfile.write("perkYell = ", 0, 11);
			characterfile.write(Integer.toString(p.perkYell), 0, Integer.toString(p.perkYell).length());
			characterfile.newLine();
			characterfile.write("perkConquer = ", 0, 14);
			characterfile.write(Integer.toString(p.perkConquer), 0, Integer.toString(p.perkConquer).length());
			characterfile.newLine();
			characterfile.write("perkVengTwo = ", 0, 14);
			characterfile.write(Integer.toString(p.perkVengTwo), 0, Integer.toString(p.perkVengTwo).length());
			characterfile.newLine();
			characterfile.write("perkLazy = ", 0, 11);
			characterfile.write(Integer.toString(p.perkLazy), 0, Integer.toString(p.perkLazy).length());
			characterfile.newLine();
			characterfile.write("pumpkinPerk = ", 0, 14);
			characterfile.write(Integer.toString(p.pumpkinPerk), 0, Integer.toString(p.pumpkinPerk).length());
			characterfile.newLine();
			characterfile.write("beerPerk = ", 0, 11);
			characterfile.write(Integer.toString(p.beerPerk), 0, Integer.toString(p.beerPerk).length());
			characterfile.newLine();
			characterfile.write("noSmuggle = ", 0, 12);
			characterfile.write(Integer.toString(p.noSmuggle), 0, Integer.toString(p.noSmuggle).length());
			characterfile.newLine();
			characterfile.write("firstLogin = ", 0, 13);
			characterfile.write(Integer.toString(p.firstLogin), 0, Integer.toString(p.firstLogin).length());
			characterfile.newLine();
			characterfile.write("resetPass = ", 0, 12);
			characterfile.write(Integer.toString(p.resetPass), 0, Integer.toString(p.resetPass).length());
			characterfile.newLine();
			characterfile.write("has-npc = ", 0, 10);
			characterfile.write(Boolean.toString(p.hasNpc), 0, Boolean.toString(p.hasNpc).length());
			characterfile.newLine();
			characterfile.write("summonId = ", 0, 11);
			characterfile.write(Integer.toString(p.summonId), 0, Integer.toString(p.summonId).length());
			characterfile.newLine();
			characterfile.newLine();
			
			/*EQUIPMENT*/
			characterfile.write("[EQUIPMENT]", 0, 11);
			characterfile.newLine();
			for (int i = 0; i < p.playerEquipment.length; i++) {
				characterfile.write("character-equip = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipment[i]), 0, Integer.toString(p.playerEquipment[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipmentN[i]), 0, Integer.toString(p.playerEquipmentN[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.newLine();
			}
			characterfile.newLine();
			
			/*LOOK*/
			characterfile.write("[LOOK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.playerAppearance.length; i++) {
				characterfile.write("character-look = ", 0, 17);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerAppearance[i]), 0, Integer.toString(p.playerAppearance[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();
			
			/*SKILLS*/
			characterfile.write("[SKILLS]", 0, 8);
			characterfile.newLine();
			for (int i = 0; i < p.playerLevel.length; i++) {
				characterfile.write("character-skill = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerLevel[i]), 0, Integer.toString(p.playerLevel[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerXP[i]), 0, Integer.toString(p.playerXP[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();
			
			/*ITEMS*/
			characterfile.write("[ITEMS]", 0, 7);
			characterfile.newLine();
			for (int i = 0; i < p.playerItems.length; i++) {
				if (p.playerItems[i] > 0) {
					characterfile.write("character-item = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItems[i]), 0, Integer.toString(p.playerItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItemsN[i]), 0, Integer.toString(p.playerItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();
			
		/*BANK*/
			characterfile.write("[BANK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.bankItems.length; i++) {
				if (p.bankItems[i] > 0) {
					characterfile.write("character-bank = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.bankItems[i]), 0, Integer.toString(p.bankItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.bankItemsN[i]), 0, Integer.toString(p.bankItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();
			
		/*FRIENDS*/
			characterfile.write("[FRIENDS]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < p.friends.length; i++) {
				if (p.friends[i] > 0) {
					characterfile.write("character-friend = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write("" + p.friends[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();
			
		/* HG Kits */
			characterfile.write("[KITS]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.kits.length; i++) {
				if (p.kits[i] != null) {
					characterfile.write("character-kit = ", 0, 16);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(p.kits[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();
			
			/* HG Acheivements */
			characterfile.write("[ACHEIVEMENTS]", 0, 14);
			characterfile.newLine();
			for (int i = 0; i < p.achievements.length; i++) {
				characterfile.write("character-ach = ", 0, 16);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				if (p.achievements[i][0] == 1) {
					characterfile.write("1");
					characterfile.write("	", 0, 1);
					characterfile.write("1");
				} else {
					characterfile.write("0");
					characterfile.write("	", 0, 1);
					characterfile.write("0");
				}
				characterfile.newLine();
			}
			characterfile.newLine();
			
		/*IGNORES*/
			/*characterfile.write("[IGNORES]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < ignores.length; i++) {
				if (ignores[i] > 0) {
					characterfile.write("character-ignore = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Long.toString(ignores[i]), 0, Long.toString(ignores[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();*/
		/*EOF*/
			characterfile.write("[EOF]", 0, 5);
			characterfile.newLine();
			characterfile.newLine();
			characterfile.close();
		} catch(IOException ioexception) {
			Misc.println(p.playerName+": error writing file.");
			return false;
		}
		return true;
	}	
	

}