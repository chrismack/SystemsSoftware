package com.systems.chatserver.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.systems.chatserver.gui.Gui;

public class NetworkHandler extends Thread
{

	private static int PORT = 4557;
	
	/*
	 * All of the users that have a connected chat
	 * 
	 * String : username
	 * Socket : their socket
	 */
	private HashMap<String, Socket> connectedUsers = new HashMap<String, Socket>();
	
	public NetworkHandler()
	{
		
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
	
	private class NetworkListener extends Thread
	{
		
		private Socket socket;
		
		public NetworkListener(Socket socket)
		{
			this.socket = socket;
		}
		
		@Override
		public void run()
		{
			int count = 0;
			byte[] bytes = new byte[1024 * 4];
			String usernameFrom = "";
			String usernameTo = "";
			String message;
			try
			{
				while ((count = socket.getInputStream().read(bytes)) > 0)
				{
					message = new String(bytes, 0, count);
					usernameFrom = message.substring(0, message.indexOf(":"));
					usernameTo = message.substring(message.indexOf(":") + 1, message.indexOf(":", message.indexOf(":") + 1));
					
					message = message.substring(usernameFrom.length() + 1 + usernameTo.length() + 1);
					message = removeEscapedChars(message);
					
					System.out.println(usernameFrom );
					System.out.println(usernameTo);
					System.out.println(message);
					/*
					 * Connect message
					 * should be the first message that is sent to that chat server
					 * 
					 * CONNECT : <username>
					 */
					if(usernameTo.equals("CONNECT"))
					{
						synchronized (connectedUsers)
						{
							if(!connectedUsers.containsKey(message))
							{
								connectedUsers.put(usernameFrom, socket);
								try
								{
									synchronized (Gui.INSTANCE.lstConnectedUsers)
									{
										Gui.getGui().addUser(usernameFrom);
									}
								}
								catch(NullPointerException e)
								{}
							}
						}
					}
					else
					{
						synchronized (connectedUsers)
						{
							if(connectedUsers.containsKey(usernameTo))
							{
								sendMessage(usernameFrom + ":" + message, connectedUsers.get(usernameTo));
							}
						}
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
					synchronized (connectedUsers)
					{
	         			connectedUsers.values().remove(socket);
					}
					try
					{
						synchronized (Gui.INSTANCE.lstConnectedUsers)
						{
							Gui.INSTANCE.removeUser(getKeyFromValue(connectedUsers, socket));
						}
					}
					catch(NullPointerException e)
					{
					}
					socket.close();
				} 
				catch (IOException e)
				{
				}
			}
		}
	}
	
	private String getKeyFromValue(HashMap<String, Socket> hm, Object value) {
	    for (String o : hm.keySet()) {
	      if (hm.get(o).equals(value)) {
	        return o;
	      }
	    }
	    return null;
	  }
		
	
	public String removeEscapedChars(String str)
	{
		String noEsc = str.replaceAll("[\r\n\t\b\f\"\']", "");
		return noEsc;
	}
}
