package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.*;

public abstract class OxygenOrder {

    protected LocalDate limitDeliveryDate;
    protected int tankFabricationQuantity;
    protected int fabricationTimeInDays;
    protected EnumMap<HistoryType, Integer> orderDateQuantitiesPerBatch = new EnumMap<>(HistoryType.class);
    protected EnumMap<HistoryType, Integer> completionDateQuantitiesPerBatch = new EnumMap<>(HistoryType.class);

    public OxygenOrder(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
        this.limitDeliveryDate = limitDeliveryDate;
        this.tankFabricationQuantity = tankFabricationQuantity;
        this.fabricationTimeInDays = fabricationTimeInDays;
        initializeQuantitiesPerBatch();
    }

    abstract void initializeQuantitiesPerBatch();

    public boolean enoughTimeToFabricate(LocalDate orderDate) {
        LocalDate fabricationCompletionDate = orderDate.plusDays(fabricationTimeInDays);

        return fabricationCompletionDate.isBefore(limitDeliveryDate) || fabricationCompletionDate.equals(limitDeliveryDate);
    }

    public int getQuantityToReserve(LocalDate orderDate, int requiredQuantity) {
        int quantityToFabricate = getQuantityToFabricate(requiredQuantity, this.tankFabricationQuantity);

        if (!enoughTimeToFabricate(orderDate)) {
            throw new IllegalArgumentException("Not enough time to reserve oxygen.");
        }

        return quantityToFabricate;
    }

    private int getQuantityToFabricate(int tankRequiredQuantity, int fabricationQuantity) {
        return getQuantityOfFabricationBatchesRequired(tankRequiredQuantity) * fabricationQuantity;
    }

    private int getQuantityOfFabricationBatchesRequired(int requiredQuantity) {
        if (requiredQuantity % tankFabricationQuantity > 0) {
            return requiredQuantity / tankFabricationQuantity + 1;
        }
        return requiredQuantity / tankFabricationQuantity;
    }

    public SortedMap<LocalDate, OxygenDateHistory> updateOxygenHistory(SortedMap<LocalDate, OxygenDateHistory> history, LocalDate orderDate, int requiredQuantity) {
        if (requiredQuantity >= 0) {
            SortedMap<LocalDate, OxygenDateHistory> updatedHistory = updateOrderDateHistory(history, orderDate, requiredQuantity);
            updatedHistory = updateCompletionDateHistory(updatedHistory, orderDate, requiredQuantity);
            return updatedHistory;
        }
        return history;
    }

    private SortedMap<LocalDate, OxygenDateHistory> updateOrderDateHistory(SortedMap<LocalDate, OxygenDateHistory> history, LocalDate orderDate, int orderedQuantity) {
        if (!orderDateQuantitiesPerBatch.isEmpty()) {
            OxygenDateHistory orderDateHistory = getDateHistory(orderDateQuantitiesPerBatch, orderDate, orderedQuantity);
            return updateHistory(history, orderDateHistory);
        }
        return history;
    }

    private SortedMap<LocalDate, OxygenDateHistory> updateCompletionDateHistory(SortedMap<LocalDate, OxygenDateHistory> history, LocalDate orderDate, int orderedQuantity) {
        if (!completionDateQuantitiesPerBatch.isEmpty()) {
            OxygenDateHistory completionDateHistory = getDateHistory(completionDateQuantitiesPerBatch, getFabricationCompletionDate(orderDate), orderedQuantity);
            return updateHistory(history, completionDateHistory);
        }
        return history;
    }

    private OxygenDateHistory getDateHistory(EnumMap<HistoryType, Integer> quantitiesPerBatch, LocalDate date, int orderedQuantity) {
        OxygenDateHistory oxygenDateHistory = new OxygenDateHistory(date);
        quantitiesPerBatch.forEach(
                (historyType, fabricationQuantity) -> oxygenDateHistory.updateQuantity(historyType, getQuantityToFabricate(orderedQuantity, fabricationQuantity))
        );
        return oxygenDateHistory;
    }

    private LocalDate getFabricationCompletionDate(LocalDate orderDate) {
        return orderDate.plusDays(fabricationTimeInDays);
    }

    private SortedMap<LocalDate, OxygenDateHistory> updateHistory(SortedMap<LocalDate, OxygenDateHistory> history, OxygenDateHistory oxygenDateHistory) {
        LocalDate date = oxygenDateHistory.getDate();
        if (history.containsKey(date)) {
            history.get(date).updateQuantities(oxygenDateHistory);
        } else {
            history.put(date, oxygenDateHistory);
        }
        return history;
    }
}
