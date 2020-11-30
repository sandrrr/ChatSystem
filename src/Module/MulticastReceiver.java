package Module;
import java.io.IOException;
import java.net.*;

public class MulticastReceiver extends CommunicationMulticast implements Runnable   {
	 private String adressMulticastIP = "224.0.0.1" ;
	 private MulticastSocket socket ;
	 private byte[] adressMAC ;
	 private InetAddress adressIP ;
	 private String pseudo;
	 private User user;
	 int port_UDP = 2228; //port multicast par default
	 
	 public MulticastReceiver(User user){  
		    super(user) ;
		    this.user = user;
	        this.adressIP = user.getAdressIP() ;
	        this.adressMAC = user.getAdressMAC();
	        this.pseudo = user.getPseudo();
	        try {
	        	 // important that this is a multicast socket
	        	 socket = new MulticastSocket(port_UDP);
	 	         // join by ip
	 	         socket.joinGroup(InetAddress.getByName(adressMulticastIP));
	         }
	         catch(Exception e) {
	        	 e.printStackTrace();
	         }
	      

	 }  
	 public void Received_pseudo() { //when receives a pseudo after this user's demande
		 //update user list
	 }
	 public void Received_Demande_pseudo(String data) throws IOException {//when receives other's getUserList, sends pseudo
		 //sends pseudo
		 System.out.println(data.substring(18, 22)) ;
		 int port_TCP_destination = Integer.parseInt(data.substring(18, 21)); //partie port source de paquet, donc port dest pour envoie de pseudo
		 // envoyer ses données par TCP
	 }
	 
	 public void run(){
        byte[] message = new byte[1024];
        String data;
        DatagramPacket packet = new DatagramPacket(message, message.length);
        System.out.println("MR starts to receive");
        
        
        try {
    		 // important that this is a multicast socket
        	 socket = new MulticastSocket(port_UDP);
 	         socket.joinGroup(InetAddress.getByName(adressMulticastIP)); // join the group
	         socket.receive(packet); // recieve the packet
	        new Thread(new MulticastReceiver(user)).start(); //open a new thread to recieve other messages
        }
        catch(Exception e) {
        	 e.printStackTrace();
        }
        data = new String(packet.getData());
 	    System.out.println("received: " + data);
 	    
 	    
 	    if(data.indexOf("getUserList") != (-1) ) { // protocol est getUserList
 	    	try {
 	    		Received_Demande_pseudo(data);
 	    	}
 	    	 catch(Exception e) {
 	        	 e.printStackTrace();
 	        }
 	    }
 	    if(data.indexOf("returnPseudo") != (-1) ) // protocol est returnPseudo
	    	Received_pseudo();
	    
       }
	       
	 public void close(){ //à utiliser pour fermeture
	        socket.close();
	 }
}
