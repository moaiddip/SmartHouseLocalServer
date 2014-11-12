import java.sql.*;

import javax.swing.JOptionPane;


public class LogInDb {
//	private String ipAdress = "localhost";
//	private String port = "3306";
//	private String url = "jdbc:mysql://" + ipAdress + ":" + port + "/";
//	private String dbName = "LocalDatabase";
//	private String driver = "com.mysql.jdbc.Driver";
//	private String userName = "root";
//	private String password = "7980066";
	private Connection conn;
	private Statement st;
//	private ResultSet res;
//	private PreparedStatement ps;
//	private String user_ssn;	
	private ResultSet rs;
	
	
	
	public LogInDb() {
		connect();
	}
		public void connect() {
		 String sql="Select * from Users";
			System.out.print("Trying to connect to database . . . ");
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/LocalDatabase","root","7980066");
				Statement stmt=con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				System.out.println("Connected!!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		public boolean loginCheck(String username, String password){
			String dbUsername, dbPassword;
			boolean login=false;
			try{
				while(rs.next()){
					dbUsername=rs.getString("username");
					dbPassword=rs.getString("password");
					if(dbUsername.equals(username) && dbPassword.equals(password)){
						System.out.println("OK");
						login = true;
					}
					System.out.println(username + password + "" +dbUsername + dbPassword);
				return true;
				}
			}
				
				catch(Exception e){
				e.printStackTrace();
			   }
			return false;
		}


			
		}
		
		
		
		
	
	
		
		
		
/*String sql="Select * from Table_name";

try {
Class.forName("com.mysql.jdbc.Driver");
Connection con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdatabasename","yourmysqlid","yourmysqlpassword");
//As we are creating a connection on a local computer we will write the url as jdbc:mysql://localhost:3306 
Statement stmt=con.createStatement();
ResultSet rs = stmt.executeQuery(sql);
}
catch (Exception e){
JOptionPane.showMessageDialog(parentComponent, message);essageDialog(this, e.getMessage());
}*/





