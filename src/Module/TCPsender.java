package Module   ;

import java.net.InetAddress;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintStream;  
import java.net.Socket;  
import java.net.SocketTimeoutException;



import java.io.OutputStream;
// à faire envoyer info de user
public class TCPsender implements Runnable   {
	 private String IP_destination ;
	 private int port_destination;
	 private User user;
	 TCPsender(String IP_destination , int port_destination, User user) {
		 this.IP_destination = IP_destination;
		 this.port_destination = port_destination;
		 this.user = user;
	 }
	 public String DatatoString(User user)  {
	    	StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < user.getAdressMAC().length; i++) {
	            sb.append(String.format("%02X%s", user.getAdressMAC()[i], (i < user.getAdressMAC().length - 1) ? "-" : ""));        
	        }
	        return (sb.toString()+ ":" +user.getPort_tcp() +":" + user.getPseudo()) ;
	        
	 }
	 
	 public void run() {
		 if(this.port_destination == this.user.getPort_tcp() &&  this.IP_destination.equals( user.getAdressIP().getHostAddress())) {
			 System.out.println("moi meme!!!!!"); //n'envoie pas TCP a soi meme, n'envoie à soi meme que Multicast
		 }else {
			 try {
				 Socket dest = new Socket(IP_destination, port_destination); 
				 String message =  DatatoString(this.user);
				 byte[] bstream=message.getBytes();  
				 OutputStream os=dest.getOutputStream();   
				 os.write(bstream);
				 dest.close();

			 }
			 catch(Exception e)  {
				 e.printStackTrace();
			 }
		 }
		 
		 
	 }
	 
}
