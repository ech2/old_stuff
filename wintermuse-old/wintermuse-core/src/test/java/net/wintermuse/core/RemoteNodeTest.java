package net.wintermuse.core;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by oscii on 20/04/14.
 */
public class RemoteNodeTest {
    @Test
    public void Constructor_CustomUid() {
        UUID customUid = UUID.randomUUID();
        RemoteNode n1 = new RemoteNode(customUid);
        RemoteNode n2 = new RemoteNode(customUid.toString());

        assertEquals(customUid, n1.getUid());
        assertEquals(customUid, n2.getUid());
    }


    @Test
    public void testEquals() {
        UUID customUid = UUID.randomUUID();
        RemoteNode n1 = new RemoteNode(customUid);
        RemoteNode n2 = new RemoteNode(customUid);

        assertEquals(n1, n2);
    }
}
