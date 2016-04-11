package com.systems.client.main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils
{
	
	/*
	 * Hash pass words using sha-256
	 */
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
	
	/*
	 * Remove all special characters from strings
	 */
	public static String removeEscapedChars(String str)
	{
		String noEsc = str.replaceAll("[\r\n\t\b\f\"\']", "");
		return noEsc;
	}
}
