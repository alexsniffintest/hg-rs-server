package server.model.players.skills;

import server.Config;
import server.Server;
import server.model.objects.Objects;
import server.model.players.Client;
import server.util.Misc;
/**
* @Author Sanity
*/

public class Woodcutting {
	
	Client c;
	
	private final int VALID_AXE[] = {1351,1349,15296,1353,1361,1355,1357,1359,6739,13661};
	private final int[] AXE_REQS = {1,1,1,6,6,21,31,41,61,1};
	private int logType;
	private int exp;
	private int levelReq;
	private int axeType;
	private final int EMOTE = 867;
		public boolean resetAnim = false;

	
	public Woodcutting(Client c) {
		this.c = c;
	}
	
	public void startWoodcutting(int logType, int levelReq, int exp) {
		if (goodAxe() > 0) {
			c.turnPlayerTo(c.objectX, c.objectY);
			if (c.playerLevel[c.playerWoodcutting] >= levelReq) {
				this.logType = logType;
				this.exp = exp;
				this.levelReq = levelReq;
				this.axeType = goodAxe();
				c.wcTimer = getWcTimer();
				c.startAnimation(EMOTE);
			} else {
				c.getPA().resetVariables();
				c.startAnimation(65535);
				c.sendMessage("You need a woodcutting level of " + levelReq + " to cut this tree.");
			}		
		} else {
			c.startAnimation(65535);
			c.sendMessage("You need an axe to cut this tree.");
			c.getPA().resetVariables();
		}
	}
	
	public void resetWoodcut() {
		this.logType = -1;
		this.exp = -1;
		this.levelReq = -1;
		this.axeType = -1;
		c.wcTimer = -1;	
	}
	
	public void cutWood() {
		if (c.getItems().addItem(logType,1)) {
			c.startAnimation(EMOTE);
				int random = Misc.random(10);
				int random2 = Misc.random(15);
			if (c.playerEquipment[c.playerWeapon] == VALID_AXE[9] && random == 8) {
					c.startAnimation(733,0);
					Objects fire = new Objects(2732,c.getX(),c.getY(), 0, -1, 10, 3);
					Objects fire2 = new Objects(-1,c.getX(),c.getY(), 0, -1, 10, 60);
					Server.objectHandler.addObject(fire);
					Server.objectHandler.addObject(fire2);
					c.getPA().checkObjectSpawn(2732,c.getX(),c.getY(), -1, 10);
					c.sendMessage("You light a fire.");
					c.getPA().walkTo(0,0);
					c.turnPlayerTo(c.getX() + 1, c.getY());
					//c.getPA().frame1();
					cutWood();
				
					resetAnim = true;
				}
							if (c.playerEquipment[c.playerWeapon] == VALID_AXE[9] && random2 == 15) {

							}
			c.sendMessage("You get some logs.");
			c.getPA().addSkillXP(exp * Config.WOODCUTTING_EXPERIENCE, c.playerWoodcutting);
			c.getPA().refreshSkill(c.playerWoodcutting);
			c.wcTimer = getWcTimer();
		} else {
			c.getPA().resetVariables();
		}
	}
	
	public int goodAxe() {
		for (int j = VALID_AXE.length - 1; j >= 0; j--) {
			if (c.playerEquipment[c.playerWeapon] == VALID_AXE[j]) {
				if (c.playerLevel[c.playerWoodcutting] >= AXE_REQS[j])
					return VALID_AXE[j];
			}		
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			for (int j = VALID_AXE.length - 1; j >= 0; j--) {
				if (c.playerItems[i] == VALID_AXE[j] + 1) {
					if (c.playerLevel[c.playerWoodcutting] >= AXE_REQS[j])
						return VALID_AXE[j];
						//Inferno Adze Effects
	
				}
			}		
		}
		return - 1;
	}
	
	public int getWcTimer() {
		int time = Misc.random(5);
		return time;
	}

}