package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;
import java.util.List;

public class PassCounter {
    
    public int countFestivalAttendeesForOneDay(List<Pass> passes, LocalDate eventDate) {
        int numberOfFestivalAttendees = 0;
        for (Pass pass : passes) {
            if (pass.isOfTypeOption(PassOption.PACKAGE) || pass.isOfDate(eventDate)) {
                numberOfFestivalAttendees++;
            }
        }
        return numberOfFestivalAttendees;
    }
}