package ca.ulaval.glo4002.booking.application;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.program.SingleDayProgram;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramUseCase {

    private final TransportReserver transportReserver;
    private final OxygenReserver oxygenReserver;
    private final ArtistRepository artistRepository;

    public ProgramUseCase(TransportReserver transportReserver, OxygenReserver oxygenReserver, ArtistRepository artistRepository) {
        this.transportReserver = transportReserver;
        this.oxygenReserver = oxygenReserver;
        this.artistRepository = artistRepository;
    }

    public void provideProgramResources(List<SingleDayProgram> program) {
        for (SingleDayProgram singleDayProgram : program) {
            singleDayProgram.orderShuttle(transportReserver, artistRepository);
        }
    }
}