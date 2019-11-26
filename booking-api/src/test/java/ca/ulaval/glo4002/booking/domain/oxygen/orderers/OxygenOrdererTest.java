package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettings;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplier;

@ExtendWith(MockitoExtension.class)
public class OxygenOrdererTest {

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate SOME_DATE = LocalDate.now();
    private static final int SOME_QUANTITY = 21;
    private static final OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    
    @Mock OxygenRequestSettings requestSettingsWithLongTimeToReceive;
    @Mock OxygenRequestSettings requestSettingsWithInstantTimeToReceive;
    @Mock OxygenDates oxygenDates;
    @Mock OxygenSupplier oxygenSupplier;
    @Mock HeapOxygenInventoryRepository oxygenInventory;
    @Mock OxygenInventoryEntry inventoryEntry;

    @BeforeEach
    public void setup() {
        when(oxygenDates.getOxygenLimitDeliveryDate()).thenReturn(NOW);
        when(oxygenInventory.find(SOME_OXYGEN_GRADE)).thenReturn(inventoryEntry);
    }

    @Test
    public void givenNoNextOrdererAndNoTimeToSupply_whenOrdering_itThrowsARuntimeException() {
        setupRequestSettingsWithLongTimeToReceive();
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithLongTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory);
        
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> {
            oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        });
    }

    @Test
    public void givenNextOrdererAvailableAndNoTimeToSupply_whenOrdering_itOrdersToTheNextOrderer() {
        setupRequestSettingsWithInstantTimeToReceive();
        setupRequestSettingsWithLongTimeToReceive();
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithLongTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory);
        OxygenOrderer nextOxygenOrderer = spy(new OxygenOrderer(requestSettingsWithInstantTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory));
        oxygenOrderer.setNextOrderer(nextOxygenOrderer);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);

        verify(nextOxygenOrderer).order(SOME_DATE, SOME_QUANTITY);
    }

    @Test
    public void givenNextOrdererAvailableAndNoTimeToSupply_whenOrdering_itUsesAllTheSurplusOxygenBeforeOrderingToTheNextOrderer() {
        setupRequestSettingsWithInstantTimeToReceive();
        setupRequestSettingsWithLongTimeToReceive();
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithLongTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory);
        OxygenOrderer nextOxygenOrderer = spy(new OxygenOrderer(requestSettingsWithInstantTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory));
        oxygenOrderer.setNextOrderer(nextOxygenOrderer);
        int surplus = SOME_QUANTITY - 1;
        when(inventoryEntry.getSurplusQuantity()).thenReturn(surplus);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);

        verify(inventoryEntry).useQuantity(surplus);
    }

    @Test
    public void givenNextOrdererAvailableAndNoTimeToSupply_whenOrdering_itOrdersToTheNextOrdererWithSurplusRemovedFromQuantityNeeded() {
        setupRequestSettingsWithInstantTimeToReceive();
        setupRequestSettingsWithLongTimeToReceive();
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithLongTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory);
        OxygenOrderer nextOxygenOrderer = spy(new OxygenOrderer(requestSettingsWithInstantTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory));
        oxygenOrderer.setNextOrderer(nextOxygenOrderer);
        int surplus = SOME_QUANTITY - 1;
        when(inventoryEntry.getSurplusQuantity()).thenReturn(surplus);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);

        verify(nextOxygenOrderer).order(SOME_DATE, SOME_QUANTITY - surplus);
    }

    @Test
    public void givenEmptyInventory_whenOrdering_itAskSupplierWithQuantityNeeded() {
        setupRequestSettingsWithInstantTimeToReceive();
        when(inventoryEntry.getSurplusQuantity()).thenReturn(0);
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithInstantTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        
        verify(oxygenSupplier).supply(SOME_DATE, SOME_QUANTITY, inventoryEntry);
    }

    @Test
    public void givenInventoryWithSmallerQuantityThanNeeded_whenOrdering_itAskSupplierWithQuantityNeededMinusInventoryQuantity() {
        setupRequestSettingsWithInstantTimeToReceive();
        int inventoryQuantity = SOME_QUANTITY - 1;
        when(inventoryEntry.getSurplusQuantity()).thenReturn(inventoryQuantity);
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithInstantTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        
        verify(oxygenSupplier).supply(SOME_DATE, SOME_QUANTITY - inventoryQuantity, inventoryEntry);
    }

    @Test
    public void givenInventoryWithSameQuantityThanNeeded_whenOrdering_itDoesNotAskTheSupplier() {
        setupRequestSettingsWithInstantTimeToReceive();
        when(inventoryEntry.getSurplusQuantity()).thenReturn(SOME_QUANTITY);
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithInstantTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        
        verify(oxygenSupplier, times(0)).supply(any(LocalDate.class), any(Integer.class), any(OxygenInventoryEntry.class));
    }

    @Test
    public void givenInventoryWithGreaterQuantityThanNeeded_whenOrdering_itDoesNotAskTheSupplier() {
        setupRequestSettingsWithInstantTimeToReceive();
        when(inventoryEntry.getSurplusQuantity()).thenReturn(SOME_QUANTITY + 1);
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithInstantTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        
        verify(oxygenSupplier, times(0)).supply(any(LocalDate.class), any(Integer.class), any(OxygenInventoryEntry.class));
    }

    @Test
    public void whenOrdering_itRemovesTheRequestedQuantityFromTheInventory() {
        setupRequestSettingsWithInstantTimeToReceive();
        OxygenOrderer oxygenOrderer = new OxygenOrderer(requestSettingsWithInstantTimeToReceive, oxygenSupplier, oxygenDates, oxygenInventory);
        
        oxygenOrderer.order(SOME_DATE, SOME_QUANTITY);
        
        verify(inventoryEntry).useQuantity(SOME_QUANTITY);
        verify(oxygenInventory).save(inventoryEntry);
    }

    private void setupRequestSettingsWithInstantTimeToReceive() {
        when(requestSettingsWithInstantTimeToReceive.getNumberOfDaysToReceive()).thenReturn(0);
        when(requestSettingsWithInstantTimeToReceive.getGrade()).thenReturn(SOME_OXYGEN_GRADE);
    }

    private void setupRequestSettingsWithLongTimeToReceive() {
        when(requestSettingsWithLongTimeToReceive.getNumberOfDaysToReceive()).thenReturn(100);
        when(requestSettingsWithLongTimeToReceive.getGrade()).thenReturn(SOME_OXYGEN_GRADE);
    }
}
