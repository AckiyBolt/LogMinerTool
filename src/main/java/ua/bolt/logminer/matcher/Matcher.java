package ua.bolt.logminer.matcher;

/**
 * Created by ackiybolt on 23.03.15.
 */
public interface Matcher {

    boolean isMatches(String line, String criteria);
}
