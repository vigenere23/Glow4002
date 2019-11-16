package ca.ulaval.glo4002.booking.domain.oxygen2.settings;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public class OxygenRequestSettingsFactory {

    public OxygenRequestSettings create(OxygenGrade oxygenGrade) {
        switch (oxygenGrade) {
        case A:
            return new OxygenGradeASettings();
        case B:
            return new OxygenGradeBSettings();
        case E:
            return new OxygenGradeESettings();
        default:
            throw new IllegalArgumentException(
                String.format("No request settings for grade %s", oxygenGrade.toString())
            );
        }
    }
}
