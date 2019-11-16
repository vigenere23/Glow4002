package ca.ulaval.glo4002.booking.domain.oxygen2.requesting;

import java.time.LocalDate;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.dateUtil.DateCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.ordering.OxygenOrderer;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenRequestSettings;

public class OxygenRequester {

    private Optional<OxygenRequester> nextStrategy;
    private OxygenRequestSettings requestSettings;
    private OxygenOrderer oxygenOrderer;
    private LocalDate limitDate;
    private OxygenInventory oxygenInventory;

    public OxygenRequester(OxygenRequestSettings requestSettings, OxygenOrderer oxygenOrderer, LocalDate limitDate, OxygenInventory oxygenInventory) {
        nextStrategy = Optional.empty();

        this.requestSettings = requestSettings;
        this.oxygenOrderer = oxygenOrderer;
        this.limitDate = limitDate;
        this.oxygenInventory = oxygenInventory;
    }

    public void setNextStrategy(OxygenRequester nextStrategy) {
        this.nextStrategy = Optional.of(nextStrategy);
    }

    private void requestOxygenToNextStrategy(LocalDate orderDate, int requestedQuantity) {
        if (nextStrategy.isPresent()) {
            nextStrategy.get().requestOxygen(orderDate, requestedQuantity);
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
            oxygenOrderer.order(orderDate, requestedQuantity - quantityRemaining);
        }
    }

    private void removeQuantityFromInventory(int requestedQuantity) {
        int oldQuantity = oxygenInventory.getQuantity(requestSettings.getGrade());
        int newQuantity = oldQuantity - requestedQuantity;
        oxygenInventory.updateQuantity(requestSettings.getGrade(), newQuantity);
    }
}
