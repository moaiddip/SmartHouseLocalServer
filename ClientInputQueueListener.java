import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

import Protocols.ACQueue;
import Protocols.Item;
import Protocols.Server;
import Protocols.WriteQueue;

	
public class ClientInputQueueListener {
	private WriteQueue wq;
	private Item its;
	private Server server;
	private LogIn login;
	private User user;
	private Admin admin;
	ACQueue ac = new ACQueue();
	private static AtomicBoolean updated = new AtomicBoolean(true);
	private HashMap<Integer,Item> commandQueue;
	public ClientInputQueueListener(){
		this.its = new Item();
		this.server= new Server(60000, 0, "keystore.jks", "password", "pwnage12");
		Thread connectionListener = new Thread(server,"Server");
		
		connectionListener.start();
		this.wq = server.getTheQueue();
		this.user = new User();
 		this.admin = new Admin();
 		this.login = new LogIn();
		
		System.out.println("Object av protokollet är skapade");
	}
	
	public static void main(String[] args) {
		ClientInputQueueListener ciq = new ClientInputQueueListener();
		System.out.println("ciq.commandsReceiver();");
		ciq.commandsReceiver();
	}
	
	private HashMap getUpdatedHashMapQueue(){
		Map<Integer, Item> items = wq.returnMap();
		Map<Integer, Item> newQueue = new HashMap<Integer, Item>();
		int index=0;
		for(java.util.Map.Entry<Integer, Item> entry : items.entrySet()) {		
			newQueue.put(index, entry.getValue());
			System.out.println("Values : " + entry.getValue().getState()+" ,"+entry.getValue().getMsg()+" ,"+entry.getValue().getReply()+" ,"+entry.getValue().getAddress()+" ,"/*+entry.getValue().getSessionKey()+" ,"*/+entry.getValue().getUserPrio()+" ,"+entry.getValue().getPriority());
		}
		
		return (HashMap)newQueue;
	}
	
	private HashMap quickSortHashMap(HashMap unsorted){
		HashMap sorted = new HashMap();
		
		return sorted;
	}
	
	public HashMap converArrayToHashMap(Item[] hasharray) {
		  Map<Integer, Item> sortedMap = new HashMap<Integer, Item>();
		  for (int i = 0; i < hasharray.length; i++) {
		   sortedMap.put(i, hasharray[i]);
		  }
		  return (HashMap) sortedMap;
    }

	 public Item[] TESTconvertHashmapToArray(Map<Integer, Item> items) {
		  Item[] hashArray = new Item[TESTgetSizeOfHashMap(items)];
		  int temp = -1, keyTemp = -1;
		  int i = 0;
		  for (java.util.Map.Entry<Integer, Item> entry : items.entrySet()) {
		   hashArray[i] = entry.getValue();
		   i++;
		  }
		  return hashArray;
		 }
	
	public int TESTgetSizeOfHashMap(Map<Integer, Item> treeMap){
		int size=0;
		for (java.util.Map.Entry<Integer, Item> entry : treeMap.entrySet()) {
			size ++;
		}
		//System.out.println("size: " + size);
		return size;
	}
	
	public Item[] bubbleSort( Item[] hashArray){
		Item temp;
		for (int i = 0; i < hashArray.length; i++) {
	        for (int j = 1; j < (hashArray.length - i); j++) {
	            if (hashArray[j - 1].getPriority() > hashArray[j].getPriority()) {
	                temp = hashArray[j - 1];
	                hashArray[j - 1] = hashArray[j];
	                hashArray[j] = temp;
	            }

	        }
	    }
		return hashArray;
	}
	
	public void printMap(Map<Integer, Item> items) {
		System.out.println("\n\n\n***************************************************\n");
		int temp=-1,keyTemp=-1;
		for(java.util.Map.Entry<Integer, Item> entry : items.entrySet()) {		
			System.out.println("Values : " + entry.getValue().getState()+" ,"+entry.getValue().getMsg()+" ,"+entry.getValue().getReply()+" ,"+entry.getValue().getAddress()+" ,"/*+entry.getValue().getSessionKey()+" ,"*/+entry.getValue().getUserPrio()+" ,"+entry.getValue().getPriority());
		}
	}
	//Map.Entry<String, String> studentEntry : studentGrades.entrySet()
	 
