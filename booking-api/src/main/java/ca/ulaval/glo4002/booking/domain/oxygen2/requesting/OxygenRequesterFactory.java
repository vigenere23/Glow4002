package ca.ulaval.glo4002.booking.domain.oxygen2.requesting;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.ordering.OxygenOrderer;
import ca.ulaval.glo4002.booking.domain.oxygen2.ordering.OxygenOrdererFactory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeASettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeBSettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeESettings;

public class OxygenRequesterFactory {

    private OxygenOrdererFactory oxygenOrdererFactory;
    private OxygenInventory oxygenInventory;

    public OxygenRequesterFactory(OxygenOrdererFactory oxygenOrdererFactory, OxygenInventory oxygenInventory) {
        this.oxygenOrdererFactory = oxygenOrdererFactory;
        this.oxygenInventory = oxygenInventory;
    }

    public OxygenRequester create(OxygenGrade oxygenGrade, LocalDate limitDate) {
        OxygenRequester strategy = createGradeEStrategy(limitDate);

        if (oxygenGrade.compareTo(OxygenGrade.B) >= 0) {
            OxygenRequester previousStrategy = createGradeBStrategy(limitDate);
            strategy = setNextStrategy(previousStrategy, strategy);
        }

        if (oxygenGrade.compareTo(OxygenGrade.A) >= 0) {
            OxygenRequester previousStrategy = createGradeAStrategy(limitDate);
            strategy = setNextStrategy(previousStrategy, strategy);
        }

        return strategy;
    }

    private OxygenRequester createGradeEStrategy(LocalDate limitDate) {
        OxygenGradeESettings oxygenGradeESettings = new OxygenGradeESettings();
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(oxygenGradeESettings);
        return new OxygenRequester(oxygenGradeESettings, oxygenOrderer, limitDate, oxygenInventory);
    }

    private OxygenRequester createGradeBStrategy(LocalDate limitDate) {
        OxygenGradeBSettings oxygenGradeBSettings = new OxygenGradeBSettings();
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(oxygenGradeBSettings);
        return new OxygenRequester(oxygenGradeBSettings, oxygenOrderer, limitDate, oxygenInventory);
    }

    private OxygenRequester createGradeAStrategy(LocalDate limitDate) {
        OxygenGradeASettings oxygenGradeASettings = new OxygenGradeASettings();
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(oxygenGradeASettings);
        return new OxygenRequester(oxygenGradeASettings, oxygenOrderer, limitDate, oxygenInventory);
    }

    private OxygenRequester setNextStrategy(OxygenRequester strategy,
            OxygenRequester nextStrategy) {
        strategy.setNextStrategy(nextStrategy);
        return strategy;
    }
}
