package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.mockito.Mockito.*;

class OxygenReserverTest {

    private static final int TANK_FABRICATION_QUANTITY_GRADE_A = 5;
    private static final int CANDLE_FABRICATION_QUANTITY_GRADE_A = 15;
    private static final int QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY_GRADE_A = 4;
    private static final int QUANTITY_LESS_THAN_TWO_GRADE_A_TANK_FABRICATION_QUANTITIES = 6;
    private static final int SOME_INVENTORY = 2;
    private static final int SOME_REMAINING_QUANTITY = 1;
    private final static LocalDate FESTIVAL_STARTING_DATE = LocalDate.of(2050, 7, 17);
    private final static LocalDate ONE_MONTH_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusMonths(1);
    private final static LocalDate DELIVERY_DATE_GRADE_A_ORDER = LocalDate.of(2050, 7, 7);
    private final static LocalDate FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusDays(15);
    private final static LocalDate FIVE_DAYS_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusDays(5);

    private OxygenInventory oxygenInventoryGradeA = new OxygenInventory(OxygenGrade.A, SOME_INVENTORY, SOME_REMAINING_QUANTITY);
    private GradeAOxygenOrder gradeAOxygen;
    private EnumMap<OxygenGrade, OxygenInventory> oxygenInventories = new EnumMap<>(OxygenGrade.class);
    private SortedMap<LocalDate, OxygenDateHistory> oxygenHistory = new TreeMap<>();
    private OxygenOrderFactory oxygenOrderFactory;
    private OxygenReserver oxygenReserver;

    @BeforeEach
    public void setUp() {
        initializeOxygenInventories();
        mockOxygenGradeA();
        mockOxygenFactory();

        oxygenReserver = new OxygenReserver(oxygenOrderFactory);
    }

//    @Test
//    public void whenOrderOxygenGradeAQuantityLessThanTankFabricationQuantity_thenTankFabricationQuantityIsAdded() {
//        oxygenReserver.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY_GRADE_A, oxygenInventories, oxygenHistory);
//
//        int expectedInventory = SOME_INVENTORY + TANK_FABRICATION_QUANTITY_GRADE_A;
//        int inventory = oxygenInventories.get(OxygenGrade.A).getInventory();
//        assertEquals(expectedInventory, inventory);
//    }
//
//    @Test
//    public void whenOrderOxygenGradeAQuantityLessThanTankFabricationQuantity_thenRemainingQuantityIsAdjusted() {
//        oxygenReserver.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY_GRADE_A, oxygenInventories, oxygenHistory);
//
//        int expectedRemainingQuantity = TANK_FABRICATION_QUANTITY_GRADE_A - QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY_GRADE_A + SOME_REMAINING_QUANTITY;
//        int remainingQuantity = oxygenInventories.get(OxygenGrade.A).getRemainingQuantity();
//        assertEquals(expectedRemainingQuantity, remainingQuantity);
//    }
//
//    @Test
//    public void whenOrderOxygenGradeAQuantityLessThanTankFabricationQuantity_thenOrderDateHistoryIsAdjusted() {
//        oxygenReserver.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY_GRADE_A, oxygenInventories, oxygenHistory);
//
//        OxygenDateHistory orderDateHistory = oxygenHistory.get(ONE_MONTH_BEFORE_FESTIVAL_DATE);
//
//        verify(gradeAOxygen).updateOxygenHistory(oxygenHistory, ONE_MONTH_BEFORE_FESTIVAL_DATE, TANK_FABRICATION_QUANTITY_GRADE_A);
//    }
//
//    @Test
//    public void whenOrderOxygenGradeAQuantityLessThanTankFabricationQuantity_thenDeliveryrDateHistoryIsAdjusted() {
//        oxygenReserver.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY_GRADE_A, oxygenInventories, oxygenHistory);
//
//        OxygenDateHistory deliveryDateHistory = oxygenHistory.get(DELIVERY_DATE_GRADE_A_ORDER);
//
//        int oxygenTankMade = deliveryDateHistory.getOxygenTankMade();
//        assertEquals(TANK_FABRICATION_QUANTITY_GRADE_A, oxygenTankMade);
//    }

    //TODO issue #112 part 2

    private void initializeOxygenInventories() {
        EnumSet.allOf(OxygenGrade.class)
                .forEach(grade -> oxygenInventories.put(grade, new OxygenInventory(grade, 0, 0)));

        oxygenInventories.put(OxygenGrade.A, oxygenInventoryGradeA);
    }

    private void mockOxygenGradeA() {
        gradeAOxygen = mock(GradeAOxygenOrder.class);

        when(gradeAOxygen.isNotEnoughTimeToFabricate(ONE_MONTH_BEFORE_FESTIVAL_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY_GRADE_A)).thenReturn(false);
        when(gradeAOxygen.isNotEnoughTimeToFabricate(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, QUANTITY_LESS_THAN_TANK_FABRICATION_QUANTITY_GRADE_A)).thenReturn(true);
        when(gradeAOxygen.updateOxygenHistory(oxygenHistory, ONE_MONTH_BEFORE_FESTIVAL_DATE, TANK_FABRICATION_QUANTITY_GRADE_A)).thenReturn(oxygenHistory);
    }

    private void mockOxygenFactory() {
        oxygenOrderFactory = mock(OxygenOrderFactory.class);

        when(oxygenOrderFactory.create(OxygenGrade.A, oxygenInventoryGradeA)).thenReturn(gradeAOxygen);
    }
}