import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDb {
	private String ipAdress = "localhost";
	private String port = "3307";
	private String url = "jdbc:mysql://" + ipAdress +":" + port +"/";
	private String dbName = "localdatabase";
	private String driver = "com.mysql.jdbc.Driver";
	private String userName = "root";
	private String password = "root321";
	private Connection conn;
	private Statement st;
	private ResultSet res;
	private User user;

	public UserDb(User user) {
		this.user = user;
		testConnection();

	}

	private void testConnection() {
		try {
			connect();
			toggleDevice(2, false);
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
		// Need to insert condition so user can only users with permission can
		String update = "UPDATE devices\n"
				+ "SET deviceState = " + state + "\n"
				+ "WHERE deviceId = " + deviceID;
		System.out.println("******UPDATE****** \n" + update +"\n******************");
		try {
			st = conn.createStatement();
			st.executeUpdate(update);
			System.out.println("Update Succesful");
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public int getDevicePin(int deviceID) {
		return 1;
	}

	public void getAllAllowedDevices() {
	}

	// FOR CHANGING FOR DATATYPES BECAUSE BOOLEAN IN SQL IS 1 or 2
	public int booleanToInt(boolean b) {
		if (b) {
			return 1;
		}
		return 0;
	}

	public boolean intToBoolean(int i) {
		if (i == 1) {
			return true;
		}
		return false;
	}

	// FOR MAKING OF NEW QUERIES/UPDATES
	public void defaultQuery() {
		String query = "SELECT roomName " + "From rooms";
		System.out.println(query);
		try {
			st = conn.createStatement();

			res = st.executeQuery("******UPDATE****** \n" + query +"\n*****************");// //////////
			while (res.next()) {// /////////
				System.out.println(res.getString("roomName"));// ////////
			} // /////////////
			System.out.println("Query Succesful");
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void defaultUpdate() {
		String update = "UPDATE devices\n"
				+ "SET deviceState = " + true + "\n"
				+ "WHERE deviceId = " + 1;
		System.out.println("******UPDATE****** \n" + update +"\n*****************");
		try {
			st = conn.createStatement();
			st.executeUpdate(update);
			System.out.println("Update Succesful");
		} catch (Exception e) {
			System.out.print(e);
		}
	}
}
