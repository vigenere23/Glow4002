package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public interface FestivalDates {

    public LocalDate getStartDate();
    public LocalDate getEndDate();
    public OffsetDateTime getSaleStartDate();
    public OffsetDateTime getSaleEndDate();
    public boolean isDuringSaleTime(OffsetDateTime dateTime);
    public boolean isDuringEventTime(LocalDate date);
    public void validateOrderDate(OffsetDateTime orderDate);
    public void validateEventDate(LocalDate localDate);
}
