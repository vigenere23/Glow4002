package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.exceptions.NotFoundException;
import ca.ulaval.glo4002.booking.domain.transport2.Direction;
import ca.ulaval.glo4002.booking.domain.transport2.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport2.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport2.ShuttleRepository;

public class InMemoryShuttleRepository2 implements ShuttleRepository {
    
    private List<Shuttle> departureShuttles;
    private List<Shuttle> arrivalShuttles;

    public InMemoryShuttleRepository2() {
        departureShuttles = new ArrayList<>();
        arrivalShuttles = new ArrayList<>();
    }

    @Override
    public List<Shuttle> findAllByDirection(Direction direction) {
        switch (direction) {
            case DEPARTURE: return departureShuttles;
            case ARRIVAL: return arrivalShuttles;
            default: throw new IllegalArgumentException(String.format("No shuttles for direction %s", direction));
        }
    }

    @Override
    public Shuttle findNextAvailable(Direction direction, LocalDate date, ShuttleCategory shuttleCategory) {
        Optional<Shuttle> nextAvailableShuttle = findAllByDirection(direction)
            .stream()
            .filter(shuttle -> date.equals(shuttle.getDate()))
            .filter(shuttle -> shuttle.getCategory() == shuttleCategory)
            .filter(shuttle -> !shuttle.isFull())
            .findFirst();

        if (!nextAvailableShuttle.isPresent()) {
            throw new NotFoundException("shuttle");
        }

        return nextAvailableShuttle.get();
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
