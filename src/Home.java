import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.SwingConstants;

import net.proteanit.sql.DbUtils;

import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home {

	private JFrame frame;
	JMenuBar menubar;
	JMenu menu;
	private JMenuItem addFoodMenu;
	private JTable table;
	private JTextField foodnametxt;
	private JTextField caloriestxt;
	private JTextField searchtxt;
	PreparedStatement ps;
	JRadioButton rdbtnNonveg, rdbtnVeg;
	ResultSet rs;
	JButton btnClear_2_2, btnClear_2_1, btnClear_2_3;
	JComboBox foodtimecmb;
	JLabel loggeduser;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
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
	public Home() {
		initialize();
		loadTable();
	}
	public Home(String user) {
		initialize();
		loggeduser.setText(user);
		loadTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 655, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblMyFoodLog = new JLabel("My Food Log");
		lblMyFoodLog.setFont(new Font("Vijaya", Font.BOLD, 22));
		lblMyFoodLog.setBounds(253, 11, 116, 23);
		frame.getContentPane().add(lblMyFoodLog);
		
		JScrollPane foodtbl = new JScrollPane();
		foodtbl.setBounds(10, 232, 619, 146);
		frame.getContentPane().add(foodtbl);
		
		table = new JTable();
		foodtbl.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 56, 619, 165);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel foodnamelbl = new JLabel("Food Name");
		foodnamelbl.setBounds(10, 11, 79, 14);
		panel.add(foodnamelbl);
		
		JLabel lblTime = new JLabel("Time");
		lblTime.setBounds(10, 41, 60, 14);
		panel.add(lblTime);
		
		JLabel foodnamelbl_1_1 = new JLabel("Calories");
		foodnamelbl_1_1.setBounds(10, 75, 60, 14);
		panel.add(foodnamelbl_1_1);
		
		JLabel foodnamelbl_1_1_1 = new JLabel("Food Type");
		foodnamelbl_1_1_1.setBounds(10, 110, 60, 14);
		panel.add(foodnamelbl_1_1_1);
		
		foodnametxt = new JTextField();
		foodnametxt.setBounds(99, 8, 193, 20);
		panel.add(foodnametxt);
		foodnametxt.setColumns(10);
		
		foodtimecmb = new JComboBox();
		foodtimecmb.setModel(new DefaultComboBoxModel(new String[] {"Breakfast", "Lunch", "Snack", "Dinner"}));
		foodtimecmb.setBounds(99, 38, 193, 20);
		panel.add(foodtimecmb);
		
		caloriestxt = new JTextField();
		caloriestxt.setColumns(10);
		caloriestxt.setBounds(99, 72, 193, 20);
		panel.add(caloriestxt);
		
		rdbtnVeg = new JRadioButton("Veg");
		rdbtnVeg.setSelected(true);
		rdbtnVeg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnVeg.isSelected()) {
					rdbtnNonveg.setSelected(false);
				}
			}
		});
		rdbtnVeg.setBounds(101, 106, 49, 23);
		panel.add(rdbtnVeg);
		
		rdbtnNonveg = new JRadioButton("Nonveg");
		rdbtnNonveg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNonveg.isSelected()) {
					rdbtnVeg.setSelected(false);
				}
			}
		});
		rdbtnNonveg.setBounds(170, 106, 68, 23);
		panel.add(rdbtnNonveg);
		
		JButton btnClear_2 = new JButton("Clear");
		btnClear_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
				btnClear_2_3.setEnabled(true);
			}
		});
		btnClear_2.setBounds(530, 136, 79, 23);
		panel.add(btnClear_2);
		
		btnClear_2_1 = new JButton("Delete");
		btnClear_2_1.setEnabled(false);
		btnClear_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = searchtxt.getText();
				String sql = "delete from logs where id=?";
				try {
					ps = DbConnection.getConnection().prepareStatement(sql);
					ps.setString(1, id);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null,  "Deleted!");
					loadTable();
					clear();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnClear_2_1.setBounds(435, 136, 85, 23);
		panel.add(btnClear_2_1);
		
		btnClear_2_2 = new JButton("Update");
		btnClear_2_2.setEnabled(false);
		btnClear_2_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnClear_2_3.setEnabled(false);
				
				String food, type, calories, time;
				String sql = "update logs set name=?, time=?, type=?,calories=? where id=?";
				String id = searchtxt.getText();
				
				food = foodnametxt.getText();
				calories = caloriestxt.getText();
				
				if(rdbtnNonveg.isSelected()) {
					type = "Veg";
				}else {
					type = "Nonveg";
				}
				
				switch(foodtimecmb.getSelectedIndex()){
					
					case 0 : time = "Breakfast";
					break;
					case 1 : time = "Lunch";
					break;
					case 2 : time = "Snack";
					break;
					case 3 : time = "Dinner";
					break;
					
					default : time = "Breakfast";
				}
				
				if(food == null ||food.isEmpty() || calories == null || calories.isEmpty()) {
					JOptionPane.showMessageDialog(null,  "Fill required fields");
				} else {
					try {
						ps = DbConnection.getConnection().prepareStatement(sql);
						ps.setString(1, food);
						ps.setString(2, time);
						ps.setString(3, type);
						ps.setString(4, calories);
						ps.setString(5, id);
						int rs1 = ps.executeUpdate();
						JOptionPane.showMessageDialog(null,  "Successfully updated!");
						clear();
						loadTable();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnClear_2_2.setBounds(340, 136, 85, 23);
		panel.add(btnClear_2_2);
		
		btnClear_2_3 = new JButton("Add");
		btnClear_2_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String food, type, calories, time;
				String sql = "insert into logs(name, time, type, calories, user) values(?, ?, ?, ?, ?)";
				String id = getUserId(loggeduser.getText());
				
				food = foodnametxt.getText();
				calories = caloriestxt.getText();
				
				if(rdbtnNonveg.isSelected()) {
					type = "Veg";
				}else {
					type = "Nonveg";
				}
				
				switch(foodtimecmb.getSelectedIndex()){
					
					case 0 : time = "Breakfast";
					break;
					case 1 : time = "Lunch";
					break;
					case 2 : time = "Snack";
					break;
					case 3 : time = "Dinner";
					break;
					
					default : time = "Breakfast";
				}
				
				if(food == null ||food.isEmpty() || calories == null || calories.isEmpty()) {
					JOptionPane.showMessageDialog(null,  "Fill required fields");
				} else {
					try {
						ps = DbConnection.getConnection().prepareStatement(sql);
						ps.setString(1, food);
						ps.setString(2, time);
						ps.setString(3, type);
						ps.setString(4, calories);
						ps.setString(5, id);
						int rs1 = ps.executeUpdate();
						JOptionPane.showMessageDialog(null,  "Successfully saved!");
						loadTable();
						clear();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnClear_2_3.setBounds(224, 136, 68, 23);
		panel.add(btnClear_2_3);
		
		searchtxt = new JTextField();
		searchtxt.setBounds(461, 8, 148, 20);
		panel.add(searchtxt);
		searchtxt.setColumns(10);
		
		JButton btnClear_2_3_1 = new JButton("Search by ID");
		btnClear_2_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnClear_2_3.setEnabled(false);
				btnClear_2_2.setEnabled(true);
				btnClear_2_1.setEnabled(true);
				String searchText = searchtxt.getText();
				String id = getUserId(loggeduser.getText());
				String sql = "select name,time, calories, type from logs where id = ? and user=?";
				String name, time, calories, type;
				
				try {
					ps = DbConnection.getConnection().prepareStatement(sql);
					ps.setString(1, searchText);
					ps.setString(2, id);
					rs = ps.executeQuery();
					
					if(rs.next() == true) {
						name = rs.getString(1);
						time = rs.getString(2);
						calories = rs.getString(3);
						type = rs.getString(4);
						
						foodnametxt.setText(name);
						caloriestxt.setText(calories);
						
						if(type.equals("Veg")) {
							rdbtnVeg.setSelected(true);
							rdbtnNonveg.setSelected(false);
						} else {
							rdbtnVeg.setSelected(false);
							rdbtnNonveg.setSelected(true);
						}
						
					switch(time){
						
						case "Breakfast" : foodtimecmb.setSelectedIndex(0);
						break;
						case "Lunch" : foodtimecmb.setSelectedIndex(1);
						break;
						case "Snack" : foodtimecmb.setSelectedIndex(2);
						break;
						case "Dinner" : foodtimecmb.setSelectedIndex(3);
						break;
						
						default : foodtimecmb.setSelectedIndex(0);
					}
					} else {
						rdbtnVeg.setSelected(true);
						foodtimecmb.setSelectedIndex(0);
						foodnametxt.setText("");
						caloriestxt.setText("");
						JOptionPane.showMessageDialog(null,  "Nothing Found!");
						clear();
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnClear_2_3_1.setBounds(340, 7, 111, 23);
		panel.add(btnClear_2_3_1);
		
		JLabel loggeduserlbl = new JLabel("");
		loggeduserlbl.setBounds(583, 0, 46, 14);
		frame.getContentPane().add(loggeduserlbl);
		
		loggeduser = new JLabel("");
		loggeduser.setBounds(583, 0, 46, 14);
		frame.getContentPane().add(loggeduser);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmfood = new JMenuItem("Food Facts");
		mntmfood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Food food = new Food(loggeduser.getText());
			}
		});
		JMenuItem mntmNew = new JMenuItem("Edit Profile");
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Login log = new Login();
			}
		});
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				EditProfile edit = new EditProfile(loggeduser.getText());
			}
		});
		mntmfood.setIcon(new ImageIcon("C:\\Users\\kumaran\\eclipse-workspace\\FoodLog\\resources\\food.png"));
		mnFile.add(mntmfood);
		mntmNew.setIcon(new ImageIcon("C:\\Users\\kumaran\\eclipse-workspace\\FoodLog\\resources\\addnew.png"));
		mnFile.add(mntmNew);
		mntmLogout.setIcon(new ImageIcon("C:\\Users\\kumaran\\eclipse-workspace\\FoodLog\\resources\\logout.png"));
		mnFile.add(mntmLogout);
		frame.setVisible(true);
	}
	
	public void loadTable() {
		String id = getUserId(loggeduser.getText());
		String sql = "Select * from logs where user=?";
		try {
			ps = DbConnection.getConnection().prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void clear() {
		foodnametxt.setText("");
		caloriestxt.setText("");
		foodtimecmb.setSelectedIndex(0);
		rdbtnVeg.setSelected(true);
		rdbtnNonveg.setSelected(false);
		searchtxt.setText("");
		btnClear_2_2.setEnabled(false);
		btnClear_2_1.setEnabled(false);
		btnClear_2_3.setEnabled(true);
		
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
