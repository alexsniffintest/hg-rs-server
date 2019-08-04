/*package server.model.players.packets;

import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Follow Player
 **/
/*public class FollowPlayer implements PacketType {
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int followPlayer = c.getInStream().readUnsignedWordBigEndian();
		if(Server.playerHandler.players[followPlayer] == null) {
			return;
		}
		c.playerIndex = 0;
		c.npcIndex = 0;
		c.mageFollow = false;
		c.usingBow = false;
		c.usingRangeWeapon = false;
		c.followDistance = 1;
		c.followId = followPlayer;
	}	
}*/

package server.model.players.packets;
 
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
 
public class FollowPlayer implements PacketType {
       
        @Override
        public void processPacket(Client c, int packetType, int packetSize) {
                int followPlayer = c.getInStream().readUnsignedWordBigEndian();
				
                if(Server.playerHandler.players[followPlayer] == null) {
                        return;
                }
				
				if (c.isMorphed)
					return;
				
				if (server.model.minigames.HungerGames.countDownTimer > 0 && c.inCwGame && c.randomMap == 0)
					return;
				
				if (server.model.minigames.HungerGamesFal.countDownTimer > 0 && c.inCwGame && c.randomMap == 1)
					return;
				
				if (server.model.minigames.HungerGamesCan.countDownTimer > 0 && c.inCwGame && c.randomMap == 2)
					return;
					
				if (server.model.minigames.HungerGamesMisc.countDownTimer > 0 && c.inCwGame && c.randomMap == 3)
					return;
				
				if (c.spectate)
					return;
					
                c.playerIndex = 0;
                c.npcIndex = 0;
                c.mageFollow = false;
                c.usingBow = false;
                c.usingRangeWeapon = false;
                c.followDistance = 1;
                c.followId = followPlayer;
        }      
}