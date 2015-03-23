package ua.bolt.logminer;

import ua.bolt.logminer.domain.MineResult;
import ua.bolt.logminer.matcher.Matcher;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * Created by ackiybolt on 20.03.15.
 */
public class SearchWorker implements Callable<MineResult> {

    private MineResult result;
    private File file;
    private String criteria;
    private Matcher matcher;

    public SearchWorker(File file, String criteria, Matcher matcher){
        this.file = file;
        this.criteria = criteria;
        this.matcher = matcher;
        this.result = new MineResult(file.getName());
    }

    @Override
    public MineResult call() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String strLine;

            while ((strLine = br.readLine()) != null) {
                if (matcher.isMatches(strLine, criteria))
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
