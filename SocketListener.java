import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import Protocols.Server;

public class SocketListener {
	private ServerSocket serversocket = null;
	private Socket socket = null;
	private int PORT = 1111;
	private String SERVER = "127.0.0.1";

	public static void main(String[] args) {

//		UserDb u = new UserDb(null); //
//		AdminDb adDb = new AdminDb();
		SocketListener sl = new SocketListener();
		sl.initializingSocket();
		sl.handlingSocketConnections();

	}

//	public void newInitSocket() {// Use this method to create the ServerSocket
//		new Thread(new Server(1234, 0, "keystore.jks", "password", "pwnage12"))
//				.start();
//	}

	private void initializingSocket() {// ok
		try {// ok
			System.out.println("initializingSocket()  - once");
			serversocket = new ServerSocket(PORT);

			
			System.out.println("The Local Server of the SmartHouse runs and is operative\n\n\n");
			}catch(Exception ex){

			ex.printStackTrace();
			System.out
					.println("serversocket = new ServerSocket(PORT) - FAILED in CLASS SocketListener in method initilizingSocket()");
		}
	}

	private void handlingSocketConnections() {
		while (true) {
			try {
				socket = serversocket.accept();
				LogIn login = new LogIn(socket, getIpAdress());
				Thread client = new Thread(login, getIpAdress());
				System.out
						.println("The user that has connected to the local server has IP number: "
								+ getIpAdress());
				System.out.println("handlingSocketConnections() - once");
				client.start();
			} catch (IOException ex) {
				ex.printStackTrace();
				System.out
						.println("serversocket = new ServerSocket(PORT) - FAILED in CLASS SocketListener in method initilizingSocket()");
				Logger.getLogger(SocketListener.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	private String getIpAdress() {
		InetAddress ip;
		String ipAdress = socket.getRemoteSocketAddress().toString();
		int start = 0, end = 0;
		try {
			ip = InetAddress.getLocalHost();
			for (int i = 0; i < socket.getLocalSocketAddress().toString()
					.length(); i++) {
				if (ipAdress.charAt(i) == '/') {
					start = i + 1;
				}
				if (ipAdress.charAt(i) == ':') {
					end = i;
				}
			}
			socket.getLocalSocketAddress().toString();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		return ipAdress.substring(start, end);
	}
}

