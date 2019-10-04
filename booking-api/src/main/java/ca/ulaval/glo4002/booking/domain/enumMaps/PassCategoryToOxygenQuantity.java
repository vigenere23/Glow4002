package ca.ulaval.glo4002.booking.domain.enumMaps;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;

public class PassCategoryToOxygenQuantity {

    private Map<PassCategory, Integer> map;

    public PassCategoryToOxygenQuantity() {
        this.map = new HashMap<>();
        this.map.put(PassCategory.NEBULA, 3);
        this.map.put(PassCategory.SUPERGIANT, 3);
        this.map.put(PassCategory.SUPERNOVA, 5);
    }

    public int getAssociatedValue(PassCategory passCategory) {
        return this.map.get(passCategory);
    }
}
