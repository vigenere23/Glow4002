package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class SingleDayProgram {

    private static final int QUANTITY_BY_ARTIST = 6;
    private static final OxygenGrade OXYGEN_GRADE = OxygenGrade.E;
    private Activity activity;
    private String artistName;
    private LocalDate date;
    private LocalDate programPostingDate = LocalDate.of(2050, 07, 12);
    private int oxygenQuantity;

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

    //TODO va changer de place suite à la discussion pour la validation
    public void validateIfAmAndPm() {
        if(activity == null || artistName.equals(null)) {
            throw new InvalidProgramException();
        }
    }

    public void validateActivityOnlyOnAm() {
        if(!Activity.contains(activity)) {
            throw new InvalidProgramException();
        }
    }

    public boolean isDuringFestivalDate(FestivalDates festivalDates) {
        return festivalDates.isDuringEventTime(date);
    }

    public void orderOxygen(OxygenReserver oxygenReserver, ArtistRepository artistRepository) {
        ArtistProgramInformation artist = artistRepository.getArtistByName(artistName);
        
        oxygenQuantity = artist.getGroupSize() * QUANTITY_BY_ARTIST + Activity.oxygenForActivity(activity);
        oxygenReserver.reserveOxygen(programPostingDate, OXYGEN_GRADE, oxygenQuantity);
    }

    public void orderShuttle(TransportReserver transportReserver, ArtistRepository artistRepository) {
        ArtistProgramInformation artist = artistRepository.getArtistByName(artistName);
        ShuttleCategory shuttleCategory = ShuttleCategory.artistShuttle(artist.getGroupSize());
        transportReserver.reserveDeparture(shuttleCategory, date, artist.getId(), artist.getGroupSize());
        transportReserver.reserveArrival(shuttleCategory, date, artist.getId(), artist.getGroupSize());
    }

}