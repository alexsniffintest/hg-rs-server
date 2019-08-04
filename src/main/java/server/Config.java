package server;


public class Config {

	public static final boolean SERVER_DEBUG = false;//needs to be false for Real world to work

	public static String SERVER_NAME = "HungerGames-RS";
	public static final String WELCOME_MESSAGE = "";
	public static final String FORUMS = "";

	public static final int CLIENT_VERSION = 999999;
	public static final boolean MYSQL_ACTIVE = true;
	public static int MESSAGE_DELAY = 6000;
	public static final int ITEM_LIMIT = 21000; // item id limit, different clients have more items like silab which goes past 15000
	public static final int MAXITEM_AMOUNT = Integer.MAX_VALUE;
	public static final int BANK_SIZE = 352;
	public static final int MAX_PLAYERS = 2048;

	public static final int CONNECTION_DELAY = 100; // how long one ip can keep connecting
	public static final int IPS_ALLOWED = 3; // how many ips are allowed

	public static final boolean WORLD_LIST_FIX = false; // change to true if you want to stop that world--8 thing, but it can cause the screen to freeze on silabsoft client
	
	public static boolean EVENT = false;

	public static final int[] ITEM_SELLABLE 		=	{7774,7775,7776,10934,10935,10936,621,622,6853,15344,455,10588,6542,6199,290,962,963,10394,4657,1037,15000,15313,15037,15038,15040,14479,15284,15285,13902,13738,13740,13742,13744,2116,773,15286,2422,4083,4084,3842,3844,3840,10833,8844,8845,8846,8847,8848,8849,8850, 10548, 7458,7462,7461,7460,7459,7458,7457,7456,7455,7454,8839,8840,8842,11663,11664,11665,10499,
														9748,9754,9751,9769,9757,9760,9763,9802,9808,9784,9799,9805,9781,9796,9793,9775,9772,9778,9787,9811,9766,
														9749,9755,9752,9770,9758,9761,9764,9803,9809,9785,9800,9806,9782,9797,9794,9776,9773,9779,9788,9812,9767,
														9747,4566,9753,9750,9768,9756,9759,9762,9801,9807,9783,9798,9804,9780,9795,9792,9774,9771,9777,9786,9810,9765,995,4447}; // what items can't be sold in any store
	public static final int[] ITEM_TRADEABLE 		= 	{}; // what items can't be traded or staked
	public static final int[] UNDROPPABLE_ITEMS 	= 	{2528}; // what items can't be dropped

	public static final int[] PKING_ITEM	=	{14484,15335,10887,10548,10551,15001,6570,11283,15018,15019,15020,15220,13736,13734,6889,6914,13899,13905,13864,13858,13861,13896,13884,13890,13876,13870,13873,13867,15319,15297,1961};
	
	public static final int[] FUN_WEAPONS	=	{10881,10862,11673,11672,11671,11670,11669,11668,11667,11666,10877,1215,1231,5680,5698,15333,15334,4151,15315,15301,15300,15299,15298}; // fun weapons for dueling
	
	public static final int[] OLD_WEAPONS	=	{171,1731704,177,179,141,143,2434,159,161,2440,3755,3753,3749,3751,229,147,149,2436,189,161,2440,165,167,2442,6570,10551,385,7946,379,145,157,163,169,139,175,4718,4734,4726,4747,4710,4755,4740,2417,2415,2416,4089,
													4091,4093,4095,4097,4099,4101,4103,4105,4107,4109,4111,4113,4115,4117,4675,1381,1383,1385,
													1387,2579,3840,3842,3844,9075,554,555,556,557,558,559,562,563,565,561,560,579,577,1011,4502,
													544,542,656,636,646,662,642,652,1035,1033,6109,6107,6108,6110,6106,6111,2414,2412,2413,6568,
													4333,4353,4373,4393,4413,626,632,2914,2922,7394,7396,1949,2651,6182,9244,9243,9242,892,890,
													10499,9185,9183,859,861,857,868,6522,2581,2577,1169,2503,2497,2491,2501,2495,2489,2499,2493,
													3204,1377,1249,1305,4587,1434,1215,5698,4153,6528,8845,8846,8847,8848,8849,8850,7454,7455,7456,
													7457,7458,7459,7460,7461,7462,1725,1729,1727,1712,6585,11128,2550,6737,6735,6733,6731,1540,4151,
													1153,1115,1067,1191,1157,1119,1069,1193,1165,1125,1077,1195,1159,1121,1071,1197,1161,1123,1073,
													1199,1163,1127,1079,1201,1149,3140,4087,1187,5574,5575,5576,6129,6130,3105,4121,4123,4125,4127,
													4129,4131,1333,1319,2487,1135,1099,1065,7370,7378,1133,7362,7366,7364,7368,1167,1129,1095,4212}; // Weapons for the oldschool minigame!

	public static boolean ADMIN_CAN_TRADE = true; //can admins trade?
	public static boolean ADMIN_CAN_SELL_ITEMS = false; // can admins sell items?
	public static boolean ADMIN_DROP_ITEMS = false; // can admin drop items?

