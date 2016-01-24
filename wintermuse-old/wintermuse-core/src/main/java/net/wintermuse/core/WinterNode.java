package net.wintermuse.core;

import net.wintermuse.ai.genetic.Population;
import net.wintermuse.ai.genetic.impl.MusicParamsEvolution;

import java.util.*;

/**
 * Created by oscii on 19/04/14.
 */
public class WinterNode {
    private CurrentNode currentNode = new CurrentNode();
    private Map<String, RemoteNode> remoteNodes = new HashMap<String, RemoteNode>();
    private MusicParamsEvolution evolution = new MusicParamsEvolution();

    /* --- */

    public MusicParams getUserSettings() {
        return currentNode.getUserSettings();
    }

    public void setUserSettings(MusicParams musicParams) {
        if (musicParams != null) {
            currentNode.setUserSettings(musicParams);
        }
    }

    public void setUserSettings(List<Integer> inputList) {
        if (inputList != null) {
            currentNode.setUserSettings(inputList);
        }
    }

    /* --- */

    public MusicParams getCurrentSettings() {
        return currentNode.getCurrentSettings();
    }

    public void setCurrentSettings(MusicParams musicParams) {
        if (musicParams != null) {
            currentNode.setCurrentSettings(musicParams);
        }
    }

    public void setCurrentSettings(List<Integer> inputList) {
        if (inputList != null) {
            currentNode.setCurrentSettings(inputList);
        }
    }

    public MusicParamsEvolution getEvolution() {
        return evolution;
    }

    public boolean evolve() {
        if (remoteNodes.size() > 0) {
            Population<MusicParams> newPopulation = new Population<MusicParams>();
            newPopulation.add(currentNode.getCurrentSettings());
            for (RemoteNode node : remoteNodes.values()) {
                newPopulation.add(node.getCurrentSettings());
            }
            evolution.updatePopulation(newPopulation);
            try {
                setCurrentSettings(evolution.processPopulation().
                        getIndividuals().iterator().next());
                return true;
            } catch (IllegalStateException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /* --- */

    public String getUidString() {
        return currentNode.getUid().toString();
    }


    public void remoteMessage(String remoteUid, MusicParams params) {
        if (remoteUid.equals(getUidString())) {
            return;
        }
        if (remoteNodes.containsKey(remoteUid)) {
            RemoteNode node = remoteNodes.get(remoteUid);
            node.setCurrentSettings(params);
        } else {
            RemoteNode node = new RemoteNode(remoteUid);
            node.setCurrentSettings(params);
            remoteNodes.put(remoteUid, node);
        }
    }


    public Set<RemoteNode> getRemoteNodes() {
        return new HashSet<RemoteNode>(remoteNodes.values());
    }

}
