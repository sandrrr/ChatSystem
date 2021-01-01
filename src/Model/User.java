package Model;

import Launcher.Launcher;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

//utilisateur
public class User {
    private String username;
    private InetAddress addressIP;
    private String addressMAC;
    private ChatSession chatSession;
    private boolean isConnected;

    public User() throws Exception {
        NetworkInterface network = findNetworkInterface();
        addressIP = network.getInetAddresses().nextElement();
        addressMAC = getAddressMAC(network.getHardwareAddress());
        isConnected = false;
    }

    public User(String username, InetAddress addressIP, String addressMAC, ChatSession chatSession) {
        this.username = username;
        this.addressIP = addressIP;
        this.addressMAC = addressMAC;
        this.chatSession = chatSession;
    }

    private NetworkInterface findNetworkInterface() throws Exception {
        Enumeration<NetworkInterface> networkInterfaces = null;
        NetworkInterface networkInterface = null;
        networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            networkInterface = networkInterfaces.nextElement();
            if (networkInterface.getHardwareAddress() != null && networkInterface.supportsMulticast()) {
                return networkInterface;
            }
        }
        throw new Exception("Network not accessible");
    }

    private String getAddressMAC(byte[] addressMAC) {
        StringBuilder sb = new StringBuilder();
        for (byte b : addressMAC) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

        //DEBUG
        if (Launcher.DEBUG == 2) {
            addressMAC = 'G' + addressMAC.substring(1);
        }
        Launcher.printDebug("USER: " + username + " - " + addressMAC + " (" + addressIP + ')');
    }

    public InetAddress getAddressIP() {
        return addressIP;
    }

    public String getAddressMAC() {
        return addressMAC;
    }

    public ChatSession getChatSession() {
        return chatSession;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    @Override
    public String toString() {
        return username;
    }
}
