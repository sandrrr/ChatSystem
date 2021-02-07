package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message {
    final public String msg;
    final public boolean received;
    final public Date time;

    public Message(String msg, boolean received) {
        this.time = new Date();
        this.received = received;
        this.msg = msg;
    }

    public Message(String msg, boolean received, String time) {
        Date timeTemp;
        try {
            timeTemp = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us")).parse(time);
        } catch (ParseException e) {
            timeTemp = new Date();
        }
        this.time = timeTemp;
        this.received = received;
        this.msg = msg;
    }
}
