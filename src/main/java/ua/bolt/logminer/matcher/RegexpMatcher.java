package ua.bolt.logminer.matcher;

/**
 * Created by ackiybolt on 23.03.15.
 */
public class RegexpMatcher implements Matcher {

    @Override
    public boolean isMatches(String line, String criteria) {
        return line.matches(criteria);
    }
}
