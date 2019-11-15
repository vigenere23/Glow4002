package ca.ulaval.glo4002.booking.domain.oxygen2;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenProductionSettingsGradeB implements OxygenProductionSettings {

    @Override
    public OxygenGrade getGrade() {
        return OxygenGrade.B;
    }

    @Override
    public int getProductionTimeInDays() {
        return 10;
    }

    @Override
    public int getBatchSize() {
        return 3;
    }
}
