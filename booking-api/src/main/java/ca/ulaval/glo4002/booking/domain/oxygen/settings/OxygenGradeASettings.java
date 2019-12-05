package ca.ulaval.glo4002.booking.domain.oxygen.settings;

import java.time.Duration;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenGradeASettings implements OxygenRequestSettings, OxygenSupplySettings {

    @Override
    public OxygenGrade getGrade() {
        return OxygenGrade.A;
    }

    @Override
    public int getNumberOfDaysToReceive() {
        return 20;
    }

    @Override
    public Duration getTimeToReceive() {
        return Duration.ofDays(20);
    }

    @Override
    public int getBatchSize() {
        return 5;
    }

    @Override
    public Price getCostPerBatch() {
        return new Price(650 * 15);
    }
}
