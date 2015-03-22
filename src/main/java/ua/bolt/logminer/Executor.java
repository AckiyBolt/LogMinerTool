package ua.bolt.logminer;

import ua.bolt.logminer.domain.MineResult;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ackiybolt on 22.03.15.
 */
public class Executor {

    static final Integer POOL_SIZE = 12;

    private FilesFinder filesFinder;

    public Executor() {
        filesFinder = new FilesFinder();
    }

    public void executeFor(Path folder, String filesFormat, String searchable, File outFile) {

        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);

        Set<Path> files = filesFinder.findFilesInFolder(folder, filesFormat);
        StatCollector.setFoundedFiles(files.size());

        List<Future<MineResult>> futureResults = searchLinesInFiles(searchable, files, executor);
        printResults(futureResults, outFile);

        executor.shutdown();
    }

    private void printResults(List<Future<MineResult>> futureResults, File outFile)  {

        ResultWriter fileWriter = new ResultWriter();
        fileWriter.createNewWriterForFile(outFile);

        for(Future<MineResult> fut : futureResults){
            try {
                MineResult result = fut.get();

                if (!result.isEmpty())
                    fileWriter.printResults(result);

            } catch (InterruptedException ex) {
                ex.printStackTrace();

            } catch (ExecutionException ex) {
                ex.printStackTrace();
            }
        }
        fileWriter.closeWriter();
    }

    private List<Future<MineResult>> searchLinesInFiles(String searchable, Set<Path> files, ExecutorService executor) {

        List<Future<MineResult>> futureResults = new ArrayList<>(files.size());

        for (Path file: files) {
            SearchWorker callable = new SearchWorker(file.toFile(), searchable);
            futureResults.add(executor.submit(callable));
        }
        return futureResults;
    }
}
