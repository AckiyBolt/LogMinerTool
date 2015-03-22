package ua.bolt.logminer.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ackiybolt on 20.03.15.
 */
public class MineResult {
    private List<String> lines;
    private String fileName;

    public MineResult(String fileName) {
        this.fileName = fileName;
        lines = new LinkedList<>();
    }

    public void addLine (String line) {
        lines.add(line);
    }

    public boolean isEmpty() {
        return lines.isEmpty();
    }

    public List<String> getLines() {
        return lines;
    }

    public String getFileName() {
        return fileName;
    }

    public int size () {
        return lines.size();
    }
}
