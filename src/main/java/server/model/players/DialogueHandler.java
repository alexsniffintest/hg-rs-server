package server.model.players;

import server.Server;

public class DialogueHandler {

	public Client c;
	
	public DialogueHandler(Client client) {
		this.c = client;
	}
	
	/**
	 * Handles all talking
	 * @param dialogue The dialogue you want to use
	 * @param npcId The npc id that the chat will focus on during the chat
	 */
	public void sendDialogues(int dialogue, int npcId) {
		c.talkingNpc = npcId;
		switch(dialogue) {
		
			case 9999:
				sendOption2("Pick", "Leave");
				c.dialogueAction = 1000;
				c.dialogueId = 999;
				c.teleAction = -1;
            break;
			
			case 0:
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
			break;
			
			case 1:
				sendNpcChat1("Hello there "+c.playerName+"! Welcome to the Hunger Games!", c.talkingNpc, "Archaeological Expert");
				c.tutorial = true;
				c.nextChat = 2;
			break;
			
			case 2:
				sendNpcChat1("Is this your first time here?", c.talkingNpc, "Archaeological Expert");
				c.nextChat = 3;
			break;
			
			
			case 3:
				sendOption2("Yes, it is.",  "How many times must you keep asking me?!");
				c.dialogueAction = 1;
			break;
			
			/* No option */
			case 4:
				sendPlayerChat1("How many times must you keep asking me?!");
				c.nextChat = 5;
				c.firstLogin = 1;
				c.spectate = false;
				c.tutorial = false;
				c.setSidebarInterface(1, 3917);
				c.setSidebarInterface(2, 16000);
				c.setSidebarInterface(3, 3213);
				c.setSidebarInterface(4, 1644);
				c.setSidebarInterface(5, 5608);
				c.setSidebarInterface(6, 1151);
				c.setSidebarInterface(7, 18128);
				c.setSidebarInterface(8, 5065);
				c.setSidebarInterface(9, 5715);
				c.setSidebarInterface(10, 2449);
				c.setSidebarInterface(11, 904);
				c.setSidebarInterface(12, 147);
				c.setSidebarInterface(13, 6299);
				c.setSidebarInterface(14, 15001);
				c.setSidebarInterface(15, 15051);
				c.setSidebarInterface(16, 17000);
				c.setSidebarInterface(0, 2423);
			break;
			
			case 5:
				sendNpcChat1("I'm sorry!", c.talkingNpc, "Archaeological Expert");
				c.nextChat = 0;
			break;
			
			/* Yes option */
			case 6:
				sendPlayerChat1("Yes, it is.");
				c.nextChat = 27;
			break;
			
			case 18:
				sendStatement("This kit costs 100,000 experience, would you like to unlock it?");
				c.nextChat = 20;
				c.kitOption = 2;
			break;
			
			case 19:
				sendStatement("This kit costs 150,000 experience, would you like to unlock it?");
				c.nextChat = 20;
				c.kitOption = 3;
			break;
			
			case 20:
				sendOption2("Yes!",  "No thanks.");
				c.dialogueAction = c.kitOption;
				c.kitOption = 0;
			break;
			
			case 21:
				sendNpcChat3("Hello there adventurer, I can use my magic", "to convert any of your experience that you've", "earned to coins. How much would you like to convert?", c.talkingNpc, "Merlin");
				c.convert = true;
				c.nextChat = 22;
			break;
			
			case 22:
				sendOption5("5k experience : 5k coins", "15k experience : 17k coins", "50k experience : 60k coins", "100k experience : 125k coins", "No thanks.");
				c.nextChat = 0;
			break;
			
			case 23:
				sendNpcChat2("I have some items you'd possibly like,", "go ahead and have a look.", c.talkingNpc, "Old Crone");
				c.shopOne = true;
				c.nextChat = 24;
			break;
			
			case 24:
				sendOption5("Rare Item Shop", "Female Costume Shop", "Male Costume Shop", "Misc Rare Costumes", "Shoo Old Crone, I don't want your items!");
				c.inShop = true;
				c.nextChat = 0;
			break;
			
			case 25:
				sendStatement("This kit costs 500,000 experience, would you like to unlock it?");
				c.nextChat = 20;
				c.kitOption = 4;
			break;
			
			/** Login Tutorial **/
			case 26:
				sendStatement("You hear a faint voice in the background, gradually getting louder..");
				c.nextChat = 1;
			break;
			
			case 27:
				sendNpcChat3("It is? Then I welcome you to the world", "of the Hunger Games! Our records show you'll be in the", "upcoming tournments. So it's best, I show you around.", c.talkingNpc, "Archaeological Expert");
				c.nextChat = 28;
				c.spectateEx = 1;
			break;
			
			case 28:
				sendNpcChat4("The Hunger Games is a survival based mini-game", "with many other players like you, the goal is to be", "the last a live. You can only use the surrounding", "objects that you find to win.", c.talkingNpc, "Archaeological Expert");
				c.spectate = true;
				c.getPA().movePlayer2(3213, 3429, 0);
				c.nextChat = 29;
			break;
			
			case 29:
				sendNpcChat3("Furthermore, only one person may leave with their life;", "when you enter, a timer will count down.", "When this timer hits bottom, anything can happen!", c.talkingNpc, "Archaeological Expert");
				c.nextChat = 30;
			break;
			
			case 30:
				sendNpcChat3("You can find weapons, armor and food", "within the chests. Use these to", "win at whatever cost you see fit.", c.talkingNpc, "Archaeological Expert");
				c.nextChat = 31;
				c.getPA().walkTo(0,-4);
			break;
			
			case 31:
				sendNpcChat3("You'll also be given the opportunity to pick a kit.", "These kits will grant you special powers", "and ablities within the arena.", c.talkingNpc, "Archaeological Expert");
				c.nextChat = 32;
			break;
			
			case 32:
				sendNpcChat4("To pick your kit, you can click on the kit under", "the quests tab, while inside the waiting lobby.", "If you'd like more details you can go to", "our website which will have a list of kits you can use.", c.talkingNpc, "Archaeological Expert");
				c.setSidebarInterface(2, 16000);
				c.nextChat = 33;
			break;
			
			case 33:
				sendNpcChat4("If you earn lots of experience you can also", "buy special perks from Doric at the home area.", "These perks are permanent passive ablities that", "you'll always have.", c.talkingNpc, "Archaeological Expert");
				c.getPA().movePlayer2(3372, 9629, 0);
				c.nextChat = 34;
			break;
			
			case 34:
				sendNpcChat4("Players inside the waiting lobby can also", "spectate and watch the on-going match.", "Your name will be kept anonymous,", "and you can communicate with the players inside.", c.talkingNpc, "Archaeological Expert");
				c.getPA().movePlayer2(2965, 3381, 0);
				c.nextChat = 135;
			break;
			
			case 135:
				sendNpcChat4("Additionally, if you do grow bored of the Games", "then you might be interested in pking!", "We have a full pk system setup for your needs at edge.", "Talk to the Zamorak mage at home.", c.talkingNpc, "Archaeological Expert");
				c.getPA().movePlayer2(3087, 3499, 0);
				c.nextChat = 35;
			break;
			
			case 35:
				sendNpcChat3("That's about all you'll need to know,", "the rest is up to you to learn.", "Are you ready to risk your life?", c.talkingNpc, "Archaeological Expert");
				c.nextChat = 36;
				c.spectateEx = 0;
			break;
			
			case 36:
				sendPlayerChat1("Hell yeah!");
				c.nextChat = 37;
			break;
			
			case 37:
				sendNpcChat3("Good!", "You can get started by entering one of", "the portals around here!", c.talkingNpc, "Archaeological Expert");
				c.getPA().movePlayer2(3363, 9640, 0);
				c.nextChat = 38;
				c.firstLogin = 1;
				c.spectate = false;
				c.tutorial = false;
				c.setSidebarInterface(1, 3917);
				c.setSidebarInterface(2, 16000);
				c.setSidebarInterface(3, 3213);
				c.setSidebarInterface(4, 1644);
				c.setSidebarInterface(5, 5608);
				c.setSidebarInterface(6, 1151);
				c.setSidebarInterface(7, 18128);
				c.setSidebarInterface(8, 5065);
				c.setSidebarInterface(9, 5715);
				c.setSidebarInterface(10, 2449);
				c.setSidebarInterface(11, 904);
				c.setSidebarInterface(12, 147);
				c.setSidebarInterface(13, 6299);
				c.setSidebarInterface(14, 15001);
				c.setSidebarInterface(15, 15051);
				c.setSidebarInterface(16, 17000);
				c.setSidebarInterface(0, 2423);
			break;
			
			case 38:
				sendNpcChat1("Good luck "+c.playerName+"!", c.talkingNpc, "Archaeological Expert");
				c.nextChat = 0;
			break;
			
			case 39:
				if (c.totalHungerGameExp < 10000) {
					sendNpcChat2("Get away fool, you're of no interest to me!", "Come back when you have 10k experience or more.", c.talkingNpc, "Mourner");
					c.nextChat = 0;
				} else {
					sendStatement("Warning, gambling may result in lose of exp. You will not be refunded.");
					c.nextChat = 440;
				}
			break;
			
			case 440:
				sendNpcChat2("Ahh, hello there sir. Would you be interested in", "betting experience against another player?", c.talkingNpc, "Mourner");
				c.nextChat = 40;
			break;
			
			case 40:
				sendOption3("Sure, sounds like fun.(Tutorial)",  "No thanks.", "(Skip to input)");
				c.dialogueAction = 5;
			break;
			
			case 41:
				sendPlayerChat1("Sure, sounds like fun. I'll play.");
				c.nextChat = 43;
			break;
			
			case 42:
				sendPlayerChat1("No thanks, go find someone else.");
				c.nextChat = 0;
			break;
			
			case 43:
				sendNpcChat4("Good, very good, well let me teach you the", "basic rules then. You bet a certain amount of", "experience and I'll advertise your bet", "to other players. Then you choose a number through 3.", c.talkingNpc, "Mourner");
				c.nextChat = 44;
			break;
			
			case 44:
				sendNpcChat4("The other player chooses a number too.", "After both players have decided, then we roll", "the dice. If you win, then you get the pot. If he wins", "then he gets the pot. If you both lose, then we reroll.", c.talkingNpc, "Mourner");
				c.nextChat = 45;
			break;
			
			case 45:
				sendNpcChat4("If we reroll four times, and you both lost", "each of those. then I keep the pot.", "The experience I earn will be held in a special", "lottery that an admin will hold.", c.talkingNpc, "Mourner");
				c.nextChat = 46;
			break;
			
			case 46:
				sendNpcChat3("You can view the current lottery by doing ::lottery", "That's all there is to it. Now then are you", "still interested in betting your experience?", c.talkingNpc, "Mourner");
				c.nextChat = 47;
			break;
			
			case 47:
				sendOption2("Hell yes!",  "Nooo, not worth the risk.");
				c.dialogueAction = 6;
			break;
			
			case 48:
				sendPlayerChat1("Hell yes! Lets do this!");
				if (c.gambleOn) {
					c.nextChat = 60;
				} else {
					c.nextChat = 50;
				}
				c.tutorial = true;
			break;
			
			case 49:
				sendPlayerChat1("Nooo, not my precious experience.");
				c.nextChat = 0;
			break;
			
			case 50:
				sendNpcChat3("Great! Please input a value of greater than 10k", "and less than 10m. If no one bets within", "3 mins then I'll refund your bet.", c.talkingNpc, "Mourner");
				c.nextChat = 51;
			break;
			
			case 60:
				//Has someone else already started the gamble, if yes, continue?
				if (c.gambleOn) {
					//Is the player null?
					if (c.playerName.equals(null)) {
						c.sendMessage("error_3 - Tell Peeta this");
						return;
					}
					//Somehow is the first gambler trying to reenter?
					if (c.playerName.equals(c.gamblerOne))	{
						c.getDH().sendDialogues(0, 0);
						c.sendMessage("error_4 - Tell Peeta this");
						return;
					}
					//Has someone else already bet?
					if (c.gambleFinal || c.startRollin) {
						sendNpcChat1("Looks like someone beat you to the bet.", c.talkingNpc, "Mourner");
						c.firstGamble = false;
						c.tutorial = false;
						c.nextChat = 0;
					//If not then check if they have enough exp to bet.
					} else if (c.totalHungerGameExp >= c.gamblePot) {
						sendPlayerChat1("Here's " + c.gamblePot + " experience!");
						c.totalHungerGameExp -= c.gamblePot;
						c.myBet = c.gamblePot;
						c.gamblePot += c.gamblePot;
						c.gambleFinal = true;
						c.reloadHGstuff();
						c.nextChat = 54;
					//not enough
					} else {
						sendNpcChat1("You don't have enough exp for that much, sorry.", c.talkingNpc, "Mourner");
						c.tutorial = false;
						c.nextChat = 0;
					}
				//The player either left the window open and the gamble ended already or the first player logged.
				} else {
					c.sendMessage("It seems the first person logged out or dced");
					c.tutorial = false;
					c.nextChat = 0;
				}
			break;
			
			case 51:
				c.tutorial = true;
				c.getPA().removeAllWindows();
				c.outStream.createFrame(27);
				c.gambleValue = true;
				c.firstGamble = true;
				c.sendMessage("If the input message interface closes, logout and in.");
			break;
			
			case 52:
				sendNpcChat3("Sorry but I only accept values of less", "than or equal of 10 million experience or", "greater than 10k!", c.talkingNpc, "Mourner");
				c.nextChat = 51;
			break;
			
			case 53:
				sendNpcChat2("Looks like someone else just placed a bet.", "Sorry about that "+c.playerName+".", c.talkingNpc, "Mourner");
				c.tutorial = false;
				c.nextChat = 0;
			break;
		
			case 54:
				sendNpcChat1("Thank you, ok now pick a number!", c.talkingNpc, "Mourner");
				if (!c.firstGamble) {
					c.gamblerTwo = c.playerName;
				}
				c.pickingNumber = true;
				c.nextChat = 55;
			break;
			
			case 55:
				sendOption3("One",  "Two", "Three");
				c.dialogueAction = 7;
			break;
			
			case 56:
				sendNpcChat2("Your bet has been entered. I will now", "start advertising your bet. Best of luck!", c.talkingNpc, "Mourner");
				c.gambleTimer = System.currentTimeMillis();
				c.tutorial = false;
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("[@blu@Mourner@bla@] : " + c.gamblerName + " just bet " + c.firstGamblerAmount + " experience at home!");
					}
				}
				c.nextChat = 0;
			break;
			
