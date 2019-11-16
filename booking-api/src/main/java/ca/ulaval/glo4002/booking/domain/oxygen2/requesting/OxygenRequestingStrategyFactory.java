package ca.ulaval.glo4002.booking.domain.oxygen2.requesting;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.ordering.OxygenOrderer;
import ca.ulaval.glo4002.booking.domain.oxygen2.ordering.OxygenOrdererFactory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeASettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeBSettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeESettings;

public class OxygenRequestingStrategyFactory {

    private OxygenOrdererFactory oxygenOrdererFactory;
    private OxygenInventory oxygenInventory;

    public OxygenRequestingStrategyFactory(OxygenOrdererFactory oxygenOrdererFactory, OxygenInventory oxygenInventory) {
        this.oxygenOrdererFactory = oxygenOrdererFactory;
        this.oxygenInventory = oxygenInventory;
    }

    public OxygenRequestingStrategy create(OxygenGrade oxygenGrade, LocalDate limitDate) {
        OxygenRequestingStrategy strategy = createGradeEStrategy(limitDate);

        if (oxygenGrade.compareTo(OxygenGrade.B) >= 0) {
            OxygenRequestingStrategy previousStrategy = createGradeBStrategy(limitDate);
            strategy = setNextStrategy(previousStrategy, strategy);
        }

        if (oxygenGrade.compareTo(OxygenGrade.A) >= 0) {
            OxygenRequestingStrategy previousStrategy = createGradeAStrategy(limitDate);
            strategy = setNextStrategy(previousStrategy, strategy);
        }

        return strategy;
    }

    private OxygenRequestingStrategy createGradeEStrategy(LocalDate limitDate) {
        OxygenGradeESettings oxygenGradeESettings = new OxygenGradeESettings();
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(oxygenGradeESettings);
        return new OxygenProducer(oxygenGradeESettings, oxygenOrderer, limitDate, oxygenInventory);
    }

    private OxygenRequestingStrategy createGradeBStrategy(LocalDate limitDate) {
        OxygenGradeBSettings oxygenGradeESettings = new OxygenGradeBSettings();
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(oxygenGradeESettings);
        return new OxygenProducer(oxygenGradeESettings, oxygenOrderer, limitDate, oxygenInventory);
    }

    private OxygenRequestingStrategy createGradeAStrategy(LocalDate limitDate) {
        OxygenGradeASettings oxygenGradeESettings = new OxygenGradeASettings();
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(oxygenGradeESettings);
        return new OxygenProducer(oxygenGradeESettings, oxygenOrderer, limitDate, oxygenInventory);
    }

    private OxygenRequestingStrategy setNextStrategy(OxygenRequestingStrategy strategy,
            OxygenRequestingStrategy nextStrategy) {
        strategy.setNextStrategy(nextStrategy);
        return strategy;
    }
}
