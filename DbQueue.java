import java.sql.*;

public class DbQueue {
	
	UserDb udb = null;
	AdminDb adb = null;

	public DbQueue(String userOrAdmin,String user_ssn) {//
		if (userOrAdmin.equals("user")) {
//			udb = new UserDb(user_ssn);
		} else if (userOrAdmin.equals("Admin")) {
			adb = new AdminDb();
		}
	}

	public DbQueue() {
		// TODO Auto-generated constructor stub
	}
	
	
	//we have to think how we are going to solve the queue with diffrent priority values :S
}
