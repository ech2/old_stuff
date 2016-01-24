package net.wintermuse.owl;

/**
 * User: ech
 * Date: 8/4/14
 * Time: 14:48
 */
public class SynthState {
//    private URI uri;
    private double density;
    private double freqMod;
    private double basePitch;

    public SynthState(double density, double freqMod, double basePitch) {
        this.density = density;
        this.freqMod = freqMod;
        this.basePitch = basePitch;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getFreqMod() {
        return freqMod;
    }

    public void setFreqMod(double freqMod) {
        this.freqMod = freqMod;
    }

    public double getBasePitch() {
        return basePitch;
    }

    public void setBasePitch(double basePitch) {
        this.basePitch = basePitch;
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
        result.add(new StatementImpl(uri, Namespace.hasDensity,
                Namespace.literal(density, XMLSchema.FLOAT)));
        result.add(new StatementImpl(uri, Namespace.hasFreqModCoef,
                Namespace.literal(freqMod, XMLSchema.FLOAT)));
        result.add(new StatementImpl(uri, Namespace.hasBasePitch,
                Namespace.literal(basePitch, XMLSchema.FLOAT)));
        return result;
    }
*/
}
