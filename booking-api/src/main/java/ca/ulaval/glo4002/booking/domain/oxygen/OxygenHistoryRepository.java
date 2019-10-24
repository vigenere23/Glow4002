package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.SortedMap;

public interface OxygenHistoryRepository {

    public SortedMap<LocalDate, OxygenHistory> findOxygenHistory();
    public void saveOxygenHistory(SortedMap<LocalDate, OxygenHistory> history);
}
