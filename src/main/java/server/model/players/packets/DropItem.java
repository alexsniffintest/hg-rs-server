package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.npcs.PetHandler;
import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Drop Item
 **/
public class DropItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();

		if (c.noSmuggleTwo >= 0)
			return;
			
		if (c.tutorial || c.spectate)
			return;
		
		if (c.inHomeArea() && c.inCwGame)
			return;
			
		if (c.inCwWait)
			return;
		
		if(c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}
		
		if(PetHandler.spawnPet(c, itemId, slot, false)) {
			return;
		}
		boolean droppable = true;
		for (int i : Config.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
				droppable = false;
				break;
			}
		}

		if(c.inTrade) {
			c.sendMessage("You can't drop items while trading!");
			return;
		}
		
		if (c.isDead) {
			c.sendMessage("You can't drop items if you are dead!");
			return;
		}

		if (c.underAttackBy > 0 && c.inPkingZone) {
			c.sendMessage("You can't drop while being in combat!");
			return;
		}
			
		if(c.playerItemsN[slot] != 0 && itemId != -1 && c.playerItems[slot] == itemId + 1) {
			if(droppable) {

				Server.itemHandler.createGroundItem(c, itemId, c.getX(), c.getY(), c.playerItemsN[slot], c.getId());
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
			} else {
				c.sendMessage("This items cannot be dropped.");
			}
		}

	}
}