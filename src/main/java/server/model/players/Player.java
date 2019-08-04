package server.model.players;

import server.Config;
import server.Server;
import server.event.Event;
import server.event.EventContainer;
import server.event.EventManager;
import server.model.npcs.NPC;
import server.model.npcs.NPCHandler;
import server.util.ISAACRandomGen;
import server.util.Misc;
import server.util.Stream;

import java.net.InetAddress;
import java.util.ArrayList;

public abstract class Player {

	public ArrayList <String>killedPlayers = new ArrayList<String> ();
	public ArrayList <Integer>attackedPlayers = new ArrayList<Integer> ();
	public ArrayList<String> lastConnectedFrom = new ArrayList<String>();
	
	public long lastPacket = System.currentTimeMillis();

	public boolean
	yellAllowed = false,
	hiddenRoom = false,
	startSnow = false,
	inShop = false,
	readdedIn = false,
	infectionWait = false,
	lampReward = false,
	disableTrade = false,
	confirmReset = false,
	inWretched = false,
	modAccess = false,
	noEventItem = false,
	check = false,
	knightMode = false,
	necroMode = false,
	inPkingZone = false,
	pickingNumber = false,
	firstGamble = false,
	gambleValue = false,
	godMode = false,
	tutorial = false,
	waitInterface = false,
	shopOne = false,
	convert = false,
	stuck = false,
	instantWalk = false,
	visible = true,
	spectate = false,
	HGAttack = false,
	initialized = false,
	disconnected = false,
	ruleAgreeButton = false,
	RebuildNPCList = false,
	isActive = false,
	isKicked = false,
	isFffaPray = false,
	DonBank = true,
	isSkulled = false,
	winDuel = false,
	friendUpdate = false,
	newPlayer = false,
	hasMultiSign = false,
	canusePortal = false,
	saveCharacter = false,
	mouseButton = false,
	splitChat = false,
	chatEffects = true,
	acceptAid = false,
	bankLogin = false,
	rightMute = false,
	duelWin = false,
	isBanking = false,
	nextDialogue = false,
	autocasting = false,
	usedSpecial = false,
	mageFollow = false,
	dbowSpec = false,
	craftingLeather = false,
	properLogout = false,
	secDbow = false,
	maxNextHit = false,
	ssSpec = false,
	vengOn = false,
	DuelTele = false,
	DuelCommand = false,
	addStarter = false,
	accountFlagged = false,
	msbSpec = false,
	CC = true
	;
	public static boolean disableyell = false;
	public int portalX = 0, portalY = 0, stuckX, stuckY;
	public String taggedPlayer = "";
	public String myCC;
	public String rememberedKit;
	public boolean hasPlacedPortalDown = false;
	public int randomMap = -1, totalGames, totalGameWins, insaneWins, extremeWins, toughWins, totalHungerGameExp, myKills, currentExpEarned, hasDiedYet = 0, kitOption, trollFace = 0, attackingPlayer, totalKills, sicknessPoisons;
	public static int lightningSound = 0, oldLightningSound = 0;
	public double meleePerk = 0, rangePerk = 0, magicPerk = 0;
	public boolean hasNpc = false;
	public int summonId;
    public int rememberNpcIndex;
	public int ratsCaught;
	public int
	resetPass,
	prizeAmount = 0,
	difficultLevel = 0,
	jumperTimer = 0,
	votingPoints,
	abyssKc = 0,
	dragonKc = 0,
	flowerX = 0,
	flowerY = 0,
	flowers = 0,
	gifts,
	startChristmas,
	spectateEx,
	wretchedUses = 0,
	wretchedId = -1,
	chestRoomTimer = -1,
	taggedUses = 0,
	voteTimes,
	lastGame = 0,
	retBleedinDam = 0,
	switchPerk = 0,
	myNumber = 0,
	myBet = 0,
	firstLogin,
	expBoost = 0,
	chaosAttack = 0,
	ipCount,
	chestsOpened, chestsOpened_2,
	noSmuggle, noSmuggleTwo,
	perkOne, perkTwo, perkThreeMelee, perkFourRange, perkFiveMagic, perkSixPray, perkSevenIce, perkEightStat, perkKaboom, perkGifted, perkSwitch, perkVeng, perkConnect, pumpkinPerk, beerPerk, perkConquer, perkYell, perkVengTwo, perkLazy,
	donatorPoints,
	saveDelay,
	playerKilled,
	pkPoints,
	totalPokes,
	totalPlayerDamageDealt,
	killedBy,
	lastChatId = 1,
	privateChat, 
	friendSlot = 0,
	duelScreen = 0,
	tradeScreen = 0,
	dialogueId, 
	randomCoffin, 
	newLocation, 
	specEffect, 
	follow2 = 0,
	specBarId, 
	attackLevelReq, 
	defenceLevelReq, 
	strengthLevelReq, 
	hitpointsLevelReq, 
	rangeLevelReq, 
	prayerLevelReq,
	magicLevelReq,
	cookingLevelReq,
	wcLevelReq,
	fletchingLevelReq,
	fishingLevelReq,
	fmLevelReq,
	craftingLevelReq,
	smithingLevelReq,
	miningLevelReq,
	herbloreLevelReq,
	agilityLevelReq,
	thievingLevelReq,
	slayerLevelReq,
	farmingLevelReq,
	rcLevelReq,
	followId, 
	skullTimer,
	empty,
	safeTimer = 0,
	nextChat = 0,
	talkingNpc = -1,
	dialogueAction = 0,
	Chest = 0,
	autocastId,
	followDistance,
	followId2,
	playerTitle,
	barrageCount = 0,
	delayedDamage = 0,
	delayedDamage2 = 0,
	stake = 0,
	pcPoints = 0,
	magePoints = 0,
	desertTreasure = 0,
	lastArrowUsed = -1,
	clanId = -1,
	autoRet = 0,
	pcDamage = 0,
	xInterfaceId = 0,
	xRemoveId = 0,
	xRemoveSlot = 0,
	tzhaarToKill = 0,
	tzhaarKilled = 0,
	waveId,
	frozenBy = 0,
	poisonDamage = 0,
	teleAction = 0,
	bonusAttack = 0,
	lastNpcAttacked = 0,
	portalOne = 0,
	portalTwo = 0,
	portalThree = 0,
	portalFour = 0,
	//shopOne = 0,
	shopTwo = 0,
	shopThree = 0,
	shopFour = 0,
	shopFive = 0,
	shopSix = 0,
	shopTen = 0,
	shopSeven = 0,
	optionOne = 0,
	WeaponStack = 0,
	resetInt = 0,
	KC,
	dAmount,
	DC,
	Respected,
	Reset,
	showStatus,
	voteChest,
	hStreak,
	cStreak,
	RangeMaxHit,
	MeleeMaxHit,
	MagicMaxHit,
	VengMaxHit,
	DropItem,
	PVP,
	Quest,
	Easter,
	Noclip,
	Dicing,
	screenInfo,
	votePoints,
	voteLevels,
	killCount = 0;
	public double conquerBonus = 0;
	public String clanName, properName, myKit = "", location = "", hgPerk = "";
	public int[] voidStatus = new int[5];
	
	public int statusPoints = 0;
	public String statusName = "";
	public int statusShop = 0;
	public int statusint = 0;
	
	public int[] titleStatus = new int[25];
	public int sShop = 0;
	
	public int[] itemKeptId = new int [4]; 
	public int[] pouches = new int[4];
	public final int[] POUCH_SIZE = {3,6,9,12};
	public boolean[] invSlot = new boolean[28], equipSlot = new boolean[14];
	public long friends[] = new long[200];
	public String kits[] = new String[25];
	public final String normkitList[] = { "Default", "Berserk", "Archer", "Elementalist", "Protected", "Alchemist", "Forsaken", "Sickness", "Saint", "Tagged", "Mercenary" };
	public final String donatorkitList[] = { "Perished", "Venom", "Blizzard", "Flash", "Looter", "Jumper", "Skiller", "Chaos", "Tainted", "Necromorpher", "Knight", "Wretched", "@or1@Divinity" };
	public int achievements[][] = new int[38][38];
	public final String achievementsStrings[] = { "Bloodthirsty", "Merciless", "Relentless", "Pirates Bane", "Treasure Hunter", "Deal or No Deal", "Boom Goes The Dynamite", "No Stone for This Sword", "Voter", "Plus Ten", "Luck of the Dice", "Always Geared", "Specialist", "Newbie", "Getting an Upgrade", "Time to Pk", "Hidden Treasure", "Makeover", "Champion", "Unstoppable", "What Does This Do", "I'm Cool", "Defiled", "Never Too Late", "Pacifist", "Alchemy is Real", "Insanity", "Fresh Start", "I'm Set", "Slayer", "Back From the Abyss", "Fearless", "Mage Killer", "White Knight", "Stone Crusher", "Not so Cold", "Grand Slayer", "Devil of The Games"  };
	public double specAmount = 0;
	public double specAccuracy = 1;
	public double specDamage = 1;
	public int packedCountPlayer = 0;
	public double prayerPoint = 1.0;
	public int teleGrabItem, teleGrabX, teleGrabY, duelCount, underAttackBy, underAttackBy2, wildLevel, teleTimer, respawnTimer, saveTimer = 0, teleBlockLength, poisonDelay;
	public long lastPlayerMove,lastassist,lastPoison,lastPoisonSip,poisonImmune,lastSpear,lastProtItem,lastWalk, dfsDelay,lastClaim,lastReward, lastVeng,lastYell,lastDupe,lastDeclineDupe,lastCommand,lastPoke,teleGrabDelay, protMageDelay, protMeleeDelay, protRangeDelay, lastAction, lastThieve,lastLockPick,lastPortal, alchDelay, talkDelay, specDelay = System.currentTimeMillis(), duelDelay, teleBlockDelay, godSpellDelay, singleCombatDelay, singleCombatDelay2, reduceStat, restoreStatsDelay, logoutDelay, buryDelay, foodDelay, potDelay, lastyell, lastAnvil, lastClick, lastFlash, lastJumperPortal, lastFact = System.currentTimeMillis(), stuckTimer, lastStuck, lastChest, lastStone, lastPrayerDec, lastMonkey, lastSickness, lastChaos, lastDesert, lastGameMess_1, lastGameMess_2, lastGameMess_3, lastGameMessage, lastOrb, lastGambleClick, lastBleeding, lastGift, lastSmite, lastNecro, lastNecro2, lastKnight, lastKnight2, lastCheck,lastBeer, lastTag, lastWretched, lastCC, lastSeed, lastSnow;
	public static long gambleTimer, gambleText, lotteryTimer, lastAchCheck;
	public boolean canChangeAppearance = false;
	public boolean mageAllowed;
	public long muteEnd;
	public byte poisonMask = 0;
	public boolean isNpc = false;
		public boolean isRules = false;
	public boolean Turmoil = false;
	public int npcId2 = 0;
	public int memberStatus = 0;
	public int betaPlayer = 0;
	public int donatorChest = 0; 
	public int BlackMarks = 0;
	public int vote = 0; 
	public String UUID = "";
	public boolean expLock = true;
	public String playerBan="null";
	public boolean isJailed;
	public int total1 = 0;
	public boolean isFullBody = false;
	public boolean isFullHelm = false;
	public boolean isFullMask = false;
	public int cluescroll = 0;
	public int said = 0;
	 public int getLocalX() {
         return getX() - 8 * getMapRegionX();
 }

