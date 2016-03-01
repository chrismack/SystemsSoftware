package com.systems.client.network;


import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NetworkHandler
{
	/*
	 * I am assuming this has to match up with the server hostname and port.
	 */
	private static String HOSTNAME = "localhost";
	private static int PORT = 4556;
	
	/*
	 * For multi-client handling
	 * 
	 *  private static int CLIENTID = 1;
	 */
	
	private static NetworkHandler INSTANCE;
	
	public NetworkHandler()
	{
		INSTANCE = this;

		ServerSocket socket = new ServerSocket(PORT);
		try 
		{
			while(true)
			{
				
			}
		}
	}
	
	
	public static NetworkHandler getNetworkHandler()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new NetworkHandler();
		}
		return INSTANCE;
	}
}
