package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.exceptions.ItemAlreadyExists;
import ca.ulaval.glo4002.booking.domain.exceptions.ItemNotFound;
import ca.ulaval.glo4002.booking.domain.transport.Direction;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleRepository;

public class InMemoryShuttleRepository implements ShuttleRepository {
    
    private Map<Direction, List<Shuttle>> shuttles;

    public InMemoryShuttleRepository() {
        shuttles = new HashMap<>();
        shuttles.put(Direction.DEPARTURE, new ArrayList<>());
        shuttles.put(Direction.ARRIVAL, new ArrayList<>());
    }

    @Override
    public List<Shuttle> findAllByDirection(Direction direction) {
        return shuttles.get(direction);
    }

    @Override
    public List<Shuttle> findAllByDirectionAndDate(Direction direction, LocalDate date) {
        return shuttles.get(direction)
            .stream()
            .filter(shuttle -> shuttle.getDate().equals(date))
            .collect(Collectors.toList());
    }

    @Override
    public List<Shuttle> findAllAvailable(Direction direction, LocalDate date, ShuttleCategory shuttleCategory) {
        return findAllByDirectionAndDate(direction, date)
            .stream()
            .filter(shuttle -> shuttle.getCategory() == shuttleCategory)
            .filter(shuttle -> !shuttle.isFull())
            .collect(Collectors.toList());
    }

    @Override
    public void add(Shuttle shuttle) {
        List<Shuttle> shuttlesOfDirection = findAllByDirection(shuttle.getDirection());

        if (shuttlesOfDirection.contains(shuttle)) {
            throw new ItemAlreadyExists("shuttle", shuttle.toString());
        }

        shuttlesOfDirection.add(shuttle);
    }

    @Override
    public void replace(Shuttle shuttle) {
        List<Shuttle> shuttlesOfDirection = findAllByDirection(shuttle.getDirection());
        int existingShuttleIndex = shuttlesOfDirection.indexOf(shuttle);

        if (existingShuttleIndex < 0) {
            throw new ItemNotFound("shuttle");
        }

        shuttlesOfDirection.set(existingShuttleIndex, shuttle);
    }
}
