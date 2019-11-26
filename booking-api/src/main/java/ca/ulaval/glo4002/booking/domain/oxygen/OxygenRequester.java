package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrderer;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrdererFactory;

public class OxygenRequester {

    @Inject private OxygenOrdererFactory oxygenOrdererFactory;
    @Inject private OxygenDates oxygenDates;

    public void requestOxygen(OffsetDateTime orderDateTime, OxygenGrade oxygenGrade, int requestedQuantity) {
        requestOxygen(orderDateTime.toLocalDate(), oxygenGrade, requestedQuantity);
    }

    public void requestOxygen(LocalDate orderDate, OxygenGrade oxygenGrade, int requestedQuantity) {
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(oxygenGrade, oxygenDates);
        oxygenOrderer.order(orderDate, requestedQuantity);
    }
}
