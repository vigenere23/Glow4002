package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.transport.Shuttle;

public interface ShuttleRepository {
    List<Shuttle> findAllByDirection(Direction direction);
    List<Shuttle> findAllByDirectionAndDate(Direction direction, LocalDate date);
    List<Shuttle> findAllAvailable(Direction direction, LocalDate date, ShuttleCategory shuttleCategory);
    void add(Shuttle shuttle);
    void replace(Shuttle shuttle);
}
