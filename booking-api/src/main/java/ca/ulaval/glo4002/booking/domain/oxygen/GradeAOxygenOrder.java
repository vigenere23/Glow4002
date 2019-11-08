package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeAOxygenOrder extends OxygenOrder {

    private static final int CANDLE_FABRICATION_QUANTITY = 15;

    public GradeAOxygenOrder(LocalDate limitDeliveryDate, OxygenInventory oxygenInventory, int tankFabricationQuantity, int fabricationTimeInDays) {
        super(limitDeliveryDate, oxygenInventory, tankFabricationQuantity, fabricationTimeInDays);
    }

    protected void initializeQuantitiesPerBatch() {
        completionDateQuantitiesPerBatch.put(HistoryType.OXYGEN_TANK_MADE, tankFabricationQuantity);
        orderDateQuantitiesPerBatch.put(HistoryType.CANDLES_USED, CANDLE_FABRICATION_QUANTITY);
    }
}
