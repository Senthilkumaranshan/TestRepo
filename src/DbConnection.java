import java.sql.SQLException;

import java.sql.*;
//test
public class DbConnection {
	
    private final static String url = "jdbc:mysql://localhost:3306/foodlog?autoReconnect=true&useSSL=false";
    private final static String user = "root";
    private final static String password = "";
    static Connection con = null;
    
    public static Connection getConnection() {
        try {
        	Class.forName("com.mysql.jdbc.Driver"); 
        	con = DriverManager.getConnection(url, user, password); 
        	
        	return con;
        }
        catch(Exception e) {
        	System.out.println("Exception is : " + e );
        }
		return con;
    }
    
    public void close() {
    	if(con != null) {
    		try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
}
