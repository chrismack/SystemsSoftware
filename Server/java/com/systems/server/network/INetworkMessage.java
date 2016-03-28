package com.systems.server.network;

import java.net.Socket;

public interface INetworkMessage
{
	public void processMessage(String message, Socket socket);
}
