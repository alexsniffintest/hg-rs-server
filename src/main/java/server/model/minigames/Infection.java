package server.model.minigames;

import server.Server;
import server.model.players.Client;
import server.model.players.ExceptionCatcher;
import server.util.Misc;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;


public class Infection {
	
	private Client c;
	public Infection() {
		
	}
	
	public static ArrayList<Integer> currentPlayers = new ArrayList<Integer> ();
	public ArrayList<Integer> currentPlayers_Wait = new ArrayList<Integer> ();
	
	public int startingSpawn[][] = {
		{ 3503, 3217, 0 }
	};
	
	private final int GAME_TIMER = 60;
	private final int GAME_START_TIMER = 30;	
	public int timeRemaining = -1, gameTimer = 0, deathTimer = -1, deathCounter = 0, winnerWins = 0, randomRefill, randomRefill_2;
	public int gameStartTimer = GAME_START_TIMER;
	public static int countDownTimer = -1, gameId = 0, propTimerAmount = 2;
	public static boolean lessThen5Left = true, doubleExp = false, gameOver = true;
	private boolean chestReset = false, chestReset_2 = false;
	private String winnerName = "", winnerKit = "";
	

	public void joinWait(Client c) {
		for (int player : currentPlayers_Wait) {
			if (Server.playerHandler.players[player] != null) {
				Client o = (Client) Server.playerHandler.players[player];
				if (c.connectedFrom.equals(o.connectedFrom)) {
					o.ipCount++;
					if (o.ipCount >= 2) {
						c.sendMessage("You can only have two accounts per ip in a match!");
						o.sendMessage("You can only have two accounts per ip in a match!");
						return;
					} else {
						o.sendMessage("Someone with the same ip has joined the lobby!");
						o.sendMessage("You have been marked, if found attempting to cheat could result in a ban!");
					}
				}
			}
		}
		if (c.getPA().getWearingAmount() > 0 || c.getItems().freeSlots() < 28) {
			c.sendMessage("Please bank any of your items before joining!");
			return;
		}
		if (currentPlayers_Wait.size() >= 50) {
			c.sendMessage("The lobby is currently full, please try again soon.");
			return;
		} else {
			c.infectionWait = true;
			c.inCwWait = true;
			if(currentPlayers_Wait.contains(c.playerId) || currentPlayers.contains(c.playerId)) {
				c.sendMessage("You're already in the game.");
				c.getPA().movePlayer(3165, 9633, 0);
				c.randomMap = 4;
				return;
			}
			c.randomMap = 4;
			currentPlayers_Wait.add(c.playerId);
			c.getItems().deleteAllItems();
			c.myKit = "Outcast";
		}
		if(!c.isInLobbyInfect()) {
			c.sendMessage("You've enter the Infection game lobby, ready yourself before the game starts.");
			c.getPA().movePlayer(3165, 9633, 0);
			c.randomMap = 4;
		}
	}
	
	public void leaveWaitingRoom(Client c) {//Leaving location
		try {
			c.infectionWait = false;
			c.inCwWait = false;
			c.getPA().movePlayer(3363, 9640, 0);
			currentPlayers_Wait.remove(currentPlayers_Wait.indexOf(c.playerId));
			c.myKit = "";
			c.setAppearanceUpdateRequired(true);
			c.ipCount = 0;
			c.randomMap = -1;
		} catch (Exception ex) {
			ex.printStackTrace();
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			c.printException = sw.toString();
			ExceptionCatcher.printException();
		}
	}
	
	int properTimer = 0;
	public void process() {
		try {
			if (properTimer > 0) {
				properTimer--;
				return;
			} else {
				properTimer = propTimerAmount;
			}

			if (countDownTimer > 0) {
				countDownTimer--;
				countDown();
			}
			
			if (gameOver) {
				if (gameStartTimer == 1 && currentPlayers_Wait.size() < 1) {
					doubleExp = false;
				}
				if (Misc.random(45) == 0 && gameStartTimer == 30 && !doubleExp || Misc.random(45) == 0 && gameStartTimer == 50 && !doubleExp) {
					doubleExp = true;
					announceExpBoost();
				}
				if (gameStartTimer > 1) {
					gameStartTimer--;
				}
			}
			
			if (gameStartTimer > 1) {
				updatePlayers();
			} else if (gameStartTimer <= 1) {
				startGame();
			}
			
			if (!gameOver)
				gameTimer++;
			
			if (timeRemaining > 0) {
				timeRemaining--;
				updateInGamePlayers();
			}
			
			/** 
			* The process is called every 2 seconds
			**/
		} catch (Exception ex) {
			ex.printStackTrace();
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			c.printException = sw.toString();
			ExceptionCatcher.printException();
		}
	}
	
