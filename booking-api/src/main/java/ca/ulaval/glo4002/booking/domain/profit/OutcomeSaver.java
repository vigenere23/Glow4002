package ca.ulaval.glo4002.booking.domain.profit;

import ca.ulaval.glo4002.booking.domain.Price;

public interface OutcomeSaver {
    void saveOutcome(Price outcome);
}