			case 57:
				sendNpcChat2("Your bet has been entered against", c.gamblerOne + ". I'll now roll the dice, goodluck!", c.talkingNpc, "Mourner");
				c.tutorial = false;
				c.nextChat = 0;
			break;
			
			case 58:
				sendNpcChat2("Looks like " + c.gamblerOne + " already bet with this number.", "Try picking another number.", c.talkingNpc, "Mourner");
				c.pickingNumber = true;
				c.nextChat = 55;
			break;
			
			case 59:
				sendNpcChat2("There's currently " + c.gamblePot + " experience in", "the pot. Would you like to equal it?", c.talkingNpc, "Mourner");
				c.nextChat = 47;
			break;
			
			case 61:
				sendNpcChat3("There's currently already a bet going on", "between " + c.gamblerOne + " and " + c.gamblerTwo + "!", "Please wait till they finish.", c.talkingNpc, "Mourner");
				c.nextChat = 0;
			break;
			
			case 62:
				sendNpcChat2("I'm still looking for another person.", "Please hang tight for a few minutes.", c.talkingNpc, "Mourner");
				c.nextChat = 0;
			break;
			
			case 63:
				sendNpcChat1("You don't have enough exp for that much, sorry.", c.talkingNpc, "Mourner");
				c.nextChat = 51;
			break;
			
