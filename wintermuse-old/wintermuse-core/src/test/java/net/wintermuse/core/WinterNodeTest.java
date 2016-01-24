package net.wintermuse.core;

import net.wintermuse.core.MusicParams;
import net.wintermuse.core.RemoteNode;
import net.wintermuse.core.WinterNode;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by oscii on 20/04/14.
 */
public class WinterNodeTest {

    /* set/getUserSettings */

    @Test
    public void setUserSettings_ListWith4Ints_NothingChanged() {
        WinterNode n = new WinterNode();
        List<Integer> params = Arrays.asList(1,2,3,4);
        n.setUserSettings(params);

        assertEquals(new MusicParams(), n.getUserSettings());
    }
    @Test
    public void setUserSettings_ListWith5Ints_UserSettingsUpdated() {
        WinterNode n = new WinterNode();
        List<Integer> params = Arrays.asList(1,2,3,4,5);
        n.setUserSettings(params);

        assertEquals(new MusicParams(params), n.getUserSettings());
    }
    @Test
    public void setUserSettings_ListWith6Ints_NothingChanged() {
        WinterNode n = new WinterNode();
        List<Integer> params = Arrays.asList(1,2,3,4,5,6);
        n.setUserSettings(params);

        assertEquals(new MusicParams(), n.getUserSettings());
    }
    @Test
    public void setUserSettings_ListNull_NothingChanged() {
        WinterNode n = new WinterNode();
        List<Integer> params = null;
        n.setUserSettings(params);

        assertEquals(new MusicParams(), n.getUserSettings());
    }
    @Test
    public void setUserSettings_MusicParams_UserSettingsUpdated() {
        WinterNode n = new WinterNode();
        MusicParams params = new MusicParams(1,2,3,4,5);
        n.setUserSettings(params);

        assertEquals(params, n.getUserSettings());
    }
    @Test
    public void setUserSettings_MusicParamsNull_NothingChanged() {
        WinterNode n = new WinterNode();
        MusicParams params = null;
        n.setUserSettings(params);

        assertEquals(new MusicParams(), n.getUserSettings());
    }
    
    
    /* set/getCurrentSettings */

    @Test
    public void setCurrentSettings_ListWith4Ints_NothingChanged() {
        WinterNode n = new WinterNode();
        List<Integer> params = Arrays.asList(1,2,3,4);
        n.setCurrentSettings(params);

        assertEquals(new MusicParams(), n.getCurrentSettings());
    }
    @Test
    public void setCurrentSettings_ListWith5Ints_CurrentSettingsUpdated() {
        WinterNode n = new WinterNode();
        List<Integer> params = Arrays.asList(1,2,3,4,5);
        n.setCurrentSettings(params);

        assertEquals(new MusicParams(params), n.getCurrentSettings());
    }
    @Test
    public void setCurrentSettings_ListWith6Ints_NothingChanged() {
        WinterNode n = new WinterNode();
        List<Integer> params = Arrays.asList(1,2,3,4,5,6);
        n.setCurrentSettings(params);

        assertEquals(new MusicParams(), n.getCurrentSettings());
    }
    @Test
    public void setCurrentSettings_ListNull_NothingChanged() {
        WinterNode n = new WinterNode();
        List<Integer> params = null;
        n.setCurrentSettings(params);

        assertEquals(new MusicParams(), n.getCurrentSettings());
    }
    @Test
    public void setCurrentSettings_MusicParams_CurrentSettingsUpdated() {
        WinterNode n = new WinterNode();
        MusicParams params = new MusicParams(1,2,3,4,5);
        n.setCurrentSettings(params);

        assertEquals(params, n.getCurrentSettings());
    }
    @Test
    public void setCurrentSettings_MusicParamsNull_NothingChanged() {
        WinterNode n = new WinterNode();
        MusicParams params = null;
        n.setCurrentSettings(params);

        assertEquals(new MusicParams(), n.getCurrentSettings());
    }


    /* Other */

    @Test
    public void getUidString_NotNull() {
        WinterNode n = new WinterNode();
        assertNotEquals(null, n.getUidString());
    }


    /* remoteMessage() */

    @Test
    public void remoteMessage_newMessageArrived() {
        WinterNode n = new WinterNode();

        UUID uid = UUID.randomUUID();
        MusicParams params = new MusicParams(1,2,3,4,5);

        RemoteNode remoteNode = new RemoteNode(uid);
        remoteNode.setCurrentSettings(params);


        n.remoteMessage(uid.toString(), params);

        assertEquals(1, n.getRemoteNodes().size());
        assertTrue(n.getRemoteNodes().contains(remoteNode));
    }
    @Test
    public void remoteMessage_twoNewMessagesArrived() {
        WinterNode n = new WinterNode();

        // message/node 1
        UUID uid1 = UUID.randomUUID();
        MusicParams params1 = new MusicParams(1,2,3,4,5);

        RemoteNode remoteNode1 = new RemoteNode(uid1);
        remoteNode1.setCurrentSettings(params1);

        // message/node 2
        UUID uid2 = UUID.randomUUID();
        MusicParams params2 = new MusicParams(2,3,4,5,6);

        RemoteNode remoteNode2 = new RemoteNode(uid2);
        remoteNode2.setCurrentSettings(params2);

        // adding messages
        n.remoteMessage(uid1.toString(), params1);
        n.remoteMessage(uid2.toString(), params2);

        assertEquals(2, n.getRemoteNodes().size());
        assertTrue(n.getRemoteNodes().contains(remoteNode1));
        assertTrue(n.getRemoteNodes().contains(remoteNode2));
    }
    @Test
    public void remoteMessage_messageUpdated() {
        WinterNode n = new WinterNode();

        // old message
        UUID uid = UUID.randomUUID();
        MusicParams paramsOld = new MusicParams(1,2,3,4,5);

        RemoteNode remoteNodeOld = new RemoteNode(uid);
        remoteNodeOld.setCurrentSettings(paramsOld);

        n.remoteMessage(uid.toString(), paramsOld);

        // new message
        MusicParams paramsNew = new MusicParams(2,3,4,5,6);

        RemoteNode remoteNodeNew = new RemoteNode(uid);
        remoteNodeNew.setCurrentSettings(paramsNew);

        n.remoteMessage(uid.toString(), paramsNew);

        assertEquals(1, n.getRemoteNodes().size());
        assertTrue(n.getRemoteNodes().contains(remoteNodeNew));
    }
}
