package ca.ulaval.glo4002.booking.domain.oxygen2;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenRequestingStrategyFactory {

    private LocalDate limitDate;
    private OxygenInventory oxygenInventory;
    private OxygenHistory oxygenHistory;

    public OxygenRequestingStrategyFactory(FestivalDates festivalDates, OxygenInventory oxygenInventory, OxygenHistory oxygenHistory) {
        this.limitDate = festivalDates.getStartDate().minusDays(1);
        this.oxygenInventory = oxygenInventory;
        this.oxygenHistory = oxygenHistory;
    }

    public OxygenRequestingStrategy create(OxygenGrade oxygenGrade) {
        OxygenRequestingStrategy strategy = new OxygenProducerGradeA(limitDate, oxygenInventory, oxygenHistory);

        if (oxygenGrade == OxygenGrade.B) {
            OxygenRequestingStrategy previousStrategy = new OxygenProducerGradeB(limitDate, oxygenInventory, oxygenHistory);
            strategy = setPrevious(strategy, previousStrategy);
        }

        if (oxygenGrade == OxygenGrade.E) {
            OxygenRequestingStrategy previousStrategy = new OxygenBuyerGradeE();
            strategy = setPrevious(strategy, previousStrategy);
        }
        
        return strategy;
    }

    private OxygenRequestingStrategy setPrevious(OxygenRequestingStrategy strategy, OxygenRequestingStrategy previousStrategy) {
        previousStrategy.setNextStrategy(strategy);
        return previousStrategy;
    }
}
