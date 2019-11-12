package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;
import java.util.List;

public class PassCounter {
    
    public int countFestivalAttendeesForOneDay(List<Pass> passes, LocalDate eventDate) {
        int numberOfFestivalAttendees = 0;
        for (Pass pass : passes) {
            if (pass.isOfOption(PassOption.PACKAGE) || pass.hasSameDateAs(eventDate)) {
                numberOfFestivalAttendees++;
            }
        }
        return numberOfFestivalAttendees;
    }
}