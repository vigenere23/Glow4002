package ca.ulaval.glo4002.booking.domain.oxygen;

import ca.ulaval.glo4002.booking.domain.exceptions.NotEnoughTimeException;

import java.time.LocalDate;
import java.util.*;

public abstract class Oxygen {
    protected int tankFabricationQuantity;
    protected int fabricationTimeInDays;
    protected int remainingQuantity;
    protected EnumMap<HistoryType, Integer> quantityPerFabricationBatch;
    protected OxygenProduction oxygenProduction;
    protected OxygenInventory oxygenInventory;

    public Oxygen(LocalDate limitDeliveryDate, int totalQuantity, int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
        initializeQuantityPerFabricationBatch();
        oxygenInventory = new OxygenInventory(totalQuantity, remainingQuantity);
        oxygenProduction = new OxygenProduction(limitDeliveryDate, fabricationTimeInDays, tankFabricationQuantity, quantityPerFabricationBatch);
    }

    protected void initializeQuantityPerFabricationBatch() {
        quantityPerFabricationBatch = new EnumMap<HistoryType, Integer>(HistoryType.class);
        quantityPerFabricationBatch.put(HistoryType.OXYGEN_TANK_BOUGHT, tankFabricationQuantity);
    }

    public int getTotalQuantity() {
        return oxygenInventory.getTotalQuantity();
    }

    public int getRemainingQuantity() {
        return oxygenInventory.getRemainingQuantity();
    }

    public void adjustInventory(LocalDate orderDate, int requirementQuantity) throws NotEnoughTimeException {
        int quantityOfTanksLacking = getQuantityOfTanksLacking(requirementQuantity);
        int quantityToFabricate = getQuantityToFabricate(quantityOfTanksLacking, tankFabricationQuantity);

        if (hasToFabricateMore(quantityToFabricate) && !oxygenProduction.enoughTimeForFabrication(orderDate)) {
            throw new NotEnoughTimeException();
        }
        oxygenInventory.updateInventory(quantityToFabricate, quantityOfTanksLacking);
    }

    public int getQuantityOfTanksLacking(int requirementQuantity) {
        return requirementQuantity - remainingQuantity;
    }

    public SortedMap<LocalDate, OxygenProductionInventory> updateOxygenHistory(SortedMap<LocalDate, OxygenProductionInventory> history, LocalDate orderDate, int requirementQuantity) {
        oxygenProduction.setQuantityOfTanksLacking(getQuantityOfTanksLacking(requirementQuantity));
        oxygenProduction.updateOxygenHistory(history, orderDate, requirementQuantity);
        return history;
    }

    private int getQuantityToFabricate(int quantityOfTanksLacking, int fabricationQuantity) {
        int quantityOfBatchesToFabricate = oxygenProduction.getQuantityOfFabricationBatchesNeeded(quantityOfTanksLacking);
        return quantityOfBatchesToFabricate * fabricationQuantity;
    }

    private boolean hasToFabricateMore(int quantityToFabricate) {
        return quantityToFabricate > 0;
    }
}
