package ua.bolt.logminer;

import ua.bolt.logminer.domain.MineResult;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * Created by ackiybolt on 20.03.15.
 */
public class SearchWorker implements Callable<MineResult> {

    private MineResult result;
    private File file;
    private String pattern;

    public SearchWorker(File file, String pattern){
        this.file = file;
        this.pattern = pattern;
        this.result = new MineResult(file.getName());
    }

    @Override
    public MineResult call() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String strLine;

            while ((strLine = br.readLine()) != null) {
                if (strLine.matches(pattern))
                    result.addLine(strLine);
            }

        } catch (IOException ex) {
            System.out.println(ex);
            throw new RuntimeException(ex);
        }

        if (result.size() > 0)
            StatCollector.contentFound(result.size());

        return result;
    }
}
