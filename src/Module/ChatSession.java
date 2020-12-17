package  Module    ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
//Session pour que deux, voir plusieurs personnes puissent communiquer

public class ChatSession extends Thread {
    //class for reading and writing
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private Socket socket;

    public ChatSession(Socket socket) {
    	this.socket = socket;
        System.out.println("Chat session created");
        try {
        	 BufferedReader buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
             String data =  buf.readLine();  
             if (data.substring(0,data.indexOf(":")).equals("Message")) {
            	 // create_socket_sender(socket.getPort(),socket.getInetAddress());
                 out = new ObjectOutputStream(socket.getOutputStream());
                 out.flush();
                 in = new ObjectInputStream(socket.getInputStream());
            	 this.start(); //when receives messages
             }
             else {
            	 received_user(data, socket.getInetAddress()); //when receives a new user
            	 socket.close(); //receives only one message so close
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void sendMessage(String text) {
        try {
            new Message(text,false);
            out.writeObject(text);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void received_user(String data, InetAddress IP) {
    	 int port_src = Integer.parseInt(data.substring(data.indexOf(":")+1,data.indexOf(":",18)));
         //System.out.println("TCPreceiver_thread port: "+ port_src);
         String MAC = data.substring(0,data.indexOf(":"));
         //System.out.println("TCPreceiver_thread MAC "+MAC);
         String pseudo = data.substring(data.indexOf(":",18)+1,data.length());
         //System.out.println("TCPreceiver_thread pseudo "+pseudo);
         if(UserList.Verify_pseudo(pseudo)) { //true if nobody uses this name
        	 
			 User new_user = new User(pseudo, port_src ,IP, MAC);
			 UserList.add(new_user);
			 System.out.println("TCP  successfully ajouté");
			 UserList.affiche_list();
		 }
    }

    public void close(){
    	try {
    		//socket_sender.close();
        	socket.close();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object objectIn = in.readObject();
                if (objectIn instanceof String){
                    new Message((String) objectIn,true);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

