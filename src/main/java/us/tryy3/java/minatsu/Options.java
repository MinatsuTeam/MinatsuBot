package us.tryy3.java.minatsu;

import org.apache.commons.cli.*;

import java.util.Arrays;

/**
 * Created by tryy3 on 2016-01-09.
 */
public class Options {
    private org.apache.commons.cli.Options options = new org.apache.commons.cli.Options();
    private CommandLineParser parser = new DefaultParser();
    private CommandLine line;

    public Options(String[] args) {
        options.addOption("version", "Reports the current version");
        options.addOption("help", "Show help page");
        options.addOption("pluginPath", true, "Plugin folder path");
        options.addOption("port", true, "Port for TCP server");

        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean hasOption(String opt) {
        return line.hasOption(opt);
    }

    public org.apache.commons.cli.Options getOptions() {
        return options;
    }

    public String getPluginPath() {
        return (line.hasOption("pluginPath") ? line.getOptionValue("pluginPath") : "./plugins" );
    }

    public int getPort() {
        if (!line.hasOption("port")) return 6868;
        String s = line.getOptionValue("port");

        if (!isInteger(s)) return 6868;
        return Integer.parseInt(s);
    }

    private static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    private static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}