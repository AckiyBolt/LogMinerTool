package ua.bolt.logminer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileVisitorTest extends FilesFinder {

    public static final String SOME_FORMAT = "some format";
    public static final String SOME_OTHER_FORMAT = "some other format";

    private FilesFinder.FileVisitor undertest;
    private Set<Path> undertestResultSet;


    @Mock
    private Path mockPathWithAcceptableFile;
    @Mock
    private Path mockAcceptableFile;
    @Mock
    private Path mockPathWithNotAcceptableFile;
    @Mock
    private Path mockNotAcceptableFile;
    @Mock
    private BasicFileAttributes mockAttributes;


    @Before
    public void setUp() throws Exception {
        undertestResultSet = new TreeSet<>();
        undertest = new FileVisitor(SOME_FORMAT, undertestResultSet);

        when(mockPathWithAcceptableFile.getFileName()).thenReturn(mockAcceptableFile);
        when(mockAcceptableFile.toString()).thenReturn(SOME_FORMAT);

        when(mockPathWithNotAcceptableFile.getFileName()).thenReturn(mockNotAcceptableFile);
        when(mockNotAcceptableFile.toString()).thenReturn(SOME_OTHER_FORMAT);
    }


    @Test
    public void testVisitFile_whenMethodCalledWithAcceptableFile_thenResultSetContainsThisFile() throws Exception {

        undertest.visitFile(mockPathWithAcceptableFile, mockAttributes);

        assertTrue(undertestResultSet.contains(mockPathWithAcceptableFile));
    }

    @Test
    public void testVisitFile_whenMethodCalledWithNotAcceptableFile_thenResultSetNotContainsThisFile() throws Exception {

        undertest.visitFile(mockPathWithNotAcceptableFile, mockAttributes);

        assertFalse(undertestResultSet.contains(mockPathWithNotAcceptableFile));
    }

    @Test
    public void testVisitFile_whenMethodCalled_thenContinue() throws Exception {

        FileVisitResult actual = undertest.visitFile(mockPathWithAcceptableFile, mockAttributes);
        FileVisitResult expected = FileVisitResult.CONTINUE;

        assertEquals(expected, actual);
    }

    @Test
    public void testVisitFileFailed_whenMethodCalled_thenContinue() throws Exception {

        FileVisitResult expected = FileVisitResult.CONTINUE;
        FileVisitResult actual = undertest.visitFileFailed(mockPathWithAcceptableFile, new IOException());

        assertEquals(expected, actual);
    }
}