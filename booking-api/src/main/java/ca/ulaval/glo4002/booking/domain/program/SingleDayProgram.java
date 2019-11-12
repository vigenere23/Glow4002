package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class SingleDayProgram {

    private static final int OXYGEN_QUANTITY_BY_ARTIST = 6;
    private static final OxygenGrade OXYGEN_GRADE_PROGRAM = OxygenGrade.E;
    private static final LocalDate PROGRAM_REVEAL_DATE = LocalDate.of(2050, 07, 12);
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

    public void orderOxygen(OxygenReserver oxygenReserver, ArtistRepository artistRepository, int numberOfFestivalAttendees) {
        ArtistProgramInformation artist = artistRepository.getArtistByName(artistName);
        int oxygenQuantity = artist.getGroupSize() * OXYGEN_QUANTITY_BY_ARTIST + Activity.oxygenForActivity(activity) * numberOfFestivalAttendees;
        oxygenReserver.reserveOxygen(PROGRAM_REVEAL_DATE, OXYGEN_GRADE_PROGRAM, oxygenQuantity);
    }

    public void orderShuttle(TransportReserver transportReserver, ArtistRepository artistRepository) {
        ArtistProgramInformation artist = artistRepository.getArtistByName(artistName);
        ShuttleCategory shuttleCategory = ShuttleCategory.artistShuttle(artist.getGroupSize());
        transportReserver.reserveDeparture(shuttleCategory, date, artist.getId(), artist.getGroupSize());
        transportReserver.reserveArrival(shuttleCategory, date, artist.getId(), artist.getGroupSize());
    }
}