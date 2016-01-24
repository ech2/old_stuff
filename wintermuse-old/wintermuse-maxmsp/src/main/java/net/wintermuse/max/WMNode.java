package net.wintermuse.max;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;
import net.wintermuse.core.MusicParams;
import net.wintermuse.core.RemoteNode;
import net.wintermuse.core.WinterNode;
import net.wintermuse.helpers.AtomHelper;

import java.util.Arrays;

/**
 * Created by oscii on 12/04/14.
 */
public class WMNode extends MaxObject {
    private WinterNode node = new WinterNode();

    public WMNode() {
        get_uid();
        get_user_settings();
        get_current_settings();
    }

    public void get_user_settings() {
        outlet(0, "user_settings",
                AtomHelper.musicParamsToAtomArray(node.getUserSettings()));
    }

    public void set_user_settings(Atom[] params) {
        MusicParams musicParams = AtomHelper.atomArrayToMusicParams(params);

        if (musicParams != null) {
            node.setUserSettings(musicParams);
            node.getEvolution().updateUserSettings(musicParams);
            get_user_settings();
        }

    }

    public void get_current_settings() {
        outlet(0, "current_settings",
                AtomHelper.musicParamsToAtomArray(node.getCurrentSettings()));
    }

    public void set_current_settings(Atom[] params) {
        MusicParams musicParams = AtomHelper.atomArrayToMusicParams(params);

        if (musicParams != null) {
            node.setCurrentSettings(musicParams);
        }

        get_current_settings();
    }

    public void get_uid() {
        outlet(0, "uid", Atom.newAtom(node.getUidString()));
    }


    public void evolve() {
        if (node.evolve()) {
            get_current_settings();
        }
    }


    /**
     * @param args String uid, bass, drum, sfx, sweepType, sweetTrig
     */
    public void remote_message(Atom[] args) {
        if (args.length == 6 && args[0].isString()) {
            String remoteUid = args[0].getString();
            MusicParams inputParams =
                    AtomHelper.atomArrayToMusicParams(Arrays.copyOfRange(args, 1, args.length));

            node.remoteMessage(remoteUid, inputParams);
        }
    }

    public void print_remote_nodes() {
        post("Node: " + node.getUidString());
        if (node.getRemoteNodes().size() == 0) {
            post("  no remote nodes");
        } else {
            for (RemoteNode n : node.getRemoteNodes()) {
                post("  remote: " + n.getUid().toString());
            }
        }
    }
}
