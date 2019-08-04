package server.model.players;

import server.Config;
import server.Server;

public class PlayerOnlineKicks implements Runnable  {

	@Override
	public void run() {
		while(true) {
			 try {
				 for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					 if(Server.playerHandler.players[i] != null) {
						 Client c = (Client) Server.playerHandler.players[i];
						 if(System.currentTimeMillis() - c.lastPacket > 10000 && !c.loggingIn) {
							 c.disconnected = true;
						 }
					 }
				 }
				  Thread.sleep(1000);
			 } catch(Exception e) {
				 e.printStackTrace();
			 }
		} 
	}
	
	

}
