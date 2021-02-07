package Model;

import Controller.ListController;
import Launcher.Launcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
    public static Connection conn = null;

    public static void connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:Histories";
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);

            Launcher.printDebug("BDD: connection succeed");
            createNewTable();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Histories.db";

        // SQL statement for creating a new table
        String sql = " CREATE TABLE IF NOT EXISTS Messages (\n"
                + "	m_id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	mac  text NOT NULL,\n"
                + "	date text NOT NULL,\n"
                + "	message text,\n"
                + "	received boolean\n"
                + " );";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            Launcher.printDebug("BDD: table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Launcher.printDebug("BDD: table failed");
        }
    }

    public static void close_connection() {
        try {
            if (conn != null) {
                conn.close();
            }
            Launcher.printDebug("BDD: connection closed");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void get_messages(String MAC, ListController<Message> messageList) {
        String sql = "SELECT message, received, date FROM Messages WHERE MAC = ?";
        String url = "jdbc:sqlite:Histories.db";
        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, MAC);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Message m = new Message(rs.getString("message"), rs.getBoolean("received"), rs.getString("date"));
                messageList.add(m);
                //Launcher.printDebug(rs.getString("message") + "   " + rs.getBoolean("received"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void insert_history(String MAC, Message message) {
        String url = "jdbc:sqlite:Histories.db";
        String sql = "INSERT INTO Messages(mac,date,message,received) VALUES(?,?,?,?)";
        //Launcher.printDebug(sql);
        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, MAC);
            pstmt.setString(2, message.time.toString());
            pstmt.setString(3, message.msg);
            pstmt.setBoolean(4, message.received);
            //Launcher.printDebug(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //Launcher.printDebug("insert failure ");
        }
    }

    public static void main(String[] args) {
        ListController<Message> messageList = new ListController<>();
        connect();
        //Message m = new Message ("i love java" , false);
        //insert_history("test",m);
        get_messages("GCDE48001122", messageList);
        close_connection();
    }
}

