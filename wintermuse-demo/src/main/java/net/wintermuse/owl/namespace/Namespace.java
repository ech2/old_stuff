package net.wintermuse.owl.namespace;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.impl.ValueFactoryImpl;

/**
 * Created by oscii on 05/08/14.
 */
public abstract class Namespace {
    public static URI uri(String base, String name) {
        return ValueFactoryImpl.getInstance().createURI(base + name);
    }
    public static Literal literal(Object value, URI dataType) {
        return ValueFactoryImpl.getInstance().createLiteral(value.toString(), dataType);
    }

    public static final String BASE = "http://wintermuse.net/ontology/demo/0.1#";
    public static final String BASE_NODE = "http://wintermuse.net/ontology/demo/0.1/node#";

    // Classes
    public static final URI NodeState = uri(BASE, "NodeState");
    public static final URI NodeId = uri(BASE, "NodeID");
    public static final URI SynthState = uri(BASE, "SynthState");

    // Object properties
    public static final URI hasNeighbor = uri(BASE, "hasNeighbor");
    public static final URI hasNodeState = uri(BASE, "hasNodeState");
    public static final URI hasSynthState = uri(BASE, "hasSynthState");

    // Data properties
    public static final URI hasBasePitch = uri(BASE, "hasBasePitch");
    public static final URI hasDensity = uri(BASE, "hasDensity");
    public static final URI hasFreqModCoef = uri(BASE, "hasFreqModCoef");
    public static final URI hasIpAddress = uri(BASE, "hasIPAddress");
    public static final URI hasReliabilityFactor = uri(BASE, "hasReliabilityFactor");

}
