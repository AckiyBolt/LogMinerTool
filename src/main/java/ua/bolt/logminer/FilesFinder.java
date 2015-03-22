package ua.bolt.logminer;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ackiybolt on 21.03.15.
 */
public class FilesFinder {

    public Set<Path> findFilesInFolder (final Path folder, final String fileFormat) {

        final Set<Path> result = new TreeSet<>();

        try {
            Files.walkFileTree(folder, new FileVisitor(fileFormat, result) );

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    protected class FileVisitor extends SimpleFileVisitor<Path> {

        private String fileFormat;
        private Set<Path> resultSet;

        public FileVisitor(String fileFormat, Set<Path> resultSet) {
            this.fileFormat = fileFormat;
            this.resultSet = resultSet;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {

            if (file.getFileName().toString().contains(fileFormat))
                resultSet.add(file);

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException ex)
                throws IOException {

            System.out.println("Can't read file: " + file + "\n" + ex);

            return FileVisitResult.CONTINUE;
        }
    }
}
