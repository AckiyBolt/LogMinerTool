package ua.bolt.logminer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ackiybolt on 20.03.15.
 */
public class Main {

    static Path startPath = null;
    static String fileFormat = null;
    static String searchable = null;
    static File outFile = null;

    public static void main (String ... args) throws IllegalArgumentException {

        if (isHelpCalled(args) || !isVaildArgs(args)) {
            printHelp();
            System.exit(1);
        }

        System.out.println("Args are good. Lets start.");

        startPath = Paths.get(args[0]);
        fileFormat = args[1];
        searchable = args[2];
        outFile = new File(args[3]);

        Executor executor = new Executor();

        StatCollector.searchStarting();
        executor.executeFor(startPath, fileFormat, searchable, outFile);
        StatCollector.searchEnding();

        StatCollector.printStatistic();
        System.out.println("Done.");
    }

    private static boolean isVaildArgs(String[] args) {

        boolean result = true;

        File rootDir = new File(args[0]);
        if (!rootDir.exists() && !rootDir.isDirectory() && !rootDir.canRead()) {
            System.out.println("Root directory doesn't exist OR it's not a directory OR I haven't read permission for it.");
            result = false;
        } else {
            System.out.println("Root directory exist.");
        }

        File outFile = new File(args[3]);
        try {

            if (outFile.exists()) {
                System.out.println("Output file exists. Recreating.");
                outFile.delete();
            }
            outFile.createNewFile();
            System.out.println("Output file was created.");

        } catch (IOException ex) {
            System.out.println("Can't touch output file. Access is closed or path does not exist.");
            result = false;
        }

        return result;
    }

    private static boolean isHelpCalled(String[] args) {
        return !(args.length == 4);
    }


    private static void printHelp() {
        System.out.println(
                new StringBuilder()
                        .append("This is simple tool for recursively search of appropriate lines in some log archive (directory tree).")
                        .append(System.lineSeparator())
                        .append("Output is file that has format: <file_name> <founded_line>.")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append("Launch params:   <root_dir> <file_format> <looking_for> <out_file>")
                        .append(System.lineSeparator())
                        .append("   <root_dir>    - start dir path, for recursively search.")
                        .append(System.lineSeparator())
                        .append("   <file_format> - format of searchable files.")
                        .append(System.lineSeparator())
                        .append("   <looking_for> - something that log line should contains.")
                        .append(System.lineSeparator())
                        .append("   <out_file>    - output file path. If exist - will be deleted. Be careful.")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append("Launch example (for *nix): java -jar logminer.jar \"/user/anon/logs\" \".log\" \"LoremIpsum\" \"/user/anon/out.txt\"")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append("govnokoded by AckiyBolt©. All rigths are not reserved.")
        );
    }
}
