package Model;

import Launcher.Main;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class MulticastPacket {
    public final String addrMac;
    public final String protocol;
    public final String data;

    public MulticastPacket(String protocol, String data) {
        this.addrMac = Main.getUser().getAddressMAC();
        this.protocol = protocol;
        this.data = data;
    }

    public MulticastPacket(byte[] packet) throws IOException {
        this((new DataInputStream(new ByteArrayInputStream(packet))).readUTF());
    }

    public MulticastPacket(String data) throws IOException {
        String[] data_split = data.split(":", 3);
        this.addrMac = data_split[0];
        this.protocol = data_split[1];
        this.data = data_split[2];
    }

    @Override
    public String toString() {
        return addrMac + ":" + protocol + ":" + data;
    }
}
