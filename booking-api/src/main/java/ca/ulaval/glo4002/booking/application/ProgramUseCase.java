package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenProducer;
import ca.ulaval.glo4002.booking.domain.program.Program;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReservation;

public class ProgramUseCase {

    private final TransportReservation transportReservation;
    private final ShuttleRepository shuttleRepository;
    private final OxygenProducer oxygenProducer;
    private final ArtistRepository artistRepository;
    private final OxygenInventoryRepository oxygenInventoryRepository;
    private final OxygenHistoryRepository oxygenHistoryRepository;

    public ProgramUseCase(ShuttleRepository shuttleRepository, TransportReservation transportReservation, OxygenProducer oxygenProducer,
    ArtistRepository artistRepository, OxygenInventoryRepository oxygenInventoryRepository, OxygenHistoryRepository oxygenHistoryRepository) {
        this.shuttleRepository = shuttleRepository;
        this.transportReservation = transportReservation;
        this.oxygenProducer = oxygenProducer;
        this.artistRepository = artistRepository;
        this.oxygenInventoryRepository = oxygenInventoryRepository;
        this.oxygenHistoryRepository = oxygenHistoryRepository;
    }

    public void provideProgramResources(Program program) {
       program.provideProgramResources(transportReservation, shuttleRepository, oxygenProducer, artistRepository, oxygenInventoryRepository, oxygenHistoryRepository);
    }
}
