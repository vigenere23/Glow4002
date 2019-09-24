package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.time.LocalDate;
import java.util.EnumMap;

public class OxygenFactory implements OxygenReportable {
	private int defaultGradeEProduction;
	private int defaultGradeAProduction;
	private int defaultGradeBProduction;
	private LocalDate lastDateOfProduction;
	private EnumMap<OxygenGrade, Integer> oxygenInventory;
	
	public OxygenFactory() {
		oxygenInventory = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	}    
	
	public void setLastDateOfProduction(LocalDate lastDate) {
		lastDateOfProduction = lastDate;
	}
    
    public void orderTemplatedOxygenQuantity(OxygenGrade grade) {
    	oxygenInventory.put(grade, oxygenInventory.get(grade) + getProductionQuantity(grade));
    }
    
    public void setTemplatedOxygenOrder(EnumMap<OxygenGrade, Integer> template) {
    	defaultGradeAProduction = template.containsKey(OxygenGrade.A) ? template.get(OxygenGrade.A) : 1;
    	defaultGradeEProduction = template.containsKey(OxygenGrade.E) ? template.get(OxygenGrade.E) : 1;
    	defaultGradeBProduction = template.containsKey(OxygenGrade.B) ? template.get(OxygenGrade.B) : 1;
	}
    
    @Override
	public int getInventory(OxygenGrade oxygenGrade) {
		return oxygenInventory.get(oxygenGrade);
	}
    
    private int getProductionQuantity(OxygenGrade grade) {
    	switch (grade) {
		case A:
			return defaultGradeAProduction;
        case B:
			return defaultGradeBProduction;
        case E:
	        return defaultGradeEProduction;
		default:
			return 1;
		}
    }
}
