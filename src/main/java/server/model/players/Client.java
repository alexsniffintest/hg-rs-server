package server.model.players;

import org.apache.mina.common.IoSession;
import server.Config;
import server.Server;
import server.event.Event;
import server.event.EventContainer;
import server.event.EventManager;
import server.model.items.Item;
import server.model.items.ItemAssistant;
import server.model.npcs.NPC;
import server.model.npcs.PetHandler;
import server.model.players.skills.*;
import server.model.shops.ShopAssistant;
import server.net.HostList;
import server.net.Packet;
import server.net.StaticPacketBuilder;
import server.util.MadTurnipConnection;
import server.util.Misc;
import server.util.Stream;
import server.world.ObjectManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;


public class Client extends Player {

    public DecimalFormat df = new DecimalFormat();
	public DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	public static int playerRank = 0;
	public static int[] ranks = new int[11];
	public static String[] rankPpl = new String[11];
	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private IoSession session;
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private PlayerKilling playerKilling = new PlayerKilling(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private Potions potions = new Potions(this);
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	/**
	 * Skill instances
	 */
	private Slayer slayer = new Slayer(this);
	private Runecrafting runecrafting = new Runecrafting(this);
	private Woodcutting woodcutting = new Woodcutting(this);
	private Mining mine = new Mining(this);
	private Agility agility = new Agility(this);
	private Cooking cooking = new Cooking(this);
	private Fishing fish = new Fishing(this);
	private Crafting crafting = new Crafting(this);
	private Smithing smith = new Smithing(this);
	private TradeLog tradeLog = new TradeLog(this);
	public TradeLog getTradeLog() {
		return tradeLog;
	}
	private CommandLog commandLog = new CommandLog(this);
	public CommandLog getCommandLog() {
		return commandLog;
	}
	private Prayer prayer = new Prayer(this);
	private Fletching fletching = new Fletching(this);
	private SmithingInterface smithInt = new SmithingInterface(this);
	private Farming farming = new Farming(this);
	private Thieving thieving = new Thieving(this);
	private Firemaking firemaking = new Firemaking(this);
	private Herblore herblore = new Herblore(this);
	private int somejunk;
	public int lowMemoryVersion = 0 , SPEC;
	public static int timeOutCounter = 0;
	public static int returnCode = 2;
	public static int interFacetimer = 0;
	public static int interFacetimer2 = 0;
	public static int interfaceEffect = 0;
	public static int messageText = 0;
	public static int lamp = 0;
	public static int lamp2 = 0;
	public static int vote2 = 0;
	public static int buff = 0;
	public int froze = 0;
	public int floweritem = 0;
	public int seedtimer = 0;
	public static int packagerandom = 0;
	//public static int staffonline = 0;
	private Future<?> currentTask;
	public String lastKilled = "";
	public static String gamblerName, gamblerOne, gamblerTwo, gambleNumOne, gambleNumTwo, gambleNumThree, gamblerOneNum , gamblerTwoNum, winningNumber;
	public static int gamblePot, firstGamblerAmount, messageCount = 0;
	public static boolean startDeathBattle = false, startDeathBattleFal = false, startDeathBattleCan = false, startDeathBattleMisc = false, gambleOn, gambleFinal, gambleNum1, gambleNum2, gambleNum3, startRollin, winnerGamble, failGamble, diceRolled, rollDaDice, gambleLogged, testmode = false, betaInfection = false, yellDisabled = false;
	public boolean attackSkill = false;
	public boolean strengthSkill = false;
	public boolean defenceSkill = false;
	public boolean isMorphed = false;
	public boolean mageSkill = false;
	public boolean rangeSkill = false;
	public boolean prayerSkill = false;
	public boolean healthSkill = false;
	public boolean usingCarpet = false;
	public boolean HasIt = false;
	public int itemBeforeCarpet;
	public int clawDelay = 0;
	public int previousDamage;
	public boolean usingClaws = false;
	public static int totalGamesPlayed, totalPlayersKilled, totalPlayersDied, totalChestsLooted, totalTraps, totalGrandChests, lottery;
	public static long totalExp;
	
	public final String modSetPass = "tyvb";
	public String modPass = "";
	
	public static ArrayList<Integer> edgePlayers = new ArrayList<Integer> ();
	public static String printException = "";
	
	public static int[][] chestInfo = {
		{ 3212, 3426, 0 },//Center Chests
		{ 3214, 3426, 0 },
		{ 3210, 3428, 0 },
		{ 3210, 3430, 0 },
		{ 3212, 3432, 0 },
		{ 3214, 3432, 0 },
		{ 3216, 3430, 0 },
		{ 3216, 3428, 0 }, //end of center chests
		{ 3226, 3437, 0 }, //beside blacksmith
		{ 3239, 3425, 0 }, //inside fletching place
		{ 3221, 3413, 0 }, //beside gen store
		{ 3209, 3440, 0 }, //inside part of inner wall, north of center
		{ 3225, 3461, 0 }, //right side of castle
		{ 3233, 3460, 0 }, //right side of castle by inner wall
		{ 3240, 3469, 0 }, //house left of yew tree
		{ 3261, 3474, 0 }, //east of church
		{ 3256, 3486, 0 }, //inside church
		{ 3238, 3431, 0 }, //south of fountain by blacksmith
		{ 3251, 3439, 0 }, //training room
		{ 3260, 3420, 0 }, //right side of east bank
		{ 3267, 3408, 0 }, //beside tea stand
		{ 3261, 3403, 0 }, //inside small house by mage shop
		{ 3247, 3402, 0 }, //west by mage shop
		{ 3242, 3385, 0 }, //small house south of mage shop
		{ 3255, 3380, 0 }, //chaos church
		{ 3234, 3382, 0 }, //small house, south wall
		{ 3225, 3385, 0 }, //house by south gate
		{ 3216, 3404, 0 }, //south bar
		{ 3227, 3401, 0 }, //bar inside
		{ 3205, 3397, 0 }, //sword shop
		{ 3202, 3393, 0 }, //south of sword shop
		{ 3190, 3382, 0 }, //south west building
		{ 3182, 3390, 0 }, //south west building
		{ 3182, 3393, 0 }, //south west building
		{ 3200, 3398, 0 }, //west of sword shop
		{ 3193, 3406, 0 }, //pot shop
		{ 3189, 3407, 0 }, //west of pot shop
		{ 3189, 3413, 0 }, //west of pot shop, south of anvil
		{ 3174, 3411, 0 }, //west wall
		{ 3174, 3407, 0 }, //west wall
		{ 3185, 3432, 0 }, //south of west bank
		{ 3180, 3441, 0 }, //inside west bank
		{ 3200, 3435, 0 }, //west of staff store
		{ 3209, 3459, 0 }, //inside castle walls
		{ 3215, 3459, 0 }, //inside castle walls
		{ 3203, 3473, 0 }, //castle
		{ 3201, 3479, 0 }, //castle
		{ 3202, 3482, 0 }, //castle
		{ 3212, 3489, 0 }, //castle
		{ 3206, 3494, 0 }, //castle
		{ 3233, 3495, 0 }, //castle
		{ 3216, 3502, 0 }, //castle
		{ 3203, 3501, 0 }, //castle
		{ 3200, 3480, 0 }, //castle
		{ 3200, 3460, 0 }, //castle
		{ 3220, 3479, 0 }, //castle
		{ 3225, 3472, 0 }, //castle
		{ 3217, 3468, 0 }, //castle
		{ 3222, 3463, 0 }, //castle
		{ 3239, 3445, 0 }, //outside castle wall
		{ 3258, 3450, 0 }, //north of training room
		{ 3257, 3438, 0 }, //north of training room
		{ 3250, 3422, 0 }, //east bank
		{ 3272, 3421, 0 }, //east wall
		{ 3252, 3407, 0 }, //north of rune shop
		{ 3251, 3406, 0 }, //north of rune shop
		{ 3238, 3416, 0 }, //castle
		{ 3179, 3403, 0 }, //east wall
		{ 3193, 3415, 0 }, //center
		{ 3199, 3423, 0 }, //by clothing shop
		{ 3191, 3445, 0 }, //west bank north west corner
		{ 3231, 3443, 0 }, //north of armor shop
		{ 3238, 3417, 0 }, //by east bank
		{ 3235, 3408, 0 }, //by jails
		{ 3237, 3401, 0 }, //by jails too
		{ 3241, 3384, 0 }, //south wall
		{ 3207, 3382, 0 }, //south wall
		{ 3203, 3464, 0 }, //castle fount
		{ 3217, 3480, 0 }, //castle
		{ 3219, 3482, 0 }, //castle
		{ 3228, 3470, 0 }, //castle
		{ 3235, 3480, 0 }, //castle
		{ 3251, 3487, 0 }, //north church
		{ 3250, 3500, 0 }, //north wall
		{ 3237, 3500, 0 }, //north wall
		{ 3198, 3414, 0 } //west side of clothing shop
	};
	
	public static int[][] chestInfoFal = {
		{ 2964, 3379, 0 },
		{ 2966, 3379, 0 },
		{ 2967, 3380, 0 },
		{ 2967, 3382, 0 },
		{ 2966, 3383, 0 },
		{ 2964, 3383, 0 },
		{ 2963, 3382, 0 },
		{ 2963, 3380, 0 },
		{ 2961, 3393, 0 },
		{ 2973, 3387, 0 },
		{ 2974, 3374, 0 },
		{ 2957, 3388, 0 },
		{ 2948, 3388, 0 },
		{ 2946, 3383, 0 },
		{ 2937, 3383, 0 },
		{ 2942, 3373, 0 },
		{ 2949, 3361, 0 },
		{ 2957, 3366, 0 },
		{ 2937, 3346, 0 },
		{ 2946, 3334, 0 },
		{ 2965, 3340, 0 },
		{ 2965, 3330, 0 },
		{ 2973, 3337, 0 },
		{ 2979, 3346, 0 },
		{ 2967, 3353, 0 },
		{ 2990, 3338, 0 },
		{ 2990, 3336, 0 },
		{ 2977, 3328, 0 },
		{ 2969, 3328, 0 },
		{ 2959, 3337, 0 },
		{ 2960, 3351, 0 },
		{ 2981, 3362, 0 },
		{ 2992, 3362, 0 },
		{ 3008, 3368, 0 },
		{ 2989, 3378, 0 },
		{ 2984, 3390, 0 },
		{ 2996, 3389, 0 },
		{ 3003, 3394, 0 },
		{ 3006, 3394, 0 },
		{ 3018, 3388, 0 },
		{ 3031, 3382, 0 },
		{ 3024, 3372, 0 },
		{ 3011, 3369, 0 },
		{ 3009, 3356, 0 },
		{ 3010, 3358, 0 },
		{ 3015, 3358, 0 },
		{ 3018, 3356, 0 },
		{ 3006, 3347, 0 },
		{ 3015, 3349, 0 },
		{ 3021, 3350, 0 },
		{ 3029, 3352, 0 },
		{ 3035, 3361, 0 },
		{ 3031, 3382, 0 },
		{ 3041, 3353, 0 },
		{ 3040, 3343, 0 },
		{ 3049, 3343, 0 },
		{ 3059, 3353, 0 },
		{ 3059, 3341, 0 },
		{ 3059, 3333, 0 },
		{ 3048, 3329, 0 },
		{ 3037, 3329, 0 },
		{ 3044, 3342, 0 },
		{ 3036, 3341, 0 },
		{ 3029, 3340, 0 },
		{ 3014, 3337, 0 },
		{ 3017, 3342, 0 },
		{ 3011, 3342, 0 },
		{ 3013, 3334, 0 },
		{ 3019, 3335, 0 },
		{ 3013, 3331, 0 },
		{ 3032, 3376, 0 },
		{ 3032, 3382, 0 },
		{ 3027, 3389, 0 },
		{ 3051, 3389, 0 },
		{ 3055, 3389, 0 },
		{ 3059, 3389, 0 },
		{ 3053, 3377, 0 },
		{ 3041, 3385, 0 },
		{ 3038, 3381, 0 },
		{ 3040, 3375, 0 },
		{ 3052, 3378, 0 },
		{ 3002, 3324, 0 },
		{ 3009, 3322, 0 },
		{ 3018, 3326, 0 },
		{ 2992, 3317, 0 },
		{ 2976, 3320, 0 },
		{ 2974, 3310, 0 },
		{ 2968, 3314, 0 },
		{ 2955, 3312, 0 },
		{ 2952, 3312, 0 },
		{ 2937, 3324, 0 },
		{ 2937, 3327, 0 }
	};
	
	public static int[][] chestInfoCan = {
	//Center starting chests
		{ 3301, 2789, 0 },
 		{ 3302, 2789, 0 },
		{ 3303, 2790, 0 },
 		{ 3303, 2791, 0 },
 		{ 3301, 2792, 0 },
		{ 3302, 2792, 0 },
		{ 3300, 2791, 0 },
 		{ 3300, 2790, 0 },

		//West Side
		{ 3206, 2807, 0 },
		{ 3270, 2785, 0 },
		{ 3268, 2792, 0 },
		{ 3266, 2800, 0 },
		{ 3269, 2776, 0 },
		{ 3268, 2767, 0 },
		{ 3260, 2754, 0 },
		{ 3251, 2754, 0 },
		{ 3240, 2765, 0 },
		{ 3241, 2765, 0 },
		{ 3242, 2785, 0 },
		{ 3244, 2785, 0 },
		{ 3244, 2786, 0 },
		{ 3242, 2786, 0 },
		{ 3205, 2783, 0 },
		{ 3205, 2795, 0 },
		{ 3205, 2762, 0 },
		{ 3205, 2766, 0 },
		{ 3216, 2776, 0 },
		{ 3216, 2777, 0 },//20
		{ 3242, 2806, 0 },
		{ 3250, 2806, 0 },
		{ 3278, 2784, 0 },


		//East side (You can start here)
 		{ 3315, 2796, 0 },
 		{ 3314, 2768, 0 },
 		{ 3320, 2777, 0 },
 		{ 3313, 2759, 0 },
 		{ 3295, 2765, 0 },
 		{ 3277, 2770, 0 },
 		{ 3277, 2768, 0 },
 		{ 3294, 2807, 0 },
 		{ 3297, 2780, 0 },
		{ 3292, 2780, 0 },
		{ 3310, 2796, 0 },
		{ 3313, 2803, 0 },
		{ 3320, 2802, 0 },
		{ 3318, 2782, 0 },
		{ 3317, 2792, 0 },
		{ 3297, 2807, 0 },
		{ 3283, 2809, 0 },
		{ 3273, 2809, 0 },
		{ 3282, 2800, 0 },
		{ 3282, 2789, 0 },
		{ 3285, 2775, 0 },
		{ 3285, 2766, 0 },
		{ 3282, 2758, 0 },
		{ 3284, 2753, 0 },
		{ 3305, 2755, 0 },
		{ 3313, 2753, 0 },
		{ 3319, 2753, 0 },
	};
	
	public static int[][] chestInfoMisc = {
		{ 2576, 3882, 0 },
		{ 2569, 3883, 0 },
		{ 2574, 3873, 0 },
		{ 2571, 3898, 0 },
		{ 2572, 3898, 0 },
		{ 2563, 3881, 0 },
		{ 2565, 3862, 0 },
		{ 2565, 3863, 0 },
		{ 2566, 3845, 0 },
		{ 2576, 3845, 0 },
		{ 2582, 3844, 0 },
		{ 2572, 3859, 0 },
		{ 2573, 3859, 0 },
		{ 2544, 3848, 0 },
		{ 2545, 3848, 0 },
		{ 2527, 3847, 0 },
		{ 2526, 3862, 0 },
		{ 2518, 3858, 0 },
		{ 2513, 3852, 0 },
		{ 2518, 3851, 0 },
		{ 2510, 3845, 0 },
		{ 2511, 3860, 0 },
		{ 2512, 3868, 0 },
		{ 2520, 3870, 0 },
		{ 2515, 3878, 0 },
		{ 2512, 3878, 0 },
		{ 2509, 3878, 0 },
		{ 2532, 3892, 0 },
		{ 2528, 3894, 0 },
		{ 2545, 3897, 0 },
		{ 2550, 3893, 0 },
		{ 2557, 3884, 0 },
		{ 2557, 3885, 0 },
		{ 2553, 3869, 0 },
		{ 2545, 3863, 0 },
		{ 2583, 3878, 0 },
		{ 2583, 3881, 0 },
		{ 2597, 3877, 0 },
		{ 2600, 3872, 0 },
		{ 2605, 3869, 0 },
		{ 2609, 3867, 0 },
		{ 2602, 3882, 0 },
		{ 2605, 3854, 0 },
		{ 2620, 3852, 0 },
		{ 2620, 3851, 0 },
		{ 2617, 3841, 0 },
		{ 2613, 3841, 0 },
		{ 2599, 3863, 0 },
		{ 2620, 3872, 0 },
		{ 2620, 3874, 0 },
		{ 2620, 3876, 0 },
		{ 2621, 3896, 0 },
		{ 2616, 3897, 0 },
		{ 2613, 3890, 0 },
		{ 2598, 3893, 0 },
		{ 2508, 3852, 0 },
		{ 2542, 3872, 0 },
		{ 2542, 3874, 0 },
		{ 2541, 3875, 0 },
		{ 2539, 3875, 0 },
		{ 2538, 3874, 0 },
		{ 2538, 3872, 0 },
		{ 2539, 3871, 0 },
		{ 2541, 3871, 0 },
		{ 2540, 3900, 0 },
		{ 2535, 3898, 0 },
		{ 2550, 3851, 0 },
		{ 2550, 3852, 0 },
		{ 2535, 3843, 0 },
		{ 2539, 3842, 0 },
		{ 3551, 9695, 0 },//Hidden grand chest
		{ 3546, 9694, 0 },
		{ 3546, 9695, 0 },
		{ 3551, 9700, 0 },
		{ 3552, 9700, 0 },
		{ 3557, 9695, 0 },
		{ 3557, 9694, 0 },
		{ 3552, 9689, 0 },
		{ 3551, 9689, 0 }
	};
	
	int[] rareItem = {  11732, 11728, 6570, 4585, 2577,11128, 2581, 3481, 3483, 3486, 3488, 4087, 4212, 4224, 4708, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730,  4732, 4734, 4736, 4738, 4745, 4747, 4749, 4753, 4755, 4757, 4759, 4708, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730,  4732, 4734, 4736, 4738, 4745, 4747, 4749, 4753, 4755, 4757, 4759, 6585, 6585, 6731, 6733, 6735, 6737, 6914, 6916, 6918, 6924, 9751, 9748, 9754, 9757, 9763, 9769, 4151, 4151, 4151, 4151, 1187, 1377, 7462, 7462, 11665, 11664, 11663, 8842, 8840, 8839, 11690, 11690 };
	public int rareItem() {
			return rareItem[(int) (Math.random() * rareItem.length)];
	}
	
	int[] grandChest = { 11235, 11730, 11726, 11724, 11722, 11720, 11718, 11235, 11730, 11726, 11724, 11722, 11720, 11718, 11702, 11704, 11706, 11708, 11690, 11283 };
	public int grandChest() {
			return grandChest[(int) (Math.random() * grandChest.length)];
	}
	
	public void clueScroll(int i1, int a1, int i2, int a2, int i3, int a3, int i4,int a4, int casketID){
		getPA().showInterface(6960);
		getPA().sendFrame34a(6963, i1, 0,a1);
		getPA().sendFrame34a(6963, i2, 1, a2);
		getPA().sendFrame34a(6963, i3, 2, a3);
		getPA().sendFrame34a(6963, i4, 3, a4);
		getItems().addItem(i1,a1);
		getItems().addItem(i2,a2);
		getItems().addItem(i3,a3);
		getItems().addItem(i4,a4);
		getItems().deleteItem(casketID, getItems().getItemSlot(casketID), 1);
	}

	public Client(IoSession s, int _playerId) {
		super(_playerId);
		this.session = s;
			outStream = new Stream(new byte[Config.BUFFER_SIZE]);
			outStream.currentOffset = 0;
		
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
	}
	
	public void firstLogin() {
		getDH().sendDialogues(1, 619);
	}
	
	/**
	 * Shakes the player's screen.
	 * Parameters 1, 0, 0, 0 to reset.
	 * @param verticleAmount How far the up and down shaking goes (1-4).
	 * @param verticleSpeed How fast the up and down shaking is.
	 * @param horizontalAmount How far the left-right tilting goes.
	 * @param horizontalSpeed How fast the right-left tiling goes..
	 */
	public void shakeScreen(int verticleAmount, int verticleSpeed, int horizontalAmount, int horizontalSpeed) {
			outStream.createFrame(35); // Creates frame 35.
			outStream.writeByte(verticleAmount);
			outStream.writeByte(verticleSpeed);
			outStream.writeByte(horizontalAmount);
			outStream.writeByte(horizontalSpeed);
	}
	
	public static int diceFails = 0;
	public static boolean lotteryMessage;
	public void cleanObjects() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client o = (Client)Server.playerHandler.players[j];
				o.myBet = 0;
				o.myNumber = 0;
				o.firstGamble = false;
				o.gambleValue = false;
				o.reloadHGstuff();
			}
		}
		gambleLogged = false;
		gambleOn = false;
		gambleNum1 = false;
		gambleNum2 = false;
		gambleNum3 = false;
		firstGamble = false;
		gamblerOne = null;
		gamblerTwo = null;
		startRollin = false;
		gambleFinal = false;
		gamblerOneNum = null;
		gamblerTwoNum = null;
		gamblePot = 0;
		diceFails = 0;
		firstGamblerAmount = 0;
	}

	public void rollDice() {
		int roll = (int) (Math.random() * 3);
		System.out.println(roll);
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client o = (Client)Server.playerHandler.players[j];
				if (gamblerOne.equals(o.playerName) || gamblerTwo.equals(o.playerName)) {
					if (gambleLogged) {
						diceRolled = true;
						winnerGamble = true;
						firstGamble = false;
						winningNumber = "Other player logged, " + o.playerName + " wins by default!";
						o.sendMessage("Congratulations you've won " + gamblePot + " experience.");
						Server.npcHandler.npcAction(3372, "Congratulations, " + o.playerName + " just won the bet!", 0);
						o.totalHungerGameExp += gamblePot;
						cleanObjects();
						o.reloadHGstuff();
						break;
					}
					if (roll == 0 && o.myNumber == 1) {
						diceRolled = true;
						winnerGamble = true;
						firstGamble = false;
						winningNumber = "One";
						o.sendMessage("Congratulations you've won " + gamblePot + " experience.");
						o.totalHungerGameExp += gamblePot;
						if (gamblePot >= 1000000 && o.achievements[10][0] == 0) {
							o.achievements[10][1] = 1;
							o.achievementsHandler();
						}
						cleanObjects();
						o.reloadHGstuff();
						break;
					} else if (roll == 1 && o.myNumber == 2) {
						diceRolled = true;
						winnerGamble = true;
						firstGamble = false;
						winningNumber = "Two";
						o.sendMessage("Congratulations you've won " + gamblePot + " experience.");
						o.totalHungerGameExp += gamblePot;
						if (gamblePot >= 1000000 && o.achievements[10][0] == 0) {
							o.achievements[10][1] = 1;
							o.achievementsHandler();
						}
						cleanObjects();
						o.reloadHGstuff();
						break;
					} else if (roll == 2 && o.myNumber == 3) {
						diceRolled = true;
						winnerGamble = true;
						firstGamble = false;
						winningNumber = "Three";
						o.sendMessage("Congratulations you've won " + gamblePot + " experience.");
						o.totalHungerGameExp += gamblePot;
						if (gamblePot >= 1000000 && o.achievements[10][0] == 0) {
							o.achievements[10][1] = 1;
							o.achievementsHandler();
						}
						cleanObjects();
						o.reloadHGstuff();
						break;
					}
				}
			}
		}
		if (!winnerGamble) {
			if (diceFails < 3) {
				diceRolled = true;
				failGamble = true;
				diceFails++;
			} else if (diceFails == 3) {
				diceRolled = true;
				lottery += gamblePot;
				lotteryMessage = true;
				cleanObjects();
			}
		}
	}
	
	public void gambleNumber() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client o = (Client)Server.playerHandler.players[j];
				if (o.myNumber > 0 && gamblerOne.equals(o.playerName)) {
					if (o.myNumber == 1) {
						gamblerOneNum = "One";
					} else if (o.myNumber == 2) {
						gamblerOneNum = "Two";
					} else if (o.myNumber == 3) {
						gamblerOneNum = "Three";
					}
				} else if (o.myNumber > 0 && gamblerTwo.equals(o.playerName)) {
					if (o.myNumber == 1) {
						gamblerTwoNum = "One";
					} else if (o.myNumber == 2) {
						gamblerTwoNum = "Two";
					} else if (o.myNumber == 3) {
						gamblerTwoNum = "Three";
					}
				}
			}
		}
	}
	
	int[] dpitems = { 2675, 2671, 2673, 2667, 2665, 2663, 2659, 2657, 2655, 4151, 4151, 10460, 10468, 10462, 10466, 10458, 10464, 11724, 11726, 11718, 11720, 11722, 4507, 4072, 4512, 1050, 10348, 10346, 4151, 11283, 7927, 3481, 3483, 3486, 3488,3481, 3483, 3485, 3486, 3488, 3480, 3479, 3478, 3477, 3476, 3475, 3474, 3473, 3472, 3840, 3842, 3844, 3204, 2583, 2585, 2587, 2589, 2591, 2593, 2595, 2597, 2599, 2601, 2603, 2605, 2607, 2609, 2611, 2613, 2615, 2617, 2619, 2621, 2623, 2625, 2627, 2629, 2631, 2653, 2655, 2657, 2659, 2661, 2663, 2665, 2667, 2669, 2671, 2673, 2675, 11718, 11720, 11722, 15324, 15325, 15317, 4086, 4033, 4129, 2607, 2609, 2611, 2613, 2633, 2635, 2637, 2639, 2641, 2643, 2651, 7386, 7390, 7388, 7392, 7394, 7396, 7449, 7451, 7471, 7927, 6583, 10400, 10402, 10420, 10422, 1037, 1419, 4716, 4747, 4751, 4734, 4736, 4712, 4759, 6570, 4151, 6737, 6918, 11694, 11696, 15316, 15317, 15328, 15328, 15329, 15330, 15324, 15318, 15319, 15320, 15321, 15322, 15323, 15324, 11732, 11728, 6570, 4585, 2577,11128, 2581, 3481, 3483, 3486, 3488, 4087, 4212, 4224, 4708, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4753, 4755, 4757, 4759, 4708, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730,  4732, 4734, 4736, 4738, 4745, 4747, 4749, 4753, 4755, 4757, 4759, 6585, 6585, 6731, 6733, 6735, 6737, 6914, 6916, 6918, 6924, 9751, 9748, 9754, 9757, 9763, 9769, 4151, 4151, 4151, 4151, 1187, 1377, 7462, 7462, 11665, 11664, 11663, 8842, 8840, 8839, 11690, 11690, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4753, 4755, 4757, 4759, 4708, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730,  4732, 4734, 4736, 4738, 4745, 4747, 4749, 4753, 4755, 4757, 4759, 1959, 3802 }; /* Add all of the item IDS here */

	public int dpitems() {
			return dpitems[(int) (Math.random() * dpitems.length)];
	}
	
	public void dropparty(final Client c, final int size, final int amount, final int time) {
		for(int i = 0; i < Config.MAX_PLAYERS; i++) {
			if(Server.playerHandler.players[i] != null) {
				Client c1 = (Client)Server.playerHandler.players[i];
				c1.sendMessage("[@red@SERVER@bla@] The drop party is now starting!");
				c1.sendMessage("[@red@SERVER@bla@] " + amount + " items will be dropped at " + c.absX + ", " + c.absY + " within " + size + " blocks every " + (time/1000) + " seconds!");
			}
		}
		EventManager.getSingleton().addEvent(new Event() {
			int count = 0, xPos = c.absX, yPos = c.absY;
		
			@Override
			public void execute(EventContainer b) {
				if(count == amount) {
					b.stop();
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							Client c1 = (Client)Server.playerHandler.players[i];
							c1.sendMessage("The drop party is @red@now over@bla@, hope everyone enjoyed!");
						}
					}
				} else {	
					Server.itemHandler.createGroundItem(c, dpitems(), (xPos - size) + Misc.random(size * 2), (yPos - size) + Misc.random(size * 2), 1, c.getId());
					count++;
				}
			}
		}, time);
	};
	
	/**
	* Resets the shaking of the player's screen.
	*/
	public void resetShaking() {
			shakeScreen(1, 0, 0, 0);
	}

	public void flushOutStream() {
		if(disconnected || outStream.currentOffset == 0) return;
			StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
			byte[] temp = new byte[outStream.currentOffset];
			System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
			out.addBytes(temp);
			session.write(out.toPacket());
			outStream.currentOffset = 0;
		
    }

	public void sendClan(String name, String message, String clan, int rights) {
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}

	public static final int PACKET_SIZES[] = {
		0, 0, 0, 1, -1, 0, 0, 0, 0, 0, //0
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0,  //10
		0, 2, 0, 6, 0, 12, 0, 0, 0, 0, //20
		0, 0, 0, 0, 0, 8, 4, 0, 0, 2,  //30
		2, 6, 0, 6, 0, -1, 0, 0, 0, 0, //40
		0, 0, 0, 12, 0, 0, 0, 8, 8, 12, //50
		8, 8, 0, 0, 0, 0, 0, 0, 0, 0,  //60
		6, 0, 2, 2, 8, 6, 0, -1, 0, 6, //70
		0, 0, 0, 0, 0, 1, 4, 6, 0, 0,  //80
		0, 0, 0, 0, 0, 3, 0, 0, -1, 0, //90
		0, 13, 0, -1, 0, 0, 0, 0, 0, 0,//100
		0, 0, 0, 0, 0, 0, 0, 6, 0, 0,  //110
		1, 0, 6, 0, 0, 0, -1, 0, 2, 6, //120
		0, 4, 6, 8, 0, 6, 0, 0, 0, 2,  //130
		0, 0, 0, 0, 0, 6, 0, 0, 0, 0,  //140
		0, 0, 1, 2, 0, 2, 6, 0, 0, 0,  //150
		0, 0, 0, 0, -1, -1, 0, 0, 0, 0,//160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  //170
		0, 8, 0, 3, 0, 2, 0, 0, 8, 1,  //180
		0, 0, 12, 0, 0, 0, 0, 0, 0, 0, //190
		2, 0, 0, 0, 0, 0, 0, 0, 4, 0,  //200
		4, 0, 0, 0, 7, 8, 0, 0, 10, 0, //210
		0, 0, 0, 0, 0, 0, -1, 0, 6, 0, //220
		1, 0, 0, 0, 6, 0, 6, 8, 1, 0,  //230
		0, 4, 0, 0, 0, 0, -1, 0, -1, 4,//240
		0, 0, 6, 6, 0, 0, 0            //250
	};

	public void destruct() {
		/*StringWriter sw = new StringWriter();
		new Throwable().printStackTrace(new PrintWriter(sw));
		printException = sw.toString();
		ExceptionCatcher.printException();*/
		if (spectate) {
			teleportToX = 3363;
			teleportToY = 9640;
			System.out.println("Moved to home before logout.");
		}
		if(session == null)
			return;
		
		if (noSmuggleTwo >= 0) {
			getItems().deleteAllItems();
			isFullHelm = Item.isFullHelm(playerEquipment[playerHat]);
			isFullMask = Item.isFullMask(playerEquipment[playerHat]);
			isFullBody = Item.isFullBody(playerEquipment[playerChest]);
			getPA().requestUpdates();
		}
		
		if (pickingNumber) {
			totalHungerGameExp += myBet;
			gamblePot -= myBet;
			gambleFinal = false;
			myBet = 0;
		} else if (myNumber > 0 && startRollin) {
			if (gamblerOne.equals(playerName) || gamblerTwo.equals(playerName)) {
				gambleLogged = true;
			}
		} else if (myNumber > 0) {
			if (gambleOn && !gambleFinal) {
				gamblePot = 0;
				gambleNum1 = false;
				gambleNum2 = false;
				gambleNum3 = false;
				firstGamble = false;
				gamblerOne = null;
				gamblerTwo = null;
				startRollin = false;
				gambleFinal = false;
				totalHungerGameExp += myBet;
				myBet = 0;
				gambleOn = false;
				firstGamblerAmount = 0;
				gamblerName = null;
			}
		} else if (myBet > 0) {
			totalHungerGameExp += myBet;
			gamblePot -= myBet;
			gambleFinal = false;
			myBet = 0;
		}
		if(disconnected == true){
			getTradeAndDuel().declineTrade();
		}
		if (clanId >= 0)
			Server.clanChat.leaveClan(playerId, clanId);
				
		if (inCwWait) {
			inCwWait = false;
			if (randomMap == 0) {
				Server.HungerGames.currentPlayersWait.remove(Server.HungerGames.currentPlayersWait.indexOf(playerId));
			} else if (randomMap == 1) {
				Server.HungerGamesFal.currentPlayers_FalWait.remove(Server.HungerGamesFal.currentPlayers_FalWait.indexOf(playerId));
			} else if (randomMap == 2) {
				Server.HungerGamesCan.currentPlayers_CanWait.remove(Server.HungerGamesCan.currentPlayers_CanWait.indexOf(playerId));
			} else if (randomMap == 3) {
				Server.HungerGamesMisc.currentPlayers_MiscWait.remove(Server.HungerGamesMisc.currentPlayers_MiscWait.indexOf(playerId));
			} else if (randomMap == 4) {
				Server.Infection.currentPlayers_Wait.remove(Server.Infection.currentPlayers_Wait.indexOf(playerId));
			}
			getPA().movePlayer(3363, 9640, 0);
		}
		if (inCwGame) {
			if(myKit.equalsIgnoreCase("JUMPER")) {
				getCombat().cleanPortal(portalX, portalY);
				getCombat().resetPersonalPortal();
			}
			getItems().deleteAllItems();
			inCwGame = false;
			if (randomMap == 0) {
				Server.HungerGames.currentPlayers.remove(Server.HungerGames.currentPlayers.indexOf(playerId));
			} else if (randomMap == 1) {
				Server.HungerGamesFal.currentPlayers_Fal.remove(Server.HungerGamesFal.currentPlayers_Fal.indexOf(playerId));
			} else if (randomMap == 2) {
				Server.HungerGamesCan.currentPlayers_Can.remove(Server.HungerGamesCan.currentPlayers_Can.indexOf(playerId));
			} else if (randomMap == 3) {
				Server.HungerGamesMisc.currentPlayers_Misc.remove(Server.HungerGamesMisc.currentPlayers_Misc.indexOf(playerId));
			} else if (randomMap == 4) {
				Server.Infection.currentPlayers.remove(Server.Infection.currentPlayers.indexOf(playerId));
			}
			noSmuggle = 0;
			getPA().movePlayer(3363, 9640, 0);
		}
		if (inPkingZone) {
			//edgePlayers.remove(edgePlayers.indexOf(playerId));
			inPkingZone = false;
		}
		PlayerSave.sa1veGame(this);
		Misc.println("[DEREGISTERED]: "+playerName+"");
		HostList.getHostList().remove(session);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
	}

	public String decrypt(String toString) {
		int at = 0;
		String returns = "";
		String a1 = "";
		String a2 = "";
		String a3 = "";
		String ask = "";
		while(at < toString.length()) {
			String charValue = ""+toString.charAt(at);
			ask = "";
			if(charValue.equals("1")) {
				a1 = ""+toString.charAt(at);
				a2 = ""+toString.charAt(at + 1);
				a3 = ""+toString.charAt(at + 2);
				ask = a1+""+a2+""+a3;
				System.out.println(ask);
				returns += getChar(Integer.parseInt(ask));
				at += 3;
			} else {
				a1 = ""+toString.charAt(at);
				a2 = ""+toString.charAt(at + 1);
				ask = a1+""+a2;
				System.out.println(ask);
				returns += getChar(Integer.parseInt(ask));
				at += 2;				
			}
		}
		return returns;
	}
	
	public char getChar(int value) {
		return (char)value;
	}
	public void sendMessage(String s) {
			if(getOutStream() != null) {
				outStream.createFrameVarSize(253);
				outStream.writeString(s);
				outStream.endFrameVarSize();
			
		}
	}

	public void setSidebarInterface(int menuId, int form) {
			if(getOutStream() != null) {
				outStream.createFrame(71);
				outStream.writeWord(form);
				outStream.writeByteA(menuId);
			
		}
	}
	public Client getClient(String name) {
		name = name.toLowerCase();
		for(int i = 0; i < Config.MAX_PLAYERS; i++) {
			if(validClient(i)) {
				Client client = getClient(i);
				if(client.playerName.toLowerCase().equalsIgnoreCase(name)) {
					return client;
				}
			}
		}
		return null;
	}
	public Client getClient(int id) {
		return (Client) Server.playerHandler.players[id];
	}
	public boolean validClient(int id) {
		if (id < 0 || id > Config.MAX_PLAYERS) {
			return false;
		}
		return validClient(getClient(id));
	}
	public boolean validClient(String name) {
		return validClient(getClient(name));
	}
	public boolean validClient(Client client) {
		return (client != null && !client.disconnected);
	}
	public boolean validNpc(int index) {
		if (index < 0 || index >= Config.MAX_NPCS) {
			return false;
		}
		NPC n = getNpc(index);
		if (n != null) {
			return true;
		}
		return false;
	}
	public NPC getNpc(int index) {
		return ((NPC) Server.npcHandler.npcs[index]);
	}
	public void yell(String s) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (validClient(i)) {
				getClient(i).sendMessage(s);
			}
		}
	}	
	public void staffYell(String s) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client)Server.playerHandler.players[j];
		if (c2.playerRights >= 1) {
	c2.sendMessage(s);
				}
			}
		}
	}	
	
	public void clearPlayersInterface() {
		for (int players : PlayersInterface) {
		getPA().sendFrame126("", players);
     		}
	}
	public static int[] PlayersInterface = { 8147, 8148, 8149, 8150, 8151, 8152, 8153, 8154, 8155, 8156, 8157, 8158, 8159, 8160, 8161, 8162, 8163, 8164, 8165, 8166, 8167, 8168, 8169, 8170, 8171, 8172, 8173, 8174, 8175, 8176, 8177, 8178, 8179, 8180, 8181, 8182, 8183, 8184, 8185, 8186, 8187, 8188, 8189, 8190, 8191, 8192, 8193, 8194, 8195, 8196, 8197, 8198, 8199, 8200, 8201, 8202, 8203, 8204, 8205, 8206, 8207, 8208, 8209, 8210, 8211, 8212, 8213, 8214, 8215, 8216, 8217, 8218, 8219, 8220, 8221, 8222, 8223, 8224, 8225, 8226, 8227, 8228, 8229, 8230, 8231, 8232, 8233, 8234,  8235, 8236, 8237, 8238, 8239, 8240, 8241, 8242, 8243, 8244, 8245, 8246, 8247 };
	  
	public boolean checkVotes(String playerName) {
        try {
                String urlString = "http://hg-rs.com/vote.php?type=checkvote&username="+playerName;
                urlString = urlString.replaceAll(" ", "%20");
                URL url = new URL(urlString);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String results = reader.readLine();
                if(results.length() > 0) {
                        if(results.equals("user needs reward..."))
                                return true;
                        else 
                                return false;
                }
        } catch (MalformedURLException e) {
                System.out.println("Malformed URL Exception in checkVotes(String playerName)");
        } catch (IOException e) {
                System.out.println("IO Exception in checkVotes(String playerName)");
        }
        return false;
	}
	
	public void lazyDecrease(int skill) {
		switch (skill) {
			case 0:
				playerLevel[0] -= (Misc.random(3) + 1);
				getPA().refreshSkill(0);
			break;
			
			case 1:
				playerLevel[1] -= (Misc.random(3) + 1);
				getPA().refreshSkill(1);
			break;
			
			case 2:
				playerLevel[2] -= (Misc.random(3) + 1);
				getPA().refreshSkill(2);
			break;
			
			case 4:
				playerLevel[4] -= (Misc.random(3) + 1);
				getPA().refreshSkill(4);
			break;
			
			case 5:
				playerLevel[6] -= (Misc.random(3) + 1);
				getPA().refreshSkill(6);
			break;
			
			default:
				playerLevel[0] -= 1;
				getPA().refreshSkill(0);
				playerLevel[1] -= 1;
				getPA().refreshSkill(1);
				playerLevel[2] -= 1;
				getPA().refreshSkill(2);
			break;
		}
	}
	
	public void difficultLevel(int diffilty) {
		if (diffilty == 3) {
			playerXP[0] = getPA().getXPForLevel(40) + 5;
			playerLevel[0] = getPA().getLevelForXP(playerXP[0]);
			playerXP[1] = getPA().getXPForLevel(40) + 5;
			playerLevel[1] = getPA().getLevelForXP(playerXP[1]);
			playerXP[2] = getPA().getXPForLevel(40) + 5;
			playerLevel[2] = getPA().getLevelForXP(playerXP[2]);
			playerXP[3] = getPA().getXPForLevel(60) + 5;
			playerLevel[3] = getPA().getLevelForXP(playerXP[3]);
			playerXP[4] = getPA().getXPForLevel(53) + 5;
			playerLevel[4] = getPA().getLevelForXP(playerXP[4]);
			playerXP[5] = getPA().getXPForLevel(13) + 5;
			playerLevel[5] = getPA().getLevelForXP(playerXP[5]);
			playerXP[6] = getPA().getXPForLevel(53) + 5;
			playerLevel[6] = getPA().getLevelForXP(playerXP[6]);
			getPA().refreshSkill(0);//att
			getPA().refreshSkill(1);//def
			getPA().refreshSkill(2);//str
			getPA().refreshSkill(3);//hp
			getPA().refreshSkill(4);//range
			getPA().refreshSkill(5);//pray
			getPA().refreshSkill(6);//mage
			getPA().requestUpdates();
		} else if (diffilty == 2) {
			playerXP[0] = getPA().getXPForLevel(60) + 5;
			playerLevel[0] = getPA().getLevelForXP(playerXP[0]);
			playerXP[1] = getPA().getXPForLevel(60) + 5;
			playerLevel[1] = getPA().getLevelForXP(playerXP[1]);
			playerXP[2] = getPA().getXPForLevel(60) + 5;
			playerLevel[2] = getPA().getLevelForXP(playerXP[2]);
			playerXP[3] = getPA().getXPForLevel(75) + 5;
			playerLevel[3] = getPA().getLevelForXP(playerXP[3]);
			playerXP[4] = getPA().getXPForLevel(80) + 5;
			playerLevel[4] = getPA().getLevelForXP(playerXP[4]);
			playerXP[5] = getPA().getXPForLevel(34) + 5;
			playerLevel[5] = getPA().getLevelForXP(playerXP[5]);
			playerXP[6] = getPA().getXPForLevel(80) + 5;
			playerLevel[6] = getPA().getLevelForXP(playerXP[6]);
			getPA().refreshSkill(0);//att
			getPA().refreshSkill(1);//def
			getPA().refreshSkill(2);//str
			getPA().refreshSkill(3);//hp
			getPA().refreshSkill(4);//range
			getPA().refreshSkill(5);//pray
			getPA().refreshSkill(6);//mage
			getPA().requestUpdates();
		} else if (diffilty == 1) {
			playerXP[0] = getPA().getXPForLevel(80) + 5;
			playerLevel[0] = getPA().getLevelForXP(playerXP[0]);
			playerXP[1] = getPA().getXPForLevel(80) + 5;
			playerLevel[1] = getPA().getLevelForXP(playerXP[1]);
			playerXP[2] = getPA().getXPForLevel(80) + 5;
			playerLevel[2] = getPA().getLevelForXP(playerXP[2]);
			playerXP[3] = getPA().getXPForLevel(90) + 5;
			playerLevel[3] = getPA().getLevelForXP(playerXP[3]);
			playerXP[4] = getPA().getXPForLevel(90) + 5;
			playerLevel[4] = getPA().getLevelForXP(playerXP[4]);
			playerXP[5] = getPA().getXPForLevel(60) + 5;
			playerLevel[5] = getPA().getLevelForXP(playerXP[5]);
			playerXP[6] = getPA().getXPForLevel(90) + 5;
			playerLevel[6] = getPA().getLevelForXP(playerXP[6]);
			getPA().refreshSkill(0);//att
			getPA().refreshSkill(1);//def
			getPA().refreshSkill(2);//str
			getPA().refreshSkill(3);//hp
			getPA().refreshSkill(4);//range
			getPA().refreshSkill(5);//pray
			getPA().refreshSkill(6);//mage
			getPA().requestUpdates();
		} else if (diffilty == 0) {
			playerXP[0] = getPA().getXPForLevel(99) + 5;
			playerLevel[0] = getPA().getLevelForXP(playerXP[0]);
			playerXP[1] = getPA().getXPForLevel(99) + 5;
			playerLevel[1] = getPA().getLevelForXP(playerXP[1]);
			playerXP[2] = getPA().getXPForLevel(99) + 5;
			playerLevel[2] = getPA().getLevelForXP(playerXP[2]);
			playerXP[3] = getPA().getXPForLevel(99) + 5;
			playerLevel[3] = getPA().getLevelForXP(playerXP[3]);
			playerXP[4] = getPA().getXPForLevel(99) + 5;
			playerLevel[4] = getPA().getLevelForXP(playerXP[4]);
			playerXP[5] = getPA().getXPForLevel(99) + 5;
			playerLevel[5] = getPA().getLevelForXP(playerXP[5]);
			playerXP[6] = getPA().getXPForLevel(99) + 5;
			playerLevel[6] = getPA().getLevelForXP(playerXP[6]);
			getPA().refreshSkill(0);//att
			getPA().refreshSkill(1);//def
			getPA().refreshSkill(2);//str
			getPA().refreshSkill(3);//hp
			getPA().refreshSkill(4);//range
			getPA().refreshSkill(5);//pray
			getPA().refreshSkill(6);//mage
			getPA().requestUpdates();
			difficultLevel = 0;
		}
	}
	
	public void handleGameMessage() {
		int game = 0;
		boolean send = false;
		
		if (Server.HungerGames.currentPlayersWait.size() >= 5 && Server.HungerGames.gameStartTimer <= 50 && System.currentTimeMillis() - lastGameMess_1 > 105000) {
			if (Server.HungerGames.gameOver) {
				lastGameMess_1 = System.currentTimeMillis();
				send = true;
				game = 1;
			}
		}
		if (Server.HungerGamesFal.currentPlayers_FalWait.size() >= 4 && Server.HungerGamesFal.gameStartTimer <= 50 && System.currentTimeMillis() - lastGameMess_2 > 105000) {
			if (Server.HungerGamesFal.gameOver) {
				lastGameMess_2 = System.currentTimeMillis();
				send = true;
				game = 2;
			}
		}
		if (Server.HungerGamesCan.currentPlayers_CanWait.size() >= 3 && Server.HungerGamesCan.gameStartTimer <= 50 && System.currentTimeMillis() - lastGameMess_3 > 105000) {
			if (Server.HungerGamesCan.gameOver) {
				lastGameMess_3 = System.currentTimeMillis();
				send = true;
				game = 3;
			}
		}
		if (Server.HungerGamesMisc.currentPlayers_MiscWait.size() >= 4 && Server.HungerGamesMisc.gameStartTimer <= 50 && System.currentTimeMillis() - lastGameMess_3 > 105000) {
			if (Server.HungerGamesMisc.gameOver) {
				lastGameMess_3 = System.currentTimeMillis();
				send = true;
				game = 4;
			}
		}
		if (send) {
			if (game == 1) {
				sendMessage("The game at @blu@Varrock@bla@ has " + Server.HungerGames.currentPlayersWait.size() + " players and is about to start in " + Server.HungerGames.gameStartTimer + " seconds!");
				Server.npcHandler.npcAction(619, "The game at Varrock is about to start in " + Server.HungerGames.gameStartTimer + " seconds!", 1);
			} else if (game == 2) {
				sendMessage("The game at @blu@Falador@bla@ has " + Server.HungerGamesFal.currentPlayers_FalWait.size() + " players and is about to start in " + Server.HungerGamesFal.gameStartTimer + " seconds!");
				Server.npcHandler.npcAction(619, "The game at Falador is about to start in " + Server.HungerGamesFal.gameStartTimer + " seconds!", 1);
			} else if (game == 3) {
				sendMessage("The game at @blu@M/S@bla@ has " + Server.HungerGamesCan.currentPlayers_CanWait.size() + " players and is about to start in " + Server.HungerGamesCan.gameStartTimer + " seconds!");
				Server.npcHandler.npcAction(619, "The game at M/S is about to start in " + Server.HungerGamesCan.gameStartTimer + " seconds!", 1);
			} else if (game == 4) {
				sendMessage("The game at @blu@Miscellania@bla@ has " + Server.HungerGamesMisc.currentPlayers_MiscWait.size() + " players and is about to start in " + Server.HungerGamesMisc.gameStartTimer + " seconds!");
				Server.npcHandler.npcAction(619, "The game at Miscellania is about to start in " + Server.HungerGamesMisc.gameStartTimer + " seconds!", 1);
			}
			send = false;
			game = 0;
		}
	}
	
	public void sendItems() {
		getItems().addItem(2347, 1);//hammer (all kits get this)
		getItems().addItem(4155, 1);//tracking stone (all kits get this)
		switch (myKit)
		{
			case "BERSERK":
				getItems().addItem(11128, 1);//1 berserk neck
				getItems().addItem(2440, 1);//1 super str pot
			break;
			
			case "ARCHER":
				getItems().addItem(861, 1);//1 magic shortbow
				getItems().addItem(2577, 1);//1 ranger boots
			break;
			
			case "ELEMENTALIST":
				getItems().addItem(4675, 1);//anicent staff
				getItems().addItem(2579, 1);//wizard boots
			break;
			
			case "BLIZZARD":
				getItems().addItem(1580, 1);//ice gloves
			break;
			
			case "FLASH":
				getItems().addItem(88, 1);//boots of lightness
			break;
			
			case "JUMPER":
				getItems().addItem(8408, 1);//Jumper Portal
				hasPlacedPortalDown = false;
			break;
			
			case "LOOTER":
				getItems().addItem(5554, 1);//lol
			break;
			
			case "ALCHEMIST":
				getItems().addItem(227, 5);//vials
				getItems().addItem(257, 1);//herbs
				getItems().addItem(259, 1);//herbs
				getItems().addItem(263, 1);//herbs
				getItems().addItem(267, 1);//herbs
				getItems().addItem(265, 1);//herbs
			break;
			
			case "SKILLER":
				getItems().addItem(3024, 1);//ppot
				getItems().addItem(6685, 2);//brews
				
				getItems().addItem(44, 60);//arrowhead
				getItems().addItem(52, 60);//shafts
				getItems().addItem(314, 60);//feather
			break;
			
			case "SICKNESS":
				sicknessPoisons = Misc.random(5) + 5;
			break;
			
			case "TAINTED":
				getItems().addItem(1724, 1);//holy sym
				getItems().addItem(6950, 1);//orb
			break;
			
			case "NECROMORPHER":
				getItems().addItem(4837, 1);//book
				playerMagicBook = 1;
				setSidebarInterface(6, 12855);
				sendMessage("An ancient wisdomin fills your mind.");
				autocastId = -1;
				getPA().resetAutocast();
			break;
			
			case "SAINT":
				getItems().addItem(1718, 1);//holy sym
			break;
			
			case "KNIGHT":
				getItems().addItem(35, 1);//excal
				getItems().addItem(748, 1);//holy force
			break;
			
			case "TAGGED":
				taggedUses = 3;
				getItems().addItem(4002, 1);//tagger device
				sendMessage("You've been given 3 Tagged uses for this game.");
			break;
			
			case "WRETCHED":
				wretchedUses = 6;
				getItems().addItem(4707, 1);
				sendMessage("The book seems like it can only work 6 times for this game.");
			break;
			
			default:
				getItems().addItem(391, 2);//2 manta
			break;
		}
	}
	
	public void sendSwitchItems() {
		switch (myKit)
		{
			case "BERSERK":
				Server.itemHandler.createGroundItem(this, 11128, absX, absY, 1, getId());
				Server.itemHandler.createGroundItem(this, 2440, absX, absY, 1, getId());
			break;
			
			case "ARCHER":
				Server.itemHandler.createGroundItem(this, 861, absX, absY, 1, getId());
				Server.itemHandler.createGroundItem(this, 2577, absX, absY, 1, getId());
			break;
			
			case "ELEMENTALIST":
				Server.itemHandler.createGroundItem(this, 4675, absX, absY, 1, getId());
				Server.itemHandler.createGroundItem(this, 2579, absX, absY, 1, getId());
			break;
			
			case "BLIZZARD":
				Server.itemHandler.createGroundItem(this, 1580, absX, absY, 1, getId());
			break;
			
			case "FLASH":
				Server.itemHandler.createGroundItem(this, 88, absX, absY, 1, getId());
			break;
			
			case "JUMPER":
				Server.itemHandler.createGroundItem(this, 8408, absX, absY, 1, getId());
				hasPlacedPortalDown = false;
			break;
			
			case "LOOTER":
				Server.itemHandler.createGroundItem(this, 5554, absX, absY, 1, getId());
			break;
			
			case "ALCHEMIST":
				Server.itemHandler.createGroundItem(this, 227, absX, absY, 5, getId());
				Server.itemHandler.createGroundItem(this, 257, absX, absY, 1, getId());
				Server.itemHandler.createGroundItem(this, 259, absX, absY, 1, getId());
				Server.itemHandler.createGroundItem(this, 263, absX, absY, 1, getId());
				Server.itemHandler.createGroundItem(this, 267, absX, absY, 1, getId());
				Server.itemHandler.createGroundItem(this, 265, absX, absY, 1, getId());
			break;
			
			case "SKILLER":
				Server.itemHandler.createGroundItem(this, 3024, absX, absY, 1, getId());
				Server.itemHandler.createGroundItem(this, 6685, absX, absY, 2, getId());
				Server.itemHandler.createGroundItem(this, 44, absX, absY, 60, getId());
				Server.itemHandler.createGroundItem(this, 52, absX, absY, 60, getId());
				Server.itemHandler.createGroundItem(this, 314, absX, absY, 60, getId());
			break;
			
			case "SICKNESS":
				sicknessPoisons = Misc.random(5) + 5;
			break;
			
			case "TAINTED":
				Server.itemHandler.createGroundItem(this, 1724, absX, absY, 1, getId());
				Server.itemHandler.createGroundItem(this, 6950, absX, absY, 1, getId());
			break;
			
			case "NECROMORPHER":
				Server.itemHandler.createGroundItem(this, 4837, absX, absY, 1, getId());
				playerMagicBook = 1;
				setSidebarInterface(6, 12855);
				sendMessage("An ancient wisdomin fills your mind.");
				autocastId = -1;
				getPA().resetAutocast();
			break;
			
			case "SAINT":
				Server.itemHandler.createGroundItem(this, 1718, absX, absY, 1, getId());
			break;
			
			case "KNIGHT":
				Server.itemHandler.createGroundItem(this, 35, absX, absY, 1, getId());
				Server.itemHandler.createGroundItem(this, 748, absX, absY, 1, getId());
			break;
			
			case "TAGGED":
				taggedUses = 3;
				Server.itemHandler.createGroundItem(this, 4002, absX, absY, 1, getId());
				sendMessage("You've been given 3 Tagged uses for this game.");
			break;
			
			case "WRETCHED":
				wretchedUses = 6;
				Server.itemHandler.createGroundItem(this, 4707, absX, absY, 1, getId());
				sendMessage("The book seems like it can only work 6 times for this game.");
			break;
			
			default:
				Server.itemHandler.createGroundItem(this, 391, absX, absY, 2, getId());
			break;
		}
	}
	
	public void handleLotto(int winnerNum) {
		try {
			String winnerName = "";
			int exp = 0;
			
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(Server.playerHandler.players[i] != null) {
					Client c2 = (Client)Server.playerHandler.players[i];
					if (c2.playerId == winnerNum) {
						c2.sendMessage("Congratulations you've just won the lottery of @blu@" + df.format((int)lottery) + " @bla@experience!");
						c2.totalHungerGameExp += lottery;
						exp = c2.totalHungerGameExp;
						winnerName = c2.playerName;
						c2.reloadHGstuff();
						lottery = 0;
						break;
					}
				}
			}
			for(int i = 0; i < Config.MAX_PLAYERS; i++) {
				if(Server.playerHandler.players[i] != null) {
					Client c2 = (Client)Server.playerHandler.players[i];
					c2.sendMessage("[@red@SERVER@bla@] : @blu@" + winnerName + "@bla@ won the lottery and now has " + df.format((int)exp) + " experience!!!");
				}
			}
		} catch(Exception e) {
			sendMessage("Error with lottery.");
		}
	}
	
	public void wretchedForm(int id) {
		int ahrims[] = { 4708, -1, -1, 4710, 4712, -1, -1, 4714, -1, -1, -1, -1, -1, -1 };
		int dharoks[] = { 4716, -1, -1, 4718, 4720, -1, -1, 4722, -1, -1, -1, -1, -1, -1 };
		int guthans[] = { 4724, -1, -1, 4726, 4728, -1, -1, 4730, -1, -1, -1, -1, -1, -1 };
		int karils[] = { 4732, -1, -1, 4734, 4736, -1, -1, 4738, -1, -1, -1, -1, -1, -1 };
		int torags[] = { 4745, -1, -1, 4747, 4749, -1, -1, 4751, -1, -1, -1, -1, -1, -1 };
		int veracs[] = { 4753, -1, -1, 4755, 4757, -1, -1, 4759, -1, -1, -1, -1, -1, -1 };
		
		switch (id) {
			case 0://ahrim
				for (int i = 0; i < wretchedBonus.length; i++) {
					wretchedBonus[i] = ahrims[i];
				}
				getItems().sendWeapon(4710, getItems().getItemName(4710));
				getItems().resetBonus();
				getItems().getBonus();
				getItems().writeBonus();
				getCombat().getPlayerAnimIndex(getItems().getItemName(4710).toLowerCase());
				npcId2 = 2025;
				isNpc = true;
				updateRequired = true;
				appearanceUpdateRequired = true;
			break;
			
			case 1://dh
				for (int i = 0; i < wretchedBonus.length; i++) {
					wretchedBonus[i] = dharoks[i];
				}
				getItems().sendWeapon(4718, getItems().getItemName(4718));
				getItems().resetBonus();
				getItems().getBonus();
				getItems().writeBonus();
				getCombat().getPlayerAnimIndex(getItems().getItemName(4718).toLowerCase());
				npcId2 = 2026;
				isNpc = true;
				updateRequired = true;
				appearanceUpdateRequired = true;
			break;
			
			case 2://guthan
				for (int i = 0; i < wretchedBonus.length; i++) {
					wretchedBonus[i] = guthans[i];
				}
				getItems().sendWeapon(4726, getItems().getItemName(4726));
				getItems().resetBonus();
				getItems().getBonus();
				getItems().writeBonus();
				getCombat().getPlayerAnimIndex(getItems().getItemName(4726).toLowerCase());
				npcId2 = 2027;
				isNpc = true;
				updateRequired = true;
				appearanceUpdateRequired = true;
			break;
			
			case 3://karil
				for (int i = 0; i < wretchedBonus.length; i++) {
					wretchedBonus[i] = karils[i];
				}
				getItems().sendWeapon(4734, getItems().getItemName(4734));
				getItems().resetBonus();
				getItems().getBonus();
				getItems().writeBonus();
				getCombat().getPlayerAnimIndex(getItems().getItemName(4734).toLowerCase());
				npcId2 = 2028;
				isNpc = true;
				updateRequired = true;
				appearanceUpdateRequired = true;
			break;
			
			case 4://torag
				for (int i = 0; i < wretchedBonus.length; i++) {
					wretchedBonus[i] = torags[i];
				}
				getItems().sendWeapon(4747, getItems().getItemName(4747));
				getItems().resetBonus();
				getItems().getBonus();
				getItems().writeBonus();
				getCombat().getPlayerAnimIndex(getItems().getItemName(4747).toLowerCase());
				npcId2 = 2029;
				isNpc = true;
				updateRequired = true;
				appearanceUpdateRequired = true;
			break;
			
			case 5://verac
				for (int i = 0; i < wretchedBonus.length; i++) {
					wretchedBonus[i] = veracs[i];
				}
				getItems().sendWeapon(4755, getItems().getItemName(4755));
				getItems().resetBonus();
				getItems().getBonus();
				getItems().writeBonus();
				getCombat().getPlayerAnimIndex(getItems().getItemName(4755).toLowerCase());
				npcId2 = 2030;
				isNpc = true;
				updateRequired = true;
				appearanceUpdateRequired = true;
			break;
		}
	}
	
	public void handleKits() {
		/* Free kits! */
		kits[0] = "Berserk";
		kits[1] = "Archer";
		kits[2] = "Elementalist";
		/*kits[3] = "Perished";
		kits[4] = "Venom";
		kits[5] = "Blizzard";
		kits[6] = "Flash";
		kits[7] = "Looter";
		kits[8] = "Alchemist";
		kits[9] = "Forsaken";
		kits[10] = "Jumper";
		kits[11] = "Skiller";
		kits[12] = "Sickness";
		kits[13] = "Chaos";
		kits[14] = "Tainted";
		kits[15] = "Saint";
		kits[16] = "Necromorpher";
		kits[17] = "Knight";
		kits[18] = "Tagged";
		kits[19] = "Wretched";*/
		kits[20] = "Protected";
		//kits[21] = "Mercenary";

		//donatorPoints = 10000;
		//totalHungerGameExp = 1000000;
		
		if (playerRights >= 0) { //Beta player bonus kit
			if (betaPlayer == 1) {
				kits[3] = "Perished";
				betaPlayer = 0;
			}
		}
		
		/* Kits Message */
		String listKits = "Default";
		String listKits2 = "";
		String listKits3 = "";
		boolean line2 = false, line3 = false;
		for (int i = 0; kits.length > i; i++) {
			if (kits[i] != null) {
				if (listKits.length() <= 55) {
					listKits = listKits + ", " + kits[i];
				} else if (listKits2.length() <= 70) {
					line2 = true;
					listKits2 = listKits2 + ", " + kits[i];
				} else {
					line3 = true;
					listKits3 = listKits3 + ", " + kits[i];
				}
			}
		}
		sendMessage("Your kits are: @blu@" + listKits);
		if (line2)
			sendMessage("@blu@" + listKits2);
		if (line3)
			sendMessage("@blu@" + listKits3);
			
		sendMessage("There are currently " + (donatorkitList.length + normkitList.length) + " kits. Check them out under the quest tab!");
		/* End of kits message */
	}
	
	public void initialize() {
			if (spectateEx == 0) {
				/*if (inVarrockPvp() && Server.HungerGames.gameId == lastGame) {
					readdedIn = true;
					Server.HungerGames.dcJoin(playerId);
					sendMessage("@red@It seems you disconnected recently, you'll now be readded within the same game.");
				} else if (inFalPvp() && Server.HungerGamesFal.gameId == lastGame) {
					readdedIn = true;
					Server.HungerGamesFal.dcJoin(playerId);
					sendMessage("@red@It seems you disconnected recently, you'll now be readded within the same game.");
				} else if (inCanPvp() && Server.HungerGamesCan.gameId == lastGame) {
					readdedIn = true;
					Server.HungerGamesCan.dcJoin(playerId);
					sendMessage("@red@It seems you disconnected recently, you'll now be readded within the same game.");
				} else if (inMiscPvp() && Server.HungerGamesMisc.gameId == lastGame) {
					readdedIn = true;
					Server.HungerGamesMisc.dcJoin(playerId);
					sendMessage("@red@It seems you disconnected recently, you'll now be readded within the same game.");
				}*/
			} else {
				spectateEx = 0;
			}
			difficultLevel(0);
			if(hasNpc == true) {
				if (summonId > 0) {
						PetHandler.spawnPet(this, summonId, -1, true);
				}
			}
			if (yellCheck()) {
				yellAllowed = true;
			}
			if (startRollin) {
				if (gamblerOne.equals(playerName) || gamblerTwo.equals(playerName)) {
					gambleLogged = false;
				}
			}
			if (firstLogin == 0) {
				resetPass = 1;
				firstLogin();
			}
			MadTurnipConnection.addDonateItems(this,playerName);
			calcCombat();
			if (rememberedKit == null)
				rememberedKit = "Default";
			getPA().sendFrame126("Hunger Games Bank", 5383);
			sendMessage("@bla@Welcome to the Runescape Hunger Games, click on a portal to begin.@bla@");
			setAppearanceUpdateRequired(true);
			updateRequired = true;
			isFullHelm = Item.isFullHelm(playerEquipment[playerHat]);
			isFullMask = Item.isFullMask(playerEquipment[playerHat]);
			isFullBody = Item.isFullBody(playerEquipment[playerChest]);
			
			outStream.createFrame(249);
			outStream.writeByteA(1);
			outStream.writeWordBigEndianA(playerId);
			
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (j == playerId)
					continue;
				if (Server.playerHandler.players[j] != null) {
					if (Server.playerHandler.players[j].playerName.equalsIgnoreCase(playerName))
						disconnected = true;
				}
			}
			
			for (int i = 0; i < 25; i++) {
				getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
				getPA().refreshSkill(i);
			}
			
			for(int p = 0; p < PRAYER.length; p++) {
				prayerActive[p] = false;
				getPA().sendFrame36(PRAYER_GLOW[p], 0);
			}
			
			getPA().handleWeaponStyle();
			accountFlagged = getPA().checkForFlags();
			getPA().sendFrame36(108, 0);
			getPA().sendFrame36(172, 1);
			getPA().sendFrame107();
			getPA().setChatOptions(0, 0, 0);
			
			if (firstLogin == 1) {
				if (resetPass == 0) {
					forceResetPass();
				}
				setSidebarInterface(1, 3917);
				setSidebarInterface(2, 16000);
				setSidebarInterface(3, 3213);//21150
				setSidebarInterface(4, 1644);
				setSidebarInterface(5, 5608);
				
				setSidebarInterface(6, 1151);//Always reset it back to modern
				/*if(playerMagicBook == 0) {
					setSidebarInterface(6, 1151); //modern
				} else if (playerMagicBook == 1) {
					setSidebarInterface(6, 12855); // ancient
				} else if (playerMagicBook == 2) {
					setSidebarInterface(6, 29999); // lunar
				}*/
				setSidebarInterface(7, 18128);
				setSidebarInterface(8, 5065);
				setSidebarInterface(9, 5715);
				setSidebarInterface(10, 2449);
				setSidebarInterface(11, 904); // wrench tab
				setSidebarInterface(12, 147); // run tab
				setSidebarInterface(13, 6299);
				setSidebarInterface(14, 15001);
				setSidebarInterface(15, 15051);
				setSidebarInterface(16, 17000);
				setSidebarInterface(0, 2423);
			} else {
				setSidebarInterface(1, -1);
				setSidebarInterface(2, -1);
				setSidebarInterface(3, 3213);
				setSidebarInterface(4, -1);
				setSidebarInterface(5, -1);
				setSidebarInterface(6, -1);
				setSidebarInterface(7, -1);
				setSidebarInterface(8, -1);
				setSidebarInterface(9, -1);
				setSidebarInterface(10, 2449);
				setSidebarInterface(11, -1);
				setSidebarInterface(12, -1);
				setSidebarInterface(13, -1);
				setSidebarInterface(14, -1);
				setSidebarInterface(15, -1);
				setSidebarInterface(16, -1);
				setSidebarInterface(0, 2423);
			}
			correctCoordinates();
			clearPlayersInterface();
			
			handleKits(); //Handles HungeGames kits	
			getPA().showOption(4, 0,"Trade With", 3);
			getPA().showOption(5, 0,"Follow", 4);
			getItems().resetItems(3214);
			getItems().sendWeapon(playerEquipment[playerWeapon], getItems().getItemName(playerEquipment[playerWeapon]));
			getItems().resetBonus();
			getItems().getBonus();
			getItems().writeBonus();
			getItems().setEquipment(playerEquipment[playerHat],1,playerHat);
			getItems().setEquipment(playerEquipment[playerCape],1,playerCape);
			getItems().setEquipment(playerEquipment[playerAmulet],1,playerAmulet);
			getItems().setEquipment(playerEquipment[playerArrows],playerEquipmentN[playerArrows],playerArrows);
			getItems().setEquipment(playerEquipment[playerChest],1,playerChest);
			getItems().setEquipment(playerEquipment[playerShield],1,playerShield);
			getItems().setEquipment(playerEquipment[playerLegs],1,playerLegs);
			getItems().setEquipment(playerEquipment[playerHands],1,playerHands);
			getItems().setEquipment(playerEquipment[playerFeet],1,playerFeet);
			getItems().setEquipment(playerEquipment[playerRing],1,playerRing);
			getItems().setEquipment(playerEquipment[playerWeapon],playerEquipmentN[playerWeapon],playerWeapon);
			getCombat().getPlayerAnimIndex(getItems().getItemName(playerEquipment[playerWeapon]).toLowerCase());
			getPA().logIntoPM();
			getItems().addSpecialBar(playerEquipment[playerWeapon]);
			saveTimer = Config.SAVE_TIMER;
			saveCharacter = true;
			Misc.println("[REGISTERED]: "+playerName+"");
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
			getPA().clearClanChat();
			renamedInterfaces();
			checkForSpecialist();
			if (myCC == null) {
				myCC = "Help";
			}
			Server.clanChat.handleAutoJoin(this, myCC);
			if (!inCwGame) {
				myKit = "";
			} else {
				myKit = rememberedKit;
			}
			if (totalGames <= 10) {
				sendMessage("You currently have the newbie bonus for " + (10 - totalGames) + " games!");
			}
			if (noSmuggle == 1) {
				if (!readdedIn) {
					noSmuggle = 0;
					getItems().deleteAllItems();
					isFullHelm = Item.isFullHelm(playerEquipment[playerHat]);
					isFullMask = Item.isFullMask(playerEquipment[playerHat]);
					isFullBody = Item.isFullBody(playerEquipment[playerChest]);
					getPA().requestUpdates();
				} else {
					readdedIn = false;
				}
			}
			if (freePartyHat == 0 && totalGames >= 1000) {
								sendMessage("You have more than 1000 games played. You get a random party hat!");
			sendMessage("You also receive a pumpkin and keg of beer!");
						getItems().addItem(partyHat(), 1);
								getItems().addItem(1959, 1);

				getItems().addItem(3801, 1);

				freePartyHat = 1;

			}
			dfs.setGroupingSeparator(',');
			df.setDecimalFormatSymbols(dfs);
			if (autoRet == 1)
				getPA().sendFrame36(172, 1);
			else
				getPA().sendFrame36(172, 0);
	}
	
	public void forceResetPass() {
		getDH().sendDialogues(999, 0);
	}
	
	public void reloadHGstuff() {
		dfs.setGroupingSeparator(',');
		df.setDecimalFormatSymbols(dfs);
		getPA().sendFrame126("Experience: @gre@" + df.format((int)totalHungerGameExp), 16202);
		getPA().sendFrame126("Wins: @gre@" + totalGameWins, 16200);
		getPA().sendFrame126("Kills: @gre@" + KC, 16201);
		getPA().sendFrame126("Deaths: @gre@" + DC, 16241);
	}
	
	public void randomFact() { /*Method name*/
		int random = Misc.random(22);
		switch (random) {
		
			case 0:
				sendMessage("[@blu@Did you know?@bla@] - You can fix barrows with a hammer on an anvil.");
			break;
			
			case 1:
				sendMessage("[@blu@Did you know?@bla@] - You can steal raw fish from the fishing stalls and cook them.");
			break;
			
			case 2:
				sendMessage("[@blu@Did you know?@bla@] - Alchemist, Forsaken, Sickness, and Saint only cost experience?");
			break;
			
			case 3:
				sendMessage("[@blu@Did you know?@bla@] - That two venoms can't poison each other.");
			break;
			
			case 4:
				sendMessage("[@blu@Did you know?@bla@] - Varrock was the first Hunger Games area made.");
			break;
			
			case 5:
				sendMessage("[@blu@Did you know?@bla@] - Perished don't lose their items the first time they die.");
			break;
			
			case 6:
				sendMessage("[@blu@Did you know?@bla@] - You get more experience per kill, the longer the match has gone on.");
			break;
			
			case 7:
				sendMessage("[@blu@Did you know?@bla@] - We're based off a lot of the popular Minecraft Hunger Games.");
			break;
			
			case 8:
				sendMessage("[@blu@Did you know?@bla@] - The waiting and in-game timer is actually 2 seconds per count.");
			break;
			
			case 9:
				sendMessage("[@blu@Did you know?@bla@] - Our forums are online now!");
			break;
			
			case 10:
				sendMessage("[@blu@Did you know?@bla@] - You can do '::togglekit' to turn player kits display off and on?");
			break;
			
			case 11:
				sendMessage("[@blu@Did you know?@bla@] - That " + totalPlayersDied + " players have been killed in total?");
			break;
			
			case 12:
				sendMessage("[@blu@Did you know?@bla@] - That " + totalGamesPlayed + " games have been played in total?");
			break;
			
			case 13:
				sendMessage("[@blu@Did you know?@bla@] - You can vote for experience or items? ::vote");
			break;
			
			case 14:
				sendMessage("[@blu@Did you know?@bla@] - You can buy other kits at our site? www.HG-RS.com/home/shop.html");
			break;
			
			case 15:
				sendMessage("[@blu@Did you know?@bla@] - Players have triggered " + totalTraps + " traps?");
			break;
			
			case 16:
				sendMessage("[@blu@Did you know?@bla@] - " + totalGrandChests + " Grand Chests have been found?");
			break;
			
			case 17:
				sendMessage("[@blu@Did you know?@bla@] - " + totalExp + " experience has been earned by all players?");
			break;
			
			case 18:
				sendMessage("[@blu@Did you know?@bla@] - The Specialist achievement unlocks the Tagged Kit?");
			break;
			
			case 19:
				sendMessage("[@blu@Did you know?@bla@] - There's a hidden room at Miscellania?");
			break;
			
			case 20:
				sendMessage("[@blu@Did you know?@bla@] - New content comes out every week or two?");
			break;
			
			case 21:
				sendMessage("[@blu@Did you know?@bla@] - You can check which staff members are online by using ::staff?");
			break;
		}
	}
		
	public void frame74(int songID)
	{
		outStream.createFrame(74);
		outStream.writeWordBigEndian(songID);
	}
	
	public void rules() {
		
	}
	
	public void donatorKits() {
		sendMessage("Removed, go check the website for a list");
	}
	
	public void commandList() {
		getPA().sendFrame126(Config.SERVER_NAME+" - Commands", 8144);
		getPA().sendFrame126("@blu@commands", 8145);
		getPA().sendFrame126(" ", 8146);//Next button bottom right
		getPA().sendFrame126("@blu@Current Commands", 8145);
		getPA().sendFrame126("", 8147);
		getPA().sendFrame126("::togglekit", 8148);				
		getPA().sendFrame126("::players", 8149);
		getPA().sendFrame126("::allkits", 8150);
		getPA().sendFrame126("::kit [kitname]", 8151);
		getPA().sendFrame126("::perk [perkname]", 8152);
		getPA().sendFrame126("::yell [message]", 8152);
		getPA().sendFrame126("::server", 8153);
		getPA().sendFrame126("::unstuck", 8154);
		getPA().sendFrame126("::vote", 8155);
		getPA().sendFrame126("::check", 8156);
		getPA().sendFrame126("::kdr", 8157);
		getPA().sendFrame126("::changepassword", 8158);				
		getPA().sendFrame126("::togglewait", 8159);				
		getPA().sendFrame126("::lottery", 8160);				
		getPA().sendFrame126("::perks", 8161);				
		getPA().sendFrame126("::updatelist", 8162);					
		getPA().sendFrame126("::forum", 8163);
		getPA().sendFrame126("::setcc", 8164);			
		getPA().sendFrame126("::staff", 8165);				
		getPA().sendFrame126("::votingpoints", 8166);				
		getPA().sendFrame126("@bla@", 8169);				
		getPA().sendFrame126("@bla@", 8170);				
		getPA().showInterface(8134);
		flushOutStream();
	}
	
	public void applyFollowing()
	{
		if (follow2 > 0)
		{
			//Client p = Server.playerHandler.client[followId];
			Client p = (Client) Server.playerHandler.players[follow2];     
			if (p != null)
			{
				if (isDead)
				{
					follow(0, 3, 1);
					return;
				}
				if (!goodDistance(p.absX, p.absY, absX, absY, 25))
				{
					follow(0, 3, 1);
					return;
				}
			}
			else if (p == null)
			{
				follow(0, 3, 1);
			}
		}
		else if (follow2 > 0)
		{
			//Server.npcHandler.npcs.NPC npc = Server.npcHandler.npcs[followId2];
			if (Server.npcHandler.npcs[followId2] != null)
			{
				if (Server.npcHandler.npcs[followId2].isDead)
				{
					follow(0, 3, 1);
					return;
				}
				if (!goodDistance(Server.npcHandler.npcs[followId2].absX, Server.npcHandler.npcs[followId2].absY, absX, absY, 25))
				{
					follow(0, 3, 1);
					return;
				}
			}
			else if (Server.npcHandler.npcs[followId2] == null)
			{
				follow(0, 3, 1);
			}
		}
	}

	public int followDistance = 0;

	public void follow(int slot, int type, int distance)
	{
		if (slot > 0 && slot == follow2 && type == 1 && follow2 > 0 && followDistance != distance && (usingBow || usingMagic))
            return;
		else if (slot > 0 && slot == followId2 && type == 0 && followId2 > 0 && followDistance >= distance && distance != 1)
            return;

		outStream.createFrame(174);
		if (freezeTimer > 0) {
			outStream.writeWord(0);
		} else {
			outStream.writeWord(slot);
			if (type == 0) {
				follow2 = 0;
				followId2 = slot;
				faceUpdate(followId2);
			} else if (type == 1) {
				followId2 = 0;
				follow2 = slot;
				faceUpdate(32768 + follow2);
			} else if (type == 3) {
				followId2 = 0;
				follow2 = 0;
				followDistance = 0;
				faceUpdate(65535);
			}
			followDistance = distance;
		}
		outStream.writeByte(type);
		outStream.writeWord(distance);
	}
	
	public int staffOnline() {
		int staffOnline = 0;
		for (Player p : PlayerHandler.players) {
			if (p != null && p.playerRights > 0 && p.playerRights != 4)
				staffOnline++;
		}
		return staffOnline;
	}
	
	public void timedFade() { /*Method name*/
			EventManager.getSingleton().addEvent(
		new Event() {
			public void execute(EventContainer c) {
			interFaceplay();
				}
		}, 1400); // Time between each announcement
	};
	
	public void explockon() {
    	expLock = true;
   		sendMessage("EXP Lock @red@Activated.");
	}

	public void explockoff() {
    	expLock = false;
    	sendMessage("EXP Lock @red@De-Activated.");
	}
	
	public void interFaceplay() {
		if (interFacetimer == 0) {
			getPA().movePlayer(3087, 3500, 0);
			cmdBook1();
			interFacetimer = 1;
			getItems().addItem(7681,1);
		} else if (interFacetimer == 1) {
		}
	}
	
	public void renamedInterfaces() {
		getPA().sendFrame126("Wins: @gre@" + totalGameWins, 16200);
		getPA().sendFrame126("Kills: @gre@" + KC, 16201);
		getPA().sendFrame126("Deaths: @gre@" + DC, 16241);
		getPA().sendFrame126("Experience: @gre@" + df.format((int)totalHungerGameExp), 16202);
		getPA().sendFrame126("Donator Points: @gre@" + donatorPoints, 16242);
		
		for (int id = 16204, kitId = 0; id < (16204 + normkitList.length); id++, kitId++) {
			for (int id0 = 0; id0 < kits.length; id0++) {
				if (id == 16204) {
					getPA().sendFrame126("@gre@Default", id);
					break;
				}
				if (kits[id0] != null && kits[id0].equalsIgnoreCase(normkitList[kitId])) {
					getPA().sendFrame126("@gre@" + normkitList[kitId], id);
					break;
				} else {
					getPA().sendFrame126("@red@" + normkitList[kitId], id);
				}
			}
		}
		int theId = 16204 + normkitList.length;
		for (int id = theId, kitId = 0; id < (theId + donatorkitList.length); id++, kitId++) {
			for (int id0 = 3; id0 < kits.length; id0++) {
				if (kits[id0] != null && kits[id0].equalsIgnoreCase(donatorkitList[kitId])) {
					getPA().sendFrame126("@gre@" + donatorkitList[kitId], id);
					break;
				} else {
					getPA().sendFrame126("@red@" + donatorkitList[kitId], id);
				}
			}
		}
		for (int id = (16204 + normkitList.length + donatorkitList.length); id < 16240; id++) {
			getPA().sendFrame126("", id);
		}
		
		/** Achievements **/
		for (int id = 0; id < achievementsStrings.length; id++) {
			if (achievements[id][0] == 1) {
				getPA().sendFrame126("@gre@" + achievementsStrings[id], 16600 + id);
			} else {
				getPA().sendFrame126("@red@" + achievementsStrings[id], 16600 + id);
			}
			for (int id3 = 16600 + achievementsStrings.length; id3 < 16660; id3++) {
				getPA().sendFrame126("", id3);
			}
		}
	}
	
	public void achievementsHandler() {
		for (int i = 0; i < achievements.length; i++) {
			if (achievements[i][0] == 0 && achievements[i][1] == 1) {
				achievementReward(i);
			}
		}
	}
	
	public void achievementReward(int index) {
		switch (index) {
			case 0:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Bloodthirsty@bla@ achievement!");
				sendMessage("You've earned @blu@30,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 30000;
				totalExp += 30000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 1:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Merciless@bla@ achievement!");
				sendMessage("You've earned @blu@50,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 50000;
				totalExp += 50000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 2:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Relentless@bla@ achievement!");
				sendMessage("You've earned @blu@80,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 80000;
				totalExp += 80000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 3:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Pirates Bane@bla@ achievement!");
				sendMessage("You've earned @blu@50,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 50000;
				totalExp += 50000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 4:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Treasure Hunter@bla@ achievement!");
				sendMessage("You've earned @blu@150,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 150000;
				totalExp += 150000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 5:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Deal or No Deal@bla@ achievement!");
				sendMessage("You've earned @blu@100,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 100000;
				totalExp += 100000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 6:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Boom Goes The Dynamite@bla@ achievement!");
				sendMessage("You've earned @blu@10,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 10000;
				totalExp += 10000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 7:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@No Stone for This Sword@bla@ achievement!");
				sendMessage("You've earned @blu@30,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 30000;
				totalExp += 30000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 8:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Voter@bla@ achievement!");
				sendMessage("You've earned @blu@25,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 25000;
				totalExp += 25000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 9:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Plus Ten@bla@ achievement!");
				sendMessage("You've earned @blu@100,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 100000;
				totalExp += 100000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 10:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Luck of the Dice@bla@ achievement!");
				sendMessage("You've earned @blu@50,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 50000;
				totalExp += 50000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 11:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Always Geared@bla@ achievement!");
				sendMessage("You've earned @blu@25,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 25000;
				totalExp += 25000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 12:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Specialist@bla@ achievement!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				kits[18] = "Tagged";
				sendMessage("You've unlocked the @blu@Tagged@bla@ kit!!");
				getPA().sendFrame126("@gre@" + kits[18], 16213);
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("[@red@SERVER@bla@] " + playerName + " has just completed the Specialist Achievement! Congratulations.");

					}
				}
			break;
			
			case 13:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Newbie@bla@ achievement!");
				sendMessage("You've earned @blu@10,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 10000;
				totalExp += 10000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 14:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Getting an Upgrade@bla@ achievement!");
				sendMessage("You've earned @blu@25,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 25000;
				totalExp += 25000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 15:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Time to Pk@bla@ achievement!");
				sendMessage("You've earned @blu@5,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 5000;
				totalExp += 5000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 16:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Hidden Treasure@bla@ achievement!");
				sendMessage("You've earned @blu@50,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 50000;
				totalExp += 50000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 17:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Makeover@bla@ achievement!");
				sendMessage("You've earned @blu@5,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 5000;
				totalExp += 5000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 18:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Champion@bla@ achievement!");
				sendMessage("You've earned @blu@35,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 35000;
				totalExp += 35000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 19:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Unstoppable@bla@ achievement!");
				sendMessage("You've earned @blu@150,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 150000;
				totalExp += 150000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 20:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@What Does This Do@bla@ achievement!");
				sendMessage("You've earned @blu@75,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 75000;
				totalExp += 75000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 21:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@I'm Cool@bla@ achievement!");
				sendMessage("You've earned @blu@15,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 15000;
				totalExp += 15000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 22:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Defiled@bla@ achievement!");
				sendMessage("You've earned @blu@9,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 9000;
				totalExp += 9000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 23:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Never Too Late@bla@ achievement!");
				sendMessage("You've earned @blu@20,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 20000;
				totalExp += 20000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 24:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Pacifist@bla@ achievement!");
				sendMessage("You've earned @blu@40,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 40000;
				totalExp += 40000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 25:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Alchemy is Real@bla@ achievement!");
				sendMessage("You've earned @blu@10,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 10000;
				totalExp += 10000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 26:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Insanity@bla@ achievement!");
				sendMessage("You've earned @blu@30,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 30000;
				totalExp += 30000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 27:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Fresh Start@bla@ achievement!");
				sendMessage("You've earned @blu@50,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 50000;
				totalExp += 50000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 28:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@I'm Set@bla@ achievement!");
				sendMessage("You've earned @blu@5,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 5000;
				totalExp += 5000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 29:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Slayer@bla@ achievement!");
				sendMessage("You've earned @blu@15,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 15000;
				totalExp += 15000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 30:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Back From the Abyss@bla@ achievement!");
				sendMessage("You've earned @blu@100,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 100000;
				totalExp += 100000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 31:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Fearless@bla@ achievement!");
				sendMessage("You've earned @blu@125,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 125000;
				totalExp += 125000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 32:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Mage Killer@bla@ achievement!");
				sendMessage("You've earned @blu@25,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 25000;
				totalExp += 25000;
				checkForGrandSlayer();
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 33:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@White Knight@bla@ achievement!");
				sendMessage("You've earned @blu@25,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 25000;
				totalExp += 25000;
				checkForGrandSlayer();
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 34:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Stone Crusher@bla@ achievement!");
				sendMessage("You've earned @blu@25,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 25000;
				totalExp += 25000;
				checkForGrandSlayer();
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 35:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Not so Cold@bla@ achievement!");
				sendMessage("You've earned @blu@25,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 25000;
				totalExp += 25000;
				checkForGrandSlayer();
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 36:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Grand Slayer@bla@ achievement!");
				sendMessage("You've earned @blu@100,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 100000;
				totalExp += 100000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			case 37:
				achievements[index][0] = 1;
				sendMessage("Congratulations! You've completed the @blu@Devil of The Games@bla@ achievement!");
				sendMessage("You've earned @blu@50,000@bla@ experience!");
				getPA().sendFrame126("@gre@" + achievementsStrings[index], 16600 + index);
				totalHungerGameExp += 50000;
				totalExp += 50000;
				checkForSpecialist();
				reloadHGstuff();
			break;
			
			default:
			break;
		}
	}
	
	public void checkForSpecialist() {
		if (achievements[12][0] == 1)
			return;
		
		int add = perkOne + perkTwo + perkThreeMelee + perkFourRange + perkFiveMagic + perkSixPray + perkSevenIce + perkEightStat + perkKaboom + perkGifted + perkSwitch + perkVeng + perkConnect + perkConquer;
		int achCounter = 0;
		
		for (int i = 0; i < achievements.length; i++) {
			if (achievements[i][0] == 1) {
				achCounter++;
			}
		}
		
		if (add >= 10 && achCounter >= 10) {
			achievements[12][1] = 1;
			achievementsHandler();
		}
	}
	
	public void checkForGrandSlayer() {
		if (achievements[32][0] == 1 && achievements[33][0] == 1 && achievements[34][0] == 1 && achievements[35][0] == 1) {
			if (achievements[36][0] == 0) {
				achievements[36][1] = 1;
				achievementsHandler();
			}
		}
	}
	
	public boolean yellCheck() {
		int add = perkOne + perkTwo + perkThreeMelee + perkFourRange + perkFiveMagic + perkSixPray + perkSevenIce + perkEightStat + perkKaboom + perkGifted + perkSwitch + perkVeng + perkConnect + perkConquer;
		int achCounter = 0;
		
		for (int i = 0; i < achievements.length; i++) {
			if (achievements[i][0] == 1) {
				achCounter++;
			}
		}
		
		if (add >= 15 && achCounter >= 20 && totalGameWins >= 30) {
			return true;
		} else {
			return false;
		}
	}
	
	public void cmdBook1() {
		
	}
	public void cmdBook2() {
		
	}
	public void cmdBook3() {
		
	}
	public void cmdBook4() {
		
	}
	public void cmdBook5() {
		
	}
	
	public void calcCombat() {
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
			getPA().sendFrame126("Combat Level: @yel@ " + combatLevel + "", 25031);

	}
	
	public void update() {
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
		
	}
	
	public String getMacAddress() {
		try {
			InetAddress a = connectedLocal;
			NetworkInterface n = NetworkInterface.getByInetAddress(a);
			byte[] m = n.getHardwareAddress();
			StringBuilder sb = new StringBuilder();
				for (int i = 0; i < m.length; i++) {
				sb.append(String.format("%02X%s", m[i], (i <m.length - 1) ? "-" : ""));
				}
				connectedMac = sb.toString();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return connectedMac;
	}
	
	public boolean runOnce = true;
	
	public void logout() {
			if(System.currentTimeMillis() - logoutDelay > 10000) {
				if (eventStarted) {
					//er need something to end it
					//EventManager.getSingleton().stopEvents(this);
				}
				if (pickingNumber) {
					totalHungerGameExp += myBet;
					gamblePot -= myBet;
					gambleFinal = false;
					myBet = 0;
				} else if (myNumber > 0 && startRollin) {
					if (gamblerOne.equals(playerName) || gamblerTwo.equals(playerName)) {
						gambleLogged = true;
					}
				} else if (myNumber > 0) {
					if (gambleOn && !gambleFinal) {
						gamblePot = 0;
						gambleNum1 = false;
						gambleNum2 = false;
						gambleNum3 = false;
						firstGamble = false;
						gamblerOne = null;
						gamblerTwo = null;
						startRollin = false;
						gambleFinal = false;
						totalHungerGameExp += myBet;
						myBet = 0;
						gambleOn = false;
						firstGamblerAmount = 0;
						gamblerName = null;
					}
				} else if (myBet > 0) {
					totalHungerGameExp += myBet;
					gamblePot -= myBet;
					gambleFinal = false;
					myBet = 0;
				}
				if (inCwWait) {
					inCwWait = false;
					if (randomMap == 0) {
						Server.HungerGames.currentPlayersWait.remove(Server.HungerGames.currentPlayersWait.indexOf(playerId));
					} else if (randomMap == 1) {
						Server.HungerGamesFal.currentPlayers_FalWait.remove(Server.HungerGamesFal.currentPlayers_FalWait.indexOf(playerId));
					} else if (randomMap == 2) {
						Server.HungerGamesCan.currentPlayers_CanWait.remove(Server.HungerGamesCan.currentPlayers_CanWait.indexOf(playerId));
					} else if (randomMap == 3) {
						Server.HungerGamesMisc.currentPlayers_MiscWait.remove(Server.HungerGamesMisc.currentPlayers_MiscWait.indexOf(playerId));
					} else if (randomMap == 3) {
						Server.Infection.currentPlayers_Wait.remove(Server.Infection.currentPlayers_Wait.indexOf(playerId));
					}
					getPA().movePlayer(3363, 9640, 0);
				} else if (inCwGame) {
					if(myKit.equalsIgnoreCase("JUMPER")) {
						getCombat().cleanPortal(portalX, portalY);
						getCombat().resetPersonalPortal();
					}
					getItems().deleteAllItems();
					inCwGame = false;
					if (randomMap == 0) {
						Server.HungerGames.currentPlayers.remove(Server.HungerGames.currentPlayers.indexOf(playerId));
					} else if (randomMap == 1) {
						Server.HungerGamesFal.currentPlayers_Fal.remove(Server.HungerGamesFal.currentPlayers_Fal.indexOf(playerId));
					} else if (randomMap == 2) {
						Server.HungerGamesCan.currentPlayers_Can.remove(Server.HungerGamesCan.currentPlayers_Can.indexOf(playerId));
					} else if (randomMap == 3) {
						Server.HungerGamesMisc.currentPlayers_Misc.remove(Server.HungerGamesMisc.currentPlayers_Misc.indexOf(playerId));
					} else if (randomMap == 4) {
						Server.Infection.currentPlayers.remove(Server.Infection.currentPlayers.indexOf(playerId));
					}
					noSmuggle = 0;
					getPA().movePlayer(3363, 9640, 0);
				}
				if (inPkingZone) {
					//edgePlayers.remove(edgePlayers.indexOf(playerId));
					inPkingZone = false;
				}
				Highscores.highSave(this);
				outStream.createFrame(109);
				properLogout = true;
				ConnectedFrom.addConnectedFrom(this, connectedFrom);
			} else {
				sendMessage("You must wait a few seconds from being out of combat to logout.");
			}
		
	}
	
	public int kitAmount() {
		int total = 0;
		for (int i = 0; i < kits.length; i++) {
			if (kits[i] != null) {
				total++;
			}
		}
		return total;
	}
	
	public int perkAmount() {
		int total = 0;
		
		if (perkOne == 1)
			total++;
		if (perkTwo == 1)
			total++;
		if (perkThreeMelee == 1) {
			total++;
		} else if (perkThreeMelee == 2) {
			total += 2;
		}
		if (perkFourRange == 1) {
			total++;
		} else if (perkFourRange == 2) {
			total += 2;
		}
		if (perkFiveMagic == 1) {
			total++;
		} else if (perkFiveMagic == 2) {
			total += 2;
		}
		if (perkSixPray == 1)
			total++;
		if (perkSevenIce == 1)
			total++;
		if (perkEightStat == 1)
			total++;
		if (perkKaboom == 1)
			total++;
		if (perkGifted == 1)
			total++;
		if (perkSwitch == 1)
			total++;
		if (perkVeng == 1)
			total++;
		if (perkConnect == 1)
			total++;
		if (perkConquer == 1)
			total++;
		if (perkVengTwo == 1)
			total++;
		if (perkLazy == 1)
			total++;
		if (pumpkinPerk == 1)
			total++;
		if (beerPerk == 1)
			total++;
			
		return total;
	}
	
	public void logout2() {
		outStream.createFrame(109);
		properLogout = true;
		ConnectedFrom.addConnectedFrom(this, connectedFrom);
	}
				
				
	public void remote() {
		if (inWild() || (isInPVP() || (isInMinigame())));
			return;
	}
	
	public void giveKitItems() {
		switch (myKit)
		{
			case "BLIZZARD":
				getItems().addItem(1580, 1);//ice gloves
				sendMessage("You get some ice gloves.");
			break;
			
			case "FLASH":
				getItems().addItem(88, 1);//boots of lightness
				sendMessage("You get some boots of lightness.");
			break;
			
			case "SICKNESS":
				sicknessPoisons = 10;
				sendMessage("Your Sickness poison has been reset to 10.");
			break;
			
			case "TAINTED":
				getItems().addItem(1724, 1);//holy sym
				getItems().addItem(6950, 1);//orb
				sendMessage("You get a magic orb and an unholy symbol.");
			break;
			
			case "NECROMORPHER":
				getItems().addItem(4837, 1);//book
				playerMagicBook = 1;
				setSidebarInterface(6, 12855);
				sendMessage("An ancient wisdomin fills your mind.");
				autocastId = -1;
				getPA().resetAutocast();
			break;
			
			case "SAINT":
				getItems().addItem(1718, 1);//holy sym
				sendMessage("You get a holy symbol.");
			break;
			
			case "KNIGHT":
				getItems().addItem(35, 1);//excal
				getItems().addItem(748, 1);//holy force
				sendMessage("You get Excalibur and Holy Force.");
			break;
		}
	}

	public static int packetSize = 0, packetType = -1;

	public static int tradeTimer, randomFactCount = 0;

	public void process() {
		
		applyFollowing();

		if(followId > 0) {
			getPA().followPlayer();
		} else if (followId2 > 0) {
			getPA().followNpc();
		}
		
		if (System.currentTimeMillis() - lastAchCheck > 180000) {
			lastAchCheck = System.currentTimeMillis();
			achievementsHandler();
			checkForSpecialist();
		}
		if (notallowedarea()) {
			sendMessage("You have been teleported back to home.");
			getPA().movePlayer(3363, 9640, 0);
		}
		
		if (modZone()) {
			if (playerRights < 1) {
				if (!modAccess) {
					sendMessage("This area is blocked off.");
					getPA().movePlayer(3363, 9640, 0);
				}
			}
		}
		
		/** Pking processes **/
		if (!inPkingZone && inBlockedout()) {
			if (inCwGame) {
				if (!isDead) {
					sendMessage("The Gamemakers have decided to kill you.");
					dealDamage(150);
					handleHitMask(150);
					getPA().refreshSkill(3);
				}
			} else if (!spectate) {
				sendMessage("This area is part of the Edgeville pking area!");
				getPA().movePlayer(3363, 9640, 0);
			}
		}
		if (inPkingZone && inPkingZone()) {
			if(inWild() && !inCwGame) {
				int modY = absY > 6400 ?  absY - 6400 : absY;
				wildLevel = (((modY - 3521) / 8) + 1);
				getPA().walkableInterface(197);
				if(Config.SINGLE_AND_MULTI_ZONES) {
					if(inMulti()) {
						getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
					} else {
						getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
					}
				} else {
					getPA().multiWay(-1);
					getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
				}
				getPA().showOption(3, 0, "Attack", 1);
			}
			if (badZone()) {
				getPA().movePlayer(3087, 3499, 0);	
			}
		} else if (inPkingZone && !inPkingZone()) {
			if (!isDead) {
				if (absY > 3575) {
					sendMessage("Please return back to the Edgeville pking area!");
					dealDamage(15);
					handleHitMask(15);
					getPA().refreshSkill(3);
				} else {
					sendMessage("You can't leave the Edgeville area, you've been teleported back!");
					getPA().movePlayer(3087, 3499, 0);
				}
			}
		} else if (!inPkingZone && inPkingZone()) {
			if (!tutorial) {
				sendMessage("This area is part of the Edgeville pking area!");
				getPA().movePlayer(3363, 9640, 0);
			}
		}
		
		/** Random Fact process **/
		if (System.currentTimeMillis() - lastFact > 600000) {
			randomFact();
			lastFact = System.currentTimeMillis();
		}
		
		/** Gambling processes **/
		if (rollDaDice) {
			rollDaDice = false;
			rollDice();
		}
		
		if (gambleOn) {
			if (System.currentTimeMillis() - gambleTimer > 200000) {//little more than 3min
				if (playerName.equals(gamblerOne) && !gambleFinal) {
					gamblePot = 0;
					gambleNum1 = false;
					gambleNum2 = false;
					gambleNum3 = false;
					firstGamble = false;
					gamblerOne = null;
					gamblerTwo = null;
					startRollin = false;
					gambleFinal = false;
					totalHungerGameExp += myBet;
					gamblerName = null;
					myBet = 0;
					gambleOn = false;
					reloadHGstuff();
					sendMessage("It seems no one bet within the time limit, you're exp has been returned.");
				}
			}
		}

		if (System.currentTimeMillis() - lastSeed > 15000) {
			ObjectManager.removeFlower(flowerX, flowerY);
		}
		
		/** Hunger games Process stuff **/
		if (inCwWait && openTrade == true && !inCwGame) {
			getTradeAndDuel().declineTrade();
		}
		
		if(System.currentTimeMillis() - lastNecro > 35000 && necroMode || isDead && necroMode) {
			lastNecro2 = System.currentTimeMillis();
			necroMode = false;
			isNpc = false;
			updateRequired = true;
			appearanceUpdateRequired = true;
			sendMessage("You've been morphed back to a human!");
		}
		
		if (inWretched && System.currentTimeMillis() - lastCheck > 10000 && !inDeathBattle()) {
			lastCheck = System.currentTimeMillis();
			getItems().resetBonus();
			getItems().getBonus();
			getItems().writeBonus();
		}
		
		if (inWretched) {
			if(System.currentTimeMillis() - lastWretched > 35000 || isDead) {
				lastWretched = System.currentTimeMillis();
				inWretched = false;
				isNpc = false;
				getItems().sendWeapon(playerEquipment[playerWeapon], getItems().getItemName(playerEquipment[playerWeapon]));
				getItems().resetBonus();
				getItems().getBonus();
				getItems().writeBonus();
				getCombat().getPlayerAnimIndex(getItems().getItemName(playerEquipment[playerWeapon]).toLowerCase());
				updateRequired = true;
				appearanceUpdateRequired = true;
				wretchedId = -1;
				autocasting = false;
				sendMessage("The anicent power of the barrows brother fades from you!");
			}
		}
		
		if(System.currentTimeMillis() - lastKnight > 20000 && knightMode || isDead && knightMode) {
			lastKnight2 = System.currentTimeMillis();
			knightMode = false;
			sendMessage("The protection from Saradomin has stopped.");
		}
		
		if (noSmuggleTwo >= 0) {
			noSmuggleTwo--;
			if (noSmuggleTwo == 0) {
				getItems().deleteAllItems();
				isFullHelm = Item.isFullHelm(playerEquipment[playerHat]);
				isFullMask = Item.isFullMask(playerEquipment[playerHat]);
				isFullBody = Item.isFullBody(playerEquipment[playerChest]);
				getPA().requestUpdates();
				
				//Add extra reward items here
				if (!noEventItem && Config.EVENT) {
					/** Event Item **/
					if (Misc.random(15) == 0) {
						getItems().addItem(1044, 1);
						sendMessage("You've won a Green PartyHat for winning during the Easter event!");
						String winner = playerName;
						for (int player = 0; player < Server.playerHandler.players.length; player++) {
							if (Server.playerHandler.players[player] != null) {
								Client c2 = (Client)Server.playerHandler.players[player];
								c2.sendMessage("[@red@SERVER@bla@] @blu@" + winner + " @bla@just won a Green PartyHat!");
							}
						}
					} else {
						getItems().addItem(1961, 1);
						sendMessage("You've won an Easter Egg for winning during the Easter event!");
					}
				} else {
					noEventItem = false;
				}
				
				if (lampReward) {
					if (Misc.random(2) == 0) {
						getItems().addItem(lampItems(), 1);
						getItems().addItem(lampItems(), 1);
						getItems().addItem(lampItems(), 1);
					} else {
						getItems().addItem(lampItems(), 1);
						getItems().addItem(lampItems(), 1);
						getItems().addItem(299, Misc.random(20) + 10);
					}
					lampReward = false;
					sendMessage("You get some rare items from the lamp that you found in-game.");
				}
				
				if (prizeAmount == 1) {
					getItems().addItem(995, 20000);
				} else if (prizeAmount == 2) {
					getItems().addItem(995, 30000);
				} else if (prizeAmount == 3) {
					getItems().addItem(995, 50000);
				}
				
				prizeAmount = 0;
				headIconPk = 1;
			}
		}
		
		if (retBleedinDam > 0) {
			if (inCwGame) {
				if (System.currentTimeMillis() - lastBleeding > 10000) {
					lastBleeding = System.currentTimeMillis();
					int bleed = Misc.random(5);
					dealDamage(bleed);
					handleHitMask(bleed);
					retBleedinDam--;
					getPA().refreshSkill(3);
				}
			} else {
				retBleedinDam = 0;
			}
		}
		
		if (System.currentTimeMillis() - lastGameMessage > 5000) {
			lastGameMessage = System.currentTimeMillis();
			handleGameMessage();
		}
		
		if (stuck && System.currentTimeMillis() - stuckTimer > 5000 && stuckX == getX() && stuckY == getY()) {
			getPA().movePlayer2((getX() - 5) + Misc.random(10), (getY() - 5) + Misc.random(10), 0);
			stuckTimer = 0;
			stuckX = 0;
			stuckY = 0;
			stuck = false;
			sendMessage("You've been moved to a random nearby spot!");
		} else if (stuck && stuckX != getX() && stuckY != getY()) {
			stuck = false;
			stuckTimer = 0;
			stuckX = 0;
			stuckY = 0;
			sendMessage("Unstuck failed because you moved!");
		}
		
		if (chaosAttack > 0) {
			if (!isDead) {
				dealDamage(1);
				handleHitMask(1);
				getPA().refreshSkill(3);
				chaosAttack--;
			} else {
				chaosAttack = 0;
			}
		}
		
		if(HGAttack || inCwGame) {
			switch (randomMap)
			{
				case 0: 
					if (inHomeArea()) {
						if (startDeathBattle) {
							sendMessage("error11 - you've been forced back to the game area.");
							getPA().movePlayer(3098 + Misc.random(14), 3926 + Misc.random(14), 0);
						} else if (!startDeathBattle) {
							sendMessage("error11_2 - you've been forced back to the game area.");
							getPA().movePlayer(3216, 3422, 0);
						}
					}
					if(!inVarrockPvp() && !startDeathBattle && !isInLobby()) {
						if (!isDead && jumperTimer < 0) {
							sendMessage("You're not within the combat area! Get back in or you'll die!");
							dealDamage(10);
							handleHitMask(10);
							getPA().refreshSkill(3);
						}
						if (jumperTimer >= 0) {
							sendMessage("You've been teleported out of the combat area! Get back in or you'll die!");
							jumperTimer--;
						}
					} else if (startDeathBattle) {
						if (inVarrockPvp()) {
							getPA().movePlayer(3098 + Misc.random(14), 3926 + Misc.random(14), 0);
						}
						if (!inVarrockPvp()) {
							if(!inDeathBattle()) {
								if (!isDead) {
									sendMessage("You can't escape from the death match!");
									dealDamage(15);
									handleHitMask(15);
									getPA().refreshSkill(3);
								}
							}
						}
					}
				break;
				
				case 1:
					if (inHomeArea()) {
						if (startDeathBattle) {
							sendMessage("error12 - you've been forced back to the game area.");
							getPA().movePlayer(3152 + Misc.random(22), 3660 + Misc.random(22), 0);
						} else if (!startDeathBattle) {
							sendMessage("error12_2 - you've been forced back to the game area.");
							getPA().movePlayer(2970, 3383, 0);
						}
					}
					if(!inFalPvp() && !startDeathBattleFal && !isInLobbyFal()) {
						if (!isDead && jumperTimer < 0) {
							sendMessage("You're not within the combat area! Get back in or you'll die!");
							dealDamage(10);
							handleHitMask(10);
							getPA().refreshSkill(3);
						}
						if (jumperTimer >= 0) {
							sendMessage("You've been teleported out of the combat area! Get back in or you'll die!");
							jumperTimer--;
						}
					} else if (startDeathBattleFal) {
						if (inFalPvp()) {
							getPA().movePlayer(3152 + Misc.random(22), 3660 + Misc.random(22), 0);
						}
						if (!inFalPvp()) {
							if(!inDeathBattle()) {
								if (!isDead) {
									sendMessage("You can't escape from the death match!");
									dealDamage(15);
									handleHitMask(15);
									getPA().refreshSkill(3);
								}
								if (inPkingZone()) {
									sendMessage("You can't escape from the death match!");
									dealDamage(50);
									handleHitMask(50);
									getPA().refreshSkill(3);
								}
							}
						}
					}
				break;
				
				case 2:
					if (inHomeArea()) {
						if (startDeathBattle) {
							getPA().movePlayer(3152 + Misc.random(22), 3660 + Misc.random(22), 0);
							sendMessage("error12 - you've been forced back to the game area.");
						} else if (!startDeathBattle) {
							sendMessage("error12_2 - you've been forced back to the game area.");
							getPA().movePlayer(3305, 2787, 0);
						}
					}
					if(!inCanPvp() && !startDeathBattleCan && !isInLobbyCan()) {
						if (!isDead && jumperTimer < 0) {
							sendMessage("You're not within the combat area! Get back in or you'll die!");
							dealDamage(10);
							handleHitMask(10);
							getPA().refreshSkill(3);
						}
						if (jumperTimer >= 0) {
							sendMessage("You've been teleported out of the combat area! Get back in or you'll die!");
							jumperTimer--;
						}
					} else if (startDeathBattleCan) {
						if (inCanPvp()) {
							getPA().movePlayer(3280 + Misc.random(22), 3019 + Misc.random(10), 0);
						}
						if (!inCanPvp()) {
							if(!inDeathBattle()) {
								if (!isDead) {
									sendMessage("You can't escape from the death match!");
									dealDamage(15);
									handleHitMask(15);
									getPA().refreshSkill(3);
								}
							}
						}
					}
				break;
				
				case 3:
					if (inHomeArea()) {
						if (startDeathBattle) {
							getPA().movePlayer(2787 + Misc.random(15), 3774 + Misc.random(19), 0);
							sendMessage("error12 - you've been forced back to the game area.");
						} else if (!startDeathBattle) {
							sendMessage("error12_2 - you've been forced back to the game area.");
							getPA().movePlayer(2544, 3869, 0);
						}
					}
					if(!inMiscPvp() && !startDeathBattleMisc && !isInLobbyMisc()) {
						if (!isDead && jumperTimer < 0) {
							sendMessage("You're not within the combat area! Get back in or you'll die!");
							dealDamage(10);
							handleHitMask(10);
							getPA().refreshSkill(3);
						}
						if (jumperTimer >= 0) {
							sendMessage("You've been teleported out of the combat area! Get back in or you'll die!");
							jumperTimer--;
						}
					} else if (startDeathBattleMisc) {
						if (inMiscPvp()) {
							getPA().movePlayer(2787 + Misc.random(15), 3774 + Misc.random(19), 0);
						}
						if (!inMiscPvp()) {
							if(!inDeathBattle()) {
								if (!isDead) {
									sendMessage("You can't escape from the death match!");
									dealDamage(15);
									handleHitMask(15);
									getPA().refreshSkill(3);
								}
							}
						}
					}
				break;
				
				case 4:
					if (inHomeArea()) {
						sendMessage("error12_2 - you've been forced back to the game area.");
						getPA().movePlayer(2544, 3869, 0);
					}
					if(!inInfectedPvp() && !isInLobbyInfect()) {
						if (!isDead) {
							sendMessage("This area is toxic, return back quickly!");
							dealDamage(10);
							handleHitMask(10);
							getPA().refreshSkill(3);
						}
					}
				break;
				
				default: System.out.println("Something is wrong, check the int. RandomMap: " + randomMap);
				break;
			}
			if (badZone()) {
				sendMessage("You're not within the combat area! Get back in or you'll die!");
				dealDamage(99);
				handleHitMask(99);
				getPA().refreshSkill(3);
			}
		} else if (!inCwGame && inVarrockPvp() && !spectate || !inCwGame && inFalPvp() && !spectate || !inCwGame && inCanPvp() && !spectate || !inCwGame && inMiscPvp() && !spectate || !inCwGame && inDeathBattle() && !spectate || !inCwGame && inInfectedPvp() && !spectate) {
			if (playerName.equalsIgnoreCase("peeta")) {
				
			} else {
				if (!readdedIn) {
					sendMessage("You may only enter this area if you're in a match!");
					getPA().movePlayer(3363, 9640, 0);
				}
			}
		}
		
		if (playerRights < 2 && !readdedIn) {
			if (overWorld() && !inPkingZone && !inCwGame && !inCwWait && !HGAttack && !tutorial && !spectate) {
				sendMessage("You can't be in this area!");
				getPA().movePlayer(3363, 9640, 0);
			}
			if (inHomeArea()) {
				if (inPkingZone) {
					sendMessage("error12_3 - you've been forced back to the game area.");
					getPA().movePlayer(3087, 3499, 0);
				}
			}
			if (!inCwWait && !inCwGame && (isInLobby() || isInLobbyFal() || isInLobbyCan() || isInLobbyMisc())) {
				getPA().movePlayer(3363, 9640, 0);
			}
		}
		
		if (HGAttack) {
			getPA().showOption(3, 0, "Attack", 1);
		} else if (!inWild()) {
			getPA().showOption(3, 0, "Null", 1);
		}
		
		if (inCwGame) {
			if(System.currentTimeMillis() - lastSnow < 60000) {
				getPA().walkableInterface(11877);
			} else {
				if (desertSand()) {
					getPA().walkableInterface(16152);
				} else if (hiddenRoom) {
					getPA().walkableInterface(14600);
				} else {
					getPA().walkableInterface(22100);
				}
				setSidebarInterface(3, 3213);
			}
		} else if (inCwWait) {
			setSidebarInterface(3, 21150);
			if (waitInterface == false) {
				getPA().walkableInterface(22130);
			} else {
				getPA().walkableInterface(-1);
			}
		} else if (!inWild()) {
			getPA().walkableInterface(-1);
			setSidebarInterface(3, 3213);
		}
		
		if (randomMap == 0) {
			if (inCwWait && !isInLobby() && !spectate)
				getPA().movePlayer(3040, 2910, 0);
		} else if (randomMap == 1) {
			if (inCwWait && !isInLobbyFal() && !spectate)
				getPA().movePlayer(3118, 2888, 0);
		} else if (randomMap == 2) {
			if (inCwWait && !isInLobbyCan() && !spectate)
				getPA().movePlayer(3118, 2980, 0);
		} else if (randomMap == 3) {
			if (inCwWait && !isInLobbyMisc() && !spectate)
				getPA().movePlayer(2595, 3808, 0);
		} else if (randomMap == 4) {
			if (inCwWait && !isInLobbyInfect() && !spectate)
				getPA().movePlayer(3165, 9633, 0);
		}
		
		if (startSnow) {
			getPA().walkableInterface(11877);
		}
		
		/** End of HG stuff **/

		if (tradeTimer > 0) {
			tradeTimer--;
		}

		if(clawDelay > 0) {
			clawDelay--;
		}
		
		/*if (overloadcounter > 0) {
			startAnimation(3170);
			dealDamage(10);
			handleHitMask(10);
			overloadcounter -= 1;
			getPA().refreshSkill(3);
		}*/
		
		if(clawDelay == 1) {
		double damage4 = 0;
			if(npcIndex > 0) {
				getCombat().applyNpcMeleeDamage(npcIndex, 1, previousDamage / 2);
			}
			if(playerIndex > 0) {
				getCombat().applyPlayerMeleeDamage(playerIndex, 1, previousDamage / 2);
			}
			damage4 = previousDamage % 2;
			if(damage4 >= 0.001) {
				previousDamage = previousDamage + 1;
				damage4 = 0;
			}
			if(npcIndex > 0) {
				getCombat().applyNpcMeleeDamage(npcIndex, 2, previousDamage);
			}
			if(playerIndex > 0) {
				getCombat().applyPlayerMeleeDamage(playerIndex, 2, previousDamage);
			}
			clawDelay = 0;
			specEffect = 0;
			previousDamage = 0;
			usingClaws = false;
		}

		if (System.currentTimeMillis() - lastPoison > 20000 && poisonDamage > 0) {
			int damage = poisonDamage/2;
			if (damage > 0) {
				if (!getHitUpdateRequired()) {
					if (playerLevel[3] < 6 && inDuelArena() || myKit.equalsIgnoreCase("VENOM")) {
		                   // sendMessage("No Damage");
		            } else {
						sendMessage("Applying poison damage.");
						setHitUpdateRequired(true);
						setHitDiff(damage);
						updateRequired = true;
						poisonMask = 1;
						getPA().refreshSkill(3);
		            }
				} else if (!getHitUpdateRequired2()) {
					 if (playerLevel[3] < 6 && inDuelArena() || myKit.equalsIgnoreCase("VENOM")) {
		                    //sendMessage("No Damage");
		                } else {
							setHitUpdateRequired2(true);
							setHitDiff2(damage);
							updateRequired = true;
							poisonMask = 2;
							getPA().refreshSkill(3);
		                }
				}
				lastPoison = System.currentTimeMillis();
				poisonDamage--;
				 if (playerLevel[3] < 6 && inDuelArena()) {
	                    //sendMessage("No Damage");
	                } else {
						dealDamage(damage);
						getPA().refreshSkill(3);
	                }
			} else {
				poisonDamage = -1;
				sendMessage("You are no longer poisoned.");
			}
		}

		if (perkOne == 1 && inCwGame) {
			SPEC = 7250;
		} else {
			SPEC = Config.INCREASE_SPECIAL_AMOUNT;
		}
		
		if(System.currentTimeMillis() - specDelay > SPEC) {
			specDelay = System.currentTimeMillis();
			if(specAmount < 10) {
				specAmount += .5;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}

		if(clickObjectType > 0 && goodDistance(objectX + objectXOffset, objectY + objectYOffset, getX(), getY(), objectDistance)) {
			if(clickObjectType == 1) {
				getActions().firstClickObject(objectId, objectX, objectY);
			}
			if(clickObjectType == 2) {
				getActions().secondClickObject(objectId, objectX, objectY);
			}
			if(clickObjectType == 3) {
				getActions().thirdClickObject(objectId, objectX, objectY);
			}
		}

		if((clickNpcType > 0) && Server.npcHandler.npcs[npcClickIndex] != null) {
			if(goodDistance(getX(), getY(), Server.npcHandler.npcs[npcClickIndex].getX(), Server.npcHandler.npcs[npcClickIndex].getY(), 1)) {
				if(clickNpcType == 1) {
					turnPlayerTo(Server.npcHandler.npcs[npcClickIndex].getX(), Server.npcHandler.npcs[npcClickIndex].getY());
					Server.npcHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().firstClickNpc(npcType);
				}
				if(clickNpcType == 2) {
					turnPlayerTo(Server.npcHandler.npcs[npcClickIndex].getX(), Server.npcHandler.npcs[npcClickIndex].getY());
					Server.npcHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().secondClickNpc(npcType);
				}
				if(clickNpcType == 3) {
					turnPlayerTo(Server.npcHandler.npcs[npcClickIndex].getX(), Server.npcHandler.npcs[npcClickIndex].getY());
					Server.npcHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().thirdClickNpc(npcType);
				}
			}
		}

		if(walkingToItem) {
			if(getX() == pItemX && getY() == pItemY || goodDistance(getX(), getY(), pItemX, pItemY,1)) {
				walkingToItem = false;
				Server.itemHandler.removeGroundItem(this, pItemId, pItemX, pItemY, true);
			}
		}

		getCombat().handlePrayerDrain();

		if(System.currentTimeMillis() - singleCombatDelay >  9900) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 9900) {
			underAttackBy2 = 0;
		}

		if(System.currentTimeMillis() - restoreStatsDelay >  60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++)  {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if(level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}

		if(System.currentTimeMillis() - teleGrabDelay >  1550 && usingMagic) {
			usingMagic = false;
			if(Server.itemHandler.itemExists(teleGrabItem, teleGrabX, teleGrabY)) {
				Server.itemHandler.removeGroundItem(this, teleGrabItem, teleGrabX, teleGrabY, true);
			}
		}

		if(!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}

		if(hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}

		if(skullTimer > 0) {
			if(skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}
			skullTimer--;
		}

		if(isDead && respawnTimer == -6) {
			getPA().applyDead();
		}

		if(respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if(respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
			poisonDamage = -1;
		}

		if(respawnTimer > -6) {
			respawnTimer--;
		}
		
		if(freezeTimer > -6) {
			if (perkSevenIce == 1) {
				freezeTimer--;
			}
			freezeTimer--;
			if (frozenBy > 0) {
				if (Server.playerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY, Server.playerHandler.players[frozenBy].absX, Server.playerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}

		if(hitDelay > 0) {
			hitDelay--;
		}

		if(teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if(teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if(teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if(teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					if (teleGfx == 678) {
					gfx0(teleGfx);
					} else {
					gfx100(teleGfx);
					}
				}
			} else {
				teleTimer = 0;
			}
		}

		if(hitDelay == 1) {
			if(oldNpcIndex > 0) {
				getCombat().delayedHit(oldNpcIndex);
			}
			if(oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(oldPlayerIndex);
			}
		}

		if(attackTimer > 0) {
			attackTimer--;
		}

		if(attackTimer == 1){
			if(npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if(playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}

		if(timeOutCounter > Config.TIMEOUT) {
			disconnected = true;
		}

		timeOutCounter++;

		if(inTrade && tradeResetNeeded){
			Client o = (Client) Server.playerHandler.players[tradeWith];
			if(o != null){
				if(o.tradeResetNeeded){
					getTradeAndDuel().resetTrade();
					o.getTradeAndDuel().resetTrade();
				}
			}
		}
	}

	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}

	public Stream getInStream() {
		return inStream;
	}

	public int getPacketType() {
		return packetType;
	}

	public int getPacketSize() {
		return packetSize;
	}

	public Stream getOutStream() {
		return outStream;
	}

	public ItemAssistant getItems() {
		return itemAssistant;
	}

	public PlayerAssistant getPA() {
		return playerAssistant;
	}

	public DialogueHandler getDH() {
		return dialogueHandler;
	}

	public ShopAssistant getShops() {
		return shopAssistant;
	}

	public TradeAndDuel getTradeAndDuel() {
		return tradeAndDuel;
	}

	public CombatAssistant getCombat() {
		return combatAssistant;
	}

	public ActionHandler getActions() {
		return actionHandler;
	}

	public IoSession getSession() {
		return session;
	}

	public Potions getPotions() {
		return potions;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	public Food getFood() {
		return food;
	}

	/**
	 * Skill Constructors
	 */
	public Slayer getSlayer() {
		return slayer;
	}

	public Runecrafting getRunecrafting() {
		return runecrafting;
	}

	public Woodcutting getWoodcutting() {
		return woodcutting;
	}

	public Mining getMining() {
		return mine;
	}

	public Cooking getCooking() {
		return cooking;
	}

	public Agility getAgility() {
		return agility;
	}

	public Fishing getFishing() {
		return fish;
	}

	public Crafting getCrafting() {
		return crafting;
	}

	public Smithing getSmithing() {
		return smith;
	}

	public Farming getFarming() {
		return farming;
	}

	public Thieving getThieving() {
		return thieving;
	}

	public Herblore getHerblore() {
		return herblore;
	}

	public Firemaking getFiremaking() {
		return firemaking;
	}

	public SmithingInterface getSmithingInt() {
		return smithInt;
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public Fletching getFletching() {
		return fletching;
	}

	/**
	 * End of Skill Constructors
	 */

	public void queueMessage(Packet arg1) {
			//if (arg1.getId() != 41)
				queuedPackets.add(arg1);
			//else
				//processPacket(arg1);
		
	}

	public synchronized boolean processQueuedPackets() {
		Packet p = null;
		synchronized(queuedPackets) {
			p = queuedPackets.poll();
		}
		if(p == null) {
			return false;
		}
		inStream.currentOffset = 0;
		packetType = p.getId();
		packetSize = p.getLength();
		inStream.buffer = p.getData();
		if(packetType > 0) {
			//sendMessage("PacketType: " + packetType);
			PacketHandler.processPacket(this, packetType, packetSize);
		}
		timeOutCounter = 0;
		return true;
	}

	public synchronized boolean processPacket(Packet p) {
		synchronized (this) {
			if(p == null) {
				return false;
			}
			inStream.currentOffset = 0;
			packetType = p.getId();
			packetSize = p.getLength();
			inStream.buffer = p.getData();
			if(packetType > 0) {
				//sendMessage("PacketType: " + packetType);
				PacketHandler.processPacket(this, packetType, packetSize);
			}
			timeOutCounter = 0;
			return true;
		}
	}


	public void correctCoordinates() {
		if (inFightCaves()) {
			getPA().movePlayer(absX, absY, playerId * 4);
			sendMessage("Your wave will start in 10 seconds.");
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer c) {
					Server.fightCaves.spawnNextWave((Client)Server.playerHandler.players[playerId]);
					c.stop();
				}
				}, 10000);

		}

	}

}