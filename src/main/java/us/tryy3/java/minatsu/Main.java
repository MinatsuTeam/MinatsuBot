package us.tryy3.java.minatsu;

import org.apache.commons.cli.HelpFormatter;

/**
 * Created by dennis.planting on 11/6/2015.
 */
public class Main {
    public static void main(String[] args) {
        String version = "0.0.1";

        try {
            Options options = new Options(args);
            if (options.hasOption("version")) {
                System.out.println("Minatsu version v" + version);
            } else if (options.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("Minatsu", options.getOptions());
            } else {
                new Bot(version, options);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
