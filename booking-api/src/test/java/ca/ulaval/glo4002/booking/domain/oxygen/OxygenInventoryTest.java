package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OxygenInventoryTest {

    private final static OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    private final static int SOME_INVENTORY = 2;
    private final static int SOME_REMAINING_QUANTITY = 3;
    private final static int SOME_QUANTITY_TO_FABRICATE = 4;

    @Test
    public void whenUpdateInventory_thenInventoryIncreases() {
        OxygenInventory oxygenInventory = new OxygenInventory(SOME_OXYGEN_GRADE, SOME_INVENTORY, SOME_REMAINING_QUANTITY);
        oxygenInventory.updateInventory(SOME_QUANTITY_TO_FABRICATE);

        int expectedInventory = SOME_INVENTORY + SOME_QUANTITY_TO_FABRICATE;
        assertEquals(expectedInventory, oxygenInventory.getInventory());
    }
}