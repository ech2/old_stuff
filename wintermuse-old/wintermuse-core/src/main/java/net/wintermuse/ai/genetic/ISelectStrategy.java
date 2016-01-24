package net.wintermuse.ai.genetic;

/**
 * Created by oscii on 20/04/14.
 */
public interface ISelectStrategy<T> {
    public abstract Population<T> select(Population<T> population);
}
