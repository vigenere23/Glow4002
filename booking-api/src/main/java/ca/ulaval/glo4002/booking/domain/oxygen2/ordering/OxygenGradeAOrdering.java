package ca.ulaval.glo4002.booking.domain.oxygen2.ordering;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenOrderingSettings;

public class OxygenGradeAOrdering extends OxygenOrderer {

    private final int numberOfCandlesPerBatch = 15;
    private final Price costPerCandle = new Price(650);

    public OxygenGradeAOrdering(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory, OxygenOrderingSettings orderingSettings) {
        super(oxygenInventory, oxygenHistory, orderingSettings);
    }

    @Override
    public void order(LocalDate orderDate, int minQuantityToProduce) {
        int numberOfBatchesProduced = calculateNumberOfBatchesRequired(minQuantityToProduce);
        oxygenHistory.addCandlesUsed(orderDate, numberOfBatchesProduced * numberOfCandlesPerBatch);

        LocalDate receivedDate = orderDate.plusDays(orderingSettings.getNumberOfDaysToReceive());
        oxygenHistory.addTankMade(receivedDate, numberOfBatchesProduced * orderingSettings.getBatchSize());
    }
}
