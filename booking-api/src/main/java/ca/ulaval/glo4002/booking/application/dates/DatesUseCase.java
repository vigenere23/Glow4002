package ca.ulaval.glo4002.booking.application.dates;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;

public class DatesUseCase {

    private FestivalDates festivalDates;

    public DatesUseCase(FestivalDates festivalDates) {
        this.festivalDates = festivalDates;
    }

    public void updateFestivalDates(LocalDate startDate, LocalDate endDate) {
        festivalDates.updateStartDate(startDate);
        festivalDates.updateEndDate(endDate);
    }
}
