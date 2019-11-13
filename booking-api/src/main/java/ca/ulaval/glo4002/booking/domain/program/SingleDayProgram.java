package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class SingleDayProgram {

    protected static final int OXYGEN_QUANTITY_BY_ARTIST = 6;
    protected static final OxygenGrade OXYGEN_GRADE_PROGRAM = OxygenGrade.E;
    protected static final LocalDate PROGRAM_REVEAL_DATE = LocalDate.of(2050, 07, 12);
    private Activity activity;
    private String artistName;
    private LocalDate date;

    public SingleDayProgram(Activity activity, String artistName, LocalDate date) {
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

    public void orderOxygen(OxygenReserver oxygenReserver, List<ArtistProgramInformation> artistsForProgram, int numberOfFestivalAttendees) {
        ArtistProgramInformation artist = getArtist(artistsForProgram);
        int oxygenQuantity = artist.getGroupSize() * OXYGEN_QUANTITY_BY_ARTIST + Activity.oxygenForActivity(activity) * numberOfFestivalAttendees;
        oxygenReserver.reserveOxygen(PROGRAM_REVEAL_DATE, OXYGEN_GRADE_PROGRAM, oxygenQuantity);
    }

    public void orderShuttle(TransportReserver transportReserver, List<ArtistProgramInformation> artistsForProgram) {
        ArtistProgramInformation artist = getArtist(artistsForProgram);
        ShuttleCategory shuttleCategory = ShuttleCategory.getCategoryAccordingToPassengerCount(artist.getGroupSize());
        transportReserver.reserveDeparture(shuttleCategory, date, artist.getPassNumber(), artist.getGroupSize());
        transportReserver.reserveArrival(shuttleCategory, date, artist.getPassNumber(), artist.getGroupSize());
    }

    private ArtistProgramInformation getArtist(List<ArtistProgramInformation> artistsForProgram) {
        return artistsForProgram.stream().filter(artist -> artistName.equals(artist.getArtistName())).findAny().orElse(null);
    }
}