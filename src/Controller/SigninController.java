package Controller;

import Launcher.Launcher;
import Launcher.Main;
import Model.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

public class SigninController {
    @FXML
    private TextField username;

    public void onSubmit(Event e) {
        String username = this.username.getText().trim();

        if (username.isEmpty()) return;

        try {
            User user = Main.getUser();
            user.setUsername(username);

            Main.getMulticast().initUserList();
            Main.getServlet().sendRequest("PUT", "addressMAC=" + user.getAddressMAC() + "&username=" + user.getUsername());
            //wait 1s to fill the userlist
            Thread.sleep(1000);

            if (Main.getMulticast().verifyUsername(username)) {
                //DEBUG
                Launcher.printDebug(Main.getMulticast().getUserList().toString());

                user.setIsConnected(true);
                new Thread(Main.getMulticast()).start();
                Main.startChat();
            } else {
                Main.getUnicast().closeAllChatSession();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This username is already used!");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
