package server.model.players;

import org.apache.mina.common.IoSession;
import server.Config;
import server.Connection;
import server.Server;
import server.model.items.Item;
import server.model.npcs.NPCHandler;
import server.util.Misc;

public class PlayerAssistant{

	private Client c;
	private IoSession session;
	public PlayerAssistant(Client Client) {
		this.c = Client;
	}
	public int CraftInt, Dcolor, FletchInt;
	
	/**
	 * MulitCombat icon
	 * @param i1 0 = off 1 = on
	 */
	public void multiWay(int i1) {
		synchronized(c) {
			c.outStream.createFrame(61);
			c.outStream.writeByte(i1);
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		}
	}
	
	
	public void clearClanChat() {
		c.clanId = -1;
		c.getPA().sendFrame126("Talking in: ", 18139);
		c.getPA().sendFrame126("Owner: ", 18140);
		for (int j = 18144; j < 18244; j++)
			c.getPA().sendFrame126("", j);
	}
		public boolean wearingCape(int cape) {
		int capes[] = {
			9747, 9748, 9750, 9751, 
			9753, 9754, 9756, 9757, 
			9759, 9760, 9762, 9763,
			9765, 9766, 9768, 9769,
			9771, 9772, 9774, 9775,
			9777, 9778, 9780, 9781,
			9783, 9784, 9786, 9787,
			9789, 9790, 9792, 9793,
			9795, 9796, 9798, 9799,
			9801, 9802, 9804, 9805,
			9807, 9808, 9810, 9811,
			9813,15319
		};
		for(int i = 0; i < capes.length; i++) {
			if(capes[i] == cape) {
				return true;
			}
		}
		return false;
	}
	public int backupItems[] = new int[Config.BANK_SIZE];
	public int backupItemsN[] = new int[Config.BANK_SIZE];
	
	public int backupInvItems[] = new int[28];
	public int backupInvItemsN[] = new int[28];

	public void otherInv(Client c, Client o) {
		if(o == c || o == null || c == null) 
			return;
		for (int i = 0; i < o.playerItems.length; i++) {
			backupInvItems[i] = c.playerItems[i]; c.playerItemsN[i] = c.playerItemsN[i];
			c.playerItemsN[i] = o.playerItemsN[i]; c.playerItems[i] = o.playerItems[i];
		}
		c.getItems().updateInventory();
		
		for (int i = 0; i < o.playerItems.length; i++) {
		c.playerItemsN[i] = backupInvItemsN[i]; c.playerItems[i] = backupInvItems[i];
		}
	}
	

	public void otherBank(Client c, Client o) {
		if(o == c || o == null || c == null)
		{
		return;
		}

		for (int i = 0; i < o.bankItems.length; i++)
		{
			backupItems[i] = c.bankItems[i]; backupItemsN[i] = c.bankItemsN[i];
			c.bankItemsN[i] = o.bankItemsN[i]; c.bankItems[i] = o.bankItems[i];
		}
			openUpBank();

		for (int i = 0; i < o.bankItems.length; i++)
		{
		c.bankItemsN[i] = backupItemsN[i]; c.bankItems[i] = backupItems[i];
		}
	}
	
	  public void playerWalk(int x, int y) {
          PathFinder.getPathFinder().findRoute(c, x, y, true, 1, 1);
  }
	public static final String RULES [] ={
		"Welcome to *Server Name*",
		"1. No flaming over yell.",
		"2. No excessive Flaming/Trolling/Bullying.",
		"3. No excessive Disrespecting Staff.",
		"4. No spamming.",
		"5. No real world trading.",
		"6. No X-Logging purposely during a fight.",
		"7. No Multi-Logging.",
		"8. No Scamming/Attempting to Scam.",
		"9. No Luring against Mods/Admins.",
		"10. No Bug Abusing.",
		"11. No Botting/Cheating/Macroing.",
		"12. No Asking For Staff.",
		"13. No PK farming.",
		"14. No Discrimination.",
		"16. No Staff Impersonation.",
		"17. No DDoSing.",
		"18. No duping nor possession of duped item.",
		"19. No ban evading.",
		"20. No threatening the wellbeing of the server.",
		"21. No advertising other servers/market links etc."
	};
	
	public void resetAutocast() {
		c.autocastId = 0;
		c.autocasting = false;
		c.getPA().sendFrame36(108, 0);
	}
	
	
	
