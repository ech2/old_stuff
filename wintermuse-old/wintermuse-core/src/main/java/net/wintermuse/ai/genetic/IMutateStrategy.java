package net.wintermuse.ai.genetic;

/**
 * Created by oscii on 20/04/14.
 */
public interface IMutateStrategy<T> {
    public T mutate(T individual);
}
