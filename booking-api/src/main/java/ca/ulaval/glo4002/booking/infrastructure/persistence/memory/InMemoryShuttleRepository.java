package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;

public class InMemoryShuttleRepository implements ShuttleRepository {
    
    private List<Shuttle> departureShuttles;
    private List<Shuttle> arrivalShuttles;

    public InMemoryShuttleRepository() {
        departureShuttles = new ArrayList<>();
        arrivalShuttles = new ArrayList<>();
    }

    @Override
    public List<Shuttle> findShuttlesByLocation(Location location) {
        return location == Location.EARTH ? departureShuttles : arrivalShuttles;
    }

    @Override
    public List<Shuttle> findShuttlesByDate(Location location, LocalDate date) {
        List<Shuttle> shuttlesByDate = findShuttlesByLocation(location).stream()
            .filter(shuttle -> date.equals(shuttle.getDate()))
            .collect(Collectors.toList());
        return shuttlesByDate;
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
