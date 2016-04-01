package com.systems.client.main;

import java.io.IOException;

import com.systems.client.gui.Registration;
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
		System.out.println("==============");
		System.out.println("Client running");
		System.out.println("==============");
		
		/*
		 * Init the network handler before we initalise any of the client GUI
		 * Network handler is a Singleton class 
		 */
		NetworkHandler netHandler = new NetworkHandler();
		netHandler.start();
		
		// Init screen is registration
		Registration reg = new Registration();
		reg.init();
	}

}
