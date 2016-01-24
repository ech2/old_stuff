package net.wintermuse.ai.genetic;

import java.util.Set;

/**
 * Created by oscii on 22/04/14.
 */
public interface IChoosePairsStrategy<T> {
    public Set<Couple<T>> choosePairs(Population<T> population);
}
