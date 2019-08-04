package server.model.players.packets;

import server.Connection;
import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;

/**
 * Chat : Modified by Alex & mr king Gretar. 
 **/
public class Chat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.setChatTextEffects(c.getInStream().readUnsignedByteS());
		c.setChatTextColor(c.getInStream().readUnsignedByteS());
        c.setChatTextSize((byte)(c.packetSize - 2));
        c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
		
		if(System.currentTimeMillis() < c.muteEnd) {
		c.sendMessage("You are muted for certain amount of time.!");
			return;
		} else {
			c.muteEnd = 0;
		}
		
		String[] stuffz = {"full -1", "king pkz", ".webs.", "join www", "smffy.com", "kingpkz", ".netne", "join rsps", "allstarscape", "forumotion.com", "rswebclients.com", "scape.com", "scape.net", "scape.org", "no-ip."}; //add more!
		String term = Misc.textUnpack(c.getChatText(), c.packetSize - 2).toLowerCase();
		
		if(c.said >= 2){
			c.sendMessage("Don't even try :)");
			c.logout();
		}
		
		for(int i = 0; i < stuffz.length; i++) {
			if(term.contains(stuffz[i])) {
				c.said++;
				c.getDH().sendStatement("Please do not Advertise another websites/servers");
				return;
			}	
		}
		
		if(c.Warning >= 25 && c.Warning <= 32) { // Number of times you can spam a word before getting muted
			c.sendMessage("@red@ If you keep on spamming you will get muted!");
		} else if(c.Warning == 33) { // Number of times you can spam a word before getting muted
			c.sendMessage("@red@ You have been Auto-muted for spamming.");
			Connection.addNameToMuteList(c.playerName);
		}
		
		if(term.contains(""+c.saveText+"")) {
			c.Warning += 1;
		} else {
			c.Warning = 0;
		}
		
		c.saveText = term;
		if (!Connection.isMuted(c)) {
			c.setChatTextUpdateRequired(true);
		} else {
			c.sendMessage("You are currently muted.");
		}
	}
}