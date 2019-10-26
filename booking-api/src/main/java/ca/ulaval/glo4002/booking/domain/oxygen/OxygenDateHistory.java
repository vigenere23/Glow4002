package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.EnumSet;

public class OxygenDateHistory {

    private EnumMap<HistoryType, Integer> oxygenHistory;
    private LocalDate date;

    public OxygenDateHistory(LocalDate date) {
        this.date = date;
        initializeOxygenInventory();
    }

    public LocalDate getDate() {
        return date;
    }

    public EnumMap<HistoryType, Integer> getOxygenHistory() {
        return oxygenHistory;
    }

    public void updateQuantity(HistoryType type, int quantity) {
        int currentQuantity = oxygenHistory.get(type);
        oxygenHistory.put(type, currentQuantity + quantity);
    }

    public void updateQuantities(OxygenDateHistory oxygenDateHistory) {
        oxygenDateHistory.getOxygenHistory().forEach(this::updateQuantity);
    }

    private void initializeOxygenInventory() {
        oxygenHistory = new EnumMap<HistoryType, Integer>(HistoryType.class);
        EnumSet.allOf(HistoryType.class)
                .forEach(type -> oxygenHistory.put(type, 0));
    }
}
