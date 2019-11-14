package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.Map;

public interface OxygenHistoryRepository {

    public OxygenHistoryItem findOxygenHistoryOfDate(LocalDate date);
    public void save (OxygenHistoryItem history);
    public Map<LocalDate, OxygenHistoryItem> findAll();
}