			case 64:
				sendOption2("Perk Shop",  "Donator Perk Shop");
				c.dialogueAction = 8;
			break;
			
			case 65:
				sendStatement("This kit costs 500,000 experience, would you like to unlock it?");
				c.nextChat = 20;
				c.kitOption = 9;
			break;
			
			case 66:
				if (c.myKit.equalsIgnoreCase("SICKNESS") || c.myKit.equalsIgnoreCase("SAINT") || c.myKit.equalsIgnoreCase("KNIGHT") || c.myKit.equalsIgnoreCase("BLIZZARD") || c.myKit.equalsIgnoreCase("FLASH") || c.myKit.equalsIgnoreCase("TAINTED") || c.myKit.equalsIgnoreCase("NECROMORPHER")) {
					c.getItems().addItem(2347, 1);//hammer
					if (c.getItems().freeSlots() >= 2) {
						sendNpcChat2("It appears your kit needs special items.", "Please take these, and goodluck in your battles!", c.talkingNpc, "Guild Master");
						c.giveKitItems();
						c.nextChat = 0;
					} else {
						sendNpcChat2("Sorry but you need atleast two", "inventory spots free.", c.talkingNpc, "Guild Master");
						c.nextChat = 0;
					}
				} else {
					c.getItems().addItem(2347, 1);//hammer
					sendNpcChat3("Hello, welcome to Edgeville.", "Come talk to me when you need your special kit", "items and I'll get you set up.", c.talkingNpc, "Guild Master");
					c.nextChat = 0;
				}
			break;
			
