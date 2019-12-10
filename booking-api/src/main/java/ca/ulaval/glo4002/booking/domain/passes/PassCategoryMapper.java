package ca.ulaval.glo4002.booking.domain.passes;

import java.util.EnumMap;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleCategory;

public class PassCategoryMapper {

    private static final EnumMap<PassCategory, OxygenGrade> oxygenGradeMap = new EnumMap<PassCategory, OxygenGrade>(PassCategory.class) {{
        put(PassCategory.NEBULA, OxygenGrade.A);
        put(PassCategory.SUPERGIANT, OxygenGrade.B);
        put(PassCategory.SUPERNOVA, OxygenGrade.E);
    }};

    private static final EnumMap<PassCategory, Integer> oxygenQuantityMap = new EnumMap<PassCategory, Integer>(PassCategory.class) {{
        put(PassCategory.NEBULA, 3);
        put(PassCategory.SUPERGIANT, 3);
        put(PassCategory.SUPERNOVA, 5);
    }};

    private static final EnumMap<PassCategory, ShuttleCategory> shuttleCategoryMap = new EnumMap<PassCategory, ShuttleCategory>(PassCategory.class) {{
        put(PassCategory.NEBULA, ShuttleCategory.SPACE_X);
        put(PassCategory.SUPERGIANT, ShuttleCategory.MILLENNIUM_FALCON);
        put(PassCategory.SUPERNOVA, ShuttleCategory.ET_SPACESHIP);
    }};

    public static OxygenGrade getOxygenGrade(PassCategory passCategory) {
        return oxygenGradeMap.get(passCategory);
    }

    public static int getOxygenQuantity(PassCategory passCategory) {
        return oxygenQuantityMap.get(passCategory);
    }

    public static ShuttleCategory getShuttleCategory(PassCategory passCategory) {
        return shuttleCategoryMap.get(passCategory);
    }
}
