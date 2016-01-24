package net.wintermuse.ai.genetic.impl;

import net.wintermuse.ai.genetic.GAPipeline;
import net.wintermuse.ai.genetic.Population;
import net.wintermuse.ai.genetic.impl.MusicParamsEvolution;
import net.wintermuse.ai.genetic.impl.MusicParamsGAStrategies;
import net.wintermuse.core.MusicParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Collection;

import static org.mockito.Mockito.*;

/**
 * Created by oscii on 23/04/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class MusicParamsEvolutionTest {
    @Mock
    private GAPipeline<MusicParams> gaPipeline;
    @Mock
    private MusicParamsGAStrategies gaStrategies;
    @InjectMocks
    MusicParamsEvolution mpe = new MusicParamsEvolution();

    @Test
    public void updatePopulation_Collection_GaPipelineSetPopulationInvoked()
            throws NoSuchFieldException, IllegalAccessException {
        mpe.updatePopulation(mock(Collection.class));
        verify(gaPipeline, times(1)).setPopulation(Matchers.any(Collection.class));
    }
    @Test
    public void updatePopulation_Population_GaPipelineSetPopulationInvoked()
            throws NoSuchFieldException, IllegalAccessException {
        mpe.updatePopulation(new Population<MusicParams>());
        verify(gaPipeline, times(1)).setPopulation(Matchers.any(Population.class));
    }
    @Test(expected = NullPointerException.class)
    public void updatePopulation_Null_ThrowNPE() {
        Collection nullCollection = null;
        MusicParamsEvolution mpe = new MusicParamsEvolution();
        mpe.updatePopulation(nullCollection);
    }

    @Test
    public void updateUserSettings_MusicParams_GaStrategiesSetUserSettingsInvoked() {
        mpe.updateUserSettings(mock(MusicParams.class));
        verify(gaStrategies, times(1)).setUserSettings(any(MusicParams.class));
    }
    @Test(expected = NullPointerException.class)
    public void updateUserSettings_Null_ThrowNPE() {
        MusicParams nullMusicParams = null;
        MusicParamsEvolution mpe = new MusicParamsEvolution();
        mpe.updateUserSettings(nullMusicParams);
    }

    @Test
    public void process_GaPipelineProcessInvoked() {
        mpe.processPopulation();
        verify(gaPipeline, times(1)).process();
    }


    /* Helpers */

    private void injectGaPipeline(GAPipeline<MusicParams> gap,
                                  MusicParamsEvolution mpe)
            throws NoSuchFieldException, IllegalAccessException {
        Field inj = MusicParamsEvolution.class.getDeclaredField("gaPipeline");
        inj.setAccessible(true);
        inj.set(mpe, gap);
        inj.setAccessible(false);
    }

    private void injectGaStrategies(MusicParamsGAStrategies gap,
                                  MusicParamsEvolution mpe)
            throws NoSuchFieldException, IllegalAccessException {
        Field inj = MusicParamsEvolution.class.getDeclaredField("gaStrategies");
        inj.setAccessible(true);
        inj.set(mpe, gap);
        inj.setAccessible(false);
    }

}
