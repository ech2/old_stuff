package net.wintermuse.util;

import net.wintermuse.util.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by oscii on 22/04/14.
 */
public class PairTest {
    @Test
    public void equals_12vs12_Equals() {
        Pair<Integer, Integer> couple_1 = Pair.of(1,2);
        Pair<Integer, Integer> couple_2 = Pair.of(1,2);

        assertEquals(couple_1, couple_2);
    }
    @Test
    public void equals_12vs21_NotEquals() {
        Pair<Integer, Integer> couple_12 = Pair.of(1,2);
        Pair<Integer, Integer> couple_21 = Pair.of(2,1);

        assertNotEquals(couple_12, couple_21);
    }
    @Test
    public void equals_12vs11_NotEquals() {
        Pair<Integer, Integer> couple_12 = Pair.of(1,2);
        Pair<Integer, Integer> couple_11 = Pair.of(1,1);

        assertNotEquals(couple_12, couple_11);
    }
    @Test
    public void equals_12vs22_NotEquals() {
        Pair<Integer, Integer> couple_12 = Pair.of(1,2);
        Pair<Integer, Integer> couple_22 = Pair.of(2,2);

        assertNotEquals(couple_12, couple_22);
    }
    @Test
    public void equals_11vs12_NotEquals() {
        Pair<Integer, Integer> couple_11 = Pair.of(1,1);
        Pair<Integer, Integer> couple_12 = Pair.of(1,2);

        assertNotEquals(couple_11, couple_12);
    }
    @Test
    public void equals_22vs12_NotEquals() {
        Pair<Integer, Integer> couple_22 = Pair.of(2,2);
        Pair<Integer, Integer> couple_12 = Pair.of(1,2);

        assertNotEquals(couple_22, couple_12);
    }
}
