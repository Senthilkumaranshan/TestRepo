import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Register {

	private JFrame frame;
	private JTextField registerusertxt;
	private JTextField registeremailtxt;
	PreparedStatement ps;
	ResultSet rs;
	private JPasswordField registerpasswordtxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register window = new Register();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 0, 102));
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblUserRegistration = new JLabel("User Registration");
		lblUserRegistration.setForeground(new Color(255, 255, 255));
		lblUserRegistration.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		lblUserRegistration.setBounds(136, 11, 171, 26);
		frame.getContentPane().add(lblUserRegistration);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setBounds(74, 74, 92, 19);
		frame.getContentPane().add(lblUsername);
		
		registerusertxt = new JTextField();
		registerusertxt.setBounds(176, 73, 171, 20);
		frame.getContentPane().add(registerusertxt);
		registerusertxt.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(74, 109, 92, 19);
		frame.getContentPane().add(lblEmail);
		
		registeremailtxt = new JTextField();
		registeremailtxt.setColumns(10);
		registeremailtxt.setBounds(176, 108, 171, 20);
		frame.getContentPane().add(registeremailtxt);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(74, 139, 92, 19);
		frame.getContentPane().add(lblPassword);
		
		JButton registerbtn = new JButton("Register");
		registerbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username, email, password;
				String sql = "insert into users(username, email, password) values(?, ?, ?)";
				String checkemail = "select * from users where email=?";
				
				
				username = registerusertxt.getText();
				email = registeremailtxt.getText();
				password = registerpasswordtxt.getText();
				
				try {
					ps = DbConnection.getConnection().prepareStatement(checkemail);
					ps.setString(1, email);
					rs = ps.executeQuery();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				
				if(username == null ||username.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
					JOptionPane.showMessageDialog(null,  "Fill required fields");
				} else
					try {
						if(rs.next()) {
							JOptionPane.showMessageDialog(null,  "This email already exist!");
						} else {
							try {
								ps = DbConnection.getConnection().prepareStatement(sql);
								ps.setString(1, username);
								ps.setString(2, email);
								ps.setString(3, password);
								int rs1 = ps.executeUpdate();
								JOptionPane.showMessageDialog(null,  "Successfully registered");
								frame.dispose();
								Login login = new Login();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} catch (HeadlessException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}

		});
		registerbtn.setBounds(176, 177, 87, 23);
		frame.getContentPane().add(registerbtn);
		
		JButton backbtn = new JButton("Back");
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Login login = new Login();
			}
		});
		backbtn.setBounds(273, 177, 74, 23);
		frame.getContentPane().add(backbtn);
		
		registerpasswordtxt = new JPasswordField();
		registerpasswordtxt.setBounds(176, 139, 171, 20);
		frame.getContentPane().add(registerpasswordtxt);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
