package com.systems.server.network.processors;

import java.net.Socket;

import com.systems.server.main.Utils;
import com.systems.server.network.INetworkMessage;
import com.systems.server.network.NetworkHandler;
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
			String sql = "INSERT INTO Users (username, password, dateOfBirth, placeOfBirth) VALUES('" 
					+ messageArray[0] + "','" + messageArray[1] + "','" 
					+ messageArray[2] + "','" + messageArray[3] + "');";
			sqlHandler.insertValues(sql);
			NetworkHandler.getNetwork().sendMessage("REG:SUCCESS", socket);
		}
		else
		{
			NetworkHandler.getNetwork().sendMessage("REG:FAIL", socket);
		}
	}

}
