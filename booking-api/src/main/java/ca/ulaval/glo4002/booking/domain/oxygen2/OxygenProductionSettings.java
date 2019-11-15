package ca.ulaval.glo4002.booking.domain.oxygen2;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public interface OxygenProductionSettings {
    public OxygenGrade getGrade();
    public int getProductionTimeInDays();
    public int getBatchSize();
}
