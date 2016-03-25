package com.systems.client.network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.systems.client.main.Main;



public class NetworkHandler implements Runnable
{
	/*
	 * I am assuming this has to match up with the server hostname and port.
	 */
	private static String HOSTNAME = "127.0.0.1";
	private static int PORT = 4556;
	
	private Socket socket;;
	private BufferedReader in;	
	private PrintWriter out; 
	private DataInputStream din;
	private DataOutputStream dout;
	
	/*
	 * For multi-client handling
	 * 
	 *  private static int CLIENTID = 1;
	 */
	
	private static NetworkHandler INSTANCE;
	
	public NetworkHandler()
	{
		INSTANCE = this;
		String address = HOSTNAME;
		int port = PORT;
		
		try
		{
			this.socket = new Socket(address, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static NetworkHandler getNetworkHandler()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new NetworkHandler();
		}
		return INSTANCE;
	}
	
	public String getHostName()
	{
		return HOSTNAME;
	}
	
	public int getPort()
	{
		return PORT;
	}
	
	public void sendMessage(String message)
	{
		this.out.println(message);
	}
	
	public void sendBytes(byte[] b) throws IOException
	{
		this.socket.getOutputStream().write(b);
	}

	@Override
	public void run()
	{
	while(Main.RUNNING)
		{
			try
			{
				System.out.println(in.readLine());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
