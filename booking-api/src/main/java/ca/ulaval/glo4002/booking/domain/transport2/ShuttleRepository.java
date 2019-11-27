package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.transport2.Shuttle;

public interface ShuttleRepository {
    
    public List<Shuttle> findByDirection(Direction direction);
    public List<Shuttle> findByDirectionAndDate(Direction direction, LocalDate date);
    public void saveDeparture(List<Shuttle> shuttlesToSave);
    public void saveArrival(List<Shuttle> shuttlesToSave);
}
