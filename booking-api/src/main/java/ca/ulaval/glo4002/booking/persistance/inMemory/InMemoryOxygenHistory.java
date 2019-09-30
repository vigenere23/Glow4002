package ca.ulaval.glo4002.booking.persistance.inMemory;

import java.time.OffsetDateTime;
import java.util.HashMap;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenCreationHistory;

public class InMemoryOxygenHistory implements OxygenHistory {

    private HashMap<OffsetDateTime, OxygenCreationHistory> history;
    public InMemoryOxygenHistory() {
        history = new HashMap<OffsetDateTime, OxygenCreationHistory>();
    }

    @Override
    public void increaseOxygenTankBoughtCount(OffsetDateTime date, int tankQuantity) {
        history.get(date).qtyOxygenTankBought += tankQuantity;
    }

    @Override
    public void increaseWaterLitreUsed(OffsetDateTime date, int waterLitre) {
        history.get(date).qtyWaterUsed += waterLitre;
    }

    @Override
    public void increaseCandleUsed(OffsetDateTime date, int candleQuantity) {
        history.get(date).qtyCandlesUsed += candleQuantity;
    }

    @Override
    public void increaseOxygenTankMade(OffsetDateTime date, int oxygenTankQuantity) {
        history.get(date).qtyOxygenTankMade += oxygenTankQuantity;
    }

    @Override
    public HashMap<OffsetDateTime, OxygenCreationHistory> getCreationHistory() {
        return history;
    }
}