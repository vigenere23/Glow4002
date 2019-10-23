package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.SortedMap;

public interface OxygenHistoryRepository {

    public SortedMap<LocalDate, OxygenProductionInventory> findOxygenHistory();
    public void saveOxygenHistory(SortedMap<LocalDate, OxygenProductionInventory> history);
    public void saveCreationHistory(LocalDate date, OxygenProductionInventory history);
    public OxygenProductionInventory findCreationHistoryPerDate(LocalDate date);
}
