package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeBOxygen extends Oxygen {

    private static final int WATER_FABRICATION_QUANTITY_IN_LITRE = 8;

    public GradeBOxygen(LocalDate limitDeliveryDate, OxygenInventory oxygenInventory, int tankFabricationQuantity, int fabricationTimeInDays) {
        super(limitDeliveryDate, oxygenInventory, tankFabricationQuantity, fabricationTimeInDays);
    }

    protected void initializeQuantitiesPerBatch() {
        completionDateQuantitiesPerBatch.put(HistoryType.OXYGEN_TANK_MADE, tankFabricationQuantity);
        orderDateQuantitiesPerBatch.put(HistoryType.WATER_USED, WATER_FABRICATION_QUANTITY_IN_LITRE);
    }
}
