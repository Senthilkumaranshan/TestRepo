import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class EditProfile {

	private JFrame frame;
	private JTextField usrtxt;
	private JTextField emailtxt;
	PreparedStatement ps;
	ResultSet rs;
	private JPasswordField pwdtxt;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditProfile window = new EditProfile();
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
	public EditProfile() {
		initialize();
	}
	
	public EditProfile(String user) {
		initialize();
		setupdate(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 0, 102));
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(89, 74, 92, 19);
		frame.getContentPane().add(lblUsername);
		
		usrtxt = new JTextField();
		usrtxt.setColumns(10);
		usrtxt.setBounds(191, 73, 171, 20);
		frame.getContentPane().add(usrtxt);
		
		emailtxt = new JTextField();
		emailtxt.setColumns(10);
		emailtxt.setBounds(191, 108, 171, 20);
		frame.getContentPane().add(emailtxt);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(89, 155, 92, 19);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(89, 109, 92, 19);
		frame.getContentPane().add(lblEmail);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username, email, password, id;
				String sql = "update users set username=?, email=?, password=? where id=?";
				String getuser = "select id, username, email, password from users where username =?";
				username = usrtxt.getText();
				email = emailtxt.getText();
				password = pwdtxt.getText();
				id = null;
				
				
				try {
					ps = DbConnection.getConnection().prepareStatement(getuser);
					ps.setString(1, username);
					rs= ps.executeQuery();
					if(rs.next()) {
						if(pwdtxt.getPassword() == null || pwdtxt.getText().isEmpty()) {
							password = rs.getString(4);
						}
						id= rs.getString(1);
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				if(username == null ||username.isEmpty() || email == null || email.isEmpty()) {
					JOptionPane.showMessageDialog(null,  "Fill required fields");
				} else {
					try {
						ps = DbConnection.getConnection().prepareStatement(sql);
						ps.setString(1, username);
						ps.setString(2, email);
						ps.setString(3, password);
						ps.setString(4, id);
						int rs1 = ps.executeUpdate();
						JOptionPane.showMessageDialog(null,  "Successfully updated!");
						frame.dispose();
						Home home = new Home(username);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnUpdate.setBounds(191, 203, 87, 23);
		frame.getContentPane().add(btnUpdate);
		
		JButton backbtn = new JButton("Back");
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Home home = new Home();
			}
		});
		backbtn.setBounds(288, 203, 74, 23);
		frame.getContentPane().add(backbtn);
		
		JLabel lblEditYourProfile = new JLabel("Edit your profile");
		lblEditYourProfile.setForeground(Color.WHITE);
		lblEditYourProfile.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		lblEditYourProfile.setBounds(151, 11, 171, 26);
		frame.getContentPane().add(lblEditYourProfile);
		
		pwdtxt = new JPasswordField();
		pwdtxt.setBounds(191, 156, 171, 20);
		frame.getContentPane().add(pwdtxt);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setupdate(String user) {
		
		String sql = "select username, email from users where username = ?";
		
		try {
			ps = DbConnection.getConnection().prepareStatement(sql);
			ps.setString(1, user);
			
			rs = ps.executeQuery();
					
			if(rs.next()) {
				usrtxt.setText(rs.getString(1));
				emailtxt.setText(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
