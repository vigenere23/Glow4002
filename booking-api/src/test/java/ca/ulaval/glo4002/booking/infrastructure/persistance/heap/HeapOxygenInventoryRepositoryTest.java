package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class HeapOxygenInventoryRepositoryTest {

    private HeapOxygenInventory inventory;

    @BeforeEach
    public void setUp() {
        inventory = new HeapOxygenInventory();
    }

    @Test
    public void whenSetOxygenCategoryInventory_thenInventoryIsCorrectlyUpdated() {
        inventory.setOxygenInventory(OxygenGrade.A, 20);
        assertEquals(20, inventory.getInventoryOfGrade(OxygenGrade.A));
    }

    @Test
    public void whenSetOxygenCategoryRemaining_thenInventoryIsCorrectlyUpdated() {
        inventory.setOxygenRemaining(OxygenGrade.B, 20);
        assertEquals(20, inventory.getOxygenRemaining(OxygenGrade.B));
    }

    @Test
    public void whenGetCompleteInventory_thenInventoryIsCorrectlyPresented() {
        inventory.setOxygenInventory(OxygenGrade.B, 20);
        inventory.setOxygenInventory(OxygenGrade.A, 15);
        inventory.setOxygenInventory(OxygenGrade.E, 26);
        
        assertEquals(20, inventory.getInventoryOfGrade(OxygenGrade.B));
        assertEquals(15, inventory.getInventoryOfGrade(OxygenGrade.A));
        assertEquals(26, inventory.getInventoryOfGrade(OxygenGrade.E));
    }
}