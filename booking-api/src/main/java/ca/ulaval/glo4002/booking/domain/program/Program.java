package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class Program {
    private List<SingleDayProgram> program;
    private FestivalDates glow4002Dates;

    public Program(List<SingleDayProgram> program, FestivalDates glow4002Dates) {
        this.program = program;
		this.glow4002Dates = glow4002Dates;
		validateProgram();
    }
     
    private void validateProgram() {
        for(SingleDayProgram programForOneDay : program) {
        	programForOneDay.validateIfAmAndPm();
			programForOneDay.validateActivityOnlyOnAm();
			validateArtistDifferentOnEachDay(programForOneDay);
			validateEventDates(programForOneDay);
		}
	}

    private void validateArtistDifferentOnEachDay(SingleDayProgram programForOneDay) {
		if(Collections.frequency(retrieveArtists(), programForOneDay.getArtist()) != 1) {
			throw new InvalidProgramException();
		}
	}
	
    private List<String> retrieveArtists() {
        return program.stream().map(SingleDayProgram::getArtist).collect(Collectors.toList());
	}
	
	private void validateEventDates(SingleDayProgram programForOneDay) {
		if(!programForOneDay.isDuringFestivalDate(glow4002Dates) || !dateIsUnique(programForOneDay)) {
			throw new InvalidProgramException();
		}
        if (retrieveDates().size() != ChronoUnit.DAYS.between(glow4002Dates.getStartDate(), glow4002Dates.getEndDate().plusDays(1))) {
            throw new InvalidProgramException();
        }
    }

    private boolean dateIsUnique(SingleDayProgram programForOneDay) {
        return Collections.frequency(retrieveDates(), programForOneDay.getDate()) == 1;
    }

    private List<LocalDate> retrieveDates() {
        return program.stream().map(SingleDayProgram::getDate).collect(Collectors.toList());
    }
    
    public void provideProgramResources(TransportRequester transportRequester, OxygenRequester oxygenRequester, ArtistRepository artistRepository) {
        for (SingleDayProgram programForOneDay : program) {   
            programForOneDay.orderOxygen(oxygenRequester, artistRepository);
            programForOneDay.orderShuttle(transportRequester, artistRepository);
        }
    }
}