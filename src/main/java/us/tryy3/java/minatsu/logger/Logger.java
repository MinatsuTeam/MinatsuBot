package us.tryy3.java.minatsu.logger;

import us.tryy3.java.minatsu.plugins.Plugin;

import java.util.Calendar;

/**
 * Created by dennis.planting on 11/30/2015.
 */
public class Logger {
    private String pluginName;

    public Logger(Plugin plugin) {
        if (plugin == null) {
            this.pluginName = "";
        } else {
            String prefix = plugin.getDescription().getPrefix();
            this.pluginName = prefix != null ? "[" + prefix + "] " : "[" + plugin.getDescription().getName() + "] ";
        }
    }

    //Implement log to file.
    public void log(Level level, String msg) {
        System.out.println(String.format("[%s:%s:%s %s]: %s%s", Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, level.getName(), pluginName, msg));
    }

    public void severe(String msg) {
        this.log(Level.SEVERE, msg);
    }
    public void severe(String msg, Object... args) {
        this.log(Level.SEVERE, String.format(msg, args));
    }

    public void warning(String msg) {
        this.log(Level.WARNING, msg);
    }
    public void warning(String msg, Object... args) {
        this.log(Level.WARNING, String.format(msg, args));
    }

    public void info(String msg) {
        this.log(Level.INFO, msg);
    }
    public void info(String msg, Object... args) {
        this.log(Level.INFO, String.format(msg, args));
    }

    public void config(String msg) {
        this.log(Level.CONFIG, msg);
    }
    public void config(String msg, Object... args) {
        this.log(Level.CONFIG, String.format(msg, args));
    }

    public void fine(String msg) {
        this.log(Level.FINE, msg);
    }
    public void fine(String msg, Object... args) {
        this.log(Level.FINE, String.format(msg, args));
    }

    public void finer(String msg) {
        this.log(Level.FINER, msg);
    }
    public void finer(String msg, Object... args) {
        this.log(Level.FINER, String.format(msg, args));
    }

    public void finest(String msg) {
        this.log(Level.FINEST, msg);
    }
    public void finest(String msg, Object... args) {
        this.log(Level.FINEST, String.format(msg, args));
    }

    public void all(String msg) {
        this.log(Level.ALL, msg);
    }
    public void all(String msg, Object... args) {
        this.log(Level.ALL, String.format(msg, args));
    }

    public enum Level {
        SEVERE("Severe"),
        WARNING("Warning"),
        INFO("Info"),
        CONFIG("Config"),
        FINE("Fine"),
        FINER("Finer"),
        FINEST("Finest"),
        ALL("All");

        String name;

        Level(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}