			case 67:
				sendStatement("This area is under construction, but feel free to still have fun here!");
				c.nextChat = 0;
			break;
			
			case 68:
				sendStatement("Teleport to Edgeville pking area?");
				c.nextChat = 20;
				c.kitOption = 10;
			break;
			
			case 69:
				sendNpcChat1("Here, have some moderator cake.", c.talkingNpc, "Drill Sergeant");
				c.nextChat = 0;
			break;
			
			case 70:
				sendNpcChat1("Ho ho ho! Merry Christmas!", c.talkingNpc, "Santa");
				c.nextChat = 71;
				c.tutorial = true;
			break;
			
			case 71:
				sendPlayerChat1("Santa.. I-Is that you, is that really you!?");
				c.nextChat = 72;
			break;
			
			case 72:
				sendNpcChat1("Of course! Who else would I be? Ho ho ho!", c.talkingNpc, "Santa");
				c.nextChat = 73;
			break;
			
			case 73:
				sendPlayerChat1("Saaaaaaannnntaaaaaa!");
				c.nextChat = 74;
			break;
			
			case 74:
				sendPlayerChat3("You never visited me last year, what happened?", "I waited all night for you to show up,", "but you never did!");
				c.nextChat = 75;
			break;
			
			case 75:
				sendNpcChat3("I'm so sorry " + c.playerName + ",", "I've been having", "a lot of troubles recently.", c.talkingNpc, "Santa");
				c.nextChat = 76;
			break;
			
