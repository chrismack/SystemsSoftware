package com.systems.client.gui;

public class GuiScreen
{
	public static GuiScreen INSTANCE;
	
	/*
	 * Used to get instance of GUI screens that are open
	 * Used for sending network messages directly to GUI's
	 */
	public static GuiScreen getInstance()
	{
		if(INSTANCE == null)
		{
			return null;
		}
		return INSTANCE;
	}
	
	/*
	 * When the GUI has been closed
	 * does nothing by default each case is different
	 */
	public void close()
	{
	}
}