 public int getLocalY() {
         return getY() - 8 * getMapRegionY();
 }

	public int overloadcounter = 0;
	public boolean clanDice = false;
	public long diceDelay;
	public int diceID = 15084;
	public int cDice = 0;
	public boolean logCommand = false;
	public boolean total500() {
		if ((playerLevel[0]) + 
		(playerLevel[1]) + 
		(playerLevel[2]) + 
		(playerLevel[3]) + 
		(playerLevel[4]) + 
		(playerLevel[5]) + 
		(playerLevel[6]) + 
		(playerLevel[7]) + 
		(playerLevel[8]) + 
		(playerLevel[9]) + 
		(playerLevel[10]) + 
		(playerLevel[11]) + 
		(playerLevel[12]) + 
		(playerLevel[13]) + 
		(playerLevel[14]) + 
		(playerLevel[15]) + 
		(playerLevel[16]) + 
		(playerLevel[17]) + 
		(playerLevel[18]) + 
		(playerLevel[19]) + 
		(playerLevel[20]) > 500) {
		total1 = 1;
		return true;
		}
		total1 = 0;
		return false;
	}
	public int randomSpot;
	
	public int spectateFalador[][] = {
		{ 2965, 3381 },
		{ 2945, 3370 },
		{ 2953, 3320 },
		{ 2971, 3342 },
		{ 3012, 3364 },
		{ 3039, 3355 },
		{ 3007, 3323 },
		{ 3166, 3673 }
	};
	
	public int spectateVarrock[][] = {
		{ 3213, 3429 },
		{ 3212, 3463 },
		{ 3216, 3493 },
		{ 3247, 3487 },
		{ 3246, 3429 },
		{ 3228, 3410 },
		{ 3251, 3389 },
		{ 3211, 3387 },
		{ 3198, 3415 },
		{ 3178, 3412 },
		{ 3182, 3436 },
		{ 3105, 3933 }
	};
	
	public int spectateCanfis[][] = {
		{ 3301, 2790 },
		{ 3308, 2770 },
		{ 3288, 2762 },
		{ 3283, 2784 },
		{ 3278, 2803 },
		{ 3255, 2797 },
		{ 3217, 2797 },
		{ 3238, 2781 },
		{ 3258, 2765 },
		{ 3217, 2768 },
		{ 3289, 3023 }//dm
	};
	
	public int spectateMisc[][] = {
		{ 2540, 3873 },
		{ 2517, 3884 },
		{ 2516, 3859 },
		{ 2542, 3849 },
		{ 2572, 3855 },
		{ 2540, 3891 },
		{ 2579, 3880 },
		{ 2603, 3875 },
		{ 2615, 3852 },
		{ 2611, 3892 },
		{ 2794, 3789 }//dm
	};
	public int[] sicknessFish = { 391, 361, 385, 379, 373 };
	int[] voteReward = {  15324, 15325, 15317, 4086, 4033, 4129, 2607, 2609, 2611, 2613, 2633, 2635, 2637, 2639, 2641, 2643, 2651, 7386, 7390, 7388, 7392, 7394, 7396, 7449, 7451, 7471, 7927, 6583, 10400, 10402, 10420, 10422, 1037, 1419 };
	public int voteReward() {
			return voteReward[(int) (Math.random() * voteReward.length)];
	}
	
	int[] voteRewardTwo = { 4716, 4747, 4751, 4734, 4736, 4712, 4759, 6570, 4151, 6737, 6918, 11694, 11696 };
	public int voteRewardTwo() {
			return voteRewardTwo[(int) (Math.random() * voteRewardTwo.length)];
	}
	int[] lampItems = { 3481, 3483, 3485, 3486, 3488, 3480, 3479, 3478, 3477, 3476, 3475, 3474, 3473, 3472, 3840, 3842, 3844, 3204, 2583, 2585, 2587, 2589, 2591, 2593, 2595, 2597, 2599, 2601, 2603, 2605, 2607, 2609, 2611, 2613, 2615, 2617, 2619, 2621, 2623, 2625, 2627, 2629, 2631, 2653, 2655, 2657, 2659, 2661, 2663, 2665, 2667, 2669, 2671, 2673, 2675, 11718, 11720, 11722 };
	public int lampItems() {
			return lampItems[(int) (Math.random() * lampItems.length)];
	}
	int[] xmasItem = { 3481, 3483, 3485, 3486, 3488, 3480, 3479, 3478, 3477, 3476, 3475, 3474, 3473, 3472, 3840, 3842, 3844, 3204, 2583, 2585, 2587, 2589, 2591, 2593, 2595, 2597, 2599, 2601, 2603, 2605, 2607, 2609, 2611, 2613, 2615, 2617, 2619, 2621, 2623, 2625, 2627, 2629, 2631, 2653, 2655, 2657, 2659, 2661, 2663, 2665, 2667, 2669, 2671, 2673, 2675, 11718, 11720, 11722, 6856, 6857, 6858, 6859, 6860, 6861, 6862, 6863 };
	public int xmasItem() {
			return xmasItem[(int) (Math.random() * xmasItem.length)];
	}
	public int freePartyHat;
	int[] partyHat = { 1038, 1040, 1042, 1044, 1046, 1048 };
	public int partyHat() {
			return partyHat[(int) (Math.random() * partyHat.length)];
	}
	public final int[] BOWS = 	{9185,839,845,847,851,855,859,841,843,849,853,857,861,4212,4214,4215,11235,4216,4217,4218,4219,4220,4221,4222,4223,6724,4734,4934,4935,4936,4937};
	public final int[] ARROWS = {882,884,886,888,890,892,4740,11212,9140,9141,4142,9143,9144,9240,9241,9242,9243,9244,9245};
	public final int[] NO_ARROW_DROP = {4212,4214,4215,4216,4217,4218,4219,4220,4221,4222,4223,4734,4934,4935,4936,4937};
	public final int[] OTHER_RANGE_WEAPONS = 	{863,864,865,866,867,868,869,806,807,808,809,810,811,825,826,827,828,829,830,800,801,802,803,804,805,6522};
	
	public final int[][] MAGIC_SPELLS = { 
	// example {magicId, level req, animation, startGFX, projectile Id, endGFX, maxhit, exp gained, rune 1, rune 1 amount, rune 2, rune 2 amount, rune 3, rune 3 amount, rune 4, rune 4 amount}
	
	// Modern Spells
	{1152,1,711,90,91,92,2,5,556,1,558,1,0,0,0,0}, //wind strike
	{1154,5,711,93,94,95,4,7,555,1,556,1,558,1,0,0}, // water strike
	{1156,9,711,96,97,98,6,9,557,2,556,1,558,1,0,0},// earth strike
	{1158,13,711,99,100,101,8,11,554,3,556,2,558,1,0,0}, // fire strike
	{1160,17,711,117,118,119,9,13,556,2,562,1,0,0,0,0}, // wind bolt
	{1163,23,711,120,121,122,10,16,556,2,555,2,562,1,0,0}, // water bolt
	{1166,29,711,123,124,125,11,20,556,2,557,3,562,1,0,0}, // earth bolt
	{1169,35,711,126,127,128,12,22,556,3,554,4,562,1,0,0}, // fire bolt
	{1172,41,711,132,133,134,13,25,556,3,560,1,0,0,0,0}, // wind blast
	{1175,47,711,135,136,137,14,28,556,3,555,3,560,1,0,0}, // water blast
	{1177,53,711,138,139,140,15,31,556,3,557,4,560,1,0,0}, // earth blast
	{1181,59,711,129,130,131,16,35,556,4,554,5,560,1,0,0}, // fire blast
	{1183,62,711,158,159,160,17,36,556,5,565,1,0,0,0,0}, // wind wave
	{1185,65,711,161,162,163,18,37,556,5,555,7,565,1,0,0},  // water wave
	{1188,70,711,164,165,166,19,40,556,5,557,7,565,1,0,0}, // earth wave
	{1189,75,711,155,156,157,20,42,556,5,554,7,565,1,0,0}, // fire wave
	{1153,3,716,102,103,104,0,13,555,3,557,2,559,1,0,0},  // confuse
	{1157,11,716,105,106,107,0,20,555,3,557,2,559,1,0,0},  // weaken
	{1161,19,716,108,109,110,0,29,555,2,557,3,559,1,0,0}, // curse
	{1542,66,729,167,168,169,0,76,557,5,555,5,566,1,0,0}, // vulnerability
	{1543,73,729,170,171,172,0,83,557,8,555,8,566,1,0,0}, // enfeeble
	{1562,80,729,173,174,107,0,90,557,12,555,12,556,1,0,0},  // stun
	{1572,20,711,177,178,181,0,30,557,3,555,3,561,2,0,0}, // bind
	{1582,50,711,177,178,180,2,60,557,4,555,4,561,3,0,0}, // snare
	{1592,79,711,177,178,179,4,90,557,5,555,5,561,4,0,0}, // entangle
	{1171,39,724,145,146,147,15,25,556,2,557,2,562,1,0,0},  // crumble undead
	{1539,50,708,87,88,89,25,42,554,5,560,1,0,0,0,0}, // iban blast
	{12037,50,1576,327,328,329,19,30,560,1,558,4,0,0,0,0}, // magic dart
	{1190,60,811,0,0,76,20,60,554,2,565,2,556,4,0,0}, // sara strike
	{1191,60,811,0,0,77,20,60,554,1,565,2,556,4,0,0}, // cause of guthix
	{1192,60,811,0,0,78,20,60,554,4,565,2,556,1,0,0}, // flames of zammy
	{12445,85,1819,0,344,345,0,65,563,1,562,1,560,1,0,0}, // teleblock
	
	// Ancient Spells
	{12939,50,1978,0,384,385,13,30,560,2,562,2,554,1,556,1}, // smoke rush
	{12987,52,1978,0,378,379,14,31,560,2,562,2,566,1,556,1}, // shadow rush
	{12901,56,1978,0,0,373,15,33,560,2,562,2,565,1,0,0},  // blood rush
	{12861,58,1978,0,360,361,16,34,560,2,562,2,555,2,0,0},  // ice rush
	{12963,62,1979,0,0,389,19,36,560,2,562,4,556,2,554,2}, // smoke burst
	{13011,64,1979,0,0,382,20,37,560,2,562,4,556,2,566,2}, // shadow burst 
	{12919,68,1979,0,0,376,21,39,560,2,562,4,565,2,0,0},  // blood burst
	{12881,70,1979,0,0,363,22,40,560,2,562,4,555,4,0,0}, // ice burst
	{12951,74,1978,0,386,387,23,42,560,2,554,2,565,2,556,2}, // smoke blitz
	{12999,76,1978,0,380,381,24,43,560,2,565,2,556,2,566,2}, // shadow blitz
	{12911,80,1978,0,374,375,25,45,560,2,565,4,0,0,0,0}, // blood blitz
	{12871,82,1978,366,0,367,26,46,560,2,565,2,555,3,0,0}, // ice blitz
	{12975,86,1979,0,0,391,27,48,560,4,565,2,556,4,554,4}, // smoke barrage
	{13023,88,1979,0,0,383,28,49,560,4,565,2,556,4,566,3}, // shadow barrage
	{12929,92,1979,0,0,377,29,51,560,4,565,4,566,1,0,0},  // blood barrage
	{12891,94,1979,0,0,369,30,52,560,4,565,2,555,6,0,0}, // ice barrage
	
	{-1,80,811,301,0,0,0,0,554,3,565,3,556,3,0,0}, // charge
	{-1,21,712,112,0,0,0,10,554,3,561,1,0,0,0,0}, // low alch
	{-1,55,713,113,0,0,0,20,554,5,561,1,0,0,0,0}, // high alch
	{-1,33,728,142,143,144,0,35,556,1,563,1,0,0,0,0} // telegrab

	};
	
