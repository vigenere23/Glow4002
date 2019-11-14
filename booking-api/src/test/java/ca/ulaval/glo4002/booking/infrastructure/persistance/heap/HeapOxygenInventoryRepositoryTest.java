package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

import java.util.List;

public class HeapOxygenInventoryRepositoryTest {

    private final static int SOME_OXYGEN_REMAINING = 2;
    private final static int SOME_OXYGEN_INVENTORY = 3;

    private HeapOxygenInventoryRepository oxygenInventoryRepository;

    @BeforeEach
    public void setUpOxygenInventory() {
        oxygenInventoryRepository = new HeapOxygenInventoryRepository();
    }

    @Test
    public void inventoriesAreInitialized() {
        List<OxygenInventory> initialOxygenInventories = oxygenInventoryRepository.findAll();

        for (OxygenInventory oxygenInventory : initialOxygenInventories) {
            assertEquals(0, oxygenInventory.getInventory());
        }
    }

    @Test
    public void remainingQuantitiesAreInitialized() {
        List<OxygenInventory> initialOxygenInventories = oxygenInventoryRepository.findAll();

        for (OxygenInventory oxygenInventory : initialOxygenInventories) {
            assertEquals(0, oxygenInventory.getRemainingQuantity());
        }
    }

    @Test
    public void whenSetOxygenCategoryInventoryOfGradeA_thenInventoryIsCorrectlyUpdated() {
        OxygenInventory expectedOxygenInventoryGradeB = new OxygenInventory(OxygenGrade.A, SOME_OXYGEN_INVENTORY, SOME_OXYGEN_REMAINING);
        oxygenInventoryRepository.save(expectedOxygenInventoryGradeB);

        OxygenInventory oxygenInventoryGradeB = oxygenInventoryRepository.findByGrade(OxygenGrade.A);
        assertEquals(expectedOxygenInventoryGradeB, oxygenInventoryGradeB);
    }

    @Test
    public void whenSetOxygenCategoryInventoryOfGradeB_thenInventoryIsCorrectlyUpdated() {
        OxygenInventory expectedOxygenInventoryGradeA = new OxygenInventory(OxygenGrade.B, SOME_OXYGEN_INVENTORY, SOME_OXYGEN_REMAINING);
        oxygenInventoryRepository.save(expectedOxygenInventoryGradeA);

        OxygenInventory oxygenInventoryGradeA = oxygenInventoryRepository.findByGrade(OxygenGrade.B);
        assertEquals(expectedOxygenInventoryGradeA, oxygenInventoryGradeA);
    }

    @Test
    public void whenSetOxygenCategoryInventoryOfGradeE_thenInventoryIsCorrectlyUpdated() {
        OxygenInventory expectedOxygenInventoryGradeE = new OxygenInventory(OxygenGrade.E, SOME_OXYGEN_INVENTORY, SOME_OXYGEN_REMAINING);
        oxygenInventoryRepository.save(expectedOxygenInventoryGradeE);

        OxygenInventory oxygenInventoryGradeE = oxygenInventoryRepository.findByGrade(OxygenGrade.E);
        assertEquals(expectedOxygenInventoryGradeE, oxygenInventoryGradeE);
    }
}
