package  Module    ;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
 import java.net.Socket;
//Session pour que deux, voir plusieurs personnes puissent communiquer

public class ChatSession extends Thread {
    //class for reading and writing
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ChatSession(Socket socket) {
        System.out.println("Chat session created");
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
    }

    public void sendMessage(String text) {
        try {
            System.out.println("Send: " + text);
            out.writeObject(text);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object objectIn = in.readObject();
                if (objectIn instanceof String){
                    System.out.println("Received: " + objectIn);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

