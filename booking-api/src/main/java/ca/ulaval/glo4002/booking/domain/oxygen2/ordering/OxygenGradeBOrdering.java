package ca.ulaval.glo4002.booking.domain.oxygen2.ordering;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenOrderingSettings;

public class OxygenGradeBOrdering extends OxygenOrderer {

    private final int litersOfWaterPerBatch = 8;
    private final Price costPerWaterLiter = new Price(600);

    public OxygenGradeBOrdering(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory, OxygenOrderingSettings oxygenOrderingSettings) {
        super(oxygenInventory, oxygenHistory, oxygenOrderingSettings);
    }

    @Override
    public void order(LocalDate orderDate, int minQuantityToProduce) {
        int numberOfBatchesProduced = calculateNumberOfBatchesRequired(minQuantityToProduce);
        oxygenHistory.addWaterUsed(orderDate, numberOfBatchesProduced * litersOfWaterPerBatch);
        
        LocalDate receivedDate = orderDate.plusDays(orderingSettings.getNumberOfDaysToReceive());
        oxygenHistory.addTankMade(receivedDate, numberOfBatchesProduced * orderingSettings.getBatchSize());
    }
}
