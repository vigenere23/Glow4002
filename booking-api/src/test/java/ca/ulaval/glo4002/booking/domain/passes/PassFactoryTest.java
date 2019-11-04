package ca.ulaval.glo4002.booking.domain.passes;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;

public class PassFactoryTest {

    private PassFactory passFactory;
    private FestivalDates festivalDates;

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
    public void givenNullEventDate_whenCreatingASinglePass_itThrowsAnException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            passFactory.create(PassOption.SINGLE_PASS, PassCategory.NEBULA, null);
        });
    }

    @Test
    public void whenCreatingANebulaSinglePass_itSetsThePriceTo50000() {
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, PassCategory.NEBULA, LocalDate.now());
        assertThat(pass.getPrice()).isEqualTo(Money.of(CurrencyUnit.CAD, 50000));
    }

    @Test
    public void whenCreatingANebulaPackagePass_itSetsThePriceTo250000() {
        Pass pass = passFactory.create(PassOption.PACKAGE, PassCategory.NEBULA, LocalDate.now());
        assertThat(pass.getPrice()).isEqualTo(Money.of(CurrencyUnit.CAD, 250000));
    }

    @Test
    public void whenCreatingASupergiantSinglePass_itSetsThePriceTo100000() {
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, LocalDate.now());
        assertThat(pass.getPrice()).isEqualTo(Money.of(CurrencyUnit.CAD, 100000));
    }

    @Test
    public void whenCreatingASupergiantPackagePass_itSetsThePriceTo500000() {
        Pass pass = passFactory.create(PassOption.PACKAGE, PassCategory.SUPERGIANT, LocalDate.now());
        assertThat(pass.getPrice()).isEqualTo(Money.of(CurrencyUnit.CAD, 500000));
    }

    @Test
    public void whenCreatingASupernovaSinglePass_itSetsThePriceTo150000() {
        Pass pass = passFactory.create(PassOption.SINGLE_PASS, PassCategory.SUPERNOVA, LocalDate.now());
        assertThat(pass.getPrice()).isEqualTo(Money.of(CurrencyUnit.CAD, 150000));
    }

    @Test
    public void whenCreatingASupernovaPackagePass_itSetsThePriceTo700000() {
        Pass pass = passFactory.create(PassOption.PACKAGE, PassCategory.SUPERNOVA, LocalDate.now());
        assertThat(pass.getPrice()).isEqualTo(Money.of(CurrencyUnit.CAD, 700000));
    }
}
