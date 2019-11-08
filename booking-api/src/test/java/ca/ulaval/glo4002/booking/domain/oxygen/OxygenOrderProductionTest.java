package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OxygenOrderProductionTest {

    private static final int SOME_FABRICATION_TIME_IN_DAYS = 4;
    private static final int SOME_TANK_FABRICATION_QUANTITY = 5;
    private static final int SOME_FABRICATION_QUANTITY = 3;
    private static final int QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY = 2;
    private static final int QUANTITY_LESS_THAN_TWO_TANK_FABRICATION_QUANTITIES = 6;
    private static final int SOME_QUANTITY = 2;
    private static final HistoryType SOME_ORDER_DATE_HISTORY_TYPE = HistoryType.CANDLES_USED;
    private static final HistoryType SOME_COMPLETION_DATE_HISTORY_TYPE = HistoryType.OXYGEN_TANK_MADE;
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 6, 22);
    private final static LocalDate SOME_COMPLETION_DATE = LocalDate.of(2050, 6, 26);

    private EnumMap<HistoryType, Integer> someOrderDateQuantityPerBatch = new EnumMap<>(HistoryType.class);
    private EnumMap<HistoryType, Integer> someCompletionDateQuantityPerBatch = new EnumMap<>(HistoryType.class);
    private SortedMap<LocalDate, OxygenDateHistory> someHistory = new TreeMap<>();
    private OxygenProduction oxygenProduction;

    @BeforeEach
    public void setUp() {
        initializeQuantitiesPerFabricationBatch();
        initializeHistory();

        oxygenProduction = new OxygenProduction(SOME_FABRICATION_TIME_IN_DAYS, SOME_TANK_FABRICATION_QUANTITY, someOrderDateQuantityPerBatch, someCompletionDateQuantityPerBatch);
    }

    @Test
    public void whenOrderOxygenQuantityLessThanFabricationQuantity_thenFabricationQuantityIsFabricated() {
        int quantityToFabricate = oxygenProduction.getQuantityToFabricate(QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY, SOME_FABRICATION_QUANTITY);

        assertEquals(SOME_FABRICATION_QUANTITY, quantityToFabricate);
    }

    @Test
    public void whenOrderOxygenQuantityMoreThanFabricationQuantity_thenMultipleOfFabricationQuantityIsAdded() {
        int quantityToFabricate = oxygenProduction.getQuantityToFabricate(QUANTITY_LESS_THAN_TWO_TANK_FABRICATION_QUANTITIES, SOME_FABRICATION_QUANTITY);

        int expectedQuantity = 2 * SOME_FABRICATION_QUANTITY;
        assertEquals(expectedQuantity, quantityToFabricate);
    }

    @Test
    public void whenUpdateOxygenHistory_thenOrderedDateHistoryIsUpdated() {
        SortedMap<LocalDate, OxygenDateHistory> updatedOxygenHistory = oxygenProduction.updateOxygenHistory(someHistory, SOME_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

        int producedQuantity = updatedOxygenHistory.get(SOME_DATE).getCandlesUsed();
        int updatedQuantity = SOME_QUANTITY + SOME_FABRICATION_QUANTITY;
        assertEquals(updatedQuantity, producedQuantity);
    }

    @Test
    public void whenUpdateOxygenHistory_thenCompletionDateHistoryIsUpdated() {
        SortedMap<LocalDate, OxygenDateHistory> updatedOxygenHistory = oxygenProduction.updateOxygenHistory(someHistory, SOME_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

        int producedQuantity = updatedOxygenHistory.get(SOME_COMPLETION_DATE).getOxygenTankMade();
        assertEquals(SOME_TANK_FABRICATION_QUANTITY, producedQuantity);
    }

    private void initializeQuantitiesPerFabricationBatch() {
        someCompletionDateQuantityPerBatch.put(SOME_COMPLETION_DATE_HISTORY_TYPE, SOME_TANK_FABRICATION_QUANTITY);
        someOrderDateQuantityPerBatch.put(SOME_ORDER_DATE_HISTORY_TYPE, SOME_FABRICATION_QUANTITY);
    }

    private void initializeHistory() {
        OxygenDateHistory someOxygenOrderDateHistory = new OxygenDateHistory(SOME_DATE);
        someOxygenOrderDateHistory.updateQuantity(SOME_ORDER_DATE_HISTORY_TYPE, SOME_QUANTITY);

        someHistory.put(SOME_DATE, someOxygenOrderDateHistory);
    }
}