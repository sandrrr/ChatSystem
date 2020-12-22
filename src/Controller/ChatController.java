package Controller;

import Launcher.Main;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @Override //lancer au démarrage de l'écran
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Main.getUser().getUsername());

        for (User user : Main.getMulticast().getUserList()) {
            addUserToUserlist(user);
        }
        Main.getMulticast().getUserList().addListener((userAdded) -> {
            addUserToUserlist(userAdded);
        }, (userRemoved) -> {
            removeUserToUserlist(userRemoved);
        });
    }

    private void addUserToUserlist(User user) {
        VBox vb = new VBox();
        vb.setPadding(new Insets(10, 20, 10, 20));
        vb.setId(user.getUsername());

        Label username = new Label(user.getUsername());
        vb.getChildren().add(username);

        Label lastMessage = new Label("Exemple");
        lastMessage.setStyle("-fx-opacity: 0.5;");
        vb.getChildren().add(lastMessage);

        userList.getChildren().add(vb);
    }

    private void removeUserToUserlist(User user) {
        for (Node node : userList.getChildren()) {
            if (node.getId().equals(user.getUsername())) {
                userList.getChildren().remove(node);
                return;
            }
        }
    }


    @FXML
    private VBox userList;

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
