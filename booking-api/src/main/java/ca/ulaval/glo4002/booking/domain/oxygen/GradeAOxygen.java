package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeAOxygen extends Oxygen {

    private static final int CANDLE_FABRICATION_QUANTITY = 15;

    public GradeAOxygen(LocalDate limitDeliveryDate, OxygenInventory oxygenInventory, int tankFabricationQuantity, int fabricationTimeInDays) {
        super(limitDeliveryDate, oxygenInventory, tankFabricationQuantity, fabricationTimeInDays);
    }

    protected void initializeQuantitiesPerBatch() {
        completionDateQuantityPerBatch.put(HistoryType.OXYGEN_TANK_MADE, tankFabricationQuantity);
        orderDateQuantityPerBatch.put(HistoryType.CANDLES_USED, CANDLE_FABRICATION_QUANTITY);
    }
}
