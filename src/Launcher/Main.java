package Launcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    private static Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Chat System");
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setResizable(false);
        startSignin();
    }

    public  static void startSignin() {
        try {
            root = new FXMLLoader(Main.class.getResource("../View/Signin.fxml")).load();
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startChat() {
        try {
            root = new FXMLLoader(Main.class.getResource("../View/Chat.fxml")).load();
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}