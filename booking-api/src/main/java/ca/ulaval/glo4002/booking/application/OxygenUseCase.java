package ca.ulaval.glo4002.booking.application;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventoryRepository;

public class OxygenUseCase {
    private final OxygenHistoryRepository oxygenHistory;
    private final OxygenInventoryRepository oxygenInventory;

    public OxygenUseCase(OxygenHistoryRepository oxygenHistory, OxygenInventoryRepository oxygenInventory) {
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
