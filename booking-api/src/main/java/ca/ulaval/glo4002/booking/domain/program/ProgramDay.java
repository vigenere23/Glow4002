package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramDay {

    private static final OxygenGrade OXYGEN_GRADE_PROGRAM = OxygenGrade.E;
    private static final LocalDate PROGRAM_REVEAL_DATE = LocalDate.of(2050, 07, 12);
    private Activity activity;
    private Artist artist;
    private LocalDate date;
    private int numberOfAttendees;

    public ProgramDay(Activity activity, Artist artist, LocalDate date, int numberOfAttendees) {
        this.activity = activity;
        this.artist = artist;
        this.date = date;
        this.numberOfAttendees = numberOfAttendees;
    }

    public LocalDate getDate() {
        return date;
    }

    public Artist getArtist() {
        return artist;
    }

    public void orderOxygen(OxygenRequester oxygenRequester) {
        artist.orderOxygen(oxygenRequester, PROGRAM_REVEAL_DATE, OXYGEN_GRADE_PROGRAM);

        int oxygenQuantityForActivities = Activity.oxygenForActivity(activity) * numberOfAttendees;
        if (oxygenQuantityForActivities > 0) {
            oxygenRequester.requestOxygen(PROGRAM_REVEAL_DATE, OXYGEN_GRADE_PROGRAM, oxygenQuantityForActivities);
        }
    }

    public void orderShuttle(TransportReserver transportReserver) {
        artist.orderShuttle(transportReserver, date);
    }

    public void saveOutcome(OutcomeSaver outcomeSaver) {
        artist.saveOutcome(outcomeSaver);
	}
}
