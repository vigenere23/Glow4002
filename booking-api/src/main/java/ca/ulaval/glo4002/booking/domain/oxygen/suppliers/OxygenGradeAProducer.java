package ca.ulaval.glo4002.booking.domain.oxygen.suppliers;

import java.time.LocalDate;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenGradeASettings;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenSupplySettings;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class OxygenGradeAProducer implements OxygenSupplier {

    private final OxygenSupplySettings supplySettings = new OxygenGradeASettings();
    private final int numberOfCandlesPerBatch = 15;

    private OxygenHistoryRepository oxygenHistory;
    private OutcomeSaver outcomeSaver;

    public OxygenGradeAProducer(OxygenHistoryRepository oxygenHistory, OutcomeSaver outcomeSaver) {
        this.oxygenHistory = oxygenHistory;
        this.outcomeSaver = outcomeSaver;
    }

    @Override
    public void supply(LocalDate orderDate, int minQuantityToProduce, OxygenInventoryEntry oxygenInventoryEntry) {
        int numberOfBatchesProduced = OxygenCalculator.calculateNumberOfBatchesRequired(minQuantityToProduce, supplySettings.getBatchSize());
        int numberOfTanksProduced = numberOfBatchesProduced * supplySettings.getBatchSize();
        Price cost = supplySettings.getCostPerBatch().multipliedBy(numberOfBatchesProduced);
        
        oxygenInventoryEntry.addQuantity(numberOfTanksProduced);
        outcomeSaver.saveOutcome(cost);

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
