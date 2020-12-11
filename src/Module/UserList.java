package Module;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class UserList {
	//public static ArrayList <User> Userlist = new ArrayList<User>();
	public static ObservableList<User> Userlist = FXCollections.observableArrayList();
	private static boolean availble = true;
	
	synchronized public static void add(User user) {
		while(!availble) {
			
		}
		availble = false;
		Userlist.add(user);
		availble = true;
	}
	
	public static void affiche_list() {
		 int i = 0;
		 for(User user: Userlist)
	       {
			 byte[] adressMAC = user.getAdressMAC();
			 StringBuilder sb = new StringBuilder();
			 /* temporaire MAC pas encore pret
		        for (int i = 0; i < adressMAC.length; i++) {
		            sb.append(String.format("%02X%s", adressMAC[i], (i < adressMAC.length - 1) ? "-" : ""));        
		        }
		     */
	       	 System.out.println ((i+1) + "/"+Userlist.size()+" "+user.getPseudo()+":"+user.getAdressIP().getHostAddress()+":"+user.getPort_tcp()+":"+user.isConnected());
	       	 i ++;
	       }
	}
	
	 public static boolean Verify_pseudo(String pseudo) {
		if (Userlist.isEmpty()) {
			//System.out.println ("first if true");
			return true;
			
		}
			
		 for(User user: Userlist)
		 {
			 if (pseudo.equals(user.getPseudo())) {
				 //System.out.println ("seconde if false");
				 return false;
			 }
		 }
		 //System.out.println ("default if true");
		 return true;
	}


	public static boolean existe_pseudo(String pseudo) {
		if (Userlist.isEmpty()) {
			//System.out.println ("first if true");
			return false;

		}

		for(User user: Userlist)
		{
			if (pseudo.equals(user.getPseudo())) {
				//System.out.println ("seconde if false");
				return true;
			}
		}
		//System.out.println ("default if true");
		return false;
	}
}
