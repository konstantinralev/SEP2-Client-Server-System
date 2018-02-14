import java.net.*;
import java.io.*;
import java.util.*;


public class Client  {


	private ObjectInputStream input;		
	private ObjectOutputStream output;		
	private Socket socket;

	
	private ClientGUI gui;
	

	private String server, username;
	private int port;



	Client(String server, int port, String username, ClientGUI cg) {
		this.server = server;
		this.port = port;
		this.username = username;
	
		this.gui = cg;
	}
	

	public boolean start() {

		try {
			socket = new Socket(server, port);
		} 
	
		catch(Exception ef) {
			display("Error connectiong to server:" + ef);
			return false;
		}
		
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
	
	
		try
		{
			input  = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}


		new ServerListener().start();
		
		try
		{
			output.writeObject(username);
		}
		catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
	
		return true;
	}


	private void display(String msg) {
	
			gui.append(msg + "\n");		
	}
	
	
	void sendMessage(ChatMessage msg) {
		try {
			output.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}


	private void disconnect() {
		try { 
			if(input != null) 
				input.close();
		}
		catch(Exception e) {} 
		try {
			
			if(output != null) 
				output.close();
		}
		catch(Exception e) {} 
        try{
        	
			if(socket != null) 
				socket.close();
		}
		catch(Exception e) {} 
		
		
		if(gui != null)
			gui.connectionFailed();
			
	}


	class ServerListener extends Thread {

		public void run() {
			while(true) {
				try {
					String msg = (String) input.readObject();
			
						gui.append(msg);
				}
				catch(IOException e) {
					display("Server has close the connection: " + e);
					if(gui != null) 
						gui.connectionFailed();
					break;
				}
			
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}
