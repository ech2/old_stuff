package net.wintermuse.ai.genetic.impl;

import net.wintermuse.ai.genetic.Couple;
import net.wintermuse.ai.genetic.Population;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by oscii on 22/04/14.
 */
public class EachWithEachStrategyTest {
    EachWithEachStrategy<Integer> s = new EachWithEachStrategy<Integer>();
    @Test
    public void choosePairs_Population_AllCombinationsOfIndividualAsPairs() {
        Population<Integer> p = new Population<Integer>(Arrays.asList(1,2,3,4,5));

        Set<Couple<Integer>> result = s.choosePairs(p);

        Set<Couple<Integer>> expected = new HashSet<Couple<Integer>>();
        expected.add(new Couple<Integer>(1,2));
        expected.add(new Couple<Integer>(1,3));
        expected.add(new Couple<Integer>(1,4));
        expected.add(new Couple<Integer>(1,5));
        expected.add(new Couple<Integer>(2,3));
        expected.add(new Couple<Integer>(2,4));
        expected.add(new Couple<Integer>(2,5));
        expected.add(new Couple<Integer>(3,4));
        expected.add(new Couple<Integer>(3,5));
        expected.add(new Couple<Integer>(4,5));

        assertEquals(10, result.size());
        assertTrue(result.containsAll(expected));
    }

    @Test
    public void choosePairs_SingleIndividualInPopulation_SetWithOneCoupleContainsTwoEqualIndividuals() {
        Population<Integer> p = new Population<Integer>(Arrays.asList(1));
        Set<Couple<Integer>> result = s.choosePairs(p);

        assertEquals(1, result.size());

        Couple<Integer> c = result.iterator().next();
        assertEquals(c.first, c.second);
    }

    @Test
    public void choosePairs_Null_ThrowNPE() {
        Population<Integer> p = new Population<Integer>(Arrays.asList(1,2,3,4,5));
        s.choosePairs(p);
    }
}
