package ca.ulaval.glo4002.booking.domain.oxygen.suppliers;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenGradeBSettings;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenSupplySettings;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class OxygenGradeBProducer implements OxygenSupplier {

    private final OxygenSupplySettings supplySettings = new OxygenGradeBSettings();
    private final int litersOfWaterPerBatch = 8;

    private OxygenHistoryRepository oxygenHistory;
    private OutcomeSaver outcomeSaver;

    public OxygenGradeBProducer(OxygenHistoryRepository oxygenHistory, OutcomeSaver outcomeSaver) {
        this.oxygenHistory = oxygenHistory;
        this.outcomeSaver = outcomeSaver;
    }

    @Override
    public void supply(OffsetDateTime orderDate, int minQuantityToProduce, OxygenInventoryEntry oxygenInventoryEntry) {
        int numberOfBatchesProduced = OxygenCalculator.calculateNumberOfBatchesRequired(minQuantityToProduce, supplySettings.getBatchSize());
        int numberOfTanksProduced = numberOfBatchesProduced * supplySettings.getBatchSize();
        Price cost = supplySettings.getCostPerBatch().multipliedBy(numberOfBatchesProduced);
        
        outcomeSaver.addOutcome(cost);
        oxygenInventoryEntry.addQuantity(numberOfTanksProduced);
        
        addTankMade(orderDate, numberOfTanksProduced);
        addWaterUsed(orderDate, numberOfBatchesProduced);
    }

    private void addTankMade(OffsetDateTime orderDate, int numberOfTanksProduced) {
        OffsetDateTime receivedDate = orderDate.plus(supplySettings.getTimeToReceive());
        OxygenHistoryEntry receivedDateHistoryEntry = oxygenHistory.findOrCreate(receivedDate);
        receivedDateHistoryEntry.addTankMade(numberOfTanksProduced);
        oxygenHistory.add(receivedDateHistoryEntry);
    }

    private void addWaterUsed(OffsetDateTime orderDate, int numberOfBatchesProduced) {
        OxygenHistoryEntry orderDateHistoryEntry = oxygenHistory.findOrCreate(orderDate);
        orderDateHistoryEntry.addWaterUsed(numberOfBatchesProduced * litersOfWaterPerBatch);
        oxygenHistory.add(orderDateHistoryEntry);
    }
}
