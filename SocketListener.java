import java.net.Socket;


public class SocketListener {
	Socket socket;
	int PORT=1111;
	String SERVER="127.0.0.1";
	public static void main(String[] args) {
		SocketListener sl = new SocketListener();
		UserDb userDb = new UserDb();
		sl.socketConnections();
	}
	
	private void socketConnections(){
		
	}
}
