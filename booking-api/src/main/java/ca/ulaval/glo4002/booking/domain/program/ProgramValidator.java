package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.helpers.enums.EnumExistenceChecker;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;

public class ProgramValidator {
    
    private FestivalDates festivalDates;

    @Inject
    public ProgramValidator(FestivalDates festivalDates) {
        this.festivalDates = festivalDates;
    }

    public void validateProgramDays(List<ProgramDay> programDays) {
        boolean artistsAreDifferent;
        boolean artistOnPMOnly;
        boolean validDates;

        List<String> artistNames = retrieveArtistNames(programDays);
        List<LocalDate> dates = retrieveDates(programDays);

        for (ProgramDay programForOneDay : programDays) {
            artistsAreDifferent = artistDifferentOnEachDay(artistNames, programForOneDay);
            artistOnPMOnly = onlyArtistOnPm(artistNames, programForOneDay);
            validDates = validEventDates(dates, programForOneDay);

            if (!artistOnPMOnly || !artistsAreDifferent || !validDates) {
                throw new InvalidProgramException();
            }
        }
    }

    private boolean artistDifferentOnEachDay(List<String> artistNames, ProgramDay programForOneDay) {
        return Collections.frequency(artistNames, programForOneDay.getArtist().getName()) == 1;
    }

    private boolean onlyArtistOnPm(List<String> artistNames, ProgramDay programForOneDay) {
        return !artistNames
            .stream()
            .filter(artistName -> EnumExistenceChecker.isInEnum(artistName, Activity.class))
            .findFirst()
            .isPresent();
    }

    private boolean validEventDates(List<LocalDate> dates, ProgramDay programForOneDay) {
        return programForOneDay.isDuringFestivalDate(festivalDates) && dateIsUnique(dates, programForOneDay)
                && dates.size() == festivalDates.getNumberOfFestivalDays();
    }

    private boolean dateIsUnique(List<LocalDate> dates, ProgramDay programForOneDay) {
        return Collections.frequency(dates, programForOneDay.getDate()) == 1;
    }

    private List<String> retrieveArtistNames(List<ProgramDay> programDays) {
        return programDays
            .stream()
            .map(ProgramDay::getArtist)
            .map(Artist::getName)
            .collect(Collectors.toList());
    }

    private List<LocalDate> retrieveDates(List<ProgramDay> programDays) {
        return programDays
            .stream()
            .map(ProgramDay::getDate)
            .collect(Collectors.toList());
    }
}
