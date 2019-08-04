package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Entering an X amount of items to be banked, traded, or duelled.
 */

public class BankX2 implements PacketType {
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int amount = c.getInStream().readDWord();
		if (amount == 0) {
			c.sendMessage("Invalid amount, please enter a value greater than 0.");
			return;
		}
		//Did the player recieve the input gambling boolean?
		if (c.gambleValue) {
			//Is the player null?
			if (c.playerName.equals(null)) {
				c.sendMessage("error_10 - Tell Peeta this");
				return;
			}
			//Is the player trying to input during a already started gamble?
			if (c.gambleOn || c.gambleFinal || c.startRollin) {
				c.getDH().sendDialogues(53, 372);
				c.firstGamble = false;
				c.gambleValue = false;
				return;
			}
			//Does the player not have enough exp?
			if (c.totalHungerGameExp < 10000) {
				c.firstGamble = false;
				c.gambleValue = false;
				return;
			}
			//Is the input within range?
			if (amount > 10000000 || amount < 10000) {
				c.getDH().sendDialogues(52, 372);
				c.firstGamble = false;
				c.gambleValue = false;
				return;
			}
			//Does the player not have enough exp compared to their input?
			if (c.totalHungerGameExp < amount) {
				c.getDH().sendDialogues(63, 372);
				c.firstGamble = false;
				c.gambleValue = false;
				return;
			}
			//Is this the first gambler?
			if (c.firstGamble) {
				//Double check if no one else started before
				if (!c.gambleOn) {
					c.gamblerName = c.playerName;
					c.firstGamblerAmount = amount;
					c.totalHungerGameExp -= amount;
					c.gamblePot += amount;
					c.myBet = amount;
					c.reloadHGstuff();
					c.getDH().sendDialogues(54, 372);
					return;
				//Looks like the player was beat
				} else if (c.gambleOn || c.gambleFinal || c.startRollin) { 
					c.getDH().sendDialogues(53, 372);
					c.firstGamble = false;
					c.gambleValue = false;
					return;
				}
			}
			return;
		}
		switch (c.xInterfaceId) {
			case 5064: 
			if(c.inTrade) {
				c.sendMessage("You can't store items while trading!");
				return;
			}
			if (c.inCwGame || c.inCwWait) {
				c.sendMessage("Error19, report this to Peeta.");
				return;
			}
			c.getItems().bankItem(c.playerItems[c.xRemoveSlot] , c.xRemoveSlot, amount > c.getItems().getItemAmount(c.xRemoveId) ? c.getItems().getItemAmount(c.xRemoveId) : amount); 
			break;
				
			case 5382: 
			if (c.inCwGame || c.inCwWait) {
				c.sendMessage("Error19, report this to Peeta.");
				return;
			}
			c.getItems().fromBank(c.bankItems[c.xRemoveSlot] , c.xRemoveSlot, amount > c.getItems().getBankAmount(c.xRemoveId) ? c.getItems().getBankAmount(c.xRemoveId) : amount); break;
				
			case 3322:
			if(!c.getItems().playerHasItem(c.xRemoveId, amount, c.xRemoveSlot))
		return;
				if (c.duelStatus <= 0) {
					c.getTradeAndDuel().tradeItem(c.xRemoveId, c.xRemoveSlot, amount > c.getItems().getItemAmount(c.xRemoveId) ? c.getItems().getItemAmount(c.xRemoveId) : amount);
				} else {				
					c.getTradeAndDuel().stakeItem(c.xRemoveId, c.xRemoveSlot, amount > c.getItems().getItemAmount(c.xRemoveId) ? c.getItems().getItemAmount(c.xRemoveId) : amount);
				}
				break;
				
			case 3415:
				if(!c.getItems().playerHasItem(c.xRemoveId, amount))
		return;
				if (c.duelStatus <= 0) { 
					//c.getTradeAndDuel().fromTrade(c.xRemoveId, c.xRemoveSlot, amount > c.getItems().getItemAmount(c.xRemoveId) ? c.getItems().getItemAmount(c.xRemoveId) : amount);
				} 
				break;
				
			case 6669: 
			if(!c.getItems().playerHasItem(c.xRemoveId, amount, c.xRemoveSlot))
		return;
			c.getTradeAndDuel().fromDuel(c.xRemoveId, c.xRemoveSlot, amount > c.getItems().getItemAmount(c.xRemoveId) ? c.getItems().getItemAmount(c.xRemoveId) : amount); break;			
		}
	}
}