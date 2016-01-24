package net.wintermuse.core;

import java.util.Arrays;
import java.util.List;

/**
 * Created by oscii on 12/04/14.
 */
public class CurrentNode extends AbstractNode {
    private MusicParams userSettings = new MusicParams();

    public CurrentNode() {
        super();
    }

    public MusicParams getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(MusicParams params) {
        userSettings = new MusicParams(params);
    }

    public boolean setUserSettings(List<Integer> params) {
        try {
            userSettings = new MusicParams(params);
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CurrentNode that = (CurrentNode) o;

        if (!userSettings.equals(that.userSettings)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + userSettings.hashCode();
        return result;
    }
}
