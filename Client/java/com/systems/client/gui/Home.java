package com.systems.client.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.systems.client.main.Utils;
import com.systems.client.network.ChatDispatcher;
import com.systems.client.network.INetworkMessage;
import com.systems.client.network.NetworkHandler;

import javazoom.jl.player.advanced.AdvancedPlayer;
import sun.net.NetHooks;
import sun.nio.ch.Net;

public class Home extends GuiScreen implements INetworkMessage
{
	private String username;
	private String[] connectedUsers;
	private String[] friends;
	private String[] pendingFriends;
	private String[] posts;
	private String[] songs;

	private ChatDispatcher chats;
	
	private JFrame frmHome;
	private JTextField textFieldPost;
	private List listConnectedPeople;
	private List listRequestsFrom;
	private List listFriends;
	private List listFriendInfo;
	private JTextArea textArea_FriendsPosts;
	private List listShareSongs;
	private JLabel lblProfilePic;
	private ImageIcon image;
	
	private static Search SEARCHINSTANCE;
	
	private SongPlayer songPlayer;
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
					frmHome.setVisible(true);
					frmHome.setTitle("Home: " + username);
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
	public Home(String username, String[] connectedUsers, String[] friends, String[] pendingFriends, String[] posts, String[] songs)
	{
		INSTANCE = this;
		this.username = username;
		this.connectedUsers = connectedUsers;
		this.friends = friends;
		this.pendingFriends = pendingFriends;
		this.posts = posts;
		this.songs = songs;
		
		chats = new ChatDispatcher(username);
		
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
		
		listFriends = new List();
		
		
		listFriends.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listFriends.setBounds(32, 48, 122, 167);
		frmHome.getContentPane().add(listFriends);
		
		listShareSongs = new List();
		
		listShareSongs.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listShareSongs.setBounds(377, 48, 219, 167);
		frmHome.getContentPane().add(listShareSongs);
		
		JLabel lblFriendsPosts = new JLabel("Friends Posts");
		lblFriendsPosts.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblFriendsPosts.setBounds(32, 239, 84, 14);
		frmHome.getContentPane().add(lblFriendsPosts);
		
		textArea_FriendsPosts = new JTextArea();
		textArea_FriendsPosts.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		textArea_FriendsPosts.setEditable(false);
		textArea_FriendsPosts.setBorder(BorderFactory.createLineBorder(Color.gray));
		textArea_FriendsPosts.setBounds(32, 264, 2, 111);
		frmHome.getContentPane().add(textArea_FriendsPosts);
				
		
		JButton btnUpload = new JButton("Upload");
		btnUpload.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnUpload.setBounds(377, 221, 84, 23);
		frmHome.getContentPane().add(btnUpload);
		
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
		
		listConnectedPeople = new List();
		listConnectedPeople.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listConnectedPeople.setBounds(32, 459, 122, 167);
		frmHome.getContentPane().add(listConnectedPeople);
		
		JButton btnRequestFriendship = new JButton("<html>Friendship <br> Request<html>");
		
		btnRequestFriendship.setToolTipText("sdfsdf");
		btnRequestFriendship.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnRequestFriendship.setBounds(174, 459, 97, 43);
		frmHome.getContentPane().add(btnRequestFriendship);
		
		JButton btnChat = new JButton("Chat");
		btnChat.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnChat.setBounds(174, 513, 97, 43);
		frmHome.getContentPane().add(btnChat);
		
		listRequestsFrom = new List();
		listRequestsFrom.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listRequestsFrom.setBounds(327, 459, 122, 167);
		frmHome.getContentPane().add(listRequestsFrom);
		
		JLabel lblFriendshipRequestsFrom = new JLabel("Friendship Requests From");
		lblFriendshipRequestsFrom.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblFriendshipRequestsFrom.setBounds(327, 439, 154, 14);
		frmHome.getContentPane().add(lblFriendshipRequestsFrom);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnAccept.setBounds(465, 459, 97, 43);
		frmHome.getContentPane().add(btnAccept);
		
		JButton btnRefuse = new JButton("Refuse");
		
		btnRefuse.setToolTipText("sdfsdf");
		btnRefuse.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnRefuse.setBounds(465, 513, 97, 43);
		frmHome.getContentPane().add(btnRefuse);
		
		listFriendInfo = new List();
		listFriendInfo.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		listFriendInfo.setBounds(180, 48, 171, 80);
		frmHome.getContentPane().add(listFriendInfo);
		
		JScrollPane scrollPane = new JScrollPane(textArea_FriendsPosts);
		scrollPane.setBounds(32, 264, 560, 111);
		frmHome.getContentPane().add(scrollPane);
		
		JButton buttonPlay = new JButton(">");
		buttonPlay.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		buttonPlay.setBounds(481, 221, 46, 23);
		frmHome.getContentPane().add(buttonPlay);
		
		JButton buttonPause = new JButton("||");
		buttonPause.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		buttonPause.setBounds(546, 221, 46, 23);
		frmHome.getContentPane().add(buttonPause);

		
		image = new ImageIcon("default.png");
		lblProfilePic = new JLabel("", image, JLabel.CENTER);
		lblProfilePic.setVisible(false);
		lblProfilePic.setBounds(180, 135, 80, 80);
		frmHome.getContentPane().add(lblProfilePic);
		
		JButton btnLogOff = new JButton("LOG OFF");
		
		btnLogOff.setBounds(515, 661, 89, 23);
		frmHome.getContentPane().add(btnLogOff);
		
		JButton btnSearch = new JButton("Search");
		
		btnSearch.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnSearch.setBounds(174, 577, 97, 43);
		frmHome.getContentPane().add(btnSearch);
		
//		JLabel label = new JLabel("", image, JLabel.CENTER);
//		JPanel panel = new JPanel(new BorderLayout());
//		panel.add( label, BorderLayout.CENTER );
		
		
		
		
		for(String connectedUser : connectedUsers)
		{
			listConnectedPeople.add(connectedUser);
		}
		
		for(String friend : friends)
		{
			listFriends.add(friend);
		}
		
		for(String pendingFriend : pendingFriends)
		{
			listRequestsFrom.add(pendingFriend);
		}
		
		for(String post : posts)
		{
			
			textArea_FriendsPosts.setText(textArea_FriendsPosts.getText() + post + "\n");
		}
		
		for(String song : songs)
		{
			listShareSongs.add(song);
		}
		
		/*
		 * ACTIONS
		 */
		
		//Close window
		frmHome.addWindowListener(new WindowAdapter() 
		{
		    @Override
		    public void windowClosing(WindowEvent windowEvent) 
		    {
		    	chats.closeAllChats();
		    }
		});
		
		//Send friend request
		btnRequestFriendship.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				// If name is selected
				if(listConnectedPeople.getSelectedIndex() > -1)
				{
					// If not already in friends list
					if(!Arrays.asList(listFriends.getItems()).contains(listConnectedPeople.getSelectedItem()))
					{
						String message = Utils.removeEscapedChars("FRIEND:REQ=" + username + "," + listConnectedPeople.getSelectedItem());
						NetworkHandler.getNetworkHandler().sendMessage(message);
						NetworkHandler.getNetworkHandler().sendMessage("SONG:GET=" + username);
					}
				}
			}
		});
		
		// Accept friend request
		btnAccept.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				// Check a request is selected
				if(listRequestsFrom.getSelectedIndex() > -1)
				{
					String message = "FRIEND:ACCEPT=" + username + ","+ Utils.removeEscapedChars(listRequestsFrom.getSelectedItem());
					NetworkHandler.getNetworkHandler().sendMessage(message);
				}
				
			}
		});
		
		/*
		 * Double click friend profile
		 */
		listFriends.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(listFriends.getSelectedIndex() > -1)
				{
					NetworkHandler.getNetworkHandler().sendMessage("FRIEND:INFO=" + listFriends.getSelectedItem());
					File imgDir = new File(System.getProperty("user.dir") + "/img/");
					if(!imgDir.exists())
						imgDir.mkdirs();
					
					File profilePic = new File(imgDir + File.separator + listFriends.getSelectedItem());
					if(!profilePic.exists())
					{
						NetworkHandler.getNetworkHandler().sendMessage("PIC:GET=" + listFriends.getSelectedItem());
					}
					else
					{
						displayNewProfilePic(profilePic);
					}
				}
			}
		});
		
		// Decline friend request
		btnRefuse.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				// Check a request is selected
				if(listRequestsFrom.getSelectedIndex() > -1)
				{
					String message = "FRIEND:DECLINE=" + Utils.removeEscapedChars(listRequestsFrom.getSelectedItem()) + "," + username;
					NetworkHandler.getNetworkHandler().sendMessage(message);
					listRequestsFrom.remove(listRequestsFrom.getSelectedItem());
				}
			}
		});
		
		//Post
		btnPost.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				long time = System.currentTimeMillis();
				String post = textFieldPost.getText();
				
				post = post.replaceAll("\\n", "");
				if(!post.isEmpty())
				{
					NetworkHandler.getNetworkHandler().sendMessage("POST:SUBMIT=" + username + "," + time + "," + post);
					textArea_FriendsPosts.setText(textArea_FriendsPosts.getText()
							+ username + " : " + post + "\n");
					
				}
				textFieldPost.setText("");
			}
		});
		
		// Song selected
		listShareSongs.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(listShareSongs.getSelectedIndex() > -1)
				{
					File songDir = new File(System.getProperty("user.dir") + "/songs/");
					if(!songDir.exists())
						songDir.mkdirs();
					
					File fileCheck = new File(songDir + File.separator + listShareSongs.getSelectedItem());
					if(!fileCheck.exists())
					{
						NetworkHandler.getNetworkHandler().sendMessage("SONG:LISTEN=" + listShareSongs.getSelectedItem().toString());
					}
					else
					{
						startSong(fileCheck.getAbsolutePath());
					}
				}
			}
		});
		
		// Upload song
		btnUpload.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				File file = null;
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION)
				{
					file = fileChooser.getSelectedFile();
				};
				
				if(file != null && file.getName().endsWith(".mp3"))
				{
					
					// Get the size of the file
					long length = (file.length());
					//Send inital header information
					NetworkHandler.getNetworkHandler().sendMessage("SONG:UPLOAD=" + username + "," + file.getName() + "," + length);
					
					try
					{
						int count;
						byte[] bytes = new byte[(int) length];
						InputStream in = new FileInputStream(file);
						
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
					listShareSongs.add(file.getName());
				}
			}
		});

		
		//Play Button
		buttonPlay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(listShareSongs.getSelectedIndex() > -1)
				{
					File songDir = new File(System.getProperty("user.dir") + "/songs/");
					if(!songDir.exists())
						songDir.mkdirs();
					
					File fileCheck = new File(songDir + File.separator + listShareSongs.getSelectedItem());
					if(!fileCheck.exists())
					{
						NetworkHandler.getNetworkHandler().sendMessage("SONG:LISTEN=" + listShareSongs.getSelectedItem().toString());
					}
					else
					{
						startSong(fileCheck.getAbsolutePath());
					}
				}
			}
		});
		
		//Stop button
		buttonPause.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				stopSong();
			}
		});
		
		// LOGOFF
		btnLogOff.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				NetworkHandler.getNetworkHandler().sendMessage("LOGOFF:");
				Login login = new Login();
				login.init();
				frmHome.setVisible(false);
				frmHome.dispose();
			}
		});
		
		// Open Chat
		btnChat.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(listConnectedPeople.getSelectedIndex() > -1)
				{
					chats.openChat(listConnectedPeople.getSelectedItem());
				}
			}
		});
		
		btnSearch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(SEARCHINSTANCE == null)
				{
					Search search = new Search(username);
					search.init();
					SEARCHINSTANCE = search;
				}
			}
		});
	}

	@Override
	public void processMessage(String message)
	{
		message = message.substring(5);
		message = Utils.removeEscapedChars(message);
		if(message.startsWith("CONNECTUSER="))
		{
			message = message.substring(12);
			listConnectedPeople.add(message);
		}
		else if(message.startsWith("REMOVEUSER="))
		{
			message = message.substring(11);
			if(Arrays.asList(listConnectedPeople.getItems()).contains(message))
			{
				listConnectedPeople.remove(message);
			}
		}
		else if(message.startsWith("SENTREQ="))
		{
			message = message.substring(8);
		}
		else if(message.startsWith("RECIEVEDREQ="))
		{
			message = message.substring(12);
			listRequestsFrom.add(message);
		}
		else if(message.startsWith("ADDFRIEND="))
		{
			message = message.substring(10);
			if(Arrays.asList(listRequestsFrom.getItems()).contains(message))
			{
				listRequestsFrom.remove(message);
			}
			if(!Arrays.asList(listFriends).contains(message))
			{
				listFriends.add(message);
			}
			 JOptionPane.showMessageDialog(null, message + " has added you!! YAY", "New friend", JOptionPane.INFORMATION_MESSAGE);
			 NetworkHandler.getNetworkHandler().sendMessage("SONG:GET=" + username);
		}
		else if(message.startsWith("INFO="))
		{
			listFriendInfo.removeAll();
			message = message.substring(5);
			message = Utils.removeEscapedChars(message);
			String[] messageArray = message.split(",");
			for(String info : messageArray)
			{
				listFriendInfo.add(info);
			}
		}
		else if(message.startsWith("INFOIMG="))
		{
			message = message.substring(8);
			message = Utils.removeEscapedChars(message);
			if(message.equals("SERVER-NOPROFILEPIC"))
			{
				displayNewProfilePic(Registration.class.getResourceAsStream("/default.png"));
			}
			else
			{
				String[] imgInfo = message.split(",");
				
				File imgDir = new File(System.getProperty("user.dir") + "/img/");
				if(!imgDir.exists())
					imgDir.mkdirs();
				
				writeFile(imgDir + File.separator + imgInfo[0], Long.parseLong(imgInfo[1]), NetworkHandler.getNetworkHandler().getServerSocket());
				
				File profilePic = new File(imgDir + "/" + imgInfo[0]);
				displayNewProfilePic(profilePic);
			}
		}
		else if(message.startsWith("POST="))
		{
			message = message.substring(5);
			message = Utils.removeEscapedChars(message);
			String[] messageArray = message.split(",");
			
			textArea_FriendsPosts.setText(textArea_FriendsPosts.getText()
										 + messageArray[0] + " : " + messageArray[2].replaceAll("\\n", "") + "\n");
		}
		else if(message.startsWith("PLAY="))
		{
			message = message.substring(5);
			message = Utils.removeEscapedChars(message);
			String[] messageArray = message.split(",");
			
			File songsDir = new File(System.getProperty("user.dir") + "/songs/");
			if(!songsDir.exists())
				songsDir.mkdirs();
			
			writeFile(songsDir + File.separator + messageArray[0], Long.parseLong(messageArray[1]), NetworkHandler.getNetworkHandler().getServerSocket());
			startSong(songsDir + File.separator + messageArray[0]);
		}
		else if(message.startsWith("NEWSONG="))
		{
			message = message.substring(8);
			message = Utils.removeEscapedChars(message);
			listShareSongs.add(message);
			
		}
		else if(message.startsWith("SONGS="))
		{
			message =  message.substring(6);
			message = Utils.removeEscapedChars(message);
			
			if(!message.equals(""))
			{
				if(message.contains(","))
				{
					String[] messageArray = message.split(",");
					for(String song : messageArray)
					{
						if(!Arrays.asList(listShareSongs.getItems()).contains(song))
						{
							listShareSongs.add(song);
						}
					}
				}
				else
				{
					if(!Arrays.asList(listShareSongs.getItems()).contains(message))
					{
						listShareSongs.add(message);
					}
				}
			}
		}
	}
	
	private void displayNewProfilePic(InputStream resourceAsStream)
	{
		if(image != null)
		{
			image.getImage().flush();
			image = null; 
		}
		
		try
		{
			image = new ImageIcon(ImageIO.read(resourceAsStream));
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		lblProfilePic.setIcon(image);
		lblProfilePic.setVisible(true);
	}

	private void displayNewProfilePic(File file)
	{
		if(image != null)
		{
			image.getImage().flush();
			image = null; 
		}
		image = new ImageIcon(file.getAbsolutePath());
		lblProfilePic.setIcon(image);
		lblProfilePic.setVisible(true);
	}
	
	private void writeFile(String fileName, long fileSize, Socket socket)
	{
		int count = 0;
		int countedBytes = 0;
		byte[] bytes = new byte[10];
		FileOutputStream inF;
		try
		{
			BufferedInputStream	bis = new BufferedInputStream(socket.getInputStream());
			inF = new FileOutputStream(new File(fileName));
			while ((count = bis.read(bytes)) > 0)
			{
				countedBytes += count;	// The number of bytes that have been sent over the socket
				inF.write(bytes, 0, count);		// Write the bytes into the file
				// If all the expected bytes have been sent
				if(countedBytes >= fileSize)
				{
					inF.flush();
					inF.close();
					break;				// Stop listening to the socket as the file has been sent
				}						// Listening will resume in the network listener
			}
			
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void startSong(String file)
	{
		if(this.songPlayer != null)
		{
			songPlayer.end();
			songPlayer = null;
		}
		songPlayer = new SongPlayer(file);
		songPlayer.start();
	}
	
	private void stopSong()
	{
		if(this.songPlayer != null)
		{
			songPlayer.end();
			songPlayer = null;
		}
	}

	private class SongPlayer extends Thread
	{
		private String fileName;
		private AdvancedPlayer player;
		
		public SongPlayer(String fileName)
		{
			this.fileName = fileName;
		}
		
		public void end()
		{
			this.player.close();
		}
		
		@Override
		public void run()
		{
			try
			{
				File file = new File(fileName);
				FileInputStream fis = new FileInputStream(file);
				player = new AdvancedPlayer(fis);
				player.play();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	@Override
	public void close()
	{
		if(songPlayer != null)
			songPlayer.end();
		frmHome.setVisible(false);
		frmHome.dispose();
	}
	
	public static Search getSearchInstance() throws NullPointerException
	{
		if(SEARCHINSTANCE == null)
		{
			throw new NullPointerException();
		}
		return SEARCHINSTANCE;
	}
	
	public static void makeSearchNull()
	{
		if(SEARCHINSTANCE != null)
		{
			SEARCHINSTANCE = null;
		}
	}
}
