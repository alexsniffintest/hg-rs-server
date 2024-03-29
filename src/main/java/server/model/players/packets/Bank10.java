package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
/**
 * Bank 10 Items
 **/
public class Bank10 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
	int interfaceId = c.getInStream().readUnsignedWordBigEndian();
	int removeId = c.getInStream().readUnsignedWordA();
	int removeSlot = c.getInStream().readUnsignedWordA();
					
		switch(interfaceId){
			case 1688:
				c.getPA().useOperate(removeId);
			break;
			case 3900:
			if (c.myShopId == 30 && c.memberStatus < 1) {
				c.sendMessage("You must be a donator to buy from this shop.");
				return;
			}
			c.getShops().buyItem(removeId, removeSlot, 5);
			break;
			
			case 3823:
				if (c.inShop) {
					c.getShops().sellItem(removeId, removeSlot, 5);
				} else {
					c.sendMessage("Error15, report this to Peeta");
				}
			break;	

			case 5064:
			if(!c.getItems().playerHasItem(removeId, removeSlot)) {
				return;
			}
			if (c.inCwGame || c.inCwWait) {
				c.sendMessage("Error19, report this to Peeta.");
				return;
			}
			c.getItems().bankItem(removeId, removeSlot, 10);
			break;
			
			case 5382:
			if (c.inCwGame || c.inCwWait) {
				c.sendMessage("Error19, report this to Peeta.");
				return;
			}
			c.getItems().fromBank(removeId, removeSlot, 10);
			break;
			
			case 3322:
			if(c.duelStatus <= 0) { 
			if(!c.getItems().playerHasItem(removeId, removeSlot)) {
				return;
			}
                c.getTradeAndDuel().tradeItem(removeId, removeSlot, 10);
           	} else {
				c.getTradeAndDuel().stakeItem(removeId, removeSlot, 10);
			}	
			break;
			
			case 3415:
			
			if(c.duelStatus <= 0) { 
				c.getTradeAndDuel().fromTrade(removeId, removeSlot, 10);
           	} 
			break;
			
			case 6669:
			c.getTradeAndDuel().fromDuel(removeId, removeSlot, 10);
			break;
			
			case 1119:
			case 1120:
			case 1121:
			case 1122:
			case 1123:
				c.getSmithing().readInput(c.playerLevel[c.playerSmithing], Integer.toString(removeId), c, 5);
			break;
		}	
	}

}
