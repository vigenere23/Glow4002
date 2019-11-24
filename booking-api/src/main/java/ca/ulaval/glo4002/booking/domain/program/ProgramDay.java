package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramDay {

    protected static final int OXYGEN_QUANTITY_BY_ARTIST = 6;
    protected static final OxygenGrade OXYGEN_GRADE_PROGRAM = OxygenGrade.E;
    protected static final LocalDate PROGRAM_REVEAL_DATE = LocalDate.of(2050, 07, 12);
    private Activity activity;
    private String artistName;
    private LocalDate date;

    public ProgramDay(Activity activity, String artistName, LocalDate date) {
        this.activity = activity;
        this.artistName = artistName;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getArtist() {
        return artistName;
    }

    public boolean isDuringFestivalDate(FestivalDates festivalDates) {
        return festivalDates.isDuringEventTime(date);
    }

    public void orderOxygen(OxygenRequester oxygenRequester, List<ArtistProgramInformation> artistsForProgram, int numberOfFestivalAttendees) {
        ArtistProgramInformation artist = getArtist(artistsForProgram);
        int oxygenQuantity = artist.getGroupSize() * OXYGEN_QUANTITY_BY_ARTIST + Activity.oxygenForActivity(activity) * numberOfFestivalAttendees;
        oxygenRequester.requestOxygen(PROGRAM_REVEAL_DATE, OXYGEN_GRADE_PROGRAM, oxygenQuantity);
    }

    public void orderShuttle(TransportReserver transportReserver, List<ArtistProgramInformation> artistsForProgram) {
        ArtistProgramInformation artist = getArtist(artistsForProgram);
        ShuttleCategory shuttleCategory = ShuttleCategory.getCategoryAccordingToPassengerCount(artist.getGroupSize());
        transportReserver.reserveDeparture(shuttleCategory, date, artist.getPassengerNumber(), artist.getGroupSize());
        transportReserver.reserveArrival(shuttleCategory, date, artist.getPassengerNumber(), artist.getGroupSize());
    }

    public void saveOutcome(OutcomeSaver outcomeSaver, List<ArtistProgramInformation> artistsForProgram) {
        outcomeSaver.saveOutcome(getArtist(artistsForProgram).getPrice());
	}

    private ArtistProgramInformation getArtist(List<ArtistProgramInformation> artistsForProgram) {
        return artistsForProgram.stream().filter(artistInformation -> artistName.equals(artistInformation.getArtistName())).findAny().orElse(null);
    }
}
