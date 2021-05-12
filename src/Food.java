import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Food {

	private JFrame frame;
	private JTable foodfacttbl;
	PreparedStatement ps;
	ResultSet rs;
	private JLabel loggeduser;
	private JScrollPane scrollPane;
	private JButton btnAdd;
	private JTextField foodtxt;
	private JTextField caltxt;
	private JLabel lblFood;
	private JLabel lblCalorie;
	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Food window = new Food();
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
	public Food() {
		initialize();
		loadFoodFact();
		checkPrivillege();
	}
	
	public Food(String user) {
		initialize();
		loadFoodFact();
		loggeduser.setText(user);
		checkPrivillege();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		
		JLabel lblFoodFacts = new JLabel("Food Facts");
		lblFoodFacts.setForeground(new Color(0, 0, 0));
		lblFoodFacts.setFont(new Font("Trebuchet MS", Font.PLAIN, 22));
		lblFoodFacts.setBounds(163, 11, 125, 26);
		frame.getContentPane().add(lblFoodFacts);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 82, 396, 153);
		frame.getContentPane().add(scrollPane);
		
		foodfacttbl = new JTable();
		scrollPane.setViewportView(foodfacttbl);
		
		loggeduser = new JLabel("");
		loggeduser.setBounds(378, 11, 46, 14);
		frame.getContentPane().add(loggeduser);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name, calories;
				String sql = "insert into food(name, calories) values(?, ?)";
				
				
				name = foodtxt.getText();
				calories = caltxt.getText();
				
				if(name == null ||name.isEmpty() || calories == null || calories.isEmpty()) {
					JOptionPane.showMessageDialog(null,  "Fill required fields");
				} else {
							try {
								ps = DbConnection.getConnection().prepareStatement(sql);
								ps.setString(1, name);
								ps.setString(2, calories);
								int rs1 = ps.executeUpdate();
								JOptionPane.showMessageDialog(null,  "Successfully Added");
								loadFoodFact();
								foodtxt.setText("");
								caltxt.setText("");
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
			}
		});
		btnAdd.setBounds(347, 51, 58, 23);
		frame.getContentPane().add(btnAdd);
		
		foodtxt = new JTextField();
		foodtxt.setBounds(30, 52, 138, 20);
		frame.getContentPane().add(foodtxt);
		foodtxt.setColumns(10);
		
		caltxt = new JTextField();
		caltxt.setColumns(10);
		caltxt.setBounds(196, 52, 125, 20);
		frame.getContentPane().add(caltxt);
		
		lblFood = new JLabel("Food");
		lblFood.setBounds(30, 40, 46, 14);
		frame.getContentPane().add(lblFood);
		
		lblCalorie = new JLabel("Calorie");
		lblCalorie.setBounds(196, 40, 46, 14);
		frame.getContentPane().add(lblCalorie);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Home home = new Home(loggeduser.getText());
			}
		});
		btnBack.setBounds(0, 0, 76, 23);
		frame.getContentPane().add(btnBack);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void loadFoodFact() {
		//String id = getUserId(loggeduser.getText());
		String sql = "Select * from food";
		try {
			ps = DbConnection.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			foodfacttbl.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkPrivillege() {
		if(!getUserId(loggeduser.getText()).contentEquals("1")) {
			lblCalorie.setVisible(false);
			lblFood.setVisible(false);
			caltxt.setVisible(false);
			foodtxt.setVisible(false);
			btnAdd.setVisible(false);
		} else {
			lblCalorie.setVisible(true);
			lblFood.setVisible(true);
			caltxt.setVisible(true);
			foodtxt.setVisible(true);
			btnAdd.setVisible(true);
		}
	}
	
	private String getUserId(String user) {
		String getuser = "select id, username, email, password from users where username =?";
		
		try {
			ps = DbConnection.getConnection().prepareStatement(getuser);
			ps.setString(1, user);
			rs= ps.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		return null;
	}

}
