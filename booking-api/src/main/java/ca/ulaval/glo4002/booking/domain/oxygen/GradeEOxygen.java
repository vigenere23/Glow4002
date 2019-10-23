package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class GradeEOxygen extends Oxygen {
    private static final int gradeEFabricationQuantity = 1;
    private static final int gradeEFabricationTimeInDays = 0;

    public GradeEOxygen(LocalDate limitDeliveryDate, int totalQuantity, int storageQuantity) {
        super(limitDeliveryDate, totalQuantity, storageQuantity);
        tankFabricationQuantity = gradeEFabricationQuantity;
        fabricationTimeInDays = gradeEFabricationTimeInDays;
        initializeQuantityPerFabricationBatch();
    }
}
