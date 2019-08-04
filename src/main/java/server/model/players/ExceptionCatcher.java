package server.model.players;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class ExceptionCatcher {
	
	private static Client c;
	private static Date date = new Date();
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static String fileName = "./Data/FatalExceptions.txt";
	
	public static void printException()
	{
		PrintWriter printException = null;
		try
		{       
			printException = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
		}
		catch (IOException e)
		{
			System.out.println("Problem writing " + fileName);
		}

		printException.println("/--------------- FATAL EXCEPTION " + dateFormat.format(date) + " ---------------/"); 
		printException.println(c.printException);
		printException.println("\n");

		printException.close();
	}
}