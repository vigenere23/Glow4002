package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettings;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplier;

public class OxygenOrdererTest {

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate SOME_DATE = LocalDate.now();
    private static final int SOME_QUANTITY = 21;
    private static final OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    
    private OxygenDates someOxygenDates;
    private OxygenSupplier oxygenSupplier;
    private HeapOxygenInventoryRepository oxygenInventory;
    private OxygenInventoryEntry inventoryEntry;
    private OxygenRequestSettings requestSettingsWithLongTimeToReceive;
    private OxygenRequestSettings requestSettingsWithNoTimeToReceive;

    @BeforeEach
    public void setup() {
        oxygenSupplier = mock(OxygenSupplier.class);
        oxygenInventory = mock(HeapOxygenInventoryRepository.class);
        inventoryEntry = mock(OxygenInventoryEntry.class);
        someOxygenDates = mock(OxygenDates.class);
        when(someOxygenDates.getOxygenLimitDeliveryDate()).thenReturn(NOW);

        when(oxygenInventory.find(SOME_OXYGEN_GRADE)).thenReturn(inventoryEntry);
        
        requestSettingsWithLongTimeToReceive = mock(OxygenRequestSettings.class);
        setupRequestSettings(requestSettingsWithLongTimeToReceive, 100);
        requestSettingsWithNoTimeToReceive = mock(OxygenRequestSettings.class);
        setupRequestSettings(requestSettingsWithNoTimeToReceive, 0);
    }

    @Test
    public void givenNoNextOrdererAndNoTimeToSupply_whenOrdering_itThrowsARuntimeException() {
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithLongTimeToReceive, oxygenSupplier, someOxygenDates, oxygenInventory);
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        });
    }

    @Test
    public void givenNextOrdererAvailableAndNoTimeToSupply_whenOrdering_itOrdersToTheNextOrderer() {
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithLongTimeToReceive, oxygenSupplier, someOxygenDates, oxygenInventory);
        OxygenOrderer nextOxygenOrderer = spy(new OxygenOrderer(requestSettingsWithNoTimeToReceive, oxygenSupplier, someOxygenDates, oxygenInventory));
        oxygenOrderer.setNextOrderer(nextOxygenOrderer);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        verify(nextOxygenOrderer).order(SOME_DATE, SOME_QUANTITY);
    }

    @Test
    public void givenEmptyInventory_whenOrdering_itAskSupplierWithQuantityNeeded() {
        when(inventoryEntry.getSurplusQuantity()).thenReturn(0);
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithNoTimeToReceive, oxygenSupplier, someOxygenDates, oxygenInventory);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        
        verify(oxygenSupplier).supply(SOME_DATE, SOME_QUANTITY, inventoryEntry);
    }

    @Test
    public void givenInventoryWithSmallerQuantityThanNeeded_whenOrdering_itAskSupplierWithQuantityNeededMinusInventoryQuantity() {
        int inventoryQuantity = SOME_QUANTITY - 1;
        when(inventoryEntry.getSurplusQuantity()).thenReturn(inventoryQuantity);
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithNoTimeToReceive, oxygenSupplier, someOxygenDates, oxygenInventory);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        
        verify(oxygenSupplier).supply(SOME_DATE, SOME_QUANTITY - inventoryQuantity, inventoryEntry);
    }

    @Test
    public void givenInventoryWithSameQuantityThanNeeded_whenOrdering_itDoesNotAskTheSupplier() {
        when(inventoryEntry.getSurplusQuantity()).thenReturn(SOME_QUANTITY);
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithNoTimeToReceive, oxygenSupplier, someOxygenDates, oxygenInventory);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        
        verify(oxygenSupplier, times(0)).supply(any(LocalDate.class), any(Integer.class), any(OxygenInventoryEntry.class));
    }

    @Test
    public void givenInventoryWithGreaterQuantityThanNeeded_whenOrdering_itDoesNotAskTheSupplier() {
        when(inventoryEntry.getSurplusQuantity()).thenReturn(SOME_QUANTITY + 1);
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithNoTimeToReceive, oxygenSupplier, someOxygenDates, oxygenInventory);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        
        verify(oxygenSupplier, times(0)).supply(any(LocalDate.class), any(Integer.class), any(OxygenInventoryEntry.class));
    }

    @Test
    public void whenOrdering_itRemovesTheRequestedQuantityFromTheInventory() {
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithNoTimeToReceive, oxygenSupplier, someOxygenDates, oxygenInventory);
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        verify(inventoryEntry).useQuantity(SOME_QUANTITY);
        verify(oxygenInventory).save(inventoryEntry);
    }

    private void setupRequestSettings(OxygenRequestSettings requestSettings, int daysToReceive) {
        when(requestSettings.getNumberOfDaysToReceive()).thenReturn(daysToReceive);
        when(requestSettings.getGrade()).thenReturn(SOME_OXYGEN_GRADE);
    }
}
