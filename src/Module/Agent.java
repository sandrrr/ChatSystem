package  Module    ;
//Class Agent, class principale qui joue l'interface avec l'utilisateur
//pour spécifique voir diagramme
//gestion de l'utilsiateur
//vérification de pseudo
//activation de fenetre de ChatSession etc.

import java.io.BufferedReader ;
import java.io.InputStreamReader;


public class Agent {

   Agent(){
	   
   }
   public void start() throws Exception {
	 //enter the pesudo choisit
	 		String  s;
	 		String pseudo;
	 		int port_multicast  ;
	 		int port_TCP;
	 		System.out.println("Choisir votre pseudo : ");
	 		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	        pseudo = bufferRead.readLine();
			System.out.println("Choisir votre port de Multicast : ");
			bufferRead = new BufferedReader(new InputStreamReader(System.in));
			s = bufferRead.readLine();
			port_multicast = Integer.parseInt(s); 
			System.out.println("Choisir votre port de TCP : ");
			bufferRead = new BufferedReader(new InputStreamReader(System.in));
			s = bufferRead.readLine();
			bufferRead.close();
			port_TCP = Integer.parseInt(s); 
	        User user = new User(pseudo,port_TCP);
	        //MulticastReceiver MR = new MulticastReceiver(user);
	        CommunicationUnicast MU = new CommunicationUnicast(port_TCP);
	      
	        new Thread(new MulticastReceiver(user,port_multicast)).start(); 
	        
	        MulticastSender MS = new MulticastSender(user,port_multicast)  ;
	        MS.send_getUserList();
	        
	        
	        while(true) {
	        	try {
	   			 Thread.sleep(5000); //make sure that TCP has collected all other users
	   		 }
	   		 catch(Exception e) {
	   			 e.printStackTrace();
	   		 }
	        	UserList.affiche_list();
	        }
   }
   
}