	public int magicHitBonus() {
		if (playerEquipment[playerWeapon] == 15001) {
			return 4;
		} else if (playerEquipment[playerWeapon] == 6914) {
			return 3;
		} else if (playerEquipment[playerWeapon] == 15040) {
			return 6;
		} else {
			return 0;
		}
	}

	public int totalMagicBonus() {
		if (playerEquipment[playerAmulet] == 15000) {
			return 4 + magicHitBonus();
		} else if (perkFiveMagic > 0)  {
			if (myKit.equalsIgnoreCase("ELEMENATLIST")) {
				if (perkFiveMagic == 1)
					return 8 + magicHitBonus();
				else if (perkFiveMagic == 2)
					return 11 + magicHitBonus();
			} else if (necroMode && myKit.equalsIgnoreCase("NECROMORPHER")) {
				if (perkFiveMagic == 1)
					return 13 + magicHitBonus();
				else if (perkFiveMagic == 2)
					return 16 + magicHitBonus();
			} else {
				if (perkFiveMagic == 1)
					return 3 + magicHitBonus();
				else if (perkFiveMagic == 2)
					return 6 + magicHitBonus();
			}
		} else {
			if (myKit.equalsIgnoreCase("ELEMENATLIST")) {
				return 5 + magicHitBonus();
			} else if (necroMode && myKit.equalsIgnoreCase("NECROMORPHER")) {
				return 10 + magicHitBonus();
			}
		}
		return magicHitBonus();
	}
		
	public boolean isAutoButton(int button) {
		for (int j = 0; j < autocastIds.length; j += 2) {
			if (autocastIds[j] == button)
				return true;
		}
		return false;
	}
	
	public int[] autocastIds = {51133,32,51185,33,51091,34,24018,35,51159,36,51211,37,51111,38,51069,39,51146,40,51198,41,51102,42,51058,43,51172,44,51224,45,51122,46,51080,47,
								7038,0,7039,1,7040,2,7041,3,7042,4,7043,5,7044,6,7045,7,7046,8,7047,9,7048,10,7049,11,7050,12,7051,13,7052,14,7053,15,
								47019,27,47020,25,47021,12,47022,13,47023,14,47024,15};
								
	//public String spellName = "Select Spell";
	public void assignAutocast(int button) {
		for (int j = 0; j < autocastIds.length; j++) {
			if (autocastIds[j] == button) {
				Client c = (Client) Server.playerHandler.players[this.playerId];
				autocasting = true;
				autocastId = autocastIds[j+1];
				c.getPA().sendFrame36(108, 1);
				c.setSidebarInterface(0, 328);
				//spellName = getSpellName(autocastId);
				//spellName = spellName;
				//c.getPA().sendFrame126(spellName, 354);
				c = null;
				break;
			}		
		}	
	}
	public boolean isInDuel = false;
	public String getSpellName(int id) {
		switch (id) {
			case 0: return "Air Strike";
			case 1: return "Water Strike";
			case 2: return "Earth Strike";
			case 3: return "Fire Strike";
			case 4: return "Air Bolt";
			case 5: return "Water Bolt";
			case 6: return "Earth Bolt";
			case 7: return "Fire Bolt";
			case 8: return "Air Blast";
			case 9: return "Water Blast";
			case 10: return "Earth Blast";
			case 11: return "Fire Blast";
			case 12: return "Air Wave";
			case 13: return "Water Wave";
			case 14: return "Earth Wave";
			case 15: return "Fire Wave";
			case 32: return "Shadow Rush";
			case 33: return "Smoke Rush";
			case 34: return "Blood Rush";
			case 35: return "Ice Rush";
			case 36: return "Shadow Burst";
			case 37: return "Smoke Burst";
			case 38: return "Blood Burst";
			case 39: return "Ice Burst";
			case 40: return "Shadow Blitz";
			case 41: return "Smoke Blitz";
			case 42: return "Blood Blitz";
			case 43: return "Ice Blitz";
			case 44: return "Shadow Barrage";
			case 45: return "Smoke Barrage";
			case 46: return "Blood Barrage";
			case 47: return "Ice Barrage";
			default:
			return "Select Spell";
		}
	}
	
	public boolean fullVoidRange() {
		return playerEquipment[playerHat] == 11664 && playerEquipment[playerLegs] == 8840 && playerEquipment[playerChest] == 8839 && playerEquipment[playerHands] == 8842;
	}
	
	public boolean fullVoidMage() {
		return playerEquipment[playerHat] == 11663 && playerEquipment[playerLegs] == 8840 && playerEquipment[playerChest] == 8839 && playerEquipment[playerHands] == 8842;
	}
	
	public boolean fullVoidMelee() {
		return playerEquipment[playerHat] == 11665 && playerEquipment[playerLegs] == 8840 && playerEquipment[playerChest] == 8839 && playerEquipment[playerHands] == 8842;
	}
	
	public int[][] barrowsNpcs = {
	{2030, 0}, //verac
	{2029, 0}, //toarg
	{2028, 0}, // karil
	{2027, 0}, // guthan
	{2026, 0}, // dharok
	{2025, 0} // ahrim
	};
	public int barrowsKillCount;
	
	public int reduceSpellId;
	public final int[] REDUCE_SPELL_TIME = {2500, 2500, 2500, 5000, 5000, 5000}; // how long does the other player stay immune to the spell
	public long[] reduceSpellDelay = new long[6];
	public final int[] REDUCE_SPELLS = {1153,1157,1161,1542,1543,1562};
	public boolean[] canUseReducingSpell = {true, true, true, true, true, true};
	
	public int slayerTask,taskAmount;
	
