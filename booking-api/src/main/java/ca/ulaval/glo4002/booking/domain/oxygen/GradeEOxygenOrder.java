package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeEOxygenOrder extends OxygenOrder {

    public GradeEOxygenOrder(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
        super(limitDeliveryDate, tankFabricationQuantity, fabricationTimeInDays);
    }

    protected void initializeQuantitiesRequiredPerBatch() {
        quantitiesRequiredPerBatchForOrderDate.put(HistoryType.OXYGEN_TANK_BOUGHT, tankFabricationQuantity);
    }
}
