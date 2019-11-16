package ca.ulaval.glo4002.booking.domain.oxygen2.orderers;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.suppliers.OxygenSupplier;
import ca.ulaval.glo4002.booking.domain.oxygen2.suppliers.OxygenSupplierFactory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenRequestSettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenRequestSettingsFactory;

public class OxygenOrdererFactory {

    private OxygenSupplierFactory oxygenSupplierFactory;
    private OxygenRequestSettingsFactory requestSettingsFactory;
    private OxygenInventory oxygenInventory;

    public OxygenOrdererFactory(OxygenSupplierFactory oxygenSupplierFactory, OxygenRequestSettingsFactory requestSettingsFactory, OxygenInventory oxygenInventory) {
        this.oxygenSupplierFactory = oxygenSupplierFactory;
        this.requestSettingsFactory = requestSettingsFactory;
        this.oxygenInventory = oxygenInventory;
    }

    public OxygenOrderer create(OxygenGrade oxygenGrade, LocalDate limitDate) {
        OxygenOrderer orderer = null;

        for (OxygenGrade oxygenGradeToCompare : OxygenGrade.values()) {
            if (oxygenGrade.compareTo(oxygenGradeToCompare) <= 0) {
                OxygenOrderer previousOrderer = createOrderer(oxygenGradeToCompare, limitDate);
                orderer = setNextOrderer(previousOrderer, orderer);
            }
        }
        
        if (orderer == null) {
            throw new RuntimeException(
                String.format("Could not create oxygen orderer for grade %s", oxygenGrade)
            );
        }

        return orderer;
    }

    private OxygenOrderer createOrderer(OxygenGrade oxygenGrade, LocalDate limitDate) {
        OxygenRequestSettings requestSettings = requestSettingsFactory.create(oxygenGrade);
        OxygenSupplier oxygenSupplier = oxygenSupplierFactory.create(oxygenGrade);
        return new OxygenOrderer(requestSettings, oxygenSupplier, limitDate, oxygenInventory);
    }

    private OxygenOrderer setNextOrderer(OxygenOrderer orderer, OxygenOrderer nextOrderer) {
        orderer.setNextOrderer(nextOrderer);
        return orderer;
    }
}
