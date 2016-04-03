package com.systems.server.main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.systems.server.gui.Gui;
import com.systems.server.network.NetworkHandler;
import com.systems.server.sql.SQLHandler;

public class Main 
{

	public static boolean RUNNING = false;
	public static boolean GUI = true;
	
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException 
	{
		RUNNING = true;
		System.out.println("Server running");
		try 
		{
			SQLHandler sqlHandler = new SQLHandler(System.getProperty("user.dir") + File.separator + "testold.db");
			NetworkHandler networkHandler = new NetworkHandler(sqlHandler);
			networkHandler.start();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("SERVER STARTED");
		if(args.length > 0)
		{
			if(args[0].equalsIgnoreCase("nogui"))
			{
				GUI = false;
			}
		}
		if(GUI == true)
		{
			Gui gui = new Gui();
			gui.init();
		}

	}
	

}
