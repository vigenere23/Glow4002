package ca.ulaval.glo4002.booking.domain.oxygen2.ordering;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenOrderingSettings;

public class OxygenGradeEOrdering extends OxygenOrderer {

    public OxygenGradeEOrdering(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory, OxygenOrderingSettings oxygenOrderingSettings) {
        super(oxygenInventory, oxygenHistory, oxygenOrderingSettings);
    }

    @Override
    public void order(LocalDate orderDate, int minQuantityToProduce) {
        int numberOfBatchesProduced = calculateNumberOfBatchesRequired(minQuantityToProduce);
        oxygenHistory.addTankBought(orderDate, numberOfBatchesProduced * orderingSettings.getBatchSize());
    }
}