			case 76:
				sendNpcChat2("It's really a long story, but I'll try my", "best to explain it to you.", c.talkingNpc, "Santa");
				c.spectate = true;
				c.handleXmasEvent(c);
			break;
		
			case 77:
				sendNpcChat1("It all started on a snowy Christmas Eve last year...", c.talkingNpc, "Santa");
				c.nextChat = 0;
			break;
			
			case 78:
				sendNpcChat4("I was on one of my round trips going from", "Camelot over to the children of Varrock", "when all of a sudden, as I was passing the great White", "Wolf Mountain... That's when it appeared..", c.talkingNpc, "Santa");
				c.spectate = false;
				c.nextChat = 79;
			break;
			
			case 79:
				sendNpcChat3("It was like nothing I had ever seen.", "The teeth, the claws, the smell, it was a troll!", "Not any normal troll though..", c.talkingNpc, "Santa");
				Server.npcHandler.npcAction(1552, "", 1);
				c.nextChat = 80;
			break;
			
			case 80:
				sendNpcChat2("It was a troll of great magic, the troll who", "ruined Chirstmas!!", c.talkingNpc, "Santa");
				Server.npcHandler.npcAction(1552, "", 1);
				c.nextChat = 81;
			break;
			
			case 81:
				sendNpcChat2("It used a powerful ice spell, and before my", "own eyes. I was trapped helplessly for an entire year!", c.talkingNpc, "Santa");
				c.nextChat = 82;
			break;
			
