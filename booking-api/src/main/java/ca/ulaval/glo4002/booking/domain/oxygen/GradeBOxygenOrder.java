package ca.ulaval.glo4002.booking.domain.oxygen;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

import java.time.LocalDate;

public class GradeBOxygenOrder extends OxygenOrder {

    private static final int WATER_FABRICATION_QUANTITY_IN_LITRE = 8;
    private static final Price COST_PER_WATER_LITRE = new Price(600);

    public GradeBOxygenOrder(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
        super(limitDeliveryDate, tankFabricationQuantity, fabricationTimeInDays);
    }

    @Override
    protected void initializeQuantitiesRequiredPerBatch() {
        quantitiesRequiredPerBatchForOrderDate.put(HistoryType.WATER_USED, WATER_FABRICATION_QUANTITY_IN_LITRE);
        quantitiesRequiredPerBatchForCompletionDate.put(HistoryType.OXYGEN_TANK_MADE, tankFabricationQuantity);
    }

    @Override
    protected void saveOutcome(OutcomeSaver outcomeSaver) {
        outcomeSaver.saveOutcome(calculateOrderCost());
    }

    private Price calculateOrderCost() {
        return COST_PER_WATER_LITRE.multipliedBy(quantityOfBatches * WATER_FABRICATION_QUANTITY_IN_LITRE);
    }
}
