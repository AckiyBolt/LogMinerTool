package ua.bolt.logminer;

import ua.bolt.logminer.domain.MineResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ackiybolt on 21.03.15.
 */
public class ResultWriter {

    private FileWriter fileWriter;

    public void createNewWriterForFile (File outFile) {
        try {
            if (fileWriter != null)
                closeWriter();
                fileWriter = new FileWriter(outFile);

        } catch (IOException ex) {
            System.out.println("File for writing does not exist.");
            throw new RuntimeException(ex);
        }
    }

    public void printResults(MineResult result) {

        checkExistence();

        if (!result.isEmpty())
            for (String line : result.getLines())
                try {
                    fileWriter
                            .append(result.getFileName())
                            .append(" ")
                            .append(line)
                            .append(System.lineSeparator());

                } catch (IOException ex) {
                    System.out.println("Can't write to file");
                    throw new RuntimeException(ex);
                }
    }

    public void closeWriter () {
        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("Can't close ResultWriter.");
            throw new RuntimeException(ex);
        }
    }

    private void checkExistence() {
        if (fileWriter == null) {
            throw new RuntimeException("FileWriter hasn't been created. Call createNewWriterForFile() method first.");
        }
    }
}
