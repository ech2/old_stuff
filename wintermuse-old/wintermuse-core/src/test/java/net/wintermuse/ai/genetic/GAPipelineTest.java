package net.wintermuse.ai.genetic;

import net.wintermuse.ai.genetic.*;
import net.wintermuse.util.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by oscii on 22/04/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class GAPipelineTest {
    @Mock
    private ICrossoverStrategy crossoverStrategy;
    @Mock
    private IMutateStrategy mutationStrategy;
    @Mock
    private ISelectStrategy selectionStrategy;
    @Mock
    private IChoosePairsStrategy choosePairsStrategy;
    @Mock
    private Population population;

    @Spy
    private GAPipeline gaPipeline = new GAPipeline();

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        initMocks(this);

        //injecting Population mock
        Field inj = GAPipeline.class.getDeclaredField("population");
        inj.setAccessible(true);
        inj.set(gaPipeline, population);
        inj.setAccessible(false);

        gaPipeline.setChoosePairsStrategy(choosePairsStrategy);
        gaPipeline.setCrossoverStrategy(crossoverStrategy);
        gaPipeline.setMutationStrategy(mutationStrategy);
        gaPipeline.setSelectionStrategy(selectionStrategy);

        // We add 2 elements to makePairs() return,
        // so crossoverStrategy.crossover() should be invoked 2 times
        Set<Couple> crossoverStub = new HashSet<Couple>();
        crossoverStub.add(Couple.ofCouple(1,2));
        crossoverStub.add(Couple.ofCouple(1, 3));

        // Init strategies
        when(choosePairsStrategy.choosePairs(Matchers.<Population>any()))
                .thenReturn(crossoverStub);
        when(crossoverStrategy.crossover(Matchers.<net.wintermuse.util.Pair>any()))
                .thenReturn(mock(Pair.class));
        when(mutationStrategy.mutate(any())).thenReturn(mock(Object.class));
        when(selectionStrategy.select(Matchers.<Population>any()))
                .thenReturn(mock(Population.class));
    }

    @Test
    public void process_AllStrategiesFired() {
        gaPipeline.process();
        verify(choosePairsStrategy, times(1)).choosePairs(Matchers.<Population>any());
        verify(crossoverStrategy, times(2)).crossover(Matchers.<net.wintermuse.util.Pair>any());
        verify(selectionStrategy, times(1)).select(Matchers.<Population>any());
    }

    @Test(expected = NullPointerException.class)
    public void setCrossoverStrategy_Null_ThrowNPE() {
        gaPipeline.setCrossoverStrategy(null);
    }
    @Test(expected = NullPointerException.class)
    public void setMutationStrategy_Null_ThrowNPE() {
        gaPipeline.setMutationStrategy(null);
    }
    @Test(expected = NullPointerException.class)
    public void setSelectionStrategy_Null_ThrowNPE() {
        gaPipeline.setSelectionStrategy(null);
    }
    @Test(expected = NullPointerException.class)
    public void setChoosePairsStrategy_Null_ThrowNPE() {
        gaPipeline.setChoosePairsStrategy(null);
    }

    @Test(expected = NullPointerException.class)
    public void setPopulation_PopulationNull_ThrowNPE() {
        Collection testCollection = null;
        gaPipeline.setPopulation(testCollection);
    }
    @Test(expected = NullPointerException.class)
    public void setPopulation_CollectionNull_ThrowNPE() {
        Population testPopulation = null;
        gaPipeline.setPopulation(testPopulation);
    }



}
