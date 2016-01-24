package net.wintermuse.owl;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ech
 * Date: 8/4/14
 * Time: 14:47
 */
public class NodeState {
    private List<NodeId> neighbors = new ArrayList<>();
    private double reliability;
    private SynthState synthState;
//    private URI uri;

    public NodeState(double reliability, SynthState synthState) {
        this.reliability = reliability;
        this.synthState = synthState;
    }

    public List<NodeId> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<NodeId> neighbors) {
        this.neighbors = neighbors;
    }

    public double getReliability() {
        return reliability;
    }

    public void setReliability(double reliability) {
        this.reliability = reliability;
    }

    public SynthState getSynthState() {
        return synthState;
    }

    public void setSynthState(SynthState synthState) {
        this.synthState = synthState;
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
        Collection<Statement> result = new HashSet<>();
        result.add(new StatementImpl(uri, Namespace.hasReliabilityFactor,
                Namespace.literal(reliability, XMLSchema.FLOAT)));
        result.add(new StatementImpl(uri, Namespace.hasSynthState,
                synthState.getUri()));
        for (NodeId remoteNode : neighbors) {
            result.add(new StatementImpl(uri, Namespace.hasNeighbor,
                    remoteNode.getUri()));
        }
        return result;
    }
*/
}
