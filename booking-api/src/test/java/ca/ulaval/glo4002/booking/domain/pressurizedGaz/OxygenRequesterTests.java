package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import static org.junit.jupiter.api.Assertions.*;
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

    OxygenProductionResults oxygenProductionResults;
    OxygenPersistance oxygenPersistance;
    OxygenInventory oxygenInventory;
    OxygenHistory oxygenHistory;
    private OxygenRequester oxygenRequester;
    private final static OffsetDateTime festivalStartingDate = OffsetDateTime.of(2050, 7, 17, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime oneMonthBeforeFestivalDate = OffsetDateTime.of(2050, 6, 17, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime completionDateGradeAOrder = OffsetDateTime.of(2050, 7, 7, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime fifteenDaysBeforeFestivalDate = OffsetDateTime.of(2050, 7, 2, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime fiveDaysBeforeFestivalDate = OffsetDateTime.of(2050, 7, 12, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime duringFestival = OffsetDateTime.of(2050, 7, 18, 0, 0, 0, 0, ZoneOffset.UTC);

    @BeforeEach
    public void testInitialize() {
        oxygenProductionResults = mock(OxygenProductionResults.class);
        oxygenPersistance = mock(HeapOxygenPersistance.class);
        oxygenInventory = mock(HeapOxygenInventory.class);
        oxygenHistory = mock(HeapOxygenHistory.class);
        when(oxygenPersistance.getOxygenInventory()).thenReturn(oxygenInventory);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(0);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.B)).thenReturn(0);
        when(oxygenInventory.getOxygenRemaining(OxygenGrade.E)).thenReturn(0);
        when(oxygenPersistance.getOxygenHistory()).thenReturn(oxygenHistory);
        when(oxygenHistory.getCreationHistoryPerDate(oneMonthBeforeFestivalDate)).thenReturn(new History());
        when(oxygenHistory.getCreationHistoryPerDate(completionDateGradeAOrder)).thenReturn(new History());
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

//    @Test
//    public void whenQuantityToProduceIsHigherThanRemainingQuantity_thenCreationHistoryOfOrderDateIsUpdated() {
//        when(oxygenInventory.getOxygenRemaining(OxygenGrade.A)).thenReturn(0);
//        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 2);
//        verify(oxygenHistory).updateCreationHistory(oneMonthBeforeFestivalDate, oxygenProductionResults.orderDateHistory);
//    }
}