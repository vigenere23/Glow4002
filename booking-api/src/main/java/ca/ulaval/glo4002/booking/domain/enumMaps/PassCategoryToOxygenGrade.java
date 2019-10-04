package ca.ulaval.glo4002.booking.domain.enumMaps;

import java.util.EnumMap;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;

public class PassCategoryToOxygenGrade {

    private EnumMap<PassCategory, OxygenGrade> enumMap;

    public PassCategoryToOxygenGrade() {
        enumMap = new EnumMap<>(PassCategory.class);
        enumMap.put(PassCategory.NEBULA, OxygenGrade.A);
        enumMap.put(PassCategory.SUPERGIANT, OxygenGrade.B);
        enumMap.put(PassCategory.SUPERNOVA, OxygenGrade.E);
    }

    public OxygenGrade getAssociatedValue(PassCategory passCategory) {
        return enumMap.get(passCategory);
    }
}