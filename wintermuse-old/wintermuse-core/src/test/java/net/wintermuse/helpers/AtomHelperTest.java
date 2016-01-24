package net.wintermuse.helpers;

import com.cycling74.max.Atom;
import net.wintermuse.core.MusicParams;
import net.wintermuse.helpers.AtomHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by oscii on 19/04/14.
 */
public class AtomHelperTest extends Assert {
    private List<Integer> expectedIntList = Arrays.asList(1,2,3,4,5);
    private MusicParams expectedMusicParams = new MusicParams(1,2,3,4,5);
    private Atom[] expectedAtomArray = new Atom[] {
        Atom.newAtom(1),
        Atom.newAtom(2),
        Atom.newAtom(3),
        Atom.newAtom(4),
        Atom.newAtom(5)
    };

    @Test
    public void checkAtomArrayApi() {
        Atom[] a1 = new Atom[] {
                Atom.newAtom(1),
                Atom.newAtom(2),
                Atom.newAtom(3),
                Atom.newAtom(4),
                Atom.newAtom(5)
        };
        Atom[] a2 = new Atom[] {
                Atom.newAtom(1),
                Atom.newAtom(2),
                Atom.newAtom(3),
                Atom.newAtom(4),
                Atom.newAtom(5)
        };
        assertArrayEquals(a1, a2);
    }


    /* musicParamsToAtomArray */

    @Test
    public void musicParamsToAtomArray_checkResultLength_5() {
        Atom[] atomArray = AtomHelper.musicParamsToAtomArray(expectedMusicParams);
        assertEquals(5, atomArray.length);
    }

    @Test
    public void musicParamsToAtomArray_allElementsAreInt() {
        Atom[] atomArray = AtomHelper.musicParamsToAtomArray(expectedMusicParams);
        for (Atom atom : atomArray) {
            assertTrue(atom.isInt());
        }
    }

    @Test
    public void musicParamsToAtomArray_resultEqualToExpected() {
        Atom[] atomArray = AtomHelper.musicParamsToAtomArray(expectedMusicParams);
        for (int i = 0; i < atomArray.length; i++) {
            assertEquals(expectedIntList.get(i), (Integer) atomArray[i].getInt());
        }
    }

    @Test
    public void musicParamsToAtomArray_resultNotEqualToExpected() {
        MusicParams wrongData = new MusicParams(6,7,8,9,10);
        Atom[] atomArray = AtomHelper.musicParamsToAtomArray(wrongData);
        for (int i = 0; i < atomArray.length; i++) {
            assertNotEquals(expectedIntList.get(i), (Integer) atomArray[i].getInt());
        }
    }


    /* atomArrayToIntegerList */

    @Test
    public void atomArrayToIntegerList_resultLength_5() {
        List<Integer> resultList = AtomHelper.atomArrayToIntegerList(expectedAtomArray);
        assertEquals(5, resultList.size());
    }

    @Test
    public void atomArrayToIntegerList_resultEqualToExpected() {
        List<Integer> resultList = AtomHelper.atomArrayToIntegerList(expectedAtomArray);
        assertEquals(expectedIntList, resultList);
    }

    @Test
    public void atomArrayToIntegerList_resultNotEqualToExpected() {
        Atom[] wrongData = new Atom[] {
                Atom.newAtom(2),
                Atom.newAtom(3),
                Atom.newAtom(4),
                Atom.newAtom(5),
                Atom.newAtom(6)
        };
        List<Integer> resultList = AtomHelper.atomArrayToIntegerList(wrongData);
        assertNotEquals(expectedIntList, resultList);
    }


    /* atomArrayToMusicParams */

    @Test
    public void atomArrayToMusicParams_resultEqualToExpected() {
        MusicParams result = AtomHelper.atomArrayToMusicParams(expectedAtomArray);
        assertEquals(expectedMusicParams, result);
    }

    @Test
    public void atomArrayToMusicParams_resultNotEqualToExpected() {
        Atom[] wrongData = new Atom[] {
                Atom.newAtom(2),
                Atom.newAtom(3),
                Atom.newAtom(4),
                Atom.newAtom(5),
                Atom.newAtom(6)
        };
        MusicParams result = AtomHelper.atomArrayToMusicParams(wrongData);
        assertNotEquals(expectedAtomArray, result);
    }

}
