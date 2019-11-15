package ca.ulaval.glo4002.booking.domain.oxygen2;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenRequester {

    private OxygenRequestingStrategyFactory oxygenRequestingStrategyFactory;

    public OxygenRequester(OxygenRequestingStrategyFactory oxygenRequestingStrategyFactory) {
        this.oxygenRequestingStrategyFactory = oxygenRequestingStrategyFactory;
    }

    public void requestOxygen(OffsetDateTime orderDateTime, OxygenGrade oxygenGrade, int quantity) {
        LocalDate orderDate = orderDateTime.toLocalDate();
        OxygenRequestingStrategy oxygenRequestingStrategy = oxygenRequestingStrategyFactory.create(oxygenGrade);
        oxygenRequestingStrategy.requestOxygen(quantity, orderDate);
    }
}
