package com.systems.client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.systems.client.main.Main;

public class ChatClient implements Runnable
{

	private NetworkHandler network;


	
	public ChatClient()
	{
		network = NetworkHandler.getNetworkHandler();
		
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
		
	}

}
