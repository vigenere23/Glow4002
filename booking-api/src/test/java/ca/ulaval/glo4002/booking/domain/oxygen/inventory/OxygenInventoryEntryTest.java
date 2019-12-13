package ca.ulaval.glo4002.booking.domain.oxygen.inventory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenInventoryEntryTest {

    private final static OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;

    private OxygenInventoryEntry inventoryEntry;

    @BeforeEach
    public void setup() {
        inventoryEntry = new OxygenInventoryEntry(SOME_OXYGEN_GRADE);
    }

    @Test
    public void whenCreating_totalQuantityIsZero() {
        OxygenInventoryEntry inventoryEntry = new OxygenInventoryEntry(SOME_OXYGEN_GRADE);
        assertThat(inventoryEntry.getTotalQuantity()).isZero();
    }

    @Test
    public void givenOxygenGrade_whenCreating_itAssignsTheOxygenGrade() {
        OxygenInventoryEntry inventoryEntry = new OxygenInventoryEntry(SOME_OXYGEN_GRADE);
        assertThat(inventoryEntry.getOxygenGrade()).isEqualTo(SOME_OXYGEN_GRADE);
    }

    @Test
    public void givenNegativeValue_whenAddingQuantity_itThrowsAnIllegalArgumentException() {
        final int quantity = -1;
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            inventoryEntry.addQuantity(quantity);
        });
    }

    @Test
    public void whenAddingQuantity_itAddsQuantityToSurplus() {
        final int quantityAdded = 15;
        inventoryEntry.addQuantity(quantityAdded);
        assertThat(inventoryEntry.getSurplusQuantity()).isEqualTo(quantityAdded);
    }

    @Test
    public void givenSurplusQuantityNotPresent_whenUsingQuantity_itThrowsAnIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            inventoryEntry.useQuantity(1);
        });
    }

    @Test
    public void givenSurplusQuantityPresent_whenUsingGreaterQuantity_itThrowsAnIllegalArgumentException() {
        final int quantityAlreadyPresent = 15;
        final int quantityRemoved = 20;
        inventoryEntry.addQuantity(quantityAlreadyPresent);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            inventoryEntry.useQuantity(quantityRemoved);
        });
    }

    @Test
    public void givenSurplusQuantityPresent_whenUsingSmallerQuantity_itRemovesQuantity() {
        final int quantityAlreadyPresent = 15;
        final int quantityRemoved = 10;
        inventoryEntry.addQuantity(quantityAlreadyPresent);

        inventoryEntry.useQuantity(quantityRemoved);
        
        assertThat(inventoryEntry.getSurplusQuantity()).isEqualTo(quantityAlreadyPresent - quantityRemoved);
    }

    @Test
    public void givenNegativeValue_whenUsingQuantity_itThrowsAnIllegalArgumentException() {
        final int quantity = -1;
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            inventoryEntry.useQuantity(quantity);
        });
    }
}
