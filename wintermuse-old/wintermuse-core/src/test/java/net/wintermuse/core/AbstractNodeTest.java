package net.wintermuse.core;

import net.wintermuse.core.AbstractNode;
import net.wintermuse.core.MusicParams;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by oscii on 19/04/14.
 */
public class AbstractNodeTest {
    class AbstractNodeMock extends AbstractNode {
        public AbstractNodeMock() {
            super();
        }
        public AbstractNodeMock(String uid) {
            super(uid);
        }
    }


    @Test
    public void getUid_UidConstructor() {
        UUID constructorUid = UUID.randomUUID();
        UUID falseUid = UUID.randomUUID();
        AbstractNode nString = new AbstractNodeMock(constructorUid.toString());
        AbstractNode nDefault = new AbstractNodeMock();

        assertEquals(constructorUid, nString.getUid());
        assertNotEquals(nString.getUid(), nDefault.getUid());
        assertNotEquals(falseUid, nDefault.getUid());
    }
    @Test
    public void getUid_DefaultConstructor_UidNotNull() {
        AbstractNode n = new AbstractNodeMock();
        assertNotEquals(null, n.getUid());
    }

    @Test
    public void setCurrentSettings_List5Integers_True() {
        List<Integer> params = Arrays.asList(1,2,3,4,5);
        AbstractNode n = new AbstractNodeMock();

        assertTrue(n.setCurrentSettings(params));
    }
    @Test
    public void setCurrentSettings_List4Integers_False() {
        List<Integer> params = Arrays.asList(1,2,3,4);
        AbstractNode n = new AbstractNodeMock();

        assertFalse(n.setCurrentSettings(params));
    }
    @Test
    public void setCurrentSettings_List6Integers_False() {
        List<Integer> params = Arrays.asList(1,2,3,4,5,6);
        AbstractNode n = new AbstractNodeMock();

        assertFalse(n.setCurrentSettings(params));
    }
    @Test(expected = NullPointerException.class)
    public void setCurrentSettings_ListIntegerNull_ThrowNPE() {
        List<Integer> params = null;
        AbstractNode n = new AbstractNodeMock();
        n.setCurrentSettings(params);
    }
    @Test(expected = NullPointerException.class)
    public void setCurrentSettings_MusicParamsNull_ThrowNPE() {
        MusicParams params = null;
        AbstractNode n = new AbstractNodeMock();
        n.setCurrentSettings(params);
    }
    @Test
    public void setAndGetCurrentSettings_MusicParams() {
        MusicParams params = new MusicParams(1,2,3,4,5);
        AbstractNode n = new AbstractNodeMock();
        n.setCurrentSettings(params);

        assertEquals(params, n.getCurrentSettings());
    }


}
