package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.finance.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeASettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenSupplySettings;

public class OxygenGradeAProducer implements OxygenSupplier {

    private final OxygenSupplySettings supplySettings = new OxygenGradeASettings();
    private final int numberOfCandlesPerBatch = 15;

    private OxygenInventory oxygenInventory;
    private OxygenHistory oxygenHistory;
    private ProfitCalculator profitCalculator;

    public OxygenGradeAProducer(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory, ProfitCalculator profitCalculator) {
        this.oxygenInventory = oxygenInventory;
        this.oxygenHistory = oxygenHistory;
        this.profitCalculator = profitCalculator;
    }

    @Override
    public void supply(LocalDate orderDate, int minQuantityToProduce) {
        int numberOfBatchesProduced = OxygenCalculator.calculateNumberOfBatchesRequired(minQuantityToProduce, supplySettings.getBatchSize());
        int numberOfTanksProduced = numberOfBatchesProduced * supplySettings.getBatchSize();
        Price cost = supplySettings.getCostPerBatch().multipliedBy(numberOfBatchesProduced);
        
        oxygenInventory.addQuantity(supplySettings.getGrade(), numberOfTanksProduced);
        profitCalculator.addOutcome(cost);

        addTankMade(orderDate, numberOfTanksProduced);
        addCandlesUsed(orderDate, numberOfBatchesProduced);
    }

    private void addTankMade(LocalDate orderDate, int numberOfTanksProduced) {
        LocalDate receivedDate = orderDate.plusDays(supplySettings.getNumberOfDaysToReceive());
        OxygenHistoryEntry receivedDateHistoryEntry = oxygenHistory.findOrCreate(receivedDate);
        receivedDateHistoryEntry.addTankMade(numberOfTanksProduced);
        oxygenHistory.save(receivedDateHistoryEntry);
    }

    private void addCandlesUsed(LocalDate orderDate, int numberOfBatchesProduced) {
        OxygenHistoryEntry orderDateHistoryEntry = oxygenHistory.findOrCreate(orderDate);
        orderDateHistoryEntry.addCandlesUsed(numberOfBatchesProduced * numberOfCandlesPerBatch);
        oxygenHistory.save(orderDateHistoryEntry);
    }
}
