package ca.ulaval.glo4002.booking.domain.enumMaps;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;

public class PassCategoryMapperTest {

    private final static PassCategory SOME_PASS_CATEGORY = PassCategory.SUPERGIANT;

    @Test
    public void givenPassCategory_whenGettingOxygenGradeFromMap_thenReturnAssociatedOxygenGrade() {
        OxygenGrade oxygenGrade = PassCategoryMapper.getOxygenGrade(SOME_PASS_CATEGORY);

        assertEquals(OxygenGrade.B, oxygenGrade);
    }

    @Test
    public void givenPassCategory_whenGettingOxygenQuantityFromMap_thenReturnAssociatedQuantity() {
        int oxygenQuantity = PassCategoryMapper.getOxygenQuantity(SOME_PASS_CATEGORY);

        assertEquals(3, oxygenQuantity);
    }

    @Test
    public void givenPassCategory_whenGettingShuttleCategoryFromMap_thenReturnAssociatedShuttleCategory() {
        ShuttleCategory shuttleCategory = PassCategoryMapper.getShuttleCategory(SOME_PASS_CATEGORY);

        assertEquals(ShuttleCategory.MILLENNIUM_FALCON, shuttleCategory);
    }
}
