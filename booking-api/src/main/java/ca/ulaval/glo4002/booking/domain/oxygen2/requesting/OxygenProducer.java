package ca.ulaval.glo4002.booking.domain.oxygen2.requesting;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.dateUtil.DateCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.ordering.OxygenOrderer;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenOrderingSettings;

public class OxygenProducer extends OxygenRequestingStrategy {

    private OxygenOrderingSettings orderingSettings;

    public OxygenProducer(OxygenOrderingSettings orderingSettings, OxygenOrderer oxygenOrderer, LocalDate limitDate, OxygenInventory oxygenInventory) {
        super(oxygenOrderer, limitDate, oxygenInventory);

        this.orderingSettings = orderingSettings;
    }

    @Override
    public void requestOxygen(LocalDate orderDate, int requestedQuantity) {
        int numberOfDaysUntilLimitDate = DateCalculator.numberOfDaysInclusivelyBetween(orderDate, limitDate);
        
        if (numberOfDaysUntilLimitDate < orderingSettings.getNumberOfDaysToReceive()) {
            requestOxygenToNextStrategy(orderDate, requestedQuantity);
        }

        produceOxygenIfNeeded(orderDate, requestedQuantity);
        removeQuantityFromInventory(requestedQuantity);
    }

    private void produceOxygenIfNeeded(LocalDate orderDate, int requestedQuantity) {
        int quantityRemaining = oxygenInventory.getQuantity(orderingSettings.getGrade());
        if (quantityRemaining < requestedQuantity) {
            oxygenOrderer.order(orderDate, requestedQuantity - quantityRemaining);
        }
    }

    private void removeQuantityFromInventory(int requestedQuantity) {
        int oldQuantity = oxygenInventory.getQuantity(orderingSettings.getGrade());
        int newQuantity = oldQuantity - requestedQuantity;
        oxygenInventory.updateQuantity(orderingSettings.getGrade(), newQuantity);
    }
}
