package ca.ulaval.glo4002.booking.application.program.validators;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;

public class EventDatesValidator implements ProgramDayRequestValidator {

    private FestivalDates festivalDates;

    @Inject
    public EventDatesValidator(FestivalDates festivalDates) {
        super();
        this.festivalDates = festivalDates;
    }

    @Override
    public void validate(List<ProgramDayRequest> programDayRequests) {
        List<LocalDate> dates = programDayRequests
            .stream()
            .map(programDayRequest -> programDayRequest.eventDate)
            .collect(Collectors.toList());
        Collections.sort(dates);

        boolean isValid = dates.equals(festivalDates.getFestivalDays());

        if (!isValid) {
            throw new InvalidProgramException();
        }
    }
}
