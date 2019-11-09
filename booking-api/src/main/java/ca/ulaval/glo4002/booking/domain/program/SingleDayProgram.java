package ca.ulaval.glo4002.booking.domain.program;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.SortedMap;

import ca.ulaval.glo4002.booking.api.dtos.program.Activity;
import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenDateHistory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenProducer;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReservation;

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

    public void orderOxygen(OxygenProducer oxygenProducer, ArtistRepository artistRepository, OxygenInventoryRepository oxygenInventoryRepository, OxygenHistoryRepository oxygenHistoryRepository) {
        ArtistProgramInformation artist = artistRepository.getArtistByName(artistName);
        EnumMap<OxygenGrade, OxygenInventory> inventories = oxygenInventoryRepository.findInventories();
        SortedMap<LocalDate, OxygenDateHistory> history = oxygenHistoryRepository.findOxygenHistory();
        oxygenQuantity = artist.getGroupSize()*6;

        if (activity == activity.CARDIO) {
            oxygenQuantity += 15;
        } else {
            oxygenQuantity += 10;
        }

        oxygenProducer.orderOxygen(date, oxygenGrade, oxygenQuantity, inventories, history);
    }

    public void orderShuttle(TransportReservation transportReservation, ShuttleRepository shuttleRepository, ArtistRepository artistRepository) {
        ArtistProgramInformation artist = artistRepository.getArtistByName(artistName);
        ShuttleCategory shuttleCategory = ShuttleCategory.artistShuttle(artist.getGroupSize());
        reserveDepartureShuttles(shuttleCategory, transportReservation, shuttleRepository, artist);
        reserveArrivalShuttles(shuttleCategory, transportReservation, shuttleRepository,artist);
    }

    private void reserveDepartureShuttles(ShuttleCategory shuttleCategory, TransportReservation transportReservation, ShuttleRepository shuttleRepository, ArtistProgramInformation artist) {
        List<Shuttle> departureShuttles = shuttleRepository.findShuttlesByLocation(Location.EARTH);
        departureShuttles = transportReservation.reserveShuttle(shuttleCategory, date, artist.getId() , departureShuttles, artist.getGroupSize());
        shuttleRepository.saveDeparture(departureShuttles);
    }

    private void reserveArrivalShuttles(ShuttleCategory shuttleCategory, TransportReservation transportReservation, ShuttleRepository shuttleRepository, ArtistProgramInformation artist) {
        List<Shuttle> departureShuttles = shuttleRepository.findShuttlesByLocation(Location.EARTH);
        departureShuttles = transportReservation.reserveShuttle(shuttleCategory, date, artist.getId() , departureShuttles, artist.getGroupSize());
        shuttleRepository.saveDeparture(departureShuttles);
    }

}