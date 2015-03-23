package ua.bolt.logminer.matcher;

/**
 * Created by ackiybolt on 23.03.15.
 */
public class ContainsMatcher implements Matcher {
    @Override
    public boolean isMatches(String line, String criteria) {
        return line.contains(criteria);
    }
}
