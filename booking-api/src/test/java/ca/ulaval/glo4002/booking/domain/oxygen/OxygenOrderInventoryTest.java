package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OxygenOrderInventoryTest {

    private final static OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    private final static int SOME_INVENTORY = 2;
    private final static int SOME_REMAINING_QUANTITY = 3;
    private final static int SOME_QUANTITY_TO_FABRICATE = 4;
    private final static int SOME_REQUIRED_QUANTITY = 1;

    private OxygenInventory oxygenInventory;

    @BeforeEach
    public void setUp() {
        oxygenInventory = new OxygenInventory(SOME_OXYGEN_GRADE, SOME_INVENTORY, SOME_REMAINING_QUANTITY);
    }

    @Test
    public void whenUpdateInventory_thenInventoryIncreases() {
        oxygenInventory.updateInventory(SOME_QUANTITY_TO_FABRICATE, SOME_REQUIRED_QUANTITY);

        int inventory = SOME_INVENTORY + SOME_QUANTITY_TO_FABRICATE;
        assertEquals(inventory, oxygenInventory.getInventory());
    }

    @Test
    public void whenUpdateInventory_thenRemainingQuantityIsUpdated() {
        int someQuantityToFabricate = 4;
        int someQuantityRequired = 2;
        oxygenInventory.updateInventory(someQuantityToFabricate, someQuantityRequired);

        int expectedRemainingQuantity = 2;
        assertEquals(expectedRemainingQuantity, oxygenInventory.getRemainingQuantity());
    }
}