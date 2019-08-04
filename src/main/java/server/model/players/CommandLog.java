package server.model.players;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
* TradeLog class
* @author Aintaro
*/

public class CommandLog {

	private Client c;
	
	
	
	public CommandLog(Client Client) {
		this.c = Client;
	}
	
	/**
	* Will write what kind of item a player has received.
	* MONTH = 0 = January
	* DAY OF MONTH = 30 || 31
	*/
	public void commandLog(int X, int Y) {
	Calendar C = Calendar.getInstance();
		try {
				BufferedWriter bItem = new BufferedWriter(new FileWriter("./Commands/" + c.playerName + ".txt", true));
				try {			
					bItem.write("sendReplaceObject(-1, "+X+", "+Y+", 3, 10);");
					bItem.newLine();
					} finally {
						bItem.close();
					}
				} catch (IOException e) {
                    e.printStackTrace();
            }
	}
	

}