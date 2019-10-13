package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;

public class HeapShuttleRepository implements ShuttleRepository {
    
    private List<Shuttle> departureShuttles;
    private List<Shuttle> arrivalShuttles;

    public HeapShuttleRepository() {
        departureShuttles = new LinkedList<Shuttle>();
        arrivalShuttles = new LinkedList<Shuttle>();
    }

    @Override
    public List<Shuttle> getShuttles(Location location) {
        return location == Location.EARTH ? departureShuttles : arrivalShuttles;
    }

    @Override
    public List<Shuttle> getShuttlesByDate(Location location, LocalDate date) {
        List<Shuttle> shuttlesByDate = getShuttles(location).stream()
            .filter(shuttle -> date.equals(shuttle.getDate()))
            .collect(Collectors.toList());
        return shuttlesByDate;
    }

    @Override
    public void saveDeparture(List<Shuttle> shuttlesToSave) {
        if (!shuttlesToSave.contains(null)) {
            departureShuttles = shuttlesToSave;
        }
    }

    @Override
    public void saveArrival(List<Shuttle> shuttlesToSave) {
        if (!shuttlesToSave.contains(null)) {
            arrivalShuttles = shuttlesToSave;
        }
    }
}