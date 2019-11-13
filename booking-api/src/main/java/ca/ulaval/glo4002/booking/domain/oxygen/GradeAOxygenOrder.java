package ca.ulaval.glo4002.booking.domain.oxygen;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

import java.time.LocalDate;

public class GradeAOxygenOrder extends OxygenOrder {

    private static final int CANDLE_FABRICATION_QUANTITY = 15;
    private static final Price COST_PER_CANDLE = new Price(650);

    public GradeAOxygenOrder(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
        super(limitDeliveryDate, tankFabricationQuantity, fabricationTimeInDays);
    }

    @Override
    protected void initializeQuantitiesRequiredPerBatch() {
        quantitiesRequiredPerBatchForOrderDate.put(HistoryType.CANDLES_USED, CANDLE_FABRICATION_QUANTITY);
        quantitiesRequiredPerBatchForCompletionDate.put(HistoryType.OXYGEN_TANK_MADE, tankFabricationQuantity);
    }

    @Override
    protected void saveOutcome(OutcomeSaver outcomeSaver) {
        outcomeSaver.saveOutcome(calculateOrderCost());
    }

    private Price calculateOrderCost() {
        return COST_PER_CANDLE.multipliedBy(quantityOfBatches * CANDLE_FABRICATION_QUANTITY);
    }
}
