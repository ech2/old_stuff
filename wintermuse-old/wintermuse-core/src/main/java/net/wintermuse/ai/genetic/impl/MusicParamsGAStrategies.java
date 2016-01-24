package net.wintermuse.ai.genetic.impl;

import net.wintermuse.ai.genetic.IFitnessFunction;
import net.wintermuse.ai.genetic.Couple;
import net.wintermuse.ai.genetic.IChoosePairsStrategy;
import net.wintermuse.ai.genetic.Population;
import net.wintermuse.core.MusicParams;
import net.wintermuse.util.Pair;

import java.util.*;

/**
 * Created by oscii on 21/04/14.
 */
public class MusicParamsGAStrategies extends AbstractGAStrategies<MusicParams> {
    private Random random = new Random();
    private MusicParams userSettings;
    private IChoosePairsStrategy<MusicParams> choosePairsStrategy =
            new EachWithEachStrategy<MusicParams>();

    private Comparator<MusicParams> musicParamsComparator = new Comparator<MusicParams>() {
        @Override
        public int compare(MusicParams p1, MusicParams p2) {
            double p1Fitness = fitnessFunction.calculate(p1);
            double p2Fitness = fitnessFunction.calculate(p2);

            if (p1Fitness == p2Fitness) {
                return 0;
            } else if (p1Fitness > p2Fitness) {
                return 1;
            } else {
                return -1;
            }

        }
    };

    private IFitnessFunction<MusicParams> fitnessFunction = new IFitnessFunction<MusicParams>() {
        @Override
        public double calculate(MusicParams individual) {
            // Euclidean distance between two MusicParams
            return Math.sqrt(
                    Math.pow(userSettings.getBass() - individual.getBass(), 2) +
                            Math.pow(userSettings.getDrums() - individual.getDrums(), 2) +
                            Math.pow(userSettings.getSfx() - individual.getSfx(), 2) +
                            Math.pow(userSettings.getSweepTrig() - individual.getSweepTrig(), 2) +
                            Math.pow(userSettings.getSweepType() - individual.getSweepType(), 2)
            );
        }
    };

    /* --- */

    public MusicParamsGAStrategies() {
        userSettings = new MusicParams();
    }

    public MusicParamsGAStrategies(MusicParams userSettings) {
        this.userSettings = userSettings;
    }

    public void setUserSettings(MusicParams userSettings) {
        this.userSettings = new MusicParams(userSettings);
    }

    @Override
    public MusicParams crossover(Pair<MusicParams, MusicParams> pair) {
        List<Integer> i1 = pair.first.asList();
        List<Integer> i2 = pair.second.asList();
        List<Integer> newParams = new ArrayList<Integer>(i1.size());

        for (int i = 0; i < i1.size(); i++) {
            newParams.add((int) Math.round(
                    interp(i1.get(i), i2.get(i), random.nextFloat())
            ));
        }
        return new MusicParams(newParams);
    }

    @Override
    public MusicParams mutate(MusicParams individual) {
        List<Integer> params = individual.asList();

        for (ListIterator<Integer> it = params.listIterator(); it.hasNext(); ) {
            Integer p = it.next();
            it.set(clip(p + decide()));
        }
        return new MusicParams(params);
    }

    @Override
    public Population<MusicParams> select(Population<MusicParams> population) {
        SortedSet<MusicParams> s = new TreeSet<MusicParams>(musicParamsComparator);
        s.addAll(population.getIndividuals());

        Population<MusicParams> resultPopulation = new Population<MusicParams>();
        resultPopulation.add(s.first());
        return resultPopulation;
    }

    @Override
    public Set<Couple<MusicParams>> choosePairs(Population<MusicParams> population) {
        return choosePairsStrategy.choosePairs(population);
    }

    private int clip(int input) {
        return Math.max(0, Math.min(10, input));
    }

    private int decide() {
        if (random.nextFloat() > 0.5) {
            return 1;
        } else {
            return -1;
        }
    }

    private double interp(double a, double b, double f) {
        return a + f * (b - a);
    }
}
