package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettings;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplier;

public class OxygenOrderer {

    private Optional<OxygenOrderer> nextOrderer;
    private OxygenRequestSettings requestSettings;
    private OxygenSupplier oxygenSupplier;
    private OxygenDates oxygenDates;
    private OxygenInventoryRepository oxygenInventory;

    public OxygenOrderer(OxygenRequestSettings requestSettings, OxygenSupplier oxygenSupplier, OxygenDates oxygenDates, OxygenInventoryRepository oxygenInventory) {
        nextOrderer = Optional.empty();

        this.requestSettings = requestSettings;
        this.oxygenSupplier = oxygenSupplier;
        this.oxygenDates = oxygenDates;
        this.oxygenInventory = oxygenInventory;
    }

    public void setNextOrderer(OxygenOrderer nextOrderer) {
        this.nextOrderer = Optional.of(nextOrderer);
    }

	public void order(OffsetDateTime orderDate, int requestedQuantity) {
        Duration timeUntilLimit = Duration.between(orderDate, oxygenDates.getOxygenLimitDeliveryDate());
        OxygenInventoryEntry inventoryEntry = oxygenInventory.find(requestSettings.getGrade());
        
        if (requestSettings.getTimeToReceive().compareTo(timeUntilLimit) > 0) {
            int surplusQuantity = inventoryEntry.getSurplusQuantity();
            inventoryEntry.useQuantity(surplusQuantity);
            delegateToNextOrderer(orderDate, requestedQuantity - surplusQuantity);
        }
        else {
            orderOxygenIfNeeded(orderDate, requestedQuantity, inventoryEntry);
            inventoryEntry.useQuantity(requestedQuantity);
            oxygenInventory.replace(inventoryEntry);
        }
    }

    private void delegateToNextOrderer(OffsetDateTime orderDate, int requestedQuantity) {
        if (nextOrderer.isPresent()) {
            nextOrderer.get().order(orderDate, requestedQuantity);
        } else {
            throw new RuntimeException("Not enough time to produce");
        }
    }

    private void orderOxygenIfNeeded(OffsetDateTime orderDate, int requestedQuantity, OxygenInventoryEntry inventoryEntry) {
        int quantityRemaining = inventoryEntry.getSurplusQuantity();
        if (quantityRemaining < requestedQuantity) {
            oxygenSupplier.supply(orderDate, requestedQuantity - quantityRemaining, inventoryEntry);
        }
    }
}
