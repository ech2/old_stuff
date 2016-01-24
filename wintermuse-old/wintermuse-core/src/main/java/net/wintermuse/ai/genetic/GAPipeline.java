package net.wintermuse.ai.genetic;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by oscii on 21/04/14.
 */
public class GAPipeline<T> {
    private ICrossoverStrategy<T> crossoverStrategy;
    private IMutateStrategy<T> mutationStrategy;
    private ISelectStrategy<T> selectionStrategy;
    private IChoosePairsStrategy<T> choosePairsStrategy;

    private Population<T> population;

    public GAPipeline() {
        population = new Population<T>();
    }

    public GAPipeline(Collection<T> initPopulation) {
        population.addAll(initPopulation);
    }


    /* Get/set population */

    public Population<T> getPopulation() {
        return population;
    }

    public void setPopulation(Population<T> population) {
        if (population == null) {
            throw new NullPointerException("Setting null population");
        }
        this.population = population;
    }

    public void setPopulation(Collection<T> population) {
        if (population == null) {
            throw new NullPointerException("Setting null population");
        }
        this.population = new Population<T>(population);
    }


    /* Private methods wit GA steps */

    private Collection<Couple<T>> makePairs() throws IllegalStateException {
        if (choosePairsStrategy == null) {
            throw new IllegalStateException("ChoosePairStrategy has not been initialized");
        }
        return choosePairsStrategy.choosePairs(population);
    }

    private Population<T> crossover(Collection<Couple<T>> pairs) throws IllegalStateException {
        if (crossoverStrategy == null) {
            throw new IllegalStateException("CrossoverStrategy has not been initialized");
        }
        Population<T> children = new Population<T>();
        for (Couple<T> pair : pairs) {
            children.add(crossoverStrategy.crossover(pair));
        }
        return children;
    }

    private Population<T> mutate(Population<T> population) throws IllegalStateException {
        if (mutationStrategy == null) {
            throw new IllegalStateException("MutationStrategy has not been initialized");
        }
        Population<T> result = new Population<T>();
        for (T element : population.getIndividuals()) {
            result.add(mutationStrategy.mutate(element));
        }
        return result;
    }

    private Population<T> select(Population<T> population) {
        if (selectionStrategy == null) {
            throw new IllegalStateException("SelectionStrategy has not been initialized");
        }
        return selectionStrategy.select(population);
    }


    /* Pipeline process */

    public Population<T> process() throws IllegalStateException {
        return select(mutate(crossover(makePairs())));
    }


    /* Strategy setters */

    public void setCrossoverStrategy(ICrossoverStrategy<T> crossoverStrategy) {
        if (crossoverStrategy == null) {
            throw new NullPointerException("Setting null strategy");
        }
        this.crossoverStrategy = crossoverStrategy;
    }

    public void setMutationStrategy(IMutateStrategy<T> mutationStrategy) {
        if (mutationStrategy== null) {
            throw new NullPointerException("Setting null strategy");
        }
        this.mutationStrategy = mutationStrategy;
    }

    public void setSelectionStrategy(ISelectStrategy<T> selectionStrategy) {
        if (selectionStrategy == null) {
            throw new NullPointerException("Setting null strategy");
        }
        this.selectionStrategy = selectionStrategy;
    }

    public void setChoosePairsStrategy(IChoosePairsStrategy<T> choosePairsStrategy) {
        if (choosePairsStrategy== null) {
            throw new NullPointerException("Setting null strategy");
        }
        this.choosePairsStrategy = choosePairsStrategy;
    }


}
