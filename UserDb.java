import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDb {

	// private String url = "jdbc:mysql://localhost:3306/";
	// private String dbName = "localdatabase";
	// private String driver = "com.mysql.jdbc.Driver";
	// private String userName = "root";
	// private String password = "t02h1844";

	private String ipAdress = "localhost";
	private String port = "3307";
	private String url = "jdbc:mysql://" + ipAdress + ":" + port + "/";
	private String dbName = "LocalDatabase";
	private String driver = "com.mysql.jdbc.Driver";
	private String userName = "root";
	private String password = "root321";
	private Connection conn;
	private Statement st;
	private ResultSet res;
	private PreparedStatement ps;
	private String user_ssn;
	private boolean printMsg;

	public UserDb() {
		connect();
		// this.user_ssn = user_ssn;
		// testConnection();

	}

	private void testConnection() {
		try {
			System.out
					.println(isAuthorized("820907-9002", "S3P1D1H"));
			// toggleDevice(3,true);
			// addDeviceHistory(3,true);
			// getDevicePin(3);
			// getAllAllowedDevices();
			// checkDevice(3);
			// checkAllDevices();
			// testDevice(3);
		} catch (Exception e) {
			System.out.print("Error in testing connection");
		} finally {
			if (printMsg) {
				System.out.println("Test Connection Succesful");
			}
		}
	}

	public void connect() {
		System.out.print("Trying to connect to database . . . ");
		try {

			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			st = conn.createStatement();
			System.out.println("Connected!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			conn.close();
			st.close();
			if (printMsg) {
				System.out.println("Disconnected from database.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList toggleDevice(int deviceID, boolean state) {
		ArrayList toggleList = new ArrayList();
		String query = "SELECT isAllowed\n" 
				+ "FROM Permissions \n"
				+ "WHERE userSSN = '879724-6009' \n" 
				+ "AND deviceId = " + deviceID + ";";
		String update = "UPDATE Devices SET deviceState= ? "
				+ " WHERE deviceId = ? ;";
		if (printMsg) {
			System.out.println("******QUERY****** \n" + query
					+ "\n******************");
			System.out.println("******UPDATE****** \n" + update
					+ "\n******************");
		}
		try {
			res = st.executeQuery(query);
			res.next();
			boolean isAllowed = res.getBoolean("isAllowed");
			if (isAllowed == true) {
				ps = conn.prepareStatement(update);
				ps.setBoolean(1, state);
				ps.setInt(2, deviceID);
				ps.executeUpdate();
				res = st.executeQuery("SELECT devicename, DeviceState \n"
						+ "FROM Devices "
						+ "WHERE deviceId = " + deviceID + ";");
				res.next();
				toggleList.add(res.getString("deviceName"));
				toggleList.add(res.getBoolean("deviceState"));
				addDeviceHistory(deviceID, state);
			}
			if (printMsg) {
				System.out.println("Update Succesful");
			}
		} catch (Exception e) {
			System.out.print(e);
		} 
		return toggleList;
	}

	public String getDeviceAbr(int deviceID) {
		String query = "SELECT deviceAbreviation \n" + "FROM Devices \n"
				+ "WHERE deviceID = " + deviceID + ";";
		System.out.println("******UPDATE****** \n" + query
				+ "\n*****************");
		try {
			res = st.executeQuery(query);// //////////
			res.next();
			if (printMsg) {
				System.out.println("Query Succesful");
			}
			return res.getString("deviceAbreviation");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}
		return "";

	}

	private void addDeviceHistory(int deviceID, boolean state) {

	}

	public int getDevicePin(int deviceID) {
		String query = "SELECT devicePin \n" + "FROM Devices \n"
				+ "WHERE deviceID = " + deviceID + ";";
		if (printMsg) {
			System.out.println("******UPDATE****** \n" + query
					+ "\n*****************");
		}
		try {
			res = st.executeQuery(query);// //////////
			res.next();
			if (printMsg) {
				System.out.println("Query Succesful");
			}
			return res.getInt("devicePin");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
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
		if (printMsg) {
			System.out.println("******UPDATE****** \n" + query
					+ "\n*****************");
		}
		try {
			res = st.executeQuery(query);// //////////
			System.out.println("Query Succesful");
			while (res.next()) {// /////////
				System.out.println(res.getString("devices.deviceName") + " "
						+ res.getString("rooms.roomName") + " "
						+ res.getBoolean("devices.deviceState"));// ////////
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		} finally {
			disconnect();
		}
	}

	public ArrayList checkDevice(int deviceID) {
		
		ArrayList checkList = new ArrayList();
		try {
			res = st.executeQuery("SELECT deviceName,deviceState FROM Devices WHERE deviceId="
					+ deviceID + ";");
			res.next();
			checkList.add(res.getString("Devices.deviceName"));
			checkList.add(res.getBoolean("Devices.deviceState"));
			System.out.println("Update Succesful");
		} catch (Exception e) {
			System.out.print(e);
		} 
		return checkList;
	}

	public ArrayList checkAllDevices() {
		ArrayList checkAllList = new ArrayList();
		try {
			checkAllList.add("checkAllDevices");
			res = st.executeQuery("SELECT deviceName, deviceState FROM Devices WHERE deviceId>0");
			while (res.next()) {
				checkAllList.add(res.getString("deviceName"));
				checkAllList
						.add(Boolean.toString(res.getBoolean("deviceState")));
			}
			if (printMsg) {
				System.out.println("Update Succesful");
			}
		} catch (Exception e) {
			System.out.print(e);
			checkAllList = null;
		}
		return checkAllList;
	}

	public ArrayList testDevice(int deviceID) {
		ArrayList<String> returnArray = new ArrayList<String>();
		String query = "SELECT deviceName, deviceState\n" + "FROM Devices\n"
				+ "WHERE Devices.deviceId = " + deviceID + ";";
		String updateOn = "UPDATE devices\n" + "SET deviceState = true " + "\n"
				+ "WHERE deviceId = " + deviceID;
		String updateOff = "UPDATE devices\n" + "SET deviceState = false "
				+ "\n" + "WHERE deviceId = " + deviceID;
		try {

			res = st.executeQuery(query);
			res.next();
			// boolean isAllowed = res.getBoolean("isAllowed");
			String s = res.getString("Devices.deviceName");
			returnArray.add(s);
			st.executeUpdate(updateOn);
			Thread.sleep(100);
			st.executeUpdate(updateOff);
			returnArray.add("true");
		} catch (Exception e) {
			returnArray.add("false");
			e.printStackTrace();
		}
		return returnArray;
	}

	public boolean isAuthorized(String userSSN, String password) {
		String query = "SELECT userSSN, userPassword\n" + "From users\n"
				+ "Where userSSN = '" + userSSN + "' and userPassword = '"
				+ password + "';";
		if (printMsg) {
			System.out.println("******UPDATE****** \n" + query
					+ "\n*****************");
		}
		try {
			res = st.executeQuery(query);
			String s = "";
			while (res.next()) {
				s = res.getString("userSSN");
			}
			System.out.println("Query Succesful");
			if (s != "") {
				return true;
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		return false;
	}

	public void setSessionKey(String byteArray) {
		
		String update = "UPDATE users\n" + "SET userSessionKey = " + byteArray
				+ "\n" + "WHERE userSSN = '" + user_ssn + "';";
		if (printMsg) {
			System.out.println("******UPDATE****** \n" + update
					+ "\n*****************");
		}
		try {
			st.executeUpdate(update);
			System.out.println("Update Succesful");
		} catch (Exception e) {
			System.out.print(e);
		}
		}

	public ArrayList getSessionKey() {
		
		ArrayList<String> returnArray = new ArrayList<String>();
		String query = "SELECT userSessionKey\n" + "From users\n"
				+ "Where userSSN = '" + user_ssn + "';";
		if (printMsg) {
			System.out.println("******QUERY****** \n" + query
					+ "\n*****************");
		}
		try {
			res = st.executeQuery(query);
			while (res.next()) {
				returnArray.add(res.getString("userSessionKey"));
			}
			System.out.println("Query Succesful");
		} catch (Exception e) {
			System.out.print(e);
		}
		return returnArray;
	}

	public ArrayList getPriority(String command) {
		
		ArrayList<Integer> returnArray = new ArrayList<Integer>();
		String query = "SELECT commandPriority\n" + "From commands\n"
				+ "Where commandName = '" + command + "';";
		if (printMsg) {
			System.out.println("******QUERY****** \n" + query
					+ "\n*****************");
		}
		try {
			res = st.executeQuery(query);
			while (res.next()) {
				returnArray.add(res.getInt("commandPriority"));
			}
			System.out.println("Query Succesful");
		} catch (Exception e) {
			System.out.print(e);
		}
		return returnArray;
	}

	public void insertLogInHistory(String state) {
		String insert = "INSERT INTO LogHistory(logHistoryId,userSSN,state, ip, timestamp)\n"
				+ "VALUES( null , ? , ? , ? , null);";
		if (printMsg) {
			System.out.println("******INSERT****** \n" + insert
					+ "\n*****************");
		}
		try {
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, user_ssn);
			ps.setString(2, state);
			ps.setString(3, "127.0.0.1"); // /////////////// EDIT IP LATER
			ps.executeUpdate();
			if (printMsg) {
				System.out.println("Insert Succesful");
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void insertDeviceHistory(int deviceId, String state) {
		String insert = "INSERT INTO DeviceHistory(deviceHistoryId,userSSN, deviceId ,state,  timestamp)\n"
				+ "VALUES( null , ? , ? , ? , null);";
		if (printMsg) {
			System.out.println("******INSERT****** \n" + insert
					+ "\n*****************");
		}
		try {
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, user_ssn);
			ps.setInt(2, deviceId);
			ps.setString(3, state);
			if (printMsg) {
				System.out.println("Insert Succesful");
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	// FOR MAKING OF NEW QUERIES/UPDATES/INSERTS
	public void defaultQuery() {
		String query = "SELECT roomName " + "From rooms";
		if (printMsg) {
			System.out.println("******QUERY****** \n" + query
					+ "\n*****************");
		}
		try {
			res = st.executeQuery(query);
			while (res.next()) {
				System.out.println(res.getString("roomName"));
			}
			System.out.println("Query Succesful");
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void defaultUpdate() {
		String update = "UPDATE devices\n" 
				+ "SET deviceState = ? \n"
				+ "WHERE deviceId = ?";
		try {
			
			ps = conn.prepareStatement(update);
			ps.setBoolean(1, true);
			ps.setInt(2, 32);
			if (printMsg) {System.out.println("******UPDATE****** \n" + update + "\n*****************");}
			ps.executeUpdate();
			if (printMsg) {System.out.println("Update Succesful");}
		} catch (Exception e) {
			System.out.print(e);
		} 
	}

	public void defaultInsert() {
		String insert = "INSERT INTO course(tablename1,tablename2,tablename3)\n"
				+ "VALUES( ? , ? , ? );";
		if (printMsg) {System.out.println("******INSERT****** \n" + insert + "\n*****************");}
		try {
			ps = conn.prepareStatement(insert);
			ps.setString(1, "Test");
			ps.setString(2, "Test2");
			ps.setString(3, "Test3");
			ps.executeUpdate();
			if (printMsg) {System.out.println("Insert Succesful");}
		} catch (Exception e) {
			System.out.print(e);
		}
	}
}
