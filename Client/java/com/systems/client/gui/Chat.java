package com.systems.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.systems.client.network.ChatDispatcher;

public class Chat
{
	
	private JFrame frmChat;
	private JPanel contentPane;
	private JTextField textFieldChat;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	
	private String un;   // This is our name
	private String username;
	private ChatDispatcher dispatcher;
	
	private boolean stillConnected = true;

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
					//Chat frame = new Chat();
					frmChat.setVisible(true);
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
	public Chat(String us, String user, ChatDispatcher chatDispatcher)
	{
		this.un = us;
		this.username = user;
		this.dispatcher = chatDispatcher;
		
		frmChat = new JFrame();
		frmChat.setTitle("Chat : " + user);
		frmChat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmChat.setBounds(100, 100, 635, 350);
		frmChat.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frmChat.setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textFieldChat = new JTextField();
		textFieldChat.setBounds(10, 278, 500, 20);
		panel.add(textFieldChat);
		textFieldChat.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Calibri Light", Font.PLAIN, 15));
		textArea.setBounds(605, 0, 4, 22);
		textArea.setEditable(false);
		panel.add(textArea);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(0, 0, 609, 271);
		panel.add(scrollPane);

		JButton btnSend = new JButton("Send");
		btnSend.setBounds(520, 277, 89, 23);
		panel.add(btnSend);
		btnSend.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(textFieldChat.getText() != "")
				{
					textArea.setText(textArea.getText() + un + ":" + textFieldChat.getText() + "\n");
					dispatcher.writeMessage(username + ":" + textFieldChat.getText());
					textFieldChat.setText("");
				}
			}
		});
		
		frmChat.addWindowListener(new WindowAdapter() 
		{
		    @Override
		    public void windowClosing(WindowEvent windowEvent) 
		    {
		    	if(stillConnected == true)
		    	{
		    		dispatcher.writeMessage(username + ":" + "I HAVE CLOSED THE CHAT");
		    	}
		    	dispatcher.closeChat(username);
		    }
		});
		
	}
	
	/*
	 * Focus the window and bring to the from
	 */
	public void toFront()
	{
		frmChat.toFront();
	}
	
	/*
	 * writes messages to the screen if user has disconnected will notify that client that other client isn't connected
	 */
	public void newMessage(String message)
	{
		if(message.contains("I HAVE CLOSED THE CHAT"))
		{
			stillConnected = false;
		}
		textArea.setText(textArea.getText() + message + "\n");
	}
}
