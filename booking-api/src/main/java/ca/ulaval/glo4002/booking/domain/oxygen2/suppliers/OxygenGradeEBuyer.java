package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.finance.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeESettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenSupplySettings;

public class OxygenGradeEBuyer implements OxygenSupplier {

    private final OxygenSupplySettings supplySettings = new OxygenGradeESettings();

    private OxygenInventory oxygenInventory;
    private OxygenHistory oxygenHistory;
    private ProfitCalculator profitCalculator;

    public OxygenGradeEBuyer(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory, ProfitCalculator profitCalculator) {
        this.oxygenInventory = oxygenInventory;
        this.oxygenHistory = oxygenHistory;
        this.profitCalculator = profitCalculator;
    }

    @Override
    public void supply(LocalDate orderDate, int minQuantityToProduce) {
        int numberOfBatchesBought = OxygenCalculator.calculateNumberOfBatchesRequired(minQuantityToProduce, supplySettings.getBatchSize());
        int numberOfTanksBought = numberOfBatchesBought * supplySettings.getBatchSize();
        Price cost = supplySettings.getCostPerBatch().multipliedBy(numberOfBatchesBought);

        oxygenInventory.addQuantity(supplySettings.getGrade(), numberOfTanksBought);
        oxygenHistory.addTankBought(orderDate, numberOfTanksBought);
        profitCalculator.addOutcome(cost);
    }
}
