package server.model.minigames;

import server.Server;
import server.model.players.Client;
import server.model.players.ExceptionCatcher;
import server.util.Misc;
import server.world.ObjectManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;


public class HungerGamesMisc {
	
	private Client c;
	public HungerGamesMisc() {
		
	}
	
	public static ArrayList<Integer> currentPlayers_Misc = new ArrayList<Integer> ();
	public ArrayList<Integer> currentPlayers_MiscWait = new ArrayList<Integer> ();
	
	public int startingSpawnMisc[][] = {
		{ 2544, 3869, 0 },
		{ 2544, 3871, 0 },
		{ 2544, 3873, 0 },
		{ 2544, 3875, 0 },
		{ 2544, 3877, 0 },
		{ 2542, 3877, 0 },
		{ 2540, 3877, 0 },
		{ 2538, 3877, 0 },
		{ 2536, 3877, 0 },
		{ 2536, 3875, 0 },
		{ 2536, 3873, 0 },
		{ 2536, 3871, 0 },
		{ 2536, 3869, 0 },
		{ 2538, 3869, 0 },
		{ 2540, 3869, 0 },
		{ 2542, 3869, 0 }
	};
	
	public int forsakenSpawnMisc[][] = {
		{ 2505, 3884 },
		{ 2594, 3875 },
		{ 2616, 3894 }
	};
	
	public int npcSpawnMisc[][] = {
		{ 2536, 3861, 0 },
		{ 2525, 3880, 0 },
		{ 2509, 3883, 0 },
		{ 2541, 3892, 0 },
		{ 2541, 3887, 0 },
		{ 2570, 3877, 0 },
		{ 2560, 3860, 0 },
		{ 2554, 3852, 0 },
		{ 2536, 3848, 0 },
		{ 2591, 3882, 0 },
		{ 2606, 3892, 0 },
		{ 2615, 3851, 0 },
		{ 2603, 3858, 0 },
		{ 2592, 3872, 0 }
	};
	
	private final int GAME_TIMER = 300;
	private final int GAME_START_TIMER = 60;	
	public int timeRemaining = -1, gameTimer = 0, deathTimer = -1, deathCounter = 0, winnerWins = 0, randomRefill, randomRefill_2, npcsSpawned = 0;;
	public int gameStartTimer = GAME_START_TIMER;
	public static int countDownTimer = -1, gameId = 0, propTimerAmount = 2, dcTimer = 10;
	public static boolean lessThen5Left = true, doubleExp = false, gameOver = true, dcHandle = false;
	private boolean chestReset = false, chestReset_2 = false;
	private String winnerName = "", winnerKit = "", winnerDiff = "";
	

