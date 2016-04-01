package com.systems.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import com.systems.client.main.Utils;
import com.systems.client.network.INetworkMessage;
import com.systems.client.network.NetworkHandler;

import java.awt.List;
import java.awt.Button;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Choice;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class Search implements INetworkMessage
{

	private String user;
	
	private JFrame frmSearch;
	private JPanel contentPane;
	private JTextField textField;
	private List listFoundPeople;

	/**
	 * Launch the application.
	 */
	public void init()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					frmSearch.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Search(String username)
	{
		this.user = username;
		
		frmSearch = new JFrame("Search");
		frmSearch.setResizable(false);
		frmSearch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSearch.setBounds(100, 100, 382, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frmSearch.setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		listFoundPeople = new List();
		listFoundPeople.setBounds(186, 29, 176, 265);
		panel.add(listFoundPeople);
		
		JButton btnAddFriend = new JButton("Add");
		
		btnAddFriend.setBounds(231, 301, 89, 23);
		panel.add(btnAddFriend);
		
		JButton btnSearchName = new JButton("Search");
		
		btnSearchName.setBounds(10, 60, 89, 23);
		panel.add(btnSearchName);
		
		textField = new JTextField();
		textField.setBounds(10, 29, 170, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		final Choice choice = new Choice();
		choice.setBounds(10, 104, 102, 20);
		choice.add("Blues");
		choice.add("Classical");
		choice.add("Country");
		choice.add("Electronic");
		choice.add("Folk");
		choice.add("Jazz");
		choice.add("New Age");
		choice.add("Reggae");
		choice.add("Rock");
		choice.add("Pop");
		choice.add("Hip-Hop");
		panel.add(choice);
		
		JButton btnAddMusic = new JButton("Add");
		
		btnAddMusic.setBounds(118, 101, 54, 23);
		panel.add(btnAddMusic);
		
		final List listMusic = new List();
		listMusic.setBounds(10, 130, 163, 164);
		panel.add(listMusic);
		
		JButton button = new JButton("Search");
		
		button.setBounds(10, 301, 89, 23);
		panel.add(button);
		
		// Search by user name
		btnSearchName.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				listFoundPeople.removeAll();
				if(!textField.getText().equals(""))
				{
					// Send search for user
					String search = "SRCH:USER=" + textField.getText();
					NetworkHandler.getNetworkHandler().sendMessage(search);
				}
			}
		});
		
		// Add music
		btnAddMusic.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(choice.getSelectedIndex() > -1)
				{
					if(!Arrays.asList(listMusic.getItems()).contains(choice.getSelectedItem()))
					{
						listMusic.add(choice.getSelectedItem());
					}
				}
			}
		});
		
		// Search by music
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				listFoundPeople.removeAll();
				if(!Arrays.asList(listMusic.getItems()).isEmpty())
				{
					String music = "";
					for(String str : listMusic.getItems())
					{
						music += str + ",";
					}
					if(music.length() > 0)
					{
						music = music.substring(0, music.length() - 1);
					}
					// Send search by music
					String searchMusic = "SRCH:MUSIC=" + music;
					NetworkHandler.getNetworkHandler().sendMessage(searchMusic);
				}
			}
		});
		
		// Add user
		btnAddFriend.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(listFoundPeople.getSelectedIndex() > -1)
				{
					String message = Utils.removeEscapedChars("FRIEND:REQ=" + user + "," + listFoundPeople.getSelectedItem());
					NetworkHandler.getNetworkHandler().sendMessage(message);
				}
			}
		});
		
		frmSearch.addWindowListener(new WindowAdapter() 
		{
		    @Override
		    public void windowClosing(WindowEvent windowEvent) 
		    {
		    	Home.makeSearchNull();
		    }
		});
	}

	@Override
	public void processMessage(String message)
	{
		message = message.substring(5);
		message = Utils.removeEscapedChars(message);
		
		if(message.startsWith("USER="))
		{
			message = message.substring(5);
			if(!message.equals(""))
			{
				if(message.contains(","))
				{
					String[] messageArray = message.split(",");
					for (String user : messageArray)
					{
						if(!Arrays.asList(listFoundPeople.getItems()).contains(user))
						{
							listFoundPeople.add(user);
						}
					}
				}
				else
				{
					listFoundPeople.add(message);
				}
			}
		}
	}
}
