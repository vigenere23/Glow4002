package ca.ulaval.glo4002.booking.domain.enumMaps;

import static org.junit.Assert.assertEquals;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;

public class PassCategoryMapperTest {

    private final static EnumMap<PassCategory, OxygenGrade> oxygenGradeMap = new EnumMap<PassCategory, OxygenGrade>(PassCategory.class);
    private final static EnumMap<PassCategory, Integer> oxygenQuantityMap = new EnumMap<PassCategory, Integer>(PassCategory.class);
    private final static EnumMap<PassCategory, ShuttleCategory> shuttleCategoryMap = new EnumMap<PassCategory, ShuttleCategory>(PassCategory.class);
    private final static PassCategory SOME_PASS_CATEGORY = PassCategory.SUPERGIANT;

    @BeforeEach
    public void setupPassCategoryMapper() {
        setupOxygenGradeMap();
        setupOxygenQuantityMap();
        setupShuttleCategoryMap();
    }

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

    private void setupOxygenGradeMap() {
        oxygenGradeMap.put(PassCategory.NEBULA, OxygenGrade.A);
        oxygenGradeMap.put(PassCategory.SUPERGIANT, OxygenGrade.B);
        oxygenGradeMap.put(PassCategory.SUPERNOVA, OxygenGrade.E);
    }

    private void setupOxygenQuantityMap() {
        oxygenQuantityMap.put(PassCategory.NEBULA, 3);
        oxygenQuantityMap.put(PassCategory.SUPERGIANT, 3);
        oxygenQuantityMap.put(PassCategory.SUPERNOVA, 5);
    }

    private void setupShuttleCategoryMap() {
        shuttleCategoryMap.put(PassCategory.NEBULA, ShuttleCategory.SPACE_X);
        shuttleCategoryMap.put(PassCategory.SUPERGIANT, ShuttleCategory.MILLENNIUM_FALCON);
        shuttleCategoryMap.put(PassCategory.SUPERNOVA, ShuttleCategory.ET_SPACESHIP);
    }
}