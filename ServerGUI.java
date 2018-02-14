import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;


public class ServerGUI extends JFrame implements ActionListener, WindowListener {
	
	

	private JButton stopStart;

	private JTextArea chat, event;

	private JTextField tPortNumber;
	private Server server;
	
	private JPanel contentPane;
	  private javax.swing.JScrollPane jScrollPane1;
	  private javax.swing.JScrollPane jScrollPane2;
	
	
	
	
	ServerGUI(int port) {
		
		
		
		super("VIA Instant Messaging Server");
		server = null;
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 821, 592);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JPanel north = new JPanel();
		north.add(new JLabel("Port number: "));
		tPortNumber = new JTextField("  " + port);
		tPortNumber.setBounds(87, 29, 84, 14);
		contentPane.add(tPortNumber);
		
		
		
		stopStart = new JButton("Start");
		stopStart.addActionListener(this);
		stopStart.setBounds(85, 471, 135, 45);
		contentPane.add(stopStart);
		
		
		
		
		jScrollPane1  = new JScrollPane();
		jScrollPane1.setBounds(85, 73, 621, 192);
		contentPane.add(jScrollPane1 );
		
		chat = new JTextArea();
		jScrollPane1.setViewportView(chat);
		chat.setEditable(false);
		appendRoom("Chat room.\n");
	
	
		
		
		jScrollPane2  = new JScrollPane();
		jScrollPane2.setBounds(85, 293, 621, 167);
		contentPane.add(jScrollPane2);
		
		event = new JTextArea();
		event.setEditable(false);
		jScrollPane2.setViewportView(event);
		
		JLabel lblNewLabel = new JLabel("Port Number");
		lblNewLabel.setBounds(197, 29, 150, 14);
		contentPane.add(lblNewLabel);
		appendEvent("Events log.\n");
	


		
		addWindowListener(this);
		setVisible(true);
	}		

	void appendRoom(String str) {
		chat.append(str);
		chat.setCaretPosition(chat.getText().length() - 1);
	}
	void appendEvent(String str) {
		event.append(str);
		event.setCaretPosition(chat.getText().length() - 1);
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		// if running we have to stop
		if(server != null) {
			server.stop();
			server = null;
			tPortNumber.setEditable(true);
			stopStart.setText("Start");
			return;
		}
      	// OK start the server	
		int port;
		try {
			port = Integer.parseInt(tPortNumber.getText().trim());
		}
		catch(Exception er) {
			appendEvent("Invalid port number");
			return;
		}
		
		server = new Server(port, this);
		// and start it as a thread
		new ServerRunning().start();
		stopStart.setText("Stop");
		tPortNumber.setEditable(false);
	}
	
	
	public static void main(String[] arg) {
		new ServerGUI(1500);
	}

	
	public void windowClosing(WindowEvent e) {
		if(server != null) {
			try {
				server.stop();			// ask the server to close the conection
			}
			catch(Exception eClose) {
			}
			server = null;
		}
		// dispose the frame
		dispose();
		System.exit(0);
	}
	// I can ignore the other WindowListener method
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	/*
	 * A thread to run the Server
	 */
	class ServerRunning extends Thread {
		public void run() {
			server.start();         // should execute until if fails
			// the server failed
			stopStart.setText("Start");
			tPortNumber.setEditable(true);
			appendEvent("Server crashed\n");
			server = null;
		}
	}
}
