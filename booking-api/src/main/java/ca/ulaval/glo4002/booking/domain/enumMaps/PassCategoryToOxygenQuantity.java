package ca.ulaval.glo4002.booking.domain.enumMaps;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;

public class PassCategoryToOxygenQuantity {

    private Map<PassCategory, Integer> map;

    public PassCategoryToOxygenQuantity() {
        map = new HashMap<>();
        map.put(PassCategory.NEBULA, 3);
        map.put(PassCategory.SUPERGIANT, 3);
        map.put(PassCategory.SUPERNOVA, 5);
    }

    public int getAssociatedValue(PassCategory passCategory) {
        return map.get(passCategory);
    }
}
