package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeBOxygen extends Oxygen {
    private static final OxygenGrade oxygenGrade = OxygenGrade.B;
    private static final int gradeBFabricationQuantity = 3;
    private static final int gradeBFabricationTimeInDays = 10;
    private static final int waterFabricationQuantityInLitre = 8;

    public GradeBOxygen(LocalDate limitDeliveryDate, int totalQuantity, int storageQuantity) {
        super(limitDeliveryDate, totalQuantity, storageQuantity);
        tankFabricationQuantity = gradeBFabricationQuantity;
        fabricationTimeInDays = gradeBFabricationTimeInDays;
        initializeQuantityPerFabricationBatch();
    }

    protected void initializeQuantityPerFabricationBatch() {
        super.initializeQuantityPerFabricationBatch();
        quantityPerFabricationBatch.put(HistoryType.WATER_USED, waterFabricationQuantityInLitre);
    }
}
