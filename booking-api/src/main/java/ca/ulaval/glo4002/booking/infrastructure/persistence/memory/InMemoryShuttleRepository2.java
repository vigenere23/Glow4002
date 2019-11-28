package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.transport2.Direction;
import ca.ulaval.glo4002.booking.domain.transport2.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport2.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport2.ShuttleRepository;

public class InMemoryShuttleRepository2 implements ShuttleRepository {
    
    private Map<Direction, List<Shuttle>> shuttles;

    public InMemoryShuttleRepository2() {
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

        if (!shuttlesOfDirection.contains(shuttle)) {
            shuttlesOfDirection.add(shuttle);
        }
    }

    @Override
    public void replace(Shuttle shuttle) {
        List<Shuttle> shuttlesOfDirection = findAllByDirection(shuttle.getDirection());
        int existingShuttleIndex = shuttlesOfDirection.indexOf(shuttle);

        if (existingShuttleIndex >= 0) {
            shuttlesOfDirection.set(existingShuttleIndex, shuttle);
        }
    }
}
