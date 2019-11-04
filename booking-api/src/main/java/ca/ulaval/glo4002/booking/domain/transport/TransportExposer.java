package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.transport.Shuttle;

public interface TransportExposer {
    
    public abstract List<Shuttle> getShuttlesDepartureByDate(LocalDate date);
    public abstract List<Shuttle> getShuttlesArrivalByDate(LocalDate date);
    public abstract List<Shuttle> getAllDepartures();
    public abstract List<Shuttle> getAllArrivals();
}
