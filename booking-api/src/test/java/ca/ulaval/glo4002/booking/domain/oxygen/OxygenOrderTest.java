// package ca.ulaval.glo4002.booking.domain.oxygen;

// import ca.ulaval.glo4002.booking.domain.Price;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import java.time.LocalDate;
// import java.util.SortedMap;

// import static org.junit.jupiter.api.Assertions.*;

// class OxygenOrderTest {

//     private final static Price SOME_COST = new Price(500);
//     private final static int SOME_FABRICATION_TIME_IN_DAYS = 20;
//     private final static int SOME_TANK_FABRICATION_QUANTITY = 5;
//     private final static int SOME_FABRICATION_QUANTITY = 3;
//     private final static int QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY = 3;
//     private final static int QUANTITY_LESS_THAN_TWO_TANK_FABRICATION_QUANTITIES = 7;
//     private final static HistoryType SOME_ORDER_DATE_HISTORY_TYPE = HistoryType.CANDLES_USED;
//     private final static HistoryType SOME_COMPLETION_DATE_HISTORY_TYPE = HistoryType.OXYGEN_TANK_MADE;
//     private final static LocalDate SOME_FESTIVAL_START_DATE = LocalDate.of(2050, 7, 17);
//     private final static LocalDate SOME_ORDER_DATE = SOME_FESTIVAL_START_DATE.minusMonths(1);
//     private final static LocalDate COMPLETION_DATE = LocalDate.of(2050, 7, 7);
//     private final static LocalDate TOO_LATE_DATE = SOME_FESTIVAL_START_DATE.minusDays(15);

//     private static class OxygenOrderImplementationTest extends OxygenOrder {

//         public OxygenOrderImplementationTest(LocalDate limitDeliveryDate, int tankFabricationQuantity, int fabricationTimeInDays) {
//             super(limitDeliveryDate, tankFabricationQuantity, fabricationTimeInDays);
//         }

//         protected void initializeQuantitiesRequiredPerBatch() {
//             quantitiesRequiredPerBatchForOrderDate.put(SOME_ORDER_DATE_HISTORY_TYPE, SOME_FABRICATION_QUANTITY);
//             quantitiesRequiredPerBatchForCompletionDate.put(SOME_COMPLETION_DATE_HISTORY_TYPE, tankFabricationQuantity);
//         }

//         @Override
//         Price getOrderCost() {
//             return SOME_COST;
//         }
//     }

//     private OxygenOrder oxygenOrder;

//     @BeforeEach
//     public void setUpOxygenOrder() {
//         oxygenOrder = new OxygenOrderImplementationTest(SOME_FESTIVAL_START_DATE, SOME_TANK_FABRICATION_QUANTITY, SOME_FABRICATION_TIME_IN_DAYS);
//     }

//     @Test void whenOrderOnTime_thenThereIsEnoughTimeToFabricate() {
//         boolean isEnoughTime = oxygenOrder.isEnoughTimeToFabricate(SOME_ORDER_DATE);

//         assertTrue(isEnoughTime);
//     }


//     @Test void whenOrderOxygenLessThanTankFabricationQuantity_thenHasToReserveTankFabricationQuantity() {
//         int quantityToReserve = oxygenOrder.getQuantityToReserve(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

//         assertEquals(SOME_TANK_FABRICATION_QUANTITY, quantityToReserve);
//     }

//     @Test void whenOrderOxygenMoreThanTankFabricationQuantity_thenHasToReserveAMultipleOfTankFabricationQuantity() {
//         int quantityToReserve = oxygenOrder.getQuantityToReserve(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TWO_TANK_FABRICATION_QUANTITIES);

//         int expectedQuantityToReserve = SOME_TANK_FABRICATION_QUANTITY * 2;
//         assertEquals(expectedQuantityToReserve, quantityToReserve);
//     }

//     @Test
//     public void givenOxygenLessThanTankFabricationQuantityOrdered_whenGetOxygenHistory_thenOrderDateHistoryIsUpdated() {
//         oxygenOrder.getQuantityToReserve(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

//         SortedMap<LocalDate, OxygenHistoryItem> history = oxygenOrder.getOxygenOrderHistory(SOME_ORDER_DATE);

//         int actualQuantity =  history.get(SOME_ORDER_DATE).getCandlesUsed();
//         assertEquals(SOME_FABRICATION_QUANTITY, actualQuantity);
//     }

//     @Test
//     public void givenOxygenLessThanTankFabricationQuantityOrdered_whenGetOxygenHistory_thenCompletionDateHistoryIsUpdated() {
//         oxygenOrder.getQuantityToReserve(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY);

//         SortedMap<LocalDate, OxygenHistoryItem> history = oxygenOrder.getOxygenOrderHistory(SOME_ORDER_DATE);

//         int actualQuantity =  history.get(COMPLETION_DATE).getOxygenTankMade();
//         assertEquals(SOME_TANK_FABRICATION_QUANTITY, actualQuantity);
//     }

//     @Test
//     public void givenOxygenMoreThanTankFabricationQuantityOrdered_whenOxygenHistory_thenOrderDateHistoryIsUpdated() {
//         oxygenOrder.getQuantityToReserve(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TWO_TANK_FABRICATION_QUANTITIES);

//         SortedMap<LocalDate, OxygenHistoryItem> history = oxygenOrder.getOxygenOrderHistory(SOME_ORDER_DATE);

//         int expectedQuantity = SOME_FABRICATION_QUANTITY * 2;
//         int actualQuantity =  history.get(SOME_ORDER_DATE).getCandlesUsed();
//         assertEquals(expectedQuantity, actualQuantity);
//     }

//     @Test
//     public void givenOxygenMoreThanTankFabricationQuantityOrdered_whenOxygenHistory_thenCompletionDateHistoryIsUpdated() {
//         oxygenOrder.getQuantityToReserve(SOME_ORDER_DATE, QUANTITY_LESS_THAN_TWO_TANK_FABRICATION_QUANTITIES);

//         SortedMap<LocalDate, OxygenHistoryItem> history = oxygenOrder.getOxygenOrderHistory(SOME_ORDER_DATE);

//         int expectedQuantity = SOME_TANK_FABRICATION_QUANTITY * 2;
//         int actualQuantity =  history.get(COMPLETION_DATE).getOxygenTankMade();
//         assertEquals(expectedQuantity, actualQuantity);
//     }

//     @Test void whenOrderTooLate_thenThereIsNotEnoughTimeToFabricate() {
//         boolean isEnoughTime = oxygenOrder.isEnoughTimeToFabricate(TOO_LATE_DATE);

//         assertFalse(isEnoughTime);
//     }

//     @Test void whenOrderTooLate_thenExceptionIsThrown() {
//         assertThrows(IllegalArgumentException.class, () -> oxygenOrder.getQuantityToReserve(TOO_LATE_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY));
//     }
// }