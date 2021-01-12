package Controller;

import Launcher.Main;
import Model.Database;
import Model.Message;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @FXML
    private VBox userList;

    @FXML
    private VBox messageList;

    @FXML
    private ScrollPane messageScroll;

    @FXML
    private Label username;

    @FXML
    private Label usernameActiveChat;

    @FXML
    private TextField messageField;

    @Override //lancé au démarrage de l'écran
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Main.getUser().getUsername());

        for (User user : Main.getMulticast().getUserList()) {
            addUserToUserlist(user);
        }
        Main.getMulticast().getUserList().addListener(
            this::addUserToUserlist,
            this::removeUserToUserlist
        );

        //When a new message arrive, scroll down to the new message
        messageList.heightProperty().addListener(observable -> messageScroll.setVvalue(1D));

    }

    private VBox activeUserVBox;
    private User activeUser;

    private void addUserToUserlist(User user) {
        VBox vb = new VBox();
        vb.setPadding(new Insets(10, 20, 10, 20));
        vb.setId(user.getUsername());
        vb.setOnMouseClicked((MouseEvent event) -> {
            onUserActive(user, vb);
        });

        Label username = new Label(user.getUsername());
        vb.getChildren().add(username);

        Label lastMessage = new Label("");
        lastMessage.setStyle("-fx-opacity: 0.5;");
        vb.getChildren().add(lastMessage);

        userList.getChildren().add(vb);

        user.getChatSession().getMessageList().addListener((Message message) -> {
            lastMessage.setText(message.msg);
            if (vb == activeUserVBox) {
                addMessageToMessagelist(message);
            }
        }, null);
    }

    private void removeUserToUserlist(User user) {
        user.getChatSession().getMessageList().removeListener();
        for (Node node : userList.getChildren()) {
            if (node.getId().equals(user.getUsername())) {
                userList.getChildren().remove(node);
                return;
            }
        }
    }

    private void onUserActive(User user, VBox vb) {
        if (activeUserVBox != null) {
            activeUserVBox.setStyle("");
        }
        activeUser = user;
        usernameActiveChat.setText(user.getUsername());
        activeUserVBox = vb;
        activeUserVBox.setStyle("-fx-background-color: #444A63;");
        messageList.getChildren().clear();
        for (Message message : user.getChatSession().getMessageList()) {
            addMessageToMessagelist(message);
        }
    }

    private void addMessageToMessagelist(Message message) {
        HBox hb = new HBox();
        if(!message.received) {
            hb.setAlignment(Pos.TOP_RIGHT);
        }

        Label msg = new Label(message.msg);
        msg.setPadding(new Insets(10, 10, 10, 10));
        msg.setMaxWidth(400.0);
        msg.setMinHeight(Region.USE_PREF_SIZE);
        msg.setWrapText(true);
        if (message.received) {
            msg.setStyle("-fx-background-color: -fx-white;");
        } else {
            msg.setStyle("-fx-background-color: -fx-saumon-orange;");
        }
        msg.getStyleClass().add("font-dark-blue");
        msg.setStyle(msg.getStyle() + "-fx-background-radius: 5px;");
        hb.getChildren().add(msg);

        Tooltip date = new Tooltip(message.time.toString());
        msg.setTooltip(date);

        messageList.getChildren().add(hb);
    }

    @FXML
    void logoutClick(MouseEvent event) {
        Main.getMulticast().logout();
        Main.startSignin();
        Database.close_connection();
    }

    @FXML
    void sendClick(MouseEvent event) {
        sendMessage();
    }

    @FXML
    void sendKeyboard(ActionEvent event) {
        sendMessage();
    }

    private void sendMessage() {
        if (activeUser != null) {
            activeUser.getChatSession().sendMessage(messageField.getText());
            messageField.setText("");
        }
    }
}
