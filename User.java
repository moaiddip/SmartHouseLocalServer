import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class User implements Runnable {
	DataInputStream serverInput;//PRELIMINARY PROTOCOL FUNCTION
    DataOutputStream serverOutput;//PRELIMINARY PROTOCOL FUNCTION
	DbQueue db_queue = null;
	private String user_ssn=null;
	
	private boolean userConnected=false;
	private Socket socket=null;
	public User(Socket socket,String user_ssn) {//
		this.socket=socket;
		this.userConnected=true;
		this.user_ssn=user_ssn;
		db_queue = new DbQueue("User",this.user_ssn);
		//maybe send all important info to user like, name, ssn,permissions etc
	}

	
	
	public void run() {//As long as user is connected the taskhandler will wait for instructions from user to calculate and retrun answer
		while(userConnected==true){
			try{
				taskHandler(getInputObjectFromUser());
			}catch(Exception ex){
				ex.printStackTrace();
				userConnected=false;
			}
		}
	}
	
	private void taskHandler(ArrayList task) {
		if (task.get(0).equals("lightsOn")) {//reacts on if-stmt
			ArrayList send = new ArrayList();
			//send.add(db_queue.udb.toggleDevice((int)task.get(1)));//calls the method to preform actions and stores the confirmation in the arrayList, task.get(1) is an int(deviceID).
			sendOutputObjectToUser(send);//sends arrayList
		}

	}
	
	public void sendOutputObjectToUser(ArrayList outputList){//PRELIMINARY PROTOCOL FUNCTION
		try {
			ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectOutput.writeObject(outputList);
			objectOutput.flush();
		} catch (IOException ex) {
			System.out.println("\n\n\n####################################\nWrong with: Class User - sendOutputObjectToUser()\n####################################\n\n\n");
			System.out.print("\nprintStackTrace()==");
			ex.printStackTrace();
		}
    }
    
    public ArrayList getInputObjectFromUser(){//PRELIMINARY PROTOCOL FUNCTION
    	ArrayList inputList;
    	try{
    		ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
    		inputList=(ArrayList) objectInput.readObject();
    	}catch(Exception ex){
    		System.out.println("\n\n\n####################################\nWrong with: Class User - getInputObjectFromUser()\n####################################\n\n\n");
			System.out.print("\nprintStackTrace()==");
			ex.printStackTrace();
			return null;
    	}
    	return inputList;
    }
	
}
