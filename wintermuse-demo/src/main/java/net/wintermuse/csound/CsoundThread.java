package net.wintermuse.csound;

import csnd6.Csound;

/**
 * Created by oscii on 31/07/14.
 */
public class CsoundThread implements Runnable {
    private Csound cs;

    public CsoundThread(Csound cs) {
        this.cs = cs;
    }

    @Override
    public void run() {
        while (cs.PerformKsmps() == 0 && !Thread.currentThread().isInterrupted()) {

        }
    }
}
