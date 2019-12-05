package ca.ulaval.glo4002.booking.domain.dates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.dates.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.dates.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.helpers.dates.DateCalculator;

public class Glow4002DatesTest {

    private Glow4002Dates glow4002Dates;

    private static final LocalDate SOME_DATE = LocalDate.now();
    private static final LocalDate DEFAULT_FESTIVAL_START = LocalDate.of(2050, 07, 17);
    private static final LocalDate DEFAULT_FESTIVAL_END = LocalDate.of(2050, 07, 24);
    private static final int DAYS_BETWEEN_SALE_START_AND_FESTIVAL_START = 180;
    private static final int DAYS_BETWEEN_SALE_END_AND_FESTIVAL_START = 1;
    private static final int DAYS_BETWEEN_OXYGEN_LIMIT_AND_FESTIVAL_START = 1;

    @BeforeEach
    public void setupGlow4002Dates() {
        glow4002Dates = new Glow4002Dates();
    }

    @Test
    public void whenCreating_thenTheDefaultStartDateIsSet() {
        assertThat(glow4002Dates.getStartDate()).isEqualTo(DEFAULT_FESTIVAL_START);
    }

    @Test
    public void whenCreating_thenTheDefaultEndDateIsSet() {
        assertThat(glow4002Dates.getEndDate()).isEqualTo(DEFAULT_FESTIVAL_END);
    }

    @Test
    public void whenCreating_thenTheCorrectSaleStartDateIsSet() {
        int daysBetweenSaleStartAndFestivalStart = DateCalculator.numberOfDaysInclusivelyBetween(
            glow4002Dates.getSaleStartDate().toLocalDate(), glow4002Dates.getStartDate());
        assertThat(daysBetweenSaleStartAndFestivalStart).isEqualTo(DAYS_BETWEEN_SALE_START_AND_FESTIVAL_START + 1);
    }

    @Test
    public void whenCreating_thenTheCorrectSaleEndDateIsSet() {
        int daysBetweenSaleEndAndFestivalStart = DateCalculator.numberOfDaysInclusivelyBetween(
            glow4002Dates.getSaleEndDate().toLocalDate(), glow4002Dates.getStartDate());
        assertThat(daysBetweenSaleEndAndFestivalStart).isEqualTo(DAYS_BETWEEN_SALE_END_AND_FESTIVAL_START + 1);
    }

    @Test
    public void whenCreating_thenTheCorrectOxygenEndDateIsSet() {
        int daysBetweenOxygenLimitAndFestivalStart = DateCalculator.numberOfDaysInclusivelyBetween(
            glow4002Dates.getOxygenLimitDeliveryDate().toLocalDate(), glow4002Dates.getStartDate());
        assertThat(daysBetweenOxygenLimitAndFestivalStart).isEqualTo(DAYS_BETWEEN_OXYGEN_LIMIT_AND_FESTIVAL_START + 1);
    }

    @Test
    public void givenNewStartDate_whenUpdatingStartDate_itSetTheNewStartDate() {
        glow4002Dates.updateStartDate(SOME_DATE);
        assertThat(glow4002Dates.getStartDate()).isEqualTo(SOME_DATE);
    }

    @Test
    public void whenUpdatingStartDate_thenTheCorrectSaleStartDateIsSet() {
        glow4002Dates.updateStartDate(SOME_DATE);
        int daysBetweenSaleStartAndFestivalStart = DateCalculator.numberOfDaysInclusivelyBetween(
            glow4002Dates.getSaleStartDate().toLocalDate(), glow4002Dates.getStartDate());
        assertThat(daysBetweenSaleStartAndFestivalStart).isEqualTo(DAYS_BETWEEN_SALE_START_AND_FESTIVAL_START + 1);
    }

    @Test
    public void whenUpdatingStartDate_thenTheCorrectSaleEndDateIsSet() {
        glow4002Dates.updateStartDate(SOME_DATE);
        int daysBetweenSaleEndAndFestivalStart = DateCalculator.numberOfDaysInclusivelyBetween(
            glow4002Dates.getSaleEndDate().toLocalDate(), glow4002Dates.getStartDate());
        assertThat(daysBetweenSaleEndAndFestivalStart).isEqualTo(DAYS_BETWEEN_SALE_END_AND_FESTIVAL_START + 1);
    }

