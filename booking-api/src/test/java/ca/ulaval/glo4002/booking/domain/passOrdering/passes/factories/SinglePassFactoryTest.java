package ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupernovaSinglePass;

public class SinglePassFactoryTest {

    Glow4002 festival = new Glow4002();
    private SinglePassFactory passFactory = new SinglePassFactory(festival);
    private OffsetDateTime validDate = OffsetDateTime.of(LocalDate.of(2050, 7, 18), LocalTime.MIDNIGHT, ZoneOffset.UTC);

    @Test
    public void whenPassingInvalidEventDate_thenAnExceptionIsThrown() {
        assertThatExceptionOfType(OutOfFestivalDatesException.class).isThrownBy(() -> {
            passFactory.create(PassCategory.NEBULA, OffsetDateTime.now());
        });
    }

    @Test
    public void shouldCreateNebulaSinglePassInstance() throws OutOfFestivalDatesException {
        SinglePass pass = passFactory.create(PassCategory.NEBULA, validDate);
        assertTrue(pass.getClass().equals(NebulaSinglePass.class));
    }

    @Test
    public void shouldCreateSupergiantSinglePassInstance() throws OutOfFestivalDatesException {
        SinglePass pass = passFactory.create(PassCategory.SUPERNOVA, validDate);
        assertTrue(pass.getClass().equals(SupernovaSinglePass.class));
    }

    @Test
    public void shouldCreateSupernovaSinglePassInstance() throws OutOfFestivalDatesException {
        SinglePass pass = passFactory.create(PassCategory.SUPERGIANT, validDate);
        assertTrue(pass.getClass().equals(SupergiantSinglePass.class));
    }
}