package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;

import ca.ulaval.glo4002.booking.api.dtos.program.Activity;
import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenDateHistory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class SingleDayProgram {

    private Activity activity;
    private String artistName;
    private LocalDate date;
    private int oxygenQuantity;
    private OxygenGrade oxygenGrade = OxygenGrade.E;

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

    public void orderOxygen(OxygenReserver oxygenReserver, ArtistRepository artistRepository, OxygenInventoryRepository oxygenInventoryRepository, OxygenHistoryRepository oxygenHistoryRepository) {
        ArtistProgramInformation artist = artistRepository.getArtistByName(artistName);
        EnumMap<OxygenGrade, OxygenInventory> inventories = oxygenInventoryRepository.findAll();
        SortedMap<LocalDate, OxygenDateHistory> history = oxygenHistoryRepository.findOxygenHistory();
        oxygenQuantity = artist.getGroupSize()*6;

        if (activity == activity.CARDIO) {
            oxygenQuantity += 15;
        } else {
            oxygenQuantity += 10;
        }

        //oxygenReserver.orderOxygen(date, oxygenGrade, oxygenQuantity, inventories, history);
    }

    public void orderShuttle(TransportReserver transportReserver, ArtistRepository artistRepository) {
        ArtistProgramInformation artist = artistRepository.getArtistByName(artistName);
        ShuttleCategory shuttleCategory = ShuttleCategory.artistShuttle(artist.getGroupSize());
        transportReserver.reserveDeparture(shuttleCategory, date, artist.getId(), artist.getGroupSize());
        transportReserver.reserveArrival(shuttleCategory, date, artist.getId(), artist.getGroupSize());
    }

}