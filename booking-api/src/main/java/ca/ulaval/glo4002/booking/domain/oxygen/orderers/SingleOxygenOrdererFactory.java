package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettings;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettingsFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplier;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplierFactory;

public class SingleOxygenOrdererFactory {

    @Inject private OxygenSupplierFactory oxygenSupplierFactory;
    @Inject private OxygenRequestSettingsFactory requestSettingsFactory;
    @Inject private OxygenInventoryRepository oxygenInventory;
    @Inject private OxygenDates oxygenDates;

    public OxygenOrderer create(OxygenGrade oxygenGrade) {
        OxygenRequestSettings requestSettings = requestSettingsFactory.create(oxygenGrade);
        OxygenSupplier oxygenSupplier = oxygenSupplierFactory.create(oxygenGrade);
        return new OxygenOrderer(requestSettings, oxygenSupplier, oxygenDates, oxygenInventory);
    }
}
