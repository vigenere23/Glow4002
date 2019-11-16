package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeESettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenSupplySettings;

public class OxygenGradeEBuyer implements OxygenSupplier {

    private final OxygenSupplySettings supplySettings = new OxygenGradeESettings();
    private final Price costPerTank = new Price(5000);

    private OxygenInventory oxygenInventory;
    private OxygenHistory oxygenHistory;

    public OxygenGradeEBuyer(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory) {
        this.oxygenInventory = oxygenInventory;
        this.oxygenHistory = oxygenHistory;
    }

    @Override
    public void order(LocalDate orderDate, int minQuantityToProduce) {
        int numberOfBatchesBought = OxygenCalculator.calculateNumberOfBatchesRequired(minQuantityToProduce, supplySettings.getBatchSize());
        int numberOfTanksBought = numberOfBatchesBought * supplySettings.getBatchSize();

        oxygenInventory.updateQuantity(supplySettings.getGrade(), numberOfTanksBought);
        oxygenHistory.addTankBought(orderDate, numberOfTanksBought);
    }
}
