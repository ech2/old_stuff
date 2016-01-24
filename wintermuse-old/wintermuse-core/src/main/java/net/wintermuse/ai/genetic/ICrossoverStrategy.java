package net.wintermuse.ai.genetic;

import net.wintermuse.util.Pair;

/**
 * Created by oscii on 20/04/14.
 */
public interface ICrossoverStrategy<T> {
    public T crossover(Pair<T, T> pair);
}
