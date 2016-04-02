package com.systems.client.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.systems.client.gui.Chat;

public class ChatDispatcher extends Thread
{
	
	private String username;		// This is the current users name
	
	private HashMap<String, Chat> connectedChats;
	private Socket socket;
	private PrintWriter out;
	private String chatAddress = "127.0.0.1";
	private int port = 4557;
	
	public ChatDispatcher(String username)
	{
		this.username = username;
		connectedChats = new HashMap<String, Chat>();
		try
		{
			this.socket = new Socket(chatAddress, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			this.start();
			writeMessage("CONNECT:");
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeMessage(String message)
	{
		// Writes our name to we can be identified
		this.out.println(username + ":" + message);
		
	}
	public void openChat(String user)
	{
		// If a chat has already been opened with a specific user
		synchronized (connectedChats)
		{
			if(connectedChats.containsKey(user))
			{
				// Focus on that chat
				connectedChats.get(user).toFront();	//Focus on the chat window
			}
			Chat chat = new Chat(username, user, this);			// New chat window  //username this client user = toCLient
			chat.init();
			connectedChats.put(user, chat);		// Add the chat to the collection of open chats
			
		}
	}
	
	@Override
	public void run()
	{
		int count = 0;
		byte[] bytes = new byte[1024 * 4];
		String user = "";
		String message = "";
		try
		{
			while((count = this.socket.getInputStream().read(bytes)) > 0)
			{
				message = new String(bytes, 0, count);
				// Gets the text upto the first :
				user = message.substring(0, message.indexOf(":"));
				message = removeEscapedChars(message);
				message = removeEscapedChars(message);
				
				synchronized (connectedChats)
				{
					if(connectedChats.containsKey(user))
					{
						connectedChats.get(user).newMessage(message);
					}
					else
					{
						openChat(user);
						connectedChats.get(user).newMessage(message);
					}
				}
				
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String removeEscapedChars(String str)
	{
		String noEsc = str.replaceAll("[\r\n\t\b\f\"\']", "");
		return noEsc;
	}

	public void closeChat(String user)
	{
		if(this.connectedChats.containsKey(user))
		{
			this.connectedChats.remove(user);
		}
	}

	public void closeAllChats()
	{
		for(Entry<String, Chat> chats : connectedChats.entrySet())
		{
			writeMessage(chats.getKey() + ":" + "I HAVE LOGGED OFF");
		}
	}
	
}