	public void sendFrame126(String s, int id) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null ) {
				c.getOutStream().createFrameVarSizeWord(126);
				c.getOutStream().writeString(s);
				c.getOutStream().writeWordA(id);
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}
		}
	}
	
	
	public void sendLink(String s) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null ) {
				c.getOutStream().createFrameVarSizeWord(187);
				c.getOutStream().writeString(s);
			}
		}	
	}
	
	public void setSkillLevel(int skillNum, int currentLevel, int XP) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(134);
				c.getOutStream().writeByte(skillNum);
				c.getOutStream().writeDWord_v1(XP);
				c.getOutStream().writeByte(currentLevel);
				c.flushOutStream();
			}
		}
	}
	
	public void sendFrame106(int sideIcon) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(106);
				c.getOutStream().writeByteC(sideIcon);
				c.flushOutStream();
				requestUpdates();
			}
		}
	}
	
	public void sendFrame107() {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(107);
				c.flushOutStream();
			}
		}
	}
	public void sendFrame36(int id, int state) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(36);
				c.getOutStream().writeWordBigEndian(id);
				c.getOutStream().writeByte(state);
				c.flushOutStream();
			}
		}
	}
	
	public void sendFrame185(int Frame) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(185);
				c.getOutStream().writeWordBigEndianA(Frame);
			}
		}
	}
	
	public void showInterface(int interfaceid) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(97);
				c.getOutStream().writeWord(interfaceid);
				c.flushOutStream();
			}
		}
	}
	
	public void sendFrame248(int MainFrame, int SubFrame) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(248);
				c.getOutStream().writeWordA(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.flushOutStream();
			}
		}
	}
	
	public void sendFrame246(int MainFrame, int SubFrame, int SubFrame2) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(246);
				c.getOutStream().writeWordBigEndian(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.getOutStream().writeWord(SubFrame2);
				c.flushOutStream();
			}
		}
	}
	
	public void sendFrame171(int MainFrame, int SubFrame) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(171);
				c.getOutStream().writeByte(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.flushOutStream();
			}
		}
	}
	
	public void sendFrame200(int MainFrame, int SubFrame) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(200);
				c.getOutStream().writeWord(MainFrame);
				c.getOutStream().writeWord(SubFrame);
				c.flushOutStream();
			}
		}
	}
	public void handleEmpty() {
c.getDH().sendOption2("Yes, Empty my inventory please.", "No, don't empty my inventory.");
c.dialogueAction = 850;        
}
	public void sendFrame70(int i, int o, int id) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(70);
				c.getOutStream().writeWord(i);
				c.getOutStream().writeWordBigEndian(o);
				c.getOutStream().writeWordBigEndian(id);
				c.flushOutStream();
			}
		}
	}

	public void sendFrame75(int MainFrame, int SubFrame) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(75);
				c.getOutStream().writeWordBigEndianA(MainFrame);
				c.getOutStream().writeWordBigEndianA(SubFrame);
				c.flushOutStream();
			}
		}
	}
	
	public void sendFrame164(int Frame) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(164);
				c.getOutStream().writeWordBigEndian_dup(Frame);
				c.flushOutStream();
			}
		}
	}
	
	public void setPrivateMessaging(int i) { // friends and ignore list status
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
		        c.getOutStream().createFrame(221);
		        c.getOutStream().writeByte(i);
				c.flushOutStream();
			}
		}
    }
	
	public void setChatOptions(int publicChat, int privateChat, int tradeBlock) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(206);
				c.getOutStream().writeByte(publicChat);	
				c.getOutStream().writeByte(privateChat);	
				c.getOutStream().writeByte(tradeBlock);
				c.flushOutStream();
			}
		}
	}
	
	public void sendFrame87(int id, int state) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(87);
				c.getOutStream().writeWordBigEndian_dup(id);	
				c.getOutStream().writeDWord_v1(state);
				c.flushOutStream();
			}
		}
	}
	
	public void sendPM(long name, int rights, byte[] chatmessage, int messagesize) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSize(196);
				c.getOutStream().writeQWord(name);
				c.getOutStream().writeDWord(c.lastChatId++);
				c.getOutStream().writeByte(rights);
				c.getOutStream().writeBytes(chatmessage, messagesize, 0);
				c.getOutStream().endFrameVarSize();
				c.flushOutStream();
				String chatmessagegot = Misc.textUnpack(chatmessage, messagesize);
				String target = Misc.longToPlayerName(name);
			}	
		}
	}
	
	public void createPlayerHints(int type, int id) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(254);
				c.getOutStream().writeByte(type);
				c.getOutStream().writeWord(id); 
				c.getOutStream().write3Byte(0);
				c.flushOutStream();
			}
		}
	}

	public void createObjectHints(int x, int y, int height, int pos) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(254);
				c.getOutStream().writeByte(pos);
				c.getOutStream().writeWord(x);
				c.getOutStream().writeWord(y);
				c.getOutStream().writeByte(height);
				c.flushOutStream();
			}
		}
	}
	
	public void loadPM(long playerName, int world) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				if(world != 0) {
		            world += 9;
				} else if(!Config.WORLD_LIST_FIX) {
					world += 1;
				}	
				c.getOutStream().createFrame(50);
				c.getOutStream().writeQWord(playerName);
				c.getOutStream().writeByte(world);
				c.flushOutStream();
			}
		}
	}
	public void removeAllItems() {
		for (int i = 0; i < c.playerItems.length; i++) {
			c.playerItems[i] = 0;
		}
		for (int i = 0; i < c.playerItemsN.length; i++) {
			c.playerItemsN[i] = 0;
		}
		c.getItems().resetItems(3214);
	}
	public void removeAllWindows() {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getPA().resetVariables();
				c.getOutStream().createFrame(219);
				c.flushOutStream();
			}
		}
	}
	
	public void closeAllWindows() {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(219);
				c.flushOutStream();
			}
		}
	}
	
	public void sendFrame34(int id, int slot, int column, int amount) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.outStream.createFrameVarSizeWord(34); // init item to smith screen
				c.outStream.writeWord(column); // Column Across Smith Screen
				c.outStream.writeByte(4); // Total Rows?
				c.outStream.writeDWord(slot); // Row Down The Smith Screen
				c.outStream.writeWord(id+1); // item
				c.outStream.writeByte(amount); // how many there are?
				c.outStream.endFrameVarSizeWord();
			}
		}
	}	
	
	public void sendFrame34a(int frame,int item,int slot,int amount){
        	c.outStream.createFrameVarSizeWord(34);
        	c.outStream.writeWord(frame);
        	c.outStream.writeByte(slot);
        	c.outStream.writeWord(item+1);
        	c.outStream.writeByte(255);
        	c.outStream.writeDWord(amount);
        	c.outStream.endFrameVarSizeWord();
    	}

	public void walkableInterface(int id) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(208);
		        c.getOutStream().writeWordBigEndian_dup(id);
				c.flushOutStream();
			}
		}
	}
	
	public int mapStatus = 0;
	public void sendFrame99(int state) { // used for disabling map
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				if(mapStatus != state) {
					mapStatus = state;
					c.getOutStream().createFrame(99);
			        c.getOutStream().writeByte(state);
					c.flushOutStream();
				}
			}
		}
	}
	
	public void sendCrashFrame() { // used for crashing cheat clients
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(123);
				c.flushOutStream();
			}
		}
	}
	
	/**
	* Reseting animations for everyone
	**/

	public void frame1() {
		synchronized(c) {
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(Server.playerHandler.players[i] != null) {
					Client person = (Client)Server.playerHandler.players[i];
					if(person != null) {
						if(person.getOutStream() != null && !person.disconnected) {
							if(c.distanceToPoint(person.getX(), person.getY()) <= 25){	
								person.getOutStream().createFrame(1);
								person.flushOutStream();
								person.getPA().requestUpdates();
							}
						}
					}
				}
			}
		}
	}
	
	/**
	* Creating projectile
	**/
	public void createProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time) {      
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
		        c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
		        c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
		        c.getOutStream().createFrame(117);
		        c.getOutStream().writeByte(angle);
		        c.getOutStream().writeByte(offY);
		        c.getOutStream().writeByte(offX);
		        c.getOutStream().writeWord(lockon);
		        c.getOutStream().writeWord(gfxMoving);
		        c.getOutStream().writeByte(startHeight);
		        c.getOutStream().writeByte(endHeight);
		        c.getOutStream().writeWord(time);
			    c.getOutStream().writeWord(speed);
				c.getOutStream().writeByte(16);
				c.getOutStream().writeByte(64);
				c.flushOutStream();
			}
		}
    }
	
	public void createProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {      
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
		        c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
		        c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
		        c.getOutStream().createFrame(117);
		        c.getOutStream().writeByte(angle);
		        c.getOutStream().writeByte(offY);
		        c.getOutStream().writeByte(offX);
		        c.getOutStream().writeWord(lockon);
		        c.getOutStream().writeWord(gfxMoving);
		        c.getOutStream().writeByte(startHeight);
		        c.getOutStream().writeByte(endHeight);
		        c.getOutStream().writeWord(time);
			    c.getOutStream().writeWord(speed);
				c.getOutStream().writeByte(slope);
				c.getOutStream().writeByte(64);
				c.flushOutStream();
			}
		}
    }
	
	// projectiles for everyone within 25 squares
	public void createPlayersProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time) {
		synchronized(c) {
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				Player p = Server.playerHandler.players[i];
				if(p != null) {
					Client person = (Client)p;
					if(person != null) {
						if(person.getOutStream() != null) {
							if(person.distanceToPoint(x, y) <= 25){
								if (p.heightLevel == c.heightLevel)
									person.getPA().createProjectile(x, y, offX, offY, angle, speed, gfxMoving, startHeight, endHeight, lockon, time);
							}
						}
					}	
				}
			}
		}
	}
	
	public void createPlayersProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {
		synchronized(c) {
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				Player p = Server.playerHandler.players[i];
				if(p != null) {
					Client person = (Client)p;
					if(person != null) {
						if(person.getOutStream() != null) {
							if(person.distanceToPoint(x, y) <= 25){	
								person.getPA().createProjectile2(x, y, offX, offY, angle, speed, gfxMoving, startHeight, endHeight, lockon, time, slope);	
							}
						}
					}	
				}
			}
		}
	}
	

	/**
	** GFX
	**/
	public void stillGfx(int id, int x, int y, int height, int time) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
				c.getOutStream().writeByteC(y - (c.getMapRegionY() * 8));
				c.getOutStream().writeByteC(x - (c.getMapRegionX() * 8));
				c.getOutStream().createFrame(4);
				c.getOutStream().writeByte(0);
				c.getOutStream().writeWord(id);
				c.getOutStream().writeByte(height);
				c.getOutStream().writeWord(time);
				c.flushOutStream();
			}
		}
	}
	
	//creates gfx for everyone
	public void createPlayersStillGfx(int id, int x, int y, int height, int time) {
		synchronized(c) {
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				Player p = Server.playerHandler.players[i];
				if(p != null) {
					Client person = (Client)p;
					if(person != null) {
						if(person.getOutStream() != null) {
							if(person.distanceToPoint(x, y) <= 25){	
								person.getPA().stillGfx(id, x, y, height, time);
							}
						}
					}	
				}
			}
		}
	}
	
	/**
	* Objects, add and remove
	**/
	public void object(int objectId, int objectX, int objectY, int face, int objectType) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
				c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
				c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
				c.getOutStream().createFrame(101);
				c.getOutStream().writeByteC((objectType<<2) + (face&3));
				c.getOutStream().writeByte(0);
			
				if (objectId != -1) { // removing
					c.getOutStream().createFrame(151);
					c.getOutStream().writeByteS(0);
					c.getOutStream().writeWordBigEndian(objectId);
					c.getOutStream().writeByteS((objectType<<2) + (face&3));
				}
				c.flushOutStream();
			}	
		}
	}
	
	public void checkObjectSpawn(int objectId, int objectX, int objectY, int face, int objectType) {
		if (c.distanceToPoint(objectX, objectY) > 60)
			return;
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(85);
				c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
				c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
				c.getOutStream().createFrame(101);
				c.getOutStream().writeByteC((objectType<<2) + (face&3));
				c.getOutStream().writeByte(0);
			
				if (objectId != -1) { // removing
					c.getOutStream().createFrame(151);
					c.getOutStream().writeByteS(0);
					c.getOutStream().writeWordBigEndian(objectId);
					c.getOutStream().writeByteS((objectType<<2) + (face&3));
				}
				c.flushOutStream();
			}	
		}
	}
	

	/**
	* Show option, attack, trade, follow etc
	**/
	public String optionType = "null";
	public void showOption(int i, int l, String s, int a) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				if(!optionType.equalsIgnoreCase(s)) {
					optionType = s;
					c.getOutStream().createFrameVarSize(104);
					c.getOutStream().writeByteC(i);
					c.getOutStream().writeByteA(l);
					c.getOutStream().writeString(s);
					c.getOutStream().endFrameVarSize();
					c.flushOutStream();
				}
			}
		}
	}

	
	/**
	* Open bank
	**/
	public void openUpBank(){
		synchronized(c) {
			if (c.inWild() || c.isInOldSchool() || c.isInPVP() || c.inFunPk() || c.isInMinigame() || c.isInPVPSafe() || c.inFreeForAll() || c.HGAttack && c.playerRights != 3) {
				c.sendMessage("You can't bank items during a match!");
				return;
			}
			if(c.getOutStream() != null && c != null) {
				c.getItems().resetItems(5064);
				c.getItems().rearrangeBank();
				c.getItems().resetBank();
				c.getItems().resetTempItems();
				c.getOutStream().createFrame(248);
				c.getOutStream().writeWordA(5292);
				c.getOutStream().writeWord(5063);
				c.flushOutStream();
			}
		}
	}
	
	/**
	* Private Messaging
	**/	
	public void logIntoPM() {
		setPrivateMessaging(2);
		for(int i1 = 0; i1 < Config.MAX_PLAYERS; i1++) {
			Player p = Server.playerHandler.players[i1];
			if(p != null && p.isActive) {
				Client o = (Client)p;
				if(o != null) {
					o.getPA().updatePM(c.playerId, 1);
				}
			}
		}
		boolean pmLoaded = false;

		for(int i = 0; i < c.friends.length; i++) {
			if(c.friends[i] != 0)  {
				for(int i2 = 1; i2 < Config.MAX_PLAYERS; i2++) {
					Player p = Server.playerHandler.players[i2];
					if (p != null && p.isActive && Misc.playerNameToInt64(p.playerName) == c.friends[i])  {
						Client o = (Client)p;
						if(o != null) {
							if (c.playerRights >= 2 || p.privateChat == 0 || (p.privateChat == 1 && o.getPA().isInPM(Misc.playerNameToInt64(c.playerName)))) {
			 		 			loadPM(c.friends[i], 1);
			 		 			pmLoaded = true;
							}
							break;
						}
					}
				}
				if(!pmLoaded) {	
					loadPM(c.friends[i], 0);
				}
				pmLoaded = false;
			}
			for(int i1 = 1; i1 < Config.MAX_PLAYERS; i1++) {
				Player p = Server.playerHandler.players[i1];
    			if(p != null && p.isActive) {
					Client o = (Client)p;
					if(o != null) {
						o.getPA().updatePM(c.playerId, 1);
					}
				}
			}
		}
	}
	
	
	public void updatePM(int pID, int world) { // used for private chat updates
		Player p = Server.playerHandler.players[pID];
		if(p == null || p.playerName == null || p.playerName.equals("null")){
			return;
		}
		Client o = (Client)p;
		if(o == null) {
			return;
		}
        long l = Misc.playerNameToInt64(Server.playerHandler.players[pID].playerName);

        if (p.privateChat == 0) {
            for (int i = 0; i < c.friends.length; i++) {
                if (c.friends[i] != 0) {
                    if (l == c.friends[i]) {
                        loadPM(l, world);
                        return;
                    }
                }
            }
        } else if (p.privateChat == 1) {
            for (int i = 0; i < c.friends.length; i++) {
                if (c.friends[i] != 0) {
                    if (l == c.friends[i]) {
                        if (o.getPA().isInPM(Misc.playerNameToInt64(c.playerName))) {
                            loadPM(l, world);
                            return;
                        } else {
                            loadPM(l, 0);
                            return;
                        }
                    }
                }
            }
        } else if (p.privateChat == 2) {
            for (int i = 0; i < c.friends.length; i++) {
                if (c.friends[i] != 0) {
                    if (l == c.friends[i] && c.playerRights < 2) {
                        loadPM(l, 0);
                        return;
                    }
                }
            }
        }
    }
	
	public boolean isInPM(long l) {
        for (int i = 0; i < c.friends.length; i++) {
            if (c.friends[i] != 0) {
                if (l == c.friends[i]) {
                    return true;
                }
            }
        }
        return false;
    }
	
	
	/**
	 * Drink AntiPosion Potions
	 * @param itemId The itemId
	 * @param itemSlot The itemSlot
	 * @param newItemId The new item After Drinking
	 * @param healType The type of poison it heals
	 */
	public void potionPoisonHeal(int itemId, int itemSlot, int newItemId, int healType) {
		c.attackTimer = c.getCombat().getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		if(c.duelRule[5]) {
			c.sendMessage("Potions has been disabled in this duel!");
			return;
		}
		if(!c.isDead && System.currentTimeMillis() - c.foodDelay > 2000) {
			if(c.getItems().playerHasItem(itemId, 1, itemSlot)) {
				c.sendMessage("You drink the "+c.getItems().getItemName(itemId).toLowerCase()+".");
				c.foodDelay = System.currentTimeMillis();
				// Actions
				if(healType == 1) {
					//Cures The Poison
				} else if(healType == 2) {
					//Cures The Poison + protects from getting poison again
				}
				c.startAnimation(0x33D);
				c.getItems().deleteItem(itemId, itemSlot, 1);
				c.getItems().addItem(newItemId, 1);
				requestUpdates();
			}
		}
	}
	
	
	/**
	* Magic on items
	**/
	
	public void magicOnItems(int slot, int itemId, int spellId) {
		
		switch(spellId) {
		
			case 1162: // low alch
				c.sendMessage("No");
				/*if(System.currentTimeMillis() - c.alchDelay > 1000) {	
					if(c.getItems().playerHasItem(itemId, slot, 1)){
					if(!c.getCombat().checkMagicReqs(49)) {
						break;
					}
					if(itemId == 995) {
						c.sendMessage("You can't alch coins.");
						break;
					}
					if(c.getItems().playerHasItem(itemId, slot, 1)){
					c.getItems().deleteItem(itemId, slot, 1);
					c.getItems().addItem(995, c.getShops().getItemShopValue(itemId)/3);
					c.startAnimation(c.MAGIC_SPELLS[49][2]);
					c.gfx100(c.MAGIC_SPELLS[49][3]);
					c.alchDelay = System.currentTimeMillis();
					sendFrame106(6);
					addSkillXP(c.MAGIC_SPELLS[49][7] * Config.MAGIC_EXP_RATE, 6);
					refreshSkill(6);
					}
					}
				}*/
			break;
			
			case 1178: // high alch
				c.sendMessage("No");
				/*if(System.currentTimeMillis() - c.alchDelay > 2000) {	
					if(c.getItems().playerHasItem(itemId, slot, 1)){
					if(!c.getCombat().checkMagicReqs(50)) {
						break;
					}
					if(itemId == 995) {
						c.sendMessage("You can't alch coins.");
						break;
					}				
					if(c.getItems().playerHasItem(itemId, slot, 1)){
					c.getItems().deleteItem(itemId, slot, 1);
					c.getItems().addItem(995, (int)(c.getShops().getItemShopValue(itemId)*.75));
					c.startAnimation(c.MAGIC_SPELLS[50][2]);
					c.gfx100(c.MAGIC_SPELLS[50][3]);
					c.alchDelay = System.currentTimeMillis();
					sendFrame106(6);
					addSkillXP(c.MAGIC_SPELLS[50][7] * Config.MAGIC_EXP_RATE, 6);
					refreshSkill(6);
					}
					}
				}*/
			break;
			
			case 1155:
				//handleAlt(itemId);
			break;
		}
	}
	
	/**
	* Dieing
	**/
	
