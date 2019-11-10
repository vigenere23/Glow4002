package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class OxygenReserverTest {

    private final static int SOME_QUANTITY = 1;
    private final static int SOME_REMAINING_QUANTITY = 3;
    private final static int QUANTITY_LESS_THAN_REMAINING_QUANTITY = 2;
    private final static int QUANTITY_MORE_THAN_REMAINING_QUANTITY = 4;
    private final static int LARGE_QUANTITY = 20;
    private final static int SOME_QUANTITY_TO_RESERVE = 5;
    private final static LocalDate FESTIVAL_STARTING_DATE = LocalDate.of(2050, 7, 17);
    private final static LocalDate SOME_ORDER_DATE = FESTIVAL_STARTING_DATE.minusMonths(1);
    private final static LocalDate SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A = FESTIVAL_STARTING_DATE.minusDays(15);
    private final static LocalDate SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_B = FESTIVAL_STARTING_DATE.minusDays(5);

    SortedMap<LocalDate, OxygenDateHistory> someOxygenHistory = new TreeMap<>();
    private OxygenInventory oxygenInventoryGradeA;
    private OxygenInventory oxygenInventoryGradeB;
    private OxygenInventory oxygenInventoryGradeE;
    private OxygenOrderFactory oxygenOrderFactory;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;
    private OxygenOrder oxygenOrderGradeA;
    private OxygenOrder oxygenOrderGradeB;
    private OxygenOrder oxygenOrderGradeE;
    private OxygenReserver oxygenReserver;

    @BeforeEach
    public void setUp() {
        mockOxygenOrders();
        mockOxygenOrderFactory();
        initializeOxygenInventories();
        mockOxygenInventoryRepository();
        mockOxygenHistoryRepository();
        oxygenReserver = new OxygenReserver(oxygenOrderFactory, oxygenInventoryRepository, oxygenHistoryRepository);
    }

    @Test
    public void whenOrderOxygenQuantityLessThanRemainingQuantity_thenOxygenHistoryIsNotUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_REMAINING_QUANTITY);

        verify(oxygenOrderGradeA, never()).updateOxygenHistory(any(), any(), anyInt());
    }

    @Test
    public void whenOrderOxygenQuantityLessThanRemainingQuantity_thenOxygenInventoryIsNotUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_REMAINING_QUANTITY);

        int actualInventory = oxygenInventoryGradeA.getInventory();
        assertEquals(SOME_QUANTITY, actualInventory);
    }

    @Test
    public void whenOrderOxygenQuantityLessThanRemainingQuantity_thenQuantityRemainingIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_REMAINING_QUANTITY);

        int expectedRemainingQuantity = SOME_REMAINING_QUANTITY - QUANTITY_LESS_THAN_REMAINING_QUANTITY;
        int actualRemainingQuantity = oxygenInventoryGradeA.getRemainingQuantity();
        assertEquals(expectedRemainingQuantity, actualRemainingQuantity);
    }

    @Test
    public void whenOrderOxygenQuantityMoreThanRemainingQuantity_thenOxygenHistoryIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        int quantityToProduce = QUANTITY_MORE_THAN_REMAINING_QUANTITY - SOME_REMAINING_QUANTITY;
        verify(oxygenOrderGradeA).updateOxygenHistory(someOxygenHistory, SOME_ORDER_DATE, quantityToProduce);
    }

    @Test
    public void whenOrderOxygenQuantityMoreThanRemainingQuantity_thenOxygenInventoryIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        int expectedInventory = SOME_QUANTITY + SOME_QUANTITY_TO_RESERVE;
        int actualInventory = oxygenInventoryGradeA.getInventory();
        assertEquals(expectedInventory, actualInventory);
    }

    @Test
    public void whenOrderOxygenQuantityMoreThanRemainingQuantity_thenQuantityRemainingIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        int expectedRemainingQuantity = 0;
        int actualRemainingQuantity = oxygenInventoryGradeA.getRemainingQuantity();
        assertEquals(expectedRemainingQuantity, actualRemainingQuantity);
    }

    @Test void whenOrderOxygenGradeATooLateForGradeA_thenInventoryOfGradeBIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A, OxygenGrade.A, LARGE_QUANTITY);

        int expectedInventory = SOME_QUANTITY + SOME_QUANTITY_TO_RESERVE;
        int actualInventory = oxygenInventoryGradeB.getInventory();
        assertEquals(expectedInventory, actualInventory);
    }

    @Test void whenOrderOxygenGradeATooLateForGradeB_thenInventoryOfGradeEIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_B, OxygenGrade.A, LARGE_QUANTITY);

        int expectedInventory = SOME_QUANTITY + SOME_QUANTITY_TO_RESERVE;
        int actualInventory = oxygenInventoryGradeE.getInventory();
        assertEquals(expectedInventory, actualInventory);
    }

    @Test void whenOrderOxygenGradeATooLateForGradeB_thenRemainingQuantityOfGradeAIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A, OxygenGrade.A, LARGE_QUANTITY);

        int expectedRemainingQuantity = 0;
        int actualRemainingQuantity = oxygenInventoryGradeA.getRemainingQuantity();
        assertEquals(expectedRemainingQuantity, actualRemainingQuantity);
    }

    @Test void whenOrderOxygenGradeATooLateForGradeB_thenRemainingQuantityOfGradeBIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A, OxygenGrade.A, LARGE_QUANTITY);

        int expectedRemainingQuantity = 0;
        int actualRemainingQuantity = oxygenInventoryGradeB.getRemainingQuantity();
        assertEquals(expectedRemainingQuantity, actualRemainingQuantity);
    }

    @Test void whenOrderOxygenGradeATooLateForGradeA_thenOxygenHistoryOfGradeBIsUpdated() {
        when(oxygenOrderGradeA.enoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A)).thenReturn(false);
        when(oxygenOrderGradeB.enoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A)).thenReturn(true);

        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A, OxygenGrade.A, LARGE_QUANTITY);

        int quantityToProduce = LARGE_QUANTITY - 2 * SOME_REMAINING_QUANTITY;
        verify(oxygenOrderGradeB).updateOxygenHistory(someOxygenHistory, SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A, quantityToProduce);
    }

    @Test void whenOrderOxygenGradeATooLateForGradeB_thenOxygenHistoryOfGradeEIsUpdated() {
        when(oxygenOrderGradeA.enoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A)).thenReturn(false);
        when(oxygenOrderGradeB.enoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A)).thenReturn(false);
        when(oxygenOrderGradeE.enoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A)).thenReturn(true);

        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A, OxygenGrade.A, LARGE_QUANTITY);

        int quantityToProduce = LARGE_QUANTITY - 2 * SOME_REMAINING_QUANTITY;
        verify(oxygenOrderGradeE).updateOxygenHistory(someOxygenHistory, SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A, quantityToProduce);
    }

    private void mockOxygenOrders() {
        mockOxygenOrderGradeA();
        mockOxygenOrderGradeB();
        mockOxygenOrderGradeE();
    }

    private void mockOxygenOrderGradeA() {
        oxygenOrderGradeA = mock(OxygenOrder.class);

        when(oxygenOrderGradeA.getQuantityToReserve(any(), anyInt())).thenReturn(SOME_QUANTITY_TO_RESERVE);
        when(oxygenOrderGradeA.enoughTimeToFabricate(SOME_ORDER_DATE)).thenReturn(true);
        when(oxygenOrderGradeA.enoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A)).thenReturn(false);
    }

    private void mockOxygenOrderGradeB() {
        oxygenOrderGradeB = mock(OxygenOrder.class);

        when(oxygenOrderGradeB.getQuantityToReserve(any(), anyInt())).thenReturn(SOME_QUANTITY_TO_RESERVE);
        when(oxygenOrderGradeB.enoughTimeToFabricate(SOME_ORDER_DATE)).thenReturn(true);
        when(oxygenOrderGradeB.enoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A)).thenReturn(true);
        when(oxygenOrderGradeB.enoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_B)).thenReturn(false);
    }

    private void mockOxygenOrderGradeE() {
        oxygenOrderGradeE = mock(OxygenOrder.class);

        when(oxygenOrderGradeE.getQuantityToReserve(any(), anyInt())).thenReturn(SOME_QUANTITY_TO_RESERVE);
        when(oxygenOrderGradeE.enoughTimeToFabricate(SOME_ORDER_DATE)).thenReturn(true);
        when(oxygenOrderGradeE.enoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A)).thenReturn(true);
        when(oxygenOrderGradeE.enoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_B)).thenReturn(true);
    }

    private void mockOxygenOrderFactory() {
        oxygenOrderFactory = mock(OxygenOrderFactory.class);

        when(oxygenOrderFactory.create(OxygenGrade.A)).thenReturn(oxygenOrderGradeA);
        when(oxygenOrderFactory.create(OxygenGrade.B)).thenReturn(oxygenOrderGradeB);
        when(oxygenOrderFactory.create(OxygenGrade.E)).thenReturn(oxygenOrderGradeE);
    }

    private void initializeOxygenInventories() {
        oxygenInventoryGradeA = new OxygenInventory(OxygenGrade.A, SOME_QUANTITY, SOME_REMAINING_QUANTITY);
        oxygenInventoryGradeB = new OxygenInventory(OxygenGrade.B, SOME_QUANTITY, SOME_REMAINING_QUANTITY);
        oxygenInventoryGradeE = new OxygenInventory(OxygenGrade.E, SOME_QUANTITY, 0);
    }

    private void mockOxygenInventoryRepository() {
        oxygenInventoryRepository = mock(OxygenInventoryRepository.class);

        when(oxygenInventoryRepository.findByGrade(OxygenGrade.A)).thenReturn(oxygenInventoryGradeA);
        when(oxygenInventoryRepository.findByGrade(OxygenGrade.B)).thenReturn(oxygenInventoryGradeB);
        when(oxygenInventoryRepository.findByGrade(OxygenGrade.E)).thenReturn(oxygenInventoryGradeE);
    }

    private void mockOxygenHistoryRepository() {
        oxygenHistoryRepository = mock(OxygenHistoryRepository.class);

        when(oxygenHistoryRepository.findOxygenHistory()).thenReturn(someOxygenHistory);
    }
}
