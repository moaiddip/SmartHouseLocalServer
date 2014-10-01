import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class LogIn implements Runnable {
	private DataInputStream serverInput;
	private DataOutputStream serverOutput;
    private boolean userLogin =  false;
    private String userID = "";    
    private String userType = "";    
    private Socket socket;
	public LogIn(Socket socket){
		this.socket=socket;
	}
	
	@Override
	public void run() {//ok
		ArrayList log_in=getInputObjectFromuser();
		if(log_in.size()>1 && authenticationIsCorrect("username", "password")==true){
			User user=new User(socket);
	        Thread login =new Thread(user,"ssn");
	        login.start();
	        Thread.currentThread().stop();//ok
		}else{
			//skicka information att lösenordet är fel
		}
		
	}     
	
	private boolean authenticationIsCorrect(String userName, String password){
		if(userName.equals("username") && password.equals("password")){
			return true;
		}else{
			return false;
		}
		
		
	}
    
    
    public void sendOutputObjectToUser(ArrayList outputList){
		try {
			ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectOutput.writeObject(outputList);
			objectOutput.flush();
		} catch (IOException e) {
			System.out.println();
			e.printStackTrace();
		}
   }
   
    public ArrayList getInputObjectFromuser(){
    	ArrayList inputList;
    	try{
    		ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
    		inputList=(ArrayList) objectInput.readObject();
    	}catch(Exception ex){
    		System.out.println("##  Wrong with input object in class TB_LogInGUI in method - public ArrayList getInputObjectFromuser()  ##");
    		return null;
    	}
    	return inputList;
    }
}   