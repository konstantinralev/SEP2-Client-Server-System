import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Server {
	
	private static int UserID;
	
	private ArrayList<ClientHandler> userlist;
	
	private ServerGUI servergui;
	
	private SimpleDateFormat timer;
	
	private int port;

	private boolean forward;
	


	public Server(int port) {
		this(port, null);
	}
	
	public Server(int port, ServerGUI gui) {
		
		this.servergui = gui;
		
		this.port = port;
		
		timer = new SimpleDateFormat("HH:mm:ss");
		
		userlist = new ArrayList<ClientHandler>();
	}
	
	public void start() {
		forward = true;
	
		try 
		{
			
			ServerSocket serverSocket = new ServerSocket(port);

			while(forward) 
			{
				
				display("Server waiting for Clients on port " + port + ".");
				
				Socket socket = serverSocket.accept();  
				
				if(!forward)
					break;
				ClientHandler t = new ClientHandler(socket); 
				userlist.add(t);									
				t.start();
			}
			try {
				serverSocket.close();
				for(int i = 0; i < userlist.size(); ++i) {
					ClientHandler tc = userlist.get(i);
					try {
						
					tc.Inputs.close();
					tc.Outputs.close(); 
					tc.socket.close();
					}
					catch(IOException ioE) {
					
					}
				}
			}
			catch(Exception e) {
				display("Exception while closing server and clients " + e);
			}
		}
		catch (IOException e) {
            String msg = timer.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
			display(msg);
		}
	}		
  
	
	protected void stop() {
		forward = false;
		
	}

	
	private void display(String msg) {
		String time = timer.format(new Date()) + " " + msg;
		if(servergui == null)
			System.out.println(time);
		else
			servergui.appendEvent(time + "\n");
	}

	private synchronized void broadcast(String message) {
		
		String time = timer.format(new Date());
		String messageLf = time + " " + message + "\n";
		
		if(servergui == null)
			System.out.print(messageLf);
		else
			servergui.appendRoom(messageLf);  
		
		
		for(int i = userlist.size(); --i >= 0;) {
			ClientHandler ct = userlist.get(i);
			
			if(!ct.writeMsg(messageLf)) {
				userlist.remove(i);
				display("Disconnected Client " + ct.username + " removed from list.");
			}
		}
	}

	
	synchronized void remove(int id) {
	
		for(int i = 0; i < userlist.size(); ++i) {
			ClientHandler ct = userlist.get(i);
			
			if(ct.id == id) {
				userlist.remove(i);
				return;
			}
		}
	}
	
	
	class ClientHandler extends Thread {
		
		Socket socket;
		ObjectInputStream Inputs;
		ObjectOutputStream Outputs;
		
		int id;
		
		String username;
		
		ChatMessage cm;
		
		String date;
		

		
		ClientHandler(Socket socket) {
		
			id = UserID++;
			this.socket = socket;
			
			System.out.println("Thread trying to create Object Input/Output Streams");
			try
			{
				
				Outputs = new ObjectOutputStream(socket.getOutputStream());
				Inputs  = new ObjectInputStream(socket.getInputStream());
				
				username = (String) Inputs.readObject();
				display(username + " just connected.");
			}
			catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
			
			catch (ClassNotFoundException e) {
			}
            date = new Date().toString() + "\n";
		}

	
		public void run() {
			
			boolean move = true;
			while(move) {
			
				try {
					cm = (ChatMessage) Inputs.readObject();
				}
				catch (IOException e) {
					display(username + " Exception reading Streams: " + e);
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
				String message = cm.getMessage();

				
				switch(cm.getType()) {

				case ChatMessage.MESSAGE:
					broadcast(username + ": " + message);
					break;
				case ChatMessage.LOGOUT:
					display(username + " disconnected with a LOGOUT message.");
					move = false;
					break;
				case ChatMessage.OnlineUsers:
					writeMsg("Online Users:  " + timer.format(new Date()) + "\n");
				
					for(int i = 0; i < userlist.size(); ++i) {
						ClientHandler emp1 = userlist.get(i);
						writeMsg((i+1) + ") " + emp1.username + " since " + emp1.date);
					}
					break;
				}
			}
			
			remove(id);
			close();
		}
		
	
		private void close() {
		
			try {
				if(Outputs != null) Outputs.close();
			}
			catch(Exception e) {}
			try {
				if(Inputs != null) Inputs.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
		}


		private boolean writeMsg(String msg) {
		
			if(!socket.isConnected()) {
				close();
				return false;
			}
			
			try {
				Outputs.writeObject(msg);
			}
			
			catch(IOException e) {
				display("Error sending message to " + username);
				display(e.toString());
			}	
			return true;
		}
	}
}

