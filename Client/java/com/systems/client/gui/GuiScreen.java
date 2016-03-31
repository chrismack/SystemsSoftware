package com.systems.client.gui;

public class GuiScreen
{
	public static GuiScreen INSTANCE;
	
	public static GuiScreen getInstance()
	{
		if(INSTANCE == null)
		{
			return null;
		}
		return INSTANCE;
	}
	
	public void close()
	{
	}
}
