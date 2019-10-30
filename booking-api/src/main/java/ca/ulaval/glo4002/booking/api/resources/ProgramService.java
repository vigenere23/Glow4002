package ca.ulaval.glo4002.booking.api.resources;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.api.dtos.program.Activity;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class ProgramService {

    //private ArtistRepository artistRepository;
    private final TransportRequester transportRequester;
    private final OxygenRequester oxygenRequester;

    public ProgramService(TransportRequester transportRequester, OxygenRequester oxygenRequester) {
        this.transportRequester = transportRequester;
        this.oxygenRequester = oxygenRequester;
        //this.artistRepository = artistRepository;
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
        
        //PassNumber passNumber = new PassNumber(artist.getPassNumber());
        //transportRequester.reserveDeparture(artist.getShuttleCategory(), eventDate, passNumber, artist.getPeopleNumber());
        //transportRequester.reserveArrival(artist.getShuttleCategory(), eventDate, passNumber, artist.getPeopleNumber());
    }
}
