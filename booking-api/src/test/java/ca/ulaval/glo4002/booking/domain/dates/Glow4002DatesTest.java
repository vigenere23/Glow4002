package ca.ulaval.glo4002.booking.domain.dates;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.helpers.dates.DateCalculator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class Glow4002DatesTest {

    private Glow4002Dates festivalDates;
    private LocalDate outsideFestivalDate;
    private OffsetDateTime outsideOrderDateTime;

    private static final LocalDate DEFAULT_FESTIVAL_START = LocalDate.of(2050, 07, 17);
    private static final LocalDate DEFAULT_FESTIVAL_END = LocalDate.of(2050, 07, 24);
    private static final int DAYS_BETWEEN_SALE_START_AND_FESTIVAL_START = 180;
    private static final int DAYS_BETWEEN_SALE_END_AND_FESTIVAL_START = 1;

    @BeforeEach
    public void setupGlow4002Dates() {
        festivalDates = new Glow4002Dates();
        outsideFestivalDate = festivalDates.getStartDate().minusDays(50);
        outsideOrderDateTime = festivalDates.getSaleStartDate().minusDays(50);
    }

    @Test
    public void whenCreating_thenTheDefaultStartDateIsSet() {
        assertThat(festivalDates.getStartDate()).isEqualTo(DEFAULT_FESTIVAL_START);
    }

    @Test
    public void whenCreating_thenTheDefaultEndDateIsSet() {
        assertThat(festivalDates.getEndDate()).isEqualTo(DEFAULT_FESTIVAL_END);
    }

    @Test
    public void whenCreating_thenTheCorrectSaleStartDateIsSet() {
        int daysBetweenSaleStartAndFestivalStart = DateCalculator.numberOfDaysInclusivelyBetween(
            festivalDates.getSaleStartDate().toLocalDate(), festivalDates.getStartDate().minusDays(1));
        assertThat(daysBetweenSaleStartAndFestivalStart).isEqualTo(DAYS_BETWEEN_SALE_START_AND_FESTIVAL_START);
    }

    @Test
    public void whenCreating_thenTheCorrectSaleEndDateIsSet() {
        int daysBetweenSaleEndAndFestivalStart = DateCalculator.numberOfDaysInclusivelyBetween(
            festivalDates.getSaleEndDate().toLocalDate(), festivalDates.getStartDate().minusDays(1));
        assertThat(daysBetweenSaleEndAndFestivalStart).isEqualTo(DAYS_BETWEEN_SALE_END_AND_FESTIVAL_START);
    }

    @Test
    public void givenDateOneDayBeforeFestivalStart_whenCheckingIfDateIsDuringEventTime_itReturnsFalse() {
        LocalDate oneDayBeforeFestivalStart = festivalDates.getStartDate().minusDays(1);
        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(oneDayBeforeFestivalStart);
        assertFalse(isDateDuringEventTime);
    }

    @Test
    public void givenDateOneDayAfterFestivalEnd_whenCheckingIfDateIsDuringEventTime_itReturnsFalse() {
        LocalDate oneDayAfterFestivalEnd = festivalDates.getEndDate().plusDays(1);
        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(oneDayAfterFestivalEnd);
        assertFalse(isDateDuringEventTime);
    }

    @Test
    public void givenDateIsFestivalStart_whenCheckingIfDateIsDuringEventTime_itReturnsTrue() {
        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(festivalDates.getStartDate());
        assertTrue(isDateDuringEventTime);
    }

    @Test
    public void givenDateIsFestivalEnd_whenCheckingIfDateIsDuringEventTime_itReturnsTrue() {
        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(festivalDates.getEndDate());
        assertTrue(isDateDuringEventTime);
    }

    @Test
    public void givenDateOneSecondBeforeSaleStart_whenCheckingIfDateIsDuringSaleTime_itReturnsFalse() {
        OffsetDateTime oneSecondBeforeSaleStart = festivalDates.getSaleStartDate().minusSeconds(1);
        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(oneSecondBeforeSaleStart);
        assertFalse(isDateDuringSaleTime);
    }

    @Test
    public void givenDateOneSecondAfterSaleEnd_whenCheckingIfDateIsDuringSaleTime_itReturnsFalse() {
        OffsetDateTime oneSecondAfterSaleEnd = festivalDates.getSaleEndDate().plusSeconds(1);
        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(oneSecondAfterSaleEnd);       
        assertFalse(isDateDuringSaleTime);
    }

    @Test
    public void givenDateIsSaleStart_whenCheckingIfDateIsDuringSaleTime_itReturnsTrue() {
        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(festivalDates.getSaleStartDate());
        assertTrue(isDateDuringSaleTime);
    }

    @Test
    public void givenDateIsSaleEnd_whenCheckingIfDateIsDuringSaleTime_itReturnsTrue() {
        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(festivalDates.getSaleEndDate());
        assertTrue(isDateDuringSaleTime);
    }

    @Test
    public void givenDateIsDuringEventTime_whenValidatingEventDates_itDoesNothing() {
        assertDoesNotThrow(() -> festivalDates.validateEventDate(festivalDates.getStartDate()));
    }

    @Test
    public void givenDateIsOutsideEventTime_whenValidatingEventDates_itThrowsAnOutOfFestivalDatesException() {
        assertThrows(OutOfFestivalDatesException.class, () -> festivalDates.validateEventDate(outsideFestivalDate));
    }

    @Test
    public void givenDateIsDuringSaleTime_whenValidatingOrderDate_itDoesNothing() {
        assertDoesNotThrow(() -> festivalDates.validateOrderDate(festivalDates.getSaleStartDate()));
    }

    @Test
    public void givenDateIsOutsideSaleTime_whenValidatingOrderDate_itThrowsAnOutOfSaleDatesException() {
        assertThrows(OutOfSaleDatesException.class, () -> festivalDates.validateOrderDate(outsideOrderDateTime));
    }
}
