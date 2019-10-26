package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeEOxygen extends Oxygen {

    public GradeEOxygen(LocalDate limitDeliveryDate, OxygenInventory oxygenInventory) {
        this.limitDeliveryDate = limitDeliveryDate;
        this.remainingQuantity = oxygenInventory.getRemainingQuantity();
        this.oxygenInventory = oxygenInventory;
        tankFabricationQuantity = 1;
        fabricationTimeInDays = 0;
        initializeQuantitiesPerBatch();
        oxygenProduction = new OxygenProduction(fabricationTimeInDays, tankFabricationQuantity, orderDateQuantityPerBatch, completionDateQuantityPerBatch);
    }

    protected void initializeQuantitiesPerBatch() {
        orderDateQuantityPerBatch.put(HistoryType.OXYGEN_TANK_BOUGHT, tankFabricationQuantity);
    }
}
