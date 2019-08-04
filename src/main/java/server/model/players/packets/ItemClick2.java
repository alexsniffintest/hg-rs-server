package server.model.players.packets;

import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;

import java.util.ArrayList;

/**
 * Item Click 2 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 * Proper Streams
 */

public class ItemClick2 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();
		
		if (!c.getItems().playerHasItem(itemId,1))
			return;

		switch (itemId) {
			
			case 4079:
				if (System.currentTimeMillis() - c.lastClick > 1000) {
					c.lastClick = System.currentTimeMillis();
					c.startAnimation(1459);
				}
			break;
			
			case 8408:
				if(c.myKit.equalsIgnoreCase("JUMPER") && c.inCwGame) {
					if(c.hasPlacedPortalDown) {
						c.sendMessage("You've already placed your portal down.");
						return;
					}
					if(c.getCombat().CheckForPortal(c.absX, c.absY)) {
						c.sendMessage("A portal has already been spawned here.");	
						return;
					}
					c.portalX = c.absX;
					c.portalY = c.absY;
					c.hasPlacedPortalDown = true;
					c.getCombat().spawnPortal(c.absX, c.absY, c.playerName);
					c.sendMessage("You place down your Jumper Portal.");
				}
			break;
			
			case 4002:
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
									int chances = Misc.random(10);
									if (chances <= 4) {
										c.dealDamage(50);
										c.handleHitMask(50);
										c.getPA().refreshSkill(3);
										c.sendMessage("The device backfires, dealing 50 damage!");
										c.sendMessage("The device has " + (c.taggedUses - 1) + " uses left.");
										c.startAnimation(1114);
										c.gfx0(542);
									} else if (chances > 4 && chances <= 6) {
										c.sendMessage("The device completly fails and destroys itself!");
										c.getItems().deleteItem(4002,1);
										c.startAnimation(860);
									} else {
										c.sendMessage("You use the Tagger device to copy @blu@" + c.taggedPlayer + "'s@bla@ equipment!");
										c.sendMessage("The device has " + (c.taggedUses - 1) + " uses left.");
										o.sendMessage("You feel a strange force pull from your equipment!");
										c.startAnimation(1972);
										int[]  arm = new int[14];
										for(int q = 0; q < o.playerEquipment.length; q++) {
											arm[q] = o.playerEquipment[q];
											c.playerEquipment[q] = o.playerEquipment[q];
										}
										for(int q = 0; q < arm.length; q++) {
											c.getItems().setEquipment(arm[q],1,q);
										}
										c.getCombat().getPlayerAnimIndex(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
										c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
									}
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
			break;
			
			case 11694:
				c.getItems().deleteItem(itemId,1);
				c.getItems().addItem(11690,1);
				c.getItems().addItem(11702,1);
				c.sendMessage("You dismantle the godsword blade from the hilt.");
			break;

			case 16717:
				c.getPA().movePlayer(2938, 4714, 0);
				c.sendMessage("You teleport to the staff zone");
			break;
				
			case 5863:
				c.getItems().wearItem(c.wearId, c.wearSlot);
			break;
				
			case 11696:
				c.getItems().deleteItem(itemId,1);
				c.getItems().addItem(11690,1);
				c.getItems().addItem(11704,1);
				c.sendMessage("You dismantle the godsword blade from the hilt.");
			break;
			case 11698:
				c.getItems().deleteItem(itemId,1);
				c.getItems().addItem(11690,1);
				c.getItems().addItem(11706,1);
				c.sendMessage("You dismantle the godsword blade from the hilt.");
			break;
			case 11700:
				c.getItems().deleteItem(itemId,1);
				c.getItems().addItem(11690,1);
				c.getItems().addItem(11708,1);
				c.sendMessage("You dismantle the godsword blade from the hilt.");
			break;
			
			default:
				if (c.playerRights == 3)
					Misc.println(c.playerName+ " - Item3rdOption: "+itemId);
			break;
		}
	}
}
