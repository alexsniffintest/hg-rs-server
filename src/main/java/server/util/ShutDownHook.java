package server.util;

import server.Server;
import server.model.players.Client;
import server.model.players.PlayerSave;

public class ShutDownHook extends Thread {

	@Override
	public void run() {
		System.out.println("Shutdown thread run.");
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				PlayerSave.sa1veGame(c);			
			}		
		}
		System.out.println("Shutting down...");
	}

}