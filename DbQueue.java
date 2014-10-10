import java.sql.*;

public class DbQueue {
	
	UserDb udb = null;
	AdminDb adb = null;

	public DbQueue(String userOrAdmin,String user_ssn) {//
		if (userOrAdmin.equals("User")) {
			System.out.println("isUser");
			udb = new UserDb(user_ssn);
		} else if (userOrAdmin.equals("Admin")) {
			adb = new AdminDb();
		}
	}

	public DbQueue() {
	}
}
