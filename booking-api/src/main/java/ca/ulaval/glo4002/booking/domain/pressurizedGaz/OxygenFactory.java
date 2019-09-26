package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.time.LocalDate;
import java.util.EnumMap;

public class OxygenFactory implements OxygenReportable {
    private static final int gradeAFabricationQuantity = 5;
    private static final int gradeBFabricationQuantity = 3;
    private static final int gradeEFabricationQuantity = 1;
    private static final int gradeAFabricationTimeInDays = 20;
    private static final int gradeBFabricationTimeInDays = 10;
    private static final int gradeEFabricationTimeInDays = 1;
    private int defaultGradeERequirement;
    private int defaultGradeARequirement;
    private int defaultGradeBRequirement;
    private EnumMap<OxygenGrade, Integer> fabricationQuantities;
    private EnumMap<OxygenGrade, Integer> fabricationTimesInDays;
    private EnumMap<OxygenGrade, OxygenTanks> oxygenInventory;
    private LocalDate limitDeliveryDate;

    public OxygenFactory(LocalDate limitDeliveryDate) {
	this.limitDeliveryDate = limitDeliveryDate;
	defaultGradeERequirement = 1;
	defaultGradeARequirement = 1;
	defaultGradeBRequirement = 1;
	initializeFabricationQuantities();
	initializeFabricationTimesInDays();
	initializeOxygenInventory();
    }

    public void orderTemplatedOxygenQuantity(LocalDate orderDate, OxygenGrade grade) {
	if (oxygenInventory.containsKey(grade)) {
	    adjustOxygenTankInventory(orderDate, grade);
	} else {
	    throw new IllegalArgumentException(String.format("Not possible to order oxygen of grade %s.", grade));
	}
    }

    public void setTemplatedOxygenOrder(EnumMap<OxygenGrade, Integer> template) {
	defaultGradeARequirement = template.containsKey(OxygenGrade.A) ? template.get(OxygenGrade.A) : 1;
	defaultGradeERequirement = template.containsKey(OxygenGrade.E) ? template.get(OxygenGrade.E) : 1;
	defaultGradeBRequirement = template.containsKey(OxygenGrade.B) ? template.get(OxygenGrade.B) : 1;
    }

    @Override
    public int getInventory(OxygenGrade grade) {
	return oxygenInventory.get(grade).getTotalQuantity();
    }

    private void initializeFabricationQuantities() {
	fabricationQuantities = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	fabricationQuantities.put(OxygenGrade.A, gradeAFabricationQuantity);
	fabricationQuantities.put(OxygenGrade.B, gradeBFabricationQuantity);
	fabricationQuantities.put(OxygenGrade.E, gradeEFabricationQuantity);
    }

    private void initializeFabricationTimesInDays() {
	fabricationTimesInDays = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	fabricationTimesInDays.put(OxygenGrade.A, gradeAFabricationTimeInDays);
	fabricationTimesInDays.put(OxygenGrade.B, gradeBFabricationTimeInDays);
	fabricationTimesInDays.put(OxygenGrade.E, gradeEFabricationTimeInDays);
    }

    private void initializeOxygenInventory() {
	oxygenInventory = new EnumMap<OxygenGrade, OxygenTanks>(OxygenGrade.class);
	for (OxygenGrade grade : OxygenGrade.values()) {
	    OxygenTanks oxygenTank = new OxygenTanks(fabricationQuantities.get(grade),
		    fabricationTimesInDays.get(grade));
	    oxygenInventory.put(grade, oxygenTank);
	}
    }

    private void adjustOxygenTankInventory(LocalDate orderDate, OxygenGrade grade) {
	OxygenTanks oxygenTanks = oxygenInventory.get(grade);
	if (oxygenTanks.isEnoughTimeForFabrication(orderDate, limitDeliveryDate)) {
	    oxygenInventory.get(grade).adjustInventory(getRequirementQuantity(grade));
	} else {
	    orderTemplatedOxygenQuantity(orderDate, getLowerGradeOf(grade));
	}
    }

    private int getRequirementQuantity(OxygenGrade grade) {
	switch (grade) {
	case A:
	    return defaultGradeARequirement;
	case B:
	    return defaultGradeBRequirement;
	case E:
	    return defaultGradeERequirement;
	default:
	    throw new IllegalArgumentException(String.format("No oxygen requirement implemented for grade %s.", grade));
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