	public void announceExpBoost() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client)Server.playerHandler.players[j];
				c2.sendMessage("The next @blu@Infection@bla@ match will be running at double exp, hurry and join!");
			}
		}
	}
	
	public void updatePlayers() {
		for (int player : currentPlayers_Wait) {
			if (Server.playerHandler.players[player] != null) {
				Client c = (Client) Server.playerHandler.players[player];
				if (!c.waitInterface) {
					c.getPA().sendFrame126("Time till next match: @whi@" + ((timeRemaining) + (gameStartTimer)), 22132);
					if (doubleExp) {
						c.getPA().sendFrame126("Players in lobby: @red@(X2 EXP) @whi@" + currentPlayers_Wait.size(), 22134);
					} else {
						c.getPA().sendFrame126("Players in lobby: @whi@" + currentPlayers_Wait.size(), 22134);
					}
					c.getPA().sendFrame126("Players remaining in previous match: @whi@" + currentPlayers.size(), 22133);
					c.getPA().sendFrame126("Your kit: @whi@ "  + c.myKit, 22135);
				}
			} else {
				currentPlayers_Wait.remove(currentPlayers_Wait.indexOf(player));
			}
		}
	}
	
	public void updateInGamePlayers() {
		try {
			if (currentPlayers.size() > 0) {
				for (int player : currentPlayers) {
					if (Server.playerHandler.players[player] != null) {
						Client c = (Client) Server.playerHandler.players[player];
						
						/** 
						** This is for PLAYER BASED ingame stuff, do not call any minigame methods here 
						**/
						
						if (c.inCwGame) {
							if (c.HGAttack) {
								c.getCombat().timedPrayerDec();
							}
							c.getPA().sendFrame126("Survive for: @whi@" + timeRemaining, 22101);
							c.getPA().sendFrame126("Outcasts left: @whi@" + currentPlayers.size(), 22102);
							c.getPA().sendFrame126("Experience earned: @whi@" + c.currentExpEarned, 22103);
							c.getPA().sendFrame126("blank for now", 22104);
						}
						if (currentPlayers.size() == 1 && !c.testmode) { //1
							c.sendMessage("You're the last alive! Best of luck!");
						}
						
						if (System.currentTimeMillis() - c.lastGift > 20000) {
							if (c.perkGifted == 1 && gameTimer > 20 && Misc.random(55) == 0) {
								c.sendMessage("Some Manta rays appear beneath your feet randomly! Lucky!");
								c.lastGift = System.currentTimeMillis();
								c.sendMessage("You receive some food.");
								Server.itemHandler.createGroundItem(c, 391, c.absX, c.absY, 1, c.getId());
							}
						}
						
						if (c.chestRoomTimer >= 0) { //Hidden safe room?
							if (c.startDeathBattleMisc) {
								c.chestRoomTimer = 0;
							} else {
								c.chestRoomTimer--;
							}
							if (c.chestRoomTimer == 10) {
								c.sendMessage("You only have 10 seconds left before the magic affect ends!");
							} else if (c.chestRoomTimer <= 5 && c.chestRoomTimer > 0) {
								c.sendMessage("You're going to be teleported back in " + c.chestRoomTimer + "!");
							} else if (c.chestRoomTimer == 0) {
								if (c.startDeathBattleMisc) {
									c.getPA().movePlayer(2787 + Misc.random(15), 3774 + Misc.random(19), 0);
								} else {
									c.getPA().movePlayer(2540, 3871, 0);
								}
							}
						}
						
						/* end of player stuff */
						
					} else {
						currentPlayers.remove(currentPlayers.indexOf(player));
					}
				}
				
				/**
				** This is for SERVER BASED ingame stuff, do not add any player related methods, variables, or objects 
				**/
				if (c.startDeathBattleMisc == true && Misc.random(2) == 0) {
					//random goozing gases? lol
					int xIce = 2776 + Misc.random(26), yIce = 3775 + Misc.random(24);
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client o = (Client)Server.playerHandler.players[j];
							int xVal = (xIce - o.absX);
							int yVal = (yIce - o.absY);
							o.getPA().createPlayersStillGfx(370, xIce, yIce, 0, 20);
							if (Math.abs(xVal) <= 2 && Math.abs(yVal) <= 2 && o.inCwGame) {
								if (o.myKit.equalsIgnoreCase("BLIZZARD")) {
									o.sendMessage("You've been hit by random ice, but you're immune!");
									return;
								}
								o.sendMessage("You've been hit by random ice!");
								o.freezeTimer = 20;
								int blizzardDamage = Misc.random(25);
								o.dealDamage(blizzardDamage);
								o.handleHitMask(blizzardDamage);
								o.getPA().refreshSkill(3);
								o.gfx0(369);
							}
						}
					}
				}
				/* End of server stuff */
			} else {
				System.out.println("Error3, current players: " + currentPlayers.size());
				endGame();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			c.printException = sw.toString();
			ExceptionCatcher.printException();
		}
	}
	
	public void forceJoin(String name) {
		for (int player = 0; player < Server.playerHandler.players.length; player++) {
			if (Server.playerHandler.players[player] != null) {
				Client c = (Client)Server.playerHandler.players[player];
				if (c.randomMap == 4) {
					if (name.equals(c.playerName) && !c.inCwGame) {
						if (c.lastGame == gameId) {
							c.sendMessage("You can't join because you've already been in this game!");
							break;
						}
						if (currentPlayers.size() >= 50) {
							c.sendMessage("The game seems to already be full, try again when someone dies.");
							break;
						}
						int randomArray = (int)(Math.random() * startingSpawn.length);
						c.getPA().movePlayer(startingSpawn[randomArray][0], startingSpawn[randomArray][1], 0);
						c.getItems().deleteAllItems();
						c.infectionWait = false;
						c.inCwWait = false;
						c.inCwGame = true;
						c.spectate = false;
						c.HGAttack = true;
						c.lastGame = gameId;
						
						if (c.achievements[23][0] == 0) {
							c.achievements[23][1] = 1;
							c.achievementsHandler();
						}
						
						if (c.perkTwo == 1) {
							c.getItems().addItem(385, 5);
						}
										
						if (c.perkThreeMelee == 1) {
							c.meleePerk = 0.3;
						} else if (c.perkThreeMelee == 2) {
							c.meleePerk = 0.8;
						}
						
						if (c.perkFourRange == 1) {
							c.rangePerk = 0.3;
						} else if (c.perkFourRange == 2) {
							c.rangePerk = 0.8;
						}
						
						if (c.perkFiveMagic == 1) {
							c.magicPerk = 0.3;
						} else if (c.perkFiveMagic == 2) {
							c.magicPerk = 0.8;
						}
						
						if (c.perkEightStat == 1) {
							c.playerLevel[0] = 120;
							c.playerLevel[1] = 120;
							c.playerLevel[2] = 120;
							c.playerLevel[4] = 120;
							c.getPA().refreshSkill(0);
							c.getPA().refreshSkill(1);
							c.getPA().refreshSkill(2);
							c.getPA().refreshSkill(4);
						}
						
						if (c.perkVeng == 1) {
							c.getItems().addItem(557, 100);
							c.getItems().addItem(9075, 40);
							c.getItems().addItem(560, 20);
						}
						
						if (c.beerPerk == 1) {
							c.getItems().addItem(3801, 1);
						}
						
						if (c.totalGames <= 10) {
							c.meleePerk = 0.5;
							c.rangePerk = 0.5;
							if (doubleExp) {
								c.expBoost = 4;
							} else {
								c.expBoost = 2;
							}
						} else {
							c.expBoost = 1;
						}
						
						if (doubleExp) {
							c.expBoost = 2;
						} else {
							c.expBoost = 1;
						}
						
						c.totalGames++;
						if (c.totalGames == 11) {
							c.sendMessage("Your newbie bonus has ended!");
						}
						c.noSmuggle = 1;
						c.totalKills = c.KC;
						c.setAppearanceUpdateRequired(true);
						currentPlayers_Wait.remove(currentPlayers_Wait.indexOf(player));
						currentPlayers.add(player);
						for (int player2 : currentPlayers) {
							if (Server.playerHandler.players[player2] != null) {
								Client c2 = (Client)Server.playerHandler.players[player2];
								if (c.playerName.equals(c2.playerName)) {
									c.sendMessage("You've dropped down into the maddness. Good luck strange fool.!");
								} else {
									c2.sendMessage("@blu@" + name + "@bla@ has joined into the maddess using the Connector Perk, are they mad?!");
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void startGame() {
		if (currentPlayers_Wait.size() < 1 && !c.testmode) {//1 for now
			for (int player : currentPlayers_Wait) {
				if (Server.playerHandler.players[player] != null) {
					Client c = (Client)Server.playerHandler.players[player];
					c.sendMessage("Sorry but the match can't start till there's atleast 6 players.");
				} else {
					currentPlayers_Wait.remove(currentPlayers_Wait.indexOf(player));
				}
			}
			gameStartTimer = 60;	
			System.out.println("Need 6 players to start, adding 60 more seconds to timer.");
			return;
		}
		gameId = Misc.random(100000);
		gameStartTimer = -1;
		System.out.println("Starting game.");
		for (int player : currentPlayers_Wait) {
			if (Server.playerHandler.players[player] != null) {
				Client c = (Client)Server.playerHandler.players[player];
				c.ipCount = 0;
				c.spectate = false;
				c.lastGame = gameId;
				int randomArray;
				c.getItems().deleteAllItems();
				
				for (int coord = 0; coord < currentPlayers_Wait.size(); coord = coord) {
					randomArray = (int)(Math.random() * startingSpawn.length);
					if (startingSpawn[randomArray][2] == 0) {
						startingSpawn[randomArray][2] = 1;
						c.infectionWait = false;
						c.inCwWait = false;
						c.inCwGame = true;
						coord++;
						c.getPA().movePlayer(startingSpawn[randomArray][0], startingSpawn[randomArray][1], 0);
						break;
					}
				}
				c.playerLevel[3] = 99;
				c.getPA().refreshSkill(3);
				c.playerLevel[5] = 99;
				c.getPA().refreshSkill(5);
				
				/* handle any items */
				//c.getItems().addItem(2347, 1);//hammer (all kits get this)
				
				if (c.perkTwo == 1) {
					c.getItems().addItem(385, 5);
				}
				
				if (c.perkThreeMelee == 1) {
					c.meleePerk = 0.03;
				} else if (c.perkThreeMelee == 2) {
					c.meleePerk = 0.08;
				}
				
				if (c.perkFourRange == 1) {
					c.rangePerk = 0.03;
				} else if (c.perkFourRange == 2) {
					c.rangePerk = 0.08;
				}
				
				if (c.perkFiveMagic == 1) {
					c.magicPerk = 3;
				} else if (c.perkFiveMagic == 2) {
					c.magicPerk = 6;
				}
				
				if (c.perkEightStat == 1) {
					c.playerLevel[0] = 120;
					c.playerLevel[1] = 120;
					c.playerLevel[2] = 120;
					c.playerLevel[4] = 120;
					c.getPA().refreshSkill(0);
					c.getPA().refreshSkill(1);
					c.getPA().refreshSkill(2);
					c.getPA().refreshSkill(4);
				}
				
				if (c.perkVeng == 1) {
					c.getItems().addItem(557, 100);
					c.getItems().addItem(9075, 40);
					c.getItems().addItem(560, 20);
				}
				
				if (c.beerPerk == 1) {
					c.getItems().addItem(3801, 1);
				}
				
				if (c.totalGames <= 10) {
					c.meleePerk = 0.5;
					c.rangePerk = 0.5;
					if (doubleExp) {
						c.expBoost = 4;
					} else {
						c.expBoost = 2;
					}
				} else {
					c.expBoost = 1;
				}
				
				if (doubleExp) {
					c.expBoost = 2;
				} else {
					c.expBoost = 1;
				}
				
				c.totalGames++;
				if (c.totalGames == 11) {
					c.sendMessage("Your newbie bonus has ended!");
				}
				c.noSmuggle = 1;
				c.totalKills = c.KC;
				c.setAppearanceUpdateRequired(true);
				currentPlayers.add(player);
			} else {
				currentPlayers_Wait.remove(currentPlayers_Wait.indexOf(player));
			}	
		}
		timeRemaining = GAME_TIMER;
		gameOver = false;
		countDownTimer = 16;
		gameStartTimer = GAME_START_TIMER;
		currentPlayers_Wait.clear();
	}
	
	public void countDown() {
		for (int player : currentPlayers) {
			if (Server.playerHandler.players[player] != null) {
				Client c = (Client)Server.playerHandler.players[player];
				if (countDownTimer > 0) {
					c.sendMessage("Picking first infected in... " + countDownTimer);
				} else {
					c.HGAttack = true;
					c.isSkulled = true;
					c.skullTimer = 2500;
					c.headIconPk = 0;
					c.getPA().requestUpdates();
					c.sendMessage("Survive!");
					if (c.achievements[13][0] == 0) {
						c.achievements[13][1] = 1;
						c.achievementsHandler();
					}
					//Enable PVP under process in client
				}
			} else {
				currentPlayers.remove(currentPlayers.indexOf(player));
			}	
		}
	}
	
	public void endGame() {
		System.out.println("Ending game.");
		
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				
				c.sendMessage("Who won?");
				
			}
		}
		
		for (int coord = 0; coord < startingSpawn.length; coord++) { //Resets spawns
				if (startingSpawn[coord][2] == 1) {
					startingSpawn[coord][2] = 0;
				}
		}
		
		gameStartTimer = GAME_START_TIMER;
		timeRemaining = -1;
		lessThen5Left = true;
		doubleExp = false;
		gameOver = true;
		gameTimer = 0;
		deathCounter = 0;
		deathTimer = -1;
		c.startDeathBattleMisc = false;
		chestReset = false;
		chestReset_2 = false;
		currentPlayers.clear();
		c.totalGamesPlayed++;
	}
}