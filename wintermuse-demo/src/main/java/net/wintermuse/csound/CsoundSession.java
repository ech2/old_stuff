package net.wintermuse.csound;

import csnd6.Csound;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by oscii on 31/07/14.
 */
public class CsoundSession {
    private Csound cs = new Csound();
    private String csdPath;

    private Thread csoundThread;

    public CsoundSession(String csdName) {
        try {
            File csd = new File(CsoundSession.class.getResource(csdName).toURI());
            if (csd.exists()) {
                csdPath = csd.getAbsolutePath();
                csoundThread = new Thread(new CsoundThread(cs));
            } else {
                throw new IllegalArgumentException("Can't find a .csd file: " + csdName);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    synchronized public void init() {
        if (csoundThread.isAlive()) {
            return;
        }
        cs.Compile(csdPath);
        csoundThread.start();
    }

    synchronized public void reset() {
        if (!csoundThread.isAlive()) {
            return;
        }
        csoundThread.interrupt();
        try {
            csoundThread.join();
            cs.Reset();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendScoreEvent(ScoreEvent event) {
        if (!csoundThread.isAlive()) {
            return;
        }
        cs.InputMessage(event.getMessage());
    }
}
