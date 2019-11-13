package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeBOxygenOrder extends OxygenOrder {

    private static final int WATER_FABRICATION_QUANTITY_IN_LITRE = 8;

    public GradeBOxygenOrder(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
        super(limitDeliveryDate, tankFabricationQuantity, fabricationTimeInDays);
    }

    protected void initializeQuantitiesRequiredPerBatch() {
        quantitiesRequiredPerBatchForOrderDate.put(HistoryType.WATER_USED, WATER_FABRICATION_QUANTITY_IN_LITRE);
        quantitiesRequiredPerBatchForCompletionDate.put(HistoryType.OXYGEN_TANK_MADE, tankFabricationQuantity);
    }
}
