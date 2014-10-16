import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDb {
	// WE HAVE TO CHANGE THIS
	private String url = "jdbc:mysql://localhost:3307/";
	private String dbName = "localdatabase";
	private String mySqlDriver = "com.mysql.jdbc.Driver";
	private String userName = "root";
	private String password = "root321";
	private Connection conn;
	private Statement st;
	private ResultSet res;
	private String user_ssn;

	public UserDb(String user_ssn) {
		this.user_ssn = user_ssn;
		testConnection();

	}

	private void testConnection() {
		try {
			// getAllAllowedDevices();
			checkDevice(4);
			// checkAllDevices();
			// testDevice(3);
			// toggleDevice(3, true);
			// System.out.println("DevicePin: " + getDevicePin(2));
			// getAllAllowedDevices();
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
			st = conn.createStatement();
			System.out.println("Connected!!!");
		} catch (Exception e) {
			System.out.print("Couldn't connect.");
			System.out.println(e);
		}
	}

	public void disconnect() {
		try {
			st.close();
			conn.close();
			System.out.println("Disconnected from database.");
		} catch (Exception e) {
			System.out.println("Something is fishy with the connection");
			System.out.println(e);
		}
	}

	public String[] toggleDevice(int deviceID, boolean state) {
		connect();
		String[] returnArray = null;
		String query = "SELECT Permissions.isAllowed, Devices.deviceName, Devices.deviceState\n"
				+ "FROM Permissions\n"
				+ "WHERE userssn = "
				+ user_ssn
				+ "\n"
				+ "AND deviceId = " + deviceID + ";";
		String update = "UPDATE devices\n" + "SET deviceState = " + state
				+ "\n" + "WHERE deviceId = " + deviceID;
		System.out.println("******UPDATE****** \n" + update
				+ "\n******************");
		try {

			res = st.executeQuery(query);
			res.next();
			boolean isAllowed = res.getBoolean("isAllowed");
			returnArray = new String[] { res.getString("deviceName"),
					Boolean.toString(res.getBoolean("deviceState")) };
			if (isAllowed == true) {
				// ///////////////////////////////////////////////// arduino
				// Togglemethod here
				st.executeUpdate(update);
				addDeviceHistory(deviceID, state);
			}
			System.out.println("Update Succesful");
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			disconnect();
		}
		return returnArray;
	}

	public String[] checkDevice(int deviceID) {
		connect();
		String[] returnArray = null;
		String query = "SELECT Permissions.isAllowed, Devices.deviceName, Devices.deviceState\n"
				+ "FROM Permissions, Devices\n"
				+ "WHERE Permissions.userssn = " + user_ssn + "\n" 
				+ "AND Permissions.deviceId = " + deviceID + ";";
		try {
			res = st.executeQuery(query);
			res.next();
			// boolean isAllowed = res.getBoolean("isAllowed");
			// if (isAllowed == true) {
			returnArray = new String[] { res.getString("Devices.deviceName"),
					Boolean.toString(res.getBoolean("Devices.deviceState")) };
			// }
			System.out.println("Update Succesful");
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			disconnect();
		}
		return returnArray;
	}

	public String[] checkAllDevices() {
		String[] returnArr = null;
		ArrayList<String> returnList = new ArrayList<String>();
		connect();
		String query = "SELECT Permissions.isAllowed, Devices.deviceName, Devices.deviceState\n"
				+ "FROM Permissions, Devices\n"
				+ "WHERE userssn = "
				+ user_ssn
				+ ";";
		try {

			res = st.executeQuery(query);
			while (res.next()) {// /////////
				// boolean isAllowed = res.getBoolean("isAllowed");
				// if (isAllowed == true) {
				returnList.add(res.getString("deviceName"));
				returnList.add(Boolean.toString(res.getBoolean("deviceState")));
				// }
			}
			System.out.println("Update Succesful");
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			disconnect();
		}
		if (!returnList.isEmpty()) {
			returnArr = new String[returnList.size()];
			for (int i = 0; i < returnList.size(); i++) {
				returnArr[i] = returnList.get(1);
			}
		}
		return returnArr;
	}

	public String[] testDevice(int deviceID) {
		connect();
		String[] returnArray = new String[2];

		String query = "SELECT Permissions.isAllowed, Devices.deviceName, Devices.deviceState\n"
				+ "FROM Permissions, Devices\n"
				+ "WHERE userssn = "
				+ user_ssn
				+ "\n" + "AND Devices.deviceId = " + deviceID + ";";
		String updateOn = "UPDATE devices\n" + "SET deviceState = true " + "\n"
				+ "WHERE deviceId = " + deviceID;
		String updateOff = "UPDATE devices\n" + "SET deviceState = false "
				+ "\n" + "WHERE deviceId = " + deviceID;
		try {

			res = st.executeQuery(query);
			res.next();
			boolean isAllowed = res.getBoolean("isAllowed");
			returnArray[0] = res.getString("deviceName");
			if (isAllowed == true) {
				st.executeUpdate(updateOn);
				// //////////////////////////Arduino method here for turning on
				wait(100);
				st.executeUpdate(updateOff);
				// //////////////////////////Arduino method here for turning off
				returnArray[1] = "true";
			} else {
				returnArray[1] = "false";
			}
		} catch (Exception e) {
			returnArray[1] = "false";
			System.out.print(e);
		} finally {
			disconnect();
		}
		return returnArray;
	}

	private void addDeviceHistory(int deviceID, boolean state) {

	}

	public int getDevicePin(int deviceID) {
		String query = "SELECT devicePin \n" + "FROM Devices \n"
				+ "WHERE deviceID = " + deviceID + ";";
		System.out.println("******UPDATE****** \n" + query
				+ "\n*****************");
		try {

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
				+ "AND permissions.UserSSN = " + user_ssn + "\n" // later
																	// User.ssn
				+ "AND permissions.isAllowed = true" + ";";
		System.out.println("******UPDATE****** \n" + query
				+ "\n*****************");
		try {

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

			st.executeUpdate(update);
			System.out.println("Update Succesful");
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	// public void defaultInsert() {
	// String insert = "INSERT INTO rooms(\n" + "SET deviceState = " + true +
	// "\n"
	// + "WHERE deviceId = " + 1;
	// table_name (column1,column2,column3,...)
	// VALUES (value1,value2,value3,...);
	// System.out.println("******INSERT****** \n" + insert
	// + "\n*****************");
	// try {
	// st = conn.createStatement();
	// st.executeUpdate(insert);
	// System.out.println("Insert Succesful");
	// } catch (Exception e) {
	// System.out.print(e);
	// }
	// }
}