			case 82:
				sendNpcChat3("After finally finding an escape from the evil", "monster, I've returned, but it's Christmas again!", "I have no gifts left and sadly, I don't think I can do anyting.", c.talkingNpc, "Santa");
				c.nextChat = 83;
			break;
			
			case 83:
				sendPlayerChat1("I could help you Santa!");
				c.nextChat = 84;
			break;
			
			case 84:
				sendNpcChat4("Well, I don't normally ask for help, but I've", "herd that some of the gifts from last year", "have been showing up around the world", "inside of some chests.", c.talkingNpc, "Santa");
				c.nextChat = 85;
			break;
			
			case 85:
				sendNpcChat4("If you do happen to find some, please bring them to", "me. That would be the best help to save Christmas!", "If you collect me 50, then I might be able to get you", "something special. How does that sound?", c.talkingNpc, "Santa");
				c.nextChat = 86;
			break;
			
			case 86:
				sendPlayerChat2("There will be a Christmas, don't worry Santa!", "I'll be back soon with every single lost gift!");
				c.startChristmas = 1;
				c.tutorial = false;
				c.nextChat = 87;
			break;
			
			case 87:
				sendNpcChat3("Thank you " + c.playerName + "!", "You're really the best! Ho ho ho!", "Please hurry!", c.talkingNpc, "Santa");
				c.nextChat = 0;
			break;
			
			case 88:
				sendNpcChat2("Hello again " + c.playerName + "!", "Have you collected any of the gifts yet?", c.talkingNpc, "Santa");
				c.tutorial = true;
				c.nextChat = 89;
			break;
			
			case 89:
				if (c.gifts == 0) {
					sendPlayerChat1("None yet, sorry Santa.");
					c.nextChat = 90;
				} else if (c.gifts >= 50) {
					sendPlayerChat1("I've collected all 50 that you need!");
					c.nextChat = 92;
				} else {
					sendPlayerChat1("I've collected " + c.gifts + " so far!");
					c.nextChat = 91;
				}
			break;
			
			case 90:
				sendNpcChat1("You'd better go get started!", c.talkingNpc, "Santa");
				c.tutorial = false;
				c.nextChat = 0;
			break;
			
			case 91:
				sendNpcChat2("Good job " + c.playerName + ", keep up the amazing work!", "Only " + (50 - c.gifts) + " left!", c.talkingNpc, "Santa");
				c.tutorial = false;
				c.nextChat = 0;
			break;
			
			case 92:
				sendNpcChat3("Amazing " + c.playerName + ", you've help saved Christmas!", "Ho ho ho! Please accept this small reward for your", "troubles in helping! Thank you so much!", c.talkingNpc, "Santa");
				if (c.getItems().freeSlots() >= 1 && c.gifts >= 50) {
					c.getItems().addItem(15337, 1);
					c.gifts -= 50;
					c.sendMessage("Your collected gifts is now at " + c.gifts + ".");
					Server.npcHandler.npcAction(1552, "Thank you so much " + c.playerName + "!", 2);
				} else {
					c.sendMessage("Not enough inventory space to receive the special gift!");
					c.nextChat = 0;
					return;
				}
				c.nextChat = 93;
			break;
			
