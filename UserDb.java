import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDb {
	private String ipAdress = "localhost";
	private String port = "3307";
	private String url = "jdbc:mysql://" + ipAdress + ":" + port + "/";
	private String dbName = "localdatabase";
	private String driver = "com.mysql.jdbc.Driver";
	private String userName = "root";
	private String password = "root321";
	private Connection conn;
	private Statement st;
	private ResultSet res;
	private String user_ssn;

	public UserDb(String user_ssn) {
		this.user_ssn = user_ssn;
//		testConnection();

	}

	private void testConnection() {
		try {
			connect();
			// toggleDevice(3, true);
			// System.out.println("DevicePin: " + getDevicePin(2));
			getAllAllowedDevices();
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

	public String toggleDevice(int deviceID, boolean state) {
		connect();
		System.out.println("entered ToggleDevice");
		// Need to insert condition so user can only users with permission can
		String update1 = "UPDATE devices\n" + "SET deviceState = " + state
				+ "\n"
				+ "WHERE deviceId =(Select  deviceId \n"
				+ "FROM Permissions \n"
				+ "WHERE Permissions.userSSN = '9910101437' \n" // laterUser.SSN
				+ "AND Permissions.deviceId = " + deviceID + "\n"
				+ "AND Permissions.isAllowed = true);";

		String query = "SELECT isAllowed\n" + "FROM permissions\n"
				+ "WHERE userssn = '9910101437'\n" // Later user.ssn
				+ "AND deviceId = " + deviceID + ";";
		String update = "UPDATE devices\n" + "SET deviceState = " + state
				+ "\n" + "WHERE deviceId = " + deviceID;
		System.out.println("******UPDATE****** \n" + update
				+ "\n******************");
		try {
			st = conn.createStatement();
			res = st.executeQuery(query);
			res.next();
			boolean isAllowed = res.getBoolean("isAllowed");
			if (isAllowed == true) {
				// arduino Togglemethod here
				st.executeUpdate(update);
				addDeviceHistory(deviceID, state);
			}
			System.out.println("Update Succesful");
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			disconnect();
		}
		return "OKEY";
	}

	private void addDeviceHistory(int deviceID, boolean state) {

	}

	public int getDevicePin(int deviceID) {
		String query = "SELECT devicePin \n" + "FROM Devices \n"
				+ "WHERE deviceID = " + deviceID + ";";
		System.out.println("******UPDATE****** \n" + query
				+ "\n*****************");
		try {
			st = conn.createStatement();
			res = st.executeQuery(query);// //////////
			res.next();
			System.out.println("Query Succesful");
			return res.getInt("devicePin");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		} finally {
			disconnect();
		}
		return -1;

	}

	public void getAllAllowedDevices() {
		String query = "SELECT devices.deviceName, rooms.roomName, deviceState\n"
				+ "FROM rooms, devices, permissions\n"
				+ "WHERE rooms.roomId = devices.roomId\n"
				+ "AND devices.deviceId = permissions.deviceId\n"
				+ "AND permissions.UserSSN = '9310101337'\n" // later User.ssn
				+ "AND permissions.isAllowed = true" + ";";
		System.out.println("******UPDATE****** \n" + query
				+ "\n*****************");
		try {
			st = conn.createStatement();
			res = st.executeQuery(query);// //////////
			System.out.println("Query Succesful");
			while (res.next()) {// /////////
				System.out.println(res.getString("deviceName") + " "
						+ res.getString("roomName") + " "
						+ res.getBoolean("deviceState"));// ////////
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		} finally {
			disconnect();
		}
	}

	// FOR CHANGING FOR DATATYPES AND RETRIEVING DATE/TIME
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

	// public String getCurrentDate(){
	// DateFormat dt =new SimpleDateFormat("yyyy-MM-dd");
	// Date date = new Date();
	// return dt.format(date);
	// }
	//
	// public String getCurrentTime(){
	// DateFormat dt =new SimpleDateFormat("HH:mm:ss");
	// Date date = new Date();
	// return dt.format(date);
	// }

	
	// FOR MAKING OF NEW QUERIES/UPDATES/INSERTS
	public void defaultQuery() {
		String query = "SELECT roomName " + "From rooms";
		System.out.println("******UPDATE****** \n" + query
				+ "\n*****************");
		try {
			st = conn.createStatement();

			res = st.executeQuery(query);// //////////
			while (res.next()) {// /////////
				System.out.println(res.getString("roomName"));// ////////
			} // /////////////
			System.out.println("Query Succesful");
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void defaultUpdate() {
		String update = "UPDATE devices\n" + "SET deviceState = " + true + "\n"
				+ "WHERE deviceId = " + 1;
		System.out.println("******UPDATE****** \n" + update
				+ "\n*****************");
		try {
			st = conn.createStatement();
			st.executeUpdate(update);
			System.out.println("Update Succesful");
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void defaultInsert() {
	}
}
