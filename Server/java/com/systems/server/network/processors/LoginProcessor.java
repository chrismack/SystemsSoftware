package com.systems.server.network.processors;

import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.plaf.SliderUI;

import com.systems.server.main.Utils;
import com.systems.server.network.INetworkMessage;
import com.systems.server.network.NetworkHandler;
import com.systems.server.sql.SQLHandler;

public class LoginProcessor implements INetworkMessage
{

	private SQLHandler sqlHandler;
	
	public LoginProcessor(SQLHandler sqlHandler)
	{
		this.sqlHandler = sqlHandler;
	}
	
	@Override
	public void processMessage(String message, Socket socket)
	{
		message = message.substring(6);
		String[] messageArray = message.split("[|]");
		
		try
		{
			if(!(NetworkHandler.getNetwork().connectedUser.containsKey(messageArray[0])))
			{
				if(Utils.userExists(messageArray[0]))
				{
					ResultSet userPassword = sqlHandler.eqecuteCommand("SELECT password FROM Users WHERE username = '" + messageArray[0] + "';");
					if(userPassword.next())
					{
						if(messageArray[1].startsWith(userPassword.getString(1)))
						{
							/*
							 * Login success
							 */
							
							//Send all connected clients
							String connectedUsersMessage = "";
							for(String connectedUser : NetworkHandler.getNetwork().connectedUser.keySet())
							{
								connectedUsersMessage += connectedUser + ",";
							}
							// Send connected users if anyone is connected
							if(connectedUsersMessage.length() > 1)
							{
								connectedUsersMessage = connectedUsersMessage.substring(0, connectedUsersMessage.length() - 1);
								NetworkHandler.getNetwork().sendMessage("LOGIN:CONNECTED=" + connectedUsersMessage, socket);
							}
							
							//Send all friends
							//========================================================================
							String friends = "";
							String sql = "SELECT friendUsername FROM UserFriends WHERE username = '" 
										 + messageArray[0] + "' AND pending = 'false'"
										 + "UNION ALL "
										 + "SELECT username FROM UserFriends WHERE friendUsername = '"
										 + messageArray[0] +"' AND pending = 'false';";
							ResultSet rs = sqlHandler.eqecuteCommand(sql);
							
							while(rs.next())
							{
 								friends += rs.getString(1) + ",";
							}
							if(friends.length() > 0)
							{
								friends = friends.substring(0, friends.length() - 1);
								NetworkHandler.getNetwork().sendMessage("LOGIN:FRIENDS=" + friends, socket);
							}
							
							
							//Send all pending friends
							//================================================================
							String pendingFriends = "";
							String pendingSql = "SELECT username FROM UserFriends WHERE friendUsername = '" 
										 + messageArray[0] + "' AND pending = 'true';";
							ResultSet rsPending = sqlHandler.eqecuteCommand(pendingSql);
							
							while(rsPending.next())
							{
								pendingFriends += rsPending.getString(1) + ",";
							}
							if(pendingFriends.length() > 0)
							{
								pendingFriends = pendingFriends.substring(0, pendingFriends.length() - 1);
								NetworkHandler.getNetwork().sendMessage("LOGIN:PENDING=" + pendingFriends, socket);
							}
							
							
							//Send successful login message
							//=============================================================
							NetworkHandler.getNetwork().sendMessage("LOGIN:SUCCESS", socket);
							
							/*
							 * Tell all other connected clients that they have connected
							 */
							Iterator<Entry<String, Socket>> it = NetworkHandler.getNetwork().connectedUser.entrySet().iterator();
			                while (it.hasNext())
			                {
			                	 Entry<String, Socket> pair = it.next();
			                	 NetworkHandler.getNetwork().sendMessage("HOME:CONNECTUSER=" + messageArray[0], pair.getValue());
			                }
			                
			                // Add client to connected users list
			                NetworkHandler.getNetwork().connectedUser.put(messageArray[0], socket);
						}
						else // Auth failed
						{
							NetworkHandler.getNetwork().sendMessage("LOGIN:FAIL", socket);
						}
					}
				}
				else //User doesn't exist
				{
					NetworkHandler.getNetwork().sendMessage("LOGIN:FAIL", socket);
				}
			}
			else //User already connected
			{
				NetworkHandler.getNetwork().sendMessage("LOGIN:FAILCONNECTED", socket);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
