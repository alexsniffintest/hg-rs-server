package server.world;

import server.Server;
import server.model.objects.Object;
import server.model.players.Client;
import server.util.Misc;

import java.util.ArrayList;

/**
 * @author Sanity
 */

public class ObjectManager {

	public ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Object> toRemove = new ArrayList<Object>();
	public static int chestsToSpawn;
	
	//0 north, 1 east, 2 south, 3 west
	private static int[][] chestsLoad = {
		{ 3226, 3437, 2, 0 }, //beside blacksmith
		{ 3239, 3425, 3, 0 }, //inside fletching place
		{ 3221, 3413, 1, 0 }, //beside gen store
		{ 3209, 3440, 0, 0 }, //inside part of inner wall, north of center
		{ 3225, 3461, 1, 0 }, //right side of castle
		{ 3233, 3460, 3, 0 }, //right side of castle by inner wall
		{ 3240, 3469, 2, 0 }, //house left of yew tree
		{ 3261, 3474, 3, 0 }, //east of church
		{ 3256, 3486, 1, 0 }, //inside church
		{ 3238, 3431, 2, 0 }, //south of fountain by blacksmith
		{ 3251, 3439, 2, 0 }, //training room
		{ 3260, 3420, 1, 0 }, //right side of east bank
		{ 3267, 3408, 0, 0 }, //beside tea stand
		{ 3261, 3403, 2, 0 }, //inside small house by mage shop
		{ 3247, 3402, 2, 0 }, //west by mage shop
		{ 3242, 3385, 1, 0 }, //small house south of mage shop
		{ 3255, 3380, 0, 0 }, //chaos church
		{ 3234, 3382, 0, 0 }, //small house, south wall
		{ 3225, 3385, 1, 0 }, //house by south gate
		{ 3216, 3404, 2, 0 }, //south bar
		{ 3227, 3401, 2, 0 }, //bar inside
		{ 3205, 3397, 1, 0 }, //sword shop
		{ 3202, 3393, 2, 0 }, //south of sword shop
		{ 3190, 3382, 0, 0 }, //south west building
		{ 3182, 3390, 1, 0 }, //south west building
		{ 3182, 3393, 1, 0 }, //south west building
		{ 3200, 3398, 2, 0 }, //west of sword shop
		{ 3193, 3406, 2, 0 }, //pot shop
		{ 3189, 3407, 3, 0 }, //west of pot shop
		{ 3189, 3413, 3, 0 }, //west of pot shop, south of anvil
		{ 3174, 3411, 1, 0 }, //west wall
		{ 3174, 3407, 1, 0 }, //west wall
		{ 3185, 3432, 2, 0 }, //south of west bank
		{ 3180, 3441, 1, 0 }, //inside west bank
		{ 3200, 3435, 3, 0 }, //west of staff store
		{ 3209, 3459, 0, 0 }, //inside castle walls
		{ 3215, 3459, 0, 0 }, //inside castle walls
		{ 3203, 3473, 1, 0 }, //castle
		{ 3201, 3479, 1, 0 }, //castle
		{ 3202, 3482, 0, 0 }, //castle
		{ 3212, 3489, 2, 0 }, //castle
		{ 3206, 3494, 3, 0 }, //castle
		{ 3233, 3495, 3, 0 }, //castle
		{ 3216, 3502, 2, 0 }, //castle
		{ 3203, 3501, 0, 0 }, //castle
		{ 3200, 3480, 3, 0 }, //castle
		{ 3200, 3460, 3, 0 }, //castle
		{ 3220, 3479, 0, 0 }, //castle
		{ 3225, 3472, 1, 0 }, //castle
		{ 3217, 3468, 2, 0 }, //castle
		{ 3222, 3463, 3, 0 }, //castle
		{ 3239, 3445, 2, 0 }, //outside castle wall
		{ 3258, 3450, 3, 0 }, //north of training room
		{ 3257, 3438, 0, 0 }, //north of training room
		{ 3250, 3422, 1, 0 }, //east bank
		{ 3272, 3421, 3, 0 }, //east wall
		{ 3252, 3407, 1, 0 }, //north of rune shop
		{ 3251, 3406, 3, 0 }, //north of rune shop
		{ 3238, 3416, 2, 0 }, //castle
		{ 3179, 3403, 0, 0 }, //east wall
		{ 3193, 3415, 1, 0 }, //center
		{ 3199, 3423, 3, 0 }, //by clothing shop
		{ 3191, 3445, 1, 0 }, //west bank north west corner
		{ 3231, 3443, 0, 0 }, //north of armor shop
		{ 3238, 3417, 0, 0 }, //by east bank
		{ 3235, 3408, 3, 0 }, //by jails
		{ 3237, 3401, 2, 0 }, //by jails too
		{ 3241, 3384, 3, 0 }, //south wall
		{ 3207, 3382, 0, 0 }, //south wall
		{ 3203, 3464, 1, 0 }, //castle fount
		{ 3217, 3480, 3, 0 }, //castle
		{ 3219, 3482, 1, 0 }, //castle
		{ 3228, 3470, 1, 0 }, //castle
		{ 3235, 3480, 1, 0 }, //castle
		{ 3251, 3487, 3, 0 }, //north church
		{ 3250, 3500, 2, 0 }, //north wall
		{ 3237, 3500, 2, 0 }, //north wall
		{ 3198, 3414, 1, 0 } //west side of clothing shop
	};
	
