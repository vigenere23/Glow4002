package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.finance.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeESettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenSupplySettings;

public class OxygenGradeEBuyer implements OxygenSupplier {

    private final OxygenSupplySettings supplySettings = new OxygenGradeESettings();

    private OxygenHistory oxygenHistory;
    private ProfitCalculator profitCalculator;

    public OxygenGradeEBuyer(OxygenHistory oxygenHistory, ProfitCalculator profitCalculator) {
        this.oxygenHistory = oxygenHistory;
        this.profitCalculator = profitCalculator;
    }

    @Override
    public void supply(LocalDate orderDate, int minQuantityToProduce, OxygenInventoryEntry oxygenInventoryEntry) {
        int numberOfBatchesBought = OxygenCalculator.calculateNumberOfBatchesRequired(minQuantityToProduce, supplySettings.getBatchSize());
        int numberOfTanksBought = numberOfBatchesBought * supplySettings.getBatchSize();
        Price cost = supplySettings.getCostPerBatch().multipliedBy(numberOfBatchesBought);

        oxygenInventoryEntry.addQuantity(numberOfTanksBought);
        profitCalculator.addOutcome(cost);
        
        addTankBought(orderDate, numberOfTanksBought);
    }

    private void addTankBought(LocalDate orderDate, int numberOfTanksBought) {
        OxygenHistoryEntry orderDateHistoryEntry = oxygenHistory.findOrCreate(orderDate);
        orderDateHistoryEntry.addTankBought(numberOfTanksBought);
        oxygenHistory.save(orderDateHistoryEntry);
    }
}
