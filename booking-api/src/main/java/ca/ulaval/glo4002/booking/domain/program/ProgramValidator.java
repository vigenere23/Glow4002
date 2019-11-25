package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;

public class ProgramValidator {
    private FestivalDates festivalDates;
    private List<ProgramDay> program;

    @Inject
    public ProgramValidator(FestivalDates festivalDates) {
        this.festivalDates = festivalDates;
    }

    public void validateProgram(List<ProgramDay> program) {
        this.program = program;
        boolean artistsAreDifferent;
        boolean artistOnPMOnly;
        boolean validDates;
        for (ProgramDay programForOneDay : this.program) {
            artistsAreDifferent = artistDifferentOnEachDay(programForOneDay);
            artistOnPMOnly = onlyArtistOnPm(programForOneDay);
            validDates = validEventDates(programForOneDay);

            if (!artistOnPMOnly || !artistsAreDifferent || !validDates) {
                throw new InvalidProgramException();
            }
        }
    }

    private boolean artistDifferentOnEachDay(ProgramDay programForOneDay) {
        return Collections.frequency(retrieveArtists(), programForOneDay.getArtist()) == 1;
    }

    private boolean onlyArtistOnPm(ProgramDay programForOneDay) {
        boolean isAnNotActivity = true;
        for (String artistName : retrieveArtists()) {
            if (Activity.artistIsActivity(artistName)) {
                isAnNotActivity = false;
            }
        }
        return isAnNotActivity;
    }

    private List<String> retrieveArtists() {
        return program.stream().map(ProgramDay::getArtist).collect(Collectors.toList());
    }

    private boolean validEventDates(ProgramDay programForOneDay) {
        return programForOneDay.isDuringFestivalDate(festivalDates) && dateIsUnique(programForOneDay)
                && retrieveDates().size() == festivalDates.getNumberOfDays();
    }

    private boolean dateIsUnique(ProgramDay programForOneDay) {
        return Collections.frequency(retrieveDates(), programForOneDay.getDate()) == 1;
    }

    private List<LocalDate> retrieveDates() {
        return program.stream().map(ProgramDay::getDate).collect(Collectors.toList());
    }
}
