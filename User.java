import java.net.Socket;


public class User implements Runnable {

	DbQueue db_queue = null;
	private Socket socket=null;
	public User(Socket socket) {//
		db_queue = new DbQueue("User");
		this.socket=socket;
		int deviceId = 0;
	}

	private void taskHandler(String task) {
		if (task.equals("lampaPå")) {

		}

	}

	public void run() {

	}
	
	
}
