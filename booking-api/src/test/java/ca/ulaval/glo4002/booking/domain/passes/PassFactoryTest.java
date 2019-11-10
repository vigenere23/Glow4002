package ca.ulaval.glo4002.booking.domain.passes;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;

public class PassFactoryTest {

    private PassFactory passFactory;
    private FestivalDates festivalDates;
    
    private static final Optional<LocalDate> VALID_EVENT_DATE = Optional.of(LocalDate.now());
    private static final Optional<LocalDate> EMPTY_EVENT_DATE = Optional.empty();
    
    @BeforeEach
    public void setupPassFactory() {
        festivalDates = mock(FestivalDates.class);
        passFactory = new PassFactory(festivalDates);
    }

    @Test
    public void givenNoEventDate_whenCreatingASinglePass_itThrowsAnIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            passFactory.create(PassOption.SINGLE_PASS, PassCategory.NEBULA, Optional.empty());
        });
    }

    @Test
    public void givenAValidEventDate_whenCreatingAPackagePass_itThrowsAnIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            passFactory.create(PassOption.PACKAGE, PassCategory.NEBULA, VALID_EVENT_DATE);
        });
    }

    @Test
    public void whenCreatingASinglePass_itReturnsASinglePass() {
        PassCategory anyPassCategory = PassCategory.NEBULA;
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, anyPassCategory, VALID_EVENT_DATE);
        assertThat(pass.getPassOption()).isEqualTo(PassOption.SINGLE_PASS);
    }

    @Test
    public void whenCreatingAPackagePass_itReturnsAPackagePass() {
        PassCategory anyPassCategory = PassCategory.NEBULA;
        Pass pass = passFactory.create(PassOption.PACKAGE, anyPassCategory, EMPTY_EVENT_DATE);
        assertThat(pass.getPassOption()).isEqualTo(PassOption.PACKAGE);
    }

    @Test
    public void whenCreatingANebulaSinglePass_itSetsThePriceTo50000() {
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, PassCategory.NEBULA, VALID_EVENT_DATE);
        Price expectedPrice = new Price(50000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCreatingANebulaPackagePass_itSetsThePriceTo250000() {
        Pass pass = passFactory.create(PassOption.PACKAGE, PassCategory.NEBULA, EMPTY_EVENT_DATE);
        Price expectedPrice = new Price(250000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCreatingASupergiantSinglePass_itSetsThePriceTo100000() {
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, VALID_EVENT_DATE);
        Price expectedPrice = new Price(100000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCreatingASupergiantPackagePass_itSetsThePriceTo500000() {
        Pass pass = passFactory.create(PassOption.PACKAGE, PassCategory.SUPERGIANT, EMPTY_EVENT_DATE);
        Price expectedPrice = new Price(500000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCreatingASupernovaSinglePass_itSetsThePriceTo150000() {
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, PassCategory.SUPERNOVA, VALID_EVENT_DATE);
        Price expectedPrice = new Price(150000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCreatingASupernovaPackagePass_itSetsThePriceTo700000() {
        Pass pass = passFactory.create(PassOption.PACKAGE, PassCategory.SUPERNOVA, EMPTY_EVENT_DATE);
        Price expectedPrice = new Price(700000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }
}
