package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;

import java.time.LocalDate;
import java.util.List;

public class TransportUseCase {

    private FestivalDates festival;
    private ShuttleRepository transportRepository;

    public TransportUseCase(FestivalDates festival, ShuttleRepository transportRepository) {
        this.festival = festival;
        this.transportRepository = transportRepository;
    }

    public List<Shuttle> getAllDepartures() {
        return transportRepository.findShuttlesByLocation(Location.EARTH);
    }

    public List<Shuttle> getAllArrivals() {
        return transportRepository.findShuttlesByLocation(Location.ULAVALOGY);
    }

    public List<Shuttle> getShuttlesDepartureByDate(LocalDate date) {
        validateDateRange(date);
        return transportRepository.findShuttlesByDate(Location.EARTH, date);
    }

    public List<Shuttle> getShuttlesArrivalByDate(LocalDate date) {
        validateDateRange(date);
        return transportRepository.findShuttlesByDate(Location.ULAVALOGY, date);
    }

    private void validateDateRange(LocalDate date) {
        if (!festival.isDuringEventTime(date)) {
            throw new OutOfFestivalDatesException(festival.getStartDate(), festival.getEndDate());
        }
    }
}
