package server.model.players;

import server.Server;
import server.model.npcs.PetHandler;
import server.model.objects.Object;
import server.util.Misc;
import server.util.ScriptManager;

public class ActionHandler {

	private Client c;
	public int grandKey = 0;
	public static boolean keyFound = false;
	
	int[] weaponLow = { 861, 861, 861, 4675, 4675, 4675, 1317, 1331, 1727, 1333, 1319, 1373, 1371, 1347, 1346, 1359, 1432, 1213 };
	public int weaponLow() {
			return weaponLow[(int) (Math.random() * weaponLow.length)];
	}
	
	int[] commonItem = { 7946, 7946, 7946, 7946, 857, 361, 361, 361, 379, 373, 385, 385, 385, 391, 391, 391, 385, 385, 385, 391, 391, 391, 385, 385, 385, 391, 391, 391, 385, 385, 385, 391, 391, 391, 385, 385, 385, 391, 391, 391, 385, 385, 385, 391, 391, 391, 361, 361, 361, 379, 373, 385, 385, 385, 391, 391, 391, 385, 385, 385, 391, 391, 391, 113, 1317, 1331, 4315, 4323, 4335, 4365, 1478, 1725, 1727, 1729, 6685, 6685, 175, 175, 175, 175, 175, 1540 };
	public int commonItem() {
			return commonItem[(int) (Math.random() * commonItem.length)];
	}
	
	int[] semiCommonItem = { 2412, 2413, 2414, 1033, 1035, 1731, 577, 579, 1052, 1073, 1079, 1079, 1079, 1127, 1123, 1127, 1123, 1127, 1123, 1319, 2487, 2489, 2491, 2493, 2495, 2497, 2499, 2501, 2503, 2579, 2591, 2593, 2615, 2617, 2653, 2655, 2657, 2659, 2663, 2669, 2671, 2597, 3751, 3749, 3749, 3755, 3840, 3842, 4089, 4091, 4093, 4095, 4097, 4127, 4129, 4131, 5698, 5698, 6106, 6107, 6108, 6109, 6110, 6111, 6524, 6528, 6568, 6739, 6912, 6920, 6922, 8849, 8849, 8850, 9185, 9185, 9185, 9185, 10551, 10551, 4153, 1149, 1161, 1163, 1183, 1185, 1199, 1201, 1215, 1289, 1301, 1303, 1305, 1381, 1383, 1385, 1387, 1434, 1704, 1704, 4587, 4587, 4587, 4587, 1093, 1093, 1093, 7459, 7459, 7460, 7460, 7461, 7461, 10499, 10499, 4089, 4091, 4093, 4095, 4097, 10828, 10828, 3122, 3385, 3387, 3389, 3391, 3393 };
	public int semiCommonItem() {
			return semiCommonItem[(int) (Math.random() * semiCommonItem.length)];
	}
	
	int[] rareItem = {  11732, 11728, 6570, 4585, 2577,11128, 2581, 3481, 3483, 3486, 3488, 4087, 4212, 4224, 4708, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4751, 4751, 4753, 4755, 4757, 4759, 4708, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730,  4732, 4734, 4736, 4738, 4745, 4747, 4749, 4753, 4755, 4757, 4759, 6585, 6585, 6731, 6733, 6735, 6737, 6914, 6916, 6918, 6924, 9751, 9748, 9754, 9757, 9763, 9769, 4151, 4151, 4151, 4151, 1187, 1377, 7462, 7462, 11665, 11664, 11663, 8842, 8840, 8839, 11690, 11690, 2550, 2550, 3204, 6889, 3140, 11335 };
	public int rareItem() {
			return rareItem[(int) (Math.random() * rareItem.length)];
	}
	
	int[] grandChest = { 11235, 11730, 11726, 11724, 11722, 11720, 11718, 11235, 11730, 11726, 11724, 11722, 11720, 11718, 11702, 11704, 11706, 11708, 11690, 11283, 15285 };
	public int grandChest() {
			return grandChest[(int) (Math.random() * grandChest.length)];
	}
	
	int[] arrowsAndRunes = { 888, 886, 554, 555, 556, 557, 558, 562, 863, 9142, 9141, 9241, 9075, 9075 };
	public int arrowsAndRunes() {
			return arrowsAndRunes[(int) (Math.random() * arrowsAndRunes.length)];
	}
	
	int[] rareArrowsAndRunes = { 11212, 566, 890, 892, 560, 560, 561, 565, 868, 867, 4740, 6522, 9143, 9144, 9244, 9243, 9242 };
	public int rareArrowsAndRunes() {
			return rareArrowsAndRunes[(int) (Math.random() * rareArrowsAndRunes.length)];
	}
	
	int[] someFish = { 335, 331, 317, 377, 383 };
	public int someFish() {
			return someFish[(int) (Math.random() * someFish.length)];
	}
	
	int[] someFishSkiller = { 333, 329, 315, 379, 385 };
	public int someFishSkiller() {
			return someFishSkiller[(int) (Math.random() * someFishSkiller.length)];
	}
	
	int[] highRunes = { 565, 562, 560, 560 };
	public int highRunes() {
			return highRunes[(int) (Math.random() * highRunes.length)];
	}
	
	int[] highArrows = { 892, 9244 };
	public int highArrows() {
			return highArrows[(int) (Math.random() * highArrows.length)];
	}
	
	int[] alchemist = { 227, 227, 227, 227, 227, 227, 227, 257, 259, 263, 265, 267 };
	public int alchemist() {
			return alchemist[(int) (Math.random() * alchemist.length)];
	}
	//2428, 2432, 2434, 2436, 2440, 2442, 2444, 3024
	
	int[] desert = { 1833, 1835, 1837 };
	public int desert() {
			return desert[(int) (Math.random() * desert.length)];
	}
	
	public ActionHandler(Client Client) {
		this.c = Client;
	}

	public String portalIsFrom = "nil";

	public void firstClickObject(int objectType, int obX, int obY) {
		if (c.spectate)
			return;
		c.clickObjectType = 0;
		switch(objectType) {
		
		case 375:
			c.sendMessage("Looks like this chest is locked.");
		break;
		
		case 7272:
			portalIsFrom = c.getCombat().CheckForPortalName(obX, obY);
			c.sendMessage("This portal is owned by @blu@" + portalIsFrom + "@bla@.");
		break;
		
		case 10284:
			if (c.HGAttack && c.chestRoomTimer >= 0) {
				if (System.currentTimeMillis() - c.lastChest > 1000) {
				c.lastChest = System.currentTimeMillis();
					if (c.randomMap == 3) {
						for (int i = 0; i < c.chestInfoMisc.length; i++) {
							if (c.objectX == c.chestInfoMisc[i][0] && c.objectY == c.chestInfoMisc[i][1]) {
								if (c.chestInfoMisc[i][2] == 0) {
									c.totalGrandChests++;
									Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
									Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
									Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
									Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
									Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 150 + Misc.random(50), c.getId());
									Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 150 + Misc.random(50), c.getId());
									Server.itemHandler.createGroundItem(c, grandChest(), c.absX, c.absY, 1, c.getId());
									Server.itemHandler.createGroundItem(c, grandChest(), c.absX, c.absY, 1, c.getId());
									c.sendMessage("You check the chest and some items appear below you.");
									c.startAnimation(899);
									for (int player : Server.HungerGamesMisc.currentPlayers_Misc) {
										if (Server.playerHandler.players[player] != null) {
											Client c1 = (Client)Server.playerHandler.players[player];
											c1.sendMessage(c.playerName + " found the hidden @red@GRAND CHEST!");
										}
									}
									c.chestInfoMisc[i][2] = 1;
								} else {
									c.sendMessage("The chest is empty!");
								}
							}
						}
					}
				}
			}
		break;
		
