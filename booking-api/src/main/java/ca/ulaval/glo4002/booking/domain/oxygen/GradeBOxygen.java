package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeBOxygen extends Oxygen {

    private static final int waterFabricationQuantityInLitre = 8;

    public GradeBOxygen(LocalDate limitDeliveryDate, OxygenInventory oxygenInventory) {
        this.limitDeliveryDate = limitDeliveryDate;
        this.remainingQuantity = oxygenInventory.getRemainingQuantity();
        this.oxygenInventory = oxygenInventory;
        tankFabricationQuantity = 3;
        fabricationTimeInDays = 10;
        initializeQuantitiesPerBatch();
        oxygenProduction = new OxygenProduction(fabricationTimeInDays, tankFabricationQuantity, orderDateQuantityPerBatch, completionDateQuantityPerBatch);
    }

    protected void initializeQuantitiesPerBatch() {
        completionDateQuantityPerBatch.put(HistoryType.OXYGEN_TANK_MADE, tankFabricationQuantity);
        orderDateQuantityPerBatch.put(HistoryType.WATER_USED, waterFabricationQuantityInLitre);
    }
}
