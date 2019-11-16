package ca.ulaval.glo4002.booking.domain.oxygen2.ordering;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenOrderSettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;

public abstract class OxygenOrderer {

    protected OxygenInventory oxygenInventory;
    protected OxygenHistory oxygenHistory;
    protected OxygenOrderSettings orderingSettings;

    public OxygenOrderer(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory, OxygenOrderSettings oxygenOrderingSettings) {
        this.oxygenInventory = oxygenInventory;
        this.oxygenHistory = oxygenHistory;
        this.orderingSettings = oxygenOrderingSettings;
    }

    public abstract void order(LocalDate orderDate, int minQuantityToProduce);
    
    protected int calculateNumberOfBatchesRequired(int minQuantityToProduce) {
        return minQuantityToProduce % orderingSettings.getBatchSize() + 1;
    }
}
