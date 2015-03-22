package ua.bolt.logminer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ExecutorTest {

    private static final int COUNT_OF_ALL = 10;

    private static final String TEST_FOLDER = "test/";
    private static final String OUTPUT_FILE = "test/out.txt";
    private static final String CORRECT_TEST_FILE = "src/test/resources/out_for_correct.txt";
    private static final String EMPTY_TEST_FILE = "src/test/resources/out_empty.txt";


    private static final String CORRECT_FORMAT = ".log";
    private static final String WRONG_FORMAT = ".txt";

    private static final String LINE_WITHOUT_CORRECT_CONTENT = "Ut enim ad minim veniam, quis nostrud exercitation";
    private static final String LINE_WITH_CORRECT_CONTENT = "neque porro quisquam est, qui dolorem ipsum, quia dolor sit";
    private static final String CORRECT_CONTENT = "lorem ipsum";


    private Executor undertest;

    @Before
    public void setUp() throws Exception {
        undertest = new Executor();
        File testDir = new File(TEST_FOLDER);
        createDirs();

        recreateOutFile(OUTPUT_FILE);
    }

    @After
    public void clean() throws Exception {
        File testDir = new File(TEST_FOLDER);
        deleteTestDirIfExist(testDir);
    }

    @Test
    public void testExecuteFor_whenFilesExist() throws Exception {

        createFiles(WRONG_FORMAT, true);
        createFiles(CORRECT_FORMAT, true);

        undertest.executeFor(Paths.get(TEST_FOLDER), CORRECT_FORMAT, CORRECT_CONTENT, new File(OUTPUT_FILE));

        assertTrue(Arrays.equals(getFileHash(CORRECT_TEST_FILE), getFileHash(OUTPUT_FILE)));
    }

    @Test
    public void testExecuteFor_whenFilesExistButWithoutContent() throws Exception {

        createFiles(WRONG_FORMAT, true);
        createFiles(CORRECT_FORMAT, false);

        undertest.executeFor(Paths.get(TEST_FOLDER), CORRECT_FORMAT, CORRECT_CONTENT, new File(OUTPUT_FILE));

        assertTrue(Arrays.equals(getFileHash(EMPTY_TEST_FILE), getFileHash(OUTPUT_FILE)));
    }

    @Test
    public void testExecuteFor_whenNoFiles() throws Exception {

        createFiles(WRONG_FORMAT, true);

        undertest.executeFor(Paths.get(TEST_FOLDER), CORRECT_FORMAT, CORRECT_CONTENT, new File(OUTPUT_FILE));

        assertTrue(Arrays.equals(getFileHash(EMPTY_TEST_FILE), getFileHash(OUTPUT_FILE)));
    }



    private void deleteTestDirIfExist(File dir) {
        if (dir.exists() && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                File f = new File(dir, children[i]);
                deleteTestDirIfExist(f);
            }
            dir.delete();
        } else dir.delete();

    }

    private File recreateOutFile(String outputFile) throws Exception {
        File result = new File(outputFile);
        result.delete();
        result.createNewFile();
        return result;
    }

    private void createDirs() {
        for (int i = 0; i < COUNT_OF_ALL; i++) {
            new File(TEST_FOLDER + i + "/" + (i * 2) + "/").mkdirs();
        }
    }

    private void createFiles(String format, boolean isCorrectContentExist) throws Exception {
        for (int i = 0; i < COUNT_OF_ALL; i++) {
            File file = new File(TEST_FOLDER + i + "/" + (i * 2) + "/" + i + format);
            file.createNewFile();
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter
                        .append(LINE_WITHOUT_CORRECT_CONTENT)
                        .append(System.lineSeparator())
                        .append(isCorrectContentExist ? LINE_WITH_CORRECT_CONTENT : "")
                        .append(System.lineSeparator())
                        .append(LINE_WITHOUT_CORRECT_CONTENT);
                fileWriter.flush();
            }
        }
    }

    private byte [] getFileHash (String filePath) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(Paths.get(filePath))) {
            DigestInputStream dis = new DigestInputStream(is, md);
        }
        return md.digest();
    }
}