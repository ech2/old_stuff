package net.wintermuse.core;

import java.util.Arrays;
import java.util.List;

/**
 * Created by oscii on 12/04/14.
 */
public class MusicParams {
    private int drums;
    private int bass;
    private int sfx;
    private int sweepType;
    private int sweepTrig;

    public MusicParams() {
    }

    public MusicParams(int drums, int bass, int sfx, int sweepType, int sweepTrig) {
        this.drums = drums;
        this.bass = bass;
        this.sfx = sfx;
        this.sweepType = sweepType;
        this.sweepTrig = sweepTrig;
    }

    public MusicParams(MusicParams params) {
        drums = params.getDrums();
        bass = params.getBass();
        sfx = params.getSfx();
        sweepType = params.getSweepType();
        sweepTrig = params.getSweepTrig();
    }

    public MusicParams(List<Integer> intList) throws IllegalArgumentException {
        if (intList.size() != 5) {
            throw new IllegalArgumentException("Length of the list for initialization of MusicParams class should be 5");
        }

        drums = intList.get(0);
        bass = intList.get(1);
        sfx = intList.get(2);
        sweepType = intList.get(3);
        sweepTrig = intList.get(4);
    }

    public int getDrums() {
        return drums;
    }

    public int getBass() {
        return bass;
    }

    public int getSfx() {
        return sfx;
    }

    public int getSweepType() {
        return sweepType;
    }

    public int getSweepTrig() {
        return sweepTrig;
    }

    public List<Integer> asList() {
        return Arrays.asList(drums, bass, sfx, sweepType, sweepTrig);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicParams that = (MusicParams) o;

        if (bass != that.bass) return false;
        if (drums != that.drums) return false;
        if (sfx != that.sfx) return false;
        if (sweepTrig != that.sweepTrig) return false;
        if (sweepType != that.sweepType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = drums;
        result = 31 * result + bass;
        result = 31 * result + sfx;
        result = 31 * result + sweepType;
        result = 31 * result + sweepTrig;
        return result;
    }

    @Override
    public String toString() {
        return "MusicParams{" +
                "drums=" + drums +
                ", bass=" + bass +
                ", sfx=" + sfx +
                ", sweepType=" + sweepType +
                ", sweepTrig=" + sweepTrig +
                '}';
    }
}
