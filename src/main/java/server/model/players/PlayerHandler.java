package server.model.players;

import server.Config;
import server.Server;
import server.model.npcs.NPCHandler;
import server.util.Stream;

import java.net.InetSocketAddress;

;
public class PlayerHandler{

	
	
	public static Player players[] = new Player[Config.MAX_PLAYERS];
	public static String messageToAll = "";
	public static int playerCount = 0;
	public static String playersCurrentlyOn[] = new String[Config.MAX_PLAYERS];
	public static boolean updateAnnounced;
	public static boolean updateRunning;
	public static int updateSeconds;
	public static long updateStartTime;
	private boolean kickAllPlayers = false;
	
	static {
		for(int i = 0; i < Config.MAX_PLAYERS; i++)
			players[i] = null;
	}

	private int playerSlotSearchStart = 1;
	
	public synchronized boolean newPlayerClient(final Client newClient) {
		if (newClient == null)
			return false;	
		int slot = getFreeSlot();
		newClient.playerId = slot;
		if (players[slot] == null) {
			players[slot] = newClient;
			players[slot].connectedFrom = ((InetSocketAddress) newClient.getSession().getRemoteAddress()).getAddress().getHostAddress();
		} else {
			newPlayerClient(newClient);
			return false;
		}
		newClient.handler = this;
		playerSlotSearchStart = slot + 1;
		if (playerSlotSearchStart > 2048)
			playerSlotSearchStart = 1;

		newClient.isActive = true;
		return true;
	}
	
	public int getFreeSlot()
	{
		for (int i = 1; i < 2048; i++)
		{
			if (players[i] == null)
			{
				
				return i;
			}
		}
		return -1;
	}

	public void destruct() {
		for(int i = 0; i < Config.MAX_PLAYERS; i++) {
			if(players[i] == null) 
				continue;
			players[i].destruct();
			players[i] = null;
		}
	}

	public static int getPlayerCount() {
		return playerCount;
	}

	public void updatePlayerNames() {
		playerCount = 0;
		for(int i = 0; i < Config.MAX_PLAYERS; i++) {
			if(players[i] != null) {
				playersCurrentlyOn[i] = players[i].playerName;
				playerCount++;
			} else {
				playersCurrentlyOn[i] = "";
			}
		}
	}

