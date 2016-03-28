package com.systems.server.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHandler
{
	private static SQLHandler INSTANCE;
	
	private Connection connection = null;
	
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
	    try 
	    {
	      Class.forName("org.sqlite.JDBC");
	      connection = DriverManager.getConnection("jdbc:sqlite:D:\\Chris\\Documents\\Uni\\Year Two\\Programming\\Systems\\SystemsSoftware\\testold.db");
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

}
