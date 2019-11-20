package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import java.time.LocalDate;
import java.util.Optional;

import ca.ulaval.glo4002.booking.helpers.dates.DateCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplier;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettings;

public class OxygenOrderer {

    private Optional<OxygenOrderer> nextOrderer;
    private OxygenRequestSettings requestSettings;
    private OxygenSupplier oxygenSupplier;
    private LocalDate limitDate;
    private OxygenInventoryRepository oxygenInventory;

    public OxygenOrderer(OxygenRequestSettings requestSettings, OxygenSupplier oxygenSupplier, LocalDate limitDate, OxygenInventoryRepository oxygenInventory) {
        nextOrderer = Optional.empty();

        this.requestSettings = requestSettings;
        this.oxygenSupplier = oxygenSupplier;
        this.limitDate = limitDate;
        this.oxygenInventory = oxygenInventory;
    }

    public void setNextOrderer(OxygenOrderer nextOrderer) {
        this.nextOrderer = Optional.of(nextOrderer);
    }

    public OxygenGrade getMinimumProducedGrade() {
        return requestSettings.getGrade();
    }

	public void order(LocalDate orderDate, int requestedQuantity) {
        int numberOfDaysUntilLimitDate = DateCalculator.numberOfDaysInclusivelyBetween(orderDate, limitDate);
        
        if (numberOfDaysUntilLimitDate < requestSettings.getNumberOfDaysToReceive()) {
            delegateToNextOrderer(orderDate, requestedQuantity);
            return;
        }

        OxygenInventoryEntry inventoryEntry = oxygenInventory.find(requestSettings.getGrade());
        orderOxygenIfNeeded(orderDate, requestedQuantity, inventoryEntry);
        inventoryEntry.useQuantity(requestedQuantity);
        oxygenInventory.save(inventoryEntry);
    }

    private void delegateToNextOrderer(LocalDate orderDate, int requestedQuantity) {
        if (nextOrderer.isPresent()) {
            nextOrderer.get().order(orderDate, requestedQuantity);
        } else {
            throw new RuntimeException("Not enough time to produce");
        }
    }

    private void orderOxygenIfNeeded(LocalDate orderDate, int requestedQuantity, OxygenInventoryEntry inventoryEntry) {
        int quantityRemaining = inventoryEntry.getSurplusQuantity();
        if (quantityRemaining < requestedQuantity) {
            oxygenSupplier.supply(orderDate, requestedQuantity - quantityRemaining, inventoryEntry);
        }
    }
}
