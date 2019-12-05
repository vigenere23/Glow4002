package ca.ulaval.glo4002.booking.domain.oxygen.settings;

import java.time.Duration;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenGradeBSettings implements OxygenRequestSettings, OxygenSupplySettings {

    @Override
    public OxygenGrade getGrade() {
        return OxygenGrade.B;
    }

    @Override
    public Duration getTimeToReceive() {
        return Duration.ofDays(10);
    }

    @Override
    public int getBatchSize() {
        return 3;
    }

    @Override
    public Price getCostPerBatch() {
        return new Price(600 * 8);
    }
}
