package ca.ulaval.glo4002.booking.domain.dates;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public interface FestivalDates {
    LocalDate getStartDate();
    LocalDate getEndDate();
    void updateStartDate(LocalDate startDate);
    void updateEndDate(LocalDate endDate);
    int getNumberOfFestivalDays();
    OffsetDateTime getSaleStartDate();
    OffsetDateTime getSaleEndDate();
    boolean isDuringSaleTime(OffsetDateTime dateTime);
    boolean isDuringEventTime(LocalDate date);
    void validateOrderDate(OffsetDateTime orderDate);
    void validateEventDate(LocalDate localDate);
}
