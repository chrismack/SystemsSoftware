package com.systems.client.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.systems.client.main.Utils;
import com.systems.client.network.INetworkMessage;
import com.systems.client.network.NetworkHandler;

public class Login extends GuiScreen implements INetworkMessage
{
	private JFrame frmLogin;
	private JPasswordField passwordField;
	private JTextField txtUsername;
	private JLabel lblError;
	
	private String[] connectedUsers = {};
	private String[] friends = {};
	private String[] pendingFriends = {};
	private String[] posts = {};
	private String[] songs = {};

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
					//Login window = new Login();
					frmLogin.setVisible(true);
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
	public Login()
	{
		INSTANCE = this;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmLogin = new JFrame();
		frmLogin.setResizable(false);
		frmLogin.setTitle("Login");
		frmLogin.getContentPane().setBackground(Color.WHITE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblUsername.setBounds(26, 35, 63, 14);
		frmLogin.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblPassword.setBounds(26, 69, 63, 14);
		frmLogin.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(107, 66, 119, 20);
		frmLogin.getContentPane().add(passwordField);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(107, 32, 119, 20);
		frmLogin.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		
		btnLogin.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnLogin.setBounds(300, 65, 89, 23);
		frmLogin.getContentPane().add(btnLogin);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(281, 32, 186, 14);
		frmLogin.getContentPane().add(lblError);
		frmLogin.setBackground(Color.WHITE);
		frmLogin.setBounds(100, 100, 500, 160);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// =========
		//  ACTIONS
		// =========
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				// If fields have text entered
				if(!txtUsername.getText().equals("") || passwordField.getPassword().equals(""))
				{
					String passText = new String(passwordField.getPassword());
					NetworkHandler.getNetworkHandler().sendMessage("LOGIN:" + txtUsername.getText() + "|" + passText);
				}
				else
				{
					lblError.setText("Could not log in please try again");
					// Add message saying invalid info entered
				}
			}
		});
		
	}

	@Override
	public void processMessage(String netMessage)
	{
		String[] messageArray = netMessage.split("\n");
		for(String message : messageArray)
		{
			message = message.substring(6);
			message = Utils.removeEscapedChars(message);
			if (message.startsWith("FAIL"))
			{
				lblError.setText("Could not log in please try again");
			}
			else if (message.startsWith("CONNECTED="))
			{
				message = message.substring(10);
				message = Utils.removeEscapedChars(message);
				connectedUsers = message.split(",");
			}
			else if(message.startsWith("FRIENDS="))
			{
				message = message.substring(8);
				message = Utils.removeEscapedChars(message);
				friends = message.split(",");
			}
			else if(message.startsWith("PENDING="))
			{
				message = message.substring(8);
				message = Utils.removeEscapedChars(message);
				pendingFriends = message.split(",");
			}
			else if(message.startsWith("POSTS="))
			{
				message = message.substring(6);
				message = Utils.removeEscapedChars(message);
				message = message.replaceAll("[|]", " : ");
				posts = message.split(",");
			}
			else if(message.startsWith("SONGS="))
			{
				message = message.substring(6);
				message = Utils.removeEscapedChars(message);
				songs = message.split(",");
			}
			else if(message.startsWith("SUCCESS"))
			{
				Home home = new Home(txtUsername.getText(), connectedUsers, friends, pendingFriends, posts, songs);
				home.init();
				frmLogin.setVisible(false);
				frmLogin.dispose();
			}
		}
	}
}
