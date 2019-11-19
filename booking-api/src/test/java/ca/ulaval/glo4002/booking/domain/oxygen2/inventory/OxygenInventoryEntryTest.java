package ca.ulaval.glo4002.booking.domain.oxygen2.inventory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public class OxygenInventoryEntryTest {

    private final static OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;

    private OxygenInventoryEntry inventoryEntry;

    @BeforeEach
    public void setup() {
        inventoryEntry = new OxygenInventoryEntry(SOME_OXYGEN_GRADE);
    }

    @Test
    public void whenCreating_quantityIsZero() {
        OxygenInventoryEntry inventoryEntry = new OxygenInventoryEntry(SOME_OXYGEN_GRADE);
        assertThat(inventoryEntry.getQuantity()).isZero();
    }

    @Test
    public void givenOxygenGrade_whenCreating_itAssignsTheOxygenGrade() {
        OxygenInventoryEntry inventoryEntry = new OxygenInventoryEntry(SOME_OXYGEN_GRADE);
        assertThat(inventoryEntry.getOxygenGrade()).isEqualTo(SOME_OXYGEN_GRADE);
    }

    @Test
    public void whenAddingQuantity_itAddsQuantity() {
        final int quantityAdded = 15;
        inventoryEntry.addQuantity(quantityAdded);
        assertThat(inventoryEntry.getQuantity()).isEqualTo(quantityAdded);
    }

    @Test
    public void givenQuantityNotPresent_whenRemovingQuantity_itThrowsAnIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            inventoryEntry.removeQuantity(1);
        });
    }

    @Test
    public void givenQuantityPresent_whenRemovingGreaterQuantity_itThrowsAnIllegalArgumentException() {
        final int quantityAlreadyPresent = 15;
        final int quantityRemoved = 20;
        inventoryEntry.addQuantity(quantityAlreadyPresent);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            inventoryEntry.removeQuantity(quantityRemoved);
        });
    }

    @Test
    public void givenQuantityPresent_whenRemovingSmallerQuantity_itRemovesQuantity() {
        final int quantityAlreadyPresent = 15;
        final int quantityRemoved = 10;
        inventoryEntry.addQuantity(quantityAlreadyPresent);

        inventoryEntry.removeQuantity(quantityRemoved);
        
        assertThat(inventoryEntry.getQuantity()).isEqualTo(quantityAlreadyPresent - quantityRemoved);
    }
}
