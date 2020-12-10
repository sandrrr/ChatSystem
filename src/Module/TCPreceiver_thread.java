package Module   ;
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.PrintStream;  
import java.net.Socket;  

public class TCPreceiver_thread implements Runnable {  

    private Socket client = null;  
    public TCPreceiver_thread(Socket client){  
        this.client = client;  
    }  

    @Override  
    public void run() {  
        try{   
        	 BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));  
             String data =  buf.readLine();  
             System.out.println("TCPreceiver_thread received: " + data);
             client.close(); 
             int port_src = Integer.parseInt(data.substring(data.indexOf(":")+1,data.indexOf(":",18)));
             //System.out.println("TCPreceiver_thread port: "+ port_src);
             String MAC = data.substring(0,data.indexOf(":"));
             //System.out.println("TCPreceiver_thread MAC "+MAC);
             String pseudo = data.substring(data.indexOf(":",18)+1,data.length());
             //System.out.println("TCPreceiver_thread pseudo "+pseudo);
             if(UserList.Verify_pseudo(pseudo)) { //true if nobody uses this name
            	 
    			 User new_user = new User(pseudo, port_src , client.getInetAddress(), MAC);
    			 UserList.add(new_user);
    			 System.out.println("TCP  successfully ajouté");
    			 UserList.affiche_list();
    		 }
        }  
        catch(Exception e)  {
        	e.printStackTrace();
        }

    }  
        
    
}