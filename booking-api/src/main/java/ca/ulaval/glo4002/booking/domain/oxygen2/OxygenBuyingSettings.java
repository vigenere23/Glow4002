package ca.ulaval.glo4002.booking.domain.oxygen2;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public interface OxygenBuyingSettings {
    public OxygenGrade getOxygenGrade();
    public int getBatchSize();
    public Price getPrice();
}
