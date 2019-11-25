package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplier;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplierFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettings;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettingsFactory;

public class OxygenOrdererFactory {

    private OxygenOrdererLinker oxygenOrdererLinker;
    private OxygenSupplierFactory oxygenSupplierFactory;
    private OxygenRequestSettingsFactory requestSettingsFactory;
    private OxygenInventoryRepository oxygenInventory;

    @Inject
    public OxygenOrdererFactory(OxygenOrdererLinker oxygenOrdererLinker, OxygenSupplierFactory oxygenSupplierFactory,
            OxygenRequestSettingsFactory requestSettingsFactory, OxygenInventoryRepository oxygenInventory) {
        this.oxygenOrdererLinker = oxygenOrdererLinker;
        this.oxygenSupplierFactory = oxygenSupplierFactory;
        this.requestSettingsFactory = requestSettingsFactory;
        this.oxygenInventory = oxygenInventory;
    }

    public OxygenOrderer create(OxygenGrade oxygenGrade, OxygenDates oxygenDates) {
        List<OxygenOrderer> oxygenOrderers = new ArrayList<>();

        for (OxygenGrade oxygenGradeToCompare : OxygenGrade.values()) {
            if (oxygenGrade.compareTo(oxygenGradeToCompare) <= 0) {
                oxygenOrderers.add(createOrderer(oxygenGradeToCompare, oxygenDates));
            }
        }
        
        if (oxygenOrderers.isEmpty()) {
            throw new RuntimeException(
                String.format("Could not create oxygen orderer for grade %s", oxygenGrade)
            );
        }

        return oxygenOrdererLinker.link(oxygenOrderers);
    }

    private OxygenOrderer createOrderer(OxygenGrade oxygenGrade, OxygenDates oxygenDates) {
        OxygenRequestSettings requestSettings = requestSettingsFactory.create(oxygenGrade);
        OxygenSupplier oxygenSupplier = oxygenSupplierFactory.create(oxygenGrade);
        return new OxygenOrderer(requestSettings, oxygenSupplier, oxygenDates, oxygenInventory);
    }
}