	//0 north, 1 east, 2 south, 3 west
	private static int[][] chestsLoadFal = {
		{ 2961, 3393, 2, 0 },
		{ 2973, 3387, 0, 0 },
		{ 2974, 3374, 2, 0 },
		{ 2957, 3388, 2, 0 },
		{ 2948, 3388, 2, 0 },
		{ 2946, 3383, 1, 0 },
		{ 2937, 3383, 1, 0 },
		{ 2942, 3373, 3, 0 },
		{ 2949, 3361, 1, 0 },
		{ 2957, 3366, 0, 0 },
		{ 2937, 3346, 1, 0 },
		{ 2946, 3334, 0, 0 },
		{ 2965, 3340, 1, 0 },
		{ 2965, 3330, 0, 0 },
		{ 2973, 3337, 0, 0 },
		{ 2979, 3346, 2, 0 },
		{ 2967, 3353, 2, 0 },
		{ 2990, 3338, 3, 0 },
		{ 2990, 3336, 3, 0 },
		{ 2977, 3328, 0, 0 },
		{ 2969, 3328, 0, 0 },
		{ 2959, 3337, 0, 0 },
		{ 2960, 3351, 1, 0 },
		{ 2981, 3362, 0, 0 },
		{ 2992, 3362, 2, 0 },
		{ 3008, 3368, 2, 0 },
		{ 2989, 3378, 1, 0 },
		{ 2984, 3390, 2, 0 },
		{ 2996, 3389, 2, 0 },
		{ 3003, 3394, 2, 0 },
		{ 3006, 3394, 2, 0 },
		{ 3018, 3388, 2, 0 },
		{ 3031, 3382, 3, 0 },
		{ 3024, 3372, 3, 0 },
		{ 3011, 3369, 0, 0 },
		{ 3009, 3356, 1, 0 },
		{ 3010, 3358, 2, 0 },
		{ 3015, 3358, 2, 0 },
		{ 3018, 3356, 3, 0 },
		{ 3006, 3347, 1, 0 },
		{ 3015, 3349, 3, 0 },
		{ 3021, 3350, 0, 0 },
		{ 3029, 3352, 0, 0 },
		{ 3035, 3361, 2, 0 },
		{ 3031, 3382, 3, 0 },
		{ 3041, 3353, 1, 0 },
		{ 3040, 3343, 3, 0 },
		{ 3049, 3343, 3, 0 },
		{ 3059, 3353, 3, 0 },
		{ 3059, 3341, 3, 0 },
		{ 3059, 3333, 3, 0 },
		{ 3048, 3329, 0, 0 },
		{ 3037, 3329, 3, 0 },
		{ 3044, 3342, 2, 0 },
		{ 3036, 3341, 2, 0 },
		{ 3029, 3340, 1, 0 },
		{ 3014, 3337, 1, 0 },
		{ 3017, 3342, 2, 0 },
		{ 3011, 3342, 2, 0 },
		{ 3013, 3334, 2, 0 },
		{ 3019, 3335, 2, 0 },
		{ 3013, 3331, 2, 0 },
		{ 3032, 3376, 1, 0 },
		{ 3032, 3382, 1, 0 },
		{ 3027, 3389, 2, 0 },
		{ 3051, 3389, 2, 0 },
		{ 3055, 3389, 2, 0 },
		{ 3059, 3389, 2, 0 },
		{ 3053, 3377, 2, 0 },
		{ 3041, 3385, 2, 0 },
		{ 3038, 3381, 1, 0 },
		{ 3040, 3375, 0, 0 },
		{ 3052, 3378, 0, 0 },
		{ 3002, 3324, 0, 0 },
		{ 3009, 3322, 2, 0 },
		{ 3018, 3326, 2, 0 },
		{ 2992, 3317, 0, 0 },
		{ 2976, 3320, 2, 0 },
		{ 2974, 3310, 0, 0 },
		{ 2968, 3314, 3, 0 },
		{ 2955, 3312, 0, 0 },
		{ 2952, 3312, 0, 0 },
		{ 2937, 3324, 1, 0 },
		{ 2937, 3327, 1, 0 }
	};
	
