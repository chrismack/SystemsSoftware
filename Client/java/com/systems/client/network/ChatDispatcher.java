package com.systems.client.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.systems.client.gui.Chat;

/*
 * Used to handle different chat windows and incomming messages
 */
public class ChatDispatcher extends Thread
{
	
	private String username;		// This is the current users name
	
	private HashMap<String, Chat> connectedChats;
	private Socket socket;
	private PrintWriter out;
	private String chatAddress = "localhost";
	private int port = 4557;
	
	/*
	 * set up connection to the chat server
	 */
	public ChatDispatcher(String username)
	{
		this.username = username;
		connectedChats = new HashMap<String, Chat>();
		try
		{
			this.socket = new Socket(chatAddress, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			this.start();
			writeMessage("CONNECT:");			//send connect message to the server to say we are able to chat
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Write a message with our username prefixed
	 */
	public void writeMessage(String message)
	{
		// Writes our name to we can be identified
		this.out.println(username + ":" + message);
		
	}
	
	/*
	 * Open the chat window and set that window to be focused
	 */
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
	
	/*
	 * Listen for messages from the server, should only recieve chat messages
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{
		int count = 0;							// Number of bytes recieved
		byte[] bytes = new byte[1024 * 4];		// Message buffer
		String user = "";
		String message = "";
		try
		{
			while((count = this.socket.getInputStream().read(bytes)) > 0)		//Contains break condition will wait until message has been recieved
			{
				message = new String(bytes, 0, count);		// Convert the bytes into a string
				// Gets the text upto the first :
				user = message.substring(0, message.indexOf(":"));
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

	/*
	 * called when window is closed to a specific user
	 * removes them from connected users
	 */
	public void closeChat(String user)
	{
		if(this.connectedChats.containsKey(user))
		{
			this.connectedChats.remove(user);
		}
	}

	/*
	 * Send message to all connected clients that we are no longer connected
	 */
	public void closeAllChats()
	{
		for(Entry<String, Chat> chats : connectedChats.entrySet())
		{
			writeMessage(chats.getKey() + ":" + "I HAVE LOGGED OFF");
		}
	}
	
}
