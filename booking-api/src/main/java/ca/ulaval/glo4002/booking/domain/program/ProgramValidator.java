package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.helpers.DateCalculator;

public class ProgramValidator {
    private FestivalDates glow4002Dates;
    private List<SingleDayProgram> program; 

    public ProgramValidator(FestivalDates glow4002Dates) {
		this.glow4002Dates = glow4002Dates;
    }
     
    public void validateProgram(List<SingleDayProgram> program) {
        this.program = program;
        for(SingleDayProgram programForOneDay : this.program) {
            //TODO voir SingleDayProgram
        	programForOneDay.validateIfAmAndPm();
			programForOneDay.validateActivityOnlyOnAm();
            validateArtistDifferentOnEachDay(programForOneDay);
            validateOnlyArtistOnPm(programForOneDay);
			validateEventDates(programForOneDay);
		}
	}

	private void validateArtistDifferentOnEachDay(SingleDayProgram programForOneDay) {
		if(Collections.frequency(retrieveArtists(), programForOneDay.getArtist()) != 1) {
			throw new InvalidProgramException();
		}
    }
    
    private void validateOnlyArtistOnPm(SingleDayProgram programForOneDay) {
        for(String artistName : retrieveArtists()) {
            Activity.artistIsActivity(artistName);
        }
	}
	
    private List<String> retrieveArtists() {
        return program.stream().map(SingleDayProgram::getArtist).collect(Collectors.toList());
	}
	
	private void validateEventDates(SingleDayProgram programForOneDay) {
		if(!programForOneDay.isDuringFestivalDate(glow4002Dates) || !dateIsUnique(programForOneDay)) {
			throw new InvalidProgramException();
		}
        if (retrieveDates().size() != DateCalculator.daysBetween(glow4002Dates.getStartDate(), glow4002Dates.getEndDate())) {
            throw new InvalidProgramException();
        }
    }

    private boolean dateIsUnique(SingleDayProgram programForOneDay) {
        return Collections.frequency(retrieveDates(), programForOneDay.getDate()) == 1;
    }

    private List<LocalDate> retrieveDates() {
        return program.stream().map(SingleDayProgram::getDate).collect(Collectors.toList());
    }
}