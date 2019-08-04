package server.model.players.packets;

import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;
/**
 * Chat MADE BY KING GRRETAR
 **/
public class ClanChat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String textSent = Misc.longToPlayerName2(c.getInStream().readQWord());
		textSent = textSent.replaceAll("_", " ");
		//c.sendMessage(textSent);
		Server.clanChat.handleClanChat(c, textSent);
	}	
}
