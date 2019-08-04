package server.model.players.packets;

import server.Config;
import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Trading
 */
public class Trade implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int tradeId = c.getInStream().readSignedWordBigEndian();
		c.getPA().resetFollow();
		
		if(c.arenas()) {
			c.sendMessage("You can't trade inside the arena!");
			return;
		}

		if(c.spectate) {
			c.sendMessage("You can't trade while in spectate!");
			return;
		}
		
		if (c.noSmuggleTwo >= 0) {
			return;
		}
		
		if(c.playerRights == 2 && !Config.ADMIN_CAN_TRADE) {
			c.sendMessage("Trading as an admin has been disabled.");
			return;
		}
		if(c.inWild() || c.inPkingZone) {  
			c.sendMessage("You can't trade at Edgeville, please go home first!");
			return;
		}
		if (tradeId != c.playerId)
			c.getTradeAndDuel().requestTrade(tradeId);
	}
		
}
