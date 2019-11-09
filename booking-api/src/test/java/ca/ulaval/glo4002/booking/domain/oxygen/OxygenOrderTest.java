package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class OxygenOrderTest {

    private static final int SOME_QUANTITY = 2;
    private static final int SOME_FABRICATION_TIME_IN_DAYS = 20;
    private static final int SOME_TANK_FABRICATION_QUANTITY = 5;
    private static final int SOME_FABRICATION_QUANTITY = 3;
    private static final int QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY = 3;
    private static final int QUANTITY_LESS_THAN_TWO_TANK_FABRICATION_QUANTITIES = 7;
    private static final HistoryType SOME_ORDER_DATE_HISTORY_TYPE = HistoryType.CANDLES_USED;
    private static final HistoryType SOME_COMPLETION_DATE_HISTORY_TYPE = HistoryType.OXYGEN_TANK_MADE;
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 7, 17);
    private final static LocalDate SOME_ORDER_DATE = SOME_DATE.minusMonths(1);
    private final static LocalDate COMPLETION_DATE = LocalDate.of(2050, 7, 7);
    private final static LocalDate TOO_LATE_DATE = SOME_DATE.minusDays(15);

    private static class OxygenOrderImplementationTest extends OxygenOrder {

        public OxygenOrderImplementationTest(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
            super(limitDeliveryDate, tankFabricationQuantity, fabricationTimeInDays);
        }

        protected void initializeQuantitiesPerBatch() {
            completionDateQuantitiesPerBatch.put(SOME_COMPLETION_DATE_HISTORY_TYPE, tankFabricationQuantity);
            orderDateQuantitiesPerBatch.put(SOME_ORDER_DATE_HISTORY_TYPE, SOME_FABRICATION_QUANTITY);
        }
    }

    private SortedMap<LocalDate, OxygenDateHistory> someHistory = new TreeMap<>();
    private SortedMap<LocalDate, OxygenDateHistory> updatedHistory = new TreeMap<>();
    private OxygenOrder oxygenOrder;

    @BeforeEach
    public void setUp() {
        initializeOrderDateHistory();
        initializeUpdatedOrderDateHistory();
        initializeCompletionDateHistory();

        oxygenOrder = new OxygenOrderImplementationTest(SOME_DATE, SOME_TANK_FABRICATION_QUANTITY, SOME_FABRICATION_TIME_IN_DAYS);
    }

    @Test void whenOrderOnTime_thenCallerIsNotified() {
        boolean enoughTime = oxygenOrder.enoughTimeToFabricate(SOME_ORDER_DATE);

        assertTrue(enoughTime);
    }


    @Test void whenOrderOxygenLessThanTankFabricationQuantity_thenHasToReserveTankFabricationQuantity() {
        int quantityToReserve = oxygenOrder.getQuantityToReserve(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

        assertEquals(SOME_TANK_FABRICATION_QUANTITY, quantityToReserve);
    }

    @Test void whenOrderOxygenMoreThanTankFabricationQuantity_thenHasToReserveAMultipleOfTankFabricationQuantity() {
        int quantityToReserve = oxygenOrder.getQuantityToReserve(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TWO_TANK_FABRICATION_QUANTITIES);

        int expectedQuantityToReserve = SOME_TANK_FABRICATION_QUANTITY * 2;
        assertEquals(expectedQuantityToReserve, quantityToReserve);
    }

    @Test
    public void whenOrderOxygenLessThanTankFabricationQuantity_thenOrderDateHistoryIsUpdated() {
        SortedMap<LocalDate, OxygenDateHistory> history = oxygenOrder.updateOxygenHistory(someHistory, SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

        EnumMap<HistoryType, Integer> expectedOrderDatehistory = updatedHistory.get(SOME_ORDER_DATE).getOxygenHistory();
        EnumMap<HistoryType, Integer> orderDateHistory =  history.get(SOME_ORDER_DATE).getOxygenHistory();
        assertEquals(expectedOrderDatehistory, orderDateHistory);
    }

    @Test
    public void whenOrderOxygenOnTime_thenCompletionDateHistoryIsUpdated() {
        SortedMap<LocalDate, OxygenDateHistory> history = oxygenOrder.updateOxygenHistory(someHistory, SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

        EnumMap<HistoryType, Integer> expectedOrderDatehistory = updatedHistory.get(COMPLETION_DATE).getOxygenHistory();
        EnumMap<HistoryType, Integer> orderDateHistory =  history.get(COMPLETION_DATE).getOxygenHistory();
        assertEquals(expectedOrderDatehistory, orderDateHistory);
    }

    @Test void whenOrderTooLate_thenCallerIsNotified() {
        boolean enoughTime = oxygenOrder.enoughTimeToFabricate(TOO_LATE_DATE);

        assertFalse(enoughTime);
    }

    @Test void whenOrderTooLate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> oxygenOrder.getQuantityToReserve(TOO_LATE_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY));
    }

    private void initializeOrderDateHistory() {
        OxygenDateHistory someOxygenOrderDateHistory = new OxygenDateHistory(SOME_ORDER_DATE);
        someOxygenOrderDateHistory.updateQuantity(SOME_ORDER_DATE_HISTORY_TYPE, SOME_QUANTITY);

        someHistory.put(someOxygenOrderDateHistory.getDate(), someOxygenOrderDateHistory);
    }

    private void initializeUpdatedOrderDateHistory() {
        OxygenDateHistory updatedOxygenOrderDateHistory = new OxygenDateHistory(SOME_ORDER_DATE);
        updatedOxygenOrderDateHistory.updateQuantity(SOME_ORDER_DATE_HISTORY_TYPE, SOME_QUANTITY + SOME_FABRICATION_QUANTITY);

        updatedHistory.put(updatedOxygenOrderDateHistory.getDate(), updatedOxygenOrderDateHistory);
    }

    private void initializeCompletionDateHistory() {
        OxygenDateHistory updatedOxygenCompletionDateHistory = new OxygenDateHistory(COMPLETION_DATE);
        updatedOxygenCompletionDateHistory.updateQuantity(SOME_COMPLETION_DATE_HISTORY_TYPE, SOME_TANK_FABRICATION_QUANTITY);

        updatedHistory.put(updatedOxygenCompletionDateHistory.getDate(), updatedOxygenCompletionDateHistory);
    }
}