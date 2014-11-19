import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;


public class Admin {
	DataInputStream serverInput;// PRELIMINARY PROTOCOL FUNCTION
	DataOutputStream serverOutput;// PRELIMINARY PROTOCOL FUNCTION
	DbQueue db_queue = null;
	private String user_ssn = null;
	static ArdConnector ar;
	
	private static final String ARDUINO_COMPORT = "COM26";

	private boolean userConnected = false;
	
	public Admin(){
		this.userConnected = true;
		db_queue = new DbQueue("DEFAULT Admin");
		System.out.println("");
		ar = new ArdConnector(ARDUINO_COMPORT);
		ar.start();
		
	}		
		
		

	
	public void taskHandler(ArrayList task){
		System.out.println("taskHandler");
		for(int i=0;i<task.size();i++){
			System.out.println("task.get(i)=="+task.get(i));
		}
	    if(task.get(0).equals("toggleDevice")&& task.size()==3){
	    	long startTime = System.currentTimeMillis();
			String prefix = db_queue.udb.getDeviceAbr(Integer.parseInt(task.get(1).toString()));
			String suffix;
			boolean bool = Boolean.parseBoolean(task.get(2).toString());
			//suffix = bool == true ? "on" : "off";
			if(bool == true){
				suffix = "on";
			} else {
				suffix = "off";
			}
			String cmd = prefix + "_" + suffix + "!";			
			System.out.println("cmd: " + cmd);
			System.out.println(System.currentTimeMillis() - startTime);

			ar.setCommand(cmd);
			return "toggleDevice";
			
			
	    }else if(task.get(0).equals("checkDevice") && task.isEmpty()==false && task.size()==2)
	    {
		
	    	String print=converArrayListToString(db_queue.adb.checkDevice(Integer.parseInt(task.get(1).toString())));
		
		
		System.out.println("checkDevice("+Integer.parseInt(task.get(1).toString())+")=="+print);
		
		return print;
		
	}else if(task.get(0).equals("testDevice") && task.isEmpty()==false && task.size()==2)
	{
		return convertArrayListToString(db_queue.adb.testDevice(Integer.parseInt(task.get(1).toString())));
		
	}else if(task.get(0).equals("checkAllDevices") && task.isEmpty()==false && task.size()==1){
		   
		return convertArrayListToString(db_queue.adb.checkAllDevices());
	}
	
	    return("Totally Wrong:");
	
	    if (task.get(0).equals("getDeviceState")) {// reacts on if-stmt
			try {
				
				db_queue.adb.getDeviceState(Integer.parseInt(task.get(1).toString()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}
	
			if (task.get(0).equals("getAllPermissionsForUser")) {
				db_queue.adb.getAllPermissionsForUser(user_ssn);
				db_queue.adb((int)task.get(0),(String)task.get(1),(String)task.get(2),(String)task.get(3),(String)task.get(4),(boolean)task.get(5),(double)task.get(6));
				
			}
			if (task.get(0).equals("getDeviceHistory")) {

					db_queue.adb.getDeviceHistory();
				} 
			if(task.get(0).equals("setPermission")){
				db_queue.adb.setPermission(user_ssn);
				
				db_queue.adb((int)task.get(0),(String)task.get(1),(String)task.get(2),(String)task.get(3),(String)task.get(4),(boolean)task.get(5),(double)task.get(6));
			}
				
			if(task.get(0).equals("addRoom")){
				db_queue.adb.addRoom(roomName);
				
					}
				}
				
				
				
				
			}
	    
	    
}
	
	
	
	private String convertArrayListToString(ArrayList arrayList) {
		String stringList = "";
		int lastSeparator = -1;
		for (int i = 0; i < arrayList.size(); i++) {
			stringList = stringList + arrayList.get(i) + ":";
		}
		return stringList;
	}
	
}
	
	
	
	
	
	
	
