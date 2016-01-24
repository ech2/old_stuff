package net.wintermuse.ai.genetic;

import net.wintermuse.ai.genetic.Population;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
/**
 * Created by oscii on 22/04/14.
 */
public class PopulationTest {

    /* Constructors */

    @Test
    public void Constructor_Empty_ZeroSizeCollectionInside() {
        Population<Integer> population = new Population<Integer>();
        assertEquals(0, population.getIndividuals().size());
    }
    @Test
    public void Constructor_Collection_EqualCollection() {
        Set<Integer> constructorData = new TreeSet<Integer>();
        constructorData.add(1);
        constructorData.add(2);
        constructorData.add(3);

        Population<Integer> population = new Population<Integer>(constructorData);
        // Sorting collection
        Set<Integer> populationData = new TreeSet<Integer>(population.getIndividuals());

        assertEquals(constructorData, populationData);
    }
    @Test
    public void Constructor_Collection_NotEqualCollection() {
        Set<Integer> constructorData = new TreeSet<Integer>();
        constructorData.add(1);
        constructorData.add(2);
        constructorData.add(3);

        Population<Integer> population = new Population<Integer>(constructorData);
        // Add more data after population initialization
        constructorData.add(4);
        // Sorting collection
        Set<Integer> populationData = new TreeSet<Integer>(population.getIndividuals());

        assertNotEquals(constructorData, populationData);
    }
    @Test
    public void Constructor_CollectionWithDuplicates_NoDuplicatesInPopulation() {
        List<Integer> dataWithDuplicates = Arrays.asList(1,2,3,4,4);
        Population<Integer> population = new Population<Integer>(dataWithDuplicates);

        Collection<Integer> populationData = population.getIndividuals();

        assertNotEquals(dataWithDuplicates.size(), populationData.size());
        assertEquals(4, populationData.size());
        assertTrue(populationData.containsAll(dataWithDuplicates));
    }
    @Test(expected = NullPointerException.class)
    public void Constructor_Null_ThrowNPE() {
        Population<Integer> population = new Population<Integer>(null);
    }


    /* add() cases */

    @Test(expected = NullPointerException.class)
    public void add_Null_ThrowNPE() {
        Population<Integer> population = new Population<Integer>();
        population.add(null);
    }
    @Test
    public void add_Element_ContainsAddedElement() {
        Population<Integer> population = new Population<Integer>();
        population.add(100);
        assertTrue(population.getIndividuals().contains(100));
    }
    @Test
    public void add_TwoEqualElements_NoDuplicatesInPopulation() {
        Population<Integer> population = new Population<Integer>();
        population.add(100);
        population.add(101);
        population.add(102);
        population.add(102);

        Collection<Integer> populationData = population.getIndividuals();

        assertEquals(3, populationData.size());
        assertTrue(populationData.containsAll(Arrays.asList(100,101,102)));
    }


    /* addAll() cases */

    @Test(expected = NullPointerException.class)
    public void addAll_Null_ThrowNPE() {
        Population<Integer> population = new Population<Integer>();
        population.addAll(null);
    }
    @Test
    public void addAll_Collection_AllElementsFromCollection() {
        Set<Integer> inputData = new TreeSet<Integer>();
        inputData.add(1);
        inputData.add(2);
        inputData.add(3);

        Population<Integer> population = new Population<Integer>();
        population.addAll(inputData);

        Set<Integer> populationData = new TreeSet<Integer>(population.getIndividuals());
        assertEquals(inputData, populationData);
    }
    @Test
    public void addAll_CollectionWithDuplicates_NoDuplicatesInPopulation() {
        List<Integer> dataWithDuplicates = Arrays.asList(1,2,3,4,4);
        Population<Integer> population = new Population<Integer>();

        population.addAll(dataWithDuplicates);

        Collection<Integer> populationData = population.getIndividuals();

        assertNotEquals(dataWithDuplicates.size(), populationData.size());
        assertEquals(4, populationData.size());
        assertTrue(populationData.containsAll(dataWithDuplicates));

    }


    /* replaceAll() cases */

    @Test(expected = NullPointerException.class)
    public void replaceAll_Null_ThrowNPE() {
        Population<Integer> population = new Population<Integer>();
        population.add(1);
        population.add(2);

        population.replaceAll(null);
    }
    @Test
    public void replaceAll_CollectionWithDuplicates_NewPopulationWithNoDuplicates() {
        Population<Integer> population = new Population<Integer>();
        population.add(1);
        population.add(2);

        List<Integer> newData = Arrays.asList(3, 4, 5, 6, 6);

        population.replaceAll(newData);

        Collection<Integer> newPopulation = population.getIndividuals();

        assertEquals(4, newPopulation.size());
        assertTrue(newPopulation.containsAll(newData));
    }
}
