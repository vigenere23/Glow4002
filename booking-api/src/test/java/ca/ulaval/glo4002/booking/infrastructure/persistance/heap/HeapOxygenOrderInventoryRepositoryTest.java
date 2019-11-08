package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

import java.util.EnumMap;

public class HeapOxygenOrderInventoryRepositoryTest {
    private static final OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    private static final int SOME_OXYGEN_REMAINING = 2;
    private static final int SOME_OXYGEN_INVENTORY = 3;

    private HeapOxygenInventoryRepository oxygenInventoryRepository;
    private EnumMap<OxygenGrade, OxygenInventory> oxygenInventories = new EnumMap<OxygenGrade, OxygenInventory>(OxygenGrade.class);

    @BeforeEach
    public void setUp() {
        OxygenInventory oxygenInventory = new OxygenInventory(SOME_OXYGEN_GRADE, SOME_OXYGEN_INVENTORY, SOME_OXYGEN_REMAINING);
        oxygenInventories.put(SOME_OXYGEN_GRADE, oxygenInventory);
        oxygenInventoryRepository = new HeapOxygenInventoryRepository();
    }

    @Test
    public void inventoriesAreInitialized() {
        EnumMap<OxygenGrade, OxygenInventory> initialOxygenInventories = oxygenInventoryRepository.findInventories();

        for (OxygenInventory oxygenInventory: initialOxygenInventories.values()) {
            assertEquals(0, oxygenInventory.getInventory());
        }
    }

    @Test
    public void remainingQuantitiesAreInitialized() {
        EnumMap<OxygenGrade, OxygenInventory> initialOxygenInventories = oxygenInventoryRepository.findInventories();

        for (OxygenInventory oxygenInventory: initialOxygenInventories.values()) {
            assertEquals(0, oxygenInventory.getRemainingQuantity());
        }
    }

    @Test
    public void whenSetOxygenCategoryInventory_thenInventoryIsCorrectlyUpdated() {
        oxygenInventoryRepository.saveOxygenInventories(oxygenInventories);
        assertEquals(oxygenInventories, oxygenInventoryRepository.findInventories());
    }
}