package us.tryy3.java.minatsu.command;

import us.tryy3.java.minatsu.TCPServer.Connection;

import java.util.List;

/**
 * Created by dennis.planting on 11/29/2015.
 */
public interface ICommand {
    String getName();
    String getUsage();
    String getDescription();
    List<String> getAliases();
    List<Command> getChildren();
    Boolean onCommand(Connection connection, String user, String channel, Command command, String label, String[] args);

    void setName(String name);
    void setUsage(String usage);
    void setDescription(String desc);
    void setChildren(List<Command> children);
    void setAliases(List<String> aliases);
}
