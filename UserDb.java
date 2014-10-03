import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDb {
	private String url = "jdbc:mysql://localhost:3307/";
	private String dbName = "localdatabase";
	private String driver = "com.mysql.jdbc.Driver";
	private String userName = "root";
	private String password = "root321";
	private Connection conn;
	private Statement st;
	private ResultSet res;

	public UserDb() {
		testConnection();

	}

	private void testConnection() {
		try {
			connect();
			disconnect();
		} catch (Exception e) {
			System.out.print("Error in testing connection");
		} finally {
			System.out.println("Test Connection Succesful");
		}
	}

	public void connect() {
		System.out.print("Trying to connect to database . . . ");
		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected!!!");
		} catch (Exception e) {
			System.out.print("Couldn't connect.");
			System.out.println(e);
		}
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

	public void toggleDevice(int deviceID, boolean state) {
//		String query = "select roomName from rooms";
//		System.out.println(query);
//		try {
//			st = conn.createStatement();
//			res = st.executeQuery(query);
//			while (res.next()) {
//				System.out.println(res.getString("roomName"));
//			}
//		} catch (Exception e) {
//			System.out.print(e);
//		}

	}

	public int getDevicePin(int deviceID) {
		return 1;
	}

	public void getAllAllowedDevices() {
	}
}
