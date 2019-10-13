package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;

public interface ShuttleRepository {
    
    public List<Shuttle> findShuttlesByLocation(Location location);
    public void saveDeparture(List<Shuttle> shuttlesToSave);
    public void saveArrival(List<Shuttle> shuttlesToSave);
    public List<Shuttle> findShuttlesByDate(Location location, LocalDate date);
}
