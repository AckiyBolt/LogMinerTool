package ua.bolt.logminer.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MineResultTest {

    private MineResult undertest;

    @Before
    public void setUp() {
        undertest = new MineResult("undertest");
    }

    @Test
    public void testAddLine_givenEmptyResults_whenAddTwoLines_thenSizeIncreasesOnTwo() throws Exception {

        undertest.addLine("line 1");
        undertest.addLine("line 2");

        int expected = 2;
        int actual = undertest.getLines().size();

        assertEquals(expected, actual);
    }

    @Test
    public void testIsEmpty_givenEmptyResults_whenDoNothing_thenIsTrue() throws Exception {
        assertTrue(undertest.isEmpty());
    }

    @Test
    public void testIsEmpty_givenEmptyResults_whenAddLines_thenIsFalse() throws Exception {

        undertest.addLine("line 1");
        undertest.addLine("line 2");

        assertFalse(undertest.isEmpty());
    }
}