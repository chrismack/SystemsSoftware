package com.systems.server.main;

import java.io.IOException;

import com.systems.server.network.NetworkHandler;

public class Main 
{

	public static boolean RUNNING = false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		RUNNING = true;
		System.out.println("Server running");
		try 
		{
			NetworkHandler networkHandler = new NetworkHandler();
			networkHandler.start();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("SERVER");
	}

}
