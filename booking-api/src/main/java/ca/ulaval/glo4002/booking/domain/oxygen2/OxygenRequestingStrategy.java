package ca.ulaval.glo4002.booking.domain.oxygen2;

import java.time.LocalDate;
import java.util.Optional;

public abstract class OxygenRequestingStrategy {

    protected Optional<OxygenRequestingStrategy> nextStrategy;

    public OxygenRequestingStrategy() {
        nextStrategy = Optional.empty();
    }

    public void setNextStrategy(OxygenRequestingStrategy oxygenRequestingStrategy) {
        nextStrategy = Optional.of(oxygenRequestingStrategy);
    }

    public abstract void requestOxygen(int quantity, LocalDate orderDate);

    protected void requestOxygenToNextStrategy(int quantity, LocalDate orderDate) {
        if (nextStrategy.isPresent()) {
            nextStrategy.get().requestOxygen(quantity, orderDate);
        }
        else {
            throw new RuntimeException("Not enough time to produce");
        }
    }
}
