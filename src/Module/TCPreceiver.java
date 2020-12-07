package Module   ;

import java.net.ServerSocket ;  
import java.net.Socket;  
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.PrintStream;  
import java.net.Socket;  

public class TCPreceiver implements Runnable  {  
	private User user;
	TCPreceiver(User user){
		this.user = user;
	}
	//à faire  recevoir info de facon continue, mettre à jour l'info de users
    public void run(){  
    	try { 
    			ServerSocket server = new ServerSocket(user.getPort_tcp()) ;  
        		Socket client = null;  
        		while(true) {
        		System.out.println("wating for connection" );
                client = server.accept(); 
                System.out.println("connected" );
                new Thread(new TCPreceiver_thread(client)).start();  
                //BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));  
                //String str =  buf.readLine();  
                //System.out.println("received: " + str);
                //server.close(); 
    		    }
        		
    	}
        catch(Exception e)  {
        	e.printStackTrace();
        }
        
    }  
}  