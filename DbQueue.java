import java.sql.*;

public class DbQueue {
	
	UserDb udb = null;
	AdminDb adb = null;

	public DbQueue(String userOrAdmin) {//

		
		if (userOrAdmin.equals("user")) {
			udb = new UserDb();
		} else if (userOrAdmin.equals("Admin")) {
			adb = new AdminDb();
		}

	}

	public DbQueue() {
		// TODO Auto-generated constructor stub
	}
}
