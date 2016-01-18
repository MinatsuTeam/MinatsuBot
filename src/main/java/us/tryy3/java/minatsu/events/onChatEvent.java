package us.tryy3.java.minatsu.events;

/**
 * Created by dennis.planting on 11/6/2015.
 */
public class onChatEvent extends Event {
    private String message;
    private String from;
    private String id;

    public onChatEvent(String message, String from, String id) {
        this.message = message;
        this.from = from;
        this.id = id;
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
