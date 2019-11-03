package ca.ulaval.glo4002.booking.domain.application;

import ca.ulaval.glo4002.booking.domain.oxygen.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.SortedMap;

public class OxygenUseCase {
    private final OxygenHistoryRepository oxygenHistoryRepository;
    private final OxygenInventoryRepository oxygenInventoryRepository;

    public OxygenUseCase(OxygenHistoryRepository oxygenHistoryRepository, OxygenInventoryRepository oxygenInventoryRepository) {
        this.oxygenHistoryRepository = oxygenHistoryRepository;
        this.oxygenInventoryRepository = oxygenInventoryRepository;
    }

    public List<OxygenInventory> getOxygenInventories() {
        EnumMap<OxygenGrade, OxygenInventory> oxygenInventories = oxygenInventoryRepository.findInventories();
        List<OxygenInventory> inventoryList = new ArrayList<OxygenInventory>();
        for (OxygenGrade oxygenGrade: OxygenGrade.values()) {
            inventoryList.add(oxygenInventories.get(oxygenGrade));
        }
        return inventoryList;
    }

    public SortedMap<LocalDate, OxygenDateHistory> getOxygenHistory() {
        return oxygenHistoryRepository.findOxygenHistory();
    }
}