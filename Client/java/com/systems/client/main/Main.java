package com.systems.client.main;

import java.io.IOException;
import java.nio.charset.Charset;

import com.systems.client.network.NetworkHandler;

public class Main 
{

	public static boolean RUNNING = false;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		RUNNING = true;
		System.out.println("Client running");
		NetworkHandler netHandler = new NetworkHandler();
		netHandler.sendMessage("Test message");
		netHandler.sendBytes("jsbhfjhebfhbsebfsebfb".getBytes());
		System.out.println("Client sent message");
		try
		{
			System.in.read();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
