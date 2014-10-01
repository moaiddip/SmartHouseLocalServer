import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SocketListener {
	private ServerSocket serversocket=null;
	private Socket socket=null;
	private int PORT=1111;
	private String SERVER="127.0.0.1";
	public static void main(String[] args) {
		SocketListener sl = new SocketListener();
		sl.initilizingSocket();
		sl.handlingSocketConnections();

	}
	
	private void initilizingSocket(){//ok
		try{//ok
			serversocket = new ServerSocket(PORT);
			}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("serversocket = new ServerSocket(PORT) - FAILED in CLASS SocketListener in method initilizingSocket()");
		}
	}
	
	private void handlingSocketConnections(){
		try {
            socket = serversocket.accept();
            LogIn login= new LogIn(socket);
             Thread client =new Thread(login,getIpAdress());
             System.out.println("The user that has connected to the local server has IP number: "+getIpAdress());
             client.start();
        } catch (IOException ex) {
        	ex.printStackTrace();
			System.out.println("serversocket = new ServerSocket(PORT) - FAILED in CLASS SocketListener in method initilizingSocket()");
            Logger.getLogger(SocketListener.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	private String getIpAdress(){
    	String ipAdress=socket.getLocalSocketAddress().toString();
    	System.out.println("socket.getLocalSocketAddress().toString()=="+socket.getLocalSocketAddress().toString());
    	int start=0,end=0;
    	try {
			InetAddress ip=InetAddress.getLocalHost();
			for(int i=0;i<socket.getLocalSocketAddress().toString().length();i++){
				if(ipAdress.charAt(i)=='/'){
					start=i+1;
				}
				if(ipAdress.charAt(i)==':'){
					end=i;
				} 
			}
			socket.getLocalSocketAddress().toString();
			System.out.println("\n\n\nsocket.getLocalSocketAddress().toString()=="+socket.getLocalSocketAddress().toString());
			System.out.println("\n\n\nIP adressen är: "+ip+"\n\n\n");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return ipAdress.substring(start, end);
    }
}