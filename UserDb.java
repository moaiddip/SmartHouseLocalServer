import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class UserDb {
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "demo";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "mypassword";
	Connection conn;
	Statement st;
	ResultSet res;


	public void connect() {
		System.out.print("Trying to connect . . . ");

		try {
			//Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url + dbName,
					userName, password);
			// Statement st = conn.createStatement();
			// ResultSet res = st.executeQuery("SELECT * FROM  event");
			// while (res.next()) {
			// int id = res.getInt("id");
			// String msg = res.getString("msg");
			// System.out.println(id + "\t" + msg);
			// }
			// int val = st.executeUpdate("INSERT into event VALUES(" + 1 + ","
			// + "'Easy'" + ")");
			// if (val == 1)
			System.out.println("Connected!!!");
		} catch (Exception e) {
			System.out.println(e);
		}	
	}

	public void disconnect() {
		System.out.println("Disconnected.");
		try {
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
