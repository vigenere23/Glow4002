package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.time.LocalDate;
import java.util.EnumMap;

public class OxygenRequester implements OxygenReportable {
    private static final int gradeAFabricationQuantity = 5;
    private static final int gradeBFabricationQuantity = 3;
    private static final int gradeEFabricationQuantity = 1;
    private static final int gradeAFabricationTimeInDays = 20;
    private static final int gradeBFabricationTimeInDays = 10;
    private static final int gradeEFabricationTimeInDays = 0;
    private EnumMap<OxygenGrade, Integer> defaultGradeRequirements;
    private EnumMap<OxygenGrade, OxygenProducer> oxygenInventory;

    public OxygenRequester(LocalDate limitDeliveryDate) {
	initializeDefaultRequirements();
	oxygenInventory = new EnumMap<OxygenGrade, OxygenProducer>(OxygenGrade.class);
	initializeOxygenInventoryGradeA(limitDeliveryDate);
	initializeOxygenInventoryGradeB(limitDeliveryDate);
	initializeOxygenInventoryGradeE(limitDeliveryDate);
    }

    public void orderTemplatedOxygenQuantity(LocalDate orderDate, OxygenGrade grade) {
	if (!oxygenInventory.containsKey(grade)) {
	    throw new IllegalArgumentException(String.format("Not possible to order oxygen of grade %s.", grade));
	}
	adjustOxygenTankInventory(orderDate, grade);
    }

    public void setTemplatedOxygenOrder(OxygenGrade gradeToProduce, int quantity) {
	defaultGradeRequirements.put(gradeToProduce, quantity);
    }

    @Override
    public int getInventory(OxygenGrade grade) {
	    return oxygenInventory.get(grade).getTotalQuantity();
    }

    private void initializeDefaultRequirements() {
	defaultGradeRequirements = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	for (OxygenGrade grade : OxygenGrade.values()) {
	    defaultGradeRequirements.put(grade, 1);
	}
    }

    private void initializeOxygenInventoryGradeA(LocalDate limitDeliveryDate) {
	OxygenProducer oxygenTank = new OxygenProducer(gradeAFabricationQuantity, gradeAFabricationTimeInDays,
		limitDeliveryDate);
	oxygenInventory.put(OxygenGrade.A, oxygenTank);
    }

    private void initializeOxygenInventoryGradeB(LocalDate limitDeliveryDate) {
	OxygenProducer oxygenTank = new OxygenProducer(gradeBFabricationQuantity, gradeBFabricationTimeInDays,
		limitDeliveryDate);
	oxygenInventory.put(OxygenGrade.B, oxygenTank);
    }

    private void initializeOxygenInventoryGradeE(LocalDate limitDeliveryDate) {
	OxygenProducer oxygenTank = new OxygenProducer(gradeEFabricationQuantity, gradeEFabricationTimeInDays,
		limitDeliveryDate);
	oxygenInventory.put(OxygenGrade.E, oxygenTank);
    }

    private void adjustOxygenTankInventory(LocalDate orderDate, OxygenGrade grade) {
	try {
	    oxygenInventory.get(grade).adjustInventory(orderDate, defaultGradeRequirements.get(grade));
	} catch (NotEnoughTimeException e) {
	    orderTemplatedOxygenQuantity(orderDate, getLowerGradeOf(grade));
	}
    }

    private OxygenGrade getLowerGradeOf(OxygenGrade grade) {
	switch (grade) {
	case A:
	    return OxygenGrade.B;
	case B:
	    return OxygenGrade.E;
	default:
	    throw new IllegalArgumentException(String.format("No lower oxygen grade exists for grade %s.", grade));
	}
    }
}
