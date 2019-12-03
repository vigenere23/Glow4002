package ca.ulaval.glo4002.booking.domain.oxygen.settings;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public interface OxygenRequestSettings {
    int getNumberOfDaysToReceive();
    OxygenGrade getGrade();
}
