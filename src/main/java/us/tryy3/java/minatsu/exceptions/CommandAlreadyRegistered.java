package us.tryy3.java.minatsu.exceptions;

/**
 * Created by dennis.planting on 11/30/2015.
 */
public class CommandAlreadyRegistered extends Error {
    public CommandAlreadyRegistered(String name) {
        super(name);
    }
}
