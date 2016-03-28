package com.systems.client.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.systems.client.network.INetworkMessage;

public class Home extends GuiScreen implements INetworkMessage
{
	private static String username;
	private JFrame frmHome;
	private JTextField textFieldPost;
	

	/**
	 * Launch the application.
	 */
	public void init()
	{
		username = "Test";
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Home window = new Home();
					window.frmHome.setVisible(true);
					window.frmHome.setTitle("Home: " + username);
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
	public Home()
	{
		INSTANCE = this;
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
		
		List listFriends = new List();
		listFriends.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listFriends.setBounds(32, 48, 122, 167);
		frmHome.getContentPane().add(listFriends);
		
		JTextArea textAreaInformation = new JTextArea();
		textAreaInformation.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		textAreaInformation.setEditable(false);
		textAreaInformation.setBorder(BorderFactory.createLineBorder(Color.gray));
		textAreaInformation.setBounds(180, 48, 200, 167);
		frmHome.getContentPane().add(textAreaInformation);
		
		List listShareSongs = new List();
		listShareSongs.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listShareSongs.setBounds(410, 48, 186, 167);
		frmHome.getContentPane().add(listShareSongs);
		
		JLabel lblFriendsPosts = new JLabel("Friends Posts");
		lblFriendsPosts.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblFriendsPosts.setBounds(32, 239, 84, 14);
		frmHome.getContentPane().add(lblFriendsPosts);
		
		JTextArea textArea_FriendsPosts = new JTextArea();
		textArea_FriendsPosts.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		textArea_FriendsPosts.setEditable(false);
		textArea_FriendsPosts.setBorder(BorderFactory.createLineBorder(Color.gray));
		textArea_FriendsPosts.setBounds(32, 264, 564, 111);
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
		
		List listConnectedPeople = new List();
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
		
		List listRequestsFrom = new List();
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
	}

	@Override
	public void processMessage(String message)
	{
		// TODO Auto-generated method stub
		
	}
}
