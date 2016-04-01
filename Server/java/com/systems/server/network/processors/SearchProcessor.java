package com.systems.server.network.processors;

import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.systems.server.main.Utils;
import com.systems.server.network.INetworkMessage;
import com.systems.server.network.NetworkHandler;
import com.systems.server.sql.SQLHandler;

public class SearchProcessor implements INetworkMessage
{
	
	private SQLHandler sqlHandler;

	public SearchProcessor(SQLHandler sqlHandler)
	{
		this.sqlHandler = sqlHandler;
	}
	
	@Override
	public void processMessage(String message, Socket socket)
	{
		message = message.substring(5);
		Utils.removeEscapedChars(message);
		
		if(message.startsWith("USER="))
		{
			message = message.substring(5);
			message = Utils.removeEscapedChars(message);
			String searchUserSQL = "SELECT username FROM Users WHERE username = '" + message + "';";
			ResultSet foundUsersRS = sqlHandler.eqecuteCommand(searchUserSQL);
			
			try
			{
				if(foundUsersRS.next())
				{
					NetworkHandler.getNetwork().sendMessage("SRCH:USER=" + foundUsersRS.getString(1), socket);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else if(message.startsWith("MUSIC="))
		{
			message = message.substring(6);
			message = Utils.removeEscapedChars(message);
			String[] genres = message.split(",");
			if(genres.length > 0)
			{
				String searchMusicSQL = "SELECT DISTINCT username FROM LikedMusic WHERE ";
				for(String genre : genres)
				{
					searchMusicSQL += "genre = '" + genre + "' OR ";
				}
				searchMusicSQL = searchMusicSQL.substring(0, searchMusicSQL.length() - 3);
				ResultSet searMusicRS = sqlHandler.eqecuteCommand(searchMusicSQL);
				
				String foundMusicUsers = "";
				
				try
				{
					while(searMusicRS.next())
					{
						foundMusicUsers += searMusicRS.getString(1) + ",";
					}
					if(foundMusicUsers.length() > 0)
					{
						foundMusicUsers = foundMusicUsers.substring(0, foundMusicUsers.length() -1);
						NetworkHandler.getNetwork().sendMessage("SRCH:USER=" + foundMusicUsers, socket);
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

}
