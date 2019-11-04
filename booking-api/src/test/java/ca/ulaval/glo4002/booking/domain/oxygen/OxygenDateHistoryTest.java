package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class OxygenDateHistoryTest {

    private static final int SOME_FABRICATION_TIME_IN_DAYS = 4;
    private static final int SOME_FABRICATION_QUANTITY = 7;
    private static final int SOME_TANK_FABRICATION_QUANTITY = 3;
    private static final int QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY = 2;
    private static final int QUANTITY_LESS_THAN_TWO_FABRICATION_QUANTITIES = 4;
    private static final int SOME_OXYGEN_TANK_MADE_QTY = 5;
    private static final int SOME_QUANTITY = 2;
    private static final int SOME_OTHER_QUANTITY = 6;
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 6, 22);
    private final static LocalDate SOME_COMPLETION_DATE = LocalDate.of(2050, 6, 26);

    private OxygenDateHistory someOxygenDateHistory;
    private OxygenDateHistory oxygenDateHistory;

    @BeforeEach
    public void setUp() {
        initializeDateHistory();
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

    //TODO issue #112 part 2

    private void initializeDateHistory() {
        someOxygenDateHistory = new OxygenDateHistory(SOME_DATE);
        someOxygenDateHistory.updateQuantity(HistoryType.CANDLES_USED, SOME_QUANTITY);
    }
}