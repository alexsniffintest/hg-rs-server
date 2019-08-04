package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Change appearance
 **/
public class ChangeAppearance implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int gender = c.getInStream().readSignedByte();
		int head = c.getInStream().readSignedByte();
		int jaw = c.getInStream().readSignedByte();
		int torso = c.getInStream().readSignedByte();
		int arms = c.getInStream().readSignedByte();
		int hands = c.getInStream().readSignedByte();
		int legs = c.getInStream().readSignedByte();
		int feet = c.getInStream().readSignedByte();
		int hairColour = c.getInStream().readSignedByte();
		int torsoColour = c.getInStream().readSignedByte();
		int legsColour = c.getInStream().readSignedByte();
		int feetColour = c.getInStream().readSignedByte();
		int skinColour = c.getInStream().readSignedByte();
		
		int[] maxLevel = { 
    2 , 55, 61,
    66, 69, 78, 
    81, 18, 12,
	16, 16 ,6 ,9
		};
		
		if (c.canChangeAppearance) { 
		
		
		if(gender < 0) {
			c.sendMessage("Error Message 0.1, Please report this to Gretar");
		return;
			}
			if(head < 0) {
			c.sendMessage("Error Message 1.2, Please report this to Gretar");
		return;
			}	
			if(torso  < 0) {
			c.sendMessage("Error Message 2.21, Please report this to Gretar");
		return;
			}
			if(arms  < 0) {
			c.sendMessage("Error Message 2.24, Please report this to Gretar");
		return;
			}
			if(hands  < 0) {
			c.sendMessage("Error Message 2.25, Please report this to Gretar");
		return;
			}
			if(legs  < 0) {
			c.sendMessage("Error Message 2.27, Please report this to Gretar");
		return;
			}
			if(feet  < 0) {
			c.sendMessage("Error Message 2.24223235, Please report this to Gretar");
		return;
			}
			if(hairColour  < 0) {
			c.sendMessage("Error Message 2.243435, Please report this to Gretar");
		return;
			}
			if(torsoColour  < 0) {
			c.sendMessage("Error Message 2.244445, Please report this to Gretar");
		return;
			}
			if(skinColour  < 0) {
			c.sendMessage("Error Message 2.24343445, Please report this to Gretar");
		return;
			}
			if(jaw  < 0 && gender > 2) {
			c.sendMessage("Error Message 2.254, Please report this to Gretar");
		return;
			}

		
		if(gender >= maxLevel[0]) {
			c.sendMessage("Error Message 0, Please report this to Gretar");
		return;
			}
			if(head >= maxLevel[1]) {
			c.sendMessage("Error Message 1, Please report this to Gretar");
		return;
			}	
			if(torso >= maxLevel[2]) {
			c.sendMessage("Error Message 2, Please report this to Gretar");
		return;
			}
			if(arms >= maxLevel[3]) {
			c.sendMessage("Error Message 3, Please report this to Gretar");
		return;
			}
			if(hands >= maxLevel[4]) {
			c.sendMessage("Error Message 4, Please report this to Gretar");
		return;
			}
			if(legs >= maxLevel[5]) {
			c.sendMessage("Error Message 5, Please report this to Gretar");
		return;
			}
			if(feet >= maxLevel[6]) {
			c.sendMessage("Error Message 6, Please report this to Gretar");
		return;
			}
			if(jaw >= maxLevel[7]) { 
			c.sendMessage("Error Message 7, Please report this to Gretar");
		return;
			}
			if(hairColour >= maxLevel[8]) {
			c.sendMessage("Error Message 8, Please report this to Gretar");
		return;
			}
			if(torsoColour >= maxLevel[9]) {
			c.sendMessage("Error Message 9, Please report this to Gretar");
		return;
			}
			if(legsColour >= maxLevel[10]) {
			c.sendMessage("Error Message 10, Please report this to Gretar");
		return;
			}
			if(feetColour >= maxLevel[11]) {
			c.sendMessage("Error Message 11, Please report this to Gretar");
		return;
			}
			if(skinColour >= maxLevel[12]) {
		c.sendMessage("Error Message 12, Please report this to Gretar");
		return;
		}
			c.playerAppearance[0] = gender; // gender
			c.playerAppearance[1] = head; // head
			c.playerAppearance[2] = torso;// Torso
			c.playerAppearance[3] = arms; // arms
			c.playerAppearance[4] = hands; // hands
			c.playerAppearance[5] = legs; // legs
			c.playerAppearance[6] = feet; // feet
			c.playerAppearance[7] = jaw; // beard
			c.playerAppearance[8] = hairColour; // hair colour
			c.playerAppearance[9] = torsoColour; // torso colour
			c.playerAppearance[10] = legsColour; // legs colour
			c.playerAppearance[11] = feetColour; // feet colour
			c.playerAppearance[12] = skinColour; // skin colour
			
			

			c.getPA().removeAllWindows();
			c.getPA().requestUpdates();
			c.canChangeAppearance = false;
			if (c.achievements[17][0] == 0) {
				c.achievements[17][1] = 1;
				c.achievementsHandler();
			}
		}	
	}	
}
