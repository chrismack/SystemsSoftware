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
	
	
	/*
	 * Set up connection to the server
	 */
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
			if(GuiScreen.getInstance() != null)
				GuiScreen.getInstance().close();
			JOptionPane.showMessageDialog(null, "Server connection not found", "Disconnect", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/*
	 * Singleton instance of the network handler
	 */
	public static NetworkHandler getNetworkHandler()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new NetworkHandler();
		}
		return INSTANCE;
	}
	
	/*
	 * Address of the host we are connecting to
	 */
	public String getHostName()
	{
		return HOSTNAME;
	}
	
	/*
	 * Port of the host we are connecting to
	 */
	public int getPort()
	{
		return PORT;
	}
	
	/*
	 * Instance of the socket to the server
	 */
	public Socket getServerSocket()
	{
		return this.socket;
	}
	
	/*
	 * Send text message to the server
	 */
	public void sendMessage(String message)
	{
		this.out.println(message);
	}
	
	/*
	 * Send an array of bytes to the server
	 */
	public void sendBytes(byte[] b) throws IOException
	{
		this.socket.getOutputStream().write(b);
	}

	/*
	 * Listen for in coming messages from the server 
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
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
				
				while ((count = socket.getInputStream().read(bytes)) > 0)		//Contains breaking statement will wait for message to be recieved
				{
					if(count >= 8 * 1024)			//If buffer has been exceeded
					{
						message += new String(bytes, 0, count);						// convert bytes to readable string
						dataType = message.substring(0, message.indexOf(":"));		// Get first header message from the message
					}
					else
					{
						message += new String(bytes, 0, count);
						if(dataType == "")
						{
							dataType = message.substring(0, message.indexOf(":"));
						}
						INetworkMessage networkMessage = null;
						
						switch (dataType)					// Route message to correct location based on the first header message
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
						case "DISCONNECT":					// Special case only one header
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