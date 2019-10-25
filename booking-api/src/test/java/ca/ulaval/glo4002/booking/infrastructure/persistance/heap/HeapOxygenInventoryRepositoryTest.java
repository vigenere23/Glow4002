package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenDateHistory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

public class HeapOxygenInventoryRepositoryTest {
    private static final OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    private static final int SOME_OXYGEN_REMAINING = 2;
    private static final int SOME_OXYGEN_INVENTORY = 3;
    private static final SortedMap<LocalDate, OxygenDateHistory> SOME_OXYGEN_HISTORIES = new TreeMap<LocalDate, OxygenDateHistory>();

    private HeapOxygenInventoryRepository oxygenInventoryRepository;
    private OxygenInventory oxygenInventory;

    @BeforeEach
    public void setUp() {
        oxygenInventoryRepository = new HeapOxygenInventoryRepository();
        oxygenInventory = new OxygenInventory(SOME_OXYGEN_GRADE, SOME_OXYGEN_INVENTORY, SOME_OXYGEN_REMAINING);
    }

    @Test
    public void whenSetOxygenCategoryInventory_thenInventoryIsCorrectlyUpdated() {
        oxygenInventoryRepository.saveOxygenInventory(oxygenInventory);
        assertEquals(oxygenInventory, oxygenInventoryRepository.findInventoryOfGrade(OxygenGrade.A));
    }
}