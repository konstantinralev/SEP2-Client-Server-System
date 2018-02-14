

import javax.swing.JOptionPane;

import Controller.Controller;
import Model.User;
import Model.UserInteractionListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;


public class Login extends javax.swing.JFrame implements UserInteractionListener {

    
    private final Controller controller;
    
   
    public Login() {
    	setTitle("VIA Instant Messaging");
        initComponents();
        setLocationRelativeTo(null);
        this.controller = Controller.getController();
    }

    
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel3.setBackground(Color.BLACK);
        login = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        signup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Account Name");

        jLabel2.setText("Password:");

        jPanel1.setBackground(new java.awt.Color(31, 31, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); 
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("VIA IM Login ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        login.setText("Login");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        signup.setText("Signup");
        signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(50)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(clear)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(login))
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(jLabel1)
        								.addComponent(jLabel2))
        							.addGap(18)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(password, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
        								.addComponent(username, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)))))
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(signup)))
        			.addContainerGap(71, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(43)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel1)
        				.addComponent(username, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
        			.addGap(23)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jLabel2)
        				.addComponent(password, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(login)
        				.addComponent(clear))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(signup)
        			.addContainerGap(20, Short.MAX_VALUE))
        );
        getContentPane().setLayout(layout);

        pack();
    }

    private void signupActionPerformed(java.awt.event.ActionEvent evt) {
        SignUp signupForm = new SignUp(this, true, this);
        signupForm.setVisible(true);
    }

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {
        this.username.setText(null);
        this.password.setText(null);
    }

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {
        User user = this.controller.login(this.username.getText(), this.password.getText());
        if(user != null) {
            System.out.println("You have logged in successfully!");
            new ClientGUI("localhost", 1500);
            setVisible(false);
        } else {
			JOptionPane.showMessageDialog(null, "User Not Found");
        }
    }

   
    private javax.swing.JButton clear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton login;
    private javax.swing.JPasswordField password;
    private javax.swing.JButton signup;
    private javax.swing.JTextField username;

    @Override
    public boolean signup(User user) {
        return this.controller.signup(user);
    }
}
