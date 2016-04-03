package com.systems.chatserver.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ChatLogger
{
	private File log;
	private PrintWriter out;
	private FileWriter fw;
	
	public ChatLogger(String usernameFrom, String usernameTo)
	{
		String fileName;
		int compare = usernameFrom.compareTo(usernameTo);
				
		if (compare < 0)
			fileName = usernameFrom + "-" + usernameTo;
		else
			fileName = usernameTo + "-" + usernameFrom;
		
		File logDir = new File(System.getProperty("user.dir") + "/Logs/");
		
		if(!logDir.exists())
			logDir.mkdirs();

		log = new File(logDir  + File.separator + fileName + ".log");
		try
		{
			if(!log.exists())
			{
				log.createNewFile();
			} 
			fw = new FileWriter(log, true);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeLog(String logMessage)
	{
		try
		{
			if(log != null)
			{
				out = new PrintWriter(new BufferedWriter(new FileWriter(log, true)));
				out.println(logMessage);
				out.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public FileWriter getFileWriter()
	{
		return this.fw;
	}
	
}