    @Test
    public void whenUpdatingStartDate_thenTheCorrectOxygneLimitDateIsSet() {
        glow4002Dates.updateStartDate(SOME_DATE);
        long daysBetweenSaleEndAndFestivalStart = DateCalculator.numberOfDaysInclusivelyBetween(
            glow4002Dates.getOxygenLimitDeliveryDate().toLocalDate(), glow4002Dates.getStartDate());
        assertThat(daysBetweenSaleEndAndFestivalStart).isEqualTo(DAYS_BETWEEN_OXYGEN_LIMIT_AND_FESTIVAL_START + 1);
    }

    @Test
    public void givenNewEndDate_whenUpdatingEndDate_itSetTheNewEndDate() {
        glow4002Dates.updateEndDate(SOME_DATE);
        assertThat(glow4002Dates.getEndDate()).isEqualTo(SOME_DATE);
    }

    @Test
    public void givenNDaysInclusivelyBetweenFestivalStartAndEnd_whenGettingNumberOfDays_itShouldReturnNDays() {
        int numberOfDays = 4;
        glow4002Dates.updateEndDate(DEFAULT_FESTIVAL_START.plusDays(numberOfDays - 1));
        LocalDate[] expectedFestivalDates = {
            DEFAULT_FESTIVAL_START,
            DEFAULT_FESTIVAL_START.plusDays(1),
            DEFAULT_FESTIVAL_START.plusDays(2),
            DEFAULT_FESTIVAL_START.plusDays(3)
        };

        assertThat(glow4002Dates.getFestivalDates()).isEqualTo(Arrays.asList(expectedFestivalDates));
    }

    @Test
    public void givenDateOneDayBeforeFestivalStart_whenValidatingEventDate_itThrowsAnOutOfFestivalDatesException() {
        LocalDate oneDayBeforeFestivalStart = glow4002Dates.getStartDate().minusDays(1);
        assertThatExceptionOfType(OutOfFestivalDatesException.class).isThrownBy(() -> {
            glow4002Dates.validateEventDate(oneDayBeforeFestivalStart);
        });
    }

    @Test
    public void givenDateOneDayAfterFestivalEnd_whenValidatingEventDate_itThrowsAnOutOfFestivalDatesException() {
        LocalDate oneDayAfterFestivalEnd = glow4002Dates.getEndDate().plusDays(1);
        assertThatExceptionOfType(OutOfFestivalDatesException.class).isThrownBy(() -> {
            glow4002Dates.validateEventDate(oneDayAfterFestivalEnd);
        });
    }

    @Test
    public void givenDateIsFestivalStart_whenValidatingEventDate_itDoesNotThrowAnyException() {
        assertThatCode(() -> {
            glow4002Dates.validateEventDate(glow4002Dates.getStartDate());
        }).doesNotThrowAnyException();
    }

    @Test
    public void givenDateIsFestivalEnd_whenValidatingEventDate_itDoesNotThrowAnyException() {
        assertThatCode(() -> {
            glow4002Dates.validateEventDate(glow4002Dates.getEndDate());
        }).doesNotThrowAnyException();
    }

    @Test
    public void givenDateOneSecondBeforeSaleStart_whenValidatingOrderDate_itThrowsAnOutOfSaleDatesException() {
        OffsetDateTime oneSecondBeforeSaleStart = glow4002Dates.getSaleStartDate().minusSeconds(1);
        assertThatExceptionOfType(OutOfSaleDatesException.class).isThrownBy(() -> {
            glow4002Dates.validateOrderDate(oneSecondBeforeSaleStart);
        });
    }

    @Test
    public void givenDateOneSecondAfterSaleEnd_whenValidatingOrderDate_itThrowsAnOutOfSaleDatesException() {
        OffsetDateTime oneSecondAfterSaleEnd = glow4002Dates.getSaleEndDate().plusSeconds(1);
        assertThatExceptionOfType(OutOfSaleDatesException.class).isThrownBy(() -> {
            glow4002Dates.validateOrderDate(oneSecondAfterSaleEnd);
        });
    }

    @Test
    public void givenDateIsSaleStart_whenValidatingOrderDate_itDoesNotThrowAnyException() {
        assertThatCode(() -> {
            glow4002Dates.validateOrderDate(glow4002Dates.getSaleStartDate());
        }).doesNotThrowAnyException();
    }

    @Test
    public void givenDateIsSaleEnd_whenValidatingOrderDate_itDoesNotThrowAnyException() {
        assertThatCode(() -> {
            glow4002Dates.validateOrderDate(glow4002Dates.getSaleEndDate());
        }).doesNotThrowAnyException();
    }
}
