package server.model.players.packets;

import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;


/**
 * Pickup Item
 **/
public class PickupItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.pItemY = c.getInStream().readSignedWordBigEndian();
		c.pItemId = c.getInStream().readUnsignedWord();
		c.pItemX = c.getInStream().readSignedWordBigEndian();
		
		if (c.spectate)
			return;
			
		if (c.randomMap == 0 && Server.HungerGames.currentPlayers.size() == 1) {
			return;
		} else if (c.randomMap == 1 && Server.HungerGamesFal.currentPlayers_Fal.size() == 1) {
			return;
		} else if (c.randomMap == 2 && Server.HungerGamesCan.currentPlayers_Can.size() == 1) {
			return;
		} else if (c.randomMap == 3 && Server.HungerGamesMisc.currentPlayers_Misc.size() == 1) {
			return;
		}
		
		if (Math.abs(c.getX() - c.pItemX) > 25 || Math.abs(c.getY() - c.pItemY) > 25) {
			c.resetWalkingQueue();
			return;
		}
		c.getCombat().resetPlayerAttack();
		if(c.getX() == c.pItemX && c.getY() == c.pItemY) {
			Server.itemHandler.removeGroundItem(c, c.pItemId, c.pItemX, c.pItemY, true);
		} else {
			if (c.freezeTimer > -6 && Math.abs(c.getX() - c.pItemX) <= 1 && Math.abs(c.getY() - c.pItemY) <= 1) {
				Server.itemHandler.removeGroundItem(c, c.pItemId, c.pItemX, c.pItemY, true);
			} else {
				c.walkingToItem = true;
			}
		}
	}
}
