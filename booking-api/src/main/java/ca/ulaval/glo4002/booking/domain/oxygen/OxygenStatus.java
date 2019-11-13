package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

public class OxygenStatus {

    private OxygenInventory oxygenInventory;
    private SortedMap<LocalDate, OxygenHistoryItem> orderHistory = new TreeMap<>();

    public OxygenStatus(OxygenInventory oxygenInventory, SortedMap<LocalDate, OxygenHistoryItem> orderDateHistory) {
        this.oxygenInventory = oxygenInventory;
        this.orderHistory = orderDateHistory;
    }

    public OxygenStatus(OxygenInventory oxygenInventory) {
        this.oxygenInventory = oxygenInventory;
    }

    public OxygenInventory getOxygenInventory() {
        return oxygenInventory;
    }

    public SortedMap<LocalDate, OxygenHistoryItem> getOrderHistory() {
        return orderHistory;
    }

}
