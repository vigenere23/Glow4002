package ca.ulaval.glo4002.booking.domain.passes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.passNumber.PassNumberFactory;

public class PassFactoryTest {

    private PassFactory passFactory;
    private FestivalDates festivalDates;
    private PassNumberFactory passNumberFactory;
    private PassPriceFactory passPriceFactory;
    
    private static final Optional<LocalDate> VALID_EVENT_DATE = Optional.of(LocalDate.now());
    private static final Optional<LocalDate> EMPTY_EVENT_DATE = Optional.empty();
    private static final PassOption SOME_PASS_OPTION = PassOption.SINGLE_PASS;
    private static final PassCategory SOME_PASS_CATEGORY = PassCategory.NEBULA;
    
    @BeforeEach
    public void setupPassFactory() {
        festivalDates = mock(FestivalDates.class);
        passNumberFactory = new PassNumberFactory(new AtomicLong(0));
        passPriceFactory = mock(PassPriceFactory.class);
        passFactory = new PassFactory(festivalDates, passNumberFactory, passPriceFactory);
    }

    @Test
    public void givenNoEventDate_whenCreatingASinglePass_itThrowsAnIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> passFactory.create(PassOption.SINGLE_PASS, PassCategory.NEBULA, Optional.empty()));
    }

    @Test
    public void givenAValidEventDate_whenCreatingAPackagePass_itThrowsAnIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> passFactory.create(PassOption.PACKAGE, PassCategory.NEBULA, VALID_EVENT_DATE));
    }

    @Test
    public void whenCreatingASinglePass_itReturnsASinglePass() {
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, SOME_PASS_CATEGORY, VALID_EVENT_DATE);
        assertEquals(PassOption.SINGLE_PASS, pass.getPassOption());
    }

    @Test
    public void whenCreatingAPackagePass_itReturnsAPackagePass() {
        Pass pass = passFactory.create(PassOption.PACKAGE, SOME_PASS_CATEGORY, EMPTY_EVENT_DATE);
        assertEquals(PassOption.PACKAGE, pass.getPassOption());
    }

    @Test
    public void whenCreatingAPass_itCallsThePassPriceFactoryWithPassOptionAndCategory() {
        passFactory.create(SOME_PASS_OPTION, SOME_PASS_CATEGORY, VALID_EVENT_DATE);
        verify(passPriceFactory).create(SOME_PASS_OPTION, SOME_PASS_CATEGORY);
    }
}
