package Controller;

import Launcher.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @Override //lancer au démarrage de l'écran
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Main.getUser().getUsername());
    }

    @FXML
    private Label username;

    @FXML
    private ImageView logout;

    @FXML
    private ImageView close;

    @FXML
    private TextField messageField;

    @FXML
    private ImageView send;

    @FXML
    void closeClick(MouseEvent event) {

    }

    @FXML
    void logoutClick(MouseEvent event) {
        Main.startSignin();
    }

    @FXML
    void sendClick(MouseEvent event) {
        System.out.println(messageField.getText());
        messageField.setText("");
    }

    @FXML
    void sendKeyboard(ActionEvent event) {
        System.out.println(messageField.getText());
        messageField.setText("");
    }
}
