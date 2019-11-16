package ca.ulaval.glo4002.booking.domain.oxygen2.settings;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public class OxygenGradeBSettings implements OxygenRequestSettings, OxygenSupplySettings {

    @Override
    public OxygenGrade getGrade() {
        return OxygenGrade.B;
    }

    @Override
    public int getNumberOfDaysToReceive() {
        return 10;
    }

    @Override
    public int getBatchSize() {
        return 3;
    }
}