	public static final int[] RARE_ITEM	=	{};

	public static final int[] FailItem = {9923,11064,11061,10877,10878,10879,10880,10881,10882,11061,10294,10295,10304,10305,10315,10703,10713,10292,10293,10302,10303,10312,10313,10707,10712,10290,10291,10300,10301,10310,10311,10701,10706,10288,10289,10299,10308,10309,10700,10705,10710,10286,10287,10296,10297,10306,10307,10699,10306,10307,10699,10704,10709,11079,11080,11081,11082,11083,11084,11413,11414,11415,11416,11417,11418,11419,11420,11406,11407,11408,11409,11410,11411,11412,11413,11399,11400,11401,11402,11403,11404,11405,11406,11381,11382,11383,11384,11385,11386,11387,11388,11375,11376,11377,11378,11379,11380,11381,11382,11370,11371,11373,11374,11375,11376,11377,11367,11368,11369,11370,11371,11372,11373,10501,10509,10510,10314,10315,10703,9946,11021,11019,8878,8876,8879,8877,9702,10071,10072,10074,10664,10822,11117,10150,10778,10780,10798,10776,10800,10782,3058,11037,11377,11259,10646,9906,9907,9908,9909,9910,9911,9912,10487,8929,5929,4033,9472,6199,10882,10880,9470,4033,10723,9944,9945,10825,10824,10823,11015,11022,11020,10069,8924,8925,8926,8927,9924,8962,8965,8992,8993,9013,8994,8995,8996,8997,8991,14499,11756,11757,11758,9948,11790,12004,11789,10010,12002,11022,9729,8872,4250,552,8871,806,807,808,809,810,811,812,813,814,815,816,817,818,819,820,821,822,823,824,1849,1853,3093,3094,5628,5629,5630,5631,5632,5633,5634,5635,5636,5637,5638,5639,5640,5641,7684,7685,11230,11231,11232,11233,11234,8952,8953,8954,8955,8956,8957,8958,13181,13182,13183,13184,13185,13186,13187,7447,11127,11126,11125,11124,11123,11122,11121,11120,11119,11118,11116,11115,11069,11070,11072,11073,11074,11075,11076,11077,11085,11086,11092,11093,11130,11131,11134,11133,8856,8928,8959,8960,10067,10156,10171,10500,10624,10627,10628,10725,10826,10858,10862,8961,8962,8963,8964,9006,9704,10047,10049,10061,10063,10065,8874,9920,10625,10053,10055,9912,10589,8967,10564,9907,8966,8968,8969,8970,89719729,10954,10956,10958,9730,11200,11201,9733,9734,9069,10941,10608,10045,10046,10039,10040,10608,9074,9073,9072,9071,9070,9069,9068,10623,10043,10044,10041,10042,10045,10046,10622,10040,10039,10038,10037,10036,10035,11136,11138,11140,10057,10058,10059,10060,10626}; //Those id are an example
	
	

	public static final int START_LOCATION_X = 3363; // start here
	public static final int START_LOCATION_Y = 9640;
	public static final int RESPAWN_X = 3363; // when dead respawn here
	public static final int RESPAWN_Y = 9640;
	public static final int DUELING_RESPAWN_X = 3362; // when dead in duel area spawn here
	public static final int DUELING_RESPAWN_Y = 3263;
	public static final int RANDOM_DUELING_RESPAWN = 5; // random coords

	public static final int NO_TELEPORT_WILD_LEVEL = 20; // level you can't tele on and above
	public static final int SKULL_TIMER = 1200; // how long does the skull last? seconds x 2
	public static final int TELEBLOCK_DELAY = 20000; // how long does teleblock last for.
	public static final boolean SINGLE_AND_MULTI_ZONES = true; // multi and single zones?
	public static final boolean COMBAT_LEVEL_DIFFERENCE = true; // wildy levels and combat level differences matters

	
	
	public static final boolean itemRequirements = true; // attack, def, str, range or magic levels required to wield weapons or wear items?
	
	/****
	CPANNEL
	***/
	public static boolean LOCK_EXPERIENCE = false;
	public static boolean MINI_GAMES = true;
	public static int MAX_NPCS = Server.npcHandler.maxNPCs;
	public static String LOGOUT_MESSAGE = "Click here to logout!";
	public static String DEATH_MESSAGE = "Oh dear you are dead!";
	public static boolean DOUBLE_EXP = true;
	//end
	
	public static final int MELEE_EXP_RATE = 0; // damage * exp rate
	public static final int RANGE_EXP_RATE = 0;
	public static final int MAGIC_EXP_RATE = 0;
	public static final double SERVER_EXP_BONUS = 0;

