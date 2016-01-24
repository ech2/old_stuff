package net.wintermuse.core;

import java.util.List;
import java.util.UUID;

/**
 * Created by oscii on 12/04/14.
 */
public abstract class AbstractNode {
    protected MusicParams currentSettings = new MusicParams();
    private UUID uid;

    public AbstractNode() {
        uid = UUID.randomUUID();
    }

    public AbstractNode(String uidString) {
        uid = UUID.fromString(uidString);
    }

    public MusicParams getCurrentSettings() {
        return currentSettings;
    }

    public void setCurrentSettings(MusicParams params) {
        currentSettings = new MusicParams(params);
    }

    public boolean setCurrentSettings(List<Integer> params) {
        try {
            currentSettings = new MusicParams(params);
            return true;
        } catch (IllegalArgumentException e) {
//            System.err.println(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public UUID getUid() {
        return uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractNode that = (AbstractNode) o;

        if (!currentSettings.equals(that.currentSettings)) return false;
        if (!uid.equals(that.uid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = currentSettings.hashCode();
        result = 31 * result + uid.hashCode();
        return result;
    }
}
