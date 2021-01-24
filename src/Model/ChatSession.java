package Model;

import Controller.ListController;
import Launcher.Launcher;
import Launcher.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

//Session pour que deux, voir plusieurs personnes puissent communiquer
public class ChatSession extends Thread {
    //class for reading and writing
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private final Socket socket;
    private volatile boolean active = true;

    private final ListController<Message> messageList = new ListController<>();

    public ChatSession(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            this.start(); //when receives messages
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String text) {
        sendMessage(text, true);
    }

    public void sendMessage(String text, boolean addToList) {
        try {
            if (addToList) {
                Message m = new Message(text, false);
                messageList.add(m);
                Database.insert_history(get_MAC(),m);
            }
            out.writeObject(text);
            out.flush();

            //DEBUG
            Launcher.printDebug("U-S: " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //DEBUG
        Launcher.printDebug("Chat session started");

        while (active) {
            try {
                Object objectIn = in.readObject();
                if (objectIn instanceof String) {
                    //DEBUG
                    Launcher.printDebug("U-R: " + objectIn);

                    if (Main.getUser().isConnected()) {
                        Message rcv = new Message((String) objectIn, true);
                        messageList.add(rcv);
                        Database.insert_history(get_MAC(),rcv);
                    } else {
                        MulticastPacket packet = new MulticastPacket((String) objectIn);
                        if (packet.protocol.equals("newUser")) {
                            Main.getMulticast().getUserList().add(new User(packet.data, socket.getInetAddress(), packet.addrMac, this));
                        }
                        get_histories();
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                //Socket closed
            }
        }

        //DEBUG
        Launcher.printDebug("Chat session stopped");
    }

    public ListController<Message> getMessageList() {
        return messageList;
    }

    public void close() {
        try {
            active = false;
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String  get_MAC (){
        InetAddress IP = socket.getInetAddress();
        for (User u : Main.getMulticast().getUserList()) {
            if (u.getAddressIP().equals(IP)) {
                return u.getAddressMAC();
            }
        }
        return ("Error");
    }

    public void get_histories(){
        if( ! get_MAC().equals("Error")){
            Database.get_messages(get_MAC(),messageList);
        }
    }
}

