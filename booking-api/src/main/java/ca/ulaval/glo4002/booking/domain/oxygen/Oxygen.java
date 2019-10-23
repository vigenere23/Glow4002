package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.*;

public abstract class Oxygen {
    protected int tankFabricationQuantity;
    protected int fabricationTimeInDays;
    protected int totalQuantity;
    protected int remainingQuantity;
    protected EnumMap<HistoryType, Integer> quantityPerFabricationBatch;
    protected OxygenProduction oxygenProduction;

    public Oxygen(LocalDate limitDeliveryDate, int totalQuantity, int storageQuantity) {
        this.totalQuantity = totalQuantity;
        this.remainingQuantity = storageQuantity;
        oxygenProduction = new OxygenProduction(limitDeliveryDate, fabricationTimeInDays, tankFabricationQuantity);
        initializeQuantityPerFabricationBatch();
    }

    public void adjustInventory(LocalDate orderDate, int requirementQuantity) throws NotEnoughTimeException {
        int quantityOfTanksLacking = getQuantityOfTanksLacking(requirementQuantity);
        int quantityToFabricate = getQuantityToFabricate(quantityOfTanksLacking, tankFabricationQuantity);
        if (hasToFabricateMore(quantityToFabricate) && !oxygenProduction.enoughTimeForFabrication(orderDate)) {
            throw new NotEnoughTimeException();
        }
        updateInventory(quantityToFabricate, quantityOfTanksLacking);
    }

    public int getQuantityOfTanksLacking(int requirementQuantity) {
        return requirementQuantity - remainingQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public SortedMap<LocalDate, OxygenProductionInventory> updateOxygenHistory(SortedMap<LocalDate, OxygenProductionInventory> history, LocalDate orderDate, int requirementQuantity) {
        OxygenProductionInventory orderDateInventory = getOrderDateProductionInventory(orderDate, requirementQuantity);
        OxygenProductionInventory completionDateInventory = getCompletionDateProductionInventory(orderDate, requirementQuantity);
        oxygenProduction.updateOxygenHistory(history, orderDateInventory, completionDateInventory);
        return history;
    }

    public OxygenProductionInventory getCompletionDateProductionInventory(LocalDate orderDate, int requirementQuantity) {
        OxygenProductionInventory oxygenProductionInventory = new OxygenProductionInventory(oxygenProduction.getFabricationCompletionDate(orderDate));
        oxygenProductionInventory.updateQuantity(HistoryType.OXYGEN_TANK_MADE, getQuantityToFabricate(requirementQuantity));
        return oxygenProductionInventory;
    }

    public OxygenProductionInventory getOrderDateProductionInventory(LocalDate orderDate, int requirementQuantity) {
        OxygenProductionInventory oxygenProductionInventory = new OxygenProductionInventory(orderDate);
        int quantityOfTanksLacking = getQuantityOfTanksLacking(requirementQuantity);
        quantityPerFabricationBatch.forEach(
                (historyType, fabricationQuantity) -> oxygenProductionInventory.updateQuantity(historyType, getQuantityToFabricate(quantityOfTanksLacking, fabricationQuantity))
        );
        return oxygenProductionInventory;
    }

    protected void initializeQuantityPerFabricationBatch() {
        quantityPerFabricationBatch = new EnumMap<HistoryType, Integer>(HistoryType.class);
        quantityPerFabricationBatch.put(HistoryType.OXYGEN_TANK_BOUGHT, tankFabricationQuantity);
    }

    private void updateInventory(int quantityToFabricate, int quantityOfTanksLacking) {
        remainingQuantity = quantityToFabricate - quantityOfTanksLacking;
        totalQuantity += quantityToFabricate;
    }

    private boolean hasToFabricateMore(int quantityToFabricate) {
        return quantityToFabricate > 0;
    }

    private int getQuantityToFabricate(int requirementQuantity) {
        int quantityOfTanksLacking = getQuantityOfTanksLacking(requirementQuantity);
        return oxygenProduction.getQuantityOfFabricationBatchesNeeded(quantityOfTanksLacking) * tankFabricationQuantity;
    }

    private int getQuantityToFabricate(int quantityOfTanksLacking, int fabricationQuantity) {
        int quantityOfBatchesToFabricate = oxygenProduction.getQuantityOfFabricationBatchesNeeded(quantityOfTanksLacking);
        return quantityOfBatchesToFabricate * fabricationQuantity;
    }
}
