package ca.ulaval.glo4002.booking.domain.oxygen;

import ca.ulaval.glo4002.booking.domain.dateUtil.DateComparator;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.EnumSet;

public class OxygenHistoryItem {

    private EnumMap<HistoryType, Integer> oxygenHistory;
    private LocalDate date;

    public OxygenHistoryItem(LocalDate date) {
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

    public void updateQuantities(OxygenHistoryItem oxygenHistoryItem) {
        if (!DateComparator.areDatesEquals(oxygenHistoryItem.getDate(), date)) {
            throw new IllegalArgumentException("Both OxygenHistoryItem don't have the same date.");
        }
        oxygenHistoryItem.getOxygenHistory().forEach(this::updateQuantity);
    }

    private void initializeOxygenInventory() {
        oxygenHistory = new EnumMap<>(HistoryType.class);
        EnumSet.allOf(HistoryType.class)
                .forEach(type -> oxygenHistory.put(type, 0));
    }

    public int getOxygenTankBought() {
        return oxygenHistory.get(HistoryType.OXYGEN_TANK_BOUGHT);
    }

    public int getOxygenTankMade() {
        return oxygenHistory.get(HistoryType.OXYGEN_TANK_MADE);
    }

    public int getWaterUsed() {
        return oxygenHistory.get(HistoryType.WATER_USED);
    }

    public int getCandlesUsed() {
        return oxygenHistory.get(HistoryType.CANDLES_USED);
    }
}
