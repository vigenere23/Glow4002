package ca.ulaval.glo4002.booking.domain.oxygen2.settings;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public interface OxygenOrderingSettings {
    public OxygenGrade getGrade();
    public int getBatchSize();
    public int getNumberOfDaysToReceive();
}
