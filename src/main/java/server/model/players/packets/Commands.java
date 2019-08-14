package server.model.players.packets;

import org.runetoplist.VoteReward;
import server.Config;
import server.Connection;
import server.Server;
import server.model.players.*;
import server.util.Misc;

import java.util.Random;


/**
 * Commands
 **/
public class Commands implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
	String playerCommand = c.getInStream().readString();
	
		if(1==1)//Config.SERVER_DEBUG
			Misc.println(c.playerName+" playerCommand: " + playerCommand);
			
		if (playerCommand.startsWith("/") && playerCommand.length() > 1) {
			if (Server.clanChat.timeLimit(c, c.clanId)) {
				if ((Connection.isMuted(c)) || (System.currentTimeMillis() < c.muteEnd)) {
					c.sendMessage("You cannot use the Clan Chat, you are muted.");
					return;
				}
				if (c.clanId >= 0) {
					playerCommand = playerCommand.substring(1);
					Server.clanChat.playerMessageToClan(c.playerId, playerCommand, c.clanId);
					c.lastCC = System.currentTimeMillis();
				} else {
					if (c.clanId != -1)
						c.clanId = -1;
						c.sendMessage("You are not in a clan.");
				}
			}
			return;
		}
			
		/** Reg Players **/
		if (c.playerRights >= 0) {
		
			if(playerCommand.equalsIgnoreCase("mykits") || playerCommand.equalsIgnoreCase("kits")) {
				String listKits = "Default";
				String listKits2 = "";
				boolean line2 = false;
				for (int i = 0; c.kits.length > i; i++) {
					if (c.kits[i] != null) {
						if (listKits.length() <= 75) {
							listKits = listKits + ", " + c.kits[i];
						} else {
							line2 = true;
							listKits2 = listKits2 + ", " + c.kits[i];
						}
					}
				}
				c.sendMessage("@blu@" + listKits);
				if (line2)
					c.sendMessage("@blu@" + listKits2);
			}
			
			if (playerCommand.equalsIgnoreCase("togglewait")) {
				c.waitInterface = !c.waitInterface;
				if (c.waitInterface == false)
					c.getPA().walkableInterface(-1);
			}
			
			if (playerCommand.equalsIgnoreCase("staff")) {
				boolean online = false;
				c.sendMessage("Current staff members online:");
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
						Client c2 = (Client)Server.playerHandler.players[i];
						if (c2.playerRights == 1) {
							online = true;
							c.sendMessage("[@blu@MOD@bla@] " + c2.playerName);
						} else if (c2.playerRights == 2) {
							online = true;
							c.sendMessage("[@blu@ADMIN@bla@] " + c2.playerName);
						} else if (c2.playerRights == 3) {
							online = true;
							c.sendMessage("[@blu@OWNER@bla@] " + c2.playerName);
						}
					}
				}
				if (!online)
					c.sendMessage("None");
			}
			
			if (playerCommand.equalsIgnoreCase("betainfection")) {
				if (c.betaInfection) {
					if (Server.Infection.gameStartTimer > 10) {
						c.lastClick = System.currentTimeMillis();
						c.lastClick = System.currentTimeMillis();
						c.getPA().clearHungerGamesInterface();
						Server.Infection.joinWait(c);
						c.getTradeAndDuel().declineTrade();
					} else {
						c.lastClick = System.currentTimeMillis();
						c.sendMessage("The match is about to start, players are currently preping for next match in ("+Server.HungerGamesCan.gameStartTimer+").");
					}
				} else {
					c.sendMessage("Beta mode is currently disabled at the moment.");
				}
			}
			
			if (playerCommand.equalsIgnoreCase("kdr")) {
				double KDR = ((double)c.KC)/((double)c.DC);
				c.forcedChat("Kills: "+c.KC+", Deaths: "+c.DC+", Wins: "+c.totalGameWins+", KDRatio: "+KDR+".");
				c.sendMessage("Kills: "+c.KC+", Deaths: "+c.DC+", Wins: "+c.totalGameWins+", KDRatio: "+KDR+".");
			}
			
			if (playerCommand.equalsIgnoreCase("votingpoints")) {
				c.sendMessage("Current voting points is now " + c.votingPoints + ".");
			}
			
			if (playerCommand.startsWith("changepassword")) {
				try {
					String[] arg = playerCommand.split(" ");
					String pass = arg[1];
					if (arg.length == 2) {
						if (pass.length() >= 13) {
							c.sendMessage("Password is too long! Retry!");
							return;
						}
						c.playerPass = pass;
						c.sendMessage("Your password is now: @red@" + c.playerPass);
						c.sendMessage("@blu@Do not forget this or you might get locked out!");
						if (c.resetPass == 0) {
							c.resetPass = 1;
						}
					} else {
						c.sendMessage("Please do not use any spaces in your password!");
					}
				} catch(Exception e) {
					c.sendMessage("Incorrect format, use ::changepassword passhere");
				}
			}
			
			if (playerCommand.equalsIgnoreCase("updatelist")) {
				c.getPA().sendFrame126("www.hg-rs.com/forum/index.php?topic=36.0", 12000);
				c.sendMessage("Opening www.hg-rs.com/forum/index.php?topic=36.0 . .");
			}
			
			if (playerCommand.equalsIgnoreCase("forum")) {
				c.getPA().sendFrame126("www.hg-rs.com/forum/", 12000);
				c.sendMessage("Opening www.hg-rs.com/forum/ . .");
			}
			
			if (playerCommand.equalsIgnoreCase("perks")) {
				c.sendMessage("Here is a list of the perks you have..");
				if (c.perkOne == 1) {
					c.sendMessage("You have the Food perk");
				}
				if (c.perkTwo == 1) {
					c.sendMessage("You have the Special perk");
				}
				if (c.perkThreeMelee == 1) {
					c.sendMessage("You have the first Melee perk");
				} else if (c.perkThreeMelee == 2) {
					c.sendMessage("You have both Melee perks");
				}
				if (c.perkFourRange == 1) {
					c.sendMessage("You have the first Range perk");
				} else if (c.perkFourRange == 2) {
					c.sendMessage("You have both Range perks");
				}
				if (c.perkFiveMagic == 1) {
					c.sendMessage("You have the first Mage perk");
				} else if (c.perkFiveMagic == 2) {
					c.sendMessage("You have both Mage perks");
				}
				if (c.perkSixPray == 1) {
					c.sendMessage("You have the Pray perk");
				}
				if (c.perkSevenIce == 1) {
					c.sendMessage("You have the Ice perk");
				}
				if (c.perkEightStat == 1) {
					c.sendMessage("You have the Stat perk");
				}
				if (c.perkKaboom == 1) {
					c.sendMessage("You have the Kaboom perk");
				}
				if (c.perkGifted == 1) {
					c.sendMessage("You have the Gifted perk");
				}
				if (c.perkSwitch == 1) {
					c.sendMessage("You have the Switch perk");
				}
				if (c.perkVeng == 1) {
					c.sendMessage("You have the Veng perk");
				}
				if (c.perkConnect == 1) {
					c.sendMessage("You have the Connect perk");
				}
				if (c.perkConquer == 1) {
					c.sendMessage("You have the Conquer perk");
				}
				if (c.perkVengTwo == 1) {
					c.sendMessage("You have the Veng II perk");
				}
				if (c.perkLazy == 1) {
					c.sendMessage("You have the Lazy perk");
				}
				if (c.pumpkinPerk == 1) {
					c.sendMessage("You have the Pumpkin perk");
				}
				if (c.beerPerk == 1) {
					c.sendMessage("You have the Keg of Beer perk");
				}
			}
			
			if (playerCommand.equalsIgnoreCase("players") || playerCommand.equalsIgnoreCase("player")) {
				c.sendMessage("There are currently " + PlayerHandler.getPlayerCount() + " players online.");
				int counter = 0;
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
						Client c2 = (Client)Server.playerHandler.players[i];
						if (c2.inPkingZone) {
							counter++;
						}	
					}
				}
				c.sendMessage("There are currently " + counter + " players at Edgeville.");
				c.sendMessage("There are currently " + (Server.HungerGames.currentPlayers.size() + Server.HungerGames.currentPlayersWait.size()) + " players in the Varrock game and lobby.");
				c.sendMessage("There are currently " + (Server.HungerGamesFal.currentPlayers_Fal.size() + Server.HungerGamesFal.currentPlayers_FalWait.size()) + " players in the Falador game and lobby.");
				c.sendMessage("There are currently " + (Server.HungerGamesCan.currentPlayers_Can.size() + Server.HungerGamesCan.currentPlayers_CanWait.size()) + " players in the Desert game and lobby.");
				c.sendMessage("There are currently " + (Server.HungerGamesMisc.currentPlayers_Misc.size() + Server.HungerGamesMisc.currentPlayers_MiscWait.size()) + " players in the Miscellania game and lobby.");
			}
			
			if (playerCommand.equalsIgnoreCase("unstuck") || playerCommand.equalsIgnoreCase("stuck")) {
				if (c.randomMap == 0 && Server.HungerGames.timeRemaining <= 10 && Server.HungerGames.timeRemaining >= 1) {
					c.sendMessage("Please wait till the countdown is over.");
					return;
				}
				if (c.randomMap == 1 && Server.HungerGamesFal.timeRemaining <= 10 && Server.HungerGamesFal.timeRemaining >= 1) {
					c.sendMessage("Please wait till the countdown is over.");
					return;
				}
				if (c.randomMap == 2 && Server.HungerGamesCan.timeRemaining <= 10 && Server.HungerGamesCan.timeRemaining >= 1) {
					c.sendMessage("Please wait till the countdown is over.");
					return;
				}
				if (c.randomMap == 3 && Server.HungerGamesMisc.timeRemaining <= 10 && Server.HungerGamesMisc.timeRemaining >= 1) {
					c.sendMessage("Please wait till the countdown is over.");
					return;
				}
				if (c.HGAttack) {
					if (System.currentTimeMillis() - c.lastStuck > 15000) {
						if(c.underAttackBy != 0) {
							c.sendMessage("You can't use this command while in combat!");
						} else {
							c.stuckX = c.getX();
							c.stuckY = c.getY();
							c.stuck = true;
							c.lastStuck = System.currentTimeMillis();
							c.stuckTimer = System.currentTimeMillis();
							c.sendMessage("Don't move for 5 seconds!");
						}
					} else {
						c.sendMessage("Please wait for 15 seconds before using this again!");
					}
				} else {
					c.sendMessage("You can only use this once the game has started!");
				}
			}
			
			/* if (playerCommand.equalsIgnoreCase("check") || playerCommand.equalsIgnoreCase("claim")) {
								if (c.inCwWait || c.inCwGame) {
					c.sendMessage("You can't use that here!");
					return;
				}
				if (System.currentTimeMillis() - c.lastClaim > 60000) {
					c.lastClaim = System.currentTimeMillis();
					try {
						VoteReward reward = Server.voteChecker.getReward(c.playerName.replaceAll(" ", "_"));
						if(reward != null){
							int voteExp = 50000;
							c.voteTimes++;
							if (c.voteTimes == 1 && c.achievements[8][0] == 0) {
								c.achievements[8][1] = 1;
								c.achievementsHandler();
							} else if (c.voteTimes == 10 && c.achievements[9][0] == 0) {
								c.achievements[9][1] = 1;
								c.achievementsHandler();
							}
							switch(reward.getReward()){
								case 0:
									c.totalHungerGameExp += voteExp;
									c.totalExp += voteExp;
									c.sendMessage("Thanks for voting, you earned " + voteExp + " experience!");
									c.reloadHGstuff();
										
									for (int j = 0; j < Server.playerHandler.players.length; j++) {
										if (Server.playerHandler.players[j] != null) {
											Client c2 = (Client)Server.playerHandler.players[j];
											c2.sendMessage("[@red@SERVER@bla@] "+c.playerName+" just voted and got " + voteExp + " experience! To vote too, do ::vote");
				
										}
									}
								break;
								
								case 1:
									c.getItems().addItem(15328, 1);
									c.sendMessage("Thanks for voting, you got a Voting Package!");
									c.reloadHGstuff();
										
									for (int j = 0; j < Server.playerHandler.players.length; j++) {
										if (Server.playerHandler.players[j] != null) {
											Client c2 = (Client)Server.playerHandler.players[j];
											c2.sendMessage("[@red@SERVER@bla@] "+c.playerName+" just voted and got a Voting Package! To vote too, do ::vote");
				
										}
									}
								break;
								
								case 2:
									c.votingPoints += 10;
									c.sendMessage("Thanks for voting, you get 10 voting points!");
									c.sendMessage("Current voting points is now " + c.votingPoints + ".");
									c.reloadHGstuff();
										
									for (int j = 0; j < Server.playerHandler.players.length; j++) {
										if (Server.playerHandler.players[j] != null) {
											Client c2 = (Client)Server.playerHandler.players[j];
											c2.sendMessage("[@red@SERVER@bla@] "+c.playerName+" just voted and got 10 voting points! To vote too, do ::vote");
				
										}
									}
								break;
										
								default:
									c.sendMessage("Reward not found.");
								break;
							}
								c.sendMessage("Thank you for voting.");
							} else {
								c.sendMessage("You have no items waiting for you.");
							}
						} catch (Exception e){
							c.sendMessage("An error occurred please try again later.");
						}
				} else {
					c.sendMessage("You just recently used that command, please wait 60 seconds!");
				}
			}*/
			
			if (playerCommand.equalsIgnoreCase("vote")) {
				//c.getPA().sendFrame126("www.hg-rs.com/vote", 12000);
				c.sendMessage("Voting is currently disabled.");
				c.sendMessage("Check back soon!");
            }
			
			if (playerCommand.startsWith("server")) {
				c.sendMessage("These are server stats for ALL players.");
				c.sendMessage("[SERVER] Total games played: @red@" + c.df.format((int)c.totalGamesPlayed));
				c.sendMessage("[SERVER] Total players killed: @red@" + c.df.format((int)c.totalPlayersKilled));
				c.sendMessage("[SERVER] Total players died: @red@" + c.df.format((int)c.totalPlayersDied));
				c.sendMessage("[SERVER] Total chests looted: @red@" + c.df.format((int)c.totalChestsLooted));
				c.sendMessage("[SERVER] Total traps triggered: @red@" + c.df.format((int)c.totalTraps));
				c.sendMessage("[SERVER] Total grand chests looted: @red@" + c.df.format((int)c.totalGrandChests));
				c.sendMessage("[SERVER] Total exp earned: @red@" + c.df.format((long)c.totalExp));
			}
			
			if (playerCommand.startsWith("allkits")) {
				if(!c.inCwGame)
					c.donatorKits();
				else
					c.sendMessage("You can't use this while in a game.");
			}
			
			if (playerCommand.startsWith("lottery")) {
				c.sendMessage("The lottery is currently at " + c.lottery + " experience");
			}
			
			if (playerCommand.equalsIgnoreCase("commands")) {
				c.commandList();
			}
			
			if (playerCommand.equalsIgnoreCase("kit")) {
				if(c.inCwWait) {
					boolean picked = false;
					String pickedKit = playerCommand.substring(4);
					for (int i = 0; c.kits.length > i; i++) {
						if (c.kits[i] != null && c.kits[i].equalsIgnoreCase(pickedKit)) {
							picked = true;
							c.myKit = pickedKit;
							c.myKit = c.myKit.toUpperCase();
							c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
							c.getPA().requestUpdates();
							c.rememberedKit = c.myKit;
							break;
						}
					}
					if (!picked && pickedKit.length() != 0) {
						c.sendMessage("You don't have @blu@" + Misc.optimizeText(pickedKit) + "@bla@ or you made a typo.");
					} else if (pickedKit.length() <= 1) {
						c.sendMessage("No kit selected, correct usage: @red@::kit kitName");
					}
				} else {
					c.sendMessage("You can only choose your kit from within the waiting lobby.");
					c.donatorKits();
					return;
				}
			}
			
			if (playerCommand.startsWith("setcc")) {
				try {
					String[] arg = playerCommand.split(" ");
					String cc = arg[1];
					if (arg.length == 2) {
						c.myCC = cc;
						c.sendMessage("You've set your default Clan Chat as @blu@" + c.myCC + "@bla@.");
						if (c.achievements[28][0] == 0) {
							c.achievements[28][1] = 1;
							c.achievementsHandler();
						}
					} else {
						c.sendMessage("Wrong format, use ::setcc CCname");
					}
				} catch(Exception e) {
					c.sendMessage("Wrong format, use ::setcc CCname");
				}
			}
			
			if (playerCommand.startsWith("yell")) {
				String Message = playerCommand.substring(4).toLowerCase();
				String area = "";
				if (c.yellDisabled) {
					c.sendMessage("Yell has been temporaryly disabled by an Admin go to cc 'help'!");
					return;
				}
				if ((Connection.isMuted(c))) {
					c.sendMessage("You cannot use Yell, you are muted!");
					return;
				}
				if (c.playerName.equalsIgnoreCase("Snow")) {
					area = "President";
				} else if (c.playerRights == 0) {
					if (c.inCwGame) {
						if (c.randomMap == 0) {
							area = "Varrock";
						} else if (c.randomMap == 1) {
							area = "Falador";
						} else if (c.randomMap == 2) {
							area = "M/S";
						} else if (c.randomMap == 3) {
							area = "Misc";
						}
					} else {
						if (c.inPkingZone) {
							area = "Edge";
						} else if (c.inHomeArea() && c.inEggArea()) {
							area = "Gambling";
						} else if (c.isInLobby() || c.isInLobbyFal() || c.isInLobbyCan() || c.isInLobbyMisc()) {
							area = "Lobby";
						} else {
							area = "Home";
						}
					}
				} else if (c.playerRights == 1) {
					area = "Mod";
				} else if (c.playerRights >= 2) {
					area = "Admin";
				}
				if (c.perkYell == 1) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							if (c.yellAllowed) {
								c2.sendMessage("[@gre@Donor-Vet@bla@] [@mag@" + area + "@bla@] "+ c.playerName +"@bla@: @blu@" + Misc.optimizeText(Message));
							} else {
								c2.sendMessage("[@gre@Donator@bla@] [@mag@" + area + "@bla@] "+ c.playerName +"@bla@: @blu@" + Misc.optimizeText(Message));
							}
						}
					}
					return;
				}
				if (c.yellAllowed) {
					if (System.currentTimeMillis() - c.lastYell > 15000) {
						c.lastYell = System.currentTimeMillis();
						for (int j = 0; j < Server.playerHandler.players.length; j++) {
							if (Server.playerHandler.players[j] != null) {
								Client c2 = (Client)Server.playerHandler.players[j];
								c2.sendMessage("[@blu@Veteran@bla@] [@mag@" + area + "@bla@] "+ c.playerName +"@bla@: @blu@" + Misc.optimizeText(Message));
							}
						}
					} else {
						long finalTime = 15 - (System.currentTimeMillis() - c.lastYell) / 1000;
						c.sendMessage("Pleased wait for " + finalTime + " seconds till you can yell again!");
					}
				} else {
					c.sendMessage("You have to unlock 15+ perks, 20+ achievements, and have 30+ wins to use this!");
				}
			}
		} 
		
		if (c.playerRights >= 1) {
			if (playerCommand.startsWith("mpass")) {
				String[] arg = playerCommand.split(" ");
				String pass = arg[1];
				if (pass.equals(c.modSetPass)) {
					c.sendMessage("You've correctly entered the command password.");
					c.modPass = "tyvb";
				} else {
					c.sendMessage("Incorrect password, if you've forgotten the pass, please contact Peeta.");
				}
			}
		}
		
		if (c.modPass.equals("tyvb")) {
			/** Mods **/
			if (c.playerRights >= 1) {
			
				if (playerCommand.equals("mcommands")) {
					c.sendMessage("::ban, ::unban, ::mute, ::unmute, ::timedmute, ::ipmute, ::unipmute, ::hide, ::xteleme");
					c.sendMessage("::xteleto, ::tele, ::checkbank, ::kick, ::getip, ::pnpc, ::unnpc, ::sm, ::mzone, ::telemzone");
					c.sendMessage("::resetmourner, ::doit, ::mark, ::clearmarks, ::stoptrade, ::mshop, ::modkit");
				}
				
				if (playerCommand.equalsIgnoreCase("resetmourner")) {
					c.sendMessage("You're about to attempt to reset the Mourner, this may cause");
					c.sendMessage("unknown bugs, but I will attempt to refund everyone. If you're");
					c.sendMessage("sure you want to go through with this, type ::doit.");
					c.confirmReset = true;
				}
				
				if (playerCommand.equalsIgnoreCase("stoptrade")) {
					c.disableTrade = !c.disableTrade;
					c.sendMessage("Trade disabled = " + c.disableTrade);
				}
				
				if (playerCommand.equalsIgnoreCase("doit")) {
					if (c.confirmReset) {
						for (int j = 0; j < Server.playerHandler.players.length; j++) {
							if (Server.playerHandler.players[j] != null) {
								Client o = (Client)Server.playerHandler.players[j];
								if (c.gamblerOne.equals(o.playerName) || c.gamblerTwo.equals(o.playerName)) {
									o.totalHungerGameExp += o.myBet;
									o.reloadHGstuff();
									o.sendMessage("The bet has been @red@canceled@bla@, you've been refunded your bet from the Mourner.");
								}
							}
						}
						c.sendMessage("You've succesfully reset all of the Mourners settings.");
						c.sendMessage("If for some reason hes still bugged, please report this to Peeta.");
						c.cleanObjects();
						Server.npcHandler.npcAction(372, "It seems " + c.playerName + " has forcefully made me cancel the bet.", 0);
						c.confirmReset = false;
					} else {
						c.sendMessage("If you're resetting Mourner, do ::resetmourner first!");
					}
				}
				
				if (playerCommand.startsWith("mark")) {
					try {
						String playerToBan = playerCommand.substring(5);
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
									Client c2 = (Client)Server.playerHandler.players[i];
									c2.BlackMarks += 1;
									c2.sendMessage("You've been given a black mark from @blu@" + c.playerName + "@bla@! You now have " + c2.BlackMarks + " / 5.");
									c2.sendMessage("If you get 3 marks you'll be muted, if you get 5 you'll be banned.");
									
									c.sendMessage("You've given @red@" + c2.playerName + "@bla@ a blackmark.");
									c.sendMessage("Use ::unban if they get banned then ::clearmarks to undo.");
									c.staffYell("[@red@Commands@bla@]: @blu@" + c.playerName + "@bla@ just marked @blu@" + c2.playerName + "@bla@ (" + c2.BlackMarks + " marks)!");
									if (c2.BlackMarks == 3) {
										c2.sendMessage("You've been muted because you have three black marks!");
										Connection.addNameToMuteList(playerToBan);
									} else if(c2.BlackMarks >= 5) {
										c2.sendMessage("Banned!");
										Connection.addNameToBanList(playerToBan);
										Connection.addNameToFile(playerToBan);
										Server.playerHandler.players[i].disconnected = true;
									}
								}
							}
						}
					} catch(Exception e) {
						c.sendMessage("Player Must Be Online.");
					}
				}
				
				if (playerCommand.startsWith("clearmarks")) {
					try {
						String player = playerCommand.substring(11);
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(player)) {
									Client c2 = (Client)Server.playerHandler.players[i];
									c2.BlackMarks = 0;
									c2.sendMessage("Your black marks have been cleared by @blu@" + c.playerName + "@bla@!");
									c.sendMessage("You successfully clear " + c2.playerName + "'s black marks.");
									c.staffYell("[@red@Commands@bla@]: @blu@" + c.playerName + "@bla@ just cleared @blu@" + c2.playerName + "'s@bla@ black marks!");
								}
							}
						}
						
					} catch(Exception e) {
						c.sendMessage("Player Must Be Online.");
					}
				}
				
				if (playerCommand.startsWith("tele")) {
					String[] arg = playerCommand.split(" ");
					if (arg.length > 3)
						c.getPA().movePlayer2(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]));
					else if (arg.length == 3)
						c.getPA().movePlayer2(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),c.heightLevel);
				}
				
				if (playerCommand.startsWith("sm")) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							if (c.playerRights == 1) {
								c2.sendMessage("[@red@SERVER@bla@][@blu@MOD@bla@] - " + Misc.optimizeText(playerCommand.substring(3)));
							} else if (c.playerRights > 1) {
								c2.sendMessage("[@red@SERVER@bla@][@yel@ADMIN@bla@] - " + Misc.optimizeText(playerCommand.substring(3)));
							}
						}
					}
				}
				
				if (playerCommand.equalsIgnoreCase("mzone")) {
					c.sendMessage("Welcome " + c.playerName + ", you've teleported to the mod zone!");
					c.modAccess = true; 
					c.getPA().movePlayer2(3375, 9811, 0);
					Server.npcHandler.npcAction(2790, "Welcome to the Mod Zone!", 0);
				}
				
				if (playerCommand.startsWith("telemzone")) {
					String[] args = playerCommand.split(" ");
					String player = args[1];
					
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(player)) {
								Client c2 = (Client) Server.playerHandler.players[i];
								
								c.sendMessage("You have teleported and given access to " + c2.playerName+" at the mod zone till they log.");
								c2.sendMessage("You have been teleported and given access to the mod zone by " + c.playerName+".");
								c2.getPA().movePlayer2(3375, 9811, 0);
								c2.modAccess = true; 
								Server.npcHandler.npcAction(2790, "Welcome to the Mod Zone!", 0);
								c.staffYell("[@red@Commands@bla@]: @blu@" + c.playerName + "@bla@ has teleported @blu@" + c2.playerName + "@bla@ to the mod zone!");
								break;
							} 
						}
					}
				}
				
				if (playerCommand.startsWith("modkit")) {
					try {
						String[] args = playerCommand.split(" ");
						String player = args[1];
						c.myKit = player;
						c.getPA().requestUpdates();
					} catch(Exception e) {
						c.sendMessage("Invalid format.");
					}  
				}
			
				if (playerCommand.startsWith("timedmute")) {
					try {   
						String[] args = playerCommand.split("-");
						if(args.length < 2) {
							c.sendMessage("Currect usage: ::timedmute-playername-time");
							return;
						}
						String playerToMute = args[1];
						int muteTimer = Integer.parseInt(args[2])*60000;
				 
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToMute)) {
									Client c2 = (Client) Server.playerHandler.players[i];
									c.sendMessage("You have muted : " + c2.playerName+" for "+muteTimer/60000+" minutes.");
									c2.sendMessage("You have been muted by: " + c.playerName+" for "+muteTimer/60000+" minutes.");
									c2.muteEnd = System.currentTimeMillis()+ muteTimer;
									break;
								} 
							}
						}																		 
					} catch(Exception e) {
						c.sendMessage("Player Must Be Offline.");
					}           
				}
				
				if(playerCommand.startsWith("getip")) {
					try {	
						String playerIP = playerCommand.substring(6);
						String theIP = null;
						
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerIP)) {
									c.sendMessage(playerIP + "'s ip seems to be " + Server.playerHandler.players[i].connectedFrom);
									theIP = Server.playerHandler.players[i].connectedFrom;
								} 
							}
						}
						c.sendMessage("Now checking for other players with same IP.");
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].connectedFrom.equals(theIP)) {
									c.sendMessage("Player with same IP = @blu@" + Server.playerHandler.players[i].playerName);
								} 
							}
						}
					} catch(Exception e) {
						c.sendMessage("Player not found.");
					}
				}
				
				if(playerCommand.startsWith("pnpc")) {
					int npc = Integer.parseInt(playerCommand.substring(5));
					if(npc < 9999) {
						c.npcId2 = npc;
						c.isNpc = true;
						c.updateRequired = true;
						c.appearanceUpdateRequired = true;
					}
				}
				
				if(playerCommand.startsWith("unnpc")) {
					c.isNpc = false;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
				}
				
				if (playerCommand.startsWith("kick") && playerCommand.charAt(4) == ' ') {
					try {	
						String playerToBan = playerCommand.substring(5);
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
									Server.playerHandler.players[i].disconnected = true;
									c.staffYell("[@red@Commands@bla@]: @blu@" + c.playerName + "@bla@ has just kicked @blu@" + Misc.optimizeText(playerToBan) + "@bla@!");
								} 
							}
						}
					} catch(Exception e) {
						c.sendMessage("Player not found.");
					}
				}
				
				if (playerCommand.startsWith("ban")) {
					try {
						String playerToBan = playerCommand.substring(4);
						Connection.addNameToBanList(playerToBan);
						Connection.addNameToFile(playerToBan);
						c.playerBan = playerToBan;
						c.sendMessage("You have banned "+playerToBan+"");
						c.playerBan = playerToBan;
						c.staffYell("[@red@Commands@bla@]: @blu@"+c.playerName+ " @bla@just banned @blu@"+ Misc.optimizeText(playerToBan) +"@bla@!");
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Server.playerHandler.players[i].disconnected = true;
								}
							}
						}
					} catch(Exception e) {
						c.sendMessage("Player not found.");
					}
				}
				
				if (playerCommand.startsWith("unban")) {
					try {
						String playerToBan = playerCommand.substring(6);
						Connection.removeNameFromBanList(playerToBan);
						c.sendMessage(playerToBan + " has been unbanned.");
						c.playerBan = playerToBan;
						c.staffYell("[@red@Commands@bla@]: @blu@"+c.playerName+ " @bla@just unbanned @blu@"+ Misc.optimizeText(playerToBan) +"@bla@!");
					} catch(Exception e) {
						c.sendMessage("Player not found.");
					}
				}
				
				if (playerCommand.startsWith("mute")) {
					try {	
						String playerToBan = playerCommand.substring(5);
						Connection.addNameToMuteList(playerToBan);
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
									Client c2 = (Client)Server.playerHandler.players[i];
									c.sendMessage("You have muted " + playerToBan + ".");
									c2.sendMessage("You have been muted by: " + c.playerName);
									c.playerBan = playerToBan;
									c.staffYell("[@red@Commands@bla@]: @blu@"+c.playerName+ " @bla@just muted @blu@"+ Misc.optimizeText(playerToBan) +"@bla@!");
									break;
								} 
							}
						}
					} catch(Exception e) {
						c.sendMessage("Player not found.");
					}			
				}
				
				if (playerCommand.startsWith("ipmute")) {
					try {	
						String playerToBan = playerCommand.substring(7);
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
									Connection.addIpToMuteList(Server.playerHandler.players[i].connectedFrom);
									c.sendMessage("You have IP Muted the user: "+Server.playerHandler.players[i].playerName);
									Client c2 = (Client)Server.playerHandler.players[i];
									c.staffYell("[@red@Commands@bla@]: @blu@"+c.playerName+ " @bla@just IP-muted @blu@"+ Misc.optimizeText(playerToBan) +"@bla@!");
									c2.sendMessage("You have been muted!");
									break;
								} 
							}
						}
					} catch(Exception e) {
						c.sendMessage("Player not found.");
					}			
				}
				
				if (playerCommand.startsWith("unipmute")) {
					try {	
						String playerToBan = playerCommand.substring(9);
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
									Connection.unIPMuteUser(Server.playerHandler.players[i].connectedFrom);
									c.staffYell("[@red@Commands@bla@]: @blu@"+c.playerName+ " @bla@just unIP-muted @blu@"+ Misc.optimizeText(playerToBan) +"@bla@!");
									c.sendMessage("You have unIP-muted the user: "+Server.playerHandler.players[i].playerName);
									break;
								} 
							}
						}
					} catch(Exception e) {
						c.sendMessage("Player not found.");
					}			
				}
				
				if (playerCommand.startsWith("unmute")) {
					try {	
						String playerToBan = playerCommand.substring(7);
						Connection.unMuteUser(playerToBan);
						c.sendMessage("You have Unmuted "+playerToBan+"");
						c.playerBan = playerToBan;
						c.staffYell("[@red@Commands@bla@]: @blu@"+c.playerName+ " @bla@just unmuted @blu@"+ Misc.optimizeText(playerToBan) +"@bla@!");
					} catch(Exception e) {
						c.sendMessage("Player not found.");
					}			
				}
			
				if (playerCommand.startsWith("xteleme")) {
					String name = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (Server.playerHandler.players[i] != null) {
							if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.teleportToX = c.absX;
								c2.teleportToY = c.absY;
								c2.heightLevel = c.heightLevel;
								c.sendMessage("You have teleported @red@" + c2.playerName + " @bla@to you.");
								c2.sendMessage("You have been teleported to @red@" + c.playerName + ".");
							}
						}
					}
				}
				
				if (playerCommand.startsWith("xteleto")) {
					String name = playerCommand.substring(8);
					for (int i = 0; i < Config.MAX_PLAYERS; i++) {
						if (Server.playerHandler.players[i] != null) {
							if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
								c.getPA().movePlayer(Server.playerHandler.players[i].getX(), Server.playerHandler.players[i].getY(), Server.playerHandler.players[i].heightLevel);
							}
						}
					}			
				}
				
				if (playerCommand.equalsIgnoreCase("hide")) {
					c.spectate = !c.spectate;
					c.getPA().requestUpdates();
				}
				
				if (playerCommand.equalsIgnoreCase("mshop")) {
					if (c.inCwGame || c.inCwWait) {
						c.sendMessage("You can't use this in a game.");
					} else {
						c.sendMessage("These items are for mods only, the voting pack and other items");
						c.sendMessage("are incase players need a refund. Beware of liers or scammers.");
						c.getShops().openShop(14);
					}
				}
				
				if (playerCommand.startsWith("checkbank")) {
					if (c.inCwGame) {
						c.sendMessage("You can't use this command here");
						return;
					}
					try {
						String[] args = playerCommand.split(" ", 2);
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							Client o = (Client) Server.playerHandler.players[i];
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(args[1])) {
									c.getPA().otherBank(c, o);
									break;
								}
							}
						}
					} catch(Exception e) {
						c.sendMessage("Player Must Be Offline."); 
					}
				}
			}
			
			/** Visible Owner/Admin **/
			if (c.playerRights >= 2) {
			
				if (playerCommand.equals("ocommands")) {
					c.sendMessage("::opwalk, ::ipban, ::update, ::item");
					c.sendMessage("::givekit, ::takekit, ::update, ::troll, ::restart, ::allvote, ::godmode");
					c.sendMessage("::gfx, ::int, ::link, ::giveexp, ::startlotto, ::anim, ::spec, ::alltome");
					c.sendMessage("::shop, ::extend, ::speedup, ::reloadchests, ::startbetainfect");
					c.sendMessage("::dropparty, ::disableyell");
				}

				if (playerCommand.startsWith("opwalk")) {
					c.instantWalk = !c.instantWalk;
					c.sendMessage("Instant walk = " + c.instantWalk);
				}
				
				if (playerCommand.startsWith("dropparty")) {
					try {
						String[] args = playerCommand.split(" ");
						if (args.length == 4) {
							int size = Integer.parseInt(args[1]), amount = Integer.parseInt(args[2]), time = Integer.parseInt(args[3]);
							if (size < 5) {
								c.sendMessage("Size must be greater than or equal to 5.");
								return;
							} else if (amount < 5) {
								c.sendMessage("Amount must be greater than or equal to 5.");
								return;
							} else if (time < 1000) {
								c.sendMessage("Time must be greater than or equal to 1000.");
								return;
							}
							c.dropparty(c, size, amount, time);
						} else {
							c.sendMessage("Invalid format, use ::droparty size amount time");
						}
					} catch(Exception e) {
						c.sendMessage("Invalid format, use ::droparty size amount tim");
					}
				}
				
				if (playerCommand.equals("startbetainfect")) {
					c.betaInfection = !c.betaInfection;
					c.sendMessage("Beta on = " + c.betaInfection);
				}
				
				if (playerCommand.startsWith("shop")) {
					String[] args = playerCommand.split(" ");
					if (args.length == 2) {
						int id = Integer.parseInt(args[1]);
						c.getShops().openShop(id);
					} else {
						c.sendMessage("Use as ::shop shopnumber");
					}
				}
				
				if (playerCommand.equals("testmode")) {
					if (c.inCwWait || c.inCwGame) {
						c.testmode = !c.testmode;
						c.sendMessage("Testmode = " + c.testmode);
					} else {
						c.sendMessage("You can only use this in the waiting lobby.");
					}
				}
				
				if (playerCommand.startsWith("giveexp")) {
					try {
						String[] args = playerCommand.split(" ");
						if (args.length == 3) {
							String name = args[1];
							int exp = Integer.parseInt(args[2]);
							for(int i = 0; i < Config.MAX_PLAYERS; i++) {
								if(Server.playerHandler.players[i] != null) {
									if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
										Client c2 = (Client)Server.playerHandler.players[i];
										
										c.sendMessage("You've given @blu@" + c2.playerName + " @bla@" + exp + " experience!");
										c2.sendMessage("@blu@" + c.playerName + "@bla@ just sent you " + exp + " experience!");
										c2.totalHungerGameExp += exp;
										c2.reloadHGstuff();
									} 
								}
							}
						} else {
							c.sendMessage("Use as ::giveexp name amount.");
						}
					} catch(Exception e) {
						c.sendMessage("Player not found.");
					}
				}
				
				if (playerCommand.equals("alltome")) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							c2.teleportToX = c.absX;
							c2.teleportToY = c.absY;
							c2.heightLevel = c.heightLevel;
							c2.sendMessage("Mass teleport to: " + c.playerName + "");
						}
					}
				}
				
				if (playerCommand.startsWith("startlotto")) {
					int winnerId = Misc.random(PlayerHandler.getPlayerCount());
					if (winnerId == 0) {
						winnerId++;
					}
					c.sendMessage("Starting lottery, " + PlayerHandler.getPlayerCount() + " players, winnerId is " + winnerId + ", that's " + Server.playerHandler.players[winnerId].playerName + "!");
					c.handleLotto(winnerId);
				}
				
				if (playerCommand.startsWith("myid")) {
					c.sendMessage("myid" + c.playerId);
				}
				
				if (playerCommand.startsWith("ipban")) {
					try {
						String playerToBan = playerCommand.substring(6);
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
									Connection.addIpToBanList(Server.playerHandler.players[i].connectedFrom);
									Connection.addIpToFile(Server.playerHandler.players[i].connectedFrom);
									c.staffYell("[@red@Commands@bla@]: @blu@"+c.playerName+ " @bla@Just IP-Banned @blu@"+ Misc.optimizeText(playerToBan) +"@bla@ !");
									c.sendMessage("You have IP banned the user: "+Server.playerHandler.players[i].playerName+" with the IP: "+Server.playerHandler.players[i].connectedFrom);
									Server.playerHandler.players[i].disconnected = true;
								} 
							}
						}
					} catch(Exception e) {
						c.sendMessage("Player not found.");
					}
				}
				
				if(playerCommand.startsWith("unuidban")) {
					 String player = playerCommand.substring(9);
					 Connection.getUidForUser(c, player);
				}
				
				if (playerCommand.startsWith("uidban")) {
					try {
						String playerToBan = playerCommand.substring(7);
						for (int i = 0; i < PlayerHandler.players.length; i++) {
							if (PlayerHandler.players[i] != null) {
								if (PlayerHandler.players[i].playerName.equalsIgnoreCase(playerToBan) && PlayerHandler.players[i].playerRights != 3) {
									if(PlayerHandler.players[i].UUID.equalsIgnoreCase("null")) {
										c.sendMessage("UUID is Null unable to ban!");
										return;
									} 
									
									Connection.addUidToBanList(PlayerHandler.players[i].UUID);
									Connection.addUidToFile(PlayerHandler.players[i].UUID);
									c.staffYell("[@red@Commands@bla@]: @blu@"+c.playerName+ " @bla@Just MAC-Banned @blu@"+ Misc.optimizeText(playerToBan) +"@bla@ !");
									if (c.playerRights == 3) {
										c.sendMessage("@red@[" + PlayerHandler.players[i].playerName + "] has been UUID Banned with the UUID: " + PlayerHandler.players[i].UUID);
									}
									PlayerHandler.players[i].disconnected = true;
								}
							}
						}
					}
					catch (Exception ignored) {
					}
				}
				
				if (playerCommand.startsWith("update")) {
					String[] args = playerCommand.split(" ");
					int a = Integer.parseInt(args[1]);
					PlayerHandler.updateSeconds = a;
					PlayerHandler.updateAnnounced = false;
					PlayerHandler.updateRunning = true;
					PlayerHandler.updateStartTime = System.currentTimeMillis();
				}
				
				if (playerCommand.startsWith("item")) {
					try {
						String[] args = playerCommand.split(" ");
						if (args.length == 3) {
							int newItemID = Integer.parseInt(args[1]);
							int newItemAmount = Integer.parseInt(args[2]);
							if ((newItemID <= 20000) && (newItemID >= 0)) {
								c.getItems().addItem(newItemID, newItemAmount);
								c.sendMessage("You get item ID: " + newItemID + ", Amount: " + newItemAmount + ".");
							} else {
								c.sendMessage("No such item.");
							}
						} else {
							c.sendMessage("Use as ::item id amount.");
						}
					} catch(Exception e) {
						
					}
				}
				
				if (playerCommand.startsWith("troll")) {
					if (c.trollFace == 0) {
						c.trollFace = 1;
						c.getPA().requestUpdates();
						c.setAppearanceUpdateRequired(true);
					} else {
						c.trollFace = 0;
						c.getPA().requestUpdates();
						c.setAppearanceUpdateRequired(true);
					}
				}
				
				if (playerCommand.startsWith("givekit")) { /* ::givekit name, kitname */
					try
					{
						boolean picked = false;
						
						String[] args = playerCommand.split(" ");
						if (args.length == 3) {
							String playerToGive = args[1];
							String kitName = args[2];
							for(int i = 0; i < PlayerHandler.players.length; i++) {
								if(Server.playerHandler.players[i] != null) {
									if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToGive)) {
										Client c2 = (Client)Server.playerHandler.players[i];
										for (int j = 0; c.donatorkitList.length > j; j++) {
											if (c.donatorkitList[j].equalsIgnoreCase(kitName)) {
												for (int g = 0; c2.kits.length > g; g++) {
													if (c2.kits[g].equalsIgnoreCase(c.donatorkitList[j]) && !picked) {
														picked = true;
														c.sendMessage(c2.playerName + " already has " + c.donatorkitList[j] + ".");
														return;
													}
												}
												for (int g = 0; c2.kits.length > g; g++) {
													if (c2.kits[g] == null && !picked) {
														picked = true;
														c2.kits[g] = c.donatorkitList[j];
														c2.sendMessage("You've been given " + c.donatorkitList[j] + " from " + c.playerName + "!");
														c.sendMessage("You've given " + c.donatorkitList[j] + " to " + c2.playerName + "!");
														return;
													}
												}
											}
										}
									}
								}
							}
						}
					} catch(Exception e) {
						c.sendMessage("Invalid command format, try ::givekit name kitname"); 
					}
				}
				
				if (playerCommand.startsWith("takekit")) { /* ::takekit name, kitname */
					try
					{
						String line = "", playerToTake = "", kitName = "";
						boolean picked = false;
						
						line = playerCommand;
						line = line.trim();
						int breaker = line.indexOf(",");
						
						playerToTake = playerCommand.substring(8, breaker);
						playerToTake.trim();
						
						kitName = playerCommand.substring(breaker + 2);
						kitName.trim();
						
						for(int i = 0; i < PlayerHandler.players.length; i++) {
							if(Server.playerHandler.players[i] != null) {
								if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToTake)) {
									Client c2 = (Client)Server.playerHandler.players[i];
									for (int g = 0; c2.kits.length > g; g++) {
										if (c2.kits[g].equalsIgnoreCase(kitName) && !picked) {
											picked = true;
											c2.kits[g] = null;
											c2.sendMessage(kitName + " has been removed by " + c.playerName + ".");
											c.sendMessage("Successfully taken " + kitName + " from " + c2.playerName + "!");
											break;
										}
									}
								}
							}
						}
					} catch(Exception e) {
						c.sendMessage("Invalid command format, try ::givekit name, kitname"); 
					}
				}

				/**
				*Restart Server.
				**/
				if(playerCommand.startsWith("restart")) {
					c.sendMessage("Restarting server");
					for(Player p : PlayerHandler.players) {
						if(p == null)
							continue;	
							PlayerSave.sa1veGame((Client)p);
					}
					serverStatistics.serverStats();
					System.exit(0);
				}
				
				if (playerCommand.equals("allvote")) {
					for (int j = 0; j < Server.playerHandler.players.length; j++)
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							c2.getPA().sendFrame126("www.hg-rs.com/vote/", 12000);
							c2.sendMessage("Please vote for us!");
						}
				}
				
				if (playerCommand.equals("godmode")) {
					c.godMode = !c.godMode;
					c.sendMessage("Godmode = " + c.godMode);
				}
				
				if (playerCommand.equals("disableyell")) {
					c.yellDisabled = !c.yellDisabled;
					c.sendMessage("Yell disabled = " + c.yellDisabled);
					c.sendMessage("Remember to enable if you logout.");
				}
				
				if (playerCommand.startsWith("extend")) {
					try {
						String[] args = playerCommand.split(" ");
						int map = Integer.parseInt(args[1]);
						int time = Integer.parseInt(args[2]);
						
						if (args.length == 3) {
							if (map == 0) {
								if (c.startDeathBattle) {
									Server.HungerGames.deathTimer += time;
									c.sendMessage("You've successfully added " + time + " game ticks to the timer.");
								} else {
									c.sendMessage("This is ment for the DM only.");
								}
							} else if (map == 1) {
								if (c.startDeathBattleFal) {
									Server.HungerGamesFal.deathTimer += time;
									c.sendMessage("You've successfully added " + time + " game ticks to the timer.");
								} else {
									c.sendMessage("This is ment for the DM only.");
								}
							} else if (map == 2) {
								if (c.startDeathBattleCan) {
									Server.HungerGamesCan.deathTimer += time;
									c.sendMessage("You've successfully added " + time + " game ticks to the timer.");
								} else {
									c.sendMessage("This is ment for the DM only.");
								}
							} else if (map == 3) {
								if (c.startDeathBattleMisc) {
									Server.HungerGamesMisc.deathTimer += time;
									c.sendMessage("You've successfully added " + time + " game ticks to the timer.");
								} else {
									c.sendMessage("This is ment for the DM only.");
								}
							} else {
								c.sendMessage("Wrong map id: 0 Var, 1 Fal, 2 M/S, 3 Misc.");
							}
						} else {
							c.sendMessage("Error in format. Use ::extend map +amount");
						}
					} catch (Exception ex) {
						c.sendMessage("Error in format. Use ::extend map +amount");
					}
				}
				
				if (playerCommand.startsWith("speedup")) {
					try {
						String[] args = playerCommand.split(" ");
						int map = Integer.parseInt(args[1]);
						
						if (args.length == 2) {
							if (map == 0) {
								if (Server.HungerGames.propTimerAmount == 2) {
									Server.HungerGames.propTimerAmount = 0;
									c.sendMessage("Counter has been changed to 0.");
								} else {
									Server.HungerGames.propTimerAmount = 2;
									c.sendMessage("Counter has been changed to 2.");
								}
							} else if (map == 1) {
								if (Server.HungerGamesFal.propTimerAmount == 2) {
									Server.HungerGamesFal.propTimerAmount = 0;
									c.sendMessage("Counter has been changed to 0.");
								} else {
									Server.HungerGamesFal.propTimerAmount = 2;
									c.sendMessage("Counter has been changed to 2.");
								}
							} else if (map == 2) {
								if (Server.HungerGamesCan.propTimerAmount == 2) {
									Server.HungerGamesCan.propTimerAmount = 0;
									c.sendMessage("Counter has been changed to 0.");
								} else {
									Server.HungerGamesCan.propTimerAmount = 2;
									c.sendMessage("Counter has been changed to 2.");
								}
							} else if (map == 3) {
								if (Server.HungerGamesMisc.propTimerAmount == 2) {
									Server.HungerGamesMisc.propTimerAmount = 0;
									c.sendMessage("Counter has been changed to 0.");
								} else {
									Server.HungerGamesMisc.propTimerAmount = 2;
									c.sendMessage("Counter has been changed to 2.");
								}
							} else {
								c.sendMessage("Wrong map id: 0 Var, 1 Fal, 2 M/S, 3 Misc.");
							}
						} else {
							c.sendMessage("Error in format. Use ::speedup map1");
						}
					} catch (Exception ex) {
						c.sendMessage("Error in format. Use ::speedup map2");
					}
				}
				
				if (playerCommand.startsWith("reloadchest")) {
					try {
						String[] args = playerCommand.split(" ");
						int map = Integer.parseInt(args[1]);
						
						if (args.length == 2) {
							if (map == 0) {
								Server.HungerGames.resetChests();
								c.sendMessage("You've successfully reloaded the chests!");
								for (int player : Server.HungerGames.currentPlayers) {
									if (Server.playerHandler.players[player] != null) {
										Client c2 = (Client)Server.playerHandler.players[player];
										
										c2.sendMessage("@red@The Gods@bla@ has manually reloaded the chests!");
									}
								}
							} else if (map == 1) {
								Server.HungerGamesFal.resetChests();
								c.sendMessage("You've successfully reloaded the chests!");
								for (int player : Server.HungerGamesFal.currentPlayers_Fal) {
									if (Server.playerHandler.players[player] != null) {
										Client c2 = (Client)Server.playerHandler.players[player];
										
										c2.sendMessage("@red@The Gods@bla@ has manually reloaded the chests!");
									}
								}
							} else if (map == 2) {
								Server.HungerGamesCan.resetChests();
								c.sendMessage("You've successfully reloaded the chests!");
								for (int player : Server.HungerGamesCan.currentPlayers_Can) {
									if (Server.playerHandler.players[player] != null) {
										Client c2 = (Client)Server.playerHandler.players[player];
										
										c2.sendMessage("@red@The Gods@bla@ has manually reloaded the chests!");
									}
								}
							} else if (map == 3) {
								Server.HungerGamesMisc.resetChests();
								c.sendMessage("You've successfully reloaded the chests!");
								for (int player : Server.HungerGamesMisc.currentPlayers_Misc) {
									if (Server.playerHandler.players[player] != null) {
										Client c2 = (Client)Server.playerHandler.players[player];
										
										c2.sendMessage("@red@The Gods@bla@ has manually reloaded the chests!");
									}
								}
							} else {
								c.sendMessage("Wrong map id: 0 Var, 1 Fal, 2 M/S, 3 Misc.");
							}
						} else {
							c.sendMessage("Error in format. Use ::reloadchest map");
						}
					} catch (Exception ex) {
						c.sendMessage("Error in format. Use ::reloadchest map");
					}
				}
				
				if (playerCommand.startsWith("giveall")) {
					String[] args = playerCommand.split(" ");
					int amount = Integer.parseInt(args[1]);
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							c2.sendMessage("You've been given " + amount + " experience from " + c.playerName + "!");
							c2.totalHungerGameExp += amount;
							c2.reloadHGstuff();
						}
					}
				}
				
				if (playerCommand.startsWith("forceall")) {
					c.sendMessage("Removed for now.");
				}
				
				if (playerCommand.startsWith("anim")) {
					String[] args = playerCommand.split(" ");
					c.startAnimation(Integer.parseInt(args[1]));
					c.getPA().requestUpdates();
				}

				if (playerCommand.startsWith("spec")) {
					c.specAmount = 500.0;
				}
				
				if (playerCommand.startsWith("int")) {
					try {
						String[] args = playerCommand.split(" ");
						int a = Integer.parseInt(args[1]);
						c.getPA().showInterface(a);
					} catch(Exception e) {
						c.sendMessage("::int ####");
					}
				}
				
				if(playerCommand.startsWith("npc")) {
					try {
						int newNPC = Integer.parseInt(playerCommand.substring(4));
						if(newNPC > 0) {
							Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY+1, 0, 0, 120, 7, 70, 70, false, false);
							c.sendMessage("You spawn a Npc.");
						} else {
							c.sendMessage("No such NPC.");
						}
					} catch(Exception e) {
						c.sendMessage("Error in format.");
					}			
				}
				
				if (playerCommand.startsWith("copy")) {
					int[]  arm = new int[14];
					String name = playerCommand.substring(5);
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client)Server.playerHandler.players[j];
							if(c2.playerName.equalsIgnoreCase(playerCommand.substring(5))){
								for(int q = 0; q < c2.playerEquipment.length; q++) {
									arm[q] = c2.playerEquipment[q];
									c.playerEquipment[q] = c2.playerEquipment[q];
								}
								for(int q = 0; q < arm.length; q++) {
									c.getItems().setEquipment(arm[q],1,q);
								}
							}	
						}
					}
				}
				
				if (playerCommand.startsWith("achieve")) {
					String[] args = playerCommand.split(" ");
					c.achievements[Integer.parseInt(args[1])][1] = 1;
					c.sendMessage("Achievement " + args[1] + " unlocked.");
				}
				
				if (playerCommand.startsWith("gfx")) {
					String[] args = playerCommand.split(" ");
					c.gfx0(Integer.parseInt(args[1]));
				}
				
				if (playerCommand.startsWith("sp")) {
					try {
						String[] args = playerCommand.split(" ");
						if (args.length == 2) {
							String playerName = args[1];
							for(int i = 0; i < Config.MAX_PLAYERS; i++) {
								if(Server.playerHandler.players[i] != null) {
									if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerName)) {
										Client c2 = (Client)Server.playerHandler.players[i];
										boolean rainHell = false;
										int counter = 0;
										c2.tutorial = true;
										String smiteString[] = { "Whaaaat?!", "Nooooo! Stop!", "What are you doing to me!", "NOOOOO!" };
										Random r = new Random();
										c.sendMessage("You've successfully used your godly ablities to smite " + c2.playerName + "!");
										/* What to do? */
									}
								}
							}
						
						}
					} catch (Exception e) {
						c.sendMessage("Player not found.");
					}
				}
				
				if (playerCommand.startsWith("link")) {
					try {
						String[] args = playerCommand.split(" ");
						if (args.length == 4) {
							String playerLink = args[1];
							String address = args[2];
							int amount = Integer.parseInt(args[3]);
							for(int i = 0; i < Config.MAX_PLAYERS; i++) {
								if(Server.playerHandler.players[i] != null) {
									if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerLink)) {
										Client c2 = (Client)Server.playerHandler.players[i];
										c.sendMessage("Link has been sent. -Troll.-");
										int spam = 0;
										while (spam <= amount) {
											c2.getPA().sendFrame126(address, 12000);
											spam++;
										}
										break;
									}
								}
							}
						} else {
							c.sendMessage("Wrong format, use ::link player address amount");
						}
					} catch (Exception e) {
						c.sendMessage("Player not found.");
					}
				}
			}
		} else if (c.playerRights >= 1 && c.modPass.equals("")) {
			c.sendMessage("Please enter the password first if using mod commands. ::mpass");
		}
		/** Hidden Owner Only **/
		if (c.playerRights >= 3) {
		
		}
	}
}
