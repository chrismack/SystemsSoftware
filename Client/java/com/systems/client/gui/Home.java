package com.systems.client.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.util.Arrays;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.systems.client.main.Utils;
import com.systems.client.network.INetworkMessage;
import com.systems.client.network.NetworkHandler;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.ScrollPane;

public class Home extends GuiScreen implements INetworkMessage
{
	private String username;
	private String[] connectedUsers;
	private String[] friends;
	private String[] pendingFriends;
	private String[] posts;
	private JFrame frmHome;
	private JTextField textFieldPost;
	private List listConnectedPeople;
	private List listRequestsFrom;
	private List listFriends;
	private List listFriendInfo;
	private JTextArea textArea_FriendsPosts;
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
					frmHome.setVisible(true);
					frmHome.setTitle("Home: " + username);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home(String username, String[] connectedUsers, String[] friends, String[] pendingFriends, String[] posts)
	{
		INSTANCE = this;
		this.username = username;
		this.connectedUsers = connectedUsers;
		this.friends = friends;
		this.pendingFriends = pendingFriends;
		this.posts = posts;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmHome = new JFrame();
		frmHome.getContentPane().setBackground(Color.WHITE);
		frmHome.setTitle("Home : ");
		frmHome.setBackground(Color.WHITE);
		frmHome.setResizable(false);
		frmHome.setBounds(100, 100, 620, 724);
		frmHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHome.getContentPane().setLayout(null);
		
		JLabel lblFriends = new JLabel("Friends");
		lblFriends.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblFriends.setBounds(32, 28, 46, 14);
		frmHome.getContentPane().add(lblFriends);
		
		JLabel lblInformation = new JLabel("Information");
		lblInformation.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblInformation.setBounds(180, 28, 66, 14);
		frmHome.getContentPane().add(lblInformation);
		
		JLabel lblSharedSongs = new JLabel("Shared Songs");
		lblSharedSongs.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblSharedSongs.setBounds(410, 28, 84, 14);
		frmHome.getContentPane().add(lblSharedSongs);
		
		listFriends = new List();
		
		
		listFriends.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listFriends.setBounds(32, 48, 122, 167);
		frmHome.getContentPane().add(listFriends);
		
		List listShareSongs = new List();
		listShareSongs.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listShareSongs.setBounds(410, 48, 186, 167);
		frmHome.getContentPane().add(listShareSongs);
		
		JLabel lblFriendsPosts = new JLabel("Friends Posts");
		lblFriendsPosts.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblFriendsPosts.setBounds(32, 239, 84, 14);
		frmHome.getContentPane().add(lblFriendsPosts);
		
		textArea_FriendsPosts = new JTextArea();
		textArea_FriendsPosts.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		textArea_FriendsPosts.setEditable(false);
		textArea_FriendsPosts.setBorder(BorderFactory.createLineBorder(Color.gray));
		textArea_FriendsPosts.setBounds(32, 264, 2, 111);
		frmHome.getContentPane().add(textArea_FriendsPosts);
				
		
		JButton btnPlay = new JButton("Play");
		btnPlay.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnPlay.setBounds(465, 235, 89, 23);
		frmHome.getContentPane().add(btnPlay);
		
		JLabel lblPost = new JLabel("Post:");
		lblPost.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblPost.setBounds(32, 389, 46, 14);
		frmHome.getContentPane().add(lblPost);
		
		textFieldPost = new JTextField();
		textFieldPost.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		textFieldPost.setBounds(67, 386, 415, 20);
		frmHome.getContentPane().add(textFieldPost);
		textFieldPost.setColumns(10);
		
		JButton btnPost = new JButton("Post");
		btnPost.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnPost.setBounds(503, 385, 89, 23);
		frmHome.getContentPane().add(btnPost);
		
		JLabel lblConnectedPeople = new JLabel("Connected People");
		lblConnectedPeople.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblConnectedPeople.setBounds(32, 439, 122, 14);
		frmHome.getContentPane().add(lblConnectedPeople);
		
		listConnectedPeople = new List();
		listConnectedPeople.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listConnectedPeople.setBounds(32, 459, 122, 167);
		frmHome.getContentPane().add(listConnectedPeople);
		
		JButton btnRequestFriendship = new JButton("<html>Friendship <br> Request<html>");
		
		btnRequestFriendship.setToolTipText("sdfsdf");
		btnRequestFriendship.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnRequestFriendship.setBounds(174, 459, 97, 43);
		frmHome.getContentPane().add(btnRequestFriendship);
		
		JButton btnChat = new JButton("Chat");
		btnChat.setToolTipText("sdfsdf");
		btnChat.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnChat.setBounds(174, 513, 97, 43);
		frmHome.getContentPane().add(btnChat);
		
		listRequestsFrom = new List();
		listRequestsFrom.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listRequestsFrom.setBounds(327, 459, 122, 167);
		frmHome.getContentPane().add(listRequestsFrom);
		
		JLabel lblFriendshipRequestsFrom = new JLabel("Friendship Requests From");
		lblFriendshipRequestsFrom.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblFriendshipRequestsFrom.setBounds(327, 439, 154, 14);
		frmHome.getContentPane().add(lblFriendshipRequestsFrom);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.setToolTipText("sdfsdf");
		btnAccept.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnAccept.setBounds(465, 459, 97, 43);
		frmHome.getContentPane().add(btnAccept);
		
		JButton btnRefuse = new JButton("Refuse");
		
		btnRefuse.setToolTipText("sdfsdf");
		btnRefuse.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnRefuse.setBounds(465, 513, 97, 43);
		frmHome.getContentPane().add(btnRefuse);
		
		listFriendInfo = new List();
		listFriendInfo.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listFriendInfo.setBounds(180, 48, 206, 167);
		frmHome.getContentPane().add(listFriendInfo);
		
		JScrollPane scrollPane = new JScrollPane(textArea_FriendsPosts);
		scrollPane.setBounds(32, 264, 560, 111);
		frmHome.getContentPane().add(scrollPane);
		
		
		
		for(String connectedUser : connectedUsers)
		{
			listConnectedPeople.add(connectedUser);
		}
		
		for(String friend : friends)
		{
			listFriends.add(friend);
		}
		
		for(String pendingFriend : pendingFriends)
		{
			listRequestsFrom.add(pendingFriend);
		}
		
		for(String post : posts)
		{
			
			textArea_FriendsPosts.setText(textArea_FriendsPosts.getText() + post + "\n");
		}
		
		/*
		 * ACTIONS
		 */
		
		//Send friend request
		btnRequestFriendship.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				// If name is selected
				if(listConnectedPeople.getSelectedIndex() > -1)
				{
					// If not already in friends list
					if(!Arrays.asList(listFriends.getItems()).contains(listConnectedPeople.getSelectedItem()))
					{
						String message = Utils.removeEscapedChars("FRIEND:REQ=" + username + "," + listConnectedPeople.getSelectedItem());
						NetworkHandler.getNetworkHandler().sendMessage(message);
					}
				}
			}
		});
		
		// Accept friend request
		btnAccept.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				// Check a request is selected
				if(listRequestsFrom.getSelectedIndex() > -1)
				{
					String message = "FRIEND:ACCEPT=" + username + ","+ Utils.removeEscapedChars(listRequestsFrom.getSelectedItem());
					NetworkHandler.getNetworkHandler().sendMessage(message);
				}
			}
		});
		
		/*
		 * Double click friend profile
		 */
		listFriends.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				NetworkHandler.getNetworkHandler().sendMessage("FRIEND:INFO=" + listFriends.getSelectedItem());
			}
		});
		
		// Decline friend request
		btnRefuse.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				// Check a request is selected
				if(listRequestsFrom.getSelectedIndex() > -1)
				{
					String message = "FRIEND:DECLINE=" + Utils.removeEscapedChars(listRequestsFrom.getSelectedItem()) + "," + username;
					NetworkHandler.getNetworkHandler().sendMessage(message);
					listRequestsFrom.remove(listRequestsFrom.getSelectedItem());
				}
			}
		});
		
		//Post
		btnPost.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				long time = System.currentTimeMillis();
				String post = textFieldPost.getText();
				
				post = post.replaceAll("\\n", "");
				if(!post.isEmpty())
				{
					NetworkHandler.getNetworkHandler().sendMessage("POST:SUBMIT=" + username + "," + time + "," + post);
					textArea_FriendsPosts.setText(textArea_FriendsPosts.getText()
							+ username + " : " + post + "\n");
					
				}
				textFieldPost.setText("");
			}
		});
	}

	@Override
	public void processMessage(String message)
	{
		message = message.substring(5);
		message = Utils.removeEscapedChars(message);
		if(message.startsWith("CONNECTUSER="))
		{
			message = message.substring(12);
			listConnectedPeople.add(message);
		}
		else if(message.startsWith("REMOVEUSER="))
		{
			message = message.substring(11);
			if(Arrays.asList(listConnectedPeople.getItems()).contains(message))
			{
				listConnectedPeople.remove(message);
			}
		}
		else if(message.startsWith("SENTREQ="))
		{
			message = message.substring(8);
		}
		else if(message.startsWith("RECIEVEDREQ="))
		{
			message = message.substring(12);
			listRequestsFrom.add(message);
		}
		else if(message.startsWith("ADDFRIEND="))
		{
			message = message.substring(10);
			if(Arrays.asList(listRequestsFrom.getItems()).contains(message))
			{
				listRequestsFrom.remove(message);
			}
			if(!Arrays.asList(listFriends).contains(message))
			{
				listFriends.add(message);
			}
		}
		else if(message.startsWith("INFO="))
		{
			listFriendInfo.removeAll();
			message = message.substring(5);
			message = Utils.removeEscapedChars(message);
			String[] messageArray = message.split(",");
			for(String info : messageArray)
			{
				listFriendInfo.add(info);
			}
		}
		else if(message.startsWith("POST="))
		{
			message = message.substring(5);
			message = Utils.removeEscapedChars(message);
			String[] messageArray = message.split(",");
			
			textArea_FriendsPosts.setText(textArea_FriendsPosts.getText()
										 + messageArray[0] + " : " + messageArray[2].replaceAll("\\n", "") + "\n");
		}
	}
}
