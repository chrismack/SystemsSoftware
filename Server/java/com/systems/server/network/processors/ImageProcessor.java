package com.systems.server.network.processors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.systems.server.main.Utils;
import com.systems.server.network.INetworkMessage;
import com.systems.server.network.NetworkHandler;
import com.systems.server.sql.SQLHandler;

public class ImageProcessor implements INetworkMessage
{

	private SQLHandler sqlHandler;
	
	public ImageProcessor(SQLHandler sqlHandler)
	{
		this.sqlHandler	= sqlHandler;
	}
	
	@Override
	public void processMessage(String message, Socket socket)
	{
		message = message.substring(4);
		
		if(message.startsWith("NEW="))
		{
			message = message.substring(4);
			message = Utils.removeEscapedChars(message);
			String[] messageArray = message.split(",");
			
			String sqlAddImage = "UPDATE Users SET profilePic = 'true' WHERE username = '" + messageArray[0] + "';";
			sqlHandler.insertValues(sqlAddImage);
			
			//Save profile pics as username
			writeFile("img/" + messageArray[0] + getExtension(messageArray[1]), Long.parseLong(messageArray[2]), socket);
		}
		else if(message.startsWith("GET="))
		{
			message = message.substring(4);
			message = Utils.removeEscapedChars(message);
			String sqlProfileHasImage = "SELECT username FROM Users WHERE username = '" + message + "' AND profilePic = 'true';";
			try
			{
				ResultSet hasImageSQL = sqlHandler.eqecuteCommand(sqlProfileHasImage);
				if(hasImageSQL.next())
				{
					if(sqlHandler.getColsCount(hasImageSQL) > 0)
					{
						File profilePic = new File("img/" + message + ".png");
						NetworkHandler.getNetwork().sendMessage("HOME:INFOIMG=" + profilePic.getName() + "," + String.valueOf(profilePic.length()), socket);
						
						try
						{
							int count;
							byte[] bytes = new byte[(int) profilePic.length()];
							InputStream in = new FileInputStream(profilePic);
							
							// Send the file to the server
							while ((count = in.read(bytes)) > 0)
							{
								// Send the file
								NetworkHandler.getNetwork().sendBytes(bytes, socket);
							}
							in.close();
						}
						catch (Exception ee)
						{
							ee.printStackTrace();
						}
					}
					else
					{
						NetworkHandler.getNetwork().sendMessage("HOME:INFOIMG=SERVER-NOPROFILEPIC", socket);
					}
				}
				else
				{
					NetworkHandler.getNetwork().sendMessage("HOME:INFOIMG=SERVER-NOPROFILEPIC", socket);
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	}
	
	private String getExtension(String fileName)
	{
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		return "." + extension;
	}
	
	private void writeFile(String fileName, long fileSize, Socket socket)
	{
		int count = 0;
		int countedBytes = 0;
		byte[] bytes = new byte[8 * 1024];
		FileOutputStream inF;
		try
		{
			inF = new FileOutputStream(new File(fileName));
		
			while ((count = socket.getInputStream().read(bytes)) > 0)
			{
				countedBytes += count;	// The number of bytes that have been sent over the socket
				inF.write(bytes);		// Write the bytes into the file
				
				// If all the expected bytes have been sent
				if(countedBytes >= fileSize)
				{
					inF.flush();
					inF.close();
					break;				// Stop listening to the socket as the file has been sent
				}						// Listening will resume in the network listener
			}
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
