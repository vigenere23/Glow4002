package ca.ulaval.glo4002.booking.domain.oxygen2.inventory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public class OxygenInventoryTest {

    private final static OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    
    private OxygenInventory oxygenInventory;

    @BeforeEach
    public void setup() {
        oxygenInventory = new OxygenInventory();
    }

    @Test
    public void whenCreating_thenAllQuantitiesAreAtZero() {
        for (OxygenGrade oxygenGrade : OxygenGrade.values()) {
            int quantity = oxygenInventory.getQuantity(oxygenGrade);
            assertThat(quantity).isZero();
        }
    }

    @Test
    public void whenAddingQuantity_itAddsQuantity() {
        final int quantityAdded = 15;
        oxygenInventory.addQuantity(SOME_OXYGEN_GRADE, quantityAdded);
        
        int quantity = oxygenInventory.getQuantity(SOME_OXYGEN_GRADE);
        assertThat(quantity).isEqualTo(quantityAdded);
    }

    @Test
    public void givenQuantityPresent_whenRemovingSmallerQuantity_itRemovesQuantity() {
        final int quantityAlreadyPresent = 15;
        final int quantityRemoved = 10;
        oxygenInventory.addQuantity(SOME_OXYGEN_GRADE, quantityAlreadyPresent);

        oxygenInventory.removeQuantity(SOME_OXYGEN_GRADE, quantityRemoved);
        
        int quantity = oxygenInventory.getQuantity(SOME_OXYGEN_GRADE);
        assertThat(quantity).isEqualTo(quantityAlreadyPresent - quantityRemoved);
    }

    @Test
    public void givenQuantityPresent_whenRemovingGreaterQuantity_itThrowsAnIllegalArgumentException() {
        final int quantityAlreadyPresent = 15;
        final int quantityRemoved = 20;
        oxygenInventory.addQuantity(SOME_OXYGEN_GRADE, quantityAlreadyPresent);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            oxygenInventory.removeQuantity(SOME_OXYGEN_GRADE, quantityRemoved);
        });
    }

    @Test
    public void givenQuantityNotPresent_whenRemovingQuantity_itThrowsAnIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            oxygenInventory.removeQuantity(SOME_OXYGEN_GRADE, 1);
        });
    }
}
