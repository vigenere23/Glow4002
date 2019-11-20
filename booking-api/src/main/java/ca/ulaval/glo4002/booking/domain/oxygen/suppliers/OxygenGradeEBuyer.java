package ca.ulaval.glo4002.booking.domain.oxygen.suppliers;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenGradeESettings;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenSupplySettings;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class OxygenGradeEBuyer implements OxygenSupplier {

    private final OxygenSupplySettings supplySettings = new OxygenGradeESettings();

    private OxygenHistoryRepository oxygenHistory;
    private OutcomeSaver outcomeSaver;

    public OxygenGradeEBuyer(OxygenHistoryRepository oxygenHistory, OutcomeSaver outcomeSaver) {
        this.oxygenHistory = oxygenHistory;
        this.outcomeSaver = outcomeSaver;
    }

    @Override
    public void supply(LocalDate orderDate, int minQuantityToProduce, OxygenInventoryEntry oxygenInventoryEntry) {
        int numberOfBatchesBought = OxygenCalculator.calculateNumberOfBatchesRequired(minQuantityToProduce, supplySettings.getBatchSize());
        int numberOfTanksBought = numberOfBatchesBought * supplySettings.getBatchSize();
        Price cost = supplySettings.getCostPerBatch().multipliedBy(numberOfBatchesBought);

        oxygenInventoryEntry.addQuantity(numberOfTanksBought);
        outcomeSaver.saveOutcome(cost);
        
        addTankBought(orderDate, numberOfTanksBought);
    }

    private void addTankBought(LocalDate orderDate, int numberOfTanksBought) {
        OxygenHistoryEntry orderDateHistoryEntry = oxygenHistory.findOrCreate(orderDate);
        orderDateHistoryEntry.addTankBought(numberOfTanksBought);
        oxygenHistory.save(orderDateHistoryEntry);
    }
}