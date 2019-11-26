package ca.ulaval.glo4002.booking.application.dates;

import java.time.LocalDate;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;

public class DatesUseCase {

    @Inject private FestivalDates festivalDates;

    public void updateFestivalDates(LocalDate startDate, LocalDate endDate) {
        festivalDates.updateStartDate(startDate);
        festivalDates.updateEndDate(endDate);
    }
}
