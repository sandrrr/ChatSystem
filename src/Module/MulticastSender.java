package Module;

import java.io.IOException   ;
import java.net.*;

import Module.Data.Protocol;


public class MulticastSender extends CommunicationMulticast   {
	 private DatagramSocket socket;
	 private String adressMulticastIP = "224.0.0.1" ;
	 private int port_TCP ; 
	 private int  port_UDP = 2302 ;
	 private byte[] adressMAC ;
	 private InetAddress adressIP;
	 private String pseudo;
	 
	 private enum Protocol  {
	        getUserList,
	        activeUser,
	        inactiveUser,
	        editUsernamen
	    }

	 public String DatatoString(String data, Protocol protocol, int port_source)  {
	    	StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < this.adressMAC.length; i++) {
	            sb.append(String.format("%02X%s", this.adressMAC[i], (i < this.adressMAC.length - 1) ? "-" : ""));        
	        }
	        return (sb.toString()+ ":" +port_source +":"+protocol  +":" + pseudo +":" + data) ;
	 }
	 public MulticastSender(User user,int port_UDP) {  
		    super(user);
	        this.adressIP = user.getAdressIP();
	        this.adressMAC = user.getAdressMAC();
	        this.pseudo = user.getPseudo();
	        this.port_TCP = user.getPort_tcp();
	        this.port_UDP = port_UDP;
	 }  
	 public MulticastSender(User user) {  
		    super(user); 
	        this.adressIP = user.getAdressIP();
	        this.adressMAC = user.getAdressMAC();
	        this.pseudo = user.getPseudo();
	        this.port_TCP = user.getPort_tcp();
	 }  
	//TCP's job
	/*public void  send_pseudo() throws IOException{ //revoie le pseudo de cet utilsiateur
		byte[] message =  DatatoString(this.pseudo, Protocol.returnPseudo, port_TCP).getBytes();
		System.out.println("MS send,protocol returnPseudo" );
		  
		 socket = new DatagramSocket();
		 DatagramPacket packet = new DatagramPacket(message, message.length, 
		 InetAddress.getByName(adressMulticastIP), port_UDP);
		 // send packet
		 socket.send(packet);
		 socket.close();
        
	}*/
	public void  send_getUserList() throws IOException{ //renvoie une demande de getUserlist, si recu cette demande revoie son pseudo
		byte[] message =  DatatoString("", Protocol.getUserList, port_TCP).getBytes() ;
		System.out.println("MS send,protocol getUserList");
		socket = new DatagramSocket();
   	    DatagramPacket packet = new DatagramPacket(message, message.length, 
   	    InetAddress.getByName(adressMulticastIP), port_UDP) ;
   	    socket.send(packet);
	    socket.close();
	}

    
}
