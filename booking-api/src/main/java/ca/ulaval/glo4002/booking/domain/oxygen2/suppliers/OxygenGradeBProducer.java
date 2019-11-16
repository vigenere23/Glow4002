package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeBSettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenSupplySettings;

public class OxygenGradeBProducer implements OxygenSupplier {

    private final OxygenSupplySettings supplySettings = new OxygenGradeBSettings();
    private final int litersOfWaterPerBatch = 8;
    private final Price costPerWaterLiter = new Price(600);

    private OxygenInventory oxygenInventory;
    private OxygenHistory oxygenHistory;

    public OxygenGradeBProducer(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory) {
        this.oxygenInventory = oxygenInventory;
        this.oxygenHistory = oxygenHistory;
    }

    @Override
    public void order(LocalDate orderDate, int minQuantityToProduce) {
        int numberOfBatchesProduced = OxygenCalculator.calculateNumberOfBatchesRequired(minQuantityToProduce, supplySettings.getBatchSize());
        int numberOfTanksProduced = numberOfBatchesProduced * supplySettings.getBatchSize();
        LocalDate receivedDate = orderDate.plusDays(supplySettings.getNumberOfDaysToReceive());
        
        oxygenInventory.updateQuantity(supplySettings.getGrade(), numberOfTanksProduced);
        oxygenHistory.addWaterUsed(orderDate, numberOfBatchesProduced * litersOfWaterPerBatch);
        oxygenHistory.addTankMade(receivedDate, numberOfTanksProduced);
    }
}
