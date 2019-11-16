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
        LocalDate orderDate = orderDateTime.toLocalDate();
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(oxygenGrade, limitDate);
        oxygenOrderer.requestOxygen(orderDate, requestedQuantity);
    }
}
