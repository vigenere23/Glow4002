package ca.ulaval.glo4002.booking.domain.enumMaps;

import java.util.EnumMap;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;

public class PassCategoryToOxygenGrade {

    private EnumMap<PassCategory, OxygenGrade> enumMap;

    public PassCategoryToOxygenGrade() {
        this.enumMap = new EnumMap<>(PassCategory.class);
        this.enumMap.put(PassCategory.NEBULA, OxygenGrade.A);
        this.enumMap.put(PassCategory.SUPERGIANT, OxygenGrade.B);
        this.enumMap.put(PassCategory.SUPERNOVA, OxygenGrade.E);
    }

    public OxygenGrade getAssociatedValue(PassCategory passCategory) {
        return enumMap.get(passCategory);
    }
}
