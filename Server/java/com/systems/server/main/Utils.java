package com.systems.server.main;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.systems.server.sql.SQLHandler;

public class Utils
{
	public static boolean userExists(String username)
	{
		ResultSet rs = SQLHandler.getInstance().eqecuteCommand("SELECT COUNT(username) FROM Users WHERE username = '" + username + "'");
		try
		{
			return Integer.parseInt(rs.getString(1)) > 0;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
