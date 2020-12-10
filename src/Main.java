import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Chat System");
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setResizable(false);
        startSignin();
    }

    public void startSignin() {
        try {
            root = new FXMLLoader(getClass().getResource("View/Signin.fxml")).load();
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startChat() {
        try {
            root = new FXMLLoader(getClass().getResource("View/Chat.fxml")).load();
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