			case 93:
				sendNpcChat3("If you'd like, you can continue to collect more gifts", "and I will reward you again.", "The more gifts we get, the more happy children!", c.talkingNpc, "Santa");
				c.tutorial = false;
				c.nextChat = 0;
			break;
			
			case 94:
				sendStatement("This kit costs 300k exp and 50 voter points, want to unlock it?");
				c.nextChat = 20;
				c.kitOption = 11;
			break;
			
			case 95:
				sendNpcChat3("Hello there, I have a shop setup", "for those that help the server by voting.", "The more votes we get, the more players!", c.talkingNpc, "Survival Expert");
				c.nextChat = 96;
			break;
			
			case 96:
				c.getShops().openShop(15);
				c.inShop = true;
			break;
			
			case 908:
				sendPlayerChat1("Hello there kitty!");
				c.nextChat = 909;	
			break;
			
			case 909:
				sendNpcChat1("Meeeooow.", npcId, "Kitty");
				c.nextChat = 0;	
			break;
			
			case 910:
				sendOption3("Pet", "Catch Rat", "Shoo Away");
				c.dialogueAction = 222;
				c.nextChat = 0;	
			break;
			
			case 911:
				sendStatement("You pet your cat.");
				Server.npcHandler.startAnimation(318, c.rememberNpcIndex);
				c.startAnimation(827);
				Server.npcHandler.npcs[c.rememberNpcIndex].forceChat("Meow!");
				if(Server.npcHandler.npcs[c.rememberNpcIndex].npcType >= 761 && Server.npcHandler.npcs[c.rememberNpcIndex].npcType <= 766 || Server.npcHandler.npcs[c.rememberNpcIndex].npcType == 3505) {
					c.ratsCaught++;
					if (c.ratsCaught >= 5) {
						Server.npcHandler.growCat(c.rememberNpcIndex);
					}
				}
				c.nextChat = 0;	
			break;
			
			case 912:
				Server.npcHandler.catchRat(c.rememberNpcIndex);
				c.getPA().removeAllWindows();
				c.nextChat = 0;	
			break;
			
			case 913:
				sendStatement("You shoo your cat away.");
				if(Server.npcHandler.npcs[c.rememberNpcIndex].npcType >= 761 && Server.npcHandler.npcs[c.rememberNpcIndex].npcType >= 766)
					c.ratsCaught = 0;
				Server.npcHandler.npcs[c.rememberNpcIndex].absX = 0;
				Server.npcHandler.npcs[c.rememberNpcIndex].absY = 0;
				Server.npcHandler.npcs[c.rememberNpcIndex] = null;
				c.summonId = 0;
				c.hasNpc = false;
				c.nextChat = 0;	
			break;
			
