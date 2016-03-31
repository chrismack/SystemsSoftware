package com.systems.server.gui;

import java.awt.EventQueue;
import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.systems.server.network.NetworkHandler;
import com.systems.server.sql.SQLHandler;
import java.awt.event.ActionListener;
import java.io.LineNumberInputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Gui 
{
	private static Gui INSTANCE;
	private JFrame frmGUI;
	private JPanel contentPane;
	
	private List lstAllUsers;
	private List lstUserPosts;
	private JButton btnUpdate;

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
					frmGUI.setVisible(true);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		frmGUI = new JFrame("SERVER GUI");
		frmGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGUI.setBounds(100, 100, 551, 522);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmGUI.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lstAllUsers = new List();
		lstAllUsers.setBounds(300, 27, 134, 358);
		contentPane.add(lstAllUsers);
		
		String allUsers = "SELECT username FROM Users";
		ResultSet allUsersRS = SQLHandler.getInstance().eqecuteCommand(allUsers);
		try
		{
			while(allUsersRS.next())
			{
				if(!Arrays.asList(lstAllUsers.getItems()).contains(allUsersRS.getString(1)))
				{
					lstAllUsers.add(allUsersRS.getString(1));
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		JButton btnRemoveUser = new JButton("Remove User");
		btnRemoveUser.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(lstAllUsers.getSelectedIndex() > -1)
				{
					String sqlRemoveUser = "DELETE FROM Users WHERE username = '" + lstAllUsers.getSelectedItem() + "';";
					SQLHandler.getInstance().insertValues(sqlRemoveUser);
					lstAllUsers.remove(lstAllUsers.getSelectedItem());
				}
			}
		});
		btnRemoveUser.setBounds(310, 396, 113, 23);
		contentPane.add(btnRemoveUser);
		
		lstUserPosts = new List();
		lstUserPosts.setBounds(18, 27, 262, 358);
		contentPane.add(lstUserPosts);
		
		JButton btnRemovePost = new JButton("Remove Post");
		btnRemovePost.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(lstUserPosts.getSelectedIndex() > -1)
				{
					String removePostSQL = "DELETE FROM UserPost WHERE ID = '" + lstUserPosts.getSelectedItem().substring(0, lstUserPosts.getSelectedItem().indexOf(" : ")) + "';";
					SQLHandler.getInstance().insertValues(removePostSQL);
					lstUserPosts.remove(lstUserPosts.getSelectedItem());
				}
			}
		});
		
		btnRemovePost.setBounds(81, 396, 113, 23);
		contentPane.add(btnRemovePost);
		
		String sqlAllPosts = "SELECT post, ID FROM UserPost";
		ResultSet allPostsRS = SQLHandler.getInstance().eqecuteCommand(sqlAllPosts);
		
		try
		{
			while (allPostsRS.next())
			{
				lstUserPosts.add(allPostsRS.getString(2) + " : "+ allPostsRS.getString(1));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		JLabel lblPosts = new JLabel("Posts");
		lblPosts.setBounds(18, 11, 46, 14);
		contentPane.add(lblPosts);
		
		JButton btnOff = new JButton("Off");
		btnOff.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				for(Entry<String, Socket> entry : NetworkHandler.getNetwork().connectedUser.entrySet())
				{
					NetworkHandler.getNetwork().sendMessage("DISCONNECT:", entry.getValue());
				}
				System.exit(0);
			}
		});
		btnOff.setBounds(440, 31, 89, 23);
		contentPane.add(btnOff);
		
		btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				lstUserPosts.removeAll();
				String sqlAllPosts = "SELECT post, ID FROM UserPost";
				ResultSet allPostsRS = SQLHandler.getInstance().eqecuteCommand(sqlAllPosts);
				
				try
				{
					while (allPostsRS.next())
					{
						lstUserPosts.add(allPostsRS.getString(2) + " : "+ allPostsRS.getString(1));
					}
				
					lstAllUsers.removeAll();
					String allUsers = "SELECT username FROM Users";
					ResultSet allUsersRS = SQLHandler.getInstance().eqecuteCommand(allUsers);
					while(allUsersRS.next())
					{
						if(!Arrays.asList(lstAllUsers.getItems()).contains(allUsersRS.getString(1)))
						{
							lstAllUsers.add(allUsersRS.getString(1));
						}
					}
				}
				catch (SQLException ew)
				{
					ew.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(440, 82, 89, 23);
		contentPane.add(btnUpdate);
	}
	
	public static Gui getGui()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new Gui();
		}
		return INSTANCE;
	}
	
	public void addConnectedUser(String user)
	{
		if(!Arrays.asList(this.lstAllUsers.getItems()).contains(user))
		{
			this.lstAllUsers.add(user);
		}
	}
	
	public void removeConnectedUser(String user)
	{
		if(Arrays.asList(this.lstAllUsers.getItems()).contains(user))
		{
			this.lstAllUsers.remove(user);
		}
	}
	
}