	public int prayerId = -1;
	public int headIcon = -1;
	public int bountyIcon = 0;
	public long stopPrayerDelay, prayerDelay;
	public boolean usingPrayer;
		public final int[] PRAYER_DRAIN_RATE = 	{500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500,500};
	public final int[] PRAYER_LEVEL_REQUIRED = {1,4,7,8,9,10,13,16,19,22,25,26,27,28,31,34,37,40,43,44,45,46,49,52,60,70,95};
	public final int[] PRAYER = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};
	public final String[] PRAYER_NAME = {
		"Thick Skin", "Burst of Strength", "Clarity of Thought", "Sharp Eye", 
		"Mystic Will", "Rock Skin", "Superhuman Strength", "Improved Reflexes",
		"Rapid Restore", "Rapid Heal", "Protect Item", "Hawk Eye", "Mystic Lore", 
		"Steel Skin", "Ultimate Strength", "Incredible Reflexes","Protect from Magic", 
		"Protect from Missiles", "Protect from Melee","Eagle Eye", "Mystic Might", 
		"Retribution", "Redemption", "Smite", "Chivalry", "Piety", "Turmoil"
	};
	public boolean hasPoint = false;
	public final int[] PRAYER_GLOW = {83,84,85,601,602,86,87,88,89,90,91,603,604,92,93,94,95,96,97,605,606,98,99,100,607,608, 609};
	public final int[] PRAYER_HEAD_ICONS = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,2,1,0,-1,-1,3,5,4,-1,-1,-1};
	public boolean[] prayerActive = 			{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
	
	public int duelTimer, duelTeleX, duelTeleY, duelSlot, duelSpaceReq, duelOption, duelingWith, duelStatus;
	public int headIconPk = -1, headIconHints;
	public boolean duelRequested;
	public boolean[] duelRule = new boolean[22];
	public final int[] DUEL_RULE_ID = {1, 2, 16, 32, 64, 128, 256, 512, 1024, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 2097152, 8388608, 16777216, 67108864, 134217728};
	
	public boolean doubleHit, usingSpecial, npcDroppingItems, usingRangeWeapon, usingBow, usingMagic, castingMagic;
	public int specMaxHitIncrease, freezeDelay, freezeTimer = -6, killerId, playerIndex, oldPlayerIndex, lastWeaponUsed, projectileStage, crystalBowArrowCount, playerMagicBook, teleGfx, teleEndAnimation, teleHeight, teleX, teleY, rangeItemUsed, killingNpcIndex, totalDamageDealt, oldNpcIndex, fightMode, attackTimer, npcIndex,npcClickIndex, npcType, castingSpellId, oldSpellId, spellId, hitDelay;
	public boolean magicFailed, oldMagicFailed;
	public boolean openDuel = false;
	public boolean openTrade = false;
	public int lastWeapon;
	public boolean isUsingSpecial;
	public int bowSpecShot, clickNpcType, clickObjectType, objectId, objectX, objectY, objectXOffset, objectYOffset, objectDistance;
	public int pItemX, pItemY, pItemId;
	public boolean isMoving, walkingToItem;
	public boolean isShopping, updateShop;
	public int myShopId;
	public int tradeStatus, tradeWith;
	public boolean forcedChatUpdateRequired, inDuel, inDuelArena, inDice, tradeAccepted, goodTrade, inTrade, oneMark, tradeRequested, tradeResetNeeded, tradeConfirmed, tradeConfirmed2, canOffer, acceptTrade, acceptedTrade;
	public int attackAnim, animationRequest = -1,animationWaitCycles;
	public int[] playerBonus = new int[12];
	public boolean isRunning2 = true;
	public boolean takeAsNote;
	public int combatLevel;
	public boolean saveFile = false;
	public int playerAppearance[] = new int[13];
	public int apset;
	public int actionID;
	public int wearItemTimer, wearId, wearSlot, interfaceId;
	public int XremoveSlot, XinterfaceID, XremoveID, Xamount;
	public int droppedItem = -1;
	//public int tutorial = 15;
	public boolean usingGlory = false;
	public boolean usingROD = false;
	public boolean usingGamesNeck = false;
	public int[] woodcut = new int [3];
	public int wcTimer = 0;
	public int[] mining = new int [3];
	public int miningTimer = 0;
	public boolean fishing = false;
	public int fishTimer = 0;
	public int smeltType; //1 = bronze, 2 = iron, 3 = steel, 4 = gold, 5 = mith, 6 = addy, 7 = rune
	public int smeltAmount;
	public int smeltTimer = 0;
	public boolean smeltInterface;
	public boolean patchCleared, eventStarted = false;
	public int[] farm = new int[2];
	
	
	public boolean antiFirePot = false;
	
	/**
	 * Christmas Event 2013
	 */
	 
	public void handleXmasEvent(final Client c) {
		if (eventStarted)
			return;
		
		eventStarted = true;
		System.out.println(Server.playerHandler.players[c.playerId].playerName);
		EventManager.getSingleton().addEvent(new Event() {
			int count = 6, count2 = 15;
			boolean check = false;
			
			@Override
			public void execute(EventContainer t) {
				if (Server.playerHandler.players[c.playerId] == null) {
					t.stop();
					System.out.println("X-Mas cycle stopped because player logged during cutscene.");
					Server.npcHandler.npcActionEvent(1553, "", c.playerId * 2, 4);
					Server.npcHandler.npcActionEvent(1554, "", c.playerId * 2, 4);
					return;
				}
				if (Server.playerHandler.players[c.playerId].playerName.equals(c.playerName)) {
					//System.out.println(c.playerName);
					if (count == 6) {
						c.getPA().showInterface(18460);
						count--;
					} else if (count == 2) {
						c.getPA().movePlayer2(2841, 3496, 0);
						count--;
					} else if (count == 1) {
						startSnow = true;
						if (!check) {
							startSnow = true;
							c.getPA().movePlayer2(c.absX, c.absY, c.playerId * 2);
							c.getDH().sendDialogues(77, npcType);
							check = true;
						}
						if (count2 == 15) {
							Server.npcHandler.spawnNpc2(1553, 2836, 3496, c.heightLevel, 0, 10, 0, 0, 0);
							Server.npcHandler.spawnNpc2(1554, 2843, 3496, c.heightLevel, 0, 10, 0, 0, 0);
							Server.npcHandler.npcActionEvent(1553, "", c.playerId * 2, 0);
							Server.npcHandler.npcActionEvent(1554, "Arggssssshhh", c.playerId * 2, 0);
							count2--;
						} else if (count2 == 14) {
							Server.npcHandler.npcActionEvent(1553, "It's going to be such a nice Chirstmas, ho ho ho!", c.playerId * 2, 0);
							count2--;
						} else if (count2 == 13) {
							Server.npcHandler.npcActionEvent(1553, "What.. who are you?!", c.playerId * 2, 1);
							count2--;
						} else if (count2 == 11) {
							Server.npcHandler.npcActionEvent(1554, "Argshhhhhh, thoust nastai homan.", c.playerId * 2, 0);
							count2--;
						} else if (count2 == 10) {
							Server.npcHandler.npcActionEvent(1554, "Arrsggh!!!", c.playerId * 2, 1);
							count2--;
						} else if (count2 == 9) {
							Server.npcHandler.npcActionEvent(1553, "What, what?! NOOO!", c.playerId * 2, 2);
							count2--;
						} else if (count2 == 8) {
							Server.npcHandler.npcActionEvent(1554, "", c.playerId * 2, 2);
							count2--;
						} else if (count2 == 7) {
							Server.npcHandler.npcActionEvent(1553, "...", c.playerId * 2, 3);
							Server.npcHandler.npcActionEvent(1554, "Bwahtaaahaaa!!", c.playerId * 2, 3);
							count2--;
						} else if (count2 == 5) {
							c.getPA().showInterface(18460);
							count2--;
						} else if (count2 == 1) {
							Server.npcHandler.npcActionEvent(1553, "", c.playerId * 2, 4);
							Server.npcHandler.npcActionEvent(1554, "", c.playerId * 2, 4);
							c.getPA().showInterface(-1);
							c.getPA().movePlayer2(3364, 9641, 0);
							c.getDH().sendDialogues(78, npcType);
							startSnow = false;
							eventStarted = false;
							t.stop();
						} else {
							count2--;
						}
					} else {
						count--;
					}
				} else {
					t.stop();
					Server.npcHandler.npcActionEvent(1553, "", c.playerId * 2, 4);
					Server.npcHandler.npcActionEvent(1554, "", c.playerId * 2, 4);
					System.out.println("X-Mas cycle stopped because player logged during cutscene.");
				}
			}
		}, 1300);
	}
	
	/**
	 * Fight Pits
	 */
	public boolean inPits = false;
	public int pitsStatus = 0;
	
	/**
	 * SouthWest, NorthEast, SouthWest, NorthEast
	 */
		public void closeTrades() {
			if(inTrade && tradeResetNeeded){
			Client o = (Client) Server.playerHandler.players[tradeWith];
			if(o != null){
				if(o.tradeResetNeeded){
					((Client)this).getTradeAndDuel().resetTrade();
					o.getTradeAndDuel().resetTrade();
				}
			}
		}
	}
	
	/**
	 * Hunger Games
	 */
	 
	public int castleWarsTeam;
	public boolean inCwGame;
	public boolean inCwWait;
	
	/** Coords for Varrock HG zone.
	* 3174, 3448 North West of West Bank
	* 3201, 3399 South East of Pot shop
	*
	* 3182, 3398 South West corner
	* 3290, 3376 South East corner
	*
	* 3202, 3399 Beside other pot shop coords
	* 3273, 3436 Slightly north of east gate
	*
	* 3268, 3437 Beside east gate spot
	* 3187, 3475 West of castle
	*
	* 3190, 3476 West of castle
	* 3263, 3501 North East varrock, south of wildy
	*
	* Going to need a few more exact coords north of west bank, trying to keep all coords within walls
	**/
	public boolean inVarrockPvp() {
		if (absX >= 3174 && absX <= 3201 && absY <= 3448 && absY >= 3399 ||
			absX >= 3182 && absX <= 3290 && absY <= 3398 && absY >= 3376 ||
			absX >= 3202 && absX <= 3273 && absY >= 3399 && absY <= 3436 ||
			absX >= 3187 && absX <= 3268 && absY >= 3437 && absY <= 3475 ||
			absX >= 3190 && absX <= 3263 && absY >= 3476 && absY <= 3501 ||
			absX >= 3175 && absX <= 3195 && absY >= 3448 && absY <= 3456) {
			return true;
		}
		return false;
	}
	
	public boolean inFalPvp() {
		if (absX >= 2933 && absX <= 3060 && absY >= 3328 && absY <= 3394 ||
			absX >= 2935 && absX <= 3024 && absY >= 3306 && absY <= 3328) {
			return true;
		}
		return false;
	}
	
	public boolean inCanPvp() {
		if (absX >= 3203 && absX <= 3323 && absY >= 2752 && absY <= 2809) {
			return true;
		}
		return false;
	}
	
	public boolean inMiscPvp() {
		if (absX >= 2590 && absX <= 2599 && absY >= 3806 && absY <= 3815) {
			return false;
		}
		if (absX >= 2468 && absX <= 2644 && absY >= 2383 && absY <= 3901 ||
			absX >= 3543 && absX <= 3559 && absY >= 9687 && absY <= 9702) {
			return true;
		}
		return false;
	}
	
	public boolean inInfectedPvp() {
		if (absX >= 3470 && absX <= 3583 && absY >= 3161 && absY <= 3249) {
			return true;
		}
		return false;
	}
	
	public boolean inHomeArea() {
		if (absX >= 3240 && absX <= 3488 && absY >= 9550 && absY <= 9765) {
			return true;
		}
		return false;
	}
	
	public boolean inEggArea() {
		if (absX >= 3371 && absX <= 3389 && absY >= 9646 && absY <= 9663) {
			return true;
		}
		return false;
	}
	public boolean inEdgewilderness() {
		if (absX >= 2930 && absX <= 3110 && absY >= 3521 && absY <= 3590) {
			return true;
		}
		return false;
	}
	
	public boolean notallowedarea() {
		if((absX >= 3360 && absX <= 3666 && absY <= 9625 && absY >= 9623) ||
		(absX >= 3360 && absX <= 3666 && absY <= 9654 && absY >= 9656)) {
		return true;
	}
	return false;
}
	public boolean inPkingZone() {
		if (absX >= 2936 && absX <= 3151 && absY >= 3455 && absY <= 3575) {
			return true;
		}
		return false;
	}

	
	
	public boolean inBlockedout() {
		if (absX >= 2936 && absX <= 3162 && absY >= 3465 && absY <= 3455 ||
			absX >= 3152 && absX <= 3162 && absY >= 3456 && absY <= 3637 ||
			absX >= 2936 && absX <= 3162 && absY >= 3627 && absY <= 3637 ||
			absX >= 3199 && absX <= 3211 && absY >= 3508 && absY <= 3591) {
			return true;
		}
		return false;
	}
	
	public boolean inDeathBattle() {
		if (absX >= 3089 && absX <= 3123 && absY >= 3918 && absY <= 3947 ||
			absX >= 3147 && absX <= 3184 && absY >= 3658 && absY <= 3690 ||
			absX >= 3273 && absX <= 3305 && absY >= 3012 && absY <= 3038 ||
			absX >= 2770 && absX <= 2812 && absY >= 3764 && absY <= 3804){
			return true;
		}
		return false;
	}
	
	public boolean isInLobby() {		
		if (absX >= 3034 && absX <= 3045 && absY >= 2907 && absY <= 2918) {
			return true;
		}
		return false;
	}
	
	public boolean isInLobbyFal() {		
		if (absX >= 3113 && absX <= 3122 && absY >= 2886 && absY <= 2895) {
			return true;
		}
		return false;
	}
	
	public boolean isInLobbyCan() {		
		if (absX >= 3113 && absX <= 3122 && absY >= 2978 && absY <= 2987) {
			return true;
		}
		return false;
	}
	
	public boolean isInLobbyMisc() {		
		if (absX >= 2590 && absX <= 2599 && absY >= 3806 && absY <= 3815) {
			return true;
		}
		return false;
	}
	
	public boolean isInLobbyInfect() {		
		if (absX >= 3147 && absX <= 3191 && absY >= 9613 && absY <= 9638) {
			return true;
		}
		return false;
	}
	
	public boolean desertSand() {		
		if (absX >= 3205 && absX <= 3265 && absY >= 2752 && absY <= 2807) {
			return true;
		}
		return false;
	}
	
	public boolean inMulti() {
		if((absX >= 3174 && absX <= 3201 && absY <= 3448 && absY >= 3399) ||
			(absX >= 3182 && absX <= 3290 && absY <= 3398 && absY >= 3376) ||
			(absX >= 3202 && absX <= 3273 && absY >= 3399 && absY <= 3436) ||
			(absX >= 3187 && absX <= 3268 && absY >= 3437 && absY <= 3475) ||
			(absX >= 3190 && absX <= 3263 && absY >= 3476 && absY <= 3501) ||
			(absX >= 3089 && absX <= 3123 && absY >= 3918 && absY <= 3947) ||
			(absX >= 3027 && absX <= 3059 && absY >= 3329 && absY <= 3371) ||
			(absX >= 2940 && absX <= 3005 && absY >= 3327 && absY <= 3391) ||
			(absX >= 3147 && absX <= 3184 && absY >= 3658 && absY <= 3690) ||
			(absX >= 3203 && absX <= 3323 && absY >= 2752 && absY <= 2809) ||
			(absX >= 2770 && absX <= 2812 && absY >= 3764 && absY <= 3804) ||
			(absX >= 2468 && absX <= 2644 && absY >= 2383 && absY <= 3901)) {
			return true;
		}
		return false;
	}
	
	public boolean modZone() {
		if (absX >= 3351 && absX <= 3397 && absY >= 9798 && absY <= 9829) {
			return true;
		}
		return false;
	}
	
	public boolean isInJail() {		
		return false;
	}
	
	public boolean isInOldSchool() {		
		return false;
	}
	
	public boolean inBarrows() {		
		return false;
	}
	
	public boolean inTutorial() {		
		return false;
	}

	public boolean isInTut() {		
		return false;
	}
	
	public boolean inArea(int x, int y, int x1, int y1) {
		return false;
	}
	
	public boolean isInPVP() {		
		return false;
	}
	
	public boolean isInMinigame() {		
		return false;
	}
	
	public boolean inFunPk() {		
		return false;
	}
	
	public boolean inFreeForAll() {		
		return false;
	}
	
	public boolean inFunPkSafe() {		
		return false;
	}
	
	public boolean isInNoclip() {
		return false;
	}
	
	public boolean inStaffzone() {
		return true;
	}
	
	public boolean isInPVPSafe() {		
		return false;
	}
	
	public boolean inWild() {
		if (absX >= 2936 && absX <= 3151 && absY >= 3521 && absY <= 3575) {
			return true;
		}
		return false;
	}
	
	public boolean badZone() {
		if (absX >= 3102 && absX <= 3214 && absY >= 3521 && absY <= 3584 ||
			absX >= 3021 && absX <= 3114 && absY >= 3421 && absY <= 3438 ||
			absX >= 3101 && absX <= 3170 && absY >= 3456 && absY <= 3466 ||
			absX >= 2920 && absX <= 3068 && absY >= 3463 && absY <= 3466 ||
			absX >= 3063 && absX <= 3072 && absY >= 3450 && absY <= 3465 ||
			absX >= 3102 && absX <= 3150 && absY >= 3460 && absY <= 3471) {
			return true;
		}
		return false;
	}
	
	public boolean overWorld() {
		if (absX >= 2400 && absX <= 4100 && absY >= 2500 && absY <= 4100) {
			return true;
		}
		return false;
	}
	

	public boolean inWild2() {
		return true;
	}
	
	public boolean arenas() {
		return false;
	}
	
	public boolean inDuelArena() {
		return false;
	}

	public boolean rugAreas() {
		return false;
	}
	
	public boolean inFightCaves()
    {
       return false;
    }
	
	public boolean inPirateHouse() {
		return false;
	}
	
	public boolean inMasterZone() {
		return false;
	}
	
	public boolean inBoxingZone() {
		return false;
	}
	
	public boolean inDice() {
		return false;
	}


	public boolean inEdge() {
		return true;
	}
	
	public boolean isInBank() {
		if ((absX >= 3091 && absX <= 3098 && absY >= 3488 && absY <= 3499) || 
			(absX >= 3090 && absX <= 3090 && absY >= 3497 && absY <= 3493)) {
		return false;
		}
		return true;
	}
	
	public boolean inArena() {
		return true;
	}
	
	public boolean inDonatorzone() {
		return true;
	}
	
	public boolean inGhostspot() {
		return true;
	}

	public boolean inWildsong() {
		return true;
	}
	
	public boolean inAxeHut() {
		return false;
	}
	
	public String connectedFrom="";
	public String globalMessage="";
	public String connectedIsp = "";
	public String connectedMac = "";
	InetAddress connectedLocal;
	public int markedMarks;
	public abstract void initialize();
	public abstract void update();
	public int playerId = -1;		
	public String playerName = null;
	public String playerName2 = null;
	public String playerPass = null;			
	public int playerRights;		
	public PlayerHandler handler = null;
	public int playerItems[] = new int[28];
	public int playerItemsN[] = new int[28];
	public int bankItems[] = new int[Config.BANK_SIZE];
	public int bankItemsN[] = new int[Config.BANK_SIZE];
	public boolean bankNotes = false;
	public String saveText = "null";
	public String saveText2 = "null";
	public int Warning = 0;

		
	public int playerStandIndex = 0x328;//
	public int playerTurnIndex = 0x337;
	public int playerWalkIndex = 0x333;
	public int playerTurn180Index = 0x334;
	public int playerTurn90CWIndex = 0x335;
	public int playerTurn90CCWIndex = 0x336;
	public int playerRunIndex = 0x338;

	public int playerHat=0;
	public int playerCape=1;
	public int playerAmulet=2;
	public int playerWeapon=3;
	public int playerChest=4;
	public int playerShield=5;
	public int playerLegs=7;
	public int playerHands=9;
	public int playerFeet=10;
	public int playerRing=12;
	public int playerArrows=13;

	public int playerAttack = 0;
	public int playerDefence = 1;
	public int playerStrength = 2;
	public int playerHitpoints = 3;
	public int playerRanged = 4;
	public int playerPrayer = 5;
	public int playerMagic = 6;
	public int playerCooking = 7;
	public int playerWoodcutting = 8;
	public int playerFletching = 9;
	public int playerFishing = 10;
	public int playerFiremaking = 11;
	public int playerCrafting = 12;
	public int playerSmithing = 13;
	public int playerMining = 14;
	public int playerHerblore = 15;
	public int playerAgility = 16;
	public int playerThieving = 17;
	public int playerSlayer = 18;
	public int playerFarming = 19;
	public int playerRunecrafting = 20;
	
    public int[] playerEquipment = new int[14];
	public int[] wretchedBonus = new int[14];
	public int[] playerEquipmentN = new int[14];
	public int[] playerLevel = new int[25];
	public int[] playerXP = new int[25];
	
	public void updateshop(int i){
		Client p = (Client) Server.playerHandler.players[playerId];
		p.getShops().resetShop(i);
	}
	
	public void println_debug(String str) {
		System.out.println("[player-"+playerId+"]: "+str);
	}
	public void println(String str) {
		System.out.println("[player-"+playerId+"]: "+str);
	}
	public Player(int _playerId) {
		playerId = _playerId;
		playerRights = 0;

		for (int i=0; i<playerItems.length; i++) {
			playerItems[i] = 0;
		}
		for (int i=0; i<playerItemsN.length; i++) {
			playerItemsN[i] = 0;
		}

		for (int i=0; i<playerLevel.length; i++) {

				playerLevel[i] = 99;
		}

		for (int i=0; i<playerXP.length; i++) {
				playerXP[i] = 13500000;
		}
		
		for (int i=0; i < Config.BANK_SIZE; i++) {
			bankItems[i] = 0;
		}

		for (int i=0; i < Config.BANK_SIZE; i++) {
			bankItemsN[i] = 0;
		}
		
		playerAppearance[0] = 0; // gender
		playerAppearance[1] = 0; // head
		playerAppearance[2] = 18;// Torso
		playerAppearance[3] = 26; // arms
		playerAppearance[4] = 33; // hands
		playerAppearance[5] = 36; // legs
		playerAppearance[6] = 42; // feet
		playerAppearance[7] = 10; // beard
		playerAppearance[8] = 0; // hair colour
		playerAppearance[9] = 0; // torso colour
		playerAppearance[10] = 5; // legs colour
		playerAppearance[11] = 0; // feet colour
		playerAppearance[12] = 0; // skin colour	
		
		apset = 0;
		actionID = 0;

		playerEquipment[playerHat]=-1;
		playerEquipment[playerCape]=-1;
		playerEquipment[playerAmulet]=-1;
		playerEquipment[playerChest]=-1;
		playerEquipment[playerShield]=-1;
		playerEquipment[playerLegs]=-1;
		playerEquipment[playerHands]=-1;
		playerEquipment[playerFeet]=-1;
		playerEquipment[playerRing]=-1;
		playerEquipment[playerArrows]=-1;
		playerEquipment[playerWeapon]=-1;
		
		heightLevel = 0;
		
		teleportToX = Config.START_LOCATION_X;
		teleportToY = Config.START_LOCATION_Y;
		specAmount = 10;

		
		absX = absY = -1;
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}

	void destruct() {
		playerListSize = 0;
		for(int i = 0; i < maxPlayerListSize; i++) 
			playerList[i] = null;
		absX = absY = -1;
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}


	public static final int maxPlayerListSize = Config.MAX_PLAYERS;
	public Player playerList[] = new Player[maxPlayerListSize];
	public int playerListSize = 0;
	
	
	public byte playerInListBitmap[] = new byte[(Config.MAX_PLAYERS+7) >> 3];
	
	
	public static final int maxNPCListSize = NPCHandler.maxNPCs;
	public NPC npcList[] = new NPC[maxNPCListSize];
	public int npcListSize = 0;
	
	public byte npcInListBitmap[] = new byte[(NPCHandler.maxNPCs+7) >> 3];

	
	
	public boolean withinDistance(Player otherPlr) {
		if(heightLevel != otherPlr.heightLevel) return false;
		int deltaX = otherPlr.absX-absX, deltaY = otherPlr.absY-absY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public boolean withinDistance(NPC npc) {
		if (heightLevel != npc.heightLevel) return false;
		if (npc.needRespawn == true) return false;
		int deltaX = npc.absX-absX, deltaY = npc.absY-absY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public int distanceToPoint(int pointX,int pointY) {
		return (int) Math.sqrt(Math.pow(absX - pointX, 2) + Math.pow(absY - pointY, 2));
	}

	public int mapRegionX, mapRegionY;		
	public int absX, absY;				
	public int currentX, currentY;			
	
	public int heightLevel;		
	public int playerSE = 0x328; 
	public int playerSEW = 0x333; 
	public int playerSER = 0x334; 

	public boolean updateRequired = true;		
												
	
	public final int walkingQueueSize = 50;
    public int walkingQueueX[] = new int[walkingQueueSize], walkingQueueY[] = new int[walkingQueueSize];
	public int wQueueReadPtr = 0;		
	public int wQueueWritePtr = 0;		
	public boolean isRunning = true;
	public int teleportToX = -1, teleportToY = -1;	

	public void resetWalkingQueue() {
		wQueueReadPtr = wQueueWritePtr = 0;
		
		for(int i = 0; i < walkingQueueSize; i++) {
			walkingQueueX[i] = currentX;
			walkingQueueY[i] = currentY;
		}
	}
	

	public void addToWalkingQueue(int x, int y) {
		//if (VirtualWorld.I(heightLevel, absX, absY, x, y, 0)) {
			int next = (wQueueWritePtr+1) % walkingQueueSize;
			if(next == wQueueWritePtr) return;		
			walkingQueueX[wQueueWritePtr] = x;
			walkingQueueY[wQueueWritePtr] = y;
			wQueueWritePtr = next; 
		//}
	}

	public boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		for (int i = 0; i <= distance; i++) {
		  for (int j = 0; j <= distance; j++) {
			if ((objectX + i) == playerX && ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
				return true;
			} else if ((objectX - i) == playerX && ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
				return true;
			} else if (objectX == playerX && ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
				return true;
			}
		  }
		}
		return false;
	}
	
	public int getNextWalkingDirection() {
		if(wQueueReadPtr == wQueueWritePtr) 
			return -1;		
		int dir;
		do {
			dir = Misc.direction(currentX, currentY, walkingQueueX[wQueueReadPtr], walkingQueueY[wQueueReadPtr]);
			if(dir == -1) {
				wQueueReadPtr = (wQueueReadPtr+1) % walkingQueueSize;
			} else if((dir&1) != 0) {
				println_debug("Invalid waypoint in walking queue!");
				resetWalkingQueue();
				return -1;
			}
		} while((dir == -1) && (wQueueReadPtr != wQueueWritePtr));
		if(dir == -1) return -1;
		dir >>= 1;
		currentX += Misc.directionDeltaX[dir];
		currentY += Misc.directionDeltaY[dir];
		absX += Misc.directionDeltaX[dir];
		absY += Misc.directionDeltaY[dir];
		return dir;
	}

	
	public boolean didTeleport = false;		
	public boolean mapRegionDidChange = false;
	public int dir1 = -1, dir2 = -1;		
    public boolean createItems = false;
    public int poimiX = 0, poimiY = 0;
		
	public synchronized void getNextPlayerMovement() {
			mapRegionDidChange = false;
			didTeleport = false;
			dir1 = dir2 = -1;
	
			if(teleportToX != -1 && teleportToY != -1) {
				mapRegionDidChange = true;
				if(mapRegionX != -1 && mapRegionY != -1) {
					int relX = teleportToX-mapRegionX*8, relY = teleportToY-mapRegionY*8;
					if(relX >= 2*8 && relX < 11*8 && relY >= 2*8 && relY < 11*8)
						mapRegionDidChange = false;
				}
				if(mapRegionDidChange) {
					mapRegionX = (teleportToX>>3)-6;
					mapRegionY = (teleportToY>>3)-6;
				}
				currentX = teleportToX - 8*mapRegionX;
				currentY = teleportToY - 8*mapRegionY;
				absX = teleportToX;
				absY = teleportToY;
				resetWalkingQueue();
				
				teleportToX = teleportToY = -1;
				didTeleport = true;
			} else {			
				dir1 = getNextWalkingDirection();
				if(dir1 == -1) 
					return;
				if(isRunning) {
					dir2 = getNextWalkingDirection();
				}
				Client c = (Client)this;
				//c.sendMessage("Cycle Ended");	
				int deltaX = 0, deltaY = 0;
				if(currentX < 2*8) {
					deltaX = 4*8;
					mapRegionX -= 4;
					mapRegionDidChange = true;
				} else if(currentX >= 11*8) {
					deltaX = -4*8;
					mapRegionX += 4;
					mapRegionDidChange = true;
				}
				if(currentY < 2*8) {
					deltaY = 4*8;
					mapRegionY -= 4;
					mapRegionDidChange = true;
				} else if(currentY >= 11*8) {
					deltaY = -4*8;
					mapRegionY += 4;
					mapRegionDidChange = true;
				}
	
				if(mapRegionDidChange/* && VirtualWorld.I(heightLevel, currentX, currentY, currentX + deltaX, currentY + deltaY, 0)*/) {
					currentX += deltaX;
					currentY += deltaY;
					for(int i = 0; i < walkingQueueSize; i++) {
						walkingQueueX[i] += deltaX;
						walkingQueueY[i] += deltaY;
					}
				}
				//CoordAssistant.processCoords(this);
	
			}
	}

	
	public void updateThisPlayerMovement(Stream str) {
		synchronized(this) {
			if(mapRegionDidChange) {
				str.createFrame(73);
				str.writeWordA(mapRegionX+6);	
				str.writeWord(mapRegionY+6);
			}

			if(didTeleport) {
				str.createFrameVarSizeWord(81);
				str.initBitAccess();
				str.writeBits(1, 1);
				str.writeBits(2, 3);			
				str.writeBits(2, heightLevel);
				str.writeBits(1, 1);			
				str.writeBits(1, (updateRequired) ? 1 : 0);
				str.writeBits(7, currentY);
				str.writeBits(7, currentX);
				return ;
			}
			

			if(dir1 == -1) {
				// don't have to update the character position, because we're just standing
				str.createFrameVarSizeWord(81);
				str.initBitAccess();
				isMoving = false;
				if(updateRequired) {
					// tell client there's an update block appended at the end
					str.writeBits(1, 1);
					str.writeBits(2, 0);
				} else {
					str.writeBits(1, 0);
				}
				if (DirectionCount < 50) {
					DirectionCount++;
				}
			} else {
				DirectionCount = 0;
				str.createFrameVarSizeWord(81);
				str.initBitAccess();
				str.writeBits(1, 1);

				if(dir2 == -1) {
					isMoving = true;
					str.writeBits(2, 1);		
					str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
					if(updateRequired) str.writeBits(1, 1);		
					else str.writeBits(1, 0);
				}
				else {
					isMoving = true;
					str.writeBits(2, 2);		
					str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
					str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
					if(updateRequired) str.writeBits(1, 1);		
					else str.writeBits(1, 0);
				}
			}
		}
	}

	
	public void updatePlayerMovement(Stream str) {
		synchronized(this) {
			if(dir1 == -1) {
				if(updateRequired || isChatTextUpdateRequired()) {
					
					str.writeBits(1, 1);
					str.writeBits(2, 0);
				}
				else str.writeBits(1, 0);
			}
			else if(dir2 == -1) {
				
				str.writeBits(1, 1);
				str.writeBits(2, 1);
				str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
				str.writeBits(1, (updateRequired || isChatTextUpdateRequired()) ? 1: 0);
			}
			else {
				
				str.writeBits(1, 1);
				str.writeBits(2, 2);
				str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
				str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
				str.writeBits(1, (updateRequired || isChatTextUpdateRequired()) ? 1: 0);
			}
		}
	}

	
	public byte cachedPropertiesBitmap[] = new byte[(Config.MAX_PLAYERS+7) >> 3];

	public void addNewNPC(NPC npc, Stream str, Stream updateBlock) {
		synchronized(this) {
			int id = npc.npcId;
			npcInListBitmap[id >> 3] |= 1 << (id&7);	
			npcList[npcListSize++] = npc;
	
			str.writeBits(14, id);	
			
			int z = npc.absY-absY;
			if(z < 0) z += 32;
			str.writeBits(5, z);	
			z = npc.absX-absX;
			if(z < 0) z += 32;
			str.writeBits(5, z);	
	
			str.writeBits(1, 0); 
			str.writeBits(14, npc.npcType);
			
			boolean savedUpdateRequired = npc.updateRequired;
			npc.updateRequired = true;
			npc.appendNPCUpdateBlock(updateBlock);
			npc.updateRequired = savedUpdateRequired;	
			str.writeBits(1, 1); 
		}
	}
	
	public void addNewPlayer(Player plr, Stream str, Stream updateBlock) {
		synchronized(this) {
			if(playerListSize >= 255) {
				return;
			}
			int id = plr.playerId;
			playerInListBitmap[id >> 3] |= 1 << (id&7);
			playerList[playerListSize++] = plr;
			str.writeBits(11, id);	
			str.writeBits(1, 1);	
			boolean savedFlag = plr.isAppearanceUpdateRequired();
			boolean savedUpdateRequired = plr.updateRequired;
			plr.setAppearanceUpdateRequired(true);
			plr.updateRequired = true;
			plr.appendPlayerUpdateBlock(updateBlock);
			plr.setAppearanceUpdateRequired(savedFlag);
			plr.updateRequired = savedUpdateRequired;
			str.writeBits(1, 1);							
			int z = plr.absY-absY;
			if(z < 0) z += 32;
			str.writeBits(5, z);	
			z = plr.absX-absX;
			if(z < 0) z += 32;
			str.writeBits(5, z);
		}
	}

	public int DirectionCount = 0;
	public boolean appearanceUpdateRequired = true;	
	protected int hitDiff2;
	private int hitDiff = 0;
	protected boolean hitUpdateRequired2;
	private boolean hitUpdateRequired = false;
	public boolean isDead = false;
	
	protected static Stream playerProps;
	static {
		playerProps = new Stream(new byte[100]);
	}
	
	protected void appendPlayerAppearance(Stream str) {
		synchronized(this) {
			playerProps.currentOffset = 0;
			playerProps.writeByte(playerAppearance[0]);	
			playerProps.writeByte(headIcon);
			playerProps.writeByte(headIconPk);
			playerProps.writeByte(headIconHints);
			
			if (isNpc == false) {
				if (playerEquipment[playerHat] > 1) {
					playerProps.writeWord(0x200 + playerEquipment[playerHat]);
				} else {
					playerProps.writeByte(0);
				}
		
				if (playerEquipment[playerCape] > 1) {
					playerProps.writeWord(0x200 + playerEquipment[playerCape]);
				} else {
					playerProps.writeByte(0);
				}
		
				if (playerEquipment[playerAmulet] > 1) {
					playerProps.writeWord(0x200 + playerEquipment[playerAmulet]);
				} else {
					playerProps.writeByte(0);
				}
		
				if (playerEquipment[playerWeapon] > 1) {
					playerProps.writeWord(0x200 + playerEquipment[playerWeapon]);
				} else {
					playerProps.writeByte(0);
				}
		
				if (playerEquipment[playerChest] > 1) {
					playerProps.writeWord(0x200 + playerEquipment[playerChest]);
				} else {
					playerProps.writeWord(0x100+playerAppearance[2]);
				}
				
				if (playerEquipment[playerShield] > 1) {
					playerProps.writeWord(0x200 + playerEquipment[playerShield]);
				} else {
					playerProps.writeByte(0);
				}
				
				if (!isFullBody) {
					playerProps.writeWord(0x100+playerAppearance[3]);
				} else {
					playerProps.writeByte(0);
				}
				
				if (playerEquipment[playerLegs] > 1) {
					playerProps.writeWord(0x200 + playerEquipment[playerLegs]);
				} else {
					playerProps.writeWord(0x100+playerAppearance[5]);
				}
				
				if (!isFullHelm && !isFullMask) {
					playerProps.writeWord(0x100 + playerAppearance[1]);		
				} else {
					playerProps.writeByte(0);
				}
		
				if (playerEquipment[playerHands] > 1) {
					playerProps.writeWord(0x200 + playerEquipment[playerHands]);
				} else {
					playerProps.writeWord(0x100+playerAppearance[4]);
				}
				
				if (playerEquipment[playerFeet] > 1) {
					playerProps.writeWord(0x200 + playerEquipment[playerFeet]);
				} else {
					 playerProps.writeWord(0x100+playerAppearance[6]);
				}
					 
				if (playerAppearance[0] != 1 && !isFullMask) {
					playerProps.writeWord(0x100 + playerAppearance[7]);
				} else {
					playerProps.writeByte(0);
				}
			} else if (isNpc == true) {
				playerProps.writeWord(-1);
				playerProps.writeWord(npcId2);
			}
			
			if (playerAppearance[8] == playerAppearance[8]) {
				playerProps.writeByte(playerAppearance[8]);
			}
			if (playerAppearance[9] == playerAppearance[9]) {
				playerProps.writeByte(playerAppearance[9]);
			}
			if (playerAppearance[10] == playerAppearance[10]) {
				playerProps.writeByte(playerAppearance[10]);
			}
			if (playerAppearance[11] == playerAppearance[11]) {
				playerProps.writeByte(playerAppearance[11]);
			}
			if (playerAppearance[12] == playerAppearance[12]) {
				playerProps.writeByte(playerAppearance[12]);
			}
			
			playerProps.writeWord(playerStandIndex);		// standAnimIndex
			playerProps.writeWord(playerTurnIndex);		// standTurnAnimIndex
			playerProps.writeWord(playerWalkIndex);		// walkAnimIndex
			playerProps.writeWord(playerTurn180Index);		// turn180AnimIndex
			playerProps.writeWord(playerTurn90CWIndex);		// turn90CWAnimIndex
			playerProps.writeWord(playerTurn90CCWIndex);		// turn90CCWAnimIndex
			playerProps.writeWord(playerRunIndex);		// runAnimIndex	
		
			if (spectate == false || playerRights >= 1) {
				if (inWretched) {
					String brother = "";
					switch (wretchedId) {
						case 0:
							brother = "Ahrim";
						break;
						
						case 1:
							brother = "Dharok";
						break;
						
						case 2:
							brother = "Guthan";
						break;
						
						case 3:
							brother = "Karil";
						break;
						
						case 4:
							brother = "Torag";
						break;
						
						case 5:
							brother = "Verac";
						break;
					}
					playerProps.writeQWord(Misc.playerNameToInt64(brother));
				} else {
					playerProps.writeQWord(Misc.playerNameToInt64(playerName));
				}
			} else if (spectate == true && playerRights < 1) {
				playerProps.writeQWord(Misc.playerNameToInt64("Spectator"));
			}
			
			if (inCwGame || inCwWait || inPkingZone) {
				playerProps.writeQWord(Misc.playerNameToInt64(myKit));
			} else {
				playerProps.writeQWord(Misc.playerNameToInt64(""));
			}
			
			//
			int mag = (int) ((getLevelForXP(playerXP[6])) * 1.5);
			int ran = (int) ((getLevelForXP(playerXP[4])) * 1.5);
			int attstr = (int) ((double) (getLevelForXP(playerXP[0])) + (double) (getLevelForXP(playerXP[2])));
			if (ran > attstr) {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[4])) * 0.4875));
			} else if (mag > attstr) {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[6])) * 0.4875));
			} else {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125)
						+ ((getLevelForXP(playerXP[0])) * 0.325) + ((getLevelForXP(playerXP[2])) * 0.325));
			}
			//
			playerProps.writeByte(combatLevel);
			
			if (playerRights > 2) {
				playerProps.writeByte(trollFace);
			} else {
				playerProps.writeByte(0);
			}
			if (spectate == false) {
				playerProps.writeByte(0);
			} else if (spectate == true) {
				playerProps.writeByte(1);
			}
			if (lightningSound > oldLightningSound) {
				playerProps.writeWord(lightningSound);
			} else {
				playerProps.writeWord(lightningSound);
			}
			playerProps.writeWord(playerTitle);	
			str.writeByteC(playerProps.currentOffset);		
			str.writeBytes(playerProps.buffer, playerProps.currentOffset, 0);
		}
	}

	
	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor((double)lvl + 300.0 * Math.pow(2.0, (double)lvl / 7.0));
			output = (int)Math.floor(points / 4);
			if (output >= exp)
				return lvl;
		}
		return 99;
	}

	private boolean chatTextUpdateRequired = false;
	private byte chatText[] = new byte[4096];
	private byte chatTextSize = 0;
	private int chatTextColor = 0;
	private int chatTextEffects = 0;
	
	protected void appendPlayerChatText(Stream str) {
		synchronized(this) {
			str.writeWordBigEndian(((getChatTextColor()&0xFF) << 8) + (getChatTextEffects()&0xFF));
			str.writeByte(playerRights);
			str.writeByteC(getChatTextSize());		
			str.writeBytes_reverse(getChatText(), getChatTextSize(), 0);
		}
	}
	
	public void forcedChat(String text) {
		forcedText = text;
		forcedChatUpdateRequired = true;
		updateRequired = true;
		setAppearanceUpdateRequired(true);
	}
	public String forcedText = "null";
	public void appendForcedChat(Stream str) {
		synchronized(this) {
			str.writeString(forcedText);
		}
    }

	/**
	*Graphics
	**/
	
	public int mask100var1 = 0;
    public int mask100var2 = 0;
	protected boolean mask100update = false;
	
	public void appendMask100Update(Stream str) {
		synchronized(this) {
			str.writeWordBigEndian(mask100var1);
	        str.writeDWord(mask100var2);
		}
    }
		
	public void gfx100(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 6553600;
		mask100update = true;
		updateRequired = true;
	}
	public void gfx0(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 65536;
		mask100update = true;
		updateRequired = true;
	}
	
	public boolean wearing2h() {
		Client c = (Client)this;
		String s = c.getItems().getItemName(c.playerEquipment[c.playerWeapon]);
		if (s.contains("2h"))
			return true;
		else if (s.contains("godsword"))
			return true;
		return false;	
	}
	
	/**
	*Animations
	**/
	public void startAnimation(int animId) {
		if (wearing2h() && animId == 829)
			return;
		animationRequest = animId;
		animationWaitCycles = 0;
		updateRequired = true;
	}
	
	public void startAnimation(int animId, int time) {
		animationRequest = animId;
		animationWaitCycles = time;
		updateRequired = true;
	}

	public void appendAnimationRequest(Stream str) {
		synchronized(this) {
			str.writeWordBigEndian((animationRequest==-1) ? 65535 : animationRequest);
			str.writeByteC(animationWaitCycles);
		}
	}
	
	/** 
	*Face Update
	**/
	
	protected boolean faceUpdateRequired = false;
    public int face = -1;
	public int FocusPointX = -1, FocusPointY = -1;
	
	public void faceUpdate(int index) {
		face = index;
		faceUpdateRequired = true;
		updateRequired = true;
    }

	public void appendFaceUpdate(Stream str) {
		synchronized(this) {
			str.writeWordBigEndian(face);
		}
	}
	
	public void turnPlayerTo(int pointX, int pointY){
      FocusPointX = 2*pointX+1;
      FocusPointY = 2*pointY+1;
	  updateRequired = true;
    }
	
	private void appendSetFocusDestination(Stream str) {
		synchronized(this) {
			str.writeWordBigEndianA(FocusPointX);
	        str.writeWordBigEndian(FocusPointY);
		}
    }
	
	/** 
	*Hit Update
	**/
	
	protected void appendHitUpdate(Stream str) {
		synchronized(this) {
			str.writeByte(getHitDiff()); // What the perseon got 'hit' for
			if (poisonMask == 1) {
				str.writeByteA(2);
			} else if (getHitDiff() > 0) {
				str.writeByteA(1); // 0: red hitting - 1: blue hitting
			} else {
				str.writeByteA(0); // 0: red hitting - 1: blue hitting
			}
			if (playerLevel[3] <= 0) {
				playerLevel[3] = 0;
				isDead = true;	
			}
			str.writeByteC(playerLevel[3]); // Their current hp, for HP bar
			str.writeByte(getLevelForXP(playerXP[3])); // Their max hp, for HP bar
		}
	}
	
	
	protected void appendHitUpdate2(Stream str) {
		synchronized(this) {
			str.writeByte(hitDiff2); // What the perseon got 'hit' for
			if (poisonMask == 2) {
				str.writeByteS(2);
				poisonMask = -1;
			} else if (hitDiff2 > 0) {
				str.writeByteS(1); // 0: red hitting - 1: blue hitting
			} else {
				str.writeByteS(0); // 0: red hitting - 1: blue hitting
			}
			if (playerLevel[3] <= 0) {
				playerLevel[3] = 0;
				isDead = true;	
			}
			str.writeByte(playerLevel[3]); // Their current hp, for HP bar
			str.writeByteC(getLevelForXP(playerXP[3])); // Their max hp, for HP bar
		}
	}
	
	
	public void appendPlayerUpdateBlock(Stream str){
		synchronized(this) {
			if(!updateRequired && !isChatTextUpdateRequired()) return;		// nothing required
			int updateMask = 0;
			if(mask100update) {
				updateMask |= 0x100;
			}
			if(animationRequest != -1) {
				updateMask |= 8;
			}
			if(forcedChatUpdateRequired) {
				updateMask |= 4;
			}
			if(isChatTextUpdateRequired()) {
				updateMask |= 0x80;
			}
			if(isAppearanceUpdateRequired()) {
				updateMask |= 0x10;
			}
			if(faceUpdateRequired) {
				updateMask |= 1;
			}
			if(FocusPointX != -1) { 
				updateMask |= 2;
			}
			if (isHitUpdateRequired()) {
				updateMask |= 0x20;
			}
	
			if(hitUpdateRequired2) {
				updateMask |= 0x200;
			}
			
			if(updateMask >= 0x100) {
				updateMask |= 0x40;	
				str.writeByte(updateMask & 0xFF);
				str.writeByte(updateMask >> 8);
			} else {	
				str.writeByte(updateMask);
			}
	
			// now writing the various update blocks itself - note that their order crucial
			if(mask100update) {   
				appendMask100Update(str);
			}
			if(animationRequest != -1) {
				appendAnimationRequest(str);	
			}
			if(forcedChatUpdateRequired) {
				appendForcedChat(str);
			}
			if(isChatTextUpdateRequired()) {
				appendPlayerChatText(str);
			}
			if(faceUpdateRequired) {
				appendFaceUpdate(str);
			}
			if(isAppearanceUpdateRequired()) { 
				appendPlayerAppearance(str);
			}		
			if(FocusPointX != -1) { 
				appendSetFocusDestination(str);
			}
			if(isHitUpdateRequired()) {
				appendHitUpdate(str); 
			}
			if(hitUpdateRequired2) {
				appendHitUpdate2(str); 
			}
		}
	}

	public void clearUpdateFlags(){
		updateRequired = false;
		setChatTextUpdateRequired(false);
		setAppearanceUpdateRequired(false);
		setHitUpdateRequired(false);
		hitUpdateRequired2 = false;
		forcedChatUpdateRequired = false;
		mask100update = false;
		animationRequest = -1;
		FocusPointX = -1;
		FocusPointY = -1;
		poisonMask = -1;
		faceUpdateRequired = false;
        face = 65535;
	}

	public void stopMovement() {
        if(teleportToX <= 0 && teleportToY <= 0) {
            teleportToX = absX;
            teleportToY = absY;
        }
		newWalkCmdSteps = 0;
        getNewWalkCmdX()[0] = getNewWalkCmdY()[0] = travelBackX[0] = travelBackY[0] = 0;
        getNextPlayerMovement();
    }


	private int newWalkCmdX[] = new int[walkingQueueSize];
	private int newWalkCmdY[] = new int[walkingQueueSize];
	public int newWalkCmdSteps = 0;
	public int ClanChatSpam = 0;
	private boolean newWalkCmdIsRunning = false;
	protected int travelBackX[] = new int[walkingQueueSize];
	protected int travelBackY[] = new int[walkingQueueSize];
	protected int numTravelBackSteps = 0;

	public void preProcessing() {
		newWalkCmdSteps = 0;
	}

	public abstract void process();
	public abstract boolean processQueuedPackets();
	
	public synchronized void postProcessing() {
		if(newWalkCmdSteps > 0) {
			int firstX = getNewWalkCmdX()[0], firstY = getNewWalkCmdY()[0];	

			int lastDir = 0;
			boolean found = false;
			numTravelBackSteps = 0;
			int ptr = wQueueReadPtr;
			int dir = Misc.direction(currentX, currentY, firstX, firstY);
			
			if(dir != -1 && (dir&1) != 0) {				
				do {
					lastDir = dir;
					if(--ptr < 0) 
						ptr = walkingQueueSize-1;

					travelBackX[numTravelBackSteps] = walkingQueueX[ptr];
					travelBackY[numTravelBackSteps++] = walkingQueueY[ptr];
					dir = Misc.direction(walkingQueueX[ptr], walkingQueueY[ptr], firstX, firstY);
					if(lastDir != dir) {
						found = true;
						break;		
					}

				} while(ptr != wQueueWritePtr);
			}
			else found = true;	

			if(!found) println_debug("Fatal: couldn't find connection vertex! Dropping packet.");
			else {
				wQueueWritePtr = wQueueReadPtr;		

				addToWalkingQueue(currentX, currentY);	

				if(dir != -1 && (dir&1) != 0) {
					

					for(int i = 0; i < numTravelBackSteps-1; i++) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}
					int wayPointX2 = travelBackX[numTravelBackSteps-1], wayPointY2 = travelBackY[numTravelBackSteps-1];
					int wayPointX1, wayPointY1;
					if(numTravelBackSteps == 1) {
						wayPointX1 = currentX;
						wayPointY1 = currentY;
					}
					else {
						wayPointX1 = travelBackX[numTravelBackSteps-2];
						wayPointY1 = travelBackY[numTravelBackSteps-2];
					}
					
					dir = Misc.direction(wayPointX1, wayPointY1, wayPointX2, wayPointY2);
					if(dir == -1 || (dir&1) != 0) {
						println_debug("Fatal: The walking queue is corrupt! wp1=("+wayPointX1+", "+wayPointY1+"), "+
							"wp2=("+wayPointX2+", "+wayPointY2+")");
					}
					else {
						dir >>= 1;
						found = false;
						int x = wayPointX1, y = wayPointY1;
						while(x != wayPointX2 || y != wayPointY2) {
							x += Misc.directionDeltaX[dir];
							y += Misc.directionDeltaY[dir];
							if((Misc.direction(x, y, firstX, firstY)&1) == 0) {
								found = true;
								break;
							}
						}
						if(!found) {
							println_debug("Fatal: Internal error: unable to determine connection vertex!"+
								"  wp1=("+wayPointX1+", "+wayPointY1+"), wp2=("+wayPointX2+", "+wayPointY2+"), "+
								"first=("+firstX+", "+firstY+")");
						}
						else addToWalkingQueue(wayPointX1, wayPointY1);
					}
				}
				else {
					for(int i = 0; i < numTravelBackSteps; i++) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}
				}

				
				for(int i = 0; i < newWalkCmdSteps; i++) {
					addToWalkingQueue(getNewWalkCmdX()[i], getNewWalkCmdY()[i]);
				}

			}

			isRunning = isNewWalkCmdIsRunning() || isRunning2;
		}
	}
	
	public int getMapRegionX() {
		return mapRegionX;
	}
	public int getMapRegionY() {
		return mapRegionY;
	}
	
	public int getX() {
		return absX;
	}
	
	public int getY() {
		return absY;
	}
	
	public int getId() {
		return playerId;
	}
	


	public void setHitDiff(int hitDiff) {
		this.hitDiff = hitDiff;
	}
	
	public void setHitDiff2(int hitDiff2) {
		this.hitDiff2 = hitDiff2;
	}


	public int getHitDiff() {
		return hitDiff;
	}


	public void setHitUpdateRequired(boolean hitUpdateRequired) {
		this.hitUpdateRequired = hitUpdateRequired;
	}
	
	public void setHitUpdateRequired2(boolean hitUpdateRequired2) {
		this.hitUpdateRequired2 = hitUpdateRequired2;
	}


	public boolean isHitUpdateRequired() {
		return hitUpdateRequired;
	}
	
	public boolean getHitUpdateRequired() {
		return hitUpdateRequired;
	}
	
	public boolean getHitUpdateRequired2() {
		return hitUpdateRequired2;
	}


	public void setAppearanceUpdateRequired(boolean appearanceUpdateRequired) {
		this.appearanceUpdateRequired = appearanceUpdateRequired;
	}


	public boolean isAppearanceUpdateRequired() {
		return appearanceUpdateRequired;
	}


	public void setChatTextEffects(int chatTextEffects) {
		this.chatTextEffects = chatTextEffects;
	}


	public int getChatTextEffects() {
		return chatTextEffects;
	}


	public void setChatTextSize(byte chatTextSize) {
		this.chatTextSize = chatTextSize;
	}


	public byte getChatTextSize() {
		return chatTextSize;
	}


	public void setChatTextUpdateRequired(boolean chatTextUpdateRequired) {
		this.chatTextUpdateRequired = chatTextUpdateRequired;
	}


	public boolean isChatTextUpdateRequired() {
		return chatTextUpdateRequired;
	}


	public void setChatText(byte chatText[]) {
		this.chatText = chatText;
	}


	public byte[] getChatText() {
		return chatText;
	}


	public void setChatTextColor(int chatTextColor) {
		this.chatTextColor = chatTextColor;
	}


	public int getChatTextColor() {
		return chatTextColor;
	}


	public void setNewWalkCmdX(int newWalkCmdX[]) {
		this.newWalkCmdX = newWalkCmdX;
	}


	public int[] getNewWalkCmdX() {
		return newWalkCmdX;
	}


	public void setNewWalkCmdY(int newWalkCmdY[]) {
		this.newWalkCmdY = newWalkCmdY;
	}


	public int[] getNewWalkCmdY() {
		return newWalkCmdY;
	}


	public void setNewWalkCmdIsRunning(boolean newWalkCmdIsRunning) {
		this.newWalkCmdIsRunning = newWalkCmdIsRunning;
	}


	public boolean isNewWalkCmdIsRunning() {
		return newWalkCmdIsRunning;
	}

	private ISAACRandomGen inStreamDecryption = null, outStreamDecryption = null;
	
	public void setInStreamDecryption(ISAACRandomGen inStreamDecryption) {
		this.inStreamDecryption = inStreamDecryption;
	}

	public void setOutStreamDecryption(ISAACRandomGen outStreamDecryption) {
		this.outStreamDecryption = outStreamDecryption;
	}
	
	public boolean samePlayer() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (j == playerId)
				continue;
			if (Server.playerHandler.players[j] != null) {
				if (Server.playerHandler.players[j].playerName.equalsIgnoreCase(playerName)) {
					disconnected = true;
					return true;
				}	
			}
		}
		return false;	
	}
	
	public void putInCombat(int attacker) {
		underAttackBy = attacker;
		logoutDelay = System.currentTimeMillis();
		singleCombatDelay = System.currentTimeMillis();	
	}
	
	public void dealDamage(int damage) {
		if (teleTimer <= 0)
			playerLevel[3] -= damage;
		else {
			if (hitUpdateRequired)
				hitUpdateRequired = false;
			if (hitUpdateRequired2)
				hitUpdateRequired2 = false;
		}
	
	}
	
	public int[] damageTaken = new int[Config.MAX_PLAYERS];
	public boolean loggingIn;
	
	public void handleHitMask(int damage) {
		if (!hitUpdateRequired) {
			hitUpdateRequired = true;
			hitDiff = damage;
		} else if (!hitUpdateRequired2) {
			hitUpdateRequired2 = true;
			hitDiff2 = damage;		
		}
		updateRequired = true;
	}
	
}