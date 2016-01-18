package us.tryy3.java.minatsu.plugins;

import us.tryy3.java.minatsu.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dennis.planting on 12/5/2015.
 */
public class PluginManager {
    private final Logger logger = new Logger(null);
    Map<String, IPlugin> plugins = new HashMap<>();

    public PluginManager(Map<String, IPlugin> plugins) {
        this.plugins = plugins;
    }

    public Map<String, IPlugin> getPlugins() {
        return plugins;
    }

    public boolean pluginIsLoaded(String name) {
        return plugins.containsKey(name);
    }

    public IPlugin getPlugin(String name) {
        return this.plugins.get(name);
    }

    public boolean disablePlugin(String name) {
        if (!plugins.containsKey(name)) return false;
        plugins.remove(name);
        return true;
    }
}