	public void process() {
		for (Object o : objects) {
			if (o.tick > 0)
				o.tick--;
			else {
				updateObject(o);
				toRemove.add(o);
			}
		}
		for (Object o : toRemove) {
			if (isObelisk(o.newId)) {
				int index = getObeliskIndex(o.newId);
				if (activated[index]) {
					activated[index] = false;
					teleportObelisk(index);
				}
			}
			objects.remove(o);
		}
		toRemove.clear();
	}

  public void removeObject(int x, int y) {
        for (int j = 0; j < Server.playerHandler.players.length; j++) {
            if (Server.playerHandler.players[j] != null) {
                Client c = (Client)Server.playerHandler.players[j];
                c.getPA().object(-1, x, y, 0, 10);    			
                                             //c.getPA().object(158, 3097, 3493, 0, 10);
            }    
        }    
    }
 	public void Deletewalls(Client c) {
		/*c.getPA().checkObjectSpawn(-1,3095, 3493, -1, 0);
	    c.getPA().checkObjectSpawn(-1,3095, 3492, -1, 0);
		c.getPA().checkObjectSpawn(-1,3095, 3490, -1, 0);
		c.getPA().checkObjectSpawn(-1,3095, 3488, -1, 0);
		c.getPA().checkObjectSpawn(-1,3097, 3493, -1, 0);
		c.getPA().checkObjectSpawn(-1,3096, 3493, -1, 0);
		c.getPA().checkObjectSpawn(-1,3096, 3490, -1, 0);
		c.getPA().checkObjectSpawn(-1,3096, 3492, -1, 0);*/
	}
	public void updateObject(Object o) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				c.getPA().object(o.newId, o.objectX, o.objectY, o.face, o.type);
			}
		}
	}

	public void placeObject(Object o) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60)
					c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
			}
		}
	}

	public Object getObject(int x, int y, int height) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height)
				return o;
		}
		return null;
	}
	
	/**
	* Hunger Game Objects
	*/
	
	/* Picks the chests first */
	public static void pickChests(int amount, int city) {
		chestsToSpawn = amount;
		int randomArray = 0;
		
		switch (city)
		{
			case 0:
				for (int count = 0; count < chestsToSpawn; count = count) {
					randomArray = (int)(Math.random() * chestsLoad.length);
					if (chestsLoad[randomArray][3] == 0) {
						chestsLoad[randomArray][3] = 1;
						count++;
						//System.out.println("Count is at "+count+", chest "+randomArray+" has been picked for spawning.");
					}
				}
				System.out.println(chestsToSpawn + " chests have been picked for spawning at Varrock.");
			break;
			
			case 1:
				for (int count = 0; count < chestsToSpawn; count = count) {
					randomArray = (int)(Math.random() * chestsLoadFal.length);
					if (chestsLoadFal[randomArray][3] == 0) {
						chestsLoadFal[randomArray][3] = 1;
						count++;
						//System.out.println("Count is at "+count+", chest "+randomArray+" has been picked for spawning.");
					}
				}
				System.out.println(chestsToSpawn + " chests have been picked for spawning at Falador.");
			break;
		}
	}
	
	public static void placeFlower(int flower, int X, int Y) {
		/* Will place a portal down x.x */
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				c.getPA().checkObjectSpawn(flower, X, Y, 0, 10);  			
	        }    
	    }    
	}
		
	public static void removeFlower(int X, int Y) {
		/* Will place a portal down x.x */
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				c.getPA().checkObjectSpawn(-1, X, Y, 0, 10);  			
			}    
	    }    
	}
	
	public static void placePortal(int X, int Y) {
		/* Will place a portal down x.x */
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				c.getPA().checkObjectSpawn(7272, X, Y, 0, 10);  			
	        }    
	    }    
	}
		
	public static void removePortal(int X, int Y) {
		/* Will place a portal down x.x */
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				c.getPA().checkObjectSpawn(-1, X, Y, 0, 10);  			
			}    
	    }    
	}
	
	/* Spawn the randomly picked chests */
	public static void randomChests(Client c) {
		/* Will randomly spawn chests from the array */
		if(c.randomMap == 0) {
			/*for (int i = 0; i < chestsLoad.length; i++) {
				if (chestsLoad[i][3] == 1) {
					c.getPA().checkObjectSpawn(376, chestsLoad[i][0], chestsLoad[i][1], chestsLoad[i][2], 10);
				}
			}
			
			/* Spawn in chests around center (always 100%) */
			/*c.getPA().checkObjectSpawn(376, 3212, 3426, 2, 10);
			c.getPA().checkObjectSpawn(376, 3214, 3426, 2, 10);
			c.getPA().checkObjectSpawn(376, 3210, 3428, 3, 10);
			c.getPA().checkObjectSpawn(376, 3210, 3430, 3, 10);
			c.getPA().checkObjectSpawn(376, 3212, 3432, 0, 10);
			c.getPA().checkObjectSpawn(376, 3214, 3432, 0, 10);
			c.getPA().checkObjectSpawn(376, 3216, 3430, 1, 10);
			c.getPA().checkObjectSpawn(376, 3216, 3428, 1, 10);
			/* other stuff */
			c.getPA().checkObjectSpawn(4277, 3227, 3410, 0, 10);//Fish stands
			c.getPA().checkObjectSpawn(4277, 3191, 3422, 1, 10);
			c.getPA().checkObjectSpawn(4277, 3238, 3495, 1, 10);
					
			c.getPA().checkObjectSpawn(6552, 3229, 3423, 3, 10);//Anicents
			c.getPA().checkObjectSpawn(4008, 3191, 3436, 1, 10);//lunar
		} else if (c.randomMap == 1) {
		
			/*for (int i = 0; i < chestsLoadFal.length; i++) {
				if (chestsLoadFal[i][3] == 1) {
					c.getPA().checkObjectSpawn(376, chestsLoadFal[i][0], chestsLoadFal[i][1], chestsLoadFal[i][2], 10);
				}
			}
			
			/* Spawn in chests around center (always 100%) */
			/*c.getPA().checkObjectSpawn(376, 2964, 3379, 2, 10);
			c.getPA().checkObjectSpawn(376, 2966, 3379, 2, 10);
			c.getPA().checkObjectSpawn(376, 2967, 3380, 1, 10);
			c.getPA().checkObjectSpawn(376, 2967, 3382, 1, 10);
			c.getPA().checkObjectSpawn(376, 2966, 3383, 0, 10);
			c.getPA().checkObjectSpawn(376, 2964, 3383, 0, 10);
			c.getPA().checkObjectSpawn(376, 2963, 3382, 3, 10);
			c.getPA().checkObjectSpawn(376, 2963, 3380, 3, 10);
			/* other stuff */
			c.getPA().checkObjectSpawn(4277, 2958, 3376, 1, 10);//Fish stands
			c.getPA().checkObjectSpawn(4277, 3001, 3374, 3, 10);
			c.getPA().checkObjectSpawn(4277, 3029, 3350, 2, 10);
					
			c.getPA().checkObjectSpawn(6552, 2975, 3337, 0, 10);//Anicents
			c.getPA().checkObjectSpawn(4008, 2985, 3368, 1, 10);//lunar
		} else if (c.randomMap == 2) {
		
		}
	}
	
	/* Delete when game ends */
	public static void deleteRandomChests(Client c) {
		
		if (c.randomMap == 0) {
			for (int i = 0; i < chestsLoad.length; i++) {
				if (chestsLoad[i][3] == 1) {
					c.getPA().object(-1, chestsLoad[i][0], chestsLoad[i][1], chestsLoad[i][2], 10);
					chestsLoad[i][3] = 0;
					//System.out.println("Chest number "+i+" has been deleted at "+chestsLoad[i][0]+", "+chestsLoad[i][1]+".");
				}
			}
			
			/* Delete chest around center */
			//System.out.println("Starting spawn chests deleted.");
			c.getPA().object(-1, 3212, 3426, 2, 10);
			c.getPA().object(-1, 3214, 3426, 2, 10);
			c.getPA().object(-1, 3210, 3428, 3, 10);
			c.getPA().object(-1, 3210, 3430, 3, 10);
			c.getPA().object(-1, 3212, 3432, 0, 10);
			c.getPA().object(-1, 3214, 3432, 0, 10);
			c.getPA().object(-1, 3216, 3430, 1, 10);
			c.getPA().object(-1, 3216, 3428, 1, 10);
			/* other stuff */
			c.getPA().object(-1, 3227, 3410, 0, 10);
			c.getPA().object(-1, 3191, 3422, 1, 10);
			c.getPA().object(-1, 3238, 3495, 1, 10);
			
			c.getPA().object(-1, 3229, 3423, 3, 10);//Anicents
			c.getPA().object(-1, 3191, 3436, 1, 10);//lunar
		} else if (c.randomMap == 1) {
			for (int i = 0; i < chestsLoadFal.length; i++) {
				if (chestsLoadFal[i][3] == 1) {
					c.getPA().object(-1, chestsLoadFal[i][0], chestsLoadFal[i][1], chestsLoadFal[i][2], 10);
					chestsLoadFal[i][3] = 0;
					//System.out.println("Chest number "+i+" has been deleted at "+chestsLoad[i][0]+", "+chestsLoad[i][1]+".");
				}
			}
			
			/* Spawn in chests around center (always 100%) */
			c.getPA().object(-1, 2964, 3379, 2, 10);
			c.getPA().object(-1, 2966, 3379, 2, 10);
			c.getPA().object(-1, 2967, 3380, 1, 10);
			c.getPA().object(-1, 2967, 3382, 1, 10);
			c.getPA().object(-1, 2966, 3383, 0, 10);
			c.getPA().object(-1, 2964, 3383, 0, 10);
			c.getPA().object(-1, 2963, 3382, 1, 10);
			c.getPA().object(-1, 2963, 3380, 1, 10);
			/* other stuff */
			c.getPA().object(-1, 2958, 3376, 1, 10);//Fish stands
			c.getPA().object(-1, 3001, 3374, 3, 10);
			c.getPA().object(-1, 3029, 3350, 2, 10);
					
			c.getPA().object(-1, 2975, 3337, 0, 10);//Anicents
			c.getPA().object(-1, 2985, 3368, 1, 10);//lunar
		}
	}
	
	/**
	* End of Hunger Game objects
	**/

	public void loadObjects(Client c) {
		if (c == null)
			return;
		for (Object o : objects) {
			if (loadForPlayer(o,c))
				c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
		}
		loadCustomSpawns(c);
		Deletewalls(c);
		if (c.distanceToPoint(2813, 3463) <= 60) {
			//c.getFarming().updateHerbPatch();
		}
	}
	
	public void loadCustomSpawns(Client c) {
	
		/*c.getPA().checkObjectSpawn(4406, 2559, 4959, 0, 10);//tele
		c.getPA().checkObjectSpawn(4483, 3373, 9806, 0, 10);
		c.getPA().checkObjectSpawn(4483, 3374, 9806, 0, 10);
		c.getPA().checkObjectSpawn(4483, 3375, 9806, 0, 10);
		c.getPA().checkObjectSpawn(4483, 3376, 9806, 0, 10);
		
		c.getPA().checkObjectSpawn(4389, 3039, 2912, 0, 10);//waiting room portal
		c.getPA().checkObjectSpawn(4389, 3117, 2890, 0, 10);//waiting room portal
		c.getPA().checkObjectSpawn(4407, 3117, 2982, 0, 10);//waiting room portal
		
		c.getPA().checkObjectSpawn(2783, 3311, 2762, 0, 10);
		c.getPA().checkObjectSpawn(2783, 3266, 2781, 0, 10);
		c.getPA().checkObjectSpawn(4277, 3266, 2789, 3, 10);
		
		c.getPA().checkObjectSpawn(6552, 3288, 2780, 0, 10);
		c.getPA().checkObjectSpawn(4008, 3300, 2780, 0, 10);
		
		c.getPA().checkObjectSpawn(2783, 3089, 3496, 1, 10);
		c.getPA().checkObjectSpawn(6552, 3092, 3506, 2, 10);
		c.getPA().checkObjectSpawn(4008, 3096, 3506, 2, 10);
		c.getPA().checkObjectSpawn(409, 3095, 3500, 0, 10);*/
	}

	public final int IN_USE_ID = 14825;
	public boolean isObelisk(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return true;
		}
		return false;
	}
	public int[] obeliskIds = {14829,14830,14827,14828,14826,14831};
	public int[][] obeliskCoords = {{3154,3618},{3225,3665},{3033,3730},{3104,3792},{2978,3864},{3305,3914}};
	public boolean[] activated = {false,false,false,false,false,false};

	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
			}
		}
	}

	public int getObeliskIndex(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return j;
		}
		return -1;
	}

	public void teleportObelisk(int port) {
		int random = Misc.random(5);
		while (random == port) {
			random = Misc.random(5);
		}
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if (c.goodDistance(c.getX(), c.getY(), obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2, 1)) {
					c.getPA().startTeleport2(obeliskCoords[random][0] + xOffset, obeliskCoords[random][1] + yOffset, 0);
				}
			}
		}
	}

	public boolean loadForPlayer(Object o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60 && c.heightLevel == o.height;
	}

	public void addObject(Object o) {
		if (getObject(o.objectX, o.objectY, o.height) == null) {
			objects.add(o);
			placeObject(o);
		}
	}




}