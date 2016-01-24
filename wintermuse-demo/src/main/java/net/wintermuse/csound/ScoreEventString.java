package net.wintermuse.csound;

/**
 * Created by oscii on 31/07/14.
 */
public class ScoreEventString implements ScoreEvent {
    private String event;

    public ScoreEventString(String event) {
        this.event = event;
    }

    @Override
    public String getMessage() {
        return event;
    }
}
