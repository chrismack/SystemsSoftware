package com.systems.client.gui;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.systems.client.network.INetworkMessage;
import com.systems.client.network.NetworkHandler;

public class Registration extends GuiScreen implements INetworkMessage
{
	private JFrame frmReg;
	private JPanel contentPane;
	private JTextField textFieldUserName;
	private JTextField textFieldPlaceOfBirth;
	private JTextField textFieldDateOfBirth;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirm;
	private JLabel lblError;
	private JLabel lblProfilePic;
	private ImageIcon image;
	private String imageLoc;
	private long imageSize;
	private File profilePic;
	private String gUsername;
	

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
		INSTANCE = this;
		initialize();
	}
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public void initialize()
	{
		frmReg = new JFrame();
		frmReg.setResizable(false);
		frmReg.setBackground(Color.GRAY);
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
		
		lblError = new JLabel("");
		lblError.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblError.setForeground(Color.RED);
		lblError.setBounds(29, 11, 250, 14);
		contentPane.add(lblError);
		
		JButton btnAddProfilePic = new JButton("Add Profile Pic");
		
		btnAddProfilePic.setBounds(321, 257, 137, 23);
		contentPane.add(btnAddProfilePic);
		
		
		File defaultPic = new File("/default.png");
		URL url = Registration.class.getClass().getResource("default.png");
		InputStream in = Registration.class.getResourceAsStream("/default.png");
		try
		{
			image = new ImageIcon(ImageIO.read(in));
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		lblProfilePic = new JLabel("", image, JLabel.CENTER);
		lblProfilePic.setBounds(470, 207, 80, 80);
		contentPane.add(lblProfilePic);
		
		// =========================================== //
		// 					Actions					   //
		// =========================================== //
		
		btnRegister.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String username 	= textFieldUserName.getText();
				String dob 			= textFieldDateOfBirth.getText();
				String placeOfBirth = textFieldPlaceOfBirth.getText();
				
				String passText = new String(passwordField.getPassword());
				String passTextConf = new String(passwordFieldConfirm.getPassword());
				
				String likedMusic = "";
				
				for(String music : listMusicPreferences.getItems())
				{
					likedMusic += music + ",";
				}
				
				//if any of the fields are empty
				if(listMusicPreferences.getItemCount()      < 1 || username.length()     < 1 ||
				   placeOfBirth.length() < 1 || dob.length()  < 1)
				{
					// Add message saying invalid info has been entered
					lblError.setText("Please enter value in all fields");
				}
				else if(!(passText.equals(passTextConf)))
				{
					lblError.setText("Passwords do not match");
				}
				else // Everything is valid send reg information
				{
					
					System.out.println("sending reg message");
					
					NetworkHandler.getNetworkHandler().sendMessage("REG:" + username + "|" 
																		  + passText + "|" 
																		  + dob + "|" 
																		  + placeOfBirth + "|"
																		  + likedMusic);
				
					gUsername = username;
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
				showLogin();
			}
		});
		
		btnAddProfilePic.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION)
				{
					profilePic = fileChooser.getSelectedFile();
				};
				
				if(profilePic != null && profilePic.getName().endsWith(".png"))
				{
					image.getImage().flush();
					image = new ImageIcon(profilePic.getAbsolutePath());
					lblProfilePic.setIcon(image);
					imageLoc = profilePic.getAbsolutePath();
					imageSize = profilePic.length();
				}
			}
		});
		
	}


	@Override
	public void processMessage(String message)
	{
		message = message.substring(4);
		
		if(message.startsWith("SUCCESS"))
		{
			if(this.profilePic != null)
			{
				NetworkHandler.getNetworkHandler().sendMessage("PIC:NEW=" +gUsername + ","+ imageLoc + "," + String.valueOf(imageSize));
				
				try
				{
					int count;
					byte[] bytes = new byte[(int) imageSize];
					InputStream in = new FileInputStream(profilePic);
					
					// Send the file to the server
					while ((count = in.read(bytes)) > 0)
					{
						NetworkHandler.getNetworkHandler().sendBytes(bytes);
					}
					in.close();
				}
				catch (Exception ee)
				{
					ee.printStackTrace();
				}
				
			}
			showLogin();
		}
		else
		{
			// set a lable to say login failed
			System.out.println("reg failed");
			lblError.setText("Registration Failed");
		}
	}
	
	private void showLogin()
	{
		Login login = new Login();
		login.init();
		frmReg.setVisible(false);
		frmReg.dispose();
	}
	
	@Override
	public void close()
	{
		frmReg.setVisible(false);
		frmReg.dispose();
	}
}
