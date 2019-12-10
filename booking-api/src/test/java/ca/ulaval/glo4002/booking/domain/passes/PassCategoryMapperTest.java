package ca.ulaval.glo4002.booking.domain.passes;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleCategory;

public class PassCategoryMapperTest {

    @Test
    public void givenNebula_whenGettingOxygenGrade_itReturnsOxygenGradeA() {
        OxygenGrade oxygenGrade = PassCategoryMapper.getOxygenGrade(PassCategory.NEBULA);
        assertEquals(OxygenGrade.A, oxygenGrade);
    }

    @Test
    public void givenSupergiant_whenGettingOxygenGrade_itReturnsOxygenGradeB() {
        OxygenGrade oxygenGrade = PassCategoryMapper.getOxygenGrade(PassCategory.SUPERGIANT);
        assertEquals(OxygenGrade.B, oxygenGrade);
    }

    @Test
    public void givenSupernova_whenGettingOxygenGrade_itReturnsOxygenGradeE() {
        OxygenGrade oxygenGrade = PassCategoryMapper.getOxygenGrade(PassCategory.SUPERNOVA);
        assertEquals(OxygenGrade.E, oxygenGrade);
    }

    @Test
    public void givenNebula_whenGettingOxygenQuantity_itReturns3() {
        int oxygenQuantity = PassCategoryMapper.getOxygenQuantity(PassCategory.NEBULA);
        assertEquals(3, oxygenQuantity);
    }

    @Test
    public void givenSupergiant_whenGettingOxygenQuantity_itReturns3() {
        int oxygenQuantity = PassCategoryMapper.getOxygenQuantity(PassCategory.SUPERGIANT);
        assertEquals(3, oxygenQuantity);
    }

    @Test
    public void givenSupernova_whenGettingOxygenQuantity_itReturns5() {
        int oxygenQuantity = PassCategoryMapper.getOxygenQuantity(PassCategory.SUPERNOVA);
        assertEquals(5, oxygenQuantity);
    }

    @Test
    public void givenNebula_whenGettingShuttleCategory_itReturnsSpaceX() {
        ShuttleCategory shuttleCategory = PassCategoryMapper.getShuttleCategory(PassCategory.NEBULA);
        assertEquals(ShuttleCategory.SPACE_X, shuttleCategory);
    }

    @Test
    public void givenSupergiant_whenGettingShuttleCategory_itReturnsMilleniumFalcon() {
        ShuttleCategory shuttleCategory = PassCategoryMapper.getShuttleCategory(PassCategory.SUPERGIANT);
        assertEquals(ShuttleCategory.MILLENNIUM_FALCON, shuttleCategory);
    }

    @Test
    public void givenSupernova_whenGettingShuttleCategory_itReturnsETSpaceship() {
        ShuttleCategory shuttleCategory = PassCategoryMapper.getShuttleCategory(PassCategory.SUPERNOVA);
        assertEquals(ShuttleCategory.ET_SPACESHIP, shuttleCategory);
    }
}
