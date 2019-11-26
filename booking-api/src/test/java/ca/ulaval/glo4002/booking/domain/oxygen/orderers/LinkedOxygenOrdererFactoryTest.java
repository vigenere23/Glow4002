package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

@ExtendWith(MockitoExtension.class)
public class LinkedOxygenOrdererFactoryTest {

    @Mock OxygenOrderer returnedOxygenOrderer;
    @Mock OxygenOrdererLinker oxygenOrdererLinker;
    @Mock SingleOxygenOrdererFactory singleOxygenOrdererFactory;
    @InjectMocks LinkedOxygenOrdererFactory oxygenOrdererFactory;

    @Test
    public void givenOxygenGradeA_whenCreating_itReturnsOrdererWithMinimumProducedGradeA() {
        mockFactoryAndLinkerWithGrade(OxygenGrade.A);
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(OxygenGrade.A);
        assertThat(oxygenOrderer).isEqualTo(returnedOxygenOrderer);
    }

    @Test
    public void givenOxygenGradeB_whenCreating_itReturnsOrdererWithMinimumProducedGradeB() {
        mockFactoryAndLinkerWithGrade(OxygenGrade.B);
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(OxygenGrade.B);
        assertThat(oxygenOrderer).isEqualTo(returnedOxygenOrderer);
    }

    @Test
    public void givenOxygenGradeE_whenCreating_itReturnsOrdererWithMinimumProducedGradeE() {
        mockFactoryAndLinkerWithGrade(OxygenGrade.E);
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(OxygenGrade.E);
        assertThat(oxygenOrderer).isEqualTo(returnedOxygenOrderer);
    }

    private void mockFactoryAndLinkerWithGrade(OxygenGrade minOxygenGrade) {
        List<OxygenOrderer> orderers = new ArrayList<>();
        for (OxygenGrade oxygenGrade : OxygenGrade.values()) {
            if (minOxygenGrade.compareTo(oxygenGrade) <= 0) {
                OxygenOrderer oxygenOrderer = mock(OxygenOrderer.class);
                when(singleOxygenOrdererFactory.create(oxygenGrade)).thenReturn(oxygenOrderer);
                orderers.add(oxygenOrderer);
            }
        }
        when(oxygenOrdererLinker.link(orderers)).thenReturn(returnedOxygenOrderer);
    }
}
