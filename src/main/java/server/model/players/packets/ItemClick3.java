package server.model.players.packets;

import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;

/**
 * Item Click 3 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 * Proper Streams
 */

public class ItemClick3 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId11 = c.getInStream().readSignedWordBigEndianA();
		int itemId1 = c.getInStream().readSignedWordA();
		int itemId = c.getInStream().readSignedWordA();
		if (c.usingCarpet) {
			return;
		}
		switch (itemId) {
		
		case 16717:
		c.getPA().movePlayer(2599, 4774, 0);
				c.sendMessage("You teleport to Portalszone");
		break;
		
		case 2552:
			c.getPA().handleROD(itemId);
		break;
		
		case 4079:
			if (System.currentTimeMillis() - c.lastClick > 1000) {
				c.lastClick = System.currentTimeMillis();
				c.startAnimation(1460);
			}
		break;
			
		case 1712:
			c.getPA().handleGlory(itemId);
		break;
		
		case 5863:
				c.getItems().wearItem(c.wearId, c.wearSlot);
		break;
		
		case 3853:
			c.getPA().handleGamesNeck(itemId);
		break; 
		
		case 3867:
			if (c.randomMap == 3) {
				if(System.currentTimeMillis() - c.logoutDelay > 5000) {
					if (Server.HungerGamesMisc.timeRemaining <= 21 && Server.HungerGamesMisc.timeRemaining >= -1) {
						c.sendMessage("Sorry but the DM is about to start within the next 20 seconds!");
						return;
					}
					if (c.inCwGame && !c.startDeathBattleMisc) {
						c.sendMessage("You rub the necklace and it teleports you to an unknown location!");
						c.sendMessage("You have 20 seconds before the magic affect ends!");
						c.chestRoomTimer = 20;
						c.getItems().deleteItem(3867, 1);
						c.getPA().startTeleport(3551, 9692, 0, "modern");
						c.hiddenRoom = true;
						if (c.achievements[16][0] == 0) {
							c.achievements[16][1] = 1;
							c.achievementsHandler();
						}
					} else {
						c.sendMessage("You can't use this in the Death Match");
					}
				} else {
					c.sendMessage("You can't use this in combat!");
				}
			}
		break;

		default:
			if (c.playerRights == 3)
				Misc.println(c.playerName+ " - Item3rdOption: "+itemId+" : "+itemId11+" : "+itemId1);
			break;
		}

	}

}
