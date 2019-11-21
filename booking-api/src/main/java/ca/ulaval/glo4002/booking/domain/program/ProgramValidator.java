package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.helpers.dates.DateCalculator;

public class ProgramValidator {
    private FestivalDates glow4002Dates;
    private List<ProgramDay> program; 

    public ProgramValidator(FestivalDates glow4002Dates) {
        this.glow4002Dates = glow4002Dates;
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
        for(String artistName : retrieveArtists()) {
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
        boolean validDates = true;
        if (!programForOneDay.isDuringFestivalDate(glow4002Dates) || !dateIsUnique(programForOneDay)) {
            validDates = false;
        }
        int numberOfFestivalDays = DateCalculator.numberOfDaysInclusivelyBetween(glow4002Dates.getStartDate(), glow4002Dates.getEndDate());
        if (retrieveDates().size() != numberOfFestivalDays) {
            validDates = false;
        }
        return validDates;
    }

    private boolean dateIsUnique(ProgramDay programForOneDay) {
        return Collections.frequency(retrieveDates(), programForOneDay.getDate()) == 1;
    }

    private List<LocalDate> retrieveDates() {
        return program.stream().map(ProgramDay::getDate).collect(Collectors.toList());
    }
}