	/*public class Admin implements Runnable {
	DataInputStream serverInput;// PRELIMINARY PROTOCOL FUNCTION
	DataOutputStream serverOutput;// PRELIMINARY PROTOCOL FUNCTION
	DbQueue db_queue = null;
	private String user_ssn = null;

	private boolean userConnected = false;
	
	public void Admin() {
		
		this.userConnected = true;
		this.user_ssn = user_ssn;
		db_queue = new DbQueue("Admin", this.user_ssn);
	}

	public void run() {// As long as user is connected the taskhandler will wait
		// for instructions from user to calculate and return
		// answer
		while (userConnected == true) {
			try {
				taskHandler(convertStringToArrayList(getStringInputFromUser()));
			} catch (Exception ex) {
				ex.printStackTrace();
				userConnected = false;
			}
		}
	}

	private void taskHandler(ArrayList task) {
		System.out.println("taskHandler");
		if (task.get(0).equals("getDeviceState")) {// reacts on if-stmt
			try {
				
				db_queue.adb.getDeviceState(Integer.parseInt(task.get(1).toString()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
			if (task.get(0).equals("getAllPermissionsForUser")) {
				db_queue.adb.getAllPermissionsForUser(user_ssn);
				db_queue.adb((int)task.get(0),(String)task.get(1),(String)task.get(2),(String)task.get(3),(String)task.get(4),(boolean)task.get(5),(double)task.get(6));
				
			}
			if (task.get(0).equals("getDeviceHistory")) {

					db_queue.adb.getDeviceHistory();
				} 
			if(task.get(0).equals("setPermission")){
				db_queue.adb.setPermission(user_ssn);
				db_queue.adb((int)task.get(0),(String)task.get(1),(String)task.get(2),(String)task.get(3),(String)task.get(4),(boolean)task.get(5),(double)task.get(6));
			}
				
			if(task.get(0).equals("")){
				
			}
			     
			
			ArrayList send = new ArrayList();
			// sendStringOutputToUser(db_queue.adb.metod(parameter));// sends
			// arrayList
		}
	

	private String getStringInputFromUser() {
		System.out.println(Thread.currentThread().getName()
				+ " är inne i getInputFromUser() ");
		String input = "";
		try {
			serverInput = new DataInputStream(socket.getInputStream());
			input = serverInput.readUTF();
			return input;
		} catch (IOException e) {
			e.printStackTrace();
			System.out
					.println("\n\nERROR - Class: User(Local Server)\nMethod: private String getStringInputFromUser()");
		}
		return input;
	}

	private void sendStringOutputToUser(String output) {
		System.out.println(Thread.currentThread().getName()
				+ "  Är inne i sendOutputToUser(String output) ");
		try {
			serverOutput = new DataOutputStream(socket.getOutputStream());
			serverOutput.writeUTF(output);
			serverOutput.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.out
					.println("\n\nERROR - Class: Admin(Local Server)\nMethod: private String sendStringOutputToAdmin(String output)");
		}
	}

	private ArrayList convertStringToArrayList(String stringList) {
		ArrayList taskList = new ArrayList();
		int lastSeparator = -1;
		for (int i = 0; i < stringList.length(); i++) {
			if (stringList.charAt(i) == ':') {
				taskList.add(stringList.subSequence(lastSeparator + 1, i));
				lastSeparator = i;
			}
		}
		return taskList;
	}

	private String convertArrayListToString(ArrayList arrayList) {
		String stringList = "";
		int lastSeparator = -1;
		for (int i = 0; i < arrayList.size(); i++) {
			stringList = stringList + arrayList.get(i) + ":";
		}
		return stringList;
	}
}*/
	
	
	
	
	
	
	
	
	
	
	
	
	/*public void taskHandler(ArrayList task) {
		System.out.println("taskHandler");
		if (task.get(0).equals("")) {// reacts on if-stmt
			//db_queue.adb.någonmetod();
			ArrayList send = new ArrayList();
			//sendStringOutputToUser(db_queue.adb.metod(parameter));// sends arrayList
		}
		if (task.get(0).equals("insertUser")) {// reacts on if-stmt
			//db_queue.adb.någonmetod();
			ArrayList send = new ArrayList();
			//sendStringOutputToUser(db_queue.adb.insertUser((String)task.get(1),(int)task.get(2),(boolean)task.get(3),(double)task.get(4))));// sends arrayList
		}
	}

	private String convertArrayListToString(ArrayList arrayList) {
		String stringList = "";
		int lastSeparator = -1;
		for (int i = 0; i < arrayList.size(); i++) {
			stringList = stringList + arrayList.get(i) + ":";
		}
		return stringList;
	}
}*/
