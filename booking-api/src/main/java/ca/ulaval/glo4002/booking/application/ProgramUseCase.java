package ca.ulaval.glo4002.booking.application;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.program.SingleDayProgram;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramUseCase {

    private final TransportReserver transportReserver;
    private final OxygenReserver oxygenReserver;
    private final ArtistRepository artistRepository;
    private final ShuttleRepository shuttleRepository;

    public ProgramUseCase(TransportReserver transportReserver, OxygenReserver oxygenReserver, ArtistRepository artistRepository, ShuttleRepository shuttleRepository) {
        this.transportReserver = transportReserver;
        this.oxygenReserver = oxygenReserver;
        this.artistRepository = artistRepository;
        this.shuttleRepository = shuttleRepository;
    }

    public void provideProgramResources(List<SingleDayProgram> program) {
        for (SingleDayProgram singleDayProgram : program) {
            singleDayProgram.orderShuttle(transportReserver, artistRepository);
        }

        for (SingleDayProgram singleDayProgram : program) {
            singleDayProgram.orderOxygen(oxygenReserver, artistRepository, shuttleRepository);
        }
    }
}