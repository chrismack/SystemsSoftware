package com.systems.server.network.processors;

import java.net.Socket;

import com.systems.server.main.Utils;
import com.systems.server.network.INetworkMessage;
import com.systems.server.network.NetworkHandler;
import com.systems.server.sql.SQLHandler;
import org.mindrot.jbcrypt.*;

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
		
		String[] likedMusic = messageArray[4].split(",");
		
		if(!Utils.userExists(messageArray[0]))
		{
			String hash = BCrypt.hashpw(Utils.removeEscapedChars(messageArray[1]), BCrypt.gensalt());

			String sql = "INSERT INTO Users (username, password, dateOfBirth, placeOfBirth, profilePic, ip) VALUES('" 
					+ messageArray[0] + "','" + hash + "','" 
					+ messageArray[2] + "','" + messageArray[3] + "','false','"
					+ socket.getInetAddress().getHostAddress() + "');";
			sqlHandler.insertValues(sql);
			
			// length - 1 to negate the extra comma that is sent at the end
			for(int i = 0; i < likedMusic.length - 1; i++)
			{
				String musicSQL = "INSERT INTO LikedMusic (username, genre) VALUES ('" + messageArray[0] + "','" + likedMusic[i] + "');";
				sqlHandler.insertValues(musicSQL);
			}
			NetworkHandler.getNetwork().sendMessage("REG:SUCCESS", socket);
		}
		else
		{
			NetworkHandler.getNetwork().sendMessage("REG:FAIL", socket);
		}
	}

}
