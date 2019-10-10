package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class HeapOxygenInventoryRepositoryTest {

    private HeapOxygenInventoryRepository oxygenInventoryRepository;

    @BeforeEach
    public void setUp() {
        oxygenInventoryRepository = new HeapOxygenInventoryRepository();
    }

    @Test
    public void whenSetOxygenCategoryInventory_thenInventoryIsCorrectlyUpdated() {
        oxygenInventoryRepository.setOxygenInventory(OxygenGrade.A, 20);
        assertEquals(20, oxygenInventoryRepository.getInventoryOfGrade(OxygenGrade.A));
    }

    @Test
    public void whenSetOxygenCategoryRemaining_thenInventoryIsCorrectlyUpdated() {
        oxygenInventoryRepository.setOxygenRemaining(OxygenGrade.B, 20);
        assertEquals(20, oxygenInventoryRepository.getOxygenRemaining(OxygenGrade.B));
    }

    @Test
    public void whenGetCompleteInventory_thenInventoryIsCorrectlyPresented() {
        oxygenInventoryRepository.setOxygenInventory(OxygenGrade.B, 20);
        oxygenInventoryRepository.setOxygenInventory(OxygenGrade.A, 15);
        oxygenInventoryRepository.setOxygenInventory(OxygenGrade.E, 26);
        
        assertEquals(20, oxygenInventoryRepository.getInventoryOfGrade(OxygenGrade.B));
        assertEquals(15, oxygenInventoryRepository.getInventoryOfGrade(OxygenGrade.A));
        assertEquals(26, oxygenInventoryRepository.getInventoryOfGrade(OxygenGrade.E));
    }
}