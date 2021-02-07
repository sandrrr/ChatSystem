package Model;

import Launcher.Launcher;
import Launcher.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class CommunicationServlet extends Thread {
    public static /*DEBUG final*/ int port = 53000;
    private ServerSocket serverSocket;
    private volatile boolean active = true;

    public CommunicationServlet() {
        try {
            //DEBUG
            if (Launcher.DEBUG == 2) {
                port++;
            }

            serverSocket = new ServerSocket(port);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //DEBUG
        Launcher.printDebug("Servlet server started");

        while (active) {
            try {
                Socket s = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                String message;
                while ((message = (String) in.readObject()) != null) {
                    //DEBUG
                    Launcher.printDebug("S-R: " + message);

                    Main.getMulticast().receiveFromServlet(message);
                }
                in.close();
                s.close();
            } catch (IOException e) {
                //Socket closed
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //DEBUG
        Launcher.printDebug("Servlet server stopped");
    }

    public void sendRequest(String method, String params) {
        try {
            //DEBUG
            Launcher.printDebug("S-S: " + method + " /Servlet?" + params);

            URL url = new URL("https://srv-gei-tomcat.insa-toulouse.fr/ChatSystemServlet_SHI_WANG/Servlet?" + params);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (http.getResponseCode() >= 400) {
                //DEBUG
                Launcher.printDebug("Servlet error");
            }
        } catch (IOException e) {
            //DEBUG
            Launcher.printDebug("Servlet unreachable");
            //e.printStackTrace();
        }
    }

    public void close() {
        if (!active) return;
        active = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
