package ca.ulaval.glo4002.booking.domain.oxygen2;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.dateUtil.DateCalculator;

public abstract class OxygenProducer extends OxygenRequestingStrategy {

    protected OxygenProductionSettings productionSettings;
    private LocalDate limitDate;
    private OxygenInventory oxygenInventory;

    protected OxygenProducer(LocalDate limitDate, OxygenInventory oxygenInventory) {
        this.limitDate = limitDate;
        this.oxygenInventory = oxygenInventory;
    }

    public abstract void produceOxygen(int minQuantityToProduce, LocalDate orderDate);

    public void requestOxygen(int quantityNeeded, LocalDate orderDate) {
        int numberOfDaysUntilLimitDate = DateCalculator.numberOfDaysInclusivelyBetween(limitDate, orderDate);
        
		if (numberOfDaysUntilLimitDate > productionSettings.getProductionTimeInDays()) {
            requestOxygenToNextStrategy(quantityNeeded, orderDate);
            return;
        }

        produceOxygenIfNeeded(quantityNeeded, orderDate);
        reduceInventory(quantityNeeded);
    }
    
    private void produceOxygenIfNeeded(int quantityNeeded, LocalDate orderDate) {
        int remainingQuantity = oxygenInventory.getRemainingQuantityOfGrade(productionSettings.getGrade());
        
        if (remainingQuantity < quantityNeeded) {
			produceOxygen(quantityNeeded - remainingQuantity, orderDate);
        }
    }

    private void reduceInventory(int quantityToReduce) {
        oxygenInventory.reduceQuantity(productionSettings.getGrade(), quantityToReduce);
    }
}
