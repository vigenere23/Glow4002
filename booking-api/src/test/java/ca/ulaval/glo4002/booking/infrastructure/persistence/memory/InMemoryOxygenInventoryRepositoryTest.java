package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;

public class InMemoryOxygenInventoryRepositoryTest {

    private final static OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    
    private InMemoryOxygenInventoryRepository oxygenInventory;

    @BeforeEach
    public void setup() {
        oxygenInventory = new InMemoryOxygenInventoryRepository();
    }

    @Test
    public void whenCreating_thenAllGradesArePresent() {
        for (OxygenGrade oxygenGrade : OxygenGrade.values()) {
            assertThat(oxygenInventory.find(oxygenGrade)).isNotNull();
        }
    }

    @Test
    public void whenUpdating_itUpdatesWithTheNewInformation() {
        OxygenInventoryEntry entry = new OxygenInventoryEntry(SOME_OXYGEN_GRADE);
        oxygenInventory.replace(entry);

        OxygenInventoryEntry savedEntry = oxygenInventory.find(SOME_OXYGEN_GRADE);
        
        assertThat(savedEntry).isEqualTo(entry);
    }
}
