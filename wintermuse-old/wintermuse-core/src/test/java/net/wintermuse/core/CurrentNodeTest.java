package net.wintermuse.core;

import net.wintermuse.core.CurrentNode;
import net.wintermuse.core.MusicParams;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by oscii on 19/04/14.
 */
public class CurrentNodeTest {

    @Test
    public void Constructor_UniqueUid() {
        CurrentNode n = new CurrentNode();
        CurrentNode n1 = new CurrentNode();
        UUID testUid = UUID.randomUUID();

        assertNotEquals(n.getUid(), n1.getUid());
        assertNotEquals(n.getUid(), testUid);
    }


    @Test
    public void setUserSettings_ListOf4Integers_False() {
        List<Integer> params = Arrays.asList(1,2,3,4);
        CurrentNode n = new CurrentNode();
        assertFalse(n.setUserSettings(params));
    }
    @Test
    public void setUserSettings_ListOf5Integers_True() {
        List<Integer> params = Arrays.asList(1,2,3,4,5);
        CurrentNode n = new CurrentNode();
        assertTrue(n.setUserSettings(params));
    }
    @Test
    public void setUserSettings_ListOf6Integers_True() {
        List<Integer> params = Arrays.asList(1,2,3,4,5,6);
        CurrentNode n = new CurrentNode();
        assertFalse(n.setUserSettings(params));
    }
    @Test(expected = NullPointerException.class)
    public void setUserSettings_ListNull_ThrowNPE() {
        List<Integer> params = null;
        CurrentNode n = new CurrentNode();
        n.setUserSettings(params);
    }
    @Test
    public void setAndGetUserSettings_MusicParams_OK() {
        MusicParams params = new MusicParams(1,2,3,4,5);
        CurrentNode n = new CurrentNode();
        n.setUserSettings(params);

        assertEquals(params, n.getUserSettings());
    }
    @Test(expected = NullPointerException.class)
    public void setUserSettings_MusicParamsNull_ThrowNPE() {
        MusicParams params = null;
        CurrentNode n = new CurrentNode();
        n.setUserSettings(params);
    }

}
