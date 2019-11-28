package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.transport2.Shuttle;

public interface ShuttleRepository {
    
    public List<Shuttle> findAllByDirection(Direction direction);
    public List<Shuttle> findAllAvailable(Direction direction, LocalDate date, ShuttleCategory shuttleCategory);
    public void add(Shuttle shuttle);
    public void replace(Shuttle shuttle);
}
