package net.wintermuse.ai.genetic;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by oscii on 20/04/14.
 */
public class Population<T> {
    private Set<T> individuals;

    public Population() {
        individuals = new HashSet<T>();
    }

    public Population(Collection<T> initIndividuals) {
        individuals = new HashSet<T>(initIndividuals);
    }

    public void add(T individual) {
        if (individual == null) {
            throw new NullPointerException("You can't add null to Population");
        }
        individuals.add(individual);
    }

    public void addAll(Collection<T> newIndividuals) {
        individuals.addAll(newIndividuals);
    }

    public void replaceAll(Collection<T> newIndividuals) {
        individuals.clear();
        addAll(newIndividuals);
    }

    public Set<T> getIndividuals() {
        return individuals;
    }

    public int size() {
        return individuals.size();
    }
}
