package ca.ulaval.glo4002.booking.domain.oxygen2.orderers;

import java.time.LocalDate;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.dateUtil.DateCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.suppliers.OxygenSupplier;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenRequestSettings;

public class OxygenOrderer {

    private Optional<OxygenOrderer> nextOrderer;
    private OxygenRequestSettings requestSettings;
    private OxygenSupplier oxygenSupplier;
    private LocalDate limitDate;
    private OxygenInventory oxygenInventory;

    public OxygenOrderer(OxygenRequestSettings requestSettings, OxygenSupplier oxygenSupplier, LocalDate limitDate, OxygenInventory oxygenInventory) {
        nextOrderer = Optional.empty();

        this.requestSettings = requestSettings;
        this.oxygenSupplier = oxygenSupplier;
        this.limitDate = limitDate;
        this.oxygenInventory = oxygenInventory;
    }

    public void setNextOrderer(OxygenOrderer nextOrderer) {
        this.nextOrderer = Optional.of(nextOrderer);
    }

    private void requestOxygenToNextStrategy(LocalDate orderDate, int requestedQuantity) {
        if (nextOrderer.isPresent()) {
            nextOrderer.get().requestOxygen(orderDate, requestedQuantity);
        } else {
            throw new RuntimeException("Not enough time to produce");
        }
    }

	public void requestOxygen(LocalDate orderDate, int requestedQuantity) {
        int numberOfDaysUntilLimitDate = DateCalculator.numberOfDaysInclusivelyBetween(orderDate, limitDate);
        
        if (numberOfDaysUntilLimitDate < requestSettings.getNumberOfDaysToReceive()) {
            requestOxygenToNextStrategy(orderDate, requestedQuantity);
        }

        orderOxygenIfNeeded(orderDate, requestedQuantity);
        removeQuantityFromInventory(requestedQuantity);
    }

    private void orderOxygenIfNeeded(LocalDate orderDate, int requestedQuantity) {
        int quantityRemaining = oxygenInventory.getQuantity(requestSettings.getGrade());
        if (quantityRemaining < requestedQuantity) {
            oxygenSupplier.order(orderDate, requestedQuantity - quantityRemaining);
        }
    }

    private void removeQuantityFromInventory(int requestedQuantity) {
        int oldQuantity = oxygenInventory.getQuantity(requestSettings.getGrade());
        int newQuantity = oldQuantity - requestedQuantity;
        oxygenInventory.updateQuantity(requestSettings.getGrade(), newQuantity);
    }
}
