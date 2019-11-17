package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.finance.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeBSettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenSupplySettings;

public class OxygenGradeBProducer implements OxygenSupplier {

    private final OxygenSupplySettings supplySettings = new OxygenGradeBSettings();
    private final int litersOfWaterPerBatch = 8;

    private OxygenInventory oxygenInventory;
    private OxygenHistory oxygenHistory;
    private ProfitCalculator profitCalculator;

    public OxygenGradeBProducer(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory, ProfitCalculator profitCalculator) {
        this.oxygenInventory = oxygenInventory;
        this.oxygenHistory = oxygenHistory;
        this.profitCalculator = profitCalculator;
    }

    @Override
    public void supply(LocalDate orderDate, int minQuantityToProduce) {
        int numberOfBatchesProduced = OxygenCalculator.calculateNumberOfBatchesRequired(minQuantityToProduce, supplySettings.getBatchSize());
        int numberOfTanksProduced = numberOfBatchesProduced * supplySettings.getBatchSize();
        LocalDate receivedDate = orderDate.plusDays(supplySettings.getNumberOfDaysToReceive());
        Price cost = supplySettings.getCostPerBatch().multipliedBy(numberOfBatchesProduced);
        
        oxygenInventory.updateQuantity(supplySettings.getGrade(), numberOfTanksProduced);
        oxygenHistory.addWaterUsed(orderDate, numberOfBatchesProduced * litersOfWaterPerBatch);
        oxygenHistory.addTankMade(receivedDate, numberOfTanksProduced);
        profitCalculator.addOutcome(cost);
    }
}
