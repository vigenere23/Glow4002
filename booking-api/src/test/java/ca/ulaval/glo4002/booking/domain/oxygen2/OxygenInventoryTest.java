package ca.ulaval.glo4002.booking.domain.oxygen2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void whenUpdatingQuantity_itSavesNewQuantity() {
        final int newQuantity = 15;
        oxygenInventory.updateQuantity(SOME_OXYGEN_GRADE, newQuantity);
        
        int quantity = oxygenInventory.getQuantity(SOME_OXYGEN_GRADE);
        assertThat(quantity).isEqualTo(newQuantity);
    }
}
