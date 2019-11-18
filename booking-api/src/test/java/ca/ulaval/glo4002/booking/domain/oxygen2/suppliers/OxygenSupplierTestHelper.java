package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenSupplySettings;

public class OxygenSupplierTestHelper {

    public static int getQuantityProduced(int minQuantity, OxygenSupplySettings supplySettings) {
        return supplySettings.getBatchSize() * getNumberOfBatchesProduced(minQuantity, supplySettings);
    }

    public static int getNumberOfBatchesProduced(int minQuantity, OxygenSupplySettings supplySettings) {
        return OxygenCalculator.calculateNumberOfBatchesRequired(minQuantity, supplySettings.getBatchSize());
    }
}
