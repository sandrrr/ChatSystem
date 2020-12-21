package Controller;

import Launcher.Launcher;
import Launcher.Main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SigninController {
    @FXML
    private TextField username;

    public void onSubmit(Event e) {
        String username = this.username.getText();

        try {
            Main.getUser().setUsername(username);

            Main.getMulticast().initUserList();
            //wait 1s to fill the userlist
            Thread.sleep(1000);

            if (Main.getMulticast().verifyUsername(username)) {
                //DEBUG
                Launcher.printDebug(Main.getMulticast().getUserList().toString());

                Main.getUser().setIsConnected(true);
                new Thread(Main.getMulticast()).start();
                Main.startChat();
            } else {
                Main.getUnicast().closeAllChatSession();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
