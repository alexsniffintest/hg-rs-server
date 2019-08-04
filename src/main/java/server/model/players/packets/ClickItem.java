package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;
import server.world.ObjectManager;

import java.util.ArrayList;



/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

	public static int flower[] = { 2980, 2981, 2982, 2983, 2984, 2985, 2986, 2987 };
	public int randomflower() {
		return flower[(int)(Math.random() * flower.length)];
	}
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int junk = c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		if (c.usingCarpet) {
			return;
		}
		if (c.noSmuggleTwo >= 0) {
			return;
		}
		if (itemId != c.playerItems[itemSlot] - 1) {
			return;
		}
		
		if(itemId == 299 && c.seedtimer == 0) {
			if (c.inHomeArea() && c.inEggArea()) {
				if (System.currentTimeMillis() - c.lastSeed > 15000) {
					c.flowers = randomflower();
					for (int i = 0, flowerId = 2980, flowerItemC = 2460; i < 8; i++, flowerId++, flowerItemC += 2) {
						if (c.flowers == flowerId) {
							c.floweritem = flowerItemC;
						}
					}
					c.flowerX = c.absX;
					c.flowerY = c.absY;
					ObjectManager.placeFlower(c.flowers, c.absX, c.absY);
					c.lastSeed = System.currentTimeMillis();
					c.getItems().deleteItem2(299, 1);
					c.getPA().walkTo(-1,0); 
					c.getDH().sendDialogues(9999, -1);
					c.sendMessage("You plant the seed...");
				} else {
					long finalTime = 15 - (System.currentTimeMillis() - c.lastSeed) / 1000;
					c.sendMessage("Please wait " + finalTime + " seconds till before planting another!");
				}
			} else {
				c.sendMessage("You can only use these in the north-east corner of home!");
			}
		}
		
		if (itemId == 748 && c.myKit.equalsIgnoreCase("KNIGHT") && c.inCwGame || itemId == 748 && c.myKit.equalsIgnoreCase("KNIGHT") && c.inWild() && c.inPkingZone) {
			if (c.knightMode) {
				c.sendMessage("You're already under the influence of the spell.");
				return;
			}
			if(System.currentTimeMillis() - c.lastKnight2 > 80000) {
				c.lastKnight = System.currentTimeMillis();
				c.forcedChat("Protect me Saradomin!");
				c.startAnimation(1670);
				c.knightMode = true;
			} else {
				long finalTime = 80 - (System.currentTimeMillis() - c.lastKnight2) / 1000;
				c.sendMessage("You have " + finalTime + " seconds till you can cast this spell again!");
			}
		}
		
		if (itemId == 7498) {
			if (c.inCwGame && !c.lampReward) {
				c.sendMessage("You rub the lamp, and it turns to dust infront of you.");
				c.getItems().deleteItem(7498,1);
				c.lampReward = true;
				if (c.achievements[20][0] == 0) {
					c.achievements[20][1] = 1;
					c.achievementsHandler();
				}
			} else if (c.lampReward) {
				c.sendMessage("Nothing happens.");
			}
		}
		
		if (itemId == 4707) {
			if (c.myKit.equalsIgnoreCase("WRETCHED")) {
			System.out.println("here wretched");
				if (c.inWretched) {
					c.inWretched = false;
					c.isNpc = false;
					c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon], c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
					c.getItems().resetBonus();
					c.getItems().getBonus();
					c.getItems().writeBonus();
					c.getCombat().getPlayerAnimIndex(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.wretchedId = -1;
					c.autocasting = false;
					c.sendMessage("The anicent power of the barrows brother fades from you!");
					return;
				}
				if (c.randomMap == 0 && Server.HungerGames.gameTimer < 25) {
					c.sendMessage("You can't use this till 15 game seconds have passed!");
					return;
				} else if (c.randomMap == 1 && Server.HungerGamesFal.gameTimer < 25) {
					c.sendMessage("You can't use this till 15 game seconds have passed!");
					return;
				} else if (c.randomMap == 2 && Server.HungerGamesCan.gameTimer < 25) {
					c.sendMessage("You can't use this till 15 game seconds have passed!");
					return;
				} else if (c.randomMap == 3 && Server.HungerGamesMisc.gameTimer < 25) {
					c.sendMessage("You can't use this till 15 game seconds have passed!");
					return;
				}
				if (c.wretchedUses < 1) {
					return;
				}
				if (System.currentTimeMillis() - c.lastClick > 45000) {
					c.lastClick = System.currentTimeMillis();
					int id = (int)(Math.random() * 6);
					if (c.wretchedId > -1) {
						while (id == c.wretchedId) {
							id = (int)(Math.random() * 6);
						}
					} else if (c.wretchedId == -1) {
						id = (int)(Math.random() * 6);
					}

					String brother = "";
					switch (id) {
						case 0:
							brother = "Ahrims";
						break;
						
						case 1:
							brother = "Dharoks";
						break;
						
						case 2:
							brother = "Guthans";
						break;
						
						case 3:
							brother = "Karils";
						break;
						
						case 4:
							brother = "Torags";
						break;
						
						case 5:
							brother = "Veracs";
						break;
					}
					c.forcedChat(brother + ", lend me your strength!");
					c.wretchedUses--;
					if (c.wretchedUses > 0) {
						c.sendMessage("You have " + c.wretchedUses + " uses left before the book crumbles!");
					} else {
						c.getItems().deleteItem(4707, 1);
						c.sendMessage("You've run out of uses, the book crumbles!");
					}
					c.startAnimation(1914);
					c.wretchedId = id;
					c.inWretched = true;
					c.wretchedForm(id);
					c.lastWretched = System.currentTimeMillis();
					c.getPA().requestUpdates();
				} else {
					long finalTime = 45 - (System.currentTimeMillis() - c.lastClick) / 1000;
					c.sendMessage("Please wait for " + finalTime + " more seconds before using this again!");
				}
			}
		}
		
		if (itemId == 4002) {
			if (c.myKit.equalsIgnoreCase("TAGGED") && c.inCwGame) {
				ArrayList<Integer> map = new ArrayList<Integer>();
				if (c.isDead) {
					return;
				}
				if (c.taggedUses == 0) {
					c.sendMessage("You have no more Tagged uses for this Game!");
					return;
				}
				if (c.taggedPlayer.equals("")) {
					c.sendMessage("You haven't tagged anyone yet!");
					return;
				}
				if (c.randomMap == 0 && Server.HungerGames.gameTimer < 20) {
					c.sendMessage("You can't use this till 10 game seconds have passed!");
					return;
				} else if (c.randomMap == 1 && Server.HungerGamesFal.gameTimer < 20) {
					c.sendMessage("You can't use this till 10 game seconds have passed!");
					return;
				} else if (c.randomMap == 2 && Server.HungerGamesCan.gameTimer < 20) {
					c.sendMessage("You can't use this till 10 game seconds have passed!");
					return;
				} else if (c.randomMap == 3 && Server.HungerGamesMisc.gameTimer < 20) {
					c.sendMessage("You can't use this till 10 game seconds have passed!");
					return;
				}
				if (System.currentTimeMillis() - c.lastTag > 8000) {
					if (c.randomMap == 0) {
						map = Server.HungerGames.currentPlayers;
					} else if (c.randomMap == 1) {
						map = Server.HungerGamesFal.currentPlayers_Fal;
					} else if (c.randomMap == 2) {
						map = Server.HungerGamesCan.currentPlayers_Can;
					} else if (c.randomMap == 3) {
						map = Server.HungerGamesMisc.currentPlayers_Misc;
					}
					for (int player : map) {
						if (Server.playerHandler.players[player] != null) {
							Client o = (Client)Server.playerHandler.players[player];
							if (c.taggedPlayer.equals(o.playerName)) {
								if (!o.inCwGame && c.randomMap != o.randomMap) {
									c.sendMessage("The player you've tagged isn't in the match anymore.");
									c.taggedPlayer = null;
								}
								int damage = Misc.random(20) + 10;
								o.dealDamage(damage);
								o.handleHitMask(damage);
								o.getPA().refreshSkill(3);
								o.startAnimation(3170);
								c.sendMessage("You use the Tagger device to hurt @blu@" + c.taggedPlayer + "@bla@, doing " + damage + " damage!");
								c.sendMessage("The device has " + (c.taggedUses - 1) + " uses left.");
								o.sendMessage("You feel a strange force hit you, dealing " + damage + " damage!");
								c.lastTag = System.currentTimeMillis();
								c.taggedUses--;
								break;
							}
						}
					}
				} else {
					long finalTime = 8 - (System.currentTimeMillis() - c.lastTag) / 1000;
					c.sendMessage("Please wait for " + finalTime + " more seconds before using this device again!");
				}
			}
		}
		
		if (itemId == 4837 && c.myKit.equalsIgnoreCase("NECROMORPHER") && c.inCwGame || itemId == 4837 && c.myKit.equalsIgnoreCase("NECROMORPHER") && c.inWild() && c.inPkingZone) {
			if (!c.inPkingZone) {
				if (c.randomMap == 0 && Server.HungerGames.gameTimer < 40) {
					c.sendMessage("You can't use this till 30 game seconds have passed!");
					return;
				} else if (c.randomMap == 1 && Server.HungerGamesFal.gameTimer < 40) {
					c.sendMessage("You can't use this till 30 game seconds have passed!");
					return;
				} else if (c.randomMap == 2 && Server.HungerGamesCan.gameTimer < 40) {
					c.sendMessage("You can't use this till 30 game seconds have passed!");
					return;
				} else if (c.randomMap == 3 && Server.HungerGamesMisc.gameTimer < 40) {
					c.sendMessage("You can't use this till 30 game seconds have passed!");
					return;
				}
			}
			if (c.necroMode) {
				c.sendMessage("You're already morphed!");
				return;
			}
			if (c.inWretched) {
				c.sendMessage("You're still in Wretched mode!");
				return;
			}
			if (System.currentTimeMillis() - c.lastNecro2 > 85000) {
				c.lastNecro = System.currentTimeMillis();
				c.npcId2 = 94;
				c.isNpc = true;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.necroMode = true;
				c.startAnimation(1914);
				c.getPA().createPlayersStillGfx(197, (c.absX - 0), (c.absY - 1), 0, 5);
				c.getPA().createPlayersStillGfx(197, (c.absX + 1), (c.absY - 0), 0, 5);
				c.getPA().createPlayersStillGfx(197, (c.absX - 0), (c.absY + 1), 0, 5);
				c.getPA().createPlayersStillGfx(197, (c.absX - 1), (c.absY - 0), 0, 5);
				c.forcedChat("Fear the power of the undead!");
			} else {
				long finalTime = 85 - (System.currentTimeMillis() - c.lastNecro2) / 1000;
				c.sendMessage("You have " + finalTime + " seconds till you can morph again!");
			}
		}
		
		if(itemId == 8408  && c.myKit.equalsIgnoreCase("JUMPER") && c.inCwGame) {
			if (c.isDead)
				return;
			if (c.inDeathBattle()) {
				c.sendMessage("You can't use this while inside the Death Match!");
				return;
			}
			if(System.currentTimeMillis() - c.lastJumperPortal < 20000) {//Jumper
				long finalTime = 20 - (System.currentTimeMillis() - c.lastJumperPortal) / 1000;
				c.sendMessage("You have " + finalTime + " seconds till you can use your Portal again!");
			} else if(c.hasPlacedPortalDown) {
				c.lastJumperPortal = System.currentTimeMillis();
				
				ArrayList<Integer> map = new ArrayList<Integer>();
				if (c.randomMap == 0) {
					map = Server.HungerGames.currentPlayers;
				} else if (c.randomMap == 1) {
					map = Server.HungerGamesFal.currentPlayers_Fal;
				} else if (c.randomMap == 2) {
					map = Server.HungerGamesCan.currentPlayers_Can;
				} else if (c.randomMap == 3) {
					map = Server.HungerGamesMisc.currentPlayers_Misc;
				}
				for (int player : map) {
					if (Server.playerHandler.players[player] != null) {
						Client o = (Client)Server.playerHandler.players[player];
						int xVal = (c.absX - o.absX);
						int yVal = (c.absY - o.absY);
						if (Math.abs(xVal) <= 1 && Math.abs(yVal) <= 1) {
							if (c.playerId == o.playerId) {
								o.getPA().movePlayer(c.portalX, c.portalY, 0);
								c.sendMessage("You teleport to your portal!");
								o.jumperTimer = 5;
							} else {
								if (Misc.random(2) == 0) {
									o.sendMessage("You've been teleported by @blu@" + c.playerName + "@bla@!");
									o.jumperTimer = 5;
									o.getPA().movePlayer(c.portalX, c.portalY, 0);
								} else {
									int damage = Misc.random(20) + 10;
									c.dealDamage(damage);
									c.handleHitMask(damage);
									c.getPA().refreshSkill(3);
									o.dealDamage(damage);
									o.handleHitMask(damage);
									o.getPA().refreshSkill(3);
									o.sendMessage("@blu@" +c.playerName + "@bla@ tried to teleport you, but it failed!");
									c.sendMessage("You failed to teleport @blu@" + o.playerName + "@bla@!");
									c.forcedChat("Owww!");
									o.forcedChat("Owww!");
									c.forcedChatUpdateRequired = true;
									o.forcedChatUpdateRequired = true;
								}
							}
						}
					}
				}
			} else {
				c.sendMessage("You must first place your portal down.");
			}
		}
		
		if(itemId == 6950 && c.myKit.equalsIgnoreCase("TAINTED") && c.inCwGame || itemId == 6950 && c.myKit.equalsIgnoreCase("TAINTED") && c.inWild() && c.inPkingZone) {
			if (c.isDead)
				return;
			if (!c.HGAttack && !c.inPkingZone) {
				c.sendMessage("You can't use this till the count down is over!");
				return;
			}
			if (System.currentTimeMillis() - c.lastOrb > 60000) {
				int healthGain = 0;
				c.lastOrb = System.currentTimeMillis();
				c.stopMovement();
				c.getPA().createPlayersStillGfx(559, (c.absX - 1), (c.absY - 1), 0, 20);
				c.getPA().createPlayersStillGfx(197, (c.absX - 0), (c.absY - 1), 0, 10);
				c.getPA().createPlayersStillGfx(559, (c.absX + 1), (c.absY - 1), 0, 20);
				c.getPA().createPlayersStillGfx(197, (c.absX + 1), (c.absY - 0), 0, 10);
				c.getPA().createPlayersStillGfx(559, (c.absX + 1), (c.absY + 1), 0, 20);
				c.getPA().createPlayersStillGfx(197, (c.absX - 0), (c.absY + 1), 0, 10);
				c.getPA().createPlayersStillGfx(559, (c.absX - 1), (c.absY + 1), 0, 20);
				c.getPA().createPlayersStillGfx(197, (c.absX - 1), (c.absY - 0), 0, 10);
				c.getPA().createPlayersStillGfx(559, (c.absX - 2), (c.absY - 2), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX - 1), (c.absY - 2), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX - 0), (c.absY - 2), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX + 1), (c.absY - 2), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX + 2), (c.absY - 2), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX + 2), (c.absY - 1), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX + 2), (c.absY - 0), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX + 2), (c.absY + 1), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX + 2), (c.absY + 2), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX + 1), (c.absY + 2), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX + 0), (c.absY + 2), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX - 1), (c.absY + 2), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX - 2), (c.absY + 2), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX - 2), (c.absY + 1), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX - 2), (c.absY + 0), 0, 30);
				c.getPA().createPlayersStillGfx(559, (c.absX - 2), (c.absY - 1), 0, 30);
				
				c.forcedChat("Your soul is mine!");
				c.gfx0(444);
				c.startAnimation(1500);
				ArrayList<Integer> map = new ArrayList<Integer>();
				if (c.randomMap == 0) {
					map = Server.HungerGames.currentPlayers;
				} else if (c.randomMap == 1) {
					map = Server.HungerGamesFal.currentPlayers_Fal;
				} else if (c.randomMap == 2) {
					map = Server.HungerGamesCan.currentPlayers_Can;
				} else if (c.randomMap == 3) {
					map = Server.HungerGamesMisc.currentPlayers_Misc;
				} else if (c.inPkingZone) {
					map = c.edgePlayers;
				}
				for (int player : map) {
					if (Server.playerHandler.players[player] != null) {
						Client o = (Client)Server.playerHandler.players[player];
						if (o.playerId != c.playerId) {
							if (c.inWild() && !o.inWild()) {
								return;
							}
							int xVal = (c.absX - o.absX);
							int yVal = (c.absY - o.absY);
							if (Math.abs(xVal) <= 3 && Math.abs(yVal) <= 3) {
								int damageTainted = 0;
								if (o.inPkingZone) {
									damageTainted = Misc.random(2) + 1;
								} else {
									damageTainted = Misc.random(10) + 5;
								}
								if (o.playerLevel[3] > 0) {
									o.dealDamage(damageTainted);
									o.handleHitMask(damageTainted);
								}
								healthGain += damageTainted / 2;
								o.getPA().refreshSkill(3);
								o.gfx0(436);
								o.sendMessage("Some of your soul has been leeched by @blu@" + c.playerName + "@bla@!");
							}
						}
					}
				}
				if (c.inPkingZone) {
					c.sendMessage("The damage has been reduced because you're at Edge.");
				}
				if (c.playerLevel[3] >= 85) {
					c.playerLevel[3] = 99;
				} else {
					c.playerLevel[3] += healthGain;
				}
				if (c.playerLevel[5] > 99) {
					c.playerLevel[5] = 114;
				} else {
					c.playerLevel[5] += 15;
				}
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You must wait " + Math.abs(((System.currentTimeMillis() - c.lastOrb) / 1000) - 60) + " more seconds before using this again.");
			}
		} else if (c.inPkingZone && itemId == 6950 && c.myKit.equalsIgnoreCase("TAINTED")) {
			c.sendMessage("You can only use this from inside the wildy.");
		}
		
		if(itemId == 4155) {
			if (c.isDead)
				return;
			if (c.HGAttack) {
				if (System.currentTimeMillis() - c.lastStone > 20000) {
					String xstring = "", ystring = "";
					int px, py, fx = 5000, fy = 5000, cx, cy;
					if (c.randomMap == 0) {
						for (int player : Server.HungerGames.currentPlayers) {
							if (Server.playerHandler.players[player] != null) {
								Client c2 = (Client)Server.playerHandler.players[player];
								px = (c2.absX - c.absX);
								py = (c2.absY - c.absY);

								if (px == 0 && py == 0) {
									px = 9000;
									py = 9000;
								}
								
								if (Math.abs(px) <  Math.abs(fx) && Math.abs(py) < Math.abs(fy)) {
									fx = px;
									fy = py;
								}
							}
						}
					} else if (c.randomMap == 1) {
						for (int player : Server.HungerGamesFal.currentPlayers_Fal) {
							if (Server.playerHandler.players[player] != null) {
								Client c2 = (Client)Server.playerHandler.players[player];
								px = (c2.absX - c.absX);
								py = (c2.absY - c.absY);
								
								if (px == 0 && py == 0) {
									px = 9000;
									py = 9000;
								}
								
								if (Math.abs(px) <  Math.abs(fx) && Math.abs(py) < Math.abs(fy)) {
									fx = px;
									fy = py;
								}
							}
						}
					} else if (c.randomMap == 2) {
						for (int player : Server.HungerGamesCan.currentPlayers_Can) {
							if (Server.playerHandler.players[player] != null) {
								Client c2 = (Client)Server.playerHandler.players[player];
								px = (c2.absX - c.absX);
								py = (c2.absY - c.absY);
								
								if (px == 0 && py == 0) {
									px = 9000;
									py = 9000;
								}
								
								if (Math.abs(px) <  Math.abs(fx) && Math.abs(py) < Math.abs(fy)) {
									fx = px;
									fy = py;
								}
							}
						}
					} else if (c.randomMap == 3) {
						for (int player : Server.HungerGamesMisc.currentPlayers_Misc) {
							if (Server.playerHandler.players[player] != null) {
								Client c2 = (Client)Server.playerHandler.players[player];
								px = (c2.absX - c.absX);
								py = (c2.absY - c.absY);
								
								if (px == 0 && py == 0) {
									px = 9000;
									py = 9000;
								}
								
								if (Math.abs(px) <  Math.abs(fx) && Math.abs(py) < Math.abs(fy)) {
									fx = px;
									fy = py;
								}
							}
						}
					}
					
					if (fx < 0) {
						xstring = "West (" + fx + ")";
					} else if (fx >= 0) {
						xstring = "East (" + fx + ")";
					}
					
					if (fy < 0) {
						ystring = "South (" + fy + ")";
					} else if (fy >= 0) {
						ystring = "North (" + fy + ")";
					}
					
					c.sendMessage("You rub the gem, and you feel it tug...");
					c.sendMessage("@red@" + ystring + ", " + xstring);
					c.lastStone = System.currentTimeMillis();
				} else {
					c.sendMessage("The stone feels weak, I need to wait 20 seconds before using it again.");
				}
			} else {
				c.sendMessage("You can only use this once the game has started!");
			}
		}
		
		if(itemId == 7927) {
			if (c.inHomeArea() && c.inEggArea()) {
				c.resetWalkingQueue();
				for (int i = 0; i < 14; i++) {
					c.setSidebarInterface(i, 6014);
				}
				c.isMorphed = true;
				c.sendMessage("As you put on the ring you turn into an egg!");
				c.npcId2 = 3689 + Misc.random(5);
				c.isNpc = true;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
			} else if (!c.inHomeArea()) {
				c.resetWalkingQueue();
				for (int i = 0; i < 14; i++) {
					c.setSidebarInterface(i, 6014);
				}
				c.isMorphed = true;
				c.sendMessage("As you put on the ring you turn into an egg!");
				c.npcId2 = 3689 + Misc.random(5);
				c.isNpc = true;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
			} else {
				c.sendMessage("Please go to the north-east corner to use this at home!");
			}
		}
		
		if (itemId == 15316) {
			if (c.perkOne == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkOne = 1;
				c.getItems().deleteItem(15316,1);
				c.sendMessage("You've unlocked the special perk, your special will now recover quicker!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15317) {
			if (c.perkTwo == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkTwo = 1;
				c.getItems().deleteItem(15317,1);
				c.sendMessage("You've unlocked the food perk, you'll now start with extra food each game!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15318) {
			if (c.perkThreeMelee >= 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkThreeMelee = 1;
				c.getItems().deleteItem(15318,1);
				c.sendMessage("You've unlocked Melee Attack Boost I, you'll now deal 3% more damage!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15319) {
			if (c.perkThreeMelee != 2) {
				if (c.perkThreeMelee < 1) {
					c.sendMessage("You need to unlock Melee Attack Boost I first!");
				} else {
					c.perkThreeMelee = 2;
					c.getItems().deleteItem(15319,1);
					c.sendMessage("You've unlocked Melee Attack Boost II, you'll now deal 8% more damage!");
					c.checkForSpecialist();
					if (c.achievements[11][0] == 0) {
						c.achievements[11][1] = 1;
						c.achievementsHandler();
					}
				}
			} else {
				c.sendMessage("You already have this perk!");
			}
		}
		
		if (itemId == 15320) {
			if (c.perkFourRange >= 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkFourRange = 1;
				c.getItems().deleteItem(15320,1);
				c.sendMessage("You've unlocked Range Attack Boost I, you'll now deal 3% more damage!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15321) {
			if (c.perkFourRange != 2) {
				if (c.perkFourRange < 1) {
					c.sendMessage("You need to unlock Range Attack Boost I first!");
				} else {
					c.perkFourRange = 2;
					c.getItems().deleteItem(15321,1);
					c.sendMessage("You've unlocked Range Attack Boost II, you'll now deal 8% more damage!");
					c.checkForSpecialist();
					if (c.achievements[11][0] == 0) {
						c.achievements[11][1] = 1;
						c.achievementsHandler();
					}
				}
			} else {
				c.sendMessage("You already have this perk!");
			}
		}

		if (itemId == 15322) {
			if (c.perkFiveMagic >= 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkFiveMagic = 1;
				c.getItems().deleteItem(15322,1);
				c.sendMessage("You've unlocked Magic Attack Boost I, you'll now deal 3% more damage!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15323) {
			if (c.perkFiveMagic != 2) {
				if (c.perkFiveMagic < 1) {
					c.sendMessage("You need to unlock Magic Attack Boost I first!");
				} else {
					c.perkFiveMagic = 2;
					c.getItems().deleteItem(15323,1);
					c.sendMessage("You've unlocked Magic Attack Boost II, you'll now deal 8% more damage!");
					c.checkForSpecialist();
					if (c.achievements[11][0] == 0) {
						c.achievements[11][1] = 1;
						c.achievementsHandler();
					}
				}
			} else {
				c.sendMessage("You already have this perk!");
			}
		}
		
		if (itemId == 15324) {
			if (c.perkSixPray == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkSixPray = 1;
				c.getItems().deleteItem(15324,1);
				c.sendMessage("You've unlocked the Prayer Perk, your prayer will drain less now!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15325) {
			if (c.perkSevenIce == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkSevenIce = 1;
				c.getItems().deleteItem(15325,1);
				c.sendMessage("You've unlocked the Ice Resist Perk, you now won't be frozen as long!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15326) {
			if (c.perkEightStat == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkEightStat = 1;
				c.getItems().deleteItem(15326,1);
				c.sendMessage("You've unlocked the Stat Boost perk, your stats will be boosted each game!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15328) { //Vote Package
			if (c.getItems().freeSlots() >= 2) {
				c.getItems().deleteItem(15328,1);
				c.getItems().addItem(c.voteReward(), 1);
				if (Misc.random(3) == 0) {
					c.getItems().addItem(c.voteRewardTwo(), 1);
				} else {
					c.getItems().addItem(995, 30000);
				}
				c.sendMessage("Enjoy your reward!");
			} else {
				c.sendMessage("You need atleast two inventory spots.");
			}
		}
		
		if (itemId == 4079) {
			if (System.currentTimeMillis() - c.lastClick > 1000) {
				c.lastClick = System.currentTimeMillis();
				c.startAnimation(1457);
			}
		}
		
		if (itemId == 15329) {
			if (c.perkKaboom == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkKaboom = 1;
				c.getItems().deleteItem(15329,1);
				c.sendMessage("You've unlocked the Kaboom! perk, your Retribution has been given an upgrade!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15330) {
			if (c.perkGifted == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkGifted = 1;
				c.getItems().deleteItem(15330,1);
				c.sendMessage("You've unlocked the Gifted perk, now you'll be randomly treated with gifts in-game!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15331) {
			if (c.perkSwitch == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkSwitch = 1;
				c.getItems().deleteItem(15331,1);
				c.sendMessage("You've unlocked the Switch perk, now you can switch kits in-game, once per game!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15332) {
			if (c.perkVeng == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkVeng = 1;
				c.getItems().deleteItem(15332,1);
				c.sendMessage("You've unlocked the Vengful perk, you'll now start with vengeance runes each game!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15333) {
			if (c.perkConnect == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkConnect = 1;
				c.getItems().deleteItem(15333,1);
				c.sendMessage("You've unlocked the Connector perk, you can now join already started games!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15334) {
			c.getItems().deleteItem(15334,1);
			if (c.KC >= 100 || c.DC >= 100) {
				c.checkForSpecialist();
				if (c.achievements[27][0] == 0) {
					c.achievements[27][1] = 1;
					c.achievementsHandler();
				}
			}
			c.KC = 0;
			c.DC = 0;
			c.reloadHGstuff();
			c.sendMessage("Your kills and deaths have been reset to zero!");
		}
		
		if (itemId == 15335) {
			if (c.perkConquer == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkConquer = 1;
				c.getItems().deleteItem(15335,1);
				c.sendMessage("You've unlocked the Conquer perk, you'll now gain 2% damage per kill!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15338) {
			if (c.perkYell == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkYell = 1;
				c.getItems().deleteItem(15338,1);
				c.sendMessage("You've unlocked the Yell perk, you can now freely use the ::yell command!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15336) { //collect gift
			if (c.startChristmas == 0) {
				c.sendMessage("Maybe I should talk to Santa at home first.");
				return;
			}
			if (System.currentTimeMillis() - c.lastClick > 1000) {
				c.lastClick = System.currentTimeMillis();
				c.getItems().deleteItem(15336,1);
				c.gifts++;
				if (c.gifts == 50) {
					c.sendMessage("You've collected the 50 gifts needed for a reward, go talk to Santa!");
				} else {
					c.sendMessage("You've collected " + c.gifts + " so far!");
				}
			}
		}
		
		if (itemId == 15337) { //special gift
			if (System.currentTimeMillis() - c.lastClick > 1000) {
				if (c.getItems().freeSlots() >= 5) {
					c.lastClick = System.currentTimeMillis();
					c.getItems().deleteItem(15337,1);
					
					//Rare kit or exp
					if (Misc.random(130) == 69) {
						boolean which = false;
						if(c.kits[5] == null) {
							c.sendMessage("You won @red@Blizzard@bla@! It's has been added to your kit list, congratulations!");
							c.kits[5] = "Blizzard";
							c.getPA().sendFrame126("@gre@" + "Blizzard", 16212);
							which = true;
						} else {
							c.sendMessage("Congratulations, you just won @red@Blizzard@bla@ but it appears you already have it.");
							c.sendMessage("You've been given @red@ 400 donator points instead!");
							c.donatorPoints += 400;
						}
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								Client c1 = (Client)Server.playerHandler.players[i];
								if (which) {
									c1.sendMessage("[@red@SERVER@bla@] Congratulations to " + c.playerName + ", who just won the Blizzard kit!");
								} else {
									c1.sendMessage("[@red@SERVER@bla@] Congratulations to " + c.playerName + ", who just won 400 donator points!");
								}
							}
						}
					}
					//Rainbow Phat
					if (Misc.random(140) == 69) {
						c.getItems().addItem(15284, 1);
						for(int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i] != null) {
								Client c1 = (Client)Server.playerHandler.players[i];
								c1.sendMessage("[@red@SERVER@bla@] Congratulations to " + c.playerName + ", who just won a Rainbow Party hat!");
							}
						}
					}
					//Sled
					if (Misc.random(2) == 0) {
						c.getItems().addItem(4084, 1);
					}
					c.getItems().addItem(c.xmasItem(), 1);
					c.getItems().addItem(c.voteRewardTwo(), 1);
					c.getItems().addItem(4079, 1); //100% for YO-YO
					c.sendMessage("Merry Christmas!");
				} else {
					c.sendMessage("You need atleast 5 open inventory spots.");
				}
			}
		}
		
		if (itemId == 15339) {
			if (c.perkVengTwo == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkVengTwo = 1;
				c.getItems().deleteItem(15339,1);
				c.sendMessage("You've unlocked the Vengeful perk II, your Veng will now do more damage");
				c.sendMessage("and have a smaller cooldown!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 15340) {
			if (c.perkLazy == 1) {
				c.sendMessage("You already have this perk!");
			} else {
				c.perkLazy = 1;
				c.getItems().deleteItem(15340,1);
				c.sendMessage("You've unlocked the Lazy perk, you'll now randomly decrese your opponents stats!");
				c.checkForSpecialist();
				if (c.achievements[11][0] == 0) {
					c.achievements[11][1] = 1;
					c.achievementsHandler();
				}
			}
		}
		
		if (itemId == 1959) {
			if (c.pumpkinPerk == 0) {
				c.pumpkinPerk = 1;
				c.getItems().deleteItem(1959, 1);
				c.sendMessage("As you eat the Pumpkin for your first time, you feel a great evil engulf you!");
				c.sendMessage("Anyone you touch has a chance to get cursed!");
			}
		}
		
		if (itemId == 3801) {
			if (System.currentTimeMillis() - c.foodDelay >= 1500) {
				if (System.currentTimeMillis() - c.lastBeer > 60000) {
					if (c.beerPerk == 0 && !c.inCwGame) {
						c.beerPerk = 1;
						c.getItems().deleteItem(3801, 1);
						c.sendMessage("This is only the first of many beers... You'll now get beer every game!");
						if (c.playerLevel[0] < 41) {
							c.playerLevel[0] = 0;
						} else {
							c.playerLevel[0] -= 40;
						}
						if (c.playerLevel[2] < 140) {
							c.playerLevel[2] += 10;
						}
					} else {
						if (c.playerLevel[0] < 41) {
							c.playerLevel[0] = 0;
						} else {
							c.playerLevel[0] -= 40;
						}
						if (c.playerLevel[2] < 140) {
							c.playerLevel[2] += 10;
						}
					}
					c.getPA().refreshSkill(0);
					c.getPA().refreshSkill(2);
					c.lastBeer = System.currentTimeMillis();
				} else {
					c.sendMessage("Not another one! Not so soon!");
					return;
				}
			}
		}
		
		if (itemId == 4033) {
			if (System.currentTimeMillis() - c.lastMonkey > 1200) {
				c.startAnimation(2780);
				c.lastMonkey = System.currentTimeMillis();
				c.forcedChat("Arrghhhhhhhhhhhhh!! Stupid Monkey!");
				c.sendMessage("The Monkey bites your finger off!");
			}
		}
	
		if (c.getHerblore().isUnidHerb(itemId))
			c.getHerblore().handleHerbClick(itemId);
		if (c.getFood().isFood(itemId))
			c.getFood().eat(itemId,itemSlot);
		//ScriptManager.callFunc("itemClick_"+itemId, c, itemId, itemSlot);
		if (c.getPotions().isPotion(itemId))
			c.getPotions().handlePotion(itemId,itemSlot);
		if (c.getPrayer().isBone(itemId))
			c.getPrayer().buryBone(itemId, itemSlot);
		if (itemId == 952) {
			if(c.inArea(3553, 3301, 3561, 3294)) {
				c.teleTimer = 3;
				c.newLocation = 1;
			} else if(c.inArea(3550, 3287, 3557, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 2;
			} else if(c.inArea(3561, 3292, 3568, 3285)) {
				c.teleTimer = 3;
				c.newLocation = 3;
			} else if(c.inArea(3570, 3302, 3579, 3293)) {
				c.teleTimer = 3;
				c.newLocation = 4;
			} else if(c.inArea(3571, 3285, 3582, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 5;
			} else if(c.inArea(3562, 3279, 3569, 3273)) {
				c.teleTimer = 3;
				c.newLocation = 6;
			}
		}
	}
}


