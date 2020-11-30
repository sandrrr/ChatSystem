package Module;

import java.io.IOException;
import java.net.*;


public class MulticastSender extends CommunicationMulticast {
	 private DatagramSocket socket;
	 private String adressMulticastIP = "224.0.0.1";
	 private int port = 2334;
	 private byte[] adressMAC ;
	 private InetAddress adressIP;
	 private String pseudo;
	 
	  

	  
	 public MulticastSender(User user){  
		    super(user);
	        this.adressIP = user.getAdressIP();
	        this.adressMAC = user.getAdressMAC();
	        this.pseudo = user.getPseudo();
	        try {
	        	 // important that this is a multicast socket
	        	 socket = new DatagramSocket();
	         }
	         catch(Exception e) {
	        	 e.printStackTrace() ;
	         }
	      

	 }  
	 public void  send() throws IOException{ //revoie le pseudo de cet utilsiateur
	        try {
	        	// make datagram packet
	            byte[] message = ("Multicasting...").getBytes() ;
	            DatagramPacket packet = new DatagramPacket(message, message.length, 
	                InetAddress.getByName(adressMulticastIP), port);
	            // send packet
	            socket.send(packet);
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	 }
	 
	public void  send_pseudo() throws IOException{ //revoie le pseudo de cet utilsiateur
		Data data = new Data(Data.Protocol.returnPseudo, this.pseudo , this.adressMAC,this.adressIP);
        byte[] message =  data.toString().getBytes();
        System.out.println("MS send,protocol returnPseudo : "+ data.toString() );
        try {
        	 DatagramPacket packet = new DatagramPacket(message, message.length, 
        	 InetAddress.getByName(adressMulticastIP), port);
        	 // send packet
        	 socket.send(packet);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        
	}
	public void  send_getUserList() throws IOException{ //renvoie une demande de getUserlist, si recu cette demande revoie son pseudo
		Data data = new Data(Data.Protocol.getUserList, "", this.adressMAC,this.adressIP);
		byte[] message =  data.toByte();
		try {
       	 DatagramPacket packet = new DatagramPacket(message, message.length, 
       	 InetAddress.getByName(adressMulticastIP), port);
       	 // send packet
       }
       catch(Exception e) {
       	e.printStackTrace();
       }
       System.out.println("MS sended,protocol getUserList");
	}
	
    
}
