package com.systems.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class NetworkHandler
{

	private static String HOSTNAME = "localhost";
	private static int PORT = 4556;
	
	private NetworkListener listener;
	/*
	 * Static singleton instance of the networkHandler
	 */
	private static NetworkHandler INSTANCE;
	
	public NetworkHandler() throws IOException
	{
		INSTANCE = this;
		
		
		ServerSocket socket = new ServerSocket(PORT);
		try
		{
			// TODO : This should be changed to some kind of flag to properly detect when the server is stopping
			// Or if we want the server to stop
			while(true)
			{
				this.listener = new NetworkListener(socket);
			}
		}
		finally
		{
			socket.close();
		}
	}
	
	public static NetworkHandler getNetwork()
	{
		if(INSTANCE == null)
		{
			try
			{
				INSTANCE = new NetworkHandler();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return INSTANCE;
	}
	
	public class NetworkListener extends Thread
	{
		
		private String hostname;
		private int port;
		
		private ServerSocket socket;
		private BufferedReader in;
		private PrintWriter out;
		
		
		public NetworkListener(ServerSocket socket)
		{
			this.socket = socket;
			
			this.hostname = HOSTNAME;
			this.port = PORT;
		}
		
		@Override
		public void run()
		{
		}
	}
}
