package com.systems.chatserver.gui;

import java.awt.EventQueue;
import java.awt.List;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui
{
	public static Gui INSTANCE;
	
	private JFrame frmGui;
	private JPanel contentPane;
	
	private List lstConnectedUsers;
	private JButton btnOff;

	/**
	 * Launch the application.
	 */
	public void init() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		frmGui = new JFrame("Chat server");
		frmGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGui.setBounds(100, 100, 362, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmGui.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lstConnectedUsers = new List();
		lstConnectedUsers.setBounds(10, 27, 234, 358);
		contentPane.add(lstConnectedUsers);
		
		JLabel lblConnectedUsers = new JLabel("Connected Users");
		lblConnectedUsers.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectedUsers.setBounds(10, 11, 234, 14);
		contentPane.add(lblConnectedUsers);
		
		btnOff = new JButton("Off");
		btnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnOff.setBounds(247, 38, 89, 23);
		contentPane.add(btnOff);
	}
	
	public void addUser(String username)
	{
		if(!Arrays.asList(lstConnectedUsers.getItems()).contains(username))
		{
			lstConnectedUsers.add(username);
		}
	}
	
	public void removeUser(String username)
	{
		if(Arrays.asList(lstConnectedUsers.getItems()).contains(username))
		{
			lstConnectedUsers.remove(username);
		}
	}
	
	public static Gui getGui()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new Gui();
		}
		return INSTANCE;
	}
}
