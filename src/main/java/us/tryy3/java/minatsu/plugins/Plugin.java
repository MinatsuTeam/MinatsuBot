package us.tryy3.java.minatsu.plugins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.TCPServer.Connection;
import us.tryy3.java.minatsu.logger.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dennis.planting on 11/9/2015.
 */
public abstract class Plugin implements IPlugin {
    private boolean isEnabled = false;
    private PluginDescription description;
    private File file;
    private File pluginFolder;
    private Bot bot;
    private Logger logger;

    public File getFile() {
        return file;
    }

    public File getPluginFolder() {
        return pluginFolder;
    }

    public PluginDescription getDescription() {
        return description;
    }

    public Logger getLogger() {
        return logger;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public Bot getBot() {
        return bot;
    }

    public void setDescription(PluginDescription description) {
        this.description = description;
    }

    public void setEnabled(boolean enabled) {
        if (this.isEnabled != enabled) {
            this.isEnabled = enabled;
            if (this.isEnabled) {
                this.onStart();
            } else {
                this.onStop();
            }
        }
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setPluginFolder(File pluginFolder) {
        this.pluginFolder = pluginFolder;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void onStart() {
        getLogger().info("Enabling %s %s", getDescription().getName(), getDescription().getVersion());
    }
    public void onStop() {
        getLogger().info("Disabling %s %s", getDescription().getName(), getDescription().getVersion());
    }

    public void unload() {
        this.onStop();
        this.getBot().unloadPlugin(this);
    }

    /* TODO: Come up with a better system for this. */
    public void init(Bot bot, File pluginDir) {
        init(bot, pluginDir, null);
    }
    public void init(Bot bot, File datafolder, PluginDescription description) {
        if (description == null) {
            throw new Error("This plugin was not properly initalized.");
        }
        if (description.getName() == null) {
            throw new Error("Name was not set in PluginDescription.");
        }
        if (description.getVersion() == null) {
            throw new Error("Version was not set in PluginDescription.");
        }
        this.bot = bot;
        this.pluginFolder = new File(datafolder + "/" + description.getName());
        this.description = description;
        this.logger = new Logger(this);
        getLogger().info("Loading %s %s", getDescription().getName(), getDescription().getVersion());
    }


    /*
			final File file = (File) config.getOptions( ).getArgument("file");

			final Gson gson = new GsonBuilder( ).setPrettyPrinting( ).create( );
			final PrintWriter writer = new PrintWriter(new FileWriter(file, false));
			writer.write(gson.toJson(config.getMap( )));
			writer.close( );
     */

    public boolean onCommand(Connection connection, String from, String id, String cmd, String[] args) {
        return false;
    }

    public void saveDefaultConfig(Map config) {
        saveConfig(getPluginFolder() + "/config.json", config);
    }
    public void saveDefaultConfig(String s, Map config) {
        saveConfig(getPluginFolder() + "/" + s + ".json", config);
    }

    public void saveConfig(String file, Map config) {
        saveConfig(new File(file), config);
    }
    public void saveConfig(File file, Map config) {
        try {
            if (!checkFile(file)) {
                return;
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            PrintWriter writer = new PrintWriter(new FileWriter(file, false));

            writer.write(gson.toJson(config));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map loadDefaultConfig() {
        return loadConfig(getPluginFolder() + "/config.json");
    }
    public Map loadDefaultConfig(String s) {
        return loadConfig(getPluginFolder() + "/" + s + ".json");
    }

    public Map loadConfig(String file) {
        return loadConfig(new File(file));
    }
    public Map loadConfig(File file) {

        try {
            if (!checkFile(file)) {
                return null;
            }
            Gson gson = new Gson();

            Reader reader = new FileReader(file);

            Map map = gson.fromJson(reader, Map.class);
            return (map == null) ? new HashMap() : map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkFile(File file) throws IOException {
        if (file.exists() && !file.isFile()) {
            getLogger().severe("The config file is not a file. " + file.getPath());
            return false;
        }
        File folder = file.getParentFile();
        if (!folder.exists()) {
            getLogger().fine("Can't find file, creating folders.");
            boolean folderBol = folder.mkdirs();
            getLogger().fine((folderBol ? "Folder created." : "Folder failed to create."));
        }
        if (!file.exists()) {
            getLogger().fine("Can't find file, creating file.");
            boolean fileBol = file.createNewFile();
            getLogger().fine((fileBol ? "File created." : "File failed to create."));
        }
        return true;
    }
}