	public void joinWait(Client c) {
		for (int player : currentPlayers_MiscWait) {
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
		if (currentPlayers_MiscWait.size() >= 16) { //Current max is 14
			if(c.isInLobby()) { 
				c.getPA().movePlayer(3363, 9640, 0);
			}
			c.sendMessage("The lobby is currently full, please try again soon.");
			return;
		} else {
			c.inCwWait = true;
			if(currentPlayers_MiscWait.contains(c.playerId) || currentPlayers_Misc.contains(c.playerId)) { //Checking if the player is already inside the lobby or game.
				c.sendMessage("You're already in the game.");
				c.getPA().movePlayer(2595, 3808, 0);
				c.randomMap = 3;
				return;
			}
			c.randomMap = 3;
			currentPlayers_MiscWait.add(c.playerId);
			c.getItems().deleteAllItems();
			if (c.rememberedKit == null) {
				c.myKit = "Default";
				c.rememberedKit = "Default";
			} else {
				c.myKit = c.rememberedKit;
			}
		}
		if(!c.isInLobby()) {
			c.sendMessage("You enter the Miscellania Lobby.");
			c.getPA().movePlayer(2595, 3808, 0);
			c.randomMap = 3;
		}
	}
	
	public void leaveWaitingRoom(Client c) {//Leaving location
		try {
			c.inCwWait = false;
			c.getPA().movePlayer(3363, 9640, 0);
			currentPlayers_MiscWait.remove(currentPlayers_MiscWait.indexOf(c.playerId));
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
			
			if (countDownTimer == 11) {
				spawnChests();
			}

			if (countDownTimer > 0) {
				countDownTimer--;
				countDown();
			}
			
			if (gameOver) {
				if (gameStartTimer == 1 && currentPlayers_MiscWait.size() < 1) {
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
				if (!dcHandle) {
					timeRemaining--;
				}
				if (gameTimer >= 15 && Misc.random(44) == 0 && npcsSpawned < 4) {
					npcSpawning(Misc.random(15), (int)(Math.random() * npcSpawnMisc.length));
					npcsSpawned++;
				}
				updateInGamePlayers();
			}
			
			if (deathCounter > 10 && c.startDeathBattleMisc == false) {
				deathBattle();
			}
			
			if (deathTimer > 0 && c.startDeathBattleMisc == true) {
				if (!dcHandle) {
					deathTimer--;
				}
				updateInGamePlayers();
			} else if (deathTimer == 0 || gameTimer >= 1500) {
				endDeathBattle();
				endGame();
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
	
	public void npcBoss() {
		int x = 2787 + Misc.random(15);
		int y = 3774 + Misc.random(19);
		Server.npcHandler.spawnNpc2(795, x, y, 0, 1, 200, 30, 260, 300);
		Server.npcHandler.spawnNpc2(145, x - 1 + Misc.random(2), y - 1 + Misc.random(2), 0, 1, 104, 12, 90, 140);
		Server.npcHandler.spawnNpc2(145, x - 1 + Misc.random(2), y - 1 + Misc.random(2), 0, 1, 104, 12, 90, 140);
		Server.npcHandler.spawnNpc2(145, x - 1 + Misc.random(2), y - 1 + Misc.random(2), 0, 1, 104, 12, 90, 140);
		for (int player : currentPlayers_Misc) {
			if (Server.playerHandler.players[player] != null) {
				Client c = (Client) Server.playerHandler.players[player];
				c.sendMessage("Watch out! The @blu@Ice Queen @bla@ has and her warriors have appeared!");
			}
		}
	}
	
	public void npcSpawning(int monster, int randomSpawn) {
		//Common - Hill giants, Moss giants, Spiders, Zombies, Skeletons, Wolfs
		//Rare - Green Dragon, Dagannoths, Hero, Abby demon
		//Super Rare(Bosses) - none yet
		String npcName = "";
		int amount = 0;
		
		if (monster <= 10) { //common
			switch ((int)(Math.random() * 7)) {
				case 0:
					amount = Misc.random(1) + 2;
					npcName = "Hill Giants";
					for (int i = 0; i < amount; i++) {
						Server.npcHandler.spawnNpc2(117, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 70, 16, 40, 80);
					}
				break;
				
				case 1:
					amount = Misc.random(1) + 2;
					npcName = "Moss Giants";
					for (int i = 0; i < amount; i++) {
						Server.npcHandler.spawnNpc2(112, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 120, 20, 80, 120);
					}
				break;
				
				case 2:
					amount = Misc.random(1) + 2;
					npcName = "Spiders";
					for (int i = 0; i < amount; i++) {
						Server.npcHandler.spawnNpc2(1478, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 70, 16, 50, 50);
					}
				break;
				
				case 3:
					amount = Misc.random(1) + 2;
					npcName = "Zombies";
					for (int i = 0; i < amount; i++) {
						Server.npcHandler.spawnNpc2(76, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 62, 6, 20, 30);
					}
				break;
				
				case 4:
					amount = Misc.random(1) + 2;
					npcName = "Skeletons";
					for (int i = 0; i < amount; i++) {
						Server.npcHandler.spawnNpc2(93, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 90, 10, 50, 90);
					}
				break;
				
				case 5:
					amount = Misc.random(1) + 2;
					npcName = "Wolves";
					for (int i = 0; i < amount; i++) {
						Server.npcHandler.spawnNpc2(95, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 140, 25, 100, 120);
					}
				break;
				
				case 6:
					amount = Misc.random(1) + 2;
					npcName = "Black Knights";
					for (int i = 0; i < amount; i++) {
						Server.npcHandler.spawnNpc2(178, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 84, 8, 50, 100);
					}
				break;
			}
		} else { //rare
			switch ((int)(Math.random() * 5)) {
				case 0:
					amount = Misc.random(1) + 1;
					npcName = "Green Dragons";
					for (int i = 0; i < amount; i++) {
						Server.npcHandler.spawnNpc2(941, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 140, 30, 150, 200);
					}
				break;
				
				case 1:
					amount = Misc.random(1) + 1;
					npcName = "Dagannoths";
					for (int i = 0; i < amount; i++) {
						Server.npcHandler.spawnNpc2(1347, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 190, 24, 180, 210);
					}
				break;
				
				case 2:
					amount = Misc.random(1) + 1;
					npcName = "Heros";
					for (int i = 0; i < amount; i++) {
						Server.npcHandler.spawnNpc2(21, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 136, 24, 200, 210);
					}
				break;
				
				case 3:
					amount = Misc.random(1) + 1;
					npcName = "Abyssal Demons";
					for (int i = 0; i < amount; i++) {
						Server.npcHandler.spawnNpc2(1615, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 190, 30, 260, 260);
					}
				break;
				
				case 4:
					amount = Misc.random(1) + 1;
					npcName = "Mages";
					Server.npcHandler.spawnNpc2(912, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 190, 20, 200, 200);
					Server.npcHandler.spawnNpc2(913, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 190, 20, 200, 200);
					Server.npcHandler.spawnNpc2(914, npcSpawnMisc[randomSpawn][0] - 1 + Misc.random(2), npcSpawnMisc[randomSpawn][1] - 1 + Misc.random(2), 0, 1, 190, 20, 200, 200);
				break;
			}
		}
		for (int player : currentPlayers_Misc) {
			if (Server.playerHandler.players[player] != null) {
				Client c = (Client) Server.playerHandler.players[player];
				c.sendMessage("Some @blu@" + npcName + "@bla@ have spawned somewhere on the map, becareful!");
			}
		}
	}
	
	public void announceExpBoost() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client)Server.playerHandler.players[j];
				c2.sendMessage("The game at @blu@Miscellania@bla@ has been choosen for a double exp game!");
			}
		}
	}
	
	public void deathBattle() {
		deathTimer = 300;
		if (currentPlayers_Misc.size() > 0) {
			Server.npcHandler.removeGameNpcs(3);
			for (int player : currentPlayers_Misc) {
				if (Server.playerHandler.players[player] != null) {
					Client c = (Client) Server.playerHandler.players[player];
					
					c.getCombat().resetPlayerAttack();
					c.getPA().movePlayer(2787 + Misc.random(15), 3774 + Misc.random(19), 0);
					//c.stopMovement();
					c.sendMessage("Only the last alive wins!");
					c.sendMessage("Watch out for the ice...");
					c.startDeathBattleMisc = true;
					c.getPA().requestUpdates();
				} else {
					currentPlayers_Misc.remove(currentPlayers_Misc.indexOf(player));
				}
			}
			if (Misc.random(4) == 0) {
				npcBoss();
				Server.npcHandler.npcAction(221, "How dare you disturb my rest!", 0);
				deathTimer = 400;
			}
		} else {
			System.out.println("Error1, current players: " + currentPlayers_Misc.size());
			endGame();
		}
	}
	
	public void endDeathBattle() {
		if (currentPlayers_Misc.size() > 0) {
			for (int player : currentPlayers_Misc) {
				if (Server.playerHandler.players[player] != null) {
					Client c = (Client) Server.playerHandler.players[player];
					if (c.openTrade == true) {
						c.getTradeAndDuel().declineTrade();
					}
					c.sendMessage("You have failed to win within the time given to you!");
					c.dealDamage(300);
					c.handleHitMask(300);
					c.getPA().refreshSkill(3);
				} else {
					currentPlayers_Misc.remove(currentPlayers_Misc.indexOf(player));
				}
			}
		} else {
			System.out.println("Error2, current players: " + currentPlayers_Misc.size());
			endGame();
		}
	}
	
	public void updatePlayers() {
		for (int player : currentPlayers_MiscWait) {
			if (Server.playerHandler.players[player] != null) {
				Client c = (Client) Server.playerHandler.players[player];
				if (!c.waitInterface) {
					if (c.startDeathBattleMisc) {
						c.getPA().sendFrame126("Time till next match: @red@(DM) @whi@" + ((gameStartTimer) + (deathTimer)), 22132);
					} else {
						c.getPA().sendFrame126("Time till next match: @whi@" + ((timeRemaining) + (gameStartTimer)), 22132);
					}
					if (doubleExp) {
						c.getPA().sendFrame126("Players in lobby: @red@(X2 EXP) @whi@" + currentPlayers_MiscWait.size(), 22134);
					} else {
						c.getPA().sendFrame126("Players in lobby: @whi@" + currentPlayers_MiscWait.size(), 22134);
					}
					c.getPA().sendFrame126("Players remaining in previous match: @whi@" + currentPlayers_Misc.size(), 22133);
					c.getPA().sendFrame126("Your kit: @whi@ "  + c.myKit, 22135);
				}
			} else {
				currentPlayers_MiscWait.remove(currentPlayers_MiscWait.indexOf(player));
			}
		}
	}
	
	public void updateInGamePlayers() {
		try {
			if (currentPlayers_Misc.size() > 0 && !dcHandle) {
				for (int player : currentPlayers_Misc) {
					if (Server.playerHandler.players[player] != null) {
						Client c = (Client) Server.playerHandler.players[player];
						
						/** 
						** This is for PLAYER BASED ingame stuff, do not call any minigame methods here 
						**/
						
						if (c.inCwGame) {
							if (c.HGAttack) {
								c.getCombat().timedPrayerDec();
							}
							if (c.startDeathBattleMisc) {
								c.getPA().sendFrame126("Win in: @whi@" + deathTimer, 22101);
							} else {
								c.getPA().sendFrame126("Survive for: @whi@" + timeRemaining, 22101);
							}
							c.getPA().sendFrame126("Players left: @whi@" + currentPlayers_Misc.size(), 22102);
							c.getPA().sendFrame126("Experience earned: @whi@" + (((gameTimer * 50) / currentPlayers_Misc.size()) + c.currentExpEarned) * c.expBoost, 22103);
							if(c.myKit.equalsIgnoreCase("FLASH")) { 
								if (System.currentTimeMillis() - c.lastFlash > 8000) {
									c.getPA().sendFrame126("Your kit: @whi@ " + c.myKit + " [ Cooldown: @gre@READY@whi@ ]", 22104);
								} else {
									long finalTimeFlash = 8 - (System.currentTimeMillis() - c.lastFlash) / 1000;
									c.getPA().sendFrame126("Your kit: @whi@ " + c.myKit + " [ Cooldown: @red@" + finalTimeFlash + "@whi@ ]", 22104);
								}
							} else if(c.myKit.equalsIgnoreCase("JUMPER")) { 
								 if(c.hasPlacedPortalDown == false) {
									c.getPA().sendFrame126("Your kit: @whi@ " + c.myKit + " [ Cooldown: @red@NOT PLACED@whi@ ]", 22104);
								} else if (System.currentTimeMillis() - c.lastJumperPortal > 20000) {
									c.getPA().sendFrame126("Your kit: @whi@ " + c.myKit + " [ Cooldown: @gre@READY@whi@ ]", 22104);
								} else {
									long finalTimeJump = 20 - (System.currentTimeMillis() - c.lastJumperPortal) / 1000;
									c.getPA().sendFrame126("Your kit: @whi@ " + c.myKit + " [ Cooldown: @red@" + finalTimeJump + "@whi@ ]", 22104);
								}
							} else if(c.myKit.equalsIgnoreCase("NECROMORPHER")) {
								if (System.currentTimeMillis() - c.lastNecro2 < 85000) {
									long necrotime = 85 - (System.currentTimeMillis() - c.lastNecro2) / 1000;
									c.getPA().sendFrame126("Your kit: @whi@ " + c.myKit + " [ Cooldown: @red@" + necrotime + "@whi@ ]", 22104);
								} else if (System.currentTimeMillis() - c.lastNecro > 45000 && System.currentTimeMillis() - c.lastNecro2 > 75000) {
									c.getPA().sendFrame126("Your kit: @whi@ " + c.myKit + " [ Cooldown: @gre@READY@whi@ ]", 22104);
								} else if (c.necroMode && System.currentTimeMillis() - c.lastNecro < 45000) {
									long necrotime2 = 35 - (System.currentTimeMillis() - c.lastNecro) / 1000;
									c.getPA().sendFrame126("Your kit: @whi@ " + c.myKit + " [ Morph Time: @yel@" + necrotime2 + "@whi@ ]", 22104);
								}
							} else {
								c.getPA().sendFrame126("Your kit: @whi@ " + c.myKit, 22104);
							}
						}
						if (currentPlayers_Misc.size() == 1 && !c.testmode) { //1
							c.getPA().movePlayer(3363, 9640, 0);
							c.totalGameWins++;
							double diffcultyExp = 0;
							if (c.difficultLevel == 3) {
								diffcultyExp = (((gameTimer * 50) + c.currentExpEarned) * c.expBoost) * 0.3;
								winnerDiff = "INSANE";
								c.insaneWins++;
							} else if (c.difficultLevel == 2) {
								diffcultyExp = (((gameTimer * 50) + c.currentExpEarned) * c.expBoost) * 0.2;
								winnerDiff = "EXTREME";
								c.extremeWins++;
							} else if (c.difficultLevel == 1) {
								diffcultyExp = (((gameTimer * 50) + c.currentExpEarned) * c.expBoost) * 0.1;
								winnerDiff = "TOUGH";
								c.toughWins++;
							} else if (c.difficultLevel == 0) {
								diffcultyExp = (((gameTimer * 50) + c.currentExpEarned) * c.expBoost) * 0;
								winnerDiff = "EASY";
							}
							c.totalHungerGameExp = (int) (((gameTimer * 50) * c.expBoost) + diffcultyExp) + c.totalHungerGameExp;
							c.currentExpEarned = (int) ((((gameTimer * 50) + c.currentExpEarned) * c.expBoost) + diffcultyExp);
							c.totalExp += c.currentExpEarned;
							c.sendMessage("You won! You earned @red@" + c.currentExpEarned + "@bla@ exp and now have @red@" + c.totalGameWins + "@bla@ wins!");
							String killed = "";
							if((c.KC - c.totalKills) ==  0) {
								killed = "no";
								if (c.achievements[24][0] == 0) {
									c.achievements[24][1] = 1;
									c.achievementsHandler();
								}
							} else if((c.KC - c.totalKills) == 1) {
								killed = "1 player";
							} else {
								killed = ""+ (c.KC - c.totalKills) +"";
							}
							c.sendMessage("You killed @red@" + killed + "@bla@ players, lived for @red@" + gameTimer * 2 + "@bla@ seconds and placed first!");
							c.reloadHGstuff();
							c.switchPerk = 0;
							winnerWins = c.totalGameWins;
							winnerName = c.playerName;
							winnerKit = c.myKit;
							c.getPA().removeAllWindows();
							c.getPA().resetAnimation();
							c.HGAttack = false;
							c.inCwGame = false;
							c.getCombat().resetPlayerAttack();
							c.getCombat().resetPrayers();
							c.poisonDamage = -1;
							c.randomMap = -1;
							c.specAmount = 10;
							c.currentExpEarned = 0;
							c.noSmuggle = 0;
							c.chestsOpened_2 = 0;
							for(int p = 0; p < c.PRAYER.length; p++) { // reset prayer glows 
								c.prayerActive[p] = false;
								c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0);		
							}
								c.setSidebarInterface(6, 1151); //reset to modern
							c.myKit = "";
							c.getItems().deleteAllItems();
							c.noSmuggleTwo = 5;
							c.taggedPlayer = "";
							if (c.difficultLevel == 3) {
								if (c.achievements[37][0] == 0) {
									c.achievements[37][1] = 1;
									c.achievementsHandler();
								}
							}
							c.prizeAmount = c.difficultLevel;
							c.difficultLevel(0);
							if (c.achievements[18][0] == 0) {
								c.achievements[18][1] = 1;
								c.achievementsHandler();
							}
							if (c.totalGameWins >= 50) {
								if (c.achievements[19][0] == 0) {
									c.achievements[19][1] = 1;
									c.achievementsHandler();
								}
							}
						} else if (currentPlayers_Misc.size() < 5 && lessThen5Left) {
							c.sendMessage("Less than @red@5 players@bla@ remain!");
						}
						
						if (timeRemaining == randomRefill && !chestReset) {
							c.sendMessage("Chests have been @red@reloaded@bla@ with new items!");
						} else if (timeRemaining == randomRefill_2 && !chestReset_2) {
							c.sendMessage("Chests have been @red@reloaded@bla@ with new items!");
						} else if (timeRemaining <= 10 && timeRemaining > 0 && !c.startDeathBattleMisc) {
							c.sendMessage("The death match starts in... " + timeRemaining);
						}
						
						if (System.currentTimeMillis() - c.lastGift > 20000) {
							if (c.perkGifted == 1 && gameTimer > 20 && Misc.random(55) == 0) {
								c.sendMessage("The Gamemakers have sent you a special gift beneath your feet!");
								c.lastGift = System.currentTimeMillis();
								if (deathTimer <= 0) {
									if (Misc.random(15) == 0) {
										c.sendMessage("You receive a grand item! Lucky!");
										Server.itemHandler.createGroundItem(c, c.grandChest(), c.absX, c.absY, 1, c.getId());
									} else {
										c.sendMessage("You receive a rare item.");
										Server.itemHandler.createGroundItem(c, c.rareItem(), c.absX, c.absY, 1, c.getId());
									}
								} else {
									c.sendMessage("You receive some food.");
									Server.itemHandler.createGroundItem(c, 391, c.absX, c.absY, 1, c.getId());
								}
							}
						}
						
						if (c.myKit.equalsIgnoreCase("SAINT") && c.playerEquipment[c.playerAmulet] == 1718 && c.playerLevel[3] < c.getPA().getLevelForXP(c.playerXP[3]) && Misc.random(50) == 0) {
							c.sendMessage("Saradomin is watching after you and has given you some health!");
							c.gfx0(436);
							c.playerLevel[3] += Misc.random(10) + 20;
							c.getPA().refreshSkill(3);
						}
						
						if ((c.KC - c.totalKills) == 3 && c.achievements[0][0] == 0) {
							c.achievements[0][1] = 1;
							c.achievementsHandler();
						} else if ((c.KC - c.totalKills) == 5 && c.achievements[1][0] == 0) {
							c.achievements[1][1] = 1;
							c.achievementsHandler();
						} else if ((c.KC - c.totalKills) > 5 && c.achievements[2][0] == 0) {
							c.achievements[2][1] = 1;
							c.achievementsHandler();
						}
						
						if (deathTimer < 10 && c.openTrade == true) {
							c.getTradeAndDuel().declineTrade();
						}
						
						if (c.chestRoomTimer >= 0) {
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
								c.hiddenRoom = false;
							}
						}
						
						/* end of player stuff */
						
					} else {
						currentPlayers_Misc.remove(currentPlayers_Misc.indexOf(player));
					}
				}
				
				/**
				** This is for SERVER BASED ingame stuff, do not add any player related methods, variables, or objects 
				**/
				if (c.startDeathBattleMisc == true && Misc.random(2) == 0) {
					//random fucking ice barrages
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
				if (currentPlayers_Misc.size() == 1 && !c.testmode) { //1
					endGame();
				} else if (currentPlayers_Misc.size() < 5 && lessThen5Left) {
					lessThen5Left = false;
				}
				if (timeRemaining == randomRefill && !chestReset) {
					resetChests();
					chestReset = true;
				} else if (timeRemaining == randomRefill_2 && !chestReset_2) {
					resetChests();
					chestReset_2 = true;
				} else if (timeRemaining <= 10 && timeRemaining >= 0 && !c.startDeathBattleMisc) {
					deathCounter++;
				}
				
				/* End of server stuff */
			} else {
				System.out.println("Error3, current players: " + currentPlayers_Misc.size());
				endGame();
				/*
				if (dcTimer <= 0) {
					if (currentPlayers_Misc.size() == 0) {
						System.out.println("Error3, current players: " + currentPlayers_Misc.size());
						endGame();
					} else {
						dcHandle = false;
						dcTimer = 10;
					}
				} else {
					dcHandle = true;
					dcTimer--;
				}*/
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
				if (c.randomMap == 3) {
					if (name.equals(c.playerName) && !c.inCwGame) {
						if (c.lastGame == gameId) {
							c.sendMessage("You can't join because you've already been in this game!");
							break;
						}
						if (currentPlayers_Misc.size() >= 14) {
							c.sendMessage("The game seems to already be full, try again when someone dies.");
							break;
						}
						int randomArray = (int)(Math.random() * startingSpawnMisc.length);
						c.getPA().movePlayer(startingSpawnMisc[randomArray][0], startingSpawnMisc[randomArray][1], 0);
						c.getItems().deleteAllItems();
						c.inCwWait = false;
						c.inCwGame = true;
						c.spectate = false;
						c.HGAttack = true;
						c.sendItems();
						c.lastGame = gameId;
						
						c.difficultLevel(c.difficultLevel);
						
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
							c.playerLevel[0] += 21;
							c.playerLevel[1] += 21;
							c.playerLevel[2] += 21;
							c.playerLevel[4] += 21;
							c.getPA().refreshSkill(0);
							c.getPA().refreshSkill(1);
							c.getPA().refreshSkill(2);
							c.getPA().refreshSkill(4);
						}
						
						if (c.perkSwitch == 1) {
							c.switchPerk = 1;
							c.sendMessage("You have one kit switch for this game.");
						} else {
							c.switchPerk = 0;
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
						currentPlayers_MiscWait.remove(currentPlayers_MiscWait.indexOf(player));
						currentPlayers_Misc.add(player);
						for (int player2 : currentPlayers_Misc) {
							if (Server.playerHandler.players[player2] != null) {
								Client c2 = (Client)Server.playerHandler.players[player2];
								if (c.playerName.equals(c2.playerName)) {
									c.sendMessage("You've forcefully joined into the game! Best of luck!");
								} else {
									c2.sendMessage("@blu@" + name + "@bla@ has forcefully joined the game using the Connector Perk!");
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void dcJoin(int id) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				if (currentPlayers_Misc.size() > 1) {
					if (id == c.playerId) {
						c.ipCount = 0;
						c.spectate = false;
						c.lastGame = gameId;
						c.inCwGame = true;
						c.inCwWait = false;
						c.HGAttack = true;
						c.randomMap = 3;
						c.myKit = c.rememberedKit;
						c.noSmuggle = 1;
						c.totalKills = c.KC;
						currentPlayers_Misc.add(j);
					}
				} else if (dcHandle) {
					if (id == c.playerId) {
						c.ipCount = 0;
						c.spectate = false;
						c.lastGame = gameId;
						c.inCwGame = true;
						c.inCwWait = false;
						c.HGAttack = true;
						c.randomMap = 1;
						c.myKit = c.rememberedKit;
						c.noSmuggle = 1;
						c.totalKills = c.KC;
						currentPlayers_Misc.add(j);
					}
				} else {
					c.readdedIn = false;
				}
			}
		}
	}
	
	public void startGame() {
		if (currentPlayers_MiscWait.size() < 4 && !c.testmode) {
			for (int player : currentPlayers_MiscWait) {
				if (Server.playerHandler.players[player] != null) {
					Client c = (Client)Server.playerHandler.players[player];
					c.sendMessage("Sorry but the match can't start till there's atleast 4 players.");
				} else {
					currentPlayers_MiscWait.remove(currentPlayers_MiscWait.indexOf(player));
				}
			}
			gameStartTimer = 30;	
			System.out.println("Need 4 players to start, adding 30 more seconds to timer.");
			return;
		}
		gameId = Misc.random(100000);
		randomRefill = (int)(Math.random() * 50) + 50; //Random refill of chests
		randomRefill_2 = (int)(Math.random() * 50) + 170;
		gameStartTimer = -1;
		dcTimer = 8;
		System.out.println("Starting game.");
		for (int player : currentPlayers_MiscWait) {
			if (Server.playerHandler.players[player] != null) {
				Client c = (Client)Server.playerHandler.players[player];
				c.ipCount = 0;
				c.spectate = false;
				c.lastGame = gameId;
				int randomArray;
				c.getItems().deleteAllItems();
				
				if (currentPlayers_MiscWait.size() >= 16) {
					if (c.achievements[26][0] == 0) {
						c.achievements[26][1] = 1;
						c.achievementsHandler();
					}
				}
				
				c.difficultLevel(c.difficultLevel);
				
				if (c.myKit.equalsIgnoreCase("FORSAKEN")) {
						randomArray = (int)(Math.random() * forsakenSpawnMisc.length);
						c.inCwWait = false;
						c.inCwGame = true;
						c.getPA().movePlayer(forsakenSpawnMisc[randomArray][0], forsakenSpawnMisc[randomArray][1], 0);
						c.sendMessage("You spawn away from the main area!");
				} else {
					for (int coord = 0; coord < currentPlayers_MiscWait.size(); coord = coord) {
						randomArray = (int)(Math.random() * startingSpawnMisc.length);
						if (startingSpawnMisc[randomArray][2] == 0) {
							startingSpawnMisc[randomArray][2] = 1;
							c.inCwWait = false;
							c.inCwGame = true;
							coord++;
							c.getPA().movePlayer(startingSpawnMisc[randomArray][0], startingSpawnMisc[randomArray][1], 0);
							break;
						}
					}
				}
				c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
				c.getPA().refreshSkill(3);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.getPA().refreshSkill(5);
				c.getItems().addItem(2347, 1);//hammer (all kits get this)
				c.getItems().addItem(4155, 1);//tracking stone (all kits get this)
				switch (c.myKit)
				{
					case "BERSERK":
						c.getItems().addItem(11128, 1);//1 berserk neck
						c.getItems().addItem(2440, 1);//1 super str pot
					break;
					
					case "ARCHER":
						c.getItems().addItem(861, 1);//1 magic shortbow
						c.getItems().addItem(2577, 1);//1 ranger boots
					break;
					
					case "ELEMENTALIST":
						c.getItems().addItem(4675, 1);//anicent staff
						c.getItems().addItem(2579, 1);//wizard boots
					break;
					
					case "BLIZZARD":
						c.getItems().addItem(1580, 1);//ice gloves
					break;
					
					case "FLASH":
						c.getItems().addItem(88, 1);//boots of lightness
					break;
					
					case "JUMPER":
						c.getItems().addItem(8408, 1);//Jumper Portal
						c.hasPlacedPortalDown = false;
					break;
					
					case "LOOTER":
						c.getItems().addItem(5554, 1);//lol
					break;
					
					case "ALCHEMIST":
						c.getItems().addItem(227, 5);//vials
						c.getItems().addItem(257, 1);//herbs
						c.getItems().addItem(259, 1);//herbs
						c.getItems().addItem(263, 1);//herbs
						c.getItems().addItem(267, 1);//herbs
						c.getItems().addItem(265, 1);//herbs
					break;
					
					case "SKILLER":
						c.getItems().addItem(3024, 1);//ppot
						c.getItems().addItem(6685, 2);//brews
						
						c.getItems().addItem(44, 60);//arrowhead
						c.getItems().addItem(52, 60);//shafts
						c.getItems().addItem(314, 60);//feather
					break;
					
					case "SICKNESS":
						c.sicknessPoisons = Misc.random(5) + 5;
					break;
					
					case "TAINTED":
						c.getItems().addItem(1724, 1);//holy sym
						c.getItems().addItem(6950, 1);//orb
					break;
					
					case "NECROMORPHER":
						c.getItems().addItem(4837, 1);//book
						c.playerMagicBook = 1;
						c.setSidebarInterface(6, 12855);
						c.sendMessage("An ancient wisdomin fills your mind.");
						c.autocastId = -1;
						c.getPA().resetAutocast();
					break;
					
					case "SAINT":
						c.getItems().addItem(1718, 1);//holy sym
					break;
					
					case "KNIGHT":
						c.getItems().addItem(35, 1);//excal
						c.getItems().addItem(748, 1);//holy force
					break;
					
					case "TAGGED":
						c.taggedUses = 3;
						c.getItems().addItem(4002, 1);//tagger device
						c.sendMessage("You've been given 3 Tagged uses for this game.");
					break;
					
					case "WRETCHED":
						c.wretchedUses = 6;
						c.getItems().addItem(4707, 1);
						c.sendMessage("The book seems like it can only work 6 times for this game.");
					break;
					
					default:
						c.getItems().addItem(391, 2);//2 manta
					break;
				}
				
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
					c.playerLevel[0] += 21;
					c.playerLevel[1] += 21;
					c.playerLevel[2] += 21;
					c.playerLevel[4] += 21;
					c.getPA().refreshSkill(0);
					c.getPA().refreshSkill(1);
					c.getPA().refreshSkill(2);
					c.getPA().refreshSkill(4);
				}
				
				if (c.perkSwitch == 1) {
					c.switchPerk = 1;
					c.sendMessage("You have one kit switch for this game.");
				} else {
					c.switchPerk = 0;
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
				currentPlayers_Misc.add(player);
			} else {
				currentPlayers_MiscWait.remove(currentPlayers_MiscWait.indexOf(player));
			}	
		}
		timeRemaining = GAME_TIMER;
		gameOver = false;
		countDownTimer = 11;
		gameStartTimer = GAME_START_TIMER;
		//ObjectManager.pickChests(55 + currentPlayers_MiscWait.size(), 1);
		currentPlayers_MiscWait.clear();
	}
	
	public void spawnChests() {
		for (int player : currentPlayers_Misc) {
			if (Server.playerHandler.players[player] != null) {
				Client c = (Client)Server.playerHandler.players[player];
				ObjectManager.randomChests(c);
			} else {
				currentPlayers_Misc.remove(currentPlayers_Misc.indexOf(player));
			}
		}
	}
	
	public void countDown() {
		for (int player : currentPlayers_Misc) {
			if (Server.playerHandler.players[player] != null) {
				Client c = (Client)Server.playerHandler.players[player];
				if (countDownTimer > 0) {
					c.sendMessage("Match starts in... " + countDownTimer);
				} else {
					c.forcedChat("Fight!");
					c.forcedChatUpdateRequired = true;
					c.HGAttack = true;
					c.isSkulled = true;
					c.skullTimer = 2500;
					c.headIconPk = 0;
					c.getPA().requestUpdates();
					if (c.achievements[13][0] == 0) {
						c.achievements[13][1] = 1;
						c.achievementsHandler();
					}
					//Enable PVP under process in client
				}
			} else {
				currentPlayers_Misc.remove(currentPlayers_Misc.indexOf(player));
			}	
		}
	}
	
	public void endGame() {
		System.out.println("Ending game.");
		
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				if (currentPlayers_Misc.size() == 1) { //1
					if (( gameTimer * 2 / 60) == 0) {
						c.sendMessage("Congratulations to @blu@" + winnerName + "@bla@ (" + winnerDiff + ") as a (" + winnerKit + "), who just won at Miscellania.");
						c.sendMessage("They won in " + ( gameTimer * 2 % 60 ) + " seconds with a total of " + winnerWins + " wins!");
					} else {
						c.sendMessage("Congratulations to @blu@" + winnerName + "@bla@ (" + winnerDiff + ") as a (" + winnerKit + "), who just won at Miscellania.");
						c.sendMessage("They won in " + ( gameTimer * 2 / 60 ) + " minutes and " + ( gameTimer * 2 % 60 ) + " seconds with a total of " + winnerWins + " wins!");
					}
					Server.npcHandler.npcAction(619, "Congratulations to " + winnerName + ", who just won at Miscellania!", 0);
				} else {
					c.sendMessage("There were no winners in the previous Hunger Games at Miscellania.");
				}
			}
		}
		
		for (int coord = 0; coord < startingSpawnMisc.length; coord++) { //Resets spawns
				if (startingSpawnMisc[coord][2] == 1) {
					startingSpawnMisc[coord][2] = 0;
				}
		}
		Server.npcHandler.removeGameNpcs(3);
		npcsSpawned = 0;
		gameStartTimer = GAME_START_TIMER;
		timeRemaining = -1;
		lessThen5Left = true;
		resetChests();
		doubleExp = false;
		gameOver = true;
		gameTimer = 0;
		deathCounter = 0;
		deathTimer = -1;
		c.startDeathBattleMisc = false;
		chestReset = false;
		chestReset_2 = false;
		currentPlayers_Misc.clear();
		c.totalGamesPlayed++;
	}
	
	public void resetChests() {
		for (int i = 0; i < c.chestInfoMisc.length; i++) {
			if (c.chestInfoMisc[i][2] == 1) 
				c.chestInfoMisc[i][2] = 0;
		}
	}
}