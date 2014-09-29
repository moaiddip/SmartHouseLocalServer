public class LogIn {

    private boolean userLogin =  false;    
    private String userID = "";    
    private String userType = "";    

	public LogIn(){
	}
	
	public void resetVariable() {
		setUserLogin(false);
		setUserID("");
		setUserType("");
	}
	
    public void setUserLogin(boolean userLogin) {
        this.userLogin = userLogin;
    }
    
    public boolean isUserLogin() {
        return userLogin;
    }
    
    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public String getUserID() {
        return userID;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public String getUserType () {
        return userType;
    }     
    
}