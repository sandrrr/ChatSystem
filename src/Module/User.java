package Module   ;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

//utilisateur
public class User {
    private String pseudo;
    private byte[] adressMAC ;
    private InetAddress adressIP;
    private boolean isConnected;
    int port_tcp ;
    
    public int port_TCP_finder() {
        for(int i = 1026 ; i < 10000 ; i++) {	
			if (available(i)) 
				return i ;
        }
        System.out.println("port libre non trouvé");
        return 0;
    }
	private boolean available(int port) {
		try (Socket ignored = new Socket("localhost", port))   { 
			return false;
		} catch (Exception ignored) {
			return true;
		}
	}

	//local user
	public User(String pseudo) {  // on toruve un port pour la communication tcp si on ne precise pas un port
    	//vérification doublante
    	//notify all users
        this.pseudo = pseudo;
        this.port_tcp = port_TCP_finder();
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
	//local user
    User(String pseudo ,int port_tcp) { // on precise un port pour la communication en TCP
    	//vérification doublante
    	//notify all users
        this.pseudo = pseudo;
        this.port_tcp = port_tcp;
        System.out.println("Current pseudo : " + this.pseudo) ;
	    try {

	        //this.adressIP = InetAddress.getLocalHost();
	        
	        InetAddress theOneAddress = null;
	        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
	        for (NetworkInterface netint : Collections.list(nets)) {
	            if (!netint.isLoopback()) {
	                theOneAddress = Collections.list(netint.getInetAddresses()).stream().findFirst().orElse(null);
	                if (theOneAddress != null) {
	                    break;
	                }
	            }
	        }
	        
	        this.adressIP = theOneAddress;
	        
	        System.out.println("Current IP address : " + adressIP.getHostAddress());

	        NetworkInterface network = NetworkInterface.getByInetAddress(adressIP);

	        this.adressMAC = network.getHardwareAddress();
	        

	        System.out.print("Current MAC address : ");

	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < this.adressMAC.length; i++) {
	            sb.append(String.format("%02X%s", this.adressMAC[i], (i < this.adressMAC.length - 1) ? "-" : ""));        
	        }
	        System.out.println(sb.toString());

	    } catch (Exception e) {

	        e.printStackTrace();

	    } 
	    this.isConnected = false ;
    }
    
    
    User(String pseudo ,int port_tcp, InetAddress IP , String MAC) { // on precise un port pour la communication en TCP
    	//vérification doublante
    	//notify all users
        this.pseudo = pseudo;
        this.port_tcp = port_tcp;
        this.adressIP = IP;
	    this.isConnected = true ;
	    //get string MAC to byte MAC   
    }
    public int getPort_tcp() {
		return port_tcp;
	}
	public void setPort_tcp(int port_tcp) {
		this.port_tcp = port_tcp;
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
