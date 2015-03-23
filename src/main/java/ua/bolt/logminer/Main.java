package ua.bolt.logminer;

import ua.bolt.logminer.matcher.ContainsMatcher;
import ua.bolt.logminer.matcher.Matcher;
import ua.bolt.logminer.matcher.RegexpMatcher;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ackiybolt on 20.03.15.
 */
public class Main {

    static Path startPath = null;
    static String fileFormat = null;
    static String isRegular = null;
    static String searchable = null;
    static File outFile = null;
    static Matcher matcher = null;

    public static void main (String ... args) throws IllegalArgumentException {

        if (isHelpCalled(args) || !Validator.isArgsVaild(args)) {
            printHelp();
            System.exit(1);
        }

        System.out.println("Args are good. Lets start.");

        startPath = Paths.get(args[0]);
        fileFormat = args[1];
        isRegular = args[2];
        resolveMatcherAndSearchable(args[3]);
        outFile = new File(args[4]);

        Executor executor = new Executor();

        StatCollector.searchStarting();
        executor.executeFor(startPath, fileFormat, searchable, outFile, matcher);
        StatCollector.searchEnding();

        StatCollector.printStatistic();
        System.out.println("Done.");
    }

    private static void resolveMatcherAndSearchable(String arg) {

        if (isRegular.matches("[Yy]")) {
            matcher = new RegexpMatcher();
            searchable = ".*" + arg + ".*";

        } else {
            matcher = new ContainsMatcher();
            searchable = arg;
        }
    }


    private static boolean isHelpCalled(String[] args) {
        return !(args.length == 5);
    }


    private static void printHelp() {
        System.out.println(
                new StringBuilder()
                        .append("This is simple tool for recursively search of appropriate lines in some log archive (directory tree).").append(System.lineSeparator())

                        .append("Search types:").append(System.lineSeparator())
                        .append("   contains    - checks if line contains specific character sequence.").append(System.lineSeparator())
                        .append("   regexp      - checks if line matches specific regexp.").append(System.lineSeparator())

                        .append("Output is file that has format: <file_name> <founded_line>.").append(System.lineSeparator()).append(System.lineSeparator())

                        .append("Launch params:   <root_dir> <file_format> <regex_flag> <looking_for> <out_file>").append(System.lineSeparator())
                        .append("   <root_dir>    - start dir path, for recursively search.").append(System.lineSeparator())
                        .append("   <file_format> - format of searchable files.").append(System.lineSeparator())
                        .append("   <regex_flag>  - [y] - regex search. [n] - contains search. Flag isn't case sensitive.").append(System.lineSeparator())
                        .append("   <looking_for> - searchable character sequence OR regular expression that matches searchable line.").append(System.lineSeparator())
                        .append("                   RU manual about regex is here: http://www.quizful.net/post/Java-RegExp").append(System.lineSeparator())
                        .append("   <out_file>    - output file path. If exist - will be deleted. Be careful.").append(System.lineSeparator()).append(System.lineSeparator())

                        .append("Launch example (for *nix):").append(System.lineSeparator())
                        .append("java -jar logminer.jar \"/user/anon/logs\" \".log\" \"n\" \"LoremIpsum\" \"/user/anon/out.txt\"").append(System.lineSeparator())
                        .append("java -jar logminer.jar \"/user/anon/logs\" \".log\" \"y\" \"(Lorem|Ipsum|Dolor)\" \"/user/anon/out.txt\"").append(System.lineSeparator()).append(System.lineSeparator())

                        .append("govnokoded by AckiyBolt©. All rigths are not reserved.")
        );
    }
}
