package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import java.time.OffsetDateTime;
import java.util.HashMap;

import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenCreationHistory;

public interface OxygenHistory {

    public void increaseOxygenTankBoughtCount(OffsetDateTime date, int tankQuantity);
    public void increaseWaterLitreUsed(OffsetDateTime date, int waterLitre);
    public void increaseCandleUsed(OffsetDateTime date, int candleQuantity);
    public void increaseOxygenTankMade(OffsetDateTime date, int oxygenTankQuantity);
    public HashMap<OffsetDateTime, OxygenCreationHistory> getCreationHistory();
}