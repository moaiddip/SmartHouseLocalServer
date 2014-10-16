import com.mysql.fabric.xmlrpc.base.Data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.jar.Attributes.Name;
import com.mysql.fabric.xmlrpc.base.Data;

public class AdminDb {
	private String url = "jdbc:mysql://127.0.0.1:3306/";
	private String dbName = "localdatabase";
	private String mySqlDriver = "com.mysql.jdbc.Driver";
	private String userName = "root";
	private String password = "cnncnn22";
	private Connection conn;
	private Statement st;
	private static ResultSet rs;
	private String queryString = "";

	// **********************************************************************************************
	public AdminDb() {
		testConnection();
	} //
		// **********************************************************************************************

	private void testConnection() {
		try {
			// setPermission(2, "9910101437", false);
			getAllPermissionsForUser("9910101437");
			// getDviceHistory();
<<<<<<< HEAD
			//addRoom("Basement");
			//insertUser("9810101547", "password", "name", "fName", "lName", false);
//			test(); //
=======
			// test(); //
>>>>>>> origin/master
		} catch (Exception e) {
			System.out.println("Error in testing connection");
		}
	}

	// **********************************************************************************************
	public void connect() {
		System.out.println("Trying to connect to database . . . ");
		try {
			Class.forName(mySqlDriver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected!!!");
		} catch (Exception e) {
			System.out.println("Couldn't connect.");
			System.out.println(e);
		}
	}

	// **********************************************************************************************
	public void disconnect() {
		try {
			st.close();
			conn.close();
			System.out.println("Disconnected from database.");
		} catch (Exception e) {
			System.out.println("Something is fishy with the connection");
			System.out.println(e);
		}
	} //
		// **************************************************************************************

	public boolean getDeviceState(int inDviceId) throws SQLException {
		connect();
		boolean state = true;
		queryString = "select deviceState from devices where deviceId="
				+ inDviceId;
		st = conn.createStatement();
		rs = st.executeQuery(queryString);
		// iterate through the java resultset
		while (rs.next()) {
			state = rs.getBoolean("deviceState");
			System.out.println("Answer: " + state);
		} // st.close();
		disconnect();
		return state;
	} //
		// ********************************************************************************************

	public void setPermission(int dviceId, String userSSN, boolean isAllowed)
			throws SQLException {
		connect();
		boolean state = true;
		String query = "select isAllowed FROM permissions WHERE userSSN="
				+ userSSN + " AND Permissions.deviceId=" + dviceId;
		String queryStringUpdate = "update permissions set isAllowed="
				+ isAllowed + " where userSSN=" + userSSN + " and deviceId="
				+ dviceId;
		st = conn.createStatement();
		rs = st.executeQuery(query);
		System.out.println("******UPDATE****** \n" + queryStringUpdate
				+ "\n******************");
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next();
			st.executeUpdate(queryStringUpdate);
			System.out.println("Update Succesful");
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			disconnect();
		}
	} //
		// ********************************************************************************************

	public boolean getAllPermissionsForUser(String user_ssn) {
		connect();
		String query = "SELECT permissions.isAllowed, devices.deviceName\n"
				+ "FROM  devices, permissions, users\n"
				+ "WHERE devices.deviceId = permissions.deviceId\n"
				+ "AND users.UserSSN  = " + user_ssn + "\n"
				+ "AND permissions.UserSSN = " + user_ssn + ";"; // later
		// User.ssn
		System.out.println("******Query****** \n" + query
				+ "\n*****************");
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);// //////////
			System.out.println("Query Succesful");
			System.out.println("Device Name\t isAllowed");
			while (rs.next()) {// /////////
				System.out.println(rs.getString("deviceName") + "\t\t "
						+ rs.getBoolean("isAllowed"));// ////////
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		} finally {
			disconnect();
		}
		boolean state = true;
		return state;
	} //
		// ********************************************************************************************

	public ResultSet getDeviceHistory() throws SQLException {
		connect();
		queryString = "select * from deviceHistory";
		st = conn.createStatement();
		rs = st.executeQuery(queryString);
		return rs;
	} //
		// ********************************************************************************************

	public void test() throws SQLException {
		ResultSet result = getDeviceHistory();
		while (rs.next()) {
			int entryId = result.getInt("entryId");
			String userSSN = result.getString("userSSN");
			int deviceId = result.getInt("deviceId");
			boolean state = result.getBoolean("state");
			Date date = result.getDate("date");
			Time time = result.getTime("time");
			System.out.println(entryId + " " + userSSN + " " + deviceId + " "
					+ state + " " + date + " " + time);
		}
		disconnect();
	}
<<<<<<< HEAD

	public void insertUser(String user_ssn, String password, String name,
			String fName, String lName, boolean isAdmin) {
		connect();
		// String insert =
		// "INSERT INTO users(userSSN, userPassword, userName, userFirstname, userLastname, userIsAdmin, root) \n"
		// + "VALUES ("
		// + user_ssn
		// + ",'"
		// + password
		// + "','"
		// + name
		// + "','"
		// + fName
		// + "','"
		// + lName
		// + "',"
		// + isAdmin
		// + ","
		// + false
		// + ");";
		// System.out.println("******INSERT****** \n" + insert
		// + "\n*****************");
		// try {
		// st = conn.createStatement();
		// st.executeUpdate(insert);
		// System.out.println("Insert Succesful");
		// } catch (Exception e) {
		// System.out.print(e);
		// } finally {
		insertDefaultPermission();
		disconnect();
		// }
	}

	// ********************************************************************************************
	private void insertDefaultPermission() {
		// String insert =
		// "INSERT INTO users(userSSN, userPassword, userName, userFirstname, userLastname, userIsAdmin, root) \n"
		// + "VALUES ("
		// + user_ssn
		// + ",'"
		// + password
		// + "','"
		// + name
		// + "','"
		// + fName
		// + "','"
		// + lName
		// + "',"
		// + isAdmin
		// + ","
		// + false
		// + ");";
		// System.out.println("******INSERT****** \n" + insert
		// + "\n*****************");
		// try {
		// st = conn.createStatement();
		// st.executeUpdate(insert);
		// System.out.println("Insert Succesful");
		// } catch (Exception e) {
		// System.out.print(e);
		//
		// }
	}

	// ********************************************************************************************

	public void addDevice() {

	}

	public void addRoom(String roomName) {
		connect();
		String insert = "INSERT INTO rooms(roomId,roomName)\n"
				+ "VALUES (DEFAULT,'" + roomName + "');";
		System.out.println("******INSERT****** \n" + insert
				+ "\n*****************");
		try {
			st = conn.createStatement();
			st.executeUpdate(insert);
			System.out.println("Insert Succesful");
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			disconnect();
		}
	}

	// public ArrayList
	// getDviceHistory2() throws
	// SQLException { // queryString =
	// "select * from deviceHistory"; // st
	// = conn.createStatement(); // rs =
	// st.executeQuery(queryString); //
	// ArrayList al= new ArrayList(); //
	// while (rs.next()) { //
	// al.add(rs.getInt("entryId")); // // }
	// return al; // }

=======
	// ********************************************************************************************
//	 public ArrayList
//	 getDviceHistory2() throws
//	 SQLException { // queryString =
//	 "select * from deviceHistory"; // st
//	 = conn.createStatement(); // rs =
//	 st.executeQuery(queryString); //
//	 ArrayList al= new ArrayList(); //
//	 while (rs.next()) { //
//	 al.add(rs.getInt("entryId")); // // }
//	 return al; // }
>>>>>>> origin/master
}