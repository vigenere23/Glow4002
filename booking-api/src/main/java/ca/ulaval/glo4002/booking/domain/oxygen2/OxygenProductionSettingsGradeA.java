package ca.ulaval.glo4002.booking.domain.oxygen2;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenProductionSettingsGradeA implements OxygenProductionSettings {

    @Override
    public OxygenGrade getGrade() {
        return OxygenGrade.A;
    }

    @Override
    public int getProductionTimeInDays() {
        return 20;
    }

    @Override
    public int getBatchSize() {
        return 5;
    }
}
