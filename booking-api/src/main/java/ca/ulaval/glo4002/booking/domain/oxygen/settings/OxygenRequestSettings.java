package ca.ulaval.glo4002.booking.domain.oxygen.settings;

import java.time.Duration;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public interface OxygenRequestSettings {
    Duration getTimeToReceive();
    OxygenGrade getGrade();
}
