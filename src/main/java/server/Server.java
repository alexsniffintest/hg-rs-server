package server;


import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.runetoplist.VoteChecker;
import server.clip.region.ObjectDef;
import server.clip.region.Region;
import server.event.EventManager;
import server.model.minigames.*;
import server.model.npcs.NPCDrops;
import server.model.npcs.NPCHandler;
import server.model.objects.Doors;
import server.model.players.*;
import server.net.ConnectionHandler;
import server.net.ConnectionThrottleFilter;
import server.util.HostBlacklist;
import server.util.MadTurnipConnection;
import server.util.SimpleTimer;
import server.util.log.Logger;
import server.world.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;
/**
 * Server.java
 *
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan
 *
 */

public class Server {


	

	public static boolean sleeping;
	public static final int cycleRate;
	public static MadTurnipConnection md;
	public static boolean UpdateServer = false;
	public static long lastMassSave = System.currentTimeMillis();
	private static IoAcceptor acceptor;
	private static ConnectionHandler connectionHandler;
	private static ConnectionThrottleFilter throttleFilter;
	private static SimpleTimer engineTimer, debugTimer;
	private static long cycleTime, cycles, totalCycleTime, sleepTime;
	private static DecimalFormat debugPercentFormat;
	public static boolean shutdownServer = false;		
	public static boolean shutdownClientHandler;			
	public static int serverlistenerPort; 
	public static ItemHandler itemHandler = new ItemHandler();
	public static PlayerHandler playerHandler = new PlayerHandler();
    public static NPCHandler npcHandler = new NPCHandler();
	public static ShopHandler shopHandler = new ShopHandler();
	public static ObjectHandler objectHandler = new ObjectHandler();
	public static ObjectManager objectManager = new ObjectManager();
	public static HungerGames HungerGames = new HungerGames();
	public static HungerGamesFal HungerGamesFal = new HungerGamesFal();
	public static HungerGamesCan HungerGamesCan = new HungerGamesCan();
	public static HungerGamesMisc HungerGamesMisc = new HungerGamesMisc();
	public static Infection Infection = new Infection();
	public static CastleWars castleWars = new CastleWars();
	public static FightPits fightPits = new FightPits();
	public static NPCDrops npcDrops = new NPCDrops();
	public static ClanChatHandler clanChat = new ClanChatHandler();
	public static int days, hours, minutes, seconds;
	public static FightCaves fightCaves = new FightCaves();
	public static VoteChecker voteChecker = new VoteChecker("www.hg-rs.com", "hgrscom_vote", "hgrscom_vote", "$@V6eQDDi#Th");

	
	static {

		if(!Config.SERVER_DEBUG) {
			serverlistenerPort = 46900;
		} else {
			serverlistenerPort = 46900;
		}
		cycleRate = 600;
		shutdownServer = false;
		engineTimer = new SimpleTimer();
		debugTimer = new SimpleTimer();
		sleepTime = 0;
		debugPercentFormat = new DecimalFormat("0.0#%");
	}

	
	public static void main(java.lang.String args[]) throws NullPointerException, IOException {
	
		/**
		 * Starting Up Server
		 */
		
		 
		System.setOut(new Logger(System.out));
		System.setErr(new Logger(System.err));
		System.out.println("Launching RuneScape Hunger Games...");
		
		/**
		 * Donations
		 */

		//md = new MadTurnipConnection();
		//md.start();
		//System.out.println("[DONATION] Connected to MySQL Database!");
		
		/**
		  * Hiscores
		  */
		
		/*Highscores.process();
		if (Highscores.connected) {
			System.out.println("[HISCORE] Connected to MySQL Database!");
		} else {
			System.out.println("[HISCORE] Failed to connect to MySQL Database!");
		}*/
	
		/**
		 * Accepting Connections
		 */
		acceptor = new SocketAcceptor();
		connectionHandler = new ConnectionHandler();
		
		SocketAcceptorConfig sac = new SocketAcceptorConfig();
		sac.getSessionConfig().setTcpNoDelay(false);
		sac.setReuseAddress(true);
		sac.setBacklog(100);
		
		throttleFilter = new ConnectionThrottleFilter(Config.CONNECTION_DELAY);
		sac.getFilterChain().addFirst("throttleFilter", throttleFilter);
		acceptor.bind(new InetSocketAddress(serverlistenerPort), connectionHandler, sac);

		/**
		 * Initialise Handlers
		 */
		EventManager.initialize();
		Doors.getSingleton().load();
		//DoubleDoors.getSingleton().load();
		Connection.initialize();
		//MySQLManager.getSingleton();
		ObjectDef.loadConfig();
        Region.load();
		
		/**
		 * Server Stats
		 */
		serverStatistics.readServerStats();
		System.out.println("Server Statistics loaded.");
		
		/**
		 * Hosts Black List
		 */
		HostBlacklist.loadBlacklist();
		
		/**
		 * Server Successfully Loaded 
		 */
		System.out.println("Server listening on port 0.0.0.0:" + serverlistenerPort);
		
		/**
		 * Main Server Tick
		 */
		try {
			while (!Server.shutdownServer) {
				if (sleepTime >= 0)
					Thread.sleep(sleepTime);
				else
					Thread.sleep(600);
					
				engineTimer.reset();
				itemHandler.process();
				playerHandler.process();
				shopHandler.process();
				objectManager.process();
				npcHandler.process();
				HungerGames.process();
				HungerGamesFal.process();
				HungerGamesCan.process();
				HungerGamesMisc.process();
				//Infection.process();
				cycleTime = engineTimer.elapsed();
				sleepTime = cycleRate - cycleTime;
				totalCycleTime += cycleTime;
				cycles++;
				
				debug();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("A fatal exception has been thrown!");
			for(Player p : PlayerHandler.players) {
				if(p == null)
					continue;
						if (p.inTrade) {
                    ((Client)p).getTradeAndDuel().declineTrade();
                }
				PlayerSave.sa1veGame((Client)p);
				System.out.println("Saved game for " + p.playerName + ".");

			}
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			serverStatistics.serverStats();
			Client.printException = sw.toString();
			ExceptionCatcher.printException();
		}
		acceptor = null;
		connectionHandler = null;
		sac = null;
		System.exit(0);
	}


	public static boolean playerExecuted = false;
	private static void debug() {
		if (debugTimer.elapsed() > 120000 || playerExecuted) {
			long averageCycleTime = totalCycleTime / cycles;
			System.out.println("Average Cycle Time: " + averageCycleTime + "ms");
			double engineLoad = ((double) averageCycleTime / (double) cycleRate);
			System.out.println("Players online: " + PlayerHandler.playerCount+ ", engine load: "+ debugPercentFormat.format(engineLoad));
			serverStatistics.serverStats();
			System.out.println("Server statistics have been saved.");
			totalCycleTime = 0;
			cycles = 0;
			System.gc();
			System.runFinalization();
			debugTimer.reset();
			playerExecuted = false;
		}
	}
	
	public static long getSleepTimer() {
		return sleepTime;
	}
	
}