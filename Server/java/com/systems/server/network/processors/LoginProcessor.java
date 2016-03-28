package com.systems.server.network.processors;

import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			if(Utils.userExists(messageArray[0]))
			{
				ResultSet userPassword = sqlHandler.eqecuteCommand("SELECT password FROM Users WHERE username = '" + messageArray[0] + "';");
				if(userPassword.next())
				{
					String pw, pw1 = "";
					System.out.println(pw = userPassword.getString(1));
					System.out.println(pw1 = messageArray[1]);
					if(messageArray[1].startsWith(userPassword.getString(1)))
					{
						NetworkHandler.getNetwork().sendMessage("LOGIN:SUCCESS", socket);
						NetworkHandler.getNetwork().connectedUser.put(messageArray[0], socket);
					}
					else
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
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
