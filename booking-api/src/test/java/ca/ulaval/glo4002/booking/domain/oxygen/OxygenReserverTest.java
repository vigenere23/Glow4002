package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

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
    private final static int QUANTITY_MORE_THAN_REMAINING_QUANTITY = 20;
    private final static int SOME_QUANTITY_TO_RESERVE = 5;
    private final static LocalDate FESTIVAL_STARTING_DATE = LocalDate.of(2050, 7, 17);
    private final static LocalDate SOME_ORDER_DATE = FESTIVAL_STARTING_DATE.minusMonths(1);
    private final static LocalDate SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A = FESTIVAL_STARTING_DATE.minusDays(15);
    private final static LocalDate SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_B = FESTIVAL_STARTING_DATE.minusDays(5);

    SortedMap<LocalDate, OxygenHistoryItem> someOrderHistory = new TreeMap<>();
    OxygenHistoryItem someOxygenHistoryItem = new OxygenHistoryItem(SOME_ORDER_DATE);
    private OxygenInventory oxygenInventoryGradeA;
    private OxygenInventory oxygenInventoryGradeB;
    private OxygenInventory oxygenInventoryGradeE;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;
    private OxygenOrder oxygenOrderGradeA;
    private OxygenOrder oxygenOrderGradeB;
    private OxygenOrder oxygenOrderGradeE;
    private OxygenReserver oxygenReserver;
    private OutcomeSaver outcomeSaver;

    @BeforeEach
    public void setUp() {
        mockOxygenOrders();
        OxygenOrderFactory oxygenOrderFactory = mockOxygenOrderFactory();
        initializeOxygenOrderHistory();
        initializeOxygenInventories();
        mockOxygenInventoryRepository();
        mockOxygenHistoryRepository();
        outcomeSaver = mock(OutcomeSaver.class);
        oxygenReserver = new OxygenReserver(oxygenOrderFactory, oxygenInventoryRepository, oxygenHistoryRepository, outcomeSaver);
    }

    @Test
    public void whenOrderOxygen_thenOxygenInventoryIsSaved() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_REMAINING_QUANTITY);

        verify(oxygenInventoryRepository).save(oxygenInventoryGradeA);
    }

    @Test
    public void whenOrderOxygenQuantityLessThanRemainingQuantity_thenOxygenHistoryIsNotUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_REMAINING_QUANTITY);

        verify(oxygenHistoryRepository, never()).save(any());
    }

    @Test
    public void whenOrderOxygenQuantityLessThanRemainingQuantity_thenOxygenInventoryIsNotUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_REMAINING_QUANTITY);

        assertEquals(SOME_QUANTITY, oxygenInventoryGradeA.getInventory());
    }

    @Test
    public void whenOrderOxygenQuantityLessThanRemainingQuantity_thenQuantityRemainingIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_REMAINING_QUANTITY);

        int expectedRemainingQuantity = SOME_REMAINING_QUANTITY - QUANTITY_LESS_THAN_REMAINING_QUANTITY;
        assertEquals(expectedRemainingQuantity, oxygenInventoryGradeA.getRemainingQuantity());
    }

    @Test
    public void whenOrderOxygenQuantityMoreThanRemainingQuantity_thenOxygenHistoryIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        verify(oxygenOrderGradeA).getOxygenOrderHistory(SOME_ORDER_DATE);
    }

    @Test
    public void whenOrderOxygenQuantityMoreThanRemainingQuantity_thenOxygenHistoryISavedTwice() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        verify(oxygenHistoryRepository, times(2)).save(someOxygenHistoryItem);
    }

    @Test
    public void whenOrderOxygenQuantityMoreThanRemainingQuantity_thenOxygenInventoryIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        int expectedInventory = SOME_QUANTITY + SOME_QUANTITY_TO_RESERVE;
        assertEquals(expectedInventory, oxygenInventoryGradeA.getInventory());
    }

    @Test
    public void whenOrderOxygenQuantityMoreThanRemainingQuantity_thenQuantityRemainingIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        int expectedRemainingQuantity = 0;
        assertEquals(expectedRemainingQuantity, oxygenInventoryGradeA.getRemainingQuantity());
    }

    @Test void whenOrderOxygenGradeATooLateForGradeA_thenInventoryOfGradeBIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        int expectedInventory = SOME_QUANTITY + SOME_QUANTITY_TO_RESERVE;
        assertEquals(expectedInventory, oxygenInventoryGradeB.getInventory());
    }

    @Test void whenOrderOxygenGradeATooLateForGradeB_thenInventoryOfGradeEIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_B, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        int expectedInventory = SOME_QUANTITY + SOME_QUANTITY_TO_RESERVE;
        assertEquals(expectedInventory, oxygenInventoryGradeE.getInventory());
    }

    @Test void whenOrderOxygenGradeATooLateForGradeB_thenRemainingQuantityOfGradeAIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        int expectedRemainingQuantity = 0;
        assertEquals(expectedRemainingQuantity, oxygenInventoryGradeA.getRemainingQuantity());
    }

    @Test void whenOrderOxygenGradeATooLateForGradeB_thenRemainingQuantityOfGradeBIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        int expectedRemainingQuantity = 0;
        assertEquals(expectedRemainingQuantity, oxygenInventoryGradeB.getRemainingQuantity());
    }

    @Test void whenOrderOxygenGradeATooLateForGradeA_thenOxygenHistoryOfGradeBIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        verify(oxygenOrderGradeB).getOxygenOrderHistory(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A);
    }

    @Test void whenOrderOxygenGradeATooLateForGradeB_thenOxygenHistoryOfGradeEIsUpdated() {
        oxygenReserver.reserveOxygen(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_B, OxygenGrade.A, QUANTITY_MORE_THAN_REMAINING_QUANTITY);

        verify(oxygenOrderGradeE).getOxygenOrderHistory(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_B);
    }

    private void mockOxygenOrders() {
        mockOxygenOrderGradeA();
        mockOxygenOrderGradeB();
        mockOxygenOrderGradeE();
    }

    private void mockOxygenOrderGradeA() {
        oxygenOrderGradeA = mock(OxygenOrder.class);

        when(oxygenOrderGradeA.getOxygenOrderHistory(any())).thenReturn(someOrderHistory);
        when(oxygenOrderGradeA.getQuantityToReserve(any(), anyInt())).thenReturn(SOME_QUANTITY_TO_RESERVE);
        when(oxygenOrderGradeA.isEnoughTimeToFabricate(SOME_ORDER_DATE)).thenReturn(true);
        when(oxygenOrderGradeA.isEnoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A)).thenReturn(false);
    }

    private void mockOxygenOrderGradeB() {
        oxygenOrderGradeB = mock(OxygenOrder.class);

        when(oxygenOrderGradeB.getOxygenOrderHistory(any())).thenReturn(someOrderHistory);
        when(oxygenOrderGradeB.getQuantityToReserve(any(), anyInt())).thenReturn(SOME_QUANTITY_TO_RESERVE);
        when(oxygenOrderGradeB.isEnoughTimeToFabricate(SOME_ORDER_DATE)).thenReturn(true);
        when(oxygenOrderGradeB.isEnoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A)).thenReturn(true);
        when(oxygenOrderGradeB.isEnoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_B)).thenReturn(false);
    }

    private void mockOxygenOrderGradeE() {
        oxygenOrderGradeE = mock(OxygenOrder.class);

        when(oxygenOrderGradeE.getOxygenOrderHistory(any())).thenReturn(someOrderHistory);
        when(oxygenOrderGradeE.getQuantityToReserve(any(), anyInt())).thenReturn(SOME_QUANTITY_TO_RESERVE);
        when(oxygenOrderGradeE.isEnoughTimeToFabricate(SOME_ORDER_DATE)).thenReturn(true);
        when(oxygenOrderGradeE.isEnoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_A)).thenReturn(true);
        when(oxygenOrderGradeE.isEnoughTimeToFabricate(SOME_ORDER_DATE_TOO_LATE_FOR_GRADE_B)).thenReturn(true);
    }

    private OxygenOrderFactory mockOxygenOrderFactory() {
        OxygenOrderFactory oxygenOrderFactory = mock(OxygenOrderFactory.class);

        when(oxygenOrderFactory.create(OxygenGrade.A)).thenReturn(oxygenOrderGradeA);
        when(oxygenOrderFactory.create(OxygenGrade.B)).thenReturn(oxygenOrderGradeB);
        when(oxygenOrderFactory.create(OxygenGrade.E)).thenReturn(oxygenOrderGradeE);

        return oxygenOrderFactory;
    }

    private void initializeOxygenInventories() {
        oxygenInventoryGradeA = new OxygenInventory(OxygenGrade.A, SOME_QUANTITY, SOME_REMAINING_QUANTITY);
        oxygenInventoryGradeB = new OxygenInventory(OxygenGrade.B, SOME_QUANTITY, SOME_REMAINING_QUANTITY);
        oxygenInventoryGradeE = new OxygenInventory(OxygenGrade.E, SOME_QUANTITY, 0);
    }

    private void initializeOxygenOrderHistory() {
        someOrderHistory.put(SOME_ORDER_DATE, someOxygenHistoryItem);
        someOrderHistory.put(SOME_ORDER_DATE.minusDays(1), someOxygenHistoryItem);
    }

    private void mockOxygenInventoryRepository() {
        oxygenInventoryRepository = mock(OxygenInventoryRepository.class);

        when(oxygenInventoryRepository.findByGrade(OxygenGrade.A)).thenReturn(oxygenInventoryGradeA);
        when(oxygenInventoryRepository.findByGrade(OxygenGrade.B)).thenReturn(oxygenInventoryGradeB);
        when(oxygenInventoryRepository.findByGrade(OxygenGrade.E)).thenReturn(oxygenInventoryGradeE);
    }

    private void mockOxygenHistoryRepository() {
        oxygenHistoryRepository = mock(OxygenHistoryRepository.class);

        when(oxygenHistoryRepository.findOxygenHistoryOfDate(any())).thenReturn(someOxygenHistoryItem);
    }
}
