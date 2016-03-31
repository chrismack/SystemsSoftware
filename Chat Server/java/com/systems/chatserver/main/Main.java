package com.systems.chatserver.main;

import com.systems.chatserver.network.NetworkHandler;

public class Main
{

	public static void main(String[] args)
	{
		NetworkHandler network = new NetworkHandler();
		network.start();
	}

}
