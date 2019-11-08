package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OxygenReserverTest {

    private static final int SOME_INVENTORY = 3;
    private static final int SOME_REMAINING_QUANTITY = 2;
    private static final int SOME_QUANTITY_MORE_THAN_REMAINING_QUANTITY = 4;
    private static final int SOME_QUANTITY_LESS_THAN_REMAINING_QUANTITY = 1;
    private final static LocalDate FESTIVAL_STARTING_DATE = LocalDate.of(2050, 7, 17);
    private final static LocalDate ONE_MONTH_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusMonths(1);
    private final static LocalDate FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusDays(15);
    private final static LocalDate FIVE_DAYS_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusDays(5);

    private OxygenInventory oxygenInventoryGradeA = new OxygenInventory(OxygenGrade.A, SOME_INVENTORY, SOME_REMAINING_QUANTITY);
    private GradeAOxygenOrder gradeAOxygenOrder;
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

    @Test
    public void whenOrderOxygenGradeA_thenInventoryIsUpdated() {
        oxygenReserver.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, SOME_QUANTITY_MORE_THAN_REMAINING_QUANTITY, oxygenInventories, oxygenHistory);

        int quantityToFabricate = SOME_QUANTITY_MORE_THAN_REMAINING_QUANTITY - SOME_REMAINING_QUANTITY;
        verify(gradeAOxygenOrder).adjustInventory(ONE_MONTH_BEFORE_FESTIVAL_DATE, quantityToFabricate);
    }

//    @Test
//    public void whenOrderOxygenGradeAMoreThanRemainingQuantity_thenRemainingQuantityIsUpdated() {
//        oxygenReserver.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, SOME_QUANTITY_MORE_THAN_REMAINING_QUANTITY, oxygenInventories, oxygenHistory);
//
//        verify(oxygenInventories.get(OxygenGrade.A)).setRemainingQuantity(0);
//    }

    @Test
    public void whenOrderOxygenGradeA_thenHistoryIsUpdated() {
        oxygenReserver.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, SOME_QUANTITY_MORE_THAN_REMAINING_QUANTITY, oxygenInventories, oxygenHistory);

        int quantityToFabricate = SOME_QUANTITY_MORE_THAN_REMAINING_QUANTITY - SOME_REMAINING_QUANTITY;
        verify(gradeAOxygenOrder).updateOxygenHistory(oxygenHistory, ONE_MONTH_BEFORE_FESTIVAL_DATE, quantityToFabricate);
    }

//    @Test
//    public void whenOrderOxygenGradeAQuantityLessThanTankFabricationQuantity_thenDeliveryrDateHistoryIsAdjusted() {
//        oxygenReserver.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, SOME_QUANTITY_MORE_THAN_REMAINING_QUANTITY, oxygenInventories, oxygenHistory);
//
//        OxygenDateHistory deliveryDateHistory = oxygenHistory.get(DELIVERY_DATE_GRADE_A_ORDER);
//
//        int oxygenTankMade = deliveryDateHistory.getOxygenTankMade();
//        assertEquals(TANK_FABRICATION_QUANTITY_GRADE_A, oxygenTankMade);
//    }

    //TODO issue #112 part 2

    private void initializeOxygenInventories() {
        oxygenInventories.put(OxygenGrade.A, oxygenInventoryGradeA);
    }

    private void mockOxygenGradeA() {
        gradeAOxygenOrder = mock(GradeAOxygenOrder.class);

        when(gradeAOxygenOrder.isNotEnoughTimeToFabricate(ONE_MONTH_BEFORE_FESTIVAL_DATE, SOME_QUANTITY_MORE_THAN_REMAINING_QUANTITY)).thenReturn(false);
        when(gradeAOxygenOrder.isNotEnoughTimeToFabricate(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, SOME_QUANTITY_MORE_THAN_REMAINING_QUANTITY)).thenReturn(true);
    }

    private void mockOxygenFactory() {
        oxygenOrderFactory = mock(OxygenOrderFactory.class);

        when(oxygenOrderFactory.create(OxygenGrade.A, oxygenInventoryGradeA)).thenReturn(gradeAOxygenOrder);
    }
}