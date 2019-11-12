package ca.ulaval.glo4002.booking.domain.oxygen;

import ca.ulaval.glo4002.booking.domain.Price;

import java.time.LocalDate;

public class GradeEOxygenOrder extends OxygenOrder {

    private static final Price UNITY_COST = new Price(5000);

    public GradeEOxygenOrder(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
        super(limitDeliveryDate, tankFabricationQuantity, fabricationTimeInDays);
    }

    @Override
    protected void initializeQuantitiesRequiredPerBatch() {
        quantitiesRequiredPerBatchForOrderDate.put(HistoryType.OXYGEN_TANK_BOUGHT, tankFabricationQuantity);
    }

    @Override
    Price getOrderCost() {
        return UNITY_COST.multipliedBy(quantityOfBatches);
    }
}
