package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.util.EnumMap;

public class OxygenFactory implements OxygenReportable {
	private int defaultGradeERequirement;
	private int defaultGradeARequirement;
	private int defaultGradeBRequirement;
	private EnumMap<OxygenGrade, Integer> oxygenInventory;
	
	public OxygenFactory() {
		oxygenInventory = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
		defaultGradeERequirement = 1;
		defaultGradeARequirement = 1;
		defaultGradeBRequirement = 1;
	}    
	
    public void orderTemplatedOxygenQuantity(OxygenGrade grade) {
    	if(oxygenInventory.containsKey(grade)) {
    	    oxygenInventory.put(grade, oxygenInventory.get(grade) + getRequirementQuantity(grade));
    	} else {
    		 oxygenInventory.put(grade, getRequirementQuantity(grade));
    	}   	
    }
    
    public void setTemplatedOxygenOrder(EnumMap<OxygenGrade, Integer> template) {
    	defaultGradeARequirement = template.containsKey(OxygenGrade.A) ? template.get(OxygenGrade.A) : 1;
    	defaultGradeERequirement = template.containsKey(OxygenGrade.E) ? template.get(OxygenGrade.E) : 1;
    	defaultGradeBRequirement = template.containsKey(OxygenGrade.B) ? template.get(OxygenGrade.B) : 1;
	}
    
    @Override
	public int getInventory(OxygenGrade oxygenGrade) {
		return oxygenInventory.get(oxygenGrade);
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
			 throw new IllegalArgumentException(
					    String.format("No oxygen requirement impemented for grade %s.", grade));
		}
    }
}