		case 376:
			try {
				if (c.HGAttack) {
					if (System.currentTimeMillis() - c.lastChest > 1000) {
						c.lastChest = System.currentTimeMillis();
						String message = "";
						int randomItems, specialItems;
						if (c.randomMap == 0) {
							for (int i = 0; i < c.chestInfo.length; i++) {
								if (c.objectX == c.chestInfo[i][0] && c.objectY == c.chestInfo[i][1]) {
									if (c.chestInfo[i][2] == 0) {
									c.totalChestsLooted++;
									c.chestsOpened++;
									c.chestsOpened_2++;
									if (c.chestsOpened == 500 && c.achievements[3][0] == 0) {
										c.achievements[3][1] = 1;
										c.achievementsHandler();
									} else if (c.chestsOpened == 1000 && c.achievements[4][0] == 0) {
										c.achievements[4][1] = 1;
										c.achievementsHandler();
									}
									if (c.chestsOpened_2 == 40 && c.achievements[5][0] == 0) {
										c.achievements[5][1] = 1;
										c.achievementsHandler();
									}
									randomItems = (int)(Math.random() * 16);
										switch (randomItems) {
											case 0://common
											case 1:
											case 2:
											case 3:
											case 4:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												//Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												//Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(300), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 30 + Misc.random(5), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 5://less common
											case 6:
											case 7:
											case 8:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												//Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(300), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(20), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 40 + Misc.random(15), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 9://slightly common
											case 10:
											case 11:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												//Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(400), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 15 + Misc.random(40), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 50 + Misc.random(40), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 12://rare
											case 13:
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 15 + Misc.random(50), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 60 + Misc.random(50), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 14://vrare
												if (c.myKit.equalsIgnoreCase("LOOTER") && c.playerEquipment[c.playerHat] == 5554) {
													grandKey = (int)(Math.random() * 3);
												} else {
													grandKey = (int)(Math.random() * 5);
												}
												if (grandKey == 0 && Server.HungerGames.gameTimer > 100) {
													c.totalGrandChests++;
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 150 + Misc.random(50), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 150 + Misc.random(50), c.getId());
													Server.itemHandler.createGroundItem(c, grandChest(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, grandChest(), c.absX, c.absY, 1, c.getId());
													c.sendMessage("You check the chest and some items appear below you.");
													c.startAnimation(899);
													for (int player : Server.HungerGames.currentPlayers) {
														if (Server.playerHandler.players[player] != null) {
															Client c1 = (Client)Server.playerHandler.players[player];
															c1.sendMessage(c.playerName + " found a @red@GRAND CHEST!");
														}
													}
												} else {
													Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
													Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 20 + Misc.random(30), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 20 + Misc.random(30), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 80 + Misc.random(40), c.getId());
													c.sendMessage("You check the chest and some items appear below you.");
													c.startAnimation(899);
												}
												break;
											case 15:
													if(c.myKit.equalsIgnoreCase("SKILLER")) {
														c.sendMessage("The chest is trapped!");
														c.sendMessage("You carefully disable the trap and remove the items!");
														Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
														Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 20 + Misc.random(30), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 20 + Misc.random(30), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 80 + Misc.random(40), c.getId());
														c.startAnimation(899);
													} else if (Server.HungerGames.gameTimer > 25) {
														c.totalTraps++;
														c.sendMessage("The chest is trapped!");
														int trap = Misc.random(3);
														if (trap == 0) {
															c.getPA().createPlayersStillGfx(542, c.objectX, c.objectY, 0, 5);
															c.dealDamage(35);
															c.handleHitMask(35);
															c.getPA().refreshSkill(3);
															c.sendMessage("You've been hit by an explosive trap!");
															if (c.playerLevel[3] <= 0 && c.achievements[6][0] == 0) {
																c.achievements[6][1] = 1;
																c.achievementsHandler();
															}
														} else if (trap == 1) {
															c.getPA().createPlayersStillGfx(267, c.objectX, c.objectY, 0, 5);
															if(c.myKit.equalsIgnoreCase("VENOM")) {
																c.sendMessage("You're a Venom and therefore immune!");
															} else {
																c.getPA().appendPoison(12);
																c.sendMessage("You've been poisoned by the trap!");
															}
														} else {
															c.getPA().createPlayersStillGfx(287, c.objectX, c.objectY, 0, 5);
															for (int player : Server.HungerGames.currentPlayers) {
																if (Server.playerHandler.players[player] != null) {
																	Client o = (Client)Server.playerHandler.players[player];
																	int xVal = (o.absX - c.objectX);
																	int yVal = (o.absY - c.objectY);
																	if (Math.abs(xVal) <= 4 && Math.abs(yVal) <= 4) {
																		o.dealDamage(50);
																		o.handleHitMask(50);
																		o.getPA().refreshSkill(3);
																		o.sendMessage("You've been hit by a huge explosive trap!");
																		if (o.playerLevel[3] <= 0 && o.achievements[6][0] == 0) {
																			o.achievements[6][1] = 1;
																			o.achievementsHandler();
																		}
																	}
																}
															}
														}
													} else {
														c.totalTraps++;
														c.getPA().createPlayersStillGfx(188, c.objectX, c.objectY, 0, 5);
														c.sendMessage("The chest appears to have been trapped, but seems to be a dud! That was close!");
													}
												break;
										}
										specialItems = (int)(Math.random() * 2); //50% for one of these kits to get extra stuff
										if (specialItems == 0) {
											if (c.myKit.equalsIgnoreCase("ARCHER")) {
												Server.itemHandler.createGroundItem(c, highArrows(), c.absX, c.absY, 30 + Misc.random(30), c.getId());
											} else if (c.myKit.equalsIgnoreCase("ELEMENTALIST")) {
												Server.itemHandler.createGroundItem(c, highRunes(), c.absX, c.absY, 100 + Misc.random(150), c.getId());
												Server.itemHandler.createGroundItem(c, highRunes(), c.absX, c.absY, 100 + Misc.random(150), c.getId());
											} else if (c.myKit.equalsIgnoreCase("LOOTER") && c.playerEquipment[c.playerHat] == 5554) {
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
											} else if (c.myKit.equalsIgnoreCase("ALCHEMIST")) {
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
											} else if (c.myKit.equalsIgnoreCase("Default")) {
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												//Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
											}
										}
										if (Misc.random(250) == 0) {
											Server.itemHandler.createGroundItem(c, 7498, c.absX, c.absY, 1, c.getId());
											c.sendMessage("A @red@very rare magical item@bla@ appears beneath you!");
											for (int player : Server.HungerGames.currentPlayers) {
												if (Server.playerHandler.players[player] != null) {
													Client c1 = (Client)Server.playerHandler.players[player];
													c1.sendMessage(c.playerName + " found a @red@Rare Lamp!");
												}
											}
										}
										
										c.chestInfo[i][2] = 1;
									} else {
										c.sendMessage("The chest is empty.");
									}
								}
							}
						} else if (c.randomMap == 1) {
							for (int i = 0; i < c.chestInfoFal.length; i++) {
								if (c.objectX == c.chestInfoFal[i][0] && c.objectY == c.chestInfoFal[i][1]) {
									if (c.chestInfoFal[i][2] == 0) {
									randomItems = (int)(Math.random() * 16);
									c.totalChestsLooted++;
									c.chestsOpened++;
									c.chestsOpened_2++;
									if (c.chestsOpened == 500 && c.achievements[3][0] == 0) {
										c.achievements[3][1] = 1;
										c.achievementsHandler();
									} else if (c.chestsOpened == 1000 && c.achievements[4][0] == 0) {
										c.achievements[4][1] = 1;
										c.achievementsHandler();
									}
									if (c.chestsOpened_2 == 40 && c.achievements[5][0] == 0) {
										c.achievements[5][1] = 1;
										c.achievementsHandler();
									}
										switch (randomItems) {
											case 0://common
											case 1:
											case 2:
											case 3:
											case 4:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(300), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(5), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 5://less common
											case 6:
											case 7:
											case 8:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(300), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 3 + Misc.random(10), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(5), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 9://slightly common
											case 10:
											case 11:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(400), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 3 + Misc.random(20), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(20), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 12://rare
											case 13:
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 3 + Misc.random(30), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(30), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 14://vrare
												if (c.myKit.equalsIgnoreCase("LOOTER") && c.playerEquipment[c.playerHat] == 5554) {
													grandKey = (int)(Math.random() * 3);
												} else {
													grandKey = (int)(Math.random() * 5);
												}
												if (grandKey == 0 && Server.HungerGamesFal.gameTimer > 100) {
													c.totalGrandChests++;
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 150 + Misc.random(50), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 150 + Misc.random(50), c.getId());
													Server.itemHandler.createGroundItem(c, grandChest(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, grandChest(), c.absX, c.absY, 1, c.getId());
													c.sendMessage("You check the chest and some items appear below you.");
													c.startAnimation(899);
													for (int player : Server.HungerGamesFal.currentPlayers_Fal) {
														if (Server.playerHandler.players[player] != null) {
															Client c1 = (Client)Server.playerHandler.players[player];
															c1.sendMessage(c.playerName + " found a @red@GRAND CHEST!");
														}
													}
												} else {
													Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
													Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(30), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(30), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 40 + Misc.random(40), c.getId());
													c.sendMessage("You check the chest and some items appear below you.");
													c.startAnimation(899);
												}
												break;
											case 15:
												if(c.myKit.equalsIgnoreCase("SKILLER")) {
														c.sendMessage("The chest is trapped!");
														c.sendMessage("You carefully disable the trap and remove the items!");
														Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
														Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 20 + Misc.random(30), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 20 + Misc.random(30), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 80 + Misc.random(40), c.getId());
														c.startAnimation(899);
												} else if (Server.HungerGamesFal.gameTimer > 25) {
													c.totalTraps++;
													c.sendMessage("The chest is trapped!");
													int trap = Misc.random(3);
													if (trap == 0) {
														c.getPA().createPlayersStillGfx(542, c.objectX, c.objectY, 0, 5);
														c.dealDamage(35);
														c.handleHitMask(35);
														c.getPA().refreshSkill(3);
														c.sendMessage("You've been hit by an explosive trap!");
														if (c.playerLevel[3] <= 0 && c.achievements[6][0] == 0) {
															c.achievements[6][1] = 1;
															c.achievementsHandler();
														}
													} else if (trap == 1) {
														c.getPA().createPlayersStillGfx(267, c.objectX, c.objectY, 0, 5);
														if(c.myKit.equalsIgnoreCase("VENOM")) {
															c.sendMessage("You're a Venom and therefore immune!");
														} else {
															c.getPA().appendPoison(12);
															c.sendMessage("You've been poisoned by the trap!");
														}
													} else {
														c.getPA().createPlayersStillGfx(287, c.objectX, c.objectY, 0, 5);
														for (int player : Server.HungerGamesFal.currentPlayers_Fal) {
															if (Server.playerHandler.players[player] != null) {
																Client o = (Client)Server.playerHandler.players[player];
																int xVal = (o.absX - c.objectX);
																int yVal = (o.absY - c.objectY);
																if (Math.abs(xVal) <= 4 && Math.abs(yVal) <= 4) {
																	o.dealDamage(50);
																	o.handleHitMask(50);
																	o.getPA().refreshSkill(3);
																	o.sendMessage("You've been hit by a huge explosive trap!");
																	if (o.playerLevel[3] <= 0 && o.achievements[6][0] == 0) {
																		o.achievements[6][1] = 1;
																		o.achievementsHandler();
																	}
																}
															}
														}
													}
												} else {
													c.totalTraps++;
													c.getPA().createPlayersStillGfx(188, c.objectX, c.objectY, 0, 2);
													c.sendMessage("The chest appears to have been trapped, but seems to be a dud! That was close!");
												}
												break;
										}
										specialItems = (int)(Math.random() * 2); //50% for one of these kits to get extra stuff
										if (specialItems == 0) {
											if (c.myKit.equalsIgnoreCase("ARCHER")) {
												Server.itemHandler.createGroundItem(c, highArrows(), c.absX, c.absY, 30 + Misc.random(30), c.getId());
											} else if (c.myKit.equalsIgnoreCase("ELEMENTALIST")) {
												Server.itemHandler.createGroundItem(c, highRunes(), c.absX, c.absY, 30 + Misc.random(30), c.getId());
											} else if (c.myKit.equalsIgnoreCase("LOOTER") && c.playerEquipment[c.playerHat] == 5554) {
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
											} else if (c.myKit.equalsIgnoreCase("ALCHEMIST")) {
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
											} else if (c.myKit.equalsIgnoreCase("Default")) {
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
											}
										}
										if (Misc.random(250) == 0) {
											Server.itemHandler.createGroundItem(c, 7498, c.absX, c.absY, 1, c.getId());
											c.sendMessage("A @red@very rare magical item@bla@ appears beneath you!");
											for (int player : Server.HungerGamesFal.currentPlayers_Fal) {
												if (Server.playerHandler.players[player] != null) {
													Client c1 = (Client)Server.playerHandler.players[player];
													c1.sendMessage(c.playerName + " found a @red@Rare Lamp!");
												}
											}
										}
										
										c.chestInfoFal[i][2] = 1;
									} else {
										c.sendMessage("The chest is empty.");
									}
								}
							}
						} else if (c.randomMap == 2) {
							for (int i = 0; i < c.chestInfoCan.length; i++) {
								if (c.objectX == c.chestInfoCan[i][0] && c.objectY == c.chestInfoCan[i][1]) {
									if (c.chestInfoCan[i][2] == 0) {
									randomItems = (int)(Math.random() * 16);
									c.totalChestsLooted++;
									c.chestsOpened++;
									c.chestsOpened_2++;
									if (c.chestsOpened == 500 && c.achievements[3][0] == 0) {
										c.achievements[3][1] = 1;
										c.achievementsHandler();
									} else if (c.chestsOpened == 1000 && c.achievements[4][0] == 0) {
										c.achievements[4][1] = 1;
										c.achievementsHandler();
									}
									if (c.chestsOpened_2 == 40 && c.achievements[5][0] == 0) {
										c.achievements[5][1] = 1;
										c.achievementsHandler();
									}
										if (Misc.random(3) == 0) {
											Server.itemHandler.createGroundItem(c, desert(), c.absX, c.absY, 1, c.getId());
										}
										switch (randomItems) {
											case 0://common
											case 1:
											case 2:
											case 3:
											case 4:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(300), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(5), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 5://less common
											case 6:
											case 7:
											case 8:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(300), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 3 + Misc.random(10), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(5), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 9://slightly common
											case 10:
											case 11:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(400), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 3 + Misc.random(20), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(20), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 12://rare
											case 13:
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 3 + Misc.random(30), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(30), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 14://vrare
												if (c.myKit.equalsIgnoreCase("LOOTER") && c.playerEquipment[c.playerHat] == 5554) {
													grandKey = (int)(Math.random() * 3);
												} else {
													grandKey = (int)(Math.random() * 5);
												}
												if (grandKey == 0 && Server.HungerGamesCan.gameTimer > 100) {
													c.totalGrandChests++;
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 150 + Misc.random(50), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 150 + Misc.random(50), c.getId());
													Server.itemHandler.createGroundItem(c, grandChest(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, grandChest(), c.absX, c.absY, 1, c.getId());
													c.sendMessage("You check the chest and some items appear below you.");
													c.startAnimation(899);
													for (int player : Server.HungerGamesCan.currentPlayers_Can) {
														if (Server.playerHandler.players[player] != null) {
															Client c1 = (Client)Server.playerHandler.players[player];
															c1.sendMessage(c.playerName + " found a @red@GRAND CHEST!");
														}
													}
												} else {
													Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
													Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(30), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(30), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 40 + Misc.random(40), c.getId());
													c.sendMessage("You check the chest and some items appear below you.");
													c.startAnimation(899);
												}
												break;
											case 15:
												if(c.myKit.equalsIgnoreCase("SKILLER")) {
														c.sendMessage("The chest is trapped!");
														c.sendMessage("You carefully disable the trap and remove the items!");
														Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
														Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 20 + Misc.random(30), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 20 + Misc.random(30), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 80 + Misc.random(40), c.getId());
														c.startAnimation(899);
												} else if (Server.HungerGamesCan.gameTimer > 25) {
													c.totalTraps++;
													c.sendMessage("The chest is trapped!");
													int trap = Misc.random(3);
													if (trap == 0) {
														c.getPA().createPlayersStillGfx(542, c.objectX, c.objectY, 0, 2);
														c.dealDamage(35);
														c.handleHitMask(35);
														c.getPA().refreshSkill(3);
														c.sendMessage("You've been hit by an explosive trap!");
														if (c.playerLevel[3] <= 0 && c.achievements[6][0] == 0) {
															c.achievements[6][1] = 1;
															c.achievementsHandler();
														}
													} else if (trap == 1) {
														c.getPA().createPlayersStillGfx(267, c.objectX, c.objectY, 0, 2);
														if(c.myKit.equalsIgnoreCase("VENOM")) {
															c.sendMessage("You're a Venom and therefore immune!");
														} else {
															c.getPA().appendPoison(12);
															c.sendMessage("You've been poisoned by the trap!");
														}
													} else {
														c.getPA().createPlayersStillGfx(287, c.objectX, c.objectY, 0, 2);
														for (int player : Server.HungerGamesCan.currentPlayers_Can) {
															if (Server.playerHandler.players[player] != null) {
																Client o = (Client)Server.playerHandler.players[player];
																int xVal = (o.absX - c.objectX);
																int yVal = (o.absY - c.objectY);
																if (Math.abs(xVal) <= 4 && Math.abs(yVal) <= 4) {
																	o.dealDamage(50);
																	o.handleHitMask(50);
																	o.getPA().refreshSkill(3);
																	o.sendMessage("You've been hit by a huge explosive trap!");
																	if (o.playerLevel[3] <= 0 && o.achievements[6][0] == 0) {
																		o.achievements[6][1] = 1;
																		o.achievementsHandler();
																	}
																}
															}
														}
													}
												} else {
													c.totalTraps++;
													c.getPA().createPlayersStillGfx(188, c.objectX, c.objectY, 0, 2);
													c.sendMessage("The chest appears to have been trapped, but seems to be a dud! That was close!");
												}
												break;
										}
										specialItems = (int)(Math.random() * 2); //50% for one of these kits to get extra stuff
										if (specialItems == 0) {
											if (c.myKit.equalsIgnoreCase("ARCHER")) {
												Server.itemHandler.createGroundItem(c, highArrows(), c.absX, c.absY, 30 + Misc.random(30), c.getId());
											} else if (c.myKit.equalsIgnoreCase("ELEMENTALIST")) {
												Server.itemHandler.createGroundItem(c, highRunes(), c.absX, c.absY, 30 + Misc.random(30), c.getId());
											} else if (c.myKit.equalsIgnoreCase("LOOTER") && c.playerEquipment[c.playerHat] == 5554) {
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
											} else if (c.myKit.equalsIgnoreCase("ALCHEMIST")) {
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
											} else if (c.myKit.equalsIgnoreCase("Default")) {
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
											}
										}
										if (Misc.random(250) == 0) {
											Server.itemHandler.createGroundItem(c, 7498, c.absX, c.absY, 1, c.getId());
											c.sendMessage("A @red@very rare magical item@bla@ appears beneath you!");
											for (int player : Server.HungerGamesCan.currentPlayers_Can) {
												if (Server.playerHandler.players[player] != null) {
													Client c1 = (Client)Server.playerHandler.players[player];
													c1.sendMessage(c.playerName + " found a @red@Rare Lamp");
												}
											}
										}
										
										c.chestInfoCan[i][2] = 1;
									} else {
										c.sendMessage("The chest is empty.");
									}
								}
							}
						} else if (c.randomMap == 3) {
							if (c.chestRoomTimer == 0 || c.chestRoomTimer == 1 || c.chestRoomTimer == 2) {
								return;
							}
							for (int i = 0; i < c.chestInfoMisc.length; i++) {
								if (c.objectX == c.chestInfoMisc[i][0] && c.objectY == c.chestInfoMisc[i][1]) {
									if (c.chestInfoMisc[i][2] == 0) {
									randomItems = (int)(Math.random() * 16);
									c.totalChestsLooted++;
									c.chestsOpened++;
									c.chestsOpened_2++;
									if (c.chestsOpened == 500 && c.achievements[3][0] == 0) {
										c.achievements[3][1] = 1;
										c.achievementsHandler();
									} else if (c.chestsOpened == 1000 && c.achievements[4][0] == 0) {
										c.achievements[4][1] = 1;
										c.achievementsHandler();
									}
									if (c.chestsOpened_2 == 40 && c.achievements[5][0] == 0) {
										c.achievements[5][1] = 1;
										c.achievementsHandler();
									}
										switch (randomItems) {
											case 0://common
											case 1:
											case 2:
											case 3:
											case 4:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(300), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(5), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 5://less common
											case 6:
											case 7:
											case 8:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(300), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 3 + Misc.random(10), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(5), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 9://slightly common
											case 10:
											case 11:
												Server.itemHandler.createGroundItem(c, weaponLow(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(400), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 3 + Misc.random(20), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(20), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 12://rare
											case 13:
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 3 + Misc.random(30), c.getId());
												Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(30), c.getId());
												c.sendMessage("You check the chest and some items appear below you.");
												c.startAnimation(899);
												break;
											case 14://vrare
												if (c.myKit.equalsIgnoreCase("LOOTER") && c.playerEquipment[c.playerHat] == 5554) {
													grandKey = (int)(Math.random() * 3);
												} else {
													grandKey = (int)(Math.random() * 5);
												}
												if (grandKey == 0 && Server.HungerGamesMisc.gameTimer > 100) {
													c.totalGrandChests++;
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 150 + Misc.random(50), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 150 + Misc.random(50), c.getId());
													Server.itemHandler.createGroundItem(c, grandChest(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, grandChest(), c.absX, c.absY, 1, c.getId());
													c.sendMessage("You check the chest and some items appear below you.");
													c.startAnimation(899);
													for (int player : Server.HungerGamesMisc.currentPlayers_Misc) {
														if (Server.playerHandler.players[player] != null) {
															Client c1 = (Client)Server.playerHandler.players[player];
															c1.sendMessage(c.playerName + " found a @red@GRAND CHEST!");
														}
													}
												} else {
													Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
													Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
													Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(30), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 10 + Misc.random(30), c.getId());
													Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 40 + Misc.random(40), c.getId());
													c.sendMessage("You check the chest and some items appear below you.");
													c.startAnimation(899);
													if (Misc.random(2) == 0) {
														Server.itemHandler.createGroundItem(c, 3867, c.absX, c.absY, 1, c.getId());
														c.sendMessage("An item with a strange magical force appears below you.");
													}
												}
												break;
											case 15:
												if(c.myKit.equalsIgnoreCase("SKILLER")) {
														c.sendMessage("The chest is trapped!");
														c.sendMessage("You carefully disable the trap and remove the items!");
														Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
														Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
														Server.itemHandler.createGroundItem(c, arrowsAndRunes(), c.absX, c.absY, Misc.random(500), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 20 + Misc.random(30), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 20 + Misc.random(30), c.getId());
														Server.itemHandler.createGroundItem(c, rareArrowsAndRunes(), c.absX, c.absY, 80 + Misc.random(40), c.getId());
														c.startAnimation(899);
												} else if (Server.HungerGamesMisc.gameTimer > 25) {
													c.totalTraps++;
													c.sendMessage("The chest is trapped!");
													int trap = Misc.random(3);
													if (trap == 0) {
														c.getPA().createPlayersStillGfx(542, c.objectX, c.objectY, 0, 2);
														c.dealDamage(35);
														c.handleHitMask(35);
														c.getPA().refreshSkill(3);
														c.sendMessage("You've been hit by an explosive trap!");
														if (c.playerLevel[3] <= 0 && c.achievements[6][0] == 0) {
															c.achievements[6][1] = 1;
															c.achievementsHandler();
														}
													} else if (trap == 1) {
														c.getPA().createPlayersStillGfx(267, c.objectX, c.objectY, 0, 2);
														if(c.myKit.equalsIgnoreCase("VENOM")) {
															c.sendMessage("You're a Venom and therefore immune!");
														} else {
															c.getPA().appendPoison(12);
															c.sendMessage("You've been poisoned by the trap!");
														}
													} else {
														c.getPA().createPlayersStillGfx(287, c.objectX, c.objectY, 0, 2);
														for (int player : Server.HungerGamesMisc.currentPlayers_Misc) {
															if (Server.playerHandler.players[player] != null) {
																Client o = (Client)Server.playerHandler.players[player];
																int xVal = (o.absX - c.objectX);
																int yVal = (o.absY - c.objectY);
																if (Math.abs(xVal) <= 4 && Math.abs(yVal) <= 4) {
																	o.dealDamage(50);
																	o.handleHitMask(50);
																	o.getPA().refreshSkill(3);
																	o.sendMessage("You've been hit by a huge explosive trap!");
																	if (o.playerLevel[3] <= 0 && o.achievements[6][0] == 0) {
																		o.achievements[6][1] = 1;
																		o.achievementsHandler();
																	}
																}
															}
														}
													}
												} else {
													c.totalTraps++;
													c.getPA().createPlayersStillGfx(188, c.objectX, c.objectY, 0, 2);
													c.sendMessage("The chest appears to have been trapped, but seems to be a dud! That was close!");
												}
												break;
										}
										specialItems = (int)(Math.random() * 2); //50% for one of these kits to get extra stuff
										if (specialItems == 0) {
											if (c.myKit.equalsIgnoreCase("ARCHER")) {
												Server.itemHandler.createGroundItem(c, highArrows(), c.absX, c.absY, 30 + Misc.random(30), c.getId());
											} else if (c.myKit.equalsIgnoreCase("ELEMENTALIST")) {
												Server.itemHandler.createGroundItem(c, highRunes(), c.absX, c.absY, 30 + Misc.random(30), c.getId());
											} else if (c.myKit.equalsIgnoreCase("LOOTER") && c.playerEquipment[c.playerHat] == 5554) {
												Server.itemHandler.createGroundItem(c, semiCommonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, rareItem(), c.absX, c.absY, 1, c.getId());
											} else if (c.myKit.equalsIgnoreCase("ALCHEMIST")) {
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, alchemist(), c.absX, c.absY, 1, c.getId());
											} else if (c.myKit.equalsIgnoreCase("Default")) {
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
												Server.itemHandler.createGroundItem(c, commonItem(), c.absX, c.absY, 1, c.getId());
											}
										}
										if (Misc.random(250) == 0) {
											Server.itemHandler.createGroundItem(c, 7498, c.absX, c.absY, 1, c.getId());
											c.sendMessage("A @red@very rare magical item@bla@ appears beneath you!");
											for (int player : Server.HungerGamesMisc.currentPlayers_Misc) {
												if (Server.playerHandler.players[player] != null) {
													Client c1 = (Client)Server.playerHandler.players[player];
													c1.sendMessage(c.playerName + " found a @red@Rare Lamp!");
												}
											}
										}
										
										c.getPA().movePlayer2(c.absX, c.absY, 0);
										c.chestInfoMisc[i][2] = 1;
									} else {
										c.sendMessage("The chest is empty.");
									}
								}
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		break;
		
		case 9299:
			if (c.absY <= 3190) {
					c.getPA().walkTo(0, 1);
				} else {
					c.getPA().walkTo(0, -1);
				}
		break;
		
		case 1752:
		case 1750://stairs up
		case 1747:		
		case 1749://stairs down
		case 1754:
		case 1746:
		case 1726:// Varrock big stair down
		case 1725:// Varrock big stair up
		case 1722:// Varrock big stair up
		case 1723:// Varrock big stair up
		case 1740:// Varrock bank stair
		case 1738:// Varrock bank stair
		case 1739:// Varrock bank stair
		case 11724:
		case 11727:
		case 11729:
		case 11736:
		case 11732:
		case 11739:
		case 11734:
			c.sendMessage("I don't think it would be safe to climb these.");
		break;
		
		case 1733:
		case 2113:
			c.sendMessage("Propably not a good idea.");
		break;
		
		case 1528:
			if (c.objectX == 3182 && c.objectY == 2984) {
				if (c.absX < 3183) {
					c.getPA().walkTo(1, 0);
				} else {
					c.getPA().walkTo(-1 , 0);
				}
			}
			if (c.objectX == 3172 && c.objectY == 2977) {
				if (c.absY >= 2977) {
					c.getPA().walkTo(0, -1);
				} else if (c.absY < 2977) {
					c.getPA().walkTo(0, 1);
				}
			}
		break;
		
		case 11844:
			if (c.absX == 2936) {
			c.getPA().walkTo(-1, 0);
			} else if (c.absX == 2935) {
			c.getPA().walkTo(1, 0);
			}
		break;
		
		case 4577:
			if (c.absY == 3635) {
				c.getPA().walkTo(0,1);
			} else {
				c.getPA().walkTo(0,-1);
			}
		break;
		
		case 1764:
			if (c.objectX == 2856 && c.objectY == 9569) {
				c.getPA().movePlayer(2337, 9804, 0);
			}
		break;
		
		case 2406:
			if (c.absX > 3201) {
				c.getPA().walkTo(-1,0);
			} else {
				c.getPA().walkTo(1,0);
			}
		break;
		case 3725:
		case 3726:
			if (c.absX > 2824) {
				c.getPA().walkTo(-1,0);
			} else {
				c.getPA().walkTo(1,0);
			}
		break;
		case 3745:
			if (c.absX >= 2823) {
				c.getPA().walkTo(-1,0);
			} else {
				c.getPA().walkTo(1,0);
			}
		break;

		case 7257:
			if (c.objectX == 2905 && c.objectY == 3537) {
				c.getPA().movePlayer(3061, 4983, 1);
			}
		break;
		case 11867:
			if (c.objectX == 3019 && c.objectY == 3450) {
				c.getPA().movePlayer(3058, 9776, 0);
			}
		break;
		case 1755:
			if (c.objectX == 3019 && c.objectY == 9850) {
				c.getPA().movePlayer(3018, 3450, 0);
			}
		break;
		case 4615:
			if (c.objectX == 2596 && c.objectY == 3608) {
				c.getPA().movePlayer(2598, 3608, 0);
			}
		break;
		case 2475:
			if (c.objectX == 3233 && c.objectY == 9312) {
				c.getPA().movePlayer(3233, 2887, 0);
			}
		break;
		case 6481:
			if (c.objectX == 3233 && c.objectY == 2888) {
				c.getPA().movePlayer(3234, 9312, 0);
			}
		break;

		case 2882:
		case 2883:
			if (c.objectX == 3268) {
				if (c.absX < c.objectX) {
					c.getPA().walkTo(1,0);
				} else {
					c.getPA().walkTo(-1,0);
				}
			}
		break;
		case 272:
			c.getPA().movePlayer(c.absX, c.absY, 1);
		break;

		case 273:
			c.getPA().movePlayer(c.absX, c.absY, 0);
		break;
		case 245:
			c.getPA().movePlayer(c.absX, c.absY + 2, 2);
		break;
		case 246:
			c.getPA().movePlayer(c.absX, c.absY - 2, 1);
		break;
		case 1766:
		
		case 4389:// Leave lobby
			if (System.currentTimeMillis() - c.lastClick < 1500) {
				return;
			}
			if (c.randomMap == 0) {
				if (Server.HungerGames.gameStartTimer > 4) {
					Server.HungerGames.leaveWaitingRoom(c);
				} else {
					c.sendMessage("Sorry but the match is about to begin!");
				}
				c.lastClick = System.currentTimeMillis();
				return;
			} else if (c.randomMap == 1) {
				if (Server.HungerGamesFal.gameStartTimer > 4) {
					Server.HungerGamesFal.leaveWaitingRoom(c);
				} else {
					c.sendMessage("Sorry but the match is about to begin!");
				}
				c.lastClick = System.currentTimeMillis();
				return;
			}
		break;
		
		case 4407:
			if (System.currentTimeMillis() - c.lastClick < 1500) {
				return;
			}
			if (c.randomMap == 2) {
				if (Server.HungerGamesCan.gameStartTimer > 4) {
					Server.HungerGamesCan.leaveWaitingRoom(c);
				} else {
					c.sendMessage("Sorry but the match is about to begin!");
				}
				c.lastClick = System.currentTimeMillis();
				return;
			}
		break;
		
		case 4408:
			if (System.currentTimeMillis() - c.lastClick < 1500) {
				return;
			}
			if (c.randomMap == 3) {
				if (Server.HungerGamesMisc.gameStartTimer > 4) {
					Server.HungerGamesMisc.leaveWaitingRoom(c);
				} else {
					c.sendMessage("Sorry but the match is about to begin!");
				}
				c.lastClick = System.currentTimeMillis();
				return;
			}
		break;
			
			
		case 10782: //portal hunger games
			if (System.currentTimeMillis() - c.lastClick < 1000) {
				return;
			}
			if (c.myNumber > 0) {
				c.sendMessage("Please wait till the gamble is over, or logout if you're bugged.");
				return;
			}
			if (c.inCwGame || c.tutorial || c.spectate || c.inPkingZone || c.isMorphed || c.eventStarted) {
				return;
			}
			if (c.hasNpc) {
				c.sendMessage("Please bank your cat before joining.");
				return;
			}
			if (c.objectX == 3353 && c.objectY == 9640) {
				if (Server.HungerGames.gameStartTimer > 3) {
					c.lastClick = System.currentTimeMillis();
					c.getPA().clearHungerGamesInterface();
					Server.HungerGames.joinWait(c);
					c.getTradeAndDuel().declineTrade();
				} else {
					c.lastClick = System.currentTimeMillis();
					c.sendMessage("The match is about to begin, please try again in a few seconds.("+Server.HungerGames.gameStartTimer+")");
				}
			} else if (c.objectX == 3363 && c.objectY == 9650) {
				if (Server.HungerGamesCan.gameStartTimer > 3) {
					c.lastClick = System.currentTimeMillis();
					c.lastClick = System.currentTimeMillis();
					c.getPA().clearHungerGamesInterface();
					Server.HungerGamesCan.joinWait(c);
					c.getTradeAndDuel().declineTrade();
				} else {
					c.lastClick = System.currentTimeMillis();
					c.sendMessage("The match is about to begin, please try again in a few seconds.("+Server.HungerGamesCan.gameStartTimer+")");
				}
			} else if (c.objectX == 3374 && c.objectY == 9640) {
				if (Server.HungerGamesFal.gameStartTimer > 3) {
					c.lastClick = System.currentTimeMillis();
					c.getPA().clearHungerGamesInterface();
					Server.HungerGamesFal.joinWait(c);
					c.getTradeAndDuel().declineTrade();
				} else {
					c.lastClick = System.currentTimeMillis();
					c.sendMessage("The match is about to begin, please try again in a few seconds.("+Server.HungerGamesFal.gameStartTimer+")");
				}
			} else if (c.objectX == 3363 && c.objectY == 9629) {
				//c.sendMessage("This will be release this weekend.");
				if (Server.HungerGamesMisc.gameStartTimer > 3) {
					c.lastClick = System.currentTimeMillis();
					c.getPA().clearHungerGamesInterface();
					Server.HungerGamesMisc.joinWait(c);
					c.getTradeAndDuel().declineTrade();
				} else {
					c.lastClick = System.currentTimeMillis();
					c.sendMessage("The match is about to begin, please try again in a few seconds.("+Server.HungerGamesMisc.gameStartTimer+")");
				}
			}
		break;

		case 6552:
			if (c.playerMagicBook != 1) {//Ancients
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.sendMessage("An ancient wisdomin fills your mind.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			} else {
				c.playerMagicBook = 0;
				c.setSidebarInterface(6, 1151); //modern
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
		break;
		case 4008:
			if (c.playerMagicBook != 2) {//lunar
				c.playerMagicBook = 2;
				c.setSidebarInterface(6, 29999);
				c.sendMessage("You suddenly remember lunar magic!");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			} else {
				c.playerMagicBook = 0;
				c.setSidebarInterface(6, 1151); //modern
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
		break;

		case 1734:
			if (c.absY != 10323) {
			c.getPA().movePlayer(3018, 3450, 0);
			} else {
			c.getPA().movePlayer(3044, 3927, 0);
			}
		break;

		case 8959:
			if (c.getX() == 2490 && (c.getY() == 10146 || c.getY() == 10148)) {
				if (c.getPA().checkForPlayer(2490, c.getY() == 10146 ? 10148 : 10146)) {
					new Object(6951, c.objectX, c.objectY, c.heightLevel, 1, 10, 8959, 15);
				}
			}
		break;
		
		case 2213:
		case 14367:
		case 11758:
		case 3193:
		case 2693:
		case 4483:
		case 11338:
		case 3045:
			if (c.noSmuggleTwo >= 0) {
				return;
			}
			
			if (!c.inCwGame) {
				c.isBanking = true;
				c.getPA().openUpBank();
			}
		break;

		case 2623:
			if (c.absX >= c.objectX)
				c.getPA().walkTo(-1,0);
			else
				c.getPA().walkTo(1,0);
		break;

		case 1596:
		case 1597:
		case 534:
		case 676:
		case 1918:
		case 12:
		case 19:
		case 21:
		case 941:
		case 52:
		case 1589:
		case 53:
		case 54:
		case 50:
		case 3200:
		case 1265:
		case 1202:
		case 145:
		case 1116:
		case 141:
		case 142:
		case 1601:
		case 1649:
		case 1623:
		case 122:
		case 1620:
		case 374:
		case 1585:
		case 1634:
		case 1617:
		case 72:
		case 1639:
		case 1627:
		case 997:
		case 1624:
		case 1619:
		//gw stuff
		case 2555:
		case 2556:
		case 2557:
		//case 2558:
		case 2559:
		//case 2560:
		//case 2561:
		//case 2562:
		case 2563:
		//case 2564:
		case 2565:
		if (c.absX == 2936) {
			c.getPA().walkTo(-1,0);
		} else {
			c.getPA().walkTo(1,0);
		}
		if (c.absY != 3451 && c.absY != 3450) {
		if (c.getY() >= c.objectY)
			c.getPA().walkTo(0,-1);
		else
			c.getPA().walkTo(0,1);
		}
		break;


		case 14235:
		case 14233:
			if (c.objectX == 2670)
				if (c.absX <= 2670)
					c.absX = 2671;
				else
					c.absX = 2670;
			if (c.objectX == 2643)
				if (c.absX >= 2643)
					c.absX = 2642;
				else
					c.absX = 2643;
			if (c.absX <= 2585)
				c.absY += 1;
			else c.absY -= 1;
			c.getPA().movePlayer(c.absX, c.absY, 0);
		break;

		case 14829: case 14830: case 14827: case 14828: case 14826: case 14831:
			//Server.objectHandler.startObelisk(objectType);
			Server.objectManager.startObelisk(objectType);
		break;

		//doors
		case 6749:
			if(obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6730:
			if(obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6727:
			if(obX == 3551 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
		case 6746:
			if(obX == 3552 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
		case 6748:
			if(obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6729:
			if(obX == 3545 && obY == 9677){
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6726:
			if(obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6745:
			if(obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6743:
			if(obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
		case 6724:
			if(obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
		//end doors

		
		// DOORS
		case 1516:
		case 1519:
			if (c.objectY == 9698 || c.objectY == 3558) {
				if (c.absY >= c.objectY)
					c.getPA().walkTo(0,-1);
				else
					c.getPA().walkTo(0,1);
				break;
			}
		case 1600:
			if (c.objectY == 9487 || c.objectY == 3087) {
				if (c.absY >= c.objectY)
					c.getPA().walkTo(-1,0);
				else
					c.getPA().walkTo(1,0);
				break;
			}
		case 1530:
			if (c.absX == 2922) {
				c.getPA().walkTo(-1, 0);
			} else if (c.absX == 2921) {
				c.getPA().walkTo(1, 0);
			}
			if (c.objectY == 2564 || c.objectY == 3310) {
				if (c.absX >= c.objectX)
					c.getPA().walkTo(-1,0);
				else
					c.getPA().walkTo(1,0);
				break;
			}
		case 1531:
		case 1533:
		case 1534:
		case 11712:
		case 11711:
		case 11707:
		case 11708:
		case 6725:
		case 3198:
		case 3197:
			Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY, 0);
			break;

		

		case 4496:
		case 4494:
			if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 0);
			}
		break;

		case 4493:
		if (c.inWild() || c.isInOldSchool() || c.isInPVP() || c.inFunPk() || c.isInMinigame() || c.isInPVPSafe() || c.inFreeForAll()) {
			c.sendMessage("You can't use this here.");
			return;
			}
			if (c.heightLevel == 0) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;

		case 4495:
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;

		case 5126:
			if (c.absY == 3554)
				c.getPA().walkTo(0,1);
			else
				c.getPA().walkTo(0,-1);
		break;

		case 1759:
			if (c.objectX == 2884 && c.objectY == 3397)
				c.getPA().movePlayer(c.absX, c.absY + 6400, 0);
		break;

		case 409:
		case 4859:
		//case 2640:
			if (c.objectX == 3095 && c.objectY == 3500 || c.objectX == 3253 && c.objectY == 3486) {
				if(c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
					c.startAnimation(645);
					c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
					c.sendMessage("You recharge your prayer points.");
					c.getPA().refreshSkill(5);
				} else {
					c.sendMessage("You already have full prayer points.");
				}
			} else {
				//c.sendMessage("Exception error! Something has gone wrong!");
				c.staffYell("[@red@Cheater@bla@]: @blu@" + c.playerName + "@bla@ just tried to use a spawned altar at (" + c.objectX + ", " + c.objectY +")!");
				//dealDamage(150);
				//handleHitMask(150);
				//getPA().refreshSkill(3);
			}
		break;

		case 2558:
			c.sendMessage("This door is locked.");
		break;

		case 9294:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(c.objectX + 1, c.absY, 0);
			} else if (c.absX > c.objectX) {
				c.getPA().movePlayer(c.objectX - 1, c.absY, 0);
			}
		break;

		case 9293:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(2892, 9799, 0);
			} else {
				c.getPA().movePlayer(2886, 9799, 0);
			}
		break;
		
		case 10529:
		case 10527:
			if (c.absY <= c.objectY)
				c.getPA().walkTo(0,1);
			else
				c.getPA().walkTo(0,-1);
		break;
		
		case 3044:
			c.getSmithing().sendSmelting();
		break;

		default:
			ScriptManager.callFunc("objectClick1_"+objectType, c, objectType, obX, obY);
			break;

		}
	}

	public void secondClickObject(int objectType, int obX, int obY) {
		if (c.spectate)
			return;
		c.clickObjectType = 0;
		switch(objectType) {
		
			case 4705:
			case 4707:
			case 4277:
				if(c.HGAttack) {
					if (c.myKit.equalsIgnoreCase("SKILLER")) {
						c.getThieving().stealFromStall(someFishSkiller(), 1, 0, 0);
					} else {
						c.getThieving().stealFromStall(someFish(), 1, 0, 0);
					}
				} else {
					c.sendMessage("No fish for you.");
				}
			break;


			case 11666:
			case 3044:
				c.getSmithing().sendSmelting();
			break;
			
			case 2213:
			case 14367:
			case 11758:
			case 2693:
			case 2214:
			case 3045:
			case 5276:
			case 6084:
			case 4483:
			case 11338:
				if (!c.inCwGame) {
					c.isBanking = true;
					c.getPA().openUpBank();
				}
			break;

		default:
			ScriptManager.callFunc("objectClick2_"+objectType, c, objectType, obX, obY);
			break;
		}
	}


	public void thirdClickObject(int objectType, int obX, int obY) {
		if (c.spectate)
			return;
		c.clickObjectType = 0;
		switch(objectType) {
		default:
			ScriptManager.callFunc("objectClick3_"+objectType, c, objectType, obX, obY);
			break;
		}
	}

	public void firstClickNpc(int npcType) {
		if (c.spectate)
			return;
		c.clickNpcType = 0;
		c.rememberNpcIndex = c.npcClickIndex;
		c.npcClickIndex = 0;
		if(PetHandler.pickupPet(c, npcType))
			return;
		switch(npcType) {
		
		case 239:
			c.getShops().openShop(7);
			c.inShop = true;
		break;
		
		case 240:
			c.getShops().openShop(8);
			c.inShop = true;
		break;
		
		case 943:
			c.getDH().sendDialogues(95, npcType);
		break;
		
		case 2257:
			Server.npcHandler.npcAction(npcType, "", 0);
			c.tutorial = true;
			c.getDH().sendDialogues(68, npcType);
		break;
		
		case 1552:
			if (!c.inCwGame || !c.inCwWait || !c.tutorial || !c.spectate || !c.inPkingZone) {
				Server.npcHandler.npcAction(npcType, "Merry Christmas!", 0);
				if (c.startChristmas == 0) {
					c.getDH().sendDialogues(70, npcType);
				} else {
					c.getDH().sendDialogues(88, npcType);
				}
			}
		break;
		
		case 2790:
			c.getDH().sendDialogues(69, npcType);
			c.getItems().addItem(1891, 1);
		break;
		
		case 241:
			c.getShops().openShop(9);
			c.inShop = true;
		break;
		
		case 242:
			c.getShops().openShop(11);
			c.inShop = true;
		break;
		
		case 243:
			c.getShops().openShop(12);
			c.inShop = true;
		break;
		
		case 244:
			c.getShops().openShop(13);
			c.inShop = true;
		break;
		
		case 198:
			if (!c.inCwGame || !c.inCwWait || !c.tutorial || !c.spectate || !c.inPkingZone || !c.eventStarted) {
				c.getDH().sendDialogues(66, npcType);
			}
		break;
		
		case 599:
			if (!c.inCwGame || !c.inCwWait || !c.tutorial || !c.spectate || !c.inPkingZone || !c.eventStarted) {
				c.getPA().showInterface(3559);
				c.canChangeAppearance = true;
			}
		break;
		
		case 372:
			if (!c.inCwGame || !c.inCwWait || !c.tutorial || !c.spectate || !c.inPkingZone || !c.eventStarted) {
				if (c.gambleFinal) {
					c.getDH().sendDialogues(61 ,npcType);
					return;
				}
				if (c.gambleOn) {
					if (c.playerName.equals(c.gamblerOne) || c.firstGamble) {
						c.getDH().sendDialogues(62 ,npcType);
					} else {
						c.getDH().sendDialogues(59 ,npcType);
					}
				} else {
					c.getDH().sendDialogues(39 ,npcType);
				}
			}
		break;
		
		case 619:
			if (c.hasNpc) {
				c.sendMessage("You can't talk to him with a cat out!");
				return;
			}
			if (c.noSmuggleTwo >= 0) {
				return;
			}
			if (!c.inCwGame || !c.inCwWait || !c.tutorial || !c.spectate || !c.inPkingZone || !c.eventStarted) {
				c.getDH().sendDialogues(1 ,npcType);
			}
		break;
		
		case 213:
			if (!c.inCwGame || !c.inCwWait || !c.tutorial || !c.spectate || !c.inPkingZone || !c.eventStarted) {
				c.getDH().sendDialogues(21 ,npcType);
			}
		break;
		
		case 1695:
			if (!c.inCwGame || !c.inCwWait || !c.tutorial || !c.spectate || !c.inPkingZone || !c.eventStarted) {
				c.getDH().sendDialogues(23 ,npcType);
			}
		break;
		
		case 284:
			if (!c.inCwGame || !c.inCwWait || !c.tutorial || !c.spectate || !c.inPkingZone || !c.eventStarted) {
				c.getDH().sendDialogues(64, 0);
			}
		break;
		
		default:
			ScriptManager.callFunc("npcClick1_"+npcType, c, npcType);
			if(c.playerRights == 3)
				Misc.println("First Click Npc : "+npcType);
			break;
		}
	}

	public void secondClickNpc(int npcType) {
		if (c.spectate)
			return;
		c.clickNpcType = 0;
		c.rememberNpcIndex = c.npcClickIndex;
		c.npcClickIndex = 0;
		if(npcType >= 761 && npcType <= 773 && npcType != 767 || npcType == 3504 || npcType == 3505 || npcType == 3506) {
			c.getDH().sendDialogues(908, npcType);
		}
		switch(npcType) {

			default:
				ScriptManager.callFunc("npcClick2_"+npcType, c, npcType);
				if(c.playerRights == 3)
					Misc.println("Second Click Npc : "+npcType);
				break;

		}
	}

	public void thirdClickNpc(int npcType) {
		if (c.spectate)
			return;
		c.clickNpcType = 0;
		c.rememberNpcIndex = c.npcClickIndex;
		c.npcClickIndex = 0;
		if(npcType >= 761 && npcType <= 773 && npcType != 767 || npcType == 3504 || npcType == 3505 || npcType == 3506) {
			if(Server.npcHandler.npcs[c.rememberNpcIndex].spawnedBy == c.playerId)
				c.getDH().sendDialogues(910, npcType);
			else
				c.sendMessage("This isn't your cat.");
		}
		switch(npcType) {

			default:
				ScriptManager.callFunc("npcClick3_"+npcType, c, npcType);
				if(c.playerRights == 3)
					Misc.println("Third Click NPC : "+npcType);
				break;

		}
	}
}