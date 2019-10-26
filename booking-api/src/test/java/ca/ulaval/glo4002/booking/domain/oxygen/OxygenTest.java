package ca.ulaval.glo4002.booking.domain.oxygen;

import ca.ulaval.glo4002.booking.domain.exceptions.NotEnoughTimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        private final OxygenGrade oxygenGrade = SOME_OXYGEN_GRADE;
        private static final int someFabricationQuantity = SOME_FABRICATION_QUANTITY;

        public OxygenImplementationTest(LocalDate limitDeliveryDate, OxygenInventory oxygenInventory) {
            this.limitDeliveryDate = limitDeliveryDate;
            this.remainingQuantity = oxygenInventory.getRemainingQuantity();
            this.oxygenInventory = oxygenInventory;
            tankFabricationQuantity = SOME_TANK_FABRICATION_QUANTITY;
            fabricationTimeInDays = SOME_FABRICATION_TIME_IN_DAYS;
            initializeQuantitiesPerBatch();
            oxygenProduction = new OxygenProduction(fabricationTimeInDays, tankFabricationQuantity, orderDateQuantityPerBatch, completionDateQuantityPerBatch);
        }

        protected void initializeQuantitiesPerBatch() {
            completionDateQuantityPerBatch.put(SOME_COMPLETION_DATE_HISTORY_TYPE, tankFabricationQuantity);
            orderDateQuantityPerBatch.put(SOME_ORDER_DATE_HISTORY_TYPE, someFabricationQuantity);
        }
    }

    private SortedMap<LocalDate, OxygenDateHistory> someHistory = new TreeMap<LocalDate, OxygenDateHistory>();
    private SortedMap<LocalDate, OxygenDateHistory> updatedHistory = new TreeMap<LocalDate, OxygenDateHistory>();
    private OxygenInventory someOxygenInventory;
    private Oxygen oxygen;

    @BeforeEach
    public void setUp() {
        initializeHistories();
        someOxygenInventory = new OxygenInventory(SOME_OXYGEN_GRADE, SOME_INVENTORY, SOME_REMAINING_QUANTITY);

        oxygen = new OxygenImplementationTest(SOME_DATE, someOxygenInventory);
    }

    @Test
    public void whenOrderMoreOxygenThanRemainingQuantity_thenOrderDateHistoryIsUpdated() {
        SortedMap<LocalDate, OxygenDateHistory> history = oxygen.updateOxygenHistory(someHistory, SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

        assertEquals(updatedHistory.get(SOME_ORDER_DATE).getOxygenHistory(), history.get(SOME_ORDER_DATE).getOxygenHistory());
    }

    @Test
    public void whenOrderMoreOxygenThanRemainingQuantity_thenCompletionDateHistoryIsUpdated() {
        SortedMap<LocalDate, OxygenDateHistory> history = oxygen.updateOxygenHistory(someHistory, SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

        assertEquals(updatedHistory.get(COMPLETION_DATE).getOxygenHistory(), history.get(COMPLETION_DATE).getOxygenHistory());
    }

    @Test
    public void whenOrderLessOxygenThanRemainingQuantity_thenCompletionDateHistoryIsNotUpdated() {
        SortedMap<LocalDate, OxygenDateHistory> history = oxygen.updateOxygenHistory(someHistory, SOME_ORDER_DATE, QUANTITY_LESS_THAN_REMAINING_QUANTITY);

        assertEquals(someHistory, history);
    }

    @Test
    public void whenOrderMoreOxygenThanRemainingQuantity_thenInventoryIsUpdated() throws NotEnoughTimeException {
        OxygenInventory oxygenInventory = oxygen.adjustInventory(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);
        int inventory = SOME_TANK_FABRICATION_QUANTITY + SOME_INVENTORY;

        assertEquals(inventory, oxygenInventory.getInventory());
    }

    @Test
    public void whenOrderMoreOxygenThanRemainingQuantity_thenRemainingQuantityIsUpdated() throws NotEnoughTimeException {
        OxygenInventory oxygenInventory = oxygen.adjustInventory(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);
        int remainingQuantity = SOME_TANK_FABRICATION_QUANTITY - QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY + SOME_REMAINING_QUANTITY;

        assertEquals(remainingQuantity, oxygenInventory.getRemainingQuantity());
    }

    @Test
    public void whenOrderLessOxygenThanRemainingQuantity_thenInventoryIsNotUpdated() throws NotEnoughTimeException {
        OxygenInventory oxygenInventory = oxygen.adjustInventory(SOME_ORDER_DATE, QUANTITY_LESS_THAN_REMAINING_QUANTITY);

        assertEquals(SOME_INVENTORY, oxygenInventory.getInventory());
    }

    @Test
    public void whenOrderLessOxygenThanRemainingQuantity_thenRemainingQuantityIsUpdated() throws NotEnoughTimeException {
        OxygenInventory oxygenInventory = oxygen.adjustInventory(SOME_ORDER_DATE, QUANTITY_LESS_THAN_REMAINING_QUANTITY);
        int remainingQuantity = SOME_REMAINING_QUANTITY - QUANTITY_LESS_THAN_REMAINING_QUANTITY;

        assertEquals(remainingQuantity, oxygenInventory.getRemainingQuantity());
    }

    @Test void whenOrderTooLate_thenException() {
        assertThrows(NotEnoughTimeException.class, () -> oxygen.adjustInventory(TOO_LATE_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY));
    }

    private void initializeHistories() {
        OxygenDateHistory someOxygenOrderDateHistory = new OxygenDateHistory(SOME_ORDER_DATE);
        someOxygenOrderDateHistory.updateQuantity(SOME_ORDER_DATE_HISTORY_TYPE, SOME_QUANTITY);
        someHistory.put(someOxygenOrderDateHistory.getDate(), someOxygenOrderDateHistory);

        OxygenDateHistory updatedOxygenOrderDateHistory = new OxygenDateHistory(SOME_ORDER_DATE);
        updatedOxygenOrderDateHistory.updateQuantity(SOME_ORDER_DATE_HISTORY_TYPE, SOME_QUANTITY + SOME_FABRICATION_QUANTITY);
        OxygenDateHistory updatedOxygenCompletionDateHistory = new OxygenDateHistory(COMPLETION_DATE);
        updatedOxygenCompletionDateHistory.updateQuantity(SOME_COMPLETION_DATE_HISTORY_TYPE, SOME_TANK_FABRICATION_QUANTITY);
        updatedHistory.put(updatedOxygenOrderDateHistory.getDate(), updatedOxygenOrderDateHistory);
        updatedHistory.put(updatedOxygenCompletionDateHistory.getDate(), updatedOxygenCompletionDateHistory);
    }
}