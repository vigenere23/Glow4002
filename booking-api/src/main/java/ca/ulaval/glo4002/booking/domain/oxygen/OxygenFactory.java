package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class OxygenFactory {
    private LocalDate limitDeliveryDate;

    public OxygenFactory(LocalDate limitDeliveryDate) {
        this.limitDeliveryDate = limitDeliveryDate;
    }

    public Oxygen create(OxygenGrade grade, int totalQuantity, int storageQuantity) {
        switch (grade) {
            case A:
                return new GradeAOxygen(limitDeliveryDate, totalQuantity, storageQuantity);
            case B:
                return new GradeBOxygen(limitDeliveryDate, totalQuantity, storageQuantity);
            case E:
                return new GradeEOxygen(limitDeliveryDate, totalQuantity, storageQuantity);
            default:
                throw new IllegalArgumentException(String.format("No oxygen tanks implemented for grade %s.", grade));
        }
    }
}
