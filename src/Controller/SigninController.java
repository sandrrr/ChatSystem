package Controller;
import Launcher.Main;
import Module.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SigninController {
    @FXML
    private TextField username;


    public void onSubmit(Event e) {
        String username = this.username.getText();
        System.out.println(username);
        User user = new User(username);
        Main.setUser(user);
        CommunicationUnicast MU = new CommunicationUnicast(user.getPort_tcp());
        new Thread(new MulticastReceiver(user)).start();
        MulticastSender MS = new MulticastSender(user,2302);

        try{
            MS.send_getUserList();
            Thread.sleep(2000);
        }
        catch (Exception e1){
            e1.printStackTrace();
        }

        if(UserList.existe_pseudo(username)){
            //cest a dire ajouté success, donc username unique
            System.out.println("OK je suis okokokokokok");
            Main.startChat();
        }else{
            // gération de eidter le pseudo
            System.out.println("PB!!! Pseudo doublant pas encore traité!!");
        }
    }
}
