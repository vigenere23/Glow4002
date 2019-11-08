package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.*;

public abstract class OxygenOrder {

    protected LocalDate limitDeliveryDate;
    protected int tankFabricationQuantity;
    protected int fabricationTimeInDays;
    protected int remainingQuantity;
    protected EnumMap<HistoryType, Integer> orderDateQuantitiesPerBatch = new EnumMap<>(HistoryType.class);
    protected EnumMap<HistoryType, Integer> completionDateQuantitiesPerBatch = new EnumMap<>(HistoryType.class);
    protected OxygenProduction oxygenProduction;
    protected OxygenInventory oxygenInventory;

    public OxygenOrder(LocalDate limitDeliveryDate, OxygenInventory oxygenInventory, int tankFabricationQuantity, int fabricationTimeInDays) {
        this.limitDeliveryDate = limitDeliveryDate;
        this.remainingQuantity = oxygenInventory.getRemainingQuantity();
        this.oxygenInventory = oxygenInventory;
        this.tankFabricationQuantity = tankFabricationQuantity;
        this.fabricationTimeInDays = fabricationTimeInDays;
        initializeQuantitiesPerBatch();
        oxygenProduction = new OxygenProduction(fabricationTimeInDays, tankFabricationQuantity, orderDateQuantitiesPerBatch, completionDateQuantitiesPerBatch);
    }

    abstract void initializeQuantitiesPerBatch();

    public OxygenInventory getOxygenInventory() {
        return oxygenInventory;
    }

    public SortedMap<LocalDate, OxygenDateHistory> updateOxygenHistory(SortedMap<LocalDate, OxygenDateHistory> history, LocalDate orderDate, int requirementQuantity) {
        int quantityOfTanksLacking = getQuantityOfTanksLacking(requirementQuantity);
        if (quantityOfTanksLacking >= 0) {
            return oxygenProduction.updateOxygenHistory(history, orderDate, quantityOfTanksLacking);
        }
        return history;
    }

    public boolean isNotEnoughTimeToFabricate(LocalDate orderDate, int quantityToFabricate) {
        return hasToFabricateMore(quantityToFabricate) && !enoughTimeForFabrication(orderDate);
    }

    public OxygenInventory adjustInventory(LocalDate orderDate, int requiredQuantity) {
        int quantityOfTanksLacking = getQuantityOfTanksLacking(requiredQuantity);
        int quantityToFabricate = oxygenProduction.getQuantityToFabricate(quantityOfTanksLacking, this.tankFabricationQuantity);

        if (isNotEnoughTimeToFabricate(orderDate, quantityToFabricate)) {
            throw new IllegalArgumentException("Not enough time to reserve oxygen.");
        }
        oxygenInventory.updateInventory(quantityToFabricate, quantityOfTanksLacking);

        return oxygenInventory;
    }

    private int getQuantityOfTanksLacking(int requiredQuantity) {
        return requiredQuantity - remainingQuantity;
    }

    private boolean hasToFabricateMore(int quantityToFabricate) {
        return quantityToFabricate > 0;
    }

    private boolean enoughTimeForFabrication(LocalDate orderDate) {
        LocalDate fabricationCompletionDate = orderDate.plusDays(fabricationTimeInDays);
        return fabricationCompletionDate.isBefore(limitDeliveryDate) || fabricationCompletionDate.equals(limitDeliveryDate);
    }
}
