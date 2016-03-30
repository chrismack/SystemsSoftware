package com.systems.server.network.processors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.systems.server.main.Utils;
import com.systems.server.network.INetworkMessage;
import com.systems.server.network.NetworkHandler;
import com.systems.server.sql.SQLHandler;

public class SongProcessor implements INetworkMessage
{

	private SQLHandler sqlHandler;
	
	
	public SongProcessor(SQLHandler sqlHandler)
	{
		this.sqlHandler = sqlHandler;
	}
	
	@Override
	public void processMessage(String message, Socket socket)
	{
		message = message.substring(5);
		message = Utils.removeEscapedChars(message);
		if(message.startsWith("UPLOAD="))
		{
			message = message.substring(7);
			String[] messageArray = message.split(",");
			String username = messageArray[0];
			String fileName = messageArray[1];
			long fileSize = Long.parseLong(messageArray[2]);
			
			String sql = "INSERT INTO Songs(username, songTitle) VALUES('" + username + "','" + fileName + "');";
			sqlHandler.insertValues(sql);
			
			writeFile(fileName, fileSize, socket);
		}
		else if(message.startsWith("LISTEN="))
		{
			message = message.substring(7);
			
			// Check we have the file on record
			String songExistsSQL = "SELECT songTitle FROM Songs WHERE songTitle = '" + message + "';";
			boolean songExists = sqlHandler.getColsCount(sqlHandler.eqecuteCommand(songExistsSQL)) > 0 ? true : false;
			if(songExists)
			{
				File file = new File(message);
				if(file.exists())			// We have the file
				{
					long fileLength = file.length();
					// Send header information
					NetworkHandler.getNetwork().sendMessage("HOME:PLAY=" + file.getName() + "," + String.valueOf(fileLength), socket);
					
					try
					{
						int count;
						byte[] bytes = new byte[(int) fileLength];
						InputStream in = new FileInputStream(file);
						
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
			}
		}
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
