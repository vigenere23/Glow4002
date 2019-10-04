package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;

public abstract class TransportExposer {
    
    public abstract List<Shuttle> getShuttlesDepartureByDate(LocalDate date) throws OutOfFestivalDatesException;
    public abstract List<Shuttle> getShuttlesArrivalByDate(LocalDate date) throws OutOfFestivalDatesException;
    public abstract void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, long passNumber);
    public abstract void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, long passNumber);
    public abstract List<Shuttle> getAllDepartures();
    public abstract List<Shuttle> getAllArrivals();
}