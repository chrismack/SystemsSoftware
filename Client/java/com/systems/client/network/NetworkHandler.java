package com.systems.client.network;

public class NetworkHandler
{

	private static NetworkHandler INSTANCE;
	
	public NetworkHandler()
	{
		INSTANCE = this;
	}
	
	
	public static NetworkHandler getNetworkHandler()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new NetworkHandler();
		}
		return INSTANCE;
	}
}
