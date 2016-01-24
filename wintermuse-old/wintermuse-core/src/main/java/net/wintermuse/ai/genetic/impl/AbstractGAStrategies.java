package net.wintermuse.ai.genetic.impl;

import net.wintermuse.ai.genetic.*;
import net.wintermuse.util.Pair;

import java.util.Set;

/**
 * Created by oscii on 21/04/14.
 */
public abstract class AbstractGAStrategies<T> implements ISelectStrategy<T>,
        ICrossoverStrategy<T>, IMutateStrategy<T>, IChoosePairsStrategy<T> {


    @Override
    public abstract T crossover(Pair<T, T> pair);

    @Override
    public abstract T mutate(T individual);

    @Override
    public abstract Population<T> select(Population<T> population);

    @Override
    public abstract Set<Couple<T>> choosePairs(Population<T> population);
}
