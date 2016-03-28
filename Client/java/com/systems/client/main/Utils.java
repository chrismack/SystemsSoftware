package com.systems.client.main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils
{
	
	public static String hashPassword(String password)
	{
		MessageDigest messageDigest;
		String encr = null;
		try
		{
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			encr = new String(messageDigest.digest());	
		} 
		catch (NoSuchAlgorithmException e1)
		{
			e1.printStackTrace();
		}
		//Removes all the escape chars
		String noEsc = encr.replaceAll("\\.", "");
		String noQuote = noEsc.replaceAll("'", "");
		return noQuote;
	}
}
