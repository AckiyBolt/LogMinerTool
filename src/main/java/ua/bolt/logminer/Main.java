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

        Validator validator = new Validator();

        if (isHelpCalled(args) || !validator.isArgsVaild(args)) {
            printHelp();
            System.exit(1);
        }

        System.out.println("Args are good. Lets start.");

        startPath = Paths.get(args[0]);
        fileFormat = args[1];
        searchable = ".*" + args[2] + ".*";
        outFile = new File(args[3]);

        Executor executor = new Executor();

        StatCollector.searchStarting();
        executor.executeFor(startPath, fileFormat, searchable, outFile);
        StatCollector.searchEnding();

        StatCollector.printStatistic();
        System.out.println("Done.");
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
                        .append("   <looking_for> - regular expression that matches searchable line.")
                        .append(System.lineSeparator())
                        .append("                   ru man is here: http://www.quizful.net/post/Java-RegExp")
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
