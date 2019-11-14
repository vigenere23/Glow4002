package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Glow4002DatesTest {

    private Glow4002Dates festivalDates;

    private final static LocalDate FESTIVAL_START = LocalDate.of(2050, 7, 17);
    private final static LocalDate FESTIVAL_END = LocalDate.of(2050, 7, 24);
    private final static OffsetDateTime SALE_START = OffsetDateTime.of(2050, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime SALE_END = OffsetDateTime.of(2050, 7, 16, 23, 59, 59, 0, ZoneOffset.UTC);
    private final static LocalDate OUTSIDE_FESTIVAL_DATE = FESTIVAL_START.minusDays(50);
    private final static OffsetDateTime OUTSIDE_ORDER_DATETIME = SALE_START.minusDays(50);

    @BeforeEach
    public void setupGlow4002Dates() {
        festivalDates = new Glow4002Dates();
    }

    @Test
    public void whenCreating_thenTheCorrectEventDatesAreSet() {
        assertEquals(FESTIVAL_START, festivalDates.getStartDate());
        assertEquals(FESTIVAL_END, festivalDates.getEndDate());
    }

    @Test
    public void whenCreating_thenTheCorrectSaleDatesAreSet() {
        assertEquals(SALE_START, festivalDates.getSaleStartDate());
        assertEquals(SALE_END, festivalDates.getSaleEndDate());
    }

    @Test
    public void givenDateOneDayBeforeFestivalStart_whenCheckingIfDateIsDuringEventTime_itReturnsFalse() {
        LocalDate oneDayBeforeFestivalStart = FESTIVAL_START.minusDays(1);

        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(oneDayBeforeFestivalStart);

        assertFalse(isDateDuringEventTime);
    }

    @Test
    public void givenDateOneDayAfterFestivalEnd_whenCheckingIfDateIsDuringEventTime_itReturnsFalse() {
        LocalDate oneDayAfterFestivalEnd = FESTIVAL_END.plusDays(1);

        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(oneDayAfterFestivalEnd);

        assertFalse(isDateDuringEventTime);
    }

    @Test
    public void givenDateIsFestivalStart_whenCheckingIfDateIsDuringEventTime_itReturnsTrue() {
        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(FESTIVAL_START);
        assertTrue(isDateDuringEventTime);
    }

    @Test
    public void givenDateIsFestivalEnd_whenCheckingIfDateIsDuringEventTime_itReturnsTrue() {
        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(FESTIVAL_END);
        assertTrue(isDateDuringEventTime);
    }

    @Test
    public void givenDateOneSecondBeforeSaleStart_whenCheckingIfDateIsDuringSaleTime_itReturnsFalse() {
        OffsetDateTime oneSecondBeforeSaleStart = SALE_START.minusSeconds(1);

        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(oneSecondBeforeSaleStart);

        assertFalse(isDateDuringSaleTime);
    }

    @Test
    public void givenDateOneSecondAfterSaleEnd_whenCheckingIfDateIsDuringSaleTime_itReturnsFalse() {
        OffsetDateTime oneSecondAfterSaleEnd = SALE_END.plusSeconds(1);

        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(oneSecondAfterSaleEnd);
        
        assertFalse(isDateDuringSaleTime);
    }

    @Test
    public void givenDateIsSaleStart_whenCheckingIfDateIsDuringSaleTime_itReturnsTrue() {
        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(SALE_START);
        assertTrue(isDateDuringSaleTime);
    }

    @Test
    public void givenDateIsSaleEnd_whenCheckingIfDateIsDuringSaleTime_itReturnsTrue() {
        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(SALE_END);
        assertTrue(isDateDuringSaleTime);
    }

    @Test
    public void givenDateIsDuringEventTime_whenValidatingEventDates_itDoesNothing() {
        assertDoesNotThrow(() -> festivalDates.validateEventDate(FESTIVAL_START));
    }

    @Test
    public void givenDateIsOutsideEventTime_whenValidatingEventDates_itThrowsAnOutOfFestivalDatesException() {
        assertThrows(OutOfFestivalDatesException.class, () -> festivalDates.validateEventDate(OUTSIDE_FESTIVAL_DATE));
    }

    @Test
    public void givenDateIsDuringSaleTime_whenValidatingEventDates_itDoesNothing() {
        assertDoesNotThrow(() -> festivalDates.validateEventDate(FESTIVAL_START));
    }

    @Test
    public void givenDateIsOutsideSaleTime_whenValidatingEventDates_itThrowsAnOutOfSaleDatesException() {
        assertThrows(OutOfSaleDatesException.class, () -> festivalDates.validateOrderDate(OUTSIDE_ORDER_DATETIME));
    }
}
