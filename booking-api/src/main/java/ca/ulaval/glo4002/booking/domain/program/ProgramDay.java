package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramDay {

    private static final int OXYGEN_QUANTITY_BY_ARTIST = 6;
    private static final OxygenGrade OXYGEN_GRADE_PROGRAM = OxygenGrade.E;
    private static final LocalDate PROGRAM_REVEAL_DATE = LocalDate.of(2050, 07, 12);
    private Activity activity;
    private Artist artist;
    private LocalDate date;

    public ProgramDay(Activity activity, Artist artist, LocalDate date) {
        this.activity = activity;
        this.artist = artist;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public Artist getArtist() {
        return artist;
    }

    public boolean isDuringFestivalDate(FestivalDates festivalDates) {
        return festivalDates.isDuringEventTime(date);
    }

    public void orderOxygen(OxygenRequester oxygenRequester, int numberOfFestivalAttendees) {
        int oxygenQuantity = artist.getGroupSize() * OXYGEN_QUANTITY_BY_ARTIST + Activity.oxygenForActivity(activity) * numberOfFestivalAttendees;
        oxygenRequester.requestOxygen(PROGRAM_REVEAL_DATE, OXYGEN_GRADE_PROGRAM, oxygenQuantity);
    }

    public void orderShuttle(TransportReserver transportReserver) {
        artist.orderShuttle(transportReserver, date);
    }

    public void saveOutcome(OutcomeSaver outcomeSaver) {
        artist.saveOutcome(outcomeSaver);
	}
}
