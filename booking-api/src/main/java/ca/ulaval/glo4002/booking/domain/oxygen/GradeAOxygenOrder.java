package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeAOxygenOrder extends OxygenOrder {

    private static final int CANDLE_FABRICATION_QUANTITY = 15;

    public GradeAOxygenOrder(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
        super(limitDeliveryDate, tankFabricationQuantity, fabricationTimeInDays);
    }

    protected void initializeQuantitiesRequiredPerBatch() {
        quantitiesRequiredPerBatchForOrderDate.put(HistoryType.CANDLES_USED, CANDLE_FABRICATION_QUANTITY);
        quantitiesRequiredPerBatchForCompletionDate.put(HistoryType.OXYGEN_TANK_MADE, tankFabricationQuantity);
    }
}
