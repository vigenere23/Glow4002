package ca.ulaval.glo4002.booking.domain.enumMaps;

import java.util.EnumMap;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;

public class PassCategoryToShuttleCategory {

    private EnumMap<PassCategory, ShuttleCategory> enumMap;

    public PassCategoryToShuttleCategory() {
        enumMap = new EnumMap<>(PassCategory.class);
        enumMap.put(PassCategory.NEBULA, ShuttleCategory.SPACE_X);
        enumMap.put(PassCategory.SUPERGIANT, ShuttleCategory.MILLENNIUM_FALCON);
        enumMap.put(PassCategory.SUPERNOVA, ShuttleCategory.ET_SPACESHIP);
    }

    public ShuttleCategory getAssociatedValue(PassCategory passCategory) {
        return enumMap.get(passCategory);
    }
}
