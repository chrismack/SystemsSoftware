package com.systems.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Choice;
import java.awt.Button;
import java.awt.List;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class Registration extends JFrame
{

	private JPanel contentPane;
	private JTextField textFieldUserName;
	private JTextField textFieldPlaceOfBirth;
	private JTextField textFieldDateOfBirth;

	/**
	 * Launch the application.
	 */
	public static void init()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Registration frame = new Registration();
					frame.setVisible(true);
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
	public Registration()
	{
		setBackground(Color.GRAY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 350);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblUsername.setBounds(48, 46, 62, 14);
		contentPane.add(lblUsername);
		
		final JLabel lblPlaceOfBirth = new JLabel("Place of Birth");
		lblPlaceOfBirth.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblPlaceOfBirth.setBounds(29, 87, 82, 14);
		contentPane.add(lblPlaceOfBirth);
		
		final JLabel lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblDateOfBirth.setBounds(28, 124, 82, 14);
		contentPane.add(lblDateOfBirth);
		
		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(132, 43, 86, 20);
		contentPane.add(textFieldUserName);
		textFieldUserName.setColumns(10);
		
		textFieldPlaceOfBirth = new JTextField();
		textFieldPlaceOfBirth.setColumns(10);
		textFieldPlaceOfBirth.setBounds(132, 84, 86, 20);
		contentPane.add(textFieldPlaceOfBirth);
		
		textFieldDateOfBirth = new JTextField();
		textFieldDateOfBirth.setColumns(10);
		textFieldDateOfBirth.setBounds(132, 121, 86, 20);
		contentPane.add(textFieldDateOfBirth);
		
		final JLabel lblMusicProfile = new JLabel("Music Profile");
		lblMusicProfile.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblMusicProfile.setBounds(321, 11, 82, 14);
		contentPane.add(lblMusicProfile);
		
		final Choice choiceAdd = new Choice();
		choiceAdd.add("Blues");
		choiceAdd.add("Classical");
		choiceAdd.add("Country");
		choiceAdd.add("Electronic");
		choiceAdd.add("Folk");
		choiceAdd.add("Jazz");
		choiceAdd.add("New Age");
		choiceAdd.add("Reggae");
		choiceAdd.add("Rock");
		choiceAdd.add("Pop");
		choiceAdd.add("Hip-Hop");
		choiceAdd.setBounds(321, 46, 152, 20);
		contentPane.add(choiceAdd);
		
		final Button buttonAdd = new Button("Add");
		buttonAdd.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		buttonAdd.setBounds(501, 46, 70, 22);
		contentPane.add(buttonAdd);
		
		final List listMusicPreferences = new List();
		listMusicPreferences.setBounds(321, 87, 250, 114);
		contentPane.add(listMusicPreferences);
		
		final JButton btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnRegister.setBounds(29, 257, 89, 23);
		contentPane.add(btnRegister);
		
		final JLabel lblOrCancel = new JLabel("or");
		lblOrCancel.setFont(new Font("Calibri Light", Font.PLAIN, 11));
		lblOrCancel.setBounds(130, 261, 15, 14);
		contentPane.add(lblOrCancel);
		
		final JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnLogin.setBounds(155, 257, 89, 23);
		contentPane.add(btnLogin);
		
		/*
		 * Actions
		 */
		
		/*
		 * Add item to music list 
		 */
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(!Arrays.asList(listMusicPreferences.getItems()).contains(choiceAdd.getSelectedItem()))
				{
					listMusicPreferences.add(choiceAdd.getSelectedItem().toString());
				}
			}
		});
		
		/*
		 * Show login panel
		 */
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Login login = new Login();
				login.init();
				dispose();
			}
		});
	}
}
