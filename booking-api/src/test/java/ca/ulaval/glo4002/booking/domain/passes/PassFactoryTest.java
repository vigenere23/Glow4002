package ca.ulaval.glo4002.booking.domain.passes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumber;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumberFactory;

@ExtendWith(MockitoExtension.class)
public class PassFactoryTest {
     
    private final static Optional<LocalDate> VALID_EVENT_DATE = Optional.of(LocalDate.now());
    private final static Optional<LocalDate> EMPTY_EVENT_DATE = Optional.empty();
    private final static PassOption SOME_PASS_OPTION = PassOption.SINGLE_PASS;
    private final static PassCategory SOME_PASS_CATEGORY = PassCategory.NEBULA;

    @Mock PassNumberFactory passNumberFactory;
    @Mock FestivalDates festivalDates;
    @Mock PassPriceFactory passPriceFactory;
    @InjectMocks PassFactory passFactory;
    
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
        when(passNumberFactory.create()).thenReturn(new PassNumber(0));
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, SOME_PASS_CATEGORY, VALID_EVENT_DATE);
        assertEquals(PassOption.SINGLE_PASS, pass.getPassOption());
    }

    @Test
    public void whenCreatingAPackagePass_itReturnsAPackagePass() {
        when(passNumberFactory.create()).thenReturn(new PassNumber(0));
        Pass pass = passFactory.create(PassOption.PACKAGE, SOME_PASS_CATEGORY, EMPTY_EVENT_DATE);
        assertEquals(PassOption.PACKAGE, pass.getPassOption());
    }

    @Test
    public void whenCreatingAPass_itCallsThePassPriceFactoryWithPassOptionAndCategory() {
        when(passNumberFactory.create()).thenReturn(new PassNumber(0));
        passFactory.create(SOME_PASS_OPTION, SOME_PASS_CATEGORY, VALID_EVENT_DATE);
        verify(passPriceFactory).create(SOME_PASS_OPTION, SOME_PASS_CATEGORY);
    }
}
