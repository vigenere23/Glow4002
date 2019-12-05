package ca.ulaval.glo4002.booking.domain.dates;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public interface FestivalDates {
    LocalDate getStartDate();
    LocalDate getEndDate();
    void updateStartDate(LocalDate startDate);
    void updateEndDate(LocalDate endDate);
    List<LocalDate> getFestivalDates();
    OffsetDateTime getSaleStartDate();
    OffsetDateTime getSaleEndDate();
    void validateOrderDate(OffsetDateTime orderDate);
    void validateEventDate(LocalDate localDate);
}
