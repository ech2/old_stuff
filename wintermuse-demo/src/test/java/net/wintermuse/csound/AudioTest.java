package net.wintermuse.csound;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AudioTest {
    @Test
    public void ShouldHeadTheSound() throws Exception {
        CsoundSession cs = new CsoundSession("/csound.test.synth.csd");
        cs.init();
        ScoreEvent event = new ScoreEventString("i 1 0 1 440");
        cs.sendScoreEvent(event);
        Thread.sleep(1500);
    }
}