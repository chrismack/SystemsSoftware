package com.systems.client.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient implements Runnable
{

	private NetworkHandler network;
	
	public ChatClient()
	{
		network = NetworkHandler.getNetworkHandler();
		/*
		 * Ben start making a UI!!
		 * Can make in netbeans and move interface code over
		 */
	}

	public void run()
	{
		
		/*
		 * Thinking about it we should have the PrintWriter in the Network handler and just have a 
		 * void sendPacket(String) function;
		 * 
		 * Same for inputs from server should be handled in the handler and then dispatched to the
		 * appropriate class
		 */
		
		String address = network.getHostName();
		int port = network.getPort();
		
		try
		{
			Socket socket = new Socket(address, port);
			
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
