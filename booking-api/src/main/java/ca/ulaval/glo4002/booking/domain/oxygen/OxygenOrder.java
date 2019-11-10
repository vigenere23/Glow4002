package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.*;

public abstract class OxygenOrder {

    protected LocalDate limitDeliveryDate;
    protected int tankFabricationQuantity;
    protected int fabricationTimeInDays;
    protected EnumMap<HistoryType, Integer> quantitiesRequiredPerBatchForOrderDate = new EnumMap<>(HistoryType.class);
    protected EnumMap<HistoryType, Integer> quantitiesRequiredPerBatchForCompletionDate = new EnumMap<>(HistoryType.class);

    public OxygenOrder(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
        this.limitDeliveryDate = limitDeliveryDate;
        this.tankFabricationQuantity = tankFabricationQuantity;
        this.fabricationTimeInDays = fabricationTimeInDays;
        initializeQuantitiesRequiredPerBatch();
    }

    abstract void initializeQuantitiesRequiredPerBatch();

    public boolean isEnoughTimeToFabricate(LocalDate orderDate) {
        LocalDate fabricationCompletionDate = orderDate.plusDays(fabricationTimeInDays);

        return fabricationCompletionDate.isBefore(limitDeliveryDate) || fabricationCompletionDate.equals(limitDeliveryDate);
    }

    public int getQuantityToReserve(LocalDate orderDate, int requiredQuantity) {
        int quantityToFabricate = getQuantityToFabricate(requiredQuantity, this.tankFabricationQuantity);

        if (!isEnoughTimeToFabricate(orderDate)) {
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

    public SortedMap<LocalDate, OxygenHistoryItem> getOxygenOrderHistory(LocalDate orderDate, int requiredQuantity) {
        SortedMap<LocalDate, OxygenHistoryItem> oxygenOrderHistory = new TreeMap<>();
        if (requiredQuantity >= 0) {
            LocalDate completionDate = getFabricationCompletionDate(orderDate);
            oxygenOrderHistory.put(orderDate, getOrderDateHistory(orderDate, requiredQuantity));
            oxygenOrderHistory.put(completionDate, getCompletionDateHistory(completionDate, requiredQuantity));
        }
        return oxygenOrderHistory;
    }

    private LocalDate getFabricationCompletionDate(LocalDate orderDate) {
        return orderDate.plusDays(fabricationTimeInDays);
    }

    private OxygenHistoryItem getOrderDateHistory(LocalDate orderDate, int orderedQuantity) {
        if (!quantitiesRequiredPerBatchForOrderDate.isEmpty()) {
            return generateHistoryItem(orderDate, orderedQuantity, quantitiesRequiredPerBatchForOrderDate);
        }
        return new OxygenHistoryItem(orderDate);
    }

    private OxygenHistoryItem getCompletionDateHistory(LocalDate completionDate, int orderedQuantity) {
        if (!quantitiesRequiredPerBatchForCompletionDate.isEmpty()) {
            return generateHistoryItem(completionDate, orderedQuantity, quantitiesRequiredPerBatchForCompletionDate);
        }
        return new OxygenHistoryItem(completionDate);
    }

    private OxygenHistoryItem generateHistoryItem(LocalDate date, int orderedQuantity, EnumMap<HistoryType, Integer> quantitiesRequiredPerBatch) {
        OxygenHistoryItem oxygenHistoryItem = new OxygenHistoryItem(date);
        quantitiesRequiredPerBatch.forEach(
                (historyType, fabricationQuantity) -> oxygenHistoryItem.updateQuantity(historyType, getQuantityToFabricate(orderedQuantity, fabricationQuantity))
        );
        return oxygenHistoryItem;
    }
}
