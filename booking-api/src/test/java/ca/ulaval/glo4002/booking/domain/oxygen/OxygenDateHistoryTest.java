package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class OxygenDateHistoryTest {

    private static final int SOME_OXYGEN_TANK_BOUGHT_QTY = 1;
    private static final int SOME_OXYGEN_TANK_MADE_QTY = 2;
    private static final int SOME_WATER_USED_QTY = 3;
    private static final int SOME_CANDLES_USED_QTY = 4;
    private static final int SOME_QUANTITY = 2;
    private static final int SOME_OTHER_QUANTITY = 6;
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 6, 22);
    private final static LocalDate SOME_DATE_DIFFERENT_INSTANCE = LocalDate.of(2050, 6, 22);
    private final static LocalDate SOME_OTHER_DATE = SOME_DATE.plusDays(1);

    private OxygenDateHistory oxygenDateHistory;

    @BeforeEach
    public void setUp() {
        oxygenDateHistory = new OxygenDateHistory(SOME_DATE);
    }

    @Test
    public void oxygenDateHistoryTypeQuantitiesAreInitialized() {
        EnumMap<HistoryType, Integer> history = oxygenDateHistory.getOxygenHistory();
        for (HistoryType historyType: history.keySet()) {
            int quantity = history.get(historyType);
            assertEquals(0, quantity);
        }
    }

    @Test
    public void whenUpdateAHistoryTypeQuantity_thenHistoryTypeIsUpdated() {
        oxygenDateHistory.updateQuantity(HistoryType.CANDLES_USED, SOME_QUANTITY);

        assertEquals(SOME_QUANTITY, oxygenDateHistory.getCandlesUsed());
    }

    @Test
    public void givenAHistoryTypeQuantity_whenUpdateTheHistoryTypeQuantity_thenHistoryTypeIsUpdated() {
        oxygenDateHistory.updateQuantity(HistoryType.CANDLES_USED, SOME_QUANTITY);

        oxygenDateHistory.updateQuantity(HistoryType.CANDLES_USED, SOME_OTHER_QUANTITY);

        int expectedQuantity = SOME_QUANTITY + SOME_OTHER_QUANTITY;
        assertEquals(expectedQuantity, oxygenDateHistory.getCandlesUsed());
    }

    @Test
    public void givenAnotherOxygenDateHistory_whenUpdateWithIt_thenEveryHistoryTypeAreUpdated() {
        OxygenDateHistory someOxygenDateHistory = initializeOtherDateHistory();

        oxygenDateHistory.updateQuantities(someOxygenDateHistory);

        assertEquals(SOME_OXYGEN_TANK_BOUGHT_QTY, oxygenDateHistory.getOxygenTankBought());
        assertEquals(SOME_OXYGEN_TANK_MADE_QTY, oxygenDateHistory.getOxygenTankMade());
        assertEquals(SOME_WATER_USED_QTY, oxygenDateHistory.getWaterUsed());
        assertEquals(SOME_CANDLES_USED_QTY, oxygenDateHistory.getCandlesUsed());
    }

    @Test
    public void givenAnotherOxygenDateHistoryWithOtherDate_whenUpdateWithIt_thenException() {
        OxygenDateHistory someOxygenDateHistory = new OxygenDateHistory(SOME_OTHER_DATE);

        assertThrows(IllegalArgumentException.class, () -> oxygenDateHistory.updateQuantities(someOxygenDateHistory));
    }

    private OxygenDateHistory initializeOtherDateHistory() {
        OxygenDateHistory someOxygenDateHistory = new OxygenDateHistory(SOME_DATE_DIFFERENT_INSTANCE);
        someOxygenDateHistory.updateQuantity(HistoryType.OXYGEN_TANK_BOUGHT, SOME_OXYGEN_TANK_BOUGHT_QTY);
        someOxygenDateHistory.updateQuantity(HistoryType.OXYGEN_TANK_MADE, SOME_OXYGEN_TANK_MADE_QTY);
        someOxygenDateHistory.updateQuantity(HistoryType.WATER_USED, SOME_WATER_USED_QTY);
        someOxygenDateHistory.updateQuantity(HistoryType.CANDLES_USED, SOME_CANDLES_USED_QTY);

        return someOxygenDateHistory;
    }
}