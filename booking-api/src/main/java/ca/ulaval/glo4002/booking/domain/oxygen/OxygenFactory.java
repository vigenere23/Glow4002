package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class OxygenFactory {

    private LocalDate limitDeliveryDate;

    public OxygenFactory(LocalDate limitDeliveryDate) {
        this.limitDeliveryDate = limitDeliveryDate;
    }

    public Oxygen create(OxygenGrade grade, OxygenInventory oxygenInventory) {
        switch (grade) {
            case A:
                return new GradeAOxygen(limitDeliveryDate, oxygenInventory);
            case B:
                return new GradeBOxygen(limitDeliveryDate, oxygenInventory);
            case E:
                return new GradeEOxygen(limitDeliveryDate, oxygenInventory);
            default:
                throw new IllegalArgumentException(String.format("No oxygen tanks implemented for grade %s.", grade));
        }
    }
}