			case 999:
				sendStatement("Please reset your password before playing! ::changepassword");
				c.nextChat = 0;	
			break;
		}
	}



	/*
	 * Information Box
	 */
	
	public void sendStartInfo(String text, String text1, String text2, String text3, String title) {
		c.getPA().sendFrame126(title, 6180);
		c.getPA().sendFrame126(text, 6181);
		c.getPA().sendFrame126(text1, 6182);
		c.getPA().sendFrame126(text2, 6183);
		c.getPA().sendFrame126(text3, 6184);
		c.getPA().sendFrame164(6179);
	}
	
	/*
	 * Options
	 */
	
	public void sendOption(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2470);
	 	c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126("Click here to continue", 2473);
		c.getPA().sendFrame164(13758);
	}	
	
	public void sendOption2(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame164(2459);
	}
	
	public void sendOption3(String s, String s1, String s2) {
		c.getPA().sendFrame126("Select an Option", 2470);
		c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126(s2, 2473);
		c.getPA().sendFrame164(2469);
	}
	
	public void sendOption4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126("Select an Option", 2481);
		c.getPA().sendFrame126(s, 2482);
		c.getPA().sendFrame126(s1, 2483);
		c.getPA().sendFrame126(s2, 2484);
		c.getPA().sendFrame126(s3, 2485);
		c.getPA().sendFrame164(2480);
	}
	
	public void sendOption5(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126("Select an Option", 2493);
		c.getPA().sendFrame126(s, 2494);
		c.getPA().sendFrame126(s1, 2495);
		c.getPA().sendFrame126(s2, 2496);
		c.getPA().sendFrame126(s3, 2497);
		c.getPA().sendFrame126(s4, 2498);
		c.getPA().sendFrame164(2492);
	}

	/*
	 * Statements
	 */
	
	public void sendStatement(String s) { // 1 line click here to continue chat box interface
		c.getPA().sendFrame126(s, 357);
		c.getPA().sendFrame126("Click here to continue", 358);
		c.getPA().sendFrame164(356);
	}
	
	/*
	 * Npc Chatting
	 */
	
	public void sendNpcChat1(String s, int ChatNpc, String name) {
		c.getPA().sendFrame200(4883, 591);
		c.getPA().sendFrame126(name, 4884);
		c.getPA().sendFrame126(s, 4885);
		c.getPA().sendFrame75(ChatNpc, 4883);
		c.getPA().sendFrame164(4882);
	}
	
	public void sendNpcChat2(String s, String s1, int ChatNpc, String name) {
		c.getPA().sendFrame200(4888, 591);
		c.getPA().sendFrame126(name, 4889);
		c.getPA().sendFrame126(s, 4890);
		c.getPA().sendFrame126(s1, 4891);
		c.getPA().sendFrame75(ChatNpc, 4888);
		c.getPA().sendFrame164(4887);
	}
	
	public void sendNpcChat3(String s, String s1, String s2, int ChatNpc, String name) {
		c.getPA().sendFrame200(4894, 591);
		c.getPA().sendFrame126(name, 4895);
		c.getPA().sendFrame126(s, 4896);
		c.getPA().sendFrame126(s1, 4897);
		c.getPA().sendFrame126(s2, 4898);
		c.getPA().sendFrame75(ChatNpc, 4894);
		c.getPA().sendFrame164(4893);
	}
	
	public void sendNpcChat4(String s, String s1, String s2, String s3, int ChatNpc, String name) {
		c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame126(s1, 4904);
		c.getPA().sendFrame126(s2, 4905);
		c.getPA().sendFrame126(s3, 4906);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
	}
	
	/*
	 * Player Chating Back
	 */
	
	public void sendPlayerChat1(String s) {
		c.getPA().sendFrame200(969, 591);
		c.getPA().sendFrame126(c.playerName, 970);
		c.getPA().sendFrame126(s, 971);
		c.getPA().sendFrame185(969);
		c.getPA().sendFrame164(968);
	}
	
	public void sendPlayerChat2(String s, String s1) {
		c.getPA().sendFrame200(974, 591);
		c.getPA().sendFrame126(c.playerName, 975);
		c.getPA().sendFrame126(s, 976);
		c.getPA().sendFrame126(s1, 977);
		c.getPA().sendFrame185(974);
		c.getPA().sendFrame164(973);
	}
	
	public void sendPlayerChat3(String s, String s1, String s2) {
		c.getPA().sendFrame200(980, 591);
		c.getPA().sendFrame126(c.playerName, 981);
		c.getPA().sendFrame126(s, 982);
		c.getPA().sendFrame126(s1, 983);
		c.getPA().sendFrame126(s2, 984);
		c.getPA().sendFrame185(980);
		c.getPA().sendFrame164(979);
	}
	
	public void sendPlayerChat4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame200(987, 591);
		c.getPA().sendFrame126(c.playerName, 988);
		c.getPA().sendFrame126(s, 989);
		c.getPA().sendFrame126(s1, 990);
		c.getPA().sendFrame126(s2, 991);
		c.getPA().sendFrame126(s3, 992);
		c.getPA().sendFrame185(987);
		c.getPA().sendFrame164(986);
	}
}