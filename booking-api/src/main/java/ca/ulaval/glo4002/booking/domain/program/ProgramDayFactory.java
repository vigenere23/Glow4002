package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;

public class ProgramDayFactory {

    @Inject private ArtistRepository artistRepository;
    @Inject private PassRepository passRepository;

    public ProgramDay create(LocalDate eventDate, String artistName, Activity activity) {
        Artist artist = artistRepository.findByName(artistName);
        int numberOfAttendees = passRepository.findAttendingAtDate(eventDate).size();
        return new ProgramDay(activity, artist, eventDate, numberOfAttendees);
    }
}
