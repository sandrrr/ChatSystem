package Module;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//communication de 1 vers 1
//Principalement pour ChatSession dans le cas de 1 utilisqteur parle à un autre
public class CommunicationUnicast extends Thread implements Communication{
    private ServerSocket serverSocket;

    public CommunicationUnicast(int port){
        try {
            serverSocket = new ServerSocket(port);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Socket accepted");
                new ChatSession(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
