package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.*;

public abstract class Oxygen {

    protected LocalDate limitDeliveryDate;
    protected int tankFabricationQuantity;
    protected int fabricationTimeInDays;
    protected int remainingQuantity;
    protected EnumMap<HistoryType, Integer> orderDateQuantityPerBatch = new EnumMap<>(HistoryType.class);
    protected EnumMap<HistoryType, Integer> completionDateQuantityPerBatch = new EnumMap<>(HistoryType.class);
    protected OxygenProduction oxygenProduction;
    protected OxygenInventory oxygenInventory;

    public Oxygen(LocalDate limitDeliveryDate, OxygenInventory oxygenInventory, int tankFabricationQuantity, int fabricationTimeInDays) {
        this.limitDeliveryDate = limitDeliveryDate;
        this.remainingQuantity = oxygenInventory.getRemainingQuantity();
        this.oxygenInventory = oxygenInventory;
        this.tankFabricationQuantity = tankFabricationQuantity;
        this.fabricationTimeInDays = fabricationTimeInDays;
        initializeQuantitiesPerBatch();
        oxygenProduction = new OxygenProduction(fabricationTimeInDays, tankFabricationQuantity, orderDateQuantityPerBatch, completionDateQuantityPerBatch);
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

    public boolean adjustInventory(LocalDate orderDate, int requirementQuantity) {
        int quantityOfTanksLacking = getQuantityOfTanksLacking(requirementQuantity);
        int quantityToFabricate = oxygenProduction.getQuantityToFabricate(quantityOfTanksLacking, this.tankFabricationQuantity);

        if (hasToFabricateMore(quantityToFabricate) && !enoughTimeForFabrication(orderDate)) {
            return false;
        }
        oxygenInventory.updateInventory(quantityToFabricate, quantityOfTanksLacking);

        return true;
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
