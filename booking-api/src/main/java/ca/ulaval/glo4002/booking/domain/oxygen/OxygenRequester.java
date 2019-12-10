package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.oxygen.orderers.LinkedOxygenOrdererFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrderer;
import ca.ulaval.glo4002.booking.helpers.dates.DateConverter;

public class OxygenRequester {

    @Inject private LinkedOxygenOrdererFactory oxygenOrdererFactory;

    public void requestOxygen(LocalDate orderDate, OxygenGrade oxygenGrade, int requestedQuantity) {
        requestOxygen(DateConverter.toOffsetDateTimeStartOfDay(orderDate), oxygenGrade, requestedQuantity);
    }
    
    public void requestOxygen(OffsetDateTime orderDateTime, OxygenGrade oxygenGrade, int requestedQuantity) {
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(oxygenGrade);
        oxygenOrderer.order(orderDateTime, requestedQuantity);
    }
}
