package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;

public class OxygenStatus {
    private EnumMap<OxygenGrade, OxygenInventory> oxygenInventories;
    private SortedMap<LocalDate, OxygenDateHistory> history;

    public OxygenStatus(EnumMap<OxygenGrade, OxygenInventory> oxygenInventories, SortedMap<LocalDate, OxygenDateHistory> history) {
        this.oxygenInventories = oxygenInventories;
        this.history = history;
    }

    public EnumMap<OxygenGrade, OxygenInventory> getOxygenInventories() {
        return oxygenInventories;
    }

    public SortedMap<LocalDate, OxygenDateHistory> getHistory() {
        return history;
    }
}
