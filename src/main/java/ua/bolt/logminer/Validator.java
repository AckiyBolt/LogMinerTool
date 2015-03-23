package ua.bolt.logminer;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by ackiybolt on 23.03.15.
 */
public class Validator {

    public static boolean isArgsVaild(String[] args) {

        boolean result = true;

        result = result && isRootDirValid(args[0]);
        result = result && isOutFilePathValid(args[3]);
        result = result && isRegexFlagValid(args[2]);
        if (isRegexFlagY(args[2]))
            result = result && isCriteriaValid(args[3]);

        return result;
    }

    public static boolean isRegexFlagValid(String arg) {
        boolean result = true;

        if (!arg.matches("([Yy]|[Nn])")) {
            result = false;
            System.out.println("Regular expression flag is wrong.");
        }

        return result;
    }

    private static boolean isRegexFlagY(String arg) {
        return arg.matches("[Yy]");
    }

    private static boolean isCriteriaValid(String arg) {

        boolean result = true;

        try {
            Pattern.compile(arg);

        } catch (PatternSyntaxException ex) {
            System.out.println("Bad syntax in regular exception.");
            result = false;
        }

        return result;
    }

    private static boolean isOutFilePathValid(String arg) {
        boolean result = true;

        File outFile = new File(arg);
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

    private static boolean isRootDirValid(String arg) {
        boolean result = true;

        File rootDir = new File(arg);
        if (!rootDir.exists() && !rootDir.isDirectory() && !rootDir.canRead()) {
            System.out.println("Root directory doesn't exist OR it's not a directory OR I haven't read permission for it.");
            result = false;

        } else {
            System.out.println("Root directory exist.");
        }
        return result;
    }
}
