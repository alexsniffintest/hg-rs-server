package server.model.players.packets;


import server.Config;
import server.Server;
import server.model.items.GameItem;
import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;
import server.world.ObjectManager;


/**
 * Clicking most 
 
 **/
public class ClickingButtons implements PacketType {

	private Client c;
	int[] PvpItems = { 14876, 14877, 14878, 14879, 14880, 14881, 14882, 14883, 14884, 14885, 14886, 14888, 14889, 14890, 14891, 14892 };
	int[] PvpPrices = { 50000000, 10000000, 500000, 350000, 8000000, 50000, 80000, 840000, 50000, 25000, 800000, 50000000, 24000, 87000, 2000000, 284000 };
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(c.getInStream().buffer, 0, packetSize);
		if (c.isDead)
			return;
			
		if (c.playerRights >= 3) {
			c.sendMessage("Action Button " + actionButtonId + ".");
			Misc.println(c.playerName + " - actionbutton: " + actionButtonId);
		}
			
		switch (actionButtonId){
		
		case 62223:
			c.setSidebarInterface(2, 16400);
		break;
		
		case 64111:
			c.setSidebarInterface(2, 16000);
		break;
		
		case 82172:
			if (c.inCwWait) {
				c.sendMessage("Your difficulty is now set to @blu@Insane@bla@!"); 
				c.sendMessage("You'll gain 30% more experience but start as a level 52.");
				c.difficultLevel = 3;
			} else if (c.inCwGame) {
				c.sendMessage("You can't change your difficulty once the match started!");
			} else {
				c.sendMessage("You can only use this in the waiting lobby!");
			}
		break;
		
		case 82175:
			if (c.inCwWait) {
				c.sendMessage("Your difficulty is now set to @blu@Extreme@bla@!"); 
				c.sendMessage("You'll gain 20% more experience but start as a level 77.");
				c.difficultLevel = 2;
			} else if (c.inCwGame) {
				c.sendMessage("You can't change your difficulty once the match started!");
			} else {
				c.sendMessage("You can only use this in the waiting lobby!");
			}
		break;
		
		case 82178:
			if (c.inCwWait) {
				c.sendMessage("Your difficulty is now set to @blu@Tough@bla@!"); 
				c.sendMessage("You'll gain 10% more experience but start as a level 102.");
				c.difficultLevel = 1;
			} else if (c.inCwGame) {
				c.sendMessage("You can't change your difficulty once the match started!");
			} else {
				c.sendMessage("You can only use this in the waiting lobby!");
			}
		break;
		
		case 82181:
			if (c.inCwWait) {
				c.sendMessage("Your difficulty is now set to @blu@Easy@bla@!"); 
				c.sendMessage("You'll gain no extra experience but start as a level 126.");
				c.difficultLevel = 0;
			} else if (c.inCwGame) {
				c.sendMessage("You can't change your difficulty once the match started!");
			} else {
				c.sendMessage("You can only use this in the waiting lobby!");
			}
		break;
		
		case 82159:
			if (c.inCwWait) {
				c.spectate = true;
				c.spectateEx = 1;
				c.getPA().requestUpdates();
				c.getItems().deleteAllItems();
				if (c.randomMap == 0 && c.spectate) {
					if (!c.startDeathBattle) {
						if (c.randomSpot < (c.spectateVarrock.length - 1)) {
							c.randomSpot++;
						} else {
							c.randomSpot = 0;
						}
					} else {
						c.randomSpot = 11;
					}
					c.getPA().movePlayer(c.spectateVarrock[c.randomSpot][0], c.spectateVarrock[c.randomSpot][1], 0);
				} else if (c.randomMap == 1 && c.spectate) {
					if (!c.startDeathBattleFal) {
						if (c.randomSpot < (c.spectateFalador.length - 1)) {
							c.randomSpot++;
						} else {
							c.randomSpot = 0;
						}
					} else {
						c.randomSpot = 7;
					}
					c.getPA().movePlayer(c.spectateFalador[c.randomSpot][0], c.spectateFalador[c.randomSpot][1], 0);
				} else if (c.randomMap == 2 && c.spectate) {
					if (!c.startDeathBattleCan) {
						if (c.randomSpot < (c.spectateCanfis.length - 1)) {
							c.randomSpot++;
						} else {
							c.randomSpot = 0;
						}
					} else {
						c.randomSpot = 10;
					}
					c.getPA().movePlayer(c.spectateCanfis[c.randomSpot][0], c.spectateCanfis[c.randomSpot][1], 0);
				} else if (c.randomMap == 3 && c.spectate) {
					if (!c.startDeathBattleMisc) {
						if (c.randomSpot < (c.spectateMisc.length - 1)) {
							c.randomSpot++;
						} else {
							c.randomSpot = 0;
						}
					} else {
						c.randomSpot = 10;
					}
					c.getPA().movePlayer(c.spectateMisc[c.randomSpot][0], c.spectateMisc[c.randomSpot][1], 0);
				}
			} else {
				c.sendMessage("You can only use this inside the waiting lobby!");
			}
		break;
		
		case 82162:
			if (c.perkConnect == 0) {
				c.sendMessage("You need the Connector perk to use this function.");
				return;
			}
			if (c.inCwWait) {
				if (c.randomMap == 0) {
					if (Server.HungerGames.gameTimer < 15 && !Server.HungerGames.gameOver) {
						c.sendMessage("The game just started, please wait a few seconds!");
						return;
					}
					if (!Server.HungerGames.gameOver && !c.startDeathBattle) {
						Server.HungerGames.forceJoin(c.playerName);
					} else {
						c.sendMessage("There's currently no game or the Death Match has started.");
					}
				} else if (c.randomMap == 1) {
					if (Server.HungerGamesFal.gameTimer < 15 && !Server.HungerGamesFal.gameOver) {
						c.sendMessage("The game just started, please wait a few seconds!");
						return;
					}
					if (!Server.HungerGamesFal.gameOver && !c.startDeathBattleFal) {
						Server.HungerGamesFal.forceJoin(c.playerName);
					} else {
						c.sendMessage("There's currently no game or the Death Match has started.");
					}
				} else if (c.randomMap == 2) {
					if (Server.HungerGamesCan.gameTimer < 15 && !Server.HungerGamesCan.gameOver) {
						c.sendMessage("The game just started, please wait a few seconds!");
						return;
					}
					if (!Server.HungerGamesCan.gameOver && !c.startDeathBattleCan) {
						Server.HungerGamesCan.forceJoin(c.playerName);
					} else {
						c.sendMessage("There's currently no game or the Death Match has started.");
					}
				} else if (c.randomMap == 3) {
					if (Server.HungerGamesMisc.gameTimer < 15 && !Server.HungerGamesMisc.gameOver) {
						c.sendMessage("The game just started, please wait a few seconds!");
						return;
					}
					if (!Server.HungerGamesMisc.gameOver && !c.startDeathBattleMisc) {
						Server.HungerGamesMisc.forceJoin(c.playerName);
					} else {
						c.sendMessage("There's currently no game or the Death Match has started.");
					}
				}
			}
		break;
		
		case 82169:
			if (c.randomMap == 0) {
				if (Server.HungerGames.gameStartTimer < 4) {
					return;
				}
			} else if (c.randomMap == 1) {
				if (Server.HungerGamesFal.gameStartTimer < 4) {
					return;
				}
			} else if (c.randomMap == 2) {
				if (Server.HungerGamesCan.gameStartTimer < 4) {
					return;
				}
			} else if (c.randomMap == 3) {
				if (Server.HungerGamesMisc.gameStartTimer < 4) {
					return;
				}
			}
			if (c.inCwWait && c.spectate) {
				if (c.randomMap == 0) { 
					c.getPA().movePlayer(3040, 2910, 0);
				} else if (c.randomMap == 1) {
					c.getPA().movePlayer(3118, 2888, 0);
				} else if (c.randomMap == 2) {
					c.getPA().movePlayer(3118, 2980, 0);
				} else if (c.randomMap == 3) {
					c.getPA().movePlayer(2595, 3808, 0);
				}
				c.getItems().deleteAllItems();
				c.sendMessage("You return to the waiting lobby.");
				c.spectate = false;
				c.spectateEx = 0;
			} else {
				c.sendMessage("You're not in spectate mode!");
			}
		break;
		
		case 23132: //unmorph
            c.isMorphed = false;
			c.setSidebarInterface(1, 3917);
			c.setSidebarInterface(2, 16000);
			c.setSidebarInterface(3, 3213);
			c.setSidebarInterface(4, 1644);
			c.setSidebarInterface(5, 5608);
			if(c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151);
			} else if (c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 12855);
			} else if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 29999);
			}
			c.setSidebarInterface(7, 18128);
			c.setSidebarInterface(8, 5065);
			c.setSidebarInterface(9, 5715); 
			c.setSidebarInterface(10, 2449);
			c.setSidebarInterface(11, 904);
			c.setSidebarInterface(12, 147);
			c.setSidebarInterface(13, 962);
			c.setSidebarInterface(0, 2423);
			if (c.playerEquipment[c.playerRing] == 7927) {
			c.getItems().deleteEquipment(c.playerEquipment[c.playerRing], c.playerRing);
			c.getItems().addItem(7927,1);
			}
			c.isNpc = false;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
		break;
		
		/** KITS **/
		
		case 63076:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.myKit.equals("Default")) {
					c.sendMessage("You're already using this kit.");
				} else {
					c.myKit = "Default";
					c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
					c.getPA().requestUpdates();
					c.rememberedKit = c.myKit;
					if (c.switchPerk == 1 && !c.inCwWait) {
						c.sendSwitchItems();
						c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
						c.switchPerk = 0;
					}
				}
			} else {
				c.sendMessage("To use this kit, click it while in a waiting lobby.");
			}
		break;
		
		case 63077:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[0].equals("Berserk")) {
					if (c.myKit.equals("BERSERK")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "BERSERK";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				} else {
					c.sendMessage("You haven't unlocked this kit yet!");
				}
			} else {
				c.sendMessage("To use this kit, click it while in a waiting lobby.");
			}
		break;
		
		case 63078:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[1].equals("Archer")) {
					if (c.myKit.equals("ARCHER")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "ARCHER";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				} else {
					c.sendMessage("You haven't unlocked this kit yet!");
				}
			} else {
				c.sendMessage("To use this kit, click it while in a waiting lobby.");
			}
		break;
		
		case 63079:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[2].equals("Elementalist")) {
					if (c.myKit.equals("ELEMENTALIST")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "ELEMENTALIST";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				} else {
					c.sendMessage("You haven't unlocked this kit yet!");
				}
			} else {
				c.sendMessage("To use this kit, click it while in a waiting lobby.");
			}
		break;
		
		case 63080:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[20].equals("Protected")) {
					if (c.myKit.equals("PROTECTED")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "PROTECTED";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				} else {
					c.sendMessage("You haven't unlocked this kit yet!");
				}
			} else {
				c.sendMessage("To use this kit, click it while in a waiting lobby.");
			}
		break;
		
		case 63081:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inPkingZone) {
				c.sendMessage("You can't use this kit here.");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1) {
				if (c.kits[8].equals("Alchemist")) {
					if (c.myKit.equals("ALCHEMIST")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "ALCHEMIST";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				} else {
					c.sendMessage("You haven't unlocked this kit yet!");
				}
			} else {
				if (c.kits[8] == null) {
					c.getDH().sendDialogues(18, -1);
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63082:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inPkingZone) {
				c.sendMessage("You can't use this kit here.");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1) {
				if (c.kits[9].equals("Forsaken")) {
					if (c.myKit.equals("FORSAKEN")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "FORSAKEN";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				} else {
					c.sendMessage("You haven't unlocked this kit yet!");
				}
			} else {
				if (c.kits[9] == null) {
					c.getDH().sendDialogues(19, -1);
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63083:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[12].equals("Sickness")) {
					if (c.myKit.equals("SICKNESS")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "SICKNESS";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				} else {
					c.sendMessage("You haven't unlocked this kit yet!");
				}
			} else {
				if (c.kits[12] == null) {
					c.getDH().sendDialogues(25, -1);
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63084:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[15].equals("Saint")) {
					if (c.myKit.equals("SAINT")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "SAINT";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				} else {
					c.sendMessage("You haven't unlocked this kit yet!");
				}
			} else {
				if (c.kits[15] == null) {
					c.getDH().sendDialogues(65, -1);
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63085://tagged
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inPkingZone) {
				c.sendMessage("You can't use this kit here.");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[18].equals("Tagged")) {
					if (c.myKit.equals("TAGGED")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "TAGGED";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[18] == null) {
					c.sendMessage("Unlock this kit by completing the Specialist Achievement.");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63086:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inPkingZone) {
				c.sendMessage("You can't use this kit here.");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[21].equals("Mercenary")) {
					if (c.myKit.equals("MERCENARY")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "MERCENARY";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[21] == null) {
					c.getDH().sendDialogues(94, -1);
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63087:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[3].equals("Perished")) {
					if (c.myKit.equals("PERISHED")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "PERISHED";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[3] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#perished", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#perished . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63088:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[4].equals("Venom")) {
					if (c.myKit.equals("VEMON")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "VENOM";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[4] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#venom", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#venom . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63089:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[5].equals("Blizzard")) {
					if (c.myKit.equals("BLIZZARD")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "BLIZZARD";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[5] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#blizzard", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#blizzard . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63090:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inPkingZone) {
			c.sendMessage ("You cant use this kit here.");
			return;
			}
			if (c.inCwWait || c.switchPerk == 1 || !c.inWild()) {
				if (c.kits[6].equals("Flash")) {
					if (c.myKit.equals("FLASH")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "FLASH";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[6] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#flash", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#flash . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63091:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inPkingZone) {
				c.sendMessage("You can't use this kit here.");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1) {
				if (c.kits[7].equals("Looter")) {
					if (c.myKit.equals("LOOTER")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "LOOTER";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[7] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#looter", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#looter . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63092:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inPkingZone) {
				c.sendMessage("You can't use this kit here.");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1) {
				if (c.kits[10].equals("Jumper")) {
					if (c.myKit.equals("JUMPER")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "JUMPER";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[10] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#jumper", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#jumper . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63093:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inPkingZone) {
				c.sendMessage("You can't use this kit here.");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1) {
				if (c.kits[11].equals("Skiller")) {
					if (c.myKit.equals("SKILLER")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "SKILLER";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[11] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#skiller", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#skiller . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63094:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[13].equals("Chaos")) {
					if (c.myKit.equals("CHAOS")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "CHAOS";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[13] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#chaos", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#chaos . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63095:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[14].equals("Tainted")) {
					if (c.myKit.equals("TAINTED")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "TAINTED";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[14] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#tainted", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#tainted . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63096:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[16].equals("Necromorpher")) {
					if (c.myKit.equals("NECROMORPHER")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "NECROMORPHER";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[16] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#necromorpher", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#necromorpher . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63097:
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inPkingZone) {
				c.sendMessage("You can't use this kit here.");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[17].equals("Knight")) {
					if (c.myKit.equals("KNIGHT")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "KNIGHT";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[17] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#knight", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#knight . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63098://wretched
			if (c.inCwGame && c.switchPerk == 0) {
				c.sendMessage("You don't have any Switch perk uses to do this!");
				return;
			}
			if (c.inPkingZone) {
				c.sendMessage("You can't use this kit here.");
				return;
			}
			if (c.inCwWait || c.switchPerk == 1 || c.inPkingZone && !c.inWild()) {
				if (c.kits[19].equals("Wretched")) {
					if (c.myKit.equals("WRETCHED")) {
						c.sendMessage("You're already using this kit.");
					} else {
						c.myKit = "WRETCHED";
						c.sendMessage("Your kit is now @blu@" + c.myKit + ".");
						c.getPA().requestUpdates();
						c.rememberedKit = c.myKit;
						if (c.switchPerk == 1 && !c.inCwWait) {
							c.sendSwitchItems();
							c.sendMessage("You've used your Switch for the game. You'll no longer be able too.");
							c.switchPerk = 0;
						}
					}
				}
			} else {
				if (c.kits[19] == null) {
					c.getPA().sendFrame126("www.hg-rs.com/home/shop.html#wretched", 12000);
					c.sendMessage("Opening www.hg-rs.com/home/shop.html#wretched . . .");
				} else {
					c.sendMessage("To use this kit, click it while in a waiting lobby.");
				}
			}
		break;
		
		case 63099://div
			c.sendMessage("Coming soon!");
		break;
		/** END **/
		
		/** Achievements **/
		
		case 64216:
			c.getDH().sendStatement("Get 3 kills in one match.");
		break;
		
		case 64217:
			c.getDH().sendStatement("Get 5 kills in one match.");
		break;
		
		case 64218:
			c.getDH().sendStatement("Get over 5 kills in one match.");
		break;
		
		case 64219:
			c.getDH().sendStatement("Open 500 chests in total. (" + c.chestsOpened + " / 500)");
		break;
		
		case 64220:
			c.getDH().sendStatement("Open 1000 chests in total. (" + c.chestsOpened + " / 1000)");
		break;
		
		case 64221:
			c.getDH().sendStatement("Open 40 chests in one match.");
		break;
		
		case 64222:
			c.getDH().sendStatement("Get killed by an explosive chest.");
		break;
		
		case 64223:
			c.getDH().sendStatement("Assemble a godsword in-game.");
		break;
		
		case 64224:
			c.getDH().sendStatement("Vote once.");
		break;
		
		case 64225:
			c.getDH().sendStatement("Vote ten time. (" + c.voteTimes + " / 10)");
		break;
		
		case 64226:
			c.getDH().sendStatement("Win 1 million experience from one bet at the gambler.");
		break;
		
		case 64227:
			c.getDH().sendStatement("Unlock any perk.");
		break;
		
		case 64228:
			c.getDH().sendStatement("Unlock atleast 10 achievements and 10 perks.");
		break;
		
		case 64229:
			c.getDH().sendStatement("Join your first game.");
		break;
		
		case 64230:
			c.getDH().sendStatement("Unlock one of the free experience kits.");
		break;
		
		case 64231:
			c.getDH().sendStatement("Go to Edgeville.");
		break;
		
		case 64232:
			c.getDH().sendStatement("Find the hidden treasure room at Miscellania.");
		break;
		
		case 64233:
			c.getDH().sendStatement("Get a makeover.");
		break;
		
		case 64234:
			c.getDH().sendStatement("Win a game.");
		break;
		
		case 64235:
			c.getDH().sendStatement("Win atleast 50 games.");
		break;
		
		case 64236:
			c.getDH().sendStatement("Find the hidden item in the games.");
		break;
		
		case 64237:
			c.getDH().sendStatement("Wear the full desert robes set at M/S.");
		break;
		
		case 64238:
			c.getDH().sendStatement("Kill a Mod or Admin.");
		break;
		
		case 64239:
			c.getDH().sendStatement("Force join a game with the Connector perk.");
		break;
		
		case 64240:
			c.getDH().sendStatement("Win a game without killing anyone.");
		break;
		
		case 64241:
			c.getDH().sendStatement("Talk to Merlin and convert your experiance to gold.");
		break;
		
		case 64242:
			c.getDH().sendStatement("Start in a game with a full lobby.");
		break;
		
		case 64243:
			c.getDH().sendStatement("Have atleast 100 kills or deaths while resetting your KDR.");
		break;
		
		case 64244:
			c.getDH().sendStatement("Use the ::setcc to set your own clan chat.");
		break;
		
		case 64245:
			c.getDH().sendStatement("Kill a monster inside the game area.");
		break;
		
		case 64246:
			c.getDH().sendStatement("Kill 10 Abyssal Demons. (" + c.abyssKc + " / 10)");
		break;
		
		case 64247:
			c.getDH().sendStatement("Kill 30 Green Dragons. (" + c.dragonKc + " / 30)");
		break;
		
		case 64248:
			c.getDH().sendStatement("Kill the Varrock boss.");
		break;
		
		case 64249:
			c.getDH().sendStatement("Kill the Falador boss.");
		break;
		
		case 64250:
			c.getDH().sendStatement("Kill the M/S boss.");
		break;
		
		case 64251:
			c.getDH().sendStatement("Kill the Miscellania boss.");
		break;
		
		case 64252:
			c.getDH().sendStatement("Kill all four bosses.");
		break;
		
		case 64253:
			c.getDH().sendStatement("Win a match on the Insane difficulty.");
		break;
		
		case 97169:
		c.outStream.createFrame(27);
		c.attackSkill = true;
		break;
		
		case 97177:
				c.outStream.createFrame(27);
		c.strengthSkill = true;
		break;
		case 97173:
		c.outStream.createFrame(27);
		c.defenceSkill = true;
		break;
		case 97185:
		c.outStream.createFrame(27);
		c.rangeSkill = true;
		break;
		case 97189:
		c.outStream.createFrame(27);
		c.prayerSkill = true;
		break;
		case 97193:
		c.outStream.createFrame(27);
		c.mageSkill = true;
		break;
		case 97181:
		c.outStream.createFrame(27);
		c.healthSkill = true;
		break;
		
			//crafting + fletching interface:
			case 150:
				if (c.autoRet == 0)
					c.autoRet = 1;
				else
					c.autoRet = 0;
			break;
			

		//1st option
		case 9190:
			if (c.convert == true) {
				if (c.totalHungerGameExp >= 5000) {
					if (c.getItems().freeSlots() >= 1) {
						c.totalHungerGameExp -= 5000;
						c.getItems().addItem(995, 5000);
						Server.npcHandler.npcAction(213, "", 0);
						if (c.achievements[25][0] == 0) {
							c.achievements[25][1] = 1;
							c.achievementsHandler();
						}
					} else {
						c.sendMessage("Sorry but you need atleast one inventory slot.");
					}
				} else {
					c.sendMessage("Sorry but it appears you don't have the required experience.");
				}
				c.convert = false;
				c.reloadHGstuff();
				c.getPA().removeAllWindows();
			} else if (c.shopOne) {
				c.inShop = true;
				c.getShops().openShop(3);
				c.shopOne = false;
			}
		break;
		//2nd option
		case 9191:
			if (c.convert == true) {
				if (c.totalHungerGameExp >= 15000) {
					if (c.getItems().freeSlots() >= 1) {
						c.totalHungerGameExp -= 15000;
						c.getItems().addItem(995, 17000);
						Server.npcHandler.npcAction(213, "", 0);
						if (c.achievements[25][0] == 0) {
							c.achievements[25][1] = 1;
							c.achievementsHandler();
						}
					} else {
						c.sendMessage("Sorry but you need atleast one inventory slot.");
					}
				} else {
					c.sendMessage("Sorry but it appears you don't have the required experience.");
				}
				c.convert = false;
				c.reloadHGstuff();
				c.getPA().removeAllWindows();
			} else if (c.shopOne) {
				c.getShops().openShop(4);
				c.shopOne = false;
			}
		break;
		//3rd option
		case 9192:
			if (c.convert == true) {
				if (c.totalHungerGameExp >= 50000) {
					if (c.getItems().freeSlots() >= 1) {
						c.totalHungerGameExp -= 50000;
						c.getItems().addItem(995, 60000);
						Server.npcHandler.npcAction(213, "", 0);
						if (c.achievements[25][0] == 0) {
							c.achievements[25][1] = 1;
							c.achievementsHandler();
						}
					} else {
						c.sendMessage("Sorry but you need atleast one inventory slot.");
					}
				} else {
					c.sendMessage("Sorry but it appears you don't have the required experience.");
				}
				c.convert = false;
				c.reloadHGstuff();
				c.getPA().removeAllWindows();
			} else if (c.shopOne) {
				c.inShop = true;
				c.getShops().openShop(5);
				c.shopOne = false;
			}
		break;
		//4th option
		case 9193:
			if (c.convert == true) {
				if (c.totalHungerGameExp >= 100000) {
					if (c.getItems().freeSlots() >= 1) {
						c.totalHungerGameExp -= 100000;
						c.getItems().addItem(995, 125000);
						Server.npcHandler.npcAction(213, "", 0);
						if (c.achievements[25][0] == 0) {
							c.achievements[25][1] = 1;
							c.achievementsHandler();
						}
					} else {
						c.sendMessage("Sorry but you need atleast one inventory slot.");
					}
				} else {
					c.sendMessage("Sorry but it appears you don't have the required experience.");
				}
				c.convert = false;
				c.reloadHGstuff();
				c.getPA().removeAllWindows();
			} else if (c.shopOne) {
				c.inShop = true;
				c.getShops().openShop(6);
				c.shopOne = false;
			}
		break;
		//5th option
		case 9194:
			if (c.convert == true) {
				c.convert = false;
				c.getPA().removeAllWindows();
			} else if (c.shopOne) {
				c.shopOne = false;
				c.getPA().removeAllWindows();
			}
		break;
				
		case 55096:
			c.getPA().removeAllWindows();
                        c.droppedItem = -1;
		break;

			case 71074:
				if (c.clanId >= 0) {
					/*if (Server.clanChat.clans[c.clanId].owner.equalsIgnoreCase(c.playerName)) {
						Server.clanChat.sendLootShareMessage(c.clanId, "Lootshare has been toggled to " + (!Server.clanChat.clans[c.clanId].lootshare ? "on" : "off") + " by the clan leader.");
						Server.clanChat.clans[c.clanId].lootshare = !Server.clanChat.clans[c.clanId].lootshare;
					} else
						c.sendMessage("Only the owner of the clan has the power to do that.");*/
					c.sendMessage("This feature has been disabled.");
				}
			break;
			case 34185: case 34184: case 34183: case 34182: case 34189: case 34188: case 34187: case 34186: case 34193: case 34192: case 34191: case 34190:
				if (c.craftingLeather)
					c.getCrafting().handleCraftingClick(actionButtonId);
				if (c.getFletching().fletching)
					c.getFletching().handleFletchingClick(actionButtonId);
			break;

			case 59100:
			if(!c.isSkulled) {
				c.getItems().resetKeepItems();
				c.getItems().keepItem(0, false);
				c.getItems().keepItem(1, false);
				c.getItems().keepItem(2, false);
				c.getItems().keepItem(3, false);
				c.sendMessage("You can keep three items and a fourth if you use the protect item prayer.");
			} else {
				c.getItems().resetKeepItems();
				c.getItems().keepItem(0, false);
				c.sendMessage("You are skulled and will only keep one item if you use the protect item prayer.");
			}
			c.getItems().sendItemsKept();
			c.getPA().showInterface(6960);
			c.getItems().resetKeepItems();
			break;

			case 59097:
			c.getPA().showInterface(15106);
			break;
			case 15147:
				if (c.smeltInterface) {
					c.smeltType = 2349;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;

			case 15151:
				if (c.smeltInterface) {
					c.smeltType = 2351;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;


			case 15159:
				if (c.smeltInterface) {
					c.smeltType = 2353;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;


			case 29017:
				if (c.smeltInterface) {
					c.smeltType = 2359;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;

			case 29022:
				if (c.smeltInterface) {
					c.smeltType = 2361;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;

			case 29026:
				if (c.smeltInterface) {
					c.smeltType = 2363;
					c.smeltAmount = 1;
					c.getSmithing().startSmelting(c.smeltType);
				}
			break;

			case 30000:
			c.getPA().movePlayer(3087, 3499, 0);
			break;
		case 3071:
			if (c.interfaceEffect == 21) {
			} else if (c.interfaceEffect == 22) {
				c.cmdBook1();
			} else if (c.interfaceEffect == 23) {
				c.cmdBook2();
			} else if (c.interfaceEffect == 24) {
				c.cmdBook3();
			} else if (c.interfaceEffect == 25) {
				c.cmdBook4();
			}
		break;
		case 3073:
			if (c.interfaceEffect == 21) {
				c.cmdBook2();
			} else if (c.interfaceEffect == 22) {
				c.cmdBook3();
			} else if (c.interfaceEffect == 23) {
				c.cmdBook4();
			} else if (c.interfaceEffect == 24) {
				c.cmdBook5();
			}
		break;
		case 39178:
			c.getPA().closeAllWindows();
		break;
			case 58253:
			//c.getPA().showInterface(15106);
			c.getItems().writeBonus();
			break;

			/** Skillcapes **/
			case 154:
				c.sendMessage("Skillcape emotes are disabled.");
			break;

			case 59004:
			c.getPA().removeAllWindows();
			break;

			case 70212:
				c.ClanChatSpam = 0;
				if (c.clanId > -1)
					Server.clanChat.leaveClan(c.playerId, c.clanId);
					
				else
					c.sendMessage("You are not in a clan.");
			break;
			case 62137:
				if (c.clanId >= 0) {
					c.sendMessage("You are already in a clan.");
					break;
				}
				if (c.getOutStream() != null) {
					c.getOutStream().createFrame(187);
					c.flushOutStream();
				}
			break;

			case 9167:
				if( c.dialogueAction == 222) {
					c.getDH().sendDialogues(911, c.talkingNpc);
					c.dialogueAction = -1;
				} else if (System.currentTimeMillis() - c.lastGambleClick > 1500) {
					c.lastGambleClick = System.currentTimeMillis();
					if (c.dialogueAction == 5) {
						c.getDH().sendDialogues(41, 372);
						c.dialogueAction = 0;
						return;
					}
					//Did you receive the correct boolean?
					if (c.pickingNumber) {
						//Is player null? 
						if (c.playerName.equals(null)) {
							c.sendMessage("error_9 - Tell Peeta this");
							return;
						}
						//Is somehow the first player picking a second number?
						if (c.playerName.equals(c.gamblerOne)) {
							c.pickingNumber = false;
							c.getDH().sendDialogues(0, 0);
							c.sendMessage("error_5 - Tell Peeta this");
							return;
						}
						//Did someone else set a bet before you?
						if (c.gambleOn && c.firstGamble) {
							c.getDH().sendDialogues(53, 372);
							c.gamblePot -= c.myBet;
							c.totalHungerGameExp += c.myBet;
							c.pickingNumber = false;
							c.firstGamble = false;
							c.myBet = 0;
							c.reloadHGstuff();
							c.sendMessage("You're bet has been returned because someone set a bet before you.");
							return;
						}
						//Is this number already picked?
						if (c.gambleNum1) {
							c.getDH().sendDialogues(58, 372);
							return;
						}
						//Are you the first gambler?
						if (c.firstGamble) {
							c.gamblerOne = c.playerName;
							c.gambleNum1 = true;
							c.gambleOn = true;
							c.pickingNumber = false;
							c.myNumber = 1;
							c.getDH().sendDialogues(56, 372);
							return;
						//If not then..
						} else if (!c.firstGamble) {
							//Double check if there's already a first gambler
							if (c.gambleOn) {
								c.gambleNum1 = true;
								c.pickingNumber = false;
								c.myNumber = 1;
								c.startRollin = true;
								c.gambleNumber();
								c.getDH().sendDialogues(57, 372);
								return;
							} else if (!c.gambleOn) {
								c.gamblePot = 0;
								c.totalHungerGameExp += c.myBet;
								c.pickingNumber = false;
								c.myBet = 0;
								c.firstGamble = false;
								c.reloadHGstuff();
								c.sendMessage("It seems the first person logged out or dced");
								return;
							}
						}
					//Somehow they got to the option screen without the correct boolean
					//So this should remove them
					} else if (!c.pickingNumber) {
						if (c.gambleOn && c.firstGamble) {
							c.gamblePot -= c.myBet;
							c.totalHungerGameExp += c.myBet;
							c.myBet = 0;
							c.reloadHGstuff();
							c.getDH().sendDialogues(0, 0);
							c.sendMessage("You're bet has been returned because of an error. (1)");
							c.sendMessage("Report this on forums.");
							return;
						} else if (c.gambleOn) {
							c.totalHungerGameExp += c.myBet;
							c.gamblePot -= c.myBet;
							c.gambleFinal = false;
							c.myBet = 0;
							c.reloadHGstuff();
							c.getDH().sendDialogues(0, 0);
							c.sendMessage("You're bet has been returned because of an error. (2)");
							c.sendMessage("Report this on forums.");
							return;
						}
					}
				}
			break;

			case 9168:
				if( c.dialogueAction == 222) {
					c.getDH().sendDialogues(912, c.talkingNpc);
					c.dialogueAction = -1;
				} else if (System.currentTimeMillis() - c.lastGambleClick > 1500) {
					c.lastGambleClick = System.currentTimeMillis();
					if (c.dialogueAction == 5) {
						c.getDH().sendDialogues(42, 372);
						c.dialogueAction = 0;
						return;
					}
					//Did you receive the correct boolean?
					if (c.pickingNumber) {
						//Is player null? 
						if (c.playerName.equals(null)) {
							c.sendMessage("error_8 - Tell Peeta this");
							return;
						}
						//Is somehow the first player picking a second number?
						if (c.playerName.equals(c.gamblerOne))	{
							c.pickingNumber = false;
							c.getDH().sendDialogues(0, 0);
							c.sendMessage("error_6 - Tell Peeta this");
							return;
						}
						//Did someone else set a bet before you?
						if (c.gambleOn && c.firstGamble) {
							c.getDH().sendDialogues(53, 372);
							c.gamblePot -= c.myBet;
							c.totalHungerGameExp += c.myBet;
							c.pickingNumber = false;
							c.firstGamble = false;
							c.myBet = 0;
							c.reloadHGstuff();
							c.sendMessage("You're bet has been returned because someone set a bet before you.");
							return;
						}
						//Is this number already picked?
						if (c.gambleNum2) {
							c.getDH().sendDialogues(58, 372);
							return;
						}
						//Are you the first gambler?
						if (c.firstGamble) {
							c.gamblerOne = c.playerName;
							c.gambleNum2 = true;
							c.gambleOn = true;
							c.pickingNumber = false;
							c.myNumber = 2;
							c.getDH().sendDialogues(56, 372);
							return;
						//If not then..
						} else if (!c.firstGamble) {
							//Double check if there's already a first gambler
							if (c.gambleOn) {
								c.gambleNum2 = true;
								c.pickingNumber = false;
								c.myNumber = 2;
								c.startRollin = true;
								c.gambleNumber();
								c.getDH().sendDialogues(57, 372);
								return;
							} else if (c.gambleOn) {
								c.gamblePot = 0;
								c.totalHungerGameExp += c.myBet;
								c.pickingNumber = false;
								c.myBet = 0;
								c.firstGamble = false;
								c.reloadHGstuff();
								c.sendMessage("It seems the first person logged out or dced");
								return;
							}
						}
					//Somehow they got to the option screen without the correct boolean
					//So this should remove them
					} else if (!c.pickingNumber) {
						if (c.gambleOn && c.firstGamble) {
							c.gamblePot -= c.myBet;
							c.totalHungerGameExp += c.myBet;
							c.myBet = 0;
							c.reloadHGstuff();
							c.getDH().sendDialogues(0, 0);
							c.sendMessage("You're bet has been returned because of an error. (1)");
							c.sendMessage("Report this on forums.");
							return;
						} else if (c.gambleOn) {
							c.totalHungerGameExp += c.myBet;
							c.gamblePot -= c.myBet;
							c.gambleFinal = false;
							c.myBet = 0;
							c.reloadHGstuff();
							c.getDH().sendDialogues(0, 0);
							c.sendMessage("You're bet has been returned because of an error. (2)");
							c.sendMessage("Report this on forums.");
							return;
						}
					}
				}
			break;

			case 9169:
				if( c.dialogueAction == 222) {
					c.getDH().sendDialogues(913, c.talkingNpc);
					c.dialogueAction = -1;
				} else if (System.currentTimeMillis() - c.lastGambleClick > 1500) {
					c.lastGambleClick = System.currentTimeMillis();
					if (c.dialogueAction == 5) {
						c.getDH().sendDialogues(51, 372);
						c.dialogueAction = 0;
						return;
					}
					//Did you receive the correct boolean?
					if (c.pickingNumber) {
						//Is player null? 
						if (c.playerName.equals(null)) {
							c.sendMessage("error_2 - Tell Peeta this");
							return;
						}
						//Is somehow the first player picking a second number?
						if (c.playerName.equals(c.gamblerOne))	{
							c.pickingNumber = false;
							c.getDH().sendDialogues(0, 0);
							c.sendMessage("error_7 - Tell Peeta this");
							return;
						}
						//Did someone else set a bet before you?
						if (c.gambleOn && c.firstGamble) {
							c.getDH().sendDialogues(53, 372);
							c.gamblePot -= c.myBet;
							c.totalHungerGameExp += c.myBet;
							c.pickingNumber = false;
							c.firstGamble = false;
							c.myBet = 0;
							c.reloadHGstuff();
							c.sendMessage("You're bet has been returned because someone set a bet before you.");
							return;
						}
						//Is this number already picked?
						if (c.gambleNum3) {
							c.getDH().sendDialogues(58, 372);
							return;
						}
						//Are you the first gambler?
						if (c.firstGamble) {
							c.gamblerOne = c.playerName;
							c.gambleNum3 = true;
							c.gambleOn = true;
							c.pickingNumber = false;
							c.myNumber = 3;
							c.getDH().sendDialogues(56, 372);
							return;
						//If not then..
						} else if (!c.firstGamble) {
							//Double check if there's already a first gambler
							if (c.gambleOn) {
								c.gambleNum3 = true;
								c.pickingNumber = false;
								c.myNumber = 3;
								c.startRollin = true;
								c.gambleNumber();
								c.getDH().sendDialogues(57, 372);
								return;
							} else if (c.gambleOn) {
								c.gamblePot = 0;
								c.totalHungerGameExp += c.myBet;
								c.pickingNumber = false;
								c.myBet = 0;
								c.firstGamble = false;
								c.reloadHGstuff();
								c.sendMessage("It seems the first person logged out or dced");
								return;
							}
						}
					//Somehow they got to the option screen without the correct boolean
					//So this should remove them
					} else if (!c.pickingNumber) {
						if (c.gambleOn && c.firstGamble) {
							c.gamblePot -= c.myBet;
							c.totalHungerGameExp += c.myBet;
							c.myBet = 0;
							c.reloadHGstuff();
							c.getDH().sendDialogues(0, 0);
							c.sendMessage("You're bet has been returned because of an error. (1)");
							c.sendMessage("Report this on forums.");
							return;
						} else if (c.gambleOn) {
							c.totalHungerGameExp += c.myBet;
							c.gamblePot -= c.myBet;
							c.gambleFinal = false;
							c.myBet = 0;
							c.reloadHGstuff();
							c.getDH().sendDialogues(0, 0);
							c.sendMessage("You're bet has been returned because of an error. (2)");
							c.sendMessage("Report this on forums.");
							return;
						}
					}
				}
			break;


			case 9178:// skiller shop, godwars portal, glory

			break;

			case 9179://multi shop
			
			break;

			case 9180:

			break;

			case 9181:

			break;

			case 1093:
			case 1094:
			case 1097:
				if (c.autocastId > 0) {
					c.getPA().resetAutocast();
				} else {
				if (c.playerMagicBook == 2) {
				c.sendMessage("Autocasting with Lunar does not work.");
				c.getPA().resetAutocast();
				}
					if (c.playerMagicBook == 1) {
						if (c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15001 || c.playerEquipment[c.playerWeapon] == 15040 || c.playerEquipment[c.playerWeapon] == 6914)
							c.setSidebarInterface(0, 1689);
							else
							c.sendMessage("You can't autocast ancients without an Chaotic Staff/Ancient Staff/Staff of light.");
							c.getPA().resetAutocast();
					} else if (c.playerMagicBook == 0) {
						if (c.playerEquipment[c.playerWeapon] == 4170) {
							c.setSidebarInterface(0, 12050);
						} else {
							c.setSidebarInterface(0, 1829);
						}
					}
				}
			
			break;

			case 9157: //SendOption 2
			if (c.dialogueAction == 1) {
				c.getDH().sendDialogues(6, 619);
			} else if (c.kits[8] == null && c.dialogueAction == 2 && c.totalHungerGameExp >= 100000) {
				if (c.achievements[14][0] == 0) {
					c.achievements[14][1] = 1;
					c.achievementsHandler();
				}
				c.kits[8] = "Alchemist";
				c.sendMessage("Alchemist has been unlocked!");
				c.getPA().sendFrame126("@gre@" + c.kits[8], 16209);
				c.totalHungerGameExp -= 100000;
				c.reloadHGstuff();
				c.getPA().removeAllWindows();
			} else if (c.kits[9] == null && c.dialogueAction == 3 && c.totalHungerGameExp >= 150000) {
				if (c.achievements[14][0] == 0) {
					c.achievements[14][1] = 1;
					c.achievementsHandler();
				}
				c.kits[9] = "Forsaken";
				c.sendMessage("Forsaken has been unlocked!");
				c.getPA().sendFrame126("@gre@" + c.kits[9], 16210);
				c.totalHungerGameExp -= 150000;
				c.reloadHGstuff();
				c.getPA().removeAllWindows();
			} else if (c.kits[12] == null && c.dialogueAction == 4 && c.totalHungerGameExp >= 500000) {
				if (c.achievements[14][0] == 0) {
					c.achievements[14][1] = 1;
					c.achievementsHandler();
				}
				c.kits[12] = "Sickness";
				c.sendMessage("Sickness has been unlocked!");
				c.getPA().sendFrame126("@gre@" + c.kits[12], 16211);
				c.totalHungerGameExp -= 500000;
				c.reloadHGstuff();
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 6) {
				c.getDH().sendDialogues(48, 372);
				c.dialogueAction = 0;
			} else if (c.dialogueAction == 8) {
				c.inShop = true;
				c.getShops().openShop(2);
				c.dialogueAction = 0;
			} else if (c.kits[15] == null && c.dialogueAction == 9 && c.totalHungerGameExp >= 500000) {
				if (c.achievements[14][0] == 0) {
					c.achievements[14][1] = 1;
					c.achievementsHandler();
				}
				c.kits[15] = "Saint";
				c.sendMessage("Saint has been unlocked!");
				c.getPA().sendFrame126("@gre@" + c.kits[15], 16212);
				c.totalHungerGameExp -= 500000;
				c.reloadHGstuff();
				c.getPA().removeAllWindows();
			} else if (c.kits[21] == null && c.dialogueAction == 11) {
				if (c.totalHungerGameExp >= 300000) {
					if (c.votingPoints >= 50) {
						if (c.achievements[14][0] == 0) {
							c.achievements[14][1] = 1;
							c.achievementsHandler();
						}
						c.kits[21] = "Mercenary";
						c.sendMessage("Mercenary has been unlocked!");
						c.getPA().sendFrame126("@gre@" + c.kits[21], 16214);
						c.totalHungerGameExp -= 300000;
						c.votingPoints -= 50;
						c.sendMessage("Current voting points is now " + c.votingPoints + ".");
						c.reloadHGstuff();
						c.getPA().removeAllWindows();
					} else {
						c.sendMessage("You don't have enough voting points to unlock this kit!");
						c.sendMessage("Current voting points is now " + c.votingPoints + ".");
						c.getPA().removeAllWindows();
					}
				} else {
					c.sendMessage("You don't have enough experience to unlock this kit!");
					c.getPA().removeAllWindows();
				}
			} else if (c.dialogueAction == 10) {
				if (c.achievements[15][0] == 0) {
					c.achievements[15][1] = 1;
					c.achievementsHandler();
				}
				c.getPA().movePlayer(3087, 3499, 0);
				c.absY = 3499;
				c.absX = 3087;
				c.inPkingZone = true;
				c.myKit = "Default";
				c.rememberedKit = "Default";
				c.getTradeAndDuel().declineTrade();
				//c.edgePlayers.add(c.playerId);
				c.getPA().showInterface(1908);
				c.isSkulled = true;
				c.skullTimer = 100000;
				c.sendMessage("You've been teleported to the Edgeville pking area!");
				int counter = 0;
				for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
						Client c2 = (Client)Server.playerHandler.players[i];
						if (c2.inPkingZone) {
							counter++;
						}	
					}
				}
				c.sendMessage("There are currently @blu@" + counter + " players@bla@ in the Edgeville area.");
				c.dialogueAction = 0;
				c.tutorial = false;
			} else if (c.dialogueAction == 1000) {
				if (System.currentTimeMillis() - c.lastClick > 1000) {
					c.lastClick = System.currentTimeMillis();
					c.getItems().addItem(c.floweritem, 1);
					ObjectManager.removeFlower(c.flowerX, c.flowerY); 
					c.getPA().closeAllWindows();
					c.lastSeed -= 15000;
				}
			} else {
				c.sendMessage("You don't have enough experience to unlock this kit!");
				c.getPA().removeAllWindows();
			}
				
			break;

			case 9158: //SendOption 2
			if (c.dialogueAction == 1) {
				c.getDH().sendDialogues(4, 619);
			} else if (c.dialogueAction == 2 || c.dialogueAction == 3 || c.dialogueAction == 4 || c.dialogueAction == 9) {
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 6) {
				c.getDH().sendDialogues(49, 372);
				c.dialogueAction = 0;
			} else if (c.dialogueAction == 8) {
				c.inShop = true;
				c.getShops().openShop(10);
				c.dialogueAction = 0;
			} else if (c.dialogueAction == 10) {
				c.tutorial = false;
				c.getDH().sendDialogues(0, 0);
			} else if (c.dialogueAction == 1000) {
				c.getPA().closeAllWindows();
			} else {
				c.getDH().sendDialogues(0, 0);
			}
	
			break;

			/**Specials**/
			case 29188:
				if (c.inWretched) {
					c.sendMessage("You can't do this while in Wretched form.");
					return;
				}
		
				c.specBarId = 7636; // the special attack text - sendframe126(S P E C I A L  A T T A C K, c.specBarId);
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			break;

			case 29163:
				if (c.inWretched) {
					c.sendMessage("You can't do this while in Wretched form.");
					return;
				}
				c.specBarId = 7611;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			break;

			case 33033:
				if (c.inWretched) {
					c.sendMessage("You can't do this while in Wretched form.");
					return;
				}
				c.specBarId = 8505;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			break;

			case 29038:
				if (c.inWretched) {
					c.sendMessage("You can't do this while in Wretched form.");
					return;
				}
				if (c.playerLevel[3] <= 0) {
					return;
				} 
				c.specBarId = 7486;
				if (c.playerEquipment[c.playerWeapon] == 4153) {
					c.getCombat().handleGmaulPlayer();
				} else {
					c.usingSpecial = !c.usingSpecial;
				}
				c.getItems().updateSpecialBar();
			break;

			case 29063:
				if (c.inWretched) {
					c.sendMessage("You can't do this while in Wretched form.");
					return;
				}
				if(c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
					c.gfx0(246);
					c.forcedChat("Raarrrrrgggggghhhhhhh!");
					c.startAnimation(1056);
					c.playerLevel[2] = c.getLevelForXP(c.playerXP[2]) + (c.getLevelForXP(c.playerXP[2]) * 15 / 100);
					c.getPA().refreshSkill(2);
					c.getItems().updateSpecialBar();
				} else {
					c.sendMessage("You don't have the required special energy to use this attack.");
				}
			break;


			case 48023:
				if (c.inWretched) {
					c.sendMessage("You can't do this while in Wretched form.");
					return;
				}
				c.specBarId = 12335;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			break;

			case 29138:
				if (c.inWretched) {
					c.sendMessage("You can't do this while in Wretched form.");
					return;
				}
				if (c.playerEquipment[c.playerWeapon] == 35) {
					if(c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
						c.gfx0(247);
						c.forcedChat("For Camelot!");
						c.startAnimation(1168);
						if (c.myKit.equalsIgnoreCase("KNIGHT")) {
							c.playerLevel[1] = c.getLevelForXP(c.playerXP[1]) + (c.getLevelForXP(c.playerXP[2]) * 30 / 100);
							c.sendMessage("As a knight of the round table you receive an extra boost from Excalibur!");
						} else {
							c.playerLevel[1] = c.getLevelForXP(c.playerXP[1]) + (c.getLevelForXP(c.playerXP[2]) * 15 / 100);
						}
						c.getPA().refreshSkill(1);
						c.getItems().updateSpecialBar();
					} else {
						c.sendMessage("You don't have the required special energy to use this attack.");
					}
				} else {
					c.specBarId = 7586;
					c.usingSpecial = !c.usingSpecial;
					c.getItems().updateSpecialBar();
				}
			break;

			case 29113:
				if (c.inWretched) {
					c.sendMessage("You can't do this while in Wretched form.");
					return;
				}
				c.specBarId = 7561;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			break;

			case 29238:
				if (c.inWretched) {
					c.sendMessage("You can't do this while in Wretched form.");
					return;
				}
				c.specBarId = 7686;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			break;


			case 4169: // god spell charge
			c.usingMagic = true;
			if(!c.getCombat().checkMagicReqs(48)) {
				break;
			}

			if(System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
				c.sendMessage("You still feel the charge in your body!");
				break;
			}
			c.godSpellDelay	= System.currentTimeMillis();
			c.sendMessage("You feel charged with a magical power!");
			c.gfx100(c.MAGIC_SPELLS[48][3]);
			c.startAnimation(c.MAGIC_SPELLS[48][2]);
			c.usingMagic = false;
	        break;


			case 28164: // item kept on death
			break;

			case 152:
			c.isRunning2 = !c.isRunning2;
			int frame = c.isRunning2 == true ? 1 : 0;
			c.getPA().sendFrame36(173,frame);
			break;

			case 9154:
			c.logout();
			break;

			case 21010:
			c.takeAsNote = true;
			break;

			case 21011:
			c.takeAsNote = false;
			break;


			//home teleports
			case 4171:
			case 50056:
			case 117048:
				try {
					if (c.noSmuggleTwo >= 0 || c.isDead || c.tutorial || c.spectate || c.isMorphed || c.eventStarted)
						return;
					
					if (System.currentTimeMillis() - c.lastClick > 1500) {
						c.lastClick = System.currentTimeMillis();
						String type = c.playerMagicBook == 0 ? "modern" : "ancient";
						if (c.inCwGame || c.inCwWait || c.inWild() || c.tutorial || c.spectate || c.isMorphed) {
							c.sendMessage("A magical force prevents you from teleporting from here.");
							return;
						} else {
							c.playerLevel[3] = 99;
							c.getPA().refreshSkill(3);
							c.poisonDamage = 0;
							if (c.inPkingZone) {
								c.getPA().movePlayer(3363, 9640, 0);
								c.myKit = "";
								//c.edgePlayers.remove(c.edgePlayers.indexOf(c.playerId));
								c.inPkingZone = false;
								c.isSkulled = false;
								c.skullTimer = 0;
							} else {
								c.getPA().startTeleport(3363, 9640, 0, "modern");
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			break;

			case 50235: //paddewa
			break;

			case 4140: //varrock
			//if (c.inCwGame || c.inCwWait) {
				c.sendMessage("A magical force prevents you from teleporting from here.");
			//} else {
			//	c.getPA().startTeleport(3213, 3424, 0, "modern");
			//}
			break;

			case 50245: //senntisen
			break;

			case 4143: //lumb
			//if (c.inCwGame || c.inCwWait) {
				c.sendMessage("A magical force prevents you from teleporting from here.");
			//} else {
			//	c.getPA().startTeleport(3223, 3218, 0, "modern");
			//}
			break;

			case 50253://kharyrll

			break;

			case 4146://fally
			//if (c.inCwGame || c.inCwWait) {
				c.sendMessage("A magical force prevents you from teleporting from here.");
			//} else {	
			//	c.getPA().startTeleport(2965, 3380, 0, "modern");
			//}
			break;


			case 51005:
			break;

			case 4150: //cammy
			//if (c.inCwGame || c.inCwWait) {
				c.sendMessage("A magical force prevents you from teleporting from here.");
			//} else {		
			//	c.getPA().startTeleport(2757, 3477, 0, "modern");
			//}
			break;

			case 51013: //dareyak tele
			break;

			case 6004: //ardy
			//if (c.inCwGame || c.inCwWait) {
				c.sendMessage("A magical force prevents you from teleporting from here.");
			//} else {	
			//	c.getPA().startTeleport(2662, 3307, 0, "modern");
			//}
			break;


			case 51023: //carralangar
			//if (c.inCwGame || c.inCwWait) {
				c.sendMessage("A magical force prevents you from teleporting from here.");
			//	return;
			//}
			break;

			case 6005:
			//if (c.inCwGame || c.inCwWait) {
				c.sendMessage("A magical force prevents you from teleporting from here.");
			//	return;
			//}
			break;
	
			case 51031://annakarl
				c.sendMessage("A magical force prevents you from teleporting from here.");
			break;

			case 29031:
			break;


			case 51039: //ghorrock tele
				c.sendMessage("A magical force prevents you from teleporting from here.");
			break;

			case 72038:
				c.sendMessage("A magical force prevents you from teleporting from here.");
			break;


			case 9125: //Accurate
			case 6221: // range accurate
			case 22228: //punch (unarmed)
			case 48010: //flick (whip)
			case 21200: //spike (pickaxe)
			case 1080: //bash (staff)
			case 6168: //chop (axe)
			case 6236: //accurate (long bow)
			case 17102: //accurate (darts)
			case 8234: //stab (dagger)
			c.fightMode = 0;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

			case 9126: //Defensive
			case 48008: //deflect (whip)
			case 22229: //block (unarmed)
			case 21201: //block (pickaxe)
			case 1078: //focus - block (staff)
			case 6169: //block (axe)
			case 33019: //fend (hally)
			case 18078: //block (spear)
			case 8235: //block (dagger)
			c.fightMode = 1;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

			case 9127: // Controlled
			case 48009: //lash (whip)
			case 33018: //jab (hally)
			case 6234: //longrange (long bow)
			case 6219: //longrange
			case 18077: //lunge (spear)
			case 18080: //swipe (spear)
			case 18079: //pound (spear)
			case 17100: //longrange (darts)
			c.fightMode = 3;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

			case 9128: //Aggressive
			case 6220: // range rapid
			case 22230: //kick (unarmed)
			case 21203: //impale (pickaxe)
			case 21202: //smash (pickaxe)
			case 1079: //pound (staff)
			case 6171: //hack (axe)
			case 6170: //smash (axe)
			case 33020: //swipe (hally)
			case 6235: //rapid (long bow)
			case 17101: //repid (darts)
			case 8237: //lunge (dagger)
			case 8236: //slash (dagger)
			c.fightMode = 2;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;


			/**Prayers**/
			case 21233: // thick skin
			c.getCombat().activatePrayer(0);
			break;
			case 21234: // burst of str
			c.getCombat().activatePrayer(1);
			break;
			case 21235: // charity of thought
			c.getCombat().activatePrayer(2);
			break;
			case 70080: // range
			c.getCombat().activatePrayer(3);
			break;
			case 70082: // mage
			c.getCombat().activatePrayer(4);
			break;
			case 21236: // rockskin
			c.getCombat().activatePrayer(5);
			break;
			case 21237: // super human
			c.getCombat().activatePrayer(6);
			break;
			case 21238:	// improved reflexes
			c.getCombat().activatePrayer(7);
			break;
			case 21239: //hawk eye
			c.getCombat().activatePrayer(8);
			break;
			case 21240:
			c.getCombat().activatePrayer(9);
			break;
			case 21241: // protect Item
			c.getCombat().activatePrayer(10);
			break;
			case 70084: // 26 range
			c.getCombat().activatePrayer(11);
			break;
			case 70086: // 27 mage
			c.getCombat().activatePrayer(12);
			break;
			case 21242: // steel skin
			c.getCombat().activatePrayer(13);
			break;
			case 21243: // ultimate str
			c.getCombat().activatePrayer(14);
			break;
			case 21244: // incredible reflex
			c.getCombat().activatePrayer(15);
			break;
			case 21245: // protect from magic
						if(c.inFreeForAll()) {
			c.sendMessage("This prayer does not seem to be working in here!");
				c.prayerActive[16] = false;
				c.getPA().sendFrame36(c.PRAYER_GLOW[16], 0);		
			c.headIcon = -1;
			c.getPA().requestUpdates();
			return;
			}
			c.getCombat().activatePrayer(16);
			c.isFffaPray = true;
			break;
			case 21246: // protect from range
						if(c.inFreeForAll()) {
			c.sendMessage("This prayer does not seem to be working in here!");
				c.prayerActive[17] = false;
				c.getPA().sendFrame36(c.PRAYER_GLOW[17], 0);		
			c.headIcon = -1;
			c.getPA().requestUpdates();
			return;
			}
			c.getCombat().activatePrayer(17);
			c.isFffaPray = true;
			break;
			
			case 21247: // protect from melee
			if(c.inFreeForAll()) {
			c.sendMessage("This prayer does not seem to be working in here!");
				c.prayerActive[18] = false;
				c.getPA().sendFrame36(c.PRAYER_GLOW[18], 0);		
			c.headIcon = -1;
			c.getPA().requestUpdates();
			return;
			}
			c.isFffaPray = true;
			c.getCombat().activatePrayer(18);
			break;
			
			case 70088: // 44 range
			c.getCombat().activatePrayer(19);
			break;
			
			case 70090: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;
			
			case 2171: // retrui
			c.getCombat().activatePrayer(21);
			break;
			
			case 2172: // redem
			c.getCombat().activatePrayer(22);
			break;
			
			case 2173: // smite
			c.getCombat().activatePrayer(23);
			break;
			
			case 70092: // chiv
			c.getCombat().activatePrayer(24);
			break;
			
			case 70094: // piety
			c.getCombat().activatePrayer(25);
			break;

			case 13092:
			if(c.openTrade == false) {
//c.sendMessage("Please relog as the trading system is detecting an error. (1)");
			return ;
			}
			Client ot = (Client) Server.playerHandler.players[c.tradeWith];
			if((c.tradeScreen != 1 || ot.tradeScreen != 1) || (c.playerId == ot.playerId)) {
				return;
			}
			if(ot == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			c.getPA().sendFrame126("Waiting for other player...", 3431);
			ot.getPA().sendFrame126("Other player has accepted", 3431);
			c.goodTrade= true;
			ot.goodTrade= true;

			for (GameItem item : c.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					if(ot.getItems().freeSlots() < c.getTradeAndDuel().offeredItems.size()) {
						c.sendMessage(ot.playerName +" only has "+ot.getItems().freeSlots()+" free slots, please remove "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items.");
						ot.sendMessage(c.playerName +" has to remove "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items or you could offer them "+(c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())+" items.");
						c.goodTrade= false;
						ot.goodTrade= false;
						c.getPA().sendFrame126("Not enough inventory space...", 3431);
						ot.getPA().sendFrame126("Not enough inventory space...", 3431);
							break;
					} else {
						c.getPA().sendFrame126("Waiting for other player...", 3431);
						ot.getPA().sendFrame126("Other player has accepted", 3431);
						c.goodTrade= true;
						ot.goodTrade= true;
						}
					}
				}
				if (c.inTrade && !c.tradeConfirmed && ot.goodTrade && c.goodTrade) {
					c.tradeConfirmed = true;
					if(ot.tradeConfirmed) {
						c.getTradeAndDuel().confirmScreen();
						ot.getTradeAndDuel().confirmScreen();
						break;
					}

				}
			break;

			case 13218:
				Client ot1 = (Client) Server.playerHandler.players[c.tradeWith];
				if(c.openTrade == false) {
					return;
				}
				if((c.tradeScreen != 2 || ot1.tradeScreen != 2) || (c.playerId == ot1.playerId)) {
					return;
				}
				c.lastWalk = System.currentTimeMillis();
				c.tradeAccepted = true;
				if (ot1 == null) {
					c.getTradeAndDuel().declineTrade();
					c.sendMessage("Trade declined as the other player has disconnected.");
					break;
				}
				if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed && !c.tradeConfirmed2) {
					c.tradeConfirmed2 = true;
					if(ot1.tradeConfirmed2) {	
						c.acceptedTrade = true;
						ot1.acceptedTrade = true;
						c.getTradeAndDuel().giveItems();
						ot1.getTradeAndDuel().giveItems();
						ot1.sendMessage("@gre@Trade accepted@bla@.");
      						c.sendMessage("@gre@Trade accepted@bla@.");
						break;
					}
				ot1.getPA().sendFrame126("Other player has accepted.", 3535);
				c.getPA().sendFrame126("Waiting for other player...", 3535);
				}
				c.tradeTimer += 30;// if the time isn't enough add higher.
			break;

			case 110041:
				c.getPA().bankAll();
			break;
			
			/* Rules Interface Buttons */
			case 125011: //Click agree
				if(!c.ruleAgreeButton) {
					c.ruleAgreeButton = true;
					c.getPA().sendFrame36(701, 1);
				} else {
					c.ruleAgreeButton = false;
					c.getPA().sendFrame36(701, 0);
				}
			break;
				
			case 125003://Accept
				if(c.ruleAgreeButton) {
					c.getPA().showInterface(3559);
					c.newPlayer = false;
				} else if(!c.ruleAgreeButton) {
					c.sendMessage("You need to click on you agree before you can continue on.");
				}
			break;
				
			case 125006://Decline
				c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
			break;
				
			/* End Rules Interface Buttons */
			/* Player Options */
			case 74176:
				if(!c.mouseButton) {
					c.mouseButton = true;
					c.getPA().sendFrame36(500, 1);
					c.getPA().sendFrame36(170,1);
				} else if(c.mouseButton) {
					c.mouseButton = false;
					c.getPA().sendFrame36(500, 0);
					c.getPA().sendFrame36(170,0);
				}
			break;
				
			case 74184:
				if(!c.splitChat) {
					c.splitChat = true;
					c.getPA().sendFrame36(502, 1);
					c.getPA().sendFrame36(287, 1);
				} else {
					c.splitChat = false;
					c.getPA().sendFrame36(502, 0);
					c.getPA().sendFrame36(287, 0);
				}
			break;
				
			case 74180:
				if(!c.chatEffects) {
					c.chatEffects = true;
					c.getPA().sendFrame36(501, 1);
					c.getPA().sendFrame36(171, 0);
				} else {
					c.chatEffects = false;
					c.getPA().sendFrame36(501, 0);
					c.getPA().sendFrame36(171, 1);
				}
			break;
				
				/*Autocast*/
				case 51080:
				case 51122:
				case 51224:
				case 51172:
				case 51058:
				case 51102:
				case 51198:
				case 51146:
				case 51069:
				case 51111:
				case 51211:
				case 51159:
				case 24018:
				case 51091:
				case 51185:
				case 51133:
				if (c.playerMagicBook != 1) {
				c.setSidebarInterface(6, 12855);
				}
				
			case 74188:
				if(!c.acceptAid) {
					c.acceptAid = true;
					c.getPA().sendFrame36(503, 1);
					c.getPA().sendFrame36(427, 1);
				} else {
					c.acceptAid = false;
					c.getPA().sendFrame36(503, 0);
					c.getPA().sendFrame36(427, 0);
				}
			break;
				
			case 74192:
				if(!c.isRunning2) {
					c.isRunning2 = true;
					c.getPA().sendFrame36(504, 1);
					c.getPA().sendFrame36(173, 1);
				} else {
					c.isRunning2 = false;
					c.getPA().sendFrame36(504, 0);
					c.getPA().sendFrame36(173, 0);
				}
			break;
				
			case 74201://brightness1
				c.getPA().sendFrame36(505, 1);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166, 1);
			break;
				
			case 74203://brightness2
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 1);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166,2);
			break;

			case 74204://brightness3
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 1);
				c.getPA().sendFrame36(508, 0);
				c.getPA().sendFrame36(166,3);
			break;

			case 74205://brightness4
				c.getPA().sendFrame36(505, 0);
				c.getPA().sendFrame36(506, 0);
				c.getPA().sendFrame36(507, 0);
				c.getPA().sendFrame36(508, 1);
				c.getPA().sendFrame36(166,4);
			break;
				
			case 74206://area1
				c.getPA().sendFrame36(509, 1);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 0);
			break;
				
			case 74207://area2
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 1);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 0);
			break;
				
			case 74208://area3
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 1);
				c.getPA().sendFrame36(512, 0);
			break;
				
			case 74209://area4
				c.getPA().sendFrame36(509, 0);
				c.getPA().sendFrame36(510, 0);
				c.getPA().sendFrame36(511, 0);
				c.getPA().sendFrame36(512, 1);
			break;
				
			case 168:
                c.startAnimation(855);
            break;
			
            case 169:
                c.startAnimation(856);
            break;
			
            case 162:
                c.startAnimation(857);
            break;
			
            case 164:
                c.startAnimation(858);
            break;
			
            case 165:
                c.startAnimation(859);
            break;
			
            case 161:
                c.startAnimation(860);
            break;
			
            case 170:
                c.startAnimation(861);
            break;
			
            case 171:
                c.startAnimation(862);
            break;
			
            case 163:
                c.startAnimation(863);
            break;
			
            case 167:
                c.startAnimation(864);
            break;
			
            case 172:
                c.startAnimation(865);
            break;
			
            case 166:
                c.startAnimation(866);
            break;
			
            case 52050:
                c.startAnimation(2105);
            break;
			
            case 52051:
                c.startAnimation(2106);
            break;
			
            case 52052:
                c.startAnimation(2107);
            break;
			
            case 52053:
                c.startAnimation(2108);
            break;
			
            case 52054:
                c.startAnimation(2109);
            break;
			
            case 52055:
                c.startAnimation(2110);
            break;
			
            case 52056:
                c.startAnimation(2111);
            break;
			
            case 52057:
                c.startAnimation(2112);
            break;
			
            case 52058:
                c.startAnimation(2113);
            break;
			
            case 43092:
                c.startAnimation(0x558);
            break;
			
            case 2155:
                c.startAnimation(0x46B);
            break;
			
            case 25103:
                c.startAnimation(0x46A);
            break;
			
            case 25106:
                c.startAnimation(0x469);
            break;
			
            case 2154:
                c.startAnimation(0x468);
            break;
			
            case 52071:
                c.startAnimation(0x84F);
            break;
			
            case 52072:
                c.startAnimation(0x850);
            break;
			
            case 59062:
                c.startAnimation(2836);
            break;
			
            case 72032:
                c.startAnimation(3544);
            break;
			
            case 72033:
                c.startAnimation(3543);
            break;
			
            case 72254:
                c.startAnimation(3866);
            break;
			/* END OF EMOTES */
			case 28166:

			break;
				
			case 118098:
				if(c.duelRule[4]) {
					 c.sendMessage("You cannot use Vengance in a duel when magic is turned off!");
				  return;
				}
				if (c.playerLevel[6] < 93) {
					c.sendMessage("You need a magic level of 94 or more to cast this.");
				} else if (c.playerLevel[1] < 40) {
					c.sendMessage("You need a defence level of 40 or more to cast this.");
				} else {
					c.getPA().vengMe();
				}
				//SkillMenu.openInterface(c, -1)
				//SkillMenu.openInterface(c,0);
			break;

			case 7212:
			case 47069:
			case 24017:
				c.getPA().resetAutocast();
				//c.sendFrame246(329, 200, c.playerEquipment[c.playerWeapon]);
				c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
				//c.setSidebarInterface(0, 328);
				//c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151 : c.playerMagicBook == 1 ? 12855 : 1151);
			break;
						case 86004:
			c.getPA().showInterface(15150);
			break;
			case 86008:
			c.getPA().showInterface(5292);
			break;

		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
	}
}