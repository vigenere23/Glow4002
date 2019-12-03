package ca.ulaval.glo4002.booking.domain.profit;

import ca.ulaval.glo4002.booking.domain.Price;

public interface IncomeSaver {
    void saveIncome(Price income);
}
