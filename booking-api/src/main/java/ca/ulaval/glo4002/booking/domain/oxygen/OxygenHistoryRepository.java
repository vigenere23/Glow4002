package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.SortedMap;

public interface OxygenHistoryRepository {

    public SortedMap<LocalDate, OxygenDateHistory> findOxygenHistory();
    public void save(SortedMap<LocalDate, OxygenDateHistory> history);
}
