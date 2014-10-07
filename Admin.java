import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;


public class Admin {
    public static void main(String[] argv) throws Exception{
    	//connect to DataBase;
	    String driverName = "org.gjt.mm.mysql.Driver";
	    Class.forName(driverName);
	    String severName = "localhost";
	    String mydatabase = "mydatabase";
	    
	    String url ="jdbcl://"+severName+ "/"+mydatabase;
	    String adminname = "username";
	    String password = "password";
	    Connection connection = DriverManager.getConnection(url,adminname,password);
	    
	    User user = new User(null, password);
    }
    
	public String getUsers(){
		//create userlist;
		ArrayList <String> list=new ArrayList<String>();
		list.add("");
		return getUsers();
	}
	
	public String getAllPermissionsForUser(){
		return null;
	}
	
	public String getLogHistory(){
		return null;
	}
	
	public String getDeviceHistory(){
		return null;
	}
	
	public void changePermission(){
		
	}
	
	public void createNewUser(){
		//create new user;
		try{
			System.out.println(User.getUsers());
	}
	
	public void deleteUser(){
		
	}
	
	

}

