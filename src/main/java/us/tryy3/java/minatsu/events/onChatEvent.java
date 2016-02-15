package us.tryy3.java.minatsu.events;

import us.tryy3.java.minatsu.TCPServer.Connection;

/**
 * Created by dennis.planting on 11/6/2015.
 */
public class onChatEvent extends Event {
    private String message;
    private String from;
    private String id;
    private Connection connection;

    public onChatEvent(String message, String from, String id, Connection connection) {
        this.message = message;
        this.from = from;
        this.id = id;
        this.connection = connection;
    }

    public String getFrom() {
        return from;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
