package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.EnumSet;

public class OxygenProductionInventory {
    private EnumMap<HistoryType, Integer> oxygenInventory;
    private LocalDate date;

    public OxygenProductionInventory(LocalDate date) {
        this.date = date;
        initializeOxygenInventory();
    }

    public void updateQuantity(HistoryType type, int quantity) {
        int currentQuantity = oxygenInventory.get(type);
        oxygenInventory.put(type, currentQuantity + quantity);
    }

    public void updateQuantities(OxygenProductionInventory oxygenProductionInventory) {
        oxygenProductionInventory.getOxygenInventory().forEach(this::updateQuantity);
    }

    public EnumMap<HistoryType, Integer> getOxygenInventory() {
        return oxygenInventory;
    }

    public LocalDate getDate() {
        return date;
    }

    private void initializeOxygenInventory() {
        oxygenInventory = new EnumMap<HistoryType, Integer>(HistoryType.class);
        EnumSet.allOf(HistoryType.class)
                .forEach(type -> oxygenInventory.put(type, 0));
    }
}
