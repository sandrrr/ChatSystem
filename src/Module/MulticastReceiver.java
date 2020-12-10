package Module;
import java.io.IOException   ;
import java.net.*;

public class MulticastReceiver extends CommunicationMulticast implements Runnable    {
	 private String adressMulticastIP = "224.0.0.1" ;
	 private MulticastSocket socket ;
	 private byte[] adressMAC ;
	 private InetAddress adressIP ;
	 private String pseudo;
	 private User user;
	 int port_UDP = 2302; //port multicast par default
	 
	 
	 public MulticastReceiver(User user, int port_UDP) {  
		    super(user) ;
		    this.user = user;
	        this.adressIP = user.getAdressIP() ;
	        this.adressMAC = user.getAdressMAC();
	        this.pseudo = user.getPseudo();
	        this.port_UDP = port_UDP;
	        try {
	        	 // important that this is a multicast socket
	        	 socket = new MulticastSocket(port_UDP);
	 	         // join by ip
	 	         socket.joinGroup(InetAddress.getByName(adressMulticastIP));
	         }
	         catch(Exception e) {
	        	 e.printStackTrace() ;
	         }
	      

	 }  
	 
	 public MulticastReceiver(User user) {  
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
	        	 e.printStackTrace() ;
	         }
	      

	 }  
	 /*public void Received_pseudo() { //when receives a pseudo after this user's demande
		 //update user list
	 }
	 public void Received_Demande_pseudo(String data, String IP_destination ) throws IOException {//when receives other's getUserList, sends pseudo
		 //sends pseudo
		 System.out.println("received demande from : " + data.substring(18, 22) +" : "+data.substring(23, 35)) ;
		 int port_TCP_destination = Integer.parseInt(data.substring(18, 22)); //partie port source de paquet, donc port dest pour envoie de pseudo
		 new Thread(new TCPsender(IP_destination,port_TCP_destination,this.user)).start();
		 // envoyer ses données par TCP
	 }*/
	 
	 public void run(){
        byte[] message = new byte[1024];
        //String data;
        DatagramPacket packet = new DatagramPacket(message, message.length);
        System.out.println("MulticastReceiver :MR starts to receive");
        
        
        try {
        	while(true) {
        		 socket.receive(packet); // recieve the packet 
        		 System.out.println("MulticastReceiver run "+ new String(packet.getData()) );
        		 String data = new String(packet.getData());
        		 InetAddress IP_dest = packet.getAddress();
        		 int port = packet.getPort();
    	         new Thread(new Communication_Multicast_thread(user,data, IP_dest,port)).start(); //open a new thread to recieve other messages
        	}
    		 // important that this is a multicast socket
	        
        }
        catch(Exception e) {
        	 e.printStackTrace();
        }
        /*data = new String(packet.getData());
 	    System.out.println("received: " + data);
 	    
 	    
 	    if(data.indexOf("getUserList") != (-1) ) { // protocol est getUserList
 	    	try {
 	    		InetAddress IP_dest = packet.getAddress();
 	    		Received_Demande_pseudo(data,IP_dest.getHostAddress());
 	    	}
 	    	 catch(Exception e) {
 	        	 e.printStackTrace();
 	        }
 	    }
 	    
 	    
 	    if(data.indexOf("returnPseudo") != (-1) ) // protocol est returnPseudo
	    	Received_pseudo();
 	    
 	    
 	    
 	   socket.close();*/
	    
       }
}
