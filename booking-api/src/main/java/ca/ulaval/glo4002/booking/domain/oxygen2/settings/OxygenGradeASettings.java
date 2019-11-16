package ca.ulaval.glo4002.booking.domain.oxygen2.settings;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

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
    public int getBatchSize() {
        return 5;
    }
}