	private void checkForUpdates(){
		/*System.out.println("ac.getHasAddedCommands() = "+ac.getHasAddedCommands());
		System.out.println("wq.getHasAddedCommands() == "+wq.getHasAddedCommands());
		System.out.println("wq.returnMap().size() == "+wq.returnMap().size());
		System.out.println("commandQueue.size() == "+commandQueue.size());*/
		if(wq.getHasAddedCommands()==true || wq.returnMap().size()!=commandQueue.size()){//stop executing and update hasmap and sort it
			commandQueue=converArrayToHashMap(bubbleSort(TESTconvertHashmapToArray(wq.returnMap())));
			System.out.println("\n\ninne i : if(wq.getHasAddedCommands()==updated)\n\n");
			wq.setHasAddedCommands(false);
		}
	}
	
	private void authenticationForUnauthorizedClients(int i){
		if(((Item) commandQueue.get(i)).getState()==true && ((Item) commandQueue.get(i)).isAnswered()==false && ((Item) commandQueue.get(i)).getUser()==null && wq.getHasAddedCommands()==false){
			System.out.println("2.1");
			System.out.println("\n\n((Item) commandQueueget(i)).getUser() == "+((Item) commandQueue.get(i)).getUser());
			System.out.println("((Item) commandQueueget(i)).isAnswered() == "+((Item) commandQueue.get(i)).isAnswered()+"\n\n");
			System.out.println("2.2");
			ArrayList authentication=login.getSSN_WhenClientHasSuccessfullyLoggedIn(commandQueue.get(i).getMsg());
			if(!authentication.get(0).equals("FAILD")){
				((Item) commandQueue.get(i)).setUser(authentication.get(0).toString());//login
				((Item) commandQueue.get(i)).setUserPrio((int)authentication.get(1));
				System.out.println("2.3  -  logged in");
				((Item) commandQueue.get(i)).setReply("username_password_");
				((Item) commandQueue.get(i)).setPriority(3);
				//((Item) wq.returnMap().get(0)).makeOld();
			}
		}		
	}
	
	private void handleAuthorizedUsers(int i){
		/*System.out.println("((Item) commandQueue.get(i)).getAddress() == "+((Item) commandQueue.get(i)).getAddress());
		System.out.println("authorizedUsers(int "+i+")");
		System.out.println("((Item) commandQueue.get("+i+")).isAnswered() == "+((Item) commandQueue.get(i)).isAnswered());
		System.out.println("((Item) commandQueue.get("+i+")).getUserPrio() == "+((Item) commandQueue.get(i)).getUserPrio());
		System.out.println("((Item) commandQueue.get("+i+")).getUser() == "+((Item) commandQueue.get(i)).getUser());
		System.out.println("commandQueue.size() == "+commandQueue.size());										  //if isAnswered is commented, taskhandler shows up
		*/if(((Item) wq.returnMap().get(i)).getUserPrio()==5 && ((Item) wq.returnMap().get(i)).getUser()!=null && ((Item) wq.returnMap().get(i)).isAnswered()==false && wq.getHasAddedCommands()==false){//user
			System.out.println("3.1");
			String replay=user.taskHandler(convertStringToArrayList(((Item) commandQueue.get(i)).getMsg()));
			Item itemz = (Item) commandQueue.get(i);
			System.out.println("message "+commandQueue.get(i).getMsg());
			System.out.println("reply "+replay);
			itemz.setReply(replay);
		}
	}
//	
	private void authorizedAdmins(int i){
		if(((Item) wq.returnMap().get(i)).getUserPrio()==10 && ((Item) wq.returnMap().get(i)).getUser()!=null && ((Item) wq.returnMap().get(i)).isAnswered()==false && wq.getHasAddedCommands()==false){//admin
			System.out.println("3.3");
			System.out.println("\n\nINNE I admin-if satsen\n\n");
			admin.taskHandler(convertStringToArrayList(((Item) wq.returnMap().get(i)).getMsg()));
		}
	}
	
	private void commandsReceiver(){
		System.out.println("inne i CommandsRecevier");
		commandQueue=wq.returnMap();
		System.out.println("Kollar om listan är tom");
		while(1<2){//preliminary
			if(wq.returnMap().isEmpty()==false && its.getState() &&its.isAnswered()==false){
				for(int i=0;i<wq.returnMap().size();i++){
					checkForUpdates();
					authenticationForUnauthorizedClients(i);
					handleAuthorizedUsers(i);
					authorizedAdmins(i);
				}
			}
		}
	}
	
