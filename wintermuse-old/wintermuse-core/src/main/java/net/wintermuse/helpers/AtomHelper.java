package net.wintermuse.helpers;

import com.cycling74.max.Atom;
import net.wintermuse.core.MusicParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oscii on 12/04/14.
 */
public class AtomHelper {
    public static Atom[] musicParamsToAtomArray(MusicParams params) {
        return new Atom[]{
                Atom.newAtom(params.getDrums()),
                Atom.newAtom(params.getBass()),
                Atom.newAtom(params.getSfx()),
                Atom.newAtom(params.getSweepType()),
                Atom.newAtom(params.getSweepTrig())
        };
    }


    public static List<Integer> atomArrayToIntegerList(Atom[] input) {
        List<Integer> intList = new ArrayList<Integer>();
        for (Atom e : input) {
            if (e.isInt() || e.isFloat()) {
                intList.add(e.getInt());
            } else {
                return null;
            }
        }

        return intList;
    }


    public static MusicParams atomArrayToMusicParams(Atom[] input) {
        if (input.length != 5) {
            return null;
        }

        for (Atom atom : input) {
            if (!atom.isInt() && !atom.isFloat()) {
                System.out.println("Yo: " + atom.toString());
                return null;
            }
        }

        return new MusicParams(AtomHelper.atomArrayToIntegerList(input));
    }
}
