package us.tryy3.java.minatsu.plugins;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.TCPServer;
import us.tryy3.java.minatsu.logger.Logger;

import java.io.*;
import java.util.Map;

/**
 * Created by tryy3 on 2016-01-16.
 */
public interface IPlugin {
    File getFile();

    File getPluginFolder();

    PluginDescription getDescription();

    Logger getLogger();

    boolean isEnabled();

    Bot getBot();

    void setDescription(PluginDescription description);

    void setEnabled(boolean enabled);

    void setFile(File file);

    void setPluginFolder(File pluginFolder);

    void setBot(Bot bot);

    void onStart();

    void onStop();

    void unload();

    void init(Bot bot, File pluginDir);

    void init(Bot bot, File datafolder, PluginDescription description);

    boolean onCommand(TCPServer.Connection connection, String from, String id, String cmd, String[] args);

    void saveDefaultConfig(Map config);

    void saveDefaultConfig(String s, Map config);

    void saveConfig(String file, Map config);

    void saveConfig(File file, Map config);

    Map loadDefaultConfig();

    Map loadDefaultConfig(String s);

    Map loadConfig(String file);

    Map loadConfig(File file);

    boolean checkFile(File file) throws IOException;
}
