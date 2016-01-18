package us.tryy3.java.minatsu.command;

import us.tryy3.java.minatsu.command.Command;
import us.tryy3.java.minatsu.exceptions.CommandAlreadyRegistered;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis.planting on 11/30/2015.
 */
public class CommandManager {
    private List<Command> commands = new ArrayList<>();

    public void registerCommand(Command command) {
        if (commands.size() > 0) {
            for (Command cmd : commands) {
                if (cmd.getName() == command.getName()) {
                    throw new CommandAlreadyRegistered("This command has already been registered by a plugin.");
                }
            }
        }

        this.commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }
}
