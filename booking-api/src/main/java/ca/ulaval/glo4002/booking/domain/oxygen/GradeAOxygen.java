package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeAOxygen extends Oxygen {

    private static final int candleFabricationQuantity = 15;

    public GradeAOxygen(LocalDate limitDeliveryDate, OxygenInventory oxygenInventory) {
        this.limitDeliveryDate = limitDeliveryDate;
        this.remainingQuantity = oxygenInventory.getRemainingQuantity();
        this.oxygenInventory = oxygenInventory;
        tankFabricationQuantity = 5;
        fabricationTimeInDays = 20;
        initializeQuantitiesPerBatch();
        oxygenProduction = new OxygenProduction(fabricationTimeInDays, tankFabricationQuantity, orderDateQuantityPerBatch, completionDateQuantityPerBatch);
    }

    protected void initializeQuantitiesPerBatch() {
        completionDateQuantityPerBatch.put(HistoryType.OXYGEN_TANK_MADE, tankFabricationQuantity);
        orderDateQuantityPerBatch.put(HistoryType.CANDLES_USED, candleFabricationQuantity);
    }
}
