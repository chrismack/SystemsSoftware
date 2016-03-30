package com.systems.server.network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import com.systems.server.main.Utils;
import com.systems.server.network.processors.FriendProcessor;
import com.systems.server.network.processors.LoginProcessor;
import com.systems.server.network.processors.PostProcessor;
import com.systems.server.network.processors.RegistrationProcessor;
import com.systems.server.network.processors.SongProcessor;
import com.systems.server.sql.SQLHandler;

public class NetworkHandler extends Thread
{

	private static final int PORT = 4556;
	
	private NetworkListener listener;
	
	public HashMap<String, Socket> connectedUser = new HashMap<String, Socket>();
	
	private SQLHandler sqlHandler;
	/*
	 * Static singleton instance of the networkHandler
	 */
	private static NetworkHandler INSTANCE;
	
	public NetworkHandler(SQLHandler sqlHandler) throws IOException
	{
		INSTANCE = this;
		this.sqlHandler = sqlHandler;
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
				INSTANCE = new NetworkHandler(SQLHandler.getInstance());
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return INSTANCE;
	}
	
	
	public void sendBytes(byte[] b, Socket socket) throws IOException
	{
		socket.getOutputStream().write(b);
	}
	
	private class NetworkListener extends Thread
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
				byte[] fileBytes = {};
				String dataType = "";

				int loopCount = 0;
				int count;
				String message = new String();
				
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
					
					if(count >= 8 * 1024)
					{
						message += new String(bytes, 0, count);
					}
					else
					{
						message += new String(bytes, 0, count);
						INetworkMessage messageProcessor = null;
						
						switch (dataType)
						{
						case "REG":
							messageProcessor = new RegistrationProcessor(sqlHandler);
							break;
						case "LOGIN":
							messageProcessor = new LoginProcessor(sqlHandler);
							break;
						case "FRIEND":
							messageProcessor = new FriendProcessor(sqlHandler);
							break;
						case "POST":
							messageProcessor = new PostProcessor(sqlHandler);
							break;
						case "SONG":
							messageProcessor = new SongProcessor(sqlHandler);
							break;
						
						default:
							message = "";
							dataType = "";
							loopCount = -1;
							continue;
						}
						
						if(messageProcessor != null)
							messageProcessor.processMessage(message, socket);
						message = "";
						dataType = "";
						loopCount = -1;
					}
					
					loopCount++;
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
	            	sendMessage("DISCONECT", socket);
	                socket.close();
	                String username = (String) Utils.getKeyFromValue(connectedUser, socket);
	                if(username != null)
	                {
		                connectedUser.values().remove(socket);
		                
		                Iterator<Entry<String, Socket>> it = connectedUser.entrySet().iterator();
		                while (it.hasNext())
		                {
		                	 Entry<String, Socket> pair = it.next();
		                	 sendMessage("HOME:REMOVEUSER=" + username, pair.getValue());
		                }
	                }
	            } 
	            catch (IOException e) 
	            {
	            }
            }
		}
	}

	public void sendMessage(String message, Socket socket)
	{
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(socket.getOutputStream(), true);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		writer.flush();
		writer.println(message);
	}
}
