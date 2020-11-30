package Module;


import java.net.InetAddress;
import java.net.MulticastSocket;

//Communication en mode broadcast
//principalement utilsié pour 'vérifier doublante de pseudo'
//ou pour communiquer entre plusieur personnes
public class CommunicationMulticast {
	 private byte[] adressMAC ;
	 private InetAddress adressIP;
	 private String pseudo;
	 public CommunicationMulticast(User user){  
	        this.adressIP = user.getAdressIP() ;
	        this.adressMAC = user.getAdressMAC();
	        this.pseudo = user.getPseudo();
	 }  
}
