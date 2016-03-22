package com.systems.chatserver.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToggleButton;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Gui extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
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
	public Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 678, 522);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JToggleButton tglbtnToggleServer = new JToggleButton("ON/OFF");
		tglbtnToggleServer.setBounds(578, 27, 77, 77);
		contentPane.add(tglbtnToggleServer);
		
		JList lstConnectedUsers = new JList();
		lstConnectedUsers.setBounds(10, 27, 234, 358);
		contentPane.add(lstConnectedUsers);
		
		JList lstAllUsers = new JList();
		lstAllUsers.setBounds(353, 27, 215, 358);
		contentPane.add(lstAllUsers);
		
		JButton btnRemoveUser = new JButton("Remove User");
		btnRemoveUser.setBounds(411, 396, 113, 23);
		contentPane.add(btnRemoveUser);
		
		JLabel lblConnectedUsers = new JLabel("Connected Users");
		lblConnectedUsers.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectedUsers.setBounds(10, 11, 234, 14);
		contentPane.add(lblConnectedUsers);
		
		JLabel lblAllUsers = new JLabel("All Users");
		lblAllUsers.setHorizontalAlignment(SwingConstants.CENTER);
		lblAllUsers.setBounds(353, 11, 215, 14);
		contentPane.add(lblAllUsers);
		
		JButton btnDisconnectUser = new JButton("Disconnect User");
		btnDisconnectUser.setBounds(69, 396, 113, 23);
		contentPane.add(btnDisconnectUser);
	}
}
