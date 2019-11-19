package ca.ulaval.glo4002.booking.application;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventoryEntry;

public class OxygenUseCase {
    private final OxygenHistory oxygenHistory;
    private final OxygenInventory oxygenInventory;

    public OxygenUseCase(OxygenHistory oxygenHistory, OxygenInventory oxygenInventory) {
        this.oxygenHistory = oxygenHistory;
        this.oxygenInventory = oxygenInventory;
    }

    public List<OxygenInventoryEntry> getOxygenInventory() {
        return oxygenInventory.findAll();
    }

    public List<OxygenHistoryEntry> getOxygenHistory() {
        return oxygenHistory.findAll();
    }
}
