package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import static org.mockito.Mockito.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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
    private final static OffsetDateTime festivalStartingDate = OffsetDateTime.of(2050, 7, 17, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime oneMonthBeforeFestivalDate = OffsetDateTime.of(2050, 6, 17, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime deliveryDateGradeAOrder = OffsetDateTime.of(2050, 7, 7, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime fifteenDaysBeforeFestivalDate = OffsetDateTime.of(2050, 7, 2, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime fiveDaysBeforeFestivalDate = OffsetDateTime.of(2050, 7, 12, 0, 0, 0, 0, ZoneOffset.UTC);

    @BeforeEach
    public void testInitialize() {
        OxygenPersistance oxygenPersistance = mock(HeapOxygenPersistance.class);
        oxygenInventory = mock(HeapOxygenInventory.class);
        oxygenHistory = mock(HeapOxygenHistory.class);
        when(oxygenPersistance.getOxygenInventory()).thenReturn(oxygenInventory);
        when(oxygenPersistance.getOxygenHistory()).thenReturn(oxygenHistory);
        when(oxygenHistory.getCreationHistoryPerDate(oneMonthBeforeFestivalDate)).thenReturn(new History());
        when(oxygenHistory.getCreationHistoryPerDate(deliveryDateGradeAOrder)).thenReturn(new History());
        when(oxygenHistory.getCreationHistoryPerDate(fifteenDaysBeforeFestivalDate)).thenReturn(new History());
        when(oxygenHistory.getCreationHistoryPerDate(fiveDaysBeforeFestivalDate)).thenReturn(new History());
        oxygenRequester = new OxygenRequester(festivalStartingDate, oxygenPersistance);
    }

    @Test
    public void whenQuantityToProduceIsLowerThanRemainingQuantity_thenCreationHistoryIsNotUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(3);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 2);
        verify(oxygenHistory, never()).updateCreationHistory(any(OffsetDateTime.class), any(History.class));
    }

    @Test
    public void whenQuantityToProduceIsLowerThanRemainingQuantity_thenOxygenRemainingIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(3);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.A, 1);
    }

    @Test
    public void whenQuantityToProduceIsHigherThanRemainingQuantity_thenOxygenRemainingIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.A, 3);
    }

    @Test
    public void whenQuantityToProduceIsHigherThanRemainingQuantity_thenOxygenInventoryIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        when(oxygenInventory.getInventoryOfGrade(OxygenGrade.A)).thenReturn(0);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenInventory(OxygenGrade.A, 5);
    }

    @Test
    public void givenAnInventory_whenQuantityToProduceIsHigherThanRemainingQuantity_thenOxygenInventoryIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        when(oxygenInventory.getInventoryOfGrade(OxygenGrade.A)).thenReturn(3);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenInventory(OxygenGrade.A, 8);
    }

    @Test
    public void whenQuantityToProduceIsHigherThanRemainingQuantity_thenOxygenHistoryIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 2);
        verify(oxygenHistory, atLeast(2)).updateCreationHistory(any(OffsetDateTime.class), any(History.class));
    }

    @Test
    public void givenNoRemainingGradeA_whenTooLateToOrderGradeA_thenOxygenRemainingGradeBIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(fifteenDaysBeforeFestivalDate, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.B, 1);
    }

    @Test
    public void givenRemainingGradeA_whenTooLateToOrderMoreGradeA_thenOxygenRemainingGradeAIsSetToZero() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(fifteenDaysBeforeFestivalDate, OxygenGrade.A, 4);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.A, 0);
    }

    @Test
    public void givenRemainingGradeA_whenTooLateToOrderMoreGradeA_thenOxygenRemainingGradeBIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(fifteenDaysBeforeFestivalDate, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.B, 2);
    }

    @Test
    public void givenRemainingGradeA_whenTooLateToOrderMoreGradeA_thenOxygenInventoryGradeBIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        oxygenRequester.orderOxygen(fifteenDaysBeforeFestivalDate, OxygenGrade.A, 2);
        verify(oxygenInventory).setOxygenInventory(OxygenGrade.B, 3);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeA_thenOxygenRemainingGradeBIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(fifteenDaysBeforeFestivalDate, OxygenGrade.A, 3);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.B, 2);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeA_thenOxygenInventoryGradeBIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(fifteenDaysBeforeFestivalDate, OxygenGrade.A, 3);
        verify(oxygenInventory).setOxygenInventory(OxygenGrade.B, 3);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeAAndGradeB_thenOxygenRemainingGradeBIsSetToZero() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(fiveDaysBeforeFestivalDate, OxygenGrade.A, 4);
        verify(oxygenInventory).setOxygenRemaining(OxygenGrade.B, 0);
    }

    @Test
    public void givenRemainingGradeAAndGradeB_whenTooLateToOrderMoreGradeAAndGradeB_thenOxygenInventoryOfGradeEIsUpdated() {
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(1);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(1);
        oxygenRequester.orderOxygen(fiveDaysBeforeFestivalDate, OxygenGrade.A, 4);
        verify(oxygenInventory).setOxygenInventory(OxygenGrade.E, 2);
    }
}