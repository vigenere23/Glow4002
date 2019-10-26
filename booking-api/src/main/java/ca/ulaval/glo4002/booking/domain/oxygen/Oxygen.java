package ca.ulaval.glo4002.booking.domain.oxygen;

import ca.ulaval.glo4002.booking.domain.exceptions.NotEnoughTimeException;

import java.time.LocalDate;
import java.util.*;

public abstract class Oxygen {

    protected LocalDate limitDeliveryDate;
    protected int tankFabricationQuantity;
    protected int fabricationTimeInDays;
    protected int remainingQuantity;
    protected EnumMap<HistoryType, Integer> orderDateQuantityPerBatch = new EnumMap<HistoryType, Integer>(HistoryType.class);
    protected EnumMap<HistoryType, Integer> completionDateQuantityPerBatch = new EnumMap<HistoryType, Integer>(HistoryType.class);
    protected OxygenProduction oxygenProduction;
    protected OxygenInventory oxygenInventory;

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

    public OxygenInventory adjustInventory(LocalDate orderDate, int requirementQuantity) throws NotEnoughTimeException {
        int quantityOfTanksLacking = getQuantityOfTanksLacking(requirementQuantity);
        int quantityToFabricate = oxygenProduction.getQuantityToFabricate(quantityOfTanksLacking, this.tankFabricationQuantity);

        if (hasToFabricateMore(quantityToFabricate) && !enoughTimeForFabrication(orderDate)) {
            throw new NotEnoughTimeException();
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
