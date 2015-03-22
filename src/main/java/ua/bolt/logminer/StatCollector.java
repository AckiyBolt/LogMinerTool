package ua.bolt.logminer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ackiybolt on 22.03.15.
 */
public class StatCollector {

    private static long startTime;
    private static long endTime;
    private static int foundedFiles;
    private static AtomicInteger filesWithContent = new AtomicInteger();
    private static AtomicInteger linesWasFounded = new AtomicInteger();


    public static void searchStarting() {
        startTime = System.currentTimeMillis();
    }

    public static void searchEnding() {
        endTime = System.currentTimeMillis();
    }

    private static long calcSearchDuration() {
        return endTime - startTime;
    }


    public static void setFoundedFiles(int count) {
        foundedFiles = count;
    }

    public static void contentFound(int recordsCount) {
        filesWithContent.getAndIncrement();
        linesWasFounded.getAndAdd(recordsCount);
    }



    public static void printStatistic() {

        System.out.println(new StringBuilder()
                        .append("Search took:                                       ")
                        .append(StatCollector.calcSearchDuration() + " millis")
                        .append(System.lineSeparator())

                        .append("Appropriate files was founded:                     ")
                        .append(foundedFiles)
                        .append(System.lineSeparator())

                        .append("Files that contains lines with searchable content: ")
                        .append(filesWithContent.get())
                        .append(System.lineSeparator())

                        .append("Lines with searchable content was founded:         ")
                        .append(linesWasFounded.get())
        );
    }
}
