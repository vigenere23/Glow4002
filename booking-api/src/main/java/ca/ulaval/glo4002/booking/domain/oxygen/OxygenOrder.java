package ca.ulaval.glo4002.booking.domain.oxygen;

import ca.ulaval.glo4002.booking.domain.Price;

import java.time.LocalDate;
import java.util.*;

public abstract class OxygenOrder {

    protected LocalDate limitDeliveryDate;
    protected int tankFabricationQuantity;
    protected int fabricationTimeInDays;
    protected EnumMap<HistoryType, Integer> quantitiesRequiredPerBatchForOrderDate = new EnumMap<>(HistoryType.class);
    protected EnumMap<HistoryType, Integer> quantitiesRequiredPerBatchForCompletionDate = new EnumMap<>(HistoryType.class);
    protected int quantityOfBatches = 0;
    protected Price cost = Price.zero();

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
        setQuantityOfFabricationBatchesRequired(requiredQuantity);
        int quantityToFabricate = getQuantityToFabricate(this.tankFabricationQuantity);

        if (!isEnoughTimeToFabricate(orderDate)) {
            throw new IllegalArgumentException("Not enough time to reserve oxygen.");
        }

        return quantityToFabricate;
    }

    private int getQuantityToFabricate(int fabricationQuantity) {
        return quantityOfBatches * fabricationQuantity;
    }

    private void setQuantityOfFabricationBatchesRequired(int requiredQuantity) {
        quantityOfBatches = requiredQuantity % tankFabricationQuantity > 0 ? requiredQuantity / tankFabricationQuantity + 1 : requiredQuantity / tankFabricationQuantity;
    }

    public SortedMap<LocalDate, OxygenHistoryItem> getOxygenOrderHistory(LocalDate orderDate) {
        SortedMap<LocalDate, OxygenHistoryItem> oxygenOrderHistory = new TreeMap<>();
        return quantityOfBatches >= 0 ? updateOxygenOrderHistory(oxygenOrderHistory, orderDate) : oxygenOrderHistory;
    }

    private SortedMap<LocalDate, OxygenHistoryItem> updateOxygenOrderHistory(SortedMap<LocalDate, OxygenHistoryItem> oxygenOrderHistory, LocalDate orderDate) {
        LocalDate completionDate = getFabricationCompletionDate(orderDate);
        oxygenOrderHistory.put(orderDate, getOrderDateHistory(orderDate));
        oxygenOrderHistory.put(completionDate, getCompletionDateHistory(completionDate));
        return oxygenOrderHistory;
    }

    private LocalDate getFabricationCompletionDate(LocalDate orderDate) {
        return orderDate.plusDays(fabricationTimeInDays);
    }

    private OxygenHistoryItem getOrderDateHistory(LocalDate orderDate) {
        return !quantitiesRequiredPerBatchForOrderDate.isEmpty() ? generateHistoryItem(orderDate, quantitiesRequiredPerBatchForOrderDate) : new OxygenHistoryItem(orderDate);
    }

    private OxygenHistoryItem getCompletionDateHistory(LocalDate completionDate) {
        return !quantitiesRequiredPerBatchForCompletionDate.isEmpty() ? generateHistoryItem(completionDate, quantitiesRequiredPerBatchForCompletionDate) : new OxygenHistoryItem(completionDate);
    }

    private OxygenHistoryItem generateHistoryItem(LocalDate date, EnumMap<HistoryType, Integer> quantitiesRequiredPerBatch) {
        OxygenHistoryItem oxygenHistoryItem = new OxygenHistoryItem(date);
        quantitiesRequiredPerBatch.forEach(
                (historyType, fabricationQuantity) -> oxygenHistoryItem.updateQuantity(historyType, getQuantityToFabricate(fabricationQuantity))
        );
        return oxygenHistoryItem;
    }

    abstract Price getOrderCost();
}
