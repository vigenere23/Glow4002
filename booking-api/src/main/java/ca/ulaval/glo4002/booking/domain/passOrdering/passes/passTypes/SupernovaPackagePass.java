package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;
import ca.ulaval.glo4002.booking.interfaces.rest.orders.dtos.PassResponse;

public class SupernovaPackagePass extends PackagePass {

    public SupernovaPackagePass(OffsetDateTime startDate, OffsetDateTime endDate) {
        super(startDate, endDate);
        this.price = Money.of(CurrencyUnit.CAD, 700000);
    }

    public PassResponse serialize() {
        return new PassResponse(this.passNumber, PassOption.PACKAGE, PassCategory.SUPERNOVA);
    }
}
