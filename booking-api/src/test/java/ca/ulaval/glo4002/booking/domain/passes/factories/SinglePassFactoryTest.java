package ca.ulaval.glo4002.booking.domain.passes.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupergiantSinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupernovaSinglePass;

public class SinglePassFactoryTest {

    private Glow4002Dates festival;
    private SinglePassFactory passFactory;

    private static final LocalDate ANY_DATE = LocalDate.now();

    @BeforeEach
    public void seTup() {
        festival = mock(Glow4002Dates.class);
        when(festival.getStartDate()).thenReturn(ANY_DATE);
        when(festival.getEndDate()).thenReturn(ANY_DATE);

        passFactory = new SinglePassFactory(festival);
    }

    @Test
    public void whenPassingInvalidEventDate_thenAnExceptionIsThrown() {
        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(false);

        assertThatExceptionOfType(OutOfFestivalDatesException.class).isThrownBy(() -> {
            passFactory.create(PassCategory.NEBULA, ANY_DATE);
        });
    }

    @Test
    public void whenPassingValidDate_thenItShouldCreateNebulaSinglePassInstance() throws OutOfFestivalDatesException {
        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(true);

        SinglePass pass = passFactory.create(PassCategory.NEBULA, ANY_DATE);
        assertTrue(pass.getClass().equals(NebulaSinglePass.class));
    }

    @Test
    public void whenPassingValidDate_thenItShouldCreateSupergiantSinglePassInstance() throws OutOfFestivalDatesException {
        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(true);

        SinglePass pass = passFactory.create(PassCategory.SUPERNOVA, ANY_DATE);        
        assertTrue(pass.getClass().equals(SupernovaSinglePass.class));
    }

    @Test
    public void whenPassingValidDate_thenItShouldCreateSupernovaSinglePassInstance() throws OutOfFestivalDatesException {
        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(true);

        SinglePass pass = passFactory.create(PassCategory.SUPERGIANT, ANY_DATE);
        assertTrue(pass.getClass().equals(SupergiantSinglePass.class));
    }
}