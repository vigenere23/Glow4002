package ca.ulaval.glo4002.booking.domain.oxygen2.settings;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public class OxygenGradeESettings implements OxygenRequestSettings, OxygenSupplySettings {

    @Override
    public OxygenGrade getGrade() {
        return OxygenGrade.E;
    }

    @Override
    public int getNumberOfDaysToReceive() {
        return 0;
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
