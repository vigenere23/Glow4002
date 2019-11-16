package ca.ulaval.glo4002.booking.domain.oxygen2.orderers;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.suppliers.OxygenSupplier;
import ca.ulaval.glo4002.booking.domain.oxygen2.suppliers.OxygenSupplierFactory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeASettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeBSettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeESettings;

public class OxygenOrdererFactory {

    private OxygenSupplierFactory oxygenSupplierFactory;
    private OxygenInventory oxygenInventory;

    public OxygenOrdererFactory(OxygenSupplierFactory oxygenSupplierFactory, OxygenInventory oxygenInventory) {
        this.oxygenSupplierFactory = oxygenSupplierFactory;
        this.oxygenInventory = oxygenInventory;
    }

    public OxygenOrderer create(OxygenGrade oxygenGrade, LocalDate limitDate) {
        OxygenOrderer orderer = createGradeEOrderer(limitDate);

        if (oxygenGrade.compareTo(OxygenGrade.B) >= 0) {
            OxygenOrderer previousOrderer = createGradeBOrderer(limitDate);
            orderer = setNextOrderer(previousOrderer, orderer);
        }

        if (oxygenGrade.compareTo(OxygenGrade.A) >= 0) {
            OxygenOrderer previousOrderer = createGradeAOrderer(limitDate);
            orderer = setNextOrderer(previousOrderer, orderer);
        }

        return orderer;
    }

    private OxygenOrderer createGradeEOrderer(LocalDate limitDate) {
        OxygenGradeESettings oxygenGradeESettings = new OxygenGradeESettings();
        OxygenSupplier oxygenSupplier = oxygenSupplierFactory.create(oxygenGradeESettings);
        return new OxygenOrderer(oxygenGradeESettings, oxygenSupplier, limitDate, oxygenInventory);
    }

    private OxygenOrderer createGradeBOrderer(LocalDate limitDate) {
        OxygenGradeBSettings oxygenGradeBSettings = new OxygenGradeBSettings();
        OxygenSupplier oxygenSupplier = oxygenSupplierFactory.create(oxygenGradeBSettings);
        return new OxygenOrderer(oxygenGradeBSettings, oxygenSupplier, limitDate, oxygenInventory);
    }

    private OxygenOrderer createGradeAOrderer(LocalDate limitDate) {
        OxygenGradeASettings oxygenGradeASettings = new OxygenGradeASettings();
        OxygenSupplier oxygenSupplier = oxygenSupplierFactory.create(oxygenGradeASettings);
        return new OxygenOrderer(oxygenGradeASettings, oxygenSupplier, limitDate, oxygenInventory);
    }

    private OxygenOrderer setNextOrderer(OxygenOrderer orderer, OxygenOrderer nextOrderer) {
        orderer.setNextOrderer(nextOrderer);
        return orderer;
    }
}
