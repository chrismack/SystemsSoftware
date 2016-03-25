package com.systems.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.systems.client.network.NetworkHandler;

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
import javax.swing.JPasswordField;

public class Registration
{
	private JFrame frmReg;
	private JPanel contentPane;
	private JTextField textFieldUserName;
	private JTextField textFieldPlaceOfBirth;
	private JTextField textFieldDateOfBirth;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirm;

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
					frmReg.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Registration()
	{
		initialize();
	}
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public void initialize()
	{
		frmReg = new JFrame();
		frmReg.setBackground(Color.GRAY);
		frmReg.setResizable(false);
		frmReg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReg.setBounds(100, 100, 700, 350);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmReg.setContentPane(contentPane);
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
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblPassword.setBounds(48, 174, 62, 14);
		contentPane.add(lblPassword);
		
		JLabel lblReenterPassword = new JLabel("reEnter Password");
		lblReenterPassword.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblReenterPassword.setBounds(10, 199, 100, 14);
		contentPane.add(lblReenterPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(132, 171, 86, 20);
		contentPane.add(passwordField);
		
		passwordFieldConfirm = new JPasswordField();
		passwordFieldConfirm.setBounds(132, 196, 86, 20);
		contentPane.add(passwordFieldConfirm);
		
		// =========================================== //
		// 					Actions					   //
		// =========================================== //
		
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//if any of the fields are empty
				if(listMusicPreferences.getItemCount()      < 1 || textFieldUserName.getText().length()     < 1 ||
				   textFieldPlaceOfBirth.getText().length() < 1 || textFieldDateOfBirth.getText().length()  < 1)
				{
					System.out.println("sending reg message");
					NetworkHandler.getNetworkHandler().sendMessage("REG:HELLOTHERE");
				}
				else // Everything is valid send reg information
				{
					
					System.out.println("sending reg message");
					NetworkHandler.getNetworkHandler().sendMessage("REG:HELLOTHERE");
				}
			}
		});
		
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
				frmReg.dispose();
			}
		});
	}
}
