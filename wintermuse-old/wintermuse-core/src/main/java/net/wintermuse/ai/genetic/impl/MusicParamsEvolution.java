package net.wintermuse.ai.genetic.impl;

import net.wintermuse.ai.genetic.GAPipeline;
import net.wintermuse.ai.genetic.Population;
import net.wintermuse.core.MusicParams;

import java.util.Collection;

/**
 * Created by oscii on 22/04/14.
 */
public class MusicParamsEvolution {
    private GAPipeline<MusicParams> gaPipeline;
    private MusicParamsGAStrategies gaStrategies = new MusicParamsGAStrategies();

    public MusicParamsEvolution() {
        gaPipeline = new GAPipeline<MusicParams>();
        initPipelineStrategies();
    }

    public MusicParamsEvolution(Collection<MusicParams> initPopulation) {
        gaPipeline = new GAPipeline<MusicParams>(initPopulation);
        initPipelineStrategies();
    }


    public Population<MusicParams> processPopulation() {
        return gaPipeline.process();
    }


    public void updatePopulation(Collection<MusicParams> newPopulation) {
        gaPipeline.setPopulation(newPopulation);
    }

    public void updatePopulation(Population<MusicParams> newPopulation) {
        gaPipeline.setPopulation(newPopulation);
    }

    public void updateUserSettings(MusicParams userSettings) {
        gaStrategies.setUserSettings(userSettings);
    }

    private void initPipelineStrategies() {
        gaPipeline.setSelectionStrategy(gaStrategies);
        gaPipeline.setCrossoverStrategy(gaStrategies);
        gaPipeline.setMutationStrategy(gaStrategies);
        gaPipeline.setChoosePairsStrategy(gaStrategies);
    }
}
