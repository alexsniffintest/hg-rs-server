package server.model.players.packets;

import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;


/**
 * Clicking stuff (interfaces)
 **/
public class ClickingStuff implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.inTrade) {
			if(!c.acceptedTrade) {
			Client o = (Client) Server.playerHandler.players[c.tradeWith];
			if(o.playerId == c.playerId || c.tradeWith != o.playerId || o.tradeWith != c.playerId || c.tradeScreen == 0 || c.tradeScreen == 0) {
			return;
		}
				c.getTradeAndDuel().declineTrade();
			}
		}

		Client o = (Client) Server.playerHandler.players[c.duelingWith];
		if(o != null) {
			if(o.playerId == c.playerId || c.duelingWith != o.playerId || o.duelingWith != c.playerId || c.duelScreen == 0 || c.duelScreen == 0) {
				return;
			}
			if(c.duelStatus >= 1 && c.duelStatus <= 4) {
				c.getTradeAndDuel().declineDuel();
				o.getTradeAndDuel().declineDuel();
			}
		}
		
		if(c.duelStatus == 6) {
			c.getTradeAndDuel().claimStakedItems();		
		}
	
	}
		
}
