package ca.ulaval.glo4002.booking.domain.application;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.program.Program;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class ProgramResourcesProvider {

    private final TransportRequester transportRequester;
    private final OxygenRequester oxygenRequester;
    private final ArtistRepository artistRepository;

    public ProgramResourcesProvider(TransportRequester transportRequester, OxygenRequester oxygenRequester, 
    ArtistRepository artistRepository) {
        this.transportRequester = transportRequester;
        this.oxygenRequester = oxygenRequester;
        this.artistRepository = artistRepository;
    }

    public void provideProgramResources(Program program) {
       program.provideProgramResources(transportRequester, oxygenRequester, artistRepository);
    }
}
