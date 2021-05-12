import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Login {

	private JFrame frame;
	private final JPanel leftpanel = new JPanel();
	private JTextField usernametxt;
	private JPasswordField passwordtxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 383);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		leftpanel.setBounds(0, 11, 211, 333);
		frame.getContentPane().add(leftpanel);
		leftpanel.setLayout(null);
		frame.setVisible(true);
		
		JLabel lblFoodLogSystem = new JLabel("Food Log System");
		lblFoodLogSystem.setForeground(new Color(255, 255, 255));
		lblFoodLogSystem.setFont(new Font("Nueva Std Cond", Font.BOLD | Font.ITALIC, 26));
		lblFoodLogSystem.setHorizontalAlignment(SwingConstants.CENTER);
		lblFoodLogSystem.setBounds(10, 31, 191, 31);
		leftpanel.add(lblFoodLogSystem);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\kumaran\\eclipse-workspace\\FoodLog\\resources\\loginpagefood.png"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(0, -12, 309, 345);
		leftpanel.add(lblNewLabel);
		
		JPanel rightpanel_1 = new JPanel();
		rightpanel_1.setBackground(new Color(0, 0, 51));
		rightpanel_1.setBounds(210, 11, 224, 333);
		frame.getContentPane().add(rightpanel_1);
		rightpanel_1.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Serif", Font.PLAIN, 15));
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setBounds(10, 63, 90, 33);
		rightpanel_1.add(lblUsername);
		
		usernametxt = new JTextField();
		usernametxt.setBounds(10, 96, 192, 20);
		rightpanel_1.add(usernametxt);
		usernametxt.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Serif", Font.PLAIN, 15));
		lblPassword.setBounds(10, 127, 90, 33);
		rightpanel_1.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String loginsql = "SELECT username, password from users where username = ? and password = ?";
				try {
					PreparedStatement ps = DbConnection.getConnection().prepareStatement(loginsql);
					
					ps.setString(1, usernametxt.getText());
					ps.setString(2, String.valueOf(passwordtxt.getPassword()));
					
					ResultSet rs = ps.executeQuery();
					
					if(rs.next()) {
						frame.dispose();
						Home home = new Home(usernametxt.getText());
					} else {
						JOptionPane.showMessageDialog(null, "Check your credentials!");
					}
					
				} catch (SQLException e1) {
					System.out.println(e1);
				}
			}
		});
		btnLogin.setBounds(10, 200, 89, 23);
		rightpanel_1.add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Register register = new Register();
			}
		});
		btnRegister.setBounds(124, 11, 90, 23);
		rightpanel_1.add(btnRegister);
		
		passwordtxt = new JPasswordField();
		passwordtxt.setBounds(10, 160, 192, 20);
		rightpanel_1.add(passwordtxt);
	}
}
