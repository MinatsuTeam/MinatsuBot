package us.tryy3.java.minatsu.command;

import us.tryy3.java.minatsu.TCPServer.Connection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis.planting on 11/29/2015.
 */
public class Command implements ICommand {
    private String name = null;
    private String usage = null;
    private String desc = null;
    private List<String> aliases = new ArrayList<>();
    private List<Command> children = new ArrayList<>();

    public Command() {}
    public Command(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUsage() {
        return this.usage;
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @Override
    public List<Command> getChildren() {
        return this.children;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public Boolean onCommand(Connection connection, String user, String channel, Command command, String label, String[] args) {
        return false;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public void setChildren(List<Command> children) {
        this.children = children;
    }

    @Override
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    @Override
    public void setDescription(String desc) {
        this.desc = desc;
    }
}
