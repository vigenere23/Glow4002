package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenPersistance;
import ca.ulaval.glo4002.booking.persistance.heap.HeapOxygenHistory;
import ca.ulaval.glo4002.booking.persistance.heap.HeapOxygenInventory;
import ca.ulaval.glo4002.booking.persistance.heap.HeapOxygenPersistance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OxygenRequesterTests {

    private OxygenInventory oxygenInventory;
    private OxygenHistory oxygenHistory;
    private OxygenRequester oxygenRequester;
    private final static LocalDate FESTIVAL_STARTING_DATE = LocalDate.of(2050, 7, 17);
    private final static LocalDate ONE_MONTH_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusMonths(1);
    private final static LocalDate DELIVERY_DATE_GRADE_A_ORDER = LocalDate.of(2050, 7, 7);
    private final static LocalDate FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusDays(15);
    private final static LocalDate FIVE_DAYS_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusDays(5);

    @BeforeEach
    public void testInitialize() {
        OxygenPersistance oxygenPersistance = mock(HeapOxygenPersistance.class);
        oxygenInventory = mock(HeapOxygenInventory.class);
        oxygenHistory = mock(HeapOxygenHistory.class);
        when(oxygenPersistance.getOxygenInventory()).thenReturn(oxygenInventory);
        when(oxygenPersistance.getOxygenHistory()).thenReturn(oxygenHistory);
        when(oxygenHistory.getCreationHistoryPerDate(ONE_MONTH_BEFORE_FESTIVAL_DATE)).thenReturn(new History());
        when(oxygenHistory.getCreationHistoryPerDate(DELIVERY_DATE_GRADE_A_ORDER)).thenReturn(new History());
        when(oxygenHistory.getCreationHistoryPerDate(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE)).thenReturn(new History());
        when(oxygenHistory.getCreationHistoryPerDate(FIVE_DAYS_BEFORE_FESTIVAL_DATE)).thenReturn(new History());
        oxygenRequester = new OxygenRequester(FESTIVAL_STARTING_DATE, oxygenPersistance);
    }

    @Test
    public void whenQuantityToProduceIsLowerThanRemainingQuantity_thenCreationHistoryIsNotUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(3);
        oxygenRequester.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenHistory, never()).updateCreationHistory(any(LocalDate.class), any(History.class));
    }

    @Test
    public void whenQuantityToProduceIsLowerThanRemainingQuantity_thenOxygenRemainingIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(3);
        oxygenRequester.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.A, 1);
    }

    @Test
    public void whenQuantityToProduceIsHigherThanRemainingQuantity_thenOxygenRemainingIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        oxygenRequester.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.A, 3);
    }

    @Test
    public void whenQuantityToProduceIsHigherThanRemainingQuantity_thenOxygenInventoryIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        oxygenRequester.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenInventory(OxygenGrade.A, 5);
    }

    @Test
    public void whenQuantityToProduceIsHigherThanRemainingQuantity_thenOxygenHistoryIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        oxygenRequester.orderOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenHistory, atLeast(2)).updateCreationHistory(any(LocalDate.class), any(History.class));
    }

    @Test
    public void givenNoRemainingGradeA_whenTooLateToOrderGradeA_thenOxygenRemainingGradeBIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.B, 1);
    }

    @Test
    public void givenRemainingGradeA_whenTooLateToOrderMoreGradeA_thenOxygenRemainingGradeAIsSetToZero() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 4);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.A, 0);
    }

    @Test
    public void givenRemainingGradeA_whenTooLateToOrderMoreGradeA_thenOxygenRemainingGradeBIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.B, 2);
    }

    @Test
    public void givenRemainingGradeA_whenTooLateToOrderMoreGradeA_thenOxygenInventoryGradeBIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenInventory(OxygenGrade.B, 3);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeA_thenOxygenRemainingGradeBIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 3);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.B, 2);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeA_thenOxygenInventoryGradeBIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 3);
        verify(oxygenInventory).setOxygenInventory(OxygenGrade.B, 3);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeAAndGradeB_thenOxygenRemainingGradeBIsSetToZero() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(FIVE_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 4);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.B, 0);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeAAndGradeB_thenOxygenInventoryOfGradeEIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(FIVE_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.A, 4);
        verify(oxygenInventory).setOxygenInventory(OxygenGrade.E, 2);
    }
}