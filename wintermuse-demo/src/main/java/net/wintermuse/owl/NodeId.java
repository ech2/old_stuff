package net.wintermuse.owl;

import java.net.Inet4Address;

/**
 * User: ech
 * Date: 8/4/14
 * Time: 14:48
 */
public class NodeId {
    private NodeState nodeState;
    private Inet4Address ipAddress;

    public NodeId(NodeState nodeState, Inet4Address ipAddress) {
        this.nodeState = nodeState;
        this.ipAddress = ipAddress;
    }

    public NodeState getNodeState() {
        return nodeState;
    }

    public void setNodeState(NodeState nodeState) {
        this.nodeState = nodeState;
    }

    public Inet4Address getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Inet4Address ipAddress) {
        this.ipAddress = ipAddress;
    }

/*
    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public Collection<Statement> getStatements() {
        return null;
    }
*/
}
