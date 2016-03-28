package com.systems.server.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.systems.server.network.NetworkHandler;
import com.systems.server.sql.SQLHandler;

public class Main 
{

	public static boolean RUNNING = false;
	
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException 
	{
		RUNNING = true;
		System.out.println("Server running");
		try 
		{
			SQLHandler sqlHandler = new SQLHandler("D:\\Chris\\Documents\\Uni\\Year Two\\Programming\\Systems\\SystemsSoftware\\testold.db");
			NetworkHandler networkHandler = new NetworkHandler(sqlHandler);
			networkHandler.start();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("SERVER STARTED");

	}
	

}
