package ca.ulaval.glo4002.booking.domain.oxygen2.orderers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.suppliers.OxygenSupplier;
import ca.ulaval.glo4002.booking.domain.oxygen2.suppliers.OxygenSupplierFactory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenRequestSettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenRequestSettingsFactory;

public class OxygenOrdererFactory {

    private OxygenOrdererLinker oxygenOrdererLinker;
    private OxygenSupplierFactory oxygenSupplierFactory;
    private OxygenRequestSettingsFactory requestSettingsFactory;
    private OxygenInventory oxygenInventory;

    public OxygenOrdererFactory(OxygenOrdererLinker oxygenOrdererLinker, OxygenSupplierFactory oxygenSupplierFactory,
            OxygenRequestSettingsFactory requestSettingsFactory, OxygenInventory oxygenInventory) {
        this.oxygenOrdererLinker = oxygenOrdererLinker;
        this.oxygenSupplierFactory = oxygenSupplierFactory;
        this.requestSettingsFactory = requestSettingsFactory;
        this.oxygenInventory = oxygenInventory;
    }

    public OxygenOrderer create(OxygenGrade oxygenGrade, LocalDate limitDate) {
        List<OxygenOrderer> oxygenOrderers = new ArrayList<>();

        for (OxygenGrade oxygenGradeToCompare : OxygenGrade.values()) {
            if (oxygenGrade.compareTo(oxygenGradeToCompare) <= 0) {
                oxygenOrderers.add(createOrderer(oxygenGradeToCompare, limitDate));
            }
        }
        
        if (oxygenOrderers.isEmpty()) {
            throw new RuntimeException(
                String.format("Could not create oxygen orderer for grade %s", oxygenGrade)
            );
        }

        return oxygenOrdererLinker.link(oxygenOrderers);
    }

    private OxygenOrderer createOrderer(OxygenGrade oxygenGrade, LocalDate limitDate) {
        OxygenRequestSettings requestSettings = requestSettingsFactory.create(oxygenGrade);
        OxygenSupplier oxygenSupplier = oxygenSupplierFactory.create(oxygenGrade);
        return new OxygenOrderer(requestSettings, oxygenSupplier, limitDate, oxygenInventory);
    }
}