	public static boolean isPlayerOn(String playerName) {
		synchronized (PlayerHandler.players) {
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(players[i] != null) {
					Client c = (Client) players[i];
					if(c.playerName.equalsIgnoreCase(playerName)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public void process() {
		synchronized (PlayerHandler.players) {
		
			updatePlayerNames();
			
			if(kickAllPlayers) {
				for(int i = 1; i < Config.MAX_PLAYERS; i++) {
					if(players[i] != null) {
						players[i].disconnected = true;
						System.out.println("PLAYER REMOVED3");
					}
				}
			}
	
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(players[i] == null || !players[i].isActive || players[i].loggingIn) continue;
				try {					
					
					if(players[i].disconnected && (System.currentTimeMillis() - players[i].logoutDelay > 10000 || players[i].properLogout || kickAllPlayers)) {
						if(players[i].inTrade) {
							Client o = (Client) Server.playerHandler.players[players[i].tradeWith];
							if(o != null) {
								o.getTradeAndDuel().declineTrade();
							}
						}
						if(players[i].duelStatus == 5) {
							Client o = (Client) Server.playerHandler.players[players[i].duelingWith];
							if(o != null) {
								o.getTradeAndDuel().duelVictory();
							}
						} else if (players[i].duelStatus <= 4 && players[i].duelStatus >= 1) {
							Client o = (Client) Server.playerHandler.players[players[i].duelingWith];
							if(o != null) {
								o.getTradeAndDuel().declineDuel();
							}
						}
						Client o = (Client) Server.playerHandler.players[i];
						removePlayer(players[i]);
						players[i] = null;
						System.out.println("PLAYER REMOVED2");
						continue;
					}
					
					if(players[i].disconnected && players[i].underAttackBy < 1 && players[i].underAttackBy2 < 1 || kickAllPlayers) {
						Client o = (Client) Server.playerHandler.players[i];
						removePlayer(players[i]);
						players[i] = null;
						continue;
					}
					
					players[i].preProcessing();			
					while(players[i].processQueuedPackets());
					players[i].process();
					players[i].postProcessing();
					players[i].getNextPlayerMovement();
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
	
			
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(players[i] == null || !players[i].isActive || players[i].loggingIn) continue;
				try {
					if(players[i].disconnected && (System.currentTimeMillis() - players[i].logoutDelay > 10000 || players[i].properLogout || kickAllPlayers)) {
						if(players[i].inTrade) {
							Client o = (Client) Server.playerHandler.players[players[i].tradeWith];
							if(o != null) {
								o.getTradeAndDuel().declineTrade();
							}
						}
						if(players[i].duelStatus == 5) {
							Client o1 = (Client) Server.playerHandler.players[players[i].duelingWith];
							if(o1 != null) {
								o1.getTradeAndDuel().duelVictory();
							}
						} else if (players[i].duelStatus <= 4 && players[i].duelStatus >= 1) {
							Client o1 = (Client) Server.playerHandler.players[players[i].duelingWith];
							if(o1 != null) {
								o1.getTradeAndDuel().declineDuel();
							}
						}
						
						Client o1 = (Client) Server.playerHandler.players[i];
						removePlayer(players[i]);
						System.out.println("PLAYER REMOVED");
						players[i] = null;
					} else {
						Client o = (Client) Server.playerHandler.players[i];
						//if(o.g) {
							if(!players[i].initialized) {
								players[i].initialize();
								players[i].initialized = true;
							}
							else {
								players[i].update();
							}
						//}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			if(updateRunning && !updateAnnounced) {
				updateAnnounced = true;
				Server.UpdateServer = true;
			}
			if(updateRunning && (System.currentTimeMillis() - updateStartTime > (updateSeconds*1000))) {
				kickAllPlayers  = true;
			}
			
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(players[i] == null || !players[i].isActive) continue;
				try {
					players[i].clearUpdateFlags();
				} catch(Exception e) {
					e.printStackTrace();
				}	
			}
		}
	}
	
	public void updateNPC(Player plr, Stream str) {
		synchronized(plr) {
			updateBlock.currentOffset = 0;
			
			str.createFrameVarSizeWord(65);
			str.initBitAccess();
			
			str.writeBits(8, plr.npcListSize);
			int size = plr.npcListSize;
			plr.npcListSize = 0;
			for(int i = 0; i < size; i++) {
				if(plr.RebuildNPCList == false && plr.withinDistance(plr.npcList[i]) == true) {
					plr.npcList[i].updateNPCMovement(str);
					plr.npcList[i].appendNPCUpdateBlock(updateBlock);
					plr.npcList[plr.npcListSize++] = plr.npcList[i];
				} else {
					int id = plr.npcList[i].npcId;
					plr.npcInListBitmap[id>>3] &= ~(1 << (id&7));		
					str.writeBits(1, 1);
					str.writeBits(2, 3);		
				}
			}

			
			for(int i = 0; i < NPCHandler.maxNPCs; i++) {
				if(Server.npcHandler.npcs[i] != null) {
					int id = Server.npcHandler.npcs[i].npcId;
					if (plr.RebuildNPCList == false && (plr.npcInListBitmap[id>>3]&(1 << (id&7))) != 0) {
						
					} else if (plr.withinDistance(Server.npcHandler.npcs[i]) == false) {
						
					} else {
						plr.addNewNPC(Server.npcHandler.npcs[i], str, updateBlock);
					}
				}
			}
			
			plr.RebuildNPCList = false;

			if(updateBlock.currentOffset > 0) {
				str.writeBits(14, 16383);	
				str.finishBitAccess();
				str.writeBytes(updateBlock.buffer, updateBlock.currentOffset, 0);
			} else {
				str.finishBitAccess();
			}
			str.endFrameVarSizeWord();
		}
	}

	private Stream updateBlock = new Stream(new byte[Config.BUFFER_SIZE]);
	
	public void updatePlayer(Player plr, Stream str) {
		synchronized(plr) {
			updateBlock.currentOffset = 0;
			if(updateRunning && !updateAnnounced) {
				str.createFrame(114);
				str.writeWordBigEndian(updateSeconds*50/30);
			}
			plr.updateThisPlayerMovement(str);		
			boolean saveChatTextUpdate = plr.isChatTextUpdateRequired();
			plr.setChatTextUpdateRequired(false);
			plr.appendPlayerUpdateBlock(updateBlock);
			plr.setChatTextUpdateRequired(saveChatTextUpdate);
			str.writeBits(8, plr.playerListSize);
			int size = plr.playerListSize;
			if (size > 255)
				size = 255;
			plr.playerListSize = 0;	
			for(int i = 0; i < size; i++) {			
				if(!plr.didTeleport && !plr.playerList[i].didTeleport && plr.withinDistance(plr.playerList[i])) {
					plr.playerList[i].updatePlayerMovement(str);
					plr.playerList[i].appendPlayerUpdateBlock(updateBlock);
					plr.playerList[plr.playerListSize++] = plr.playerList[i];
				} else {
					int id = plr.playerList[i].playerId;
					plr.playerInListBitmap[id>>3] &= ~(1 << (id&7));
					str.writeBits(1, 1);
					str.writeBits(2, 3);
				}
			}
		
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(players[i] == null || !players[i].isActive || players[i] == plr)
					continue;
				int id = players[i].playerId;
				if((plr.playerInListBitmap[id>>3]&(1 << (id&7))) != 0)
					continue;	
				if(!plr.withinDistance(players[i])) 
					continue;		
				plr.addNewPlayer(players[i], str, updateBlock);
			}
	
			if(updateBlock.currentOffset > 0) {
				str.writeBits(11, 2047);	
				str.finishBitAccess();				
				str.writeBytes(updateBlock.buffer, updateBlock.currentOffset, 0);
			}
			else str.finishBitAccess();
	
			str.endFrameVarSizeWord();
		}
	}

	private void removePlayer(Player plr) {
		if(plr.privateChat != 2) { 
			for(int i = 1; i < Config.MAX_PLAYERS; i++) {
				if (players[i] == null || players[i].isActive == false) continue;
				Client o = (Client)Server.playerHandler.players[i];
				if(o != null) {
					o.getPA().updatePM(plr.playerId, 0);
				}
			}
		}
		PlayerSave.sa1veGame((Client)plr);
		plr.destruct();
	}
	
}
