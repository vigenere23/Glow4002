package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class OxygenTest {
    private final static int SOME_INVENTORY = 2;
    private final static int SOME_REMAINING_QUANTITY = 2;
    private static final int SOME_QUANTITY = 2;
    private static final int SOME_FABRICATION_TIME_IN_DAYS = 20;
    private static final int SOME_TANK_FABRICATION_QUANTITY = 5;
    private static final int SOME_FABRICATION_QUANTITY = 3;
    private static final int QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY = 3;
    private static final int QUANTITY_LESS_THAN_REMAINING_QUANTITY = 1;
    private static final OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    private static final HistoryType SOME_ORDER_DATE_HISTORY_TYPE = HistoryType.CANDLES_USED;
    private static final HistoryType SOME_COMPLETION_DATE_HISTORY_TYPE = HistoryType.OXYGEN_TANK_MADE;
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 7, 17);
    private final static LocalDate SOME_ORDER_DATE = SOME_DATE.minusMonths(1);
    private final static LocalDate COMPLETION_DATE = LocalDate.of(2050, 7, 7);
    private final static LocalDate TOO_LATE_DATE = SOME_DATE.minusDays(15);

    private class OxygenImplementationTest extends Oxygen {


        public OxygenImplementationTest(LocalDate limitDeliveryDate, OxygenInventory oxygenInventory, int tankFabricationQuantity, int fabricationTimeInDays) {
            super(limitDeliveryDate, oxygenInventory, tankFabricationQuantity, fabricationTimeInDays);
        }

        protected void initializeQuantitiesPerBatch() {
            completionDateQuantityPerBatch.put(SOME_COMPLETION_DATE_HISTORY_TYPE, tankFabricationQuantity);
            orderDateQuantityPerBatch.put(SOME_ORDER_DATE_HISTORY_TYPE, SOME_FABRICATION_QUANTITY);
        }
    }

    private SortedMap<LocalDate, OxygenDateHistory> someHistory = new TreeMap<>();
    private SortedMap<LocalDate, OxygenDateHistory> updatedHistory = new TreeMap<>();
    private Oxygen oxygen;

    @BeforeEach
    public void setUp() {
        initializeOrderDateHistory();
        initializeUpdatedOrderDateHistory();
        initializeCompletionDateHistory();
        OxygenInventory someOxygenInventory = new OxygenInventory(SOME_OXYGEN_GRADE, SOME_INVENTORY, SOME_REMAINING_QUANTITY);

        oxygen = new OxygenImplementationTest(SOME_DATE, someOxygenInventory, SOME_TANK_FABRICATION_QUANTITY, SOME_FABRICATION_TIME_IN_DAYS);
    }

    @Test
    public void whenOrderMoreOxygenThanRemainingQuantity_thenOrderDateHistoryIsUpdated() {
        SortedMap<LocalDate, OxygenDateHistory> history = oxygen.updateOxygenHistory(someHistory, SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);
        EnumMap<HistoryType, Integer> expectedOrderDatehistory = updatedHistory.get(SOME_ORDER_DATE).getOxygenHistory();
        EnumMap<HistoryType, Integer> orderDateHistory =  history.get(SOME_ORDER_DATE).getOxygenHistory();

        assertEquals(expectedOrderDatehistory, orderDateHistory);
    }

    @Test
    public void whenOrderMoreOxygenThanRemainingQuantity_thenCompletionDateHistoryIsUpdated() {
        SortedMap<LocalDate, OxygenDateHistory> history = oxygen.updateOxygenHistory(someHistory, SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);
        EnumMap<HistoryType, Integer> expectedOrderDatehistory = updatedHistory.get(COMPLETION_DATE).getOxygenHistory();
        EnumMap<HistoryType, Integer> orderDateHistory =  history.get(COMPLETION_DATE).getOxygenHistory();

        assertEquals(expectedOrderDatehistory, orderDateHistory);
    }

    @Test
    public void whenOrderLessOxygenThanRemainingQuantity_thenCompletionDateHistoryIsNotUpdated() {
        SortedMap<LocalDate, OxygenDateHistory> history = oxygen.updateOxygenHistory(someHistory, SOME_ORDER_DATE, QUANTITY_LESS_THAN_REMAINING_QUANTITY);

        assertEquals(someHistory, history);
    }

    @Test
    public void whenOrderMoreOxygenThanRemainingQuantity_thenInventoryIsUpdated() {
        oxygen.adjustInventory(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);
        int inventory = oxygen.getOxygenInventory().getInventory();
        int expectedInventory = SOME_TANK_FABRICATION_QUANTITY + SOME_INVENTORY;

        assertEquals(expectedInventory, inventory);
    }

    @Test
    public void whenOrderMoreOxygenThanRemainingQuantity_thenRemainingQuantityIsUpdated() {
        oxygen.adjustInventory(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);
        int remainingQuantity = oxygen.getOxygenInventory().getRemainingQuantity();
        int expectedRemainingQuantity = SOME_TANK_FABRICATION_QUANTITY - QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY + SOME_REMAINING_QUANTITY;

        assertEquals(expectedRemainingQuantity, remainingQuantity);
    }

    @Test
    public void whenOrderLessOxygenThanRemainingQuantity_thenInventoryIsNotUpdated() {
        oxygen.adjustInventory(SOME_ORDER_DATE, QUANTITY_LESS_THAN_REMAINING_QUANTITY);
        int inventory = oxygen.getOxygenInventory().getInventory();

        assertEquals(SOME_INVENTORY, inventory);
    }

    @Test
    public void whenOrderLessOxygenThanRemainingQuantity_thenRemainingQuantityIsUpdated() {
        oxygen.adjustInventory(SOME_ORDER_DATE, QUANTITY_LESS_THAN_REMAINING_QUANTITY);
        int expectedRemainingQuantity = SOME_REMAINING_QUANTITY - QUANTITY_LESS_THAN_REMAINING_QUANTITY;
        int remainingQuantity = oxygen.getOxygenInventory().getRemainingQuantity();

        assertEquals(expectedRemainingQuantity, remainingQuantity);
    }

    @Test void whenOrderOnTime_thenCallerIsNotified() {
        boolean adjustInventory = oxygen.adjustInventory(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

        assertTrue(adjustInventory);
    }

    @Test void whenOrderTooLate_thenCallerIsNotified() {
        boolean adjustInventory = oxygen.adjustInventory(TOO_LATE_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

        assertFalse(adjustInventory);
    }

    @Test void whenOrderTooLate_thenInventoryIsNotUpdated() {
        oxygen.adjustInventory(TOO_LATE_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);
        int inventory = oxygen.getOxygenInventory().getInventory();

        assertEquals(SOME_INVENTORY, inventory);
    }

    @Test void whenOrderTooLate_thenRemainingQuantityIsNotUpdated() {
        oxygen.adjustInventory(TOO_LATE_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);
        int remainingQuantity = oxygen.getOxygenInventory().getRemainingQuantity();

        assertEquals(SOME_REMAINING_QUANTITY, remainingQuantity);
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