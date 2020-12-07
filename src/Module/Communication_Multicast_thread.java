package Module  ;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Communication_Multicast_thread implements Runnable {
	 private User user;
	 //private DatagramPacket packet;
	 private int port;
	 private InetAddress IP_dest;
	 private String data;
	 public Communication_Multicast_thread(User user,String data,InetAddress IP_dest, int port) {  
		    this.user = user;
		    this.IP_dest = IP_dest;
		    this.data = data;
		    this.port = port;
	 }  
	 public void Received_pseudo() { //when receives a pseudo after this user's demande
		 //update user list
	 }
	 
	 
	 
	 
	 public void Received_Demande_pseudo(String data, String IP_destination ) throws IOException {//when receives other's getUserList, sends pseudo
		 //sends pseudo
		 String MAC = data.substring(0,data.indexOf(":"));
		 //System.out.println(MAC);
		 String pseudo_src = data.substring(data.indexOf(":",28)+1,data.indexOf(":",35));
		 //System.out.println(pseudo_src);
		 int port_src = Integer.parseInt(data.substring(data.indexOf(":")+1, data.indexOf(":",19)));
		 System.out.println("Test :Communication_Multicast_thread :received demande from : " + MAC +" : "+pseudo_src) ;
		 //int port_TCP_destination = Integer.parseInt(data.substring(18, 22)); //partie port source de paquet, donc port dest pour envoie de pseudo
		 new Thread(new TCPsender(IP_destination,port_src,this.user)).start();
		 // envoyer ses données par TCP
		 try {
			 Thread.sleep(1000); //make sure that TCP has collected all other users
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		 //check if the pseudo is used by someone else
		 System.out.println("Communication_Multicast_thread :" + UserList.Verify_pseudo(pseudo_src));
		 if(UserList.Verify_pseudo(pseudo_src)) { //true if nobody uses this name
			 User new_user = new User(pseudo_src, port_src , IP_dest, MAC);
			 UserList.add(new_user);
			 System.out.println("Communication_Multicast_thread  successfully ajouté");
		 }
	 }
	 
	 
	 
	 
	 
     public void run() {
    	
  	    System.out.println("Communication_Multicast_thread received: " + data);
  	     
  	    
  	    if(data.indexOf("getUserList") != (-1) ) { // protocol est getUserList
  	    	try {
  	    		Received_Demande_pseudo(data,IP_dest.getHostAddress());
  	    	}
  	    	 catch(Exception e) {
  	        	 e.printStackTrace();
  	        }
  	    }
  	    
  	    
  	    if(data.indexOf("returnPseudo") != (-1) ) // protocol est returnPseudo
 	    	Received_pseudo();
  	    
     }
}
