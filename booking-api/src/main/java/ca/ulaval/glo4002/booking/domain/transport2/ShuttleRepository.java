package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.transport2.Shuttle;

public interface ShuttleRepository {
    
    public List<Shuttle> findAllByDirection(Direction direction);
    public Shuttle findNextAvailable(Direction direction, LocalDate date, ShuttleCategory shuttleCategory);
    public void saveDeparture(List<Shuttle> shuttlesToSave);
    public void saveArrival(List<Shuttle> shuttlesToSave);
}