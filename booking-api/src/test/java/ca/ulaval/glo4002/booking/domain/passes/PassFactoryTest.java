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
    
    private static final Optional<LocalDate> EVENT_DATE = Optional.of(LocalDate.now());
    @BeforeEach
    public void setUp() {
        festivalDates = mock(FestivalDates.class);
        passFactory = new PassFactory(festivalDates);
    }

    @Test
    public void givenNoEventDate_whenCreatingASinglePass_itThrowsAnException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            passFactory.create(PassOption.SINGLE_PASS, PassCategory.NEBULA);
        });
    }

    @Test
    public void whenCreatingANebulaSinglePass_itSetsThePriceTo50000() {
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, PassCategory.NEBULA, EVENT_DATE);
        Price expectedPrice = new Price(50000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCreatingANebulaPackagePass_itSetsThePriceTo250000() {
        Pass pass = passFactory.create(PassOption.PACKAGE, PassCategory.NEBULA, EVENT_DATE);
        Price expectedPrice = new Price(250000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCreatingASupergiantSinglePass_itSetsThePriceTo100000() {
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, EVENT_DATE);
        Price expectedPrice = new Price(100000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCreatingASupergiantPackagePass_itSetsThePriceTo500000() {
        Pass pass = passFactory.create(PassOption.PACKAGE, PassCategory.SUPERGIANT, EVENT_DATE);
        Price expectedPrice = new Price(500000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCreatingASupernovaSinglePass_itSetsThePriceTo150000() {
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, PassCategory.SUPERNOVA, EVENT_DATE);
        Price expectedPrice = new Price(150000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }

    @Test
    public void whenCreatingASupernovaPackagePass_itSetsThePriceTo700000() {
        Pass pass = passFactory.create(PassOption.PACKAGE, PassCategory.SUPERNOVA, EVENT_DATE);
        Price expectedPrice = new Price(700000);
        assertThat(pass.getPrice()).isEqualTo(expectedPrice);
    }
}
