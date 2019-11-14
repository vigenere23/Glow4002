package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OxygenHistoryItemTest {

    private final static int SOME_OXYGEN_TANK_BOUGHT_QTY = 1;
    private final static int SOME_OXYGEN_TANK_MADE_QTY = 2;
    private final static int SOME_WATER_USED_QTY = 3;
    private final static int SOME_CANDLES_USED_QTY = 4;
    private final static int SOME_QUANTITY = 2;
    private final static int SOME_OTHER_QUANTITY = 6;
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 6, 22);
    private final static LocalDate SOME_DATE_DIFFERENT_INSTANCE = LocalDate.of(2050, 6, 22);
    private final static LocalDate SOME_OTHER_DATE = SOME_DATE.plusDays(1);

    private OxygenHistoryItem oxygenHistoryItem;

    @BeforeEach
    public void setUpOxygenHistory() {
        oxygenHistoryItem = new OxygenHistoryItem(SOME_DATE);
    }

    @Test
    public void whenUpdateAHistoryTypeQuantity_thenHistoryTypeIsUpdated() {
        oxygenHistoryItem.updateQuantity(HistoryType.CANDLES_USED, SOME_QUANTITY);
        assertEquals(SOME_QUANTITY, oxygenHistoryItem.getCandlesUsed());
    }

    @Test
    public void givenAHistoryTypeQuantity_whenUpdateTheHistoryTypeQuantity_thenHistoryTypeIsUpdated() {
        oxygenHistoryItem.updateQuantity(HistoryType.CANDLES_USED, SOME_QUANTITY);
        oxygenHistoryItem.updateQuantity(HistoryType.CANDLES_USED, SOME_OTHER_QUANTITY);

        int expectedQuantity = SOME_QUANTITY + SOME_OTHER_QUANTITY;
        assertEquals(expectedQuantity, oxygenHistoryItem.getCandlesUsed());
    }

    @Test
    public void givenAnotherOxygenDateHistory_whenUpdateQuantities_thenOxygenTankBoughtIsUpdated() {
        OxygenHistoryItem someOxygenHistoryItem = initializeOtherDateHistory();

        oxygenHistoryItem.updateQuantities(someOxygenHistoryItem);

        assertEquals(SOME_OXYGEN_TANK_BOUGHT_QTY, oxygenHistoryItem.getOxygenTankBought());
    }

    @Test
    public void givenAnotherOxygenDateHistory_whenUpdateQuantities_thenOxygenTankMadeIsUpdated() {
        OxygenHistoryItem someOxygenHistoryItem = initializeOtherDateHistory();

        oxygenHistoryItem.updateQuantities(someOxygenHistoryItem);

        assertEquals(SOME_OXYGEN_TANK_MADE_QTY, oxygenHistoryItem.getOxygenTankMade());
    }

    @Test
    public void givenAnotherOxygenDateHistory_whenUpdateQuantities_thenWaterUsedIsUpdated() {
        OxygenHistoryItem someOxygenHistoryItem = initializeOtherDateHistory();

        oxygenHistoryItem.updateQuantities(someOxygenHistoryItem);

        assertEquals(SOME_WATER_USED_QTY, oxygenHistoryItem.getWaterUsed());
    }

    @Test
    public void givenAnotherOxygenDateHistory_whenUpdateQuantities_thenCandlesUsedIsUpdated() {
        OxygenHistoryItem someOxygenHistoryItem = initializeOtherDateHistory();

        oxygenHistoryItem.updateQuantities(someOxygenHistoryItem);

        assertEquals(SOME_CANDLES_USED_QTY, oxygenHistoryItem.getCandlesUsed());
    }

    @Test
    public void givenAnotherOxygenDateHistoryWithOtherDate_whenUpdateWithIt_thenExceptionIsThrown() {
        OxygenHistoryItem someOxygenHistoryItem = new OxygenHistoryItem(SOME_OTHER_DATE);
        assertThrows(IllegalArgumentException.class, () -> oxygenHistoryItem.updateQuantities(someOxygenHistoryItem));
    }

    private OxygenHistoryItem initializeOtherDateHistory() {
        OxygenHistoryItem someOxygenHistoryItem = new OxygenHistoryItem(SOME_DATE_DIFFERENT_INSTANCE);
        someOxygenHistoryItem.updateQuantity(HistoryType.OXYGEN_TANK_BOUGHT, SOME_OXYGEN_TANK_BOUGHT_QTY);
        someOxygenHistoryItem.updateQuantity(HistoryType.OXYGEN_TANK_MADE, SOME_OXYGEN_TANK_MADE_QTY);
        someOxygenHistoryItem.updateQuantity(HistoryType.WATER_USED, SOME_WATER_USED_QTY);
        someOxygenHistoryItem.updateQuantity(HistoryType.CANDLES_USED, SOME_CANDLES_USED_QTY);

        return someOxygenHistoryItem;
    }
}