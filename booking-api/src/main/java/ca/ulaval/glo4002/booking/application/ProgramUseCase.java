package ca.ulaval.glo4002.booking.application;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.FestivalAttendeesCounter;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.program.SingleDayProgram;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramUseCase {

    private final TransportReserver transportReserver;
    private final OxygenReserver oxygenReserver;
    private final PassRepository passRepository;
    private final ArtistRepository artistRepository;
    private FestivalAttendeesCounter festivalAttendeesCounter;
    private OutcomeSaver outcomeSaver;

    public ProgramUseCase(TransportReserver transportReserver, OxygenReserver oxygenReserver, ArtistRepository artistRepository, PassRepository passRepository, FestivalAttendeesCounter festivalAttendeesCounter, OutcomeSaver outcomeSaver) {
        this.transportReserver = transportReserver;
        this.oxygenReserver = oxygenReserver;
        this.artistRepository = artistRepository;
        this.passRepository = passRepository;
        this.festivalAttendeesCounter = festivalAttendeesCounter;
        this.outcomeSaver = outcomeSaver;
    }

    public void provideProgramResources(List<SingleDayProgram> program) {
        List<ArtistProgramInformation> artistsForProgram = artistRepository.getArtistsForProgram();
        for (SingleDayProgram singleDayProgram : program) {
            singleDayProgram.orderShuttle(transportReserver, artistsForProgram);
            singleDayProgram.orderOxygen(oxygenReserver, artistsForProgram, countFestivalAttendeesForOneDay(singleDayProgram.getDate()));
            singleDayProgram.saveOutcome(outcomeSaver, artistsForProgram);
        }
    }

    private int countFestivalAttendeesForOneDay(LocalDate eventDate) {
        return festivalAttendeesCounter.countFestivalAttendeesForOneDay(passRepository.findAll(), eventDate);
    }
}