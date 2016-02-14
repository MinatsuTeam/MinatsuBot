package us.tryy3.java.minatsu.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by tryy3 on 2016-02-14.
 */
public class MessageBuilder {
    JsonArray array = new JsonArray();

    public MessageBuilder addMessage(String channel, String message) {
        JsonObject obj = new JsonObject();
        obj.addProperty("channel", channel);
        obj.addProperty("message", message);
        array.add(obj);

        return this;
    }

    public JsonArray build() {
        return array;
    }
}
