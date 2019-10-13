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
        oxygenInventoryRepository.saveOxygenInventory(OxygenGrade.A, 20);
        assertEquals(20, oxygenInventoryRepository.findInventoryOfGrade(OxygenGrade.A));
    }

    @Test
    public void whenSetOxygenCategoryRemaining_thenInventoryIsCorrectlyUpdated() {
        oxygenInventoryRepository.saveOxygenRemaining(OxygenGrade.B, 20);
        assertEquals(20, oxygenInventoryRepository.findOxygenRemaining(OxygenGrade.B));
    }

    @Test
    public void whenGetCompleteInventory_thenInventoryIsCorrectlyPresented() {
        oxygenInventoryRepository.saveOxygenInventory(OxygenGrade.B, 20);
        oxygenInventoryRepository.saveOxygenInventory(OxygenGrade.A, 15);
        oxygenInventoryRepository.saveOxygenInventory(OxygenGrade.E, 26);
        
        assertEquals(20, oxygenInventoryRepository.findInventoryOfGrade(OxygenGrade.B));
        assertEquals(15, oxygenInventoryRepository.findInventoryOfGrade(OxygenGrade.A));
        assertEquals(26, oxygenInventoryRepository.findInventoryOfGrade(OxygenGrade.E));
    }
}