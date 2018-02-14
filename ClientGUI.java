import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientGUI extends JFrame implements ActionListener  {

	private JPanel contentPane;

	private JLabel label;
	
	private JTextField textfield;
	
	private JTextField serverfield, tfPort;
	
	private JButton login, logout, whoIsIn, SendButton;

	private JTextArea chatroom;
	private JTextField chatfield;
	private boolean connected;
	private int defaultPort;
	private String defaultHost;
	
	private Client client;

	

	public ClientGUI(String host, int port) {
		
		super("VIA Instant Messaging");
		defaultPort = port;
		defaultHost = host;
	
		

		setBounds(100, 100, 87, 561);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textfield = new JTextField();
		textfield = new JTextField("Anonymous");
		textfield.setBounds(133, 11, 536, 20);
		contentPane.add(textfield);
		textfield.setColumns(10);
		
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(39, 14, 70, 14);
		contentPane.add(lblUsername);
		
		
		chatroom = new JTextArea();
		chatroom.setLineWrap(true);
		chatroom.setEditable(false);
		chatroom.setBounds(41, 98, 734, 252);
		contentPane.add(chatroom);
		
		chatfield = new JTextField();
		chatfield.setBounds(39, 375, 630, 77);
		contentPane.add(chatfield);
		chatfield.setColumns(10);
	
		
		
		SendButton = new JButton("Send");
		SendButton.addActionListener(this);
		SendButton.setBounds(697, 375, 78, 77);
		contentPane.add(SendButton);
		
		
		JLabel lblNewLabel_1 = new JLabel("Server Name");
		lblNewLabel_1.setBounds(433, 42, 106, 17);
		contentPane.add(lblNewLabel_1);
		
		
		tfPort = new JTextField("" + port);
		tfPort.setBounds(119, 42, 86, 20);
		contentPane.add(tfPort);
		tfPort.setColumns(10);
		
		serverfield = new JTextField(host);
		serverfield.setBounds(567, 42, 86, 20);
		contentPane.add(serverfield);
		serverfield.setColumns(10);
		
		
		JLabel lblNewLabel_2 = new JLabel("Port");
		lblNewLabel_2.setBounds(246, 42, 70, 14);
		contentPane.add(lblNewLabel_2);
		
		
		tfPort.setBounds(320, 42, 86, 20);
		contentPane.add(tfPort);
		tfPort.setColumns(10);
		
		login = new JButton("Connect");
		login.setBounds(686, 10, 106, 23);
		login.addActionListener(this);
		contentPane.add(login);
		
		logout = new JButton("Disconnect");
		logout.setBounds(686, 41, 106, 23);
		logout.addActionListener(this);
		contentPane.add(logout);
		
		whoIsIn = new JButton("Show Online Users");
		whoIsIn.setBounds(39, 42, 146, 23);
		whoIsIn.addActionListener(this);
		whoIsIn.setEnabled(false);	
		contentPane.add(whoIsIn);

		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(833, 518);
		setVisible(true);
		textfield.requestFocus();
	}
	
	

	void append(String str) {
		chatroom.append(str);
		chatroom.setCaretPosition(chatroom.getText().length() - 2);
	}
	
	void connectionFailed() {
		login.setEnabled(true);
		logout.setEnabled(false);
		whoIsIn.setEnabled(false);
		label.setText("Enter your username below");
		textfield.setText("Anonymous");
		tfPort.setText("" + defaultPort);
		serverfield.setText(defaultHost);
		serverfield.setEditable(false);
		tfPort.setEditable(false);
		textfield.removeActionListener(this);
		connected = false;
	}
		

	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
	
		if(object == logout) {
			client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, "")); 
			return;
		}
	
		if(object == whoIsIn) {
			client.sendMessage(new ChatMessage(ChatMessage.OnlineUsers, ""));				
			return;
		}

	
		if(object == SendButton) {  
			
			client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, chatfield.getText()));				
			chatfield.setText(""); 
			return;
		}
		

		if(object == login) {
			
		
			
			String nameofuser = textfield.getText().trim(); 
			
			if(nameofuser.length() == 0)
				return;
			
			String server = serverfield.getText().trim();
			if(server.length() == 0)
				return;
			
			String portNumber = tfPort.getText().trim();
			if(portNumber.length() == 0)
				return;
			int port = 0;
			try {
				port = Integer.parseInt(portNumber);
			}
			catch(Exception en) {
				return;   
			}

		
			client = new Client(server, port, nameofuser, this); 
			
			if(!client.start()) 
				return;
			textfield.setText("");
	 

			
			
			login.setEnabled(false);
			
			logout.setEnabled(true);
			whoIsIn.setEnabled(true);
			
			serverfield.setEditable(false);
			tfPort.setEditable(false);
			textfield.setText(nameofuser);
			textfield.setEditable(false);


			
		}

}


	public static void main(String[] args) {
		new ClientGUI("localhost", 1500);
	}




}
