package net.wintermuse.core;

import java.util.UUID;

/**
 * Created by oscii on 12/04/14.
 */
public class RemoteNode extends AbstractNode {

    public RemoteNode(UUID remoteUid) {
        super(remoteUid.toString());
    }

    public RemoteNode(String remoteUid) {
        super(remoteUid);
    }
}
