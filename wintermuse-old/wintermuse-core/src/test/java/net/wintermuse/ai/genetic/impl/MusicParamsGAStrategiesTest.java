package net.wintermuse.ai.genetic.impl;

import net.wintermuse.ai.genetic.Couple;
import net.wintermuse.ai.genetic.Population;
import net.wintermuse.ai.genetic.impl.MusicParamsGAStrategies;
import net.wintermuse.core.MusicParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by oscii on 22/04/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class MusicParamsGAStrategiesTest {
    @Mock
    Random mockedRandom;

    @InjectMocks
    MusicParamsGAStrategies s = new MusicParamsGAStrategies();

    @Test
    public void mutateStrategy_Correctness_Pass()
            throws NoSuchFieldException, IllegalAccessException {
        MusicParams beforeMutation = new MusicParams(1,1,1,1,1);

        when(mockedRandom.nextFloat())
                .thenReturn(0f)
                .thenReturn(1f)
                .thenReturn(0f)
                .thenReturn(1f)
                .thenReturn(0f);

        MusicParams afterMutation = s.mutate(beforeMutation);
        assertEquals(new MusicParams(0,2,0,2,0), afterMutation);
    }
    @Test(expected = NullPointerException.class)
    public void mutateStrategy_Null_ThrowNPE() {
        MusicParams beforeMutation = new MusicParams(1,1,1,1,1);
        MusicParamsGAStrategies s = new MusicParamsGAStrategies();

        MusicParams afterMutation = s.mutate(null);
    }

    @Test
    public void crossoverStrategy_Correctness_Pass() {
        MusicParams p1 = new MusicParams(2,2,2,2,2);
        MusicParams p2 = new MusicParams(4,4,4,4,4);
        Couple<MusicParams> pair = Couple.ofCouple(p1, p2);

        when(mockedRandom.nextFloat()).thenReturn(0f);
        assertEquals(p1, s.crossover(pair));

        when(mockedRandom.nextFloat()).thenReturn(1f);
        assertEquals(p2, s.crossover(pair));

        when(mockedRandom.nextFloat()).thenReturn(0.5f);
        assertEquals(new MusicParams(3,3,3,3,3), s.crossover(pair));
    }
    @Test(expected = NullPointerException.class)
    public void crossoverStrategy_Null_ThrowNPE() {
        s.crossover(null);
    }

    @Test
    public void select_Correctness_Pass() {
        Population<MusicParams> p = new Population<MusicParams>();
        p.add(new MusicParams(1,1,1,1,1));
        p.add(new MusicParams(8,8,8,8,8));

        s.setUserSettings(new MusicParams(9,9,9,9,9));

        Set<MusicParams> selected = s.select(p).getIndividuals();

        assertEquals(1, selected.size());
        assertTrue(selected.contains(new MusicParams(8,8,8,8,8)));
    }
    @Test(expected = NullPointerException.class)
    public void select_Null_ThrowNPE() {
        s.select(null);
    }

}
