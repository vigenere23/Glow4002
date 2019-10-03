package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;

public interface ShuttlePersistance {
    
    public List<Shuttle> getShuttles(Location location);
    public void saveDeparture(List<Shuttle> shuttlesToSave);
    public void saveArrival(List<Shuttle> shuttlesToSave);
}
