package com.systems.client.main;

import com.systems.client.network.NetworkHandler;

public class Main 
{

	public static boolean RUNNING = false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		RUNNING = true;
		NetworkHandler netHandler = new NetworkHandler();
	}

}
