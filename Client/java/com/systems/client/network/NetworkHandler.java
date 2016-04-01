package com.systems.client.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.systems.client.gui.GuiScreen;
import com.systems.client.gui.Home;
import com.systems.client.gui.Login;
import com.systems.client.gui.Registration;
import com.systems.client.gui.Search;



public class NetworkHandler extends Thread
{
	/*
	 * I am assuming this has to match up with the server hostname and port.
	 */
	private static String HOSTNAME = "127.0.0.1";
	private static int PORT = 4556;
	
	private Socket socket;
	private PrintWriter out; 
	
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
	
	public Socket getServerSocket()
	{
		return this.socket;
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
		while(true)
		{
			try
			{
				int count;
				String message = "";
				String dataType = "";
				byte[] bytes = new byte[8 * 1024];
				
				while ((count = socket.getInputStream().read(bytes)) > 0)
				{
					if(count >= 8 * 1024)
					{
						message += new String(bytes, 0, count);
						dataType = message.substring(0, message.indexOf(":"));
					}
					else
					{
						message += new String(bytes, 0, count);
						if(dataType == "")
						{
							dataType = message.substring(0, message.indexOf(":"));
						}
						INetworkMessage networkMessage = null;
						
						switch (dataType)
						{
						case "REG":
							networkMessage = (INetworkMessage) Registration.getInstance();
							break;
						case "LOGIN":
							networkMessage = (INetworkMessage) Login.getInstance();
							break;
						case "HOME":
							networkMessage = (INetworkMessage) Home.getInstance();
							break;
						case "SRCH":
							try
							{
								networkMessage = (INetworkMessage) Home.getSearchInstance();
							} catch (NullPointerException e) 
							{
								message = "";
								dataType = "";
								continue;
							}
							break;
						case "DISCONNECT":
							if(GuiScreen.INSTANCE != null)
							{
								GuiScreen.INSTANCE.close();
							}
							JOptionPane.showMessageDialog(null, message, "Disconnect", JOptionPane.INFORMATION_MESSAGE);
							continue;
						default:
							message = "";
							dataType = "";
							continue;
						}
						
						if(count < 8 * 1024)
							networkMessage.processMessage(message);
						message = "";
						dataType = "";
					}
				}
					
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}