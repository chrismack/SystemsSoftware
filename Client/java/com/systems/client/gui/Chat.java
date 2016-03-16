package com.systems.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import java.awt.Color;
import java.awt.List;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat extends JFrame {

	private JFrame frmChat;
	private JTextField txtMessage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat frame = new Chat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Chat() {
		frmChat = new JFrame();
		frmChat.getContentPane().setBackground(Color.WHITE);
		frmChat.setTitle("Home : ");
		frmChat.setBackground(Color.WHITE);
		frmChat.setResizable(false);
		frmChat.setBounds(100, 100, 620, 724);
		frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChat.getContentPane().setLayout(null);
		
		this.setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		
		List listFriends = new List();
		listFriends.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listFriends.setBounds(10, 37, 145, 315);
		getContentPane().add(listFriends);
		
		JLabel lblFriends = new JLabel("Friends");
		lblFriends.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFriends.setBounds(10, 17, 145, 14);
		getContentPane().add(lblFriends);
		
		JTextArea textArea_chatMessages = new JTextArea();
		textArea_chatMessages.setBorder(BorderFactory.createLineBorder(Color.gray));
		textArea_chatMessages.setBounds(165, 14, 382, 291);
		getContentPane().add(textArea_chatMessages);
		
		txtMessage = new JTextField();
		txtMessage.setBounds(160, 316, 387, 35);
		getContentPane().add(txtMessage);
		txtMessage.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 401);

	}
}