	private ArrayList convertStringToArrayList(String stringList) {
		ArrayList taskList = new ArrayList();
		int lastSeparator = -1;
		for (int i = 0; i < stringList.length(); i++) {
			if (stringList.charAt(i) == '_') {
				taskList.add(stringList.subSequence(lastSeparator + 1, i));
				lastSeparator = i;
			}
		}
		return taskList;
	}
}



//commandQueue.get(Integer.valueOf(i)).getState()==true && commandQueue.get(Integer.valueOf(i)).isAnswered()==false













/*
    
     private void checkForUpdates(){
		if(wq.getHasAddedCommands()==true){//stop executing and update hasmap and sort it
			commandQueue=converArrayToHashMap(bubbleSort(TESTconvertHashmapToArray(wq.returnMap())));
			System.out.println("\n\ninne i : if(wq.getHasAddedCommands()==updated)\n\n");
			wq.setHasAddedCommands(false);
		}
	}
	
	private void handleUnauthorizedClients(int i){
		if(((Item) wq.returnMap().get(i)).getState()==true && ((Item) wq.returnMap().get(i)).isAnswered()==false && ((Item) wq.returnMap().get(i)).getUser()==null){
			System.out.println("2.1");
			System.out.println("\n\n((Item) wq.returnMap().get(i)).getUser() == "+((Item) wq.returnMap().get(i)).getUser());
			System.out.println("((Item) wq.returnMap().get(i)).isAnswered() == "+((Item) wq.returnMap().get(i)).isAnswered()+"\n\n");
			System.out.println("2.2");
			ArrayList authentication=login.getSSN_WhenClientHasSuccessfullyLoggedIn(commandQueue.get(i).getMsg());
			if(!authentication.get(0).equals("FAILD")){
				((Item) wq.returnMap().get(i)).setUser(authentication.get(0).toString());//login
				((Item) wq.returnMap().get(i)).setUserPrio((int)authentication.get(1));
				System.out.println("2.3  -  logged in");
				((Item) wq.returnMap().get(i)).setReply("Authentication_ok_ok!");
				((Item) wq.returnMap().get(i)).setPriority(3);
			}
		}		
	}
	
	private void handleAuthorizedUsers(int i){
		System.out.println("authorizedUsers(int "+i+")");
		System.out.println("((Item) wq.returnMap().get("+i+")).isAnswered() == "+((Item) wq.returnMap().get(i)).isAnswered());
		System.out.println("((Item) wq.returnMap().get("+i+")).getUserPrio() == "+((Item) wq.returnMap().get(i)).getUserPrio());
		System.out.println("((Item) wq.returnMap().get("+i+")).getUser() == "+((Item) wq.returnMap().get(i)).getUser());
		if(((Item) wq.returnMap().get(i)).getUserPrio()==5 && ((Item) wq.returnMap().get(i)).getUser()!=null &&((Item) wq.returnMap().get(i)).isAnswered()==false){//user
			System.out.println("3.1");
			String replay=user.taskHandler(convertStringToArrayList(((Item) wq.returnMap().get(i)).getMsg()));
			if(!replay.equals("FAILD")){
				((Item) wq.returnMap().get(i)).setReply(replay);
			}
		}
	}
	
	private void authorizedAdmins(int i){
		if(((Item) wq.returnMap().get(i)).getUserPrio()==10 && ((Item) wq.returnMap().get(i)).getUser()!=null && ((Item) wq.returnMap().get(i)).isAnswered()==false){//admin
			System.out.println("3.3");
			System.out.println("\n\nINNE I admin-if satsen\n\n");
			admin.taskHandler(convertStringToArrayList(((Item) wq.returnMap().get(i)).getMsg()));
		}
	}
	
	private void commandsReceiver(){
		System.out.println("inne i CommandsRecevier");
		commandQueue=wq.returnMap();
		System.out.println("Kollar om listan är tom");
		while(1<2){//preliminary
			if(wq.returnMap().isEmpty()==false && its.getState() &&its.isAnswered()==false){
				for(int i=0;i<wq.returnMap().size();i++){
					checkForUpdates();
					handleUnauthorizedClients(i);
					handleAuthorizedUsers(i);
					authorizedAdmins(i);
				}
			}
		}
	}
 
  
  //####################################################################
  */
  
  
  
  
  
  
  
  
  
  
  
  
  
  
