package us.tryy3.java.minatsu;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import us.tryy3.java.minatsu.command.Command;
import us.tryy3.java.minatsu.command.CommandManager;
import us.tryy3.java.minatsu.events.Event;
import us.tryy3.java.minatsu.events.onChatEvent;
import us.tryy3.java.minatsu.logger.Logger;
import us.tryy3.java.minatsu.plugins.IPlugin;
import us.tryy3.java.minatsu.plugins.Plugin;
import us.tryy3.java.minatsu.plugins.PluginDescription;
import us.tryy3.java.minatsu.plugins.PluginManager;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Created by dennis.planting on 11/10/2015.
 */
public class Bot {
    private Event event;
    private CommandManager commandManager;
    private Map<String, IPlugin> plugins;
    private TCPServer tcpServer;
    private Options options;
    private PluginManager pluginManager;
    private String version;
    private final Logger logger = new Logger(null);
    private Long startupTime = new GregorianCalendar().getTimeInMillis();

    public Bot(String version, Options options) throws MalformedURLException {
        logger.info("Initalizing Minatsu Version " + version);
        this.options = options;
        this.version = version;
        this.plugins = new HashMap<>();
        this.tcpServer = new TCPServer(options, this);
        this.event = new Event();
        this.commandManager = new CommandManager();

        this.tcpServer.start();

        File pluginDir = new File(options.getPluginPath());

        if (!pluginDir.exists()) {
            pluginDir.mkdirs();
            logger.fine("Creating plugins folder.");
        }

        if (pluginDir.exists() && pluginDir.isFile()) {
            throw new Error("Plugins folder is a file.");
        }

        logger.fine("Folder found.");

        //TODO: Add some sort of dependency system and order system.

        loadPlugins(pluginDir);
    }

    private void loadPlugins(File file) {
        File[] fileList = file.listFiles(new FileFilter() {
            public boolean accept(File path) {
                return path.getPath().toLowerCase().endsWith(".jar");
            }
        });

        logger.info(fileList.length + " plugin(s) found.");

        URL[] urls = new URL[fileList.length];

        for (int i = 0; i < fileList.length; i++) {
            try {
                urls[i] = fileList[i].toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        URLClassLoader urlClassLoader = new URLClassLoader(urls);

        ServiceLoader<Plugin> pluginLoader = ServiceLoader.load(Plugin.class, urlClassLoader);

        this.plugins = new HashMap<>();

        List<Plugin> depend = new ArrayList<>();

        for (Plugin plugin : pluginLoader) {
            plugin.init(this, file);
            PluginDescription desc = plugin.getDescription();

            if (this.plugins.containsKey(desc.getName())) {
                logger.severe("The plugin " + desc.getName() + " already exists.");
            } else {
                this.plugins.put(desc.getName(), plugin);
                if (desc.getDependency() == null) {
                    plugin.onStart();
                } else {
                    depend.add(plugin);
                }
            }
        }

        this.pluginManager = new PluginManager(this.plugins);

        int count = 0;

        while (true) {
            if (depend.size() == 0) break;
            if (count >= depend.size()) {
                count = 0;
            }

            Plugin plugin = depend.get(count);

            boolean load = true;

            for (String s : plugin.getDescription().getDependency()) {
                if (!this.plugins.containsKey(s)) load = false;
            }

            if (load) {
                plugin.onStart();
                depend.remove(count);
            }
            count++;
        }
    }

    private void unloadPlugins() {
        for (IPlugin plugin : plugins.values()) {
            plugin.onStop();
        }
    }

    public void unloadPlugin(IPlugin plugin) {
        this.plugins.remove(plugin.getDescription().getName());
    }

    public void reloadPlugins() {
        File pluginDir = new File(options.getPluginPath());

        if (!pluginDir.exists()) {
            pluginDir.mkdirs();
            logger.fine("Creating plugins folder.");
        }

        if (pluginDir.exists() && pluginDir.isFile()) {
            throw new Error("Plugins folder is a file.");
        }

        logger.fine("Folder found.");

        unloadPlugins();
        loadPlugins(pluginDir);
    }

    public void stop() {
        //broadcastAll("Bot is shutting down now.");
        unloadPlugins();

        getTcpServer().stopConnection();
    }

    public void read(TCPServer.Connection connection, JsonObject obj) {
        switch (obj.get("event").getAsString()) {
            case "chatMessage": {
                if (!(obj.has("channel")) || !(obj.has("message")) || !(obj.has("from"))) {
                    logger.severe("TCPServer sent a chatMessage event with invalid args.");
                    return;
                }

                String msg = obj.get("message").getAsString();
                String from = obj.get("from").getAsString();
                String id = obj.get("channel").getAsString();

                if (msg.indexOf("!") == 0) {
                    String[] splitmsg = msg.split(" ");

                    String command = splitmsg[0].substring(1);
                    String[] args = splitmsg.length > 1 ? Arrays.copyOfRange(splitmsg, 1, splitmsg.length) : null;

                    Boolean knownCommand = false;

                    for (IPlugin plugin : getPlugins().values()) {
                        if (plugin.onCommand(connection, from, id, command, args)) {
                            knownCommand = true;
                        }
                    }

                    for (Command cmd : commandManager.getCommands()) {
                        if (cmd.getName().equalsIgnoreCase(command)) {
                            knownCommand = cmd.onCommand(connection, from, id, cmd, command, args);
                            break;
                        } else {
                            for (String s : cmd.getAliases()) {
                                if (s.equals(command)) {
                                    knownCommand = cmd.onCommand(connection, from, id, cmd, command, args);
                                    break;
                                }
                            }
                        }
                    }

                    if (!knownCommand) {
                        connection.sendMessage(id, "Unknown command, please check the help page.");
                    }
                    break;
                }
                getEvent().callEvents(new onChatEvent(msg, from, id));
                break;
            }
        }
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public Event getEvent() {
        return event;
    }

    public Map<String, IPlugin> getPlugins() {
        return plugins;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public TCPServer getTcpServer() {
        return tcpServer;
    }

    public Long getStartupTime() {
        return startupTime;
    }
}
