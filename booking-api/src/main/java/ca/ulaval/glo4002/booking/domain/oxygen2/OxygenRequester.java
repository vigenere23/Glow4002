package ca.ulaval.glo4002.booking.domain.oxygen2;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.orderers.OxygenOrderer;
import ca.ulaval.glo4002.booking.domain.oxygen2.orderers.OxygenOrdererFactory;

public class OxygenRequester {

    private OxygenOrdererFactory oxygenOrdererFactory;
    private LocalDate limitDate;

    public OxygenRequester(OxygenOrdererFactory oxygenOrdererFactory, LocalDate limitDate) {
        this.oxygenOrdererFactory = oxygenOrdererFactory;
        this.limitDate = limitDate;
    }

    public void requestOxygen(OffsetDateTime orderDateTime, OxygenGrade oxygenGrade, int requestedQuantity) {
        requestOxygen(orderDateTime.toLocalDate(), oxygenGrade, requestedQuantity);
    }

    public void requestOxygen(LocalDate orderDate, OxygenGrade oxygenGrade, int requestedQuantity) {
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(oxygenGrade, limitDate);
        oxygenOrderer.order(orderDate, requestedQuantity);
    }
}
