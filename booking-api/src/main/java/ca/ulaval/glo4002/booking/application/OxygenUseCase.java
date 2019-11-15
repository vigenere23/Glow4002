package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.oxygen.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OxygenUseCase {
    private final OxygenHistoryRepository oxygenHistoryRepository;
    private final OxygenInventoryRepository oxygenInventoryRepository;

    public OxygenUseCase(OxygenHistoryRepository oxygenHistoryRepository, OxygenInventoryRepository oxygenInventoryRepository) {
        this.oxygenHistoryRepository = oxygenHistoryRepository;
        this.oxygenInventoryRepository = oxygenInventoryRepository;
    }

    public List<OxygenInventory> getOxygenInventories() {
        return oxygenInventoryRepository.findAll();
    }

    public Map<LocalDate, OxygenHistoryItem> getOxygenHistory() {
        return oxygenHistoryRepository.findAll();
    }
}
