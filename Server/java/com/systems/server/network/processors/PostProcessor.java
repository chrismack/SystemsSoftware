package com.systems.server.network.processors;

import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.systems.server.main.Utils;
import com.systems.server.network.INetworkMessage;
import com.systems.server.network.NetworkHandler;
import com.systems.server.sql.SQLHandler;

public class PostProcessor implements INetworkMessage
{
	
	private SQLHandler sqlHandler;

	public PostProcessor(SQLHandler sqlHandler)
	{
		this.sqlHandler = sqlHandler;
	}
	
	@Override
	public void processMessage(String message, Socket socket)
	{
		message = message.substring(5);
		message = Utils.removeEscapedChars(message);
		
		if(message.startsWith("SUBMIT="))
		{
			message = message.substring(7);
			String[] messageArray = message.split(",");

			try
			{
				String friendsSql = "SELECT friendUsername FROM UserFriends WHERE username = '" 
						 + messageArray[0] + "' AND pending = 'false'"
						 + " UNION ALL "
						 + "SELECT username FROM UserFriends WHERE friendUsername = '"
						 + messageArray[0] +"' AND pending = 'false';";
				ResultSet friendsRS = sqlHandler.eqecuteCommand(friendsSql);
				
				while(friendsRS.next())
				{
					if(NetworkHandler.getNetwork().connectedUser.containsKey(friendsRS.getString(1)))
					{
						Socket friendSocket = NetworkHandler.getNetwork().connectedUser.get(friendsRS.getString(1));
						NetworkHandler.getNetwork().sendMessage("HOME:POST=" + messageArray[0] + "," + messageArray[1] + "," + messageArray[2], friendSocket);
					}
				}
				
				String insertPost = "INSERT INTO UserPost (username, time, post) VALUES('" + messageArray[0] + "','" + messageArray[1] + "','" + messageArray[2] + "');";
				sqlHandler.insertValues(insertPost);
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

}
