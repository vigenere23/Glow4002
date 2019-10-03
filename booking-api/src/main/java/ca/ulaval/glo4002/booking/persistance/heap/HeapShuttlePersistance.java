package ca.ulaval.glo4002.booking.persistance.heap;

import java.util.LinkedList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.ShuttlePersistance;
import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;

public class HeapShuttlePersistance implements ShuttlePersistance {
    private List<Shuttle> departureShuttles;
    private List<Shuttle> arrivalShuttles;
    
    public HeapShuttlePersistance() {
        departureShuttles = new LinkedList<Shuttle>();
        arrivalShuttles = new LinkedList<Shuttle>();
    }

    @Override
    public List<Shuttle> getShuttles(Location location) {
        return location == Location.EARTH ? departureShuttles : arrivalShuttles;
    }

    @Override
    public void saveDeparture(List<Shuttle> shuttlesToSave) {
        departureShuttles = shuttlesToSave;
    }

    @Override
    public void saveArrival(List<Shuttle> shuttlesToSave) {
        arrivalShuttles = shuttlesToSave;
    }
}
