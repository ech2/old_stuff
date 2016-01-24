package net.wintermuse.core;

import net.wintermuse.core.MusicParams;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by oscii on 19/04/14.
 */
public class MusicParamsTest extends Assert{
    private MusicParams testData = new MusicParams(1,2,3,4,5);

    /* equals() */

    @Test
    public void equals_true() {
        MusicParams p = new MusicParams(1,2,3,4,5);
        assertTrue(p.equals(testData));
        assertTrue(testData.equals(p));
    }
    @Test
    public void equals_false() {
        MusicParams p = new MusicParams(2,3,4,5,6);
        assertFalse(p.equals(testData));
        assertFalse(testData.equals(p));
    }


    /* Constructor equality */

    @Test
    public void Constructor_Default() {
        MusicParams p1 = new MusicParams();
        MusicParams p2 = new MusicParams(0,0,0,0,0);
        assertEquals(p1, p2);
        assertNotEquals(p1, testData);
    }
    @Test
    public void Constructor_Integers() {
        assertEquals(1, testData.getDrums());
        assertEquals(2, testData.getBass());
        assertEquals(3, testData.getSfx());
        assertEquals(4, testData.getSweepType());
        assertEquals(5, testData.getSweepTrig());
    }
    @Test
    public void Constructor_MusicParams() {
        MusicParams p = new MusicParams(testData);
        assertEquals(testData, p);
    }
    @Test
    public void Constructor_ListOfIntegers() {
        List<Integer> testList = Arrays.asList(1,2,3,4,5);
        MusicParams p = new MusicParams(testList);
        assertEquals(testData, p);
    }
    @Test(expected = IllegalArgumentException.class)
    public void Constructor_ListOf6Integers_ThrowIAE() {
        List<Integer> testList = Arrays.asList(1,2,3,4,5,6);
        MusicParams p = new MusicParams(testList);
    }
    @Test(expected = IllegalArgumentException.class)
    public void Constructor_ListOf5Integers_ThrowIAE() {
        List<Integer> testList = Arrays.asList(1,2,3,4);
        MusicParams p = new MusicParams(testList);
    }



    /* Other */

    @Test
    public void getters_true() {
        assertEquals(1, testData.getDrums());
        assertEquals(2, testData.getBass());
        assertEquals(3, testData.getSfx());
        assertEquals(4, testData.getSweepType());
        assertEquals(5, testData.getSweepTrig());
    }

}
