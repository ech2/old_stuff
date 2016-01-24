package net.wintermuse.ai.genetic.impl;

import net.wintermuse.ai.genetic.Couple;
import net.wintermuse.ai.genetic.IChoosePairsStrategy;
import net.wintermuse.ai.genetic.Population;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by oscii on 22/04/14.
 */
public class EachWithEachStrategy<T> implements IChoosePairsStrategy<T> {
    @Override
    public Set<Couple<T>> choosePairs(Population<T> population) {
        // TODO calculate initial capacity
        Set<Couple<T>> result = new HashSet<Couple<T>>();

        if (population.size() == 1) {
            T singleIndividual = population.getIndividuals().iterator().next();
            result.add(Couple.ofCouple(singleIndividual, singleIndividual));
        }

        Iterator<T> it = population.getIndividuals().iterator();
        if (it.hasNext()) {
            int i = 0;
            while (it.hasNext()) {
                T element1 = it.next();
                int j = 0;
                Iterator<T> it2 = population.getIndividuals().iterator();
                while (it2.hasNext()) {
                    if (j > i) {
                        T element2 = it2.next();
                        if (!element1.equals(element2)) {
                            result.add(Couple.ofCouple(element1, element2));
                        }
                    } else {
                        it2.next();
                    }
                    j++;
                }

                i++;
            }
        }

        return result;
    }
}
