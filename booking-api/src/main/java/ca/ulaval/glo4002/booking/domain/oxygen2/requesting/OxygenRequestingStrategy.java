package ca.ulaval.glo4002.booking.domain.oxygen2.requesting;

import java.time.LocalDate;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.ordering.OxygenOrderer;

public abstract class OxygenRequestingStrategy {

    private Optional<OxygenRequestingStrategy> nextStrategy;
    protected OxygenOrderer oxygenOrderer;
    protected LocalDate limitDate;
    protected OxygenInventory oxygenInventory;

    protected OxygenRequestingStrategy(OxygenOrderer oxygenOrderer, LocalDate limitDate, OxygenInventory oxygenInventory) {
        nextStrategy = Optional.empty();

        this.oxygenOrderer = oxygenOrderer;
        this.limitDate = limitDate;
        this.oxygenInventory = oxygenInventory;
    }

    public void setNextStrategy(OxygenRequestingStrategy nextStrategy) {
        this.nextStrategy = Optional.of(nextStrategy);
    }

    protected void requestOxygenToNextStrategy(LocalDate orderDate, int requestedQuantity) {
        if (nextStrategy.isPresent()) {
            nextStrategy.get().requestOxygen(orderDate, requestedQuantity);
        } else {
            throw new RuntimeException("Not enough time to produce");
        }
    }

	public abstract void requestOxygen(LocalDate orderDate, int requestedQuantity);
}
