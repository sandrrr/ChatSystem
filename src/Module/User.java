package Module;
import java.net.*;

//utilisateur
public class User {
    private String pseudo;
    private byte[] adressMAC ;
    private InetAddress adressIP;
    private boolean isConnected  ;
    User(String pseudo){
    	//vérification doublante
    	//notify all users
        this.pseudo = pseudo;
        System.out.println("Current pseudo : " + this.pseudo) ;
	    try {

	        this.adressIP = InetAddress.getLocalHost();
	        System.out.println("Current IP address : " + adressIP.getHostAddress());

	        NetworkInterface network = NetworkInterface.getByInetAddress(adressIP);

	        this.adressMAC = network.getHardwareAddress();

	        System.out.print("Current MAC address : ");

	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < this.adressMAC.length; i++) {
	            sb.append(String.format("%02X%s", this.adressMAC[i], (i < this.adressMAC.length - 1) ? "-" : ""));        
	        }
	        System.out.println(sb.toString());

	    } catch (UnknownHostException e) {

	        e.printStackTrace();

	    } catch (SocketException e){

	        e.printStackTrace();

	    }
	    this.isConnected = false ;
    }
    public boolean isConnected() {
		return isConnected;
	}
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	public byte[] getAdressMAC() {
        return adressMAC;
    }

    public String getPseudo() {
    	//vérification doublante
    	//notify all users
        return pseudo;
    }
	public InetAddress getAdressIP() {
		return adressIP;
	}
	public void setAdressIP(InetAddress adressIP) {
		this.adressIP = adressIP;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public void setAdressMAC(byte[] adressMAC) {
		this.adressMAC = adressMAC;
	}
    
	
    
}
