package Module;

import java.net.InetAddress;
import java.io.*;

public class Data {
	
    public enum Protocol {
        getUserList,
        activeUser,
        inactiveUser,
        editUsernamen,
        returnPseudo
    }

	
    private InetAddress addrIP; //src
    private byte[] addrMac;  //src
    private Protocol protocol;
    private String data;
    public Data(Protocol protocol, String data,byte[] addrMac,InetAddress addrIp) {
        this.addrMac =  addrMac;
        this.protocol = protocol;
        this.addrIP = addrIP;
        this.data = data;
    }
    public String toString() {
    	StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.addrMac.length; i++) {
            sb.append(String.format("%02X%s", this.addrMac[i], (i < this.addrMac.length - 1) ? "-" : ""));        
        }
        return (sb.toString() + ":" + protocol + ":" + data );
    }
    public byte[] toByte() throws IOException{ //retrun data en forme de byte pour envoyer
    	 byte[] data_byte;
    	 ByteArrayOutputStream output = new ByteArrayOutputStream() ;
    	 (new DataOutputStream(output)).writeUTF(data.toString());
    	 data_byte = output.toByteArray();
    	 return data_byte ;
    }
}