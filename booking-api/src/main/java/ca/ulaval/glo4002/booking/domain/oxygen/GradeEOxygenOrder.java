package ca.ulaval.glo4002.booking.domain.oxygen;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

import java.time.LocalDate;

public class GradeEOxygenOrder extends OxygenOrder {

    private static final Price UNIT_COST = new Price(5000);

    public GradeEOxygenOrder(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
        super(limitDeliveryDate, tankFabricationQuantity, fabricationTimeInDays);
    }

    @Override
    protected void initializeQuantitiesRequiredPerBatch() {
        quantitiesRequiredPerBatchForOrderDate.put(HistoryType.OXYGEN_TANK_BOUGHT, tankFabricationQuantity);
    }

    @Override
    protected void saveOutcome(OutcomeSaver outcomeSaver) {
        outcomeSaver.saveOutcome(calculateOrderCost());
    }

    private Price calculateOrderCost() {
        return UNIT_COST.multipliedBy(quantityOfBatches);
    }
}
