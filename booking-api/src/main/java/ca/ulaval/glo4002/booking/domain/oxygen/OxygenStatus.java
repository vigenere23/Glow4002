package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.SortedMap;

public class OxygenStatus {

    private OxygenInventory oxygenInventory;
    private SortedMap<LocalDate, OxygenDateHistory> oxygenHistory;

    public OxygenStatus(OxygenInventory oxygenInventory, SortedMap<LocalDate, OxygenDateHistory> oxygenHistory) {
        this.oxygenInventory = oxygenInventory;
        this.oxygenHistory = oxygenHistory;
    }

    public OxygenInventory getOxygenInventory() {
        return oxygenInventory;
    }

    public SortedMap<LocalDate, OxygenDateHistory> getOxygenHistory() {
        return oxygenHistory;
    }
}
