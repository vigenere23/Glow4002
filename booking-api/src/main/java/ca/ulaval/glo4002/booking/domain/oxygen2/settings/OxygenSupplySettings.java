package ca.ulaval.glo4002.booking.domain.oxygen2.settings;

import ca.ulaval.glo4002.booking.domain.Price;

public interface OxygenSupplySettings {
    public int getBatchSize();
    public int getNumberOfDaysToReceive();
    public Price getCostPerBatch();
}
