package server.model.players.packets;

import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Walking packet
 **/
public class Walking implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {	
		
		if(c.inTrade && c.openTrade) {
			Client o = (Client) Server.playerHandler.players[c.tradeWith];
			if (System.currentTimeMillis() - c.lastWalk > 1000) {
				c.lastWalk = System.currentTimeMillis(); 
				c.getTradeAndDuel().declineTrade();
				o.getTradeAndDuel().declineTrade();
			} else {
				return;
			}
		}
		
		if (c.resetPass == 0) {
			c.sendMessage("@red@Please reset your password ::changepassword");
			return;
		}

		if (packetType == 98 && c.freezeTimer > -6) {
			c.sendMessage("A magical force stops you from moving.");
			return;
		}
		
		if (c.spectate)
			return;
		
		if (c.tutorial || c.isMorphed) //|| c.convert || c.shopOne
			return;
			
		if (c.inShop) {
			c.inShop = false;
		}
		
		c.getPA().removeAllWindows();
		c.isBanking = false;
			
		if (packetType == 248 || packetType == 164) {
			c.faceUpdate(0);
			c.npcIndex = 0;
			c.playerIndex = 0;
			if (c.followId > 0 || c.followId2 > 0 || c.follow2 > 0)
				c.getPA().resetFollow();
		} else {
			c.getPA().removeAllWindows();
		}
		
		if (c.dialogueAction > 0)
			c.dialogueAction = 0;
		
		if(c.freezeTimer > -6) {
			if (c.myKit.equalsIgnoreCase("FLASH") && c.playerEquipment[c.playerFeet] == 88 && System.currentTimeMillis() - c.lastFlash > 8000) {
				c.freezeTimer = 0;
			} else if (Server.playerHandler.players[c.playerIndex] != null) {
				if(c.goodDistance(c.getX(), c.getY(), Server.playerHandler.players[c.playerIndex].getX(), Server.playerHandler.players[c.playerIndex].getY(), 1) && packetType != 98) {
					c.playerIndex = 0;	
					return;
				}
			} else if (packetType != 98) {
				c.sendMessage("A magical force stops you from moving.");
				c.playerIndex = 0;
				return;
			}	
		}
		
		if (server.model.minigames.HungerGames.countDownTimer > 0 && c.inCwGame && c.randomMap == 0)
			return;
		
		if (server.model.minigames.HungerGamesFal.countDownTimer > 0 && c.inCwGame && c.randomMap == 1)
			return;
		
		if (server.model.minigames.HungerGamesCan.countDownTimer > 0 && c.inCwGame && c.randomMap == 2)
			return;
			
		if (server.model.minigames.HungerGamesMisc.countDownTimer > 0 && c.inCwGame && c.randomMap == 3)
			return;
				
		
		if (packetType == 98) {
			c.mageAllowed = true;
		}
		
		if(c.froze == 1) {
			c.sendMessage("You have been frozen");
			return;
		}

		if(c.respawnTimer > 3) {
			return;
		}

		if(packetType == 248) {
			packetSize -= 14;
		}
			c.newWalkCmdSteps = (packetSize - 5)/2;
			if(++c.newWalkCmdSteps > c.walkingQueueSize) {
				c.newWalkCmdSteps = 0;
			return;
		}
		
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		
		int firstStepX = c.getInStream().readSignedWordBigEndianA()-c.getMapRegionX()*8;
		for(int i = 1; i < c.newWalkCmdSteps; i++) {
			c.getNewWalkCmdX()[i] = c.getInStream().readSignedByte();
			c.getNewWalkCmdY()[i] = c.getInStream().readSignedByte();
		}
		
		int firstStepY = c.getInStream().readSignedWordBigEndian()-c.getMapRegionY()*8;
		c.setNewWalkCmdIsRunning(c.getInStream().readSignedByteC() == 1);
		for(int i1 = 0; i1 < c.newWalkCmdSteps; i1++) {
			c.getNewWalkCmdX()[i1] += firstStepX;
			c.getNewWalkCmdY()[i1] += firstStepY;
			
			int value = c.newWalkCmdSteps -1;
			
			/** Special walking processes **/
			if (i1 == value) {
				int x0 = c.getNewWalkCmdX()[i1] + c.getMapRegionX() * 8;
				int y0 = c.getNewWalkCmdY()[i1] + c.getMapRegionY() * 8;
				
				if (System.currentTimeMillis() - c.lastFlash > 8000) {
					if(c.myKit.equalsIgnoreCase("FLASH") && c.playerEquipment[c.playerFeet] == 88) {
						if (c.getX() == x0 && c.getY() == y0) {
							return;
						} else {
							c.sendMessage("You move at the speed of light!");
							c.lastFlash = System.currentTimeMillis();
							c.startAnimation(3170);
							c.getPA().movePlayer2(x0, y0, 0);
							
							c.getItems().removeItem(88, 10);
							return;
						}
					}
				}
				
				if (c.playerRights >= 2 && c.instantWalk) {
					c.startAnimation(3170);
					c.getPA().movePlayer2(x0, y0, 0);
					return;
				}
			}
		}
	}
}