package ca.ulaval.glo4002.booking.domain.oxygen2.requesting;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public class OxygenRequester {

    private OxygenRequestingStrategyFactory oxygenRequestingStrategyFactory;
    private LocalDate limitDate;

    public OxygenRequester(OxygenRequestingStrategyFactory oxygenRequestingStrategyFactory, LocalDate limitDate) {
        this.oxygenRequestingStrategyFactory = oxygenRequestingStrategyFactory;
        this.limitDate = limitDate;
    }

    public void requestOxygen(OffsetDateTime orderDateTime, OxygenGrade oxygenGrade, int requestedQuantity) {
        LocalDate orderDate = orderDateTime.toLocalDate();
        OxygenRequestingStrategy OxygenRequestingStrategy = oxygenRequestingStrategyFactory.create(oxygenGrade, limitDate);
        OxygenRequestingStrategy.requestOxygen(orderDate, requestedQuantity);
    }
}
