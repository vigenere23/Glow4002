package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.*;

public abstract class OxygenOrder {

    protected LocalDate limitDeliveryDate;
    protected int tankFabricationQuantity;
    protected int fabricationTimeInDays;
    protected EnumMap<HistoryType, Integer> orderDateQuantitiesPerBatch = new EnumMap<>(HistoryType.class);
    protected EnumMap<HistoryType, Integer> completionDateQuantitiesPerBatch = new EnumMap<>(HistoryType.class);
    protected OxygenProduction oxygenProduction;

    public OxygenOrder(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
        this.limitDeliveryDate = limitDeliveryDate;
        this.tankFabricationQuantity = tankFabricationQuantity;
        this.fabricationTimeInDays = fabricationTimeInDays;
        initializeQuantitiesPerBatch();
        oxygenProduction = new OxygenProduction(fabricationTimeInDays, tankFabricationQuantity, orderDateQuantitiesPerBatch, completionDateQuantitiesPerBatch);
    }

    abstract void initializeQuantitiesPerBatch();

    public SortedMap<LocalDate, OxygenDateHistory> updateOxygenHistory(SortedMap<LocalDate, OxygenDateHistory> history, LocalDate orderDate, int requiredQuantity) {
        if (requiredQuantity >= 0) {
            return oxygenProduction.updateOxygenHistory(history, orderDate, requiredQuantity);
        }
        return history;
    }

    public boolean enoughTimeToFabricate(LocalDate orderDate) {
        LocalDate fabricationCompletionDate = orderDate.plusDays(fabricationTimeInDays);

        return fabricationCompletionDate.isBefore(limitDeliveryDate) || fabricationCompletionDate.equals(limitDeliveryDate);
    }

    public int getQuantityToReserve(LocalDate orderDate, int requiredQuantity) {
        int quantityToFabricate = oxygenProduction.getQuantityToFabricate(requiredQuantity, this.tankFabricationQuantity);

        if (!enoughTimeToFabricate(orderDate)) {
            throw new IllegalArgumentException("Not enough time to reserve oxygen.");
        }

        return quantityToFabricate;
    }
}
