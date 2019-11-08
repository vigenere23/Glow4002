package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;

public class OxygenOrderFactory {
    private static final int TANK_FABRICATION_QUANTITY_GRADE_A = 5;
    private static final int TANK_FABRICATION_QUANTITY_GRADE_B = 3;
    private static final int TANK_FABRICATION_QUANTITY_GRADE_E = 1;
    private static final int FABRICATION_TIME_IN_DAYS_GRADE_A = 20;
    private static final int FABRICATION_TIME_IN_DAYS_GRADE_B = 10;
    private static final int FABRICATION_TIME_IN_DAYS_GRADE_E = 0;
    private LocalDate limitDeliveryDate;

    public OxygenOrderFactory(LocalDate limitDeliveryDate) {
        this.limitDeliveryDate = limitDeliveryDate;
    }

    public OxygenOrder create(OxygenGrade grade, OxygenInventory oxygenInventory) {
        switch (grade) {
            case A:
                return new GradeAOxygenOrder(limitDeliveryDate, oxygenInventory, TANK_FABRICATION_QUANTITY_GRADE_A, FABRICATION_TIME_IN_DAYS_GRADE_A);
            case B:
                return new GradeBOxygenOrder(limitDeliveryDate, oxygenInventory, TANK_FABRICATION_QUANTITY_GRADE_B, FABRICATION_TIME_IN_DAYS_GRADE_B);
            case E:
                return new GradeEOxygenOrder(limitDeliveryDate, oxygenInventory, TANK_FABRICATION_QUANTITY_GRADE_E, FABRICATION_TIME_IN_DAYS_GRADE_E);
            default:
                throw new IllegalArgumentException(String.format("No oxygen tanks implemented for grade %s.", grade));
        }
    }
}
