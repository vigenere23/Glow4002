package ca.ulaval.glo4002.booking.domain.dates;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public interface FestivalDates {

    public LocalDate getStartDate();
    public LocalDate getEndDate();
    public void updateStartDate(LocalDate startDate);
    public void updateEndDate(LocalDate endDate);
    public int getNumberOfDays();
    public OffsetDateTime getSaleStartDate();
    public OffsetDateTime getSaleEndDate();
    public boolean isDuringSaleTime(OffsetDateTime dateTime);
    public boolean isDuringEventTime(LocalDate date);
    public void validateOrderDate(OffsetDateTime orderDate);
    public void validateEventDate(LocalDate localDate);
}
