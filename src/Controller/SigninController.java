package Controller;

import javafx.event.Event;
import javafx.scene.control.TextField;

public class SigninController {
    public TextField username;

    public void onSubmit(Event e) {
        String username = this.username.getText();
        System.out.println(username);
    }
}
