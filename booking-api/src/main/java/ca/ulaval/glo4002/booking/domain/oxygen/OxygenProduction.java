package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;

public class OxygenProduction {
    private int fabricationTimeInDays;
    private int tankFabricationQuantity;
    private EnumMap<HistoryType, Integer> orderDateQuantityPerBatch;
    private EnumMap<HistoryType, Integer> completionDateQuantityPerBatch;

    public OxygenProduction(int fabricationTimeInDays, int tankFabricationQuantity, EnumMap<HistoryType, Integer> orderDateQuantityPerBatch, EnumMap<HistoryType, Integer> completionDateQantityPerBatch) {
        this.fabricationTimeInDays = fabricationTimeInDays;
        this.tankFabricationQuantity = tankFabricationQuantity;
        this.orderDateQuantityPerBatch = orderDateQuantityPerBatch;
        this.completionDateQuantityPerBatch = completionDateQantityPerBatch;
    }

    public SortedMap<LocalDate, OxygenDateHistory> updateOxygenHistory(SortedMap<LocalDate, OxygenDateHistory> history, LocalDate orderDate, int orderedQuantity) {
        updateOrderDateHistory(history, orderDate, orderedQuantity);
        updateCompletionDateHistory(history, orderDate, orderedQuantity);
        return history;
    }

    private void updateOrderDateHistory(SortedMap<LocalDate, OxygenDateHistory> history, LocalDate orderDate, int orderedQuantity) {
        if (!orderDateQuantityPerBatch.isEmpty()) {
            OxygenDateHistory orderDateHistory = getDateHistory(orderDateQuantityPerBatch, orderDate, orderedQuantity);
            updateHistory(history, orderDateHistory);
        }
    }

    private void updateCompletionDateHistory(SortedMap<LocalDate, OxygenDateHistory> history, LocalDate orderDate, int orderedQuantity) {
        if (!completionDateQuantityPerBatch.isEmpty()) {
            OxygenDateHistory completionDateHistory = getDateHistory(completionDateQuantityPerBatch, getFabricationCompletionDate(orderDate), orderedQuantity);
            updateHistory(history, completionDateHistory);
        }
    }

    private OxygenDateHistory getDateHistory(EnumMap<HistoryType, Integer> quantityPerBatch, LocalDate date, int orderedQuantity) {
        OxygenDateHistory oxygenDateHistory = new OxygenDateHistory(date);
        quantityPerBatch.forEach(
                (historyType, fabricationQuantity) -> oxygenDateHistory.updateQuantity(historyType, getQuantityToFabricate(orderedQuantity, fabricationQuantity))
        );
        return oxygenDateHistory;
    }

    private LocalDate getFabricationCompletionDate(LocalDate orderDate) {
        return orderDate.plusDays(fabricationTimeInDays);
    }

    public int getQuantityToFabricate(int tankRequiredQuantity, int fabricationQuantity) {
        return getQuantityOfFabricationBatchesRequired(tankRequiredQuantity) * fabricationQuantity;
    }

    private int getQuantityOfFabricationBatchesRequired(int requiredQuantity) {
        if (requiredQuantity % tankFabricationQuantity > 0) {
            return requiredQuantity / tankFabricationQuantity + 1;
        }
        return requiredQuantity / tankFabricationQuantity;
    }

    private void updateHistory(SortedMap<LocalDate, OxygenDateHistory> history, OxygenDateHistory oxygenDateHistory) {
        LocalDate date = oxygenDateHistory.getDate();
        if (history.containsKey(date)) {
            history.get(date).updateQuantities(oxygenDateHistory);
        } else {
            history.put(date, oxygenDateHistory);
        }
    }
}