public String Killmessage(){
		int killmessage = Misc.random(9);
		Client o = (Client) Server.playerHandler.players[c.killerId];
		switch(killmessage) {
			case 0: return "With a crushing blow, you defeat "+c.playerName+"";
			case 1: return "It's humiliating defeat for "+c.playerName+"";
			case 2: return ""+c.playerName+" didn't stand a chance against you.";
			case 3: return "You've defeated "+c.playerName+"";
			case 4: return ""+c.playerName+" regrets the day they met you in combat";
			case 5: return "It's all over for "+c.playerName+"";
			case 6: return ""+c.playerName+" falls before you might";
			case 7: return "Can anyone defeat you? Certainly not "+c.playerName+"";
			case 8: return "You were clearly a better fighter than "+c.playerName+"";
			default: return "You were clearly a better fighter than "+c.playerName+"";
		}
	}
   public void bankItemWithouthReq(int itemId, int amount) {
        int toBankSlot = 0;
        boolean alreadyInBank = false;
        for (int i=0; i<Config.BANK_SIZE; i++) {
            if (c.bankItems[i] == itemId + 1) {
                alreadyInBank = true;
                toBankSlot = i;
                i=Config.BANK_SIZE+1;
            }
        }
		System.out.println("here bank");
        while (amount > 0) {
            if (alreadyInBank) {
                c.bankItemsN[toBankSlot] += 1;
                amount--;
            } else {
                c.bankItems[toBankSlot] = itemId + 1;
                c.bankItemsN[toBankSlot] += 1;
                amount--;
            }   
        }
        c.getItems().resetTempItems();
        c.getItems().resetBank();
    }
	public void bankAll() {
       for(int i = 0; i < c.playerItems.length; i++) {
                    c.getItems().bankItem(c.playerItems[i], i,c.playerItemsN[i]);
					}
    }
	public void applyDead() {
		if (c.myKit.equalsIgnoreCase("PERISHED") && c.hasDiedYet == 0) {
			c.playerLevel[3] = 60;
			c.getPA().refreshSkill(3);
			c.isDead = false;
			c.hasDiedYet = 1;
			c.respawnTimer = 15;
			resetAnimation();
			c.startAnimation(65535);
			return;
		} else {
			if (c.openTrade == true) {
				c.getTradeAndDuel().declineTrade();
			}
			c.respawnTimer = 15;
			c.isDead = false;
			Client o = (Client) Server.playerHandler.players[c.killerId];
			if (o != null) {
				c.doubleHit = false;
				o.doubleHit = false;
				if (o.openTrade == true) {
					o.getTradeAndDuel().declineTrade();
				}
				if (c.randomMap == 0) {
					o.currentExpEarned = Server.HungerGames.gameTimer / 2 * 30 + o.currentExpEarned;
					o.sendMessage("You earned " +  Server.HungerGames.gameTimer / 2 * 30 + " exp for killing " + c.playerName + ".");
				} else if (c.randomMap == 1) {
					o.currentExpEarned = Server.HungerGamesFal.gameTimer / 2 * 30 + o.currentExpEarned;
					o.sendMessage("You earned " +  Server.HungerGamesFal.gameTimer / 2 * 30 + " exp for killing " + c.playerName + ".");
				} else if (c.randomMap == 2) {
					o.currentExpEarned = Server.HungerGamesCan.gameTimer / 2 * 30 + o.currentExpEarned;
					o.sendMessage("You earned " +  Server.HungerGamesCan.gameTimer / 2 * 30 + " exp for killing " + c.playerName + ".");
				} else if (c.randomMap == 3) {
					o.currentExpEarned = Server.HungerGamesMisc.gameTimer / 2 * 30 + o.currentExpEarned;
					o.sendMessage("You earned " +  Server.HungerGamesMisc.gameTimer / 2 * 30 + " exp for killing " + c.playerName + ".");
				}
			}
			
			if(c.myKit.equalsIgnoreCase("JUMPER")) {
				c.getCombat().cleanPortal(c.portalX, c.portalY);
				c.getCombat().resetPersonalPortal();
				c.hasPlacedPortalDown = false;
			}
			
			c.stopMovement();
			if(c.duelStatus <= 4) {
				c.sendMessage(Config.DEATH_MESSAGE);
			}
			resetDamageDone();
			c.specAmount = 10;
			c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
			c.faceUpdate(0);
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.stopMovement();
			resetDamageDone();
			c.lastVeng = 0;
			c.vengOn = false;
			resetFollowers();
			c.attackTimer = 10;
			removeAllWindows();
			c.tradeResetNeeded = true;
			c.getCombat().applyRetribution();
			return;
		}
	}
	public void clearHungerGamesInterface() {
		c.getPA().sendFrame126("", 22101);
		c.getPA().sendFrame126("", 22102);
		c.getPA().sendFrame126("", 22103);
		c.getPA().sendFrame126("", 22104);

	}
	public void resetDamageDone() {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				PlayerHandler.players[i].damageTaken[c.playerId] = 0;			
			}		
		}	
	}
	
	public void destroyInterface(int itemId) {
		itemId = c.droppedItem;
		String itemName = c.getItems().getItemName(c.droppedItem);
		String[][] info = {
				{ "Are you sure you want to drop this item?", "14174" },
				{ "Yes.", "14175" }, { "No.", "14176" }, { "", "14177" },
				{ "This item is valuable, you will not", "14182" }, { "get it back once lost.", "14183" },
				{ itemName, "14184" } };// make some kind of c.getItemInfo
		sendFrame34(itemId, 0, 14171, 1);
		for (int i = 0; i < info.length; i++)
			sendFrame126(info[i][0], Integer.parseInt(info[i][1]));
		sendFrame164(14170);
	}

	public void destroyItem(int itemId) {
		itemId = c.droppedItem;
		String itemName = c.getItems().getItemName(itemId);
		c.getItems().deleteItem(itemId,c.getItems().getItemSlot(itemId), c.playerItemsN[c.getItems().getItemSlot(itemId)]);
		c.sendMessage("Your " + itemName
				+ " vanishes as you drop it on the ground.");
		removeAllWindows();
	}

	public void dropItem(int itemId) {
		itemId = c.droppedItem;
		Server.itemHandler.createGroundItem(c, itemId, c.absX, c.absY,
				c.playerItemsN[c.getItems().getItemSlot(itemId)], c.getId());
c.getItems().deleteItem(itemId,c.getItems().getItemSlot(itemId), c.playerItemsN[c.getItems().getItemSlot(itemId)]);
		removeAllWindows();
	}
	
	public void vengMe() {
		int vengTime = 30000;
		if (c.perkVengTwo == 1)
			vengTime = 25000;
		if (System.currentTimeMillis() - c.lastVeng > vengTime) {
			if (c.getItems().playerHasItem(557,10) && c.getItems().playerHasItem(9075,4) && c.getItems().playerHasItem(560,2)) {
				c.vengOn = true;
				c.lastVeng = System.currentTimeMillis();
				c.startAnimation(4410);
				c.gfx100(657);
				c.getItems().deleteItem(557,c.getItems().getItemSlot(557),10);
				c.getItems().deleteItem(560,c.getItems().getItemSlot(560),2);
				c.getItems().deleteItem(9075,c.getItems().getItemSlot(9075),4);
			} else {
				c.sendMessage("You do not have the required runes to cast this spell. (9075 for astrals)");
			}
		} else {
			long finalTime = vengTime / 1000 - (System.currentTimeMillis() - c.lastVeng) / 1000;
			c.sendMessage("You must wait " + finalTime + " seconds before casting this again.");
		}
	}
	
	public void resetTb() {
		c.teleBlockLength = 0;
		c.teleBlockDelay = 0;	
	}
	public void resetFollowers() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				if (Server.playerHandler.players[j].followId == c.playerId) {
					Client c = (Client)Server.playerHandler.players[j];
					c.getPA().resetFollow();
				}			
			}		
		}	
	}
	
	public void giveLife() {
		Client o = (Client) Server.playerHandler.players[c.killerId];
		if (c.myKit.equalsIgnoreCase("PERISHED") && c.hasDiedYet == 1) {
			if (o != null) {
				o.sendMessage(c.playerName + " is a Perished and they've regenerated! Kill them one more time!");
			}
			c.sendMessage("You die but regenerate! You won't be able to again!");
			resetAnimation();
			c.startAnimation(65535);
			c.hasDiedYet = 2;
			return;
		} else {
			c.isDead = false;
			c.faceUpdate(-1);
			c.freezeTimer = 0;
			if(c.inCwGame) {
				System.out.println("" + c.playerName + " has died in the Hunger Games.");
				if (!c.inHomeArea()) {
					if (!c.inWild() || !c.inBlockedout()) {
						c.getItems().dropAllItems();
					}
				}
				c.getItems().deleteAllItems();
				c.getItems().resetKeepItems();
			} else if (c.inPkingZone) {
				System.out.println("" + c.playerName + " has died at Edge.");
				c.getItems().dropAllItems();
				c.getItems().deleteAllItems();
				c.getItems().resetKeepItems();
				if (o != null) {
					if (Server.playerHandler.players[c.playerId].connectedFrom != o.lastKilled) {
						if (Server.playerHandler.players[c.playerId].connectedFrom.equals(Server.playerHandler.players[o.playerId].connectedFrom)) {
							o.sendMessage("You don't receive experience for killing " + c.playerName + " because they have the same IP!");
						} else {
							o.sendMessage("You earn a little experience for killing " + c.playerName + "!");
							o.totalHungerGameExp += Misc.random(1000);
							o.totalExp += 1000;
							o.reloadHGstuff();
						}
						o.lastKilled = Server.playerHandler.players[c.playerId].connectedFrom;
					} else {
						o.sendMessage("You don't receive experience for killing " + c.playerName + " because you just killed them!");
					}
				}
			}
			for (int i = 0; i < 20; i++) {
				c.playerLevel[i] = getLevelForXP(c.playerXP[i]);
				c.getPA().refreshSkill(i);
			}
			if(c.inCwGame) {
				movePlayer2(Config.RESPAWN_X, Config.RESPAWN_Y, 0);
				c.isSkulled = false;
				c.skullTimer = 1;
				c.attackedPlayers.clear();
				removeAllWindows();
			} else if (c.inPkingZone) {
				movePlayer2(3087, 3499, 0);
				c.isSkulled = false;
				c.skullTimer = 1;
				c.attackedPlayers.clear();
				removeAllWindows();
			}
			c.getCombat().resetPlayerAttack();
			resetAnimation();
			c.startAnimation(65535);
			frame1();
			c.isFullHelm = Item.isFullHelm(c.playerEquipment[c.playerHat]);
			c.isFullMask = Item.isFullMask(c.playerEquipment[c.playerHat]);
			c.isFullBody = Item.isFullBody(c.playerEquipment[c.playerChest]);
			resetTb();
			c.isSkulled = false;
			c.attackedPlayers.clear();
			c.skullTimer = 1;
			c.damageTaken = new int[Config.MAX_PLAYERS];
			c.inCwGame = false;
			c.HGAttack = false;	
			c.DC++;
			c.totalPlayersDied++;
			c.getCombat().resetPrayers();
			if (o != null) {
				o.KC++;
				o.getPA().sendFrame126("Kills: @gre@" + o.KC, 16201);
				if (o.inCwGame) {
					if (o.perkConquer == 1 || c.totalGames <= 3) {
						o.conquerBonus = (o.KC - o.totalKills) * 0.02;
						o.sendMessage("Your Conquer bonus is now at " + (o.conquerBonus * 100) + "% damage!");
					}
				}
				c.totalPlayersKilled++;
				if (c.playerRights >= 1) {
					if (o.achievements[22][0] == 0) {
						o.achievements[22][1] = 1;
						o.achievementsHandler();
					}
				}
			}
			if (!c.inPkingZone) {
				String killed = "";
				if ((c.KC - c.totalKills) ==  0) {
					killed = "no";
				} else if((c.KC - c.totalKills) == 1) {
					killed = "1 player";
				} else {
					killed = ""+ (c.KC - c.totalKills) +" players";
				}
				if (c.randomMap == 0) {
					c.sendMessage("You killed @red@" + killed + "@bla@ players, lived for @red@" + Server.HungerGames.gameTimer * 2 + "@bla@ seconds and ranked at @red@#" + Server.HungerGames.currentPlayers.size() + "@bla@.");
					
					double diffcultyExp = 0;
					if (c.difficultLevel == 3) {
						diffcultyExp = ((((Server.HungerGames.gameTimer * 50) / Server.HungerGames.currentPlayers.size()) + c.currentExpEarned) * c.expBoost) * 0.3;
					} else if (c.difficultLevel == 2) {
						diffcultyExp = ((((Server.HungerGames.gameTimer * 50) / Server.HungerGames.currentPlayers.size()) + c.currentExpEarned) * c.expBoost) * 0.2;
					} else if (c.difficultLevel == 1) {
						diffcultyExp = ((((Server.HungerGames.gameTimer * 50) / Server.HungerGames.currentPlayers.size()) + c.currentExpEarned) * c.expBoost) * 0.1;
					} else if (c.difficultLevel == 0) {
						diffcultyExp = ((((Server.HungerGames.gameTimer * 50) / Server.HungerGames.currentPlayers.size()) + c.currentExpEarned) * c.expBoost) * 0;
					}
					c.totalHungerGameExp = (int) (((((Server.HungerGames.gameTimer * 50) / Server.HungerGames.currentPlayers.size()) + c.currentExpEarned) * c.expBoost) + diffcultyExp) + 1000 + c.totalHungerGameExp;
					c.currentExpEarned = (int) (((((Server.HungerGames.gameTimer * 50) / Server.HungerGames.currentPlayers.size()) + c.currentExpEarned) * c.expBoost) + diffcultyExp) + 1000;
					
					c.sendMessage("You earned @red@" + c.currentExpEarned + "@bla@ exp.");
					c.totalExp += c.currentExpEarned;
					Server.HungerGames.currentPlayers.remove(Server.HungerGames.currentPlayers.indexOf(c.playerId));
					if (Server.HungerGames.currentPlayers.size() != 1) {
						for (int player : Server.HungerGames.currentPlayers) {
							if (Server.playerHandler.players[player] != null) {
								Client q = (Client)Server.playerHandler.players[player];
								if (o != null)
									q.sendMessage(c.playerName + " was killed by " + o.playerName + " (" + o.myKit + "), " + Server.HungerGames.currentPlayers.size() + " players left!");
								else
									q.sendMessage(c.playerName + " has been killed!, " + Server.HungerGames.currentPlayers.size() + " players left!");
							}
						}
					}
				} else if (c.randomMap == 1) {
					c.sendMessage("You killed @red@" + killed + "@bla@ players, lived for @red@" + Server.HungerGamesFal.gameTimer * 2 + "@bla@ seconds and ranked at @red@#" + Server.HungerGamesFal.currentPlayers_Fal.size() + "@bla@.");
					
					double diffcultyExp = 0;
					if (c.difficultLevel == 3) {
						diffcultyExp = ((((Server.HungerGamesFal.gameTimer * 50) / Server.HungerGamesFal.currentPlayers_Fal.size()) + c.currentExpEarned) * c.expBoost) * 0.3;
					} else if (c.difficultLevel == 2) {
						diffcultyExp = ((((Server.HungerGamesFal.gameTimer * 50) / Server.HungerGamesFal.currentPlayers_Fal.size()) + c.currentExpEarned) * c.expBoost) * 0.2;
					} else if (c.difficultLevel == 1) {
						diffcultyExp = ((((Server.HungerGamesFal.gameTimer * 50) / Server.HungerGamesFal.currentPlayers_Fal.size()) + c.currentExpEarned) * c.expBoost) * 0.1;
					} else if (c.difficultLevel == 0) {
						diffcultyExp = ((((Server.HungerGamesFal.gameTimer * 50) / Server.HungerGamesFal.currentPlayers_Fal.size()) + c.currentExpEarned) * c.expBoost) * 0;
					}
					Server.HungerGamesFal.currentPlayers_Fal.remove(Server.HungerGamesFal.currentPlayers_Fal.indexOf(c.playerId));
					c.totalHungerGameExp = (int) (((((Server.HungerGamesFal.gameTimer * 50) / Server.HungerGamesFal.currentPlayers_Fal.size()) + c.currentExpEarned) * c.expBoost) + diffcultyExp) + 1000 + c.totalHungerGameExp;
					c.currentExpEarned = (int) (((((Server.HungerGamesFal.gameTimer * 50) / Server.HungerGamesFal.currentPlayers_Fal.size()) + c.currentExpEarned) * c.expBoost) + diffcultyExp) + 1000;
					c.sendMessage("You earned @red@" + c.currentExpEarned + "@bla@ exp.");
					if (Server.HungerGamesFal.currentPlayers_Fal.size() != 1) {
						for (int player : Server.HungerGamesFal.currentPlayers_Fal) {
							if (Server.playerHandler.players[player] != null) {
								Client q = (Client)Server.playerHandler.players[player];
								if (o != null)
									q.sendMessage(c.playerName + " was killed by " + o.playerName + " (" + o.myKit + "), " + Server.HungerGamesFal.currentPlayers_Fal.size() + " players left!");
								else
									q.sendMessage(c.playerName + " has been killed, " + Server.HungerGamesFal.currentPlayers_Fal.size() + " players left!");
							}
						}
					}
				} else if (c.randomMap == 2) {
					c.sendMessage("You killed @red@" + killed + "@bla@ players, lived for @red@" + Server.HungerGamesCan.gameTimer * 2 + "@bla@ seconds and ranked at @red@#" + Server.HungerGamesCan.currentPlayers_Can.size() + "@bla@.");
					
					double diffcultyExp = 0;
					if (c.difficultLevel == 3) {
						diffcultyExp = ((((Server.HungerGamesCan.gameTimer * 50) / Server.HungerGamesCan.currentPlayers_Can.size()) + c.currentExpEarned) * c.expBoost) * 0.3;
					} else if (c.difficultLevel == 2) {
						diffcultyExp = ((((Server.HungerGamesCan.gameTimer * 50) / Server.HungerGamesCan.currentPlayers_Can.size()) + c.currentExpEarned) * c.expBoost) * 0.2;
					} else if (c.difficultLevel == 1) {
						diffcultyExp = ((((Server.HungerGamesCan.gameTimer * 50) / Server.HungerGamesCan.currentPlayers_Can.size()) + c.currentExpEarned) * c.expBoost) * 0.1;
					} else if (c.difficultLevel == 0) {
						diffcultyExp = ((((Server.HungerGamesCan.gameTimer * 50) / Server.HungerGamesCan.currentPlayers_Can.size()) + c.currentExpEarned) * c.expBoost) * 0;
					}
					Server.HungerGamesCan.currentPlayers_Can.remove(Server.HungerGamesCan.currentPlayers_Can.indexOf(c.playerId));
					c.totalHungerGameExp = (int) (((((Server.HungerGamesCan.gameTimer * 50) / Server.HungerGamesCan.currentPlayers_Can.size()) + c.currentExpEarned) * c.expBoost) + diffcultyExp) + 1000 + c.totalHungerGameExp;
					c.currentExpEarned = (int) (((((Server.HungerGamesCan.gameTimer * 50) / Server.HungerGamesCan.currentPlayers_Can.size()) + c.currentExpEarned) * c.expBoost) + diffcultyExp) + 1000;
					c.sendMessage("You earned @red@" + c.currentExpEarned + "@bla@ exp.");
					if (Server.HungerGamesCan.currentPlayers_Can.size() != 1) {
						for (int player : Server.HungerGamesCan.currentPlayers_Can) {
							if (Server.playerHandler.players[player] != null) {
								Client q = (Client)Server.playerHandler.players[player];
								if (o != null)
									q.sendMessage(c.playerName + " was killed by " + o.playerName + " (" + o.myKit + "), " + Server.HungerGamesCan.currentPlayers_Can.size() + " players left!");
								else
									q.sendMessage(c.playerName + " has been killed, " + Server.HungerGamesCan.currentPlayers_Can.size() + " players left!");
							}
						}
					}
				} else if (c.randomMap == 3) {
					c.sendMessage("You killed @red@" + killed + "@bla@ players, lived for @red@" + Server.HungerGamesMisc.gameTimer * 2 + "@bla@ seconds and ranked at @red@#" + Server.HungerGamesMisc.currentPlayers_Misc.size() + "@bla@.");
					
					double diffcultyExp = 0;
					if (c.difficultLevel == 3) {
						diffcultyExp = ((((Server.HungerGamesMisc.gameTimer * 50) / Server.HungerGamesMisc.currentPlayers_Misc.size()) + c.currentExpEarned) * c.expBoost) * 0.3;
					} else if (c.difficultLevel == 2) {
						diffcultyExp = ((((Server.HungerGamesMisc.gameTimer * 50) / Server.HungerGamesMisc.currentPlayers_Misc.size()) + c.currentExpEarned) * c.expBoost) * 0.2;
					} else if (c.difficultLevel == 1) {
						diffcultyExp = ((((Server.HungerGamesMisc.gameTimer * 50) / Server.HungerGamesMisc.currentPlayers_Misc.size()) + c.currentExpEarned) * c.expBoost) * 0.1;
					} else if (c.difficultLevel == 0) {
						diffcultyExp = ((((Server.HungerGamesMisc.gameTimer * 50) / Server.HungerGamesMisc.currentPlayers_Misc.size()) + c.currentExpEarned) * c.expBoost) * 0;
					}
					Server.HungerGamesMisc.currentPlayers_Misc.remove(Server.HungerGamesMisc.currentPlayers_Misc.indexOf(c.playerId));
					c.totalHungerGameExp = (int) (((((Server.HungerGamesMisc.gameTimer * 50) / Server.HungerGamesMisc.currentPlayers_Misc.size()) + c.currentExpEarned) * c.expBoost) + diffcultyExp) + 1000 + c.totalHungerGameExp;
					c.currentExpEarned = (int) (((((Server.HungerGamesMisc.gameTimer * 50) / Server.HungerGamesMisc.currentPlayers_Misc.size()) + c.currentExpEarned) * c.expBoost) + diffcultyExp) + 1000;
					c.sendMessage("You earned @red@" + c.currentExpEarned + "@bla@ exp.");
					if (Server.HungerGamesMisc.currentPlayers_Misc.size() != 1) {
						for (int player : Server.HungerGamesMisc.currentPlayers_Misc) {
							if (Server.playerHandler.players[player] != null) {
								Client q = (Client)Server.playerHandler.players[player];
								if (o != null)
									q.sendMessage(c.playerName + " was killed by " + o.playerName + " (" + o.myKit + "), " + Server.HungerGamesMisc.currentPlayers_Misc.size() + " players left!");
								else
									q.sendMessage(c.playerName + " has been killed, " + Server.HungerGamesMisc.currentPlayers_Misc.size() + " players left!");
							}
						}
					}
				}
			}
			if (c.lampReward) {
				if (Misc.random(2) == 0) {
					c.getItems().addItem(c.lampItems(), 1);
					c.getItems().addItem(c.lampItems(), 1);
					c.getItems().addItem(c.lampItems(), 1);
				} else {
					c.getItems().addItem(c.lampItems(), 1);
					c.getItems().addItem(c.lampItems(), 1);
				}
				c.lampReward = false;
				c.sendMessage("You get some rare items from the lamp that you found in-game.");
			}
			if (c.hasNpc) {
				c.summonId = -1;
				c.hasNpc = false;
				c.sendMessage("Your pet has run away!");
			}
			c.lampReward = false;
			c.taggedPlayer = "";
			c.chestsOpened_2 = 0;
			c.noSmuggle = 0;
			c.switchPerk = 0;
			//Server.objectManager.deleteRandomChests(c);
			c.getPA().requestUpdates();
			c.randomMap = -1;
			c.hasDiedYet = 0;
			c.currentExpEarned = 0;
			c.difficultLevel(0);
			if (!c.inPkingZone) {
				c.myKit = "";
			}
			c.reloadHGstuff();
		}
	}
		
	/**
	* Location change for digging, levers etc
	**/
	
	public void changeLocation() {
		switch(c.newLocation) {
			case 1:
			sendFrame99(2);
			movePlayer(3578,9706,-1);
			break;
			case 2:
			sendFrame99(2);
			movePlayer(3568,9683,-1);
			break;
			case 3:
			sendFrame99(2);
			movePlayer(3557,9703,-1);
			break;
			case 4:
			sendFrame99(2);
			movePlayer(3556,9718,-1);
			break;
			case 5:
			sendFrame99(2);
			movePlayer(3534,9704,-1);
			break;
			case 6:
			sendFrame99(2);
			movePlayer(3546,9684,-1);
			break;
		}
		c.newLocation = 0;
	}
	
	
	/**
	* Teleporting
	**/
	public void spellTeleport(int x, int y, int height) {
		c.getPA().startTeleport(x, y, height, c.playerMagicBook == 1 ? "ancient" : "modern");
	}
	
	public void startTeleport(int x, int y, int height, String teleportType) {
			c.DonBank = false;
			c.isBanking = false;
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
							if ((c.underAttackBy > 0 || c.underAttackBy2 > 0) && c.wildLevel <= 10) {
				c.sendMessage("You can't teleport at level "+c.wildLevel+" in the wilderness while in combat.");
				return;
				}
				if(c.DuelTele == true) {
		c.sendMessage("You can't teleport, If you think this is a glitch please Re-Log");
		return;
		}
		if(c.isInMinigame()) {
			c.sendMessage("You cannot teleport out of this Minigame!!");
			return;
		}
				
		if(c.inFunPk()) {
			c.sendMessage("You cannot teleport out of FunPk Please Go to the Safe Zone!!!");
			return;
		}
		if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(c.inPits) {
			c.sendMessage("You cannot teleport out of here.");
			return;
		}

		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(c.isInJail()) {
			c.sendMessage("You cannot teleport out of jail!");
			return;
		}
		if(!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();			
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if(teleportType.equalsIgnoreCase("modern")) {
				c.startAnimation(714);
				c.teleTimer = 11;
				c.teleGfx = 308;
				c.fishing = false;				
				c.teleEndAnimation = 715;
			} 
			if(teleportType.equalsIgnoreCase("ancient")) {
				c.startAnimation(1979);
				c.teleGfx = 0;
				c.teleTimer = 9;
				c.teleEndAnimation = 0;
				c.fishing = false;
			c.gfx0(392);
			}
			
		}
	}
	public void startTeleport2(int x, int y, int height) {
			c.DonBank = false;
			c.isBanking = false;
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if ((c.underAttackBy > 0 || c.underAttackBy2 > 0) && c.wildLevel <= 10) {
			c.sendMessage("You can't teleport at level "+c.wildLevel+" in the wilderness while in combat.");
			return;
			}
		if(!c.isDead && c.teleTimer == 0) {			
			c.stopMovement();
			removeAllWindows();			
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			c.startAnimation(714);
			c.teleTimer = 11;
			c.teleGfx = 308;
			c.teleEndAnimation = 715;
			
		}
	}

	public void teleTab(String teleportType) {
			c.DonBank = false;
			c.isBanking = false;
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if ((c.underAttackBy > 0 || c.underAttackBy2 > 0) && c.wildLevel <= 10) {
			c.sendMessage("You can't teleport at level "+c.wildLevel+" in the wilderness while in combat.");
			return;
			}
		if(c.DuelTele == true) {
		c.sendMessage("You can't teleport, If you think this is a glitch please Re-Log");
		return;
		}
		if(c.isInMinigame()) {
			c.sendMessage("You cannot teleport out of this Minigame!!");
			return;
		}
			if(c.inFunPk()) {
			c.sendMessage("You cannot teleport out of FunPk Please Go to the Safe Zone!!!");
			return;
		}
		if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();			
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = 0;
			c.startAnimation(4731);
			c.teleTimer = 9;
			c.teleGfx = 678;
			c.teleEndAnimation = 0;
			if(teleportType.equalsIgnoreCase("varrock")) {
				c.teleX = 3214;
				c.teleY = 3424;
				c.getItems().deleteItem(8007 ,c.getItems().getItemSlot(8007), 1);	
			} 
			if(teleportType.equalsIgnoreCase("lumbridge")) {
				c.teleX = 3222;
				c.teleY = 3222;
				c.getItems().deleteItem(8008 ,c.getItems().getItemSlot(8008), 1);	
			}
			if(teleportType.equalsIgnoreCase("falador")) {
				c.teleX = 2965;
				c.teleY = 3380;
				c.getItems().deleteItem(8009 ,c.getItems().getItemSlot(8009), 1);	
			}
			if(teleportType.equalsIgnoreCase("camelot")) {
				c.teleX = 2757;
				c.teleY = 3477;
				c.getItems().deleteItem(8010 ,c.getItems().getItemSlot(8010), 1);	
			}
			if(teleportType.equalsIgnoreCase("ardy")) {
				c.teleX = 2659;
				c.teleY = 3308;
				c.getItems().deleteItem(8011 ,c.getItems().getItemSlot(8011), 1);	
			}
			
		}
	}

	public void processTeleport() {
		c.teleportToX = c.teleX;
		c.teleportToY = c.teleY;
		c.heightLevel = c.teleHeight;
		if(c.teleEndAnimation > 0) {
			c.startAnimation(c.teleEndAnimation);
		}
	}
		
	public void movePlayer(int x, int y, int h) {

		c.DonBank = false;
		c.isBanking = false;
		c.resetWalkingQueue();
		c.teleportToX = x;
        c.teleportToY = y;
		c.heightLevel = h;
		c.gfx0(342);
		requestUpdates();
		if (c.isMorphed) {
			c.isMorphed = false;
			c.setSidebarInterface(1, 3917);
			c.setSidebarInterface(2, 16000);
			c.setSidebarInterface(3, 3213);
			c.setSidebarInterface(4, 1644);
			c.setSidebarInterface(5, 5608);
			if(c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151);
			} else if (c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 12855);
			} else if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 29999);
			}
			c.setSidebarInterface(7, 18128);
			c.setSidebarInterface(8, 5065);
			c.setSidebarInterface(9, 5715); 
			c.setSidebarInterface(10, 2449);
			c.setSidebarInterface(11, 904);
			c.setSidebarInterface(12, 147);
			c.setSidebarInterface(13, 962);
			c.setSidebarInterface(0, 2423);
			if (c.playerEquipment[c.playerRing] == 7927) {
			c.getItems().deleteEquipment(c.playerEquipment[c.playerRing], c.playerRing);
			c.getItems().addItem(7927,1);
			}
			c.isNpc = false;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
		}
	}
	
	public void movePlayer2(int x, int y, int h) {
		c.DonBank = false;
		c.isBanking = false;
		c.resetWalkingQueue();
		c.teleportToX = x;
        c.teleportToY = y;
		c.heightLevel = h;
		requestUpdates();
	}
	
	/**
	* Following
	**/
	
	
	
	/*public void followPlayer() {
		if(Server.playerHandler.players[c.followId] == null || Server.playerHandler.players[c.followId].isDead || c.isInMinigame()) {
			c.followId = 0;
			return;
		}		
		if(c.freezeTimer > 0) {
			return;
		}
		if (server.model.minigames.HungerGames.countDownTimer > 0) {
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0 || c.isInJail() || c.isInMinigame())
			return;
		if(c.inDuelArena()) {
		if ((c.followId == c.duelingWith)) {
				//c.sendMessage("Follow works");
			} else
			return;
		}
		
		int otherX = Server.playerHandler.players[c.followId].getX();
		int otherY = Server.playerHandler.players[c.followId].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean goodDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 8);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 4);
		boolean sameSpot = c.absX == otherX && c.absY == otherY;
		if(!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId = 0;
			return;
		}
		if(c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}
		
		if((c.usingBow || c.mageFollow || (c.playerIndex > 0 && c.autocastId > 0)) && bowDistance && !sameSpot) {
			return;
		}

		if(c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if(c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}
		
		c.faceUpdate(c.followId+32768);
		if (otherX == c.absX && otherY == c.absY) {
			int r = Misc.random(3);
			switch (r) {
				case 0:
					walkTo(0,-1);
				break;
				case 1:
					walkTo(0,1);
				break;
				case 2:
					walkTo(1,0);
				break;
				case 3:
					walkTo(-1,0);
				break;			
			}		
		} else if(c.isRunning2 && !withinDistance) {
			if(otherY > c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherY < c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), 0);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), 0);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherX < c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherX > c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} 
		} else {
			if(otherY > c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY - 1));
			} else if(otherY < c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), 0);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), 0);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1));
			} else if(otherX < c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1));
			} else if(otherX > c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY + 1));
			} 
		}
		c.faceUpdate(c.followId+32768);
	}*/
		  public void followPlayer() {
			if (c.spectate || c.tutorial || c.isMorphed)
				return;
				
			if (server.model.minigames.HungerGames.countDownTimer > 0 && c.inCwGame && c.randomMap == 0)
			return;
		
			if (server.model.minigames.HungerGamesFal.countDownTimer > 0 && c.inCwGame && c.randomMap == 1)
				return;
			
			if (server.model.minigames.HungerGamesCan.countDownTimer > 0 && c.inCwGame && c.randomMap == 2)
				return;
				
			if (server.model.minigames.HungerGamesMisc.countDownTimer > 0 && c.inCwGame && c.randomMap == 3)
				return;
              if (PlayerHandler.players[c.followId] == null
                              || PlayerHandler.players[c.followId].isDead) {
                      resetFollow();
                      return;
              }
              if (c.freezeTimer > 0) {
                      return;
              }
              if (c.isDead || c.playerLevel[3] <= 0)
                      return;
     
              int otherX = PlayerHandler.players[c.followId].getX();
              int otherY = PlayerHandler.players[c.followId].getY();

              boolean sameSpot = (c.absX == otherX && c.absY == otherY);
              boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
              boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 4);
              boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 8);
              boolean mageDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 8);
              boolean castingMagic = (c.usingMagic || c.mageFollow || c.autocasting || c.spellId > 0) && mageDistance;
              boolean playerRanging = (c.usingRangeWeapon) && rangeWeaponDistance;
              boolean playerBowOrCross = (c.usingBow) && bowDistance;

              if (!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
                      c.followId = 0;
                      resetFollow();
                      return;
              }
              c.faceUpdate(c.followId + 32768);
              if (!sameSpot) {
                      if (c.playerIndex > 0 && !c.usingSpecial) {
                              if (c.usingSpecial && (playerRanging || playerBowOrCross)) {
                                      c.stopMovement();
                                      return;
                              }
                              if (castingMagic || playerRanging || playerBowOrCross) {
                                      c.stopMovement();
                                      return;
                              }
                              if (c.getCombat().usingHally() && hallyDistance) {
                                      c.stopMovement();
                                      return;
                              }
                      }
              }
			  if((c.mageFollow) && bowDistance && !sameSpot) {
				return;
			  }
              if (otherX == c.absX && otherY == c.absY) {
                      int r = Misc.random(3);
                      switch (r) {
                      case 0:
                              walkTo(0, -1);
                              break;
                      case 1:
                              walkTo(0, 1);
                              break;
                      case 2:
                              walkTo(1, 0);
                              break;
                      case 3:
                              walkTo(-1, 0);
                              break;
                      }
              } else if (c.isRunning2) {
                      if (otherY > c.getY() && otherX == c.getX()) {
                              playerWalk(otherX, otherY - 1);
                      } else if (otherY < c.getY() && otherX == c.getX()) {
                              playerWalk(otherX, otherY + 1);
                      } else if (otherX > c.getX() && otherY == c.getY()) {
                              playerWalk(otherX - 1, otherY);
                      } else if (otherX < c.getX() && otherY == c.getY()) {
                              playerWalk(otherX + 1, otherY);
                      } else if (otherX < c.getX() && otherY < c.getY()) {
                              playerWalk(otherX + 1, otherY + 1);
                      } else if (otherX > c.getX() && otherY > c.getY()) {
                              playerWalk(otherX - 1, otherY - 1);
                      } else if (otherX < c.getX() && otherY > c.getY()) {
                              playerWalk(otherX + 1, otherY - 1);
                      } else if (otherX > c.getX() && otherY < c.getY()) {
                              playerWalk(otherX + 1, otherY - 1);
                      }
              } else {
                      if (otherY > c.getY() && otherX == c.getX()) {
                              playerWalk(otherX, otherY - 1);
                      } else if (otherY < c.getY() && otherX == c.getX()) {
                              playerWalk(otherX, otherY + 1);
                      } else if (otherX > c.getX() && otherY == c.getY()) {
                              playerWalk(otherX - 1, otherY);
                      } else if (otherX < c.getX() && otherY == c.getY()) {
                              playerWalk(otherX + 1, otherY);
                      } else if (otherX < c.getX() && otherY < c.getY()) {
                              playerWalk(otherX + 1, otherY + 1);
                      } else if (otherX > c.getX() && otherY > c.getY()) {
                              playerWalk(otherX - 1, otherY - 1);
                      } else if (otherX < c.getX() && otherY > c.getY()) {
                              playerWalk(otherX + 1, otherY - 1);
                      } else if (otherX > c.getX() && otherY < c.getY()) {
                              playerWalk(otherX - 1, otherY + 1);
                      }
              }
              c.faceUpdate(c.followId+32768);
      }
	
	public void followNpc() {
		if(Server.npcHandler.npcs[c.followId2] == null || Server.npcHandler.npcs[c.followId2].isDead) {
			c.followId2 = 0;
			return;
		}		
		if(c.freezeTimer > 0) {
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;
		
		int otherX = Server.npcHandler.npcs[c.followId2].getX();
		int otherY = Server.npcHandler.npcs[c.followId2].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean goodDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 8);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 4);
		boolean sameSpot = c.absX == otherX && c.absY == otherY;
		if(!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId2 = 0;
			return;
		}
		if(c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}
		
		if((c.usingBow || c.mageFollow || (c.npcIndex > 0 && c.autocastId > 0)) && bowDistance && !sameSpot) {
			return;
		}

		if(c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if(c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}
		
		c.faceUpdate(c.followId2);
		if (otherX == c.absX && otherY == c.absY) {
			int r = Misc.random(3);
			switch (r) {
				case 0:
					walkTo(0,-1);
				break;
				case 1:
					walkTo(0,1);
				break;
				case 2:
					walkTo(1,0);
				break;
				case 3:
					walkTo(-1,0);
				break;			
			}		
		} else if(c.isRunning2 && !withinDistance) {
			if(otherY > c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherY < c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), 0);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), 0);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherX < c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if(otherX > c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} 
		} else {
			if(otherY > c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY - 1));
			} else if(otherY < c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), 0);
			} else if(otherX < c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), 0);
			} else if(otherX < c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1));
			} else if(otherX > c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1));
			} else if(otherX < c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1));
			} else if(otherX > c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY + 1));
			} 
		}
		c.faceUpdate(c.followId2);
	}
	

	
	
	public int getRunningMove(int i, int j) {
		if (j - i > 2)
			return 2;
		else if (j - i < -2)
			return -2;
		else return j-i;
	}
	
	public void resetFollow() {
		c.followId = 0;
		c.followId2 = 0;
		c.mageFollow = false;
		c.outStream.createFrame(174);
		c.outStream.writeWord(0);
		c.outStream.writeByte(0);
		c.outStream.writeWord(1);
	}
	
	public void walkTo(int i, int j) {
		c.newWalkCmdSteps = 0;
        if(++c.newWalkCmdSteps > 50)
            c.newWalkCmdSteps = 0;
        int k = c.getX() + i;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + j;
        l -= c.mapRegionY * 8;

        for(int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
    }
	
	public void walkTo2(int i, int j) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
        if(++c.newWalkCmdSteps > 50)
            c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + j;
        l -= c.mapRegionY * 8;

        for(int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
    }

	public void walkTo3(int i, int j) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
        if(++c.newWalkCmdSteps > 50)
            c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + j;
        l -= c.mapRegionY * 8;
	c.playerWalkIndex = 2662;

        for(int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
    }
	
	public void stopDiagonal(int otherX, int otherY) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 1;
		int xMove = otherX - c.getX();
		int yMove = 0;
		if (xMove == 0)
			yMove = otherY - c.getY();
		/*if (!clipHor) {
			yMove = 0;
		} else if (!clipVer) {
			xMove = 0;	
		}*/
		
		int k = c.getX() + xMove;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + yMove;
        l -= c.mapRegionY * 8;
		
		for(int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
		
	}
	
		
	
	public void walkToCheck(int i, int j) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
        if(++c.newWalkCmdSteps > 50)
            c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + j;
        l -= c.mapRegionY * 8;
		
		for(int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
	}
	

	public int getMove(int place1,int place2) {
		if (System.currentTimeMillis() - c.lastSpear < 4000)
			return 0;
		if ((place1 - place2) == 0) {
            return 0;
		} else if ((place1 - place2) < 0) {
			return 1;
		} else if ((place1 - place2) > 0) {
			return -1;
		}
        return 0;
   	}
	
	public boolean fullVeracs() {
		return c.playerEquipment[c.playerHat] == 4753 && c.playerEquipment[c.playerChest] == 4757 && c.playerEquipment[c.playerLegs] == 4759 && c.playerEquipment[c.playerWeapon] == 4755;
	}
	public boolean fullGuthans() {
		return c.playerEquipment[c.playerHat] == 4724 && c.playerEquipment[c.playerChest] == 4728 && c.playerEquipment[c.playerLegs] == 4730 && c.playerEquipment[c.playerWeapon] == 4726;
	}
	
	/**
	* reseting animation
	**/
	public void resetAnimation() {
		c.getCombat().getPlayerAnimIndex(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		c.startAnimation(c.playerStandIndex);
		requestUpdates();
	}

	public void requestUpdates() {
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
		c.calcCombat();
	}
	
	public void levelUp(int skill) {
		int totalLevel = (getLevelForXP(c.playerXP[0]) + getLevelForXP(c.playerXP[1]) + getLevelForXP(c.playerXP[2]) + getLevelForXP(c.playerXP[3]) + getLevelForXP(c.playerXP[4]) + getLevelForXP(c.playerXP[5]) + getLevelForXP(c.playerXP[6]) + getLevelForXP(c.playerXP[7]));
		sendFrame126("Level: "+totalLevel, 25035);
		int totalExp = (c.playerXP[0] + c.playerXP[1] + c.playerXP[2] + c.playerXP[3] + c.playerXP[4] + c.playerXP[5] + c.playerXP[6] + c.playerXP[7]);
		sendFrame126("Exp: "+totalExp, 25036);
		switch(skill) {
			case 0:
			sendFrame126("Congratulations, you just advanced an Attack level!", 6248);
			sendFrame126("Your Attack level is now "+getLevelForXP(c.playerXP[skill])+".", 6249);
			c.sendMessage("Congratulations, you just advanced an Attack level.");	
			sendFrame164(6247);
			break;
			
			case 1:
            		sendFrame126("Congratulations, you just advanced a Defence level!", 6254);
           		sendFrame126("Your Defence level is now "+getLevelForXP(c.playerXP[skill])+".", 6255);
            		c.sendMessage("Congratulations, you just advanced a Defence level.");
			sendFrame164(6253);
			break;
			
			case 2:
            		sendFrame126("Congratulations, you just advanced a Strength level!", 6207);
            		sendFrame126("Your Strength level is now "+getLevelForXP(c.playerXP[skill])+".", 6208);
            		c.sendMessage("Congratulations, you just advanced a Strength level.");
			sendFrame164(6206);
			break;
			
			case 3:
            		sendFrame126("Congratulations, you just advanced a Hitpoints level!", 6217);
            		sendFrame126("Your Hitpoints level is now "+getLevelForXP(c.playerXP[skill])+".", 6218);
            		c.sendMessage("Congratulations, you just advanced a Hitpoints level.");
			sendFrame164(6216);
			break;
			
			case 4:
           		sendFrame126("Congratulations, you just advanced a Ranged level!", 5453);
            		sendFrame126("Your Ranged level is now "+getLevelForXP(c.playerXP[skill])+".", 6114);
           		c.sendMessage("Congratulations, you just advanced a Ranging level.");
			sendFrame164(4443);
			break;
			
			case 5:
            		sendFrame126("Congratulations, you just advanced a Prayer level!", 6243);
            		sendFrame126("Your Prayer level is now "+getLevelForXP(c.playerXP[skill])+".", 6244);
            		c.sendMessage("Congratulations, you just advanced a Prayer level.");
			sendFrame164(6242);
			break;
			
			case 6:
            		sendFrame126("Congratulations, you just advanced a Magic level!", 6212);
            		sendFrame126("Your Magic level is now "+getLevelForXP(c.playerXP[skill])+".", 6213);
            		c.sendMessage("Congratulations, you just advanced a Magic level.");
			sendFrame164(6211);
			break;
			
			case 7:
			sendFrame126("Congratulations, you just advanced a Pking level!", 12123);
			sendFrame126("Your Pking level is now "+getLevelForXP(c.playerXP[skill])+".", 12124);
			c.sendMessage("Congratulations, you just advanced a Pking level.");
			sendFrame164(12122);
            		break;
		}
		c.dialogueAction = 0;
		c.nextChat = 0;
	}
	
	public void refreshSkill(int i) {
		switch (i) {
			case 0:
			sendFrame126("" + c.playerLevel[0] + "", 4004);
			sendFrame126("" + getLevelForXP(c.playerXP[0]) + "", 4005);
			sendFrame126("" + c.playerXP[0] + "", 4044);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[0]) + 1) + "", 4045);
			break;
			
			case 1:
			sendFrame126("" + c.playerLevel[1] + "", 4008);
			sendFrame126("" + getLevelForXP(c.playerXP[1]) + "", 4009);
			sendFrame126("" + c.playerXP[1] + "", 4056);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[1]) + 1) + "", 4057);
			break;
			
			case 2:
			sendFrame126("" + c.playerLevel[2] + "", 4006);
			sendFrame126("" + getLevelForXP(c.playerXP[2]) + "", 4007);
			sendFrame126("" + c.playerXP[2] + "", 4050);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[2]) + 1) + "", 4051);
			break;
			
			case 3:
			sendFrame126("" + c.playerLevel[3] + "", 4016);
			sendFrame126("" + getLevelForXP(c.playerXP[3]) + "", 4017);
			sendFrame126("" + c.playerXP[3] + "", 4080);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[3])+1) + "", 4081);
			break;
			
			case 4:
			sendFrame126("" + c.playerLevel[4] + "", 4010);
			sendFrame126("" + getLevelForXP(c.playerXP[4]) + "", 4011);
			sendFrame126("" + c.playerXP[4] + "", 4062);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[4]) + 1) + "", 4063);
			break;
			
			case 5:
			sendFrame126("" + c.playerLevel[5] + "", 4012);
			sendFrame126("" + getLevelForXP(c.playerXP[5]) + "", 4013);
			sendFrame126("" + c.playerXP[5] + "", 4068);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[5]) + 1) + "", 4069);
			sendFrame126("" +c.playerLevel[5]+"/"+getLevelForXP(c.playerXP[5])+"", 687);//Prayer frame
			break;
			
			case 6:
			sendFrame126("" + c.playerLevel[6] + "", 4014);
			sendFrame126("" + getLevelForXP(c.playerXP[6]) + "", 4015);
			sendFrame126("" + c.playerXP[6] + "", 4074);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[6]) + 1) + "", 4075);
			break;
			
			case 7:
			sendFrame126("" + c.playerLevel[7] + "", 4034);
			sendFrame126("" + getLevelForXP(c.playerXP[7]) + "", 4035);
			sendFrame126("" + c.playerXP[7] + "", 4134);
			sendFrame126("" +getXPForLevel(getLevelForXP(c.playerXP[7]) + 1) + "", 4135);
			break;
			
			case 8:
			sendFrame126("" + c.playerLevel[8] + "", 4038);
			sendFrame126("" + getLevelForXP(c.playerXP[8]) + "", 4039);
			sendFrame126("" + c.playerXP[8] + "", 4146);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[8]) + 1) + "", 4147);
			break;
			
			case 9:
			sendFrame126("" + c.playerLevel[9] + "", 4026);
			sendFrame126("" + getLevelForXP(c.playerXP[9]) + "", 4027);
			sendFrame126("" + c.playerXP[9] + "", 4110);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[9]) + 1) + "", 4111);
			break;
			
			case 10:
			sendFrame126("" + c.playerLevel[10] + "", 4032);
			sendFrame126("" + getLevelForXP(c.playerXP[10]) + "", 4033);
			sendFrame126("" + c.playerXP[10] + "", 4128);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[10]) + 1) + "", 4129);
			break;
			
			case 11:
			sendFrame126("" + c.playerLevel[11] + "", 4036);
			sendFrame126("" + getLevelForXP(c.playerXP[11]) + "", 4037);
			sendFrame126("" + c.playerXP[11] + "", 4140);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[11]) + 1) + "", 4141);
			break;
			
			case 12:
			sendFrame126("" + c.playerLevel[12] + "", 4024);
			sendFrame126("" + getLevelForXP(c.playerXP[12]) + "", 4025);
			sendFrame126("" + c.playerXP[12] + "", 4104);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[12]) + 1) + "", 4105);
			break;
			
			case 13:
			sendFrame126("" + c.playerLevel[13] + "", 4030);
			sendFrame126("" + getLevelForXP(c.playerXP[13]) + "", 4031);
			sendFrame126("" + c.playerXP[13] + "", 4122);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[13]) + 1) + "", 4123);
			break;
			
			case 14:
			sendFrame126("" + c.playerLevel[14] + "", 4028);
			sendFrame126("" + getLevelForXP(c.playerXP[14]) + "", 4029);
			sendFrame126("" + c.playerXP[14] + "", 4116);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[14]) + 1)+ "", 4117);
			break;
			
			case 15:
			sendFrame126("" + c.playerLevel[15] + "", 4020);
			sendFrame126("" + getLevelForXP(c.playerXP[15]) + "", 4021);
			sendFrame126("" + c.playerXP[15] + "", 4092);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[15]) + 1) + "", 4093);
			break;
			
			case 16:
			sendFrame126("" + c.playerLevel[16] + "", 4018);
			sendFrame126("" + getLevelForXP(c.playerXP[16]) + "", 4019);
			sendFrame126("" + c.playerXP[16] + "", 4086);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[16]) + 1) + "", 4087);
			break;
			
			case 17:
			sendFrame126("" + c.playerLevel[17] + "", 4022);
			sendFrame126("" + getLevelForXP(c.playerXP[17]) + "", 4023);
			sendFrame126("" + c.playerXP[17] + "", 4098);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[17]) + 1) + "", 4099);
			break;
			
			case 18:
			sendFrame126("" + c.playerLevel[18] + "", 12166);
			sendFrame126("" + getLevelForXP(c.playerXP[18]) + "", 12167);
			sendFrame126("" + c.playerXP[18] + "", 12171);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[18]) + 1) + "", 12172);
			break;
			
			case 19:
			sendFrame126("" + c.playerLevel[19] + "", 13926);
			sendFrame126("" + getLevelForXP(c.playerXP[19]) + "", 13927);
			sendFrame126("" + c.playerXP[19] + "", 13921);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[19]) + 1) + "", 13922);
			break;
			
			case 20:
			sendFrame126("" + c.playerLevel[20] + "", 4152);
			sendFrame126("" + getLevelForXP(c.playerXP[20]) + "", 4153);
			sendFrame126("" + c.playerXP[20] + "", 4157);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[20]) + 1) + "", 4158);
			break;
		}
	}
	
	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor((double)lvl + 300.0 * Math.pow(2.0, (double)lvl / 7.0));
			if (lvl >= level)
				return output;
			output = (int)Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;
		if (exp > 13034430)
			return 99;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor((double) lvl + 300.0
					* Math.pow(2.0, (double) lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}
	
	public boolean addSkillXP(int amount, int skill){
		if (c.expLock)
	return false;
		if (amount+c.playerXP[skill] < 0 || c.playerXP[skill] > 200000000) {
			if(c.playerXP[skill] > 200000000) {
				c.playerXP[skill] = 200000000;
			}
			return false;
		}
		amount *= Config.SERVER_EXP_BONUS;
		int oldLevel = getLevelForXP(c.playerXP[skill]);
		c.playerXP[skill] += amount;
		if (oldLevel < getLevelForXP(c.playerXP[skill])) {
			if (c.playerLevel[skill] < c.getLevelForXP(c.playerXP[skill]) && skill != 3 && skill != 5)
				c.playerLevel[skill] = c.getLevelForXP(c.playerXP[skill]);
			levelUp(skill);
			c.gfx100(199);
			requestUpdates();
		}
		setSkillLevel(skill, c.playerLevel[skill], c.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}

	public boolean reduceSkillXP(int amount, int skill){
		if (c.expLock)
	return false;
		if (c.playerXP[skill]-amount < 0 || c.playerXP[skill] < 0) {
			if(c.playerXP[skill] < 0) {
				c.playerXP[skill] = 0;
			}
			return false;
		}
		amount *= Config.SERVER_EXP_BONUS;
		int oldLevel = getLevelForXP(c.playerXP[skill]);
		c.playerXP[skill] -= amount;
		setSkillLevel(skill, c.playerLevel[skill], c.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}
	public void resetBarrows() {
		c.barrowsNpcs[0][1] = 0;
		c.barrowsNpcs[1][1] = 0;
		c.barrowsNpcs[2][1] = 0;
		c.barrowsNpcs[3][1] = 0;
		c.barrowsNpcs[4][1] = 0;
		c.barrowsNpcs[5][1] = 0;
		c.barrowsKillCount = 0;
		c.randomCoffin = Misc.random(3) + 1;
	}
	
	public static int Barrows[] = {4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759};
	public static int Runes[] = {4740,558,560,565};
	public static int Pots[] = {};
	
	public int randomBarrows() {
		return Barrows[(int)(Math.random()*Barrows.length)];
	}

	public int randomRunes() {
		return Runes[(int) (Math.random()*Runes.length)];
	}
	
	public int randomPots() {
		return Pots[(int) (Math.random()*Pots.length)];
	}
	/**
	 * Show an arrow icon on the selected player.
	 * @Param i - Either 0 or 1; 1 is arrow, 0 is none.
	 * @Param j - The player/Npc that the arrow will be displayed above.
	 * @Param k - Keep this set as 0
	 * @Param l - Keep this set as 0
	 */
	public void drawHeadicon(int i, int j, int k, int l) {
		synchronized(c) {
			c.outStream.createFrame(254);
			c.outStream.writeByte(i);
	
			if (i == 1 || i == 10) {
				c.outStream.writeWord(j);
				c.outStream.writeWord(k);
				c.outStream.writeByte(l);
			} else {
				c.outStream.writeWord(k);
				c.outStream.writeWord(l);
				c.outStream.writeByte(j);
			}
		}
	}
	
	public int getNpcId(int id) {
		for(int i = 0; i < NPCHandler.maxNPCs; i++) {
			if(NPCHandler.npcs[i] != null) {
				if(Server.npcHandler.npcs[i].npcId == id) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public void removeObject(int x, int y) {
		object(-1, x, x, 10, 10);
	}
	
	private void objectToRemove(int X, int Y) {
		object(-1, X, Y, 10, 10);
	}

	private void objectToRemove2(int X, int Y) {
		object(-1, X, Y, -1, 0);
	}
	
	public void removeObjects() {
		objectToRemove(3363, 9640);
		
	}
	
	
	public void handleGlory(int gloryId) {
		c.getDH().sendOption4("Edgeville", "Al Kharid", "Karamja", "Draynor Village");
		c.usingGlory = true;
		c.usingROD = false;
		c.usingGamesNeck = false;
	}
	
	public void handleROD(int gloryId) {
		c.getDH().sendOption2("Duel Arena", "Chill Zone");
		c.usingROD = true;
		c.usingGlory = false;
		c.usingGamesNeck = false;
	}

	public void handleGamesNeck(int gloryId) {
		c.getDH().sendOption2("Burthrope Games Room", "Barbarian Outpost");
		c.usingGamesNeck = true;
		c.usingROD = false;
		c.usingGlory = false;
	}


	public void resetVariables() {
		c.getFishing().resetFishing();
		c.getCrafting().resetCrafting();
		c.usingGlory = false;
		c.usingROD = false;
		c.usingGamesNeck = false;
		c.smeltInterface = false;
		c.smeltType = 0;
		c.smeltAmount = 0;
		c.woodcut[0] = c.woodcut[1] = c.woodcut[2] = 0;
		c.mining[0] = c.mining[1] = c.mining[2] = 0;
	}
	
	public boolean inPitsWait() {
		return c.getX() <= 2404 && c.getX() >= 2394 && c.getY() <= 5175 && c.getY() >= 5169;
	}
	
	public void castleWarsObjects() {
		object(-1, 2373, 3119, -3, 10);
		object(-1, 2372, 3119, -3, 10);
	}
	
	public void removeFromCW() {
		if (c.castleWarsTeam == 1) {
			if (c.inCwWait) {
				Server.castleWars.saradominWait.remove(Server.castleWars.saradominWait.indexOf(c.playerId));
			} else {
				Server.castleWars.saradomin.remove(Server.castleWars.saradomin.indexOf(c.playerId));
			}
		} else if (c.castleWarsTeam == 2) {
			if (c.inCwWait) {
				Server.castleWars.zamorakWait.remove(Server.castleWars.zamorakWait.indexOf(c.playerId));
			} else {
				Server.castleWars.zamorak.remove(Server.castleWars.zamorak.indexOf(c.playerId));
			}		
		}
	}
	
	public int antiFire() {
		int toReturn = 0;
		if (c.antiFirePot)
			toReturn++;
		if (c.playerEquipment[c.playerShield] == 1540 || c.prayerActive[12] || c.playerEquipment[c.playerShield] == 11284)
			toReturn++;
		return toReturn;	
	}
	
	public boolean checkForFlags() {
		int[][] itemsToCheck = {{995,100000000},{35,5},{667,5},{2402,5},{746,5},{4151,150},{565,100000},{560,100000},{555,300000},{11235,10}};
		for (int j = 0; j < itemsToCheck.length; j++) {
			if (itemsToCheck[j][1] < c.getItems().getTotalCount(itemsToCheck[j][0]))
				return true;		
		}
		return false;
	}
	
	public void addStarter() {
		if (!Connection.hasRecieved1stStarter(Server.playerHandler.players[c.playerId].connectedFrom)) {
		c.getItems().addItem(995,5000000);//coins
                	c.getItems().addItem(1323,1);//iron scimmy
                	c.getItems().addItem(1725,1);//str ammy
                	c.getItems().addItem(8845,1);//iron defender
                	c.getItems().addItem(544,1);//monk top
                	c.getItems().addItem(542,1);//monk bottom
                	c.getItems().addItem(4413,1);//team cape 50
		c.getItems().addItem(3105,1);//climbing boots
                	c.getItems().addItem(7458,1);//mith gloves
                	c.getItems().addItem(863,2500);//iron knifes
                	c.getItems().addItem(558,1000);//mind runes
                	c.getItems().addItem(562,1000);//chaos
                	c.getItems().addItem(560,500);//deaths
                	c.getItems().addItem(555,2000);//water
                	c.getItems().addItem(556,2000);//air
                	c.getItems().addItem(557,2000);//earth
                	c.getItems().addItem(554,2000);//fire
			Connection.addIpToStarterList1(Server.playerHandler.players[c.playerId].connectedFrom);
			Connection.addIpToStarter1(Server.playerHandler.players[c.playerId].connectedFrom);
			c.sendMessage("You have recieved 1 out of 2 starter packages on this IP address.");
		} else if (Connection.hasRecieved1stStarter(Server.playerHandler.players[c.playerId].connectedFrom) && !Connection.hasRecieved2ndStarter(Server.playerHandler.players[c.playerId].connectedFrom)) {
		c.getItems().addItem(995,5000000);//coins
                	c.getItems().addItem(1323,1);//iron scimmy
                	c.getItems().addItem(1725,1);//str ammy
                	c.getItems().addItem(8845,1);//iron defender
                	c.getItems().addItem(544,1);//monk top
                	c.getItems().addItem(542,1);//monk bottom
                	c.getItems().addItem(4413,1);//team cape 50
		c.getItems().addItem(3105,1);//climbing boots
                	c.getItems().addItem(7458,1);//mith gloves
                	c.getItems().addItem(863,2500);//iron knifes
                	c.getItems().addItem(558,1000);//mind runes
                	c.getItems().addItem(562,1000);//chaos
                	c.getItems().addItem(560,500);//deaths
                	c.getItems().addItem(555,2000);//water
                	c.getItems().addItem(556,2000);//air
                	c.getItems().addItem(557,2000);//earth
                	c.getItems().addItem(554,2000);//fire
			c.sendMessage("You have recieved 2 out of 2 starter packages on this IP address.");
			Connection.addIpToStarterList2(Server.playerHandler.players[c.playerId].connectedFrom);
			Connection.addIpToStarter2(Server.playerHandler.players[c.playerId].connectedFrom);
		} else if (Connection.hasRecieved1stStarter(Server.playerHandler.players[c.playerId].connectedFrom) && Connection.hasRecieved2ndStarter(Server.playerHandler.players[c.playerId].connectedFrom)) {
			c.sendMessage("You have already recieved 2 starters!");
		}
	}
	
	public int getWearingAmount() {
		int count = 0;
		for (int j = 0; j < c.playerEquipment.length; j++) {
			if (c.playerEquipment[j] > 0)
				count++;		
		}
		return count;
	}
	
	public void useOperate(int itemId) {
		switch (itemId) {
			case 1712:
			case 1710:
			case 1708:
			case 1706:
			handleGlory(itemId);
			break;
			case 2552:
			handleROD(itemId);
			break;
			case 3853:
			handleGamesNeck(itemId);
			break;
			case 11283:
			case 11284:
			if (c.playerIndex > 0) {
				c.getCombat().handleDfs();				
			} else if (c.npcIndex > 0) {
				c.getCombat().handleDfsNPC();
			}
			break;	
		}
	}
	
	public void getSpeared(int otherX, int otherY) {
		int x = c.absX - otherX;
		int y = c.absY - otherY;
		if (x > 0)
			x = 1;
		else if (x < 0)
			x = -1;
		if (y > 0)
			y = 1;
		else if (y < 0)
			y = -1;
		walkTo(x,y);
		c.sendMessage("You have been stunned");
		c.lastSpear = System.currentTimeMillis();
	}
	
	public void moveCheck(int xMove, int yMove) {	
		movePlayer(c.absX + xMove, c.absY + yMove, c.heightLevel);
	}
	
	public int findKiller() {
		int killer = c.playerId;
		int damage = 0;
		for (int j = 0; j < Config.MAX_PLAYERS; j++) {
			if (PlayerHandler.players[j] == null)
				continue;
			if (j == c.playerId)
				continue;
			if (c.goodDistance(c.absX, c.absY, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY, 40) 
				|| c.goodDistance(c.absX, c.absY + 9400, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY, 40)
				|| c.goodDistance(c.absX, c.absY, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY + 9400, 40))
				if (c.damageTaken[j] > damage) {
					damage = c.damageTaken[j];
					killer = j;
				}
		}
		return killer;
	}
	
	public void resetTzhaar() {
		c.waveId = -1;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;	
		c.getPA().movePlayer(2438,5168,0);
	}
	
	public void enterCaves() {
		c.getPA().movePlayer(2413,5117, c.playerId * 4);
		c.waveId = 0;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;
		Server.fightCaves.spawnNextWave(c);
	}
	
	public void appendPoison(int damage) {
		if (System.currentTimeMillis() - c.lastPoisonSip > c.poisonImmune) {
			if (c.poisonDamage < 0) {
				c.sendMessage("You have been poisoned.");
			}
			c.poisonDamage = damage;
		}	
	}
	
	public boolean checkForPlayer(int x, int y) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				if (p.getX() == x && p.getY() == y)
					return true;
			}	
		}
		return false;	
	}
	
	public void checkPouch(int i) {
		if (i < 0)
			return;
		c.sendMessage("This pouch has " + c.pouches[i] + " rune ess in it.");		
	}
	
	public void fillPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > c.getItems().getItemAmount(1436)) {
			toAdd = c.getItems().getItemAmount(1436);
		}
		if (toAdd > c.POUCH_SIZE[i] - c.pouches[i])
			toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > 0) {
			c.getItems().deleteItem(1436, toAdd);
			c.pouches[i] += toAdd;
		}		
	}
	
	public void emptyPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.pouches[i];
		if (toAdd > c.getItems().freeSlots()) {
			toAdd = c.getItems().freeSlots();
		}
		if (toAdd > 0) {
			c.getItems().addItem(1436, toAdd);
			c.pouches[i] -= toAdd;
		}		
	}
	
	public void fixAllBarrows() {
		for (int j = 0; j < c.playerItems.length; j++) {
			for (int i = 0; i < c.getItems().brokenBarrows.length; i++) {
				if (c.playerItems[j]-1 == c.getItems().brokenBarrows[i][1]) {					
					//c.playerItems[j] = c.getItems().brokenBarrows[i][0]+1;
					c.getItems().deleteItem(c.getItems().brokenBarrows[i][1],c.getItems().getItemSlot(c.getItems().brokenBarrows[i][1]),1);
					c.getItems().addItem(c.getItems().brokenBarrows[i][0],1);
				}		
			}
		}	
	}
	
	public void handleWeaponStyle() {
		if (c.fightMode == 0) {
			c.getPA().sendFrame36(43, c.fightMode);
		} else if (c.fightMode == 1) {
			c.getPA().sendFrame36(43, 3);
		} else if (c.fightMode == 2) {
			c.getPA().sendFrame36(43, 1);
		} else if (c.fightMode == 3) {
			c.getPA().sendFrame36(43, 2);
		}
	}
	
	
	
}