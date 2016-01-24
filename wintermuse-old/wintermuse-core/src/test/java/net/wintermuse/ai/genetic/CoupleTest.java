package net.wintermuse.ai.genetic;

import net.wintermuse.ai.genetic.Couple;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by oscii on 22/04/14.
 */
public class CoupleTest {
    @Test
    public void equals_12vs12_Equals() {
        Couple<Integer> couple_1 = Couple.ofCouple(1,2);
        Couple<Integer> couple_2 = Couple.ofCouple(1,2);

        assertEquals(couple_1, couple_2);
    }
    @Test
    public void equals_12vs21_Equals() {
        Couple<Integer> couple_12 = Couple.ofCouple(1,2);
        Couple<Integer> couple_21 = Couple.ofCouple(2,1);

        assertEquals(couple_12, couple_21);
    }
    @Test
    public void equals_12vs11_NotEquals() {
        Couple<Integer> couple_12 = Couple.ofCouple(1,2);
        Couple<Integer> couple_11 = Couple.ofCouple(1,1);

        assertNotEquals(couple_12, couple_11);
    }
    @Test
    public void equals_12vs22_NotEquals() {
        Couple<Integer> couple_12 = Couple.ofCouple(1,2);
        Couple<Integer> couple_22 = Couple.ofCouple(2,2);

        assertNotEquals(couple_12, couple_22);
    }
    @Test
    public void equals_11vs12_NotEquals() {
        Couple<Integer> couple_11 = Couple.ofCouple(1,1);
        Couple<Integer> couple_12 = Couple.ofCouple(1,2);

        assertNotEquals(couple_11, couple_12);
    }
    @Test
    public void equals_22vs12_NotEquals() {
        Couple<Integer> couple_22 = Couple.ofCouple(2,2);
        Couple<Integer> couple_12 = Couple.ofCouple(1,2);

        assertNotEquals(couple_22, couple_12);
    }

}
