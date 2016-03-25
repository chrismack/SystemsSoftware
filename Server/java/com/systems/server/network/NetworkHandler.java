package com.systems.server.network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class NetworkHandler extends Thread
{

	private static final int PORT = 4556;
	
	private NetworkListener listener;
	
	private HashMap<String, Socket> connectedUser = new HashMap<String, Socket>();
	
	
	/*
	 * Static singleton instance of the networkHandler
	 */
	private static NetworkHandler INSTANCE;
	
	public NetworkHandler() throws IOException
	{
		INSTANCE = this;
		
		// TODO : This should be changed to some kind of flag to properly detect when the server is stopping
		// Or if we want the server to stop
		
		
	}
	
	@Override
	public void run()
	{
		ServerSocket serverSocket = null;
		try
		{
			serverSocket = new ServerSocket(PORT);
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
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				serverSocket.close();
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		private DataInputStream din;
		
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
				
				byte[] bytes = new byte[8 * 1024];
				String dataType = "";

				int loopCount = 0;
				int count;
				String testMessage = new String();
				while ((count = socket.getInputStream().read(bytes)) > 0)
				{
					if(loopCount == 0)
					{
						for(int i = 0; i < bytes.length; i++)
						{
							if(bytes[i] == 58)
							{
								break;
							}
							dataType += (char)bytes[i];
						}
					}
					switch (dataType)
					{
					case "TEST": // Starts with 1
						System.out.println("THIS IS A TEST");
					case "TEST2": // Starts with 2
					case "REG":
						testMessage += new String(bytes, 0, count);
						if(count < 8 * 1024)
						{
							System.out.println(testMessage);
						}
					}
					
					loopCount++;
				}
				System.out.println(dataType);
				/*
				 * Implement some kind of packet handler / Handling 
				 * probably do it with switch (string)
				 */
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
