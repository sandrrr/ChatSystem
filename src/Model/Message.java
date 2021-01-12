package Model;

import java.util.Date;

public class Message {
    final public String msg;
    final public boolean received;
    final public Date time;

    public Message(String msg, boolean received) {
        this.time = new Date();
        this.received = received;
        this.msg = msg;
    }

}
