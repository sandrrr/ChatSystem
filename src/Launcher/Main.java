package Launcher;

import Model.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private static Stage primaryStage;
    private static User user;
    private static final CommunicationMulticast multicast = new CommunicationMulticast();
    private static final CommunicationUnicast unicast = new CommunicationUnicast();
    private static final CommunicationServlet servlet = new CommunicationServlet();

    static {
        try {
            user = new User();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUser() {
        return user;
    }

    public static CommunicationMulticast getMulticast() {
        return multicast;
    }

    public static CommunicationUnicast getUnicast() {
        return unicast;
    }

    public static CommunicationServlet getServlet() {
        return servlet;
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Chat System");
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setResizable(false);
        startSignin();
        Database.connect();
    }

    public static void startSignin() {
        startScene(Main.class.getResource("/View/Signin.fxml"));
    }

    public static void startChat() {
        startScene(Main.class.getResource("/View/Chat.fxml"));
    }

    private static void startScene(URL url) {
        try {
            Parent root = new FXMLLoader(url).load();
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        unicast.close();
        multicast.close();
        servlet.close();
        Database.close_connection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
