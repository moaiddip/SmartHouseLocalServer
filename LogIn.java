import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.Client;

public class LogIn implements Runnable {
	DataInputStream serverInput;//PRELIMINARY PROTOCOL FUNCTION
    DataOutputStream serverOutput;//PRELIMINARY PROTOCOL FUNCTION
    private boolean userLogin =  false;
    private String userID = "";    
    private String userType = "";    
    private Socket socket;
    private String client_ip=null;
    private boolean clinetIsConnected=false;
	public LogIn(Socket socket,String ip){
		this.socket=socket;
		this.client_ip=ip;
		this.clinetIsConnected=true;
	}
	

	public void run() {//ok
		while(clinetIsConnected==true){
			try{
				loginHandler(getInputObjectFromUser());
			}catch(Exception ex){
				ex.printStackTrace();
				clinetIsConnected=false;
				System.out.println("\n\nClient with the ip number: "+client_ip+" has disconnected from the server\n\n");
				 Thread.currentThread().stop();
			}
		}		
	}    
	
	
	
	private void loginHandler(ArrayList usernamePassword){
			try{
				if(authenticationIsCorrect(usernamePassword.get(0).toString(), usernamePassword.toString())==true){
					User user=new User(socket,"870724-1234");
			        Thread login =new Thread(user,"ssn");
			        login.start();
			        Thread.currentThread().stop();//ok
				}else{
					ArrayList result = new ArrayList();
					result.add("Wrong password or username");
					sendOutputObjectToUser(result);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}
	
	private boolean authenticationIsCorrect(String userName, String password){
		if(userName.equals("username") && password.equals("password")){
			return true;
		}else{
			return false;
		}
		
		
	}
    
    
	public void sendOutputObjectToUser(ArrayList outputList){//PRELIMINARY PROTOCOL FUNCTION
		try {
			ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectOutput.writeObject(outputList);
			objectOutput.flush();
		} catch (IOException ex) {
			System.out.println("\n\n\n####################################\nWrong with: Class Login - sendOutputObjectToUser()\n####################################\n\n\n");
			/*System.out.print("\nprintStackTrace()==");
			ex.printStackTrace();*/
		}
    }
    
    public ArrayList getInputObjectFromUser(){//PRELIMINARY PROTOCOL FUNCTION
    	ArrayList inputList;
    	try{
    		ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
    		inputList=(ArrayList) objectInput.readObject();
    	}catch(Exception ex){
    		System.out.println("\n\n\n####################################\nWrong with: Class Login - getInputObjectFromUser()\n####################################\n\n\n");
			/*System.out.print("\nprintStackTrace()==");
			ex.printStackTrace();*/
    		clinetIsConnected=false;
			return null;
    	}
    	return inputList;
    }
}   