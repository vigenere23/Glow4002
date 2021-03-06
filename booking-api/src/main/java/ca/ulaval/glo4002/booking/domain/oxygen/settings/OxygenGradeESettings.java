package ca.ulaval.glo4002.booking.domain.oxygen.settings;

import java.time.Duration;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenGradeESettings implements OxygenRequestSettings, OxygenSupplySettings {

    @Override
    public OxygenGrade getGrade() {
        return OxygenGrade.E;
    }

    @Override
    public Duration getTimeToReceive() {
        return Duration.ZERO;
    }

    @Override
    public int getBatchSize() {
        return 1;
    }

    @Override
    public Price getCostPerBatch() {
        return new Price(5000);
    }
}
