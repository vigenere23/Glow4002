package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventoryEntry;

public class HeapOxygenInventoryRepositoryTest {

    private final static OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    
    private HeapOxygenInventoryRepository oxygenInventory;

    @BeforeEach
    public void setup() {
        oxygenInventory = new HeapOxygenInventoryRepository();
    }

    @Test
    public void whenCreating_thenAllGradesArePresent() {
        for (OxygenGrade oxygenGrade : OxygenGrade.values()) {
            assertThat(oxygenInventory.find(oxygenGrade)).isNotNull();
        }
    }

    @Test
    public void whenSaving_itDoesNotChangeTheNumberOfEntries() {
        int oldSize = oxygenInventory.findAll().size();
        OxygenInventoryEntry entry = new OxygenInventoryEntry(SOME_OXYGEN_GRADE);
        oxygenInventory.save(entry);

        int newSize = oxygenInventory.findAll().size();

        assertThat(newSize).isEqualTo(oldSize);
    }

    @Test
    public void whenSaving_itReplacesTheOldEntryByTheNewOne() {
        OxygenInventoryEntry entry = new OxygenInventoryEntry(SOME_OXYGEN_GRADE);
        oxygenInventory.save(entry);

        OxygenInventoryEntry savedEntry = oxygenInventory.find(SOME_OXYGEN_GRADE);
        
        assertThat(savedEntry).isEqualTo(entry);
    }
}
