package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;

public interface ShuttleRepository {
    
    public List<Shuttle> getShuttles(Location location);
    public void saveDeparture(List<Shuttle> shuttlesToSave);
    public void saveArrival(List<Shuttle> shuttlesToSave);
    public List<Shuttle> getShuttlesByDate(Location location, LocalDate date);
}
