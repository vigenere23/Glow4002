package ca.ulaval.glo4002.booking.domain.oxygen.suppliers;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenSupplySettings;

public class OxygenSupplierTestHelper {

    public static int getQuantityProduced(int minQuantity, OxygenSupplySettings supplySettings) {
        return supplySettings.getBatchSize() * getNumberOfBatchesProduced(minQuantity, supplySettings);
    }

    public static int getNumberOfBatchesProduced(int minQuantity, OxygenSupplySettings supplySettings) {
        return OxygenCalculator.calculateNumberOfBatchesRequired(minQuantity, supplySettings.getBatchSize());
    }
}
