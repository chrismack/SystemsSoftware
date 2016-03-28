package com.systems.server.network.processors;

import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.systems.server.main.Utils;
import com.systems.server.network.INetworkMessage;
import com.systems.server.network.NetworkHandler;
import com.systems.server.sql.SQLHandler;

public class FriendProcessor implements INetworkMessage
{

	private SQLHandler sqlHandler;
	
	public FriendProcessor(SQLHandler sqlHandler)
	{
		this.sqlHandler = sqlHandler;
	}
	
	@Override
	public void processMessage(String message, Socket socket)
	{
		message = message.substring(7);
		message = Utils.removeEscapedChars(message);
		if(message.startsWith("REQ="))
		{
			message = message.substring(4);
			String[] messageArray = message.split(",");
			
			// Check the friend exists
			if(Utils.userExists(messageArray[1]))
			{
				if(!requestAlreadySent(messageArray[0], messageArray[1]))
				{
					if(!requestAlreadySent(messageArray[1], messageArray[0]))
					{
						String sql = "INSERT INTO UserFriends(username, friendUsername, pending)"
								+ "VALUES('" + messageArray[0] + "','" + messageArray[1] + "','true')";
						sqlHandler.insertValues(sql);
						
						NetworkHandler.getNetwork().sendMessage("HOME:SENTREQ=" + messageArray[1], socket);
						
						// If the friend is online
						if(NetworkHandler.getNetwork().connectedUser.containsKey(messageArray[1])) 
						{
							Socket friendSocket = NetworkHandler.getNetwork().connectedUser.get(messageArray[1]);
							NetworkHandler.getNetwork().sendMessage("HOME:RECIEVEDREQ=" + messageArray[0], friendSocket);
						}
					}
					else //They have already sent you a request
					{
						addFriend(messageArray[0], messageArray[1], socket);
					}
				}
				else // Request already sent
				{
					
				}
			}
			else // FAIL : Friend doesn't exist on the system
			{
				
			}
		}
		else if(message.startsWith("ACCEPT="))
		{
			message = message.substring(7);
			message = Utils.removeEscapedChars(message);
			String[] messageArray = message.split(",");
			
			addFriend(messageArray[1], messageArray[0], socket);
		}
		else if(message.startsWith("DECLINE="))
		{
			message = message.substring(8);
			message = Utils.removeEscapedChars(message);
			String[] messageArray = message.split(",");
			String sql = "DELETE FROM UserFriends WHERE username = '" + messageArray[0] + "' AND friendUsername = '" + messageArray[1] + "';";
			sqlHandler.insertValues(sql);
		}
		else if(message.startsWith("INFO="))
		{
			message = message.substring(5);
			message = Utils.removeEscapedChars(message);
			String sql = "SELECT username, dateOfBirth, placeOfBirth FROM Users WHERE username = '" + message + "';";
			ResultSet friendInfo = sqlHandler.eqecuteCommand(sql);
			String info = "";
			try
			{
				if(friendInfo.next())
				{
					for(int i = 1; i < sqlHandler.getColsCount(friendInfo) + 1; i++)
					{
						info += friendInfo.getString(i) + ",";
					}
				}
				if(info.length() > 0)
				{
					info = info.substring(0, info.length() - 1);
					NetworkHandler.getNetwork().sendMessage("HOME:INFO=" + info, socket);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void addFriend(String user, String friend, Socket socket)
	{
		String sql = "UPDATE UserFriends SET pending = 'false' WHERE username = '" + user 
				+ "' AND friendUsername = '" + friend + "'";
		sqlHandler.insertValues(sql);
		
		NetworkHandler.getNetwork().sendMessage("HOME:ADDFRIEND=" + user, socket);
		
		if(Utils.userExists(friend))
		{
			// If the user is online
			if(NetworkHandler.getNetwork().connectedUser.containsKey(user)) 
			{
				Socket friendSocket = NetworkHandler.getNetwork().connectedUser.get(user);
				NetworkHandler.getNetwork().sendMessage("HOME:ADDFRIEND=" + friend, friendSocket);
			}
		}
	}
	
	private boolean requestAlreadySent(String username, String friend)
	{
		String sql = "SELECT COUNT(username) FROM UserFriends "
				 + "WHERE username = '" + username + "'"
				 + " AND friendUsername = '" + friend + "';";
		ResultSet rs = SQLHandler.getInstance().eqecuteCommand(sql);
		try
		{
			return rs.getInt(1) > 0;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
