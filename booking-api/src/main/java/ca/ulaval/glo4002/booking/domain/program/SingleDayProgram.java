package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.api.dtos.program.Activity;
import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class SingleDayProgram {

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

    public boolean isDuringFestivalDate(FestivalDates glow4002Dates) {
        return glow4002Dates.isDuringEventTime(date);
    }

    public void orderOxygen(OxygenRequester oxygenRequester, ArtistRepository artistRepository) {
        //TODO
    }

    public void orderShuttle(TransportRequester transportRequester, ArtistRepository artistRepository) {
        ArtistProgramInformation artist = artistRepository.getArtistByName(artistName);
        ShuttleCategory shuttleCategory = ShuttleCategory.artistShuttle(artist.getGroupSize());
        transportRequester.reserveDeparture(shuttleCategory, date, artist.getId(), artist.getGroupSize());
        transportRequester.reserveArrival(shuttleCategory, date, artist.getId(), artist.getGroupSize());
    }
}