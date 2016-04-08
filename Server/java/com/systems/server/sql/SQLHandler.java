package com.systems.server.sql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SQLHandler
{
	private static SQLHandler INSTANCE;
	
	private Connection connection = null;
	private String sqlCreateLikedMusic;
	private String sqlCreateUsers;
	private String sqlCreateUserFriends;
	private String sqlCreateSongs;
	private String sqlCreateUserPosts;
	
	
	
	public static SQLHandler getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new SQLHandler("database.db");
		}
		return INSTANCE;
	}
	
	public SQLHandler(String dbPath)
	{
		INSTANCE = this;
		File database =  new File(dbPath);
		
		boolean create = false;
		
		if(!database.exists())
		{
			try
			{
				database.createNewFile();
				create = true;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			 sqlCreateLikedMusic  = "CREATE TABLE 'LikedMusic' ('username'	INTEGER,'genre'	INTEGER,PRIMARY KEY(username,genre))";
			 sqlCreateUsers	      = "CREATE TABLE Users('username'	TEXT NOT NULL UNIQUE,'password'	TEXT NOT NULL,'dateOfBirth'	TEXT NOT NULL,'placeOfBirth'TEXT NOT NULL,'profilePic'	"
										+ "TEXT,'ip'	TEXT,PRIMARY KEY(username))";
			 sqlCreateUserFriends = "CREATE TABLE UserFriends ('username'	TEXT,'friendUsername'TEXT,'pending'TEXT,PRIMARY KEY(username,friendUsername))";
			 sqlCreateSongs 	  = "CREATE TABLE 'Songs' ('username'	TEXT,'songTitle'	TEXT,PRIMARY KEY(username,songTitle))";
			 sqlCreateUserPosts   = "CREATE TABLE UserPost ('username'	TEXT,'time'	INTEGER,'post'	TEXT,'ID'	INTEGER,PRIMARY KEY(ID))";
		}
		
	    try 
	    {
	      Class.forName("org.sqlite.JDBC");
	      connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	      if(create)
	      {
	    	  connection.createStatement().executeUpdate(sqlCreateLikedMusic);
	    	  connection.createStatement().executeUpdate(sqlCreateUsers);
	    	  connection.createStatement().executeUpdate(sqlCreateUserFriends);
	    	  connection.createStatement().executeUpdate(sqlCreateSongs);
	    	  connection.createStatement().executeUpdate(sqlCreateUserPosts);
	    	  if(!connection.getAutoCommit())
	    		  connection.commit();
	      }
	    }
	    catch (Exception e)
	    {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	
	public ResultSet eqecuteCommand(String sql)
	{
		if(connection != null)
		{
			ResultSet rs = null;
			try
			{
				rs = connection.createStatement().executeQuery(sql);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			return rs;
		}
		return null;
	}
	
	public void insertValues(String sql)
	{
		if(connection != null)
		{
			try
			{
				connection.createStatement().executeUpdate(sql);
				if(!connection.getAutoCommit())
					connection.commit();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public int getColsCount(ResultSet rs)
	{
		ResultSetMetaData rsmd;
		int columnsNumber = 0;
		try
		{
			rsmd = rs.getMetaData();
			columnsNumber = rsmd.getColumnCount();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return columnsNumber;
	}

}
