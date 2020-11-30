package Module;
import java.net.*;

public class MulticastReceiver extends CommunicationMulticast implements Runnable {
	 private String adressMulticastIP = "224.0.0.1";
	 private MulticastSocket socket ;
	 private byte[] adressMAC ;
	 private InetAddress adressIP ;
	 private String pseudo;
	 int port = 2334;
	 public MulticastReceiver(User user){  
		    super(user) ;
	        this.adressIP = user.getAdressIP();
	        this.adressMAC = user.getAdressMAC();
	        this.pseudo = user.getPseudo();
	        try {
	        	 // important that this is a multicast socket
	        	 socket = new MulticastSocket(port);
	 	         // join by ip
	 	         socket.joinGroup(InetAddress.getByName(adressMulticastIP));
	         }
	         catch(Exception e) {
	        	 e.printStackTrace();
	         }
	      

	 }  
	 public void run() {
		   // make datagram packet to recieve
	        byte[] message = new byte[1024];
	        DatagramPacket packet = new DatagramPacket(message, message.length) ;
	        System.out.println("MR starts to receive");
	       // while(true) { //continue to listen
	        	 try {
	 	        	// recieve the packet
	 		        socket.receive(packet);
	 	        }
	 	        catch(Exception e) {
	 	        	 e.printStackTrace();
	 	        }
	 	        System.out.println("received: " + new String(packet.getData()));
	       //}
	       
	 }
	 public void close(){
	        socket.close();
	 }
}
