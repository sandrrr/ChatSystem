package Model;

import Launcher.Launcher;
import Launcher.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//communication de 1 vers 1
//Principalement pour ChatSession dans le cas de 1 utilisqteur parle à un autre
public class CommunicationUnicast extends Thread {
    public static /*DEBUG final*/ int port = 51000;
    private ServerSocket serverSocket;
    private volatile boolean active = true;

    public CommunicationUnicast() {
        try {
            //DEBUG
            if (Launcher.DEBUG == 2) {
                port++;
            }

            serverSocket = new ServerSocket(port);
            this.start();

            //DEBUG
            if (Launcher.DEBUG == 1) {
                port++;
            } else if (Launcher.DEBUG == 2) {
                port--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //DEBUG
        Launcher.printDebug("Chat session server started");

        while (active) {
            try {
                Socket socket = serverSocket.accept();
                new ChatSession(socket);
            } catch (IOException e) {
                //Socket closed
            }
        }

        //DEBUG
        Launcher.printDebug("Chat session server stopped");
    }

    public void closeAllChatSession() {
        for (User user : Main.getMulticast().getUserList()) {
            user.getChatSession().close();
        }
    }

    public void close() {
        try {
            if (Main.getUser().isConnected()) {
                closeAllChatSession();
            }
            active = false;
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
