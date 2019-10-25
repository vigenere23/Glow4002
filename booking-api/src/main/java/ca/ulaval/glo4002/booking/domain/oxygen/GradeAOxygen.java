package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeAOxygen extends Oxygen {
    private static final OxygenGrade oxygenGrade = OxygenGrade.A;
    private static final int gradeAFabricationQuantity = 5;
    private static final int gradeAFabricationTimeInDays = 20;
    private static final int candleFabricationQuantity = 15;

    public GradeAOxygen(LocalDate limitDeliveryDate, int totalQuantity, int storageQuantity) {
        super(limitDeliveryDate, totalQuantity, storageQuantity);
        tankFabricationQuantity = gradeAFabricationQuantity;
        fabricationTimeInDays = gradeAFabricationTimeInDays;
        initializeQuantityPerFabricationBatch();
    }

    protected void initializeQuantityPerFabricationBatch() {
        super.initializeQuantityPerFabricationBatch();
        quantityPerFabricationBatch.put(HistoryType.CANDLES_USED, candleFabricationQuantity);
    }
}
