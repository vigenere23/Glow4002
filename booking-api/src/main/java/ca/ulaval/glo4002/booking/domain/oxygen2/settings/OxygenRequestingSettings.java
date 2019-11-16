package ca.ulaval.glo4002.booking.domain.oxygen2.settings;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public interface OxygenRequestingSettings {
    public int getNumberOfDaysToReceive();
    public OxygenGrade getGrade();
}