	public static final int INCREASE_SPECIAL_AMOUNT = 12500;
	public static final boolean PRAYER_POINTS_REQUIRED = true; // you need prayer points to use prayer
	public static final boolean PRAYER_LEVEL_REQUIRED = true; // need prayer level to use different prayers
	public static final boolean MAGIC_LEVEL_REQUIRED = true; // need magic level to cast spell
	public static final int GOD_SPELL_CHARGE = 300000; // how long does god spell charge last?
	public static final boolean RUNES_REQUIRED = true; // magic rune required?
	public static final boolean CORRECT_ARROWS = true; // correct arrows for bows?
	public static final boolean CRYSTAL_BOW_DEGRADES = true; // magic rune required?

	public static final int SAVE_TIMER = 120; // save every 1 minute
	public static final int NPC_RANDOM_WALK_DISTANCE = 5; // the square created , 3x3 so npc can't move out of that box when randomly walking
	public static final int NPC_FOLLOW_DISTANCE = 10; // how far can the npc follow you from it's spawn point,
	public static final int[] UNDEAD_NPCS = {90,91,92,93,94,103,104,73,74,75,76,77}; // undead npcs

	/**
	 * Barrows Reward
	 */


	/**
	 * Glory
	 */
	public static final int EDGEVILLE_X = 3087;
	public static final int EDGEVILLE_Y = 3500;
	public static final String EDGEVILLE = "";
	public static final int AL_KHARID_X = 3293;
	public static final int AL_KHARID_Y = 3174;
	public static final String AL_KHARID = "";
	public static final int KARAMJA_X = 3087;
	public static final int KARAMJA_Y = 3500;
	public static final String KARAMJA = "";
	public static final int MAGEBANK_X = 2538;
	public static final int MAGEBANK_Y = 4716;
	public static final String MAGEBANK = "";

	/**
	* Teleport Spells
	**/
	// modern
	public static final int VARROCK_X = 3213;
	public static final int VARROCK_Y = 3425;
	public static final String VARROCK = "";
	public static final int LUMBY_X = 3222;
	public static final int LUMBY_Y = 3218;
	public static final String LUMBY = "";
    public static final int FALADOR_X = 2964;
	public static final int FALADOR_Y = 3378;
	public static final String FALADOR = "";
	public static final int CAMELOT_X = 2757;
	public static final int CAMELOT_Y = 3477;
	public static final String CAMELOT = "";
	public static final int ARDOUGNE_X = 2662;
	public static final int ARDOUGNE_Y = 3305;
	public static final String ARDOUGNE = "";
	public static final int WATCHTOWER_X = 3087;
	public static final int WATCHTOWER_Y = 3500;
	public static final String WATCHTOWER = "";
	public static final int TROLLHEIM_X = 3243;
	public static final int TROLLHEIM_Y = 3513;
	public static final String TROLLHEIM = "";

	// ancient

	public static final int PADDEWWA_X = 3098;
	public static final int PADDEWWA_Y = 9884;

	public static final int SENNTISTEN_X = 3322;
	public static final int SENNTISTEN_Y = 3336;

    public static final int KHARYRLL_X = 3492;
	public static final int KHARYRLL_Y = 3471;

	public static final int LASSAR_X = 3006;
	public static final int LASSAR_Y = 3471;

	public static final int DAREEYAK_X = 3161;
	public static final int DAREEYAK_Y = 3671;

	public static final int CARRALLANGAR_X = 3156;
	public static final int CARRALLANGAR_Y = 3666;

	public static final int ANNAKARL_X = 3288;
	public static final int ANNAKARL_Y = 3886;

	public static final int GHORROCK_X = 2977;
	public static final int GHORROCK_Y = 3873;

	public static final int TIMEOUT = 20;
	public static final int CYCLE_TIME = 600;
	public static final int BUFFER_SIZE = 10000;

	/**
	 * Slayer Variables
	 */
	public static final int[][] SLAYER_TASKS = {{1,87,90,4,5}, //low tasks
												{6,7,8,9,10}, //med tasks
												{11,12,13,14,15}, //high tasks
												{1,1,15,20,25}, //low reqs
												{30,35,40,45,50}, //med reqs
												{60,75,80,85,90}}; //high reqs

	/**
	* Skill Experience Multipliers
	*/
	public static final int WOODCUTTING_EXPERIENCE = 0;
	public static final int MINING_EXPERIENCE = 0;
	public static final int SMITHING_EXPERIENCE = 0;
	public static final int FARMING_EXPERIENCE = 0;
	public static final int FIREMAKING_EXPERIENCE = 0;
	public static final int HERBLORE_EXPERIENCE = 0;
	public static final int FISHING_EXPERIENCE = 0;
	public static final int AGILITY_EXPERIENCE = 0;
	public static final int PRAYER_EXPERIENCE = 0;
	public static final int RUNECRAFTING_EXPERIENCE = 0;
	public static final int CRAFTING_EXPERIENCE = 0;
	public static final int THIEVING_EXPERIENCE = 0;
	public static final int SLAYER_EXPERIENCE = 0;
	public static final int COOKING_EXPERIENCE = 0;
	public static final int FLETCHING_EXPERIENCE = 0;
}
