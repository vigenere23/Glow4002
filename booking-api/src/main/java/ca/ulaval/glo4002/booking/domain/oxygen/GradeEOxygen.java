package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeEOxygen extends Oxygen {

    public GradeEOxygen(LocalDate limitDeliveryDate, OxygenInventory oxygenInventory, int tankFabricationQuantity, int fabricationTimeInDays) {
        super(limitDeliveryDate, oxygenInventory, tankFabricationQuantity, fabricationTimeInDays);
    }

    protected void initializeQuantitiesPerBatch() {
        orderDateQuantityPerBatch.put(HistoryType.OXYGEN_TANK_BOUGHT, tankFabricationQuantity);
    }
}
