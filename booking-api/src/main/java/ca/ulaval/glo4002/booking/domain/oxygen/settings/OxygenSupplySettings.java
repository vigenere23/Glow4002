package ca.ulaval.glo4002.booking.domain.oxygen.settings;

import ca.ulaval.glo4002.booking.domain.Price;

public interface OxygenSupplySettings {
    int getBatchSize();
    int getNumberOfDaysToReceive();
    Price getCostPerBatch();
}
