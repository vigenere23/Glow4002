package ca.ulaval.glo4002.booking.domain.oxygen;

import ca.ulaval.glo4002.booking.domain.exceptions.NotEnoughTimeException;

import java.time.LocalDate;
import java.util.*;

public abstract class Oxygen {
    protected OxygenGrade oxygenGrade;
    private LocalDate limitDeliveryDate;
    protected int tankFabricationQuantity;
    protected int fabricationTimeInDays;
    protected int remainingQuantity;
    protected EnumMap<HistoryType, Integer> quantityPerFabricationBatch;
    protected OxygenProduction oxygenProduction;
    protected OxygenInventory oxygenInventory;

    public Oxygen(LocalDate limitDeliveryDate, int totalQuantity, int remainingQuantity) {
        this.limitDeliveryDate = limitDeliveryDate;
        this.remainingQuantity = remainingQuantity;
        initializeQuantityPerFabricationBatch();
        oxygenInventory = new OxygenInventory(oxygenGrade, totalQuantity, remainingQuantity);
        oxygenProduction = new OxygenProduction(fabricationTimeInDays, tankFabricationQuantity, quantityPerFabricationBatch);
    }

    protected void initializeQuantityPerFabricationBatch() {
        quantityPerFabricationBatch = new EnumMap<HistoryType, Integer>(HistoryType.class);
        quantityPerFabricationBatch.put(HistoryType.OXYGEN_TANK_BOUGHT, tankFabricationQuantity);
    }

    public OxygenInventory getOxygenInventory() {
        return oxygenInventory;
    }

    public void adjustInventory(LocalDate orderDate, int requirementQuantity) throws NotEnoughTimeException {
        int quantityOfTanksLacking = getQuantityOfTanksLacking(requirementQuantity);
        int quantityToFabricate = oxygenProduction.getQuantityToFabricate(quantityOfTanksLacking, tankFabricationQuantity);

        if (hasToFabricateMore(quantityToFabricate) && !enoughTimeForFabrication(orderDate)) {
            throw new NotEnoughTimeException();
        }
        oxygenInventory.updateInventory(quantityToFabricate, quantityOfTanksLacking);
    }

    private int getQuantityOfTanksLacking(int requirementQuantity) {
        return requirementQuantity - remainingQuantity;
    }

    private boolean hasToFabricateMore(int quantityToFabricate) {
        return quantityToFabricate > 0;
    }

    public boolean enoughTimeForFabrication(LocalDate orderDate) {
        LocalDate fabricationCompletionDate = orderDate.plusDays(fabricationTimeInDays);
        return fabricationCompletionDate.isBefore(limitDeliveryDate) || fabricationCompletionDate.equals(limitDeliveryDate);
    }

    public SortedMap<LocalDate, OxygenDateHistory> updateOxygenHistory(SortedMap<LocalDate, OxygenDateHistory> history, LocalDate orderDate, int requirementQuantity) {
        oxygenProduction.updateOxygenHistory(history, orderDate, getQuantityOfTanksLacking(requirementQuantity));
        return history;
    }
}
