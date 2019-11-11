package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Glow4002DatesTest {

    private Glow4002Dates festivalDates;

    private static final LocalDate FESTIVAL_START = LocalDate.of(2050, 7, 17);
    private static final LocalDate FESTIVAL_END = LocalDate.of(2050, 7, 24);
    private static final OffsetDateTime SALE_START = OffsetDateTime.of(2050, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
    private static final OffsetDateTime SALE_END = OffsetDateTime.of(2050, 7, 16, 23, 59, 59, 0, ZoneOffset.UTC);
    private static final LocalDate OUTSIDE_FESTIVAL_DATE = FESTIVAL_START.minusDays(50);
    private static final OffsetDateTime OUTSIDE_ORDER_DATETIME = SALE_START.minusDays(50);

    @BeforeEach
    public void setupGlow4002Dates() {
        festivalDates = new Glow4002Dates();
    }

    @Test
    public void whenCreating_thenTheCorrectEventDatesAreSet() {
        assertThat(festivalDates.getStartDate()).isEqualTo(FESTIVAL_START);
        assertThat(festivalDates.getEndDate()).isEqualTo(FESTIVAL_END);
    }

    @Test
    public void whenCreating_thenTheCorrectSaleDatesAreSet() {
        assertThat(festivalDates.getSaleStartDate()).isEqualTo(SALE_START);
        assertThat(festivalDates.getSaleEndDate()).isEqualTo(SALE_END);
    }

    @Test
    public void givenDateOneDayBeforeFestivalStart_whenCheckingIfDateIsDuringEventTime_itReturnsFalse() {
        LocalDate oneDayBeforeFestivalStart = FESTIVAL_START.minusDays(1);
        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(oneDayBeforeFestivalStart);
        assertThat(isDateDuringEventTime).isFalse();
    }

    @Test
    public void givenDateOneDayAfterFestivalEnd_whenCheckingIfDateIsDuringEventTime_itReturnsFalse() {
        LocalDate oneDayAfterFestivalEnd = FESTIVAL_END.plusDays(1);
        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(oneDayAfterFestivalEnd);
        assertThat(isDateDuringEventTime).isFalse();
    }

    @Test
    public void givenDateIsFestivalStart_whenCheckingIfDateIsDuringEventTime_itReturnsTrue() {
        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(FESTIVAL_START);
        assertThat(isDateDuringEventTime).isTrue();
    }

    @Test
    public void givenDateIsFestivalEnd_whenCheckingIfDateIsDuringEventTime_itReturnsTrue() {
        boolean isDateDuringEventTime = festivalDates.isDuringEventTime(FESTIVAL_END);
        assertThat(isDateDuringEventTime).isTrue();
    }

    @Test
    public void givenDateOneSecondBeforeSaleStart_whenCheckingIfDateIsDuringSaleTime_itReturnsFalse() {
        OffsetDateTime oneSecondBeforeSaleStart = SALE_START.minusSeconds(1);
        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(oneSecondBeforeSaleStart);
        assertThat(isDateDuringSaleTime).isFalse();
    }

    @Test
    public void givenDateOneSecondAfterSaleEnd_whenCheckingIfDateIsDuringSaleTime_itReturnsFalse() {
        OffsetDateTime oneSecondAfterSaleEnd = SALE_END.plusSeconds(1);
        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(oneSecondAfterSaleEnd);
        assertThat(isDateDuringSaleTime).isFalse();
    }

    @Test
    public void givenDateIsSaleStart_whenCheckingIfDateIsDuringSaleTime_itReturnsTrue() {
        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(SALE_START);
        assertThat(isDateDuringSaleTime).isTrue();
    }

    @Test
    public void givenDateIsSaleEnd_whenCheckingIfDateIsDuringSaleTime_itReturnsTrue() {
        boolean isDateDuringSaleTime = festivalDates.isDuringSaleTime(SALE_END);
        assertThat(isDateDuringSaleTime).isTrue();
    }

    @Test
    public void givenDateIsDuringEventTime_whenValidatingEventDates_itDoesNothing() {
        assertThatCode(() -> {
            festivalDates.validateEventDate(FESTIVAL_START);
        }).doesNotThrowAnyException();
    }

    @Test
    public void givenDateIsOutsideEventTime_whenValidatingEventDates_itThrowsAnOutOfFestivalDatesException() {
        assertThatExceptionOfType(OutOfFestivalDatesException.class).isThrownBy(() -> {
            festivalDates.validateEventDate(OUTSIDE_FESTIVAL_DATE);
        });
    }

    @Test
    public void givenDateIsDuringSaleTime_whenValidatingEventDates_itDoesNothing() {
        assertThatCode(() -> {
            festivalDates.validateOrderDate(SALE_START);
        }).doesNotThrowAnyException();
    }

    @Test
    public void givenDateIsOutsideSaleTime_whenValidatingEventDates_itThrowsAnOutOfSaleDatesException() {
        assertThatExceptionOfType(OutOfSaleDatesException.class).isThrownBy(() -> {
            festivalDates.validateOrderDate(OUTSIDE_ORDER_DATETIME);
        });
    }
}
