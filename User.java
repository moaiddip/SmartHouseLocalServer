
public class User implements Runnable {

	DbQueue db_queue = null;

	public User() {//
		db_queue = new DbQueue("User");
		int deviceId = 0;
	}

	private void taskHandler(String task) {
		if (task.equals("lampaPÅ")) {

		}

	}

	public void run() {

	}

	public void getAllAllowedDevice() {

	}

	public void getDevicePin(int deviceId) {

	}

	public boolean toggleDevice(boolean state) {
		boolean statment = false;
		return statment;
	}

	public void getHouseIP() {

	}

	public void getHousePort() {

	}

	public void addDeviceHistory() {

	}
}
