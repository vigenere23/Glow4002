package ca.ulaval.glo4002.booking.domain.oxygen;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen.History;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OxygenRequesterTests {

    private OxygenInventoryRepository oxygenInventory;
    private OxygenHistoryRepository oxygenHistory;
    private OxygenRequester oxygenRequester;
    private final static LocalDate FESTIVAL_STARTING_DATE = LocalDate.of(2050, 7, 17);
    private final static LocalDate ONE_MONTH_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusMonths(1);
    private final static LocalDate DELIVERY_DATE_GRADE_A_ORDER = LocalDate.of(2050, 7, 7);
    private final static LocalDate FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusDays(15);
    private final static LocalDate FIVE_DAYS_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusDays(5);

    @BeforeEach
    public void testInitialize() {
        oxygenInventory = mock(HeapOxygenInventoryRepository.class);
        oxygenHistory = mock(HeapOxygenHistoryRepository.class);
        when(oxygenHistory.findCreationHistoryPerDate(ONE_MONTH_BEFORE_FESTIVAL_DATE)).thenReturn(new History());
        when(oxygenHistory.findCreationHistoryPerDate(DELIVERY_DATE_GRADE_A_ORDER)).thenReturn(new History());
        when(oxygenHistory.findCreationHistoryPerDate(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE)).thenReturn(new History());
        when(oxygenHistory.findCreationHistoryPerDate(FIVE_DAYS_BEFORE_FESTIVAL_DATE)).thenReturn(new History());
        oxygenRequester = new OxygenRequester(FESTIVAL_STARTING_DATE, oxygenHistory, oxygenInventory);
    }

    @Test
    public void whenQuantityToProduceIsLowerThanRemainingQuantity_thenCreationHistoryIsNotUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(3);
        oxygenRequester.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenHistory, never()).saveCreationHistory(any(LocalDate.class), any(History.class));
    }

    @Test
    public void whenQuantityToProduceIsLowerThanRemainingQuantity_thenOxygenRemainingIsUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(3);
        oxygenRequester.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).saveOxygenRemaining(OxygenGrade.A, 1);
    }

    @Test
    public void whenQuantityToProduceIsHigherThanRemainingQuantity_thenOxygenRemainingIsUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        oxygenRequester.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).saveOxygenRemaining(OxygenGrade.A, 3);
    }

    @Test
    public void whenQuantityToProduceIsHigherThanRemainingQuantity_thenOxygenInventoryIsUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        when(oxygenInventory.findInventoryOfGrade(OxygenGrade.A)).thenReturn(0);
        oxygenRequester.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).saveOxygenInventory(OxygenGrade.A, 5);
    }

    @Test
    public void givenAnInventory_whenQuantityToProduceIsHigherThanRemainingQuantity_thenOxygenInventoryIsUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        when(oxygenInventory.findInventoryOfGrade(OxygenGrade.A)).thenReturn(3);
        oxygenRequester.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).saveOxygenInventory(OxygenGrade.A, 8);
    }

    @Test
    public void whenQuantityToProduceIsHigherThanRemainingQuantity_thenOxygenHistoryIsUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        oxygenRequester.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenHistory, atLeast(2)).saveCreationHistory(any(LocalDate.class), any(History.class));
    }

    @Test
    public void givenNoRemainingGradeA_whenTooLateToOrderGradeA_thenOxygenRemainingGradeBIsUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).saveOxygenRemaining(OxygenGrade.B, 1);
    }

    @Test
    public void givenRemainingGradeA_whenTooLateToOrderMoreGradeA_thenOxygenRemainingGradeAIsSetToZero() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 4);
        verify(oxygenInventory).saveOxygenRemaining(OxygenGrade.A, 0);
    }

    @Test
    public void givenRemainingGradeA_whenTooLateToOrderMoreGradeA_thenOxygenRemainingGradeBIsUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).saveOxygenRemaining(OxygenGrade.B, 2);
    }

    @Test
    public void givenRemainingGradeA_whenTooLateToOrderMoreGradeA_thenOxygenInventoryGradeBIsUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).saveOxygenInventory(OxygenGrade.B, 3);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeA_thenOxygenRemainingGradeBIsUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 3);
        verify(oxygenInventory).saveOxygenRemaining(OxygenGrade.B, 2);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeA_thenOxygenInventoryGradeBIsUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 3);
        verify(oxygenInventory).saveOxygenInventory(OxygenGrade.B, 3);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeAAndGradeB_thenOxygenRemainingGradeBIsSetToZero() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(FIVE_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 4);
        verify(oxygenInventory).saveOxygenRemaining(OxygenGrade.B, 0);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeAAndGradeB_thenOxygenInventoryOfGradeEIsUpdated() {
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.findOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(FIVE_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 4);
        verify(oxygenInventory).saveOxygenInventory(OxygenGrade.E, 2);
    }
}