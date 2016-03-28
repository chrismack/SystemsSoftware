package com.systems.server.network;

import java.net.Socket;

import com.systems.server.main.Utils;
import com.systems.server.sql.SQLHandler;

public class RegistrationProcessor implements INetworkMessage
{
	private SQLHandler sqlHandler;
	
	public RegistrationProcessor(SQLHandler handler)
	{
		this.sqlHandler = handler;
	}
	
	@Override
	public void processMessage(String message, Socket socket)
	{
		message = message.replaceFirst("REG:", "");
		String[] messageArray = message.split("[|]");
		if(!Utils.userExists(messageArray[0]))
		{
			sqlHandler.insertValues("INSERT INTO Users (username, password) VALUES('" + messageArray[0] + "','" + messageArray[1] + "');");
			NetworkHandler.getNetwork().sendMessage("REG:SUCCESS", socket);
		}
		else
		{
			NetworkHandler.getNetwork().sendMessage("REG:FAIL", socket);
		}
	}

}
