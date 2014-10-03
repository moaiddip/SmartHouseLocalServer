import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDb {
	String url = "jdbc:mysql://localhost:3307/";
	String dbName = "localdatabase";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "root321";
	Connection conn;
	Statement st;
	ResultSet res;
	private String user_ssn = null;

	public UserDb(String user_ssn) {
		// Test, Comment the following lines if you face issues
		this.user_ssn = user_ssn;
		connect();
		// Method to retrieve
		disconnect();
	}

	public void connect() {
		System.out.print("Trying to connect to database . . . ");
		try {
			String query = "select roomName from " + dbName;
			System.out.println(query);
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, userName, password);
			
			st = conn.createStatement();
			res = st.executeQuery(query);
			while (res.next()) {
				System.out.println(res.getString("roomName"));
			}

			System.out.println("Connected!!!");
		} catch (Exception e) {
			System.out.print("Couldn't connect.");
			System.out.println(e);
		}
	}

	public String toggleDevice(int deviceID, boolean state) {// EXAMPLE TAKE
																// THIS AWAY IF
																// WANTED //dinO
		String confirmation = "ERROR";
		connect();
		try {
			st.executeUpdate("UPDATE");
			confirmation = "actionCompletedWithoutErrors";
		} catch (Exception ex) {
			System.out
					.println("\n\n\n\nERROR IN: Class UserDb in method: toggleDevice\n\n\n\n");
			ex.printStackTrace();
		} finally {
			disconnect();
		}
		return confirmation;
	}

	public void disconnect() {
		try {
			conn.close();
			System.out.println("Disconnected from database.");
		} catch (Exception e) {
			System.out.println("Something is fishy with the connection");
			System.out.println(e);
		}
	}
}
