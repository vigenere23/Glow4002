package ca.ulaval.glo4002.booking.domain.oxygen2;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.requesting.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.oxygen2.requesting.OxygenRequesterFactory;

public class OxygenQueryer {

    private OxygenRequesterFactory oxygenRequestingStrategyFactory;
    private LocalDate limitDate;

    public OxygenQueryer(OxygenRequesterFactory oxygenRequestingStrategyFactory, LocalDate limitDate) {
        this.oxygenRequestingStrategyFactory = oxygenRequestingStrategyFactory;
        this.limitDate = limitDate;
    }

    public void requestOxygen(OffsetDateTime orderDateTime, OxygenGrade oxygenGrade, int requestedQuantity) {
        LocalDate orderDate = orderDateTime.toLocalDate();
        OxygenRequester OxygenRequestingStrategy = oxygenRequestingStrategyFactory.create(oxygenGrade, limitDate);
        OxygenRequestingStrategy.requestOxygen(orderDate, requestedQuantity);
    }
}
