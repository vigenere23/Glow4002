package ca.ulaval.glo4002.booking.domain.oxygen2.ordering;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenOrderSettings;

public class OxygenGradeEBuyer extends OxygenOrderer {

    private final Price costPerTank = new Price(5000);

    public OxygenGradeEBuyer(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory, OxygenOrderSettings oxygenOrderingSettings) {
        super(oxygenInventory, oxygenHistory, oxygenOrderingSettings);
    }

    @Override
    public void order(LocalDate orderDate, int minQuantityToProduce) {
        int numberOfBatchesProduced = calculateNumberOfBatchesRequired(minQuantityToProduce);
        oxygenHistory.addTankBought(orderDate, numberOfBatchesProduced * orderingSettings.getBatchSize());
    }
}
