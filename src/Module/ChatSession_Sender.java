package Module;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ChatSession_Sender {
	private Socket socket_sender;
	ChatSession_Sender(InetAddress IP, int port){
		try {
			socket_sender = new Socket(IP.getHostAddress(),port);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendMessage(String text) {
	    try {
	        System.out.println("Send: " + text);
	        OutputStream os = socket_sender.getOutputStream();
	        os.write(message_string(text).getBytes()); 
	        os.close();
	        //out.writeObject(text);
	        //out.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	 public String message_string(String text) {
	    	return ("Message:" + text);
	}
	 
	 public void close(){
	    	try {
	    		//socket_sender.close();
	    		socket_sender.close();
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    	
	    }


}	
