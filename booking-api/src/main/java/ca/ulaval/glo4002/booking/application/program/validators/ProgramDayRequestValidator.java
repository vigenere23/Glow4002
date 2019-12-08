package ca.ulaval.glo4002.booking.application.program.validators;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.program.Activity;
import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.helpers.enums.EnumExistenceChecker;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;

public class ProgramDayRequestValidator {
    
    private FestivalDates festivalDates;

    @Inject
    public ProgramDayRequestValidator(FestivalDates festivalDates) {
        this.festivalDates = festivalDates;
    }

    public void validate(List<ProgramDayRequest> programDayRequests) {
        List<String> artistNames = retrieveArtistNames(programDayRequests);
        List<LocalDate> dates = retrieveDates(programDayRequests);

        try {
            validateEachArtistPresentOnlyOnce(artistNames);
            validateNoActivityOnPm(artistNames);
            validateEventDateForEachFestivalDay(dates);
        }
        catch (AssertionError exception) {
            throw new InvalidProgramException();
        }
    }

    private void validateEachArtistPresentOnlyOnce(List<String> artistNames) {
        Set<String> uniqueArtistNames = new HashSet<>(artistNames);
        assert artistNames.size() == uniqueArtistNames.size();
    }

    private void validateNoActivityOnPm(List<String> artistNames) {
        assert !artistNames
            .stream()
            .filter(artistName -> EnumExistenceChecker.isInEnum(artistName, Activity.class))
            .findFirst()
            .isPresent();
    }

    private void validateEventDateForEachFestivalDay(List<LocalDate> dates) {
        Collections.sort(dates);
        assert dates.equals(festivalDates.getFestivalDays());
    }

    private List<String> retrieveArtistNames(List<ProgramDayRequest> programDayRequests) {
        return programDayRequests
            .stream()
            .map(programDayRequest -> programDayRequest.artist)
            .collect(Collectors.toList());
    }

    private List<LocalDate> retrieveDates(List<ProgramDayRequest> programDayRequests) {
        return programDayRequests
            .stream()
            .map(programDayRequest -> programDayRequest.eventDate)
            .collect(Collectors.toList());
    }
}
