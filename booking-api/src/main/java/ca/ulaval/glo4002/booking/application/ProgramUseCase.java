package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.program.Program;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReservation;

public class ProgramUseCase {

    private final TransportReservation transportReservation;
    private final ShuttleRepository shuttleRepository;
    private final ArtistRepository artistRepository;

    public ProgramUseCase(ShuttleRepository shuttleRepository, TransportReservation transportReservation, 
    ArtistRepository artistRepository) {
        this.shuttleRepository = shuttleRepository;
        this.transportReservation = transportReservation;
        this.artistRepository = artistRepository;
    }

    public void provideProgramResources(Program program) {
       program.provideProgramResources(transportReservation, shuttleRepository, artistRepository);
    }
}
