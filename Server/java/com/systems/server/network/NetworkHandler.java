package com.systems.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.systems.server.main.Main;

public class NetworkHandler
{

	private static final int PORT = 4556;
	
	private NetworkListener listener;
	
	/*
	 * Static singleton instance of the networkHandler
	 */
	private static NetworkHandler INSTANCE;
	
	public NetworkHandler() throws IOException
	{
		INSTANCE = this;
		
		// TODO : This should be changed to some kind of flag to properly detect when the server is stopping
		// Or if we want the server to stop
		
		ServerSocket serverSocket = new ServerSocket(PORT);
		
		while(true)
		{
			try
			{
				while(true)
				{
					/*this.listener =*/ new NetworkListener(serverSocket.accept()).start();
				}
			}
			finally
			{
				serverSocket.close();
			}
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
	
	private static class NetworkListener extends Thread
	{
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;
		
		
		public NetworkListener(Socket socket)
		{
			this.socket = socket;
		}
		
		@Override
		public void run()
		{
			try
			{
				System.out.println("Listener thread");
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				
				/*
				 * Implement some kind of packet handler / Handling 
				 * probably do it with switch (string)
				 */
				while(Main.RUNNING)
				{
					String packet = in.readLine();
					System.out.println(packet);
					
				}
			} 
			catch (IOException e) 
			{
                System.out.println(e);
            } 
			finally
            {
	            try 
	            {
	                socket.close();
	            } 
	            catch (IOException e) 
	            {
	            }
            }
		}
	}
}
