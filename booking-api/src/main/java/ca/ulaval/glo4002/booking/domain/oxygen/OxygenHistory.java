package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.EnumSet;

public class OxygenHistory {
    private EnumMap<HistoryType, Integer> oxygenHistory;
    private LocalDate date;

    public OxygenHistory(LocalDate date) {
        this.date = date;
        initializeOxygenInventory();
    }

    public EnumMap<HistoryType, Integer> getOxygenHistory() {
        return oxygenHistory;
    }

    public void updateQuantity(HistoryType type, int quantity) {
        int currentQuantity = oxygenHistory.get(type);
        oxygenHistory.put(type, currentQuantity + quantity);
    }

    public void updateQuantities(OxygenHistory oxygenHistory) {
        oxygenHistory.getOxygenHistory().forEach(this::updateQuantity);
    }

    public LocalDate getDate() {
        return date;
    }

    private void initializeOxygenInventory() {
        oxygenHistory = new EnumMap<HistoryType, Integer>(HistoryType.class);
        EnumSet.allOf(HistoryType.class)
                .forEach(type -> oxygenHistory.put(type, 0));
    }
}
