package net.wintermuse.ai.genetic;

/**
 * Created by oscii on 20/04/14.
 */
public interface IFitnessFunction<T> {
    public double calculate(T individual);
}
