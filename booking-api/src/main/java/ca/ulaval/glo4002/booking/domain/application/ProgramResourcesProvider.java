package ca.ulaval.glo4002.booking.domain.application;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.api.dtos.program.Activity;
import ca.ulaval.glo4002.booking.api.dtos.program.ProgramRequest;
import ca.ulaval.glo4002.booking.api.dtos.program.SingleDayProgramRequest;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class ProgramResourcesProvider {

    private final TransportRequester transportRequester;
    private final OxygenRequester oxygenRequester;
    private final ArtistRepository artistRepository;
    private final ProgramValidator programValidator;

    public ProgramResourcesProvider(TransportRequester transportRequester, OxygenRequester oxygenRequester, 
    ArtistRepository artistRepository, ProgramValidator programValidator) {
        this.transportRequester = transportRequester;
        this.oxygenRequester = oxygenRequester;
        this.artistRepository = artistRepository;
        this.programValidator = programValidator;
    }

    public void provideProgramResources(ProgramRequest request) {
        programValidator.validateProgram(request.program);
        for (SingleDayProgramRequest singleDayProgramRequest : request.program) {
            provideProgramDailyResources(singleDayProgramRequest.activity, singleDayProgramRequest.artist, singleDayProgramRequest.eventDate);
        }
    }

	public void provideProgramDailyResources(Activity activity, String artist, LocalDate eventDate) {
        orderOxygen(activity, artist);
        orderShuttle(artist, eventDate);
    }
    
    private void orderOxygen(Activity activity, String artist) {
        //TODO
    }

    private void orderShuttle(String artist, LocalDate eventDate) {
        //Artist artist artistRepository.getArtistByName(artist);
        //transportRequester.reserveShuttleForArtist(artist.getId(), artist.getPeopleNumber(), eventDate));
    }
}
